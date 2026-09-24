package gov.ny.state.doh.bhae.nyehms.web.spring.domain.review;

import java.util.List;

public class AhiAlertTrackingVO {
	
	private int ahiAlertSeqId;
	private String stateno;
	private String ahiFlag;
	private int ahiDocId;
	private String ahiFlagSetBy;
	private String ahiFlagSetDate;
	private String alertSource;
	private String alertSourceDesc;	
	private String prfCollection;
	private String prfCollectionDesc;
	private String prfCollectionDate;
	private String prfFlaggedAhi;
	private String psMsgSent;
	private String psMsgType;
	private String psMsgTypeDesc;
	private String psNotofPotAhi;
	private String psMsgReturned;
	private String followupStepTakenCode;
	private String followupStepTaken;
	private String userNotes;

	private String lastUpdatedBy;
	private String lastUpdatedDate;	
	private String alertCompletedBy;
	private String alertCompletedDate;
	private List<AHIAlertPSMessageVO> psMessageList;
	private String psMsgExist;
	private String ahiAlertStatus;
	private String ahiAlertReviewedDate;	
	private String ahiAlertReviewedBy;
	private int assignmentId;
	private String triggeringDocType;
	
	public int getAhiAlertSeqId() {
		return ahiAlertSeqId;
	}
	public void setAhiAlertSeqId(int ahiAlertSeqId) {
		this.ahiAlertSeqId = ahiAlertSeqId;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getAhiFlag() {
		return ahiFlag;
	}
	public void setAhiFlag(String ahiFlag) {
		this.ahiFlag = ahiFlag;
	}
	public int getAhiDocId() {
		return ahiDocId;
	}
	public void setAhiDocId(int ahiDocId) {
		this.ahiDocId = ahiDocId;
	}
	public String getAhiFlagSetBy() {
		return ahiFlagSetBy;
	}
	public void setAhiFlagSetBy(String ahiFlagSetBy) {
		this.ahiFlagSetBy = ahiFlagSetBy;
	}
	public String getAhiFlagSetDate() {
		return ahiFlagSetDate;
	}
	public void setAhiFlagSetDate(String ahiFlagSetDate) {
		this.ahiFlagSetDate = ahiFlagSetDate;
	}
	public String getAlertSource() {
		return alertSource;
	}
	public void setAlertSource(String alertSource) {
		this.alertSource = alertSource;
	}
	public String getAlertSourceDesc() {
		return alertSourceDesc;
	}
	public void setAlertSourceDesc(String alertSourceDesc) {
		this.alertSourceDesc = alertSourceDesc;
	}
	public String getPrfCollection() {
		return prfCollection;
	}
	public void setPrfCollection(String prfCollection) {
		this.prfCollection = prfCollection;
	}
	public String getPrfCollectionDesc() {
		return prfCollectionDesc;
	}
	public void setPrfCollectionDesc(String prfCollectionDesc) {
		this.prfCollectionDesc = prfCollectionDesc;
	}
	public String getPrfCollectionDate() {
		return prfCollectionDate;
	}
	public void setPrfCollectionDate(String prfCollectionDate) {
		this.prfCollectionDate = prfCollectionDate;
	}
	public String getPrfFlaggedAhi() {
		return prfFlaggedAhi;
	}
	public void setPrfFlaggedAhi(String prfFlaggedAhi) {
		this.prfFlaggedAhi = prfFlaggedAhi;
	}
	public String getPsMsgSent() {
		return psMsgSent;
	}
	public void setPsMsgSent(String psMsgSent) {
		this.psMsgSent = psMsgSent;
	}
	public String getPsMsgType() {
		return psMsgType;
	}
	public void setPsMsgType(String psMsgType) {
		this.psMsgType = psMsgType;
	}
	public String getPsMsgTypeDesc() {
		return psMsgTypeDesc;
	}
	public void setPsMsgTypeDesc(String psMsgTypeDesc) {
		this.psMsgTypeDesc = psMsgTypeDesc;
	}
	public String getPsNotofPotAhi() {
		return psNotofPotAhi;
	}
	public void setPsNotofPotAhi(String psNotofPotAhi) {
		this.psNotofPotAhi = psNotofPotAhi;
	}
	public String getPsMsgReturned() {
		return psMsgReturned;
	}
	public void setPsMsgReturned(String psMsgReturned) {
		this.psMsgReturned = psMsgReturned;
	}
	public String getFollowupStepTaken() {
		return followupStepTaken;
	}
	public void setFollowupStepTaken(String followupStepTaken) {
		this.followupStepTaken = followupStepTaken;
	}
	public String getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getAlertCompletedBy() {
		return alertCompletedBy;
	}
	public void setAlertCompletedBy(String alertCompletedBy) {
		this.alertCompletedBy = alertCompletedBy;
	}
	public String getAlertCompletedDate() {
		return alertCompletedDate;
	}
	public void setAlertCompletedDate(String alertCompletedDate) {
		this.alertCompletedDate = alertCompletedDate;
	}
	public String getFollowupStepTakenCode() {
		return followupStepTakenCode;
	}
	public void setFollowupStepTakenCode(String followupStepTakenCode) {
		this.followupStepTakenCode = followupStepTakenCode;
	}
	public List<AHIAlertPSMessageVO> getPsMessageList() {
		return psMessageList;
	}
	public void setPsMessageList(List<AHIAlertPSMessageVO> psMessageList) {
		this.psMessageList = psMessageList;
	}
	public String getPsMsgExist() {
		return psMsgExist;
	}
	public void setPsMsgExist(String psMsgExist) {
		this.psMsgExist = psMsgExist;
	}
	public String getAhiAlertStatus() {
		return ahiAlertStatus;
	}
	public void setAhiAlertStatus(String ahiAlertStatus) {
		this.ahiAlertStatus = ahiAlertStatus;
	}
	public String getAhiAlertReviewedDate() {
		return ahiAlertReviewedDate;
	}
	public void setAhiAlertReviewedDate(String ahiAlertReviewedDate) {
		this.ahiAlertReviewedDate = ahiAlertReviewedDate;
	}
	public String getAhiAlertReviewedBy() {
		return ahiAlertReviewedBy;
	}
	public void setAhiAlertReviewedBy(String ahiAlertReviewedBy) {
		this.ahiAlertReviewedBy = ahiAlertReviewedBy;
	}
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getTriggeringDocType() {
		return triggeringDocType;
	}
	public void setTriggeringDocType(String triggeringDocType) {
		this.triggeringDocType = triggeringDocType;
	}
	
}
