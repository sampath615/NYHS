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
public class RunRawRecordsCleanerJob extends QuartzJobBean{
	
	
	private RawRecordsCleaner rawRecordsCleaner;
	
	public void setRawRecordsCleaner(RawRecordsCleaner rawRecordsCleaner) {
		this.rawRecordsCleaner = rawRecordsCleaner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
			JobDataMap dataMap = context.getTrigger().getJobDataMap();
			
			String initiator = dataMap.getString("initiator");
			
			String RUN_SECOND_JOB_FOR_DEAD = dataMap.getString("RUN_SECOND_JOB_FOR_DEAD");
			
			rawRecordsCleaner.cleanNyehmsRawRecords(initiator, RUN_SECOND_JOB_FOR_DEAD);
		
		
	}
	
	
}
