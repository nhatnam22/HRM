package com.module.hrm.web.authentication;

import com.module.hrm.config.filter.JwtFilter;
import com.module.hrm.security.AuthUserDetails;
import com.module.hrm.security.SecurityUtils;
import com.module.hrm.security.jwt.JwtTokenService;
import com.module.hrm.web.authentication.model.JwtToken;
import com.module.hrm.web.authentication.model.LoginRequest;
import com.module.hrm.web.common.model.SimpleRestResponse;
import com.module.hrm.web.common.utils.ResponseUtil;
import com.module.hrm.web.module.master.service.AppConstantService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateController.class);

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenService jwtTokenService;

    public AuthenticateController(JwtTokenService jwtTokenService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // check lai cho nay
    @PostMapping("/authenticate")
    public ResponseEntity<SimpleRestResponse<JwtToken>> authorize(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.jwtTokenService.createToken(authentication, loginRequest.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);

        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
        Boolean needChangePwd = authUserDetails != null && authUserDetails.getNeedChangePwd();
        if (Boolean.TRUE.equals(needChangePwd)) {
            jwt = null;
            httpHeaders.setBearerAuth(null);
        }

        return ResponseUtil.createSimpleResponse(
            new JwtToken(
                jwt,
                authUserDetails.getUsername(),
                authUserDetails.getNeedChangePwd(),
                authUserDetails.getUserType(),
                authUserDetails.getLangKey()
            )
        );
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param principal the authentication principal.
     * @return the login if the user is authenticated.
     */
    @GetMapping(value = "/authenticate", produces = MediaType.TEXT_PLAIN_VALUE)
    public String isAuthenticated(Principal principal) {
        LOG.debug("REST request to check if the current user is authenticated");
        return principal == null ? null : principal.getName();
    }
}
