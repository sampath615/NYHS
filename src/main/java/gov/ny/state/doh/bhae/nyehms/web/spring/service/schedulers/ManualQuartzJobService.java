package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls.EcrlsDataDAO;
import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.SchedulerJobsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.LabDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LabProcessDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsReAssignDocs;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsStdDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MailStatusVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepDocumentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepProcessVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FilterConditionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FilterTypeAndFilterConditionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;


/**
 * This service interface is used to get the information required for Scheduler
 * jobs and to control the Scheduler job
 * 
 * @author ska03
 * 
 */
@Service
public class ManualQuartzJobService {

	/*
	 * Reference to Log
	 */
	private static Logger log = LoggerFactory.getLogger(ManualQuartzJobService.class);

	@Autowired
	private SchedulerJobsDAO schedulerJobsDAO;
	@Autowired
	private RawRecordStatusService rawRecordStatusService;
	@Autowired
	private EcrlsDataDAO eclrsDataDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private LabDocumentDAO labDocumentDAO;
	@Autowired
	private Scheduler schedulerFactoryBean;
	@Autowired
	private ConnectionDAO connectionDAO;
	@Autowired
	private LabProcessDAO labProcessDAO;
	@Autowired
	SendEmail sendEmail;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	@Autowired
	private QuartzSchedulerTransactionHelper quartzSchedulerTransactionHelper;
	@Autowired
	private CommonService commonService;
	@Autowired
	private CommonDocumentService commonDocumentService;
	
	
	private static final long serialVersionUID = 1L;

	private static final String NAME_SPECIAL_CHARACTERS_VALIDATION = "^[A-Za-z-',. ]*$";
	
	private List<String> questClias = null;
	private List<String> questLoincCodes = null;
	private List<String> questTestResult = null;
	private List<String> labCorpClias = null;
	private List<String> labCorpLoincCodes = null;
	private List<String> labCorpTestResult = null;
	private boolean hasQuestCheck = false;
	private boolean hasLabCorpCheck = false;
	private Map<String, List<String>> missingGenoTypeLabs = null;
	
	
	
	//private static final  List<String> bioplexClias = new ArrayList<String>();
	

