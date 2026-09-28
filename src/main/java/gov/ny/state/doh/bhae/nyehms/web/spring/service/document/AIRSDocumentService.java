package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb.AIDADataDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.AIRSDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AIRSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftDetailsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This is implementation of AIRSDocumentService interface. This provides
 * implementation of the methods to interact with AIRS document.
 *
 * @author vxp06
 */
@Service
public class AIRSDocumentService {

    private static Logger log = LoggerFactory.getLogger(AIRSDocumentService.class);
    
    @Autowired AIRSDocumentDAO airsDocumentDAO;
    
    @Autowired private CommonService commonService;
    
    @Autowired AIDADataDAO aIDADataDAO;
    
    @Autowired ManualQuartzJobService manualQuartzJobService;
    
    @Autowired LaftDetailsService laftDetailsService;
    /**
     * This method will return AIRS document for a given document id
     * {@inheritDoc}
     *
     * @param documentID
     * @return
     */
    public AIRSDocVO getAIRSDocument(int documentID) {
        AIRSDocVO aIRSDocumentVO = null;
       	aIRSDocumentVO = airsDocumentDAO.getAIRSDoc(documentID);
       	String airsVer = aIRSDocumentVO.getAIRS_VERSION();
       	if (CommonUtil.isNotNullAndEmpty(airsVer)) {
       		airsVer = airsVer.replace(".", "");
       		int airsVerInt = Integer.parseInt(airsVer);
       		if(airsVer.startsWith("9") &&  airsVerInt != 904 && airsVerInt != 905) {
       			aIRSDocumentVO.setAIRS_VER_TYPE("NEW");
       		} else {
       			aIRSDocumentVO.setAIRS_VER_TYPE("OLD");
       		}
       	} else {
       		aIRSDocumentVO.setAIRS_VER_TYPE("OLD");
       	}
        return aIRSDocumentVO;
    }
    
    public PersonViewVO getStdAIRSDocumentForClericalReview(int documentId){
    	
    	PersonViewVO aIRSDocVO = airsDocumentDAO.getStdAIRSDocumentForClericalReview(documentId);
    	
    	return aIRSDocVO;
    }
    
    public AIRSDocVO getRawAIRSDocument(int documentID){
    	return airsDocumentDAO.getRawAIRSDocument(documentID);
    }

