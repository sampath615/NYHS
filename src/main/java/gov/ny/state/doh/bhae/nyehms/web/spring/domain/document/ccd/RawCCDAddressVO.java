package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

/**
 * Utility class for any <addr> element in the CCD
 * <ul>
 * <li>/ClinicalDocument/recordTarget/patientRole/addr (Patient)</li>
 * <li>/ClinicalDocument/documentationOf/serviceEvent/performer/assignedEntity/addr (Provider)</li>
 * <li>payerSection/entry/act/entryRelationship/act/performer/assignedEntity/addr (Payer)</li>
 * <li>payerSection/entry/act/entryRelationship/act/participant[@typeCode='COV'] (Policy Member)</li>
 * <li>payerSection/entry/act/entryRelationship/act/participant[@typeCode='HLD'] (Policy Holder)</li>
 *  * 
 * @author cdf02
 */
public class RawCCDAddressVO {
	
	private String ccdUse;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String county;
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
	
	@Override
	public String toString() {
		StringBuilder ab = new StringBuilder();
		ab.append("Address Use: " + ccdUse + "\n");
		ab.append(street1).append("\n")
		.append(street2).append("\n")
		.append(city).append(", ").append(state).append(" " + country + "\n");
		ab.append(zip + "\n");
		return ab.toString();
	}
	

}
