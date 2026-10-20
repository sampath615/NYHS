package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AIRSDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.StatusReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.reports.DocumentSummaryReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler class to load ALLJARO data to JAROT
 *  
 * @author ska03
 *
 */
@Component
public class WeeklyDocumentStatusLoader{

	@Autowired
	private AppProperties appProperties;
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(WeeklyDocumentStatusLoader.class);
	
	@Autowired ManualQuartzJobService manualQuartzJobService;
	
	@Autowired AIRSDocumentService aIRSDocumentService;
	
	@Autowired SendEmail sendEmail;
	
	@Autowired StatusReportsService statusReportsService;
	
	@Autowired DocumentSummaryReportsService documentSummaryReportsService;
	
	public void loadWeeklyStatus(String initiator){
		
		String runningServerIpAddress = null;
		try {
			runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
			log.error(runningServerIpAddress);
		} catch (UnknownHostException e) {
			log.error("Unable to get the Server Ip Address");
		}
		
		synchronized(this){
			
			log.error("Checking before properties   {}",appProperties.loadSchedulers());
			
			Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
			
			String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
			// Checking Server IP address, scheduler calling from and is
			
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"
			if(appProperties.loadSchedulers() && expectedServerIpAddress.equals(runningServerIpAddress)){
				
				
				

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
