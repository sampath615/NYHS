package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentUpdateVO {
	private long documentId;
	private String surveilDate;
	private String closureCategory;
	private String comments;
	private String reabstract;
	
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getSurveilDate() {
		return surveilDate;
	}
	public void setSurveilDate(String surveilDate) {
		this.surveilDate = surveilDate;
	}
	public String getClosureCategory() {
		return closureCategory;
	}
	public void setClosureCategory(String closureCategory) {
		this.closureCategory = closureCategory;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	public String getReabstract() {
		return reabstract;
	}
	public void setReabstract(String reabstract) {
		this.reabstract = reabstract;
	}
	@Override
	public String toString() {
		return "AssignmentUpdateVO [documentId=" + documentId + ", surveilDate=" + surveilDate + ", closureCategory="
				+ closureCategory + ", comments=" + comments + ", reabstract=" + reabstract + "]";
	}
}
