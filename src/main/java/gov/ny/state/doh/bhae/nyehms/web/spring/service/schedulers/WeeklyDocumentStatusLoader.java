package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AIRSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.StatusReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.reports.DocumentSummaryReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to load ALLJARO data to JAROT
 *  
 * @author ska03
 *
 */
@RequiredArgsConstructor
@Component
public class WeeklyDocumentStatusLoader{
	
	/*
	 * Reference to Log
	 */
	private static final Logger log = LoggerFactory.getLogger(WeeklyDocumentStatusLoader.class);
	
	private final ManualQuartzJobService manualQuartzJobService;
	
	private final AIRSDocumentService aIRSDocumentService;
	
	private final SendEmail sendEmail;
	
	private final StatusReportsService statusReportsService;
	
	private final DocumentSummaryReportsService documentSummaryReportsService;
	
	public void loadWeeklyStatus(String initiator){
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized(this){
			
			log.error("Checking before properties   {}",EnvironmentProperties.envProperties.getProperty("loadSchedulers"));
			
			Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
			
			String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
			// Checking Server IP address, scheduler calling from and is
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if("Y".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers")) && expectedServerIpAddress.equals(runningServerIpAddress)){
				
				
				

				String qualityStageRunDateParameterName = "LAST_WEEKLY_STATUS_LOAD_JOB_DATE";

				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						qualityStageRunDateParameterName, null, null, schedulerMetaData);
				
				// Scheduler ran today.
				log.error("Scheduler ran today {}", isSchedulerRanToday);
				
				manualQuartzJobService.updateSchedulerRanDate(qualityStageRunDateParameterName,
						null, "Weekly status email", schedulerMetaData,
						null);
				
				if (!isSchedulerRanToday
						&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_WEEKLY_STATUS_JOB))
						) {
						
						aIRSDocumentService.checkForActiveErrors(false);
						
				}
				
				if (!isSchedulerRanToday) {
					try {
						documentSummaryReportsService.generateWeeklyDocumentStatusSummaryReport();
					}catch(Exception e) {
						log.error("Failed while generating the document status report  {}",e.getMessage());
					}
				}
				
				if (!isSchedulerRanToday
						&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_WEEKLY_LAB_REPORT_JOB))
						) {
						
					statusReportsService.generateLabReportCount(schedulerMetaData.get("LAB_REPORT_COUNT_SQL"));
						
				}
				
				
				
			}
		}
		
	}
	
	
	
}
