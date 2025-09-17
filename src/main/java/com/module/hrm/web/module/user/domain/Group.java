package com.module.hrm.web.module.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.*;
import org.wildfly.common.annotation.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vmu_group")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIncludeProperties({ "code", "name" })
public class Group extends AbstractAuditingEntity<String> implements Serializable {

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String code;

    @JsonIgnore
    @Size(max = 50)
    @Column(length = 50)
    private String companyCode;

    @Size(max = 256)
    @Column(length = 256)
    private String name;

    @JsonIgnore
    private Integer displayOrder;

    @JsonIgnore
    private Boolean deleteFlag = Boolean.FALSE;

    @Override
    public String getId() {
        return this.code;
    }
}
