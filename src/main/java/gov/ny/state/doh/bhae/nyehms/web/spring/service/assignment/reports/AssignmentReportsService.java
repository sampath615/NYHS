package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.reports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * This is implementation of the AssignmentService interface. This will provide
 * method for loading Assignment and Assignment Activites
 *
 * @author ska03
 * 
 */
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.reports.AssignmentReportsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

@RequiredArgsConstructor
@Service
public class AssignmentReportsService {

	private static final Logger log = LoggerFactory.getLogger(AssignmentReportsService.class);

	
	
	private final AssignmentReportsDAO assignmentReportsDAO;
	

	private final ConnectionDAO connectionDAO;



	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentStatusReportByWorkerId(String region, BindingResult dummyBindingResult, UserObject userObject){
		
		Connection connection = connectionDAO.getConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;
		//Map<Region, Map<Worker Name, Map<Doc Type, Map<assignmentCategory, count>>>>
		Map<String, Map<String, Map<String, Map<String, Integer>>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		//Map<RegionCode, Map<WorkerId, Map<Document_type, Integer>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		
		boolean isDataForALLRegions = false;
		
		Map<String, Map<String, Integer>> allRegionsMap = new TreeMap<>();
		
		if("ALL".equals(region)){
			isDataForALLRegions = true;
		}
		
		
		
		
		
		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("Oracle server database Connection is available {}",connection);
				}
				statement = connection.createStatement();
				
				if(log.isDebugEnabled()){
					log.debug("Statement object for Oracle server database {}",statement);
				}
		
				StringBuilder sqlQuery = new StringBuilder(" SELECT /*+ INDEX (W,WORKER_ASSIGNMENT_SH_PK) */ CASE ")
				.append(" WHEN N.LEGACY_UID IS NULL")
	            .append(" THEN 'Open Not Assigned'")
	            .append(" ELSE pkg_trk_common.fn_get_fullname (N.last_name, N.first_name, N.middle_name, NULL)")
		         .append(" END WORKER_NAME,")
		         .append(" U.worker_id,")
		         .append(" CURRENT_REGION_CD,")
		         .append(" D.DOCUMENT_TYPE_CODE,")
		         .append(" COUNT (*) COUNT,")
		         .append(" SUM(CASE WHEN CREATE_DATE < TO_DATE('01/01/2025', 'MM/DD/YYYY')  and CREATE_DATE > SYSDATE - 60 THEN 0 WHEN CREATE_DATE < SYSDATE-30 THEN 1 ELSE 0 END ) \"30DAYS\" ")
		         .append("  FROM SURVEILLANCE_ASSIGNMENT S, WORKER_ASSIGNMENT W, USER_REGION U, NYEHMS_USERS N, document_locator D")
	    		.append(" WHERE     S.TRIGGERING_DOC_ID = D.DOCUMENT_ID AND S.CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID(+) AND CURRENT_ASSIGNMENT_STATUS IN ('OPEN', 'REOPEN', 'OPEN_ND', 'REASSGN') AND W.WORKER_REGION_ID = U.WORKER_REGION_ID(+) AND U.WORKER_ID = N.WORKER_ID(+)");
	    		
	    		
				
				if(!isDataForALLRegions){
					sqlQuery.append("and CURRENT_REGION_CD = ").append(region);
				}
				
				sqlQuery.append(" and NOT EXISTS (SELECT 1 FROM coded_master_setup m WHERE     m.variable_name = 'DOC_TYPE_EXCLUDE_VIEW_R' AND m.variable_code = D.document_type_code) ");
	    		sqlQuery.append(" GROUP BY CASE WHEN N.LEGACY_UID IS NULL")
	            .append(" THEN 'Open Not Assigned'")
	            .append(" ELSE pkg_trk_common.fn_get_fullname (N.last_name, N.first_name, N.middle_name, NULL) END,")
		         .append(" U.worker_id,")
		         .append(" CURRENT_REGION_CD,")
		         .append(" D.DOCUMENT_TYPE_CODE");

				
				
				
				
