package gov.ny.state.doh.bhae.nyehms.web.spring.service.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.BadlyMergedStatenosVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface MergeUnMergePersonService {
	public List<FlaggedStatenoVO> getFlaggedStatenosList(BindingResult result, UserObject userObject);
	
	public Integer flagStatenoPairsAsSame(FlaggedStatenoVO flaggedStatenoVO,BindingResult bindingResult,UserObject userObject);
	
	public void mergeStatenos(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
	
	public List<FlaggedStatenoVO> getFlagHistory(FlaggedStatenoVO flaggedStatenoVO, BindingResult result, UserObject userObject);
	
	public void rejectStatenoFlagPair(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
	
	public void updateRejectStatusForStatenoFlagPair(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
	
	public List<PersonVO> getPersonViewObjects(String duplicationId, String stateno1, String stateno2, String startDate, BindingResult bindingResult, UserObject userObject);
	
	public void flagAsBadMerge(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
	
	public void unflagAsBadMerge(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
		
	public List<FlaggedStatenoVO> getSimilarFlaggedPairs(String duplicationId, BindingResult result,UserObject userObject);
	
	public List<BadlyMergedStatenosVO> getBadlyMergedStatenos(BindingResult result,UserObject userObject);
	
	public List<BadlyMergedStatenosVO> getBadMergeFlagHistory(String stateno, BindingResult result, UserObject userObject);
	
	public void unMergeTheDocuments(String stateno1, String stateno2, String documentIds, String doccsDocumentList, BindingResult bindingResult, UserObject userObject);
	
	public String createNewStateno(BindingResult bindingResult, UserObject userObject);
	
	public void createNewStatenoAndLinkDocuments(String stateno_from, String documentIds, String doccsDocumentList, BindingResult bindingResult, UserObject userObject);

	public Integer callMergeStatenosService(FlaggedStatenoVO flaggedStatenoVO,BindingResult bindingResult, UserObject userObject);

	public void commitTransaction();

	public void updateMergeFollowUp(long id, String followUp,
			BindingResult bindingResult, UserObject userObject);

	public void flagStatenosAndMergeStatenoPairs(
			FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult,
			UserObject userObject);

	public void addACRFForRejectedStateno(int documentId, String stateno1, String stateno2,
			BindingResult bindingResult, UserObject userObject);

	public void addACRFDuplicateReviewData(int newAcrfDocId, String stateno2,
			BindingResult bindingResult, UserObject userObject);

	public void validateDodAgainstCollectionDate(String stateno,
			String documentIds, String doccsDocumentList,
			BindingResult bindingResult, UserObject userObject);

	public void validateDodAgainstCollectionDateBeforeMerge(String stateno1,
			String stateno2, BindingResult bindingResult, UserObject userObject);

	
	public List<Map<String, Object>> getMergeReportsByOrigin(String fromDate, String toDate, UserObject userObject);
	
	public List<Map<String, Object>> getMergeReportsByBadMerges(String fromDate, String toDate, UserObject userObject);
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getMergeReportsByDailyStaff(String fromDate, String toDate, Map<String, Object> map, UserObject userObject);
	
	public Map<String, Map<String, Map<String, Map<String, Integer>>>> getBadMergeReportsByDailyStaff(String fromDate, String toDate, Map<String, Object> map, UserObject userObject);

	public List<Integer> getDependentDocumentList(int documentId);
}
