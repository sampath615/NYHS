package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentWorkerTallyListVO {	
	private String workerId="";
	private String workerRegionId="";
	private String allOpenCount="";
	private String nonSubmittedAcrf="";
	private String allReopenCount="";
	private String allRejectedCount="";
	private String pendingReassignCount="";
	private String pendingAcrfRvwCount="";
	private String newCount="";
	private String adminClosedCount="";
	
	
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(String workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public String getAllOpenCount() {
		return allOpenCount;
	}
	public void setAllOpenCount(String allOpenCount) {
		this.allOpenCount = allOpenCount;
	}
	public String getNonSubmittedAcrf() {
		return nonSubmittedAcrf;
	}
	public void setNonSubmittedAcrf(String nonSubmittedAcrf) {
		this.nonSubmittedAcrf = nonSubmittedAcrf;
	}
	public String getAllReopenCount() {
		return allReopenCount;
	}
	public void setAllReopenCount(String allReopenCount) {
		this.allReopenCount = allReopenCount;
	}
	public String getAllRejectedCount() {
		return allRejectedCount;
	}
	public void setAllRejectedCount(String allRejectedCount) {
		this.allRejectedCount = allRejectedCount;
	}
	public String getPendingReassignCount() {
		return pendingReassignCount;
	}
	public void setPendingReassignCount(String pendingReassignCount) {
		this.pendingReassignCount = pendingReassignCount;
	}
	public String getPendingAcrfRvwCount() {
		return pendingAcrfRvwCount;
	}
	public void setPendingAcrfRvwCount(String pendingAcrfRvwCount) {
		this.pendingAcrfRvwCount = pendingAcrfRvwCount;
	}
	public String getNewCount() {
		return newCount;
	}
	public void setNewCount(String newCount) {
		this.newCount = newCount;
	}
	public String getAdminClosedCount() {
		return adminClosedCount;
	}
	public void setAdminClosedCount(String adminClosedCount) {
		this.adminClosedCount = adminClosedCount;
	}
}
