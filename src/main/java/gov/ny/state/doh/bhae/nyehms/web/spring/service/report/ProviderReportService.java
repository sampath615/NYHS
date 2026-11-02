package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.ProviderReportDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

@RequiredArgsConstructor
@Service
public class ProviderReportService {

	private final ProviderReportDAO providerReportDAO;
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> getProviderReportByYear(String caseType, String region, String prfSubmitted, String fromYear, String toYear, String ahi, UserObject userObject,
			BindingResult dummyBindingResult) {
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("p_case_type", caseType);
		inputParameterMap.put("p_region_cd", region);
		inputParameterMap.put("p_prf_status", prfSubmitted);
		inputParameterMap.put("p_from_date", fromYear);
		inputParameterMap.put("p_to_date", toYear);
		inputParameterMap.put("p_ahi", ahi);
		
		providerReportDAO.getProviderReportByYear(inputParameterMap);
		
		List<JSONObject> providerRptByYearData = (List<JSONObject>) inputParameterMap.get("p_prov_report");
		
		return providerRptByYearData;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getProviderReportsByHivDxDate(String fromYear, String toYear,
			UserObject userObject, BindingResult dummyBindingResult) {
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("p_from_year", fromYear);
		inputParameterMap.put("p_to_year", toYear);	
		
		providerReportDAO.getProviderReportByHivDxDate1(inputParameterMap);
		
		List<JSONObject> providerRptByHivDxDate = (List<JSONObject>) inputParameterMap.get("p_prov_report_hivdxdt");
		
		return providerRptByHivDxDate;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> getProviderReportsByHivDxDateYearly(String fromYear, String toYear,
			UserObject userObject, BindingResult dummyBindingResult) {
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("p_from_year", fromYear);
		inputParameterMap.put("p_to_year", toYear);	
		
		providerReportDAO.getProviderReportByHivDxDateYearly(inputParameterMap);
		
		List<JSONObject> providerRptByHivDxDateYearly = (List<JSONObject>) inputParameterMap.get("p_prov_report_hivdxdt");
		
		return providerRptByHivDxDateYearly;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getProviderReportsByPRFReceived(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult) {
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("p_from_year", fromYear);
		inputParameterMap.put("p_to_year", toYear);
		
		providerReportDAO.getProviderReportsByPRFReceived(inputParameterMap);
		
		List<JSONObject> providerRptByPrfSrc = (List<JSONObject>) inputParameterMap.get("p_prov_report_prf");
		
		return providerRptByPrfSrc;		
	}

	public List<JSONObject> getProviderReportsByPRFReceivedYearly(String fromYear, String toYear, UserObject userObject,
			BindingResult dummyBindingResult) {
		Map<Object, Object> inputParameterMap = new HashMap<>();
		
		inputParameterMap.put("p_from_year", fromYear);
		inputParameterMap.put("p_to_year", toYear);
		
		providerReportDAO.getProviderReportsByPRFReceivedYearly(inputParameterMap);
		
		@SuppressWarnings("unchecked")
		List<JSONObject> providerRptByPrfSrcYearly = (List<JSONObject>) inputParameterMap.get("p_prov_report_prf");
		
		return providerRptByPrfSrcYearly;		
	}

}
