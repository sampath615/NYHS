package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

//import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RawHL7XpnVO {

	private String firstName;
	private String lastName;
	private String middleName;
	private String nameSuffix;

	public RawHL7XpnVO() {
		// TODO Auto-generated constructor stub
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

}
