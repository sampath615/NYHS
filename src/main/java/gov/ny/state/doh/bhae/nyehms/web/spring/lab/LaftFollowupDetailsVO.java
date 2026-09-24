package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LaftFollowupDetailsVO {
	
	private int followupIdPk;
	private int documentId;
	private String stateno;
	private String reasonId;
	private String reasonDesc;
	private String createUid;
	private String createName;
	private String createDate;
	private String initialComments;
	private String reviewComments;
	private String resolutionId;
	private String resolutionDesc;
	private String resolutionUid;
	private String resolverName;	
	private String resolutionDate;
	private int loginUserId;
	
	public int getFollowupIdPk() {
		return followupIdPk;
	}
	public void setFollowupIdPk(int followupIdPk) {
		this.followupIdPk = followupIdPk;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getInitialComments() {
		return initialComments;
	}
	public void setInitialComments(String initialComments) {
		this.initialComments = initialComments;
	}
	public String getReviewComments() {
		return reviewComments;
	}
	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}
	public String getResolutionId() {
		return resolutionId;
	}
	public void setResolutionId(String resolutionId) {
		this.resolutionId = resolutionId;
	}
	public String getResolverName() {
		return resolverName;
	}
	public void setResolverName(String resolverName) {
		this.resolverName = resolverName;
	}
	public String getResolutionDesc() {
		return resolutionDesc;
	}
	public void setResolutionDesc(String resolutionDesc) {
		this.resolutionDesc = resolutionDesc;
	}
	public String getResolutionUid() {
		return resolutionUid;
	}
	public void setResolutionUid(String resolutionUid) {
		this.resolutionUid = resolutionUid;
	}
	public String getResolutionDate() {
		return resolutionDate;
	}
	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	public int getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}
}
