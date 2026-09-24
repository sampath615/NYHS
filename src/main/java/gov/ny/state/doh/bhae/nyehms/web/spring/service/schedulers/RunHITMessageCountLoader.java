package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import gov.ny.state.doh.bhae.nyehms.web.spring.controller.schedulers.ManualQuartzJobController;

/**
 * Entry point for the Quartz SchedulerBean from spring-config.xml or for the
 * {@link ManualQuartzJobController.triggerSchedulerJobManually}
 * 
 * @author mxm69
 *
 */

public class RunHITMessageCountLoader extends QuartzJobBean {

	private HITMessageCountLoader hitMessageCountLoader;
	private static Logger log = LoggerFactory.getLogger(RunHITMessageCountLoader.class);

	

	public void setHitMessageCountLoader(HITMessageCountLoader hitMessageCountLoader) {
		this.hitMessageCountLoader = hitMessageCountLoader;
	}



	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		String initiator = dataMap.getString("initiator");
		log.error( "{} reached RunHITMessageCountLoaderJob.executeInternal",initiator);
		hitMessageCountLoader.loadHITMessageCountsFromUphn(initiator);
	}

}
