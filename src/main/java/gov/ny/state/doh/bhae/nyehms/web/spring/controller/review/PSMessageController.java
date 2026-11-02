package gov.ny.state.doh.bhae.nyehms.web.spring.controller.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.CommonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.CommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.review.PSMessageReviewService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
/*
 * Spring controller class for system message
 */
@RequiredArgsConstructor
@Controller
public class PSMessageController {
	
	private final PSMessageReviewService psMessageReviewService;
	
	private final CommonService commonService;
	
	private final CommonDocumentService commonDocumentService;

	@RequestMapping(value="/getReviewPsMessages")	
	public String getReviewPsMessages(HttpServletRequest request, Map<String, Object> map) {
		
		return "review/reviewPSMessages";
	}
	
	
	
	/**
	 * Loading the Lab Document Ids which got failed in Validation process 
	 * @param request Request Object
	 * @return List of Document Ids
	 */
	@RequestMapping(value="/getPsMessageReviewDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> getPsMessageReviewDocuments(HttpServletRequest request, @RequestParam String RESOLVED_STATUS, @RequestParam String PARAMETER_NAME,
			@RequestParam String MSG_TYPE_NAME, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return psMessageReviewService.getPsMessageReviewDocuments(RESOLVED_STATUS,PARAMETER_NAME, MSG_TYPE_NAME, bindingResult, userObject);

	}
	
	
	@RequestMapping(value="/updatePsMessageReviewStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updatePsMessageReviewStatus(HttpServletRequest request, 
			@RequestParam  long triggerDocId, @RequestParam String actionName, @RequestParam String comments, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		psMessageReviewService.updatePsMessageReviewStatus(triggerDocId, actionName, comments, bindingResult, userObject);
		
		return targetMap;
	}
	
	@RequestMapping(value="/savePSMessageComments.json", method = RequestMethod.POST, produces = "application/json")	
	public @ResponseBody List<Map<String, String>> savePSMessageComments(HttpServletRequest request, @RequestParam String comments, @RequestParam Integer DOCUMENT_ID, UserObject userObject)
	{

		List<Map<String, String>> psMessageCommentList = psMessageReviewService.savePSMessageComments(comments, DOCUMENT_ID, userObject);
		
		return psMessageCommentList;
	}

	@RequestMapping(value="/saveLaftMessageComments.json", method = RequestMethod.POST, produces = "application/json")	
	public @ResponseBody List<Map<String, String>> saveLAFTMessageComments(HttpServletRequest request, @RequestParam String comments, @RequestParam String reason, @RequestParam Integer document_id, UserObject userObject)
	{

		List<Map<String, String>> laftMsgCommentReason = psMessageReviewService.saveLAFTMessageComments(comments, reason, document_id, userObject);
		
		return laftMsgCommentReason;
	}
	
	
	
	@RequestMapping(value="/psMessageReport")
	public String psMessageReport(Map<String, Object> map) {

		return "reports/psMessageReport";
	
	}
	
		
	
		
		
		@RequestMapping(value="/psMessageReport/{reportType}")
	public String psDispositionReport(Map<String, Object> map, HttpServletRequest request, @PathVariable String reportType, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult bindingResult = BindingResults.newDummy();

		Map<String, String> countyMap=commonService.getCountyCodes(bindingResult, userObject, request.getSession().getId());
		map.put("countyMap", countyMap);
		
		List<JSONObject> dispositionCodeValues = commonDocumentService.getDispositionCodeValues(true);
		map.put("dispositionCodeValues", dispositionCodeValues);
		
		List<JSONObject> psMessageTypes = commonService.getPsMessageTypes();
		map.put("psMessageTypes", psMessageTypes);
		
		map.put("REPORT_TYPE", reportType);
		
		map.put("REPORT_NAME", EnvironmentProperties.envProperties.getProperty(reportType+"_report"));

		return "reports/psDispositionReport";
	
	}
	
	@RequestMapping(value="/getPSDispositionReport.json", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>>  getPSDispositionReport(@RequestParam String messageType, @RequestParam String messageCounty, @RequestParam String messageDisposition, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();

		return psMessageReviewService.getPSDispositionReport(fromDate, toDate, messageType, messageCounty, messageDisposition, dummyBindingResult, userObject, null);
		
	}
	
	@RequestMapping(value="/getPSOOCExpsReport.json", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>>  getPSOOCExpsReport(@RequestParam String messageCounty,  @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();

		return psMessageReviewService.getPSOOCExpsReport(fromDate, toDate, messageCounty, dummyBindingResult, userObject, null);
		
	}
	
	
	
	@RequestMapping(value="/getPSMessageReportMatrix.html", method = RequestMethod.POST)
	public @ResponseBody String  getPSMessageReportMatrix(@RequestParam String reportType, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();

		return   psMessageReviewService.getPsMessageReport(fromDate, toDate, reportType, dummyBindingResult, userObject, null);
		
	}
	
	@RequestMapping(value="/generatePSMessageReportSpreadsheet.xlsx", method = RequestMethod.GET)
	public ModelAndView  generatePSMessageReportSpreadsheet(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, @RequestParam String reportType, @RequestParam String fromDate, @RequestParam String toDate, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();
		
		BindingResult dummyBindingResult = BindingResults.newDummy();
		
		ModelAndView mav = new ModelAndView("psMessageReportSpreadsheet");
		
		psMessageReviewService.getPsMessageReport(fromDate, toDate, reportType, dummyBindingResult, userObject, mav);
		
        return mav;
           		
	}
	
	@RequestMapping(value="/generatePsOOCExpsReport.xlsx", method = RequestMethod.GET)
	public ModelAndView generatePsOOCExpsReport(
			@RequestParam String messageCounty, @RequestParam String fromDate, @RequestParam String toDate, 
			HttpServletRequest request, 
			HttpServletResponse response,
			Map<String, Object> map, UserObject userObject) {
		
			Map<Object, Object> targetMap= new HashMap<>();
			BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
			
			List<Map<String, String>> psOOCExpsReport = psMessageReviewService.getPSOOCExpsReport(fromDate, toDate, messageCounty, bindingResult, userObject, null);
			
			
			ModelAndView mav = null;
			
			mav = new ModelAndView("generatePsOOCExpsReport");
				
		    mav.addObject("psOOCExpsReport", psOOCExpsReport);
		        
		    return mav;           		
	}
		
	
}
