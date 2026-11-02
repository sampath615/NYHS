package gov.ny.state.doh.bhae.nyehms.web.spring.controller.sierra; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.sierra.SierraWebService;

/*
 *Author		: Sampath Anumolu

 *Date Created 	: 02/12/2015
 *Description 	: This class used to Load the Document Errors and fixing it
 */

@RequiredArgsConstructor
@Controller
public class RASController {
	
	private final SierraWebService sierraWebService;
	
	@RequestMapping("/getRASErrors")
	public String getRASErrors(Map<String, Object> pMap) {
		return "ras/rasErrors";
		
	}
	
	@RequestMapping(value="/loadFailedRasDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<JSONObject> loadFailedRasDocuments(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return sierraWebService.loadFailedRasDocuments(bindingResult, userObject);
		
	}
	
	@RequestMapping(value="/runRasJob.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String runRasJob(@RequestParam Integer DOCUMENT_ID, HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		
		return sierraWebService.runRasJob(DOCUMENT_ID, bindingResult, userObject);
		
	}
	
	
	
	@RequestMapping("/scheduleRasJob")
	public String scheduleRasJob(Map<String, Object> map, HttpServletRequest request) {
		
		
		return "ras/rasScheduler";
	}
	
	
	@RequestMapping(value="/updateRASDocumentsStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> updateRASDocumentsStatus(HttpServletRequest request, @RequestParam("documentIds[]")  long[] documentIds, @RequestParam(value="comments", required = false) String comments, @RequestParam String actionName, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		sierraWebService.updateRASSelectedDocumentStatus(documentIds, comments, actionName, bindingResult, userObject);
		
		return targetMap;
	}
	
}
