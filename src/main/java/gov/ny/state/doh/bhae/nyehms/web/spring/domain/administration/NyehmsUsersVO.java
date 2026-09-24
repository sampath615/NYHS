package gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;

import java.util.Date;
import java.util.List;

public class NyehmsUsersVO {

	private long workerId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String legacyUid;
	private String lastConfidentialityAndSecurityTrainingDate;
	private String emailAddress;
	private Date lastLoginDate;
	private String vpnAccess;
	private String vpnApproveDate;
	private String telecommute;
	private String telecommuteTrainingDate;
	private String userType;
	private String userFacilityGroupCode;
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
	
	List<UserRegionVO> userRegionDetails;
	
	
	public long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(long workerId) {
		this.workerId = workerId;
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

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	private String isLocked;
	
	public String getLastConfidentialityAndSecurityTrainingDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(lastConfidentialityAndSecurityTrainingDate,"M/dd/yyyy");
	}

	public void setLastConfidentialityAndSecurityTrainingDate(
			String lastConfidentialityAndSecurityTrainingDate) {
		this.lastConfidentialityAndSecurityTrainingDate = lastConfidentialityAndSecurityTrainingDate;
	}
	

	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<UserRegionVO> getUserRegionDetails() {
		return userRegionDetails;
	}

	public void setUserRegionDetails(List<UserRegionVO> userRegionDetails) {
		this.userRegionDetails = userRegionDetails;
	}

	public String getVpnAccess() {
		return vpnAccess;
	}

	public void setVpnAccess(String vpnAccess) {
		this.vpnAccess = vpnAccess;
	}

	public String getVpnApproveDate() {
		return DateUtil.convertStringToTimeStampInCustomFormat(vpnApproveDate,"M/dd/yyyy");
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
		return DateUtil.convertStringToTimeStampInCustomFormat(telecommuteTrainingDate,"M/dd/yyyy");
	}

	public void setTelecommuteTrainingDate(String telecommuteTrainingDate) {
		this.telecommuteTrainingDate = telecommuteTrainingDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserFacilityGroupCode() {
		return userFacilityGroupCode;
	}

	public void setUserFacilityGroupCode(String userFacilityGroupCode) {
		this.userFacilityGroupCode = userFacilityGroupCode;
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
