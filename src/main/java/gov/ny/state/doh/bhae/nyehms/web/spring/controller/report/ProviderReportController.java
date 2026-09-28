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

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.administration.AdministrationService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.AssignmentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.report.ProviderReportService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;

/**
 * 
 * @author skg02
 * 
 * This controller will fetch the data for provider report Tab
 *   1) by Year
 *   2) by Hiv/Diagnosis
 *   3) by PRF Received
 */

@Controller
public class ProviderReportController {
	

	@Autowired
	private ProviderReportService providerReportService;
	
	@Autowired
	private AdministrationService administrationService;	
	
	@Autowired
	private AssignmentService assignmentService;

	@RequestMapping(value = "/getproviderReportsByYear")
	public String getproviderReportsByYear(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		if (userObject == null)
			userObject = new UserObject();
		
		Map<String, String> userRoleCodes = assignmentService.getUserRoleCodes(dummyBindingResult, userObject);
		
		
		Map<String, String> regionsList = administrationService.getSrchregionTypes(dummyBindingResult, userObject);
		
		map.put("userRoleCodes", userRoleCodes);		
		map.put("regionsList", regionsList);		

		return "reports/providerreport/ProviderReportByYear";

	}

	/*
	 * @params -> date range (from/to)
	 * getting the data for provider report by year. 
	 */
	
	@RequestMapping(value = "/getProviderReportByYearData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody  List<JSONObject> getProviderReportByYearData(@RequestParam String caseType, @RequestParam String region,
			@RequestParam String prfSubmitted, @RequestParam String fromYear, @RequestParam String toYear, @RequestParam String ahi, HttpServletRequest request, UserObject userObject) {
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		return providerReportService.getProviderReportByYear(caseType, region, prfSubmitted, fromYear, toYear, ahi, userObject, dummyBindingResult);
	}

	
	@RequestMapping(value = "/getProviderReportsByHivDxDate")
	public String getProviderReportsByHivDxDate(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		if (userObject == null)
			userObject = new UserObject();

		return "reports/providerreport/ProviderReportByHivDxDate";

	}

	/* @params -> date range (from/to) getting the data for provider report by Hiv/Diagnosis Monthly. */
	
	@RequestMapping(value = "/getProviderReportsByHivDxDateData.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<JSONObject> getProviderReportsByHivDxDateData(@RequestParam String fromYear,
			@RequestParam String toYear, HttpServletRequest request, UserObject userObject) {
		BindingResult dummyBindingResult = BindingResults.newDummy();

		List<JSONObject> res = (List<JSONObject>) providerReportService.getProviderReportsByHivDxDate(fromYear, toYear, userObject, dummyBindingResult);
		
		return res;
	}

	
	@RequestMapping(value = "/getProviderReportsByHivDxDateYearly")
	public String getProviderReportsByHivDxDateYearly(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		if (userObject == null)
			userObject = new UserObject();

		return "reports/providerreport/ProviderReportByHivDxDateYearly";
	}

	/* @params -> date range (from/to) getting the data for provider report by Hiv/Diagnosis Yearly. */
	
	@RequestMapping(value = "/getProviderReportsByHivDxDateDataYearly.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<JSONObject> getProviderReportsByHivDxDateDataYearly(@RequestParam String fromYear,
			@RequestParam String toYear, HttpServletRequest request, UserObject userObject) {
		BindingResult dummyBindingResult = BindingResults.newDummy();

		List<JSONObject> res = (List<JSONObject>) providerReportService.getProviderReportsByHivDxDateYearly(fromYear, toYear, userObject, dummyBindingResult);
		
		return res;
	}

	@RequestMapping(value = "/getProviderReportsByPRFReceived")
	public String getProviderReportsByPRFReceived(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		if (userObject == null)
			userObject = new UserObject();

		return "reports/providerreport/ProviderReportPRFReceived";

	}

	/*
	 * @params -> date range (from/to)
	 * getting the data for provider report by Hiv/Diagnosis. 
	 */
	
	@RequestMapping(value = "/getProviderReportsByPRFReceived.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<JSONObject> getProviderReportsByPRFReceivedData(@RequestParam String fromYear,
			@RequestParam String toYear, HttpServletRequest request, UserObject userObject) {
		BindingResult dummyBindingResult = BindingResults.newDummy();

		List<JSONObject> res = (List<JSONObject>) providerReportService.getProviderReportsByPRFReceived(fromYear, toYear, userObject, dummyBindingResult);
		
		return res;

	}	
	
	@RequestMapping(value = "/getProviderReportsByPRFReceivedYearly")
	public String getProviderReportsByPRFReceivedYearly(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {

		if (userObject == null)
			userObject = new UserObject();

		return "reports/providerreport/ProviderReportPRFReceivedYearly";

	}

	@RequestMapping(value = "/getProviderReportsByPRFReceivedYearly.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<JSONObject> getProviderReportsByPRFReceivedYearlyData(@RequestParam String fromYear,
			@RequestParam String toYear, HttpServletRequest request, UserObject userObject) {
		BindingResult dummyBindingResult = BindingResults.newDummy();

		List<JSONObject> res = (List<JSONObject>) providerReportService.getProviderReportsByPRFReceivedYearly(fromYear, toYear, userObject, dummyBindingResult);
		
		return res;

	}	
}
