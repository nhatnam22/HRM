package com.module.hrm.security.jwt;

import com.module.hrm.domain.User;
import com.module.hrm.web.module.user.domain.Group;
import com.module.hrm.web.module.user.domain.UserAccount;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final Jwt jwt;
    private final String principal;

    @Getter
    private String companyCode;

    @Getter
    private Long userId;

    @Getter
    private String userCode;

    @Getter
    private String userType;

    @Getter
    private String email;

    @Getter
    private String groupCode;

    @Getter
    private String fullName;

    @Getter
    private String langKey;

    public JwtAuthToken(Jwt jwt, String principal, Collection<? extends GrantedAuthority> authorities, User user, UserAccount userAccount) {
        super(authorities);
        this.jwt = jwt;
        this.principal = principal;
        setAuthenticated(true); // Important!

        this.langKey = user.getLangKey();
        this.email = user.getEmail();
        this.userId = user.getId();
        this.userType = user.getUserType();

        this.companyCode = userAccount.getCompanyCode();
        this.userCode = userAccount.getUserCode();
        this.fullName = userAccount.getFullName();

        Group group = userAccount.getGroups().stream().findFirst().orElse(null);
        if (group != null) {
            this.groupCode = group.getCode();
        }
    }

    @Override
    public Object getCredentials() {
        return jwt.getTokenValue(); // or null if you don’t expose token
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
