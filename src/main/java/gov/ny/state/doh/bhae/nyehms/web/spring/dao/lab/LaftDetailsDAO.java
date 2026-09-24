package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftDetailsVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface LaftDetailsDAO {
	
	/**
	 * This method will get the data from NYEHMS_PROCESS_ERRORS where the PROCESS_STATUS is 2
	 * (Validation failed). This data contains Error Code and Error Description and Document details
	 * @return list of Error Document details
	 * @throws DataAccessException
	 */
	public List<LaftDetailsVO> getValidationFailedLabDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException;
	
	public List<LaftDetailsVO> getValidationFailedAIRSDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException;
	
	
	/**
	 * This method will get the data from NYEHMS_PROCESS_ERRORS where the PROCESS_STATUS is 33
	 * (Validation failed & flagged deleted). This data contains Error Code and Error Description and Document details
	 * @return list of Error Document details
	 * @throws DataAccessException
	 */
	public List<LaftDetailsVO> getValidationFailedDeletedLabDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException;
	
	public List<LaftDetailsVO> getValidationFailedDeletedAIRSDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException;
	
	
	
	/**
	 * This method is used to get the List of documents which are in Standardized, Holded, NO MATCH, CLERICAL REVIEW status.
	 * @return list of document details
	 * @throws DataAccessException
	 */
	public List<LaftDetailsVO> getHEDStatusDocuments() throws DataAccessException;
	
	/**
	 * This method is used to change the document(s) status to Reprise.
	 * 
	 * When we reprise the document, the document is ready for the validation process.
	 * 
	 * When we fix the errors then we will reprise it
	 * 
	 * @param map contains input data
	 * @throws DataAccessException
	 */
	public void repriseErrorDocuments(Map<Object,Object> map) throws DataAccessException;
	
	/**
	 * This method is used to change the document status delete.
	 * 
	 * If the document is invalid, which is not matching with any criteria then will delete those documents(s)
	 * @param map contains input data
	 * @throws DataAccessException
	 */
	public void deleteErrorDocuments(Map<Object,Object> map) throws DataAccessException;
	
		
	/**
	 * This method is used to change the document status to undelete.
	 * 
	 * If the document is flagged deleted, it will be undeleted.
	 * @param map contains input data
	 * @throws DataAccessException
	 */
	public void undeleteErrorDocuments(Map<Object,Object> map) throws DataAccessException;
	
		
	/**
	 * This method is used to hold the document(s). 
	 * 
	 * When we kept it in hold status, any process will not perform on those document(s).
	 * 
	 * Like Standardizing, Matching etc 
	 * @param map contains input data
	 * @throws DataAccessException
	 */
	public void holdLabDocuments(Map<Object,Object> map) throws DataAccessException;
	
	/**
	 * This method is used to remove the hold status of the document(s)
	 * @param map contains input data
	 * @throws DataAccessException
	 */
	public void unholdLabDocuments(Map<Object,Object> map) throws DataAccessException;
		
	public List<Long> getDocumentWithTheSpecificStatusCode(Map<Object,Object> map) throws DataAccessException;
	
	public int updateLabNotes(Map<Object, Object> inOutParameterMap);
	
	public int addNotesToProcessErrors(Map<Object,Object> map);

	public List<JSONObject> getAddressSameAsEntries();

	public int addRawProviderFacilityAddressSameAsResidence(
			Map<Object, Object> inOutParameterMap);

	public int updateAddressSameAsStatus(Map<Object, Object> inOutParameterMap);

	public String getErrorMessage(int docId);

	public int checkCanBeResidenceAddressAlreadyAdded(
			Map<Object, Object> inOutParameterMap);

	public List<Map<String, Object>> getLabErrorReportsByType(
			Map<String, String> inOutParameterMap);

	public List<Map<String, Object>> getLabErrorReportsByFacilityName(
			Map<String, String> inOutParameterMap);

	public List<Map<String, String>> getLabErrorReportsByStatus(
			Map<String, String> inOutParameterMap);

	public List<Map<String, Object>> getLabErrorStatusReportByLab(
			Map<String, String> inOutParameterMap);

	public void updateStatusToDonotReport(Map<Object, Object> inOutParameterMap);

	public List<Map<String, String>> getDocumentLabErrorsByCategory(String documentId);

	public int byPassDocumentError(Map<String, Object> inputParameterMap);
	
}
 