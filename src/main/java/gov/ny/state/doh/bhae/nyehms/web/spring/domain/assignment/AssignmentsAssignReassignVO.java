package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentsAssignReassignVO {
	
	private String assignReassignType= "";
	private long supervisorRegionId;
	private long workerId;
	private String assignmentIds;
	private String comments;
	private String delCrf= "";
	
	public String getAssignReassignType() {
		return assignReassignType;
	}
	public void setAssignReassignType(String assignReassignType) {
		this.assignReassignType = assignReassignType;
	}
	public long getSupervisorRegionId() {
		return supervisorRegionId;
	}
	public void setSupervisorRegionId(long supervisorRegionId) {
		this.supervisorRegionId = supervisorRegionId;
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
	public String getDelCrf() {
		return delCrf;
	}
	public void setDelCrf(String delCrf) {
		this.delCrf = delCrf;
	}
}