				if(log.isDebugEnabled()){
					log.debug("Loading SQL Query {}",sqlQuery);
				}
				
				resultSet = statement.executeQuery(sqlQuery.toString());
				
				while (resultSet.next()) {
				
					String regionCode = resultSet.getString("CURRENT_REGION_CD");
					
					if(assignmentsByRegion.get(regionCode) == null){
						
						Map<String, Map<String, Map<String, Integer>>> assignmentByWorker = new TreeMap<>();
						
						Map<String, Map<String, Integer>> assignmentsByWorkerDocumentTypes = new TreeMap<>();
						
						Map<String, Integer> assignmentsByCategory = new TreeMap<>();
						
						Integer _currentOpenAssignmentsCount = resultSet.getInt("COUNT");
						
						Integer _current30DAYSCount = resultSet.getInt("30DAYS");
						
						assignmentsByCategory.put("OPEN", _currentOpenAssignmentsCount);
						
						assignmentsByCategory.put("30DAYS", _current30DAYSCount);
						
						assignmentsByWorkerDocumentTypes.put(resultSet.getString("DOCUMENT_TYPE_CODE"), assignmentsByCategory);
						
						String workerName = resultSet.getString("WORKER_NAME");//.replaceAll("'", "&apos;");
						
						assignmentByWorker.put(workerName, assignmentsByWorkerDocumentTypes);
						
						
						
						if(isDataForALLRegions){
							Map<String, Integer> allAssignmentsByCategory = allRegionsMap.get(workerName);
							
							if(allAssignmentsByCategory == null){
								allAssignmentsByCategory = new HashMap<String, Integer>();
							}
							
							Integer _allOpenAssignments = allAssignmentsByCategory.get("COUNT");
							
							Integer _allOpen30DAYSAssignments = allAssignmentsByCategory.get("30DAYS");
							
							if(_allOpenAssignments == null){
								_allOpenAssignments = 0;
							}
							
							if(_allOpen30DAYSAssignments == null){
								_allOpen30DAYSAssignments = 0;
							}
							allAssignmentsByCategory.put("COUNT", _allOpenAssignments+_currentOpenAssignmentsCount);
							allAssignmentsByCategory.put("30DAYS", _allOpen30DAYSAssignments+_current30DAYSCount);
							
							allRegionsMap.put(workerName, allAssignmentsByCategory);
						}
						
						
						
						
						assignmentsByRegion.put(regionCode, assignmentByWorker);
						
					}else{
						
						Map<String, Map<String, Map<String, Integer>>> assignmentsMap = assignmentsByRegion.get(regionCode);
						
						String workerName = resultSet.getString("WORKER_NAME");//.replaceAll("'", "&apos;");

						Map<String, Map<String, Integer>> assignmentByWorker = assignmentsMap.get(workerName);
						
						if(assignmentByWorker == null){
							assignmentByWorker = new TreeMap<String, Map<String, Integer>>();
						}
						
						String documentType= resultSet.getString("DOCUMENT_TYPE_CODE");
						
						
					
						Integer _currentOpenAssignmentsCount = resultSet.getInt("COUNT");
						
						Integer _current30DAYSCount = resultSet.getInt("30DAYS");
						
						Map<String, Integer> assignmentsByCategory = new TreeMap<>();
						
						assignmentsByCategory.put("OPEN", _currentOpenAssignmentsCount);
						
						assignmentsByCategory.put("30DAYS", _current30DAYSCount);
						
						
						
						assignmentByWorker.put(documentType, assignmentsByCategory);//_currentRecordsCount+_currentRecordsCount);
						
						
						assignmentsMap.put(workerName, assignmentByWorker);
						
						assignmentsByRegion.put(regionCode, assignmentsMap);
						
						if(isDataForALLRegions){
							Map<String, Integer> allAssignmentsByCategory = allRegionsMap.get(workerName);
							

							if(allAssignmentsByCategory == null){
								allAssignmentsByCategory = new HashMap<String, Integer>();
							}
							
							Integer _allOpenAssignments = allAssignmentsByCategory.get("COUNT");
							
							Integer _allOpen30DAYSAssignments = allAssignmentsByCategory.get("30DAYS");
							
							if(_allOpenAssignments == null){
								_allOpenAssignments = 0;
							}
							
							if(_allOpen30DAYSAssignments == null){
								_allOpen30DAYSAssignments = 0;
							}
							allAssignmentsByCategory.put("COUNT", _allOpenAssignments+_currentOpenAssignmentsCount);
							allAssignmentsByCategory.put("30DAYS", _allOpen30DAYSAssignments+_current30DAYSCount);
							
							allRegionsMap.put(workerName, allAssignmentsByCategory);

						}

					}
					
					
				}
				if(isDataForALLRegions){
					Map<String, Map<String, Map<String,Integer>>> _dummyAllRegionsMap = new HashMap<>();
					_dummyAllRegionsMap.put("ALL", allRegionsMap);
					assignmentsByRegion.put("ALL", _dummyAllRegionsMap);
				}
				if(log.isDebugEnabled()){
					log.debug("Results retuned in the Query {}",assignmentsByRegion);
				}
			
			}else{
				log.error("Connection is null while retrieving the data from the Ehars");
			}
		} catch (SQLException sqlException) {
	
			log.error("Exception while retrieving the Ehars data from SQL Server {}",sqlException.getMessage());
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
				log.error("Exception while closing the connection", sqlException.getMessage());
			}
		}

		return assignmentsByRegion;
	}
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentReportMatrix(String fromDate,String  toDate,String  region,
			BindingResult dummyBindingResult, UserObject userObject){
		
		Connection connection = connectionDAO.getConnectionObj();
		
		Statement statement = null;
		ResultSet resultSet = null;
		
		//Map<Region, Map<worker, Map<Month, count>>>
		Map<String, Map<String, Map<String, Map<String, Integer>>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		
		boolean isDataForALLRegions = false;
		//Map<All Region, Map<worker, Map<Month, count>>>		
		Map<String, Map<String, Map<String, Integer>>> allRegionsMap = new TreeMap<>();
		
		if("ALL".equals(region)){
			isDataForALLRegions = true;
		}
		
		
		try {
			if (connection != null) {
				
				if(log.isDebugEnabled()){
					log.debug("Oracle server database Connection is available {}", connection);
				}
				statement = connection.createStatement();
				
				if(log.isDebugEnabled()){
					log.debug("Statement object for Oracle server database {}",statement);
				}
				
				StringBuilder sqlQuery = new StringBuilder("SELECT ACTUAL_REGION_CD , pkg_trk_common.fn_get_fullname(LAST_NAME,FIRST_NAME,MIDDLE_NAME,NULL   ) WORKER_NAME, CLOSED_CNT, CLS_ADM_CNT, CLOSED_UNASSIGNED_CNT, MONTH")
						.append(" FROM ( SELECT ACTUAL_REGION_CD,ACTIVITY_DONE_BY, TO_CHAR (activity_date, 'MON') ||  TO_CHAR (activity_date, 'YYYY') MONTH,   ")
						.append(" sum(case when ACTUAL_STATUS = 'CLOSED' then 1 else 0 end) CLOSED_CNT , ")
						.append(" sum(case when ACTUAL_STATUS = 'CLS_ADM' then 1 else 0 end) CLS_ADM_CNT, ")
						.append(" sum(case when ACTUAL_STATUS = 'CLOSED_UNASSIGNED' then 1 else 0 end) CLOSED_UNASSIGNED_CNT ")
						.append(" FROM assign_report_mview WHERE ACTIVITY_DATE BETWEEN TO_DATE ('").append(fromDate).append("', 'MM/DD/YYYY') AND TO_DATE ('").append(toDate).append("', 'MM/DD/YYYY')+1 AND ACTUAL_STATUS    in ('CLOSED', 'CLS_ADM', 'CLOSED_UNASSIGNED') ");
						if(!isDataForALLRegions){
							sqlQuery.append(" and ACTUAL_REGION_CD='"+region+"'");
						}
						sqlQuery.append(" GROUP BY   ACTUAL_REGION_CD,ACTIVITY_DONE_BY, TO_CHAR (activity_date, 'MON') ||  TO_CHAR (activity_date, 'YYYY'))M, NYEHMS_USERS U WHERE M.ACTIVITY_DONE_BY = U.worker_id");
									
				if(log.isDebugEnabled()){
					log.debug("Loading Ehars data {}",sqlQuery);
				}
				
								
				resultSet = statement.executeQuery(sqlQuery.toString());
				
				while (resultSet.next()) {
				
					String regionCode = resultSet.getString("ACTUAL_REGION_CD");
					
					String workerId = resultSet.getString("WORKER_NAME");
					
					if(CommonUtil.isNullOrEmpty(workerId)){
						continue;
					}
					
					String currentMonth = resultSet.getString("MONTH");
					
					if(assignmentsByRegion.get(regionCode) == null){
						
						Map<String, Map<String, Map<String, Integer>>> assignmentsByWorker = new TreeMap<>();
						
						
						
						Map<String, Map<String, Integer>> assignmentsByMonth = new HashMap<>();
						
						Map<String, Integer> assignmentsByClosedStatus = new HashMap<>();
						
						
						
						Integer _closedCount = resultSet.getInt("CLOSED_CNT");
						
						Integer CLS_ADM_CNT = resultSet.getInt("CLS_ADM_CNT");
						
						Integer CLOSED_UNASSIGNED_CNT = resultSet.getInt("CLOSED_UNASSIGNED_CNT");
						
						
						assignmentsByClosedStatus.put("CLOSED_CNT", _closedCount+CLS_ADM_CNT+CLOSED_UNASSIGNED_CNT);
						
						assignmentsByClosedStatus.put("CLS_ADM_CNT", CLS_ADM_CNT);
						
						assignmentsByClosedStatus.put("CLOSED_UNASSIGNED_CNT", CLOSED_UNASSIGNED_CNT);
						
						assignmentsByMonth.put(currentMonth, assignmentsByClosedStatus);
						
						assignmentsByWorker.put(workerId, assignmentsByMonth);
						
						assignmentsByRegion.put(regionCode, assignmentsByWorker);
						
						
						if(isDataForALLRegions){
							
							Map<String, Map<String, Integer>> allAssignmentsByMonth = allRegionsMap.get(workerId);
							
							if(allAssignmentsByMonth == null){
								allAssignmentsByMonth = new HashMap<String, Map<String, Integer>>();
							}
							
							
							Map<String, Integer> allAssignmentsByColsedStatus = allAssignmentsByMonth.get(currentMonth);
							
							if(allAssignmentsByColsedStatus == null){
								
								allAssignmentsByColsedStatus = new HashMap<String, Integer>();
										
							}
							
							Integer allAssignmentsColsedCount = allAssignmentsByColsedStatus.get("CLOSED_CNT");
							
							Integer allAssignmentsClsAdmCount = allAssignmentsByColsedStatus.get("CLS_ADM_CNT");
							

							Integer allAssignmentsClsUnAssignedCount = allAssignmentsByColsedStatus.get("CLOSED_UNASSIGNED_CNT");
							
							if(allAssignmentsColsedCount == null){
								allAssignmentsColsedCount = 0;
							}
							
							if(allAssignmentsClsAdmCount == null){
								allAssignmentsClsAdmCount = 0;
							}
							
							if(allAssignmentsClsUnAssignedCount == null){
								allAssignmentsClsUnAssignedCount = 0;
							}
							
							allAssignmentsByColsedStatus.put("CLOSED_CNT", allAssignmentsColsedCount+_closedCount+CLS_ADM_CNT+CLOSED_UNASSIGNED_CNT);
							
							allAssignmentsByColsedStatus.put("CLS_ADM_CNT", allAssignmentsClsAdmCount+CLS_ADM_CNT);
							
							allAssignmentsByColsedStatus.put("CLOSED_UNASSIGNED_CNT", allAssignmentsClsUnAssignedCount+CLOSED_UNASSIGNED_CNT);
							
							allAssignmentsByMonth.put(currentMonth, allAssignmentsByColsedStatus);
							
							
							allRegionsMap.put(workerId, allAssignmentsByMonth);
						}
						
						
						
					}else{
						
						Map<String, Map<String, Map<String, Integer>>> assignmentsByWorker = assignmentsByRegion.get(regionCode);
						
						Map<String, Map<String, Integer>> assignmentsByMonth = assignmentsByWorker.get(workerId);
						
						if(assignmentsByMonth == null){
							assignmentsByMonth = new HashMap<String, Map<String, Integer>>();
						}
						
						
						Map<String, Integer> assignmentsByClosedStatus =  assignmentsByMonth.get(workerId); 
						
						if(assignmentsByClosedStatus == null){
							
							assignmentsByClosedStatus = new HashMap<String, Integer>();
									
						}
						
						Integer totalClosedCount = assignmentsByClosedStatus.get("CLOSED_CNT");
						
						Integer totalClosedAdmCount = assignmentsByClosedStatus.get("CLS_ADM_CNT");
						
						Integer totalClosedUnAssignedCount = assignmentsByClosedStatus.get("CLOSED_UNASSIGNED_CNT");
						
						if(totalClosedCount == null){
							totalClosedCount = 0;
						}
						
						if(totalClosedAdmCount == null){
							totalClosedAdmCount = 0;
						}
						
						if(totalClosedUnAssignedCount == null){
							totalClosedUnAssignedCount = 0;
						}
						
						
						Integer _closedCount = resultSet.getInt("CLOSED_CNT");
						
						Integer CLS_ADM_CNT = resultSet.getInt("CLS_ADM_CNT");
						
						Integer CLOSED_UNASSIGNED_CNT = resultSet.getInt("CLOSED_UNASSIGNED_CNT");
						
						
						
						assignmentsByClosedStatus.put("CLOSED_CNT", totalClosedCount+_closedCount+CLS_ADM_CNT+CLOSED_UNASSIGNED_CNT);
						assignmentsByClosedStatus.put("CLS_ADM_CNT", totalClosedAdmCount+CLS_ADM_CNT);
						
						assignmentsByClosedStatus.put("CLOSED_UNASSIGNED_CNT", totalClosedUnAssignedCount+CLOSED_UNASSIGNED_CNT);
						
						
						assignmentsByMonth.put(currentMonth, assignmentsByClosedStatus);
						
						assignmentsByWorker.put(workerId, assignmentsByMonth);
						
						assignmentsByRegion.put(regionCode, assignmentsByWorker);
						
						
						if(isDataForALLRegions){
							
							Map<String, Map<String, Integer>> allAssignmentsByWorker = allRegionsMap.get(workerId);
							
							if(allAssignmentsByWorker == null){
								allAssignmentsByWorker = new HashMap<String, Map<String, Integer>>();
							}
							
							
							
							Map<String, Integer> allAssignmentsByColsedStatus = allAssignmentsByWorker.get(currentMonth);
							
							if(allAssignmentsByColsedStatus == null){
								
								allAssignmentsByColsedStatus = new HashMap<String, Integer>();
										
							}
							
							Integer allAssignmentsColsedCount = allAssignmentsByColsedStatus.get("CLOSED_CNT");
							
							Integer allAssignmentsClsAdmCount = allAssignmentsByColsedStatus.get("CLS_ADM_CNT");
							
							Integer allAssignmentsClsUnAssignedCount = allAssignmentsByColsedStatus.get("CLOSED_UNASSIGNED_CNT");
							
							if(allAssignmentsColsedCount == null){
								allAssignmentsColsedCount = 0;
							}
							
							if(allAssignmentsClsAdmCount == null){
								allAssignmentsClsAdmCount = 0;
							}
							
							if(allAssignmentsClsUnAssignedCount == null){
								allAssignmentsClsUnAssignedCount = 0;
							}
							
							allAssignmentsByColsedStatus.put("CLOSED_CNT", allAssignmentsColsedCount+_closedCount+CLS_ADM_CNT+CLOSED_UNASSIGNED_CNT);
							
							allAssignmentsByColsedStatus.put("CLS_ADM_CNT", allAssignmentsClsAdmCount+CLS_ADM_CNT);
							
							allAssignmentsByColsedStatus.put("CLOSED_UNASSIGNED_CNT", allAssignmentsClsUnAssignedCount+CLOSED_UNASSIGNED_CNT);
							
							allAssignmentsByWorker.put(currentMonth, allAssignmentsByColsedStatus);
							
							
							
							allRegionsMap.put(workerId, allAssignmentsByWorker);
							
						}
						
						
					}
					
					
				}
				
				if(isDataForALLRegions){
					
					assignmentsByRegion.put("ALL", allRegionsMap);
				}
				if(log.isDebugEnabled()){
					log.debug("Results retuned in the Query  {}",assignmentsByRegion);
				}
			
			}else{
				log.error("Connection is null while retrieving the data from the Ehars");
			}
		} catch (SQLException sqlException) {
	
			log.error("Exception while retrieving the Ehars data from SQL Server", sqlException.getMessage());
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

		return assignmentsByRegion;
	}
	
	public List<Map<String, String>> getAssignmentStatusReportByDocument(
			String startDate, String toDate, String fileName,
			String documentType, BindingResult dummyBindingResult,
			UserObject userObject){
		
		
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		
		String testType = null;
		if("RESISTANCE".equals(documentType)){
			testType = "RESISTANCE";
			documentType = "HIV_LAB";
		}
		inputParameterMap.put("testType", testType);
		inputParameterMap.put("documentType", documentType);
		inputParameterMap.put("stdTableName", LabCommonUtils.getStdTableName(documentType));
		
		Map<String, String> stdTableColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
		
		List<Map<String, String>> assignmentReportsMatrix = null;
		
		if(CommonUtil.isNotNullAndEmpty(fileName)){
			
			inputParameterMap.put("fileName", fileName);
			inputParameterMap.put("FileNameReferenceColumn", stdTableColumnNames.get("FILE_NAME"));
		}else{
			
			inputParameterMap.put("fromDate", startDate);
			
			inputParameterMap.put("toDate", toDate);
			
		}
		
		if("HIV_LAB".equals(documentType)){
			assignmentReportsMatrix = assignmentReportsDAO.getAssignmentStatusReportByDocumentByRegionForHIV_LAB(inputParameterMap);
		}else{
			assignmentReportsMatrix = assignmentReportsDAO.getAssignmentStatusReportByDocumentByRegion(inputParameterMap);
		}
			
		return assignmentReportsMatrix;
	}
	
	public List<Map<String, String>> getAssignmentStatusReportByEharsStatus(
			String fromDate, String toDate, String fileName,
			String documentType, BindingResult dummyBindingResult,
			UserObject userObject){
		
		
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		String testType = null;
		if("RESISTANCE".equals(documentType)){
			testType = "RESISTANCE";
			documentType = "HIV_LAB";
		}
		inputParameterMap.put("testType", testType);
		inputParameterMap.put("documentType", documentType);
		inputParameterMap.put("stdTableName", LabCommonUtils.getStdTableName(documentType));
		
		Map<String, String> stdTableColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
		
		List<Map<String, String>> assignmentReportsMatrix = null;
		
		if(CommonUtil.isNotNullAndEmpty(fileName)){
			
			inputParameterMap.put("fileName", fileName);
			inputParameterMap.put("FileNameReferenceColumn", stdTableColumnNames.get("FILE_NAME"));
		}else{
			inputParameterMap.put("fromDate", fromDate);
			
			inputParameterMap.put("toDate", toDate);
			
		}
		
		if("HIV_LAB".equals(documentType)){
			assignmentReportsMatrix = assignmentReportsDAO.getAssignmentStatusReportByEharsStatus(inputParameterMap);
		}else{
			assignmentReportsMatrix = assignmentReportsDAO.getAssignmentStatusReportByEharsStatus(inputParameterMap);
		}
		
			
		return assignmentReportsMatrix;
	}
	
	public List<Map<String, String>> getAssignmentDocumentsByStatusByDocumentByRegion(
			String regionId, String assignmentStatus, String fromDate,
			String toDate, String fileName, String documentType, String eharsStatus, String category,
			BindingResult dummyBindingResult, UserObject userObject){
		
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		String testType = null;
		if("RESISTANCE".equals(documentType)){
			testType = "RESISTANCE";
			documentType = "HIV_LAB";
		}
		inputParameterMap.put("testType", testType);
		
		inputParameterMap.put("documentType", documentType);
		inputParameterMap.put("stdTableName", LabCommonUtils.getStdTableName(documentType));
		
		inputParameterMap.put("regionId", regionId);
		inputParameterMap.put("eharsStatus", eharsStatus);
		System.out.println(eharsStatus);
//		}else 
		if(assignmentStatus != null && assignmentStatus.startsWith("ACL")){
			inputParameterMap.put("assignmentStatus", "CLOSED");
			inputParameterMap.put("activityTypeCode", assignmentStatus);
		}else if(assignmentStatus != null){
			inputParameterMap.put("assignmentStatus", assignmentStatus);
			inputParameterMap.put("activityTypeCode", null);
		}
		
		Map<String, String> stdTableColumnNames = LabCommonUtils.getStdReferenceColumnNames(documentType);
		
		List<Map<String, String>> assignmentDocuments = null;
		
		if(CommonUtil.isNotNullAndEmpty(fileName)){
			
			inputParameterMap.put("fileName", fileName);
			inputParameterMap.put("FileNameReferenceColumn", stdTableColumnNames.get("FILE_NAME"));
			
		}else if(CommonUtil.isNotNullAndEmpty(fromDate) && CommonUtil.isNotNullAndEmpty(toDate)){
			
			inputParameterMap.put("fromDate", fromDate);
			
			inputParameterMap.put("toDate", toDate);
		}
		
		
		
		if("WAITING IN THE WINGS".equals(assignmentStatus)){
			assignmentDocuments = assignmentReportsDAO.getWaitingForWingsStatenosByDocumentByFileName(inputParameterMap);
		}
		else if("eHars".equals(category)){
				assignmentDocuments = assignmentReportsDAO.getEharsAssignmentDocumentsByStatusByDocumentByFileNameOrByDate(inputParameterMap);
		}else{
				assignmentDocuments = assignmentReportsDAO.getAssignmentDocumentsByStatusByDocumentByRegionByFileNameOrByDate(inputParameterMap);
		}
		return assignmentDocuments;
	}
	
	public List<Map<String, String>> getAHIAssignmentsByRegionAndCategory(String regionId, String assignmentStatus,
			String documentType, String ahiFlag, BindingResult dummyBindingResult, UserObject userObject){
		
		Map<String, String> inputData = new HashMap<>();
		
		inputData.put("ahiFlag", ahiFlag);
		inputData.put("regionId", regionId);
		inputData.put("assignmentStatus", assignmentStatus);
		inputData.put("documentType", documentType);
		
		return assignmentReportsDAO.getAHIAssignmentsByRegionAndCategory(inputData);
	}
	
	public List<Map<String, String>> getAssignmentAHIReports(String ahiFlag){
		
		Map<String, String> inputData = new HashMap<>();
		
		inputData.put("ahiFlag", ahiFlag);

		List<Map<String, String>> ahiData = assignmentReportsDAO.getAssignmentAHIReports(inputData);
		
		return ahiData;
		
		
	}

	public List<JSONObject> getAssignmentClosureStatusReport(String fromDate, String toDate, String regionCode, BindingResult dummyBindingResult, UserObject userObject) {

		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		inputData.put("p_region_cd", regionCode);
		inputData.put("p_module_id", "16");
		inputData.put("p_worker_id", userObject.getWorkerId());
		
		assignmentReportsDAO.getAssignmentClosureStatusReport(inputData);
		
		@SuppressWarnings("unchecked")
		List<JSONObject> assignmentClosureStatusReportList = (List<JSONObject>)inputData.get("pcursor");

		return assignmentClosureStatusReportList;
	}

}
