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
public class RunNyehmsLoginReminderLoaderJob extends QuartzJobBean{
		private NyehmsLoginReminderLoader nyehmsLoginReminderLoader;
		
		private static final Logger log = LoggerFactory.getLogger(RunNyehmsLoginReminderLoaderJob.class);

		
		
		public void setNyehmsLoginReminderLoader(NyehmsLoginReminderLoader nyehmsLoginReminderLoader) {
			this.nyehmsLoginReminderLoader = nyehmsLoginReminderLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started sending Nyehms login reminder email status");
			nyehmsLoginReminderLoader.sendNyehmsLoginReminderEmail();;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
