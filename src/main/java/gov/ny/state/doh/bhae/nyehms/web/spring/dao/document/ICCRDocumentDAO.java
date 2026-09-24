package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ICCRDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;

public interface ICCRDocumentDAO {
	
	public List<JSONObject> getCodedTestTypeResult(String testType);
	
	public int saveICCRDocumentData(ICCRDataEntryVO iccrDataEntryVO)throws DataAccessException;
	
	public int saveICCRTestData(Map<String, Object> iccrTestDataMap)throws DataAccessException;
	
    public ICCRDocVO getICCRDocument(int docId) throws DataAccessException;
    
    public List<ICCRTestVO> getICCRDocTestData(int documentId) throws DataAccessException;	    

	public int writeStdDocData(Map<Object, Object> docVO);

	public PersonViewVO getStdICCRDocumentForClericalReview(int documentId);

}
