package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentWorkerRegionsVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int workerRoleCode;
	private String workerRoleDescription="";
	private String workerRegionId="";
	private String workerId="";
	private String regionCode;
	private String workerRegionDescription="";
	
	public int getWorkerRoleCode() {
		return workerRoleCode;
	}
	public void setWorkerRoleCode(int workerRoleCode) {
		this.workerRoleCode = workerRoleCode;
	}
	public String getWorkerRoleDescription() {
		return workerRoleDescription;
	}
	public void setWorkerRoleDescription(String workerRoleDescription) {
		this.workerRoleDescription = workerRoleDescription;
	}
	public String getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(String workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getWorkerRegionDescription() {
		return workerRegionDescription;
	}
	public void setWorkerRegionDescription(String workerRegionDescription) {
		this.workerRegionDescription = workerRegionDescription;
	}
	@Override
	public String toString() {
		return "AssignmentWorkerRegionsVO [workerRegionId=" + workerRegionId + ", workerId=" + workerId
				+ ", workerRegionDescription=" + workerRegionDescription + "]";
	}
	

}
