package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentSuperTallyListVO {
	private long workerId;
	private long workerRegionId;
	private int allOpenTotal;
	private int myOpenTotal;
	private int myNonSubmittedAcrf;
	private int allRejectedTotal;
	private int reassignRequestedTotal;
	private int pendingAcrfRvwTotal;
	private int myPendingAcrfRvw;
	private int needsAssignTotal;
	private int adminClosedTotal;
	private int myAdminClsd;
	
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}
	public long getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(long workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public int getAllOpenTotal() {
		return allOpenTotal;
	}
	public void setAllOpenTotal(int allOpenTotal) {
		this.allOpenTotal = allOpenTotal;
	}
	public int getMyOpenTotal() {
		return myOpenTotal;
	}
	public void setMyOpenTotal(int myOpenTotal) {
		this.myOpenTotal = myOpenTotal;
	}
	public int getMyNonSubmittedAcrf() {
		return myNonSubmittedAcrf;
	}
	public void setMyNonSubmittedAcrf(int myNonSubmittedAcrf) {
		this.myNonSubmittedAcrf = myNonSubmittedAcrf;
	}
	public int getAllRejectedTotal() {
		return allRejectedTotal;
	}
	public void setAllRejectedTotal(int allRejectedTotal) {
		this.allRejectedTotal = allRejectedTotal;
	}
	public int getReassignRequestedTotal() {
		return reassignRequestedTotal;
	}
	public void setReassignRequestedTotal(int reassignRequestedTotal) {
		this.reassignRequestedTotal = reassignRequestedTotal;
	}
	public int getPendingAcrfRvwTotal() {
		return pendingAcrfRvwTotal;
	}
	public void setPendingAcrfRvwTotal(int pendingAcrfRvwTotal) {
		this.pendingAcrfRvwTotal = pendingAcrfRvwTotal;
	}
	public int getMyPendingAcrfRvw() {
		return myPendingAcrfRvw;
	}
	public void setMyPendingAcrfRvw(int myPendingAcrfRvw) {
		this.myPendingAcrfRvw = myPendingAcrfRvw;
	}
	public int getNeedsAssignTotal() {
		return needsAssignTotal;
	}
	public void setNeedsAssignTotal(int needsAssignTotal) {
		this.needsAssignTotal = needsAssignTotal;
	}
	public int getAdminClosedTotal() {
		return adminClosedTotal;
	}
	public void setAdminClosedTotal(int adminClosedTotal) {
		this.adminClosedTotal = adminClosedTotal;
	}
	public int getMyAdminClsd() {
		return myAdminClsd;
	}
	public void setMyAdminClsd(int myAdminClsd) {
		this.myAdminClsd = myAdminClsd;
	}
}
