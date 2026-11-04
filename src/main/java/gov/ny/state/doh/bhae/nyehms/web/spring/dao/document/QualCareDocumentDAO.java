package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.QualCareDocVO;

public interface QualCareDocumentDAO {
	
	public int writeStdDocData(Map<Object, Object> docVO);
	
	public QualCareDocVO getQualCareDocument(int documentId) throws DataAccessException;

}
