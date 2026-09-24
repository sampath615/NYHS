package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.SYPHDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public interface SYPHDocumentDAO {
	
    public SYPHDocVO getSYPHDoc(int docId) throws DataAccessException;
    
    public int writeRawSYPHDocDataToStdTable(Map<Object, Object> docVO);

    public PersonViewVO getStdSYPHDocumentForClericalReview(int documentId);
   
}

