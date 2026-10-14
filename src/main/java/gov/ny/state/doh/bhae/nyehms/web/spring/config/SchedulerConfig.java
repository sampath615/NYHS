package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.*;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Map;


/**
 * Quartz scheduler: 13 jobs, 12 cron triggers and the scheduler itself.
 * <p>
 * Replaces the JobDetailFactoryBean, CronTriggerFactoryBean and
 * SchedulerFactoryBean entries of spring-config.xml (and the notificationObject bean).
 * <p>
 * NAMES MATTER HERE. The method name of a job is its Quartz job name (group
 * DEFAULT): ManualQuartzJobService triggers jobs by that name from the scheduler
 * page, so do not rename the run...() methods. The key passed to newJobDetail(...)
 * is the setter on the job class (key "rawRecordsCleaner" calls
 * RunRawRecordsCleanerJob.setRawRecordsCleaner).
 * <p>
 * The loader classes (RawRecordsCleaner etc.) are @Component beans and are
 * injected here by type.
 */

@Configuration
public class SchedulerConfig {

    // ----------------------------------------------------------------  jobs

    @Bean
    public JobDetailFactoryBean runRawRecordsCleanerJob(RawRecordsCleaner  rawRecordsCleaner) {
        return newJobDetail(RunRawRecordsCleanerJob.class, "rawRecordsCleaner", rawRecordsCleaner, false);
    }

