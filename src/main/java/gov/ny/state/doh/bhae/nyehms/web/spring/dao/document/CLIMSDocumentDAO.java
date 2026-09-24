package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CLIMSDocVO;

import org.springframework.dao.DataAccessException;

public interface CLIMSDocumentDAO {
	
    public CLIMSDocVO getCLIMSDocument(int documentID) throws DataAccessException;

}
