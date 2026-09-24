package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.OldCaseDocVO;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for Old Case document . It provides methods for inserting and
 * retrieve back data for Old Case doc.
 *
 * Please see OldCasesDocumentDAO.xml for its implementation.
 *
 * @author ska03
 */
public interface OldCasesDocumentDAO {

    /**
     * This method returns data for TB document for a given document ID.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public OldCaseDocVO getOldCaseDocVO(int docId) throws DataAccessException;

}
