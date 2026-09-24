package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

/**
 * This is interface used for Assignment operations. 
 * 
 * Please see AssignmentDAO.xml for its implementation.
 *
 * @author Sampath K Anumolu
 */

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentActivityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentWorkerRegionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface AssignmentDAO {

	/**
	 * This method will be used to get the List of Assignments for the user search criteria
	 * This method will be called we are trying to get the Assignments for Worker and Supervisor roles
	 * @param map containing Assignment search criteria (Role Code, Region code, worker, Assignment status and date range)
	 * @return List of Assignments
	 * @throws DataAccessException
	 */
	public List<AssignmentsListVO> getAssignmentsList(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method will be used to get the List of Assignments for the user search criteria
	 * This method will be called we are trying to get the Assignments for Co Viewer and Coordinator roles
	 * @param map containing Assignment search criteria (Role Code, Region code, worker, Assignment status and date range)
	 * @return List of Assignments
	 * @throws DataAccessException
	 */
	public List<AssignmentsListVO> getAssignmentsListForViewerOrCoordinatiorRole(
			Map<Object, Object> map) throws DataAccessException;

	/**
	 * This method is used to get all the Regions(Region code, description and worker region id) and roles associated to the logged in user 
	 * @param workerId
	 * @return list of worker regions
	 * @throws DataAccessException
	 */
	public List<AssignmentWorkerRegionsVO> getAssignmentWorkerRegions(
			String workerId) throws DataAccessException;

	/**
	 * This method will return the List workers and supervisors in the specified region
	 * @param regionCode
	 * @return list of Workers
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getWorkerNamesByRegion(String regionCode)
			throws DataAccessException;

	/**
	 * This method will return the Assignment status available for the User role(Except Reject and Sub ACRF status).
	 * for worker role Needs Assignment will not be available. 
	 * 
	 * @param map containing user Role code
	 * @return list of Assignment status
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getAssignmentStatusByRole(
			Map<Object, Object> map) throws DataAccessException;

	/**
	 * This method will be used to requestReassign the Assignment
	 * @param map containing the Assignment input details
	 * @throws DataAccessException
	 */
	public void requestReassign(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to Assign or Re Assign an Assignment to any worker or Supervisor
	 * @param map  containing the Assignment input details
	 * @throws DataAccessException
	 */
	public void makeAssignments(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to reject the Assignments
	 * @param map containing the Assignment input details
	 * @throws DataAccessException
	 */
	public void rejectReassign(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to Revoke the Assignments
	 * @param map contining the Assignment input details
	 * @throws DataAccessException
	 */
	public void revokeReassign(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to Re open the Closed assignment
	 * @param map containing the Assignment input data
	 * @throws DataAccessException
	 */
	public void reOpenAssignments(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to Close an Assignment or to update the Assignment comments
	 * @param map containing Assignment input data
	 * @throws DataAccessException
	 */
	public void updateAssignment(Map<Object, Object> map)
			throws DataAccessException;

	/**
	 * This method is used to get the the Assignment details (start date, assign date, current status, closed category etc) and Assignment Activities
	 * @param map Assignment Id
	 * @return Assignment details object
	 * @throws DataAccessException
	 */
	public List<List<ArrayList<Object>>> getAssignmentDetails(
			Map<Object, Object> map) throws DataAccessException;

	/**
	 * This method is used to get the Assignment closure status available in the DB(Will return only Surviallance related closure status only)
	 * @param includeAllClosureStatus 
	 * @return list of Assignment closure status
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getAssignmentClosureStatus(Map<String , Object> inputParameterMap)
			throws DataAccessException;

	/**
	 * This method is used to get the Assignment Activities (Assigning, reassigning, reject,revoke etc) and its comments
	 * @param assignmentId
	 * @return list of Assignment actities performed on the Assignment
	 * @throws DataAccessException
	 */
	public List<AssignmentActivityVO> getAssignmentEditActionCommentDetails(
			Map<String, Object> map) throws DataAccessException;
	public List<AssignmentActivityVO> getAssignmentViewedCommentDetails(
			long assignmentId) throws DataAccessException;

	/**
	 * This method is used get the Roles codes associated to the user(Worker, Supervisor, Co viewer, Coordinator) will ignore the other roles if any available.
	 * Other roles will not have any access to assignments
	 * 	 * @param workerId
	 * @return list of roles codes
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getUserRoleCodes(int workerId)
			throws DataAccessException;

	/**
	 * This method is used to load the list of Regions associated to the selected used role.
	 * @param map Worker id and Role code
	 * @return list of regions
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getRegionsMapForSelectedRole(
			Map<Object, Object> map) throws DataAccessException;

	/**
	 * This method is used to load all the Regions available in the database.
	 * We will show all the regions for the Central office roles(Co viewer, Cordinator)
	 * @param map Worker id and role code
	 * @return list of regions
	 * @throws DataAccessException
	 */
	public List<Map<String, String>> getAllRegionsList(Map<Object, Object> map)
			throws DataAccessException;

	public void changeAssignmentAddressDetails(Map<Object, Object> map);
	
	public Map<String, String> getAssignmentAndAddressDetails(int assignmentId);

	public List<PersonDocHistoryVO> getunknownOutOfStateProviderDocuments(Map<Object, Object> inputParameterMap);

	public List<AssignmentsListVO> loadClosedAssignmentsForReview(
			Map<String, Object> inputParameterMap);

	public int updateTheReviewStatusInSurvAssignment(
			Map<Object, Object> inputOutputParameterMap);

	public int addAssignmentActivity(
			Map<Object, Object> inputOutputParameterMap);

	public void triggerSurvAssignment(Map<String, Object> inputParameterMap);

	public Integer getOpenAssignmentIdByTrgDoc(int documentId);
	
	public Integer getOpenAssignmentIdByAhiDoc(int documentId);

	public void updateReviewedDateForAllClosedAssignments(
			Map<String, Object> inputParameterMap);

	public int updateAssignmentWithAHIDocument(
			Map<String, Object> inputParameterMap);
	
	public int updateAssignmentWithEditAHIDocument(
			Map<String, Object> inputParameterMap);

	public Map<String, Object> getTriggeringDocumentDetails(int assignmentId);
	
	public String getStatenoForAssignment(long DOCUMENT_ID);

	public List<AssignmentsListVO> getPriorityAssignmentsList(
			Map<String, Object> map);

	public void discardAcrfDocIdFromSurveillanceAssignment(
			Map<Object, Object> docIdMap);
	
	public void discardReabAcrfDocIdFromSurveillanceAssignment(
			Map<Object, Object> docIdMap);

	public int updateDocumentFileDisplayName(
			Map<String, Object> inputParameterMap);

	public String getCurrentAssignmentStatus(long assignmentId);

	public List<Map<String, String>> getReviewActivityTypeCodes(Map<String, Object> map);
	
	public List<Map<String, String>> getUserCORoleCodes(int workerId) throws DataAccessException;
	
	public String getWorkerCORegionId(Map<Object, Object> map) throws DataAccessException;
	
	public List<JSONObject> getReabstractAssignmentStatusByRole(Map<Object, Object> map) throws DataAccessException;	
	
	public void reabAssignAssignments(Map<Object, Object> map) throws DataAccessException;
	
	public List<Map<String, String>> getReabActivities()	throws DataAccessException;
	
	public int updateReabStatus(Map<Object, Object> inputOutputParameterMap);

	public List<AssignmentsListVO> getAssignmentsListOfReabstract(Map<Object, Object> map) throws DataAccessException;
	
	public List<String> getReabComparingVariables(String tableName) throws DataAccessException;
	
	public void saveFsReabCRFDiff(Map<String, Object> map);
	
	public Map<String, Object> getRawCRFDocData(Map<String, Object> map);
	
	public List<Map<String, String>> getRawCRFData(Map<String, Object> map);
	
	public List<Map<String, Object>> getRawCRFDataObj(Map<String, Object> map);
	
	public List<String> getLabFullDiffSrcId(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getLabPartialDiffSrcId(Map<String, Object> map);
	
	public List<String> getCD4FullDiffSrcId(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getCD4PartialDiffSrcId(Map<String, Object> map);
	
	public List<String> getARVFullDiffUseType(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> getARVPartialDiffUseType(Map<String, Object> map);
	
	public Map<String, Object> getReabAssignmentClosureCategories(int assignmentId);
	
	public List<Map<String, Object>> getAssignmentCompareData(int assignmentId);
	
	public List<JSONObject> getReabstractionDiffData(int assignmentId);	
	
	public List<Map<String, String>> getReabstractionWorkerNamesByRegion(String regionCode)	throws DataAccessException;

	public List<JSONObject> getOpenCaseloadSummaryDataReport();

	public Map<String, String> getAssignmentStatusByACRF(int acrfDocId);

	public String checkForNonCaseClosureCateogory(String closureCategory);

	public List<JSONObject> getVlStatenosToReview(Map<String, Object> inputParameterMap);

	public int updateVlDocumentStatus(Map<String, Object> inputParameterMap);

	public List<Map<String, String>> getVlDocumentReviewCategories();
	
	public void eharsPersonViewQuery(Map<String, String> inputParameterMap);
	
}
