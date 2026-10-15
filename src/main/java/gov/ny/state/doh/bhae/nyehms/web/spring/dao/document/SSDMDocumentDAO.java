package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SSDMDocVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for SSDM documents. Please see SSDMDocumentDAO.xml for its
 * implementations.
 *
 * @author vxp06
 */
public interface SSDMDocumentDAO {

    /**
     * This method returns SSDM doc data fields for a given document id in the
     * form of SSDMVo object.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public SSDMDocVO getSSDMDoc(int docId) throws DataAccessException;

	public int writeStdDocData(Map<Object, Object> docVO);

	public int writeAllRawSSDMDocDataToStdTable();

	public int updateRawDocumentStatus();

	public void documentInitialValidation();

}
