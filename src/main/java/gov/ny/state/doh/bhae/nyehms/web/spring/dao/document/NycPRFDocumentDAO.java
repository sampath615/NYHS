package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface NycPRFDocumentDAO {	
	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId);	
}
