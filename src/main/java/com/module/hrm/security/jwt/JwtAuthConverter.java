package com.module.hrm.security.jwt;

import com.module.hrm.domain.User;
import com.module.hrm.repository.RoleAuthorityRepository;
import com.module.hrm.repository.UserRepository;
import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.exception.BadAuthenticationException;
import com.module.hrm.web.module.user.domain.UserAccount;
import com.module.hrm.web.module.user.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;
    private final UserAccountRepository dmsUserRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    public JwtAuthConverter(
        UserRepository userRepository,
        UserAccountRepository dmsUserRepository,
        RoleAuthorityRepository roleAuthorityRepository
    ) {
        this.userRepository = userRepository;
        this.dmsUserRepository = dmsUserRepository;
        this.roleAuthorityRepository = roleAuthorityRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();

        return userRepository
            .findOneWithRolesByLogin(username)
            .map(user -> createJwtAuthenticationToken(jwt, username, user))
            .orElseThrow(() ->
                new BadAuthenticationException("User " + username + " was not found in the database", MessageKeys.LOGIN_FAILURE)
            );
    }

    private JwtAuthToken createJwtAuthenticationToken(Jwt jwt, String username, User user) {
        if (!user.isActivated()) {
            throw new BadAuthenticationException("User " + username + " was not activated", MessageKeys.USER_IS_INACTIVATED);
        }

        // Get DMS User information
        UserAccount userAccount = dmsUserRepository
            .findOneWithGroupByDeleteFlagIsFalseAndUserId(user.getId())
            .orElseThrow(() ->
                new BadAuthenticationException("User " + username + " was not found in the database", MessageKeys.USER_IS_DELETED)
            );

        List<String> roleNames = new ArrayList<>();
        user
            .getRoles()
            .forEach(authority -> {
                roleNames.add(authority.getName());
            });

        // Get user's roles and authorities
        List<GrantedAuthority> grantedAuthorities = roleNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        Set<String> authorities = roleAuthorityRepository.findDistinctByRoles(Set.copyOf(roleNames));
        authorities.stream().map(authority -> new SimpleGrantedAuthority(authority)).forEach(grantedAuthorities::add);

        return new JwtAuthToken(jwt, username, grantedAuthorities, user, userAccount);
    }
}
