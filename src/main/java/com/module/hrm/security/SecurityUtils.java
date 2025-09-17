package com.module.hrm.security;

import com.module.hrm.config.Constants;
import com.module.hrm.security.jwt.JwtAuthToken;
import com.module.hrm.web.common.enumeration.UserType;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    private SecurityUtils() {}

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * Checks if the current user has any of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has any of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
            authentication != null && getAuthorities(authentication).anyMatch(authority -> Arrays.asList(authorities).contains(authority))
        );
    }

    /**
     * Checks if the current user has none of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has none of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
        return !hasCurrentUserAnyOfAuthorities(authorities);
    }

    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    //---Helper method-------------------------------------------------------------
    /**
     * Get the user code of the current user.
     *
     * @return the user code of the current user.
     */
    public static String extractCurrentUserCode() {
        if (extractUserDetails() != null) {
            return extractUserDetails().getUserCode();
        }

        return Constants.SYSTEM;
    }

    /**
     * Get the company code of the current user.
     *
     * @return the company code of the current user.
     */
    public static String extractCurrentCompanyCode() {
        if (extractUserDetails() != null) {
            return extractUserDetails().getCompanyCode();
        }

        return null;
    }

    /**
     * Get the distributor view code of the current user.
     *
     * @return the distributor view code of the current user.
     */
    public static String extractUserType() {
        if (extractUserDetails() != null) {
            return extractUserDetails().getUserType();
        }

        return UserType.NONE.getValue();
    }

    /**
     * Check User is administrator
     *
     * @return
     */
    public static boolean isAdministrator() {
        return UserType.ADMIN.getValue().equals(extractUserType());
    }

    public static JwtAuthToken extractUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (securityContext.getAuthentication() == null) {
            return null;
        } else if (securityContext.getAuthentication() instanceof JwtAuthToken) {
            JwtAuthToken jwtAuthToken = (JwtAuthToken) securityContext.getAuthentication();
            return jwtAuthToken;
        }

        return null;
    }

    public static Set<String> getCurrentAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthorities(authentication).collect(Collectors.toSet());
    }
}
