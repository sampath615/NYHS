package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;


import java.util.Map;


public interface CASADocumentDAO {
	
	public int writeStdDocData(Map<Object, Object> docVO);
    
}
