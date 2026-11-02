package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Entry point for the Quartz SchedulerBean from spring-config.xml or for the
 * {@link ManualQuartzJobController.triggerSchedulerJobManually}
 * 
 * @author cdf02
 *
 */
public class RunCCDLoaderJob extends QuartzJobBean {

	private CCDLoader ccdLoader;

	private static final Logger log = LoggerFactory.getLogger(RunCCDLoaderJob.class);

	public void setCcdLoader(CCDLoader ccdLoader) {
		this.ccdLoader = ccdLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			log.error("started processing ccd records job");

			JobDataMap dataMap = context.getTrigger().getJobDataMap();

			String initiator = dataMap.getString("initiator");

			ccdLoader.loadCCDIntoRAWTables(initiator);
		} catch (Exception ex) {
			log.error("unable to process ccd records cron job- : {}", ex.getMessage());
		}

	}

}
