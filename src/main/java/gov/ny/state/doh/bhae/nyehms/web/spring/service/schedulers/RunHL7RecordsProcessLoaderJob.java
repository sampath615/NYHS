package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;



public class RunHL7RecordsProcessLoaderJob extends QuartzJobBean{

	
	private Hl7RecordsProcessLoader hl7RecordsProcessLoader;
	

	public void setHl7RecordsProcessLoader(Hl7RecordsProcessLoader hl7RecordsProcessLoader) {
		this.hl7RecordsProcessLoader = hl7RecordsProcessLoader;
	}

	private static final Logger log = LoggerFactory.getLogger(RunHL7RecordsProcessLoaderJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try{
			log.error("started processing hl7 records job");
			
			JobDataMap dataMap = context.getTrigger().getJobDataMap();
			
			String initiator = dataMap.getString("initiator");
						
			// call your method
			hl7RecordsProcessLoader.processHl7Records(initiator);

		}catch(Exception ex){
			log.error("unable to process hl7 records cron job- : {}",   ex.getMessage());
		}
	}
}

