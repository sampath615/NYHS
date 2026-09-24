package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

public class RawProviderAddressVO {
	
    private long rawProviderAddressId;
    private long subProviderAddressId;
    private String countyFips;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String plus4;
    private String phone;
    private String rawProviderFname;
    private String rawProviderMname;
    private String rawProviderLname;
    private String rawProviderSuffix;
    private String autoAssignFuture;
    private String rawProviderFullName;
    private String rawProviderNPI;
    
	
    
    

    public String getRawProviderNPI() {
		return rawProviderNPI;
	}

	public void setRawProviderNPI(String rawProviderNPI) {
		this.rawProviderNPI = rawProviderNPI;
	}

	public String getRawProviderFullName() {
		return rawProviderFullName;
	}

	public void setRawProviderFullName(String fullName) {
		this.rawProviderFullName = fullName;
	}

	public long getRawProviderAddressId() {
        return rawProviderAddressId;
    }

    public void setRawProviderAddressId(long rawProviderAddressId) {
        this.rawProviderAddressId = rawProviderAddressId;
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
        this.countyFips = countyFips == null ? null : countyFips.trim();
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
    	
    	if("xxx-xxx-xxxx".equals(phone)){
			phone = "";
		}
		else if(phone != null && (phone.indexOf('x') != -1 || phone.indexOf('-') != -1)){
			phone = phone.replaceAll("-", "").replaceAll("x", "?").replaceAll("X", "?");
		}
    	
        this.phone = phone;
    }

    public String getRawProviderFname() {
        return rawProviderFname;
    }

    public void setRawProviderFname(String rawProviderFname) {
        this.rawProviderFname = rawProviderFname == null ? null : rawProviderFname.trim();
    }

    public String getRawProviderMname() {
        return rawProviderMname;
    }

    public void setRawProviderMname(String rawProviderMname) {
        this.rawProviderMname = rawProviderMname == null ? null : rawProviderMname.trim();
    }

    public String getRawProviderLname() {
        return rawProviderLname;
    }

    public void setRawProviderLname(String rawProviderLname) {
        this.rawProviderLname = rawProviderLname == null ? null : rawProviderLname.trim();
    }

    public String getRawProviderSuffix() {
        return rawProviderSuffix;
    }

    public void setRawProviderSuffix(String rawProviderSuffix) {
        this.rawProviderSuffix = rawProviderSuffix == null ? null : rawProviderSuffix.trim();
    }

    public String getAutoAssignFuture() {
        return autoAssignFuture;
    }

    public void setAutoAssignFuture(String autoAssignFuture) {
        this.autoAssignFuture = autoAssignFuture == null ? null : autoAssignFuture.trim();
    }
  
}