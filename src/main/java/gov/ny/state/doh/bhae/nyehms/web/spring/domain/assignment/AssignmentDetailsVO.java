package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

/**
 * This object is used to hold the Assignment details, Person docs and Assignment activities
 * 
 *  @author ska03
 */


import java.util.List;

public class AssignmentDetailsVO {
	
	private long assignmentId;
	private int supervisorRegionId;
	private String stateno = "";
	private String currentAssignmentStatus = "";
	private String assignmentStatusDesc = "";
	private String workerName = "";
	private String regionDescription = "";
	private String supervisorName = "";
	private String assignedDate = "";
	private String missingVariables = "";
	private long crfDocId;
	private String closedDate = "";
	private String closureStatus = "";
	private String closureCategory = "";
	private String crfDateCompleted;
	private String ahiFlag;
	private Integer ahiDocId;
	private String crfDocType;

	private List<Integer> surveillanceAssignmentDocs;
	private List<AssignmentActivityVO> assignmentActivityVOList;
	
	private String reabWorkerName = ""; 
	private String reabSupervisorName = "";
	private String reabAssignedDate = "";
	private String reabCurrentStatus = "";
	private String reabCurrentStatusDesc = "";
	private String reabCurrentActivity = "";
	private String reabCurrentActivityDesc = "";
	private long reabCrfDocId;
	private String reabCrfCompletedDate = "";
	private String reabSelectedActivity = "";
	private String reabstract = "";
	private String reabCrfDocType;
	private String reabClosureCategory = "";
	private String eharsImportDate;
	private boolean crfDataEntry = true;
	private String reabReviewBy = "";
	private String reabReviewDate = "";
	private String reabReviewComments = "";
	
	private String closureCategoryActivityCode = "";
	private String crfRequired = "N";
	
	private String ahiReviewed;
	
