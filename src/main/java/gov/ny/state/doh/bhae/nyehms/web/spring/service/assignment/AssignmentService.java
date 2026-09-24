package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

/**
 * This class implemented for Assignments 
 *
 * @author ska03
 */
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentActivityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentUpdateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentWorkerRegionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsAssignReassignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsHomeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.ReabAssignmentsAssignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface AssignmentService {	

	/**
	 * It will return the list of Assignments available for the Specified Assignment search criteria
	 * @param assignmentsHomeVO containing Assignment search criteria
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID session id
	 * @return List<AssignmentsListVO> assignmentList List of Assignment for the specified search criteria 
	 */
	public List<AssignmentsListVO> getAssignmentsList(AssignmentsHomeVO assignmentsHomeVO, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * This method will return all the worker regions list which are active for the logged in user
	 * @param workerId workerId logged in user id
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return List<AssignmentWorkerRegionsVO> List of Worker Regions information   
	 */
	public List<AssignmentWorkerRegionsVO> getAssignmentWorkerRegions(String workerId, BindingResult bindingResult, UserObject userObject, String sessionID);
	

	/**
	 * This method will return the Active users list available in the specified Region
	 * @param regionCode Region code
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return Map<Integer, String> workers List of Active workers available in the Region
	 */
	public Map<Integer, String> getWorkerNamesByRegion(String regionCode, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * This method will return the all the Assignment Status available for the Role code
	 * For worker role "Needs Assignment" will not be avaiable
	 * @param roleCode Role code to differentiate the Assignment status list between Worker and Supervisor
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return Map<String, String> status: Assignment status and its description   
	 */
	public Map<String, String> getAssignmentStatusByRole(int roleCode, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * This method will perform the Assignment activities like assigning, Re assigning, Requesting, Revoking etc
	 * Success or failure information will store in the BindingResult object 
	 * @param assignReassignVO containing list of Assignments, worker information, supervisor information and comments
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 */
	public Integer assignReassign(AssignmentsAssignReassignVO assignReassignVO, String reviewed, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * This method will return the Assignment details of a selected Assignment and Activities performed on the Assignments
	 * @param assignmentId Assignment id
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return AssignmentDetailsVO assignmentDetailsVO Assignment's Details and Activity
	 */
	public AssignmentDetailsVO getAssignmentsDetails(long assignmentId, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * Get all the Assignment closure status avaialbe in the Database
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return List<Map<String,String>> Closure status code and its description  
	 */
	public List<Map<String,String>> getAssignmentClosureStatus(BindingResult bindingResult, UserObject userObject, String sessionID, boolean includeAllClosureStatus, String closureStatusCategoryType);
	
	/**
	 * This method will be used to update the comments of the Assignment or to close the Assignment
	 * @param assignmentUpdateVO containing Assignment details, comments, closure category (if available)
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 */
	public void updateAssignment(AssignmentUpdateVO assignmentUpdateVO, BindingResult bindingResult, UserObject userObject);

	/**
	 * This method will be used to load the Ehars data of the Assignment stateno. It will get the data from SQL server database by creating new connection to it.
	 * @param stateno data of the Assignment stateno. It will get the data from SQL server database by creating new connection to it.
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return Map<String, Object> eharsPersonDetailsObj containing Ehars information   
	 */
	public Map<String, String> loadEharsPersonViewDetails(String stateno, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	/**
	 * This method will used to get the Assignment Activities of the Assignment. We will call this method when we add any new comment then we will reload the
	 * Assignment Activities and comments history
	 * @param assignmentId Assignment id
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @param sessionID
	 * @return List<AssignmentActivityVO> assignmentActivitiesList Assignment Activies information(it will contain comments too) 
	 */
	public List<AssignmentActivityVO> getAssignmentViewedCommentDetails(long assignmentId, BindingResult bindingResult, UserObject userObject, String sessionID);
	public List<AssignmentActivityVO> getAssignmentEditActionCommentDetails(long assignmentId, BindingResult bindingResult, UserObject userObject, String sessionID, String reabstract);

	/**
	 * This method used to get the Roles associated to the logged in user
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @return Map<String, String> userRoleCodeMap Role code and its description  
	 */
	public Map<String, String> getUserRoleCodes(BindingResult bindingResult, UserObject userObject);
	
	/**
	 * This method will return the list of regions associated to the selected role of the logged in user
	 * @param roleCode Selected Role code(Worker, supervisor, co viewer, Coorinator)
	 * @param bindingResult result object containing transaction status information
	 * @param userObject containing logged in user details
	 * @return List<Map<String, String>> userRegionsMapForSelectedRoleMap Region code and its description   
	 */
	public List<Map<String, String>> getRegionsMapForSelectedRole(Integer roleCode, BindingResult bindingResult, UserObject userObject);

	public Integer changeAssignmentAddressDetails(int assignmentId, String origin, String closureCategory, long supervisorRegionId, 
			long rawFacilityId, String addressType, String countyCode, UserObject userObject, BindingResult bindingResult);

	public void changeAssignmentAddressDetailsByNewRawFacility(
			int assignmentId, String origin, String closureCategory, long supervisorRegionId, RawSubFacilityVO rawSubFacilityVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult);

	public void changeAssignmentAddressDetailsByNewRawProvider(
			int assignmentId, String origin, String closureCategory, long supervisorRegionId, SubProviderAndAddressVO subProviderAndAddressVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult);
	
	public Map<String, String> getAssignmentAndAddressDetails(int assignmentId,
			BindingResult dummyBindingResult, UserObject userObject);

	public List<PersonDocHistoryVO> getunknownOutOfStateProviderDocuments(
			String documentType, String processCode, BindingResult dummyBindingResult, UserObject userObject);
	
	public List<AssignmentsListVO> loadClosedAssignmentsForReview(
			String closureCategory, BindingResult bindingResult,
			UserObject userObject, String id);

	public void triggerSurvAssignment(int documentId, String documentType,
			String triggerAssignmentCheckStatus, String ahiFlag, String stateNo, UserObject userObject,
			BindingResult bindingResult);

	public Integer getOpenAssignmentIdByTrgDoc(int documentId);
	
	public Integer getOpenAssignmentIdByAhiDoc(int documentId);

	public void updateReviewedDateForAllClosedAssignments(int documentId, UserObject userObject);

	public void closeNeedsAssignment(AssignmentsAssignReassignVO assignReassignVO,
			BindingResult bindingResult, UserObject userObject);

	public void closeAssignment(Map<Object, Object> map);

	public void updateOrClosedAssignment(AHIDocVO aHIDocVO, AssignmentUpdateVO assignmentUpdateVO,
			BindingResult result, UserObject userObject);

	public void updateAssignmentWithAHIDocument(long assignmentIdOfAHI,
			Integer document_ID, BindingResult dummyBindingResult,
			UserObject userObject);
	
	public void updateAssignmentWithEditAHIDocument(long parentDocumentId,
			Integer document_ID, BindingResult dummyBindingResult,
			UserObject userObject);	

	public Map<String, Object> getTriggeringDocumentDetails(int assignmentId);
	
	public String getStatenoForAssignment(long documentId);
	
	public boolean changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(int assignmentId, String origin, String closureCategory, long supervisorRegionId,  
			long addressId, String addressType, String countyCode, UserObject userObject, BindingResult bindingResult);

	public List<AssignmentsListVO> getPriorityAssignmentsList(String priorityType, 
			AssignmentsHomeVO assignmentsHomeVO,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public void discardAcrfDocIdFromSurveillanceAssignment(
			Map<Object, Object> docIdMap);
	
	public void discardReabAcrfDocIdFromSurveillanceAssignment(Map<Object, Object> docIdMap);

	public void updateDocumentFileDisplayName(String fileName,
			String displayName, String documentType, UserObject userObject,
			BindingResult bindingResult);
	
	public Map<String, String> getUserCORoleCodes(BindingResult bindingResult, UserObject userObject);	
	
	public String getWorkerCORegionId(int workerId, String roleCode);
	
	public List<JSONObject> getReabstractAssignmentStatusByRole(int roleCode, BindingResult bindingResult, UserObject userObject);	
	
	public Integer reabAssign(ReabAssignmentsAssignVO reabAssignVO, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	public List<Map<String,String>> getReabActivities(BindingResult bindingResult, UserObject userObject, String sessionID);
	
	public void updateReabActivityNStatus(String assignmentId, String reabCrfDocId, String crfDocId, String documentType, String comments, 
			String newReabActivity, String reabClosureCategory, BindingResult bindingResult, UserObject userObject, HttpServletRequest request);
	
	public int updateReabActivity(String assignmentId, String newReabActivity, String comments, BindingResult bindingResult, UserObject userObject);
	
	public int updateReabStatus(String assignmentId, String newReabStatus, String reabClosureCategory, BindingResult bindingResult, UserObject userObject);
	
	public void saveFSAndReabCRFDiff(String assignmentId, String reabCrfDocId, String crfDocId, String documentType, 
				BindingResult bindingResult, UserObject userObject, HttpServletRequest request);
	
	public Map<String, Object> getCRFDiffSummary(int assignmentId, String documentType, int fsCRFDocId, int reabCRFDocId, BindingResult bindingResult, UserObject userObject, HttpServletRequest request);
	
	public List<JSONObject> getReabstractionDiffData(int assignmentId);
	
	public Map<Integer, String> getReabstractionWorkerNamesByRegion(String regionCode, BindingResult bindingResult, UserObject userObject, String sessionID);
	
	public void deleteCRFNReopenAssign(AssignmentsAssignReassignVO assignReassignVO, int documentId, String documentType, String stateno, BindingResult bindingResult, 
			UserObject userObject, HttpServletRequest request);

	public List<JSONObject> getOpenCaseloadSummaryData(UserObject userObject, BindingResult dummyBindingResult);

	public Map<String, String> getAssignmentStatusByACRF(int acrfDocId, UserObject userObject,
			BindingResult bindingResult);
	
}
