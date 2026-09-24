package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;


public class LabInstrumentDetailsVO {
	
	
	private String lpiId;
	private String hivInstrumentId;
	private String hivInstrumentCode;
	private String instrumentName;
	private String instrumentFlagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String dateValidFrom;
	private String dateValidTill;
	
	public String getLpiId() {
		return lpiId;
	}
	public void setLpiId(String lpiId) {
		this.lpiId = lpiId;
	}
	public String getHivInstrumentId() {
		return hivInstrumentId;
	}
	public void setHivInstrumentId(String hivInstrumentId) {
		this.hivInstrumentId = hivInstrumentId;
	}
	public String getHivInstrumentCode() {
		return hivInstrumentCode;
	}
	public void setHivInstrumentCode(String hivInstrumentCode) {
		this.hivInstrumentCode = hivInstrumentCode;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getInstrumentFlagActive() {
		return instrumentFlagActive;
	}
	public void setInstrumentFlagActive(String instrumentFlagActive) {
		this.instrumentFlagActive = instrumentFlagActive;
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
