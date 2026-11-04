package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawHL7XadVO {

	private String pidAddressLine1;
	private String pidAddressLine2;
	private String pidCity;
	private String pidCountryCode;
	private String pidCountryOfOrigin;
	private String pidState;
	private String pidZipCode;
	private String effectiveTime;
	private String expirationTime;

	public RawHL7XadVO() {
		// TODO Auto-generated constructor stub
	}

	public String getPidAddressLine1() {
		return pidAddressLine1;
	}

	public void setPidAddressLine1(String pidAddressLine1) {
		this.pidAddressLine1 = pidAddressLine1;
	}

	public String getPidAddressLine2() {
		return pidAddressLine2;
	}

	public void setPidAddressLine2(String pidAddressLine2) {
		this.pidAddressLine2 = pidAddressLine2;
	}

	public String getPidCity() {
		return pidCity;
	}

	public void setPidCity(String pidCity) {
		this.pidCity = pidCity;
	}

	public String getPidCountryCode() {
		return pidCountryCode;
	}

	public void setPidCountryCode(String pidCountryCode) {
		this.pidCountryCode = pidCountryCode;
	}

	public String getPidCountryOfOrigin() {
		return pidCountryOfOrigin;
	}

	public void setPidCountryOfOrigin(String pidCountryOfOrigin) {
		this.pidCountryOfOrigin = pidCountryOfOrigin;
	}

	public String getPidState() {
		return pidState;
	}

	public void setPidState(String pidState) {
		this.pidState = pidState;
	}

	public String getPidZipCode() {
		return pidZipCode;
	}

	public void setPidZipCode(String pidZipCode) {
		this.pidZipCode = pidZipCode;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
