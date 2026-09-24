package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAddressDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAliasDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import org.springframework.validation.BindingResult;

/**
 * This provides methods for interacting with Tracking documents.
 *
 * @author vxp06
 */
public interface TrackingDocumentService {

    /**
     * This function will return the TRK address document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public TRKAddressDocVO getTRKAddressDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public TRKAliasDocVO getTRKAliasDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

}
