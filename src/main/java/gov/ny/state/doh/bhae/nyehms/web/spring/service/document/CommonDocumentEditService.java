package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentEditLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	
@Service
public class CommonDocumentEditService {
	
	
	private static Logger log = LoggerFactory.getLogger(CommonDocumentEditService.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;
	
	@Autowired
	private DocumentEditLabsDAO documentEditLabsDAO;
	
	@Autowired
	private CommonDocLabsDAO commonDocLabsDAO;
	
	
	@Autowired
	private DocumentTransactionHelper documentTransactionHelper;

	public void storeEditRawAndStdDocument(Map<String, String> inputData, int documentId, String documentType,  String SOFT_ERROR_ONLY, BindingResult bindingResult, UserObject userObject) {
		
		
		boolean isError = false;
		try{
			
			List<Map<String, String>> stdToRawColumnsList = commonDocLabsDAO.getStdToRawColumnList(LabCommonUtils.getDocumentType(documentType));   
			
			if(stdToRawColumnsList == null || stdToRawColumnsList.size() == 0){
				
				commonExceptionHandler.inspectDBTransacionResult(-1, "Edit document is not implemented for this document.", bindingResult);
				
			}
			
			Map<String, String> documentColumnMappingObj = LabCommonUtils.convertColumnListMapObj(stdToRawColumnsList, "STD_COLUMN", "RAW_COLUMN");
			
			
			documentTransactionHelper.storeEditedRawAndStdDocument(documentId, documentType, inputData, documentColumnMappingObj, SOFT_ERROR_ONLY, bindingResult, userObject);
			
			
			
			
		} catch (DataAccessException e) {
			isError = true;
			log.error("Document Id   {}",documentId);
			log.error(nyehmsLog+" Failed while storing the edited document " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		} catch (Exception e) {
			isError = true;
			log.error("Document Id   {}",documentId);
			log.error("{}  Failed while storing the edited document  {}", nyehmsLog, e.getMessage());
			commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
		}
		
		if(isError  && (bindingResult.getAllErrors() == null || bindingResult.getAllErrors().size() == 0) ){
			
			commonExceptionHandler.inspectDBTransacionResult(-1, "Error while saving document, please contact the Helpline. ", bindingResult);

		}
		
	}
	
	
	public void writeRawEditedDocData(String documentType, 
			Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject){
		
		int resultcount = 0;
		
		if("STD_LAB".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedStdDocument(rawDocData);
		}else if("AIRS".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedAirsDocument(rawDocData);
		}else if("TB".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedTbDocument(rawDocData);
		}else if("NYC_IJR".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedNycIJRDocument(rawDocData);
		}else if("NDI_NEW".equals(documentType) || "NDI_CAUSE".equals(documentType)){
			//resultcount = documentEditLabsDAO.insertRawEditedNdiCauseDocument(rawDocData);
		}else if("NDI".equals(documentType)){
			
			String DOD_TEXT = (String)rawDocData.get("DOD_TEXT");
			
			if(CommonUtil.isNotNullAndEmpty(DOD_TEXT)){
				
				String[] DODArray = DOD_TEXT.split("/");
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[0]) && !"..".equals(DODArray[0])){
					rawDocData.put("MONTH_DEATH", DODArray[0]);
				}else{
					rawDocData.put("MONTH_DEATH", null);
				}
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[1]) && !"..".equals(DODArray[1])){
					rawDocData.put("DAY_DEATH", DODArray[1]);
				}else{
					rawDocData.put("DAY_DEATH", null);
				}
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[2]) && !"..".equals(DODArray[2])){
					rawDocData.put("YEAR_DEATH", DODArray[2]);
				}else{
					rawDocData.put("YEAR_DEATH", null);
				}
				
				
			}
			
			resultcount = documentEditLabsDAO.insertRawEditedNDIDocument(rawDocData);
			
			
			
		}else if("SSDM".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedSSDMDocument(rawDocData);
		}else if("INS_VITAL".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedInsVitalDocument(rawDocData);
		}else if("CASA".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedCasaDocument(rawDocData);
		}else if("EPRF_OOC".equals(documentType)){
			resultcount = documentEditLabsDAO.insertRawEditedEprfOOCDocument(rawDocData);
		}else if("NYC_DEMO".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertRawEditedNycEharsDocument(rawDocData);
		}else if("NYC_LAB".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertRawEditedNycLabDocument(rawDocData);
		}else if("HIV_LAB".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertRawEditedHIVLabDocument(rawDocData);
		}else if("CLUSTER".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertRawEditedClusterDocument(rawDocData);
		}else if("OLD_CASES".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertRawEditedOldCasesDocument(rawDocData);
		}
		
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Raw ").append(documentType).append(" document  :").append(rawDocData.get("DOCUMENT_ID")).toString());
		}
		
		
	}

	public void writeStdEditedDocData(String documentType,
			Map<Object, Object> stdDocData, int oldDocumentId){
	
		// have to add code to write CASA datato STD layer
		int resultcount = 0;
		if("STD_LAB".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedStdDocument(stdDocData);
		}else if("AIRS".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedAirsDocument(stdDocData);
		}else if("TB".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedTbDocument(stdDocData);
		}else if("NYC_IJR".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedNycIJRDocument(stdDocData);
		}else if("NDI_NEW".equals(documentType) || "NDI_CAUSE".equals(documentType)){
			//resultcount = documentEditLabsDAO.insertStdEditedNdiCauseDocument(stdDocData);
		}else if("NDI".equals(documentType)){
			
			String DOD_TEXT = (String)stdDocData.get("DOD_TEXT");
			
			if(CommonUtil.isNotNullAndEmpty(DOD_TEXT)){
				
				String[] DODArray = DOD_TEXT.split("/");
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[0]) && !"..".equals(DODArray[0])){
					stdDocData.put("MONTH_DEATH", DODArray[0]);
				}else{
					stdDocData.put("MONTH_DEATH", null);
				}
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[1]) && !"..".equals(DODArray[1])){
					stdDocData.put("DAY_DEATH", DODArray[1]);
				}else{
					stdDocData.put("DAY_DEATH", null);
				}
				
				if(CommonUtil.isNotNullAndEmpty(DODArray[2]) && !"..".equals(DODArray[2])){
					stdDocData.put("YEAR_DEATH", DODArray[2]);
				}else{
					stdDocData.put("YEAR_DEATH", null);
				}
				
				
			}
			
			resultcount = documentEditLabsDAO.insertStdEditedNDIDocument(stdDocData);
		}else if("SSDM".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedSSDMDocument(stdDocData);
		}else if("INS_VITAL".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedInsVitalDocument(stdDocData);
		}else if("CASA".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedCasaDocument(stdDocData);
		}else if("EPRF_OOC".equals(documentType)){
			resultcount = documentEditLabsDAO.insertStdEditedEprfOOCDocument(stdDocData);
		}else if("NYC_DEMO".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertStdEditedNycEharsDocument(stdDocData);
		}else if("NYC_LAB".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertStdEditedNycLabDocument(stdDocData);
		}else if("HIV_LAB".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertStdEditedHivLabDocument(stdDocData);
		}else if("CLUSTER".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertStdEditedClusterDocument(stdDocData);
		}else if("OLD_CASES".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertStdEditedOldCasesDocument(stdDocData);
		}else if("TRK_ALIAS".equals(documentType) ){
			resultcount = documentEditLabsDAO.insertEditedTrkAliasDocument(stdDocData);
		}
			
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Std ").append(documentType).append(" document  :").append(stdDocData.get("DOCUMENT_ID")).toString());
		}
		
	}
	
	
	
	public void updatePersonDocAssociationData(
			Map<String, Object> inputParameterMap){
		
		int resultCount = documentEditLabsDAO.updatePersonDocAssociationData(inputParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to Person Doc Association data  :"+inputParameterMap.get("DOCUMENT_ID"));
		}
		
	}
	
	public void updateTriggeringDocumentIdInAssignments(List<Integer> assignmentIds, int newDocumentId){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("assignmentIds", assignmentIds);
		inputParameterMap.put("NEW_DOCUMENT_ID", newDocumentId);
		
		int resultCount = documentEditLabsDAO.updateTriggeringDocumentIdInAssignments(inputParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to update the Triggering document value with new Document Id  :"+newDocumentId);
		}
		
	}
	
	
	public List<Integer> getAssignmentsCreatedForDocument(int documentId){
		return documentEditLabsDAO.getAssignmentsCreatedForDocument(documentId);
	}
	
	
	public void addEditedAssignmentActivity(List<Integer> assignmentIds, UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		
		inputOutputParameterMap.put("comments", "Document Edited");
		inputOutputParameterMap.put("activityType", "AU10");
		
		inputOutputParameterMap.put("assignmentIds", assignmentIds);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		int resultCount =  documentEditLabsDAO.addEditedAssignmentActivity(inputOutputParameterMap);
		
		
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to add the Assignment Activity for the Assignments  ");
		}
		
	}
}



