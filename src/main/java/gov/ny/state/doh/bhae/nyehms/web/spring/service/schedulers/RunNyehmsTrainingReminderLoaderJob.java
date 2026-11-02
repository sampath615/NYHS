package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to send Nyehms login reminder
 * 
 * @author ska03
 *
 */
public class RunNyehmsTrainingReminderLoaderJob extends QuartzJobBean{
		private NyehmsTrainingReminderLoader nyehmsTrainingReminderLoader;
		
		private static final Logger log = LoggerFactory.getLogger(RunNyehmsTrainingReminderLoaderJob.class);

		
		
		public void setNyehmsTrainingReminderLoader(NyehmsTrainingReminderLoader nyehmsTrainingReminderLoader) {
			this.nyehmsTrainingReminderLoader = nyehmsTrainingReminderLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
			log.error("started sending Nyehms Training reminder email status");
			nyehmsTrainingReminderLoader.sendNyehmsTrainingReminderEmail();;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
