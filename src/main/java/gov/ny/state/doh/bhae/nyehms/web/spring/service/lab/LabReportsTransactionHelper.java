package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AcceptedTestResultDomainVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.CLEPLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LAFT_LAB_RECON_SUMM_REC;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabFacilitiesVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabLoincMappingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.LabDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
	/*
	 * A helper class to manage all operations which should be executed in a transaction.
	 * The methods are annotated with Spring @Transactional to ensure the transaction.
	 * This class is annotated as @Service in order to load as a Spring managed bean.
	 */
@Service
public class LabReportsTransactionHelper {
	/*
	 * Spring dependency injected reference to CommonExceptionHandler
	 */
	@Autowired
	CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	LabDocumentService labDocumentService;
	
	@Autowired
	CommonDocumentService commonDocumentService;
	
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	/*
	 * Spring dependency injected reference to ProviderService
	 */
	@Autowired
	LabReportsService labReportsService;
	
	private final String CONCAT_IND = "|";
	
	//private static Logger log = LoggerFactory.getLogger(LabReportsTransactionHelper.class);
	
	@Autowired
	LoincResultDomainMappingService loincResultDomainMappingService;
	
	@Autowired	ClepAuditResultsService clepAuditResultsService;
	
	@Autowired	LaftCountReconcilleService laftCountReconcilleService;
	
	@Transactional
	public void addNewLabLocalCodeAndLoincDetailsToFacility(String pfi,String localCode,long[] loincCodes, String dateValidFrom, String dateValidTill, BindingResult result, 
			UserObject userObject){
		long localCodeMapId = labReportsService.addNewLabLocalCodeToFacility(pfi, localCode, dateValidFrom, dateValidTill, result, userObject);
		if(localCodeMapId!=0){
			labReportsService.linkLoincCodesToLoclCode(localCodeMapId, loincCodes, result, userObject);
		}
	}
	
	@Transactional
	public void addNewLabDetailsAndContactDetailsOfPFI(BindingResult bindingResult, UserObject userObject, String clia, List<LabFacilitiesVO>  labFacilitiyDetails){
		int resultStatus = labReportsService.addNewLabDetailsToClia(bindingResult, userObject, clia, labFacilitiyDetails);
		
		if(resultStatus == labFacilitiyDetails.size()){
			for (LabFacilitiesVO labFacilitiesVO : labFacilitiyDetails) {
				
				List<JSONObject> pfiContactDetails = labFacilitiesVO.getPfiContactDetails();
				
				if(pfiContactDetails != null && pfiContactDetails.size() > 0){
					labReportsService.addNewLabContactsToPFI(bindingResult, userObject, labFacilitiesVO.getPfi(), labFacilitiesVO.getPfiContactDetails());
				}
			}
		}else{
			String transactionMessage = "unable to add the physical facility details to CLIA: "+clia;
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
		}
	}
	
	@Transactional
	public void addNewLabDetails(BindingResult bindingResult, UserObject userObject,String clia, List<LabFacilitiesVO> labFacilitiyDetails){
		String transactionMessage = null;
		
		
			String _clia_db = labReportsService.checkCliaExiststence(bindingResult, userObject, clia);
			int resultCount = 0;
			
			
			if(_clia_db == null){
				resultCount = labReportsService.addNewClia(bindingResult, userObject, clia);
			}
			
			
			if((resultCount > 0 && labFacilitiyDetails.size() > 0) || (_clia_db != null && labFacilitiyDetails.size() > 0)){
				
				addNewLabDetailsAndContactDetailsOfPFI(bindingResult, userObject, clia, labFacilitiyDetails);
				
				Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
				
				if("TRANSACTION_SUCCESS".equals(resultMap.get("TRANSACTION_RESULT"))){
					
					if(_clia_db == null){
						transactionMessage = "Successfully added the Clinical Labaratory and its Physical facility details";
					}else{
						transactionMessage = "Successfully added the new facility details to the Clinical Labaratory";
					}
					
				}else{
					
					if(_clia_db == null){
						transactionMessage = "Unable to add the Clinical Labaratory and its Physical facility details";
					}else{
						transactionMessage = "unable add the new facility details to the Clinical Labaratory";
					}
				}
				
				
			}else if(_clia_db != null){
				
				transactionMessage = "Nothing was updated to Clinical Labaratory";
			}
			
			Map<Object,Object> resultMap=((Map<Object,Object>)bindingResult.getTarget());
			
			if("TRANSACTION_SUCCESS".equals(resultMap.get("TRANSACTION_RESULT"))){
				
				commonExceptionHandler.inspectDBTransacionResult(0, transactionMessage, bindingResult);
			}else{
				
				commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			}
	
	}
	
