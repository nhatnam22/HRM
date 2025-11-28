package com.module.hrm.web.module.scheduler.domain;

import com.module.hrm.domain.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "umj_schedule_job")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@Builder
public class SchedulerJob extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "umj_schedule_job_id_seq")
    @SequenceGenerator(name = "umj_schedule_job_id_seq", sequenceName = "umj_schedule_job_id_seq", allocationSize = 50)
    private Long id;

    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String jobClass;
    private String cronExpression;
    private String description;
    private String interfaceName;
    private Long repeatTime;
    private Boolean cronJob;

    @Default
    private Boolean deleteFlag = Boolean.FALSE;
}
