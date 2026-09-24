package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.reports;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

/**
 * This is interface used for generaing Assignment Reports. 
 * 
 * Please see AssignmentReportsDAO.xml for its implementation.
 *
 * @author Sampath K Anumolu
 */


public interface AssignmentReportsDAO {

	public List<Map<String, String>> getAssignmentStatusReportByDocumentByRegion(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> getAssignmentStatusReportByEharsStatus(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> getAssignmentDocumentsByStatusByDocumentByRegionByFileNameOrByDate(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> getEharsAssignmentDocumentsByStatusByDocumentByFileNameOrByDate(
			Map<Object, Object> inputParameterMap);
	
	public List<Map<String, String>> getAssignmentStatusReportByDocumentByRegionForHIV_LAB(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> getWaitingForWingsStatenosByDocumentByFileName(
			Map<Object, Object> inputParameterMap);

	public List<Map<String, String>> getAssignmentAHIReports(Map<String, String> inputData);

	public List<Map<String, String>> getAHIAssignmentsByRegionAndCategory(Map<String, String> inputData);
	
	public List<JSONObject> getAssignmentClosureStatusReport(Map<Object,Object> map);
	
}
