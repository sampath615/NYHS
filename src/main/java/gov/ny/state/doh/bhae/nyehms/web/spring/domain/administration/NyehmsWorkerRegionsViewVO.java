package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

import java.util.List;

public class NyehmsWorkerRegionsViewVO {
	
	private long workerRegionId;
	private long workerId;
	private Integer roleCode;
	private String roleDescription;
	private String regionCode;
	private String regionDescription;
	private String fullName;
	private String firstName;
	private String lastName;
	private String middleName;
	private String legacyUid;
	private String createdDate;
	private String endDate;
	private String wrStartDate;
	private String wrEndDate;
	private String status;
	private String macAddress;
	private String effectiveStartDate;
	private String lastConfidentialityAndSecurityTrainingDate;
	private String emailAddress;
	private String macStartDate;
	private String lastLoginDate;
	private String isLocked;
	private List<Long> permissions;
	private String userTypeCode;
	private String userTypeDesc;
	private String vpnAccess;
	private String vpnApproveDate;
	private String telecommute;
	private String telecommuteTrainingDate;
	private String userFacilityGroupCode;	
	private String userFacilityGroupDesc;	
	
	private String bureauStartDate;
	private String positionStartDate;
	private String pcAccessAtLocation;
	private String bureauName;
	private String regOffice;
	private String userComments;
	private String remoteTerminalAccess;
	private String tlcmmtTrngAtstn;
	private String cnfSecTrngAtstn;
	private String tlcmmtTrngBy;
	private String cnfSecTrngBy;
	
	public List<Long> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Long> permissions) {
		this.permissions = permissions;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getMacStartDate() {
		return macStartDate;
	}
	public void setMacStartDate(String macStartDate) {
		this.macStartDate = macStartDate;
	}
	
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getLastConfidentialityAndSecurityTrainingDate() {
		return lastConfidentialityAndSecurityTrainingDate;
	}
	public void setLastConfidentialityAndSecurityTrainingDate(
			String lastConfidentialityAndSecurityTrainingDate) {
		this.lastConfidentialityAndSecurityTrainingDate = lastConfidentialityAndSecurityTrainingDate;
	}
	public String getWrStartDate() {
		return wrStartDate;
	}
	public void setWrStartDate(String wrStartDate) {
		this.wrStartDate = wrStartDate;
	}
	public String getWrEndDate() {
		return wrEndDate;
	}
	public void setWrEndDate(String wrEndDate) {
		this.wrEndDate = wrEndDate;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public String getEffectiveStartDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(effectiveStartDate,"M/dd/yyyy");
	}
	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public Integer getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionDescription() {
		return regionDescription;
	}
	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLegacyUid() {
		return legacyUid;
	}
	public void setLegacyUid(String legacyUid) {
		this.legacyUid = legacyUid;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getEndDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(endDate,"M/dd/yyyy");
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public String getUserTypeDesc() {
		return userTypeDesc;
	}
	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
	}
	public String getVpnAccess() {
		return vpnAccess;
	}
	public void setVpnAccess(String vpnAccess) {
		this.vpnAccess = vpnAccess;
	}
	public String getVpnApproveDate() {
		return vpnApproveDate;
	}
	public void setVpnApproveDate(String vpnApproveDate) {
		this.vpnApproveDate = vpnApproveDate;
	}
	public String getTelecommute() {
		return telecommute;
	}
	public void setTelecommute(String telecommute) {
		this.telecommute = telecommute;
	}
	public String getTelecommuteTrainingDate() {
		return telecommuteTrainingDate;
	}
	public void setTelecommuteTrainingDate(String telecommuteTrainingDate) {
		this.telecommuteTrainingDate = telecommuteTrainingDate;
	}
	public String getUserFacilityGroupCode() {
		return userFacilityGroupCode;
	}
	public void setUserFacilityGroupCode(String userFacilityGroupCode) {
		this.userFacilityGroupCode = userFacilityGroupCode;
	}
	public String getUserFacilityGroupDesc() {
		return userFacilityGroupDesc;
	}
	public void setUserFacilityGroupDesc(String userFacilityGroupDesc) {
		this.userFacilityGroupDesc = userFacilityGroupDesc;
	}
	public String getBureauStartDate() {
		return bureauStartDate;
	}
	public void setBureauStartDate(String bureauStartDate) {
		this.bureauStartDate = bureauStartDate;
	}
	public String getPositionStartDate() {
		return positionStartDate;
	}
	public void setPositionStartDate(String positionStartDate) {
		this.positionStartDate = positionStartDate;
	}
	public String getPcAccessAtLocation() {
		return pcAccessAtLocation;
	}
	public void setPcAccessAtLocation(String pcAccessAtLocation) {
		this.pcAccessAtLocation = pcAccessAtLocation;
	}
	public String getBureauName() {
		return bureauName;
	}
	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}
	
	public String getRegOffice() {
		return regOffice;
	}
	public void setRegOffice(String regOffice) {
		this.regOffice = regOffice;
	}
	public String getRemoteTerminalAccess() {
		return remoteTerminalAccess;
	}
	public void setRemoteTerminalAccess(String remoteTerminalAccess) {
		this.remoteTerminalAccess = remoteTerminalAccess;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public String getTlcmmtTrngAtstn() {
		return tlcmmtTrngAtstn;
	}
	public void setTlcmmtTrngAtstn(String tlcmmtTrngAtstn) {
		this.tlcmmtTrngAtstn = tlcmmtTrngAtstn;
	}
	public String getCnfSecTrngAtstn() {
		return cnfSecTrngAtstn;
	}
	public void setCnfSecTrngAtstn(String cnfSecTrngAtstn) {
		this.cnfSecTrngAtstn = cnfSecTrngAtstn;
	}
	public String getTlcmmtTrngBy() {
		return tlcmmtTrngBy;
	}
	public void setTlcmmtTrngBy(String tlcmmtTrngBy) {
		this.tlcmmtTrngBy = tlcmmtTrngBy;
	}
	public String getCnfSecTrngBy() {
		return cnfSecTrngBy;
	}
	public void setCnfSecTrngBy(String cnfSecTrngBy) {
		this.cnfSecTrngBy = cnfSecTrngBy;
	}
	
}
