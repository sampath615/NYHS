package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz scheduler class to load ALLJARO data to JAROT
 * 
 * @author ska03
 *
 */
public class RunAllJaroLoaderJob extends QuartzJobBean{
		private AllJaroLoader allJaroLoader;
		
		private static Logger log = LoggerFactory.getLogger(RunAllJaroLoaderJob.class);
		
		
		public void setAllJaroLoader(AllJaroLoader allJaroLoader) {
			this.allJaroLoader = allJaroLoader;
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
			
			String RUN_SECOND_JOB_FOR_DEAD = dataMap.getString("RUN_SECOND_JOB_FOR_DEAD");
			
			
			
			
			
			allJaroLoader.loadAllJaroData(initiator, RUN_SECOND_JOB_FOR_DEAD);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
