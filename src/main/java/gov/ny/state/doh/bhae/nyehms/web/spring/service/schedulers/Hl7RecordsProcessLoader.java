package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.CommonSchedulerHelper;

/**
 * Quartz scheduler class to run hl7 clean hit records
 *  
 * @author rzp05
 *
 ***/
@RequiredArgsConstructor
@Component
public class Hl7RecordsProcessLoader {

	private final ManualQuartzJobService manualQuartzJobService;
	
	private final RawRecordsCleaner rawRecordsCleaner;
	
	private final CommonSchedulerHelper commonSchedulerHelper;

    private final AppProperties appProperties;
	
	private static final Logger log = LoggerFactory.getLogger(Hl7RecordsProcessLoader.class);
	
	public void processHl7Records(String initiator) {
		
		// checking the server
				String runningServerIpAddress = null;
				try {
					runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
					log.error("Server Ip Address - {}",runningServerIpAddress);
				} catch (UnknownHostException e) {
					log.error("Unable to get the Server Ip Address");
				}
		
		synchronized (this) {
			
			log.error("Checking before properties : {}",appProperties.loadSchedulers());
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if(!appProperties.loadSchedulers()){
				log.error("You are running locally with a loadSchedulers property of N");
			}else{
				

				// schedulerStatusObject : is used to track the complete job
				// status (SUCCESS/FAILURE)
				List<JSONObject> schedulerStatusObject = new ArrayList<>();
				
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table). Most of the Scheduler input parameters will be inputed from Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				String hl7QualityStageRunDateParameterName = "LAST_HL7_QUALITY_STAGE_SCHEDULER_RUN_DATE";
				
				boolean isHl7SchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(hl7QualityStageRunDateParameterName, schedulerStatusObject, "HL7 Quality Stage", schedulerMetaData);
				
				//Checking Server IP address, scheduler calling from 
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				log.error("Scheduler ran today : {}", isHl7SchedulerRanToday);
				
				if(!isHl7SchedulerRanToday && "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_HL7_QUALITY_STAGE_SCHEDULER)) 
																		&& expectedServerIpAddress.equals(runningServerIpAddress)) {
					
					// Updating the Scheduler Ran data in metadata table
					manualQuartzJobService.updateSchedulerRanDate(hl7QualityStageRunDateParameterName, schedulerStatusObject, "HL7 Quality Stage", schedulerMetaData, null);
					
					try {
						String statusMailSubject = commonSchedulerHelper.getStatusMailSubject(initiator, "PRE-MATCH HL7 JOB",
								schedulerMetaData.get("DATABASE_NAME"));
						
						boolean sendNotificationRequired = "Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DETAILED_NOTIFICATION"));
						
						if (sendNotificationRequired) {
							
							manualQuartzJobService.sendNotificationEmail(schedulerMetaData,"START " + statusMailSubject,
									"Successfully started HL7 Job pre match process", CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
						}
							
				
						rawRecordsCleaner.cleanHITRecords("HL7", schedulerMetaData, schedulerStatusObject, initiator);
						
						
						
						if (sendNotificationRequired) {
							log.error("Sending the status email");
							// Sending the notification email
							manualQuartzJobService.sendSchedulerDetailedProcessStatus(schedulerStatusObject, "END " + statusMailSubject, schedulerMetaData);
						} else {
							log.error("Not sending the status email.");
						}
						
					} catch (Exception e) {
						log.error("unable to process hl7_records " + e.getMessage());
					}
				}
				
			}
			
			
			
		}
		
		
	}

	
}
