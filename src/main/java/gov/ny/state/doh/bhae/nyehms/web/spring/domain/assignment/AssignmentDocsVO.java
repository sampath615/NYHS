package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentDocsVO {
	private long documentId;
	private String documentType;
	private String recievedDate;
	
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getRecievedDate() {
		return recievedDate;
	}
	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}
}
