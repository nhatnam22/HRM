package com.module.hrm.config.filter;

import com.module.hrm.repository.RoleAuthorityRepository;
import com.module.hrm.security.AuthUserDetails;
import com.module.hrm.security.DomainUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import tech.jhipster.config.JHipsterProperties;

public class JwtFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    //     private final Key key;

    private final DomainUserDetailsService userDetailsService;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final JwtParser jwtParser;

    public JwtFilter(
        DomainUserDetailsService userDetailsService,
        RoleAuthorityRepository roleAuthorityRepository,
        JHipsterProperties jHipsterProperties
    ) {
        this.userDetailsService = userDetailsService;
        this.roleAuthorityRepository = roleAuthorityRepository;

        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            log.warn(
                "Warning: the JWT key used is not Base64-encoded. " +
                "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security."
            );
            secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        //        key = Keys.hmacShaKeyFor(keyBytes);

        this.jwtParser = Jwts.parserBuilder().setSigningKey(keyBytes).build();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (httpServletRequest.getHeader(AUTHORIZATION_HEADER) != null) {
            String jwt = resolveToken(httpServletRequest);

            if (StringUtils.hasText(jwt) && this.validateToken(jwt)) {
                String username = this.getUsernameFromToken(jwt);

                AuthUserDetails userDetails = null;
                try {
                    // Get user detail to validate activated, expired
                    userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(username);
                    //                    if (userDetails.getNeedChangePwd()) {
                    //                        throw new BadAuthenticationException("Need to change password", MessageKeys.NEED_CHANGE_PASSWORD);
                    //                    }
                } catch (RuntimeException e) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                    return;
                }

                // Load more authorities
                // Get user's roles and authorities
                List<String> roleNames = new ArrayList<>();
                userDetails
                    .getAuthorities()
                    .forEach(authority -> {
                        roleNames.add(authority.getAuthority());
                    });

                List<GrantedAuthority> grantedAuthorities = new ArrayList<>(userDetails.getAuthorities());
                Set<String> authorities = roleAuthorityRepository.findDistinctByRoles(Set.copyOf(roleNames));
                authorities.stream().map(authority -> new SimpleGrantedAuthority(authority)).forEach(grantedAuthorities::add);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    grantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.trace("Invalid JWT token: expired", e);
        } catch (UnsupportedJwtException e) {
            log.trace("Invalid JWT token: unsupported", e);
        } catch (MalformedJwtException e) {
            log.trace("Invalid JWT token: malformed", e);
        } catch (SignatureException e) {
            log.trace("Invalid JWT token: invalid signature", e);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error: {}", e.getMessage());
        }
        return false;
    }
}
