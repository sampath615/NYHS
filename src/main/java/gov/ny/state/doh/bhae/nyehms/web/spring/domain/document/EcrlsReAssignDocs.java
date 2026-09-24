package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;

public class EcrlsReAssignDocs {

	private String keyName;
	private List<Long> documentIds;
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public List<Long> getDocumentIds() {
		return documentIds;
	}
	public void setDocumentIds(List<Long> documentIds) {
		this.documentIds = documentIds;
	}
		
}
