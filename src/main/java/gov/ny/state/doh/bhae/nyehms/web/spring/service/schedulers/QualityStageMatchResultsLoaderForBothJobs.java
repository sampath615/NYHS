package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.ApplicationContextProvider;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.SchedulerNotificationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Module to perform all POST-MATCH processing
 * @author ska03
 */
public class QualityStageMatchResultsLoaderForBothJobs{
	
	private static Logger log = LoggerFactory.getLogger(QualityStageMatchResultsLoaderForBothJobs.class);
	@Autowired SendEmail sendEmail;
	@Autowired ManualQuartzJobService manualQuartzJobService;
	@Autowired QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	
	/**
	 * Drives the POST-MATCH document processing steps in a two-job scenario:
	 * <ol>
	 * <li>Performs a set of checks to insure that this step can proceed.</li>
	 * <li>Retrieves status files from Jarot</li>
	 * <li>Retrieves results of the patient match and processes them</li>
	 * <li>Runs another "match" process to standardize provider and facility data
	 * <li>Sends out an email listing the results of the POST-MATCH processing.</li>
	 * </ol>
	 * @param initiator If called from the Manual Controller web page, this
	 * will contain the HCS ID of the individual that initiated the process;
	 * otherwise it will be null, indicating that the process was initiated
	 * by the Spring-configured Quartz Scheduler bean.
	 */
	public void loadBothQualityStageMatchResultsData(String initiator){
		
		synchronized(this){
			String runningServerIpAddress = null;
			try {
				runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
				log.error(runningServerIpAddress);
			} catch (UnknownHostException e) {
				log.error("Unable to get the Server Ip Address");
			}
			//To avoid local environment scheduler calls, for local environment this property will be set it to "N"			
			if("Y".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulers"))){
				//schedulerStatusObject : is used to track the complete job status (SUCCESS/FAILURE)				
				List<JSONObject> schedulerStatusObject = new ArrayList<>();
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table).
				Map<String, String> schedulerMetaData =
						manualQuartzJobService.getSchedulerMetaData();
				/*The following two checks are for the extra cautious: check
				 * the Last ran date and Server Ip address.
				 */
				boolean schedulerRanToday = manualQuartzJobService.isSchedulerRanToday(
						"LAST_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_RUN_DATE_FOR_BOTH_JOBS",
						schedulerStatusObject, "Quality Stage Match Results",
						schedulerMetaData);
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				//Checking Server IP address, scheduler calling from and is Scheduler ran today.
				if(!schedulerRanToday && "Y".equals(schedulerMetaData.get(
						CommonConstants.LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS))
						&& expectedServerIpAddress.equals(runningServerIpAddress)){
					

					/*
					 * Storing the Status object in Application context.
					 * 
					 * This status will be high level, which will be sending it to the few users.
					 */
					ApplicationContextProvider appContext = new ApplicationContextProvider();
					
					SchedulerNotificationVO schedulerNotificationVO = appContext.getApplicationContext().getBean("notificationObject", SchedulerNotificationVO.class);
			    	
					List<String> schedulerNotificationObj =  schedulerNotificationVO.getSchedulerStatusObj();
					
					
						StringBuilder statusMailSubject = new StringBuilder("POST-MATCH ");
						statusMailSubject.append(EnvironmentProperties.envProperties.getProperty("environmentName"));
						statusMailSubject.append(" BY ");
						if(CommonUtil.isNullOrEmpty(initiator)){
							statusMailSubject.append("QUARTZ");
						}else{
							statusMailSubject.append("MANUAL <").append(initiator).append(">");
						}
						statusMailSubject.append(" DB ");
						statusMailSubject.append(schedulerMetaData.get("DATABASE_NAME"));
						//Sending notification mail that the Quality stage match results Process started
						manualQuartzJobService.sendNotificationEmail(schedulerMetaData,
								"START "+statusMailSubject.toString(),
								"Successfully called the post match process for Live and Death",
								CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
						
						
						manualQuartzJobService.updateSchedulerRanDate(
								"LAST_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_RUN_DATE_FOR_BOTH_JOBS",
								schedulerStatusObject, "Quality Stage",
								schedulerMetaData, null);
						
						String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
						
						
						String NEED_TO_RUN_SECOND_JOB = schedulerMetaData.get("NEED_TO_RUN_SECOND_JOB");
						
						
						/**************************************
						 *                                    *
						 *  RUN THE POST-MATCH PROCESSING     *
						 *                                    * 
						 **************************************/
						
						
					
						boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanYesterday(
								"LAST_QUALITY_STAGE_SCHEDULER_RUN_DATE_"+CommonConstants.FIRST_JOB,
								schedulerStatusObject, "Quality Stage", schedulerMetaData);
						
						List<Map<String, Object>> nonCaseDocumentDetails = new ArrayList<>();
						
						if(isSchedulerRanToday){
							
							if("Y".equals(schedulerMetaData.get("WILL_QS_REFERENCE_FILE_INCLUDE_DEAD_PEOPLE"))){
								schedulerNotificationObj.add("Loading Post match results for Quality stage match against Live and Dead people");
							}else{
								schedulerNotificationObj.add("Loading Post match results for Quality stage match against Live people");
							}
							
							
							
							
							transactionStatus = manualQuartzJobService.loadQualityStageDataJob(schedulerStatusObject, schedulerMetaData, CommonConstants.FIRST_JOB, false, schedulerNotificationObj, true, nonCaseDocumentDetails);
						}
						
						
						isSchedulerRanToday = manualQuartzJobService.isSchedulerRanYesterday(
								"LAST_QUALITY_STAGE_SCHEDULER_RUN_DATE_"+CommonConstants.SECOND_JOB,
								schedulerStatusObject, "Quality Stage", schedulerMetaData);
						
						if("Y".equals(NEED_TO_RUN_SECOND_JOB) && isSchedulerRanToday){
							
							schedulerNotificationObj.add("Loading Post match results for Quality stage match against Live and Dead people");
							
							if(CommonConstants.TRANSACTION_STATUS_FAILURE.equals(transactionStatus)){
							
								manualQuartzJobService.loadQualityStageDataJob(schedulerStatusObject, schedulerMetaData, CommonConstants.SECOND_JOB, true, schedulerNotificationObj, true, nonCaseDocumentDetails);
							}else{
								manualQuartzJobService.loadQualityStageDataJob(schedulerStatusObject, schedulerMetaData, CommonConstants.SECOND_JOB, true, schedulerNotificationObj, false, nonCaseDocumentDetails);
							}

						}
						
						
						if ("Y".equals(schedulerMetaData
								.get("REQUIRED_TO_RUN_PROV_FACILITY_QS_PROCESS"))) {
							log.error("started the process to run the Provider Facility Quality stage process");
							//Step4, step5, Step6
							quartzSchedulerTransactionHelper.mapProviderFacilityQsResults(
									schedulerStatusObject, schedulerMetaData, schedulerNotificationObj);
						} else {
							log.error("Skipping the process to run the Provider Facility Quality stage process");
						}
						
						
						
						if("Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DETAILED_NOTIFICATION"))){
							log.error("Started the process to send Details Process status email");
							/* Send Email */
							manualQuartzJobService.sendSchedulerDetailedProcessStatus( 
									schedulerStatusObject, "END "+statusMailSubject.toString(),
									schedulerMetaData);
						}else{
							log.error("Skipping the process to send Detailed Process status email");
						}
						
					
				}
			}
		}
	}
	

}
