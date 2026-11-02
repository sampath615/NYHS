package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load EHARS data into NYEHMS tables
 * 
 * @author ska03
 *
 */
public class RunEharsDataLoaderJob extends QuartzJobBean{
		private EharsDataLoader eharsDataLoader;
		
		private static final Logger log = LoggerFactory.getLogger(RunEharsDataLoaderJob.class);

		
		
		public void setEharsDataLoader(EharsDataLoader eharsDataLoader) {
			this.eharsDataLoader = eharsDataLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//Performing the Clean on Raw HIV Records
		try{
			log.error("started loading alljaro data");
			
			JobDataMap dataMap = context.getTrigger().getJobDataMap();
			
			String initiator = dataMap.getString("initiator");
			
			eharsDataLoader.loadEharsDataInToNyehmsTables(initiator);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