	@Transactional
	public List<LabFacilitiesVO> getFacilitiesDataAndContactsAssociatedForClia(BindingResult bindingResult, UserObject userObject, String clia){
		
		List<LabFacilitiesVO> physicalFacilitiesList = labReportsService.getFacilitiesDataForClia(bindingResult, userObject, clia);
		
		for (LabFacilitiesVO labFacilitiesVO : physicalFacilitiesList) {
			List<JSONObject> labContactDetailsList = labReportsService.getLabContactsForPFI(labFacilitiesVO.getPfi(), bindingResult, userObject);
			labFacilitiesVO.setPfiContactDetails(labContactDetailsList);
		}
		
		return physicalFacilitiesList;
				
	}
	
	@Transactional
	public void addNewLabLoincDetailsToLookup(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO){
		
		List<String> recordMatches = loincResultDomainMappingService.checkForReferenceLoincCodeMapping(bindingResult, userObject, labLoincMappingVO);
		
		if(recordMatches.size() > 0){
			
			String _loincCode = labLoincMappingVO.getLoincCode();
			
			int existingRecordsCount = loincResultDomainMappingService.getExistingLoincRecordsCount(bindingResult, userObject, _loincCode);
			
			if(existingRecordsCount == 0){
				loincResultDomainMappingService.addNewLabLoincDetailsToLookup(bindingResult, userObject, _loincCode, recordMatches.get(0));
			}
		}
		
	}
	
	
	@Transactional
	public void addNewAcceptableTestResultsAndAssociateToLoincCode(AcceptedTestResultDomainVO acceptedTestResultDomainVO, BindingResult bindingResult, UserObject userObject){
		
		
		String loincCode = acceptedTestResultDomainVO.getLoincCode();
		
		
		String acceptedValue = acceptedTestResultDomainVO.getAcceptedValue();
		String rangeLowerBound = acceptedTestResultDomainVO.getRangeLowerBound();
		String rangeUpperBound = acceptedTestResultDomainVO.getRangeUpperBound();
		String typeOfResult = acceptedTestResultDomainVO.getTypeOfResult();
		
		
		StringBuffer inputData = new StringBuffer();
		
		inputData.append(typeOfResult).append(CONCAT_IND);
		if(acceptedValue != null){
			inputData.append(acceptedValue);
		}
		inputData.append(CONCAT_IND);
		if(rangeLowerBound != null){
			inputData.append(rangeLowerBound);
		}
		inputData.append(CONCAT_IND);
		if(rangeUpperBound != null){
			inputData.append(rangeUpperBound);
		}
		
		
		List<Integer> testResultsAttributeIds = loincResultDomainMappingService.checkForCustomTestData(inputData.toString(), bindingResult, userObject);
		
		if(testResultsAttributeIds.size() == 0){
			
			int attributeDomainId = loincResultDomainMappingService.addNewAcceptableTestResults(acceptedTestResultDomainVO, bindingResult, userObject);
			
			loincResultDomainMappingService.assoicateTestResultsToLoincCode(loincCode, attributeDomainId, acceptedTestResultDomainVO.getStandardResults(), bindingResult, userObject );
			
		}else{
			if(testResultsAttributeIds.size() > 1){
				//Send an email to administrator for duplicate status update
			}
			
			int existingRecordMatches = loincResultDomainMappingService.checkLoincCodeAssociatedTestResults(acceptedTestResultDomainVO.getLoincCode(), testResultsAttributeIds, bindingResult, userObject);
			
			if(existingRecordMatches > 0){
				commonExceptionHandler.inspectDBTransacionResult(-1, "Given Test results are already assoicated to the LOINC code "+loincCode, bindingResult);
				//throw an error to the user
			}else{
				loincResultDomainMappingService.assoicateTestResultsToLoincCode(loincCode, testResultsAttributeIds.get(0), acceptedTestResultDomainVO.getStandardResults(), bindingResult, userObject );
			}
			
			
		}
	}
	
