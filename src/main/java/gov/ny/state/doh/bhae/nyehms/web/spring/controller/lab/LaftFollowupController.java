package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

@Controller
public class LaftFollowupController {
	
	@Autowired private LaftFollowupDocumentService laftFollowupDocumentService;
	@Autowired GetGridObject getGridObject;

	

	@RequestMapping(value="/flagLaftFollowupDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> flagLaftFollowupDocument(
			@RequestParam int triggeringDocId, @RequestParam String triggeringStateno,
			@RequestParam String comments,
			@RequestParam int reason,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		laftFollowupDocumentService.flagLaftFollowupDocument(triggeringDocId, triggeringStateno, comments, reason, bindingResult, userObject);
		
		return targetMap;
	}
	
	
	@RequestMapping(value="/isLaftFollowupActive.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> isLaftFollowupActive(HttpServletRequest request, @RequestParam int documentId, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		
		Map<Object, Object> targetMap= new HashMap<>();
		
		int laftFollowupCt = laftFollowupDocumentService.isLaftFollowupActive(documentId);
		
		targetMap.put("laftFollowupCt", laftFollowupCt);
		
		return targetMap;
	
	}			
	
	@RequestMapping(value="/resolveLaftFollowUpDocuments")	
	public String resolveLaftFollowUpDocuments(HttpServletRequest request, Map<String, Object> map) {
		
		List<Map<String, String>> laftFollowupResolutions = laftFollowupDocumentService.getLaftFollowupResolutions();
		map.put("laftFollowupResolutions", laftFollowupResolutions);		
		
		return "lab/FlaggedLAFTFollowupDocuments";
	}
	
	@RequestMapping(value="/loadflaggedLaftFollowupData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getFlaggedLaftFollowupData(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(laftFollowupDocumentService.getFlaggedLaftFollowupData(userObject, bindingResult));
		
		return jsonResponse;

	}	
	
	@RequestMapping(value="/resolveLaftFollowupDocuments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> resolveLaftFollowupDocuments(@RequestParam("followupIdsPk[]")  int[] followupIdsPk,
			@RequestParam String resolutionId, @RequestParam String resolutionComments, HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		laftFollowupDocumentService.resolveLaftFollowupDocuments(followupIdsPk, resolutionId, resolutionComments, userObject, bindingResult);
		
		return targetMap;
	}	
	
	@RequestMapping(value="/loadflaggedLaftResolvedData.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getFlaggedLaftResolvedData(HttpServletRequest request, UserObject userObject){
		

		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		GridObject jsonResponse = getGridObject.GetGrid(laftFollowupDocumentService.getFlaggedLaftResolvedData(userObject, bindingResult));
		
		return jsonResponse;

	}	
	
	@RequestMapping(value="/addInitialReviewComments.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addInitialReviewComments(@RequestParam int followupId, @RequestParam String comments, HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		laftFollowupDocumentService.addInitialReviewComments(followupId, comments, bindingResult, userObject);
		
		return targetMap;
	}	

}
