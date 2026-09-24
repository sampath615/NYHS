package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDICauseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NDIDocumentVO;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for NDI documents. Please see NDIDocumentDAO.xml for its
 * implementations.
 *
 * @author hpm03
 */
public interface NDIDocumentDAO {

    /**
     * This method returns NDI doc data fields for a given document id in the
     * form of NDIVo object.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public NDIDocumentVO getNDIDoc(int docId) throws DataAccessException;

	public NDICauseDocVO getNDICauseDoc(int docId);

}