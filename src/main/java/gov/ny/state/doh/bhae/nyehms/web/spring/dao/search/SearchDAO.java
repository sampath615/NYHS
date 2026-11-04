package gov.ny.state.doh.bhae.nyehms.web.spring.dao.search;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SearchDAO {
	public List<SearchPersonCriteriaVO> searchPersonFields(Map<Object,Object> map) throws DataAccessException;
	
	
	public Map<String, String> getTheActiveStateNo(String _stateno) throws DataAccessException;

	public void searchPersonFieldsWithQuickSearch(Map<Object, Object> map);

	public List<SearchPersonCriteriaVO> personSearchResultsWithQuickSearch(
			Map<Object, Object> inputOutputParameterMap);

	public Map<String, String>  getNyehmsStatenoUsingCityNo(String cityNo);
}
 