	public int validateAndWriteRawAIRSRecordDataToStdTable(
			Map<Object, Object> docVO,
			BigDecimal documentId) {
		
		int resultCount = 0;
		List<String> errors = new ArrayList<String>();
		int currentProcessStatus = 0;
		BigDecimal existingProcessStatus = (BigDecimal) docVO.get("CURRENT_PROCESS_STATUS");

		currentProcessStatus = validateAirsRecord(docVO, errors);
		if(currentProcessStatus == CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS){
			int _deleteFlagStatus = manualQuartzJobService.makeDocumentAsInactivate(documentId.intValue());
			if(_deleteFlagStatus != 0){
				_deleteFlagStatus = manualQuartzJobService.writeErrorCode(currentProcessStatus, documentId, "AIRS");
				
				if(_deleteFlagStatus == 0){
					throw new DataRetrievalFailureException("Unable to delete the document "+documentId);
				}
			}else{
				throw new DataRetrievalFailureException("Unable to delete the document "+documentId);
			}
		}
		else if(currentProcessStatus == CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS){
			int _result = manualQuartzJobService.writeErrorCode(currentProcessStatus, documentId, "AIRS");
			if(_result == 0){
				throw new DataRetrievalFailureException("Unable to update the 18 status for the document "+documentId);
			}
		}
		else if(currentProcessStatus == CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS){
			resultCount = writeRawAIRSRecordDataToStdTable(docVO);
		}else{
			String errorDesc = errors.toString();
			errorDesc = errorDesc.replace("[", "");
			errorDesc = errorDesc.replace("]", "");
			manualQuartzJobService.addValidationError(documentId, errorDesc, currentProcessStatus, false, existingProcessStatus, "AIRS");
			int _result =  manualQuartzJobService.writeErrorCode(currentProcessStatus, documentId, "AIRS");
			if(_result == 0){
				throw new DataRetrievalFailureException("Unable to update the validation error status for the document  "+documentId);
			}
		}
		return resultCount;
	}

    
	public int writeRawAIRSRecordDataToStdTable(Map<Object, Object> docVO){
		
    	String SSN = (String)docVO.get("SSN");
    	
    	docVO.put("SSN", CommonUtil.formatSSNValue(SSN));
    	
		
		String SWM = (String)docVO.get("RISK01");
		docVO.put("SWM", LabCommonUtils.checkRiskData(SWM));
		
		String SWF = (String)docVO.get("RISK02");
		docVO.put("SWF", LabCommonUtils.checkRiskData(SWF));
		
		String IDU = (String)docVO.get("RISK03");
		docVO.put("IDU", LabCommonUtils.checkRiskData(IDU));
		
		String SEX_PARTNER_IDU = (String)docVO.get("RISK04");
		docVO.put("SEX_PARTNER_IDU", LabCommonUtils.checkRiskData(SEX_PARTNER_IDU));
		
		String SEX_PARTNER_HIV = (String)docVO.get("RISK05");
		docVO.put("SEX_PARTNER_HIV", LabCommonUtils.checkRiskData(SEX_PARTNER_HIV));
		
		String RECV_BLOOD_PROD = (String)docVO.get("RISK06");
		docVO.put("RECV_BLOOD_PROD", LabCommonUtils.checkRiskData(RECV_BLOOD_PROD));
		
		String PERINATAL = (String)docVO.get("RISK07");
		docVO.put("PERINATAL", LabCommonUtils.checkRiskData(PERINATAL));
		
		String RISK_OTHER = (String)docVO.get("RISK08");
		docVO.put("RISK_OTHER", LabCommonUtils.checkRiskData(RISK_OTHER));
		
		String RISK_UNKNOWN = (String)docVO.get("RISK09");
		docVO.put("RISK_UNKNOWN", LabCommonUtils.checkRiskData(RISK_UNKNOWN));
		
		
		String RACE_INDIAN = (String)docVO.get("RACE_INDIAN");
		docVO.put("RACE_INDIAN", LabCommonUtils.checkRaceData(RACE_INDIAN));
		
		String RACE_BLACK = (String)docVO.get("RACE_BLACK");
		docVO.put("RACE_BLACK", LabCommonUtils.checkRaceData(RACE_BLACK));
		
		String BLACK_DET = (String)docVO.get("BLACK_DET");
		if((CommonUtil.isNullOrEmpty(RACE_BLACK) || RACE_BLACK.equals("0")) &&  CommonUtil.isNotNullAndEmpty(BLACK_DET)) {
			docVO.put("RACE_BLACK", "1");
		} 
		
		String RACE_ASIAN = (String)docVO.get("RACE_ASIAN");
		docVO.put("RACE_ASIAN", LabCommonUtils.checkRaceData(RACE_ASIAN));

		String ASIAN_DET = (String)docVO.get("ASIAN_DET");
		if((CommonUtil.isNullOrEmpty(RACE_ASIAN) || RACE_ASIAN.equals("0")) &&  CommonUtil.isNotNullAndEmpty(ASIAN_DET)) {
			docVO.put("RACE_ASIAN", "1");
		}

		String RACE_PACIFIC = (String)docVO.get("RACE_PACIFIC");
		docVO.put("RACE_PACIFIC", LabCommonUtils.checkRaceData(RACE_PACIFIC));

		String HAWAIIAN_DET = (String)docVO.get("HAWAIIAN_DET");
		if((CommonUtil.isNullOrEmpty(RACE_PACIFIC) || RACE_PACIFIC.equals("0")) &&  CommonUtil.isNotNullAndEmpty(HAWAIIAN_DET)) {
			docVO.put("RACE_PACIFIC", "1");
		}

		String RACE_WHITE = (String)docVO.get("RACE_WHITE");
		docVO.put("RACE_WHITE", LabCommonUtils.checkRaceData(RACE_WHITE));

		String WHITE_DET = (String)docVO.get("WHITE_DET");
		if((CommonUtil.isNullOrEmpty(RACE_WHITE) || RACE_WHITE.equals("0")) &&  CommonUtil.isNotNullAndEmpty(WHITE_DET)) {
			docVO.put("RACE_WHITE", "1");
		}

		String RACE_OTHER = (String)docVO.get("RACE_OTHER");
		docVO.put("RACE_OTHER", LabCommonUtils.checkRaceData(RACE_OTHER));
		
		String RACE_UNKNOWN = (String)docVO.get("RACE_UNKNOWN");
		docVO.put("RACE_UNKNOWN", LabCommonUtils.checkRaceData(RACE_UNKNOWN));
		
		String I_ILL = (String)docVO.get("I_ILL");
		docVO.put("I_ILL", LabCommonUtils.checkRaceData(I_ILL));
		
		String INFORMED_OF_DIAG = (String)docVO.get("INFORMED_OF_DIAG");
		docVO.put("INFORMED_OF_DIAG", LabCommonUtils.checkRaceData(INFORMED_OF_DIAG));
		
		String PREV_I_ILL = (String)docVO.get("PREV_I_ILL");
		docVO.put("PREV_I_ILL", LabCommonUtils.checkRaceData(PREV_I_ILL));
		
		String D_DIAG = (String)docVO.get("D_DIAG");
		docVO.put("D_DIAG", LabCommonUtils.checkRaceData(D_DIAG));
		
		String PREV_D_DIAG = (String)docVO.get("PREV_D_DIAG");
		docVO.put("PREV_D_DIAG", LabCommonUtils.checkRaceData(PREV_D_DIAG));
		
		String SEX = (String)docVO.get("SEX");
		docVO.put("SEX", LabCommonUtils.checkBirthSexData(SEX));
		
		String GENDER = (String)docVO.get("GENDER");
		docVO.put("GENDER", LabCommonUtils.checkCurrentGenderData(GENDER));
		
		String ETHNICCD = (String)docVO.get("ETHNICCD");
		docVO.put("ETHNICCD", LabCommonUtils.checkEthnicityData(ETHNICCD));
		
		String HISPANIC_DET = (String)docVO.get("HISPANIC_DET");
		if((CommonUtil.isNullOrEmpty(ETHNICCD) || ETHNICCD.equals("09")) &&  CommonUtil.isNotNullAndEmpty(HISPANIC_DET)) {
			docVO.put("ETHNICCD", "01");
		}
		
		docVO.put("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		
		String EVER_TEST_NEGATIVE = (String)docVO.get("EVER_TEST_NEGATIVE");
		docVO.put("EVER_TEST_NEGATIVE", LabCommonUtils.checkTestDataValues(EVER_TEST_NEGATIVE));
		
		docVO.put("FNAME", CommonUtil.NameValidation((String)docVO.get("FNAME")));
		docVO.put("MNAME", CommonUtil.NameValidation((String)docVO.get("MNAME")));
		docVO.put("LNAME", CommonUtil.NameValidation((String)docVO.get("LNAME")));
		
		docVO.put("ADDRESS", CommonUtil.addressValidation((String)docVO.get("ADDRESS")));
		
		docVO.put("MEDRECNO", CommonUtil.normalizeString((String)docVO.get("MEDRECNO")));
		
		docVO.put("MEDICAID_NUMBER", CommonUtil.checkMedicaidNumberFormat((String)docVO.get("MEDICAIDNO")));
		
		
		docVO.put("RECV_BLOOD_PROD_DATE_TEXT", (String)docVO.get("DATE_RECV_BLD_PROD"));
		
		String BIRTH_COUNTRY = (String)docVO.get("COUNTRY_BIRTH");
        String BIRTH_COUNTRY_OTHER = (String)docVO.get("BIRTH_COUNTRY");
        if(CommonUtil.isNullOrEmpty(BIRTH_COUNTRY) && CommonUtil.isNotNullAndEmpty(BIRTH_COUNTRY_OTHER)) {
               
               docVO.put("BIRTH_COUNTRY", "OTH");
               docVO.put("BIRTH_COUNTRY_OTHER", BIRTH_COUNTRY_OTHER);
               
        }else {
               docVO.put("BIRTH_COUNTRY", BIRTH_COUNTRY);
               docVO.put("BIRTH_COUNTRY_OTHER", BIRTH_COUNTRY_OTHER);
        }
		
		String BIRTH_SEX = (String)docVO.get("SEX_AT_BIRTH");
		
		if(CommonUtil.isNullOrEmpty(BIRTH_SEX)) {
			docVO.put("BIRTH_SEX", LabCommonUtils.checkBirthSexData(SEX));
		} else {
			docVO.put("BIRTH_SEX", LabCommonUtils.checkBirthSexData(BIRTH_SEX));
		}
		
		String AGE = (String)docVO.get("AGE");
		if (CommonUtil.isNotNullAndEmpty(AGE) && CommonUtil.isNumeric(AGE.trim())) {
			docVO.put("AGE", AGE);
		} else {
			docVO.put("AGE", null);
		}
		
		
		String DT_CREATED = (String)docVO.get("DT_CREATED");
			
		String DT_MODIFIED = (String)docVO.get("DT_MODIFIED");

		Date greaterDate = null;
		
		Date sqlDtCreatedDate = null;
		Date sqlDtModifiedDate = null;

		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy");
		
		try {
			
			
			if(DT_CREATED != null){
				sqlDtCreatedDate = new Date(sdf1.parse(DT_CREATED.trim()).getTime());
			}
			
			if(DT_MODIFIED != null){
				sqlDtModifiedDate = new Date(sdf1.parse(DT_MODIFIED.trim()).getTime());
			}
			
			if(sqlDtCreatedDate == null){
				greaterDate = sqlDtModifiedDate;
			}else if(sqlDtModifiedDate == null){
				greaterDate = sqlDtCreatedDate;
			}else if(sqlDtCreatedDate.compareTo(sqlDtModifiedDate) == -1){
				greaterDate = sqlDtModifiedDate; 
			}
			
			docVO.put("GREATER_DATE", greaterDate);
			

		} catch (ParseException e) {
			throw new DataRetrievalFailureException("Unable to parse the Dates the documentId  "+docVO.get("DOCUMENT_ID"));
		}
		
		
		int resultCount = airsDocumentDAO.writeRawAIRSRecordDataToStdTable(docVO);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Unable to store the document in STD AIRS table  "+docVO.get("DOCUMENT_ID"));
		}
		return resultCount;
		
	}

