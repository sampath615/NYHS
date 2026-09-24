package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

public class RawHL7AddressVO {
	
	public RawHL7AddressVO() {
		// TODO Auto-generated constructor stub
	}
	
	private String personAddress;
	private String personCity;
	private String personState;
	private String personZip;
	private String personPlus4;
	private String personCountry;
	
	
	public String getPersonAddress() {
		return personAddress;
	}
	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}
	public String getPersonCity() {
		return personCity;
	}
	public void setPersonCity(String personCity) {
		this.personCity = personCity;
	}
	public String getPersonState() {
		return personState;
	}
	public void setPersonState(String personState) {
		this.personState = personState;
	}
	public String getPersonZip() {
		return personZip;
	}
	public void setPersonZip(String personZip) {
		this.personZip = personZip;
	}
	public String getPersonPlus4() {
		return personPlus4;
	}
	public void setPersonPlus4(String personPlus4) {
		this.personPlus4 = personPlus4;
	}
	public String getPersonCountry() {
		return personCountry;
	}
	public void setPersonCountry(String personCountry) {
		this.personCountry = personCountry;
	}

}
