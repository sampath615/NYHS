package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;

/*
 * Author 	: Sampath Kumar
 * Date 	: 6/23/14
 */

public class FlaggedStatenoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long duplicationId;
	private String flaggedBy;
	private String flaggedDate;
	private String requestComments;
	private String reviewComments;
	private String statusCode;
	private String stateno1;
	private String stateno2;
	private String originCategorieVal;
	private String selectedStNo; 
	private String flaggedEarlier;
	private String reviewedBy;
	private String reviewedDate;
	private String followUp;
	private String stateno1BadFlag;
	private String stateno2BadFlag;
	
	
	
	private String recSearchActionName;
	private String recSearchUnflaggedStateno;
	private String recSearchComments;
	private String recSearchSourceStateno;
	private String recSearchStatenoToReplace;
	private String rejectStatus;
	
	private String nonCaseFlag;
	
	
	
	public String getNonCaseFlag() {
		return nonCaseFlag;
	}

	public void setNonCaseFlag(String nonCaseFlag) {
		this.nonCaseFlag = nonCaseFlag;
	}

	public String getOriginCategorieVal() {
		return originCategorieVal;
	}

	public void setOriginCategorieVal(String originCategorieVal) {
		this.originCategorieVal = originCategorieVal;
	}

	public String getSelectedStNo() {
		return selectedStNo;
	}

	public void setSelectedStNo(String selectedStNo) {
		this.selectedStNo = selectedStNo;
	}

	public long getDuplicationId() {
		return duplicationId;
	}

	public void setDuplicationId(long duplicationId) {
		this.duplicationId = duplicationId;
	}

	public String getFlaggedBy() {
		return flaggedBy;
	}

	public void setFlaggedBy(String flaggedBy) {
		this.flaggedBy = flaggedBy;
	}

	public String getFlaggedDate() {
		return flaggedDate;
	}

	public void setFlaggedDate(String flaggedDate) {
		this.flaggedDate = flaggedDate;
	}

	public String getRequestComments() {
		return requestComments;
	}

	public void setRequestComments(String requestComments) {
		this.requestComments = requestComments;
	}

	public String getStateno1() {
		return stateno1;
	}

	public void setStateno1(String stateno1) {
		this.stateno1 = stateno1;
	}

	public String getStateno2() {
		return stateno2;
	}

	public void setStateno2(String stateno2) {
		this.stateno2 = stateno2;
	}

	public String getFlaggedEarlier() {
		return flaggedEarlier;
	}

	public void setFlaggedEarlier(String flaggedEarlier) {
		this.flaggedEarlier = flaggedEarlier;
	}
	
	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(String reviewedDate) {
		this.reviewedDate = reviewedDate;
	}
	
	

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	

	public String getStateno1BadFlag() {
		return stateno1BadFlag;
	}

	public void setStateno1BadFlag(String stateno1BadFlag) {
		this.stateno1BadFlag = stateno1BadFlag;
	}

	public String getStateno2BadFlag() {
		return stateno2BadFlag;
	}

	public void setStateno2BadFlag(String stateno2BadFlag) {
		this.stateno2BadFlag = stateno2BadFlag;
	}
	
	

	public String getRecSearchActionName() {
		return recSearchActionName;
	}

	public void setRecSearchActionName(String recSearchActionName) {
		this.recSearchActionName = recSearchActionName;
	}

	public String getRecSearchUnflaggedStateno() {
		return recSearchUnflaggedStateno;
	}

	public void setRecSearchUnflaggedStateno(String recSearchUnflaggedStateno) {
		this.recSearchUnflaggedStateno = recSearchUnflaggedStateno;
	}

	public String getRecSearchComments() {
		return recSearchComments;
	}

	public void setRecSearchComments(String recSearchComments) {
		this.recSearchComments = recSearchComments;
	}

	public String getRecSearchSourceStateno() {
		return recSearchSourceStateno;
	}

	public void setRecSearchSourceStateno(String recSearchSourceStateno) {
		this.recSearchSourceStateno = recSearchSourceStateno;
	}

	public String getRecSearchStatenoToReplace() {
		return recSearchStatenoToReplace;
	}

	public void setRecSearchStatenoToReplace(String recSearchStatenoToReplace) {
		this.recSearchStatenoToReplace = recSearchStatenoToReplace;
	}
	
	

	public String getRejectStatus() {
		return rejectStatus;
	}

	public void setRejectStatus(String rejectStatus) {
		this.rejectStatus = rejectStatus;
	}

	@Override
	public String toString() {
		return "PersonViewVO [duplicationId=" + duplicationId + ", flaggedBy=" + flaggedBy + ", flaggedDate=" + flaggedDate
				+ ", requestComments=" + requestComments + ", stateno1=" + stateno1 + ", stateno2=" + stateno2
				+ ", originCategorieVal=" + originCategorieVal + ", mergedStateNo=" + selectedStNo + ", flaggedEarlier=" + flaggedEarlier + ", reviewComments=" + reviewComments + ", statusCode=" + statusCode + ", reviewedBy=" + reviewedBy + ", reviewedDate=" + reviewedDate + "]";
	}
}
