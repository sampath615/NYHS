package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.BadlyMergedStatenosVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MergeUnMergePersonDAO {
	public List<FlaggedStatenoVO> getFlaggedStatenosList()throws DataAccessException;
	
	public void flagStatenoPairsAsSame(Map<Object,Object> map)throws DataAccessException;
	
	public void mergeStatenos(Map<Object,Object> map)throws DataAccessException;
	
	public void flagHistory(Map<Object,Object> map)throws DataAccessException;
	
	public void rejectStatenoFlagPair(Map<Object,Object> map)throws DataAccessException;

	public void fetchPersonObjects(Map<Object,Object> map)throws DataAccessException;
	
	public void flagBadMerge(Map<Object,Object> map)throws DataAccessException;
	
	public void unflagBadMerge(Map<Object,Object> map)throws DataAccessException;
	
	public void getSimilarFlaggedPairs(Map<Object,Object> map)throws DataAccessException;
	
	public List<BadlyMergedStatenosVO> getBadlyMergedStatenosList()throws DataAccessException;
	
	public void getBadMergeFlagHistory(Map<Object,Object> map)throws DataAccessException;
	
	public void unMergeTheDocuments(Map<Object,Object> map)throws DataAccessException;

	public void createNewStateno(Map<Object,Object> map)throws DataAccessException;

	public void commitCurrentTransaction();

	public int updateMergeFollowUp(Map<Object, Object> inOutParameterMap);

	public int addACRFForRejectedStateno(Map<String, Object> inputParameter);

	public void addACRFDuplicateReviewData(Map<String, Object> inputParameter);

	public String validateDodAgainstCollectionDateBeforeMerge(
			Map<String, Object> inputParameterMap);

	public String validateDodAgainstCollectionDate(
			Map<String, Object> inputParameterMap);
	
	//public void associateDocumentsToStateno(Map<Object,Object> map)throws DataAccessException;

	
	public List<Map<String, Object>> getMergeReportsByOrigin(Map<String, String> inOutParameterMap);
	
	public List<Map<String, Object>> getMergeReportsByBadMerges(Map<String, String> inOutParameterMap);
	
	public List<Map<String, Object>> getMergeReportsByDailyStaff(Map<String, String> inOutParameterMap);
	
	public List<Map<String, Object>> getBadMergeReportsByDailyStaff(Map<String, String> inOutParameterMap);

	public List<Integer> getDependentDocumentList(int documentId);
}
