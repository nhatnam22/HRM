package com.module.hrm.web.module.heirarchical.domain;

import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vmt_organization")
public class Organization extends AbstractAuditingEntity<OrganizationId> implements Serializable {

    @EmbeddedId
    OrganizationId id;

    @Size(max = 256)
    @Column(length = 256)
    private String name;

    private Integer displayOrder;

    private Boolean deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
    }
}
