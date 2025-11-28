package com.module.hrm.web.module.heirarchical.domain;

import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
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
@Entity
@Table(name = "vmt_organization_department")
public class OrganizationDepartment extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vmt_organization_department_id_seq")
    @SequenceGenerator(
        name = "vmt_organization_department_id_seq",
        sequenceName = "vmt_organization_department_id_seq",
        allocationSize = 50
    )
    private Long id;

    private String companyCode;

    private String hierarchyCode;

    private String departmentCode;

    private String departmentName;

    private Boolean deleteFlag;

    //private Long distributorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationDepartment)) {
            return false;
        }
        return id != null && id.equals(((OrganizationDepartment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
