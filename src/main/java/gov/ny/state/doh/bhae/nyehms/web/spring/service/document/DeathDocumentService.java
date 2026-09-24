package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DeathDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 *
 * This interface provides methods to interact with Death document.
 *
 * @author vxp06
 */
public interface DeathDocumentService {

    /**
     * This method will return death document data for a given doc id
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public DeathDocVO getDeathDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

   public int writeRawDeathDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId, Map<String, Map<String, String>> zipCodeReferenceMap);
	
	public PersonViewVO getStdDeathDocumentForClericalReview(int documentId);
}
