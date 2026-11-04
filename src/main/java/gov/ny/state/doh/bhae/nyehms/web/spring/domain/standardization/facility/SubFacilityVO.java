package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SubFacilityVO implements Comparator<SubFacilityVO>{
    private long subFacilityId;
    private long masterFacilityId;
    private long newSubFacilityId;
    private String facilityTypeCode;
    private String facilityTypeDesc;
	private String facilityNpi;
    private String facilityName;
    private String facilityDepartment;
    private String legacyFacilityId;
    private String legacyFacilityType;
    private String countyFips;
    private String countyName;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String plus4;
    private List<String> phone;
    private String tractCensus;
    private String tractSuffixCensus;
    private String latitude;
    private String longitude;
    private long legacySeqNumAddress;
    private String createUid;
    private Date dateCreated;
    private Date dateEnded;
    private String endUid;
    private int totalRawFacility;
    private List<RawSubFacilityVO> rawFacilities;
    public List<RawSubFacilityVO> getRawFacilities() {
		return rawFacilities;
	}

	public void setRawFacilities(List<RawSubFacilityVO> rawFacilities) {
		this.rawFacilities = rawFacilities;
	}

    public long getSubFacilityId() {
        return subFacilityId;
    }

    public void setSubFacilityId(long subFacilityId) {
        this.subFacilityId = subFacilityId;
    }

    public long getMasterFacilityId() {
        return masterFacilityId;
    }

    public void setMasterFacilityId(long masterFacilityId) {
        this.masterFacilityId = masterFacilityId;
    }

    public long getNewSubFacilityId() {
        return newSubFacilityId;
    }

    public void setNewSubFacilityId(long newSubFacilityId) {
        this.newSubFacilityId = newSubFacilityId;
    }

    public String getFacilityTypeCode() {
        return facilityTypeCode;
    }

    public void setFacilityTypeCode(String facilityTypeCode) {
        this.facilityTypeCode = facilityTypeCode == null ? null : facilityTypeCode.trim();
    }

    public String getFacilityTypeDesc() {
		return facilityTypeDesc;
	}

	public void setFacilityTypeDesc(String facilityTypeDesc) {
		this.facilityTypeDesc = facilityTypeDesc;
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

    public String getCountyFips() {
        return countyFips;
    }

    public void setCountyFips(String countyFips) {
        this.countyFips = countyFips == null ? null : countyFips.trim();
    }

    public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
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

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
	    this.phone = phone;
    }

    public String getTractCensus() {
        return tractCensus;
    }

    public void setTractCensus(String tractCensus) {
        this.tractCensus = tractCensus == null ? null : tractCensus.trim();
    }

    public String getTractSuffixCensus() {
        return tractSuffixCensus;
    }

    public void setTractSuffixCensus(String tractSuffixCensus) {
        this.tractSuffixCensus = tractSuffixCensus == null ? null : tractSuffixCensus.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
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

	public String getFacilityNpi() {
		return facilityNpi;
	}

	public void setFacilityNpi(String facilityNpi) {
		this.facilityNpi = facilityNpi;
	}

	public int getTotalRawFacility() {
		return totalRawFacility;
	}

	public void setTotalRawFacility(int totalRawFacility) {
		this.totalRawFacility = totalRawFacility;
	}
	
	
	/*
	 * Overridden equals method for comparing the instances. The instances are 
	 * compared for equality based on the facility identification value they
	 * hold inside the facilityId instance variable. 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubFacilityVO other = (SubFacilityVO) obj;
		if (subFacilityId != other.subFacilityId)
			return false;
		return true;
	}

	/*
	 * Overridden hash code implementation for this object.
	 * The hash code generated is based on the facility identification 
	 * value each instance hold inside the facilityId instance variable. 
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (subFacilityId ^ (subFacilityId >>> 32));
		return result;
		
	}
	
	@Override
	public int compare(SubFacilityVO subFacilityVOFrom, SubFacilityVO subFacilityVOTo) {
		long fromSubFacilityId = subFacilityVOFrom.subFacilityId;
		if(fromSubFacilityId == -9999){
			return -1;
			
		}else{
			return subFacilityVOFrom.getFacilityName().trim().compareToIgnoreCase(subFacilityVOTo.getFacilityName().trim());
		}
	}
}
