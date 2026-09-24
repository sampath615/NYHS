package gov.ny.state.doh.bhae.nyehms.web.spring.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.DocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.search.SearchDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class SearchServiceImpl implements SearchService{
	
	private static Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	private String nyehmsLog=AppConstants.nyehmLogDebugString.getName();
	
	@Autowired
	private SearchDAO searchDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private CommonExceptionHandler commonExceptionHandler;

	@Override
	public List<String> getpersonSearchResultsRowIds(
			SearchPersonCriteriaVO searchPersonCriteriaVO, String searchBy,
			String searchType, BindingResult result,
			UserObject userObject, String sessionId){
			
			boolean errorFlag=false;
			
			List<String> searchResultsRowIds = new ArrayList<String>();

			try{
				Map<Object,Object> map = new HashMap<Object,Object>();
				List<SearchPersonCriteriaVO> cursor = new ArrayList<SearchPersonCriteriaVO>();	
				
				
				
				String cityNo = searchPersonCriteriaVO.getCityNo();
								
				String _stateno = searchPersonCriteriaVO.getStateNo();
				
				boolean hasActiveStatenoCheck = false;
				
				
				Map<String, String> outputParameterMap  = null;
				
				String searchByCondition = null;
				
				if(CommonUtil.isNotNullAndEmpty(cityNo)){
					
					hasActiveStatenoCheck = true;
					outputParameterMap = searchDAO.getNyehmsStatenoUsingCityNo(cityNo);
					
					searchByCondition = "CITYNO";
					
					if(outputParameterMap == null){
						
						commonExceptionHandler.inspectDBTransacionResult(-1, "No matches for the given the "+searchByCondition+".", result);
						
						return searchResultsRowIds;
					}
					
				}else if(_stateno != null && !"".equals(_stateno) && !_stateno.contains("*")){
					
					hasActiveStatenoCheck = true;
					
					outputParameterMap = searchDAO.getTheActiveStateNo(_stateno);
					
					searchByCondition = "stateno";
					
				}
				
				
				if(hasActiveStatenoCheck){
					
					_stateno = outputParameterMap.get("CURRENTSTATENO");
					String flagActive = outputParameterMap.get("FLAGACTIVE");
					if("-1".equals(_stateno) && "N".equals(flagActive)){
						
						commonExceptionHandler.inspectDBTransacionResult(-1, "The "+searchByCondition+" you searched is no longer active", result);
						
						return searchResultsRowIds;
					}else if("-1".equals(_stateno) && CommonUtil.isNullOrEmpty(flagActive)){
						
						commonExceptionHandler.inspectDBTransacionResult(-1, "No matches for the given the "+searchByCondition+".", result);
						
						return searchResultsRowIds;
					}
				}
				
				map.put("p_stateno", _stateno);
				map.put("p_first_name", searchPersonCriteriaVO.getFirstName());
				map.put("p_last_name", searchPersonCriteriaVO.getLastName());
				
				map.put("p_date_of_birth",  searchPersonCriteriaVO.getDateOfBirth());
							
				map.put("p_date_of_death", searchPersonCriteriaVO.getDateOfDeath());
				
				map.put("p_ssn", searchPersonCriteriaVO.getSsn());           
				map.put("p_nysid", searchPersonCriteriaVO.getNysid());         
				map.put("p_din", searchPersonCriteriaVO.getDin());           
				map.put("p_worker_id", userObject.getWorkerId());
				map.put("refCursor", cursor);	
				map.put("p_flag_doc_only_search", searchBy);
				map.put("p_calling_app_name", "NYEHMS");
				map.put("p_cityno", cityNo);
			
			
				searchDAO.searchPersonFieldsWithQuickSearch(map);
				
				searchResultsRowIds = (List<String>)map.get("refCursor");
				
				if(searchResultsRowIds == null || searchResultsRowIds.size() == 0){
					commonExceptionHandler.inspectDBTransacionResult(-1, "No documents found for the given criteria.", result);
				}
			
			}catch(DataAccessException e){
				errorFlag=true;
				log.error(nyehmsLog+"  "+ e.getMessage());
				commonExceptionHandler.handleException(e, userObject,sessionId);			
			}catch(Exception e){
				errorFlag=true;
				log.error(nyehmsLog+"  "+ e.getMessage());
				commonExceptionHandler.handleException(e, userObject,sessionId);			
			}finally{						
				if(errorFlag)
					commonExceptionHandler.inspectDBTransacionResult(-1, "Search failed, please contact helpline.", result);
			}	
			return searchResultsRowIds;
			
	}
	
	@Override
	public List<SearchPersonCriteriaVO> getpersonSearchResultsWithRowIds(
			List<String> rowIdList, String sortByColumn, String sortOrder, BindingResult dummyBindingResult,
			UserObject userObject, String sessionId){
		
		Map<Object, Object> inputOutputParameterMap  = new HashMap<Object, Object>();
		
		
		
		inputOutputParameterMap.put("sortByColumn", sortByColumn);
		
		inputOutputParameterMap.put("sortOrder", sortOrder);
		
		
		List<SearchPersonCriteriaVO> searchResults = new ArrayList<SearchPersonCriteriaVO>();
		

		int rowIdListSize = rowIdList.size();
		
		//Where condition can accept 1000 arguments.
		for (int index = 0; index < rowIdListSize; index = index+1000){
			
			if(index + 1000 > rowIdListSize){
				inputOutputParameterMap.put("rowIdList", rowIdList.subList(index, rowIdListSize));
			}else{
				inputOutputParameterMap.put("rowIdList", rowIdList.subList(index, index+1000));
			}
			
			List<SearchPersonCriteriaVO> partialSearchResults = searchDAO.personSearchResultsWithQuickSearch(inputOutputParameterMap);
			
			searchResults.addAll(partialSearchResults);
			
		}

		return searchResults;
	}
	
	@Override
	public List<SearchPersonCriteriaVO> searchPersonFields(SearchPersonCriteriaVO searchPersonCriteriaVO, String searchBy, String searchType, BindingResult result, UserObject userObject, String sessionID){
		boolean errorFlag=false;
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<SearchPersonCriteriaVO> cursor = new ArrayList<SearchPersonCriteriaVO>();	
		
		List<SearchPersonCriteriaVO> list = new ArrayList<SearchPersonCriteriaVO>();
		
		String _stateno = searchPersonCriteriaVO.getStateNo();
		
		String cityNo = searchPersonCriteriaVO.getCityNo();
		
		boolean hasActiveStatenoCheck = false;
		
		
		Map<String, String> outputParameterMap  = null;
		
		String searchByCondition = null;
		
		if(CommonUtil.isNotNullAndEmpty(cityNo)){
			
			hasActiveStatenoCheck = true;
			outputParameterMap = searchDAO.getNyehmsStatenoUsingCityNo(cityNo);
			
			searchByCondition = "CITYNO";
			
			if(outputParameterMap == null){
				
				commonExceptionHandler.inspectDBTransacionResult(-1, "No matches for the given the "+searchByCondition+".", result);
				
				return list;
			}
			
		}else if(_stateno != null && !"".equals(_stateno) && !_stateno.contains("*")){
			
			hasActiveStatenoCheck = true;
			
			outputParameterMap = searchDAO.getTheActiveStateNo(_stateno);
			
			searchByCondition = "stateno";
			
		}
		
		
		if(hasActiveStatenoCheck){
			
			
			
			_stateno = outputParameterMap.get("CURRENTSTATENO");
			String flagActive = outputParameterMap.get("FLAGACTIVE");
			
			if("-1".equals(_stateno) && "N".equals(flagActive)){
				
				commonExceptionHandler.inspectDBTransacionResult(-1, "The "+searchByCondition+" you searched is no longer active", result);
				
				return list;
			}else if("-1".equals(_stateno) && CommonUtil.isNullOrEmpty(flagActive)){
				
				commonExceptionHandler.inspectDBTransacionResult(-1, "No matches for the given the "+searchByCondition+".", result);
				
				return list;
			}
		}
		
		
		map.put("p_stateno", _stateno);
		map.put("p_first_name", searchPersonCriteriaVO.getFirstName());
		map.put("p_last_name", searchPersonCriteriaVO.getLastName());
		map.put("p_date_of_birth",  searchPersonCriteriaVO.getDateOfBirth());
		map.put("p_date_of_death", searchPersonCriteriaVO.getDateOfDeath());
		map.put("p_ssn", searchPersonCriteriaVO.getSsn());           
		map.put("p_nysid", searchPersonCriteriaVO.getNysid());         
		map.put("p_din", searchPersonCriteriaVO.getDin());           
		map.put("p_worker_id", userObject.getWorkerId());
		map.put("refCursor", cursor);	
		map.put("p_flag_doc_only_search", searchBy);
		map.put("p_calling_app_name", "NYEHMS");
		map.put("p_cityno", cityNo);
		
		try{

				searchDAO.searchPersonFields(map);
				
				list = (List<SearchPersonCriteriaVO>)map.get("refCursor");
				
				if(list == null || list.size() == 0){
					
					commonExceptionHandler.inspectDBTransacionResult(-1, "No documents found for the given criteria.", result);
				}
			
		}catch(DataAccessException e){
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);			
		}catch(Exception e){
			errorFlag=true;
			log.error(nyehmsLog+"  "+ e.getMessage());
			commonExceptionHandler.handleException(e, userObject,sessionID);			
		}finally{						
			if(errorFlag)
				commonExceptionHandler.inspectDBTransacionResult(-1, "Search failed, please contact helpline.", result);
		}		
		return list;				
	}
	
	@Override
	public List<String> getRidrStateCodesToProcess(String fileType){
		


		Map<String,String> inputParameterMap = new HashMap<String,String>();
		
		inputParameterMap.put("fileType", fileType);
		
		List<String> stateCodes =  documentDAO.getRidrStateCodesToProcess(inputParameterMap);
		
		if(stateCodes != null && stateCodes.size() > 1){
			
			stateCodes.add(0, "ALL");
			
		}
		return stateCodes;
	}
	
	
	@Override
	public List<Map<String, Object>> getRidrSearchViewResults(String stateValue, String fileType){
		

		Map<String,String> inputParameterMap = new HashMap<String,String>();
		
		inputParameterMap.put("stateValue", stateValue);
		
		inputParameterMap.put("fileType", fileType);
		
		return documentDAO.getRidrSearchViewResults(inputParameterMap);
		
		
	}
}
