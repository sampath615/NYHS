package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftFollowupDetailsVO;

public interface LaftFollowupDocumentDAO {
	
	public List<Map<String, String>> getLaftFollowupReasons();
	
	public int flagLaftFollowupDocument(Map<String, Object> inputParameterMap);
	
	public int isLaftFollowupActive(int documentId);
	
	public List<LaftFollowupDetailsVO> getLAFTFollowupDetails(int documentId);
	
	public List<LaftFollowupDetailsVO> getFlaggedLaftFollowupData() throws DataAccessException;
	
	public Map<String, Object> getResolveAndCommentsData(int followupIdPk);
	
	public List<LaftFollowupDetailsVO> getFlaggedLaftResolvedData() throws DataAccessException;
	
	public List<Map<String, String>> getLaftFollowupResolutions();
	
	public int resolveLaftFollowupDocuments(Map<String, Object> resolveLaftFollowupDocumentsMap);	
	
	public String getDocumentReviewComment(int documentId) throws DataAccessException;
	
	public int LaftFollowupCommentsUpdate(Map<String, Object> inputParameterMap);
	
	public List<String> getFlaggedLaftFollowupDocumentComments(int followupIdPk);
	
	public List<String> getFlaggedLaftFollowupInitialComments(int followupIdPk);
	
	public int updateLaftFollowupDocumentId(Map<Object, Object> inputOutputParameterMap);	
	
	public int updateLaftFollowupResolutionForDeletedDocs(Map<Object, Object> inputOutputParameterMap);	
}