	public int validateAirsRecord(Map<Object, Object> docVO,
			List<String> errors){
		
		
		
		String RECORD_TYPE = (String)docVO.get("RECORD_TYPE");
		
		if(RECORD_TYPE != null && "D".equalsIgnoreCase(RECORD_TYPE.trim())){
			return CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS;
		}else if(RECORD_TYPE != null && "Z".equalsIgnoreCase(RECORD_TYPE.trim())){
			return CommonConstants.STOPPED_AT_RAW_LAYER_PROCESS_STATUS;
		}
		
		
		boolean hasAgencyIdError = false;
		boolean hasAgencyRefernceTableError = false;
		boolean hasLnameError = false;
		boolean hasTcIdError = false;
		boolean hasURNError = false;
		boolean hasDateError = false;
		
		String AGENCY_ID = (String)docVO.get("AGENCY_ID");
		
		if(CommonUtil.isNullOrEmpty(AGENCY_ID)){
			errors.add("Agency Id is required");
			
			hasAgencyIdError = true;
			
		}else{
			Map<String, Object> agencyDetails = airsDocumentDAO.getLastUpgradedDate(AGENCY_ID);
			
			if(agencyDetails == null){
				errors.add("Agency Id not in the reference table : "+AGENCY_ID);
				hasAgencyRefernceTableError = true;
			}
		}
		
		String LNAME = (String)docVO.get("LNAME");
		
		if(CommonUtil.isNullOrEmpty(LNAME)){
			errors.add("Last name is required");
			hasLnameError = true;
		}
		
		
		
		String TC_ID = (String)docVO.get("TC_ID");
		if(CommonUtil.isNullOrEmpty(TC_ID)){
			errors.add("TC Id is required");
			hasTcIdError = true;
			
		}
		
		String URN = (String)docVO.get("URN");
		if(CommonUtil.isNullOrEmpty(URN)){
			errors.add("URN is required");
			hasURNError = true;
			
		}
		
		
		
		String DT_CREATED = (String)docVO.get("DT_CREATED");
		
		String DT_MODIFIED = (String)docVO.get("DT_MODIFIED");
		
		String CTR_FLAG = (String)docVO.get("CTR_FLAG");
		
		// Changed on 9/26/24 to process the missing 1471 records on request from Ron
		if(!(CTR_FLAG.equals("1") && RECORD_TYPE.equals("U")) && (CommonUtil.isNullOrEmpty(DT_CREATED) && CommonUtil.isNullOrEmpty(DT_MODIFIED))){
			errors.add("Create Date is required");
			hasDateError = true;
			
		}
		
		
		
		
		if(hasAgencyRefernceTableError){
			return CommonConstants.AIRS_AGENCY_NOT_IN_REFERNCE_TABLE;
		}else if(hasLnameError){
			return CommonConstants.AIRS_LNAME_NOT_AVAILABE;
		}else if(hasTcIdError){
			return CommonConstants.AIRS_TC_ID_NOT_AVAILABLE;
		}if(hasAgencyIdError){
			return CommonConstants.AIRS_AGENCY_NOT_AVAILABE;
		}else if(hasDateError){
			return CommonConstants.AIRS_DATE_CREATED_NOT_AVAILABLE;
		}else if(hasURNError){
			return CommonConstants.AIRS_URN_NOT_AVAILABLE;
		}else{
			return CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS;
		}
	}
	

