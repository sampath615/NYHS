package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb;

import java.util.List;
import java.util.Map;



public interface AIDADataDAO {
	
	public List<Map<String, String>> loadAirsAIDAData(Map<String, String> inOutParameterMap);
	
}
