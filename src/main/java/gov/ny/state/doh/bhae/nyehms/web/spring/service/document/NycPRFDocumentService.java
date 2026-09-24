package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPRFDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycPrfFormVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.validation.BindingResult;

public interface NycPRFDocumentService {
	public NycPRFDocVO getNycPRFDoc(int docId, BindingResult result, UserObject userObject, String sessionID);
	public void insertNycPRFDoc(NycPrfFormVO nycPrfFormVO, BindingResult result, UserObject userObject, String sessionID);
	public int writeRawNycPRFRecordDataToStdTable(Map<Object, Object> docVO,
			BigDecimal documentId);
	public PersonViewVO getNYCPRFDocumentForClericalReview(int documentId);
	public void writeRawNycPRFPartnerDataFromRawTable(BigDecimal documentId);
	public int writeNYCPRFDocAndPartnersData(Map<Object, Object> docVO,
			BigDecimal documentId);
}
