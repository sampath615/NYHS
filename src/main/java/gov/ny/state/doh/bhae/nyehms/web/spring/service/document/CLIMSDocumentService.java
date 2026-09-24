package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CLIMSDocVO;

import org.springframework.dao.DataAccessException;

/**
 * @author skg02
 *
 */
public interface CLIMSDocumentService {
	
    public CLIMSDocVO getCLIMSDocument(int documentID) throws DataAccessException;

}
