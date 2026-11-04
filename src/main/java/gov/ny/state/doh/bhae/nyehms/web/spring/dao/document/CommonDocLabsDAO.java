package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCEHARSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface CommonDocLabsDAO {	
	
	public List<JSONObject>  getCodedArvs();

	public List<JSONObject> getCodedHivFacilityTypes();
	
	public List<JSONObject> getPartnerAssistanceStatus();
	
	public List<JSONObject> getCodedMaritalStatus();
	
	public List<JSONObject> getAddressTypes();
	
	public List<JSONObject> getCodedSexAtBirthValues();
	
	public List<JSONObject> getCodedYesNoUnkownValues();
	
	public int deleteDocFromDocLocator(int docId);

	public List<PSMessageVO> getPSMessageDetails(Map<Object, Object> inputOutputParameterMap);

	public List<JSONObject> getCodedInvestigationStatus();

	public List<JSONObject> getDispositionCodeValues(Map<String, Boolean> inputParameterMap);

	public void moveStdDocumentToDocAttrTables(
			Map<Object, Object> inputOutputParameterMap);

	public int deleteEntryInPersonDocAssociation(
			Map<Object, Object> inputOutputParameterMap);

	public int updateRawDocumentStatusCode(
			Map<Object, Object> inputOutputParameterMap);
	
	public int updateStdDocumentStatusCode(
			Map<Object, Object> inputOutputParameterMap);

	public Map<Object, Object> getDocData(
			Map<Object, Object> inputOutputParameterMap);

	public List<JSONObject> getCOSCodesData();

	public int updateSurvellianceAssignmentStatus(
			Map<Object, Object> inputOutputParameterMap);

	public List<String> getDocumentTypes(Map<String, Object> map);

	public int deleteEntryInDocumentPersonAttr(int documentId);

	public int deleteEntryInDocumentRace(int documentId);

	public String getColumnDataFromTable(
			Map<Object, Object> inputOutputParameterMap);

	public int updateDocumentPsAssignStatus(
			Map<Object, Object> inputOutputParameterMap);

	public int updateTriggeringDocumentIdInNyehmsMessage(
			Map<Object, Object> inputOutputParameterMap);

	public int deleteDocumentFromStdTable(
			Map<Object, Object> inputOutputParameterMap);

	public Map<Object, Object> getPersonDocData(int documentId);

	public Map<String, String> getStatenoAndSourceCodeFromStdDocData(
			Map<Object, Object> inputOutputParameterMap);

	public List<JSONObject> getFileNamesForDocumentType(String _documentType);

	public List<JSONObject> getCodedPrimaryLanguageValues();

	public List<JSONObject> getCodedYesNoRefUnkownValues();

	public List<JSONObject> getCodedTelephoneTypeValues();

	public List<JSONObject> getCodedFacilityTypeValues();

	public void deleteEntryInDocumentRiskTable(int documentId);

	public NYCEHARSDocVO getNycEHARSDoc(int docId);

	public void updateDocumentsStatus(
			Map<Object, Object> inputOutputParameterMap);

	public void inActivateDocument(Map<Object, Object> inputOutputParameterMap);

	public NYCLabDocVO getNycLabDoc(int docId);
	
	public int checkAndInactiveStateno(Map<Object, Object> inputOutputParameterMap);

	public Map<String, Object> getStatenoDetailsByDocumentId(int document_ID);

	public void rejectMergePairForInactiveStateno(
			Map<Object, Object> inputOutputParameterMap);

	public void deletePsMessageForTriggeringDocument(Map<Object, Object> inputOutputParameterMap);

	public void rejectBadMergeStatneForInactiveStateno(
			Map<Object, Object> inputOutputParameterMap);

	public List<Map<String, String>> getStdToRawColumnList(String documentType);
	
	public List<String> getUnitsOfMeasurement();

	public int updateRIDRDocumentWithAcrf(
			Map<Object, Object> inputOutputParameterMap);

	public void deleteIndicatePsMessage(
			Map<Object, Object> inputOutputParameterMap);

	public String getSubDocumentType(Integer documentId);


	public String getDocumentType(Integer documentId);
	
	

	public void createNonCaseWithNewDocument(
			Map<String, Integer> inputParameterMap);

	public int checkForActiveDocumentInPersonTables(int documentId);

	public List<Map<String, String>> getHIVCategories();

	public List<Map<String, String>> getAIDSCategories();

	public List<JSONObject> getCodedProfessionValues();

	public Map<String, Long> getTriggeringDocumentAddressIds(int newDocumentId);

	public List<Integer> getOpenAssignmentsForTriggeringDocIds(int newDocumentId);

	public Map<String, Long> getAssignmentAddessIds(Integer assignmentId);

	public List<Map<String, String>> getPSMessageReferenceData(String columnName);

	public Map<String, Object> getDiscardedDocumentDetails(int docId);

	public PersonViewVO getClericalDocumentAsPerson(int documentId);

	public void updateRiaLabDocument(Map<String, Integer> inputMap);

	public List<JSONObject> getLAFTReasonslist();
	
	public List<JSONObject> getCodedSexualOrientationValues();
	
	public List<JSONObject> getCodedSexualOrientationPrfValues();

	public List<JSONObject> getExpandedRaceList();

}
