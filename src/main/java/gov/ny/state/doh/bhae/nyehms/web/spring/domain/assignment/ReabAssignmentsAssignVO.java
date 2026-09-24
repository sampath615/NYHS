package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class ReabAssignmentsAssignVO {
	
	private String reabAssignType= "";
	private long workerId;
	private String assignmentIds;
	private String comments;
	private long superWorkerId;
	private String reabActivityCode;
	
	public String getReabAssignType() {
		return reabAssignType;
	}
	public void setReabAssignType(String reabAssignType) {
		this.reabAssignType = reabAssignType;
	}
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}
	public String getAssignmentIds() {
		return assignmentIds;
	}
	public void setAssignmentIds(String assignmentIds) {
		this.assignmentIds = assignmentIds;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public long getSuperWorkerId() {
		return superWorkerId;
	}
	public void setSuperWorkerId(long superWorkerId) {
		this.superWorkerId = superWorkerId;
	}
	public String getReabActivityCode() {
		return reabActivityCode;
	}
	public void setReabActivityCode(String reabActivityCode) {
		this.reabActivityCode = reabActivityCode;
	}
	
	
	
	}
