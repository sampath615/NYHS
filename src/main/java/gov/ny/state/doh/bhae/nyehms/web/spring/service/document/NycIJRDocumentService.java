package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycIJRDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This interface provides methods to interact with NYC_IJR document.
 *
 * Since for version 1 NYC_IJR document will not be editable, we are not mentioning method for edit
 *
 * @author vxp06
 */
public interface NycIJRDocumentService {

    /**
     * This method will return the NYC_IJR document for a given document id.
     *
     * @param documentID
     * @return
     */
    public NycIJRDocVO getNYCIJRDocument(int docId);

	public int writeRawNycIJRDocDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);

	public List<String> getFileNames();

	public PersonViewVO getStdNycIJRDocumentForClericalReview(int documentId);

}
