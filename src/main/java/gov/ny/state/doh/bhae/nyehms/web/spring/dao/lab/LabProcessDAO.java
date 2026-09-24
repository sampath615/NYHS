package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FilterTypeAndFilterConditionsVO;

import java.util.List;
import java.util.Map;


public interface LabProcessDAO {
	
	public List<FilterTypeAndFilterConditionsVO> getFilterConditionsByFilterTypes(Map<Object, Object> inputOutputParamters);

	public int checkDataAgainstQueryConditions(
			Map<Object, Object> inputOutputParamters);

	public List<Map<String, String>> getFilterTypeConditions();

	public List<Map<String, String>> getPfiLabNames();

	public List<String> getBypassErrorCodes(int documentId);
		
}
 