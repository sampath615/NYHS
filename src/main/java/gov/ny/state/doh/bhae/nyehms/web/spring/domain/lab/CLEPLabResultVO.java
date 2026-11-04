package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class CLEPLabResultVO {
	
	private int surveyNumber;
	private String userEnteredPfi;
	private String labDateOfSurvey;
	private String labSurveyorName;
	private String faxReceivedDate;
	private String insBasedHivTest;
	private String rptNysDohByEclrs;
	private String labRefLabForTest;
	private String clepUserId;
	
	private List<CLEPLabResultAccessionVO> clepLabResultAccessionList;
	
	private String pfiLabClia;
	private String pfiLabName;
	private String pfiLabAddress;
	private String pfiLabCity;
	private String pfiLabState;
	private String pfiLabZip;
	private String pfiLabCountry;
	private String pfiLabLaftStaff;
	private String pfiLabApprovalStatus;
	
	private String reviewCompleted;
	private String reviewCompletedBy;
	private String reviewCompletedDate;
	private boolean reviewCompletedClicked;
	
	private String labRefPFI;
	private String refLabName;
	private String refLabCity;
	private String refLabState;
	
	private String recruitAssessOutcome;
	private String recruitAssessStaffAssign;
	private String recruitAssessDateAssign;
	private String recruitAssessStaffAssignBy;
	private String recruitAssessComments;
	private String recruitAssessDateComplete;
	private String recruitAssessStaffComplete;
	
	public int getSurveyNumber() {
		return surveyNumber;
	}
	public void setSurveyNumber(int surveyNumber) {
		this.surveyNumber = surveyNumber;
	}
	public String getUserEnteredPfi() {
		return userEnteredPfi;
	}
	public void setUserEnteredPfi(String userEnteredPfi) {
		this.userEnteredPfi = userEnteredPfi;
	}
	public String getLabDateOfSurvey() {
		return labDateOfSurvey;
	}
	public void setLabDateOfSurvey(String labDateOfSurvey) {
		this.labDateOfSurvey = labDateOfSurvey;
	}
	public String getLabSurveyorName() {
		return labSurveyorName;
	}
	public void setLabSurveyorName(String labSurveyorName) {
		this.labSurveyorName = labSurveyorName;
	}
	public String getFaxReceivedDate() {
		return faxReceivedDate;
	}
	public void setFaxReceivedDate(String faxReceivedDate) {
		this.faxReceivedDate = faxReceivedDate;
	}
	public String getInsBasedHivTest() {
		return insBasedHivTest;
	}
	public void setInsBasedHivTest(String insBasedHivTest) {
		this.insBasedHivTest = insBasedHivTest;
	}
	public String getRptNysDohByEclrs() {
		return rptNysDohByEclrs;
	}
	public void setRptNysDohByEclrs(String rptNysDohByEclrs) {
		this.rptNysDohByEclrs = rptNysDohByEclrs;
	}
	public String getLabRefLabForTest() {
		return labRefLabForTest;
	}
	public void setLabRefLabForTest(String labRefLabForTest) {
		this.labRefLabForTest = labRefLabForTest;
	}
	public String getLabRefPFI() {
		return labRefPFI;
	}
	public void setLabRefPFI(String labRefPFI) {
		this.labRefPFI = labRefPFI;
	}
	public List<CLEPLabResultAccessionVO> getClepLabResultAccessionList() {
		return clepLabResultAccessionList;
	}
	public void setClepLabResultAccessionList(List<CLEPLabResultAccessionVO> clepLabResultAccessionList) {
		this.clepLabResultAccessionList = clepLabResultAccessionList;
	}
	public String getClepUserId() {
		return clepUserId;
	}
	public void setClepUserId(String clepUserId) {
		this.clepUserId = clepUserId;
	}
	public String getPfiLabClia() {
		return pfiLabClia;
	}
	public void setPfiLabClia(String pfiLabClia) {
		this.pfiLabClia = pfiLabClia;
	}
	public String getPfiLabName() {
		return pfiLabName;
	}
	public void setPfiLabName(String pfiLabName) {
		this.pfiLabName = pfiLabName;
	}
	public String getPfiLabAddress() {
		return pfiLabAddress;
	}
	public void setPfiLabAddress(String pfiLabAddress) {
		this.pfiLabAddress = pfiLabAddress;
	}
	public String getPfiLabCity() {
		return pfiLabCity;
	}
	public void setPfiLabCity(String pfiLabCity) {
		this.pfiLabCity = pfiLabCity;
	}
	public String getPfiLabState() {
		return pfiLabState;
	}
	public void setPfiLabState(String pfiLabState) {
		this.pfiLabState = pfiLabState;
	}
	public String getPfiLabZip() {
		return pfiLabZip;
	}
	public void setPfiLabZip(String pfiLabZip) {
		this.pfiLabZip = pfiLabZip;
	}
	public String getPfiLabCountry() {
		return pfiLabCountry;
	}
	public void setPfiLabCountry(String pfiLabCountry) {
		this.pfiLabCountry = pfiLabCountry;
	}
	public String getRefLabName() {
		return refLabName;
	}
	public void setRefLabName(String refLabName) {
		this.refLabName = refLabName;
	}
	public String getRefLabCity() {
		return refLabCity;
	}
	public void setRefLabCity(String refLabCity) {
		this.refLabCity = refLabCity;
	}
	public String getRefLabState() {
		return refLabState;
	}
	public void setRefLabState(String refLabState) {
		this.refLabState = refLabState;
	}
	public String getPfiLabLaftStaff() {
		return pfiLabLaftStaff;
	}
	public void setPfiLabLaftStaff(String pfiLabLaftStaff) {
		this.pfiLabLaftStaff = pfiLabLaftStaff;
	}
	public String getPfiLabApprovalStatus() {
		return pfiLabApprovalStatus;
	}
	public void setPfiLabApprovalStatus(String pfiLabApprovalStatus) {
		this.pfiLabApprovalStatus = pfiLabApprovalStatus;
	}
	public String getReviewCompleted() {
		return reviewCompleted;
	}
	public void setReviewCompleted(String reviewCompleted) {
		this.reviewCompleted = reviewCompleted;
	}
	public String getReviewCompletedBy() {
		return reviewCompletedBy;
	}
	public void setReviewCompletedBy(String reviewCompletedBy) {
		this.reviewCompletedBy = reviewCompletedBy;
	}
	public String getReviewCompletedDate() {
		return reviewCompletedDate;
	}
	public void setReviewCompletedDate(String reviewCompletedDate) {
		this.reviewCompletedDate = reviewCompletedDate;
	}
	public boolean isReviewCompletedClicked() {
		return reviewCompletedClicked;
	}
	public void setReviewCompletedClicked(boolean reviewCompletedClicked) {
		this.reviewCompletedClicked = reviewCompletedClicked;
	}
	public String getRecruitAssessOutcome() {
		return recruitAssessOutcome;
	}
	public void setRecruitAssessOutcome(String recruitAssessOutcome) {
		this.recruitAssessOutcome = recruitAssessOutcome;
	}
	public String getRecruitAssessStaffAssign() {
		return recruitAssessStaffAssign;
	}
	public void setRecruitAssessStaffAssign(String recruitAssessStaffAssign) {
		this.recruitAssessStaffAssign = recruitAssessStaffAssign;
	}
	public String getRecruitAssessDateAssign() {
		return recruitAssessDateAssign;
	}
	public void setRecruitAssessDateAssign(String recruitAssessDateAssign) {
		this.recruitAssessDateAssign = recruitAssessDateAssign;
	}
	public String getRecruitAssessComments() {
		return recruitAssessComments;
	}
	public void setRecruitAssessComments(String recruitAssessComments) {
		this.recruitAssessComments = recruitAssessComments;
	}
	public String getRecruitAssessDateComplete() {
		return recruitAssessDateComplete;
	}
	public void setRecruitAssessDateComplete(String recruitAssessDateComplete) {
		this.recruitAssessDateComplete = recruitAssessDateComplete;
	}
	public String getRecruitAssessStaffComplete() {
		return recruitAssessStaffComplete;
	}
	public void setRecruitAssessStaffComplete(String recruitAssessStaffComplete) {
		this.recruitAssessStaffComplete = recruitAssessStaffComplete;
	}
	public String getRecruitAssessStaffAssignBy() {
		return recruitAssessStaffAssignBy;
	}
	public void setRecruitAssessStaffAssignBy(String recruitAssessStaffAssignBy) {
		this.recruitAssessStaffAssignBy = recruitAssessStaffAssignBy;
	}
	
}
