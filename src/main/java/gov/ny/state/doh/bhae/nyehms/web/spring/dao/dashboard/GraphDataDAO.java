package gov.ny.state.doh.bhae.nyehms.web.spring.dao.dashboard;

import java.util.List;
import java.util.Map;



public interface GraphDataDAO {

	List<Map<String, Object>> getWorkerTallyGraphData(
			Map<String, Integer> inputParameterMap);

    
}
