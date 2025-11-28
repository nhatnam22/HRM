package com.module.hrm.web.module.heirarchical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OrganizationId {

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String companyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationId)) {
            return false;
        }

        OrganizationId id = (OrganizationId) o;
        return Objects.equals(code, id.code) && Objects.equals(companyCode, id.companyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, companyCode);
    }

    @Override
    public String toString() {
        return "OrganzationId{" + ", code='" + getCode() + "'" + ", companyCode='" + getCompanyCode() + "'" + "}";
    }
}
