package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CLIMSDocumentDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CLIMSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author skg02
 *
 */
@Service
public class CLIMSDocumentService {

    @Autowired private CommonExceptionHandler commonExceptionHandler;
    /**
     * This method will return CLIMS document for a given document id
     * {@inheritDoc}
     *
     * @param documentID
     * @return
     */
    
    @Autowired CLIMSDocumentDAO cLIMSDocumentDAO;
    
	public CLIMSDocVO getCLIMSDocument(int documentID) throws DataAccessException {
		
		CLIMSDocVO cLIMSDocumentVO = cLIMSDocumentDAO.getCLIMSDocument(documentID);
		
		return cLIMSDocumentVO;
	}
	

}
