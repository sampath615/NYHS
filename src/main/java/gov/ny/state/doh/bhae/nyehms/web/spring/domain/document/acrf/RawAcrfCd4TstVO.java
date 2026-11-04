package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

public class RawAcrfCd4TstVO {
	
	private int cd4SrcId; 
	private int documentId;
	private int acrfSrcId;
	private Integer cd4Count;
	private Float cd4Percent;
	private String collectionDate="";
	private String closestToCurrentDx;
	private boolean firstLowCd4;
	private boolean defaultCd4 = false;
	private String manufacturer;
	private String labName;
	private String clia;
	private String facilityName;
	private String facilityEharsUid;
	private String providerName;
	private String providerFname;
	private String providerLname;
	private String providerMname;
	private String providerSuffix;
	private String cd4Index;
	
	public int getCd4SrcId() {
		return cd4SrcId;
	}
	public void setCd4SrcId(int cd4SrcId) {
		this.cd4SrcId = cd4SrcId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getAcrfSrcId() {
		return acrfSrcId;
	}
	public void setAcrfSrcId(int acrfSrcId) {
		this.acrfSrcId = acrfSrcId;
	}
	public Integer getCd4Count() {
		return cd4Count;
	}
	public void setCd4Count(Integer cd4Count) {
		this.cd4Count = cd4Count;
	}
	public Float getCd4Percent() {
		return cd4Percent;
	}
	public void setCd4Percent(Float cd4Percent) {
		this.cd4Percent = cd4Percent;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	
	public String getClosestToCurrentDx() {
		return closestToCurrentDx;
	}
	public void setClosestToCurrentDx(String closestToCurrentDx) {
		this.closestToCurrentDx = closestToCurrentDx;
	}
	public boolean isFirstLowCd4() {
		return firstLowCd4;
	}
	public void setFirstLowCd4(boolean firstLowCd4) {
		this.firstLowCd4 = firstLowCd4;
	}
	
	public boolean isDefaultCd4() {
		return defaultCd4;
	}
	public void setDefaultCd4(boolean defaultCd4) {
		this.defaultCd4 = defaultCd4;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public String getClia() {
		return clia;
	}
	public void setClia(String clia) {
		this.clia = clia;
	}
	public String getFacilityEharsUid() {
		return facilityEharsUid;
	}
	public void setFacilityEharsUid(String facilityEharsUid) {
		this.facilityEharsUid = facilityEharsUid;
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
	public String getProviderMname() {
		return providerMname;
	}
	public void setProviderMname(String providerMname) {
		this.providerMname = providerMname;
	}
	public String getProviderSuffix() {
		return providerSuffix;
	}
	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}
	
	public String getCd4Index() {
		return cd4Index;
	}
	public void setCd4Index(String cd4Index) {
		this.cd4Index = cd4Index;
	}
	@Override
	public String toString() {
		return "RawAcrfCd4TstVO [cd4SrcId=" + cd4SrcId + ", documentId=" + documentId + ", acrfSrcId=" + acrfSrcId + ", cd4Count=" + cd4Count + ", cd4Percent=" + cd4Percent + ", collectionDate=" + collectionDate + ", closestToCurrentDx=" + closestToCurrentDx + ", firstLowCd4=" + firstLowCd4 + "]";
	}
	
	
	
	
	
	

}
