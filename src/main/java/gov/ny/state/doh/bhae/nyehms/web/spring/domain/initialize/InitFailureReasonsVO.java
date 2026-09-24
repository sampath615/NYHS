package gov.ny.state.doh.bhae.nyehms.web.spring.domain.initialize;

public class InitFailureReasonsVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int reasoncd;
	String short_desc;
	String description;
	public int getReasoncd() {
		return reasoncd;
	}
	public void setReasoncd(int reasoncd) {
		this.reasoncd = reasoncd;
	}
	public String getShort_desc() {
		return short_desc;
	}
	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc.trim();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
