package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdOldDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

public interface LabDocumentService {
	
	public StdEcrlsLabDocVO  getLabDoc(int docId, String documentType, BindingResult result, UserObject userObject, String sessionID);
	@Transactional	
	public void insertLabDocData(LabDocVO stdLabDocVO, BindingResult result, UserObject userObject, String sessionID);
	public EcrlsLabDocVO getHivLabDoc(int docId, BindingResult dummyBindingResult,
			UserObject userObject, String id);
	
	public PersonViewVO getStdLabDocForClericalView(int documentId, String documentType,
			BindingResult dummyBindingResult, UserObject userObject,
			String sessionId);
	public void editLabErrorDoc(EcrlsLabDocVO ecrlsLabDocVO, String requestComments,
			BindingResult result, UserObject userObject, String id);
	
	
	public void moveRawToStdByStateno(int documentId, String stateno, String requestComments,BindingResult bindingResult, UserObject userObject);
	
	public int insertStandardHIVResistanceResult(Number documentId, String  OBXOBSERVATIONRESULTTEXT, Number OBXOBSERVATIONRESULTKEY, 
			java.util.Date collectionDate);
	public void restageStdDocumentNonMatches(
			List<JSONObject> schedulerStatusObject, Map<String,
			String> schedulerMetaData);
	public List<Map<String, Object>> loadMultiSpotDocuments(
			BindingResult bindingResult, UserObject userObject, String pfi, String loincs);

	public void processMultiSpotDocuments(
			BindingResult bindingResult, UserObject userObject,
			int[] multiSpotDocuments, int retainDocumentId, int loincGroupId);

	public int updateStatusCodeForHivDocuments(List<Integer> documentIds, int statusCode);
	public StdOldDocVO getStdOldDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);
	public void rollbackStdDocumentNonMatches( 
			Map<String, String> schedulerMetaData);
	public void insertStandardHIVResistanceResultFromOldDocument(
			int newDocumentId, Integer documentId);
	public Integer getMultiStepDocumentId(Integer documentId);
	
	public Map<String, Object> getRawDataAsObject(Integer documentId);
	public Map<String, Object> getStdDataAsObject(Integer documentId);
	public void insertRawEditedHIVLabDocumentWithUpdateData(String string,
			Map<String, Object> originalRawData, Integer documentId,
			UserObject userObject);
	public void insertStdEditedHivLabDocumentWithUpdateData(String string,
			Map<String, Object> originalStdData, Integer documentId);
	
	public void writeStandardDataFromRaw(EcrlsLabDocVO ecrlsLabDocVO, StdEcrlsLabDocVO stdEcrlsLabDocVO);
	
	public int writeStandardDataToDatabase(StdEcrlsLabDocVO stdEcrlsLabDocVO);
	public List<Map<String, String>> getLabCombiningDocData();
}
