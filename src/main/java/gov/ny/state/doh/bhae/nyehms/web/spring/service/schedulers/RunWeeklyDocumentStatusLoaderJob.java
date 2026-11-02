package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load weekly lab report
 * 
 * @author ska03
 *
 */
public class RunWeeklyDocumentStatusLoaderJob extends QuartzJobBean{
		private WeeklyDocumentStatusLoader weeklyDocumentStatusLoader;
		
		private static final Logger log = LoggerFactory.getLogger(RunWeeklyDocumentStatusLoaderJob.class);
		
		
		public void setWeeklyDocumentStatusLoader(WeeklyDocumentStatusLoader weeklyDocumentStatusLoader) {
			this.weeklyDocumentStatusLoader = weeklyDocumentStatusLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started running the Weekly status loader");
			
			JobDataMap dataMap = context.getTrigger().getJobDataMap();
			
			String initiator = dataMap.getString("initiator");
			
			weeklyDocumentStatusLoader.loadWeeklyStatus(initiator);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
