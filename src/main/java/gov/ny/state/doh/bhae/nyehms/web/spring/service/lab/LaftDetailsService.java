package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface LaftDetailsService {

	/**
	 * 
	 * Every Raw Lab document will be validated against to Validation rules
	 * (Like validating the Loinc codes with existing Loinc codes, Checking the
	 * Acceptable test results with existing range etc)
	 * 
	 * If any document is failing against the rules, that document id and Error
	 * details will be inserted into NYEHMS_PROCESS_ERRORS table.
	 * 
	 * This method is used to load the Validation failed(ProcessStatus 2) Lab
	 * Documents and its Error details for the validation failure
	 * 
	 * @param bindingResult
	 *            Containing transaction details(Success/Failure)
	 * @param userObject
	 *            contains Logged in user details
	 * @return List of document details
	 */
	public List<LaftDetailsVO> getValidationFailedLabDocumentDetails(String documentType, BindingResult bindingResult, UserObject userObject);
	
	
	/** This method is used to load the Validation failed Deleted Lab Documents (Process Status = 33) 
	 * and its Error details for the validation failure
	 * @param bindingResult
	 *            Containing transaction details(Success/Failure)
	 * @param userObject
	 *            contains Logged in user details
	 * @return List of document details
	 */
	public List<LaftDetailsVO> getValidationFailedDeletedLabDocumentDetails(String documentType, BindingResult bindingResult, UserObject userObject);
	
	

	/**
	 * This method will get the list of documents which are in Standardized,
	 * Holded, NO MATCH, CLERICAL REVIEW status.
	 * 
	 * @param bindingResult
	 * @param userObject
	 *            logged in User Object
	 * @return List of document details
	 */
	public List<LaftDetailsVO> getHEDStatusDocuments(
			BindingResult bindingResult, UserObject userObject);

	/**
	 * This method is used to update the Document status. Will use this method
	 * in Lab Errors, Lab Deleted Errors and Lab HED details pages.
	 * 
	 * In Lab Errors page we can Delete, Edit and Reprise the document(s) 
	 * In Lab Deleted Errors page we can Undelete the documents(s)
	 * In HED Details page we can Delete, Edit, Hold and Unhold the document(s)
	 * 
	 * @param documentIds
	 *            list of document ids
	 * @param comments
	 *            comments provided for the change status
	 * @param actionName
	 *            changed status
	 * @param bindingResult
	 *            contain transaction status
	 * @param userObject
	 *            logged in user object
	 */

	public void updateDocumentsStatus(List<Long> documentIds, int statusCode,
			String comments, String actionName, String documentType, BindingResult bindingResult,
			UserObject userObject);

	public void updateSelectedDocumentStatus(long[] documentIds,
			String comments, String actionName, String documentType, BindingResult bindingResult,
			UserObject userObject);

	public List<Long> getDocumentWithTheSpecificStatusCode(
			Map<Object, Object> inputOutPutParameterMap);
	
	public void updateLabNotes(long id, String notes,
			BindingResult bindingResult, UserObject userObject);

	public void addNotesToProcessErrors(long[] documentIds, String notes,
			BindingResult bindingResult, UserObject userObject);

	public List<JSONObject> getAddressSameAsEntries(
			BindingResult bindingResult, UserObject userObject);

	public void addRawProviderFacilityAddressSameAsResidence(String rawAddress,
			String addressFilterType, BindingResult bindingResult,
			UserObject userObject);

	public void updateAddressSameAsStatus(String id, String fLAG_ACTIVE,
			BindingResult bindingResult, UserObject userObject);

	public String getErrorMessage(int docId);


	public Map<String, Map<String, Map<String, Integer>>> getLabErrorReportsByType(BindingResult dummyBindingResult,
			UserObject userObject);


	public List<Map<String, Object>> getLabErrorReportsByFacilityName(
			BindingResult dummyBindingResult,
			UserObject userObject);


	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getLabErrorReportsByStatus(
			String fromDate, String toDate, Map<String, Object> map, BindingResult dummyBindingResult,
			UserObject userObject);


	public List<Map<String, Object>> getLabErrorStatusReportByLab(
			String fromDate, String toDate, BindingResult dummyBindingResult,
			UserObject userObject);

	public void updateProviderAddress(int[] documentIds, AddressFieldVO addressFieldVO, String firstName,
			String lastName, String middleName, String suffix, String phone, String npi, BindingResult bindingResult, UserObject userObject);


	public void updateUnitsOfMeasure(int[] documentIds, String units, BindingResult bindingResult,
			UserObject userObject);


	public List<Map<String, String>> getDocumentLabErrorsByCategory(String documentId, BindingResult dummyBindingResult,
			UserObject userObject);


	public void byPassDocumentError(String documentId, String errorCodes, BindingResult bindingResult,
			UserObject userObject);


	public void updateRawLabAddressDetails(int[] documentIds, AddressFieldVO addressFieldVO,
			BindingResult bindingResult, UserObject userObject);

}
