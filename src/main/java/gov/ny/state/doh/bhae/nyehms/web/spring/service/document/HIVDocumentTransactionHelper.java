package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.ManualQuartzJobService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This class provides helper methods for document functions.
 * 
 * @author vxp06
 */
@Component
public class HIVDocumentTransactionHelper {


	
	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	CommonDocumentEditService commonDocumentEditService;
	@Autowired
	LabDocumentService labDocumentService;
	@Autowired
	ManualQuartzJobService manualQuartzJobService;
	@Autowired
	DocumentTransactionHelper documentTransactionHelper;
	@Autowired
	CommonService commonService;

	@Transactional
	public void editHivDocumentWithNYCData(Integer documentId,
			Map<String, Object> hivData, Map<String, String> documentColumnMappingObj, BindingResult bindingResult,
			UserObject userObject, List<String> successDocuments, List<String> failureDocuments) {

		Integer CURRENT_PROCESS_STATUS_RAW = (Integer)hivData.get("CURRENT_PROCESS_STATUS_RAW");
		
		
		
		
		String documentType = commonDocumentService.getSubDocumentType(documentId);
		
		if(CommonUtil.isNullOrEmpty(documentType)){
			documentType = "HIV_LAB";
		}
		
		String parentDocumentType = LabCommonUtils.getDocumentType(documentType);
		
		Map<Object, Object> inputOutputParameters = new HashMap<>();
		inputOutputParameters.put("p_document_type", parentDocumentType);
		inputOutputParameters.put("p_new_doc_id", "");	
		inputOutputParameters.put("p_parent_doc_id", documentId);
		
		
		try {
			commonDocumentService.getNewDocId(inputOutputParameters);
		} catch (DataAccessException e) {
			throw new DataRetrievalFailureException("Failed to create a new Lab document for "
					+documentId+"        :   "+e.getMessage());
		} 
		int newDocumentId = (Integer)inputOutputParameters.get("p_new_doc_id");
		
		
		hivData.put("DOCUMENT_ID", newDocumentId);
		
		
		if(!documentType.equals(parentDocumentType)){
			
			commonDocumentService.writeDocumentTestType(newDocumentId, documentType);
		}
		
		
		
		
		
		Map<String, Object> originalRawData = labDocumentService.getRawDataAsObject(documentId);
		
		Map<String, Object> originalStdData = labDocumentService.getStdDataAsObject(documentId);
		
		if(originalStdData == null){
			originalStdData = new HashMap<String, Object>();
		}
		
		
		for (String columnName : hivData.keySet()) {
			
			
			if(columnName.indexOf("ZIP") != -1){
				
				String zipCode = (String)hivData.get(columnName);
				
				Map<String, String> zipCodeValues = commonService.getCountyNameByZip(zipCode);
				
				if(zipCodeValues == null){
					
					
					throw new DataRetrievalFailureException("Invalid Zip code  "+zipCode);
					
					
				}else if("PIDZIPCODE".equals(columnName)){
					

					originalRawData.put("PIDSTATE", zipCodeValues.get("STATE_CD"));
					originalRawData.put("PIDCITY", zipCodeValues.get("CITY"));
					originalRawData.put("PIDZIPCODE", zipCode);
					
					
					originalStdData.put("PERSON_CITY", zipCodeValues.get("CITY"));
					originalStdData.put("PERSON_STATE", zipCodeValues.get("STATE_CD"));
					originalStdData.put("PERSON_ZIP", zipCode);
					originalStdData.put("PERSON_COUNTY", zipCodeValues.get("COUNTY_FIPS_CD"));
					
					
					
					
					
					
				}else if("ZLRPROVIDERZIPCODE".equals(columnName)){
					
					originalRawData.put("ZLRPROVIDERSTATE", zipCodeValues.get("STATE_CD"));
					originalRawData.put("ZLRPROVIDERCITY", zipCodeValues.get("CITY"));
					originalRawData.put("ZLRPROVIDERZIPCODE", zipCode);
					
					
					originalStdData.put("PROV_CITY", zipCodeValues.get("CITY"));
					originalStdData.put("PROV_STATE", zipCodeValues.get("STATE_CD"));
					originalStdData.put("PROV_ZIP", zipCode);
					originalStdData.put("PROV_COUNTY", zipCodeValues.get("COUNTY_FIPS_CD"));
					
					
				}else if("ZLRFACILITYZIPCODE".equals(columnName)){
					
					originalRawData.put("ZLRFACILITYSTATE", zipCodeValues.get("STATE_CD"));
					originalRawData.put("ZLRFACILITYCITY", zipCodeValues.get("CITY"));
					originalRawData.put("ZLRFACILITYZIPCODE", zipCode);
					
					originalStdData.put("FACILITY_CITY", zipCodeValues.get("CITY"));
					originalStdData.put("FACILITY_STATE", zipCodeValues.get("STATE_CD"));
					originalStdData.put("FACILITY_ZIP", zipCode);
					originalStdData.put("FACILITY_COUNTY", zipCodeValues.get("COUNTY_FIPS_CD"));
					
				}
				
			}else if(columnName.indexOf("ADDRESS") != -1){
				
				
				String rawAddress = (String)hivData.get(columnName);
						
				String stdAddress = commonService.getImprovedStandardAddress(rawAddress);
				
				
				if("PIDADDRESSLINE1".equals(columnName)){
					
					originalRawData.put("PIDADDRESSLINE1", rawAddress);
					originalRawData.put("PIDADDRESSLINE2", null);
					
					originalStdData.put("PERSON_ADDRESS", stdAddress);
					
					
				}else if("ZLRPROVIDERADDRESSLINE1".equals(columnName)){
					
					originalRawData.put("ZLRPROVIDERADDRESSLINE1", rawAddress);
					originalRawData.put("ZLRPROVIDERADDRESSLINE2", null);
					
					originalStdData.put("PROV_ADDRESS", stdAddress);
					
				}else if("ZLRFACILITYADDRESSLINE1".equals(columnName)){
					originalRawData.put("ZLRFACILITYADDRESSLINE1", rawAddress);
					originalRawData.put("ZLRFACILITYADDRESSLINE2", null);
					
					originalStdData.put("FACILITY_ADDRESS", stdAddress);
				}
				
				
				
			}else{
				
				originalRawData.put(columnName, hivData.get(columnName));
				
				
				if(documentColumnMappingObj.get(columnName) != null){
					
					originalStdData.put(documentColumnMappingObj.get(columnName), hivData.get(columnName));
				}
				
				
				
				
			}
			
			
			
		}
		
		originalRawData.put("DOCUMENT_ID", newDocumentId);
		originalStdData.put("DOCUMENT_ID", newDocumentId);
		
		
		
		if(CURRENT_PROCESS_STATUS_RAW == 1 || CURRENT_PROCESS_STATUS_RAW == 3 || CURRENT_PROCESS_STATUS_RAW == 11 || CURRENT_PROCESS_STATUS_RAW == 6 || CURRENT_PROCESS_STATUS_RAW == 15){
			
			labDocumentService.insertRawEditedHIVLabDocumentWithUpdateData("HIV_LAB", originalRawData, documentId, userObject);
			
			commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			successDocuments.add(documentId+"  : Successfully edited the HIV Document in RAW table. ");
			
		}else if(CURRENT_PROCESS_STATUS_RAW == 4 || CURRENT_PROCESS_STATUS_RAW == 7){
			
			originalRawData.put("CURRENT_PROCESS_STATUS", 3);
			
			labDocumentService.insertRawEditedHIVLabDocumentWithUpdateData("HIV_LAB", originalRawData, documentId, userObject);
			
			commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			manualQuartzJobService.disableFlagInErrorTable(documentId);
			
			successDocuments.add(documentId+"  : Successfully edited the HIV Document in RAW table and disabled the flag in Error table. ");
			
		}else if(CURRENT_PROCESS_STATUS_RAW == 30 ){
			
			originalRawData.put("CURRENT_PROCESS_STATUS", 11);
			
			labDocumentService.insertRawEditedHIVLabDocumentWithUpdateData("HIV_LAB", originalRawData, documentId, userObject);
			
			commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			manualQuartzJobService.disableFlagInErrorTable(documentId);
			
			successDocuments.add(documentId+"  : Successfully edited the HIV Document in RAW table and disabled the Multi step error flag in Error table. ");
			
		}else if(CURRENT_PROCESS_STATUS_RAW == 2){
			
			originalRawData.put("CURRENT_PROCESS_STATUS", 1);
			
			labDocumentService.insertRawEditedHIVLabDocumentWithUpdateData("HIV_LAB", originalRawData, documentId, userObject);
			
			commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			successDocuments.add(documentId+"  : Successfully edited the HIV Document in RAW table and changed the Current Process status to 1 from Duplicate Status ");
			
		}else if(CURRENT_PROCESS_STATUS_RAW == 5){
			
			labDocumentService.insertRawEditedHIVLabDocumentWithUpdateData("HIV_LAB", originalRawData, documentId, userObject);
			
			labDocumentService.insertStdEditedHivLabDocumentWithUpdateData("HIV_LAB", originalStdData, documentId);
			
			
			
			
			
			
			if(CommonConstants.TEST_TYPE_RESISTANCE.equals(documentType)){
				
				labDocumentService.insertStandardHIVResistanceResultFromOldDocument(newDocumentId, documentId);
			}
			
			commonDocumentService.updateRawDocumentStatus(documentId, userObject, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			commonDocumentService.updateStdDocumentStatus(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS);
			
			Map<Object, Object> personDocData =  commonDocumentService.getPersonDocData(documentId);
			
			if(personDocData == null){
				successDocuments.add(documentId+" :  Successfully edited the HIV Document in RAW and STD table");
				
				return;
			}
			
			
			documentTransactionHelper.deleteStdDocumentExistence(documentId, parentDocumentType, CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS, "DISASSOC_EDIT",  userObject);
			
			commonDocumentService.moveStdDocumentToDocAttrTables((String)personDocData.get("STATENO"), newDocumentId, userObject, parentDocumentType, null);
			
		
			BigDecimal PS_ASSIGN_STATUS = (BigDecimal)personDocData.get("PS_ASSESS_STATUS");
			BigDecimal SURV_ASSESS_STATUS = (BigDecimal)personDocData.get("SURV_ASSESS_STATUS");
			BigDecimal SURV_ASSIGNMENT_ID = (BigDecimal)personDocData.get("SURV_ASSIGNMENT_ID");
			Map<String, Object> inputParameterMap = new HashMap<>();
			if(PS_ASSIGN_STATUS != null && PS_ASSIGN_STATUS.intValue() != 0){
				commonDocumentService.updateTriggeringDocumentIdInNyehmsMessage(documentId, newDocumentId);
				inputParameterMap.put("PS_ASSIGN_STATUS", PS_ASSIGN_STATUS);
			}
			
			String DISCARD_STATUS = (String)personDocData.get("DISCARD_STATUS");
			
			if(CommonUtil.isNotNullAndEmpty(DISCARD_STATUS)){
				
				commonDocumentService.createNonCaseWithNewDocument(documentId, newDocumentId);
				
				inputParameterMap.put("DISCARD_STATUS", DISCARD_STATUS);
			}
			
			if(SURV_ASSESS_STATUS != null && SURV_ASSESS_STATUS.intValue() == 1){
				inputParameterMap.put("SURV_ASSESS_STATUS", SURV_ASSESS_STATUS);
				inputParameterMap.put("SURV_ASSIGNMENT_ID", SURV_ASSIGNMENT_ID);
				List<Integer> assignmentIds = commonDocumentEditService.getAssignmentsCreatedForDocument(documentId);
				if(assignmentIds == null || assignmentIds.size()  == 0){
					throw new DataRetrievalFailureException("No Assignments found for the Triggering Document  "+documentId);
				}
				commonDocumentEditService.updateTriggeringDocumentIdInAssignments(assignmentIds, newDocumentId);
				commonDocumentEditService.addEditedAssignmentActivity(assignmentIds, userObject);
			}
			if(inputParameterMap.size() > 0){
				inputParameterMap.put("DOCUMENT_ID", newDocumentId);
				commonDocumentEditService.updatePersonDocAssociationData(inputParameterMap);
			}
				
				
			successDocuments.add(documentId+"   : Successfully edited the HIV Document in RAW, STD table and Attribute tables");
			
		}else{
			throw new DataRetrievalFailureException("Invalid Current Process status in Raw table");
		}
	}		
	
}
