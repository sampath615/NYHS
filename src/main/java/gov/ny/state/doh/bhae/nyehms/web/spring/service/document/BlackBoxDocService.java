package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.BlackBoxDocVO;

/**
 * This interface provides methods to interact with Black Box document.
 *
 * @author SKA03
 */
public interface BlackBoxDocService {

    /**
     * This method will return the NDI document for a given document id.
     *
     * @param documentId
     * @return
     */
    public BlackBoxDocVO getBlackBoxDoc(int docId);
    
	public List<Map<String, String>> getBlackBoxRefData();
}
