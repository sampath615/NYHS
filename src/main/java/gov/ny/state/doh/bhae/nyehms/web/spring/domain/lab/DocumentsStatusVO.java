package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;
import java.util.Map;



public class DocumentsStatusVO {
	
	private Map<String, List<Integer>> statusCodesAndDocumentIds;

	public Map<String, List<Integer>> getStatusCodesAndDocumentIds() {
		return statusCodesAndDocumentIds;
	}

	public void setStatusCodesAndDocumentIds(
			Map<String, List<Integer>> statusCodesAndDocumentIds) {
		this.statusCodesAndDocumentIds = statusCodesAndDocumentIds;
	}
	
		
	
}
