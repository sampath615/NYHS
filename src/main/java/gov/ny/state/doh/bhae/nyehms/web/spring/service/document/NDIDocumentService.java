package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDICauseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDIDocumentVO;

/**
 * This interface provides methods to interact with NDI document.
 *
 * @author vxp06
 */
public interface NDIDocumentService {

    /**
     * This method will return the NDI document for a given document id.
     *
     * @param documentId
     * @return
     */
    public NDIDocumentVO getNDIDoc(int docId);

	public NDICauseDocVO getNDICauseDoc(int docId);

}
