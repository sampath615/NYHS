package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

import java.util.Date;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

public class SubProviderAndAddressVO {
	
	
	private long providerId;
	private String providerFname;
	private String providerMname="";
	private String providerLname;
	private String providerSuffix = "";
	private long subProviderAddressId;
	private int rawProviderAddressId;
	private String countyFips;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String plus4 = "";
    private String phone;
    private String extension="";
    private Date retiredDate;
    private long retiredBy;
    private String tractCensus;
    private String tractSuffixCensus;
    private String latitude;
    private String longitude;
    private String providerNPI="";
    private String providerSpeciality="";
    private String fromNPIRegistry="";
    
    private int totalSubProviderAddress;
    
   
	public String getProviderFullName() {
		return providerLname +","+ providerFname + " " +providerMname ;
	}
    
	public int getTotalSubProviderAddress() {
		return totalSubProviderAddress;
	}
	public void setTotalSubProviderAddress(int totalSubProviderAddress) {
		this.totalSubProviderAddress = totalSubProviderAddress;
	}
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public String getProviderFname() {
		return providerFname;
	}
	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}
	public String getProviderMname() {
		return providerMname;
	}
	public void setProviderMname(String providerMname) {
		this.providerMname = providerMname;
	}
	public String getProviderLname() {
		return providerLname;
	}
	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}
	public String getProviderSuffix() {
		return providerSuffix;
	}
	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}
	public long getSubProviderAddressId() {
		return subProviderAddressId;
	}
	public void setSubProviderAddressId(long subProviderAddressId) {
		this.subProviderAddressId = subProviderAddressId;
	}
	public String getCountyFips() {
		return countyFips;
	}
	public void setCountyFips(String countyFips) {
		this.countyFips = countyFips;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = CommonUtil.toPhoneSearchPattern(phone);
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Date getRetiredDate() {
		return retiredDate;
	}
	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}
	public long getRetiredBy() {
		return retiredBy;
	}
	public void setRetiredBy(long retiredBy) {
		this.retiredBy = retiredBy;
	}
	public String getTractCensus() {
		return tractCensus;
	}
	public void setTractCensus(String tractCensus) {
		this.tractCensus = tractCensus;
	}
	public String getTractSuffixCensus() {
		return tractSuffixCensus;
	}
	public void setTractSuffixCensus(String tractSuffixCensus) {
		this.tractSuffixCensus = tractSuffixCensus;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getProviderNPI() {
		return providerNPI;
	}

	public void setProviderNPI(String providerNPI) {
		this.providerNPI = providerNPI;
	}

	public int getRawProviderAddressId() {
		return rawProviderAddressId;
	}

	public void setRawProviderAddressId(int rawProviderAddressId) {
		this.rawProviderAddressId = rawProviderAddressId;
	}

	public String getFromNPIRegistry() {
		return fromNPIRegistry;
	}

	public void setFromNPIRegistry(String fromNPIRegistry) {
		this.fromNPIRegistry = fromNPIRegistry;
	}

	public String getProviderSpeciality() {
		return providerSpeciality;
	}

	public void setProviderSpeciality(String providerSpeciality) {
		this.providerSpeciality = providerSpeciality;
	}
	
	
}
