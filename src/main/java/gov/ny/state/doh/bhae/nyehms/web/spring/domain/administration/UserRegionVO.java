package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

public class UserRegionVO {
	
	
	private long workerRegionId;
	private long workerId;
	private long roleCode;
	private String roleDescription;
	private String regionCode;
	private String regionDescription;
	private String startDate;
	private String endDate;
	private long lastUpdatedby;
	private List<Long> permissions;
	
	public long getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(long workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}
	public long getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(long roleCode) {
		this.roleCode = roleCode;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getStartDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(startDate,"M/dd/yyyy");
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(endDate,"M/dd/yyyy");
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getLastUpdatedby() {
		return lastUpdatedby;
	}
	public void setLastUpdatedby(long lastUpdatedby) {
		this.lastUpdatedby = lastUpdatedby;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public String getRegionDescription() {
		return regionDescription;
	}
	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}
	public List<Long> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Long> permissions) {
		this.permissions = permissions;
	}
}
