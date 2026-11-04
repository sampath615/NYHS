package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

public class RawHL7Dg1VO {
	private int uphnId;
	private int rawHl7AdtDg1ID;
	private int hl7DocId;
	private String dxCodingMethod;
	private String dxCodeId;
	private String dxCodetext;
	private String dxCodeSystem;
	private String dxDesc;
	private String dxDateText;
	private String dxType;
	private String providerFname;
	private String providerLname;
	private String providerID;
	private String facName;
	
	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public int getRawHl7AdtDg1ID() {
		return rawHl7AdtDg1ID;
	}
	public void setRawHl7AdtDg1ID(int rawHl7AdtDg1ID) {
		this.rawHl7AdtDg1ID = rawHl7AdtDg1ID;
	}
	public int getHl7DocId() {
		return hl7DocId;
	}
	public void setHl7DocId(int hl7DocId) {
		this.hl7DocId = hl7DocId;
	}
	public String getDxCodingMethod() {
		return dxCodingMethod;
	}
	public void setDxCodingMethod(String dxCodingMethod) {
		this.dxCodingMethod = dxCodingMethod;
	}
	public String getDxCodeId() {
		return dxCodeId;
	}
	public void setDxCodeId(String dxCodeId) {
		this.dxCodeId = dxCodeId;
	}
	public String getDxCodetext() {
		return dxCodetext;
	}
	public void setDxCodetext(String dxCodetext) {
		this.dxCodetext = dxCodetext;
	}
	public String getDxCodeSystem() {
		return dxCodeSystem;
	}
	public void setDxCodeSystem(String dxCodeSystem) {
		this.dxCodeSystem = dxCodeSystem;
	}
	public String getDxDesc() {
		return dxDesc;
	}
	public void setDxDesc(String dxDesc) {
		this.dxDesc = dxDesc;
	}
	public String getDxDateText() {
		return dxDateText;
	}
	public void setDxDateText(String dxDateText) {
		this.dxDateText = dxDateText;
	}
	public String getDxType() {
		return dxType;
	}
	public String getProviderFname() {
		return providerFname;
	}
	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}
	public String getProviderLname() {
		return providerLname;
	}
	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getFacName() {
		return facName;
	}
	public void setFacName(String facName) {
		this.facName = facName;
	}
	public void setDxType(String dxType) {
		this.dxType = dxType;
	}
	

}
