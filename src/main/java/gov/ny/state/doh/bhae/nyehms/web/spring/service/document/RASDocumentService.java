package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RASDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

public interface RASDocumentService {
	
	public RASDocVO getRASDocVO(int documentId, boolean sierraFlag, BindingResult result, UserObject userObject);
	
	public String getLinkedResDocuments(int documentId);
	
	public void updateResistanceRasDocuments(int documentId, BindingResult result, UserObject userObject);	

}
