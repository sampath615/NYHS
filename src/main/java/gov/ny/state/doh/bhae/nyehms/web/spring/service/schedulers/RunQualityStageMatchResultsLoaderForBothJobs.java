package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * This class is used to load the Quality stage match results for both the jobs
 *  
 * This class is used by scheduler when the scheduler time starts
 * 
 * @author ska03
 *
 */
public class RunQualityStageMatchResultsLoaderForBothJobs extends QuartzJobBean{
		private QualityStageMatchResultsLoaderForBothJobs qualityStageMatchResultsLoaderForBothJobs;
		public void setQualityStageMatchResultsLoaderForBothJobs(QualityStageMatchResultsLoaderForBothJobs qualityStageMatchResultsLoaderForBothJobs) {
			this.qualityStageMatchResultsLoaderForBothJobs = qualityStageMatchResultsLoaderForBothJobs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		
		
		String initiator = dataMap.getString("initiator");
		
		qualityStageMatchResultsLoaderForBothJobs.loadBothQualityStageMatchResultsData(initiator);
	}
	
	
}
