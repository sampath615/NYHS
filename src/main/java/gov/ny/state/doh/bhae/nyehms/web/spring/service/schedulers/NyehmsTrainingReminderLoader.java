package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import weblogic.jws.Context;

/**
 * Quartz scheduler class to send NYEHMS login reminder email
 *  
 * @author ska03
 *
 */
public class NyehmsTrainingReminderLoader{
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(NyehmsTrainingReminderLoader.class);

	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	
    @Context
    private ServletContext context;
	
	@Autowired
	SendEmail sendEmail;

	public void sendNyehmsTrainingReminderEmail(){
		
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
				if("Y".equals(schedulerMetaData.get(CommonConstants.LOAD_NYEHMS_TRAINING_REMINDER_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){
					
					StringBuilder message = new StringBuilder();
					
					log.error("Started the process to send NYEHMS Training reminder email");

					try{
						List<Map<String, String>> nyehmsUsersList = commonService.nyehmsTrainingUsersExpireList();
						
						System.out.println("nyehmsUserList size  "+nyehmsUsersList.size());
						if(nyehmsUsersList == null || nyehmsUsersList.size() == 0){
							log.error("No Users available to send training reminder email");
						}else{
							
							for (Map<String, String> userDetails : nyehmsUsersList) {
								try {
									   
									message = new StringBuilder();
									
									
									message.append(userDetails.get("FIRST_NAME"));
									
									if(Integer.parseInt(userDetails.get("DAYSTORENEW")) < 0){
										continue;
									}else {
										message.append(", <br/><br/>Your Security and Confidentiality  training date is expiring in ");
										message.append(userDetails.get("DAYSTORENEW"));
										message.append(" days. Please schedule the training.<br/><br/>");
										
										message.append("<span style='font-weight:bold'>If you do not do the training in before end of the day ");
										message.append(userDetails.get("WEEKNAME")+", ");
										message.append(userDetails.get("MONTH")+" ");
										message.append(userDetails.get("DAY")+" ");
										message.append(userDetails.get("YEAR"));
										message.append(", your account will be deactivated.</span><br/><br/>");
									}
									
									
									
						
									
									message.append("If you have any questions about your access to NYEHMS portal, please contact us in one of the following ways:<br/><br/>");
									
									message.append("<ul>");
	
										message.append("<li>Email BHAE.Training@health.ny.gov</li>");
										
									
									message.append("</ul>");
//									sendEmail.sendemailAttachments(userDetails.get("EMAIL_ADDR").split(","), "We are going to deactivate you account, "+userDetails.get("FIRST_NAME"), message.toString(), EnvironmentProperties.envProperties.getProperty("helpLineFrom"), EnvironmentProperties.envProperties.getProperty("helpLineFrom").split(","), CommonConstants.RESPONSE_CONTENT_TYPE_HTML, 
//											messageBodyPart2);
									sendEmail.sendSSLMessage(userDetails.get("EMAIL_ADDR").split(","), "Schedule NYEHMS Confidential training date, "+userDetails.get("FIRST_NAME"), message.toString(),  
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
