package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

/**
 * This class implemented for Assignments 
 *
 * @author ska03
 */
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

public interface AssignmentReportsService {	

	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentStatusReportByWorkerId(String region, BindingResult dummyBindingResult, UserObject userObject);
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAssignmentReportMatrix(String fromDate,String  toDate,String  region, 
			BindingResult dummyBindingResult, UserObject userObject);

	public List<Map<String, String>> getAssignmentStatusReportByDocument(
			String completeFromDate, String completeToDate, String fileName,
			String documentType, BindingResult dummyBindingResult,
			UserObject userObject);

	public List<Map<String, String>> getAssignmentStatusReportByEharsStatus(
			String fromDate, String toDate, String fileName,
			String documentType, BindingResult dummyBindingResult,
			UserObject userObject);

	public List<Map<String, String>> getAssignmentDocumentsByStatusByDocumentByRegion(
			String regionId, String assignmentStatus, String fromDate,
			String toDate, String fileName, String documentType, String eharsStatus, String category,
			BindingResult dummyBindingResult, UserObject userObject);

	public List<Map<String, String>> getAssignmentAHIReports(String ahiFlag);

	public List<Map<String, String>> getAHIAssignmentsByRegionAndCategory(String regionId, String assignmentStatus,
			String documentType, String ahiFlag, BindingResult dummyBindingResult, UserObject userObject);
	
	public List<JSONObject> getAssignmentClosureStatusReport(String fromDate,String  toDate, String regionCode, BindingResult dummyBindingResult, UserObject userObject);	
	
}
