package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LabTestMappingVO {
	
	
	private String localCodeMapId;
	private String testCode;
	private String testDescription;
	private String flagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String dateValidFrom;
	private String dateValidTill;

	
	public String getLocalCodeMapId() {
		return localCodeMapId;
	}
	public void setLocalCodeMapId(String localCodeMapId) {
		this.localCodeMapId = localCodeMapId;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
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
	public String getDateValidFrom() {
		return dateValidFrom;
	}
	public void setDateValidFrom(String dateValidFrom) {
		this.dateValidFrom = dateValidFrom;
	}
	public String getDateValidTill() {
		return dateValidTill;
	}
	public void setDateValidTill(String dateValidTill) {
		this.dateValidTill = dateValidTill;
	}
	
		
}
