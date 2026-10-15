package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TBDocVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for TB document . It provides methods for inserting and
 * retrieve back data for TB doc.
 *
 * Please see TBDocumentDAO.xml for its implementation.
 *
 * @author Srikar Kummamuri
 */
public interface TBDocumentDAO {

    /**
     * This method returns data for TB document for a given document ID.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public TBDocVO getTBDoc(int docId) throws DataAccessException;

	public int writeRawTBDocDataToStdTable(Map<Object, Object> docVO);

	public int insertRawEditedTBDocument(Map<Object, Object> rawDocData);

	public int insertStdEditedTBDocument(Map<Object, Object> stdDocData);
}
