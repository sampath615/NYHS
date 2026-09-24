package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.sql.Date;

public class HitErrorVO {

	private int hitERRORID;
	private int uphnTRANXINID;
	private String payloadCLEARTEXT;
	private String errorMESSAGE;
	private Date createDATETIME;
	private String recordType;
	private String comments;
	private String repriseFlag;
	
	
	
	public String getRepriseFlag() {
		return repriseFlag;
	}
	public void setRepriseFlag(String repriseFlag) {
		this.repriseFlag = repriseFlag;
	}
							
	public int getHitERRORID() {
		return hitERRORID;
	}
	public void setHitERRORID(int hitERRORID) {
		this.hitERRORID = hitERRORID;
	}
	public int getUphnTRANXINID() {
		return uphnTRANXINID;
	}
	public void setUphnTRANXINID(int uphnTRANXINID) {
		this.uphnTRANXINID = uphnTRANXINID;
	}
	public String getPayloadCLEARTEXT() {
		return payloadCLEARTEXT;
	}
	public void setPayloadCLEARTEXT(String payloadCLEARTEXT) {
		this.payloadCLEARTEXT = payloadCLEARTEXT;
	}
	public String getErrorMESSAGE() {
		return errorMESSAGE;
	}
	public void setErrorMESSAGE(String errorMESSAGE) {
		this.errorMESSAGE = errorMESSAGE;
	}
	public Date getCreateDATETIME() {
		return createDATETIME;
	}
	public void setCreateDATETIME(Date createDATETIME) {
		this.createDATETIME = createDATETIME;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
