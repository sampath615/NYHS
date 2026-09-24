package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

import java.util.Date;
import java.util.List;

public class SubProviderAddressVO {
	
	private long subProviderAddressId;
	private long ProviderId;
	private String countyFips;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String plus4;
    
    private String extension;
    private Date retiredDate;
    private long retiredBy;
    private String tractCensus;
    private String tractSuffixCensus;
    private String latitude;
    private String longitude;
    private List<String> phone;
 
    
    private int totalRawAddressIds;
    private List<RawProviderAddressVO> rawProviderAddresses;
	
    public int getTotalRawAddressIds() {
		return totalRawAddressIds;
	}

	public void setTotalRawAddressIds(int totalRawAddressIds) {
		this.totalRawAddressIds = totalRawAddressIds;
	}

	public long getSubProviderAddressId() {
		return subProviderAddressId;
	}

	public void setSubProviderAddressId(long subProviderAddressId) {
		this.subProviderAddressId = subProviderAddressId;
	}

	public long getProviderId() {
		return ProviderId;
	}

	public void setProviderId(long providerId) {
		ProviderId = providerId;
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

	

	public List<RawProviderAddressVO> getRawProviderAddresses() {
		return rawProviderAddresses;
	}

	public void setRawProviderAddresses(
			List<RawProviderAddressVO> rawProviderAddresses) {
		this.rawProviderAddresses = rawProviderAddresses;
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) { 
		this.phone = phone;
	}

    
}
