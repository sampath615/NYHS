package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;

/*
 * Author 	: Sampath Kumar
 * Date 	: 6/23/14
 */

public class BadlyMergedStatenosVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long unMergeId;
	private String flaggedBy;
	private String flaggedDate;
	private String requestComments;
	private String statusCode;
	private String stateno;
	private String flaggedEarlier;
	private String reviewedBy;
	private String reviewedDate; 
	private String reviewComments;
	
	
	
	public long getUnMergeId() {
		return unMergeId;
	}

	public void setUnMergeId(long unMergeId) {
		this.unMergeId = unMergeId;
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

	public String getStateno() {
		return stateno;
	}

	public void setStateno(String stateno) {
		this.stateno = stateno;
	}

	public String getFlaggedEarlier() {
		return flaggedEarlier;
	}

	public void setFlaggedEarlier(String flaggedEarlier) {
		this.flaggedEarlier = flaggedEarlier;
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

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	@Override
	public String toString() {
		return "PersonViewVO [unMergeId=" + unMergeId + ", flaggedBy=" + flaggedBy + ", flaggedDate=" + flaggedDate
				+ ", requestComments=" + requestComments + ", stateno=" + stateno + ", flaggedEarlier=" + flaggedEarlier + ", statusCode=" + statusCode + ", reviewComments=" + reviewComments + ", reviewedDate=" + reviewedDate + ", reviewedBy=" + reviewedBy + "]";
	}
}
