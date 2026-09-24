package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;


public class LoincInterpretResultsVO {
	private long ruleId;
	private String loincCode;
	private String loincDescription;
	private String lowerBound;
	private String higherBound;
	
	
	
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public String getLoincCode() {
		return loincCode;
	}
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}
	public String getLoincDescription() {
		return loincDescription;
	}
	public void setLoincDescription(String loincDescription) {
		this.loincDescription = loincDescription;
	}
	public String getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}
	public String getHigherBound() {
		return higherBound;
	}
	public void setHigherBound(String higherBound) {
		this.higherBound = higherBound;
	}
	
	
	


}
