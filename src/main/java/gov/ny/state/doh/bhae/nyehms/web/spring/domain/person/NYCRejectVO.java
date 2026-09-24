package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UploadNYCSurvRejectVO;

public class NYCRejectVO {
	
	private int DOCUMENT_ID;
	private UploadNYCSurvRejectVO rejectDocumentVO;
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public UploadNYCSurvRejectVO getRejectDocumentVO() {
		return rejectDocumentVO;
	}
	public void setRejectDocumentVO(UploadNYCSurvRejectVO rejectDocumentVO) {
		this.rejectDocumentVO = rejectDocumentVO;
	}
	
}
