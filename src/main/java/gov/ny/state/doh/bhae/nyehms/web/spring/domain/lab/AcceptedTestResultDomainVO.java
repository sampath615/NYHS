package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;


public class AcceptedTestResultDomainVO {
	
	private String loincCode;
	private String loincAtrDomainMapId;
	private String domainCode;
	private String typeOfResult;
	private String acceptedValue;
	private String rangeLowerBound;
	private String rangeUpperBound;
	private String flagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String standardResults;
	
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getTypeOfResult() {
		return typeOfResult;
	}
	public void setTypeOfResult(String typeOfResult) {
		this.typeOfResult = typeOfResult;
	}
	public String getAcceptedValue() {
		return acceptedValue;
	}
	public void setAcceptedValue(String acceptedValue) {
		this.acceptedValue = acceptedValue;
	}
	public String getRangeLowerBound() {
		return rangeLowerBound;
	}
	public void setRangeLowerBound(String rangeLowerBound) {
		this.rangeLowerBound = rangeLowerBound;
	}
	public String getRangeUpperBound() {
		return rangeUpperBound;
	}
	public void setRangeUpperBound(String rangeUpperBound) {
		this.rangeUpperBound = rangeUpperBound;
	}
	public String getFlagActive() {
		return flagActive;
	}
	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getLoincAtrDomainMapId() {
		return loincAtrDomainMapId;
	}
	public void setLoincAtrDomainMapId(String loincAtrDomainMapId) {
		this.loincAtrDomainMapId = loincAtrDomainMapId;
	}
	public String getLoincCode() {
		return loincCode;
	}
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}
	public String getStandardResults() {
		return standardResults;
	}
	public void setStandardResults(String standardResults) {
		this.standardResults = standardResults;
	}
	
	

}
