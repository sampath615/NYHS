package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.ViralLoadReportsService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.RiaReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;


@Controller
public class ViralLoadReportsController {


	
	@Autowired
	private ViralLoadReportsService viralLoadReportsService; 
	
	@Autowired
	private RiaReviewService riaReviewService;
	
	@Autowired
	private LaftFollowupDocumentService laftFollowupDocumentService;
	

	
	@RequestMapping(value="/ndvlReport")
	public String getNdvlReport(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		map.put("addressFieldVO", addressFieldVO);
		
		List<Map<String, String>> laftFollowupReasons = laftFollowupDocumentService.getLaftFollowupReasons();
		map.put("laftFollowupReasons", laftFollowupReasons);
		
		Map<String, List<Map<String, String>>> riaDropDownList = riaReviewService.getRiaCodedDropDownData();
		
		map.put("riaDropDownList", riaDropDownList);
		
		map.put("REPORT_NAME", EnvironmentProperties.envProperties.getProperty("RIA_REVIEW"));
		
		
		return "reports/lab/ndvlReport";
	
	}
	

	@RequestMapping(
			value="/getVLObxNotes.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody List<JSONObject> getVLObxNotes(
			@RequestParam String stateno ,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		return viralLoadReportsService.getVLObxNotes(stateno, bindingResult, userObject);
	}
	
	
	@RequestMapping(value="/vlReport")
	public String getVlReport(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		return "reports/lab/vlReport";
	
	}
	
	@RequestMapping(value="/loadNdvlReportData.json", method = RequestMethod.POST)
	public @ResponseBody List<JSONObject>  loadNdvlReportData(HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		return viralLoadReportsService.loadNdvlReportData(dummyBindingResult, userObject);
		
	}
	
	@RequestMapping(value="/generateLoadNdvlReportData.xlsx", method = RequestMethod.GET)
	public ModelAndView  generateLoadNdvlReportData(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");
		
		ModelAndView mav = new ModelAndView("ndvlReportSpreadsheet");
		
		List<JSONObject> ndvlReportData = viralLoadReportsService.loadNdvlReportData(dummyBindingResult, userObject);
		
		mav.addObject("ndvlReportData", ndvlReportData);
		
        return mav;
    
	}
	
	@RequestMapping(value="/noAdditionalLabReport")
	public String noAdditionalLabReport(Map<String, Object> map, HttpServletRequest request, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();

		return "reports/lab/NoAdditionalLab";
	
	}
	
	@RequestMapping(value="/getNoAdditionalReportData.json", method = RequestMethod.POST)
	public @ResponseBody List<JSONObject>  getNoAdditionalReportData(@RequestParam String dateRange, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		Map<String, String> dummyMindingMap= new HashMap<String, String>();
		BindingResult dummyBindingResult = new MapBindingResult(dummyMindingMap, "Sample");

		return viralLoadReportsService.getNoAdditionalReportData(dateRange, dummyBindingResult, userObject, null);
		
	}
	
}
