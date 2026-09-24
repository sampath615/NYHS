package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;


import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCEHARSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

/*
 * ACRF JSP is treated as 3 parts for all CRUD operations. 
 * One is ACRF Doc table itself 2nd is ACRF Labs table and 3rd ACRF CD4 Table.
 * So we define CRUD (only select, insert, update)) operation separately for three tables even though 
 * it is single operation from user view.
 * This is done to modularize code so that each table CRUD can be handles separately in future.
 */	

public interface CommonDocumentService {
	
	public List<JSONObject>  getCodedArvs();
	
	public List<JSONObject>  getCodedHivFacilityTypes();
	
	public List<JSONObject>  getPartnerAssistanceStatus();
	
	public List<JSONObject>  getCodedMaritalStatus();
	
	public List<JSONObject>  getCodedInvestigationStatus();
	
	public List<JSONObject>  getDispositionCodeValues(boolean filterRows);
	
	public List<JSONObject>  getCOSCodesData();
	
	public List<JSONObject>  getAddressTypes();
	
	public List<JSONObject>  getCodedSexAtBirthValues();
	
	public List<JSONObject>  getCodedSexualOrientationValues();
	
	public List<JSONObject>  getCodedSexualOrientationPrfValues();
	
	public List<JSONObject> getCodedYesNoUnkownValues();
	
	public List<JSONObject> getCodedPrimaryLanguageValues();
	
	public List<JSONObject> getCodedYesNoRefUnkownValues();
	
	public List<JSONObject> getCodedTelephoneTypeValues();
	
	public List<JSONObject> getCodedFacilityTypeValues();
	
	public void deleteDocFromDocLocator(int docId, UserObject userObject);

	public List<PSMessageVO> getPSMessageDetails(int messageReceivedId, String documentType);

	public Map<String, String> getEharsCodedCountryValues();

	public void moveStdDocumentToDocAttrTables(
			String stateNo, int documentId,
			UserObject userObject, String documentType, String origin);
	
	public void deleteEntryInPersonDocAssociation(int document_ID, String documentType, String associationStatus, UserObject userObject);

	public void updateRawDocumentStatus(int currentDocumentId,
			UserObject userObject, String documentType,
			int statusCode);

	// Variant without UserObject and with tableName instead of documentType
	public void updateRawDocumentStatus(int currentDocumentId,
			String tableName, int statusCode);
	
	public void updateStdDocumentStatus(int currentDocumentId, String documentType,
			int statusCode);

	public Map<Object, Object> getDocData(int documentId, String string,
			String documentType);

	public void writeRawDocData(String parentDocumentType,
			Map<Object, Object> rawDocData, int oldDocumentId, UserObject userObject);

	public void writeStdDocData(String parentDocumentType,
			Map<Object, Object> stdDocData, int oldDocumentId);

	public void updateSurvellianceAssignmentStatus(int documentId, int assignProcess,
			UserObject userObject, BindingResult bindingResult);	
	
	public List<String> getDocumentTypes(UserObject userObject);

	public void deleteEntryInDocumentPersonAttr(int documentId,
			UserObject userObject);

	public void deleteEntryInDocumentRace(int documentId, UserObject userObject);

	public String getColumnDataFromTable(String tableName, String targetColumn,
			String refColumn, int refValue);

	public void updateDocumentPsAssignStatus(int pS_ASSIGN_STATUS,
			int newDocumentId);

	public void updateTriggeringDocumentIdInNyehmsMessage(int documentId, int newDocumentId);

	public void deleteDocumentFromStdTable(String parentDocumentType, int documentId, UserObject userObject);


	public Map<Object, Object> getPersonDocData(int documentId);

	
	public Map<String, String> getStatenoAndSourceCodeFromStdDocData(String parentDocumentType, int documentId, UserObject userObject);

	public List<JSONObject> getFileNamesForDocumentType(String documentType);

	public void deleteEntryInDocumentRiskTable(int documentId,
			UserObject userObject);

	public NYCEHARSDocVO getNycEHARSDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public void updateDocumentsStatus(Long[] documentIds, String documentType,
			int rawRecordDeletedProcessStatus, BindingResult bindingResult,
			UserObject userObject);

	public NYCLabDocVO getNycLabDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);
	
	public int writeDocumentTestType(int document_ID, String testType);

	public List<String> getUnitsOfMeasurement();

	public int updateRIDRDocumentWithAcrf(String ridrDocumentId, int acrfDocId, UserObject userObject);
	
	public void getNewDocId(Map<Object,Object> map);
	
	public String validateState(String _state, List<String> errors, List<Integer> errorCodes, String _referer);

	public String getSubDocumentType(Integer documentId);

	public void createNonCaseWithNewDocument(int documentId, int newDocumentId);

	public int checkForActiveDocumentInPersonTables(int documentId);

	public List<Map<String, String>> getHIVCategories();

	public List<Map<String, String>> getAIDSCategories();

	public List<JSONObject> getCodedProfessionValues();

	public Map<String, Long> getTriggeringDocumentAddressIds(int newDocumentId);

	public List<Integer> getOpenAssignmentsForTriggeringDocIds(int newDocumentId);

	public Map<String, Long> getAssignmentAddessIds(Integer assignmentId);

	public List<Map<String, String>> getPSMessageReferenceData(String columnName);

	public String getEharsStateno(String stateno);

	public Map<String, String> getNYCStatenoDetails(String stateno1);

	public Map<String, Object> getDiscardedDocumentDetails(int docId);

	public PersonViewVO getClericalDocumentAsPerson(int documentId, String documentType);

	public void updateRiaLabDocument(int documentId, int newDocumentId);

	public List<JSONObject>  getLAFTReasonslist();

	public List<JSONObject> getExpandedRaceList();

	
}
