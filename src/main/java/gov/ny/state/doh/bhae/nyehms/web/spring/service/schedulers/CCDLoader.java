package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HITCommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.CommonSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

@Component
public class CCDLoader {

	private static Logger log = LoggerFactory.getLogger(CCDLoader.class);

	
	@Autowired
	private HITCommonDocumentService hitCommonDocumentService;
	
	@Autowired
	private ManualQuartzJobService manualQuartzJobService;

	@Autowired
	private CommonSchedulerHelper commonSchedulerHelper;


	/**
	 * @author rzp05
	 * calling extractNewPayloadsFromUphnToRaw-
	 * Loading ccd records with the help of channel sftp from H: ccd data directory 
	 * into raw_ccd_xx Tables 
	 * 
	 * calling extractHitErrorFromUphnToRaw-
	 * Loads CCDs from UPHN_TRANX_IN.PAYLOADBINARYCONTENT to RAW_CCD_DOC and 9 other
	 * RAW_CCD_XXX tables.
	 * 
	 *  Unlike RecordsCleaner, this does not initiate the
	 * validation of records and loading of STD_CCD_DOC
	 * 
	 * @param initiator
	 *            A string indicating either the user who initiated the scheduler
	 *            manually, or QUARTZ if done by the scheduler.
	 */
	
	protected void loadCCDIntoRAWTables(String initiator) {
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized (this) { // if this is present it prevents multiple simultaneous instances
			// We like to be able to run multiple simultaneous instances
			
			log.error("Checking before properties : {}",EnvironmentProperties.envProperties.getProperty("loadSchedulers"));
			
			// To avoid local environment scheduler calls, for local environment
			// this property will be set it to "N"
			// schedulerStatusObject : is used to track the complete job status
			// (SUCCESS/FAILURE)
			if("N".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers"))){
				log.error("You are running locally with a loadSchedulers property of N");
			}else{
				
				List<JSONObject> schedulerStatusObject = new ArrayList<>();
				
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				
				if (expectedServerIpAddress.equals(runningServerIpAddress)) {
					
				
					try {
							log.error("********************** {} is in CCDLoader.loadCCDfromUPHNtoRaw", initiator);
							
							String statusMailSubject = commonSchedulerHelper.getStatusMailSubject(initiator, "CCDLoader",
									schedulerMetaData.get("DATABASE_NAME"));
							
							boolean sendNotificationRequired = "Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DETAILED_NOTIFICATION"));
							
							// Email at the beginning
							if(sendNotificationRequired) {
								manualQuartzJobService.sendNotificationEmail(schedulerMetaData, "START " + statusMailSubject,
										"Successfully called the CCDLoader scheduler process",
										CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
							}
							log.error("CCD LOADER   {}", Runtime.getRuntime().totalMemory());
							log.error("CCD LOADER   {}", Runtime.getRuntime().freeMemory());
							if("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_CCD_RECORDS_MANUALLY"))) {
								hitCommonDocumentService.extractNewPayloadsFromUphnToRaw(schedulerStatusObject, "CCD",
										schedulerMetaData, initiator);
							}
							if("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_CCD_REPRISE_RECORDS"))) {
								hitCommonDocumentService.extractHitErrorFromUphnToRaw(schedulerStatusObject,"CCD", schedulerMetaData,
										initiator);
							}
							
							// Email at the end
							if (sendNotificationRequired) {
								log.error("Sending the status email");
								// Sending the notification email
								manualQuartzJobService.sendSchedulerDetailedProcessStatus(schedulerStatusObject,
										"END " + statusMailSubject, schedulerMetaData);
							} else {
								log.error("Not sending the status email.");
							}
						}catch(Exception ex) {
							log.error("unable to process ccd records " + ex.getMessage());
						}

				}
			}
			
		}
	} // synchronized (this)

}
