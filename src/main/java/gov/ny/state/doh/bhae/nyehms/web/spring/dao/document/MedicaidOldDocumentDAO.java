package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MedicaidOldDocVO;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for TB document . It provides methods for inserting and
 * retrieve back data for TB doc.
 *
 * Please see TBDocumentDAO.xml for its implementation.
 *
 * @author Srikar Kummamuri
 */
public interface MedicaidOldDocumentDAO {

    /**
     * This method returns data for TB document for a given document ID.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public MedicaidOldDocVO getMedicaidOldDoc(int docId) throws DataAccessException;
}
