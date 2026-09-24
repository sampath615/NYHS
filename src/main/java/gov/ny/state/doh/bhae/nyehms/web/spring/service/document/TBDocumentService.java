package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 * This provides methods for interacting with TB document.
 *
 * @author vxp06
 */
public interface TBDocumentService {

  
    /**
     * This function will return the TB document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public TBDocVO getTBDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int writeRawTBDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public PersonViewVO getStdTBDocumentForClericalReview(int documentId);

}
