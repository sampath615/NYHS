package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycDeathDocVO;
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
public interface NycDeathDocumentService {

    /**
     * This method will return NYC death document data for a given doc id
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public NycDeathDocVO getNycDeathDoc(int docId, BindingResult result,
    		UserObject userObject, String sessionID);

	public int writeNycRawDeathDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId, Map<String, Map<String, String>> zipCodeReferenceMap);
	
	public PersonViewVO getNycStdDeathDocumentForClericalReview(int documentId);
	
	public void setNYCFacilityData(String pFI, String nycInstCode, Map<Object, Object>  stdDocData);
}
