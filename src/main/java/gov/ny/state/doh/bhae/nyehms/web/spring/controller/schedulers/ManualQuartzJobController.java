package gov.ny.state.doh.bhae.nyehms.web.spring.controller.schedulers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HL7DocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

/**
 * This controller is used to control the Scheduler jobs and get the statistics
 * of the Scheduler job
 * 
 * @author ska03
 */
@RequiredArgsConstructor
@Controller
public class ManualQuartzJobController {

	private static final Logger log = LoggerFactory.getLogger(ManualQuartzJobController.class);

	private final ManualQuartzJobService manualQuartzJobService;
	private final CommonExceptionHandler commonExceptionHandler;
	
	private final HL7DocumentService hl7DocumentService;

	/**
	 * This method is used to load the Quartz scheduler manual page.
	 * 
	 * Before loading, the page will check the permissions for the logged-in
	 * user. If the user has the appropriate permissions then he/she will be
	 * redirected to jsp/scheduler/manualQuartzSchedulerJob.jsp. If the user
	 * does not have permissions then he/she will be redirected to the Error
	 * page
	 * 
	 * @param request
	 *            the HttpServletRequest object
	 * @param map
	 *            A map that stores transaction information
	 * @return the jsp path of the returned page.
	 */
	@RequestMapping(value = "/manualQuartzJob")
	public String manualQuartzJob(HttpServletRequest request, Map<String, String> map, UserObject userObject) {

		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Check the user's permissions
		manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		
	    if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to manual Quartz scheduler page");
			}

			return "scheduler/manualQuartzSchedulerJob";
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/scanningQuartzJob")
	public String scanningQuartzJob(HttpServletRequest request, Map<String, String> map, UserObject userObject) {

		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Check the user's permissions
		manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		
		
		if (AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName()
				.equals(targetMap.get(AppConstants.NYEHM_TRANSACTION_RESULT.getName()))) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to manual Quartz scheduler page");
			}