	/**
	 * {@inheritDoc}
	 */
	public Integer getPermissionsAccessForManualQuartzJob(BindingResult result, UserObject userObject) {
		Integer statusCode = -1;
		String transactionMessage = null;
		int count = 0;
		/*
		 * Temporarily commenting the user object null check condition code. I
		 * need to handle user session expiration condition first
		 * //if(userObject != null){
		 * 
		 * // }else{ // log.error("User session is not available"); // }
		 */
		if (log.isDebugEnabled()) {
			log.debug("Checking the permissions to access the Manual Quartz job page");
		}
		count = schedulerJobsDAO.havingPermissionsForAccessingQuarzScheduler(userObject.getUserId());
		if (count > 0) {
			if (log.isDebugEnabled()) {
				log.debug("{} has permission to access this page.", userObject.getUserFullName());
			}
			statusCode = 0;
			transactionMessage = new StringBuffer("Welcome ").append(userObject.getUserFullName()).toString();
		} else {
			log.error(userObject.getUserFullName() + " with user ID " + userObject.getUserId()
					+ " and with email address " + userObject.getEmailAddress()
					+ " does not have permission to access this page.");
			transactionMessage = new StringBuffer(userObject.getUserFullName()).append(" ")
					.append(CommonConstants.PERMISSIONS_NOT_AVAILABLE_FOR_PAGE).toString();
		}
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		return statusCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<EcrlsLabDocVO> getReAssignDocuments(String recordType) {
		
		Map<Object, Object> inOutParameterMap = new HashMap<>();
		
		if ("HIV".equals(recordType)) {
			inOutParameterMap.put("tableName", "VALLH");
			inOutParameterMap.put("NYEHMS_TABLE", "raw_hiv_eclrs_doc");
		} else {
			inOutParameterMap.put("tableName", "VALLS");
			inOutParameterMap.put("NYEHMS_TABLE", "raw_std_eclrs_doc");
		}
		inOutParameterMap.put("recordType", recordType);
		
		eclrsDataDAO.updateEcrlsProcessedRecords(inOutParameterMap);
		
		List<EcrlsLabDocVO> reAssignHivData = eclrsDataDAO.getEcrlsReAssignDocData(inOutParameterMap);
		

		return reAssignHivData;
	}

	public int updateReAssignDocProcessedStatus(long OBXOBSERVATIONRESULTKEY, long OBROBSERVATIONREQUESTKEY) {

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("OBXOBSERVATIONRESULTKEY", OBXOBSERVATIONRESULTKEY);
		inOutParameterMap.put("OBROBSERVATIONREQUESTKEY", OBROBSERVATIONREQUESTKEY);

		int resultCount = schedulerJobsDAO.updateReAssignDocProcessedStatus(inOutParameterMap);

		if (resultCount == 0) {
			throw new DataRetrievalFailureException("Unable to update the Process date for Reassign document ");
		}

		return resultCount;
	}

	public int updateDocumentProcessStatusLog(int failedDocumentIdsCount, long OBXOBSERVATIONRESULTKEY) {

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("failedDocumentIdsCount", failedDocumentIdsCount);
		inOutParameterMap.put("OBXOBSERVATIONRESULTKEY", OBXOBSERVATIONRESULTKEY);

		int resultCount = schedulerJobsDAO.updateDocumentProcessStatusLog(inOutParameterMap);

		return resultCount;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getRecipientsEmailsForRawHIVCleanedStatus() {

		String[] emailAddress = schedulerJobsDAO.getRecipientsEmailsForRawHIVCleanedStatus();

		if (emailAddress.length == 0) {
			log.error("There is no reciepients to send the status");
		}

		return emailAddress;
	}

	/**
	 * {@inheritDoc}
	 */
	public String triggerSchedulerJob(BindingResult bindingResult, String jobName, String actionName,
			JobDataMap jobDataMap) {

		String transactionMessage = null;

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;

		String initiator = "NYEHMS auto Scheduler";

		try {

			if (jobDataMap.getString("initiator") != null) {

				initiator = jobDataMap.getString("initiator");

			}
			
			JobKey jobKey = JobKey.jobKey(jobName, "DEFAULT");
			
			schedulerFactoryBean.triggerJob(jobKey, jobDataMap);

		} catch (SchedulerException schedulerException) {

			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;

			if (CommonConstants.TRIGGER_SCHEDULER.equals(actionName)) {

				transactionMessage = initiator + " Unable to trigger the Scheduler job";

			} else if (CommonConstants.TRIGGER_PAUSE.equals(actionName)) {
				transactionMessage = initiator + " Unable to pause the Scheduler job";

			} else if (CommonConstants.TRIGGER_RESUME.equals(actionName)) {
				transactionMessage = initiator + " unable to resume the Scheduler job";
			}

			log.error(AppConstants.nyehmLogErrorString.getName() + "    " + transactionMessage, schedulerException);

			if (bindingResult != null) {
				commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			}

		}

		return transactionStatus;
	}

	public List<EcrlsLabDocVO> getEclrsRawDatatoClean(String recordType, long lastInsertedId, int fetchRowCount) {

		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("lastInsertedId", lastInsertedId);
		inOutParameterMap.put("fetchRowCount", fetchRowCount);

		if ("STD".equals(recordType)) {
			inOutParameterMap.put("sourceTable", "valls");
		} else {
			inOutParameterMap.put("sourceTable", "vallh");
		}

		List<EcrlsLabDocVO> rawEcrlsLabDocList = eclrsDataDAO.getEclrsRawDatatoClean(inOutParameterMap);

		if (rawEcrlsLabDocList.size() > 0) {

			long maxOBXOBSERVATIONRESULTKEY = rawEcrlsLabDocList.get(0).getMaxOBXOBSERVATIONRESULTKEY();

			long minOBXOBSERVATIONRESULTKEY = rawEcrlsLabDocList.get(0).getMinOBXOBSERVATIONRESULTKEY();

			int totalNumberOfRecords = rawEcrlsLabDocList.size();

			inOutParameterMap.put("maxOBXOBSERVATIONRESULTKEY", maxOBXOBSERVATIONRESULTKEY);
			inOutParameterMap.put("minOBXOBSERVATIONRESULTKEY", minOBXOBSERVATIONRESULTKEY);
			inOutParameterMap.put("totalNumberOfRecords", totalNumberOfRecords);
			inOutParameterMap.put("recordType", recordType);

			schedulerJobsDAO.insertSchedulerLog(inOutParameterMap);

			// //throw an exception
			// }

		}

		return rawEcrlsLabDocList;
	}

	@Transactional
	public int writeRawEcrlsData(String recordType, EcrlsLabDocVO ecrlsData) {
		int resultCount = 0;
		try {
			if ("STD".equals(recordType) || "STD_LAB".equals(recordType)) {
				resultCount = schedulerJobsDAO.insertStdRawEcrlsData(ecrlsData);
			} else {
				resultCount = schedulerJobsDAO.insertHivRawEcrlsData(ecrlsData);
				labDocumentDAO.updateHashUniqueNumber(ecrlsData.getDOCUMENT_ID());
			}
		} catch (Exception e) {
			String transactionMessage = ecrlsData.getDOCUMENT_ID() + " Unable to insert " + recordType + " record "
					+ e.getMessage();
			log.error(transactionMessage);
			throw new DataRetrievalFailureException(transactionMessage);
		}
		return resultCount;
	}

	public boolean createNewDocAndWriteRawEcrlsData(String recordType, EcrlsLabDocVO ecrlsLabDocVO) {
		boolean transactionStatus = false;
		try {
			transactionStatus = quartzSchedulerTransactionHelper.writeRawEcrlsData(recordType, ecrlsLabDocVO, null);
		} catch (Exception e) {
			String transactionMessage = ecrlsLabDocVO.getDOCUMENT_ID() + " Unable to insert   " + e.getMessage();
			log.error(transactionMessage);
			transactionStatus = false;
		}
		return transactionStatus;
	}

	public int locateInDocumentLocator(int documentId) {
		int resultCount = 0;
		try {
			resultCount = schedulerJobsDAO.insertIntoDocumentLocator(documentId);
		} catch (Exception e) {
			log.error("Unable to insert the data in Document locator table {}", documentId);
			throw new DataRetrievalFailureException(
					"Unable to insert the data in Document locator table " + documentId);
		}
		return resultCount;
	}

	public String checkForDuplicates(String recordType, List<JSONObject> schedulerStatusObject,
			String duplicateColumns) {

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;
		boolean isError = false;

		JSONObject statusObject = new JSONObject();

		try {

			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Checking duplicates in RAW " + recordType + " Data");

			transactionStatus = quartzSchedulerTransactionHelper.checkForDuplicates(recordType, schedulerStatusObject,
					statusObject, duplicateColumns);

		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while checking the duplicates in the Raw " + recordType
					+ " documents : " + e.getMessage();
			log.error(transactionMessage);
			isError = true;
		}
		if (isError) {
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			schedulerStatusObject.add(statusObject);
		}
		return transactionStatus;
	}

	/**
	 * 
	 * Validating CLIA, PFI and (CLIA, PFI combination) exists in the database
	 * or not.
	 * 
	 * If it fails the validation, Storing the document with '7' status.
	 * 
	 * If the PFI contains characters, will remove all other characters except
	 * numerics and '-' before the number.
	 * 
	 * '-' is just identify these non numeric PFIs
	 * 
	 * @param ecrlsLabDocVO
	 *            : HIV document
	 * @param errors
	 *            : List of errors
	 * @param errorCodes
	 *            : List of error codes
	 * @return
	 */
	public boolean validateCliaAndPFi(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes) {

		int _documentId = ecrlsLabDocVO.getDOCUMENT_ID();

		Map<Object, Object> inputOutputParamters = new HashMap<>();

		String clia = ecrlsLabDocVO.getMSHSENDINGFACILITYCLIA().toUpperCase();
		inputOutputParamters.put("clia", clia);

		boolean hasErrors = false;
		int existingMatches = schedulerJobsDAO.checkForExistingCliaMatches(inputOutputParamters);

		if (existingMatches > 0) {

			String pfi = ecrlsLabDocVO.getMSHLABPFI();

			ecrlsLabDocVO.setMSHLABPFI(pfi);
			inputOutputParamters.put("pfi", pfi);
			int existingCliaAndPfiMatches = schedulerJobsDAO.checkForExistingCliaAndPFIMatches(inputOutputParamters);
			if (existingCliaAndPfiMatches == 0) {
				if (log.isDebugEnabled()) {
					log.debug("Clia and PFI combination not available:  {}", _documentId);
				}
				errorCodes.add(2);
				errors.add(new StringBuilder("Invalid CLIA/PFI : (").append(clia).append("/").append(pfi).append(")")
						.toString());
				hasErrors = true;
				writeErrorCode(CommonConstants.RAW_RECORD_CLIA_PFI_ERROR_PROCESS_STATUS, _documentId, "HIV_LAB");
			}
		} else {
			errorCodes.add(1);
			errors.add("Invalid CLIA:" + clia);
			if (log.isDebugEnabled()) {
				log.debug("Invalid Clia: {}",_documentId);
			}
			hasErrors = true;
			writeErrorCode(CommonConstants.RAW_RECORD_CLIA_PFI_ERROR_PROCESS_STATUS, _documentId, "HIV_LAB");
		}
		return hasErrors;
	}

	/**
	 * Validates an incoming HIV document against PFI-specific proficiency
	 * filter type conditions and default PFI-specific proficiency filter type
	 * conditions. If the documents matches with any one of the conditions then
	 * the document fails the validation. It's a hard stop; the document will
	 * not proceed with other validations
	 * 
	 * @param ecrlsLabDocVO
	 *            : incoming HIV document
	 * @param errors
	 *            : list of errors
	 * @param errorCodes
	 *            : list of error codes
	 * @param schedulerMetaData
	 * @return
	 */

//	@Override
//	public boolean handleProficiencyValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors,
//		List<FilterTypeAndFilterConditionsVO> filterTypeAndFilterConditions = labProcessDAO
//				.getFilterConditionsByFilterTypes(inputOutputParamters);
//		hasErrors = checkLabProcessValidationExist(filterTypeAndFilterConditions, filterTypes, documentId,

	public int makeDocumentAsInactivate(int documentId) {
		return rawRecordStatusService.makeDocumentAsInactivate(documentId);
	}

	/**
	 * Validating Incoming HIV document against PFI Specific filter type
	 * conditions except Proficiency filter type and Default PFI Specific
	 * Proficiency filter type conditions except Proficiency filter type
	 * 
	 * If the documents matches with any one of the condition then document
	 * fails the validation.
	 * 
	 * @param ecrlsLabDocVO
	 *            : incoming HIV document
	 * @param errors
	 *            : list of errors
	 * @param errorCodes
	 *            : list of error codes
	 * @param schedulerMetaData
	 * @return
	 */
	public boolean handleProcessStatusValidationErrors(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors,
			List<Integer> errorCodes, Map<String, String> schedulerMetaData) {
		boolean hasErrors = false;

		int _documentId = ecrlsLabDocVO.getDOCUMENT_ID();

		Map<Object, Object> inputOutputParamters = new HashMap<>();

		// inputOutputParamters.put("clia",
		// ecrlsLabDocVO.getMSHSENDINGFACILITYCLIA());

		List<String> pfiList = new ArrayList<>();

		pfiList.add(ecrlsLabDocVO.getMSHLABPFI());

		inputOutputParamters.put("pfiList", pfiList);

		List<Integer> filterTypes = new ArrayList<>();
		
		List<String> errorCodesToBypass = labProcessDAO.getBypassErrorCodes(ecrlsLabDocVO.getDOCUMENT_ID());
		
		if(errorCodesToBypass == null || errorCodesToBypass.size() == 0) {
			filterTypes = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		}else {
			
			if (!errorCodesToBypass.contains("4")) {
				filterTypes.add(1);
			}
			if (!errorCodesToBypass.contains("5")) {
				filterTypes.add(3);
			}
			if (!errorCodesToBypass.contains("6")) {
				filterTypes.add(4);
			}
			if (!errorCodesToBypass.contains("3")) {
				filterTypes.add(2);
			}
			if (!errorCodesToBypass.contains("7")) {
				filterTypes.add(5);
			}
			if (!errorCodesToBypass.contains("8")) {
				filterTypes.add(6);
			}
			if (!errorCodesToBypass.contains("9")) {
				filterTypes.add(7);
			}
			if (!errorCodesToBypass.contains("54")) {
				filterTypes.add(8);
			}
			if (!errorCodesToBypass.contains("53")) {
				filterTypes.add(9);
			}
			if (!errorCodesToBypass.contains("52")) {
				filterTypes.add(10);
			}
		}

		inputOutputParamters.put("filterTypes", filterTypes);

		List<FilterTypeAndFilterConditionsVO> filterTypeAndFilterConditions = labProcessDAO
				.getFilterConditionsByFilterTypes(inputOutputParamters);

		hasErrors = checkLabProcessValidationExist(filterTypeAndFilterConditions, filterTypes, _documentId,
				ecrlsLabDocVO.getMSHLABPFI(), errors, errorCodes, schedulerMetaData);
		return hasErrors;
	}

	/**
	 * Validating the incoming HIV record against Proficiency/Process matching
	 * conditions.
	 * 
	 * First will render the Matching conditions associated the input PFI.
	 * 
	 * If it exists will combine the PFI specific conditions and Default PFI
	 * conditions. Validate against incoming document.
	 * 
	 * If the document is matching against any one of the condition. Then
	 * document failed the validation.
	 * 
	 * 
	 * This validation will be done based on the Filter type
	 * (RESEARCH/DONOR/PROFICIENCY/.....etc)
	 * 
	 * @param filterTypeAndFilterConditions
	 *            : PFI specific filter conditions
	 * @param filterTypes
	 *            : validating against filter types (generally it will be
	 *            Proficiency or (RESEARCH/DONOR/INVALID VALUES ....etc)
	 * @param _documentId
	 *            : incoming HIV document Id
	 * @param pfi
	 *            : Incoming document PFI
	 * @param errors
	 *            : List of errors
	 * @param errorCodes
	 *            : list of error codes
	 * @param schedulerMetaData
	 *            : Meta data
	 * @return
	 */
	private boolean checkLabProcessValidationExist(List<FilterTypeAndFilterConditionsVO> filterTypeAndFilterConditions,
			List<Integer> filterTypes, int _documentId, String pfi, List<String> errors, List<Integer> errorCodes,
			Map<String, String> schedulerMetaData) {

		boolean hasErrors = false;

		Map<Integer, String> pfiProcessQueryStrings = new HashMap<>();

		for (FilterTypeAndFilterConditionsVO filterTypeAndFilterConditionsVO : filterTypeAndFilterConditions) {
			String _sqlQueryString = prepareLabProcessQueryCondition(
					filterTypeAndFilterConditionsVO.getFilterConditions());

			if (CommonUtil.isNotNullAndEmpty(_sqlQueryString)) {
				pfiProcessQueryStrings.put(filterTypeAndFilterConditionsVO.getFILTER_TYPE(), _sqlQueryString);
			}
		}

		for (int filterType : filterTypes) {

			String _defaultFilterConditions = schedulerMetaData.get("Default_PFI_Query_String_" + filterType);

			String _pfiFilterConditions = pfiProcessQueryStrings.get(filterType);

			StringBuilder _completeProcessConditionsForFilterType = new StringBuilder();

			boolean hasProcessQueryConditions = true;
			if (_pfiFilterConditions != null && _defaultFilterConditions != null) {

				_completeProcessConditionsForFilterType.append(_pfiFilterConditions).append(" OR ")
						.append(_defaultFilterConditions);

			} else if (_pfiFilterConditions != null) {

				_completeProcessConditionsForFilterType.append(_pfiFilterConditions);

			} else if (_defaultFilterConditions != null) {

				_completeProcessConditionsForFilterType.append(_defaultFilterConditions);

			} else {
				hasProcessQueryConditions = false;
			}

			if (hasProcessQueryConditions) {

				Map<Object, Object> inputOutputParamters = new HashMap<>();

				inputOutputParamters.put("queryCondition", _completeProcessConditionsForFilterType);

				inputOutputParamters.put("pfi", pfi);

				inputOutputParamters.put("documentId", _documentId);

				int existingMatches = labProcessDAO.checkDataAgainstQueryConditions(inputOutputParamters);

				if (existingMatches > 0) {
					if (1 == filterType) {
						if (log.isDebugEnabled()) {
							log.debug("Research report: {}", _documentId);
						}
						errorCodes.add(4);
						errors.add("Research report");
						hasErrors = true;
					} else if (filterType == 3) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Values: {}", _documentId);
						}
						errorCodes.add(5);
						errors.add("Invalid Values");
						hasErrors = true;
					} else if (filterType == 4) {
						if (log.isDebugEnabled()) {
							log.debug("Donor report: {}", _documentId);
						}
						errorCodes.add(6);
						errors.add("Donor report");
						hasErrors = true;
					} else if (filterType == 2) {
						if (log.isDebugEnabled()) {
							log.debug("Proficiency report: {}", _documentId);
						}
						errorCodes.add(3);
						errors.add("Proficiency report");
						hasErrors = true;
					} else if (filterType == 5) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Provider: {}", _documentId);
						}
						errorCodes.add(7);
						errors.add("Invalid Provider");
						hasErrors = true;
					} else if (filterType == 6) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Facility: {}", _documentId);
						}
						errorCodes.add(8);
						errors.add("Invalid Facility");
						hasErrors = true;
					} else if (filterType == 7) {
						if (log.isDebugEnabled()) {
							log.debug("Referral lab: {}", _documentId);
						}
						errorCodes.add(9);
						errors.add(CommonConstants.REFERRAL_LAB_ERROR);
						hasErrors = true;
					} else if (filterType == 8) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Patient Name: {}", _documentId);
						}
						errorCodes.add(54);
						errors.add("Invalid Patient Name");
						hasErrors = true;
					} else if (filterType == 9) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Patient Address: {}", _documentId);
						}
						errorCodes.add(53);
						errors.add("Invalid Patient Address");
						hasErrors = true;
					} else if (filterType == 10) {
						if (log.isDebugEnabled()) {
							log.debug("Invalid Patient CITY: {}", _documentId);
						}
						errorCodes.add(52);
						errors.add("Invalid Patient CITY");
						hasErrors = true;
					}
				}
			}
		}

		return hasErrors;
	}

	/**
	 * Validating HIV document against Date validations.
	 * 
	 * 
	 * @param ecrlsLabDocVO
	 * @param errors
	 * @param errorCodes
	 * @param stdEcrlsLabDocVO
	 * @return
	 */
	public boolean handleDateValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes,
			StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		int _documentId = ecrlsLabDocVO.getDOCUMENT_ID();

		Timestamp OBRCOLLECTIONDATE = ecrlsLabDocVO.getOBRCOLLECTIONDATE();

		Timestamp MSHMESSAGEDATE = ecrlsLabDocVO.getMSHMESSAGEDATE();
		Timestamp MSHCREATEDATE = ecrlsLabDocVO.getMSHCREATEDATE();
		Timestamp PIDDATEOFBIRTH = ecrlsLabDocVO.getPIDDATEOFBIRTH();
		Timestamp OBRRESULTREPORTDATE = ecrlsLabDocVO.getOBRRESULTREPORTDATE();
		Timestamp OBXOBSERVATIONDATE = ecrlsLabDocVO.getOBXOBSERVATIONDATE();

		Calendar _derivedCollectionDateCalender = null;

		if (OBRCOLLECTIONDATE != null) {
			_derivedCollectionDateCalender = CommonUtil.resetTimeToZero(OBRCOLLECTIONDATE, 1970);
		}

		if (_derivedCollectionDateCalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBRCOLLECTIONDATERAW())) {
			Timestamp collectionDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBRCOLLECTIONDATERAW());

			if (collectionDateRaw != null) {

				_derivedCollectionDateCalender = CommonUtil.resetTimeToZero(collectionDateRaw, 1970);
			}
		}

		if (_derivedCollectionDateCalender != null) {
			stdEcrlsLabDocVO.setCOLLECTION_DATE(new Date(_derivedCollectionDateCalender.getTimeInMillis()));
		}

		Calendar OBXOBSERVATIONDATECalender = null;

		if (OBXOBSERVATIONDATE != null) {

			OBXOBSERVATIONDATECalender = CommonUtil.resetTimeToZero(OBXOBSERVATIONDATE, 1970);
		}

		if (OBXOBSERVATIONDATECalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBXOBSERVATIONDATERAW())) {

			Timestamp observationDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBXOBSERVATIONDATERAW());

			if (observationDateRaw != null) {

				OBXOBSERVATIONDATECalender = CommonUtil.resetTimeToZero(observationDateRaw, 1970);
			}
		}

		if (OBXOBSERVATIONDATECalender != null) {
			stdEcrlsLabDocVO.setOBSERVATION_DATE(new Date(OBXOBSERVATIONDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = OBXOBSERVATIONDATECalender;
		}

		Calendar OBRRESULTREPORTDATECalender = null;

		if (OBRRESULTREPORTDATE != null) {

			OBRRESULTREPORTDATECalender = CommonUtil.resetTimeToZero(OBRRESULTREPORTDATE, 1970);
		}

		if (OBRRESULTREPORTDATECalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBRRESULTREPORTDATERAW())) {

			Timestamp resultReportDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBRRESULTREPORTDATERAW());

			if (resultReportDateRaw != null) {

				OBRRESULTREPORTDATECalender = CommonUtil.resetTimeToZero(resultReportDateRaw, 1970);
			}
		}

		if (OBRRESULTREPORTDATECalender != null) {
			stdEcrlsLabDocVO.setRESULT_REPORT_DATE(new Date(OBRRESULTREPORTDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = OBRRESULTREPORTDATECalender;
		}

		Calendar MSHCREATEDATECalender = null;
		if (MSHCREATEDATE != null) {
			MSHCREATEDATECalender = CommonUtil.resetTimeToZero(MSHCREATEDATE, 1970);
		}

		if (MSHCREATEDATECalender != null) {
			stdEcrlsLabDocVO.setDATE_RECEIVED(new Date(MSHCREATEDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = MSHCREATEDATECalender;

		}

		Calendar MSHMESSAGEDATECalender = null;
		if (MSHMESSAGEDATE != null) {
			MSHMESSAGEDATECalender = CommonUtil.resetTimeToZero(MSHMESSAGEDATE, 1970);
		}

		if (MSHMESSAGEDATECalender != null) {
			MSHMESSAGEDATECalender.add(Calendar.DATE, 30);
		}

		Calendar PIDDATEOFBIRTHCalender = null;

		if (PIDDATEOFBIRTH != null) {

			PIDDATEOFBIRTHCalender = CommonUtil.resetTimeToZero(PIDDATEOFBIRTH, 1900);

		}
		if (PIDDATEOFBIRTHCalender == null && CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getPIDDATEOFBIRTHRAW())) {

			Timestamp pidDateOfBirthRawValue = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getPIDDATEOFBIRTHRAW());

			if (pidDateOfBirthRawValue != null) {

				PIDDATEOFBIRTHCalender = CommonUtil.resetTimeToZero(pidDateOfBirthRawValue, 1900);
			}

		}

		if (PIDDATEOFBIRTHCalender != null) {
			ecrlsLabDocVO.setDerivedDateOfBirth(PIDDATEOFBIRTHCalender);
			stdEcrlsLabDocVO.setDOB_TEXT(CommonUtil.convertCalenderToString(PIDDATEOFBIRTHCalender));
		}

		if (_derivedCollectionDateCalender == null) {
			if (log.isDebugEnabled()) {
				log.debug("Missing Collection Date: {}", _documentId);
			}
			errorCodes.add(24);
			errors.add("Missing Collection Date");
		} else {

			java.sql.Date derivedCollectionDate = new Date(_derivedCollectionDateCalender.getTimeInMillis());

			stdEcrlsLabDocVO.setCALC_COLLECTION_DATE(derivedCollectionDate);

			ecrlsLabDocVO.setDerivedCollectionDate(_derivedCollectionDateCalender);
			ecrlsLabDocVO.setDerivedMshMessageDate(MSHMESSAGEDATECalender);

			if (PIDDATEOFBIRTHCalender != null && PIDDATEOFBIRTHCalender.after(_derivedCollectionDateCalender)) {
				if (log.isDebugEnabled()) {
					log.debug("Date of birth is after Collection Date: {}", _documentId);
				}
				errorCodes.add(10);
				errors.add(new StringBuilder("Date of birth (").append(PIDDATEOFBIRTH).append(")")
						.append(" is after Collection Date(")
						.append(new Date(_derivedCollectionDateCalender.getTimeInMillis())).append(")").toString());
				// errors.add("Date of Birth is after Collection Date");
			}

			// piddateofbirth is invalid

			if (OBRRESULTREPORTDATECalender != null
					&& OBRRESULTREPORTDATECalender.before(_derivedCollectionDateCalender)) {
				if (log.isDebugEnabled()) {
					log.debug("Result Report Date is before Collection Date: {}", _documentId);
				}
				errorCodes.add(12);
				errors.add(new StringBuilder("Result Report Date(")
						.append(new Date(OBRRESULTREPORTDATECalender.getTimeInMillis())).append(")")
						.append(" is before Collection Date(")
						.append(new Date(_derivedCollectionDateCalender.getTimeInMillis())).append(")").toString());
				// errors.add("Result Report Date is before Collection Date");
			}

		}

		java.util.Date currentDate = new java.util.Date();

		long currentTime = currentDate.getTime();

		if (PIDDATEOFBIRTH != null && PIDDATEOFBIRTH.getTime() > currentTime) {
			if (log.isDebugEnabled()) {
				log.debug("Date of birth is after current date: {}", _documentId);
			}
			errorCodes.add(11);
			errors.add(new StringBuilder("Date of birth(").append(PIDDATEOFBIRTH).append(")")
					.append(" is after current date").toString());
			// errors.add("Date of Birth is after current date");

		}

		if (errors.size() > 0) {

			return true;
		} else {
			return false;
		}
	}

	public boolean handleAddressValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes,
			StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		String pIDADDRESSLINE1 = ecrlsLabDocVO.getPIDADDRESSLINE1().toUpperCase().trim();
		String zLRFACILITYADDRESSLINE1 = ecrlsLabDocVO.getZLRFACILITYADDRESSLINE1().trim();
		String zLRPROVIDERADDRESSLINE1 = ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE1().trim();

		Map<String, String> inputParamters = new HashMap<>();

		inputParamters.put("address", pIDADDRESSLINE1);
		
		if (CommonUtil.isNullOrEmpty(pIDADDRESSLINE1)
				&& !Optional.ofNullable(missingGenoTypeLabs).map(map -> map.get(ecrlsLabDocVO.getMSHLABPFI()))
						.orElse(Collections.emptyList()).stream().anyMatch(ecrlsLabDocVO.getOBXLOINCCODE()::equals)){
			errorCodes.add(55);
			errors.add("Patient Address is missing ");
		}

		
		
		if (CommonUtil.isNotNullAndEmpty(zLRFACILITYADDRESSLINE1)
				&& zLRFACILITYADDRESSLINE1.equalsIgnoreCase(pIDADDRESSLINE1)) {

			inputParamters.put("FILTER_TYPE", "Facility");

			int resultCount = schedulerJobsDAO.checkAddressIsCanBeRes(inputParamters);

			if (resultCount == 0) {
				errorCodes.add(13);
				errors.add("Facility address(" + pIDADDRESSLINE1 + ") same as residence address ");
			}
		}

		if (CommonUtil.isNotNullAndEmpty(zLRPROVIDERADDRESSLINE1)
				&& zLRPROVIDERADDRESSLINE1.equalsIgnoreCase(pIDADDRESSLINE1)) {

			inputParamters.put("FILTER_TYPE", "Provider");

			int resultCount = schedulerJobsDAO.checkAddressIsCanBeRes(inputParamters);

			if (resultCount == 0) {
				errorCodes.add(16);
				errors.add("Provider address(" + pIDADDRESSLINE1 + ") same as residence address");
			}

		}

		stdEcrlsLabDocVO.setPROV_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getZLRPROVIDERZIPCODE()));
		stdEcrlsLabDocVO.setFACILITY_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getZLRFACILITYZIPCODE()));
		stdEcrlsLabDocVO.setPERSON_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getPIDZIPCODE()));

		stdEcrlsLabDocVO.setPERSON_STATE(
				validStateOrError(ecrlsLabDocVO.getPIDSTATE().trim(), "Person", errors, errorCodes));
		stdEcrlsLabDocVO.setPROV_STATE(validStateOrError(ecrlsLabDocVO.getZLRPROVIDERSTATE().trim(), "Provider", errors, errorCodes));
		stdEcrlsLabDocVO.setFACILITY_STATE(validStateOrError(ecrlsLabDocVO.getZLRFACILITYSTATE().trim(), "Facility", errors, errorCodes));

		if (errors.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A state as the documents store it (CommonDocumentService.validateState); a state name that is not known is
	 * the error 46 ("Invalid <referer>  State(<state>)") and stored as "".
	 */
	private String validStateOrError(String state, String referer, List<String> errors, List<Integer> errorCodes) {
		return commonDocumentService.findStateCode(state).orElseGet(() -> {
			errorCodes.add(46);
			errors.add("Invalid " + referer + "  State(" + state + ")");
			return "";
		});
	}

	public void handleRaceEthnicityValidationForStdDocument(EcrlsLabDocVO ecrlsLabDocVO,
			EcrlsStdDocVO stdEcrlsLabDocVO) {

		String pfi = ecrlsLabDocVO.getMSHLABPFI();

		int _documentId = ecrlsLabDocVO.getDOCUMENT_ID();

		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("pfi", pfi);

		stdEcrlsLabDocVO.setRACE_WHITE(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEWHITE()));
		stdEcrlsLabDocVO.setRACE_BLACK(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEBLACK()));
		stdEcrlsLabDocVO.setRACE_ASIAN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEASIAN()));
		stdEcrlsLabDocVO.setRACE_INDIAN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACENATIVEAMERALASKAN()));
		stdEcrlsLabDocVO.setRACE_OTHER(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEOTHER()));
		stdEcrlsLabDocVO.setRACE_PACIFIC(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEPACIFIC()));
		stdEcrlsLabDocVO.setRACE_UNKNOWN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEUNKNOWN()));

		String ethnicityCode = ecrlsLabDocVO.getPIDETHNICGROUPCODE().trim();

		if (CommonUtil.isNotNullAndEmpty(ethnicityCode)) {
			inputOutputParamters.put("pid_ethnic_cd", ethnicityCode);

			List<String> ehnicityMatches = schedulerJobsDAO.getMatchesForEthnicityCode(inputOutputParamters);

			if (ehnicityMatches.size() == 0) {

				Map<Object, Object> dummyPfiParameterMap = new HashMap<>();
				dummyPfiParameterMap.put("pfi", CommonConstants.DEFAULT_PFI);
				dummyPfiParameterMap.put("pid_ethnic_cd", ethnicityCode);
				ehnicityMatches = schedulerJobsDAO.getMatchesForEthnicityCode(dummyPfiParameterMap);
			}

			if (ehnicityMatches.size() > 0) {

				String _ethnicity = ehnicityMatches.get(0);

				if (_ethnicity != null && _ethnicity.length() > 3) {
					stdEcrlsLabDocVO.setETHNICITY("");
				} else {
					// More matches throw an error
					stdEcrlsLabDocVO.setETHNICITY(_ethnicity);
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Ethnicity is Empty {}", _documentId);
				}
				stdEcrlsLabDocVO.setETHNICITY("");
			}
		}

		String pidSexCode = ecrlsLabDocVO.getPIDSEXCODE();

		if (CommonUtil.isNotNullAndEmpty(pidSexCode)) {
			String sexCodeMatches = schedulerJobsDAO.getStandardizedGender(pidSexCode.trim());

			if (sexCodeMatches != null) {
				stdEcrlsLabDocVO.setCURRENT_GENDER(pidSexCode);
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Gender is empty: {}", _documentId);
				}
				stdEcrlsLabDocVO.setCURRENT_GENDER("");
			}
		}
	}

	public boolean handleRaceEthnicityValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors,
			List<Integer> errorCodes, StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		String pfi = ecrlsLabDocVO.getMSHLABPFI();

		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("pfi", pfi);

		stdEcrlsLabDocVO.setRACE_WHITE(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEWHITE()));
		stdEcrlsLabDocVO.setRACE_BLACK(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEBLACK()));
		stdEcrlsLabDocVO.setRACE_ASIAN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEASIAN()));
		stdEcrlsLabDocVO.setRACE_INDIAN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACENATIVEAMERALASKAN()));
		stdEcrlsLabDocVO.setRACE_OTHER(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEOTHER()));
		stdEcrlsLabDocVO.setRACE_PACIFIC(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEPACIFIC()));
		stdEcrlsLabDocVO.setRACE_UNKNOWN(LabCommonUtils.checkRaceData(ecrlsLabDocVO.getPIDRACEUNKNOWN()));

		String ethnicityCode = ecrlsLabDocVO.getPIDETHNICGROUPCODE().trim();

		if (CommonUtil.isNotNullAndEmpty(ethnicityCode)) {
			inputOutputParamters.put("pid_ethnic_cd", ethnicityCode);

			List<String> ehnicityMatches = schedulerJobsDAO.getMatchesForEthnicityCode(inputOutputParamters);

			if (ehnicityMatches.size() == 0) {

				Map<Object, Object> dummyPfiParameterMap = new HashMap<>();
				dummyPfiParameterMap.put("pfi", CommonConstants.DEFAULT_PFI);
				dummyPfiParameterMap.put("pid_ethnic_cd", ethnicityCode);
				ehnicityMatches = schedulerJobsDAO.getMatchesForEthnicityCode(dummyPfiParameterMap);
			}

			if (ehnicityMatches.size() > 0) {

				String _ethnicity = ehnicityMatches.get(0);

				if (_ethnicity != null && _ethnicity.length() > 3) {
					errors.add("Invalid Ethnicity(" + _ethnicity + ") for PFI :" + pfi);
				} else {
					// More matches throw an error
					stdEcrlsLabDocVO.setETHNICITY(_ethnicity);
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Invalid Ethnicity code: {}", ethnicityCode);
				}
				errorCodes.add(19);
				errors.add("Invalid Ethnicity code : " + ethnicityCode);
			}
		}

		String pidSexCode = ecrlsLabDocVO.getPIDSEXCODE().trim();

		if (CommonUtil.isNotNullAndEmpty(pidSexCode)) {
			String sexCodeMatches = schedulerJobsDAO.getStandardizedGender(pidSexCode);

			if (sexCodeMatches != null) {
				stdEcrlsLabDocVO.setGENDER(pidSexCode);
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Invalid Gender : {}", pidSexCode);
				}
				errorCodes.add(44);
				errors.add("Invalid Gender :" + pidSexCode);

			}
		}
		if (errors != null && errors.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void handleDinValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		String std_din = ecrlsLabDocVO.getPIDDIN();

		stdEcrlsLabDocVO.setDIN(CommonUtil.dinValidation(std_din));
	}

	public void handleAgeValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO) {
		Integer std_age = null;

		Float zlrAgeValue = calculateAgeValue(ecrlsLabDocVO);

		if (zlrAgeValue != null) {
			if (zlrAgeValue < 1) {
				std_age = 0;
			} else if (zlrAgeValue < 13) {
				std_age = 1;
			} else {
				std_age = 3;
			}

			stdEcrlsLabDocVO.setAGE(zlrAgeValue.intValue());
			stdEcrlsLabDocVO.setAGE_CATEGORY(std_age);
		}

	}

	private Float calculateAgeValue(EcrlsLabDocVO ecrlsLabDocVO) {
		Float zlrAgeValue = null;
		Calendar OBRCOLLECTIONDATE = ecrlsLabDocVO.getDerivedCollectionDate();
		Calendar PIDDATEOFBIRTH = ecrlsLabDocVO.getDerivedDateOfBirth();

		String zlrAge = ecrlsLabDocVO.getZLRAGE().trim();

		if (CommonUtil.isNotNullAndEmpty(zlrAge) && CommonUtil.isNumeric(zlrAge)) {
			try {
				zlrAgeValue = Float.parseFloat(zlrAge);

				if (zlrAgeValue >= 0 && zlrAgeValue <= 90) {
					return zlrAgeValue;
				}
			} catch (NumberFormatException numberFormatException) {
				String errorMessage = "Invalid zlr age(" + zlrAge + ") for : " + ecrlsLabDocVO.getDOCUMENT_ID();
				if (log.isDebugEnabled()) {
					log.debug(errorMessage);
				}
				zlrAgeValue = null;
			}
		}

		if (OBRCOLLECTIONDATE != null && PIDDATEOFBIRTH != null && OBRCOLLECTIONDATE.after(PIDDATEOFBIRTH)) {

			long _collectionDate = OBRCOLLECTIONDATE.getTimeInMillis();
			long _pidDateOfBirth = PIDDATEOFBIRTH.getTimeInMillis();

			zlrAgeValue = ((_collectionDate - _pidDateOfBirth) / (365 * 24 * 60 * 60.0f) / (1000));

			return zlrAgeValue;

		} else {
			return null;
		}

	}

	public void handleSSNValidation(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO) {

		String ssn = ecrlsLabDocVO.getPIDSSN();

		stdEcrlsLabDocVO.setSSN(CommonUtil.formatSSNValue(ssn));
	}

	public boolean handleSoftCheckValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes,
			StdEcrlsLabDocVO stdEcrlsLabDocVO, Map<String, String> schedulerMetaData) {

		String testType = ecrlsLabDocVO.getTESTTYPE();

		int alreadyValidatedCount = schedulerJobsDAO
				.checkDocumentTraversedThroughSoftCheckValidations(ecrlsLabDocVO.getDOCUMENT_ID());
		//
		// if (errors.size() == 0
		// && ecrlsLabDocVO.getCURRENT_PROCESS_STATUS() ==
		// CommonConstants.RAW_RECORD_REPRISE_PROCESS_STATUS &&

		String std_interpretation_id = "";
		String test_result = "";
		String softErrorMessage = "";
		Integer softErrorCode = null;
		boolean hasSequenceError = false;
		boolean hasSoftError = false;
		if (CommonConstants.TEST_TYPE_RESISTANCE.equalsIgnoreCase(testType)) {
			String obxObservationResultText = ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT().trim().toUpperCase();
			
			
			if (CommonUtil.isNullOrEmpty(obxObservationResultText)) {
				std_interpretation_id = "MISSING SEQUENCE";
				hasSequenceError = true;
				softErrorCode = 40;
				softErrorMessage = "Missing Sequence";
				test_result = "MISSING SEQUENCE";
			} else if (checkResultsTextHasNoSequence(obxObservationResultText, schedulerMetaData.get("NO_SEQUENCE_KEYWORDS"))) {
				ecrlsLabDocVO.setOBXOBSERVATIONRESULTTEXT("NO SEQUENCE");
				std_interpretation_id = "NO SEQUENCE";
				test_result = "MISSING SEQUENCE";
			} else if (obxObservationResultText.length() > 4000) {
				errorCodes.add(34);
				errors.add("Sequence is too high");
			} else if (obxObservationResultText.length() > 256) {

				if (obxObservationResultText.matches("[-a-zA-Z]+")) {
					std_interpretation_id = "VALID SEQUENCE";
				} else {
					std_interpretation_id = "VALID SEQUENCE NON ALPHA";
					hasSequenceError = true;
					softErrorCode = 41;
					softErrorMessage = "Sequence contains non alpha characters";
				}
				test_result = "VALID SEQUENCE";
			} else {
				std_interpretation_id = "SEQUENCE TOO SHORT";
				hasSequenceError = true;
				softErrorCode = 42;
				softErrorMessage = "Sequence is Short";
				test_result = "INVALID SEQUENCE";
			}

			Map<Object, Object> inputOutputParamters = new HashMap<>();

			inputOutputParamters.put("std_interpretation_id", std_interpretation_id);
			String INTERPRET_CD = schedulerJobsDAO.getStandardInterpretationCodeUsingDescription(inputOutputParamters);

			stdEcrlsLabDocVO.setINTERPRET_CD(INTERPRET_CD);

			stdEcrlsLabDocVO.setTEST_RESULT(test_result);
		}

		if (alreadyValidatedCount > 0) {
			return false;
		}

		if (hasSequenceError) {
			errorCodes.add(softErrorCode);
			errors.add(softErrorMessage);
		}
		
		Date COLLECTION_DATE = stdEcrlsLabDocVO.getCOLLECTION_DATE();
		
		if(COLLECTION_DATE == null) {
			errorCodes.add(50);
			errors.add("Collection date is missing");
			hasSoftError = true;
		}
		
		Calendar derivedCOLLECTIONDATE = ecrlsLabDocVO.getDerivedCollectionDate();
		Calendar MSHMESSAGEDATECalender = ecrlsLabDocVO.getDerivedMshMessageDate();
		Timestamp pIDDATEOFBIRTH = ecrlsLabDocVO.getPIDDATEOFBIRTH();
		Timestamp mshCreateDate = ecrlsLabDocVO.getMSHCREATEDATE();

		if (MSHMESSAGEDATECalender != null && derivedCOLLECTIONDATE.after(MSHMESSAGEDATECalender)) {
			if (log.isDebugEnabled()) {
				log.debug("Collection Date is after Message Date: {}", ecrlsLabDocVO.getDOCUMENT_ID());
			}
			errorCodes.add(43);
			errors.add(new StringBuilder("Collection Date (").append(new Date(derivedCOLLECTIONDATE.getTimeInMillis()))
					.append(")").append(" is after Message Date(")
					.append(new Date(MSHMESSAGEDATECalender.getTimeInMillis())).append(")").toString());
			hasSoftError = true;
		}

		if (derivedCOLLECTIONDATE != null && pIDDATEOFBIRTH != null) {

			long oBRCOLLECTIONDATE_milliSeconds = derivedCOLLECTIONDATE.getTimeInMillis();
			long pIDDATEOFBIRTH_milliSeconds = pIDDATEOFBIRTH.getTime();

			long milliSeconds = oBRCOLLECTIONDATE_milliSeconds - pIDDATEOFBIRTH_milliSeconds;
			
			
			long years = milliSeconds / (365 * 24 * 60 * 60) / (1000);
			
			long days = milliSeconds / ( 24 * 60 * 60) / (1000);
			
			long hours = milliSeconds / (60 * 60 * 1000);
			
			if (years >= 90) {
				errorCodes.add(35);
				errors.add("Age 90 or over");
				hasSoftError = true;
			}else if(days <= 365  && !CommonConstants.TEST_TYPE_CD4.equalsIgnoreCase(testType)){
				if(hours <= 24){
					errorCodes.add(48);
					errors.add("DOB("+pIDDATEOFBIRTH+") Equals Collection Date");
					hasSoftError = true;
				}else{
					errorCodes.add(39);
					errors.add("DOB("+pIDDATEOFBIRTH+") 1 year of age or Less");
					hasSoftError = true;
				}
			}

		}

		if (mshCreateDate != null && derivedCOLLECTIONDATE != null) {
			long oBRCOLLECTIONDATE_milliSeconds = derivedCOLLECTIONDATE.getTimeInMillis();
			long mshCreateDate_milliSeconds = mshCreateDate.getTime();

			if ((mshCreateDate_milliSeconds - oBRCOLLECTIONDATE_milliSeconds) / (24 * 60 * 60 * 1000.0) > 60) {
				errorCodes.add(36);
				errors.add("Create Date(" + new Date(mshCreateDate.getTime()) + ") to Collection Date("
						+ new Date(derivedCOLLECTIONDATE.getTimeInMillis()) + ") is over 60 days");
				hasSoftError = true;
			}
		}

		String pidFirstName = ecrlsLabDocVO.getPIDFIRSTNAME();
		String pidLastName = ecrlsLabDocVO.getPIDLASTNAME();

		if (!Pattern.matches(NAME_SPECIAL_CHARACTERS_VALIDATION, pidFirstName)) {
			errorCodes.add(38);
			errors.add("First Name(" + pidFirstName + ") contains invalid characters");
			hasSoftError = true;
		}
			
		if (!Pattern.matches(NAME_SPECIAL_CHARACTERS_VALIDATION, pidLastName)) {
			errorCodes.add(37);
			errors.add("Last Name(" + pidLastName + ") contains invalid characters");
			hasSoftError = true;
		} else if (pidLastName.trim().length() == 1) {
			errorCodes.add(47);
			errors.add("Last Name(" + pidLastName + ")  is one character in length");
			hasSoftError = true;
		}
		
		if((CommonUtil.isNullOrEmpty(pidFirstName) || "unknown".equalsIgnoreCase(pidFirstName.trim())) && (CommonUtil.isNullOrEmpty(pidLastName) || "unknown".equalsIgnoreCase(pidLastName.trim()))){
			errorCodes.add(49);
			errors.add("Invalid Last Name(" + pidLastName + ") and First Name(" + pidFirstName + ") ");
		}

		if (hasSoftError || hasSequenceError) {
			return true;
		} else {
			return false;
		}

	}

	public boolean handleLabDataValidation(EcrlsLabDocVO ecrlsLabDocVO, List<String> errors, List<Integer> errorCodes,
			StdEcrlsLabDocVO stdEcrlsLabDocVO, Map<String, String> schedulerMetaData) {

		int _documentId = ecrlsLabDocVO.getDOCUMENT_ID();

		Timestamp collectionDate = null;

		if (ecrlsLabDocVO.getDerivedCollectionDate() != null) {
			collectionDate = new Timestamp(ecrlsLabDocVO.getDerivedCollectionDate().getTimeInMillis());
		}

		Map<Object, Object> inputOutputParamters = new HashMap<>();

		String std_loinc = "";
		String std_units = "";
		String std_interpretation_id = null;
		String INTERPRET_CD = null;

		String MSHLABPFI = ecrlsLabDocVO.getMSHLABPFI();
		String OBXLOCALTESTCODE = ecrlsLabDocVO.getOBXLOCALTESTCODE().trim();
		String OBXLOINCCODE = ecrlsLabDocVO.getOBXLOINCCODE().trim();

		inputOutputParamters.put("pfi", MSHLABPFI);
		inputOutputParamters.put("collectionDate", collectionDate);

		if (CommonUtil.isNullOrEmpty(OBXLOINCCODE)
				&& (CommonUtil.isNotNullAndEmpty(MSHLABPFI) && CommonUtil.isNotNullAndEmpty(OBXLOCALTESTCODE))) {

			inputOutputParamters.put("localCode", OBXLOCALTESTCODE.toUpperCase());

			if (collectionDate != null) {

				String localCodeMapId = schedulerJobsDAO.getlocalCodeMapIdForPfiLocalCodeMatch(inputOutputParamters);

				if (CommonUtil.isNotNullAndEmpty(localCodeMapId)) {

					List<String> resultLoincMatches = schedulerJobsDAO.getLoincsForPfiLocalCodeMatch(localCodeMapId);

					if (resultLoincMatches.size() == 0) {
						if (log.isDebugEnabled()) {

							// TODO : logger change
							log.debug("LOINC is missing {}", _documentId);
						}
						errorCodes.add(22);
						errors.add("LOINC is missing");
					} else if (resultLoincMatches.size() > 1) {
						if (log.isDebugEnabled()) {
							log.debug("Local code have multiple LOINCs: {}", _documentId);
						}
						errorCodes.add(21);
						errors.add("Multiple LOINCs found for the Local Code : " + OBXLOCALTESTCODE);
					} else {
						std_loinc = resultLoincMatches.get(0);
					}
				} else {
					if (log.isDebugEnabled()) {
						
						log.debug("PFI( {} )/Local Test Code( {} ) are not in the reference table", MSHLABPFI, OBXLOCALTESTCODE);
						
					/*	log.debug("PFI(" + MSHLABPFI + ")/Local Test Code(" + OBXLOCALTESTCODE
								+ ") are not in the reference table");*/
					}
					errorCodes.add(23);
					errors.add("PFI(" + MSHLABPFI + ")/Local Test Code(" + OBXLOCALTESTCODE
							+ ") are not in the reference table");
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Unable to get the LOINC because of collection date is missing");
				}
				errorCodes.add(20);
				errors.add("Unable to get the LOINC from Local Test code because of collection date is missing");
			}
		} else if (CommonUtil.isNotNullAndEmpty(OBXLOINCCODE)) {
			inputOutputParamters.put("loincCode", OBXLOINCCODE);

			if (collectionDate != null) {

				int resultMatches = schedulerJobsDAO.checkForLoincAndPfiMapping(inputOutputParamters);

				if (resultMatches > 0) {
					std_loinc = OBXLOINCCODE;
				} else {
					if (log.isDebugEnabled()) {
						log.debug("PFI( {} )/LOINC( {} ) are not in reference table",MSHLABPFI,OBXLOINCCODE);
					}
					errorCodes.add(25);
					errors.add("PFI(" + MSHLABPFI + ")/LOINC(" + OBXLOINCCODE + ") are not in reference table");
				}
			} else {
				errorCodes.add(45);
				errors.add("Unable to validate LOINC(" + OBXLOINCCODE + ") because of collection date is missing");
			}
		} else {
			errorCodes.add(22);
			errors.add("LOINC is missing");
		}

		boolean unitsOfMeasurementErroredOut = false;
		boolean loincAndUnitsCombinationErrorOut = false;
		String unitsOfMeasurement = ecrlsLabDocVO.getOBXUNITOFMEASURE();
		String clia = ecrlsLabDocVO.getMSHSENDINGFACILITYCLIA();

        String[] cliaArray = new String[]{"31D0696246", "39D0656976", "39D0183896", "17D0648226", "05D0642827", "11D0255931", "10D0291120", "22D0076229"};
        
        // Convert String Array to List
        List<String> cliaList = Arrays.asList(cliaArray);
        
        if(CommonUtil.isNullOrEmpty(unitsOfMeasurement) && cliaList.contains(clia)){
        	if("20447-9".equals(std_loinc) || "41498-7".equals(std_loinc)){
        		unitsOfMeasurement = "COPIES/ML";
        	}else if("29541-0".equals(std_loinc) || "41497-9".equals(std_loinc)) {
        		unitsOfMeasurement = "LOG COPIES/ML";
        	}
        	
        }else if(CommonUtil.isNullOrEmpty(unitsOfMeasurement) &&  "62469-2".equals(std_loinc) && "5064".equals(MSHLABPFI)) {
        	unitsOfMeasurement = "COPIES/ML";
        }
        
		if (CommonUtil.isNotNullAndEmpty(unitsOfMeasurement.trim())) {
			List<String> translatedStdUnitsOfMeasurements = schedulerJobsDAO
					.getStandardizedUnitsOfMeasurement(unitsOfMeasurement);

			if (translatedStdUnitsOfMeasurements.size() > 0) {
				std_units = translatedStdUnitsOfMeasurements.get(0);

			} else {
				if (log.isDebugEnabled()) {
					log.debug("Unit of measure needs standardized mapping : {}", _documentId);
				}
				unitsOfMeasurementErroredOut = true;
			}
		} else {
			std_units = "";
		}

		stdEcrlsLabDocVO.setUNIT_MEASURE_CODE(std_units);

		String loincDescription = "";
		String testType = "";
		String unitsOfMeasureMent = "";
		String resultInterpretation = "";
		String resultStandardCode = "";
		
		if (CommonUtil.isNotNullAndEmpty(std_loinc)) {

			inputOutputParamters.put("loincCode", std_loinc);

			stdEcrlsLabDocVO.setLOINC(std_loinc);

			inputOutputParamters.put("unitsOfMeasurement", std_units);

			Map<String, String> loincUnitsOfMesurement = schedulerJobsDAO
					.checkForLoincAndUnitsOfMeasurementCombination(inputOutputParamters);

			if (loincUnitsOfMesurement != null && loincUnitsOfMesurement.get("DESCRIPTION") != null) {
				loincDescription = loincUnitsOfMesurement.get("DESCRIPTION");
			}

			if (loincUnitsOfMesurement != null && loincUnitsOfMesurement.get("TEST_TYPE") != null) {
				testType = loincUnitsOfMesurement.get("TEST_TYPE");
			}

			if (loincUnitsOfMesurement != null && loincUnitsOfMesurement.get("UNIT_OF_RESULT") != null) {
				unitsOfMeasureMent = loincUnitsOfMesurement.get("UNIT_OF_RESULT");
			} else {
				unitsOfMeasureMent = "";
			}

			if ("".equals(loincDescription) || "".equals(testType) || (!std_units.equals(unitsOfMeasureMent))) {
				if (!unitsOfMeasurementErroredOut) {
					loincAndUnitsCombinationErrorOut = true;
				}
			}

			ecrlsLabDocVO.setTESTTYPE(testType);
			
			
			String oBXOBSERVATIONRESULTTEXT = ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT().toUpperCase();
			
			String obxNote = ecrlsLabDocVO.getOBXNOTE();
			
			boolean testResultsUpdate = false;
			
			
			if(hasQuestCheck && CommonUtil.isNotNullAndEmpty(obxNote) && questClias.contains(clia) && questTestResult.contains(oBXOBSERVATIONRESULTTEXT) && questLoincCodes.contains(std_loinc)) {
				testResultsUpdate = true;
			}else if(hasLabCorpCheck && CommonUtil.isNotNullAndEmpty(obxNote) && labCorpClias.contains(clia) && labCorpTestResult.contains(oBXOBSERVATIONRESULTTEXT) && labCorpLoincCodes.contains(std_loinc)) {
				testResultsUpdate = true;
			}
			
			if(testResultsUpdate) {
				
				if(obxNote.replaceAll("\\s+", " ").toUpperCase().contains(CommonConstants.HIV_RNA_DETECTED)){
					oBXOBSERVATIONRESULTTEXT = (new StringBuilder(oBXOBSERVATIONRESULTTEXT).append(" ").append(CommonConstants.DETECTED)).toString();;
				}else if(obxNote.replaceAll("\\s+", " ").toUpperCase().contains(CommonConstants.NOT_DETECTED)) {
					oBXOBSERVATIONRESULTTEXT = (new StringBuilder(oBXOBSERVATIONRESULTTEXT).append(" ").append(CommonConstants.NOT_DETECTED)).toString();
				}else if(obxNote.replaceAll("\\s+", " ").toUpperCase().contains(CommonConstants.DETECTED)){
					oBXOBSERVATIONRESULTTEXT = (new StringBuilder(oBXOBSERVATIONRESULTTEXT).append(" ").append(CommonConstants.DETECTED)).toString();;
				}
			}

			if (!"".equals(testType) && !CommonConstants.TEST_TYPE_RESISTANCE.equalsIgnoreCase(testType)) {

				boolean isNumeric = false;
				String lowerBound = "";
				String higherBound = "";

				

				String obxTestConvertToNumericValue = oBXOBSERVATIONRESULTTEXT;

				obxTestConvertToNumericValue = obxTestConvertToNumericValue.replaceAll("CELLS/MCL", "")
						.replaceAll("PERCENT", "").replaceAll("LOG COPIES/ML", "").replaceAll("COPIES/ML", "").trim();

				if (obxTestConvertToNumericValue.startsWith(".")) {

					obxTestConvertToNumericValue = new StringBuilder("0").append(obxTestConvertToNumericValue)
							.toString();

				}

				if (obxTestConvertToNumericValue.endsWith(".")) {
					obxTestConvertToNumericValue = obxTestConvertToNumericValue.substring(0,
							obxTestConvertToNumericValue.length() - 1);
				}

				if (obxTestConvertToNumericValue.contains(",")) {
					obxTestConvertToNumericValue = obxTestConvertToNumericValue.replaceAll(",", "");
				}

				List<Map<String, String>> resultObjs = new ArrayList<>();

				if (CommonUtil.isNumeric(obxTestConvertToNumericValue)) {

					if ("VL".equals(testType) && ("C/ML".equals(unitsOfMeasureMent) || "LOG".equals(unitsOfMeasureMent))
							&& ("0".equals(oBXOBSERVATIONRESULTTEXT) || "0.00".equals(oBXOBSERVATIONRESULTTEXT))) {

						// oBXOBSERVATIONRESULTTEXT = "<0";

						isNumeric = false;
					} else {
						isNumeric = true;
						oBXOBSERVATIONRESULTTEXT = obxTestConvertToNumericValue;
					}

				}

				oBXOBSERVATIONRESULTTEXT = oBXOBSERVATIONRESULTTEXT.trim();

				inputOutputParamters.put("oBXOBSERVATIONRESULTTEXT", oBXOBSERVATIONRESULTTEXT);

				resultObjs = getObxObservationResultsMapping(oBXOBSERVATIONRESULTTEXT, std_loinc, isNumeric);

				if (resultObjs == null || resultObjs.size() == 0) {
					errorCodes.add(29);
					errors.add("obxObservationresultText(" + oBXOBSERVATIONRESULTTEXT
							+ ") needs to be standardized for LOINC : " + std_loinc);
				} else if (resultObjs.size() > 1) {
					errorCodes.add(28);
					errors.add("Multiple Result standard codes associated to the LOINC(" + std_loinc
							+ ") and obxObservationresultText(" + oBXOBSERVATIONRESULTTEXT + ") ");
				} else if (resultObjs.size() == 1) {
					Map<String, String> standardResults = resultObjs.get(0);

					if (standardResults.get("RESULTS_STD") != null) {
						resultStandardCode = standardResults.get("RESULTS_STD");
						
						String bioplexClias = schedulerMetaData.get("BIOPLEX_CLIAS");
						
						if("56888-1".equals(std_loinc) && "RCT".equals(resultStandardCode) && bioplexClias.indexOf(clia) == -1) {
							resultStandardCode = "POS";
					
						}
						
						
					}

					if (standardResults.get("RESULT_INTERPRETATION") != null) {
						resultInterpretation = standardResults.get("RESULT_INTERPRETATION");
					}

					if (standardResults.get("RANGE_LOWER_BOUND") != null) {
						lowerBound = (String) standardResults.get("RANGE_LOWER_BOUND");
					}

					if (standardResults.get("RANGE_UPPER_BOUND") != null) {
						higherBound = standardResults.get("RANGE_UPPER_BOUND");
					}

					String interpretationCodesString = schedulerMetaData
							.get("ACCEPTED_INTERPRETATION_CODES_WITHOUT_STANDARDCODE");

					List<String> validInterpretationCodes = new ArrayList<>();

					if (interpretationCodesString != null) {
						String[] interpretationCodes = interpretationCodesString.split(",");

						validInterpretationCodes = Arrays.asList(interpretationCodes);
					}

					if (!isNumeric
							&& (!validInterpretationCodes.contains(resultStandardCode)
									&& "".equals(resultInterpretation))
							&& ("".equals(resultStandardCode) || "".equals(resultInterpretation))) {
						errorCodes.add(30);
						errors.add("Invalid eHARS Standard results are associated to the LOINC(" + std_loinc + ")");
					} else if (isNumeric && ("".equals(lowerBound) || "".equals(higherBound))) {
						errorCodes.add(31);
						errors.add("Invalid acceptable test results mapped to LOINC :(" + std_loinc + ")");
					} else {

						if (isNumeric) {
							stdEcrlsLabDocVO.setTEST_RESULT(oBXOBSERVATIONRESULTTEXT);
						} else {
							StringBuilder testResults = new StringBuilder();
							if (!"=".equals(resultInterpretation)
									&& CommonUtil.isNotNullAndEmpty(resultInterpretation)) {
								testResults.append(resultInterpretation);
							}
							testResults.append(resultStandardCode);

							stdEcrlsLabDocVO.setTEST_RESULT(testResults.toString());
						}

					}

				}

				String specimenSourceName = ecrlsLabDocVO.getOBRSPECIMENSOURCENAME();

				if (CommonUtil.isNotNullAndEmpty(specimenSourceName)) {

					String stdSpecimenSourceName = schedulerJobsDAO
							.checkSpecimenSourceNameExistance(specimenSourceName);

					if (stdSpecimenSourceName == null) {
						errorCodes.add(33);
						errors.add("obrspecimensourcename(" + specimenSourceName + ") need to be standardized");
					} else {
						stdEcrlsLabDocVO.setSPECIMEN_SOURCE_NAME(stdSpecimenSourceName);
					}

				}

				
				if(errors.size() == 0 || (errors.size() == 1 && "Donor report".equals(errors.get(0))) ){
					boolean hadInterpreationError = false;
					String eharsResultcode = null;
					if (CommonConstants.TEST_TYPE_CD4.equals(testType) && isNumeric) {
						if ("100".equals(higherBound)) {
							std_interpretation_id = "CD4 PERCENT";
						} else if (!"".equals(higherBound)) {
							std_interpretation_id = "CD4 COUNT";
						} else {
							errors.add("Standard interpreation is missing for Test type CD4");
						}

					} else if (CommonConstants.TEST_TYPE_CD4.equals(testType) && !isNumeric) {
						// eharsResultcode = resultInterpretation;

						if ("NO RESULT".equals(resultStandardCode)) {
							eharsResultcode = resultStandardCode;
						} else if ("CNT".equals(unitsOfMeasureMent)) {
							std_interpretation_id = "CD4 COUNT";
						} else {
							std_interpretation_id = "CD4 PERCENT";
						}
					} else if (CommonConstants.TEST_TYPE_VL.equals(testType)
							&& (CommonConstants.STD_RESULTS_INTERPRETATION_EQUAL.equalsIgnoreCase(resultInterpretation)
									|| CommonConstants.STD_RESULTS_INTERPRETATION_GREATER
											.equalsIgnoreCase(resultInterpretation))) {
						
						if(CommonUtil.isNotNullAndEmpty(oBXOBSERVATIONRESULTTEXT) && checkTenMillion(oBXOBSERVATIONRESULTTEXT.trim())){
							errorCodes.add(51);
							errors.add("Testresult "+ oBXOBSERVATIONRESULTTEXT);
						}
						
						// eharsResultcode = resultInterpretation;
						std_interpretation_id = "DETECTABLE QUANTITATIVE VIRAL LOAD";
					} else if (CommonConstants.TEST_TYPE_VL.equals(testType)
							&& CommonConstants.STD_RESULTS_INTERPRETATION_LESSTHAN
									.equalsIgnoreCase(resultInterpretation)) {
						std_interpretation_id = "UNDETECTABLE QUANTITATIVE VIRAL LOAD";
						
						if(CommonUtil.isNotNullAndEmpty(oBXOBSERVATIONRESULTTEXT) && checkTenMillion(oBXOBSERVATIONRESULTTEXT.trim())){
							errorCodes.add(51);
							errors.add("Testresult "+ oBXOBSERVATIONRESULTTEXT);
						}
						
					} else {
						eharsResultcode = resultStandardCode;
					}

					if (!hadInterpreationError) {
						inputOutputParamters.put("std_interpretation_id", std_interpretation_id);
						inputOutputParamters.put("testType", testType);
						inputOutputParamters.put("eharsResultcode", eharsResultcode);

						INTERPRET_CD = schedulerJobsDAO
								.getStandardInterpretationCodeUsingDescription(inputOutputParamters);

						if (CommonUtil.isNullOrEmpty(INTERPRET_CD)) {
							errorCodes.add(32);
							errors.add("eHARS standard results not valid for PFI(" + MSHLABPFI + ") and LOINC("
									+ std_loinc + ")");
						} else {
							stdEcrlsLabDocVO.setINTERPRET_CD(INTERPRET_CD);
						}

					}
				}
			}

			if (CommonUtil.isNullOrEmpty(INTERPRET_CD) || INTERPRET_CD.indexOf("NO_RESULT") == -1) {

				if (unitsOfMeasurementErroredOut) {
					errorCodes.add(26);
					errors.add("Unit of measure(" + unitsOfMeasurement + ") needs standardized mapping");
				} else if (loincAndUnitsCombinationErrorOut) {
					errorCodes.add(27);
					errors.add("LOINC(" + std_loinc + ")/Unit of measure(" + std_units
							+ ") are not in the reference table");
				}
			}

		}

		if (errors.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Map<String, String>> getObxObservationResultsMapping(String oBXOBSERVATIONRESULTTEXT, String loincCode,
			Boolean isNumeric) {

		Map<Object, Object> inputOutputParamters = new HashMap<>();

		inputOutputParamters.put("oBXOBSERVATIONRESULTTEXT", oBXOBSERVATIONRESULTTEXT.toUpperCase());
		inputOutputParamters.put("loincCode", loincCode);

		if (isNumeric == null) {
			isNumeric = CommonUtil.isNumeric(oBXOBSERVATIONRESULTTEXT);
		}

		if (isNumeric) {
			return schedulerJobsDAO.checkForLoincAndObservationResultRangeCombination(inputOutputParamters);
		} else {
			return schedulerJobsDAO.checkForLoincAndObservationResultTextCombination(inputOutputParamters);

		}
	}

	public int writeErrorCode(int statusCode, Number documentId, String documentType) {
		return rawRecordStatusService.writeErrorCode(statusCode, documentId, documentType);
	}

	public int addValidationError(Number documentId, String errors, int currentProcessStatus,
			boolean havingSoftCheckValidationErrors, Number previousProcessStatus, String documentType) {
		return rawRecordStatusService.addValidationError(documentId, errors, currentProcessStatus, havingSoftCheckValidationErrors,
				previousProcessStatus, documentType);
	}

	public void writeErrorCodesMasterDetail(int processErrorId, List<Integer> errorCodes) {
		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("processErrorId", processErrorId);
		inputOutputParamters.put("errorCodes", errorCodes);

		int resultCount = schedulerJobsDAO.writeErrorCodesMasterDetail(inputOutputParamters);

		if (resultCount == 0) {
			throw new DataRetrievalFailureException("Failed to add the error codes to Master Details table");
		}
	}

	public Map<String, Object> getLastInsertedECLRSRecordId(String recordType) {
		return schedulerJobsDAO.getLastInsertedECLRSRecordId(recordType);
	}

	// @Override
	//
	// @Override
	public List<EcrlsReAssignDocs> getReAssignDocList(String recordType) {
		return schedulerJobsDAO.getReAssignDocList(recordType);
	}

	public List<EcrlsLabDocVO> runSchedulerFromNyehms() {
		List<EcrlsLabDocVO> rawEcrlsLabDocList = schedulerJobsDAO.getRawHIVDocsList();

		return rawEcrlsLabDocList;

	}

	public List<EcrlsLabDocVO> getRawStdUniqueRecordsList() {
		List<EcrlsLabDocVO> rawEcrlsLabDocList = schedulerJobsDAO.getRawStdDocsList();

		return rawEcrlsLabDocList;
	}

	public List<Map<Object, Object>> getRawDocsList(String documentType) {

		String rawTableName = LabCommonUtils.getRawTableName(documentType);

		Map<Object, Object> inputOutputParamters = new HashMap<>();
		inputOutputParamters.put("rawTableName", rawTableName);

		List<Map<Object, Object>> docsList = schedulerJobsDAO.getRawDocsList(inputOutputParamters);

		return docsList;

	}

	/**
	 * 
	 * This method is used to check the Quality stage job status
	 * (SUCCESS/FAILED/WAITING)
	 * 
	 * If SUCCESS: Will load the Quality stage output data If Failed : Will skip
	 * the process at current step and sends the notification to the users If
	 * waiting: will postpone the scheduler for the specified time
	 * 
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * @param logPath
	 *            : Quality stage Output Log file location (variable name)
	 * 
	 * @param fileName
	 *            : Quality stage Output file name to check the status (Variable
	 *            name)
	 * 
	 * @param successMessageIdentification
	 *            : Success message identified (Variable name)
	 * 
	 * @param waitTimeParameter
	 *            : postpone waiting time (Variable name)
	 * 
	 * @param statusObject
	 *            : to store current activity status
	 * 
	 * @param jobName
	 *            : job1/job2. Quality stage matching log file name will be
	 *            created using the Job name
	 * 
	 *            All the variable names will be loaded from the Metadata
	 *            object.
	 * 
	 *            return JSONObject : Current activity status
	 */
	public JSONObject checkQualityStageJobStatus(Map<String, String> schedulerMetaData, String logPath, String fileName,
			String actionName, String successMessageIdentification, String waitTimeParameter, JSONObject statusObject,
			String jobName) {

		String transactionMessage = null;

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_WAITING;

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		Session session = null;

		Channel obj_Channel = null;

		BufferedReader bufferedReader = null;

		InputStream obj_InputStream = null;

		ChannelSftp obj_SFTPChannel = null;

		StringBuilder todaysLogFileName = new StringBuilder("");
		try {

			log.error("Checking the Quality stage job status for {}", actionName);

			config.setProperty("PreferredAuthentications", "password");

			session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"), 22);

			session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

			session.setConfig(config);

			// Connecting to UNIX Jarot server
			session.connect();

			log.error("Connected Jarot prompt");

			String str_FileDirectory = schedulerMetaData.get(logPath);

			obj_Channel = session.openChannel("sftp");
			obj_Channel.connect();

			obj_SFTPChannel = (ChannelSftp) obj_Channel;

			// Changing the directory to Output log location
			obj_SFTPChannel.cd(str_FileDirectory);

			// preparing the output file name with todays data. Quality stage
			// will generate the Log file name by appending todays date.
			String logFileName = schedulerMetaData.get(fileName);

			if (CommonUtil.isNotNullAndEmpty(jobName)) {

				todaysLogFileName.append(jobName);

				todaysLogFileName.append(".");
			}

			todaysLogFileName.append(logFileName);

			if (CommonUtil.isNotNullAndEmpty(jobName)) {

				todaysLogFileName.append(schedulerMetaData.get("LAST_QUALITY_STAGE_SCHEDULER_RUN_DATE_" + jobName));

			} else {

				Calendar calendar = Calendar.getInstance();
				todaysLogFileName.append(".").append(calendar.get(Calendar.YEAR));
				int _currentMonth = calendar.get(Calendar.MONTH) + 1;
				;
				if (_currentMonth < 10) {
					todaysLogFileName.append("0");
				}
				todaysLogFileName.append(_currentMonth);
				int _currentDate = calendar.get(Calendar.DATE);
				if (_currentDate < 10) {
					todaysLogFileName.append("0");
				}
				todaysLogFileName.append(_currentDate);

			}

			// Loading the log file mname in the input stream
			obj_InputStream = obj_SFTPChannel.get(todaysLogFileName.toString());

			bufferedReader = new BufferedReader(new InputStreamReader(obj_InputStream, "UTF-8"));

			String line = null;

			String _sucessValue = schedulerMetaData.get(successMessageIdentification);
			String _errorValue = schedulerMetaData.get("QUALITY_STAGE_SCHEULER_FAILURE_MESSAGE");

			// Checking the Job status line by line with the Success and Failed
			// identifiers

			while ((line = bufferedReader.readLine()) != null) {

				if (line.indexOf(_sucessValue) != -1) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
					transactionMessage = "Successfully perfomed the Quality stage process";
				} else if (line.indexOf(_errorValue) != -1) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "Error occured while perfoming the Quality stage process";
					break;
				}

			}

			// If the job is not success or failure, then the job is still
			// running
			if (CommonConstants.TRANSACTION_STATUS_WAITING.equals(transactionStatus)) {

				long waitTime = 3600000;

				// Get the Wait time from Metadata
				if (waitTimeParameter != null) {
					waitTime = Long.parseLong(schedulerMetaData.get(waitTimeParameter));
				}

				log.error("{} Quality stage process not completed, still performing the process",todaysLogFileName);

				// Postponing the the job for the specific wait time and calling
				// the same method.
				synchronized (this) {
					wait(waitTime);
					checkQualityStageJobStatus(schedulerMetaData, logPath, fileName, actionName,
							successMessageIdentification, waitTimeParameter, statusObject, jobName);
				}

			}
			log.error(transactionMessage);

		} catch (JSchException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "JSchException occured while checking the Quality stage job status of "
					+ todaysLogFileName + "   Exception:  " + e.getMessage();
			log.error(transactionMessage);
		} catch (IOException ioException) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "IOException occured while checking the Quality stage job status of "
					+ todaysLogFileName + "   Exception:  " + ioException.getMessage();
			log.error(transactionMessage);
		} catch (SftpException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "SftpException occured while checking the Quality stage job status  of "
					+ todaysLogFileName + "   Exception:  " + e.getMessage();
			log.error(transactionMessage);
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while checking the Quality stage job status of " + todaysLogFileName
					+ "   Exception:  " + e.getMessage();
			log.error(transactionMessage);
		} finally {

			try {

				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}

			} catch (IOException e) {
				log.error("Exception occured while closing the input Streams  {}", e.getMessage());
			}

			if (!CommonConstants.TRANSACTION_STATUS_WAITING.equals(transactionStatus)) {

				statusObject.put(CommonConstants.STATUS_ACTION_NAME,
						"Checking Quality stage job status for " + actionName);

				statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			}

		}
		return statusObject;

	}

	/**
	 * 
	 * This method is used to check the Quality stage match results data to
	 * Collection objects
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param qualityStageMatchResultsData
	 *            : to hold the Quality stage match results data
	 * 
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * 
	 * @param jobName
	 *            : job1/job2 Quality stage match result files will be created
	 *            using the Job name
	 * 
	 * @return will return the status (Success/Errored out)
	 */
	public boolean loadQualityStageMatchResults(List<JSONObject> schedulerStatusObject,
			Map<String, Map<String, Object>> qualityStageMatchResultsData, Map<String, String> schedulerMetaData,
			String jobName, Map<String, Map<String, String>> zipCodeReferenceMap) {

		JSONObject statusObject = new JSONObject();

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		boolean isError = false;

		Session session = null;

		Channel obj_Channel = null;

		ChannelSftp obj_SFTPChannel = null;

		BufferedReader bufferedReader = null;

		InputStream obj_InputStream = null;

		Connection connection = connectionDAO.getConnectionObj();

		PreparedStatement ps = null;

		List<String> failedDocumentIds = new ArrayList<>();

		String transactionMessage = null;

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;

		log.error("Started the Quality stage match results process");

		boolean processingSSDMDocuments = false;

		int documentTypePosistion = CommonConstants.QS_MATCH_DATA_DOCUMENT_TYPE_POSITION;
		int matchWeightPosition = CommonConstants.QS_MATCH_DATA_MATCH_WEIGHT_POSITION;
		int matchPassPosition = CommonConstants.QS_MATCH_DATA_MATCH_PASS_POSITION;
		int matchCodePosition = CommonConstants.QS_MATCH_DATA_FLAG_MATCH_CD_POSITION;

		try {
			// Quality stage out put file name (togerther.dat) which has match
			// results data
			int fieldsCount = Integer.parseInt(schedulerMetaData.get("TOGETHER_DAT_FIELDS_COUNT"));

			// Record length, all the records should match the specified length
			int recordLength = Integer.parseInt(schedulerMetaData.get("TOGETHER_DAT_RECORD_LENGTH"));

			if ("Y".equals(schedulerMetaData.get("PROCESSING_SSDM_DOCUMENTS"))) {
				processingSSDMDocuments = true;
				documentTypePosistion = 9;
				matchWeightPosition = 10;
				matchPassPosition = 11;
				matchCodePosition = 12;
				fieldsCount = 13;
				recordLength = 175;
			}

			config.setProperty("PreferredAuthentications", "password");

			session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"), 22);

			session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

			session.setConfig(config);

			// Connecting to JAROT to load the match results data
			session.connect();

			log.error("Connected Jarot prompt");

			// Quality stage out put location to load together.dat and
			// undupset.dat
			String str_FileDirectory = schedulerMetaData.get("QUALITY_STAGE_SCHEDULER_OUTPUT_PATH");

			obj_Channel = session.openChannel("sftp");
			obj_Channel.connect();

			obj_SFTPChannel = (ChannelSftp) obj_Channel;

			obj_SFTPChannel.cd(str_FileDirectory);

			log.error("Changed the directory to {}", str_FileDirectory);

			StringBuilder fileName = new StringBuilder(
					schedulerMetaData.get("QUALITY_STAGE_SCHEDULER_OUTPUT_FILE_NAME"));

			if (CommonUtil.isNotNullAndEmpty(jobName)) {
				fileName.append("_");
				fileName.append(jobName);

			}

			obj_InputStream = obj_SFTPChannel.get(fileName.toString());

			log.error("Loading the together.dat file to input Stream");

			bufferedReader = new BufferedReader(new InputStreamReader(obj_InputStream, "UTF-8"));

			String line = null;

			int index = 0;

			ps = connection.prepareStatement(
					"insert into RAW_DOCUMENT_MATCH_RESULTS(QS_PARENT_DOC_ID, LOAD_DATE, DOCUMENT_TYPE_CODE, DOCUMENT_ID, POT_STATENO, MATCHING_DATE, MATCH_WEIGHT, MATCH_PASS, MATCH_CD) values(?,SYSDATE,?,?,?,SYSDATE,?,?,?)");

			while ((line = bufferedReader.readLine()) != null) {

				index++;

				if (CommonUtil.isNotNullAndEmpty(line)) {
					String[] matchingData = line.split("\\|");

					// Checking the record length

					if (matchingData.length == fieldsCount && line.length() == recordLength
							&& !"".equals(matchingData[CommonConstants.QS_MATCH_DATA_DOCUMENTID_POSITION].trim())) {

						/*
						 * Every parent document will have 8 passes. for 8
						 * passes matching data will get change but person,
						 * Provider, facility information will be same.
						 * 
						 * For child document it will have only one entry and
						 * MATCH _CD will be "D"
						 * 
						 */

						/*
						 * Together.dat data pipe delimited
						 * 
						 * every position will define the respective attribute
						 * value
						 * 
						 * ex: 1: Document_id 2: Stateno etc..
						 * 
						 *
						 */

						String documentId = matchingData[CommonConstants.QS_MATCH_DATA_DOCUMENTID_POSITION].trim();

						String stateno = matchingData[CommonConstants.QS_MATCH_DATA_STATENO_POSITION].trim();

						String documentType = matchingData[documentTypePosistion].trim();
						String matchCode = matchingData[matchCodePosition].trim();
						
						ps.setString(1, documentId);
						ps.setString(2, documentType);
						ps.setString(3, documentId);
						ps.setString(4, stateno);
						ps.setString(5, matchingData[matchWeightPosition].trim());
						ps.setString(6, matchingData[matchPassPosition].trim());
						ps.setString(7, matchCode);

						ps.addBatch();

						if (index % 5000 == 0) {
							ps.executeBatch();
						}
						
						if(("BTH_MOM".equals(documentType) || "BTH_INF".equals(documentType)) && "N".equals(matchCode)){
							continue;
						}
						
						
						if (!processingSSDMDocuments) {

							// While holds Person, Provider and Facility
							// information, document_id is the key
							Map<String, Object> documentMatchResults = qualityStageMatchResultsData.get(documentId);

							if (documentMatchResults == null) {

								documentMatchResults = new HashMap<String, Object>();

								documentMatchResults.put("documentId", documentId);
								documentMatchResults.put("documentType", documentType);
								documentMatchResults.put("matchCode", matchCode);

								// Person address start

								String personAddress = matchingData[CommonConstants.QS_MATCH_DATA_PERSON_ADDRESS_POSITION]
										.trim();

								// Replacing the multiple spaces with single
								// space
								documentMatchResults.put("personAddress", personAddress.replaceAll("\\s+", " "));

								String _personCounty = matchingData[CommonConstants.QS_MATCH_DATA_PERSON_COUNTY_POSITION]
										.trim();
								String _personState = matchingData[CommonConstants.QS_MATCH_DATA_PERSON_STATE_POSITION]
										.trim();
								String _personZip = matchingData[CommonConstants.QS_MATCH_DATA_PERSON_ZIP_POSITION]
										.trim();
								String _personCity = matchingData[CommonConstants.QS_MATCH_DATA_PERSON_CITY_POSITION]
										.trim();
								
								documentMatchResults.put("personZip", _personZip);
								documentMatchResults.put("personCounty", _personCounty);
								documentMatchResults.put("personState", _personState);
								documentMatchResults.put("personCity", _personCity);
								
								
								documentMatchResults.put("personCensusTract",
										matchingData[CommonConstants.QS_MATCH_DATA_PERSON_CENCUS_TRACT_POSITION]
												.trim());
								documentMatchResults.put("personLatitude",
										matchingData[CommonConstants.QS_MATCH_DATA_PERSON_LATITUDE_POSITION].trim());
								documentMatchResults.put("personLongitude",
										matchingData[CommonConstants.QS_MATCH_DATA_PERSON_LONGITUDE_POSITION].trim());
								
								
								
								verifyAndUpdateAddressFields(_personCounty, _personState, _personCity, _personZip,
											documentMatchResults, "personCounty", "personState", "personCity", "personLatitude", "personLongitude", "personCensusTract", zipCodeReferenceMap);
								
								
								
								
								
//								if (CommonUtil.isNullOrEmpty(_personCounty) || CommonUtil.isNullOrEmpty(_personState)
//										|| !"NY".equals(_personState)) {
//									populateCountyValue(_personCounty, _personState, _personCity, _personZip,

								documentMatchResults.put("personPlus4",
										matchingData[CommonConstants.QS_MATCH_DATA_PERSON_PLUS4_POSITION].trim());

								

								// Person address end

								// Provider information start
								String providerAddress = matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_ADDRESS_POSITION]
										.trim();

								// Replacing the multiple spaces with single
								// space
								documentMatchResults.put("providerAddress", providerAddress.replaceAll("\\s+", " "));

								String _providerCounty = matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_COUNTY_POSITION]
										.trim();
								String _providerState = matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_STATE_POSITION]
										.trim();
								String _providerZip = matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_ZIP_POSITION]
										.trim();
								String _providerCity = matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_CITY_POSITION]
										.trim();

								documentMatchResults.put("providerZip", _providerZip);
								documentMatchResults.put("providerCounty", _providerCounty);
								documentMatchResults.put("providerState", _providerState);
								documentMatchResults.put("providerCity", _providerCity);
								
								documentMatchResults.put("providerCensusTract",
										matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_CENCUS_TRACT_POSITION]
												.trim());
								documentMatchResults.put("providerLatitude",
										matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_LATITUDE_POSITION].trim());
								documentMatchResults.put("providerLongitude",
										matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_LONGITUDE_POSITION].trim());
								
								
								verifyAndUpdateAddressFields(_providerCounty, _providerState, _providerCity, _providerZip,
										documentMatchResults, "providerCounty", "providerState", "providerCity", "providerLatitude", "providerLongitude", "providerCensusTract", zipCodeReferenceMap);
							
								
