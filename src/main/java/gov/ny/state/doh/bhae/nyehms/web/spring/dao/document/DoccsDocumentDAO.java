package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.DinDoccsDocVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface DoccsDocumentDAO {	
	public int getDoccsDocumentId(int docId);
	public int insertRawDoccsDataToStdTable(Map<Object, Object> docVO);
	public List<Map<String, Object>> getRawDinDoccsList(BigDecimal documentId);
	public int insertRawDinDoccsDataToStdTable(Map<String, Object> inputParameterMap);
	public int insertRawDinProbCodesToStdProbCodes(BigDecimal documentId);
	public List<DinDoccsDocVO> getDinDoccsDocContent(Map<String, Object> inputParameterMap);
	public List<Integer> getDinDoccsIds(int document_ID);
	public int documentInitialValidation(BigDecimal documentId);
	public int stopDoccsDocumentAtRaw(BigDecimal documentId);
	public void updateRawDinDocStatus(BigDecimal documentId);
}
