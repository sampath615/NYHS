package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.SchedulerNotificationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

/**
 * Module to perform POST-MATCH processing if a one-job scenario or initiate
 * the second job if a two-job scenario.
 * @author ska03
 */
@RequiredArgsConstructor
@Component
public class QualityStageLoader{

    private final AppProperties appProperties;
	private static final Logger log = LoggerFactory.getLogger(QualityStageLoader.class);

	private final SendEmail sendEmail;
	private final QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	private final ManualQuartzJobService manualQuartzJobService;
	private final SchedulerNotificationVO schedulerNotificationVO;

	/**
	 * Drives the POST-MATCH document processing steps (one job scenario) or
	 * triggers the second job (two job scenario), leaving the POST-MATCH
	 * processing to {@link QualityStageMatchResultsLoaderForBothJobs}
	 * <ol>
	 * <li>Performs a set of checks to insure that this step can proceed.</li>
	 * <li>Determines if this is a one job scenario or a two job scenario and
	 * acts accordingly.
	 * <li>Sends out an email listing the results of the POST-MATCH processing.</li>
	 * </ol>
	 * @param initiator If called from the Manual Controller web page, this
	 * will contain the HCS ID of the individual that initiated the process;
	 * otherwise it will be null, indicating that the process was initiated
	 * by the Spring-configured Quartz Scheduler bean.
	 */
	public void loadRunQualityStageData(String initiator){

		synchronized(this){
			String runningServerIpAddress = null;
			try {
				runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
				log.error(runningServerIpAddress);
			} catch (UnknownHostException e) {
				log.error("Unable to get the Server Ip Address");
			}
			//To avoid local environment scheduler calls, for local environment
			//this property will be set it to "N"			
			if(appProperties.loadSchedulers()){
				//schedulerStatusObject : is used to track the complete job status (SUCCESS/FAILURE)				
				List<JSONObject> schedulerStatusObject = new ArrayList<>();
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table).
				Map<String, String> schedulerMetaData =
						manualQuartzJobService.getSchedulerMetaData();
				/*The following two checks are for the extra cautious: check
				 * the Last ran date and Server Ip address.
				 */
				boolean schedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						"LAST_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_RUN_DATE",
						schedulerStatusObject, "Quality Stage Match Results",
						schedulerMetaData);
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				log.error("schedulerRanToday  {}",schedulerRanToday);
				log.error("LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER  {}",schedulerMetaData.get(
						CommonConstants.LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER));
				log.error("expectedServerIpAddress  {}",expectedServerIpAddress);
				log.error("runningServerIpAddress  {}",runningServerIpAddress);
				
				//Checking Server IP address, scheduler calling from and is Scheduler ran today.
				if(!schedulerRanToday && "Y".equals(schedulerMetaData.get(
						CommonConstants.LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER))
						&& expectedServerIpAddress.equals(runningServerIpAddress)){
					//Updating the scheduler run date in LAB_SCHEDULER_PARAMETERS table
					manualQuartzJobService.updateSchedulerRanDate(
							"LAST_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_RUN_DATE",
							schedulerStatusObject, "Quality Stage Match results",
							schedulerMetaData, "QUALITY_STAGE_MATCH_RESULTS_DEPENDENT_SCHEDULER");

					List<String> schedulerNotificationObj =  schedulerNotificationVO.getSchedulerStatusObj();
//					if("Y".equals(NEED_TO_RUN_SECOND_JOB)){ // Two jobs
//						manualQuartzJobService.updateSchedulerMetaData(
//								"LAST_ALL_JARO_SCHEDULER_RUN_DATE", "", null);
//						String status = manualQuartzJobService.triggerSchedulerJob(
//								null, "runAllJaroLoaderJob", 
//								CommonConstants.TRIGGER_SCHEDULER,
//
//							manualQuartzJobService.sendNotificationEmail(schedulerMetaData,
//									"ALLJARO Process for Dead people",
//									"Failed while executing ALLJARO job for Dead people, Loading the First Job Results.",
//					if(loadFirstJobResults){ // Only one job
						//This method will execute when we want to process only one job.  
						quartzSchedulerTransactionHelper.loadQualityStageMatchResultsJob(
								initiator, schedulerStatusObject, schedulerMetaData,
								CommonConstants.FIRST_JOB, true, schedulerNotificationObj, true);
						//Making sure disabling the QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS,
						//if by mistake if the value is "Y" it will again load the same results again.
						manualQuartzJobService.updateSchedulerMetaData(
								"LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS",
								"N", null);
//					}
				}
			}
		}
	}

}
