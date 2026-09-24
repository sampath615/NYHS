package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;



/**
 * This is the interface for generating provider reports by year and by HIV/DX 
 * 
 * @author skg02
 *
 */

public interface ProviderReportDAO {

	List<JSONObject> getProviderReportByHivDxDate1(Map<Object, Object> inputParameterMap);
	
	List<JSONObject> getProviderReportByHivDxDateYearly(Map<Object, Object> inputParameterMap);
	
	List<JSONObject> getProviderReportsByPRFReceived(Map<Object, Object> inputParameterMap);
	
	List<JSONObject> getProviderReportsByPRFReceivedYearly(Map<Object, Object> inputParameterMap);

	List<JSONObject> getProviderReportByYear(Map<Object, Object> inputParameterMap);


}
