package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CancerDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface CancerDocumentService {

	public PersonViewVO getClericalDocumentAsPerson(int documentId);

	public CancerDocVO getCancerDoc(int docId, BindingResult bindingResult, UserObject userObject);

	public void processCancerDocument(long documentId, String stateno, Long clusterId, String match_cd,
			BindingResult bindingResult, UserObject userObject);

}
