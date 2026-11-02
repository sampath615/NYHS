package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
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
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClosedAssignmentCount;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.OpenAssignmentCount;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.assignment.AssignmentReportRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

@RequiredArgsConstructor
@Service
public class AssignmentReportsService {

	private static final Logger log = LoggerFactory.getLogger(AssignmentReportsService.class);

	
	
	private final AssignmentReportsDAO assignmentReportsDAO;
	

	private final AssignmentReportRepository assignmentReportRepository;



	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentStatusReportByWorkerId(String region, BindingResult dummyBindingResult, UserObject userObject){
		
		
		//Map<Region, Map<Worker Name, Map<Doc Type, Map<assignmentCategory, count>>>>
		Map<String, Map<String, Map<String, Map<String, Integer>>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		//Map<RegionCode, Map<WorkerId, Map<Document_type, Integer>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		
		boolean isDataForALLRegions = false;
		
		Map<String, Map<String, Integer>> allRegionsMap = new TreeMap<>();
		
		if("ALL".equals(region)){
			isDataForALLRegions = true;
		}
		
		
		
		
		
		try {
			for (OpenAssignmentCount row : assignmentReportRepository.findOpenAssignmentCounts(region)) {
			
				String regionCode = row.regionCode();
				
				if(assignmentsByRegion.get(regionCode) == null){
					
					Map<String, Map<String, Map<String, Integer>>> assignmentByWorker = new TreeMap<>();
					
					Map<String, Map<String, Integer>> assignmentsByWorkerDocumentTypes = new TreeMap<>();
					
					Map<String, Integer> assignmentsByCategory = new TreeMap<>();
					
					Integer _currentOpenAssignmentsCount = row.count();
					
					Integer _current30DAYSCount = row.days30();
					
					assignmentsByCategory.put("OPEN", _currentOpenAssignmentsCount);
					
					assignmentsByCategory.put("30DAYS", _current30DAYSCount);
					
					assignmentsByWorkerDocumentTypes.put(row.documentTypeCode(), assignmentsByCategory);
					
					String workerName = row.workerName();//.replaceAll("'", "&apos;");
					
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
					
					String workerName = row.workerName();//.replaceAll("'", "&apos;");

					Map<String, Map<String, Integer>> assignmentByWorker = assignmentsMap.get(workerName);
					
					if(assignmentByWorker == null){
						assignmentByWorker = new TreeMap<String, Map<String, Integer>>();
					}
					
					String documentType= row.documentTypeCode();
					
					
				
					Integer _currentOpenAssignmentsCount = row.count();
					
					Integer _current30DAYSCount = row.days30();
					
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
		
		} catch (DataAccessException dataAccessException) {
			log.error("Exception while loading the assignment report {}", dataAccessException.getMessage());
		}
		return assignmentsByRegion;
	}
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentReportMatrix(String fromDate,String  toDate,String  region,
			BindingResult dummyBindingResult, UserObject userObject){
		
		
		
		//Map<Region, Map<worker, Map<Month, count>>>
		Map<String, Map<String, Map<String, Map<String, Integer>>>> assignmentsByRegion = new HashMap<String, Map<String, Map<String, Map<String, Integer>>>>();
		
		boolean isDataForALLRegions = false;
		//Map<All Region, Map<worker, Map<Month, count>>>		
		Map<String, Map<String, Map<String, Integer>>> allRegionsMap = new TreeMap<>();
		
		if("ALL".equals(region)){
			isDataForALLRegions = true;
		}
		
		
		try {
			for (ClosedAssignmentCount row : assignmentReportRepository.findClosedAssignmentCounts(fromDate, toDate, region)) {
			
				String regionCode = row.regionCode();
				
				String workerId = row.workerName();
				
				if(CommonUtil.isNullOrEmpty(workerId)){
					continue;
				}
				
				String currentMonth = row.month();
				
				if(assignmentsByRegion.get(regionCode) == null){
					
					Map<String, Map<String, Map<String, Integer>>> assignmentsByWorker = new TreeMap<>();
					
					
					
					Map<String, Map<String, Integer>> assignmentsByMonth = new HashMap<>();
					
					Map<String, Integer> assignmentsByClosedStatus = new HashMap<>();
					
					
					
					Integer _closedCount = row.closedCount();
					
					Integer CLS_ADM_CNT = row.closedAdminCount();
					
					Integer CLOSED_UNASSIGNED_CNT = row.closedUnassignedCount();
					
					
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
					
					
					Integer _closedCount = row.closedCount();
					
					Integer CLS_ADM_CNT = row.closedAdminCount();
					
					Integer CLOSED_UNASSIGNED_CNT = row.closedUnassignedCount();
					
					
					
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
		
		} catch (DataAccessException dataAccessException) {
			log.error("Exception while loading the assignment report {}", dataAccessException.getMessage());
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
