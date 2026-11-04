package gov.ny.state.doh.bhae.nyehms.web.spring.dao.report;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;

public interface ProviderPortalReportDAO {
	
	public List<JSONObject> getProvPortalSubmittedProviders(Map<String, String> inputParameter);
	
	public List<ProvPortalSubmissionReportVO> getProvPortalReportData(Map<Object,Object> map);	
	
	public List<ProvPortalSubmissionReportVO> getProvPortalOOCDNLTCReportData(Map<Object,Object> map);	
	

}
