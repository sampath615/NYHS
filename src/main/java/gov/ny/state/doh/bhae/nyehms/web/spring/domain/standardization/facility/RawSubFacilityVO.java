package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility;

import java.util.Date;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

public class RawSubFacilityVO {
    private long rawSubFacilityId;
    private long subFacilityId;
    private String countyFips;
    private String facilityTypeCode;
    private String facilityTypeDesc;
    private String facilityName;
    private String facilityDepartment;
    private String legacyFacilityId;
    private String legacyFacilityType;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String plus4;
    private String phone;
    private String npi;
	private long legacySeqNumAddress;
    private String createUid;
    private Date dateCreated;
    private Date dateEnded;
    private String endUid;
    private long pmRawFacilityId;
    
    public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}
	
    public long getRawSubFacilityId() {
        return rawSubFacilityId;
    }

    public void setRawSubFacilityId(long rawSubFacilityId) {
        this.rawSubFacilityId = rawSubFacilityId;
    }

    public long getSubFacilityId() {
        return subFacilityId;
    }

    public void setSubFacilityId(long subFacilityId) {
        this.subFacilityId = subFacilityId;
    }

    public String getFacilityTypeCode() {
        return facilityTypeCode;
    }

    public String getFacilityTypeDesc() {
		return facilityTypeDesc;
	}

	public void setFacilityTypeDesc(String facilityTypeDesc) {
		this.facilityTypeDesc = facilityTypeDesc;
	}

	public void setFacilityTypeCode(String facilityTypeCode) {
        this.facilityTypeCode = facilityTypeCode == null ? null : facilityTypeCode.trim();
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName == null ? null : facilityName.trim();
    }

    public String getFacilityDepartment() {
        return facilityDepartment;
    }

    public void setFacilityDepartment(String facilityDepartment) {
        this.facilityDepartment = facilityDepartment == null ? null : facilityDepartment.trim();
    }

    public String getLegacyFacilityId() {
        return legacyFacilityId;
    }

    public void setLegacyFacilityId(String legacyFacilityId) {
        this.legacyFacilityId = legacyFacilityId == null ? null : legacyFacilityId.trim();
    }

    public String getLegacyFacilityType() {
        return legacyFacilityType;
    }

    public void setLegacyFacilityType(String legacyFacilityType) {
        this.legacyFacilityType = legacyFacilityType == null ? null : legacyFacilityType.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getPlus4() {
        return plus4;
    }

    public void setPlus4(String plus4) {
        this.plus4 = plus4 == null ? null : plus4.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
    	this.phone = CommonUtil.toPhoneSearchPattern(phone);
    }

    public long getLegacySeqNumAddress() {
        return legacySeqNumAddress;
    }

    public void setLegacySeqNumAddress(long legacySeqNumAddress) {
        this.legacySeqNumAddress = legacySeqNumAddress;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid == null ? null : createUid.trim();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Date dateEnded) {
        this.dateEnded = dateEnded;
    }

    public String getEndUid() {
        return endUid;
    }

    public void setEndUid(String endUid) {
        this.endUid = endUid == null ? null : endUid.trim();
    }

	public long getPmRawFacilityId() {
		return pmRawFacilityId;
	}

	public void setPmRawFacilityId(long pmRawFacilityId) {
		this.pmRawFacilityId = pmRawFacilityId;
	}

	public String getCountyFips() {
		return countyFips;
	}

	public void setCountyFips(String countyFips) {
		this.countyFips = countyFips;
	}
	
	
}