//								if (CommonUtil.isNullOrEmpty(_providerCounty)
//										|| CommonUtil.isNullOrEmpty(_providerState) || !"NY".equals(_providerState)) {
//									populateCountyValue(_providerCounty, _providerState, _providerCity, _providerZip,

								
								documentMatchResults.put("providerPlus4",
										matchingData[CommonConstants.QS_MATCH_DATA_PROVIDER_PLUS4_POSITION].trim());

								// Provider information end

								// facility information start
								String facilityAddress = matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_ADDRESS_POSITION]
										.trim();

								// Replacing the multiple spaces with single
								// space
								documentMatchResults.put("facilityAddress", facilityAddress.replaceAll("\\s+", " "));

								String _facilityCity = matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_CITY_POSITION]
										.trim();
								String _facilityZip = matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_ZIP_POSITION]
										.trim();
								String _facilityState = matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_STATE_POSITION]
										.trim();
								String _facilityCounty = matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_COUNTY_POSITION]
										.trim();
								
								documentMatchResults.put("facilityZip", _facilityZip);
								documentMatchResults.put("facilityState", _facilityState);
								documentMatchResults.put("facilityCounty", _facilityCounty);
								documentMatchResults.put("facilityCity", _facilityCity);
								
								documentMatchResults.put("facilityCensusTract",
										matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_CENCUS_TRACT_POSITION]
												.trim());
								documentMatchResults.put("facilityLatitude",
										matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_LATITUDE_POSITION].trim());
								documentMatchResults.put("facilityLongitude",
										matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_LONGITUDE_POSITION].trim());
								
								verifyAndUpdateAddressFields(_facilityCounty, _facilityState, _facilityCity, _facilityZip,
										documentMatchResults, "facilityCounty", "facilityState", "facilityCity", "facilityLatitude", "facilityLongitude", "facilityCensusTract", zipCodeReferenceMap);
								
