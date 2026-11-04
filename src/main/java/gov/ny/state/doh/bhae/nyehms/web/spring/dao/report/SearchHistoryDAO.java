package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface SearchHistoryDAO {
	
	public List<JSONObject> getSearchedWorkerNames();
	
	public List<JSONObject> getSearchHistoryReportData(Map<Object,Object> map);

}
