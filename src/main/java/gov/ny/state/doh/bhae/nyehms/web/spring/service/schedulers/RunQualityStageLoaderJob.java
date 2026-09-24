package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * This class is used by scheduler when the scheduler time starts
 * 
 * @author ska03
 *
 */
public class RunQualityStageLoaderJob extends QuartzJobBean{
		private QualityStageLoader qualityStageLoader;
		public void setQualityStageLoader(QualityStageLoader qualityStageLoader) {
			this.qualityStageLoader = qualityStageLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		
		
		String initiator = dataMap.getString("initiator");
		
		//Performing the Clean on Raw HIV Records
		qualityStageLoader.loadRunQualityStageData(initiator);
	}
	
	
}
