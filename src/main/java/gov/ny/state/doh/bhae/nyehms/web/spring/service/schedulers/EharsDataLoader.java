package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to load ALLJARO data to JAROT
 *  
 * @author ska03
 *
 */
@Component
public class EharsDataLoader{

	@Autowired
	private AppProperties appProperties;
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(EharsDataLoader.class);
	
	@Autowired EharsDataLoaderService eharsDataLoaderService;
	
	@Autowired ManualQuartzJobService manualQuartzJobService;
	
	@Autowired SendEmail sendEmail;

	@Autowired
    MailProperties mailProperties;
	
	public void loadEharsDataInToNyehmsTables(String initiator){
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized(this){
			
			log.error("Checking before properties   {}",appProperties.loadSchedulers());
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if(appProperties.loadSchedulers()){
				
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				

				String qualityStageRunDateParameterName = "LAST_EHARS_DATA_LOAD_JOB_DATE";

				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						qualityStageRunDateParameterName, null, null, schedulerMetaData);
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				// Checking Server IP address, scheduler calling from and is
				// Scheduler ran today.
				log.error("Scheduler ran today {}", isSchedulerRanToday);
				if (!isSchedulerRanToday
						&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_EHARS_DATA))
						&& expectedServerIpAddress.equals(runningServerIpAddress)) {
				
						List<String> notificationObj = new ArrayList<>(); 
						
						manualQuartzJobService.updateSchedulerRanDate(qualityStageRunDateParameterName,
								null, "EHARS data loading", schedulerMetaData,
								null);
				
						boolean isErroredOut = eharsDataLoaderService.loadEharsDataInToNyehmsTables(notificationObj);
						
						String subject = "Successfully loaded the EHARS data into NYEHMS tables";
						
						if(isErroredOut) {
							subject = "Failed to load EHARS data into NYEHMS tables";
						}


					String[] recipientEmailAddressesTo = mailProperties.eharsLoaderTo().split(",");
						
		
						StringBuilder mailContent = new StringBuilder("");

						mailContent.append("<table border='1' width='500px;' >");

						for (String status : notificationObj) {

							mailContent.append("<tr><td>");
							mailContent.append(status);
							mailContent.append("</td></tr>");

						}

						mailContent.append("</table><br/><br/><br/>");
						
						try {
//							sendEmail.sendSSLMessage(
//									recipientEmailAddressesTo,
//									EnvironmentProperties.envProperties.getProperty("environmentName")+": "+subject, mailContent.toString() ,
//									EnvironmentProperties.envProperties.getProperty("helpLineFrom"), null,
//									CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
							
							sendEmail.processEmail(
									null,
									appProperties.environmentName()+": "+subject, mailContent.toString(), 
									"EHARS_NOTIFICATION",
									CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
							
						}catch(MessagingException e){
							log.error("EHARS data loader notification email failed");
							log.error(e.getMessage());
							log.error(mailContent.toString());
						}
				}
				
				
				
			}
		}
		
	}
	
	
	
}
