package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.PRFDocumentVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

public interface PRFDocumentDAO {	
	public PRFDocumentVO getPRFDoc(int docId)throws DataAccessException;
	
	public int insertPrfDocData(PRFDocumentVO pRFDocumentVO)throws DataAccessException;

	public List<JSONObject> getprfReviewDocumentsData();

	public PRFDocumentVO getCompleteRawPRFDocumentContent(int docId);

	public int updatePrfDocStatusInRawTable(Map<Object, Object> inputParameterMap);

	public int writeRawPRFRecordDataToStdTable(Map<Object, Object> docVO);

	public int writeRawPRFPartnerDataToStdTable(int documentId);

	public int getPrfReviewDocsCount();

	public int insertRawPRFOOSDocument(Map<Object, Object> rawDocData);

	public int insertStdPRFOOSDocument(Map<Object, Object> stdDocData);


	public void storeRawPrfPartnersOOSDocument(
			Map<Object, Object> inputOutputParameterMap);
}
