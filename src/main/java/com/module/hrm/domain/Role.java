package com.module.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import org.wildfly.common.annotation.NotNull;

@Entity
@Table(name = "jhi_role")
@JsonIncludeProperties({ "code", "name" })
public class Role extends AbstractAuditingEntity<String> implements Serializable {

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    @JsonProperty("code")
    private String name;

    @Size(max = 256)
    @Column(length = 256)
    @JsonProperty("name")
    private String description;

    private Boolean activated;

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return Objects.equals(name, ((Role) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            "}";
    }
}
