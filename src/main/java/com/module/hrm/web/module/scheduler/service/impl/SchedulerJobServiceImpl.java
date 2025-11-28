package com.module.hrm.web.module.scheduler.service.impl;

import com.module.hrm.web.common.utils.LocalDateUtil;
import com.module.hrm.web.module.scheduler.domain.SchedulerJob;
import com.module.hrm.web.module.scheduler.repository.SchedulerJobRepository;
import com.module.hrm.web.module.scheduler.service.SchedulerJobService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchedulerJobServiceImpl implements SchedulerJobService {

    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private SchedulerJobRepository scheduleJobRepository;

    private ApplicationContext context;

    //    private TypeService typeService;
    //
    //    private OpenDateService openDateService;

    @Autowired
    private SchedulerJobCreator scheduleCreator;

    public SchedulerMetaData getMetaData() throws SchedulerException {
        SchedulerMetaData metaData = scheduler.getMetaData();
        return metaData;
    }

    @Override
    public boolean deleteJob(SchedulerJob jobInfo) {
        try {
            SchedulerJob getJobInfo = scheduleJobRepository.findByJobName(jobInfo.getJobName());
            scheduleJobRepository.delete(getJobInfo);
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " deleted.");
            return schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean pauseJob(SchedulerJob jobInfo) {
        try {
            SchedulerJob getJobInfo = scheduleJobRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("PAUSED");
            scheduleJobRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " paused.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean resumeJob(SchedulerJob jobInfo) {
        try {
            SchedulerJob getJobInfo = scheduleJobRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("RESUMED");
            scheduleJobRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " resumed.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public boolean startJobNow(SchedulerJob jobInfo) {
        try {
            SchedulerJob getJobInfo = scheduleJobRepository.findByJobName(jobInfo.getJobName());
            getJobInfo.setJobStatus("SCHEDULED & STARTED");
            scheduleJobRepository.save(getJobInfo);
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled and started now.");
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
            return false;
        }
    }

    @Override
    public List<SchedulerJob> getSchedulerJobs() {
        return scheduleJobRepository.findAllByDeleteFlagIsFalse();
    }

    //    @Override
    //    @Transactional
    //    public void initJobs() {
    //        var nowDate = LocalDateUtil.nowLocalDate();
    //        var nowDateTime = LocalDateUtil.nowLocalDateTime();
    //        List<SchedulerJob> existingSchedulers = getSchedulerJobs();
    //        if (!existingSchedulers.isEmpty()) {
    //            existingSchedulers.forEach(x -> {
    //                createScheduler(x.getJobName(), x.getJobGroup(), x.getJobClass(), x.getCronExpression());
    //            });
    //        }
    //
    //        List<OpenDate> openDates = openDateService.getOpeningOrderDateFromDeliveryDate(nowDate);
    //        if (!openDates.isEmpty()) {
    //            // ĐHKH
    //            openDates
    //                .stream()
    //                .filter(x -> nowDateTime.compareTo(x.getOrderDateT2To().atTime(LocalTime.parse(x.getOrderDateT2Time()))) < 0)
    //                .forEach(x -> {
    //                    var jobName = "OpenOrder_T2_" + x.getId();
    //                    var jobTime = LocalTime.parse(x.getOrderDateT2Time());
    //                    var executeDateTime = x.getOrderDateT2To().atTime(jobTime);
    //                    createOneTimeScheduler(
    //                        jobName,
    //                        "OpenOrdersGroup",
    //                        "com.mdlz.dms.web.modules.scheduler.service.impl.T2OpeningDateScheduler",
    //                        x.getId(),
    //                        executeDateTime
    //                    );
    //                });
    //
    //            // ĐHBS
    //            openDates
    //                .stream()
    //                .filter(x -> nowDateTime.compareTo(x.getOrderDateT1To().atTime(LocalTime.parse(x.getOrderDateT1Time()))) < 0)
    //                .forEach(x -> {
    //                    var jobName = "OpenOrder_T1_" + x.getId();
    //                    var jobTime = LocalTime.parse(x.getOrderDateT1Time());
    //                    var executeDateTime = x.getOrderDateT1To().atTime(jobTime);
    //                    createOneTimeScheduler(
    //                        jobName,
    //                        "OpenOrdersGroup",
    //                        "com.mdlz.dms.web.modules.scheduler.service.impl.T1OpeningDateScheduler",
    //                        x.getId(),
    //                        executeDateTime
    //                    );
    //                });
    //        }
    /*List<Type> types = typeService.findAllByCommonCodeIn(List.of(CommonCode.CUT_OFF_TIME), SecurityUtils.extractCurrentCompanyCode());
		
		// SuggestOrders
		Type suggestOrderType = types.stream()
				.filter(x -> AppConstants.ORDER_SUGGESTION.equals(x.getId().getTypeCode())).findFirst().orElse(null);
		// ConfirmOrders
		Type confirmOrderType = types.stream().filter(x -> AppConstants.CONFIRM_ORDER.equals(x.getId().getTypeCode()))
				.findFirst().orElse(null);
		// ConfirmOrders T3
		Type confirmOrderT3Type = types.stream()
				.filter(x -> AppConstants.CONFIRM_ORDER_T3.equals(x.getId().getTypeCode())).findFirst().orElse(null);
		// ReturnOrders
		Type returnOrderType = types.stream()
				.filter(x -> AppConstants.COMBINE_RETURN_ORDER.equals(x.getId().getTypeCode())).findFirst()
				.orElse(null);
		
		if (suggestOrderType != null) {
			// SuggestOrder
			createScheduler(AppConstants.JOB_NAME_SUGGEST_ORDER_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_SUGGEST_ORDER_SCHEDULER, ConvertUtil.getCronTimeFromString(
							suggestOrderType.getChar1(), PatternConstants.CRON_TIME_SUGGEST_ORDER_FORMAT));
		}
		
		if (confirmOrderType != null) {
			// T2
			createScheduler(AppConstants.JOB_NAME_T2_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_T2_SCHEDULER, ConvertUtil
							.getCronTimeFromString(confirmOrderType.getChar1(), PatternConstants.CRON_TIME_T2_FORMAT));
			// T1
			createScheduler(AppConstants.JOB_NAME_T1_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_T1_SCHEDULER, ConvertUtil
					.getCronTimeFromString(confirmOrderType.getChar2(), PatternConstants.CRON_TIME_T1_FORMAT));
			// AdditionalOrder
			createScheduler(AppConstants.JOB_NAME_ADDITIONAL_ORDER_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_ADDITIONAL_ORDER_SCHEDULER, ConvertUtil
					.getCronTimeFromString(confirmOrderType.getChar3(), PatternConstants.CRON_TIME_ADDITIONAL_ORDER_FORMAT));
		}
		
		if (confirmOrderT3Type != null) {
			// T3
			createScheduler(AppConstants.JOB_NAME_T3_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_T3_SCHEDULER, ConvertUtil
					.getCronTimeFromString(confirmOrderT3Type.getChar1(), PatternConstants.CRON_TIME_T3_FORMAT));
		}
		
		if (returnOrderType != null) {
			// ReturnOrder
			createScheduler(AppConstants.JOB_NAME_RETURN_ORDER_SCHEDULER, AppConstants.JOB_GROUP,
					AppConstants.QUALIFIED_NAME_RETURN_ORDER_SCHEDULER, ConvertUtil
					.getCronTimeFromString(returnOrderType.getChar1(), PatternConstants.CRON_TIME_RETURN_ORDER_FORMAT));
		}*/
    //    }

    /**
     * createScheduler
     *
     * @param jobName
     * @param jobGroup
     * @param classPackage
     * @param cron
     */
    private void createScheduler(String jobName, String jobGroup, String qualifiedName, String cron) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail;
        try {
            jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(qualifiedName))
                .withIdentity(jobName, jobGroup)
                .build();
            Trigger trigger = scheduleCreator.createCronTrigger(jobName, new Date(), cron, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * createOneTimeScheduler
     *
     * @param jobName
     * @param jobGroup
     * @param qualifiedName
     * @param executeTime
     */
    private void createOneTimeScheduler(
        String jobName,
        String jobGroup,
        String qualifiedName,
        Long openingOrderId,
        LocalDateTime executeTime
    ) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail;
        try {
            jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(qualifiedName))
                .withIdentity(jobName, jobGroup)
                .build();
            SimpleTrigger trigger = scheduleCreator.createSimpleTrigger(
                jobName,
                new Date(LocalDateUtil.getEpochMilli(executeTime)),
                1L,
                SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
            );
            jobDetail.getJobDataMap().put("openingOrderId", openingOrderId);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveOrupdate(SchedulerJob scheduleJob) throws Exception {
        scheduleJob.setJobClass(scheduleJob.getJobClass());
        scheduleJob.setCronJob(true);
        if (ObjectUtils.isEmpty(scheduleJob.getId())) {
            log.info("Job Info: {}", scheduleJob);
            scheduleNewJob(scheduleJob);
        } else {
            updateScheduleJob(scheduleJob);
        }
        scheduleJob.setDescription("Job number " + scheduleJob.getId());
        scheduleJob.setInterfaceName("interface_" + scheduleJob.getId());
        log.info(">>>>> jobName = [" + scheduleJob.getJobName() + "]" + " created.");
    }

    @Override
    public boolean isRunningTask(String jobName) {
        try {
            List<JobExecutionContext> excutingJobs = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext jobExecutionContext : excutingJobs) {
                if (jobExecutionContext.getJobDetail().getKey().getName().equals(jobName)) {
                    return true;
                }
            }
            return false;
        } catch (SchedulerException ex) {
            log.error("Failed to get currently executing jobs: " + ex.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private void scheduleNewJob(SchedulerJob jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                .build();
            if (!scheduler.checkExists(jobDetail.getKey())) {
                jobDetail = scheduleCreator.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
                    false,
                    context,
                    jobInfo.getJobName(),
                    jobInfo.getJobGroup()
                );

                Trigger trigger;
                if (jobInfo.getCronJob()) {
                    trigger = scheduleCreator.createCronTrigger(
                        jobInfo.getJobName(),
                        new Date(),
                        jobInfo.getCronExpression(),
                        SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
                    );
                } else {
                    trigger = scheduleCreator.createSimpleTrigger(
                        jobInfo.getJobName(),
                        new Date(),
                        jobInfo.getRepeatTime(),
                        SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
                    );
                }
                scheduler.scheduleJob(jobDetail, trigger);
                jobInfo.setJobStatus("SCHEDULED");
                scheduleJobRepository.save(jobInfo);
                log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateScheduleJob(SchedulerJob jobInfo) {
        Trigger newTrigger;
        if (jobInfo.getCronJob()) {
            newTrigger = scheduleCreator.createCronTrigger(
                jobInfo.getJobName(),
                new Date(),
                jobInfo.getCronExpression(),
                SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
            );
        } else {
            newTrigger = scheduleCreator.createSimpleTrigger(
                jobInfo.getJobName(),
                new Date(),
                jobInfo.getRepeatTime(),
                SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
            );
        }
        try {
            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), newTrigger);
            jobInfo.setJobStatus("EDITED & SCHEDULED");
            scheduleJobRepository.save(jobInfo);
            log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " updated and scheduled.");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
