package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

public class PersonFieldDocsVO {
	private String field="";
	private String documentId="";
	private String documentType="";
	private String startDate;
	private String docEditiedFlag;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocEditiedFlag() {
		return docEditiedFlag;
	}
	public void setDocEditiedFlag(String docEditiedFlag) {
		this.docEditiedFlag = docEditiedFlag;
	}
	
}
