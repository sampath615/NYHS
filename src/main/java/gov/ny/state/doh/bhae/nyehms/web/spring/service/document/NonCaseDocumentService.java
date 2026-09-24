package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NonCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

public interface NonCaseDocumentService {


	public void createNonCaseDocument(int triggeringDocId, String comments,
			int reason, BindingResult bindingResult, UserObject userObject);

	public NonCaseDocVO getNonCaseDoc(int docId);

	public void addCommentsToNonCaseDoc(int triggeringDocId, String comments,
			BindingResult bindingResult, UserObject userObject);

	public void changeNonCaseActiveFlagStatus(int triggeringDocId,
			String flagActive, BindingResult bindingResult,
			UserObject userObject);

	public List<Map<String, String>> getNonCaseReasons();

	public String flagAsNonCase(FlaggedStatenoVO flaggedStatenoVO, String reason, BindingResult bindingResult, UserObject userObject);

	public String unflagAsNonCase(FlaggedStatenoVO flaggedStatenoVO, BindingResult bindingResult, UserObject userObject);
	
	public void discardNonCaseDocument(Map<String, Object> inputParameterMap, int reason, BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getNonCaseDocHistory(String stateNo, BindingResult bindingResult,
			UserObject userObject);

	public int unflagStatenoAsNonCase(FlaggedStatenoVO flaggedStatenoVO, UserObject userObject);
	
	public Map<String, String> getDocDelDiscardHistory(int documentId,  BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getNonCaseEharsStatenos(String nonCasePernitalExpOnly, BindingResult bindingResult, UserObject userObject);

	public List<Map<String, String>> getNonCaseFlaggedHistory(String FROM_DATE, String TO_DATE, String ACTIVE_FLAG, BindingResult bindingResult, UserObject userObject);

	public Map<Object, Object> getReasonCodeForNonCase(String closureStatus, BindingResult bindingResult,
			UserObject userObject);
	
	public int checkForActiveNonCaseStateno(String stateNo);
}


