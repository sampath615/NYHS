package gov.ny.state.doh.bhae.nyehms.web.spring.controller.person;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*
 *Author		: Sampath Kumar

 *Date Created 	: 06/20/2014
 *Description 	: This class create to Merge & un Merge the Person details
 */

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.MergeUnMergePersonService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.MergeUnMergePersonTransactionHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;


@RequiredArgsConstructor
@Controller
public class MergeUnMergePersonController {

	private final GetGridObject getGridObject;
	private final DateUtil dateUtil;
	private final PersionService personService;
	private final MergeUnMergePersonService mergeUnMergePersonService;
	private final MergeUnMergePersonTransactionHelper mergeUnMergePersonTransactionHelper;
	


	/**
	 * Changes the user's screen to the mergePerson.jsp
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPersonMerge")
	public String getPersonMerge(
			Map<String, Object> map,
			HttpServletRequest request)
	{
		List<Map<String, String>> originCategories = personService.getOriginCategories();
		map.put("originCategories", originCategories);
		return "person/mergePerson";
	}
	
	/**
	 * Changes the user's screen to the unMergePerson.jsp
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPersonUnMerge")
	public String getPersonUnMerge(
			Map<String, Object> map,
			HttpServletRequest request)
	{
		return "person/unMergePerson";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(
			value="/getFlaggedStateNosPairs.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getFlaggedStateNosPairs(
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				mergeUnMergePersonService.getFlaggedStatenosList(bindingResult, userObject));
		return jsonResponse;
	}

	@RequestMapping(
			value="/updateMergeFollowUp.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> updateMergeFollowUp(
			@RequestParam long id,
			@RequestParam String followUp,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.updateMergeFollowUp(
				id, followUp, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * get the Raw Provider Addresses List using Sub Provider Address ID(Sub Grid)
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/flagStatenoPairsAsSame.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> flagStatenoPairsAsSame(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.flagStatenoPairsAsSame(
				flaggedStatenoVO, bindingResult, userObject);
		return targetMap;
	}


	/**
	 * get the Raw Provider Addresses List using Sub Provider Address ID(Sub Grid)
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/mergeStatenoPairs.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> mergeStatenoPairs(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		
		if(request.getParameter("rec_search_actionName") != null){
			
			flaggedStatenoVO.setRecSearchActionName(request.getParameter("rec_search_actionName"));
			flaggedStatenoVO.setRecSearchUnflaggedStateno(request.getParameter("rec_search_unflaggedStateno"));
			flaggedStatenoVO.setRecSearchComments(request.getParameter("rec_search_comments"));
			flaggedStatenoVO.setRecSearchSourceStateno(request.getParameter("rec_search_sourceStateno"));
			flaggedStatenoVO.setRecSearchStatenoToReplace(request.getParameter("rec_search_statenoToReplace"));
			
		}
		
		
		
		mergeUnMergePersonService.mergeStatenos(flaggedStatenoVO, bindingResult, userObject);
		
		
		return targetMap;
	}

	/**
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@RequestMapping(
			value="/flagStatenosAndMergeStatenoPairs.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> flagStatenosAndMergeStatenoPairs(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.flagStatenosAndMergeStatenoPairs(
				flaggedStatenoVO, bindingResult, userObject);
		return targetMap;
	}



	@RequestMapping(
			value="/getStatenosFlagHistoryList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getStatenosFlagHistoryList(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid
				(mergeUnMergePersonService.getFlagHistory(
						flaggedStatenoVO, bindingResult, userObject));
		return jsonResponse;
	}

	/**
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/rejectStatenosFlagPair.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> rejectStatenosFlagPair(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.rejectStatenoFlagPair(
				flaggedStatenoVO, bindingResult, userObject);
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
			value="/flagAsBadMerge.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> flagAsBadMerge(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.flagAsBadMerge(
				flaggedStatenoVO, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * 
	 * @param request
	 * @param duplicationId
	 * @param stateno1
	 * @param stateno2
	 * @param startDate
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getPersonViewData.json")
	public String getPersonViewData(
			HttpServletRequest request,
			@RequestParam String duplicationId,
			@RequestParam  String stateno1,
			@RequestParam  String stateno2,
			@RequestParam String startDate,
			Map<String, Object> map, UserObject userObject) 
	{		
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		List<PersonVO> personList = mergeUnMergePersonService.getPersonViewObjects(
				duplicationId, stateno1, stateno2, startDate, bindingResult, userObject);
		map.put("personObjects", personList);
		return "person/PersonComparePage";
	}
	
	/**
	 * 
	 * @param flaggedStatenoVO
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/unflagAsBadMerge.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> unflagAsBadMerge(
			@ModelAttribute("flaggedStatenoVO") FlaggedStatenoVO flaggedStatenoVO,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.unflagAsBadMerge(
				flaggedStatenoVO, bindingResult, userObject);
		return targetMap;
	}

	/**
	 * 
	 * @param duplicationId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSimilarFlaggedPairsList.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getSimilarFlaggedPairsList(
			@RequestParam String duplicationId,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				mergeUnMergePersonService.getSimilarFlaggedPairs(
						duplicationId, bindingResult, userObject));
		return jsonResponse;
	}


	@RequestMapping(
			value="/getBadlyMergedStatenos.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getBadlyMergedStatenos(
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				mergeUnMergePersonService.getBadlyMergedStatenos(
						bindingResult, userObject));
		return jsonResponse;
	}

	/**
	 * 
	 * @param stateno
	 * @param request
	 * @return
	 */
	@RequestMapping(
			value="/getBadlyMergedStatenoFlagHistory.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody GridObject getBadlyMergedStatenoFlagHistory(
			@RequestParam String stateno ,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		GridObject jsonResponse = getGridObject.GetGrid(
				mergeUnMergePersonService.getBadMergeFlagHistory(
						stateno, bindingResult, userObject));
		return jsonResponse;
	}