	@Transactional
	public void updateLoincCodeDetails(BindingResult bindingResult, UserObject userObject, LabLoincMappingVO labLoincMappingVO){
		int resultCount = labReportsService.updateLabLoincCodeDetails(bindingResult, userObject, labLoincMappingVO);
		
		if(resultCount > 0 && labLoincMappingVO.getDateValidTill() != null){
			labReportsService.updateLabLocalCodeLoincDetails(labLoincMappingVO, bindingResult, userObject);
		}
		
	}

	@Transactional
	public void changeLoincMultiSpotDocuments(Integer[] documentIds, BindingResult bindingResult, UserObject userObject) {
		
		Map<Object, Object> inputOutputParameters = new HashMap<Object, Object>();
		inputOutputParameters.put("p_document_type", "HIV_LAB");
		inputOutputParameters.put("p_new_doc_id", "");	
		inputOutputParameters.put("p_workerId", userObject.getWorkerId());
		
		
		for (Integer documentId : documentIds) {
			inputOutputParameters.put("p_parent_doc_id", documentId);
			
			commonDocumentService.getNewDocId(inputOutputParameters);
			
			int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");

			EcrlsLabDocVO labDocVO = labDocumentService.getHivLabDoc(documentId, bindingResult, userObject, userObject.getSessionId());
			labDocVO.setDOCUMENT_ID(newDocumentId);
			labDocVO.setOBXLOINCCODE("30361-0");
			labDocVO.setCREATE_UID(Integer.toString(userObject.getWorkerId()));
			labDocVO.setCREATE_DATE(new Date(Calendar.getInstance().getTimeInMillis()));
			labDocVO.setCURRENT_PROCESS_STATUS(CommonConstants.RAW_UNIQUE_RECORD_PROCESS_STATUS);
			
			int resultCount =  manualQuartzJobService.writeRawEcrlsData("HIV_LAB", labDocVO);
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed while updating the loing code for document:"+documentId);
			}
			
			
			resultCount = manualQuartzJobService.writeErrorCode(CommonConstants.RAW_DOCUMENT_EDITED_PROCESS_STATUS, documentId, "HIV_LAB");
			
			if(resultCount == 0){
				throw new DataRetrievalFailureException("Failed to update the old document status. DocumentId: "+documentId);
			}
		}
		
	}
	
	@Transactional
	public void uploadClepHIVSurveyData(String fileName, List<Map<String, Object>> clepHIVSurveyDataList, UserObject userObject) throws SQLException {
		
		clepAuditResultsService.uploadClepHIVSurveyData(fileName, clepHIVSurveyDataList, userObject.getUserId());

		
	}
	
	@Transactional
	public void saveClepLabTestResultData(CLEPLabResultVO clepLabResultVO, BindingResult bindingResult,	UserObject userObject) throws SQLException {
		
		clepAuditResultsService.insertCLEPLabResultData(clepLabResultVO, bindingResult, userObject);

		
	}	
	
	@Transactional
	public void saveLaftReconcileSurveyData(List<LAFT_LAB_RECON_SUMM_REC> surveyDataList, Map<Object, Object> inputData, BindingResult bindingResult,	UserObject userObject ) {
		
		laftCountReconcilleService.insertEditedSurveyData(surveyDataList, inputData, bindingResult, userObject);
		
	}
	
	@Transactional
	public void uploadLaftSurveyResponseData(String fileName, List<Map<String, Object>> laftSurveyResponseDataList, String collectionPeriod, UserObject userObject) throws SQLException {
		
		laftCountReconcilleService.uploadLaftSurveyResponseData(fileName, laftSurveyResponseDataList, collectionPeriod, userObject.getUserId());
		
	}	
}
