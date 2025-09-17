package com.module.hrm.web.module.user.domain;

import com.module.hrm.config.Constants;
import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "vmu_user")
public class UserAccount extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String companyCode;

    private String distributorCode;

    private String userCode;

    private String fullName;

    private String genderCode;

    private String identifyCardNo;

    private String identifyIssuePlace;

    private LocalDate identifyIssueDate;

    private String phoneNo;

    private LocalDate birthday;

    private LocalDate joinDate;

    private LocalDate endDate;

    private String referUserCode;

    private Boolean deleteFlag = Boolean.FALSE;

    private Long distributorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount", orphanRemoval = true)
    @BatchSize(size = 20)
    private Set<UserAddress> addresses = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "vmu_user_group",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "group_code", referencedColumnName = "code") }
    )
    @BatchSize(size = 20)
    private Set<Group> groups = new HashSet<>();

    @Override
    public Long getId() {
        return this.userId;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public boolean isSalesMan() {
        if (groups.isEmpty()) {
            return false;
        }

        return groups.stream().anyMatch(g -> Constants.SALES_MAN_GROUP_CODE.equals(g.getCode()));
    }

    public boolean isRsm() {
        if (groups.isEmpty()) {
            return false;
        }

        return groups.stream().anyMatch(g -> Constants.ASM_GROUP_CODE.equals(g.getCode()));
    }

    public boolean isSd() {
        if (groups.isEmpty()) {
            return false;
        }

        return groups.stream().anyMatch(g -> Constants.SD_GROUP_CODE.equals(g.getCode()));
    }

    public boolean isUsm() {
        if (groups.isEmpty()) {
            return false;
        }

        return groups.stream().anyMatch(g -> Constants.SUP_GROUP_CODE.equals(g.getCode()));
    }

    public boolean isSystem() {
        if (groups.isEmpty()) {
            return false;
        }

        return groups.stream().anyMatch(g -> Constants.SYSTEM_GROUP_CODE.equals(g.getCode()));
    }
}
