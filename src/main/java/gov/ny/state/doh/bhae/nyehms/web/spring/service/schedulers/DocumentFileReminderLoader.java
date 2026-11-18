package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to send Document reminder email 
 *  
 * @author ska03
 *
 */
@RequiredArgsConstructor
@Component
public class DocumentFileReminderLoader{

	private final AppProperties appProperties;
	
	private static final Logger log = LoggerFactory.getLogger(DocumentFileReminderLoader.class);
	
	private final CommonService commonService;
	
	private final ManualQuartzJobService manualQuartzJobService;
	
	private final SendEmail sendEmail;

	public void sendDocumentFileReminderEmail(){
		
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
											
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table). Most of the Scheduler input parameters will be inputed from Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				//Checking Server IP address, scheduler calling from 
				if("Y".equals(schedulerMetaData.get(CommonConstants.LOAD_DOCUMENT_REMINDER_STATUS_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){
					
					StringBuilder message = new StringBuilder();
					
					log.error("Started the process to send Document file reminder email");

					try{
						List<String> documentList = commonService.getDocumentListForReminder();
						
						if(documentList == null || documentList.size() == 0){
							log.error("No document files to send reminder email");
						}else{
							
							message.append("This email is a reminder to request below files in "+schedulerMetaData.get("FILE_NOTIFICATION_DAYS")+" days.<br/><br/>");
							
							message.append("<ul>");

							for (String documentType : documentList) {
								message.append("<li>");
								message.append(documentType);
								message.append("</li>");

							}

							message.append("</ul>");
							
							
							
							
							sendEmail.processEmail(null, "Reminder to request data files", message.toString(), "FILE_REMINDER", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
						}
						
						
						
					}catch(Exception e){
						log.error("Failed to send Document file reminder email");
						log.error(e.getMessage());
			            sendEmail.sendDefaultEMail("Failed, while sending Document file reminder email");

						
						
					}
					
				}
			}
		}
		
	}
	
	
	
}
