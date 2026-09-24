package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface ProviderPortalReportService {
	
	public List<JSONObject> getProvPortalSubmittedProviders(String fileType);
	
	@Transactional
	public List<ProvPortalSubmissionReportVO> getProvPortalSNPReportData(String fromDate,String  toDate, String provId, BindingResult dummyBindingResult, UserObject userObject);
	
	@Transactional
	public ProvPortalReportVO getProvPortalOOCDNLTCReportData(HttpServletRequest request, String fromDate,String  toDate, String provId, String portalFileType, BindingResult dummyBindingResult, UserObject userObject);
	
	public List<Map<String, String>> getSelectedStatenoList(HttpServletRequest request, String fileType, String filename, String provId, String uploadDate, int colPos, String gridName);

}
