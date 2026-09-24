package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;


public class MultiStepProcessVO {

	private String pfi;
	private String loincCode;
	private List<Integer> documentIds;
	
	
	public String getPfi() {
		return pfi;
	}
	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	public String getLoincCode() {
		return loincCode;
	}
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}
	public List<Integer> getDocumentIds() {
		return documentIds;
	}
	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}
	
}
