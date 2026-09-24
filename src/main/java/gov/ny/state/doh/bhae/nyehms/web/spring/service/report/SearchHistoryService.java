package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface SearchHistoryService {
	
	public Map<String, String> getSearchedWorkerNames();
	public List<JSONObject> getSearchHistoryReportData(String fromDate,String  toDate, int worker, BindingResult dummyBindingResult, UserObject userObject);

}
