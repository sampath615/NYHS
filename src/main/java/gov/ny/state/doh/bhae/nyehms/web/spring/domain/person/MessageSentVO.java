package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.sql.Date;
import java.util.List;

public class MessageSentVO {

	private Date MESSAGE_ENTERED_DATE;
	private Date MESSAGE_RECEIVED_DATE;
	private Integer CDESSCASEKEY;
	private String CDESSDISMISSED;
	private Integer DOCUMENT_ID;
	private Integer TRIGGER_DOC_ID;
	private String TRIGGER_MSG_DESC;
	private String TRIGGER_MSG_TYPE;
	private String REVIEW_UID;
	private String REVIEW_COMMENTS;
	private String REVIEW_DATE;
	private String PROCESS_IND;
	private String TRIGGER_BY;
	private String LAFT_REASON;
	private String LAFT_COMMENTS;
	
	private List<PSMessageCommentsVO> reviewComments;
	
	
	
	public String getTRIGGER_MSG_TYPE() {
		return TRIGGER_MSG_TYPE;
	}
	public void setTRIGGER_MSG_TYPE(String tRIGGER_MSG_TYPE) {
		TRIGGER_MSG_TYPE = tRIGGER_MSG_TYPE;
	}
	public Date getMESSAGE_ENTERED_DATE() {
		return MESSAGE_ENTERED_DATE;
	}
	public void setMESSAGE_ENTERED_DATE(Date mESSAGE_ENTERED_DATE) {
		MESSAGE_ENTERED_DATE = mESSAGE_ENTERED_DATE;
	}
	public Date getMESSAGE_RECEIVED_DATE() {
		return MESSAGE_RECEIVED_DATE;
	}
	public void setMESSAGE_RECEIVED_DATE(Date mESSAGE_RECEIVED_DATE) {
		MESSAGE_RECEIVED_DATE = mESSAGE_RECEIVED_DATE;
	}
	public Integer getCDESSCASEKEY() {
		return CDESSCASEKEY;
	}
	public void setCDESSCASEKEY(Integer cDESSCASEKEY) {
		CDESSCASEKEY = cDESSCASEKEY;
	}
	public String getCDESSDISMISSED() {
		return CDESSDISMISSED;
	}
	public void setCDESSDISMISSED(String cDESSDISMISSED) {
		CDESSDISMISSED = cDESSDISMISSED;
	}
	public Integer getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(Integer dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	public Integer getTRIGGER_DOC_ID() {
		return TRIGGER_DOC_ID;
	}
	public void setTRIGGER_DOC_ID(Integer tRIGGER_DOC_ID) {
		TRIGGER_DOC_ID = tRIGGER_DOC_ID;
	}
	public String getTRIGGER_MSG_DESC() {
		return TRIGGER_MSG_DESC;
	}
	public void setTRIGGER_MSG_DESC(String tRIGGER_MSG_DESC) {
		TRIGGER_MSG_DESC = tRIGGER_MSG_DESC;
	}
	public String getREVIEW_UID() {
		return REVIEW_UID;
	}
	public void setREVIEW_UID(String rEVIEW_UID) {
		REVIEW_UID = rEVIEW_UID;
	}
	public String getREVIEW_COMMENTS() {
		return REVIEW_COMMENTS;
	}
	public void setREVIEW_COMMENTS(String rEVIEW_COMMENTS) {
		REVIEW_COMMENTS = rEVIEW_COMMENTS;
	}
	public String getREVIEW_DATE() {
		return REVIEW_DATE;
	}
	public void setREVIEW_DATE(String rEVIEW_DATE) {
		REVIEW_DATE = rEVIEW_DATE;
	}
	public String getPROCESS_IND() {
		return PROCESS_IND;
	}
	public void setPROCESS_IND(String pROCESS_IND) {
		PROCESS_IND = pROCESS_IND;
	}
	public List<PSMessageCommentsVO> getReviewComments() {
		return reviewComments;
	}
	public void setReviewComments(List<PSMessageCommentsVO> reviewComments) {
		this.reviewComments = reviewComments;
	}
	public String getTRIGGER_BY() {
		return TRIGGER_BY;
	}
	public void setTRIGGER_BY(String tRIGGER_BY) {
		TRIGGER_BY = tRIGGER_BY;
	}
	
	public String getLAFT_REASON() {
		return LAFT_REASON;
	}
	public void setLAFT_REASON(String lAFT_REASON) {
		LAFT_REASON = lAFT_REASON;
	}
	public String getLAFT_COMMENTS() {
		return LAFT_COMMENTS;
	}
	public void setLAFT_COMMENTS(String lAFT_COMMENTS) {
		LAFT_COMMENTS = lAFT_COMMENTS;
	}
	
	
	
	
}
