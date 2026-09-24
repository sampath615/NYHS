

package gov.ny.state.doh.bhae.nyehms.web.spring.service.search;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.search.SearchPersonCriteriaVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface SearchService {
	
	public List<SearchPersonCriteriaVO> searchPersonFields(SearchPersonCriteriaVO searchPersonCriteriaVO, String searchBy, String searchType, BindingResult result, UserObject userObject, String sessionID);

	public List<String> getpersonSearchResultsRowIds(
			SearchPersonCriteriaVO searchPersonCriteriaVO, String searchBy,
			String searchType, BindingResult dummyBindingResult,
			UserObject userObject, String sessionId);

	public List<SearchPersonCriteriaVO> getpersonSearchResultsWithRowIds(
			List<String> rowIdList, String sortByColumn, String sortOrder, BindingResult dummyBindingResult,
			UserObject userObject, String sessionId);

	public List<String> getRidrStateCodesToProcess(String fileType);

	public List<Map<String, Object>> getRidrSearchViewResults(String stateValue, String fileType);

}
