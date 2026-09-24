package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DoccsDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface DoccsDocumentService {	
	public DoccsDocVO getDoccsDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int getDoccsDocumentId(int docId);

	public int writeRawDoccsAndDinDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public int insertRawDoccsDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public List<Map<String, Object>> getRawDinDoccsList(BigDecimal documentId);

	public void updateRawDinDocToStandardFormat(Map<String, Object> dinDoc);

	public int insertRawDinDoccsDataToStdTable(
			List<Map<String, Object>> rawDinDoccsList);

	public void insertRawDinProbCodesToStdProbCodes(BigDecimal documentId);

	public PersonViewVO getStdDinDocumentForClericalReview(int documentId);

	public DinDoccsDocVO getDinDoccsDocContent(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public int documentInitialValidation(BigDecimal documentId);

	public void updateRawDinDocStatus(BigDecimal documentId);
	
	public void loadReferenceVariables();
    	
    public void resetDocumentReferenceVariables(); 
}
