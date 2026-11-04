package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import java.util.List;
import java.util.Map;

public interface PerinatalReviewDAO {

	List<Map<String, String>> getFacilityMonitoringReportData(Map<String, String> inputParameterMap);
	
	Map<String, String> getFacilityMonitoringReportDataRange(Map<String, String> inputParameterMap);
	
	List<Map<String, String>> getFacilityNamesByBirthCriteria(Map<String, String> inputParameterMap);

	Map<String, String> getPrenatalData(Map<String, String> inputParameterMap);

	Map<String, String> getFacilityDesignees(String pfi);

	Map<String, String> getFacilityAddressDetails(String pfi);

	List<Map<String, String>> getFacilityDemographicsData(Map<String, String> inputParameterMap);

}
