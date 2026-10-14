package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Map;


import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface NycPRFDocumentDAO {	
	public int writeStdDocData(Map<Object, Object> stdDocData);
	public int writeRawPRFPartnerDataToStdTable(int documentId);
	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId);	
}
