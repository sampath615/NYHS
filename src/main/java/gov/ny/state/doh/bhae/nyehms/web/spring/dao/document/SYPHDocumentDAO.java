package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;


import java.util.Map;


public interface SYPHDocumentDAO {
	
    public int writeRawSYPHDocDataToStdTable(Map<Object, Object> docVO);

}

