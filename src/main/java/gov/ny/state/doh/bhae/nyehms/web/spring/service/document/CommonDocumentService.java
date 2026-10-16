package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.ADAPDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CommonDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DeathDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DoccsDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.LabDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.MedicaidDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.NycDeathDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.PRFDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.TBDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycEharsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.NycEharsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.exception.NyehmsException;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	
@Service
public class CommonDocumentService {
	
	
	private static Logger log = LoggerFactory.getLogger(CommonDocumentService.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired private CommonExceptionHandler    commonExceptionHandler;
	@Autowired private CommonDocLabsDAO          commonDocLabsDAO;	
	@Autowired private NycEharsDocRepository nycEharsDocRepository;
	@Autowired private CommonDAO          commonDAO;
	@Autowired private DocumentDAO               documentDAO;
	@Autowired private ConnectionDAO             connectionDAO;
	@Autowired private LabDocumentDAO            labDocumentDAO;
	@Autowired private ADAPDocumentDAO           adapDocumentDAO;
	@Autowired private MedicaidDocumentDAO       medicaidDocumentDAO;
	@Autowired private DeathDocumentDAO          deathDocumentDAO;
	@Autowired private NycDeathDocumentDAO       nycDeathDocumentDAO;
	@Autowired private DoccsDocumentDAO          doccsDocumentDAO;
	@Autowired private PRFDocumentDAO            prfDocumentDAO;
	@Autowired private TBDocumentDAO            tbDocumentDAO;
	
	public List<JSONObject>  getCodedArvs()
	{
		List<JSONObject> arvMedicationsList = commonDocLabsDAO.getCodedArvs();
		return arvMedicationsList;
	}

	public List<JSONObject>  getCodedHivFacilityTypes()
	{
		List<JSONObject> hivFacilityTypes = commonDocLabsDAO.getCodedHivFacilityTypes();
		return hivFacilityTypes;
	}
	
	public List<JSONObject>  getCodedInvestigationStatus(){
		List<JSONObject> investigationStatus = commonDocLabsDAO.getCodedInvestigationStatus();
		return investigationStatus;
	}
	
	public List<JSONObject>  getDispositionCodeValues(boolean filterRows){
		
		Map<String, Boolean> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("filterRows", filterRows);
		
		List<JSONObject> dispositionCodes = commonDocLabsDAO.getDispositionCodeValues(inputParameterMap);
		
		return dispositionCodes;
	}
	
	
	public List<JSONObject>  getCOSCodesData(){
		
		List<JSONObject> cOSCodes = commonDocLabsDAO.getCOSCodesData();
		
		return cOSCodes;
	}
	
	public List<JSONObject>  getAddressTypes(){
		
		List<JSONObject> addressTypes = commonDocLabsDAO.getAddressTypes();
		
		return addressTypes;
	}
	
	public List<JSONObject> getCodedSexAtBirthValues(){
		List<JSONObject> codedSexAtBirthValues = commonDocLabsDAO.getCodedSexAtBirthValues();
		
		return codedSexAtBirthValues;
	}
	
	public List<JSONObject> getCodedYesNoUnkownValues(){
		List<JSONObject> codedYesNoUnkownValues = commonDocLabsDAO.getCodedYesNoUnkownValues();
		
		return codedYesNoUnkownValues;
	}
	
	public List<JSONObject> getCodedPrimaryLanguageValues(){
		List<JSONObject> codedPrimaryLanguageValues = commonDocLabsDAO.getCodedPrimaryLanguageValues();
		
		return codedPrimaryLanguageValues;		
	}
	
	public List<JSONObject> getCodedYesNoRefUnkownValues(){
		List<JSONObject> codedYesNoRefUnkownValues = commonDocLabsDAO.getCodedYesNoRefUnkownValues();
		
		return codedYesNoRefUnkownValues;
	}
	
	public List<JSONObject> getCodedTelephoneTypeValues(){
		List<JSONObject> codedTelephoneTypeValues = commonDocLabsDAO.getCodedTelephoneTypeValues();

		return codedTelephoneTypeValues;
	}
	
	public List<JSONObject> getCodedFacilityTypeValues(){
		List<JSONObject> codedFacilityTypeValues = commonDocLabsDAO.getCodedFacilityTypeValues();

		return codedFacilityTypeValues;
	}
	
	public List<JSONObject>  getPartnerAssistanceStatus(){
		
		List<JSONObject> partnerAssistanceStatusList = commonDocLabsDAO.getPartnerAssistanceStatus();
		
		return partnerAssistanceStatusList;
	}
	
	public List<JSONObject>  getCodedMaritalStatus(){
		
		List<JSONObject> codedMaritalStatusList = commonDocLabsDAO.getCodedMaritalStatus();
		
		return codedMaritalStatusList;
	}
	

	public List<JSONObject> getFileNamesForDocumentType(String _documentType){
		
		List<JSONObject> fileNamesObj = commonDocLabsDAO.getFileNamesForDocumentType(_documentType);
		
		
		return fileNamesObj;
	
	}
	
	public void deleteDocFromDocLocator(int docId,
			UserObject userObject){
		int resultCount = commonDocLabsDAO.deleteDocFromDocLocator(docId);
		

		if(resultCount == 0){
			throw new DataRetrievalFailureException(docId+"  : Failed to delete the document Document Locator ");
		}
		
	}
	
	public List<PSMessageVO> getPSMessageDetails(int docId, String documentType){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("documentId", docId);
		inputOutputParameterMap.put("documentType", documentType);
		
		return commonDocLabsDAO.getPSMessageDetails(inputOutputParameterMap);
		
		
		
	}

	
	
	public Map<String, String> getEharsCodedCountryValues(){
		
		Connection connection = connectionDAO.getSqlServerConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;

		Map<String, String> countryCodes=new LinkedHashMap<>();
		
		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("SQL server database Connection is available {}",connection);
				}
				statement = connection.createStatement();
				
				resultSet = statement.executeQuery("select country_cd, descr from country_code order by descr");
				
				while(resultSet.next()){
					countryCodes.put(resultSet.getString(1), resultSet.getString(2));
				}
				
				if(log.isDebugEnabled()){
					log.debug("Ehars Country codes  : {}",countryCodes);
				}
			}
		}catch (SQLException sqlException) {

			log.error("Exception while retrieving the Ehars data from SQL Server {}", sqlException.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException sqlException) {
				log.error("Exception while closing the connection {}", sqlException.getMessage());
			}
		}
		return countryCodes;
	}
	
	public void moveStdDocumentToDocAttrTables(
			String stateNo, int documentId,
			UserObject userObject, String documentType, String origin){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		
		
		inputOutputParameterMap.put("p_stateno", commonDAO.getCurrentActiveStateNo(stateNo));
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
		inputOutputParameterMap.put("p_documentId", documentId);
		inputOutputParameterMap.put("p_document_type", documentType);
		inputOutputParameterMap.put("p_called_from", origin);
		
		
		commonDocLabsDAO.moveStdDocumentToDocAttrTables(inputOutputParameterMap);
		
		Integer statusCode=(Integer)inputOutputParameterMap.get("p_code_out");
		
		if(statusCode != 0){
			
			log.error("Exception occured while inserting Std document in Person tables : {}",inputOutputParameterMap.get("p_msg_out"));
			
			throw new DataRetrievalFailureException("Failed while associating the document to STATENO :  "+stateNo);
		}
		
	}
	
	@Transactional
	public void deleteEntryInPersonDocAssociation(int document_ID, String documentType, String associationStatus, UserObject userObject){
			
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("associationStatus", associationStatus);
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
		inputOutputParameterMap.put("p_documentId", document_ID);
		
		String stateno = null;
		
		if("DISASSOC_DEL".equals(associationStatus)){
			
			Map<String, Object> statenoDetails = commonDocLabsDAO.getStatenoDetailsByDocumentId(document_ID);
			
			//, 
			
			stateno = (String)statenoDetails.get("STATENO").toString();
			
			if(statenoDetails.get("PS_ASSESS_STATUS") != null){
				
				commonDocLabsDAO.deletePsMessageForTriggeringDocument(inputOutputParameterMap);
				
				commonDocLabsDAO.deleteIndicatePsMessage(inputOutputParameterMap);
				
				
				
			}
			
			
		}
		
		List<Integer> documentIds = new ArrayList<>();
		if("DOCCS".equals(documentType)){
			
			documentIds = doccsDocumentDAO.getDinDoccsIds(document_ID);
			
		}
		
		documentIds.add(document_ID);
		
		inputOutputParameterMap.put("p_documentId_list", documentIds);
		
		commonDocLabsDAO.deleteEntryInPersonDocAssociation(inputOutputParameterMap);
		
		
		
		if("DISASSOC_DEL".equals(associationStatus)){
							
			inputOutputParameterMap.put("p_stateno", stateno);
			int resultCount = commonDocLabsDAO.checkAndInactiveStateno(inputOutputParameterMap);
			
			if(resultCount > 0){
				
				
				inputOutputParameterMap.put("p_stateno", stateno);
				inputOutputParameterMap.put("p_reviewComments", stateno+" Inactivated");
				
				commonDocLabsDAO.rejectMergePairForInactiveStateno(inputOutputParameterMap);
				
				commonDocLabsDAO.rejectBadMergeStatneForInactiveStateno(inputOutputParameterMap);
				
				log.error("Inactivating the Stateno {}",stateno);
				
				
				
				
			}
		}
		
	}
	
	public void deleteEntryInDocumentPersonAttr(int documentId,
			UserObject userObject){
		
		commonDocLabsDAO.deleteEntryInDocumentPersonAttr(documentId);
		
	}
	

	public void deleteEntryInDocumentRiskTable(int documentId,
			UserObject userObject){
		commonDocLabsDAO.deleteEntryInDocumentRiskTable(documentId);
	}

	public void deleteEntryInDocumentRace(int documentId, UserObject userObject){
		
		commonDocLabsDAO.deleteEntryInDocumentRace(documentId);
	}
	
	public String getColumnDataFromTable(String tableName, String targetColumn,
			String refColumn, int refValue){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("targetColumn", targetColumn);
		inputOutputParameterMap.put("refColumn", refColumn);
		inputOutputParameterMap.put("refValue", refValue);
		
		return commonDocLabsDAO.getColumnDataFromTable(inputOutputParameterMap);
		
	}
	
	public void updateDocumentPsAssignStatus(int pS_ASSIGN_STATUS,
			int newDocumentId){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("documentId", newDocumentId);
		inputOutputParameterMap.put("pS_ASSIGN_STATUS", pS_ASSIGN_STATUS);
		
		
		int resultCount = commonDocLabsDAO.updateDocumentPsAssignStatus(inputOutputParameterMap);
		
		if(resultCount == 0){
			throw new DataRetrievalFailureException("Failed to PS Assign status for the new Document  :"+newDocumentId);
		}
		
	}

	public void updateTriggeringDocumentIdInNyehmsMessage(int documentId, int newDocumentId){
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("oldDocumentId", documentId);
		inputOutputParameterMap.put("newDocumentId", newDocumentId);
		
		commonDocLabsDAO.updateTriggeringDocumentIdInNyehmsMessage(inputOutputParameterMap);
		
//		
	}
	
	
	public void updateRawDocumentStatus(
			int currentDocumentId,
			UserObject userObject,
			String documentType,
			int statusCode){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		String tableName = LabCommonUtils.getRawTableNameFast(documentType);
		
		if(CommonUtil.isNotNullAndEmpty(tableName)){
			inputOutputParameterMap.put("tableName", tableName);
			inputOutputParameterMap.put("statusCode", statusCode);
			inputOutputParameterMap.put("document_ID", currentDocumentId);
			int resultcount = commonDocLabsDAO.updateRawDocumentStatusCode(inputOutputParameterMap);
			if(resultcount == 0){
				log.error("Failed to update the Raw document deleted status {}",currentDocumentId);
				throw new DataRetrievalFailureException(
						new StringBuilder("Failed to update the ")
						.append(statusCode)
						.append(" status code for the Raw ")
						.append(documentType)
						.append(" document  :")
						.append(currentDocumentId)
						.toString());
			}
		}
	}
	
	
	public void updateRawDocumentStatus(
			int currentDocumentId,
			String tableName,
			int statusCode){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("document_ID", currentDocumentId);
		int resultcount = commonDocLabsDAO.updateRawDocumentStatusCode(inputOutputParameterMap);
		if(resultcount == 0){
			throw new DataRetrievalFailureException(
					new StringBuilder("Failed to update CURRENT_STATUS_CODE in "
							+ tableName + " for DOCUMENT_ID = " 
							+ currentDocumentId).toString());
		}
	}

	
	public void deleteDocumentFromStdTable(String parentDocumentType, int documentId, UserObject userObject){
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		String tableName = LabCommonUtils.getStdTableName(parentDocumentType);
		inputOutputParameterMap.put("tableName", tableName);
		inputOutputParameterMap.put("document_ID", documentId);
		
		
		if("STD_HIV_ECLRS_DOC".equals(tableName)){
			labDocumentDAO.deleteResistanceData(documentId);
		}
		
		commonDocLabsDAO.deleteDocumentFromStdTable(inputOutputParameterMap);

	}
	
	
	public void updateStdDocumentStatus(int currentDocumentId, String documentType,
			int statusCode){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("tableName", LabCommonUtils.getStdTableName(documentType));
		inputOutputParameterMap.put("statusCode", statusCode);
		inputOutputParameterMap.put("document_ID", currentDocumentId);
		
		int resultcount = commonDocLabsDAO.updateStdDocumentStatusCode(inputOutputParameterMap);
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to update the ").append(statusCode).append(" status code for the Std ").append(documentType).append(" document  :").append(currentDocumentId).toString());
		}
	}
	
	
	
	public Map<Object, Object> getDocData(int documentId, String tableIdentification,
			String documentType){
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		String tableName = null;
		
		if("RAW".equals(tableIdentification)){
			tableName = LabCommonUtils.getRawTableName(documentType);
		}else{
			tableName = LabCommonUtils.getStdTableName(documentType);
		}
		
		if(CommonUtil.isNotNullAndEmpty(tableName)){
			inputOutputParameterMap.put("tableName", tableName);
			
			inputOutputParameterMap.put("document_ID", documentId);
			
			return commonDocLabsDAO.getDocData(inputOutputParameterMap);
		}else{
			return null;
		}
		
		
	}
	
	@Transactional
	public void writeRawDocData(String documentType, 
			Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject){
		
		int resultcount = 0;
		
		if("HIV_LAB".equals(documentType)){
			resultcount = labDocumentDAO.insertRawHivDocument(rawDocData);
		}else if("STD_LAB".equals(documentType)){
			resultcount = labDocumentDAO.insertRawStdDocument(rawDocData);
		}else if("DTH".equals(documentType)){
			resultcount = deathDocumentDAO.insertRawDTHDocument(rawDocData);
		}else if("NYC_DTH".equals(documentType)){
			resultcount = nycDeathDocumentDAO.insertNycRawDTHDocument(rawDocData);
		}else if("ADAP".equals(documentType)){
			resultcount = adapDocumentDAO.insertRawADAPDocument(rawDocData);
/*		}else if("AIRS".equals(documentType)){
			resultcount = aIRSDocumentDAO.insertRawAirsDocument(rawDocData);*/
		}else if("TB".equals(documentType)){
			resultcount = tbDocumentDAO.insertRawEditedTBDocument(rawDocData);
		}else if("MED".equals(documentType) || "INS".equals(documentType)){
			resultcount = medicaidDocumentDAO.insertRawMedDocument(rawDocData);
			if(resultcount > 0){
				
				int documentId = (Integer)rawDocData.get("DOCUMENT_ID");
				
				Map<Object, Object> inputOutputParameterMap = new HashMap<>();
				
				inputOutputParameterMap.put("oldDocumentId", oldDocumentId);
				inputOutputParameterMap.put("newDocumentId", documentId);
				inputOutputParameterMap.put("createId", userObject.getWorkerId());
				
				medicaidDocumentDAO.insertRawMedicaidArvsData(inputOutputParameterMap);
				
				
				rawDocData.put("MEDICAID_DOC_ID", documentId);
				
				resultcount = medicaidDocumentDAO.insertRawMedProvidersData(rawDocData);
				
			}
		}else if("PRF".equals(documentType)){
			resultcount = prfDocumentDAO.insertRawPRFOOSDocument(rawDocData);
			
			if(resultcount > 0){
				
				

				Integer documentId = (Integer)rawDocData.get("DOCUMENT_ID");
				
				Map<Object, Object> inputOutputParameterMap = new HashMap<>();
				
				inputOutputParameterMap.put("oldDocumentId", oldDocumentId);
				inputOutputParameterMap.put("newDocumentId", documentId);
				
				
				prfDocumentDAO.storeRawPrfPartnersOOSDocument(inputOutputParameterMap);
				
			}
			
		}
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Raw ").append(documentType).append(" document  :").append(rawDocData.get("DOCUMENT_ID")).toString());
		}
		
		
	}

	@Transactional
	public void writeStdDocData(String documentType,
			Map<Object, Object> stdDocData, int oldDocumentId){
	
		// have to add code to write CASA datato STD layer
		int resultcount = 0;
		if("HIV_LAB".equals(documentType)){
			resultcount = labDocumentDAO.insertStdHivDocument(stdDocData);
		}else if("STD_LAB".equals(documentType)){
			resultcount = labDocumentDAO.insertStdStdDocument(stdDocData);
		}else if("DTH".equals(documentType)){
			resultcount = deathDocumentDAO.writeStdDocData(stdDocData);
		}else if("NYC_DTH".equals(documentType)){
			resultcount = nycDeathDocumentDAO.writeNycStdDeathDocData(stdDocData);
		}else if("ADAP".equals(documentType)){
			stdDocData.put("FLAG_CHOSEN_PROVIDER", "RX");
			resultcount = adapDocumentDAO.writeStdDocData(stdDocData);
/*		}else if("AIRS".equals(documentType)){
			resultcount = aIRSDocumentDAO.insertStdAirsDocument(stdDocData);*/
		}else if("TB".equals(documentType)){
			resultcount = tbDocumentDAO.insertStdEditedTBDocument(stdDocData);
		}else if("MED".equals(documentType) || "INS".equals(documentType)){
			resultcount = medicaidDocumentDAO.writeStdDocData(stdDocData);
			if(resultcount > 0){
				
				int documentId = (Integer)stdDocData.get("DOCUMENT_ID");
				
				resultcount = medicaidDocumentDAO.storeRawMedicaidProvidersDataInStd(documentId);
			}
		}else if("PRF".equals(documentType)){
			resultcount = prfDocumentDAO.insertStdPRFOOSDocument(stdDocData);
			
			if(resultcount > 0){
				
				

				Integer documentId = (Integer)stdDocData.get("DOCUMENT_ID");
				
				
				prfDocumentDAO.writeRawPRFPartnerDataToStdTable(documentId);
				
			}
		}
		
		if(resultcount == 0){
			throw new DataRetrievalFailureException(new StringBuilder("Failed to insert the Std ").append(documentType).append(" document  :").append(stdDocData.get("DOCUMENT_ID")).toString());
		}
		
	}
	
	public void updateSurvellianceAssignmentStatus(int documentId, int assignProcess,
			UserObject userObject, BindingResult bindingResult){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("documentId", documentId);
		inputOutputParameterMap.put("assignProcess", assignProcess);
		inputOutputParameterMap.put("p_user_id", userObject.getWorkerId());
		
		int resultcount = commonDocLabsDAO.updateSurvellianceAssignmentStatus(inputOutputParameterMap);
		
		if(resultcount == 0){
			String errorMessage = "Failed to update the SURV_ASSES_STATUS for :"+documentId;
			log.error(errorMessage);
			throw new DataRetrievalFailureException(errorMessage);
		}else if(assignProcess == 11){
			commonExceptionHandler.inspectDBTransacionResult(0, "Document moved to OOS Follow-Up bucket." , bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the document status" , bindingResult);
		}
		
	}
	

	public List<String> getDocumentTypes(UserObject userObject){
		
		
		Map<String, Object> map = new HashMap<>();

		map.put("p_module_id", 16);
		map.put("p_worker_id", userObject.getWorkerId());
		
		return commonDocLabsDAO.getDocumentTypes(map);
	}
	
	public Map<Object, Object> getPersonDocData(int documentId){
		return commonDocLabsDAO.getPersonDocData(documentId);
	}
	
	public Map<String, String> getStatenoAndSourceCodeFromStdDocData(String parentDocumentType, int documentId, UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		inputOutputParameterMap.put("documentId", documentId);
		
		Map<String, String> stdTableFieldObj = LabCommonUtils.getStdReferenceColumnNames(parentDocumentType); 
		
		inputOutputParameterMap.put("tableName", LabCommonUtils.getStdTableName(parentDocumentType));
		
		if(stdTableFieldObj != null){
			inputOutputParameterMap.put("STATENO", stdTableFieldObj.get("MATCHED_STATENO"));
			inputOutputParameterMap.put("SOURCE_ID", stdTableFieldObj.get("SOURCE_ID"));
		}
		
		
		return commonDocLabsDAO.getStatenoAndSourceCodeFromStdDocData(inputOutputParameterMap);
	}
	
	public NycEharsDocData getNycEHARSDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		
		return nycEharsDocRepository.findDocument(docId);
		
	}
	
	public NYCLabDocVO getNycLabDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id){
		
		return commonDocLabsDAO.getNycLabDoc(docId);
		
	}
	
	@Transactional
	public void updateDocumentsStatus(Long[] documentIds, String documentType,
			int documentStatus, BindingResult bindingResult,
			UserObject userObject){
		
		
		String rawTableName = LabCommonUtils.getRawTableName(documentType);
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();
		
		
		inputOutputParameterMap.put("rawTableName", rawTableName);
		
		inputOutputParameterMap.put("documentIds", documentIds);
		
		inputOutputParameterMap.put("processStatus", documentStatus);
		
		inputOutputParameterMap.put("workerId", userObject.getUserId());
		
		
		commonDocLabsDAO.updateDocumentsStatus(inputOutputParameterMap);
		
		
		if(documentStatus == CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS){
			
			commonDocLabsDAO.inActivateDocument(inputOutputParameterMap);
			
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the document(s)" , bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the document(s) status" , bindingResult);	
		}
		
		
		
		
		
		
		
		
		
	}
	
	public int writeDocumentTestType(int document_ID, String testType) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("documentID", document_ID);
		inputOutputParameterMap.put("testType", testType);

		int resultCount = documentDAO
				.writeDocumentTestType(inputOutputParameterMap);

		if (resultCount == 0) {
			throw new DataRetrievalFailureException(
					"Unable to update the Test Type " + testType);
		}
		return resultCount;
	}
	
	

	public List<String> getUnitsOfMeasurement(){
		return commonDocLabsDAO.getUnitsOfMeasurement();
	}
	
	public int updateRIDRDocumentWithAcrf(String ridrDocumentId, int acrfDocId, UserObject userObject){
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<>();

		inputOutputParameterMap.put("acrfDocId", acrfDocId);
		inputOutputParameterMap.put("ridrDocumentId", ridrDocumentId);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		int resultCount  = commonDocLabsDAO.updateRIDRDocumentWithAcrf(inputOutputParameterMap);
		
		return resultCount; 
	}
	
	
	
	 /**
     * This method calls the database procedure to create a new document id.
     *
     * @param map
     * @throws NyehmsException
     * @throws DataAccessException
     */
	public void getNewDocId(Map<Object,Object> map) {	
		log.debug(nyehmsLog+" Getting New Doc ID...");
		documentDAO.getNewDocId(map);
		log.debug("{} After Getting New Doc ID call, Doc ID is: {}",nyehmsLog, map.get("p_new_doc_id"));	
		if ((Integer) map.get("p_return_code") == -1) {
			throw new DataRetrievalFailureException((String) map.get("p_msg_out"));
		}
	}
	
	public String validateState(String _state, List<String> errors, List<Integer> errorCodes, String _referer) {
		String _tempVariable = _state;
		
		_state = _state.replaceAll("[^a-zA-Z ]", "").trim();

		if (_state.length() > 2) {
			_state = commonDAO.getStateValue(_state.toUpperCase());

			if (_state == null || "".equals(_state)) {
				if (errors != null) {
					errorCodes.add(46);
					errors.add("Invalid " + _referer + "  State(" + _tempVariable + ")");
				}
				return "";
			} else {
				return _state;
			}
		}

		return _state;
	}
	
	public String getSubDocumentType(Integer documentId){
		return commonDocLabsDAO.getSubDocumentType(documentId);
	}
	
	
	public void createNonCaseWithNewDocument(int documentId, int newDocumentId){
		Map<String, Integer> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("oldDocumentId", documentId);
		inputParameterMap.put("newDocumentId", newDocumentId);
		
		commonDocLabsDAO.createNonCaseWithNewDocument(inputParameterMap);
		
	}
	
	public int checkForActiveDocumentInPersonTables(int documentId){
		
		return commonDocLabsDAO.checkForActiveDocumentInPersonTables(documentId);
	}
	
	
	public List<Map<String, String>> getHIVCategories(){
		return commonDocLabsDAO.getHIVCategories();
	}

	public List<Map<String, String>> getAIDSCategories(){
		return commonDocLabsDAO.getAIDSCategories();
	}
	
	

	public List<JSONObject> getCodedProfessionValues(){
		List<JSONObject> codedProfessionValues = commonDocLabsDAO.getCodedProfessionValues();

		return codedProfessionValues;
	}
	
	public Map<String, Long> getTriggeringDocumentAddressIds(int newDocumentId){
		return commonDocLabsDAO.getTriggeringDocumentAddressIds(newDocumentId);
	}
	
	public List<Integer> getOpenAssignmentsForTriggeringDocIds(int newDocumentId){
		return commonDocLabsDAO.getOpenAssignmentsForTriggeringDocIds(newDocumentId);
	}
	
	public Map<String, Long> getAssignmentAddessIds(Integer assignmentId){
		return commonDocLabsDAO.getAssignmentAddessIds(assignmentId);
	}
	
	public List<Map<String, String>> getPSMessageReferenceData(String columnName){
		return commonDocLabsDAO.getPSMessageReferenceData(columnName);
	}
	
	public String getEharsStateno(String stateno){
		return commonDAO.getEharsStateno(stateno);
	}
	
	public Map<String, String> getNYCStatenoDetails(String stateno1){
		return commonDAO.getNYCStatenoDetails(stateno1);
	}
	
	public Map<String, Object> getDiscardedDocumentDetails(int docId){
		return commonDocLabsDAO.getDiscardedDocumentDetails(docId);
	}
	
	public PersonViewVO getClericalDocumentAsPerson(int documentId, String documentType) {
		return commonDocLabsDAO.getClericalDocumentAsPerson(documentId);
	}
	
	public void updateRiaLabDocument(int documentId, int newDocumentId) {
		Map<String, Integer> inputMap = new HashMap<>();
		inputMap.put("OLD_DOCUMENTID", documentId);
		inputMap.put("NEW_DOCUMENTID", newDocumentId);
		commonDocLabsDAO.updateRiaLabDocument(inputMap);
	}

	public List<JSONObject> getLAFTReasonslist() {
		
		List<JSONObject> getLAFTReasonList = commonDocLabsDAO.getLAFTReasonslist();
		
		return getLAFTReasonList;
	}

	public List<JSONObject> getCodedSexualOrientationValues() {
		List<JSONObject> codedSexualOrientationValues = commonDocLabsDAO.getCodedSexualOrientationValues();
		
		return codedSexualOrientationValues;
	}

	public List<JSONObject> getCodedSexualOrientationPrfValues() {
		List<JSONObject> codedSexualOrientationValues = commonDocLabsDAO.getCodedSexualOrientationPrfValues();
		
		return codedSexualOrientationValues;
	}

	public List<JSONObject> getExpandedRaceList(){
		return commonDocLabsDAO.getExpandedRaceList();
	}

}



