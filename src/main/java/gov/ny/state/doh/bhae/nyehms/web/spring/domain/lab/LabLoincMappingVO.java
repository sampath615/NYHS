package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LabLoincMappingVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pfi;
	private String localCodeLoincMapId;
	private String loincMapId;
	private String loincCode;
	private String loincDescription;
	private String testType;
	private String unitOfResult;
	private String flagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String dateValidFrom;
	private String dateValidTill;
	private String testKit;
	private String testKitDesc;
	private String instrument;
	private String instrumentDesc;
	private String vlDetectionLevel;
	private String vlDetectionLevelDesc;
	
	public String getLoincMapId() {
		return loincMapId;
	}
	public String getPfi() {
		return pfi;
	}
	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	public void setLoincMapId(String loincMapId) {
		this.loincMapId = loincMapId;
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
	public String getUnitOfResult() {
		return unitOfResult;
	}
	public void setUnitOfResult(String unitOfResult) {
		this.unitOfResult = unitOfResult;
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
	public String getLocalCodeLoincMapId() {
		return localCodeLoincMapId;
	}
	public void setLocalCodeLoincMapId(String localCodeLoincMapId) {
		this.localCodeLoincMapId = localCodeLoincMapId;
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
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public String getTestKit() {
		return testKit;
	}
	public void setTestKit(String testKit) {
		this.testKit = testKit;
	}
	public String getTestKitDesc() {
		return testKitDesc;
	}
	public void setTestKitDesc(String testKitDesc) {
		this.testKitDesc = testKitDesc;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public String getInstrumentDesc() {
		return instrumentDesc;
	}
	public void setInstrumentDesc(String instrumentDesc) {
		this.instrumentDesc = instrumentDesc;
	}
	public String getVlDetectionLevel() {
		return vlDetectionLevel;
	}
	public void setVlDetectionLevel(String vlDetectionLevel) {
		this.vlDetectionLevel = vlDetectionLevel;
	}
	public String getVlDetectionLevelDesc() {
		return vlDetectionLevelDesc;
	}
	public void setVlDetectionLevelDesc(String vlDetectionLevelDesc) {
		this.vlDetectionLevelDesc = vlDetectionLevelDesc;
	}
	
	
}