//								if (CommonUtil.isNullOrEmpty(_facilityCounty)
//										|| CommonUtil.isNullOrEmpty(_facilityState) || !"NY".equals(_facilityState)) {
//									populateCountyValue(_facilityCounty, _facilityState, _facilityCity, _facilityZip,

								documentMatchResults.put("facilityPlus4",
										matchingData[CommonConstants.QS_MATCH_DATA_FACILITY_PLUS4_POSITION].trim());

								// facility information end

								qualityStageMatchResultsData.put(documentId, documentMatchResults);

							}
						}

					} else {
						// If the record length is not matching, storing the
						// failed documentId
						failedDocumentIds.add(line.substring(0, 20));
					}

				}

			}

			ps.executeBatch();

			if (index > 0) {
				if (failedDocumentIds.size() == index) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "All the document records got corrupted(Records information is not as designed) in the together.dat file.";
					failedDocumentIds = new ArrayList<String>();
					log.error(transactionMessage);
					isError = true;
				} else if (failedDocumentIds.size() > 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "Few document records got corrupted(Records information is not as designed) in the together.dat file and stored the remaining records in Results table and loaded STD table address information into Object";
					log.error(transactionMessage);
				} else {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
					transactionMessage = "Successfully stored all the match passes information into Results table and loaded the STD table address information into object";
					log.error(transactionMessage);
				}

			} else {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				transactionMessage = "No data available to process the Quality stage match results   ";
				log.error(transactionMessage);
			}

			statusObject.put(CommonConstants.STATUS_ACTION_NAME,
					"Loading Quality stage match results from together.dat file to Raw document match results and reading STD Address information");

			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			if (failedDocumentIds.size() > 0) {
				statusObject.put(CommonConstants.COMMENTS,
						"Data corrupted for the DocumentIds : " + failedDocumentIds.toString());
			}

			// Adding the Togerther.dat file parsing status
			schedulerStatusObject.add(statusObject);

		} catch (JSchException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "JSchException occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} catch (IOException ioException) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "ioException occured while processing the Quality stage match results and Duplicated results : "
					+ ioException.getMessage();
			log.error(transactionMessage);
		} catch (SftpException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "SftpException occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} finally {
			try {
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (IOException e) {
				log.error("Exception occured while closing the input Streams   {}", e.getMessage());
			} catch (SQLException e) {
				log.error("Exception occured while closing the connection objects   {}", e.getMessage());
			}

			if (isError) {
				statusObject.put(CommonConstants.STATUS_ACTION_NAME,
						"Loading Quality stage match results from together.dat file and Loading into RAW_DOCUMENT_MATCH_RESULTS table");

				statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

				schedulerStatusObject.add(statusObject);
			}

		}

		return isError;
	}

	/**
	 * 
	 * This method is used to check the Quality stage match results data to
	 * Collection objects
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * 
	 * @param parentRecords
	 *            : To hold the Parent documentIds
	 * 
	 * @param duplicatedChildRecords
	 *            : will hold the Parent documentId and its child documents
	 * 
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * 
	 * @param jobName
	 *            : job1/job2 Quality stage Duplicate match result files will be
	 *            created using the Job name
	 * 
	 * @return will return the status (Success/Errored out)
	 */
	public boolean loadQualityStageDuplicateRecords(List<JSONObject> schedulerStatusObject,
			Map<String, String> parentRecords, Map<String, List<String>> duplicatedChildRecords,
			Map<String, String> schedulerMetaData, String jobName) {

		JSONObject statusObject = new JSONObject();

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		boolean isError = false;

		Session session = null;

		Channel obj_Channel = null;

		ChannelSftp obj_SFTPChannel = null;

		BufferedReader bufferedReader = null;

		InputStream obj_InputStream = null;

		List<String> failedDocumentIds = new ArrayList<>();

		String transactionMessage = null;

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;

		log.error("Started the Quality stage Duplicate records process");

		try {
			//

			config.setProperty("PreferredAuthentications", "password");

			session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"), 22);

			session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

			session.setConfig(config);

			// Connecting to JAROT to load the match results data
			session.connect();

			log.error("Connected Jarot prompt");

			// Quality stage out put location to load together.dat and
			// undupset.dat
			String str_FileDirectory = schedulerMetaData.get("QUALITY_STAGE_SCHEDULER_OUTPUT_PATH");

			obj_Channel = session.openChannel("sftp");
			obj_Channel.connect();

			obj_SFTPChannel = (ChannelSftp) obj_Channel;

			obj_SFTPChannel.cd(str_FileDirectory);

			log.error("Changed the directory to {}", str_FileDirectory);

			String line = null;

			int index = 0;

			statusObject = new JSONObject();

			log.error("Started the process to load the quality stage duplicated records set");

			StringBuilder fileName = new StringBuilder(
					schedulerMetaData.get("QUALITY_STAGE_SCHEDULER_OUTPUT_DUPLICATES_FILE_NAME"));

			if (CommonUtil.isNotNullAndEmpty(jobName)) {
				fileName.append("_");
				fileName.append(jobName);

			}

			obj_InputStream = obj_SFTPChannel.get(fileName.toString());

			log.error("Loaded the undup_set.dat file to input Stream");

			bufferedReader = new BufferedReader(new InputStreamReader(obj_InputStream, "UTF-8"));

			index = 0;

			failedDocumentIds = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null) {

				index++;

				/*
				 * Undupset.dat file also pipe delimited
				 * 
				 * This file will have and Parent and Child documents data
				 * 
				 * XA will be identified as Parent DA will be identified as
				 * Child documents
				 * 
				 * One parent can have multiple child documents
				 * 
				 * ex: 2|XA|M 1520008842332| 19966210 2|DA|M 1520008842333|
				 * 19966211 2|DA|M 1520008842333| 19966212 6|XA|M 1520008842378|
				 * 19966252 6|DA|M 1520008842378| 19966253 19966210 : Parent
				 * document ID (XA) 19966211, 19966212 Child documents (DA)
				 * 
				 * first element (2) will define group. 19966210, 19966211,
				 * 19966212 are related Group 2
				 * 
				 * 2 and 6 are two different groups
				 * 
				 * 
				 * 
				 */
				if (CommonUtil.isNotNullAndEmpty(line)) {
					String[] duplicatedData = line.split("\\|");

					// Checking the filed count
					if (duplicatedData.length == CommonConstants.QS_DUPLICATED_DATA_FIELDS_COUNT) {

						String duplicateRecordIdentifier = duplicatedData[0].trim();
						String parentOrChildIdentified = duplicatedData[1].trim();
						String documentId = duplicatedData[3].trim();

						if ("XA".equals(parentOrChildIdentified)) {
							parentRecords.put(duplicateRecordIdentifier, documentId);
						} else if ("DA".equals(parentOrChildIdentified)) {
							List<String> duplicatedDocumentIds = duplicatedChildRecords.get(duplicateRecordIdentifier);
							if (duplicatedDocumentIds == null) {
								duplicatedDocumentIds = new ArrayList<String>();
								duplicatedDocumentIds.add(documentId);
							} else {
								duplicatedDocumentIds.add(documentId);
							}

							duplicatedChildRecords.put(duplicateRecordIdentifier, duplicatedDocumentIds);
						} else {
							failedDocumentIds.add(line);
						}
					} else {
						failedDocumentIds.add(line);
					}
				}
			}

			if (index > 0) {
				int duplicatedDocumentsCount = duplicatedChildRecords.size();
				int parentRecordsCount = parentRecords.size();

				if (index == failedDocumentIds.size()) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "All the data available in the undup_set.dat file is not as expected format.";
					failedDocumentIds = new ArrayList<String>();
					log.error(transactionMessage);
					isError = true;
				} else if (duplicatedDocumentsCount == 0 && parentRecordsCount == 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "No Parent or Child documents available in the undup_set.dat";
					log.error(transactionMessage);
				} else if (parentRecordsCount == 0 && duplicatedDocumentsCount > 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "No Parent records available in the undup_set.dat";
					log.error(transactionMessage);
					isError = true;
				} else if (parentRecordsCount > 0 && duplicatedDocumentsCount == 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "No Child records found for the parent records in the undup_set.dat";
					log.error(transactionMessage);
					isError = true;
				} else if (parentRecordsCount > duplicatedDocumentsCount) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "For few parent records there in child document(s) available in the undup_set.dat";
					log.error(transactionMessage);

				} else if (duplicatedDocumentsCount > parentRecordsCount) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "For few child documents  don't have the parent Document ids in the undup_set.dat";
					log.error(transactionMessage);
				} else if (duplicatedDocumentsCount == parentRecordsCount) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
					transactionMessage = "Successfully loaded the Parent and Duplicate documents from the undup_set.dat";
					log.error(transactionMessage);
				}

			} else {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No duplicate data set found to process the Quality stage duplicate sets";
				log.error(transactionMessage);
			}

			statusObject.put(CommonConstants.STATUS_ACTION_NAME,
					"Loading Quality stage duplicate set from undup_set.dat file");

			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			if (failedDocumentIds.size() > 0) {
				statusObject.put(CommonConstants.COMMENTS,
						"failed to process the DocumentIds : " + failedDocumentIds.toString());
			}

			schedulerStatusObject.add(statusObject);

		} catch (JSchException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "JSchException occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} catch (IOException ioException) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "ioException occured while processing the Quality stage match results and Duplicated results : "
					+ ioException.getMessage();
			log.error(transactionMessage);
		} catch (SftpException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "SftpException occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while processing the Quality stage match results and Duplicated results : "
					+ e.getMessage();
			log.error(transactionMessage);
		} finally {
			try {
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}

			} catch (IOException e) {
				log.error("Exception occured while closing the input Streams   {}", e.getMessage());
			}

			if (isError) {
				statusObject.put(CommonConstants.STATUS_ACTION_NAME,
						"Loading Quality stage match results from together.dat file and Duplicated records from undup_set.dat file");

				statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

				schedulerStatusObject.add(statusObject);
			}

		}

		return isError;
	}

	/**
	 * 
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * @param subToRawMapping
	 *            : which will hold Parent and Child Raw Ids
	 * 
	 * @param fileName
	 *            : Quality stage process out put file name
	 * 
	 * @param actionName
	 *            : current action name
	 * 
	 * @param rawIndex
	 *            : Raw Provider/Facility Record id position
	 * 
	 * @param subIndex
	 *            : Sub Provider/Facility Record id index
	 * 
	 * @param recordLength
	 *            : out put record length
	 * 
	 * @return job status true/false
	 * 
	 * 
	 */
	public boolean loadRawProviderOrFacilityMappingResults(List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData, Map<Integer, List<Integer>> subToRawMapping, String fileName,
			String actionName, int rawIndex, int subIndex, int recordLength) {

		JSONObject statusObject = new JSONObject();

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		boolean isError = false;

		Session session = null;

		Channel obj_Channel = null;

		ChannelSftp obj_SFTPChannel = null;

		BufferedReader bufferedReader = null;

		InputStream obj_InputStream = null;

		List<String> failedRawRecordIds = new ArrayList<>();

		String transactionMessage = null;

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;

		log.error("Started loading {}", actionName);

		try {

			config.setProperty("PreferredAuthentications", "password");

			session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"), 22);

			session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

			session.setConfig(config);

			// Connecting to JAROT
			session.connect();

			log.error("Connected Jarot prompt");

			String str_FileDirectory = schedulerMetaData.get("PROVIDER_FACILITY_MATCH_RESULTS_PATH");

			obj_Channel = session.openChannel("sftp");
			obj_Channel.connect();

			obj_SFTPChannel = (ChannelSftp) obj_Channel;

			// Changing the directory to output file location
			obj_SFTPChannel.cd(str_FileDirectory);

			log.error("Changed the directory to {}", str_FileDirectory);

			obj_InputStream = obj_SFTPChannel.get(schedulerMetaData.get(fileName));

			log.error("Loaded {} to input Stream", actionName);

			// Loading the out put file into the stream
			bufferedReader = new BufferedReader(new InputStreamReader(obj_InputStream, "UTF-8"));

			String line = null;

			int index = 0;

			while ((line = bufferedReader.readLine()) != null) {

				index++;

				if (CommonUtil.isNotNullAndEmpty(line)) {

					if (line.length() == recordLength) {

						// Reading Raw Provider/Facility record id
						String rawRecordIdVal = line.substring(rawIndex, rawIndex + 9).trim();

						// Storing sub Facility/Provider id
						String subRecordIdVal = line.substring(subIndex, subIndex + 9).trim();

						if (!"".equals(rawRecordIdVal) && !"".equals(subRecordIdVal)) {

							int _rawId = Integer.parseInt(rawRecordIdVal);
							int _subId = Integer.parseInt(subRecordIdVal);

							List<Integer> rawIds = null;
							if (subToRawMapping.get(_subId) != null) {
								rawIds = subToRawMapping.get(_subId);
								rawIds.add(_rawId);
							} else {
								rawIds = new ArrayList<Integer>();
								rawIds.add(_rawId);
							}
							subToRawMapping.put(_subId, rawIds);
						}
					} else {
						// If the record length is not matching, storing the Raw
						// record id
						failedRawRecordIds.add(line.substring(rawIndex, rawIndex + 9));
					}
				}

			}

			if (index > 0) {
				if (failedRawRecordIds.size() == index) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "All the records got corrupted(Records information is not as designed) in the output file.";
					failedRawRecordIds = new ArrayList<String>();
					isError = true;
				} else if (failedRawRecordIds.size() > 0 && subToRawMapping.size() > 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
					transactionMessage = "Few records got corrupted(Records information is not as designed) in the output results.";
				} else if (subToRawMapping.size() > 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
					transactionMessage = "Successfully loaded all the records from the Quality stage output file";
				} else if (subToRawMapping.size() == 0 && failedRawRecordIds.size() > 0) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "All the records got corrupted(Records information is not as designed) in the output file.";
					isError = true;
				}

			} else {
				transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
				transactionMessage = "No Standardized data available data available in the Quality stage output file";
			}

			log.error(transactionMessage);

		} catch (JSchException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = actionName + "  JSchException occured while loading the results : " + e.getMessage();
			log.error(transactionMessage);
		} catch (IOException ioException) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = actionName + "  ioException occured while loading the results : "
					+ ioException.getMessage();
			log.error(transactionMessage);
		} catch (SftpException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = actionName + "   SftpException occured while loading the results : " + e.getMessage();
			log.error(transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = actionName + "   Exception occured while loading the results : " + e.getMessage();
			log.error(transactionMessage);
		} finally {
			try {
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}

			} catch (IOException e) {
				log.error("Exception occured while closing the input Streams   {}", e.getMessage());
			}

			// Storing the status
			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Loading Quality stage " + actionName);

			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			if (failedRawRecordIds.size() > 0) {
				statusObject.put(CommonConstants.COMMENTS,
						"Data corrupted for the Raw record Ids : " + failedRawRecordIds.toString());
			}

			schedulerStatusObject.add(statusObject);

		}

		return isError;

	}

	/**
	 * 
	 * This method is used to Standardize the Raw PRovider/Facility records with
	 * Sub Facility/PRovider ids
	 * 
	 * @param subID
	 *            : Sub Provider/Facility id
	 * @param rawIds
	 *            : Raw Provider/Facilities ids
	 * @param failureComments
	 *            : Failure comment if the transaction failed
	 * @param moduleName
	 *            : Facility/Provider
	 * 
	 */
	public void storingStandardizeRawResultsInDatabase(Integer subID, List<Integer> rawIds,
			List<String> failureComments, String moduleName) {

		try {

			int resultCount = 0;

			Map<Object, Object> inputOutputParameterMap = new HashMap<>();

			inputOutputParameterMap.put("rawIds", rawIds);
			inputOutputParameterMap.put("standardSubId", subID);

			if ("PROVIDER".equals(moduleName)) {
				resultCount = schedulerJobsDAO
						.standardizeRawProviderAddressIdsWithSubProviderId(inputOutputParameterMap);
			} else if ("FACILITY".equals(moduleName)) {
				resultCount = schedulerJobsDAO.standardizeRawFacilityIdsWithSubFacilityId(inputOutputParameterMap);
			}

			int totalRawIds = rawIds.size();

			if (resultCount == 0) {
				failureComments.add("Unable to Standardize all the Raw " + moduleName + " records(" + rawIds.toString()
						+ ") with Sub " + moduleName + " id(" + subID + ")");
			} else if (resultCount < totalRawIds) {
				failureComments.add("Unable to Standardize few of Raw " + moduleName + " records(" + rawIds.toString()
						+ ") with Sub " + moduleName + " id(" + subID + ")");
			}

		} catch (Exception e) {
			String transactionMessage = "Unable to Standardize all the Raw " + moduleName + " records("
					+ rawIds.toString() + ") with Sub " + moduleName + " id(" + subID + ")";
			log.error(transactionMessage);
		}

	}


	/**
	 * 
	 * @param documentQualityStageData
	 *            : Which holds 8 passes related data (if the document is
	 *            parent), if the document is child will have only record.
	 * 
	 * @return Transaction result count.
	 */
	public int storeDocumentMatchResults(List<Map<String, String>> documentQualityStageData) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("documentQualityStageData", documentQualityStageData);

		int resultCount = schedulerJobsDAO.storeDocumentMatchResults(inputOutputParameterMap);

		if (resultCount == 0)
			throw new DataRetrievalFailureException("Failed to insert the document match results");

		return resultCount;

	}

	/**
	 * This method is used to update parent document id in the Qs_PARENT_DOC_ID
	 * columsn for the Child document
	 * 
	 * @param parentDocumentId
	 *            : Parent document ID
	 * @param duplicateDocumentIds
	 *            : Child document ids
	 * 
	 * @return transaction result count
	 * 
	 * 
	 */
	public int storeDuplicateDocumentSetDataToMatchResultsTable(String parentDocumentId,
			List<String> duplicateDocumentIds) {

		int totalResultsCount = 0;
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("parentDocumentId", parentDocumentId);
		
		int rowIdListSize = duplicateDocumentIds.size();
		
		//Where condition can accept 1000 arguments.
		for (int index = 0; index < rowIdListSize; index = index+1000){
			
			if(index + 1000 > rowIdListSize){
				inputOutputParameterMap.put("duplicateDocumentIds", duplicateDocumentIds.subList(index, rowIdListSize));
			}else{
				inputOutputParameterMap.put("duplicateDocumentIds", duplicateDocumentIds.subList(index, index+1000));
			}
			
			int resultCount = schedulerJobsDAO.storeDuplicateDocumentSetResults(inputOutputParameterMap);
			
			totalResultsCount = totalResultsCount + resultCount;
			
		}
		
		return totalResultsCount;

	}

	/**
	 * This method is used to load all Standardized STD records into EXPORT01
	 * file.
	 * 
	 * This file will be matched against ALLJARO data.
	 * 
	 * All the Standardized records will be identified with
	 * CURRENT_PROCESS_STATUS = 5
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param schedulerNotificationObj
	 *            : High level status
	 * 
	 * @return SUCCESS/FAILURE
	 */
	public String writeStandardDataToExport01(List<JSONObject> schedulerStatusObject,
			Map<String, String> schedulerMetaData, String RUN_SECOND_JOB_FOR_DEAD,
			List<String> schedulerNotificationObj) {

		StringBuilder failureComments = new StringBuilder("");
		StringBuilder failureDocuments = new StringBuilder("");
		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;
		JSONObject statusObject = new JSONObject();
		Connection connection = connectionDAO.getConnectionObj();
		Statement st = null;
		ResultSet rs = null;
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		JSch jsch = new JSch();
		boolean isError = false;
		Session session = null;
		Channel obj_Channel = null;
		Channel channel = null;
		ChannelSftp obj_SFTPChannel = null;
		InputStream obj_InputStream = null;
		InputStream input = null;

		String actionName = null;
		String statusMessage = null;

		try {
			log.error("Started the process to load Export01 and Calling QS process");

			int export01RecordLength = 522;

			if (connection != null) {
				log.error("loading the Stadard Documents for Qs Process");
				// String _includeDeadPeople =
				// schedulerMetaData.get("WILL_QS_REFERENCE_FILE_INCLUDE_DEAD_PEOPLE");
				String exportDeathDocTypes = schedulerMetaData.get("WILL_QS_REFERENCE_FILE_INCLUDE_DEAD_PEOPLE");

				if ("Y".equals(RUN_SECOND_JOB_FOR_DEAD)) {
					exportDeathDocTypes = "Y";
				}

				if ("Y".equals(exportDeathDocTypes)) {
					statusMessage = "Export01 created with AIRS, DTH, NYC_DTH, ADAP, CASA, CCD, STD_CONF, INS and DOCCS"
							+ " to match against living and dead people";
					actionName = "Creating Export01 to match against living and dead people";
					statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Writing " + actionName);
				} else {
					statusMessage = "Export01 created with HIV, STD, PRF, NYC_PRF, EPRF_OOC, NYC_IJR"
							+ "  to match against living people";
					actionName = "Creating Export01 to match against living people";
					statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Writing " + actionName);
				}

				String _isSSDM = schedulerMetaData.get("PROCESSING_SSDM_DOCUMENTS");
				if ("Y".equals(_isSSDM)) {
					export01RecordLength = 144;
				}
				st = connection.createStatement();

				rs = st.executeQuery(LabCommonUtils.getQueryToLoadStdDataForQsProcess(exportDeathDocTypes, _isSSDM));

				log.error("Got the data from ResultSet");
				int index = 0;
				config.setProperty("PreferredAuthentications", "password");
				session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"),
						22);
				session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));
				session.setConfig(config);

				// Connecting to JAROT
				session.connect();
				log.error("Connected Jarot prompt to write the EXPORT01 data");
				obj_Channel = session.openChannel("sftp");
				obj_Channel.connect();
				log.error("Opened SFTP channel to copy the  Standard data to Export01 file");
				obj_SFTPChannel = (ChannelSftp) obj_Channel;
				// Writing the Standard data to stdData object
				StringBuilder stdDataListObj = new StringBuilder("");
				obj_InputStream = new ByteArrayInputStream("".getBytes());
				String referenceFilePath = schedulerMetaData.get("QUALITY_STAGE_SCHEDULER_INPUT_EXPORT_FILE_NAME");
				// Writing the STD data to output file EXPORT01
				obj_SFTPChannel.put(obj_InputStream, referenceFilePath);
				boolean hasDataToMatch = false;
				while (rs.next()) {

					String formatedDocumentData = CommonUtil.normalizeStringForExport01(rs.getString(1));
					if (formatedDocumentData.length() == export01RecordLength) {

						hasDataToMatch = true;

						stdDataListObj.append(formatedDocumentData);
						stdDataListObj.append("\n");
						if (index == 25000) {
							obj_InputStream = new ByteArrayInputStream(stdDataListObj.toString().getBytes());
							obj_SFTPChannel.put(obj_InputStream, referenceFilePath, 2);
							stdDataListObj = new StringBuilder("");
							index = 0;
						}
						index++;
					} else {
						int documentLength = formatedDocumentData.length();

						if (documentLength > 20) {
							failureDocuments
									.append(formatedDocumentData.substring(documentLength - 20, documentLength - 10))
									.append(",");
						}
					}
				}
				if (!hasDataToMatch) {
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					transactionMessage = "No standard data available to write it to EXPORT01";

					schedulerNotificationObj.add("No standard data available to write it to EXPORT01");

					log.error(transactionMessage);

					statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);

					statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

					schedulerStatusObject.add(statusObject);

					return transactionStatus;
				}
				// After writing the data for every 25k records, if the count is
				// lessthan 25k
				if (!"".equals(stdDataListObj.toString())) {
					obj_InputStream = new ByteArrayInputStream(stdDataListObj.toString().getBytes());
					obj_SFTPChannel.put(obj_InputStream, referenceFilePath, 2);
					stdDataListObj = new StringBuilder("");
				}
				log.error("Converted the Standard data to String in EXPORT01 format.");
				log.error("written the Standard Data to EXPORT01");
				transactionMessage = statusMessage;

				schedulerNotificationObj.add(statusMessage);

			} else {
				isError = true;
				transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				transactionMessage = "Unable to get the datasource connection."
						+ " Stopped the process to load EXPORT01 file";
			}
		} catch (JSchException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("JSchException while writing standard data to Export01:  {}", transactionMessage);
			isError = true;
		} catch (IOException ioException) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = ioException.getMessage();
			log.error("IOException while writing standard data to Export01:  {}", transactionMessage);
		} catch (SftpException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("SftpException while writing standard data to Export01:  {}", transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while writing standard data to Export01:  {}", transactionMessage);
		} finally {
			if (isError) {

				schedulerNotificationObj.add("Failed to write the " + actionName);

				failureComments.append("Pausing the  Quality stage match results processes").append("\n");
				updateSchedulerMetaData("LOAD_QUALITY_STAGE_MATCH_RESULTS_SCHEDULER", "N", null);

			}
			try {
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}
				if (input != null) {
					input.close();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (channel != null) {
					channel.disconnect();
				}
				if (connection != null) {
					connection.close();
				}
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (IOException e) {
				failureComments.append("IOException while closing the channels  " + e.getMessage()).append("\n");
				log.error("IOException while closing the channels  {}", e.getMessage());
			} catch (Exception e) {
				failureComments.append("Exception while closing the channels  " + e.getMessage()).append("\n");
				log.error("exception while closing the channels  {}", e.getMessage());
			}
			// Updating the status
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			if (!"".equals(failureComments.toString())) {
				statusObject.put(CommonConstants.COMMENTS, failureComments);
			}
			if (!"".equals(failureDocuments.toString())) {
				statusObject.put("Failed Documents to write", failureDocuments);
			}
			schedulerStatusObject.add(statusObject);
		}
		return transactionStatus;
	}

	/**
	 * This method is used to call the Quality stage process to standardize the
	 * Raw Providers and Raw facilities
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * @param processName
	 *            Quality stage job name
	 * 
	 * @param schedulerNotificationObj
	 *            : High level status
	 * 
	 * @param actionName
	 *            : defines current action
	 */
	public void callingShellProcess(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData,
			String processName, String actionName, List<String> schedulerNotificationObj) {

		StringBuilder failureComments = new StringBuilder("");

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;

		String transactionMessage = null;

		JSONObject statusObject = new JSONObject();

		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Calling " + actionName);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		Session session = null;
		Channel channel = null;

		InputStream input = null;

		try {

			log.error("Started Calling Shell process for : {}", actionName);

			config.setProperty("PreferredAuthentications", "password");

			session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"), 22);

			session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

			session.setConfig(config);

			// Connecting to JAROT
			session.connect();

			channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);

			channel.connect();

			input = channel.getInputStream();

			log.error("Before running the {}", actionName);
			// Calling the shell process to standardize the records
			int existStatus = 0;
			
			do{
			
				ps.println(schedulerMetaData.get(processName));

				ps.println("exit");

				log.error("Excecuted the {}", actionName);
				
				existStatus = printResult(input, channel);
				
				ps.close();
				
				if(existStatus != 0){
					
					log.error("Unable to start the job, executing the job again");
					
					channel = session.openChannel("shell");
					ops = channel.getOutputStream();
					ps = new PrintStream(ops, true);

					channel.connect();

					input = channel.getInputStream();

				}else{
					
				}
			}while(existStatus != 0);

			transactionMessage = "Successfully executed the " + actionName;

			schedulerNotificationObj.add("Initated the " + actionName);

		} catch (JSchException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("JSchException while running the {} : {}", actionName,transactionMessage);
		} catch (IOException ioException) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = ioException.getMessage();
			log.error("IOException while running the {} : {}", actionName, transactionMessage);
		} catch (SftpException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("SftpException while running the {} : {}", actionName, transactionMessage);
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("Exception while running the {} : {}", actionName,transactionMessage);
		} finally {
			try {

				if (input != null) {
					input.close();
				}
				if (session != null) {
					session.disconnect();
				}
				if (channel != null) {
					channel.disconnect();
				}

			} catch (IOException e) {
				failureComments.append("IOException while closing the channels  " + e.getMessage()).append("\n");
				log.error("IOException while closing the channels  {}", e.getMessage());
			} catch (Exception e) {
				failureComments.append("Exception while closing the channels  " + e.getMessage()).append("\n");
				log.error("exception while closing the channels  {}", e.getMessage());
			}
			// Storing the current status
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			if (!"".equals(failureComments.toString())) {
				statusObject.put(CommonConstants.COMMENTS, failureComments);
			}

			schedulerStatusObject.add(statusObject);

			if (CommonConstants.TRANSACTION_STATUS_FAILURE.equals(transactionStatus)) {
				schedulerNotificationObj.add("Failed to Initate the " + actionName);

			}

		}
	}

	/**
	 * 
	 * This method is used to load the data into a reference file (on JAROT
	 * server).
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 * @param schedulerMetaData
	 *            : contains metadata(Scheduler metadata loaded from
	 *            LAB_SCHEDULER_PARAMETERS) required for the JOB
	 * @param queryName
	 *            : Sql query variable name. this query will be used to load the
	 *            reference file
	 * @param outputFileName
	 *            : output file variable name. Output file name
	 * @param actionName
	 *            : current activity description
	 * @param recordLength
	 *            : Length variable name. validating the Sql query returned data
	 *            with the input Length.
	 * @param referenceIdPosition
	 *            : if the record length is not matching, will be used to get
	 *            the documentid from the Sql query returned data.
	 * @param dependentSchedulers
	 *            : dependent schedulers variable name, If the current process
	 *            failed need to stop the dependent schedulers
	 * 
	 *            All the variable names will be loaded from the Metadata
	 *            object.
	 * 
	 *            Writing the out File on the JAROT server.
	 * 
	 * 
	 */
	public String loadDataToReferenceFile(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData,
			String queryName, String outputFileName, String actionName, String recordLength, String referenceIdPosition,
			String dependentSchedulers, boolean appendToExistingFile) {

		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		Connection connection = connectionDAO.getConnectionObj();

		Statement st = null;

		ResultSet rs = null;

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		boolean isError = false;

		Session session = null;
		Channel obj_Channel = null;

		ChannelSftp obj_SFTPChannel = null;

		InputStream inputStreamData = null;

		StringBuilder failureComments = new StringBuilder("");

		InputStream input = null;
		try {

			log.error("Started {}", actionName);

			String referenceFilePath = schedulerMetaData.get(outputFileName);

			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Writing data to " + referenceFilePath);

			if (connection != null) {
				st = connection.createStatement();

				log.error("started the process to fetch the data for {}", actionName);

				// getting the Sql query from the Metadata object and executing
				// it.
				rs = st.executeQuery(schedulerMetaData.get(queryName));

				log.error("Got the data from ResultSet");

				config.setProperty("PreferredAuthentications", "password");

				// Loading JAROT details from Metadata object
				session = jsch.getSession(schedulerMetaData.get("JAROT_USERNAME"), schedulerMetaData.get("JAROT_IP"),
						22);

				session.setPassword(CommonUtil.decryptValue(schedulerMetaData.get("JAROT_PASSWORD")));

				session.setConfig(config);

				// Connecting to UNIX JAROT
				session.connect();

				log.error("Connected Jarot prompt to copy the data for {}", actionName);

				obj_Channel = session.openChannel("sftp");
				obj_Channel.connect();

				log.error("connected to SFTP channel to copy to the data");

				obj_SFTPChannel = (ChannelSftp) obj_Channel;

				StringBuilder referenceData = new StringBuilder();
				long i = 0;

				if (!appendToExistingFile) {

					inputStreamData = new ByteArrayInputStream("".getBytes());

					obj_SFTPChannel.put(inputStreamData, referenceFilePath);
				}

				// loading ALLJARO data to out put file
				while (rs.next()) {

					String _returnValue = rs.getString(1);

					int referenceRecordLength = Integer.parseInt(schedulerMetaData.get(recordLength));

					if (_returnValue.length() == referenceRecordLength) {

						referenceData.append(_returnValue).append("\n");

						/*
						 * All the out put files will be in GBs and MBs.
						 * 
						 * Opening the output file stream from JAROT and writing
						 * the data to output file for every 25k records.
						 */

						if (i == 25000) {
							inputStreamData = new ByteArrayInputStream(referenceData.toString().getBytes());

							obj_SFTPChannel.put(inputStreamData, referenceFilePath, 2);

							referenceData = new StringBuilder("");

							i = 0;
						}
						i++;
					} else {

						// If the record length is not matching , tracking the
						// failed documentId
						if (referenceIdPosition != null) {
							String errorMessage = _returnValue
									.substring(Integer.parseInt(schedulerMetaData.get(referenceIdPosition)))
									+ " reference data not in proper format.";
							failureComments.append(errorMessage).append("\n");
							log.error(errorMessage);
						} else {
							String errorMessage = "Data not in proper format.";
							failureComments.append(errorMessage).append("\n");
						}
					}
				}

				// After writing the data for every 25k records, if the count is
				// lessthan 25k
				if (!"".equals(referenceData.toString())) {
					inputStreamData = new ByteArrayInputStream(referenceData.toString().getBytes());

					obj_SFTPChannel.put(inputStreamData, referenceFilePath, 2);

					referenceData = new StringBuilder("");
				}

				if (i == 0) {
					isError = true;
					transactionMessage = "There is no data to write it to " + referenceFilePath;
					transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
					log.error(transactionMessage);
				} else {
					log.error("written data to {}", referenceFilePath);
					transactionMessage = "Successfully wrote data to " + referenceFilePath;
				}

			} else {
				isError = true;
				transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
				transactionMessage = "Unable to get the datasource connection. Stopped the " + actionName;
			}
		} catch (SQLException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("{} : SQLException while writing data :  {}",actionName, transactionMessage);
		} catch (JSchException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("{} : JSchException while writing data : {}",actionName, transactionMessage);
		} catch (SftpException e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("{} : SftpException while writing data :  {}",actionName, transactionMessage);
		} catch (Exception e) {
			isError = true;
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error("{} : Exception while writing data :  {}",actionName, transactionMessage);
		} finally {

			try {
				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.exit();
				}
				if (obj_Channel != null) {
					obj_Channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				if (inputStreamData != null) {
					inputStreamData.close();
				}
				if (input != null) {
					input.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (st != null) {
					st.close();
				}

				if (rs != null) {
					rs.close();
				}

				if (isError) {

					// Pausing the dependent schedulers
					if (dependentSchedulers != null) {

						String[] dependentSchedulerFileNames = schedulerMetaData.get(dependentSchedulers).split(",");

						failureComments.append("Pausing the dependent schedulers  " + dependentSchedulerFileNames)
								.append("\n");
						for (String schedulerName : dependentSchedulerFileNames) {
							updateSchedulerMetaData(schedulerName, "N", null);
						}

					}

				}

			} catch (SQLException e) {
				failureComments.append("SQLException while closing the channels and sessions  " + e.getMessage())
						.append("\n");
				log.error("SQLException while closing the channels and sessions  {}", e.getMessage());
			} catch (IOException e) {
				failureComments.append("IOException while closing the channels and sessions  " + e.getMessage())
						.append("\n");
				log.error("IOException while closing the channels and sessions  {}", e.getMessage());
			} catch (Exception e) {
				failureComments.append("Exception while closing the channels and sessions  " + e.getMessage())
						.append("\n");
				log.error("Exception while closing the channels and sessions  {}", e.getMessage());
			}
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		if (!"".equals(failureComments.toString())) {
			statusObject.put(CommonConstants.COMMENTS, failureComments);
		}

		schedulerStatusObject.add(statusObject);

		return transactionStatus;
	}

	/**
	 * 
	 * This method is used to store the Document match results data(Standadized
	 * Person, Provider and Facility details) to STD tables
	 * 
	 * @param documentMatchData
	 *            : Which holds the standardized data
	 */
	public int storeDocumentMatchResultsToStandardTable(Map<String, Object> documentMatchData) {

		if (documentMatchData.get("documentType") != null) {
			String documentType = (String) documentMatchData.get("documentType");

			/*
			 * Storing of the data will change depends on the table
			 * 
			 * For few Document types, it will have Person, provider and
			 * Facility data For few document types it might not have Provider
			 * and Facility data
			 * 
			 * For every document type we need to addd new condition
			 */
			if ("HIV_LAB".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToHIVStandardTable(documentMatchData);
			} else if ("STD_LAB".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToSTDStandardTable(documentMatchData);
			} else if ("PRF".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToPRFStandardTable(documentMatchData);
			} else if ("NYC_PRF".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToNYCPRFStandardTable(documentMatchData);
			} else if ("AIRS".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToAIRSStandardTable(documentMatchData);
			} else if ("CCD".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToCCDStandardTable(documentMatchData);
			} else if ("ADAP".equals(documentType)) {

				/*
				 * For ADAP three provider types will be there Prescribed,
				 * Certified and PCP
				 * 
				 * flagChosenProvider column will decide in which Provider type
				 * we need to populate the Quality stage standardized provider
				 * information.
				 * 
				 * flagChosenProvider will decide based on the City. If the City
				 * is "New York" then that Provider type ("RX", "PCP", "CERT")
				 * will be updated in the column
				 */
				String flagChosenProvider = schedulerJobsDAO.getFlagChoseProviderForADAP(documentMatchData);

				if ("CERT".equals(flagChosenProvider)) {

					documentMatchData.put("PROV_ADDRESS", "CERT_PROV_ADDRESS");
					documentMatchData.put("PROV_CENSUS_TRACT", "CERT_PROV_CENSUS_TRACT");
					documentMatchData.put("PROV_CITY", "CERT_PROV_CITY");
					documentMatchData.put("PROV_COUNTY", "CERT_PROV_COUNTY");
					documentMatchData.put("PROV_LATITUDE", "CERT_PROV_LATITUDE");
					documentMatchData.put("PROV_LONGITUDE", "CERT_PROV_LONGITUDE");
					documentMatchData.put("PROV_PLUS4", "CERT_PROV_PLUS4");
					documentMatchData.put("PROV_STATE", "CERT_PROV_STATE");
					documentMatchData.put("PROV_ZIP", "CERT_PROV_ZIP");
				} else if ("PCP".equals(flagChosenProvider)) {
					documentMatchData.put("PROV_ADDRESS", "PCP_PROV_ADDRESS");
					documentMatchData.put("PROV_CENSUS_TRACT", "PROV_CENSUS_TRACT_PCP");
					documentMatchData.put("PROV_CITY", "PCP_PROV_CITY");
					documentMatchData.put("PROV_COUNTY", "PCP_PROV_COUNTY");
					documentMatchData.put("PROV_LATITUDE", "PCP_PROV_LATITUDE");
					documentMatchData.put("PROV_LONGITUDE", "PCP_PROV_LONGITUDE");
					documentMatchData.put("PROV_PLUS4", "PCP_PROV_PLUS4");
					documentMatchData.put("PROV_STATE", "PCP_PROV_STATE");
					documentMatchData.put("PROV_ZIP", "PCP_PROV_ZIP");
				} else {
					documentMatchData.put("PROV_ADDRESS", "RX_PROV_ADDRESS");
					documentMatchData.put("PROV_CENSUS_TRACT", "RX_PROV_CENSUS_TRACT");
					documentMatchData.put("PROV_CITY", "RX_PROV_CITY");
					documentMatchData.put("PROV_COUNTY", "RX_PROV_COUNTY");
					documentMatchData.put("PROV_LATITUDE", "RX_PROV_LATITUDE");
					documentMatchData.put("PROV_LONGITUDE", "RX_PROV_LONGITUDE");
					documentMatchData.put("PROV_PLUS4", "RX_PROV_PLUS4");
					documentMatchData.put("PROV_STATE", "RX_PROV_STATE");
					documentMatchData.put("PROV_ZIP", "RX_PROV_ZIP");
				}

				return schedulerJobsDAO.storeDocumentMatchResultsToADAPStandardTable(documentMatchData);

			} else if ("DIN_DOCCS".equals(documentType)) {

				String underCustody = schedulerJobsDAO.getUnderCustodyForDinDoccs(documentMatchData);

				if ("Y".equals(underCustody)) {

					documentMatchData.put("FACILITY_ADDRESS", "CURRENT_FACILITY_ADDRESS");
					documentMatchData.put("FACILITY_CENSUS_TRACT", "CURRENT_FACILITY_CENSUS_TRACT");
					documentMatchData.put("FACILITY_CITY", "CURRENT_FACILITY_CITY");
					documentMatchData.put("FACILITY_COUNTY", "CURRENT_FACILITY_COUNTY");
					documentMatchData.put("FACILITY_LATITUDE", "CURRENT_FACILITY_LATITUDE");
					documentMatchData.put("FACILITY_LONGITUDE", "CURRENT_FACILITY_LONGITUDE");
					documentMatchData.put("FACILITY_PLUS4", "CURRENT_FACILITY_PLUS4");
					documentMatchData.put("FACILITY_STATE", "CURRENT_FACILITY_STATE");
					documentMatchData.put("FACILITY_ZIP", "CURRENT_FACILITY_ZIP");
				} else {
					documentMatchData.put("FACILITY_ADDRESS", "OWNING_FACILITY_ADDRESS");
					documentMatchData.put("FACILITY_CENSUS_TRACT", "OWNING_FACILITY_CENSUS_TRACT");
					documentMatchData.put("FACILITY_CITY", "OWNING_FACILITY_CITY");
					documentMatchData.put("FACILITY_COUNTY", "OWNING_FACILITY_COUNTY");
					documentMatchData.put("FACILITY_LATITUDE", "OWNING_FACILITY_LATITUDE");
					documentMatchData.put("FACILITY_LONGITUDE", "OWNING_FACILITY_LONGITUDE");
					documentMatchData.put("FACILITY_PLUS4", "OWNING_FACILITY_PLUS4");
					documentMatchData.put("FACILITY_STATE", "OWNING_FACILITY_STATE");
					documentMatchData.put("FACILITY_ZIP", "OWNING_FACILITY_ZIP");
				}

				return schedulerJobsDAO.storeDocumentMatchResultsToDinDoccsStandardTable(documentMatchData);

			} else if ("MED".equals(documentType) || "MEDICAID".equals(documentType) || "INS".equals(documentType)
					|| "INSURANCE".equals(documentType)) {

				// For medicaid Provider information will be in a different
				// table.
				schedulerJobsDAO.storeDocumentMatchResultsToMEDProvidersStandardTable(documentMatchData);
				return schedulerJobsDAO.storeDocumentMatchResultsToMEDStandardTable(documentMatchData);
			} else if ("DTH".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToDEATHStandardTable(documentMatchData);
			} else if ("NYC_DTH".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToNycDEATHStandardTable(documentMatchData);
			} else if ("EPRF_OOC".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToEPRFOOCStandardTable(documentMatchData);
			} else if ("CASA".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToCASAStandardTable(documentMatchData);
			} else if ("SSDM".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToSSDMStandardTable(documentMatchData);
			} else if ("NYC_IJR".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToNycIJRStdTable(documentMatchData);
			} else if ("TB".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToTBStdTable(documentMatchData);
			} else if ("SYPH".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToSYPHStdTable(documentMatchData);
			} else if ("STD_CONF".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToSTDConfStdTable(documentMatchData);
			} else if ("ICCR".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToICCRStdTable(documentMatchData);
			}else if ("HL7".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToHL7StdTable(documentMatchData);
			}else if ("COPHI".equals(documentType)) {
				return schedulerJobsDAO.storeDocumentMatchResultsToCOPHIStdTable(documentMatchData);
			}else if ("BTH_MOM".equals(documentType) || "BTH_INF".equals(documentType)) {
				return quartzSchedulerTransactionHelper.storeDocumentMatchResultsToBirthTables(documentMatchData, documentType);
			}else {
				return 0;
			}

		}

		return 0;
	}

	private int printResult(InputStream input, Channel channel) throws Exception {
		int SIZE = 1024;
		byte[] tmp = new byte[SIZE];
		while (true) {
			while (input.available() > 0) {
				int i = input.read(tmp, 0, SIZE);
				if (i < 0)
					break;
				log.debug(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				log.error("exit-status: {}", channel.getExitStatus());
				break;
			}
			try {
				Thread.sleep(300);
			} catch (Exception ee) {
			}
		}
		return channel.getExitStatus();
	}

	public void getNewDocId(Map<Object, Object> map) {
		if (log.isDebugEnabled()) {
			log.debug(" Getting New Doc ID...");
		}
		documentDAO.getNewDocId(map);

		log.debug(" After Getting New Doc ID call, Doc ID is: " + map.get("p_new_doc_id"));

		if ((Integer) map.get("p_return_code") == -1) {
			throw new DataRetrievalFailureException((String) map.get("p_msg_out"));
		}
	}

	public void sendDuplicatedDocumentStatus(Map<String, String> schedulerMetaData) {

		List<MailStatusVO> duplicationObject = schedulerJobsDAO.getDocumentDuplicatedStatus();

		StringBuilder messageContent = new StringBuilder();

		for (MailStatusVO mailStatusVO : duplicationObject) {

			int totalCount = mailStatusVO.getTotalCount();

			messageContent
					.append("<table style='width:500px;margin-top: 20px' border><thead>	<tr style='background-color: #3399FF'>	<td> File Name:	<b>"
							+ mailStatusVO.getFileName() + "	</b></td>	<td> Total: <b>" + totalCount
							+ "	</b></td></tr></thead></table>");

			messageContent.append(
					"<table border='1' style='margin-left:50px; width:450px'><thead><tr style='background-color: #99CCFF'><td> LOINC:	</td>	<td> Total Duplicates	</td><td> Percent Duplicates	</td></tr></thead><tbody>");

			List<JSONObject> loincCodeData = mailStatusVO.getLoincCodeData();
			for (Map<Object, Object> loincCode : loincCodeData) {

				int duplicatedRecordsCount = Integer.parseInt(loincCode.get("duplicationStatus").toString());

				DecimalFormat df = new DecimalFormat("#.##");

				messageContent.append("<tr><td>" + loincCode.get("loincCode") + "</td><td>" + duplicatedRecordsCount
						+ "</td><td>" + df.format(duplicatedRecordsCount * 100.0 / totalCount) + "</td></tr></tbody>");
			}

			messageContent.append("</table>");

		}

		sendNotificationEmail(schedulerMetaData, schedulerMetaData.get("STAGE") + " : Duplication status",
				messageContent.toString(), CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
	}

	/**
	 * Formats the "schedulerStatusObject" into HTML, configures an email
	 * message with the HTML as the body, and calls another method to further
	 * configure the email and send it off.
	 * 
	 * @param schedulerStatusObject
	 *            A map-like object containing the names of the processing steps
	 *            just completed, and a status message describing the outcome of
	 *            the step as the corresponding value.
	 * @param subject
	 *            Subject line of the email
	 * @param schedulerMetaData
	 *            Contents of LAB_SCHEDULER_PARAMETERS in a
	 *            {@link java.util.Map}
	 */
	public void sendSchedulerDetailedProcessStatus(List<JSONObject> schedulerStatusObject, String subject,
			Map<String, String> schedulerMetaData) {

		StringBuilder mailContent = new StringBuilder("");

		
		boolean hasFailedDocuments = false;
		
		StringBuilder failedDocumentsContent = new StringBuilder("<table border='1' width='700px;' >");
		
		for (JSONObject statusObject : schedulerStatusObject) {

			Set<String> keyset = statusObject.keySet();
			mailContent.append("<table border='1' width='700px;' >");

			for (String keyname : keyset) {
				mailContent.append("<tr><td style='background-color: #3399FF'>");
				mailContent.append(keyname);
				mailContent.append("</td><td>");
				mailContent.append(statusObject.get(keyname));
				mailContent.append("</td></tr>");
				
				
				
				if(CommonConstants.FAILED_DOCUMENTS.equals(keyname)){
					
					hasFailedDocuments = true;
					
					failedDocumentsContent.append("<tr><td style='background-color: #3399FF'>");
					failedDocumentsContent.append(statusObject.get(keyname));
					failedDocumentsContent.append("</td></tr>");
					
				}
				
				
			}
			mailContent.append("</table><br/><br/><br/>");
			

		}
		failedDocumentsContent.append("</table><br/><br/><br/>");
		
		
		
		
		
		sendNotificationEmail(schedulerMetaData, subject, mailContent.toString(),
				CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		
		if(hasFailedDocuments){
			sendNotificationEmail(schedulerMetaData, schedulerMetaData.get("STAGE")+" : Documents failed in Nightly job", failedDocumentsContent.toString(),
					CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		}

	}

	/**
	 * Sends an email to the recipients in the LAB_SCHEDULER_PARAMETERS table
	 * NOTIFICATIONS_USERS_TO parameter
	 * 
	 * @param schedulerMetaData
	 *            Contents of LAB_SCHEDULER_PARAMETERS from which to extract the
	 *            recipient list and other values
	 * @param subject
	 *            The email "Subject"
	 * @param mailContent
	 *            The email content
	 * @param responseType
	 *            Not currently used, "text/html" is hard-coded
	 */

	public void sendNotificationEmail(Map<String, String> schedulerMetaData, String subject, String mailContent,
			String responseType) {

		try {
			//	sendEmail.sendSSLMessage(recipientEmailAddresses, subject.toString(), mailContent.toString(),
			sendEmail.processEmail(null, subject, mailContent, "NIGHTLY_JOB", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
		} catch (MessagingException messagingException) {
			//log.error(subject + "  :   " + mailContent.toString() + " with error: " + );
			log.error(subject + "  :  {} with error : {}",mailContent.toString(), messagingException.toString());
		}
	}

	/**
	 * This method will call the RAW_DOCUMENT_MATCH_SUMMARY stored procedure
	 * 
	 * Summary stored procedure will read the data from the
	 * RAW_DOCUMENT_MATCH_RESULTS table to RAW_DOCUMENT_MATCH_SUMMARY.
	 * 
	 * Procedure will use one algorithm to process the 8 passes. That Algorithm
	 * will decide that document is a match or Not a Mtach or Clerical review.
	 * and writes the document and its final status code to Match_summary table.
	 * 
	 * 
	 * For Matches : procedure will update the Match flag, matched stateno,
	 * MATCH_PASS, MATCH_WEIGHT in STD table and add the entries in DOC
	 * attribute tables For Non Match : Process will create a new stateno and
	 * update the Non Match flag, new stateno, MATCH_PASS, MATCH_WEIGHT in STD
	 * table and add the entries in DOC attribute tables For Clerical : User
	 * manually match the document or non match the document.
	 * 
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc) return SUCCESS/FAILURE
	 */
	public String loadDocumentMatchSummary(List<JSONObject> schedulerStatusObject) {

		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		try {
			schedulerJobsDAO.loadDocumentMatchSummary();
			transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
			transactionMessage = "Successfully initiated the stored procedure";
		} catch (DataAccessException dataAccessException) {
			transactionMessage = "Exception occured while calling the Match summary stored procedure "
					+ dataAccessException.getMessage();
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			log.error(transactionMessage);
		} catch (Exception exception) {
			transactionMessage = "Exception occured while calling the Match summary stored procedure "
					+ exception.getMessage();
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			log.error(transactionMessage);
		}

		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Calling Document match summary Stored procedure.");

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

		return transactionStatus;
	}

	/**
	 * This method is used to validate the HIV record
	 * 
	 * While validating the record first two conditions 1) Checking CLIA and PFI
	 * combination in the database 2)Validating the HIV record against the
	 * Filter type conidtions
	 * 
	 * If the document is failing these two validations then its a hard stop. We
	 * will not do the remaining validations. update the Raw table with
	 * respective status and loads the error into ERROR tables
	 * 
	 * else the document will be validated against all the validations.
	 * 
	 * 
	 * If the document is failing the any validation then it will update Raw
	 * document with VALIDATION FAILED status (4) and loads the ERROR tables
	 * 
	 * If the document passes all the validations it will move the record to
	 * Standard layer with "5" current_process_Status
	 * 
	 * 
	 * 
	 * There is soft check validation: For the first time validation only we
	 * will log the Error, if the same document is coming for validation again
	 * (Once you reprise the document, it will be validate again) then will
	 * ignore thosse soft check errors (Example: First name, last name contains
	 * invalid characters, Collection date to create date is over 60 days etc)
	 * 
	 * 
	 * If the document failed the validation
	 * 
	 * @param ecrlsLabDocVO
	 *            : hiv document
	 * @param failedToValidateDocuments
	 *            : if we unable to validate the document against all the
	 *            validations (Any exception while doing this method), this
	 *            document will be added to the list
	 * @param schedulerMetaData
	 * @return
	 */
	public boolean handleRawEclrsRecordValidations(EcrlsLabDocVO ecrlsLabDocVO, StringBuilder failedToValidateDocuments,
			Map<String, String> schedulerMetaData) {
		boolean transactionStatus = false;
		List<String> transactionMessage = new ArrayList<>();
		// boolean isError = false;
		try {
			transactionStatus = quartzSchedulerTransactionHelper.handleRawEclrsRecordValidations(ecrlsLabDocVO,
					failedToValidateDocuments, schedulerMetaData);

		} catch (DataAccessException e) {
			transactionMessage.add(ecrlsLabDocVO.getDOCUMENT_ID()
					+ " DataAccessException while validating the HIV record :  " + e.getMessage());
			failedToValidateDocuments.append(transactionMessage.toString());
			log.error(transactionMessage.toString());
			transactionStatus = false;
			// isError = true;
		} catch (Exception e) {
			transactionMessage.add(
					ecrlsLabDocVO.getDOCUMENT_ID() + " Exception while validating the HIV record :  " + e.getMessage());
			failedToValidateDocuments.append(transactionMessage.toString());
			log.error(transactionMessage.toString());
			transactionStatus = false;
			// isError = true;
		}
		// if (isError) {
		// addValidationError(
		// ecrlsLabDocVO.getDOCUMENT_ID(),
		// transactionMessage,
		// CommonConstants.RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS,
		// false, ecrlsLabDocVO.getCURRENT_PROCESS_STATUS(), "HIV_LAB");
		// writeErrorCode(
		// CommonConstants.RAW_RECORD_VALIDATION_ERRORS_PROCESS_STATUS,
		return transactionStatus;
	}

	/**
	 * Returns the contents of the LAB_SCHEDULER_PARAMETERS table.
	 * 
	 * @return a {@link java.util.Map} object with PARAMTER_NAME values as the
	 *         keys and corresponding PARAMTER_VALUE values as the map values.
	 */
	public Map<String, String> getSchedulerMetaData() {

		Map<String, String> schedulerMetaData = new TreeMap<>();

		String stage = System.getProperty("stage");

		if ("LOCAL".equals(stage)
				&& "Y".equals(EnvironmentProperties.envProperties.getProperty("loadSchedulersDataFromProperty"))) {

			try {

				InputStream inputStream = getClass().getClassLoader()
						.getResourceAsStream("SchedulerMetaData.properties");

				ResourceBundle resources = new PropertyResourceBundle(inputStream);

				Enumeration<String> keys = resources.getKeys();

				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					schedulerMetaData.put(key, resources.getString(key));
				}

			} catch (IOException e) {
				log.error("Failed to load the Scheduler metadata property  :", e);
			}

		} else {
			List<Map<String, String>> metadataList = schedulerJobsDAO.getSchedulerMetaData();

			for (Map<String, String> metaData : metadataList) {
				schedulerMetaData.put(metaData.get("PARAMTER_NAME"), metaData.get("PARAMTER_VALUE"));
			}
		}

		return schedulerMetaData;
	}

	/**
	 * This method is used to Achieve the RAW_DOCUMENT_MATCH_RESULTS table
	 * 
	 * RAW_DOCUMENT_MATCH_RESULTS table will hold latest Quality stage match
	 * results data.
	 * 
	 * For Match : Process will update the Match flag, matched stateno in STD
	 * table and add the entries in DOC attribute tables For Non Match : Process
	 * will create a new stateno and update the Non Match flag, new stateno in
	 * STD table and add the entries in DOC attribute tables For Clerical : User
	 * manually match the document or non match the document.
	 * 
	 * If any left over clerical documents are there then scheduler will process
	 * those documents again (Quality stage might match or not match with the
	 * reference ALLJARO data, if it still clerical then user has to this
	 * manually.)
	 * 
	 * 
	 * Job will load the same documents again, to avoid conflicts every time it
	 * will archieve and stores the new data
	 * 
	 * @param schedulerStatusObject
	 *            : track the each and every activity status (Success/Failure
	 *            documents processed , documents failed, comments, messages
	 *            etc)
	 */
//	@Override
//			// Calling the scheduler to Archieve the Match results data
//			transactionMessage = "Data Access Exception while archeiving the match results data "

	/**
	 * Performs the POST-MATCH processing
	 * <ol>
	 * <li>Retrieves and processes the results of the PERSON match
	 * <ol>
	 * <li>Checks and rechecks the Quality Stage match on JAROT until it is one
	 * of SUCCESS or FAILED.</li>
	 * <li>If SUCCESS, retrieves the two files containing the results of the
	 * match.</li>
	 * <li>Processes the contents of the two files into the database tables</li>
	 * </ol>
	 * </li>
	 * <li>Uses Quality Stage to perform FACILITY and PROVIDER standardization
	 * <ol>
	 * <li>Writes unstandardized FACILITY and PROVIDER info to JAROT</li>
	 * <li>Initiates the FACILITY and PROVIDER QS processes</li>
	 * <li>When they are done, retrieves the results</li>
	 * <li>Stores the results into the various DB tables</li>
	 * </ol>
	 * </li>
	 * </ol>
	 */
	public String loadQualityStageDataJob(List<JSONObject> schedulerStatusObject, Map<String, String> schedulerMetaData,
			String jobName, boolean finalStageOfLoadingResults, List<String> schedulerNotificationObj,
			boolean needToArchive, List<Map<String, Object>> nonCaseDocumentDetails) {

				String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;


				//sendNotificationEmailToUsers(schedulerMetaData, schedulerNotificationObj);
				
				
				List<Map<String, String>> dataFluxQSMatchSummary = schedulerJobsDAO.getDataFluxQSMatchSummary();
				
				
				List<Map<String, String>> adhocDataFluxStatusList = schedulerJobsDAO.getDataFluxDetailedStatus("ER_ADHOC_CYCLE");
				
				List<Map<String, String>> dailyJobDataFluxStatusList = schedulerJobsDAO.getDataFluxDetailedStatus("ER_CYCLE");
				
				
				sendDatafluxQualityStageSummaryDetails(adhocDataFluxStatusList, dailyJobDataFluxStatusList, dataFluxQSMatchSummary, schedulerMetaData);
				
				if ("Y".equals(schedulerMetaData.get("DATABASE_PROC_EMAIL_STATUS"))) {
					
					List<Map<String, Object>> assignmentList = schedulerJobsDAO.getAssignmentDetailedStatus();
					commonService.sendStatusSummary(assignmentList, "ASSIGNMENT_STATUS_EMAIL_HEADERS", "ASSIGNMENT_STATUS_EMAIL_COLUMNS", "ASSIGNMENT", "Assignment Report",  null, "ASSIGNMENT_NO_DATA");
					
					List<Map<String, Object>> ahiReportList = schedulerJobsDAO.getAhiReportList();
					commonService.sendStatusSummary(ahiReportList, "AHI_STATUS_EMAIL_HEADERS", "AHI_STATUS_EMAIL_COLUMNS", "AHI", "AHI Report",  null, "AHI_NO_DATA");
					
					List<Map<String, Object>> psMessageReport = schedulerJobsDAO.getPSMessageReport();
					commonService.sendStatusSummary(psMessageReport, "PS_MESSAGE_EMAIL_HEADERS", "PS_MESSAGE_EMAIL_COLUMNS", "PS_MESSAGE", "PS Message Report",  null, "PS_MSG_NO_DATA");
	
					List<Map<String, Object>> psMessageNYCReport = schedulerJobsDAO.getPSMessageNYCReport();
					commonService.sendStatusSummary(psMessageNYCReport, "PS_MESSAGE_NYC_EMAIL_HEADERS", "PS_MESSAGE_NYC_EMAIL_COLUMNS", "PS_NYC_PROV_FAC", "PS NYC Report",  "List of PS messages with NYC Provider/Facility", "PS_MESSAGE_NYC_NO_DATA");
				}

		return transactionStatus;
	}

//	private void sendNonCaseDocumentDetails(
//			sendEmail.sendSSLMessage(recipientEmailAddresses, "New Documents match to a person with an active non_case document", mailContent.toString(),
	
	
	private void sendDatafluxQualityStageSummaryDetails(List<Map<String, String>> adhocDataFluxStatusList, List<Map<String, String>> dailyJobDataFluxStatusList,
			List<Map<String, String>> dataFluxQualitStageSummary, Map<String, String> schedulerMetaData) {
		StringBuilder mailContent = new StringBuilder("");
		
		mailContent.append("<span style='font-weight:bold'>Match Summary</span>");
		
		
		mailContent.append("<table border='1' width='500px;' >");
		mailContent.append("<tr><th>Doc Type</th><th>Match code</th><th>Count</th></tr>");
		for (Map<String, String> summary : dataFluxQualitStageSummary) {
			mailContent.append("<tr><td>");
			mailContent.append(summary.get("DOC_TYPE"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("MATCH_CD"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("DATA_FLUX"));
			mailContent.append("</td></tr>");
			
		}

		mailContent.append("</table><br/><br/><br/>");

		
		mailContent.append("<span style='font-weight:bold'>ADHOC job detailed process</span>");
		
		mailContent.append("<table border='1' width='800px;' >");
		mailContent.append("<tr><th>JOB_TYPE</th><th>JOB_DESC</th><th>Start Date</th><th>End Date</th><th>Status</th></tr>");
		for (Map<String, String> summary : adhocDataFluxStatusList) {
			mailContent.append("<tr><td>");
			mailContent.append("ADHOC Job");
			mailContent.append("</td><td>");
			mailContent.append(summary.get("JOB_DESC"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("EXEC_START_DATE"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("EXEC_END_DATE"));
			mailContent.append("</td><td>");
			
			String _status = summary.get("STATUS");
			
			if(CommonUtil.isNotNullAndEmpty(_status) && "PASSED".equals(_status)) {
				mailContent.append(summary.get("STATUS"));
			}else {
				mailContent.append(summary.get("ERROR_DESC"));
			}
			
			
			
			mailContent.append("</td></tr>");
			
		}

		mailContent.append("</table><br/><br/><br/>");
		
		
		mailContent.append("<span style='font-weight:bold'>Daily nightly job detailed process</span>");
		
		mailContent.append("<table border='1' width='800px;' >");
		mailContent.append("<tr><th>JOB_ID</th><th>JOB_DESC</th><th>Start Date</th><th>End Date</th><th>Status</th></tr>");
		for (Map<String, String> summary : dailyJobDataFluxStatusList) {
			mailContent.append("<tr><td>");
			mailContent.append("Daily Job");
			mailContent.append("</td><td>");
			mailContent.append(summary.get("JOB_DESC"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("EXEC_START_DATE"));
			mailContent.append("</td><td>");
			mailContent.append(summary.get("EXEC_END_DATE"));
			mailContent.append("</td><td>");
			String _status = summary.get("STATUS");
			
			if(CommonUtil.isNotNullAndEmpty(_status) && "PASSED".equals(_status)) {
				mailContent.append(summary.get("STATUS"));
			}else {
				mailContent.append(summary.get("ERROR_DESC"));
			}
			mailContent.append("</td></tr>");
			
		}

		mailContent.append("</table><br/><br/><br/>");
		
		
		try {
			
			sendEmail.processEmail(null, "Today's DATAFLUX match summary", mailContent.toString(), "NIGHTLY_JOB", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			
		} catch (MessagingException e) {
			log.error(mailContent.toString());

			log.error("failed to send the DATAFLUX match summary to Users ", e);

		}
		
	}

	/*Section ENDS*/
	public void sendNotificationEmailToUsers(Map<String, String> schedulerMetaData,
			List<String> schedulerNotificationObj) {

		StringBuilder mailContent = new StringBuilder("");

		mailContent.append("<table border='1' width='500px;' >");

		for (String status : schedulerNotificationObj) {

			mailContent.append("<tr><td>");
			mailContent.append(status);
			mailContent.append("</td></tr>");

		}

		mailContent.append("</table><br/><br/><br/>");

		//String[] recipientEmailAddresses = schedulerMetaData.get("NOTIFICATIONS_USERS_TO").split(",");

		try {
			//sendEmail.sendSSLMessage(recipientEmailAddresses, "Record Processing Summary", mailContent.toString(),
			//		schedulerMetaData.get("NOTIFICATIONS_FROM"), null, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			sendEmail.processEmail(null, "Record Processing Summary", mailContent.toString(), "NIGHTLY_JOB", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);		
		} catch (MessagingException e) {
			log.error(mailContent.toString());

			log.error("failed to send the Notification to Users ", e);

		}

	}

	public int deleteChildDocumentsFromMatchResults(List<String> duplicateDocumentIds) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("duplicateDocumentIds", duplicateDocumentIds);

		int resultCount = schedulerJobsDAO.deleteChildDocumentsFromMatchResults(inputOutputParameterMap);

		return resultCount;

	}

	public int disableFlagInErrorTable(int _documentID) {
		return schedulerJobsDAO.disableFlagInErrorTable(_documentID);
	}

	public void updateSchedulerMetaData(String parameterName, String parameterValue, BindingResult bindingResult) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		if ("JAROT_PASSWORD".equals(parameterName)) {
			try {
				parameterValue = CommonUtil.encryptValue(parameterValue);
			} catch (UnsupportedEncodingException e) {
				log.error("Exception occured while encrypting the value  {}", e.getMessage());
				if (bindingResult != null) {
					commonExceptionHandler.inspectDBTransacionResult(-1,
							"Unable to update the Meta data. please contact helpline.", bindingResult);
				}

				return;
			}
		}
		inputOutputParameterMap.put("parameterName", parameterName);
		inputOutputParameterMap.put("parameterValue", parameterValue);

		int statusCode = schedulerJobsDAO.updateSchedulerMetaData(inputOutputParameterMap);

		if (bindingResult != null) {
			if (statusCode > 0) {
				commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the meta data. ",
						bindingResult);
			} else {
				commonExceptionHandler.inspectDBTransacionResult(-1,
						"Unable to update the Meta data. please contact helpline.", bindingResult);
			}
		}

	}

	public String getMetaDataValue(String parameterName) {
		return schedulerJobsDAO.getMetaDataValue(parameterName);
	}

	public void loadClericalReviewRecordsForQSProcess(List<JSONObject> schedulerStatusObject, String recordType) {

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		JSONObject statusObject = new JSONObject();

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		try {

			if ("DOCCS".equals(recordType)) {
				recordType = "DIN_DOCCS";
			}
			inputOutputParameterMap.put("tableName", LabCommonUtils.getStdTableName(recordType));

			int resultCount = schedulerJobsDAO.loadClericalReviewRecordsForQSProcess(inputOutputParameterMap);

			if (resultCount > 0) {
				transactionMessage = "Successfully changed the process status for " + resultCount
						+ " Clerical review records";
			} else {
				transactionMessage = "There is no Clerical review records exist in the standard table to load it for Quality stage process";
			}

		} catch (DataAccessException e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error(
					"Unable to change the process status of Standard Clerical review records :  {}", transactionMessage);
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = e.getMessage();
			log.error(
					"Unable to change the process status of Standard Clerical review records :  {}", transactionMessage);
		}

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Loading Standard Clerical review records part of Quality stage process");
		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

	}

//	@Override

	public String checkMultiStepDocumentsInRawHIVRecords(List<JSONObject> schedulerStatusObject, String recordType) {

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;
		boolean isError = false;

		JSONObject statusObject = new JSONObject();

		try {

			statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Started checking multi step documents");

			transactionStatus = quartzSchedulerTransactionHelper.checkAndUpdateMultiStepHIVDocuments(recordType,
					schedulerStatusObject, statusObject);
		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while checking the multistep documents : " + e.getMessage();
			log.error(transactionMessage);
			isError = true;
		}
		if (isError) {
			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			schedulerStatusObject.add(statusObject);
		}

		return transactionStatus;
	}

	public int checkForReferenceLoincDocumentsCount(MultiStepProcessVO multiStepProcessVO) {

		int statusCode = 0;
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		inputOutputParameterMap.put("pfi", multiStepProcessVO.getPfi());
		inputOutputParameterMap.put("loincCode", multiStepProcessVO.getLoincCode());

		int documentsCount = schedulerJobsDAO.checkForReferenceLoincDocumentsCount(inputOutputParameterMap);

		if (documentsCount != 0 && (documentsCount == multiStepProcessVO.getDocumentIds().size())) {
			statusCode = 1;
		} else if (documentsCount != 0 && (documentsCount != multiStepProcessVO.getDocumentIds().size())) {
			statusCode = -1;
		}

		return statusCode;
	}

	public int changeDocumentsStatusToMultiStepCompressFlag() {

		return schedulerJobsDAO.changeDocumentsStatusToMultiStepCompressFlag();

	}
	
	public int removeMultiProcessStatus() {
		

		try {
			
			Map<String, String> schedulerMetaData = getSchedulerMetaData();
			
			String  STD_NON_MATCHES_ROLLBACK_QUERY = schedulerMetaData.get("SKIP_MULTI_PROCESS_STATUS");
			
			Map<String, String> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("sqlQuery", STD_NON_MATCHES_ROLLBACK_QUERY);
			
			return schedulerJobsDAO.removeMultiProcessStatus(inputParameterMap);
			
			
		} catch (Exception e) {
			
			log.error("Exception occured while skipping Multi step process status {}", e.getMessage());
						
			UserObject userObject = new UserObject();
			
			userObject.setUserId("NYEHMS");
			
			commonExceptionHandler.handleException(e, userObject, null);
			
		}

		return 0;

	}
	
	
	

	public List<MultiStepDocumentsVO> getMultiStepProcessDocumentDetails() {

		return schedulerJobsDAO.getMultiStepDocuments();

	}

	public EcrlsLabDocVO getUpdatedMultiProcessDocument(List<EcrlsLabDocVO> multiStepProcessDocuments,
			int retainDocumentId, List<String> errors, int geeniusRecordsCount, String pfi) {

		EcrlsLabDocVO ecrlsLabDocVO1 = null;

		String loincCode1 = null;
		String resultCode1 = null;

		String loincCode2 = null;
		String resultCode2 = null;

		String loincCode3 = null;
		String resultCode3 = null;

		boolean isError = false;
		StringBuilder errorMessage = new StringBuilder("");
		try {
			
			
			
			
			if (geeniusRecordsCount == 0) {

				
				if (multiStepProcessDocuments.size() == 2) {

					for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepProcessDocuments) {

						if (retainDocumentId == _ecrlsLabDocVO.getDOCUMENT_ID()) {
							ecrlsLabDocVO1 = _ecrlsLabDocVO;
						}

						String _loincCode = _ecrlsLabDocVO.getOBXLOINCCODE();

						if ("29893-5".equals(_loincCode)) {
							loincCode1 = _loincCode;

							resultCode1 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
									errors);

						} else if ("30361-0".equals(_loincCode)) {
							loincCode2 = _loincCode;

							resultCode2 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
									errors);

						}
					}

					if (resultCode1 == null || resultCode2 == null) {
						return null;
					}

					if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
							&& "30361-0".equals(loincCode2)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
							&& "30361-0".equals(loincCode2)
							&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("UNDIFFERENTIATED");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
							&& "30361-0".equals(loincCode2)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("NEG");
					} else if ("29893-5".equals(loincCode1)
							&& CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode1)
							&& "30361-0".equals(loincCode2)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("IND");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_NO_RESULTS.equals(resultCode1)
							&& "30361-0".equals(loincCode2) && CommonConstants.STD_NO_RESULTS.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("NO RESULT");
					} else if ("29893-5".equals(loincCode1)
							&& (CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
									|| CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode1))
							&& "30361-0".equals(loincCode2)
							&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2");
					} else {
						errors.add("Unable to derive the test result from multi spot documents");
						return null;
					}
				} else if (multiStepProcessDocuments.size() == 3) {
					ecrlsLabDocVO1 = multiStepProcessDocuments.get(0);

					for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepProcessDocuments) {
						String _loincCode = _ecrlsLabDocVO.getOBXLOINCCODE();

						if ("29893-5".equals(_loincCode)) {
							loincCode1 = _loincCode;

							resultCode1 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
									errors);

						} else if ("40438-4".equals(_loincCode)) {
							loincCode2 = _loincCode;

							resultCode2 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
									errors);

						} else if ("30361-0".equals(_loincCode)) {
							loincCode3 = _loincCode;

							resultCode3 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
									errors);

						}
					}

					if (resultCode1 == null || resultCode2 == null || resultCode3 == null) {
						return null;
					}

					if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1");
					} else if ("29893-5".equals(loincCode1)
							&& (CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
									|| CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1))
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("UNDIFFERENTIATED");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("IND");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("IND");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("NEG");
					} else if ("29893-5".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
							&& "40438-4".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)
							&& "30361-0".equals(loincCode3)
							&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode3)) {
						ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2");
					} else {
						errors.add("Unable to derive the test result from multi spot documents");
						return null;
					}

				} else {
					errors.add("Multi step document was not implemented for group size : "
							+ multiStepProcessDocuments.size());
					return null;
				}

				ecrlsLabDocVO1.setOBXLOINCCODE("69668-2");
				ecrlsLabDocVO1
						.setOBXLOINCDESCSHORT("HIV 1 and 2 Ab [Identifier] in Serum or Plasma by Rapid immunoassay");
			} else if (geeniusRecordsCount > 0 && multiStepProcessDocuments.size() == 2 && "2502".equals(pfi)) {
				for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepProcessDocuments) {
					if (retainDocumentId == _ecrlsLabDocVO.getDOCUMENT_ID()) {
						ecrlsLabDocVO1 = _ecrlsLabDocVO;
					}

					String _loincCode = _ecrlsLabDocVO.getOBXLOINCCODE();

					if ("25835-0".equals(_loincCode)) {
						loincCode1 = _loincCode;

						resultCode1 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
								errors);

					} else if ("69353-1".equals(_loincCode)) {
						loincCode2 = _loincCode;

						resultCode2 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO,
								errors);

					}
				}
				
				if (resultCode1 == null || resultCode2 == null) {
					return null;
				}

				if ("25835-0".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
						&& "69353-1".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1 AND HIV-2 POSITIVE");
				} else if ("25835-0".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
						&& "69353-1".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1 POSITIVE");
				} else if ("25835-0".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
						&& "69353-1".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2 POSITIVE");
				} else if ("25835-0".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
						&& "69353-1".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV NEGATIVE");
				} else {
					errors.add("Unable to derive the test result from multi spot documents");
					return null;
				}
				
				ecrlsLabDocVO1.setOBXLOINCCODE("96556-6");
				
				return ecrlsLabDocVO1;
				
				
			} else {
				ecrlsLabDocVO1 = multiStepProcessDocuments.get(0);

				for (EcrlsLabDocVO _ecrlsLabDocVO : multiStepProcessDocuments) {
					String _loincCode = _ecrlsLabDocVO.getOBXLOINCCODE();

					if ("68961-2".equals(_loincCode)) {
						loincCode1 = _loincCode;

						resultCode1 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO, errors);

					} else if ("81641-3".equals(_loincCode)) {
						loincCode2 = _loincCode;

						resultCode2 = quartzSchedulerTransactionHelper.getResultStandardCode(_ecrlsLabDocVO, errors);

					}
				}

				if (resultCode1 == null || resultCode2 == null) {
					return null;
				}

				if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1 POSITIVE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV POSITIVE UNTYPABLE (UNDIFFERENTIATED)");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV NEGATIVE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode1)
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1 INDETERMINATE");
				} else if ("68961-2".equals(loincCode1)
						&& (CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
								|| CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode1))
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2 POSITIVE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode1)
						&& "81641-3".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV INDETERMINATE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_NEGATIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2 INDETERMINATE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2)
						&& CommonConstants.STD_RESULTS_INDETERMINATE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-1 POSITIVE");
				} else if ("68961-2".equals(loincCode1) && CommonConstants.STD_RESULTS_REACTIVE.equals(resultCode1)
						&& "81641-3".equals(loincCode2) && CommonConstants.STD_RESULTS_POSITIVE.equals(resultCode2)) {
					ecrlsLabDocVO1.setOBXOBSERVATIONRESULTTEXT("HIV-2 POSITIVE WITH HIV-1 CROSS-REACTIVITY");
				} else {
					errors.add("Unable to derive the test result from Geenius documents");
					return null;
				}

				ecrlsLabDocVO1.setOBXLOINCCODE("80203-3");
				ecrlsLabDocVO1
						.setOBXLOINCDESCSHORT("HIV 1 and 2 Ab [Identifier] in Serum or Plasma by Rapid immunoassay");
			}
		} catch (DataAccessException dataAccessException) {
			isError = true;
			log.error(dataAccessException.getMessage());

		} catch (Exception exception) {
			isError = true;
			log.error(exception.getMessage());
		}

		if (isError) {

			errorMessage.append("Exception occured while grouping ");

			if (geeniusRecordsCount == 0) {
				errorMessage.append("Multistep documents ");
			} else {
				errorMessage.append("Geenius documents ");
			}
			for (EcrlsLabDocVO ecrlsLabDocVO : multiStepProcessDocuments) {
				errorMessage.append(ecrlsLabDocVO.getDOCUMENT_ID()).append(",");
			}

			errors.add(errorMessage.toString());
			return null;

		}
		return ecrlsLabDocVO1;
	}

	public int associateParentDocumentForMultiStepDocuments(List<Integer> multiStepDocumentIds, int parentDocumentId,
			String flagStatus) {
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("documentIds", multiStepDocumentIds);
		inputOutputParameterMap.put("statusCode", flagStatus);
		inputOutputParameterMap.put("parentDocumentId", parentDocumentId);

		int resultsCount = schedulerJobsDAO.associateParentDocumentForMultiStepDocuments(inputOutputParameterMap);

		return resultsCount;

	}

	public String generateHashNumberForNewRecords(String recordType, List<JSONObject> schedulerStatusObject,
			String duplicateColumns) {

		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Updating hashnumber for new Raw " + recordType + " records");
		try {

			Map<Object, Object> inputOutputParameterMap = new HashMap<>();

			String tableName = LabCommonUtils.getRawTableName(recordType);

			inputOutputParameterMap.put("tableName", tableName);
			inputOutputParameterMap.put("duplicateColumns", duplicateColumns);

			int resultCount = schedulerJobsDAO.generateHashNumberForNewRecords(inputOutputParameterMap);

			if (resultCount == 0) {
				transactionMessage = "There is no new Documents to genreate Hash Unique number";
			} else {
				transactionMessage = "Generated Hashnumber for " + resultCount + " new Raw " + recordType + " records.";
			}

		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while Generating the Hashnumber : " + e.getMessage();
			log.error(transactionMessage);
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

		return transactionStatus;

	}

	public String checkDuplicatesinRawDocTable(String recordType, List<JSONObject> schedulerStatusObject) {

		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Checking the duplicates for RAW " + recordType + " Document");

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("p_doc_type_cd", recordType);
		inputOutputParameterMap.put("process_date", new Date(Calendar.getInstance().getTimeInMillis()));

		schedulerJobsDAO.checkDuplicatesinRawDocTable(inputOutputParameterMap);

		Integer statusCode = (Integer) inputOutputParameterMap.get("p_code_out");

		if (statusCode == 0) {
			transactionMessage = "Successfully formed the Duplicate process";
		} else {
			transactionMessage = "Failed to perform the Duplicate process";
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

		return transactionStatus;

	}

	public String checkingDuplicatesInNewRawHIVRecords(String recordType, List<JSONObject> schedulerStatusObject) {

		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"Checking the duplicates in the new RAW " + recordType + " List");
		try {
			Map<Object, Object> inputOutputParameterMap = new HashMap<>();

			inputOutputParameterMap.put("tableName", LabCommonUtils.getRawTableName(recordType));

			int resultCount = schedulerJobsDAO.checkingDuplicatesInNewRawHIVRecords(inputOutputParameterMap);

			if (resultCount == 0) {
				transactionMessage = "There is no Duplicates in the new Raw " + recordType
						+ " List. Its a unique record set";
			} else {
				transactionMessage = "Total " + resultCount + " new Raw " + recordType + " Records found as Duplicate";
			}

		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while checking the Duplicates in the new Raw " + recordType
					+ " List : " + e.getMessage();
			log.error(transactionMessage);
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

		return transactionStatus;

	}

	public String checkingDuplicatesInEntireDataWithNewRawHIVData(String recordType,
			List<JSONObject> schedulerStatusObject) {
		JSONObject statusObject = new JSONObject();

		String transactionStatus = CommonConstants.TRANSACTION_STATUS_SUCCESS;
		String transactionMessage = null;

		statusObject.put(CommonConstants.STATUS_ACTION_NAME,
				"checking the duplicates of New Raw " + recordType + " documents List with the Entire Raw Data");
		try {

			Map<Object, Object> inputOutputParameterMap = new HashMap<>();

			inputOutputParameterMap.put("tableName", LabCommonUtils.getRawTableName(recordType));

			int resultCount = schedulerJobsDAO.checkingDuplicatesInEntireDataWithNewRawHIVData(inputOutputParameterMap);

			if (resultCount == 0) {
				transactionMessage = "There is no Duplicates in the new Raw " + recordType + " List.";
			} else {
				transactionMessage = "Total " + resultCount + " new Raw " + recordType + " Records found as Duplicate";
			}

		} catch (Exception e) {
			transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			transactionMessage = "Exception occured while checking the Duplicates of New Raw " + recordType
					+ " documents List with the Entire Raw Data : " + e.getMessage();
			log.error(transactionMessage);
		}

		statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

		schedulerStatusObject.add(statusObject);

		return transactionStatus;
	}

//	@Override

	public boolean isSchedulerRanToday(String schedulerName, List<JSONObject> schedulerStatusObject, String processName,
			Map<String, String> schedulerMetaData) {

		boolean isSchedulerRanToday = true;
		try {
			int resultCount = schedulerJobsDAO.isSchedulerRanToday(schedulerName);
			if (resultCount == 0) {
				isSchedulerRanToday = false;
			}
		} catch (Exception e) {
//			String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			String transactionMessage = "Unable to verify the  Last run data for " + schedulerName + ".  Error : " + e.getMessage();


			log.error(transactionMessage);
		}

		return isSchedulerRanToday;
	}

	public boolean isSchedulerRanYesterday(String schedulerName, List<JSONObject> schedulerStatusObject,
			String processName, Map<String, String> schedulerMetaData) {
		boolean isSchedulerRanYesterday = true;
		try {
			int resultCount = schedulerJobsDAO.isSchedulerRanYesterday(schedulerName);
			if (resultCount == 0) {
				isSchedulerRanYesterday = false;
			}
		} catch (Exception e) {
			String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			String transactionMessage = "Unable to start the " + processName + " scheduler  " + e.getMessage();

			JSONObject statusObject = new JSONObject();

			statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);

			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);

			schedulerStatusObject.add(statusObject);

			sendSchedulerDetailedProcessStatus(schedulerStatusObject, processName, schedulerMetaData);

			log.error(transactionMessage);
		}

		return isSchedulerRanYesterday;
	}

	public boolean updateSchedulerRanDate(String schedulerName, List<JSONObject> schedulerStatusObject,
			String processName, Map<String, String> schedulerMetaData, String dependentSchedulers) {
		boolean schudulerCanProceed = false;
		try {
			int resultCount = schedulerJobsDAO.updateSchedulerRanDate(schedulerName);
			if (resultCount > 0) {
				schudulerCanProceed = true;
			}
		} catch (Exception e) {
			String transactionStatus = CommonConstants.TRANSACTION_STATUS_FAILURE;
			String transactionMessage = "Unable to update the " + processName
					+ " scheduler processed time. Stopping the Scheduler " + e.getMessage();

			
			if(schedulerStatusObject != null) {
				JSONObject statusObject = new JSONObject();
	
				statusObject.put(CommonConstants.TRANSACTION_STATUS, transactionStatus);
	
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
	
				if (dependentSchedulers != null) {
	
					String[] dependentSchedulerFileNames = schedulerMetaData.get(dependentSchedulers).split(",");
	
					statusObject.put(CommonConstants.COMMENTS,
							"Pausing the dependent Schedulers " + schedulerMetaData.get(dependentSchedulers));
	
					for (String _dependentSchedulerName : dependentSchedulerFileNames) {
						updateSchedulerMetaData(_dependentSchedulerName, "N", null);
					}
	
				}
	
				schedulerStatusObject.add(statusObject);
	
				sendSchedulerDetailedProcessStatus(schedulerStatusObject, processName, schedulerMetaData);
			}
			log.error(transactionMessage);
		}

		return schudulerCanProceed;
	}
	
	private void verifyAndUpdateAddressFields(String countyValue,
			String stateValue, String cityValue, String zipValue,
			Map<String, Object> documentMatchResults, String countyVarName,
			String stateVarName, String cityVarName, String latitude, String longitude, String censusTract,
			Map<String, Map<String, String>> zipCodeReferenceMap) {
		
		Map<String, String> zipCodeReferenceData = zipCodeReferenceMap.get(zipValue);
		
		if (zipCodeReferenceData != null && (CommonUtil.isNullOrEmpty(countyValue) 
				|| CommonUtil.isNullOrEmpty(stateValue) 
					|| CommonUtil.isNullOrEmpty(cityValue) 
				)) {
			
			
			countyValue = zipCodeReferenceData.get("COUNTY_FIPS_CD");
			stateValue = zipCodeReferenceData.get("STATE_CD");
			cityValue = zipCodeReferenceData.get("CITY");
			
		}else if(zipCodeReferenceData != null && (zipCodeReferenceData.get("CITY").equals(cityValue)  &&  !zipCodeReferenceData.get("STATE_CD").equals(stateValue))){
			

			countyValue = zipCodeReferenceData.get("COUNTY_FIPS_CD");
			stateValue = zipCodeReferenceData.get("STATE_CD");
			cityValue = zipCodeReferenceData.get("CITY");
			
			documentMatchResults.put(latitude, "");
			documentMatchResults.put(longitude, "");
			documentMatchResults.put(censusTract, "");
		}
		
		if (CommonUtil.isNullOrEmpty(stateValue)) {
			countyValue = "999";
		} else if (!"NY".equals(stateValue)) {
			countyValue = "998";
		}else if(CommonUtil.isNullOrEmpty(countyValue)) {
			countyValue = "999";
		}
		

		documentMatchResults.put(countyVarName, countyValue);
		documentMatchResults.put(stateVarName, stateValue);
		documentMatchResults.put(cityVarName, cityValue);
		
	}

	
	// STD_STD_table
	public int writeRawStdRecordDataToStdTable(EcrlsLabDocVO ecrlsLabDocVO) {

		EcrlsStdDocVO ecrlsStdDocVO = new EcrlsStdDocVO();

		
		String firstName = ecrlsLabDocVO.getPIDFIRSTNAME();
		String lastName = ecrlsLabDocVO.getPIDLASTNAME();
		ecrlsStdDocVO.setFNAME(CommonUtil.NameValidation(firstName));
		ecrlsStdDocVO.setMNAME(CommonUtil.NameValidation(ecrlsLabDocVO.getPIDMIDDLENAME()));
		ecrlsStdDocVO.setLNAME(CommonUtil.NameValidation(lastName));
		ecrlsStdDocVO.setPFI(ecrlsLabDocVO.getMSHLABPFI());
		
		
		if((CommonUtil.isNullOrEmpty(firstName) || "unknown".equalsIgnoreCase(firstName.trim())) && (CommonUtil.isNullOrEmpty(lastName) || "unknown".equalsIgnoreCase(lastName.trim()))){
			int resultCount = writeErrorCode(CommonConstants.STOPPED_AT_RAW_NOT_HAVING_SUFFICIENT_INFO, ecrlsLabDocVO.getDOCUMENT_ID(), "STD_LAB");
			
			if(resultCount > 0){
				throw new DataRetrievalFailureException("Document has invalid name");
			}
			
		}
		
		
		
		String pfi = ecrlsLabDocVO.getMSHLABPFI();

		ecrlsLabDocVO.setMSHLABPFI(pfi);
		ecrlsStdDocVO.setDOCUMENT_ID(ecrlsLabDocVO.getDOCUMENT_ID());
		ecrlsStdDocVO.setLAB_SRC_ID(ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY());
		ecrlsStdDocVO.setSUFFIX(ecrlsLabDocVO.getPIDNAMESUFFIX());

		Calendar PIDDATEOFBIRTHCalender = null;

		if (ecrlsLabDocVO.getPIDDATEOFBIRTH() != null) {

			PIDDATEOFBIRTHCalender = CommonUtil.resetTimeToZero(ecrlsLabDocVO.getPIDDATEOFBIRTH(), 1900);

		}
		if (PIDDATEOFBIRTHCalender == null && CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getPIDDATEOFBIRTHRAW())) {

			Timestamp pidDateOfBirthRawValue = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getPIDDATEOFBIRTHRAW());

			if (pidDateOfBirthRawValue != null) {

				PIDDATEOFBIRTHCalender = CommonUtil.resetTimeToZero(pidDateOfBirthRawValue, 1900);
			}

		}

		if (PIDDATEOFBIRTHCalender != null) {
			ecrlsLabDocVO.setDerivedDateOfBirth(PIDDATEOFBIRTHCalender);
			ecrlsStdDocVO.setDOB_TEXT(CommonUtil.convertCalenderToString(PIDDATEOFBIRTHCalender));
		}

		ecrlsStdDocVO.setDIN(CommonUtil.dinValidation(ecrlsLabDocVO.getPIDDIN()));
		ecrlsStdDocVO.setNYSID(CommonUtil.nysidValidation(ecrlsLabDocVO.getPIDNYSID()));
		ecrlsStdDocVO.setSSN(CommonUtil.formatSSNValue(ecrlsLabDocVO.getPIDSSN()));
		ecrlsStdDocVO.setBIRTH_COUNTRY(ecrlsLabDocVO.getPIDCOUNTRYOFORIGIN());
		ecrlsStdDocVO.setPERSON_ADDRESS(CommonUtil
				.addressValidation(ecrlsLabDocVO.getPIDADDRESSLINE1() + " " + ecrlsLabDocVO.getPIDADDRESSLINE2()));
		ecrlsStdDocVO.setPERSON_CITY(ecrlsLabDocVO.getPIDCITY());
		ecrlsStdDocVO.setPERSON_STATE(
				commonDocumentService.validateState(ecrlsLabDocVO.getPIDSTATE().trim()));

		ecrlsStdDocVO.setPERSON_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getPIDZIPCODE()));

		ecrlsStdDocVO.setPERSON_PHONE(ecrlsLabDocVO.getPIDHOMEPHONE());
		ecrlsStdDocVO.setPERSON_COUNTRY(ecrlsLabDocVO.getPIDCOUNTRYCODE());
		handleRaceEthnicityValidationForStdDocument(ecrlsLabDocVO, ecrlsStdDocVO);
		ecrlsStdDocVO.setMED_RECORD_NUMBER(CommonUtil.normalizeString(ecrlsLabDocVO.getPIDMEDICALRECORDNUMBER()));
		ecrlsStdDocVO.setINSURANCE_CD(ecrlsLabDocVO.getPIDTYPEOFINSURANCE());
		ecrlsStdDocVO.setINSURANCE_NUMBER(ecrlsLabDocVO.getPIDPATIENTACCOUNTNUM());
		ecrlsStdDocVO.setPROV_FNAME(ecrlsLabDocVO.getOBRPROVIDERFIRSTNAME());
		ecrlsStdDocVO.setPROV_MNAME(ecrlsLabDocVO.getOBRPROVIDERMIDDLENAME());
		ecrlsStdDocVO.setPROV_LNAME(ecrlsLabDocVO.getOBRPROVIDERLASTNAME());
		ecrlsStdDocVO.setPROV_SUFFIX(ecrlsLabDocVO.getOBRPROVIDERNAMESUFFIX());
		ecrlsStdDocVO.setPROV_ADDRESS(
				ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE1() + " " + ecrlsLabDocVO.getZLRPROVIDERADDRESSLINE2());
		ecrlsStdDocVO.setPROV_CITY(ecrlsLabDocVO.getZLRPROVIDERCITY());

		ecrlsStdDocVO.setPROV_STATE(commonDocumentService.validateState(ecrlsLabDocVO.getZLRPROVIDERSTATE().trim()));

		ecrlsStdDocVO.setPROV_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getZLRPROVIDERZIPCODE()));
		// STD table

		ecrlsStdDocVO.setPROV_PHONE(ecrlsLabDocVO.getOBRCALLBACKPHONE());
		
		ecrlsStdDocVO.setPROV_NPI(CommonUtil.formatNumbericField(ecrlsLabDocVO.getOBRPROVIDERID(), 10));
		ecrlsStdDocVO.setFACILITY_NPI(CommonUtil.formatNumbericField(ecrlsLabDocVO.getZLRFACILITYCODE(), 10));
		
		
		ecrlsStdDocVO.setFACILITY_NAME(ecrlsLabDocVO.getZLRFACILITYNAME());
		ecrlsStdDocVO.setFACILITY_ADDRESS(
				ecrlsLabDocVO.getZLRFACILITYADDRESSLINE1() + " " + ecrlsLabDocVO.getZLRFACILITYADDRESSLINE2());
		ecrlsStdDocVO.setFACILITY_CITY(ecrlsLabDocVO.getZLRFACILITYCITY());

		ecrlsStdDocVO.setFACILITY_STATE(commonDocumentService.validateState(ecrlsLabDocVO.getZLRFACILITYSTATE().trim()));

		ecrlsStdDocVO.setFACILITY_ZIP(CommonUtil.formatZipCode(ecrlsLabDocVO.getZLRFACILITYZIPCODE()));

		ecrlsStdDocVO.setFACILITY_PHONE(ecrlsLabDocVO.getZLRFACILITYPHONE());

		Calendar _derivedCollectionDateCalender = null;

		if (ecrlsLabDocVO.getOBRCOLLECTIONDATE() != null) {
			_derivedCollectionDateCalender = CommonUtil.resetTimeToZero(ecrlsLabDocVO.getOBRCOLLECTIONDATE(), 1970);
		}

		if (_derivedCollectionDateCalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBRCOLLECTIONDATERAW())) {
			Timestamp collectionDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBRCOLLECTIONDATERAW());

			if (collectionDateRaw != null) {

				_derivedCollectionDateCalender = CommonUtil.resetTimeToZero(collectionDateRaw, 1970);
			}
		}

		if (_derivedCollectionDateCalender != null) {
			ecrlsStdDocVO.setCOLLECTION_DATE(new Date(_derivedCollectionDateCalender.getTimeInMillis()));
		}

		Calendar OBXOBSERVATIONDATECalender = null;

		if (ecrlsLabDocVO.getOBXOBSERVATIONDATE() != null) {

			OBXOBSERVATIONDATECalender = CommonUtil.resetTimeToZero(ecrlsLabDocVO.getOBXOBSERVATIONDATE(), 1970);

		}
		if (OBXOBSERVATIONDATECalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBXOBSERVATIONDATERAW())) {
			Timestamp observationDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBXOBSERVATIONDATERAW());

			if (observationDateRaw != null) {

				OBXOBSERVATIONDATECalender = CommonUtil.resetTimeToZero(observationDateRaw, 1970);
			}
		}

		if (OBXOBSERVATIONDATECalender != null) {
			ecrlsStdDocVO.setOBSERVATION_DATE(new Date(OBXOBSERVATIONDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = OBXOBSERVATIONDATECalender;
		}

		Calendar OBRRESULTREPORTDATECalender = null;

		if (ecrlsLabDocVO.getOBRRESULTREPORTDATE() != null) {

			OBRRESULTREPORTDATECalender = CommonUtil.resetTimeToZero(ecrlsLabDocVO.getOBRRESULTREPORTDATE(), 1970);
		}

		if (OBRRESULTREPORTDATECalender == null
				&& CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBRRESULTREPORTDATERAW())) {

			Timestamp resultReportDateRaw = schedulerJobsDAO
					.getDateValueFromString(ecrlsLabDocVO.getOBRRESULTREPORTDATERAW());

			if (resultReportDateRaw != null) {

				OBRRESULTREPORTDATECalender = CommonUtil.resetTimeToZero(resultReportDateRaw, 1970);
			}
		}

		if (OBRRESULTREPORTDATECalender != null) {
			ecrlsStdDocVO.setRESULT_REPORT_DATE(new Date(OBRRESULTREPORTDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = OBRRESULTREPORTDATECalender;
		}

		Timestamp MSHCREATEDATE = ecrlsLabDocVO.getMSHCREATEDATE();

		Calendar MSHCREATEDATECalender = null;
		if (MSHCREATEDATE != null) {
			MSHCREATEDATECalender = CommonUtil.resetTimeToZero(MSHCREATEDATE, 1970);
		}

		if (MSHCREATEDATECalender != null) {
			ecrlsStdDocVO.setDATE_RECEIVED(new Date(MSHCREATEDATECalender.getTimeInMillis()));
		}

		if (_derivedCollectionDateCalender == null) {
			_derivedCollectionDateCalender = MSHCREATEDATECalender;
		}

		java.sql.Date derivedCollectionDate = new Date(_derivedCollectionDateCalender.getTimeInMillis());

		ecrlsStdDocVO.setCALC_COLLECTION_DATE(derivedCollectionDate);

		ecrlsStdDocVO.setLOINC(ecrlsLabDocVO.getOBXLOINCCODE());
		ecrlsStdDocVO.setLOINC_DESC(ecrlsLabDocVO.getOBXLOINCDESC());//sampatjh
		
		if(CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT())){
			ecrlsStdDocVO.setTEST_RESULT(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXT());
		}else if(CommonUtil.isNotNullAndEmpty(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXTSHORT())) {
			ecrlsStdDocVO.setTEST_RESULT(ecrlsLabDocVO.getOBXOBSERVATIONRESULTTEXTSHORT());
		}
		
		// ecrlsStdDocVO.setINTERPRET_CD(ecrlsLabDocVO);
		ecrlsStdDocVO.setUNIT_MEASURE_CODE(ecrlsLabDocVO.getOBXUNITOFMEASURE());
		ecrlsStdDocVO.setPERSON_INTERNAL_ID(ecrlsLabDocVO.getPIDPATIENTIDINTERNAL());
		ecrlsStdDocVO.setPERSON_EXTERNAL_ID(ecrlsLabDocVO.getPIDPATIENTIDEXTERNAL());
		ecrlsStdDocVO.setSENDING_FACILITY_NAME(ecrlsLabDocVO.getMSHSENDINGFACILITYNAME());
		ecrlsStdDocVO.setSPECIMEN_SOURCE_CD(ecrlsLabDocVO.getOBRSPECIMENSOURCECODE());
		ecrlsStdDocVO.setSPECIMEN_SOURCE_NAME(ecrlsLabDocVO.getOBRSPECIMENSOURCENAME());
		ecrlsStdDocVO.setREFERRING_FACILITY(ecrlsLabDocVO.getPIDFACILITYIDEXTERNAL());
		ecrlsStdDocVO.setTESTING_FACILITY(ecrlsLabDocVO.getPIDFACILITYIDINTERNAL());

		ecrlsStdDocVO.setHIV_VACCINE_RECIP(ecrlsLabDocVO.getPIDVACCINETRIALIND());
		ecrlsStdDocVO.setLENGTH_RESIDENCY_IN_USA(ecrlsLabDocVO.getPIDLENGTHOFRESIDENCY());
		ecrlsStdDocVO.setSENDING_FACILITY_CLIA(ecrlsLabDocVO.getMSHSENDINGFACILITYCLIA());
		// ecrlsStdDocVO.setDATE_SENT(ecrlsLabDocVO);
		ecrlsStdDocVO.setACCESSION_NUMBER(ecrlsLabDocVO.getOBRACCESSIONNUM());

		ecrlsStdDocVO.setMEDICAID_NUMBER(CommonUtil.checkMedicaidNumberFormat(ecrlsLabDocVO.getPIDMEDICAIDNUMBER()));

		ecrlsStdDocVO.setLOCAL_TEST_CODE(ecrlsLabDocVO.getOBXLOCALTESTCODE());
		ecrlsStdDocVO.setLOCAL_TEST_DESC(ecrlsLabDocVO.getOBXLOCALTESTDESC());

		ecrlsStdDocVO.setCREATE_UID("NYEHMS");
		// ecrlsStdDocVO.setCREATE_DATE(new Date());


		// ecrlsStdDocVO.setAGE_CATEGORY(ecrlsLabDocVO);//where to calculate

		ecrlsStdDocVO.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		// ecrlsStdDocVO.setMATCHED_STATENO

		Float _ageValue = calculateAgeValue(ecrlsLabDocVO);

		Integer std_age = null;

		if (_ageValue != null) {
			if (_ageValue < 1) {
				std_age = 0;
			} else if (_ageValue < 13) {
				std_age = 1;
			} else {
				std_age = 3;
			}

			ecrlsStdDocVO.setAGE(_ageValue.intValue());
			ecrlsStdDocVO.setAGE_CATEGORY(std_age);
		}

		int resultCount = schedulerJobsDAO.writeRawStdRecordDataToStdTable(ecrlsStdDocVO);

		return resultCount;
	}

	public int updateTheRawStdRecordToStandardize(int document_ID) {
		return schedulerJobsDAO.updateTheRawStdRecordToStandardize(document_ID);
	}

	public int updateTheRawRecordToStandardize(BigDecimal document_ID, String documentType) {

		String rawTableName = LabCommonUtils.getRawTableName(documentType);

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("rawTableName", rawTableName);
		inputOutputParameterMap.put("documentId", document_ID);

		return schedulerJobsDAO.updateTheRawRecordToStandardize(inputOutputParameterMap);
	}

	public int deleteDocumentInErrorTable(int currentDocumentId, String requestComments, int processStatus,
			UserObject userObject) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("documentID", currentDocumentId);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		inputOutputParameterMap.put("requestComments", requestComments);
		inputOutputParameterMap.put("processStatus", processStatus);

		return schedulerJobsDAO.deleteDocumentInErrorTable(inputOutputParameterMap);
	}

	/**
	 * We have Filter criteria, where we will add all the matching patterns for
	 * "RESEARCH", "PROFICIENCY", "INVALID VALUES", "DONOR", "INVALID PROVIDER",
	 * "INVALID FACILITY" and "REFERRAL LAB" Filter criteria's
	 * 
	 * The matching patterns will be like below
	 * 
	 * 1)FIRST NAME LIKE 'XXX' (FILTER_CRITERIA = 'RESEARCH') 2)FIRST NAME LIKE
	 * 'XXX' and FACILITY_NAME = 'TEST FACILITY' (FILTER_CRITERIA = 'DONOR')
	 * 
	 * If any document is matching with this pattern, the document will fail the
	 * validation and add the Error message to NYEHMS_PROCESS_ERROR.
	 * 
	 * 
	 * Only : PROFICIENCY is a hard stop. If any document is matching with
	 * "PROFICIENCY" filter type criteria list, will stop validating the
	 * document
	 * 
	 * If it is not PROFICIENCY then will continue with the remaining
	 * validations.
	 * 
	 * 
	 * Generally will add these conditions against to the PFIs.
	 * 
	 * There are some universal conditions that we need to validate against all
	 * the documents irrespective of PFIs.
	 * 
	 * Currently we have hundreds of conditions are there that we need to
	 * validate against all the documents.
	 * 
	 * Instead of preparing the default conditions (Preparing the conditions to
	 * SQL format) every time, preparing this default criteria once and storing
	 * in meta data table and using it for all the documents
	 * 
	 * 
	 * @param schedulerMetaData
	 */
	public void prepareDefaultProcessQueryConditions(Map<String, String> schedulerMetaData) {

		Map<Object, Object> inputOutputParamters = new HashMap<>();

		List<String> pfiList = new ArrayList<>();

		pfiList.add("-999");

		inputOutputParamters.put("pfiList", pfiList);

		List<Integer> filterTypes = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		inputOutputParamters.put("filterTypes", filterTypes);

		/*
		 * Getting the Default Conditions by the Filter type
		 * 
		 * [1 :[Proficiency all the default (-999) conditions], 2= [RESEARCH all
		 * the default (-999) conditions] ............etc]
		 * 
		 */

		List<FilterTypeAndFilterConditionsVO> filterTypeAndFilterConditions = labProcessDAO
				.getFilterConditionsByFilterTypes(inputOutputParamters);

		for (FilterTypeAndFilterConditionsVO filterTypeAndFilterConditionsVO : filterTypeAndFilterConditions) {

			int filterType = filterTypeAndFilterConditionsVO.getFILTER_TYPE();
			List<FilterConditionsVO> filterConditions = filterTypeAndFilterConditionsVO.getFilterConditions();
			// preparing the SQL query where condition
			String _sqlQueryString = prepareLabProcessQueryCondition(filterConditions);
			if (CommonUtil.isNotNullAndEmpty(_sqlQueryString)) {
				// Storing all the default filter type conditions in to Meta
				// data object
				schedulerMetaData.put("Default_PFI_Query_String_" + filterType, _sqlQueryString);
			}
		}
	}

	/**
	 * This method concatenates all the individual conditions into one where
	 * condition. The output will be as follows: <br>
	 * (FIRST NAME like '%TEST FIRST NAME%') or (FIRST NAME like '%FIRST%' and
	 * FACILITY_NAME = 'TEST FACILITY')<br>
	 * or (PROVIDER_NAME like 'PROV%')<br>
	 * This will return the complete where condition for the filter type
	 * ("RESEARCH", "PROFICIENCY" ..etc).
	 * 
	 * @param filterConditions
	 * @return
	 */
	private String prepareLabProcessQueryCondition(List<FilterConditionsVO> filterConditions) {

		StringBuilder queryString = new StringBuilder("");
		int _index = 0;

		for (FilterConditionsVO filterConditionsVO : filterConditions) {
			String _currentStringQuery = LabCommonUtils.getQueryCondition(filterConditionsVO.getFilterConditions());
			if (CommonUtil.isNotNullAndEmpty(_currentStringQuery)) {
				if (_index != 0) {
					// All the conditions (Not the individual list) are appended
					// with an OR condition, because if it match with any
					// individual condition we need to fail the document
					queryString.append(" OR ");
				}
				queryString.append(" (").append(_currentStringQuery).append(") ");
			}
			_index++;
		}
		return queryString.toString();
	}

	public List<Map<String, Object>> getHivErrorsByCategoryAndCount() {

		return schedulerJobsDAO.getHivErrorsByCategoryAndCount();

	}
	
	
	public List<EcrlsLabDocVO> getPIDUpdatedRecordsFromEclrs(String recordType,
			Timestamp lAST_PIDUPDATEDATE, long lastInsertedMaxObxObservationResultKey, long currentMaxObxObservationResultKey){
	
		Map<Object, Object> inOutParameterMap = new HashMap<>();

		inOutParameterMap.put("lastInsertedId", lastInsertedMaxObxObservationResultKey);
		inOutParameterMap.put("lastPidUpdateDate", lAST_PIDUPDATEDATE);

		if ("STD_LAB".equals(recordType)) {
			inOutParameterMap.put("sourceTable", "valls");
		} else {
			inOutParameterMap.put("sourceTable", "vallh");
		}

		List<EcrlsLabDocVO> rawEcrlsLabDocList = eclrsDataDAO.getPIDUpdatedRecordsFromEclrs(inOutParameterMap);

		Timestamp maxPidUpdateDate = null;
		
		if (rawEcrlsLabDocList.size() > 0) {

			maxPidUpdateDate = rawEcrlsLabDocList.get(0).getMaxPIDUPDATEDATE();
		}
		
		
		if(maxPidUpdateDate == null){
			maxPidUpdateDate = lAST_PIDUPDATEDATE;
		}

		inOutParameterMap.put("maxOBXOBSERVATIONRESULTKEY", currentMaxObxObservationResultKey);
		inOutParameterMap.put("maxPidUpdateDate", maxPidUpdateDate);
		inOutParameterMap.put("recordType", recordType);

		schedulerJobsDAO.updateSchedulerLogWithPidUpdateDate(inOutParameterMap);

			// //throw an exception
			// }



		return rawEcrlsLabDocList;
	}
	
	public List<Map<String, Object>> getNyehmsDocumentCurrentStatus(String recordType, long obxObservationResultKey){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("RAW_TABLE", LabCommonUtils.getRawTableNameFast(recordType));
		
		inputParameterMap.put("STD_TABLE", LabCommonUtils.getStdTableName(recordType));
		
		inputParameterMap.put("obxObservationResultKey", obxObservationResultKey);
		
		return schedulerJobsDAO.getNyehmsDocumentCurrentStatus(inputParameterMap);
	}
	
	public void updateRawDocumentData(String recordType, Integer cURRENT_PROCESS_STATUS_RAW, Map<String, Object> rawTableInputParameterMap){
		
		
		rawTableInputParameterMap.put("CURRENT_PROCESS_STATUS", cURRENT_PROCESS_STATUS_RAW);
		
		rawTableInputParameterMap.put("RAW_TABLE", LabCommonUtils.getRawTableNameFast(recordType));
		
		
		int resultCount = schedulerJobsDAO.updatePIDUpdatedDetailsInRaw(rawTableInputParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to update the PID Updated Details in RAW table");
		}
		
		
	}
	
	public void updateStdPidDocumentData(String recordType, Integer cURRENT_PROCESS_STATUS_STD, Map<String, Object> stdTableInputParameterMap){
		
		stdTableInputParameterMap.put("CURRENT_PROCESS_STATUS", cURRENT_PROCESS_STATUS_STD);
		
		stdTableInputParameterMap.put("STD_TABLE", LabCommonUtils.getStdTableName(recordType));
		
		int resultCount = schedulerJobsDAO.updatePIDUpdatedDetailsInStd(stdTableInputParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to update the PID Updated Details in STD table");
		}
		
	}
	
	public void createNewRawDocumentWithUpdatedPIDData(String recordType, 
			Map<String, Object> rawTableInputParameterMap){
		
		
		Map<String, String> schedulerMetaData = getSchedulerMetaData();
		
		if("HIV_LAB".equals(recordType)){
			
			
			
			rawTableInputParameterMap.put("DUPLICATE_COLUMNS", schedulerMetaData.get("HIV_DUPLICATED_COLUMNS"));
			
			int resultCount = schedulerJobsDAO.createNewRawHIVDocumentWithUpdatedPIDData(rawTableInputParameterMap);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create a new RAW HIV Document with the updated PID details");
			}
		}else{
			
			rawTableInputParameterMap.put("DUPLICATE_COLUMNS", schedulerMetaData.get("STD_DUPLICATED_COLUMNS"));
			
			int resultCount = schedulerJobsDAO.createNewRawSTDDocumentWithUpdatedPIDData(rawTableInputParameterMap);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create a new RAW STD Document with the updated PID details");
			}
		}
		
	}

	public void createNewStdDocumentWithUpdatedPIDData(String recordType, 
			Map<String, Object> stdTableInputParameterMap){
		if("HIV_LAB".equals(recordType)){
			int resultCount = schedulerJobsDAO.createNewStdHIVDocumentWithUpdatedPIDData(stdTableInputParameterMap);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create a new STD HIV Document with the updated PID details");
			}
		}else{
			int resultCount = schedulerJobsDAO.createNewStdStdDocumentWithUpdatedPIDData(stdTableInputParameterMap);
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to create a new STD STD Document with the updated PID details");
			}
		}
	}
	
	public String checkPersonAddressGotEdited(String recordType, Integer oldDocumentId,
			Integer documentId){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("OLD_DOCUMENT_ID", oldDocumentId);
		
		inputParameterMap.put("NEW_DOCUMENT_ID", documentId);
		
		inputParameterMap.put("STD_TABLE", LabCommonUtils.getStdTableName(recordType));
		
		return schedulerJobsDAO.checkPersonAddressGotEdited(inputParameterMap);
		
		
	}
	
	public void logPIDProcessError(String recodType, Long obxObservationResultKey,
			String errorMessage){
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		inputParameter.put("obxObservationResultKey", obxObservationResultKey);
		
		inputParameter.put("errorMessage", errorMessage);
		
		inputParameter.put("document_type_code", recodType);
		
		
		int resultCount = schedulerJobsDAO.logPIDProcessError(inputParameter);
		
		
		if(resultCount == 0){
			log.error("Failed to log the PID Update Process error for {}",obxObservationResultKey);
			
			throw new DataRetrievalFailureException("Failed to log the PID Update process error ");
		}
	}
	

	public List<Map<String, Object>> getMultiStepDocumentDetails(
			Integer multiStepDocumentId){
		return schedulerJobsDAO.getMultiStepDocumentDetails(multiStepDocumentId);
	}
	
	public int checkIfDocumentUpdatedWithPidDetails(String recordType,
			Long oBXOBSERVATIONRESULTKEY){
		
		Map<String, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("RAW_TABLE", LabCommonUtils.getRawTableNameFast(recordType));
		
		inputParameterMap.put("obxObservationResultKey", oBXOBSERVATIONRESULTKEY);
		
		return schedulerJobsDAO.checkIfDocumentUpdatedWithPidDetails(inputParameterMap);
		
	}
	
	public void writeEcrlsPidUpdatedRecordsToNyehms(String recordType,
			List<EcrlsLabDocVO> ecrlsLabDocList, String origin,
			List<JSONObject> schedulerStatusObject,
			List<String> schedulerNotificationObj) {
			
		JSONObject statusObject = new JSONObject();
		String transactionMessage = null;		
		List<String> failureDocuments = new ArrayList<>();
		
		List<String> successDocuments = new ArrayList<>();
	
		Iterator<EcrlsLabDocVO> rawEclrsListIterator = ecrlsLabDocList.iterator();
		while (rawEclrsListIterator.hasNext()) {
			EcrlsLabDocVO ecrlsLabDocVO = rawEclrsListIterator.next();
			boolean transactionStatus=false;
			
			String errorMessage = null;
			
			try {
				transactionStatus = quartzSchedulerTransactionHelper.writePIDUpdatedRawEcrlsData(recordType, ecrlsLabDocVO, origin, successDocuments);
			} catch (DataAccessException e) {
				transactionStatus = false;
				errorMessage = e.getMessage();
				transactionMessage = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY()+" :  "+errorMessage;
				failureDocuments.add(transactionMessage);
				log.error("unable to write the {} in NYEHMS Raw table :  {}", origin, transactionMessage);;
			} catch(Exception e){
				transactionStatus = false;
				errorMessage = e.getMessage();
				transactionMessage = ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY()+" :  "+errorMessage;
				failureDocuments.add(transactionMessage);
				log.error("unable to write the {} in NYEHMS Raw table :  {}", origin , transactionMessage);;
			}
			 if(!transactionStatus){
				 
				 logPIDProcessError(recordType, ecrlsLabDocVO.getOBXOBSERVATIONRESULTKEY(), errorMessage);
				 
				 
				 rawEclrsListIterator.remove();
			 }
		}

		if(failureDocuments.size() == 0){
			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
			
			transactionMessage = "Successfully wrote all the PID Updated records to Nyehms database";
			
			statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
			
			log.error(transactionMessage);
			
			schedulerNotificationObj.add(recordType+"  :  Successfully wrote all the PID updated records("+ecrlsLabDocList.size()+") to the NYEHMS Database");
		}else{
			statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_FAILURE);
			if(ecrlsLabDocList.size() == 0){
				transactionMessage = "All Documents("+recordType+") were failed while writing updated PID data to NYEHMS Database";
				log.error(transactionMessage);
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
				schedulerNotificationObj.add(recordType+"  :  All Documents were failed while writing updated PID data to NYEHMS Database");
				
			}else{
				transactionMessage = "Few Documents were failed while writing updated PID data to NYEHMS Database";
				log.error(transactionMessage);
				statusObject.put(CommonConstants.TRANSACTION_MESSAGE, transactionMessage);
				
				schedulerNotificationObj.add(recordType+"  :  Successfully wrote "+ecrlsLabDocList.size()+" PID updated "+recordType+" records to NYEHMS Database");
				schedulerNotificationObj.add(recordType+"  :  Failed to write  "+failureDocuments.size()+" PID Updated "+recordType+" records to NYEHMS Database");
				
			}
			statusObject.put(CommonConstants.COMMENTS, failureDocuments.toString());
		}
		
		statusObject.put("SUCCESS MESSAGESS", successDocuments.toString());
		
		log.error(transactionMessage);
		schedulerStatusObject.add(statusObject);
		
		
	}
	
	public List<Long> getErroredPIDDocumentsList(String recordType){
		
		List<Long> erroredDocumentsList = schedulerJobsDAO.getErroredPIDDocumentsList(recordType);
		
		schedulerJobsDAO.updatePIDErrorDocumentStatus(recordType);
		
		return erroredDocumentsList;
	}
	
	public List<EcrlsLabDocVO> getErroredPIDDocumentsListFromEclrs(
			List<Long> erroredDocumentsList, String recordType){
			
		Map<String, Object> inOutParameterMap = new HashMap<>();
		
		if ("HIV_LAB".equals(recordType)) {
			inOutParameterMap.put("tableName", "VALLH");
		} else {
			inOutParameterMap.put("tableName", "VALLS");
		}
		
		inOutParameterMap.put("erroredDocumentsList", erroredDocumentsList);

		List<EcrlsLabDocVO> reAssignPIDUpdatedDocuments = new ArrayList<>();

		reAssignPIDUpdatedDocuments = eclrsDataDAO.getErroredPIDDocumentsListFromEclrs(inOutParameterMap);
		
		return reAssignPIDUpdatedDocuments;
	}
	
	public void checkDuplicatesinStdDocTable(String documentType){
		
		Map<String, String> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("STD_TABLE", LabCommonUtils.getStdTableName(documentType));
		
		schedulerJobsDAO.checkDuplicatesinStdDocTable(inputParameterMap);
		
	}
	
	private boolean checkResultsTextHasNoSequence(String obxOBservationResultText, String noSequenceText){
		
		String[] noSequenceKeyWords = noSequenceText.split(",");
		
		
		
		for (String keyWord : noSequenceKeyWords) {
			
			if(obxOBservationResultText.contains(keyWord)){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public void addFileNameToAssignmentReportsTable(String documentType, Map<String, String> schedulerMetaData){
		try{
			
			
			Map<String, String> stdTableColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
			
			String fileName = stdTableColumnNames.get("FILE_NAME");
			
			String stdTableName = LabCommonUtils.getStdTableName(documentType);
			
			Map<String, String> inputParameterMap = new HashMap<>();
			
			inputParameterMap.put("fileName", fileName);
			inputParameterMap.put("stdTableName", stdTableName);
			inputParameterMap.put("documentType", documentType);
			
			schedulerJobsDAO.addFileNameToAssignmentReportsTable(inputParameterMap);
			
			
			
			
		}catch(Exception exception){
			log.error("{}  : Exception occured while adding the File name to Assignment Report table  : {}", documentType, exception.getMessage());
			
			String[] recipientEmailAddresses = schedulerMetaData.get("NOTIFICATIONS_USERS_TO").split(",");
			
			try {
				sendEmail.sendSSLMessage(recipientEmailAddresses, "Failed : Adding "+documentType+" file name to Assignment Reports table", "Failed : Adding "+documentType+" file name to Assignment Reports table",
						schedulerMetaData.get("NOTIFICATIONS_FROM"), null, null, CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			} catch (MessagingException e) {
				log.error(e.getMessage());
			}
			
		}
	}
	
	public int documentInitialValidation(BigDecimal documentId,
			Map<Object, Object> docVO, String documentType){
		
		int resultCount = 0;
		
		Map<String, String> rawColumnReferences = LabCommonUtils.getRawReferenceColumnNames(documentType);
		
		String firstName = (String)docVO.get(rawColumnReferences.get("FNAME"));
		
		String lastName = (String)docVO.get(rawColumnReferences.get("LNAME"));
		
		if((CommonUtil.isNullOrEmpty(firstName) || "unknown".equalsIgnoreCase(firstName.trim())) && (CommonUtil.isNullOrEmpty(lastName) || "unknown".equalsIgnoreCase(lastName.trim()))){
			
			resultCount = writeErrorCode(CommonConstants.STOPPED_AT_RAW_NOT_HAVING_SUFFICIENT_INFO, documentId, documentType);
					
		}
		
		return resultCount;
		
		
	}
	

	
	public String getDocumentError(int currentDocumentId) {
		return schedulerJobsDAO.getDocumentError(currentDocumentId);
	}
	
	
	public String getTestType(String obxloinccode) {
		
		String testType = null;
		
		if (CommonUtil.isNotNullAndEmpty(obxloinccode)) {

			Map<Object, Object> inputOutputParamters = new HashMap<>() ;
			inputOutputParamters.put("loincCode", obxloinccode);


			Map<String, String> loincUnitsOfMesurement = schedulerJobsDAO
					.checkForLoincAndUnitsOfMeasurementCombination(inputOutputParamters);


			if (loincUnitsOfMesurement != null && loincUnitsOfMesurement.get("TEST_TYPE") != null) {
				testType = loincUnitsOfMesurement.get("TEST_TYPE");
			}

		}
		return testType;
		
		
	}
	
	public void loadHIVDocumentReferenceVariable(Map<String, String> schedulerMetaData) {
		
		hasQuestCheck = true;
		hasLabCorpCheck = true;
		
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("QUEST_CLIAS"))){
			questClias = Arrays.asList(schedulerMetaData.get("QUEST_CLIAS").split(","));
		}else {
			hasQuestCheck = false;
		}
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("QUEST_LOINC_CODES"))){
			questLoincCodes = Arrays.asList(schedulerMetaData.get("QUEST_LOINC_CODES").split(","));
		}else {
			hasQuestCheck = false;
		}
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("QUEST_TEST_RESULT"))){
			questTestResult = Arrays.asList(schedulerMetaData.get("QUEST_TEST_RESULT").split(","));
		}else {
			hasQuestCheck = false;
		}
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("QUEST_CLIAS"))){
			labCorpClias = Arrays.asList(schedulerMetaData.get("LABCORP_CLIAS").split(","));
		}else {
			hasLabCorpCheck = false;
		}
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("LABCORP_LOINC_CODES"))){
			labCorpLoincCodes = Arrays.asList(schedulerMetaData.get("LABCORP_LOINC_CODES").split(","));
		}else {
			hasLabCorpCheck = false;
		}
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("LABCORP_TEST_RESULT"))){
			labCorpTestResult = Arrays.asList(schedulerMetaData.get("LABCORP_TEST_RESULT").split(","));
		}else {
			hasLabCorpCheck = false;
		}
		
		if(CommonUtil.isNotNullAndEmpty(schedulerMetaData.get("MISSING_ADDRESS_GENOTYPE_LABS"))) {
			missingGenoTypeLabs =  Arrays.stream(schedulerMetaData.get("MISSING_ADDRESS_GENOTYPE_LABS").split("\\|"))
		            .map(s -> s.split(":"))
		            .collect(Collectors.toMap(
		                arr -> arr[0],                                      
		                arr -> Arrays.asList(arr[1].split(","))             
		            ));;
		}
		
	}

	public void resetHIVDocumentReferenceVariable() {
		questClias = null;
		questLoincCodes = null;
		questTestResult = null;
		labCorpClias = null;
		labCorpLoincCodes = null;
		labCorpTestResult = null;
		
		hasQuestCheck= false;
		hasLabCorpCheck = false;
		missingGenoTypeLabs = null;
	}

	public void sendHl7CCDRematchingRecordsDetails(Map<String, String> schedulerMetaData,
			Map<String, String> statusObject, List<Map<String, String>> recordsDetailsList) {

		String subject = System.getProperty("stage") + ": HL7 Rematched Against CCD Summary";

		StringBuilder mailContent = new StringBuilder("");

		mailContent.append("<table border='1' width='600px;'>");

		if (statusObject.containsKey("Initiator")) {
			mailContent.append("<tr><th style='background-color: #3399FF' colspan='4'>" + statusObject.get("Initiator")
					+ "</th></tr>");
		}
		mailContent.append(
				"<tr><th style='background-color: #3399FF' colspan='4'>" + statusObject.get("Status") + "</th></tr>");

		mailContent.append(
				"<tr><th>Current Process Status</th><th>Number of Records needs Processing</th><th>Number of Records left</th><th>Number of Records Processed</th></tr>");

		for (Map<String, String> recordsDetails : recordsDetailsList) {

			mailContent.append("<tr><td>" + recordsDetails.get("Current Process Status") + "</td>");
			mailContent.append("<td>" + recordsDetails.get("Before Processing Records") + "</td>");
			mailContent.append("<td>" + recordsDetails.get("After Processing Records") + "</td>");
			BigDecimal bRecords = BigDecimal.valueOf(Double.valueOf(recordsDetails.get("Before Processing Records")));
			BigDecimal aRecords = BigDecimal.valueOf(Double.valueOf(recordsDetails.get("After Processing Records")));

			BigDecimal totalRecordsProcessed = new BigDecimal(0);
			totalRecordsProcessed = (totalRecordsProcessed.add(bRecords).subtract(aRecords)).setScale(0,
					RoundingMode.HALF_UP);
			mailContent.append("<td>" + totalRecordsProcessed.toString() + "</td></tr>");
		}

		mailContent.append("</table>");

		try {

			if (mailContent.toString().contains("HL7 Manual Process Initiated by")) {
				sendEmail.processEmail(schedulerMetaData.get("HL7_MATCHED_AGAINST_CCD_NOTIFICATION_SEND_TO"), subject,
						mailContent.toString(), "NIGHTLY_JOB", CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			} else {
				sendEmail.processEmail(null, subject, mailContent.toString(), "NIGHTLY_JOB",
						CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
			}

		} catch (MessagingException messagingException) {
			log.error(subject + "  :   {} with error: {}", mailContent.toString(), messagingException.toString());
		}

	}
	
	public Map<String, String> getLoincCombinationData(Map<String, String> inputParameterMap){
		return schedulerJobsDAO.getLoincCombinationData(inputParameterMap);
	}
	
	public Map<String, String> getLoincResultCombinationData(Map<String, String> inputParameterMap){
		return schedulerJobsDAO.getLoincResultCombinationData(inputParameterMap);
	}

	public void doHivLabUpdates() {
		schedulerJobsDAO.doHivLabUpdates();
	}
	
	public boolean checkTenMillion(String obxText) {
		String greaterChar = ">";
		int index = obxText.indexOf(greaterChar);
		if (index != -1) {
			obxText = greaterChar + obxText.replaceAll(".*" + greaterChar, "");
	    }
		if (obxText.contains("HIV-1")) {
			obxText = obxText.replaceAll("HIV-1", "");
		}
		obxText = obxText.replaceAll("[^0-9>.]", "");
		
		if (obxText.contains(">")){
			obxText = obxText.substring(1);
		}
		

		boolean isGreater = false;
		try {
			if (CommonUtil.isNumeric(obxText) && Double.parseDouble(obxText) > 10000000.00) {
				isGreater = true;
			}
		}catch(Exception e) {
			log.error("Unable to parse the obxobservationresult:", obxText);
		}
		return isGreater;
	}
	
	
}