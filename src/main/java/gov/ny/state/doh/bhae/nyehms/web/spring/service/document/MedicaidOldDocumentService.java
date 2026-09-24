package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidOldDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;


public interface MedicaidOldDocumentService {

   
    public MedicaidOldDocVO getMedicaidOldDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

}
