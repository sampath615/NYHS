package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SSDMDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 * This interface provides methods to interact with SSDM doc.
 *
 * @author vxp06
 */
public interface SSDMDocumentService {

    /**
     * This method is called from controller to get data for a given SSDM doc
     * id.
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public SSDMDocVO getLabDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

    /**
     * This method is called from controller to get data for a given SSDM doc
     * id.
     *
     * @param docId
     * @param result
     * @param userObject
     * @param sessionID
     * @return
     */
    public SSDMDocVO getSSDMDoc(int docId, BindingResult result, UserObject userObject, String sessionID);

	public int writeRawSSDMDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public PersonViewVO getStdSSDMDocumentForClericalReview(int documentId);

	public int writeAllRawSSDMDocDataToStdTable();

	public void documentInitialValidation();

}
