package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RASDocVO;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface RASDocumentDAO {
	
	public RASDocVO getRASDoc(int documentId) throws DataAccessException;
	
	public String getRASDocGeneNames(int SEQ_RESULT_ID);
	
	public List<String> getRASDocDrugClasses(int SEQ_RESULT_ID);
	
	public JSONObject getLinkedResistanceDocuments(int documentId);
	
	public int isDocumentLinked(int documentId);
	
	public void updateGenSeqRasElig(Map<Object, Object> inputOutputParameterMap);
	
	public int getLinkedRecCount(int documentId);	
	
	public void updateRasSeqLinkStatus(Map<Object, Object> inputOutputParameterMap);
	
	public void updateStdRasDoc(int documentId);
	
	public List<Integer> getLinkedResDocs(int documentId);

	public RASDocVO getSierraResponseData(int documentId);
	

}
