package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.QualCareDocVO;

public interface QualCareDocumentService {

	public int writeRawQualCareDocDataToStdTable(Map<Object, Object> docVO, BigDecimal documentId);
	
	public QualCareDocVO getQualCareDocument(int documentId);

}
