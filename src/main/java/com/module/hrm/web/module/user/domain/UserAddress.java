package com.module.hrm.web.module.user.domain;

import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "vmu_address")
public class UserAddress extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vmu_address_id_seq")
    @SequenceGenerator(name = "vmu_address_id_seq", sequenceName = "vmu_address_id_seq", allocationSize = 50)
    private Long id;

    private String companyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    private String name;

    private String phoneNo;

    private String address;

    private String address2;

    private String provinceCode;

    private String districtCode;

    private String wardCode;

    private Boolean defaultFlag;

    private Boolean deleteFlag;
}
