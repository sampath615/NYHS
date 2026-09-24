package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SYPHDocVO;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface SYPHDocumentService {
	
	
	public SYPHDocVO getSYPHDoc(int docID, BindingResult result, UserObject userObject, String sessionID);
	
	public int writeRawSYPHDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);
	
	public PersonViewVO getStdSYPHDocumentForClericalReview(int documentId);
	
}
