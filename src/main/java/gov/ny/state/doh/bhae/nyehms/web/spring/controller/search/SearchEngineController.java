package gov.ny.state.doh.bhae.nyehms.web.spring.controller.search;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.ClusterDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.NonCaseDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LaftFollowupDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.person.PersionService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.search.SearchService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.BindingResults;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 *Author		: Srikar Kummamuri
 *Date Created 	: Aug-09-2012
 *Description 	: This will handle all requests and responses related to Search Engine.
 */

@Controller
public class SearchEngineController {	
	//Crate a dummy BindingResult for the JSON calls as they don't have that object to pass to service layer.
	//This done as all service layer depended on DAO accepts binding result and json calls dont have it.
	BindingResult dummyBindingResult = BindingResults.newDummy();
	private static Logger log = LoggerFactory.getLogger(SearchEngineController.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();

	@Autowired GetGridObject getGridObject;		
	@Autowired SearchService searchService;
	@Autowired private PersionService personService;
	@Autowired private NonCaseDocumentService nonCaseDocumentService;
	@Autowired private ClusterDocumentService clusterDocumentService;
	@Autowired private LaftFollowupDocumentService laftFollowupDocumentService;
	/**
	 * 
	 * @param pMap
	 * @return
	 */
	@RequestMapping("/getSearchView")
	public String returnSearchView(
			Map<String, Object> pMap)
	{		
		SearchPersonCriteriaVO searchCriteriaVO = new SearchPersonCriteriaVO();
		pMap.put("searchCriteriaVO", searchCriteriaVO);
		List<Map<String, String>> originCategories = personService.getOriginCategories();
		pMap.put("originCategories", originCategories);
		
		List<Map<String, String>> nonCaseReasons = nonCaseDocumentService.getNonCaseReasons();
		pMap.put("nonCaseReasons", nonCaseReasons);
		
		List<Map<String, String>> clusterIdentificationMethod = clusterDocumentService.getClusterIDentificationMethod();
		pMap.put("clusterIdentificationMethod", clusterIdentificationMethod);
		
		List<Map<String, String>> laftFollowupReasons = laftFollowupDocumentService.getLaftFollowupReasons();
		pMap.put("laftFollowupReasons", laftFollowupReasons);		
		
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		pMap.put("addressFieldVO", addressFieldVO);
		return "search/UserSearchView";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/removeQuickSearchResultsFromScope.html", produces = "text/html")
	public @ResponseBody String removeQuickSearchResultsFromScope(
			HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.removeAttribute("searchResultsRowIds");
		return "SUCCESS";
	}

	@RequestMapping(value="/getSearchViewResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject returnAssignmentsAsJson(
			HttpServletRequest request,
			@RequestParam String searchBy,
			@RequestParam String searchType,
			@RequestParam String sidx, 
			@RequestParam String sord,
			@ModelAttribute("searchPersonCriteriaVO") SearchPersonCriteriaVO searchPersonCriteriaVO,
			@RequestParam int rows, @RequestParam int page, UserObject userObject)
	{
		boolean isExistingSearch  = false;
		Random randomNumber = new Random();
		HttpSession session = request.getSession();
		if(userObject==null) userObject= new UserObject();
		Map<Object, Object> targetMap= new HashMap<Object, Object>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
		List<SearchPersonCriteriaVO> cursorout = null;
		int totalRecordsCount = 0;
		if("QUICK".equals(searchType)){
			List<String> searchResultsRowIds = null;
			if(session.getAttribute("searchResultsRowIds") == null){
				searchResultsRowIds = searchService.getpersonSearchResultsRowIds(
						searchPersonCriteriaVO, searchBy, searchType, bindingResult,
						userObject, userObject.getSessionId());
				session.setAttribute("searchResultsRowIds", searchResultsRowIds);
			}else{
				searchResultsRowIds = (List<String>)session.getAttribute("searchResultsRowIds");
			}
			totalRecordsCount = searchResultsRowIds.size();
			if(totalRecordsCount > 0){
				if(CommonUtil.isNullOrEmpty(sidx )){
					int end_index = rows*page;
					if(totalRecordsCount < end_index){
						end_index = totalRecordsCount;
					}
					List<String> rowIdList = searchResultsRowIds.subList((page-1)*rows, end_index);
					cursorout = searchService.getpersonSearchResultsWithRowIds(rowIdList, sidx, sord, bindingResult, userObject, userObject.getSessionId());
				}else{
					cursorout = searchService.getpersonSearchResultsWithRowIds(searchResultsRowIds, sidx, sord, bindingResult, userObject, userObject.getSessionId());
				}
			}
		}else{
			cursorout = searchService.searchPersonFields(searchPersonCriteriaVO, searchBy, searchType, bindingResult, userObject, userObject.getSessionId());
			totalRecordsCount = cursorout.size();
		}
		if(log.isDebugEnabled() && cursorout!= null){
			log.debug(nyehmsLog+" Record Count : {}",cursorout.size());
		}
		//Process the search results and include italic/multiple records identifiers to data
		//processSearchResults.getProcessedPersonSearchResults(cursorout, request);
		GridObject jsonResponse = getGridObject.getGridDataOnPaginationLoad(cursorout, page, rows, totalRecordsCount);
		if(totalRecordsCount == 0 && targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()) != null){
			jsonResponse.setMessage((String)targetMap.get(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName()).toString());
		}
		return jsonResponse;		
	}

	/**
	 * 
	 * @param pMap
	 * @return
	 */
	@RequestMapping("/getRIDRSearchView")
	public String getRIDRSearchView(
			Map<String, Object> pMap)
	{		
		List<String> stateCodes = searchService.getRidrStateCodesToProcess(null);
		pMap.put("stateCodes", stateCodes);
		AddressFieldVO addressFieldVO = new AddressFieldVO();
		pMap.put("addressFieldVO", addressFieldVO);
		return "search/ridrSearchView";
	}
	
	
	
	@RequestMapping(value="/getPendingStateCodes.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<String> getPendingStateCodes(
			HttpServletRequest request, @RequestParam String fileType)
	{
		return searchService.getRidrStateCodesToProcess(fileType);
	}

	
	/**
	 * 
	 * @param request
	 * @param stateValue
	 * @return
	 */
	@RequestMapping(value="/getRidrSearchViewResults.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getRidrSearchViewResults(
			HttpServletRequest request, @RequestParam String stateValue, @RequestParam String fileType)
	{
		return searchService.getRidrSearchViewResults(stateValue, fileType);
	}

}