			return "scheduler/ScanningQuartzSchedulerJob";
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}
	
	
	

	/**
	 * Takes a request from the scheduler/schedulerMetaData.jsp page to change
	 * one of the parameters in LAB_SCHEDULER_PARAMETERS and makes the change.
	 * 
	 * @param request
	 *            the HttpServletReques object
	 * @param map
	 *            a {@link java.util.Map} containing ?
	 * @param parameterName
	 *            The requested PARAMTER_NAME value of the row to update
	 * @param parameterValue
	 *            The new PARAMTER_VALUE value
	 * @return ?
	 */
	@RequestMapping(value = "/updateMetaDataValue.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateMetaDataValue(HttpServletRequest request, Map<String, Object> map,
			@RequestParam String parameterName, @RequestParam String parameterValue, UserObject userObject) {

		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Check the user's permissions
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		if (statusCode == 0) { // the user has permission
			if (log.isDebugEnabled()) {
				log.debug("updating the Meta data value");
			}
			manualQuartzJobService.updateSchedulerMetaData(parameterName, parameterValue, bindingResult);
		}
		return targetMap;
	}

	@RequestMapping(value = "/updateSystemProperties.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateSystemProperties(HttpServletRequest request, Map<String, Object> map,
			@RequestParam String parameterName, @RequestParam String parameterValue) {

		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		try {
			EnvironmentProperties.envProperties.setProperty(parameterName, parameterValue);
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the Property.", bindingResult);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Failed to update the Property value {} ",parameterName);
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Unable to update the Property value. please contact helpline.", bindingResult);
		}
		return targetMap;
	}

	@RequestMapping(value = "/schedulerMetaData")
	public String schedulerMetaData(HttpServletRequest request, Map<String, Object> map, UserObject userObject) {

		HttpSession session = request.getSession();
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		// Checking the Permissions for the user
		int statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);

		if (log.isDebugEnabled()) {
			log.debug("userObject: {}",userObject);
			log.debug("targetMap: {}",targetMap);
			log.debug("statusCode: {}", statusCode);
		}

		map.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(),
				(String) targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()));
		if (statusCode == 0) {
			// If user having permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to scheduler metadata page");
			}
			Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
			session.setAttribute("schedulerMetaData", schedulerMetaData);
			return "scheduler/schedulerMetaData";
		} else {
			// if user is not having the permissions
			if (log.isDebugEnabled()) {
				log.debug("Redirecting to Error page");
			}
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/getMetaDataValue.json", produces = "application/json")
	public @ResponseBody Map<Object, Object> getMetaDataValue(HttpServletRequest request,
			@RequestParam String parameterName, UserObject userObject) {

		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		Integer statusCode = manualQuartzJobService.getPermissionsAccessForManualQuartzJob(bindingResult, userObject);
		if (statusCode == 0) {
			String metadataValue = manualQuartzJobService.getMetaDataValue(parameterName);
			if (metadataValue != null) {
				targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
						AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
				targetMap.put("parameterValue", metadataValue);
			} else {
				targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
						AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
				targetMap.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "Unable to find the value");
			}
		} else {
			targetMap.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(),
					AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			targetMap.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "You don't have access to view this page.");
		}
		return targetMap;
	}

	/**
	 * 
	 * This method is used to control the Scheduler job like pausing, resuming
	 * and Triggering manually
	 * 
	 * @param request
	 *            Request object
	 * @param jobName
	 *            Scheduler name
	 * @param actionName
	 *            Pause/Resume/Trigger
	 * @return transaction status
	 */
	@RequestMapping(value = "/triggerSchedulerJobManually.json", produces = "application/json")
	public @ResponseBody Map<Object, Object> triggerSchedulerJobManually(HttpServletRequest request,
			@RequestParam String jobName, @RequestParam String actionName, UserObject userObject) {

		log.error("jobName: {}; actionName: {}",jobName,actionName);
		Map<Object, Object> targetMap = new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("initiator", userObject.getUserId());

		if (log.isDebugEnabled()) {
			log.debug("{} is trying to {} manually",userObject.getUserFullName(),actionName);
		}

		manualQuartzJobService.triggerSchedulerJob(bindingResult, jobName, actionName, jobDataMap);

		return targetMap;
	}
	
	/**
	 * 
	 * This method is used to execute HL7 stored procedure
	 * which executes 3 cursors written inside the stored procedure 
	 * to match current process status 5 on the daily basis and to rematch other statuses of hl7 against ccd.
	 * 
	 * Before Executing this method, we need to make sure that under schedular data
	 * 2 parameter names are set to 
	 *  -> PAST_DATE_TO_PICK_HL7_CURSOR  as "60" (this is made to 60 to rematch records of hl7 to ccd by picking duration from the current date)
	 *  which means we are picking records of hl7 whose status is 60 or anything which is set in parameter name "CPS_HL7_CURSOR_TO_MATCH_AGAINST_CCD" 
	 *  by going back 60 days from today.
	 *  
	 *  -> CPS_HL7_CURSOR_TO_MATCH_AGAINST_CCD as "5,60" for regular automation process but it can be change as per the requirement to process other statuses manually.
	 *  Please make sure you revert it back to 5,60 for automated daily job process.
	 *  
	 * 
	 * @return Once the method runs successfully. It will send the notification to the person who has been set to the parameter name "HL7_MATCHED_AGAINST_CCD_NOTIFICATION_SEND_TO"
	 * otherwise if stored procedure run normally in daily routine it will notify to the users who are included in the column name "NIGHTLY_JOB". 
	 */
	@RequestMapping(value = "/executeHl7CursorstoMatchAgainstCCD", produces = "application/json")
	public @ResponseBody Map<Object, Object> executeHl7RematchingCursorsAgainstCCD(HttpServletRequest request, UserObject userObject) {

		Map<Object, Object> targetMap = new HashMap<>();
		Map<String, String> statusObject = new LinkedHashMap<>();
		// initiator
		statusObject.put("Initiator", "HL7 Manual Process Initiated by &lt;<b>" + userObject.getUserId() + "</b>&gt;");

		Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();

		try {

			log.error("Executing HL7 cursors to match against CCD-");

			hl7DocumentService.updateDataSTDMatchorNonMatchtoCCD(statusObject, schedulerMetaData);

		} catch (Exception e) {
			// System.err.println(e.getMessage());
			log.error("Exception processing Hl7 cursors : {}", CommonUtil.getStackTrace(e).substring(0, 200));

		}
		
		if(CommonUtil.isNotNullAndEmpty(statusObject.get("Status"))) {
			targetMap.put("status", statusObject.get("Status"));
		}
		
		return targetMap;

	}

}