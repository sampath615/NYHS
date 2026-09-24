package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.sierra.SierraWebService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

/**
 * Quartz scheduler class to load PRF Review Status to the users
 *  
 * @author ska03
 *
 */
public class SierraServiceLoader{
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(SierraServiceLoader.class);
	
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	
	@Autowired
	SierraWebService sierraWebService;
	
	@Autowired
	SendEmail sendEmail;

	public void loadRASDocumentsSierraResponse(){
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized(this){
			
			log.error("Checking before properties  {}",EnvironmentProperties.envProperties.getProperty("loadSchedulers"));
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if("Y".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers"))){
											
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table). Most of the Scheduler input parameters will be inputed from Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday("LAST_SIERRA_SCHEDULER_RUN_DATE", null, null, schedulerMetaData);
								
				log.error("Sierra scheduler  isSchedulerRanToday {}",isSchedulerRanToday);
				
				//Checking Server IP address, scheduler calling from and is Scheduler ran today.
				if(!isSchedulerRanToday && "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_SIERRA_SERVICE_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){
					
					
					
					
					sierraWebService.loadRASDocumentsSierraResponse();
					
					
//					StringBuilder message = new StringBuilder();
//					
//					log.error("Started the process to send Details Process status email");
//
//					try{
//						int prfReviewDocsCount = SierraWebService.getPrfReviewDocsCount();
//						
//						if(prfReviewDocsCount == 0){
//							message.append("No PRFs awaiting Central Office Review");
//						}else{
//							message.append("There are ").append(prfReviewDocsCount).append(" PRF(s) awaiting Central Office Review");
//							
//						}
//
//						sendEmail.processEmail(null, "PRFs queued for review", message.toString(), "PRF_REVIEW", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
//						
//					}catch(Exception e){
//						log.error("Failed to get the PRF review records count");
//						log.error(e.getMessage());
//					}
					
				}
			}
		}
		
	}
	
	
	
}
