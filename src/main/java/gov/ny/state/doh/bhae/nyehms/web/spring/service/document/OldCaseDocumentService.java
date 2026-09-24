package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.OldCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

/**
 * This provides methods for interacting with TB document.
 *
 * @author vxp06
 */
public interface OldCaseDocumentService {

    public OldCaseDocVO getOldCaseDocVO(int docId, BindingResult result, UserObject userObject, String sessionID);

}
