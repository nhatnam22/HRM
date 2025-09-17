package com.module.hrm.security;

import com.module.hrm.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;
    private List<GrantedAuthority> authorities;

    @Getter
    private String userType;

    @Getter
    private Boolean needChangePwd = Boolean.TRUE;

    @Getter
    private String langKey;

    public AuthUserDetails(User user, List<GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
        this.userType = user.getUserType();
        this.needChangePwd = user.getPwdInitFlag();
        this.langKey = user.getLangKey();
    }

    public void addAll(List<GrantedAuthority> grantedAuthorities) {
        this.authorities.addAll(grantedAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }
}
