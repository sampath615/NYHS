package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ICCRDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

public interface ICCRDocumentService {

	public void saveICCRDataEntryForm(ICCRDataEntryVO iccrDataEntryVO, BindingResult bindingResult, UserObject userObject);
	
	public List<JSONObject> getCodedTestTypeResult(String testType);
	
	public void insertICCRRawData(ICCRDataEntryVO iccrDataEntryVO, UserObject userObject);
	
	public ICCRDocVO getICCRDocument(int docId);
	
	public int writeRawICCRDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId);
	
	public PersonViewVO getStdICCRDocumentForClericalReview(int documentId);
	
}
