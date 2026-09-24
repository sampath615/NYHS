package gov.ny.state.doh.bhae.nyehms.web.spring.dao.reports;

import java.util.List;
import java.util.Map;

public interface DocumentSummaryReportDAO {
	
	public List<Map<String, String>> getDocumentSummaryData(Map<String, String> inputParameterMap);

}
 