package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.CASADocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CASADocumentDAO {
	
    public CASADocVO getCASADoc(int docId) throws DataAccessException;

    public PersonViewVO getStdCASADocumentForClericalReview(int documentId);

	public int writeStdDocData(Map<Object, Object> docVO);
    
}
