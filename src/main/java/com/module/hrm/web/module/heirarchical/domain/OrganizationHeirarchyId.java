package com.module.hrm.web.module.heirarchical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OrganizationHeirarchyId implements Serializable {

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String companyCode;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String organizationCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationHeirarchyId)) {
            return false;
        }

        OrganizationHeirarchyId id = (OrganizationHeirarchyId) o;
        return (
            Objects.equals(code, id.code) &&
            Objects.equals(companyCode, id.companyCode) &&
            Objects.equals(organizationCode, id.organizationCode)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, companyCode, organizationCode);
    }

    @Override
    public String toString() {
        return (
            "OrganizationHeirarchyId{" +
            ", code='" +
            getCode() +
            "'" +
            ", companyCode='" +
            getCompanyCode() +
            "'" +
            ", salesForceCode='" +
            getOrganizationCode() +
            "'" +
            "}"
        );
    }
}
