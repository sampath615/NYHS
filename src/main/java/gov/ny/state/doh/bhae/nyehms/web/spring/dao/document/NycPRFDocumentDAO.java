package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface NycPRFDocumentDAO {	
	public NycPRFDocVO getNycPRFDoc(int docId)throws DataAccessException;
	public int writeStdDocData(Map<Object, Object> stdDocData);
	public int writeRawPRFPartnerDataToStdTable(int documentId);
	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId);	
}
