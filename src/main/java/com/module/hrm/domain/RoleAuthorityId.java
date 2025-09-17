package com.module.hrm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import org.wildfly.common.annotation.NotNull;

@Embeddable
public class RoleAuthorityId implements java.io.Serializable {

    @NotNull
    @Column(name = "authority")
    private String authority;

    @NotNull
    @Column(name = "role_name")
    private String roleName;

    public RoleAuthorityId() {}

    public RoleAuthorityId(String authority, String roleName) {
        this.authority = authority;
        this.roleName = roleName;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleAuthorityId)) {
            return false;
        }

        RoleAuthorityId roleAuthorityId = (RoleAuthorityId) o;
        return Objects.equals(authority, roleAuthorityId.authority) && Objects.equals(roleName, roleAuthorityId.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority, roleName);
    }

    @Override
    public String toString() {
        return "RoleAuthorityId{" + ", authority='" + getAuthority() + "'" + ", roleName='" + getRoleName() + "'" + "}";
    }
}
