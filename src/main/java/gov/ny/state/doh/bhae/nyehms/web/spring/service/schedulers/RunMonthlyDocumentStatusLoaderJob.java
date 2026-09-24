package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load Monthly lab report
 * 
 * 
 * @author ska03
 *
 */
public class RunMonthlyDocumentStatusLoaderJob extends QuartzJobBean{
		private MonthlyDocumentStatusLoader monthlyDocumentStatusLoader;
		
		private static Logger log = LoggerFactory.getLogger(RunMonthlyDocumentStatusLoaderJob.class);
		
		
		public void setMonthlyDocumentStatusLoader(MonthlyDocumentStatusLoader monthlyDocumentStatusLoader) {
			this.monthlyDocumentStatusLoader = monthlyDocumentStatusLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started running the monthly lab report loader");
			
			JobDataMap dataMap = context.getTrigger().getJobDataMap();
			
			String initiator = dataMap.getString("initiator");
			
			monthlyDocumentStatusLoader.loadMonthlyStatus(initiator);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
