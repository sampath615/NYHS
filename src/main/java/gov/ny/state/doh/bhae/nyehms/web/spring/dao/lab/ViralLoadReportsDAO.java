package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface ViralLoadReportsDAO {
	
	public List<JSONObject> loadNdvlReportData();

	public List<JSONObject> getNoAdditionalReportData(Map<String, String> inputParameterMap);

	public List<JSONObject> getVLObxNotes(Map<Object, Object> inOutParameterMap);
}
 