	/**
	 * 
	 * Stopping Raw AIRS documents at Raw layer only with Current process status as 18
	 * 
	 * CRITERIA :  TC_ID = 'NO_RECORDS' or URN = 'NO_RECORD'
	 * @param schedulerStatusObject
	 */
	public void stopAirsNORecordsAtRaw(List<JSONObject> schedulerStatusObject){
		
		JSONObject statusObject = new JSONObject();
		
		statusObject.put(CommonConstants.STATUS_ACTION_NAME, "Checking AIRS 'NO Records' data in Raw table");
		
		int resultCount = airsDocumentDAO.stopAirsNORecordsAtRaw();
		
		statusObject.put(CommonConstants.TRANSACTION_STATUS, CommonConstants.TRANSACTION_STATUS_SUCCESS);
		statusObject.put(CommonConstants.TRANSACTION_MESSAGE, "Successfully checked the NO Records data in the Raw AIRS table");
		statusObject.put("No Records Documents count", resultCount);
		
		schedulerStatusObject.add(statusObject);
		
	}
	
	public List<Map<String, String>> loadAirsAIDAData(String tc_id, String parameterName, UserObject userObject) {
		
		List<Map<String, String>> aidaData = null;
		
		try {
			
		
			String tableName = EnvironmentProperties.envProperties.getProperty(parameterName+"_TABLE");
			String columns = EnvironmentProperties.envProperties.getProperty(parameterName+"_COLUMNS");
			
			Map<String, String> inputParameterMap = new HashMap<String, String>();
			
			inputParameterMap.put("tableName", tableName);
			inputParameterMap.put("columns", columns);
			inputParameterMap.put("tc_id", tc_id);
			aidaData = aIDADataDAO.loadAirsAIDAData(inputParameterMap);
		}catch(Exception e){

			log.error("Failed to fetch the data from AIDA table  {}",e.getMessage());
			
			throw new DataRetrievalFailureException("Failed to fetch the data from AIDA table  "+e.getMessage());
			
		}catch(Throwable e){
			log.error("Failed to fetch the data from AIDA table  {}",e.getMessage());
			
			throw new DataRetrievalFailureException("Failed to fetch the data from AIDA table  "+e.getMessage());
		}
		
		return aidaData;
		
		
	}
	
