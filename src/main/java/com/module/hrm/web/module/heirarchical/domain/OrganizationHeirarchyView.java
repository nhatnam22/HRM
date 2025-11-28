package com.module.hrm.web.module.heirarchical.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.jcip.annotations.Immutable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_organization_hierarchy")
public class OrganizationHeirarchyView {

    @Id
    private Long id;

    private String base;

    private String companyCode;

    private String organizationCode;

    private String hierarchyCode;

    private String parentCode;

    private String manageCode;

    private String name;

    private Integer displayOrder;

    private String departmentCode;

    private String departmentName;

    private String userCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationHeirarchyView)) {
            return false;
        }
        return id != null && id.equals(((OrganizationHeirarchyView) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
