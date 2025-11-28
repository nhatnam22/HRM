package com.module.hrm.web.module.heirarchical.domain;

import com.module.hrm.domain.AbstractAuditingEntity;
import com.module.hrm.web.common.utils.CompareUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
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
@Table(name = "vmt_organization_hierarchy")
public class OrganizationHeirarchy extends AbstractAuditingEntity<OrganizationHeirarchyId> implements Serializable {

    @EmbeddedId
    OrganizationHeirarchyId id;

    @Size(max = 256)
    @Column(length = 256)
    private String name;

    private String manageCode;

    private String parentCode;

    private Integer displayOrder;

    private Instant deactivedDatetime;

    private Boolean deleteFlag;

    public Boolean getActiveFlag() {
        return CompareUtil.compareToDeactive(deactivedDatetime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationHeirarchy)) {
            return false;
        }
        return id != null && id.equals(((OrganizationHeirarchy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "OrganizationHeirarchy{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
    }
}
