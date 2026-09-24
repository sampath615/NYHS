package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * This is implementation of the AssignmentService interface. This will provide
 * method for loading Assignment and Assignment Activites
 *
 * @author ska03
 * 
 */
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.EharsVariableConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AcrfDocLabsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.AssignmentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentActivityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentUpdateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentWorkerRegionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsAssignReassignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsHomeVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.ReabAssignmentsAssignVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.AcrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.CodedArvMedicationsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfCd4TstVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf.RawAcrfLabsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ACRFDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.AHIDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;


@Service
public class AssignmentServiceImpl implements AssignmentService {

	private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);
	private String nyehmsLog = AppConstants.nyehmLogDebugString.getName();

	private static final String PIPE_SYMBOL = "|";
	
	private static final String SPLIT_PIPE_REGEX = "\\|";
	
	@Autowired
	private AssignmentDAO assignmentDAO;
	
	@Autowired
	private AssignmentTransactionHelper assignmentTransactionHelper;

	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	@Autowired
	private ConnectionDAO connectionDAO;

	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	AHIDocumentService ahiDocumentService;
	
	@Autowired	ACRFDocumentService acrfFDocumentService;
	@Autowired AssignmentReabstractUtil assignmentReabstractUtil;
	@Autowired AcrfDocLabsDAO acrfDocLabsDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AssignmentWorkerRegionsVO> getAssignmentWorkerRegions(
			String workerId, BindingResult result, UserObject userObject,
			String sessionID) {
		boolean errorFlag = false;
		List<AssignmentWorkerRegionsVO> list = new ArrayList<AssignmentWorkerRegionsVO>();
		try {
			list = assignmentDAO.getAssignmentWorkerRegions(workerId);
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, String> getWorkerNamesByRegion(String regionCode,
			BindingResult result, UserObject userObject, String sessionID) {

		boolean errorFlag = false;
		Map<Integer, String> workers = new LinkedHashMap<Integer, String>();

		try {
			List<Map<String, String>> workerNamesList = assignmentDAO
					.getWorkerNamesByRegion(regionCode);
			for (Map<String, String> statusMap : workerNamesList) {
				workers.put(Integer.parseInt(statusMap.get("workerId")),
						statusMap.get("fullName"));
			}
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return workers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getAssignmentStatusByRole(int roleCode,
			BindingResult result, UserObject userObject, String sessionID) {

		boolean errorFlag = false;
		Map<String, String> status = new LinkedHashMap<String, String>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("roleCode", roleCode);
		try {
			List<Map<String, String>> statusMapList = assignmentDAO
					.getAssignmentStatusByRole(map);
			for (Map<String, String> statusMap : statusMapList) {
				status.put(statusMap.get("code"), statusMap.get("desc"));
			}

			String adminClosedText = status.get("CLS_ADM");
			status.put("CLS_ADM", adminClosedText + " (Last 30 Days)");
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return status;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer assignReassign(AssignmentsAssignReassignVO assignReassignVO, String reviewed, 
			BindingResult result, UserObject userObject, String sessionID) {
		boolean errorFlag = false;

		String transactionMessage = null;
		Integer statusCode = null;

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("p_assignment_list", assignReassignVO.getAssignmentIds());
		map.put("p_worker_id", assignReassignVO.getWorkerId());
		map.put("p_comments", assignReassignVO.getComments());
		map.put("p_review", reviewed);
		map.put("p_workerId", userObject.getWorkerId());
		map.put("p_supervisor_region_id",
				assignReassignVO.getSupervisorRegionId());
		map.put("p_deleted_crf", assignReassignVO.getDelCrf());

		log.debug(nyehmsLog + "  assign reassign assignReassignType : {}",  assignReassignVO.getAssignReassignType());
		log.debug("------------------------------ assignmentIdsArr : {}", assignReassignVO.getAssignmentIds());

		try {
			if (assignReassignVO.getAssignReassignType().equals(
					"reOpen")) {
				assignmentDAO.reOpenAssignments(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully reopened the Assignment(s)"
						: (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,
						transactionMessage, result);

			}
			else if (assignReassignVO.getAssignReassignType().equals(
					"requestReassign")) {
				assignmentDAO.requestReassign(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully requested for Assignment(s)"
						: (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,
						transactionMessage, result);

			} else if (assignReassignVO.getAssignReassignType().equals(
					"rejectReassign")) {
				assignmentDAO.rejectReassign(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully rejected the Assignment(s)"
						: (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,
						transactionMessage, result);

			} else if (assignReassignVO.getAssignReassignType().equals(
					"revokeReassign")) {
				assignmentDAO.revokeReassign(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully revoked the Assignment(s)"
						: (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,
						transactionMessage, result);
			} else {// assign, reassign

				assignmentDAO.makeAssignments(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully assigned the Assignment(s)"
						: (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,
						transactionMessage, result);
			}

			log.debug("{}  assign reassign p_code_out : {}",nyehmsLog, map.get("p_code_out"));
					
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag) {
				commonExceptionHandler
						.inspectDBTransacionResult(
								-1,
								"Failed to update the Assignment, Please contact the helpline.",
								result);
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
			}
		}
		return statusCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentsListVO> getAssignmentsList(
			AssignmentsHomeVO assignmentsHomeVO, BindingResult result,
			UserObject userObject, String sessionID) {
		boolean errorFlag = false;

		String startDate = assignmentsHomeVO.getFromDate();
		String endDate = assignmentsHomeVO.getTodate();
				
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("p_workerId", assignmentsHomeVO.getWorkerId());
		map.put("p_workerRegionId", assignmentsHomeVO.getWorkerRegionId());
		map.put("p_assignmentType", assignmentsHomeVO.getAssignmentType());
		map.put("p_roleCode", assignmentsHomeVO.getWorkerRoleCode());
		map.put("p_regionCode", assignmentsHomeVO.getRegionCode());
		map.put("p_module_id", "16");
		map.put("p_access_user_id", userObject.getWorkerId());
		
		if(CommonUtil.isNotNullAndEmpty(startDate)){
			map.put("p_startDate", dateUtil.getNyehmsDateFromString(startDate));
		}else{
			map.put("p_startDate", startDate);
		}
		if(CommonUtil.isNotNullAndEmpty(endDate)){
			map.put("p_endDate",  dateUtil.getNyehmsDateFromString(endDate));
		}else{
			map.put("p_endDate", endDate);
		}
		
		map.put("p_calling_app_name", "NYEHMS");
		
		List<AssignmentsListVO> assignmentList = null;

		try {
			// This code is added for get assignment list of reabstract and role code is changed from 4 to 1 as per written in procedure
			if (CommonUtil.isNotNullAndEmpty(assignmentsHomeVO.getReabstract()) && assignmentsHomeVO.getReabstract().equals("Y")) {
				if (assignmentsHomeVO.getWorkerRoleCode() == 4) {
					map.put("p_roleCode", 1);
				}
				assignmentDAO.getAssignmentsListOfReabstract(map);
			} else {
				if (assignmentsHomeVO.getWorkerRoleCode() == 3
						|| assignmentsHomeVO.getWorkerRoleCode() == 4 || assignmentsHomeVO.getWorkerRoleCode() == 5) {
					// calling different store procedure for "Co viewer and
					// coordinator roles
					assignmentDAO
							.getAssignmentsListForViewerOrCoordinatiorRole(map);
				} else {
					// For worker and Supervisor roles
					assignmentDAO.getAssignmentsList(map);
				}
			}
			assignmentList = (List<AssignmentsListVO>) map.get("refCursor");
			log.debug("{}  Returned List of Records are : {}", nyehmsLog, assignmentList.size());
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return assignmentList;
	}
	
	@Override
	public List<AssignmentsListVO> getPriorityAssignmentsList(String priorityType, 
			AssignmentsHomeVO assignmentsHomeVO,
			BindingResult result, UserObject userObject, String sessionID){
		boolean errorFlag = false;

				
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("p_workerId", assignmentsHomeVO.getWorkerId());
		map.put("p_assignmentType", assignmentsHomeVO.getAssignmentType());
		map.put("p_regionCode", assignmentsHomeVO.getRegionCode());
		
		Calendar calendar = Calendar.getInstance();
		
		map.put("currentYear", calendar.get(Calendar.YEAR));
		map.put("previousYear", calendar.get(Calendar.YEAR)-1);
		map.put("priorityType", priorityType);
		
		
		List<AssignmentsListVO> assignmentList = null;

		try {

			assignmentList = assignmentDAO.getPriorityAssignmentsList(map);
			
			log.debug("{}  Returned List of Records are : {}",nyehmsLog, assignmentList.size());
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return assignmentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AssignmentDetailsVO getAssignmentsDetails(long assignmentId,
			BindingResult result, UserObject userObject, String sessionID) {
		boolean errorFlag = false;

		Map<Object, Object> map = new HashMap<Object, Object>();
		List<AssignmentDetailsVO> details = new ArrayList<AssignmentDetailsVO>();
		List<AssignmentActivityVO> activity = new ArrayList<AssignmentActivityVO>();

		map.put("assignmentId", assignmentId);
		map.put("workerId", userObject.getWorkerId());
		map.put("details", details);
		map.put("activity", activity);

		List<AssignmentDetailsVO> assignmentDetailsVOList = new ArrayList<AssignmentDetailsVO>();
		List<AssignmentActivityVO> assignmentActivityVOList = new ArrayList<AssignmentActivityVO>();
		List<Integer> surveillanceAssignmentDocs = null;

		AssignmentDetailsVO assignmentDetailsVO = new AssignmentDetailsVO();
		try {
			assignmentDAO.getAssignmentDetails(map);
			//Assignment details
			assignmentDetailsVOList = (List<AssignmentDetailsVO>) map
					.get("details");
			//Person docs associated to the Assignment
			surveillanceAssignmentDocs = (List<Integer>) map
					.get("docs");
			//Activities performed on the Assignment
			//assignmentActivityVOList = (List<AssignmentActivityVO>) map.get("activity");

			if(assignmentDetailsVOList == null || assignmentDetailsVOList.size()  == 0){
				throw new DataRetrievalFailureException("Failed to get the Assignment details for the Assignment  "+assignmentId);
			}
			assignmentDetailsVO = assignmentDetailsVOList.get(0);
			assignmentDetailsVO
					.setAssignmentActivityVOList(assignmentActivityVOList);
			assignmentDetailsVO.setSurveillanceAssignmentDocs(surveillanceAssignmentDocs);

			log.debug("------------------------------ getAssignmentsDetails : {}"
					, assignmentDetailsVO.getAssignmentId());
			log.debug("------------------------------ getAssignmentsDetails : {}"
					, assignmentDetailsVO.getClosureCategory());
			log.debug("------------------------------ getAssignmentsDetails : {}"
					, assignmentDetailsVO.getCrfDocId());

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + " unable to get the Assignment details for Assignment : {}  {}",assignmentId,e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + " unable to get the Assignment details for Assignment : {}  {}",assignmentId,e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return assignmentDetailsVO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, String>> getAssignmentClosureStatus(
			BindingResult result, UserObject userObject, String sessionID, boolean includeAllClosureStatus, String closureStatusCategoryType) {

		boolean errorFlag = false;
		List<Map<String, String>> closureCategoryStatusList = null;

		try {
			
			Map<String , Object> map = new HashMap<String , Object>();
			
			map.put("includeAllClosureStatus", includeAllClosureStatus);
			
			map.put("closureStatusCategoryType", closureStatusCategoryType);
			
			
			if("R".equals(closureStatusCategoryType)) {
				closureCategoryStatusList = assignmentDAO.getReviewActivityTypeCodes(map);
			}else {
				closureCategoryStatusList = assignmentDAO
						.getAssignmentClosureStatus(map);
			}
			
			
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return closureCategoryStatusList;
	}

	
	@Override
	public void updateOrClosedAssignment(AHIDocVO aHIDocVO, AssignmentUpdateVO assignmentUpdateVO,
			BindingResult result, UserObject userObject){
		
		
		boolean isAHIDocAvailable = false;
		
		String transactionMessage = null;
		
		Integer statusCode = 0 ;
		
		try{
			
			if(CommonUtil.isNotNullAndEmpty(assignmentUpdateVO.getClosureCategory()) && CommonUtil.isNotNullAndEmpty(aHIDocVO.getFNAME())){
				
				isAHIDocAvailable = true;
			}
			
			assignmentTransactionHelper.updateAssignment(assignmentUpdateVO, aHIDocVO, result, userObject);
			
			
			
		
			
		}catch(DataAccessException exception){
			
			statusCode = -1;
			
			transactionMessage = exception.getMessage();
    		
			log.error("Assignment Id  : {}",assignmentUpdateVO.getDocumentId());
			
    		log.error("Error while updating the Assignment  {}",exception.getMessage());
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		
    		
    		
		} catch (Exception exception) {
			
			
    		
    		commonExceptionHandler.handleException(exception, userObject, userObject.getSessionId());
    		

			statusCode = -1;
    		
    		if(isAHIDocAvailable){
				
				transactionMessage = "Error when closing Assignment, please contact the Helpline.";
				
			}else if(CommonUtil.isNotNullAndEmpty(assignmentUpdateVO.getClosureCategory())){
				transactionMessage = "Error when closing Assignment, please contact the Helpline.";
			}else{
				transactionMessage = "Error when adding the Comment, please contact the Helpline.";
			}
    		
    		log.error("Assignment Id  : {}",assignmentUpdateVO.getDocumentId());
			
    		log.error(transactionMessage+"     :     "+exception.getMessage());
    		
		}

		if(statusCode == -1){
			commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, result);
		}

		
		
		
	}
	
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateAssignment(AssignmentUpdateVO assignmentUpdateVO,
			BindingResult result, UserObject userObject) {

		Map<Object, Object> map = new HashMap<Object, Object>();
		
		long document_id =  assignmentUpdateVO.getDocumentId();
		
		map.put("p_document_id", document_id);
		map.put("p_user_id", userObject.getWorkerId());
		
		if(CommonUtil.isNotNullAndEmpty(assignmentUpdateVO.getClosureCategory())){
		
			map.put("p_closure_category", assignmentUpdateVO.getClosureCategory());
			
			String assignmentStatus = assignmentDAO.getCurrentAssignmentStatus(document_id);
			
			if(CommonConstants.CLOSED_STATUS.equals(assignmentStatus) || CommonConstants.CLS_ADM_STATUS.equals(assignmentStatus)){
				
				log.error(document_id+"   : Assignment is already closed");
				
				throw new DataRetrievalFailureException("Assignment is already closed, please reload the page");
			}
				
			log.error("Current Assignment({}) status  {}",document_id,assignmentStatus);
			log.error("Closing the Assignment........");
			
		}
		
		map.put("p_comments", assignmentUpdateVO.getComments());
		
		map.put("p_reab_yn", assignmentUpdateVO.getReabstract());

		assignmentDAO.updateAssignment(map);

			log.debug("------------------------------ updateAssignment p_code_out: {}"
					, map.get("p_code_out"));
			log.debug("------------------------------ updateAssignment p_msg_out: {}"
					, map.get("p_msg_out"));

			if ((map.get("p_code_out") == null)
					|| (Integer) map.get("p_code_out") != 0) {
			
			throw new DataRetrievalFailureException((String)map.get("p_msg_out"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> loadEharsPersonViewDetails(String stateno,
			BindingResult dummyBindingResult, UserObject userObject,
			String sessionID) {

		Connection connection = connectionDAO.getSqlServerConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;

		Map<String, String> eharsPersonData = new LinkedHashMap<String, String>();
	
		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("SQL server database Connection is available "+connection);
				}
				statement = connection.createStatement();
				
				if(log.isDebugEnabled()){
					log.debug("Statement object for SQL server database "+statement);
				}
				
				
				
				

				if(stateno != null || !"".equals(stateno)){
					
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("p_stateno", stateno);
					
					assignmentDAO.eharsPersonViewQuery(map);
					
					String sqlQuery = map.get("p_ehars_sql");
					
					resultSet = statement.executeQuery(sqlQuery);
					if (log.isDebugEnabled()) {
						log.debug("Loading Ehars data {}", sqlQuery);
					}

					ResultSetMetaData rsMetaData = resultSet.getMetaData();

					int columnCount = rsMetaData.getColumnCount();
					List<String> columnNames = new ArrayList<String>();
					for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
						columnNames.add(rsMetaData.getColumnName(columnIndex + 1));

					}

					while (resultSet.next()) {

						for (String columnName : columnNames) {

							String columnValue = resultSet.getString(columnName);
							String fieldName = EharsVariableConstants.EHARS_VAR_NAME_MAP.get(columnName);

				           eharsPersonData.put(fieldName, columnValue);
						}

					}
					//eharsPersonData.forEach((k,v) -> System.out.println(k + "=" + v));
					if (log.isDebugEnabled()) {
						log.debug("Results retuned in the Query  {}", eharsPersonData);
					}
				}
				// If no ehars data available for stateno then loading the
				// column names with the Empty values
				if (eharsPersonData.size() == 0) {
					if (log.isDebugEnabled()) {
						log.debug("Creating the empty column names");
					}
					for (String columnName : EharsVariableConstants.EHARS_VAR_NAME_MAP.keySet()) {

						eharsPersonData.put(EharsVariableConstants.EHARS_VAR_NAME_MAP.get(columnName), "");

					}
				}

			}else{
				log.error("Connection is null while retrieving the data from the Ehars");
			}
		} catch (SQLException sqlException) {

			log.error("Exception while retrieving the Ehars data from SQL Server {}"
					, sqlException.getMessage());
		} catch (Exception sqlException) {

			log.error("Exception while retrieving the Ehars data from SQL Server {}"
					, sqlException.getMessage());
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
				log.error("Exception while closing the connection {}"
						, sqlException.getMessage());
			}
		}

	return eharsPersonData;
    }
	
//	private String formattedEharsVariableValue(String variableName, String variableValue) {
//
//		if (variableValue != null && variableValue.indexOf(PIPE_SYMBOL) != -1) {
//
//			Integer variableIndex = EharsVariableConstants.EHARS_VARIABLES.get(variableName);
//			String[] eharsVariables = variableValue.split(SPLIT_PIPE_REGEX);
//
//			if (variableIndex != null && eharsVariables.length > variableIndex) {
//				variableValue = eharsVariables[variableIndex];
//
//				variableValue = variableValue.replaceAll("\\~", "");
//			} else if (eharsVariables.length == 2) {
//				variableValue = variableValue.replaceAll("\\|", "");
//			} else if ("transfusion".equals(variableName)) {
//				variableValue = variableValue.replaceAll("\\|", " ");
//			}
//
//		}
//
//		return variableValue;
//	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AssignmentActivityVO> getAssignmentViewedCommentDetails(
			long assignmentId, BindingResult dummyBindingResult,
			UserObject userObject, String sessionID) {
		boolean errorFlag = false;
		List<AssignmentActivityVO> assignmentActivitiesList = null;
		try {
			assignmentActivitiesList = assignmentDAO
					.getAssignmentViewedCommentDetails(assignmentId);
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				dummyBindingResult.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}

		return assignmentActivitiesList;

	}
	
	@Override
	public List<AssignmentActivityVO> getAssignmentEditActionCommentDetails(
			long assignmentId, BindingResult dummyBindingResult,
			UserObject userObject, String sessionID, String reabstract) {
		boolean errorFlag = false;
		List<AssignmentActivityVO> assignmentActivitiesList = null;
		try {
			
			Map<String, Object> inputOutputParameterMap = new HashMap<String, Object>();
			
			inputOutputParameterMap.put("assignmentId", assignmentId);
			if (CommonUtil.isNotNullAndEmpty(reabstract) && reabstract.equals("Y")) {
				inputOutputParameterMap.put("reabstract", 1);
			} else {
				inputOutputParameterMap.put("reabstract", 0);
			}
			
			assignmentActivitiesList = assignmentDAO
					.getAssignmentEditActionCommentDetails(inputOutputParameterMap);
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  {}", e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				dummyBindingResult.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}

		return assignmentActivitiesList;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getUserRoleCodes(
			BindingResult dummyBindingResult, UserObject userObject) {
		boolean errorFlag = false;
		List<Map<String, String>> userRoleCodes = null;

		Map<String, String> userRoleCodeMap = new HashMap<String, String>();
		try {

			userRoleCodes = assignmentDAO.getUserRoleCodes(userObject
					.getWorkerId());

			for (Map<String, String> roleCodeAndDesc : userRoleCodes) {
				userRoleCodeMap.put(roleCodeAndDesc.get("ROLE_CODE"),
						roleCodeAndDesc.get("ROLE_DESC"));
			}

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject,
					userObject.getSessionId());
		} finally {
			if (errorFlag)
				dummyBindingResult.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return userRoleCodeMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, String>> getRegionsMapForSelectedRole(
			Integer roleCode, BindingResult dummyBindingResult,
			UserObject userObject) {
		boolean errorFlag = false;
		List<Map<String, String>> userRegionsMapForSelectedRoleMap = null;

		try {

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("roleCode", roleCode);
			map.put("workerId", userObject.getWorkerId());

			if (roleCode == 3 || roleCode == 4 || roleCode == 5) {
				userRegionsMapForSelectedRoleMap = assignmentDAO
						.getAllRegionsList(map);
			} else {
				userRegionsMapForSelectedRoleMap = assignmentDAO
						.getRegionsMapForSelectedRole(map);
			}

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject,
					userObject.getSessionId());
		} finally {
			if (errorFlag)
				dummyBindingResult.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return userRegionsMapForSelectedRoleMap;
	}

	
	@Override
	public Integer changeAssignmentAddressDetails(int assignmentId, String origin, String closureCategory, long supervisorRegionId,  
			long addressId, String addressType, String countyCode, UserObject userObject, BindingResult bindingResult){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("p_document_id", assignmentId);
		map.put("p_assign_address_type", addressType);
		map.put("p_prov_fac_person_id", addressId);
		map.put("p_county_code", countyCode);
		map.put("p_worker_id", userObject.getWorkerId());
		map.put("p_Call_From", origin);
		map.put("p_closure_category", closureCategory);
		map.put("p_supervisor_region_id", supervisorRegionId);
		
		assignmentDAO.changeAssignmentAddressDetails(map);
		
		String transactionMessage = null;
		
		Integer statusCode = (Integer) map.get("p_code_out");
		
		transactionMessage = (String)map.get("p_msg_out");
		
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
		
		return statusCode;
	
	}
	
	
	@Override
	public boolean changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(int assignmentId, String origin, String closureCategory, long supervisorRegionId,  
			long addressId, String addressType, String countyCode, UserObject userObject, BindingResult bindingResult){
		
		
		boolean isError = false; 
		String trasactionMessage = null;
		try{
			assignmentTransactionHelper.changeAssignmentAddressDetailsWithExistingRawFacilityOrProvider(assignmentId,  origin,  closureCategory,  supervisorRegionId,  
					 addressId,  addressType,  countyCode,  userObject,  bindingResult);
		}catch(DataAccessException dataAccessException){
			isError = true;
			trasactionMessage = "Failed while changing the Assignment Address. please contact helpline"; 
			
			log.error(dataAccessException.getMessage());
			
			log.error(trasactionMessage);
			
			
		}catch(Exception exception){
			isError = true;
			
			trasactionMessage = "Failed while changing the Assignment Address. please contact helpline"; 
			
			log.error(trasactionMessage);
			
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, trasactionMessage, bindingResult);
		}
		
		return isError;
		
		
	}
	
	@Override
	public void changeAssignmentAddressDetailsByNewRawFacility(
			int assignmentId, String origin, String closureCategory, long supervisorRegionId, RawSubFacilityVO rawSubFacilityVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult){
		
		
		boolean isError = false; 
		String trasactionMessage = null;
		try{
			assignmentTransactionHelper.changeAssignmentAddressDetailsByNewRawFacility(
					assignmentId, origin, closureCategory, supervisorRegionId, rawSubFacilityVO,
					addressType, userObject,
					bindingResult);
		}catch(DataAccessException dataAccessException){
			isError = true;
			trasactionMessage = "Failed to change the Assignment Facility details while creating new Raw Facility."; 
			
			log.error(dataAccessException.getMessage());
			
			log.error(trasactionMessage);
			
			
		}catch(Exception exception){
			isError = true;
			
			trasactionMessage = "Failed to change the Assignment Facility details while creating new Raw Facility."; 
			
			log.error(trasactionMessage);
			
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, trasactionMessage, bindingResult);
		}
		
		
		
	}
	
	
	@Override
	public void changeAssignmentAddressDetailsByNewRawProvider(
			int assignmentId, String origin, String closureCategory, long supervisorRegionId, SubProviderAndAddressVO subProviderAndAddressVO,
			String addressType, UserObject userObject,
			BindingResult bindingResult){
		
		
		boolean isError = false; 
		String trasactionMessage = null;
		try{
			assignmentTransactionHelper.changeAssignmentAddressDetailsByNewRawProvider(
					assignmentId, origin, closureCategory, supervisorRegionId, subProviderAndAddressVO,
					addressType, userObject,
					bindingResult);
		}catch(DataAccessException dataAccessException){
			isError = true;
			trasactionMessage = "Failed to change the Assignment Provider details while creating new Raw Provider Address."; 
			
			log.error(dataAccessException.getMessage());
			
			log.error(trasactionMessage);
			
			
		}catch(Exception exception){
			isError = true;
			
			trasactionMessage = "Failed to change the Assignment Provider details while creating new Raw Provider Address."; 
			
			log.error(trasactionMessage);
			
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, trasactionMessage, bindingResult);
		}
	}
	
	@Override
	public Map<String, String> getAssignmentAndAddressDetails(int assignmentId,
			BindingResult dummyBindingResult, UserObject userObject){
		return assignmentDAO.getAssignmentAndAddressDetails(assignmentId);
	}
	
	public List<PersonDocHistoryVO> getunknownOutOfStateProviderDocuments(
			String documentType, String processCode, BindingResult dummyBindingResult, UserObject userObject){
		Map<Object, Object> inputParameterMap = new HashMap<Object, Object>();
		inputParameterMap.put("documentType", documentType);
		inputParameterMap.put("processCode", processCode);
		
		return assignmentDAO.getunknownOutOfStateProviderDocuments(inputParameterMap);
	}

	@Override
	public List<AssignmentsListVO> loadClosedAssignmentsForReview(
			String closureCategory, BindingResult bindingResult,
			UserObject userObject, String id){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("closureCategory", closureCategory);
		
		inputParameterMap.put("p_module_id", 16);
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		
		return assignmentDAO.loadClosedAssignmentsForReview(inputParameterMap);
	}

	@Override
	public void triggerSurvAssignment(int documentId, String documentType,
			String triggerAssignmentCheckStatus, String ahiFlag, String stateNo, UserObject userObject,
			BindingResult bindingResult){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("p_document_id", documentId);
		inputParameterMap.put("p_document_type_code", LabCommonUtils.getDocumentType(documentType));
		inputParameterMap.put("p_USER_INPUT", triggerAssignmentCheckStatus);
		inputParameterMap.put("p_AHI_FLAG", ahiFlag);
		inputParameterMap.put("p_worker_id", userObject.getWorkerId());
		
		assignmentDAO.triggerSurvAssignment(inputParameterMap);
		
		Integer statusCode = (Integer) inputParameterMap.get("p_code");
		
		
		if(statusCode == 0 && "Y".equals(ahiFlag)){

			ahiDocumentService.sendEmailNotificationToAHIUsers(userObject, "Flagged as Potential AHI", stateNo + " flagged by " + userObject.getUserFirstName() +  " " + userObject.getUserLastName() + " " + new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()));
			
		}
		
		String transactionMessage = (String)inputParameterMap.get("p_msg");
		
		commonExceptionHandler.inspectDBTransacionResult(statusCode, transactionMessage, bindingResult);
		
	}
	
	@Override
	public Integer getOpenAssignmentIdByTrgDoc(int documentId){
		return assignmentDAO.getOpenAssignmentIdByTrgDoc(documentId);
	}
	
	@Override
	public Integer getOpenAssignmentIdByAhiDoc(int documentId){
		return assignmentDAO.getOpenAssignmentIdByAhiDoc(documentId);
	}
	
	
	@Override
	public void updateReviewedDateForAllClosedAssignments(int documentId, UserObject userObject){
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		inputParameterMap.put("p_document_id", documentId);
		inputParameterMap.put("p_workerId", userObject.getWorkerId());
		
		assignmentDAO.updateReviewedDateForAllClosedAssignments(inputParameterMap);
	}
	
	@Override
	public void closeNeedsAssignment(AssignmentsAssignReassignVO assignReassignVO,
			BindingResult bindingResult, UserObject userObject){
		
		boolean errorFlag = false;
		
		String transactionMessage = null;
		try {
			
			
			if(CommonUtil.isNotNullAndEmpty(assignReassignVO.getAssignmentIds())){
				
				
				
				assignmentTransactionHelper.closeNeedsAssignment(assignReassignVO.getAssignmentIds(), assignReassignVO.getComments(), userObject);
				
				commonExceptionHandler.inspectDBTransacionResult(0,
						"Successfully closed the Assignment(s) administratively", bindingResult);
				
				
			}else{
				
				errorFlag = true;
				
				transactionMessage = "No Assignment(s) selected to close.";
				
			}
			
		} catch (DataAccessException e) {
			errorFlag = true;
			
			log.error(nyehmsLog + "  " + e.getMessage());
			
			
			transactionMessage = "Failed while closing the Assignment(s), please contact Helpline";
		} catch (Exception e) {
			errorFlag = true;
			
			log.error(nyehmsLog + "  " + e.getMessage());
			transactionMessage = "Failed while closing the Assignment(s), please contact Helpline";
		}
		
		
		if(errorFlag){
			
			log.error("Assignment IDs  : {}",assignReassignVO.getAssignmentIds());
			
			log.error("Transaction Message  {}", transactionMessage);
			
			
			commonExceptionHandler.inspectDBTransacionResult(-1,
					transactionMessage, bindingResult);
			
			
		}
		
		
		
		
	}
	
	@Override
	public void closeAssignment(Map<Object, Object> map){
		assignmentDAO.updateAssignment(map);
	}
	
	@Override
	public void updateAssignmentWithAHIDocument(long assignmentIdOfAHI,
			Integer ahiDocumentId, BindingResult dummyBindingResult,
			UserObject userObject){
		
		Map<String, Object> inputParameterMap  = new HashMap<String, Object>();
		
		inputParameterMap.put("ahiDocumentId", ahiDocumentId);
		
		inputParameterMap.put("assignmentIdOfAHI", assignmentIdOfAHI);
		
		inputParameterMap.put("workerId", userObject.getWorkerId());
			
		int resultCount  = assignmentDAO.updateAssignmentWithAHIDocument(inputParameterMap);
		
		if(resultCount == 0){
			
			log.error("Failed to update the Assignment with AHI Doc Id, Assignment Id : {} ",assignmentIdOfAHI);
			
			throw new DataRetrievalFailureException("Error when updating Assignment with AHI document Id, please contact the Helpline. ");
		}
		
		
	}
	
	@Override
	public void updateAssignmentWithEditAHIDocument(long parentDocumentId,
			Integer ahiDocumentId, BindingResult dummyBindingResult,
			UserObject userObject){
		
		Map<String, Object> inputParameterMap  = new HashMap<String, Object>();
		
		inputParameterMap.put("ahiDocumentId", ahiDocumentId);
		
		inputParameterMap.put("parentDocumentId", parentDocumentId);
		
		inputParameterMap.put("workerId", userObject.getWorkerId());
			
		assignmentDAO.updateAssignmentWithEditAHIDocument(inputParameterMap);
		
	}
	
	@Override
	public Map<String, Object> getTriggeringDocumentDetails(int assignmentId){
		
		return assignmentDAO.getTriggeringDocumentDetails(assignmentId);
		
		
	}
	
	@Override
	public String getStatenoForAssignment(long documentId){
		return assignmentDAO.getStatenoForAssignment(documentId);
	}
	
	@Override
	public void discardAcrfDocIdFromSurveillanceAssignment(
			Map<Object, Object> docIdMap){
		assignmentDAO.discardAcrfDocIdFromSurveillanceAssignment(docIdMap);
	}
	
	@Override
	public void discardReabAcrfDocIdFromSurveillanceAssignment(
			Map<Object, Object> docIdMap){
		assignmentDAO.discardReabAcrfDocIdFromSurveillanceAssignment(docIdMap);
	}
	
	@Override
	public void updateDocumentFileDisplayName(String fileName,
			String displayName, String documentType, UserObject userObject,
			BindingResult bindingResult){
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("displayName", displayName);
		inputParameterMap.put("documentType", documentType);
		inputParameterMap.put("fileName", fileName);
		inputParameterMap.put("workerId", userObject.getWorkerId());
		
		int resultCount = assignmentDAO.updateDocumentFileDisplayName(inputParameterMap);
		
		if(resultCount > 0){
			commonExceptionHandler.inspectDBTransacionResult(0,
					"Successfully updated the Display File name", bindingResult);
		}else{
			
			log.error("Failed to update the Display File name");
			log.error("Document Type  : {}    File Name  : {}      Display Name  : {}"+documentType,fileName,displayName);
			
			commonExceptionHandler.inspectDBTransacionResult(-1,
					"Failed to update the Display File name, please contact helpline", bindingResult);
		}
		
		
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getUserCORoleCodes(BindingResult dummyBindingResult, UserObject userObject) {
		boolean errorFlag = false;
		List<Map<String, String>> userRoleCodes = null;

		Map<String, String> userRoleCodeMap = new HashMap<String, String>();
		try {

			userRoleCodes = assignmentDAO.getUserCORoleCodes(userObject.getWorkerId());

			for (Map<String, String> roleCodeAndDesc : userRoleCodes) {
				userRoleCodeMap.put(roleCodeAndDesc.get("code"),roleCodeAndDesc.get("desc"));
			}

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject,
					userObject.getSessionId());
		} finally {
			if (errorFlag)
				dummyBindingResult.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return userRoleCodeMap;
	}

	@Override
	public String getWorkerCORegionId(int workerId, String roleCode) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("roleCode", roleCode);
		map.put("workerId", workerId);
		
		String workerCORegionId = assignmentDAO.getWorkerCORegionId(map);
		
		return workerCORegionId;
	}

	@Override
	public List<JSONObject> getReabstractAssignmentStatusByRole(int roleCode, BindingResult bindingResult, UserObject userObject) {
		boolean errorFlag = false;
		List<JSONObject> assignmentStatusForselectedRole = null;

		try {

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("roleCode", roleCode);
			map.put("workerId", userObject.getWorkerId());
			
			assignmentStatusForselectedRole = assignmentDAO.getReabstractAssignmentStatusByRole(map);

		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject,	userObject.getSessionId());
		} finally {
			if (errorFlag)
				bindingResult.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
		}
		return assignmentStatusForselectedRole;
	}

	@Override
	public Integer reabAssign(ReabAssignmentsAssignVO reabAssignVO, BindingResult bindingResult, UserObject userObject, String sessionID) {
		
		boolean errorFlag = false;

		String transactionMessage = null;
		Integer statusCode = null;
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if (CommonUtil.isNotNullAndEmpty(reabAssignVO.getReabAssignType()) ) {
			map.put("p_reab_status_cd", "REAB_A");
		}
		map.put("p_assignment_list", reabAssignVO.getAssignmentIds());
		map.put("p_worker_id", reabAssignVO.getWorkerId());
		map.put("p_activity_type_cd", "AREAB07");
		map.put("p_comments", reabAssignVO.getComments());
		map.put("p_activity_done_by", userObject.getWorkerId());

		log.debug(nyehmsLog + "  assign assignReassignType : {}", reabAssignVO.getReabAssignType());
		
		try {
				assignmentDAO.reabAssignAssignments(map);

				statusCode = (Integer) map.get("p_code_out");
				transactionMessage = statusCode == 0 ? "Successfully assigned the Assignment(s)": (String) map.get("p_msg_out");
				commonExceptionHandler.inspectDBTransacionResult(statusCode,transactionMessage, bindingResult);

				log.debug(nyehmsLog + "  assign reassign p_code_out : {}", map.get("p_code_out"));
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag) {
				commonExceptionHandler
						.inspectDBTransacionResult( -1, "Failed to update the Assignment, Please contact the helpline.", bindingResult);
				bindingResult.addError(new ObjectError("SQLError", "Transaction Failed. Please try again "));
			}
		}
		return statusCode;
	}

	@Override
	public List<Map<String, String>> getReabActivities(BindingResult bindingResult, UserObject userObject, String sessionID) {

		boolean errorFlag = false;
		List<Map<String, String>> reabActivityList = null;

		try {
			reabActivityList = assignmentDAO.getReabActivities();
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				bindingResult.addError(new ObjectError("SQLError","Transaction Failed. Please try again "));
		}
		return reabActivityList;
		
	}

	@Override
	public void updateReabActivityNStatus(String assignmentId, String reabCrfDocId, String crfDocId, String documentType, String comments, String newReabActivity, String reabClosureCategory, 
			BindingResult bindingResult, UserObject userObject, HttpServletRequest request) {
		
		boolean isError = false; 
		String transactionMessage = null;
		try{
			assignmentTransactionHelper.updateReabActivityNStatus(assignmentId, reabCrfDocId, crfDocId, documentType, comments, newReabActivity, reabClosureCategory, userObject, bindingResult, request);
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to update  Reabstraction Activity " + newReabActivity + "to the Assignment";
			log.error(assignmentId+"    "+transactionMessage);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to update  Reabstraction Activity " + newReabActivity + "to the Assignment";
			log.error(assignmentId+"       "+transactionMessage);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage+", Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully updated the activity to the Assignment", bindingResult);
		}
		
	}

	@Override
	public int updateReabActivity(String assignmentId, String newReabActivity, String comments, BindingResult bindingResult, UserObject userObject) {

		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		inputOutputParameterMap.put("assignmentId", assignmentId);
		inputOutputParameterMap.put("activityType", newReabActivity);
		inputOutputParameterMap.put("comments", comments);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		return assignmentDAO.addAssignmentActivity(inputOutputParameterMap);
		
	}

	@Override
	public int updateReabStatus(String assignmentId, String newReabStatus, String reabClosureCategory, BindingResult bindingResult,UserObject userObject) {
		
		Map<Object, Object> inputOutputParameterMap = new HashMap<Object, Object>();
		
		inputOutputParameterMap.put("assignmentId", assignmentId);
		inputOutputParameterMap.put("reabStatus", newReabStatus);
		inputOutputParameterMap.put("reabClosureCategory", reabClosureCategory);
		inputOutputParameterMap.put("workerId", userObject.getWorkerId());
		
		if (newReabStatus.equals("REAB_C") || newReabStatus.equals("REAB_N")) {
			inputOutputParameterMap.put("reabCompletedDate", dateUtil.getSqlDateTimeStamp());
		} else {
			inputOutputParameterMap.put("reabCompletedDate", "");
		}
		
		return assignmentDAO.updateReabStatus(inputOutputParameterMap);
		
	}

	@Override
	public void saveFSAndReabCRFDiff(String assignmentId, String reabCrfDocId, String fsCrfDocId, String documentType,
			BindingResult bindingResult, UserObject userObject, HttpServletRequest request) {
		
		List<String> rawCrfDocDiffList = assignmentDAO.getReabComparingVariables(LabCommonUtils.getRawTableName(documentType));
		String rawCrfDocDiffCols = String.join(",", rawCrfDocDiffList);
		
		List<String> rawCrfLabDiffList = assignmentDAO.getReabComparingVariables("RAW_" + documentType + "_LABS");
		String rawCrfLabDiffCols = String.join(",", rawCrfLabDiffList);
		
		List<String> rawCrfCD4DiffList = assignmentDAO.getReabComparingVariables("RAW_" + documentType + "_CD4");
		String rawCrfCD4DiffCols = String.join(",", rawCrfCD4DiffList);
		
		List<String> rawCrfArvDiffList = assignmentDAO.getReabComparingVariables("RAW_" + documentType + "_ARVS");
		String rawCrfArvDiffCols = String.join(",", rawCrfArvDiffList);
		
		Map<String, Object> inputParameterMap = new HashMap<String, Object>();
		
		inputParameterMap.put("tableName", LabCommonUtils.getRawTableName(documentType));
		inputParameterMap.put("document_id", "DOCUMENT_ID");
		inputParameterMap.put("docType", documentType);
		inputParameterMap.put("selectColumns", rawCrfDocDiffCols);
		inputParameterMap.put("documentId", fsCrfDocId);
		Map<String, Object> fsCRFDocData = assignmentDAO.getRawCRFDocData(inputParameterMap);
		
		inputParameterMap.put("documentId", reabCrfDocId);
		Map<String, Object> reabCRFDocData = assignmentDAO.getRawCRFDocData(inputParameterMap);
		
		inputParameterMap.put("tableName", "RAW_" + documentType + "_LABS");
		inputParameterMap.put("selectColumns", rawCrfLabDiffCols);
		inputParameterMap.put("documentId", fsCrfDocId);
		List<Map<String, Object>> fsCRFFLabData = assignmentDAO.getRawCRFDataObj(inputParameterMap);
		inputParameterMap.put("documentId", reabCrfDocId);
		List<Map<String, Object>> reabCRFFLabData = assignmentDAO.getRawCRFDataObj(inputParameterMap);
		
		inputParameterMap.put("tableName", "RAW_" + documentType + "_CD4");
		inputParameterMap.put("selectColumns", rawCrfCD4DiffCols);
		inputParameterMap.put("documentId", fsCrfDocId);
		List<Map<String, Object>> fsCRFFCD4Data = assignmentDAO.getRawCRFDataObj(inputParameterMap);
		fsCRFFCD4Data = assignmentReabstractUtil.removeBlankCd4Data(fsCRFFCD4Data);
		inputParameterMap.put("documentId", reabCrfDocId);
		List<Map<String, Object>> reabCRFFCD4Data = assignmentDAO.getRawCRFDataObj(inputParameterMap);
		reabCRFFCD4Data = assignmentReabstractUtil.removeBlankCd4Data(reabCRFFCD4Data);
		
		inputParameterMap.put("tableName", "RAW_" + documentType + "_ARVS");
		inputParameterMap.put("selectColumns", rawCrfArvDiffCols);
		inputParameterMap.put("document_id", documentType + "_DOC_ID");
		inputParameterMap.put("documentId", fsCrfDocId);
		List<Map<String, String>> fsCRFFARVSData = assignmentDAO.getRawCRFData(inputParameterMap);
		inputParameterMap.put("documentId", reabCrfDocId);
		List<Map<String, String>> reabCRFFARVSData = assignmentDAO.getRawCRFData(inputParameterMap);
		
		String diffDocFields = "";
			for (String compareFieldName : rawCrfDocDiffList) {
				if (fsCRFDocData.containsKey(compareFieldName) && reabCRFDocData.containsKey(compareFieldName) && 
						!fsCRFDocData.get(compareFieldName).equals(reabCRFDocData.get(compareFieldName)) ) {
					diffDocFields += compareFieldName + ",";
				} else if ( (fsCRFDocData.containsKey(compareFieldName) && !fsCRFDocData.get(compareFieldName).equals("../../....")
						&& !reabCRFDocData.containsKey(compareFieldName)) ||
						(!fsCRFDocData.containsKey(compareFieldName) && reabCRFDocData.containsKey(compareFieldName) 
								&& !reabCRFDocData.get(compareFieldName).equals("../../....")) ) {
					diffDocFields += compareFieldName + ",";
				}
			}
		
			if (CommonUtil.isNotNullAndEmpty(diffDocFields) && diffDocFields.endsWith(",")) {
				diffDocFields = diffDocFields.substring(0, diffDocFields.length()-1);
			}
	//	System.out.println("RAW DOC diffFields : " + diffDocFields);
		
		
		Map<String, Object> diffInsertMap = new HashMap<String, Object>();
		
		diffInsertMap.put("assignmentId", assignmentId);
		diffInsertMap.put("diffFields", diffDocFields);
		diffInsertMap.put("tabName", "CRF_INFO");
		diffInsertMap.put("fsSrcId", "");
		diffInsertMap.put("reabSrcId", "");
		if (CommonUtil.isNotNullAndEmpty(diffDocFields)) {
			diffInsertMap.put("sameValues", "N");
		} else {
			diffInsertMap.put("sameValues", "Y");
		}
		assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		
	// For Lab Info New	
		
		List<Map<String, Object>> labSameDataSrcIds = assignmentReabstractUtil.getLabSameData(fsCRFFLabData, reabCRFFLabData, documentType, rawCrfLabDiffList, assignmentId);
		
		diffInsertMap.put("tabName", "LAB_INFO");
		diffInsertMap.put("diffFields", "");
		
		for (Map<String, Object> currObj : labSameDataSrcIds) {
			diffInsertMap.put("fsSrcId", currObj.get("LAB_SRC_ID1"));
			diffInsertMap.put("reabSrcId", currObj.get("LAB_SRC_ID2"));
			diffInsertMap.put("sameValues", "Y");
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("documentId1", fsCrfDocId);
		inputParameterMap.put("documentId2", reabCrfDocId);
		inputParameterMap.put("tableName", "RAW_" + documentType + "_LABS");
		List<String> fsLabSrcIds = assignmentDAO.getLabFullDiffSrcId(inputParameterMap);
		

		diffInsertMap.put("sameValues", "N");
		diffInsertMap.put("fsSrcId", "");
		diffInsertMap.put("reabSrcId", "");

		for (String fsLabSrcId : fsLabSrcIds) {
			diffInsertMap.put("fsSrcId", fsLabSrcId);
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("documentId1", reabCrfDocId);
		inputParameterMap.put("documentId2", fsCrfDocId);
		List<String> reabLabSrcIds = assignmentDAO.getLabFullDiffSrcId(inputParameterMap);
		
		diffInsertMap.put("fsSrcId", "");
		for (String reabLabSrcId : reabLabSrcIds) {
			diffInsertMap.put("reabSrcId", reabLabSrcId);
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("fsLabSrcIds", fsLabSrcIds);
		inputParameterMap.put("reabLabSrcIds", reabLabSrcIds);
		
		rawCrfLabDiffList.remove("LAB_SRC_ID");
		inputParameterMap.put("columnNames", rawCrfLabDiffList);
		List<Map<String, Object>> labDiffSrcIds = assignmentDAO.getLabPartialDiffSrcId(inputParameterMap);
		
		for (Map<String, Object> currObj : labDiffSrcIds) {
			List<String> labDiffCols= new ArrayList<String>();
			for (Map.Entry<String, Object> entry : currObj.entrySet()) {
				if (entry.getValue().equals("false")) {
					labDiffCols.add(entry.getKey());
				}
		    }
	//		System.out.println("LAB_SRC_ID_1 : " +  currObj.get("LAB_SRC_ID1") +"  LAB_SRC_ID_2 : " +  currObj.get("LAB_SRC_ID2") + " " + String.join(",", labDiffCols));
			diffInsertMap.put("fsSrcId", currObj.get("LAB_SRC_ID1"));
			diffInsertMap.put("reabSrcId", currObj.get("LAB_SRC_ID2"));
			diffInsertMap.put("diffFields", String.join(",", labDiffCols));
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		// for CD4 new 
		inputParameterMap.clear();
		inputParameterMap.put("documentId1", fsCrfDocId);
		inputParameterMap.put("documentId2", reabCrfDocId);
		
		List<Map<String, Object>> cd4SameDataSrcIds = assignmentReabstractUtil.getCd4SameData(fsCRFFCD4Data, reabCRFFCD4Data, documentType, rawCrfCD4DiffList, assignmentId);
		
		diffInsertMap.put("tabName", "CD4_INFO");
		diffInsertMap.put("diffFields", "");
		
		for (Map<String, Object> currObj : cd4SameDataSrcIds) {
			diffInsertMap.put("fsSrcId", currObj.get("CD4_SRC_ID1"));
			diffInsertMap.put("reabSrcId", currObj.get("CD4_SRC_ID2"));
			diffInsertMap.put("sameValues", "Y");
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("tableName", "RAW_" + documentType + "_CD4");
		List<String> fsCD4SrcIds = assignmentDAO.getCD4FullDiffSrcId(inputParameterMap);
		
		diffInsertMap.put("fsSrcId", "");
		diffInsertMap.put("reabSrcId", "");
		diffInsertMap.put("sameValues", "N");
		
		for (String fsCD4SrcId : fsCD4SrcIds) {
			diffInsertMap.put("fsSrcId", fsCD4SrcId);
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("documentId1", reabCrfDocId);
		inputParameterMap.put("documentId2", fsCrfDocId);
		List<String> reabCD4SrcIds = assignmentDAO.getCD4FullDiffSrcId(inputParameterMap);
		
		diffInsertMap.put("fsSrcId", "");
		for (String reabCD4SrcId : reabCD4SrcIds) {
			diffInsertMap.put("reabSrcId", reabCD4SrcId);
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		inputParameterMap.put("fsLabSrcIds", fsCD4SrcIds);
		inputParameterMap.put("reabLabSrcIds", reabCD4SrcIds);
		
		rawCrfCD4DiffList.remove("CD4_SRC_ID");
		inputParameterMap.put("columnNames", rawCrfCD4DiffList);
		List<Map<String, Object>> cd4DiffSrcIds = assignmentDAO.getCD4PartialDiffSrcId(inputParameterMap);
		
		for (Map<String, Object> currObj : cd4DiffSrcIds) {
			List<String> cd4DiffCols= new ArrayList<String>();
			for (Map.Entry<String, Object> entry : currObj.entrySet()) {
				if (entry.getValue().equals("false")) {
					cd4DiffCols.add(entry.getKey());
				}
		    }
	//		System.out.println("CD4_SRC_ID_1 : " +  currObj.get("CD4_SRC_ID1") +"  CD4_SRC_ID_2 : " +  currObj.get("CD4_SRC_ID2") + " " + String.join(",", cd4DiffCols));
			diffInsertMap.put("fsSrcId", currObj.get("CD4_SRC_ID1"));
			diffInsertMap.put("reabSrcId", currObj.get("CD4_SRC_ID2"));
			diffInsertMap.put("diffFields", String.join(",", cd4DiffCols));
			assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		// For ARVs
		Map<String, String> arvDiffMap = assignmentReabstractUtil.getARVDiffData(reabCRFFARVSData, fsCRFFARVSData, rawCrfArvDiffList, fsCrfDocId);
		Map<String, String> arvDiffMap2 = assignmentReabstractUtil.getARVDiffData(fsCRFFARVSData, reabCRFFARVSData, rawCrfArvDiffList, reabCrfDocId);
		arvDiffMap.putAll(arvDiffMap2);
		
		diffInsertMap.put("tabName", "ARV_INFO");
		diffInsertMap.put("fsSrcId", "");
		diffInsertMap.put("reabSrcId", "");
		
		for (Map.Entry<String, String> entry : arvDiffMap.entrySet()) {
		    diffInsertMap.put("arvUseType", entry.getKey());
		    diffInsertMap.put("diffFields", entry.getValue());
		    diffInsertMap.put("sameValues", "N");
		    assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
		Map<String, String> arvSameMap = assignmentReabstractUtil.getARVSameData(reabCRFFARVSData, fsCRFFARVSData, rawCrfArvDiffList, fsCrfDocId);
		for (Map.Entry<String, String> entry : arvSameMap.entrySet()) {
		    diffInsertMap.put("arvUseType", entry.getKey());
		    diffInsertMap.put("diffFields", "");
		    diffInsertMap.put("sameValues", entry.getValue());
		    assignmentDAO.saveFsReabCRFDiff(diffInsertMap);
		}
		
	}

	@Override
	public Map<String, Object> getCRFDiffSummary(int assignmentId, String documentType, int fsCRFDocId, int reabCRFDocId,
							BindingResult bindingResult, UserObject userObject, HttpServletRequest request) {
		
		Map<String, Object> crfDiffEleMap = new HashMap<String, Object>();
		
		Map<String, Object> closureStatusMap = assignmentDAO.getReabAssignmentClosureCategories(assignmentId);
		crfDiffEleMap.put("closureCategory", closureStatusMap);
		
		AcrfFormVO fsCRFVO = acrfFDocumentService.getAcrfFormVO(Integer.toString(fsCRFDocId), documentType, bindingResult, userObject, request.getSession().getId()); 
		AcrfFormVO reabCRFVO = acrfFDocumentService.getAcrfFormVO(Integer.toString(reabCRFDocId), documentType, bindingResult, userObject, request.getSession().getId());
		
		crfDiffEleMap.put("fsRawCrfDoc", assignmentReabstractUtil.getPersonInfoCodesDesc(fsCRFVO.getRawAcrfDoc(), bindingResult, userObject, request));
		crfDiffEleMap.put("reabRawCrfDoc", assignmentReabstractUtil.getPersonInfoCodesDesc(reabCRFVO.getRawAcrfDoc(), bindingResult, userObject, request));
		
		List<Map<String, Object>> compareDataMapList = assignmentDAO.getAssignmentCompareData(assignmentId);
		
		List<CodedArvMedicationsVO> arvFsMedicationsList = fsCRFVO.getArvMedicationsList();
		List<CodedArvMedicationsVO> arvReabMedicationsList = reabCRFVO.getArvMedicationsList();
		
		List<String[]> arvInfo = assignmentReabstractUtil.createArvDataForDisplay(arvFsMedicationsList, arvReabMedicationsList);
		crfDiffEleMap.put("arvInfo", arvInfo);
		
		List<RawAcrfCd4TstVO> rawFsAcrfCd4Tst = fsCRFVO.getRawAcrfCd4Tst();
		List<RawAcrfCd4TstVO> rawReabAcrfCd4Tst = reabCRFVO.getRawAcrfCd4Tst();
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("assignmentId", assignmentId);
		inputMap.put("tabName", "CD4_INFO");
		
		List<String[]> cd4Info = assignmentReabstractUtil.createCD4DataForDisplay(rawFsAcrfCd4Tst, rawReabAcrfCd4Tst, compareDataMapList);
		crfDiffEleMap.put("cd4Info", cd4Info);
		
		List<String[]> closestToCurDx = new  ArrayList<String[]>();
		List<String[]> firstLowCd4 = new  ArrayList<String[]>();
		List<String[]> otherCd4 = new  ArrayList<String[]>();
		
		for (String[] curCd4Rec : cd4Info) {
			if (curCd4Rec[6].equals("1")) {
				closestToCurDx.add(curCd4Rec);
			} else if (curCd4Rec[6].equals("2")) {
				firstLowCd4.add(curCd4Rec);
			} else if (curCd4Rec[6].equals("3")) {
				otherCd4.add(curCd4Rec);
			}
		}
		
		crfDiffEleMap.put("closestToCurDx", closestToCurDx);
		crfDiffEleMap.put("firstLowCd4", firstLowCd4);
		crfDiffEleMap.put("otherCd4", otherCd4);
		
		List<RawAcrfLabsVO> rawFsAcrfLabs = fsCRFVO.getRawAcrfLabs();
		List<RawAcrfLabsVO> rawReabAcrfLabs = reabCRFVO.getRawAcrfLabs();		
		List<String[]> labInfo = assignmentReabstractUtil.createLabDataForDisplay(rawFsAcrfLabs, rawReabAcrfLabs, compareDataMapList);
		
		List<String[]> hivImmunoNonDiff = new  ArrayList<String[]>();
		List<String[]> hivImmunoDiff = new  ArrayList<String[]>();
		List<String[]> hivDetTestQual = new  ArrayList<String[]>();
		List<String[]> hivDetTestQualVL = new  ArrayList<String[]>();
		List<String[]> drugResTestGeno = new  ArrayList<String[]>();
		for (String[] curLabRec : labInfo) {
			if (curLabRec[0].equals("01")) {
				hivImmunoNonDiff.add(curLabRec);
			} else if (curLabRec[0].equals("02")) {
				hivImmunoDiff.add(curLabRec);
			} else if (curLabRec[0].equals("03")) {
				hivDetTestQual.add(curLabRec);
			} else if (curLabRec[0].equals("04")) {
				hivDetTestQualVL.add(curLabRec);
			} else if (curLabRec[0].equals("05")) {
				drugResTestGeno.add(curLabRec);
			}
		}
		crfDiffEleMap.put("hivImmunoNonDiff", hivImmunoNonDiff);
		crfDiffEleMap.put("hivImmunoDiff", hivImmunoDiff);
		crfDiffEleMap.put("hivDetTestQual", hivDetTestQual);
		crfDiffEleMap.put("hivDetTestQualVL", hivDetTestQualVL);
		crfDiffEleMap.put("drugResTestGeno", drugResTestGeno);
		
		return crfDiffEleMap;
	}	

	public List<JSONObject> getReabstractionDiffData(int assignmentId) {
		
		return assignmentDAO.getReabstractionDiffData(assignmentId);
		
	}

	@Override
	public Map<Integer, String> getReabstractionWorkerNamesByRegion(String regionCode,
			BindingResult result, UserObject userObject, String sessionID) {

		boolean errorFlag = false;
		Map<Integer, String> workers = new LinkedHashMap<Integer, String>();

		try {
			List<Map<String, String>> workerNamesList = assignmentDAO
					.getReabstractionWorkerNamesByRegion(regionCode);
			for (Map<String, String> statusMap : workerNamesList) {
				workers.put(Integer.parseInt(statusMap.get("workerId")),
						statusMap.get("fullName"));
			}
		} catch (DataAccessException e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} catch (Exception e) {
			errorFlag = true;
			log.error(nyehmsLog + "  " + e.getMessage());
			commonExceptionHandler.handleException(e, userObject, sessionID);
		} finally {
			if (errorFlag)
				result.addError(new ObjectError("SQLError",
						"Transaction Failed. Please try again "));
		}
		return workers;
	}

	@Override
	public void deleteCRFNReopenAssign(AssignmentsAssignReassignVO assignReassignVO, int documentId, String documentType, String stateno, 
			BindingResult bindingResult, UserObject userObject, HttpServletRequest request) {
	
		boolean isError = false; 
		String transactionMessage = null;
		try{
		//	transactionMessage = "SUCCESS";
			assignmentTransactionHelper.deleteCRFNReopenAssign(assignReassignVO, documentId, documentType, stateno, bindingResult, userObject, request);
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			transactionMessage = "Failed to delete the document or reopen the Assignment.";
			log.error(assignReassignVO.getAssignmentIds()+ "    "+transactionMessage);
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			transactionMessage = "Failed to delete the document or reopen the Assignment.";
			log.error(assignReassignVO.getAssignmentIds()+ "       "+transactionMessage);
			log.error(exception.getMessage());
		}
		
		if(isError || bindingResult.hasErrors()){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage+", Please contact helpline.", bindingResult);
		}else{
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the document and reopened the Assignment", bindingResult);
		}
	}

	@Override
	public List<JSONObject> getOpenCaseloadSummaryData(UserObject userObject, BindingResult dummyBindingResult) {
		
		return assignmentDAO.getOpenCaseloadSummaryDataReport();
	}

	@Override
	public Map<String, String> getAssignmentStatusByACRF(int acrfDocId, UserObject userObject,
			BindingResult bindingResult){
		return assignmentDAO.getAssignmentStatusByACRF(acrfDocId);
	}
	
}
