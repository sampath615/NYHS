package gov.ny.state.doh.bhae.nyehms.web.spring.controller.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports.ProvPortalSubmissionReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.ProviderPortalReportService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;

@Controller
public class ProviderPortalReportController {
	
	 @Autowired ProviderPortalReportService providerPortalReportService;
	
	@RequestMapping("/provPortalSNPSubmission")
	public String provPortalSNPSubmission(Map<String, Object> map, HttpServletRequest request) {
		
		List<JSONObject> snpSubmittedProvs = providerPortalReportService.getProvPortalSubmittedProviders("P");
		
		map.put("snpSubmittedProvs", snpSubmittedProvs);
		
		return "reports/providerportal/provPortalSNPSubmission";
	}
	
	@RequestMapping(value="/getProvPortalSNPReportData.json", method = RequestMethod.GET,  produces = "application/json")
	public @ResponseBody List<ProvPortalSubmissionReportVO> getProvPortalSNPReportData(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String provId, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		List<ProvPortalSubmissionReportVO> snpReportList  = providerPortalReportService.getProvPortalSNPReportData(fromDate, toDate, provId, dummyBindingResult, userObject);
		
		return snpReportList;
	}
	
	@RequestMapping("/provPortalOocDnltcSubmission")
	public String provPortalOocDnltcSubmission(Map<String, Object> map, HttpServletRequest request) {
		
		return "reports/providerportal/provPortalOOCDNLTCSubmission";
	}	
	
	@RequestMapping(value = "/getPortalOOCDNLTCSubmissionProv.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getPortalOOCDNLTCSubmissionProv(@RequestParam String fileType, HttpServletRequest request) {

		List<JSONObject> oocDNLTCSubmittedProvs = providerPortalReportService.getProvPortalSubmittedProviders(fileType);
		
		return oocDNLTCSubmittedProvs;
	}		
	
	@RequestMapping(value="/getProvPortalOOCDNLTCReportData.json", method = RequestMethod.GET)
	public @ResponseBody ProvPortalReportVO getProvPortalOOCDNLTCReportData(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String provId, 
																			@RequestParam String portalFileType, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		ProvPortalReportVO reportObj  = providerPortalReportService.getProvPortalOOCDNLTCReportData(request, fromDate, toDate, provId, portalFileType, dummyBindingResult, userObject);
		
		return reportObj;
	}	

	@RequestMapping(value = "/getSelectedStatenoList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, String>> getSelectedStatenoList(HttpServletRequest request, @RequestParam String fileType, @RequestParam String filename, 
				@RequestParam String provId, @RequestParam String uploadDate, @RequestParam int colPosition, @RequestParam String gridName) {

		List<Map<String, String>> statenoList = providerPortalReportService.getSelectedStatenoList(request, fileType, filename, provId, uploadDate, colPosition, gridName);
		
		return statenoList;
	}		
}
