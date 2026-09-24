package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldDocsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface PersionService {
	/**
	 * This method is used to get the Person details, Documents associated to the
	 * Person
	 * 
	 * @param stateNo
	 *            sTATE NUMBER
	 * @param date
	 *            Best Indicated date (This date will be used to get the Person
	 *            details available till that date, if the date is today this method
	 *            will return all the latest values available in the database If the
	 *            date is one month before then this method will returns all the
	 *            details available till that date.
	 * @param result
	 *            Used to store the transaction status
	 * @param userObject
	 *            Logged in user object
	 * @param sessionID
	 * @return PersonView object
	 */
	public PersonViewVO getPersonViewData(String stateNo, String date, BindingResult result, UserObject userObject,
			String sessionID);

	/**
	 * This method will return the list of values available for the specified Field
	 * name. Will gather all the values for the spectified field name from the List
	 * of documents associated to the stateno.
	 * 
	 * @param stateNo
	 *            State number
	 * @param fieldName
	 *            Field name
	 * @param callingAppName
	 *            origin from person page or search page
	 * @param result
	 *            containing Transaction status
	 * @param userObject
	 *            Logged in user object
	 * @param sessionID
	 * @return List of values available for the Field name
	 */
	public List<PersonFieldVO> getFieldValsWithBestIndicator(String stateNo, String fieldName, String callingAppName,
			BindingResult result, UserObject userObject, String sessionID);

	/**
	 * This method will return the List of documents (where the field name value
	 * equal to "fieldVal" ) associated to the stateno
	 * 
	 * @param stateNo
	 *            State number
	 * @param fieldName
	 *            Field name
	 * @param fieldVal
	 *            Field value
	 * @param callingAppName
	 *            origin from person page or search page
	 * @param result
	 *            containing Transaction status
	 * @param userObject
	 *            Logged in user object
	 * @param sessionID
	 * @return List of documents (Document Id, Type and Recieved date)
	 */
	public List<PersonFieldDocsVO> getFieldDocsWithBestIndicator(String stateNo, String fieldName, String fieldVal,
			String callingAppName, BindingResult result, UserObject userObject, String sessionID);

	/**
	 * This method is used to get the Person Race data. Will show this data in the
	 * Person view page
	 * 
	 * @param stateNo
	 *            State number
	 * @param date
	 *            Best Indicated date (This date will be used to get the Person
	 *            details available till that date, if the date is today this method
	 *            will return all the latest values available in the database If the
	 *            date is one month before then this method will returns all the
	 *            details available till that date.
	 * @param result
	 *            containing Transaction status
	 * @param userObject
	 *            Logged in user object
	 * @param sessionID
	 * @return PersonRaceVO object
	 */
	public PersonRaceVO getPersonRaceData(String stateNo, String date, BindingResult result, UserObject userObject,
			String sessionID);

	/**
	 * This method is used to get the Person Risks
	 * 
	 * @param stateNo
	 *            State number
	 * @param date
	 *            Best Indicated date (This date will be used to get the Person
	 *            details available till that date, if the date is today this method
	 *            will return all the latest values available in the database If the
	 *            date is one month before then this method will returns all the
	 *            details available till that date.
	 * @param result
	 *            containing Transaction status
	 * @param userObject
	 *            Logged in user object
	 * @param sessionID
	 * @return PersonRisksVO object
	 */
	public List<PersonRisksVO> getPersonRisksData(String stateNo, String date, BindingResult result,
			UserObject userObject, String sessionID);

	/**
	 * This function is used to delete the document for the stateno
	 * 
	 * @param documentId
	 *            Document ID
	 * @param discardReason
	 * @param bindingResult
	 *            Transaction status
	 * @param userObject
	 *            Logged in user object
	 */
	public void deleteDocument(int documentId, String documentType, Integer discardReason, String discardReasonDescription, String comments,
			String stateno, String deleteFlag, BindingResult bindingResult, UserObject userObject);

	public List<PersonDocHistoryVO> getPersonDocumentDetails(String stateNo, String documentType, BindingResult dummyBindingResult,
			UserObject userObject, String id);

	public List<String> getEharsStateno(String stateNo, BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getOriginCategories();

	public List<String> getNycEharsDetails(String stateNo, BindingResult bindingResult, UserObject userObject);

	public String getNyehmsStatenoForTheDocument(int docId);

	public List<PersonDocHistoryVO> getDocumentDetails(String documentId, String documentType,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public List<String> getStatenoHistory(String stateNo, BindingResult bindingResult, UserObject userObject);

	public List<PersonDocHistoryVO> getHIVDocumentDetails(String stateNo, BindingResult dummyBindingResult,
			UserObject userObject, String id);

	public JSONObject getEharsStatenosMapping(String[] statenos, BindingResult bindingResult, UserObject userObject);

	public void updateStdDocument(Map<String, String> params, BindingResult bindingResult, UserObject userObject);

	public String checkStatenoIsActive(String stateno, BindingResult bindingResult, UserObject userObject);

	public void triggerPsMessage(int documentId, String stateno, String documentType, String messageType,
			String psMessageForce, String softErrorCode, AddressFieldVO addressFieldVO, String reason, String otherReason, BindingResult bindingResult,
			UserObject userObject);

	public List<String> getTriggeredMessageTypesForDocument(Map<Object, Object> inputParameterMap);

	public void executeTriggerPsMessage(int documentId, String stateno, String documentType, String messageType,
			AddressFieldVO addressFieldVO, BindingResult bindingResult, UserObject userObject);

	public void getPsMessageType(int documentId, String stateno, String documentType, BindingResult bindingResult,
			UserObject userObject);

	public List<String> getNycCityNoDetails(String stateno, Object object, UserObject userObject);

	public String getClusterIds(String stateNo, BindingResult result, UserObject userObject);

	public List<String> getNotAssociatedClusterIds(UserObject userObject, BindingResult result, String stateNo);

	public void associateStateNoToCluster(UserObject userObject, BindingResult bindingResult, String stateno,
			String clusterDetails, String identificationMethod, String identificationDate, long demoDocumentId);

	public void deletePendingPSMessageDocuments(int documentId, UserObject userObject);

	public Map<String, String> getNYSEharsDxDetails(String stateno);

	public Map<String, String> getNYCEharsDxDetails(String stateno);

	public void deleteRiaDocument(int documentId, UserObject userObject);

	public Map<String, String> geteHarsPersonDataforTab(Map<String, String> eharPersonDataObj, BindingResult dummyBindingResult, UserObject userObject, String stateno);
   
	public List<Map<String, String>> getDuplicateReviewSummary(String ridrValues);
}
