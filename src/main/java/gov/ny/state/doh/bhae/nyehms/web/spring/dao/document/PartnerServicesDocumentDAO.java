package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.partnerService.PsAssignmentView;

import org.springframework.dao.DataAccessException;

/**
 * This is interface for ADAP document. It is associated with
 * ADAPDocumentDAO.xml for the implementation of this functions.
 *
 * @author Srikar Kummamuri
 */
public interface PartnerServicesDocumentDAO {

    /**
     * This will query the database for the given doc id and return data in form
     * of ADAPDocVO obj.
     *
     * @param docId
     * @return
     * @throws DataAccessException
     */
    public PsAssignmentView getPartnerServicesDocData(long docId) throws DataAccessException;

	public List<Map<String, String>> getCodedSexualBehaviorsList();

   
}
