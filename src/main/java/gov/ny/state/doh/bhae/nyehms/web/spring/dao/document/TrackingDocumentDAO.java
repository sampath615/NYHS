package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAddressDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAliasDocVO;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for Tracking documents . It provides methods for inserting and
 * retrieve back data for Tracking doc.
 *
 * Please see TrackingDocumentDAO.xml for its implementation.
 *
 * @author ska03
 */
public interface TrackingDocumentDAO {

    /**
     * This method returns data for TB document for a given document ID.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public TRKAddressDocVO getTRKAddressDoc(int docId) throws DataAccessException;

	public TRKAliasDocVO getTRKAliasDoc(int docId);

}
