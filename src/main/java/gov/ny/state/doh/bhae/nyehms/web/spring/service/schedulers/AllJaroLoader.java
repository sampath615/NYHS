package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.ApplicationContextProvider;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.SchedulerNotificationVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Quartz scheduler class to load ALLJARO data to JAROT
 *  
 * @author ska03
 *
 */
public class AllJaroLoader{
	
	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(AllJaroLoader.class);

	
	@Autowired ManualQuartzJobService manualQuartzJobService;
	@Autowired private QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	
	/**
	 *  This method is load ALLJARO data to JAROT.
	 *  
	 *  ALLJARO: reference data for Quality stage match (Active Document person attribute and Person doc association data)
	 *  
	 *  By default this job will start at 5:15PM MON-FRI
	 *  
	 *  At the End of transaction will send detailed notifications to the configured users.
	 *  
	 */
	public void loadAllJaroData(String initiator, String RUN_SECOND_JOB_FOR_DEAD){
		
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
				
				//schedulerStatusObject : is used to track the complete job status (SUCCESS/FAILURE)
				List<JSONObject> schedulerStatusObject = new ArrayList<JSONObject>();
				
				//This metadata is loaded from LAB_SCHEDULER_PARAMETERS(Metadata table). Most of the Scheduler input parameters will be inputed from Metadata table
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				
				/*
				 * Below two checks are for extra cautious (Checking the Last ran date and Server Ip address check) 
				 * 
				 * Daily Schedulers will be run only one time. if we want to load it again we need to change the respective date(LAST_ALL_JARO_SCHEDULER_RUN_DATE) to previous date.
				 * 
				 */
				boolean isSchedulerRanToday = manualQuartzJobService.isSchedulerRanToday("LAST_ALL_JARO_SCHEDULER_RUN_DATE", schedulerStatusObject, "ALLJARO", schedulerMetaData);
				
				String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
				
				log.error("ALLJARO  isSchedulerRanToday {}",isSchedulerRanToday);
				
				//Checking Server IP address, scheduler calling from and is Scheduler ran today.
				if(!isSchedulerRanToday && "Y".equals(schedulerMetaData.get(CommonConstants.LOAD_ALLJARO_SCHEDULER))  && expectedServerIpAddress.equals(runningServerIpAddress)){

					
					//Updating the ALLJARO Scheduler Run date
					manualQuartzJobService.updateSchedulerRanDate("LAST_ALL_JARO_SCHEDULER_RUN_DATE", schedulerStatusObject, "ALLJARO", schedulerMetaData, "ALLJARO_DEPENDENT_SCHEDULER");
					
					/*
					 * Storing the Status object in Application context.
					 * 
					 * This status will be in high level, which will be sending it to the few users.
					 */
					
					ApplicationContextProvider appContext = new ApplicationContextProvider();
					
					SchedulerNotificationVO schedulerNotificationVO = appContext.getApplicationContext().getBean("notificationObject", SchedulerNotificationVO.class);
					
					List<String> schedulerNotificationObj =  schedulerNotificationVO.getSchedulerStatusObj();
					
		
					
					StringBuilder statusMailSubject = new StringBuilder("ALLJARO ");
					
					
					statusMailSubject.append(EnvironmentProperties.envProperties.getProperty("environmentName"));
					
					statusMailSubject.append(" BY ");
					
					if(CommonUtil.isNullOrEmpty(initiator)){
						statusMailSubject.append("QUARTZ");
					}else{
						statusMailSubject.append("MANUAL <").append(initiator).append(">");
								
					}
					
					statusMailSubject.append(" DB ");
					
					statusMailSubject.append(schedulerMetaData.get("DATABASE_NAME"));
					
					
					String completedMessage = "Successfully created ALLJARO file with Live people only";
					String actionMessage = "Create ALLJARO file with Live people only";
					
					
					/*
					 * WILL_QS_REFERENCE_FILE_INCLUDE_DEAD_PEOPLE : whether to include dead people data in ALLJARO file
					 * 
					 */
					
					String _includeDeadPeople =  schedulerMetaData.get("WILL_QS_REFERENCE_FILE_INCLUDE_DEAD_PEOPLE");
					
					boolean appendToExistingFile = false;
					
					String _alljaroContents = "ALLJARO_LIVE_SQL";
					
					/*
					 * 
					 * If we are running second job, then the Quality stage match against live and Dead people.
					 */
					if("Y".equals(RUN_SECOND_JOB_FOR_DEAD)){
						completedMessage = "Successfully appended Dead people to ALLJARO file";
						actionMessage = "Append Dead people to ALLJARO file";
						_alljaroContents = "ALLJARO_DEATH_SQL";
						appendToExistingFile = true;
						
						schedulerNotificationObj.add("");
						schedulerNotificationObj.add("");
						
						schedulerNotificationObj.add("Second Job Started");
						
						schedulerNotificationObj.add("");
						schedulerNotificationObj.add("");
					}else{
						
						
						schedulerNotificationVO.resetSchedulerStatusObj();
						
						
						if("Y".equals(schedulerMetaData.get("NEED_TO_RUN_SECOND_JOB"))){
							manualQuartzJobService.updateSchedulerMetaData("LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER_FOR_BOTH_JOBS", "Y", null);
						}
						
						
						if("Y".equals(_includeDeadPeople)){
							completedMessage = "Successfully created ALLJARO file with Live and Dead people";
							actionMessage = "Create ALLJARO file with Live and Dead people";
							_alljaroContents = "ALLJARO_LIVE_AND_DEATH_SQL";
						}
					}

					//Sending notification mail that the AllJaro Process started
					manualQuartzJobService.sendNotificationEmail(schedulerMetaData, "START "+statusMailSubject.toString(), "Successfully called the process for "+actionMessage, CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
					
					//Loading the ALLJARO data to JAROT.
//					String _livePeoplePersonData =  schedulerMetaData.get("IS_STD_SOURCE_FOR_LIVE_PEOPLE");
					String transactionStatus = manualQuartzJobService.loadDataToReferenceFile(
							schedulerStatusObject,
							schedulerMetaData,
							_alljaroContents,
							"ALLJARO_FILE_PATH",
							"Loading ALLJARO",
							"ALLJARO_RECORD_LENGTH",
							"AJJARO_REFERENCE_ID_POSITION",
							"ALLJARO_DEPENDENT_SCHEDULER",
							appendToExistingFile
					);
					
					
					
					if("Y".equals(schedulerMetaData.get("REQUIRED_TO_SEND_DETAILED_NOTIFICATION"))){
						log.error("Started the process to send Details Process status email");
						//Sending the notification email 
						manualQuartzJobService.sendSchedulerDetailedProcessStatus(schedulerStatusObject, "END "+statusMailSubject.toString(), schedulerMetaData);
					}else{
						log.error("Skipping the process to send Detailed Process status email");
					}
					
					
					if(CommonConstants.TRANSACTION_STATUS_SUCCESS.equals(transactionStatus)){
						
						
						schedulerNotificationObj.add(completedMessage);
						
						//If the the Second Job completes(Appending dead people data to Alljaro file), Need to load the STD documents for the Quality stage match 
						
						if("Y".equals(RUN_SECOND_JOB_FOR_DEAD)){
							
							JobDataMap jobDataMap = new JobDataMap();
							
							jobDataMap.put("RUN_SECOND_JOB_FOR_DEAD", "Y");
							
							jobDataMap.put("initiator", initiator);
							
							//Triggering the Pre match Quality stage scheduler to process the STD documents against Live and Dead people
							String status = manualQuartzJobService.triggerSchedulerJob(null, "runRawRecordsCleanerJob", CommonConstants.TRIGGER_SCHEDULER, jobDataMap);
							
							if(CommonConstants.TRANSACTION_STATUS_FAILURE.equals(status)){
								
								/*
								 * If the the Quality stage job failed, then loading the First job data
								 */
								manualQuartzJobService.sendNotificationEmail(schedulerMetaData,
										"Pre match process for Dead people",
										"Failed Pre match process for Dead people, Loading the First Job Results.",
										CommonConstants.RESPONSE_CONTENT_TYPE_PLAIN);
								
								
								//Loading the First Quality stage match data
								quartzSchedulerTransactionHelper.loadQualityStageMatchResultsJob(initiator, new ArrayList<JSONObject>(), schedulerMetaData,  CommonConstants.FIRST_JOB, true, schedulerNotificationObj, true);
							
							}
						}
						
					}else{
						

						schedulerNotificationObj.add("Failed to write the  "+actionMessage);
						
						if("Y".equals(RUN_SECOND_JOB_FOR_DEAD)){
							
							//If the Alljaro Job Fails (Appending dead people data)
							
							schedulerNotificationObj.add("Processing the First Job Results  ");
							
							//Loading the First job match results onlyu
							quartzSchedulerTransactionHelper.loadQualityStageMatchResultsJob(initiator, new ArrayList<JSONObject>(), schedulerMetaData,  CommonConstants.FIRST_JOB, true, schedulerNotificationObj, true);
						}else{
							
							//If the first Job Fails creating ALLJARO file
							schedulerNotificationObj.add("Quality stage Job Failed");

							manualQuartzJobService.sendNotificationEmailToUsers(schedulerMetaData, schedulerNotificationObj);

						}
						
						
					}
					
				}
			}
		}
		
	}
	
	
	
}
