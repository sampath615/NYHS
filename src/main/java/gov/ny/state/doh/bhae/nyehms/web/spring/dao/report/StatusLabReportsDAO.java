package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabReportsCountVO;

public interface StatusLabReportsDAO {
	

	public List<LabReportsCountVO> getLabReportCount(Map<String, String> inputMap);

	public void monthlyLoincTestReport(Map<String, Object> inputParameterMap);

	public void monthlyLabReportByRegion(Map<String, Object> inputParameterMap);
	
}
 