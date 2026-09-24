package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface ClericalReportDAO {

	List<JSONObject> getclericalReportByStaff(Map<String, String> inputParameterMap);

	List<JSONObject> getclericalDeathReportByStaff(Map<String, String> inputParameterMap);

	List<JSONObject> getScanningReportByStaff(Map<String, String> inputParameterMap);

	List<JSONObject> clericalNonMatchReportData(Map<Object, Object> inputData);

}
