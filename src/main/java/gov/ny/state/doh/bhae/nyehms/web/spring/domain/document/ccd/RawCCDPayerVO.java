package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDPayerVO {
	
	private int uphnId;
	private int ccdDocID;
	private int payerSeqNo;
	private int payerId;
	private String policyId;
	private String policyName; //Single string, no first/middle/last
	private RawCCDAddressVO policyAddress;
	private String policyPhone;
	private String memberId;
	private RawCCDNameVO memberName;
	private RawCCDAddressVO memberAddress; 
	private String memberDobText;
	private String memberPhone;
	private String holderId;
	private RawCCDNameVO holderName;
	private RawCCDAddressVO holderAddress;
	private String holderDobText;
	private String holderPhone;
	private String startDateText;
	private String endDateText;
	
	/*Only for CCD DOC VIEWER*/
	
	private String memberFullName;

	
	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public int getCcdDocID() {
		return ccdDocID;
	}
	public void setCcdDocID(int ccdDocID) {
		this.ccdDocID = ccdDocID;
	}
	public int getPayerSeqNo() {
		return payerSeqNo;
	}
	public void setPayerSeqNo(int payerSeqNo) {
		this.payerSeqNo = payerSeqNo;
	}
	public int getPayerId() {
		return payerId;
	}
	public void setPayerId(int payerId) {
		this.payerId = payerId;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public RawCCDAddressVO getPolicyAddress() {
		return policyAddress;
	}
	public void setPolicyAddress(RawCCDAddressVO policyAddress) {
		this.policyAddress = policyAddress;
	}
	public String getPolicyPhone() {
		return policyPhone;
	}
	public void setPolicyPhone(String policyPhone) {
		this.policyPhone = policyPhone;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public RawCCDNameVO getMemberName() {
		return memberName;
	}
	public void setMemberName(RawCCDNameVO memberName) {
		this.memberName = memberName;
	}
	public RawCCDAddressVO getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(RawCCDAddressVO memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberDobText() {
		return memberDobText;
	}
	public void setMemberDobText(String memberDobText) {
		this.memberDobText = memberDobText;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getHolderId() {
		return holderId;
	}
	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}
	public RawCCDNameVO getHolderName() {
		return holderName;
	}
	public void setHolderName(RawCCDNameVO holderName) {
		this.holderName = holderName;
	}
	public RawCCDAddressVO getHolderAddress() {
		return holderAddress;
	}
	public void setHolderAddress(RawCCDAddressVO holderAddress) {
		this.holderAddress = holderAddress;
	}
	public String getHolderDobText() {
		return holderDobText;
	}
	public void setHolderDobText(String holderDobText) {
		this.holderDobText = holderDobText;
	}
	public String getHolderPhone() {
		return holderPhone;
	}
	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}
	public String getStartDateText() {
		return startDateText;
	}
	public void setStartDateText(String startDateText) {
		this.startDateText = startDateText;
	}
	public String getEndDateText() {
		return endDateText;
	}
	public void setEndDateText(String endDateText) {
		this.endDateText = endDateText;
	}
	
	public String getMemberFullName() {
		return memberFullName;
	}
	public void setMemberFullName(String memberFullName) {
		this.memberFullName = memberFullName;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RAW_CCD_PAYER Document ID: " + ccdDocID + "\n");
		sb.append("PAYER_ID : " + payerId + "\n");
		sb.append("Policy ID: " + policyId);
		return sb.toString();
	}
}
