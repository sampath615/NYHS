package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.SearchHistoryDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

@RequiredArgsConstructor
@Service
public class SearchHistoryService {
	
	private final SearchHistoryDAO searchHistoryDAO;

	public Map<String, String> getSearchedWorkerNames() {
		
		List<JSONObject> workerNamesList = searchHistoryDAO.getSearchedWorkerNames();
		
		Map<String, String> workers = new LinkedHashMap<>();
		
		for (JSONObject jsonObj : workerNamesList) {
			workers.put(jsonObj.get("WORKER_ID").toString(), jsonObj.get("WORKER_NAME").toString());
		}
		return workers;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getSearchHistoryReportData(String fromDate, String toDate, int worker,
			BindingResult dummyBindingResult, UserObject userObject) {
		
		Map<Object, Object> inputData = new HashMap<>();
		inputData.put("p_from_date", fromDate);
		inputData.put("p_to_date", toDate);
		inputData.put("p_worker_id", worker);
		inputData.put("p_request_worker_id", userObject.getWorkerId());

		searchHistoryDAO.getSearchHistoryReportData(inputData);
		List<JSONObject> searchHistoryData = (List<JSONObject>)inputData.get("p_search_history");
		
		return searchHistoryData;
	}
	
}
