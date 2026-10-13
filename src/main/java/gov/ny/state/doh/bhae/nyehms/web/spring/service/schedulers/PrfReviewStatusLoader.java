package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.PRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to load PRF Review Status to the users
 *  
 * @author ska03
 *
 */
@Component
public class PrfReviewStatusLoader{
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(PrfReviewStatusLoader.class);
	
	@Autowired
	PRFDocumentService pRFDocumentService;
	
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	
	
	@Autowired
	SendEmail sendEmail;

	public void loadPrfReviewStatus(){
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized(this){
			
			log.error("Checking before properties   {}",EnvironmentProperties.envProperties.getProperty("loadSchedulers"));
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if("Y".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers"))){
											
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table). Most of the Scheduler input parameters will be inputed from Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				//Checking Server IP address, scheduler calling from 
				if("Y".equals(schedulerMetaData.get(CommonConstants.LOAD_PRF_REVIEW_STATUS_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){
					
					StringBuilder message = new StringBuilder();
					
					log.error("Started the process to send Details Process status email");

					try{
						int prfReviewDocsCount = pRFDocumentService.getPrfReviewDocsCount();
						
						if(prfReviewDocsCount == 0){
							message.append("No PRFs awaiting Central Office Review");
						}else{
							message.append("There are ").append(prfReviewDocsCount).append(" PRF(s) awaiting Central Office Review");
							
						}
						
						//String[] recipientEmailAddressesTo = null;
						
						
						//String[] recipientEmailAddressesCC = null;
						
						
						
						//if (recipientEmailAddressesTo != null && recipientEmailAddressesTo.length > 0) {
						//	sendEmail.sendSSLMessage(
						//			recipientEmailAddressesTo,
						//			"PRFs queued for review", message.toString() ,
						//			schedulerMetaData.get("NOTIFICATIONS_FROM"), recipientEmailAddressesCC,
						sendEmail.processEmail(null, "PRFs queued for review", message.toString(), "PRF_REVIEW", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
						
					}catch(Exception e){
						log.error("Failed to get the PRF review records count");
						log.error(e.getMessage());
					}
					
				}
			}
		}
		
	}
	
	
	
}
