package gov.ny.state.doh.bhae.nyehms.web.spring.domain.search;

public class SearchPersonCriteriaVO {

	private String stateNo;
	private String firstName;
	private String lastName;
	private String ssn;
	private String nysid;
	private String din;
	private String cityNo;
	private String dateOfDeath;
	private String dateOfBirth;
	private String flaggedSameOrBadInd;
	
	//Set the text value of the date for the JSP page field integration.
	private String dateOfDeathTextValue="";
	private String dateOfBirthTextValue="";
	
	public String getStateNo() {
		return stateNo;
	}
	public void setStateNo(String stateNo) {
		this.stateNo = stateNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getNysid() {
		return nysid;
	}
	public void setNysid(String nysid) {
		this.nysid = nysid;
	}
	public String getDin() {
		return din;
	}
	public void setDin(String din) {
		this.din = din;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfDeathTextValue() {
		return dateOfDeathTextValue;
	}
	public void setDateOfDeathTextValue(String dateOfDeathTextValue) {
		this.dateOfDeathTextValue = dateOfDeathTextValue;
	}
	public String getDateOfBirthTextValue() {
		return dateOfBirthTextValue;
	}
	public void setDateOfBirthTextValue(String dateOfBirthTextValue) {
		this.dateOfBirthTextValue = dateOfBirthTextValue;
	}	
	
	public String getFlaggedSameOrBadInd() {
		return flaggedSameOrBadInd;
	}
	public void setFlaggedSameOrBadInd(String flaggedSameOrBadInd) {
		this.flaggedSameOrBadInd = flaggedSameOrBadInd;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	@Override
	public String toString() {
		return "SearchPersonCriteriaVO [stateNo=" + stateNo + ", firstName=" + firstName + ", lastName=" + lastName + ", ssn=" + ssn + ", nysid=" + nysid + ", "
				+ "din=" + din + ", dateOfDeath=" + dateOfDeath + ", dateOfBirth=" + dateOfBirth + ", dateOfDeathTextValue=" + dateOfDeathTextValue + ", "
						+ "dateOfBirthTextValue=" + dateOfBirthTextValue + ", flaggedSameOrBadInd=" + flaggedSameOrBadInd + "]";
	}
	
	
}
