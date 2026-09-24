package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawCCDPersonAddressVO {

	private int uphnId;
	private long ccdDocID;
	private int addressSeqNo;
	private long PERSON_ADDRESS_ID;
	private RawCCDAddressVO theCCDAddrObject;
	private String useableStartTime;
	private String useableEndTime;
	// ADDED FOR CCD_DOC_VIEWER_TEST
	private String ccdUse;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String county;
	private String personAddress;

	public int getUphnId() {
		return uphnId;
	}

	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}

	public long getCcdDocID() {
		return ccdDocID;
	}

	public void setCcdDocID(long ccdDocID) {
		this.ccdDocID = ccdDocID;
	}

	public int getAddressSeqNo() {
		return addressSeqNo;
	}

	public void setAddressSeqNo(int addressSeqNo) {
		this.addressSeqNo = addressSeqNo;
	}

	public long getPERSON_ADDRESS_ID() {
		return PERSON_ADDRESS_ID;
	}

	public void setPERSON_ADDRESS_ID(long pERSON_ADDRESS_ID) {
		PERSON_ADDRESS_ID = pERSON_ADDRESS_ID;
	}

	public RawCCDAddressVO getTheCCDAddrObject() {
		return theCCDAddrObject;
	}

	public void setTheCCDAddrObject(RawCCDAddressVO theCCDAddrObject) {
		this.theCCDAddrObject = theCCDAddrObject;
	}

	public String getUseableStartTime() {
		return useableStartTime;
	}

	public void setUseableStartTime(String useableStartTime) {
		this.useableStartTime = useableStartTime;
	}

	public String getUseableEndTime() {
		return useableEndTime;
	}

	public void setUseableEndTime(String useableEndTime) {
		this.useableEndTime = useableEndTime;
	}

	public String getCcdUse() {
		return ccdUse;
	}

	public void setCcdUse(String ccdUse) {
		this.ccdUse = ccdUse;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this) + "\n";
	}
}
