package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to send NYEHMS login reminder email
 *  
 * @author ska03
 *
 */
@Component
public class NyehmsLoginReminderLoader{
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(NyehmsLoginReminderLoader.class);

	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	
	
	@Autowired
	SendEmail sendEmail;

	public void sendNyehmsLoginReminderEmail(){
		
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
				if("Y".equals(schedulerMetaData.get(CommonConstants.LOAD_NYEHMS_LOGIN_REMINDER_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){
					
					StringBuilder message = new StringBuilder();
					
					log.error("Started the process to send NYEHMS login reminder email");

					try{
						List<Map<String, String>> nyehmsUsersList = commonService.nyehmsInactiveUserDetailsList();
						
						System.out.println("nyehmsUserList size  "+nyehmsUsersList.size());
						if(nyehmsUsersList == null || nyehmsUsersList.size() == 0){
							log.error("No Users available to send reminder email");
						}else{
							
							for (Map<String, String> userDetails : nyehmsUsersList) {
								try {
									   
									message = new StringBuilder();
									
									
									message.append(userDetails.get("FIRST_NAME"));
									message.append(", <br/><br/>To keep NYEHMS secure, we will be deactivating users who have not logged in to the system within the past 90 days. It has been ");
									message.append(userDetails.get("DAYSSINCELOGGEDIN"));
									message.append(" days since you last logged in.<br/><br/>");
									
									message.append("<span style='font-weight:bold'>If you do not log in before end of the day ");
									message.append(userDetails.get("WEEKNAME")+", ");
									message.append(userDetails.get("MONTH")+" ");
									message.append(userDetails.get("DAY"));
									message.append(", your account will be deactivated.</span><br/><br/>");
									
									message.append("If you have any questions about your access to NYEHMS portal, please contact us in one of the following ways:<br/><br/>");
									
									message.append("<ul>");
	
										message.append("<li>Email nyehms@health.ny.gov</li>");
										message.append("<li>Call (518) 474-4284</li>");
										
									
									message.append("</ul>");
//									sendEmail.sendemailAttachments(userDetails.get("EMAIL_ADDR").split(","), "We are going to deactivate you account, "+userDetails.get("FIRST_NAME"), message.toString(), EnvironmentProperties.envProperties.getProperty("helpLineFrom"), EnvironmentProperties.envProperties.getProperty("helpLineFrom").split(","), CommonConstants.RESPONSE_CONTENT_TYPE_HTML, 
//											messageBodyPart2);
									sendEmail.sendSSLMessage(userDetails.get("EMAIL_ADDR").split(","), "We are going to deactivate your account, "+userDetails.get("FIRST_NAME"), message.toString(),  
											EnvironmentProperties.envProperties.getProperty("helpLineFrom"),  EnvironmentProperties.envProperties.getProperty("helpLineFrom").split(","), null, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
								}catch(Exception e) {
									log.error("Failed to send email reminder email to: {}",userDetails.get("USERNAME"));
									log.error(e.getMessage());
						            sendEmail.sendDefaultEMail(message.toString());
								}
							}
						}
					}catch(Exception e){
						log.error("Failed to send email reminders to users");
						log.error(e.getMessage());
			            sendEmail.sendDefaultEMail("Failed to send email reminders to users");

						
						
					}
					
				}
			}
		}
		
	}
	
	
	
}