    @Bean
    public JobDetailFactoryBean runQualityStageLoaderJob(QualityStageLoader qualityStageLoader) {
        return newJobDetail(RunQualityStageLoaderJob.class, "qualityStageLoader", qualityStageLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runPrfReviewStatusLoaderJob(PrfReviewStatusLoader prfReviewStatusLoader) {
        return newJobDetail(RunPrfReviewStatusLoaderJob.class, "prfReviewStatusLoader", prfReviewStatusLoader, false);
    }

    /** No cron trigger: only started manually from the scheduler page, so it must be durable. */
    @Bean
    public JobDetailFactoryBean runCCDLoaderJob(CCDLoader ccdLoader) {
        return newJobDetail(RunCCDLoaderJob.class, "ccdLoader", ccdLoader, true);
    }
    @Bean
    public JobDetailFactoryBean runHL7RecordsProcessLoaderJob(Hl7RecordsProcessLoader hl7RecordsProcessLoader) {
        return newJobDetail(RunHL7RecordsProcessLoaderJob.class, "hl7RecordsProcessLoader", hl7RecordsProcessLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runDocumentFileReminderLoaderJob(DocumentFileReminderLoader documentFileReminderLoader) {
        return newJobDetail(RunDocumentFileReminderLoaderJob.class, "documentFileReminderLoader", documentFileReminderLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runNyehmsLoginReminderLoaderJob(NyehmsLoginReminderLoader nyehmsLoginReminderLoader) {
        return newJobDetail(RunNyehmsLoginReminderLoaderJob.class, "nyehmsLoginReminderLoader", nyehmsLoginReminderLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runNyehmsTrainingReminderLoaderJob(NyehmsTrainingReminderLoader nyehmsTrainingReminderLoader) {
        return newJobDetail(RunNyehmsTrainingReminderLoaderJob.class, "nyehmsTrainingReminderLoader", nyehmsTrainingReminderLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runQualityStageMatchResultsLoaderForBothJobs(QualityStageMatchResultsLoaderForBothJobs qualityStageMatchResultsLoaderForBothJobs) {
        return newJobDetail(RunQualityStageMatchResultsLoaderForBothJobs.class, "qualityStageMatchResultsLoaderForBothJobs", qualityStageMatchResultsLoaderForBothJobs, false);
    }

    @Bean
    public JobDetailFactoryBean runHITMessageCountLoader(HITMessageCountLoader hitMessageCountLoader) {
        return newJobDetail(RunHITMessageCountLoader.class, "hitMessageCountLoader", hitMessageCountLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runEharsDataLoader(EharsDataLoader eharsDataLoader) {
        return newJobDetail(RunEharsDataLoaderJob.class, "eharsDataLoader", eharsDataLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runWeeklyDocumentStatusLoader(WeeklyDocumentStatusLoader weeklyDocumentStatusLoader) {
        return newJobDetail(RunWeeklyDocumentStatusLoaderJob.class, "weeklyDocumentStatusLoader", weeklyDocumentStatusLoader, false);
    }

    @Bean
    public JobDetailFactoryBean runMonthlyDocumentStatusLoader(MonthlyDocumentStatusLoader monthlyDocumentStatusLoader) {
        return newJobDetail(RunMonthlyDocumentStatusLoaderJob.class, "monthlyDocumentStatusLoader", monthlyDocumentStatusLoader, false);
    }

    // ---------------------------------------------------------------- cron triggers

    @Bean
    public CronTriggerFactoryBean prfReviewStatusCronTrigger(@Qualifier("runPrfReviewStatusLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 00 07 ? * MON-FRI");
    }

    @Bean
    public CronTriggerFactoryBean hl7RecordsProcessCronTrigger(@Qualifier("runHL7RecordsProcessLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 30 19 ? * SUN-FRI");
    }

    @Bean
    public CronTriggerFactoryBean nyehmsLoginReminderCronTrigger(@Qualifier("runNyehmsLoginReminderLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 05 07 ? * MON-SUN");
    }

    @Bean
    public CronTriggerFactoryBean nyehmsTrainingReminderCronTrigger(@Qualifier("runNyehmsTrainingReminderLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 10 07 ? * MON");
    }

    @Bean
    public CronTriggerFactoryBean documentFileReminderCronTrigger(@Qualifier("runDocumentFileReminderLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 05 10 ? * MON-SUN");
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(@Qualifier("runRawRecordsCleanerJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 26 18 ? * SUN-THU");
    }

    @Bean
    public CronTriggerFactoryBean qsCronTrigger(@Qualifier("runQualityStageLoaderJob") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 56 23 ? * SUN-THU");
    }

    @Bean
    public CronTriggerFactoryBean bothQsMatchResultsTrigger(@Qualifier("runQualityStageMatchResultsLoaderForBothJobs") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 00 02 ? * MON-FRI");
    }

    @Bean
    public CronTriggerFactoryBean hitMessageCountStatusCronTrigger(@Qualifier("runHITMessageCountLoader") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 00 08 ? * MON-SUN");
    }

    @Bean
    public CronTriggerFactoryBean eharsDataLoaderCronTrigger(@Qualifier("runEharsDataLoader") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 15 03 ? * MON-SUN");
    }

    @Bean
    public CronTriggerFactoryBean weeklyDocumentStatusLoaderCronTrigger(@Qualifier("runWeeklyDocumentStatusLoader") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 30 10 ? * MON");
    }

    @Bean
    public CronTriggerFactoryBean monthlyDocumentStatusLoaderCronTrigger(@Qualifier("runMonthlyDocumentStatusLoader") JobDetail jobDetail) {
        return newCronTrigger(jobDetail, "0 30 09 01 1/1 ? *");
    }

    // ---------------------------------------------------------------- scheduler


    /**
     * Bean name "schedulerFactoryBean" as before (ManualQuartzJobService injects the
     * Scheduler it produces). "triggers" is every Trigger bean in the context, which
     * is exactly the 12 cron triggers declared above.
     */
    @Bean
    public SchedulerFactoryBean  schedulerFactoryBean(@Qualifier("runCCDLoaderJob") JobDetail runCCDLoaderJob, Trigger[] triggers){

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(runCCDLoaderJob);
        schedulerFactoryBean.setTriggers(triggers);
        return schedulerFactoryBean;
    }

    // ---------------------------------------------------------------- helpers

    private static JobDetailFactoryBean newJobDetail(Class<? extends Job> jobClass, String loaderProperty, Object loader,
                                                     boolean durable) {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setJobClass(jobClass);
        jobDetail.setJobDataAsMap(Map.of(loaderProperty, loader));
        jobDetail.setDurability(durable);
        return jobDetail;
    }

    private static CronTriggerFactoryBean newCronTrigger(JobDetail jobDetail, String cronExpression) {

        CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
        cronTrigger.setJobDetail(jobDetail);
        cronTrigger.setCronExpression(cronExpression);
        return cronTrigger;
    }

}
