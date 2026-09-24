package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.STDConfDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 * This provides methods for interacting with STD_CONF document.
 *
 * @author ska03
 */
public interface STDConfDocumentService {

  
    /**
     * This function will return the TB document for a given docId
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
//    public TBDocVO getTBDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int writeRawSTDConfDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public PersonViewVO getStdConfDocumentForClericalReview(int documentId);
	
	public void loadSTDConfReferenceVariables();

	public void resetDocumentReferenceVariables();

	public STDConfDocVO getSTDConfDoc(int docId,
			BindingResult dummyBindingResult, UserObject userObject, String id);

	public List<Map<String, String>> getSTDConfRefData();

	public Map<String, List<String>> getStdConfLookupData();
}
