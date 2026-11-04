package gov.ny.state.doh.bhae.nyehms.web.spring.domain.common;

public class AddressFieldVO {	
	
	private String address="";
	private String city="";
	private String state="";
	private String zip="";
	private String plus4="";
	private String county="";
	private String countyFipsCd="";
	private String country="";
	private String fieldName="";
	private String concatField="";
	
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}	
	public String getCountyFipsCd() {
		return countyFipsCd;
	}
	public void setCountyFipsCd(String countyFipsCd) {
		this.countyFipsCd = countyFipsCd;
	}
	
	@Override
	public String toString() {
		return "AddressDropDownVO [address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + ", plus4="
				+ plus4 + ", county=" + county + ", country=" + country + "]";
	}
	public String getConcatField() {
		return concatField;
	}
	public void setConcatField(String concatField) {
		this.concatField = concatField;
	}
	
}
