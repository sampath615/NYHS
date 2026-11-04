package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDNameVO {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String title;
	private String suffix;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public void setNameFML(String first, String middle, String last) {
		firstName = first;
		middleName = middle;
		lastName = last;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(firstName  + " ");
		if (middleName != null && !"".equals(middleName)) sb.append(middleName + " \n");
		sb.append(lastName);
		
		return sb.toString();
	}

}
