package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CASADocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface CASADocumentService {
	
	
	public CASADocVO getCASADoc(int docID, BindingResult result, UserObject userObject, String sessionID);
	
	public PersonViewVO getStdCASADocumentForClericalReview(int documentId);

	
	public int validateAndwriteRawCasaDocDataToStdTable(
			Map<Object, Object> docVO, BigDecimal documentId);
	
}
