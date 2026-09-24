package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftFollowupDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface LaftFollowupDocumentService {
	
	public List<Map<String, String>> getLaftFollowupReasons();
	
	public void flagLaftFollowupDocument(int triggeringDocId, String triggeringStateno, String comments, int reason, BindingResult bindingResult, UserObject userObject);
	
	public int isLaftFollowupActive(int documentId);
	
	public List<LaftFollowupDetailsVO> getLAFTFollowupDetails(int documentId, UserObject userObject);
	
	public List<LaftFollowupDetailsVO> getFlaggedLaftFollowupData(UserObject userObject, BindingResult bindingResult);
	
	public List<LaftFollowupDetailsVO> getFlaggedLaftResolvedData(UserObject userObject, BindingResult bindingResult);	
	
	public List<Map<String, String>> getLaftFollowupResolutions();	
	
	public void resolveLaftFollowupDocuments(int[] documentIds, String resolutionId, String resolutionComments, UserObject userObject, BindingResult bindingResult);	
	
	public void updateLaftFollowupDocumentId(int flaggedDocumentId, int newDocumentId);
	
	public void updateLaftFollowupResolutionForDeletedDocs(int documentId, UserObject userObject);
	
	public void addInitialReviewComments(int followupId, String comments, BindingResult bindingResult, UserObject userObject);

}
