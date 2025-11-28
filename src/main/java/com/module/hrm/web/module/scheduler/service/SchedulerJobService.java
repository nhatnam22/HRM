package com.module.hrm.web.module.scheduler.service;

import com.module.hrm.web.module.scheduler.domain.SchedulerJob;
import java.util.List;

public interface SchedulerJobService {
    void saveOrupdate(SchedulerJob scheduleJob) throws Exception;

    boolean deleteJob(SchedulerJob jobInfo);

    boolean pauseJob(SchedulerJob jobInfo);

    boolean resumeJob(SchedulerJob jobInfo);

    boolean startJobNow(SchedulerJob jobInfo);

    //    void initJobs();

    List<SchedulerJob> getSchedulerJobs();

    boolean isRunningTask(String jobName);
}