	public void checkForActiveErrors(Boolean checkForToday) {
		
		Map<String, Boolean> inputParameterMap = new HashMap<String, Boolean>();
		inputParameterMap.put("checkForToday", checkForToday);
		List<Map<String, Object>> errorList = airsDocumentDAO.checkForActiveErrors(inputParameterMap);
		//
		if(errorList.size() > 0 ) {
			commonService.sendStatusSummary(errorList, "AIRS_AGENCY_STATUS_EMAIL_HEADERS", "AIRS_AGENCY_STATUS_EMAIL_COLUMNS", "AIRS_AGENCY", EnvironmentProperties.envProperties.getProperty("AIRS_AGENCY_EMAIL_SUBJECT"), EnvironmentProperties.envProperties.getProperty("AIRS_AGENCY_EMAIL_BODY_MSG"),   null);
		}
		
	}
	
	public void repriseAgencyErrorDocuments() {
		
		List<Long> documentIds = airsDocumentDAO.getDocumentIdsHasValidAgency();
		
		if(documentIds != null && documentIds.size() > 0) {
			UserObject userObject = new UserObject();
			userObject.setWorkerId(900);
			laftDetailsService.updateDocumentsStatus(documentIds, CommonConstants.AIRS_AGENCY_NOT_IN_REFERNCE_TABLE, "AGENCY ID added", "reprise", "AIRS", null, userObject);
		}
	}
}