	public long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getMissingVariables() {
		return missingVariables;
	}
	public void setMissingVariables(String missingVariables) {
		this.missingVariables = missingVariables;
	}
	public long getCrfDocId() {
		return crfDocId;
	}
	public void setCrfDocId(long crfDocId) {
		this.crfDocId = crfDocId;
	}
	public String getRegionDescription() {
		return regionDescription;
	}
	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	public String getClosureStatus() {
		return closureStatus;
	}
	public void setClosureStatus(String closureStatus) {
		this.closureStatus = closureStatus;
	}
	public String getClosureCategory() {
		return closureCategory;
	}
	public void setClosureCategory(String closureCategory) {
		this.closureCategory = closureCategory;
	}
	public String getCurrentAssignmentStatus() {
		return currentAssignmentStatus;
	}
	public void setCurrentAssignmentStatus(String currentAssignmentStatus) {
		this.currentAssignmentStatus = currentAssignmentStatus;
	}
	public String getAssignmentStatusDesc() {
		return assignmentStatusDesc;
	}
	public void setAssignmentStatusDesc(String assignmentStatusDesc) {
		this.assignmentStatusDesc = assignmentStatusDesc;
	}
	public List<Integer> getSurveillanceAssignmentDocs() {
		return surveillanceAssignmentDocs;
	}
	public void setSurveillanceAssignmentDocs(List<Integer> surveillanceAssignmentDocs) {
		this.surveillanceAssignmentDocs = surveillanceAssignmentDocs;
	}
	public List<AssignmentActivityVO> getAssignmentActivityVOList() {
		return assignmentActivityVOList;
	}
	public void setAssignmentActivityVOList(List<AssignmentActivityVO> assignmentActivityVOList) {
		this.assignmentActivityVOList = assignmentActivityVOList;
	}
	public String getCrfDateCompleted() {
		return crfDateCompleted;
	}
	public void setCrfDateCompleted(String crfDateCompleted) {
		this.crfDateCompleted = crfDateCompleted;
	}
	public int getSupervisorRegionId() {
		return supervisorRegionId;
	}
	public void setSupervisorRegionId(int supervisorRegionId) {
		this.supervisorRegionId = supervisorRegionId;
	}
	public String getAhiFlag() {
		return ahiFlag;
	}
	public void setAhiFlag(String ahiFlag) {
		this.ahiFlag = ahiFlag;
	}
	public Integer getAhiDocId() {
		return ahiDocId;
	}
	public void setAhiDocId(Integer ahiDocId) {
		this.ahiDocId = ahiDocId;
	}
	public String getCrfDocType() {
		return crfDocType;
	}
	public void setCrfDocType(String crfDocType) {
		this.crfDocType = crfDocType;
	}
	public String getReabWorkerName() {
		return reabWorkerName;
	}
	public void setReabWorkerName(String reabWorkerName) {
		this.reabWorkerName = reabWorkerName;
	}
	public String getReabSupervisorName() {
		return reabSupervisorName;
	}
	public void setReabSupervisorName(String reabSupervisorName) {
		this.reabSupervisorName = reabSupervisorName;
	}
	public String getReabAssignedDate() {
		return reabAssignedDate;
	}
	public void setReabAssignedDate(String reabAssignedDate) {
		this.reabAssignedDate = reabAssignedDate;
	}
	public String getReabCurrentStatus() {
		return reabCurrentStatus;
	}
	public void setReabCurrentStatus(String reabCurrentStatus) {
		this.reabCurrentStatus = reabCurrentStatus;
	}
	public String getReabCurrentStatusDesc() {
		return reabCurrentStatusDesc;
	}
	public void setReabCurrentStatusDesc(String reabCurrentStatusDesc) {
		this.reabCurrentStatusDesc = reabCurrentStatusDesc;
	}
	public String getReabCurrentActivity() {
		return reabCurrentActivity;
	}
	public void setReabCurrentActivity(String reabCurrentActivity) {
		this.reabCurrentActivity = reabCurrentActivity;
	}
	public String getReabCurrentActivityDesc() {
		return reabCurrentActivityDesc;
	}
	public void setReabCurrentActivityDesc(String reabCurrentActivityDesc) {
		this.reabCurrentActivityDesc = reabCurrentActivityDesc;
	}
	public long getReabCrfDocId() {
		return reabCrfDocId;
	}
	public void setReabCrfDocId(long reabCrfDocId) {
		this.reabCrfDocId = reabCrfDocId;
	}
	public String getReabCrfCompletedDate() {
		return reabCrfCompletedDate;
	}
	public void setReabCrfCompletedDate(String reabCrfCompletedDate) {
		this.reabCrfCompletedDate = reabCrfCompletedDate;
	}
	public String getReabSelectedActivity() {
		return reabSelectedActivity;
	}
	public void setReabSelectedActivity(String reabSelectedActivity) {
		this.reabSelectedActivity = reabSelectedActivity;
	}
	public String getReabstract() {
		return reabstract;
	}
	public void setReabstract(String reabstract) {
		this.reabstract = reabstract;
	}
	public String getReabCrfDocType() {
		return reabCrfDocType;
	}
	public void setReabCrfDocType(String reabCrfDocType) {
		this.reabCrfDocType = reabCrfDocType;
	}
	public String getReabClosureCategory() {
		return reabClosureCategory;
	}
	public void setReabClosureCategory(String reabClosureCategory) {
		this.reabClosureCategory = reabClosureCategory;
	}
	public String getEharsImportDate() {
		return eharsImportDate;
	}
	public void setEharsImportDate(String eharsImportDate) {
		this.eharsImportDate = eharsImportDate;
	}
	public boolean isCrfDataEntry() {
		return crfDataEntry;
	}
	public void setCrfDataEntry(boolean crfDataEntry) {
		this.crfDataEntry = crfDataEntry;
	}
	public String getReabReviewBy() {
		return reabReviewBy;
	}
	public void setReabReviewBy(String reabReviewBy) {
		this.reabReviewBy = reabReviewBy;
	}
	public String getReabReviewDate() {
		return reabReviewDate;
	}
	public void setReabReviewDate(String reabReviewDate) {
		this.reabReviewDate = reabReviewDate;
	}
	public String getReabReviewComments() {
		return reabReviewComments;
	}
	public void setReabReviewComments(String reabReviewComments) {
		this.reabReviewComments = reabReviewComments;
	}
	public String getClosureCategoryActivityCode() {
		return closureCategoryActivityCode;
	}
	public void setClosureCategoryActivityCode(String closureCategoryActivityCode) {
		this.closureCategoryActivityCode = closureCategoryActivityCode;
	}
	public String getCrfRequired() {
		return crfRequired;
	}
	public void setCrfRequired(String crfRequired) {
		this.crfRequired = crfRequired;
	}
	public String getAhiReviewed() {
		return ahiReviewed;
	}
	public void setAhiReviewed(String ahiReviewed) {
		this.ahiReviewed = ahiReviewed;
	}
	
}
