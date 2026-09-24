package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentsListVO {
	//Common
	private String workerId ="";
	private String workerShortName ="";
	private String workerName ="";
	private String workerRegionId ="";
	private String assignmentId ="";
	private String stateNo ="";
	private String assignmentActivityCd ="";
	private String assignmentStatus ="";
	private String createDate ="";
	private String workerAssignedDate ="";
	private String assignAddress ="";
	private String providerFacilityName ="";
	
	private long acrfDocId;
	private long pcrfDocId;
	
	private String lastActivityDate ="";
	private String lastActivityDesc ="";
	private String activityDoneBy ="";

	//Reopen
	private String lastReopenDate ="";
	private String lastClosedDate ="";
	
	//reject
	private String rejectionDate ="";
	private String rejectionReason ="";
	private String comments ="";
	
	//closed, admin closed
	private String closureDate ="";
	private String closureCategoryCd ="";
	private String closureActivity ="";
	
	//all
	private String currentWorker ="";
	private String currentStatus ="";
	private String location;
	private String address;
	private String eventDate;
	
	private String documentId;
	private String documentType;
	private String triggeringDocumentFirstName;
	private String triggeringDocumentLastName;
	private String triggeringDocumentDob;
	private String statenoActiveStatus;
	private String documentStatus;
	private String ahiFlag;
	private String crfDiffs; 
	private String closedBy;
	
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getWorkerShortName() {
		return workerShortName;
	}
	public void setWorkerShortName(String workerShortName) {
		this.workerShortName = workerShortName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerRegionId() {
		return workerRegionId;
	}
	public void setWorkerRegionId(String workerRegionId) {
		this.workerRegionId = workerRegionId;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getStateNo() {
		return stateNo;
	}
	public void setStateNo(String stateNo) {
		this.stateNo = stateNo;
	}
	
	public String getAssignmentActivityCd() {
		return assignmentActivityCd;
	}
	public void setAssignmentActivityCd(String assignmentActivityCd) {
		this.assignmentActivityCd = assignmentActivityCd;
	}
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getWorkerAssignedDate() {
		return workerAssignedDate;
	}
	public void setWorkerAssignedDate(String workerAssignedDate) {
		this.workerAssignedDate = workerAssignedDate;
	}
	public String getAssignAddress() {
		return assignAddress;
	}
	public void setAssignAddress(String assignAddress) {
		this.assignAddress = assignAddress;
	}
	public String getProviderFacilityName() {
		return providerFacilityName;
	}
	public void setProviderFacilityName(String providerFacilityName) {
		this.providerFacilityName = providerFacilityName;
	}
	public long getAcrfDocId() {
		return acrfDocId;
	}
	public void setAcrfDocId(long acrfDocId) {
		this.acrfDocId = acrfDocId;
	}
	public String getLastActivityDate() {
		return lastActivityDate;
	}
	public void setLastActivityDate(String lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}
	public String getLastActivityDesc() {
		return lastActivityDesc;
	}
	public void setLastActivityDesc(String lastActivityDesc) {
		this.lastActivityDesc = lastActivityDesc;
	}
	public String getActivityDoneBy() {
		return activityDoneBy;
	}
	public void setActivityDoneBy(String activityDoneBy) {
		this.activityDoneBy = activityDoneBy;
	}
	public String getLastReopenDate() {
		return lastReopenDate;
	}
	public void setLastReopenDate(String lastReopenDate) {
		this.lastReopenDate = lastReopenDate;
	}
	public String getLastClosedDate() {
		return lastClosedDate;
	}
	public void setLastClosedDate(String lastClosedDate) {
		this.lastClosedDate = lastClosedDate;
	}
	public String getRejectionDate() {
		return rejectionDate;
	}
	public void setRejectionDate(String rejectionDate) {
		this.rejectionDate = rejectionDate;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}
	public String getClosureCategoryCd() {
		return closureCategoryCd;
	}
	public void setClosureCategoryCd(String closureCategoryCd) {
		this.closureCategoryCd = closureCategoryCd;
	}
	public String getClosureActivity() {
		return closureActivity;
	}
	public void setClosureActivity(String closureActivity) {
		this.closureActivity = closureActivity;
	}
	public String getCurrentWorker() {
		return currentWorker;
	}
	public void setCurrentWorker(String currentWorker) {
		this.currentWorker = currentWorker;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getTriggeringDocumentFirstName() {
		return triggeringDocumentFirstName;
	}
	public void setTriggeringDocumentFirstName(String triggeringDocumentFirstName) {
		this.triggeringDocumentFirstName = triggeringDocumentFirstName;
	}
	public String getTriggeringDocumentLastName() {
		return triggeringDocumentLastName;
	}
	public void setTriggeringDocumentLastName(String triggeringDocumentLastName) {
		this.triggeringDocumentLastName = triggeringDocumentLastName;
	}
	public String getTriggeringDocumentDob() {
		return triggeringDocumentDob;
	}
	public void setTriggeringDocumentDob(String triggeringDocumentDob) {
		this.triggeringDocumentDob = triggeringDocumentDob;
	}
	public String getStatenoActiveStatus() {
		return statenoActiveStatus;
	}
	public void setStatenoActiveStatus(String statenoActiveStatus) {
		this.statenoActiveStatus = statenoActiveStatus;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getAhiFlag() {
		return ahiFlag;
	}
	public void setAhiFlag(String ahiFlag) {
		this.ahiFlag = ahiFlag;
	}
	public long getPcrfDocId() {
		return pcrfDocId;
	}
	public void setPcrfDocId(long pcrfDocId) {
		this.pcrfDocId = pcrfDocId;
	}
	public String getCrfDiffs() {
		return crfDiffs;
	}
	public void setCrfDiffs(String crfDiffs) {
		this.crfDiffs = crfDiffs;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}
	
	
}
