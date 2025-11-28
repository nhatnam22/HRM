package com.module.hrm.web.module.scheduler.repository;

import com.module.hrm.web.module.scheduler.domain.SchedulerJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerJobRepository extends JpaRepository<SchedulerJob, Long> {
    SchedulerJob findByJobName(String jobName);

    List<SchedulerJob> findAllByDeleteFlagIsFalse();
}
