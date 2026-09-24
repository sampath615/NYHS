package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;


public class LabInteractionHistoryVO {
	
	private int interactionId;
	private String pfi;
	private String interactionNotes = "";	
	private String communicationType;
	private String interactionDate;
	private String interactionBy;
	private String issueResolved;
	private int workerId;

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public int getInteractionId() {
		return interactionId;
	}

	public void setInteractionId(int interactionId) {
		this.interactionId = interactionId;
	}
	
	public String getPfi() {
		return pfi;
	}

	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	
	public String getIssueResolved() {
		return issueResolved;
	}

	public void setIssueResolved(String issueResolved) {
		this.issueResolved = issueResolved;
	}

	public String getInteractionNotes() {
		return interactionNotes;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	public void setInteractionNotes(String interactionNotes) {
		this.interactionNotes = interactionNotes;
	}

	public String getInteractionDate() {
		return interactionDate;
	}

	public void setInteractionDate(String interactionDate) {
		this.interactionDate = interactionDate;
	}

	public String getInteractionBy() {
		return interactionBy;
	}

	public void setInteractionBy(String interactionBy) {
		this.interactionBy = interactionBy;
	}

}
