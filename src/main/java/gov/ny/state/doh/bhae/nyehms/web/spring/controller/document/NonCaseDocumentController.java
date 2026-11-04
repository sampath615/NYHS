package gov.ny.state.doh.bhae.nyehms.web.spring.controller.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DocumentTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;


@Controller
public class NonCaseDocumentController {
	
	@Autowired private DocumentTransactionHelper documentTransactionHelper;
	

	@Autowired private NonCaseDocumentService nonCaseDocumentService;
	


	@RequestMapping(value="/createNonCaseDocument.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> createNonCaseDocument(
			@RequestParam int triggeringDocId,
			@RequestParam String comments,
			@RequestParam int reason,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		documentTransactionHelper.createNonCaseDocument(triggeringDocId, comments, reason, bindingResult, userObject);
		
		return targetMap;
	}
	
	

	@RequestMapping(value="/addCommentsToNonCaseDoc.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addCommentsToNonCaseDoc(
			@RequestParam int triggeringDocId,
			@RequestParam String comments,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		nonCaseDocumentService.addCommentsToNonCaseDoc(triggeringDocId, comments, bindingResult, userObject);
		
		return targetMap;
	}
	

	@RequestMapping(value="/changeNonCaseActiveFlagStatus.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> changeNonCaseActiveFlagStatus(
			@RequestParam int triggeringDocId,
			@RequestParam boolean flagActive,
			HttpServletRequest request, UserObject userObject)
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		if(flagActive){
			documentTransactionHelper.changeNonCaseActiveFlagStatus(triggeringDocId, "Y", bindingResult, userObject);
		}else{
			documentTransactionHelper.changeNonCaseActiveFlagStatus(triggeringDocId, "N", bindingResult, userObject);
		}
		
		
		
		return targetMap;
	}
	
	
	/**
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/flagAsNonCase.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> flagAsNonCase(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO, @RequestParam String reason,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		nonCaseDocumentService.flagAsNonCase(
				flaggedStatenoVO, reason, bindingResult, userObject);
		return targetMap;
	}
	
	
	/**
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/unflagAsNonCase.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> unflagAsNonCase(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		String newStateno = nonCaseDocumentService.unflagAsNonCase(
				flaggedStatenoVO, bindingResult, userObject);
		
		targetMap.put("NEW_STATENO", newStateno);
		
		return targetMap;
	}
	
//	@RequestMapping(value="/discardNonCaseDocument.json", method = RequestMethod.POST, produces = "application/json")
//	public @ResponseBody Map<Object, Object> discardNonCaseDocument(
//			@RequestParam int triggeringDocId,
//			@RequestParam String comments,
//			@RequestParam int reason,
//			HttpServletRequest request)
	
	@RequestMapping(value="/getNonCaseDocHistory.html", method = RequestMethod.POST)	
	public String getNonCaseDocHistory(
			Map<String, Object> pMap,
			@RequestParam String stateNo,
			HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
				
		List<Map<String, String>> nonCaseDocHistory = nonCaseDocumentService.getNonCaseDocHistory(stateNo,  bindingResult,  userObject);

		pMap.put("nonCaseDocHistory", nonCaseDocHistory);
		
		return "review/NonCaseDocHistory";
	}
	
	@RequestMapping(value="/getNonCaseEharsStatenos")	
	public String getNonCaseEharsStatenos(
			Map<String, Object> pMap,
			HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> nonCaseDocEharsStatenos = nonCaseDocumentService.getNonCaseEharsStatenos("No", bindingResult,  userObject);
		
		pMap.put("title", "Not Deleted from eHARS");

		pMap.put("nonCaseDocEharsStatenos", nonCaseDocEharsStatenos);
		
		return "review/UndeletedNonCaseStatenos";
	}
	
	@RequestMapping(value="/getNonCasePerinatalStatenos")	
	public String getNonCasePerinatalStatenos(
			Map<String, Object> pMap,
			HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		List<Map<String, String>> nonCaseDocEharsStatenos = nonCaseDocumentService.getNonCaseEharsStatenos("Yes", bindingResult,  userObject);

		pMap.put("nonCaseDocEharsStatenos", nonCaseDocEharsStatenos);
		
		pMap.put("title", "DON'T DELETE from eHARS");
		
		return "review/UndeletedNonCaseStatenos";
	}
	
	@RequestMapping(value="/getNonCaseFlaggedHistory")	
	public String getNonCaseFlaggedHistory(
			Map<String, Object> pMap,
			HttpServletRequest request, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		return "review/NonCaseFlaggedHistory";
	}
	
	@RequestMapping(value="/loadNoncaseFlaggedData.json" , method = RequestMethod.POST, produces = "application/json")	
	public @ResponseBody List<Map<String, String>> loadNoncaseFlaggedData(
			Map<String, Object> pMap,
			HttpServletRequest request, @RequestParam(required=false) String FROM_DATE, @RequestParam(required=false) String TO_DATE, @RequestParam String ACTIVE_FLAG, UserObject userObject)
	{
		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
				
		return nonCaseDocumentService.getNonCaseFlaggedHistory(FROM_DATE, TO_DATE, ACTIVE_FLAG, bindingResult,  userObject);

	}
	
		
}
