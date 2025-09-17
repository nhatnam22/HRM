package com.module.hrm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import org.wildfly.common.annotation.NotNull;

@Entity
@Table(name = "jhi_role_authority")
public class RoleAuthority implements Serializable {

    @EmbeddedId
    RoleAuthorityId id;

    @NotNull
    @Column(name = "authority", nullable = false, insertable = false, updatable = false)
    private String authority;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(insertable = false, updatable = false)
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not
    // remove

    public RoleAuthorityId getId() {
        return id;
    }

    public void setId(RoleAuthorityId id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public RoleAuthority authority(String authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Role getRole() {
        return role;
    }

    public RoleAuthority role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleAuthority)) {
            return false;
        }
        return id != null && id.equals(((RoleAuthority) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoleAuthority{" + "id=" + getId() + ", authority='" + getAuthority() + "'" + "}";
    }
}