	/**
	 * 
	 * @param stateno1
	 * @param stateno2
	 * @param documentIds
	 * @param doccsDocumentList
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/unMergeTheDocuments.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> unMergeTheDocuments(
			@RequestParam String stateno1,
			@RequestParam String stateno2,
			@RequestParam String documentIds,
			@RequestParam(required = false) String doccsDocumentList,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonTransactionHelper.unMergeTheDocuments(
				stateno1, stateno2, documentIds, doccsDocumentList,
				bindingResult, userObject);
		return targetMap;
	}
	
	/**
	 * 
	 * @param stateno1
	 * @param documentIds
	 * @param doccsDocumentList
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/validateDodAgainstCollectionDate.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> validateDodAgainstCollectionDate(
			@RequestParam String stateno,
			@RequestParam String documentIds,
			@RequestParam String doccsDocumentList,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.validateDodAgainstCollectionDate(
				stateno, documentIds, doccsDocumentList,
				bindingResult, userObject);
		return targetMap;
	}
	

	/**
	 * 
	 * @param stateno1
	 * @param documentIds
	 * @param doccsDocumentList
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(
			value="/validateDodAgainstCollectionDateBeforeMerge.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> validateDodAgainstCollectionDateBeforeMerge(
			@RequestParam String stateno1,
			@RequestParam String stateno2,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.validateDodAgainstCollectionDateBeforeMerge(
				stateno1, stateno2,
				bindingResult, userObject);
		return targetMap;
	}
	

	/**
	 * 
	 * @param stateno_from
	 * @param documentIds
	 * @param doccsDocumentList
	 * @param request
	 * @return
	 */
	@RequestMapping(
			value="/createNewStatenoAndLinkDocuments.json",
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Map<Object, Object> createNewStateno(
			@RequestParam String stateno_from,
			@RequestParam String documentIds,
			@RequestParam(required = false) String doccsDocumentList,
			HttpServletRequest request, UserObject userObject)
	{
		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		mergeUnMergePersonService.createNewStatenoAndLinkDocuments(
				stateno_from, documentIds, doccsDocumentList, bindingResult, userObject);
		return targetMap;
	}
	
		
	//Get Number of Merges report by origin category
	@RequestMapping("/getMergesByOrigin")
	public String getMergesByOrigin(Map<String, Object> map, HttpServletRequest request) {
		map.put("category", "origin");
		map.put("reportType", "Merges");
		return "reports/MergesReport";
	}
	
	//Get Daily Count of Merges Reviewed by Staff report
	@RequestMapping("/getMergesByDailyStaff")
	public String getMergesByDailyStaff(Map<String, Object> map, HttpServletRequest request) {
		map.put("category", "byDailyStaff");
		map.put("reportType", "Merges");
		return "reports/MergesReport";
	}	
	
	//Get Number of Bad Merges report
	@RequestMapping("/getMergesByBadMerges")
	public String getMergesByBadMerges(Map<String, Object> map, HttpServletRequest request) {
		map.put("category", "badMerges");
		map.put("reportType", "Bad Merges");
		return "reports/MergesReport";
	}
	
	//Get Daily Count of Bad Merges Reviewed by Staff report
	@RequestMapping("/getBadMergesByDailyStaff")
	public String getBadMergesByDailyStaff(Map<String, Object> map, HttpServletRequest request) {
		map.put("category", "badMergesByDailyStaff");
		map.put("reportType", "Bad Merges");
		return "reports/MergesReport";
	}	
	
	//Get the view for merge reports
	@RequestMapping(value="/getMergeReports.html", method = RequestMethod.POST)
	public String  getMergeReports(@RequestParam String category, @RequestParam String fromDate, @RequestParam String toDate, HttpServletRequest request, Map<String, Object> map, UserObject userObject){
		
		if(userObject==null) userObject= new UserObject();

		if("origin".equals(category)){
			List<Map<String, Object>> mergeReportsByOrigin = mergeUnMergePersonService.getMergeReportsByOrigin(fromDate, toDate, userObject);			
			map.put("mergeReportsByOrigin", mergeReportsByOrigin);			
			map.put("previousWeeksFriday1", getPreviousWeeksFriday(7));
	        map.put("previousWeeksFriday2", getPreviousWeeksFriday(6));
	        map.put("previousWeeksFriday3", getPreviousWeeksFriday(5));
	        map.put("previousWeeksFriday4", getPreviousWeeksFriday(4));
	        map.put("previousWeeksFriday5", getPreviousWeeksFriday(3));
	        map.put("previousWeeksFriday6", getPreviousWeeksFriday(2));
	        map.put("previousWeeksFriday7", getPreviousWeeksFriday(1));
	        map.put("previousWeeksFriday8", getPreviousWeeksFriday(0));	  
			return "reports/merges/mergeReportsByOrigin";			
		} else if("badMerges".equals(category)){		
			List<Map<String, Object>> mergeReportsByBadMerges = mergeUnMergePersonService.getMergeReportsByBadMerges(fromDate, toDate, userObject);			
			map.put("mergeReportsByBadMerges", mergeReportsByBadMerges);	
			map.put("previousWeeksFriday1", getPreviousWeeksFriday(7));
	        map.put("previousWeeksFriday2", getPreviousWeeksFriday(6));
	        map.put("previousWeeksFriday3", getPreviousWeeksFriday(5));
	        map.put("previousWeeksFriday4", getPreviousWeeksFriday(4));
	        map.put("previousWeeksFriday5", getPreviousWeeksFriday(3));
	        map.put("previousWeeksFriday6", getPreviousWeeksFriday(2));
	        map.put("previousWeeksFriday7", getPreviousWeeksFriday(1));
	        map.put("previousWeeksFriday8", getPreviousWeeksFriday(0));	  
			return "reports/merges/mergeReportsByBadMerges";
		} else if("byDailyStaff".equals(category)){
			Map<String, Map<String, Map<String, Map<String, Integer>>>> mergeReportsByDailyStaff =  mergeUnMergePersonService.getMergeReportsByDailyStaff(fromDate, toDate, map, userObject);			
			map.put("mergeReportsByDailyStaff", mergeReportsByDailyStaff);
			return "reports/merges/mergeReportsByDailyStaff";	
		} else {
			Map<String, Map<String, Map<String, Map<String, Integer>>>> badMergeReportsByDailyStaff =  mergeUnMergePersonService.getBadMergeReportsByDailyStaff(fromDate, toDate, map, userObject);			
			map.put("badMergeReportsByDailyStaff", badMergeReportsByDailyStaff);
			return "reports/merges/badMergeReportsByDailyStaff";	
		}
	}
	
	//Create the merge report as an Excel file to be downloaded
	@RequestMapping(value="/generateMergeReport.xlsx", method = RequestMethod.GET)
	public ModelAndView generateMergeReport(@RequestParam String category, @RequestParam String fromDate, @RequestParam String toDate, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, UserObject userObject) {		
		Cookie myCookie = new Cookie("filedownload", "false");		
		response.addCookie(myCookie);		
		boolean isError = false;		
		ModelAndView mav = null;
		String reportType = "Merges";
		
		
		if ("origin".equals(category)){
			List<Map<String, Object>> mergeReportsByOrigin =  mergeUnMergePersonService.getMergeReportsByOrigin(fromDate, toDate, userObject);
			if(mergeReportsByOrigin != null && mergeReportsByOrigin.size() > 0 ){
				mav = new ModelAndView("generateMergeReportByOrigin");				
		        mav.addObject("mergeReportsByOrigin", mergeReportsByOrigin);	
		        mav.addObject("previousWeeksFriday1", getPreviousWeeksFriday(7));
		        mav.addObject("previousWeeksFriday2", getPreviousWeeksFriday(6));
		        mav.addObject("previousWeeksFriday3", getPreviousWeeksFriday(5));
		        mav.addObject("previousWeeksFriday4", getPreviousWeeksFriday(4));
		        mav.addObject("previousWeeksFriday5", getPreviousWeeksFriday(3));
		        mav.addObject("previousWeeksFriday6", getPreviousWeeksFriday(2));
		        mav.addObject("previousWeeksFriday7", getPreviousWeeksFriday(1));
		        mav.addObject("previousWeeksFriday8", getPreviousWeeksFriday(0));	       
			}else{
				isError = true;
			}	
		} else if ("badMerges".equals(category)) {
			reportType = "Bad Merges";
			List<Map<String, Object>> mergeReportsByBadMerges =  mergeUnMergePersonService.getMergeReportsByBadMerges(fromDate, toDate, userObject);
			if(mergeReportsByBadMerges != null && mergeReportsByBadMerges.size() > 0 ){
				mav = new ModelAndView("generateMergeReportByBadMerges");				
		        mav.addObject("mergeReportsByBadMerges", mergeReportsByBadMerges);	
		        mav.addObject("previousWeeksFriday1", getPreviousWeeksFriday(7));
		        mav.addObject("previousWeeksFriday2", getPreviousWeeksFriday(6));
		        mav.addObject("previousWeeksFriday3", getPreviousWeeksFriday(5));
		        mav.addObject("previousWeeksFriday4", getPreviousWeeksFriday(4));
		        mav.addObject("previousWeeksFriday5", getPreviousWeeksFriday(3));
		        mav.addObject("previousWeeksFriday6", getPreviousWeeksFriday(2));
		        mav.addObject("previousWeeksFriday7", getPreviousWeeksFriday(1));
		        mav.addObject("previousWeeksFriday8", getPreviousWeeksFriday(0));	       
			}else{
				isError = true;
			}	
		} else if ("byDailyStaff".equals(category)) {
			Map<String, Map<String, Map<String, Map<String, Integer>>>> mergeReportsByDailyStaff =  mergeUnMergePersonService.getMergeReportsByDailyStaff(fromDate, toDate, map, userObject);
			if(mergeReportsByDailyStaff != null && mergeReportsByDailyStaff.size() > 0 ){
				mav = new ModelAndView("generateMergeReportByDailyStaff");				
		        mav.addObject("mergeReportsByDailyStaff", mergeReportsByDailyStaff);	
    
			}else{
				isError = true;
			}	
		} else {
			reportType = "Bad Merges";
			Map<String, Map<String, Map<String, Map<String, Integer>>>> badMergeReportsByDailyStaff =  mergeUnMergePersonService.getBadMergeReportsByDailyStaff(fromDate, toDate, map, userObject);
			if(badMergeReportsByDailyStaff != null && badMergeReportsByDailyStaff.size() > 0 ){
				mav = new ModelAndView("generateBadMergeReportByDailyStaff");				
		        mav.addObject("badMergeReportsByDailyStaff", badMergeReportsByDailyStaff);	
    
			}else{
				isError = true;
			}	
		}
			
		Cookie myCookie1 = null;
		Cookie myCookie2 = null;				
		if(!isError){			
			mav.addObject("fromDate", fromDate);			
			mav.addObject("toDate", toDate);
			myCookie1 = new Cookie("TRANSACTION_MESSAGE", CommonUtil.encodeCookieValue("Successfully generated the report."));
	        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
		}else{
			myCookie1 = new Cookie("TRANSACTION_MESSAGE",CommonUtil.encodeCookieValue("There is no data to generate the report."));
	        myCookie2 = new Cookie("TRANSACTION_RESULT", AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());	 
			map.put("category", category);
			map.put("reportType", reportType);
			mav = new ModelAndView("reports/MergesReport");
		}
		
        myCookie = new Cookie("filedownload", "true");
        response.addCookie(myCookie);
        response.addCookie(myCookie1);
        response.addCookie(myCookie2);
        
        return mav;		
	}
	
	
	public String getPreviousWeeksFriday(int numOfWeeks) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		
		cal.add(Calendar.DATE, -(cal.get(Calendar.DAY_OF_WEEK)-1) - 2 - 7*numOfWeeks);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String previousWeeksFriday = sdf.format(cal.getTime());
		
		return previousWeeksFriday;
	}
	
	
	
	@RequestMapping(value="/getDependentDocumentList.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<Object, Object> getDependentDocumentList(HttpServletRequest request, @RequestParam int documentId, UserObject userObject) {
		
		if(userObject==null) userObject= new UserObject();
		//request.getParameter("documentId")
		Map<Object, Object> targetMap= new HashMap<>();
		//, 
		List<Integer> linkedDocumentIds = mergeUnMergePersonService.getDependentDocumentList(documentId);
		
		targetMap.put("linkedDocumentIds", linkedDocumentIds);
		
		return targetMap;
	
	}
}
