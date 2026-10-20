package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.StatusReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Quartz scheduler to load monthly lab report
 *  
 * @author ska03
 *
 */
@Component
public class MonthlyDocumentStatusLoader{

	@Autowired
	private AppProperties appProperties;
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(MonthlyDocumentStatusLoader.class);
	
	@Autowired ManualQuartzJobService manualQuartzJobService;
	@Autowired SendEmail sendEmail;
	
	@Autowired StatusReportsService statusReportsService;
	
	public void loadMonthlyStatus(String initiator){
		
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
				
				
				

				String qualityStageRunDateParameterName = "LAST_MONTHLY_STATUS_LOAD_JOB_DATE";

				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						qualityStageRunDateParameterName, null, null, schedulerMetaData);
				
				// Scheduler ran today.
				log.error("Scheduler ran today {}", isSchedulerRanToday);
				
				manualQuartzJobService.updateSchedulerRanDate(qualityStageRunDateParameterName,
						null, "Monthly status email", schedulerMetaData,
						null);
				
				try {
					if (!isSchedulerRanToday
							&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_MONTHLY_LAB_REPORT_JOB))
							) {
							
						statusReportsService.generateMontlyLabReportCount(null);
							
					}
				}catch(Exception e) {
					log.error("Failed to run monthly lap report count" +e.getMessage());
				}
				
				
				try {
					if (!isSchedulerRanToday
							&& "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_MONTHLY_LAB_REPORT_BY_REGION_JOB))
							) {
							
						statusReportsService.generateMontlyLabReportCount("byRegion");
							
					}
				}catch(Exception e) {
					log.error("Failed to run monthly lap report count" +e.getMessage());
				}
				
				
				
			}
		}
		
	}
	
	
	
}
