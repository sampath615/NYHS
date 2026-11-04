package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to send Document reminders 
 * 
 * @author ska03
 *
 */
public class RunDocumentFileReminderLoaderJob extends QuartzJobBean{
		private DocumentFileReminderLoader documentFileReminderLoader;
		
		private static Logger log = LoggerFactory.getLogger(RunDocumentFileReminderLoaderJob.class);
		
		
		public void setDocumentFileReminderLoader(DocumentFileReminderLoader documentFileReminderLoader) {
			this.documentFileReminderLoader = documentFileReminderLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started sending Document file reminder email status");
			documentFileReminderLoader.sendDocumentFileReminderEmail();;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
