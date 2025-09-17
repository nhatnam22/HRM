package com.module.hrm.security;

import com.module.hrm.domain.Authority;
import com.module.hrm.domain.User;
import com.module.hrm.repository.UserRepository;
import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.exception.BadAuthenticationException;
import java.util.*;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@AllArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() ->
                new BadAuthenticationException("User " + lowercaseLogin + " was not found in the database", MessageKeys.LOGIN_FAILURE)
            );
    }

    private AuthUserDetails createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new BadAuthenticationException("User " + lowercaseLogin + " was not activated", MessageKeys.USER_IS_INACTIVATED);
        }

        return new AuthUserDetails(user, List.of(new SimpleGrantedAuthority(AuthoritiesConstants.USER)));
    }
}
