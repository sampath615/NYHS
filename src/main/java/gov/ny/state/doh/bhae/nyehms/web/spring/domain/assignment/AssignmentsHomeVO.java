package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentsHomeVO {
	private String workerRegionId;
	private String assignmentType;
	private String regionCode;
	private int workerRoleCode;
	private int workerId;
	private String fromDate;
	private String todate;
	private String closureCategory;
	private String reabstract;

	public String getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(String workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	public int getWorkerRoleCode() {
		return workerRoleCode;
	}
	public void setWorkerRoleCode(int workerRoleCode) {
		this.workerRoleCode = workerRoleCode;
	}
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getClosureCategory() {
		return closureCategory;
	}
	public void setClosureCategory(String closureCategory) {
		this.closureCategory = closureCategory;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getReabstract() {
		return reabstract;
	}
	public void setReabstract(String reabstract) {
		this.reabstract = reabstract;
	}
	
}
