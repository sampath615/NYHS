package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface ProviderReportService {

	List<JSONObject> getProviderReportByYear(String caseType, String region, String prfSubmitted, String fromYear, String toYear, String ahi, UserObject userObject,
			BindingResult dummyBindingResult);
	

	List<JSONObject> getProviderReportsByHivDxDate(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult);
	
	List<JSONObject> getProviderReportsByHivDxDateYearly(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult);
	
	List<JSONObject> getProviderReportsByPRFReceived(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult);
	
	List<JSONObject> getProviderReportsByPRFReceivedYearly(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult);

}
