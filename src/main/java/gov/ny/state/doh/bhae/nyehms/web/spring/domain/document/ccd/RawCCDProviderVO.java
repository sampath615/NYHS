package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawCCDProviderVO extends RawCCDAddressVO {

	private int uphnId;
	private int ccdDocID;
	private int providerSeqNo;
	private int providerInfoId;
	private RawCCDNameVO providerName;
	private RawCCDAddressVO address;
	private String phone;
	private String providerTypeCode;
	private String providerTypeDesc;
	private String startDateText;
	private String endDateText;
	private String providerAssignedID;
	private String providerAssignedIDAuth;

	/* Below entry for CCD doc Viewer */
	private String providerFullName;
	private String providerAddress;

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

	public int getProviderSeqNo() {
		return providerSeqNo;
	}

	public void setProviderSeqNo(int providerSeqNo) {
		this.providerSeqNo = providerSeqNo;
	}

	public int getProviderInfoId() {
		return providerInfoId;
	}

	public void setProviderInfoId(int providerInfoId) {
		this.providerInfoId = providerInfoId;
	}

	public RawCCDNameVO getProviderName() {
		return providerName;
	}

	public void setProviderName(RawCCDNameVO providerName) {
		this.providerName = providerName;
	}

	public RawCCDAddressVO getAddress() {
		return address;
	}

	public void setAddress(RawCCDAddressVO address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProviderTypeCode() {
		return providerTypeCode;
	}

	public void setProviderTypeCode(String providerTypeCode) {
		this.providerTypeCode = providerTypeCode;
	}

	public String getProviderTypeDesc() {
		return providerTypeDesc;
	}

	public void setProviderTypeDesc(String providerTypeDesc) {
		this.providerTypeDesc = providerTypeDesc;
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

	public String getProviderAssignedID() {
		return providerAssignedID;
	}

	public void setProviderAssignedID(String providerAssignedID) {
		this.providerAssignedID = providerAssignedID;
	}

	public String getProviderAssignedIDAuth() {
		return providerAssignedIDAuth;
	}

	public void setProviderAssignedIDAuth(String providerAssignedIDAuth) {
		this.providerAssignedIDAuth = providerAssignedIDAuth;
	}

	public String getProviderFullName() {
		return providerFullName;
	}

	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}

	public String getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this) + "\n";
	}
}
