package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LAFTCountReconcileVO {
	
	private String userEnteredPfi;
	private String pfiLabClia;
	private String pfiLabName;
	private String pfiLabAddress;
	private String pfiLabCity;
	private String pfiLabState;
	private String pfiLabZip;
	private String pfiLabCountry;
	private String pfiLabLaftStaff;
	private String pfiLabApprovalStatus;
	
	private String collectionPeriod;
	
	private LAFTCountReconcileResolutionVO countResolutionVO;
	
	public String getUserEnteredPfi() {
		return userEnteredPfi;
	}
	public void setUserEnteredPfi(String userEnteredPfi) {
		this.userEnteredPfi = userEnteredPfi;
	}
	public String getPfiLabClia() {
		return pfiLabClia;
	}
	public void setPfiLabClia(String pfiLabClia) {
		this.pfiLabClia = pfiLabClia;
	}
	public String getPfiLabName() {
		return pfiLabName;
	}
	public void setPfiLabName(String pfiLabName) {
		this.pfiLabName = pfiLabName;
	}
	public String getPfiLabAddress() {
		return pfiLabAddress;
	}
	public void setPfiLabAddress(String pfiLabAddress) {
		this.pfiLabAddress = pfiLabAddress;
	}
	public String getPfiLabCity() {
		return pfiLabCity;
	}
	public void setPfiLabCity(String pfiLabCity) {
		this.pfiLabCity = pfiLabCity;
	}
	public String getPfiLabState() {
		return pfiLabState;
	}
	public void setPfiLabState(String pfiLabState) {
		this.pfiLabState = pfiLabState;
	}
	public String getPfiLabZip() {
		return pfiLabZip;
	}
	public void setPfiLabZip(String pfiLabZip) {
		this.pfiLabZip = pfiLabZip;
	}
	public String getPfiLabCountry() {
		return pfiLabCountry;
	}
	public void setPfiLabCountry(String pfiLabCountry) {
		this.pfiLabCountry = pfiLabCountry;
	}
	public String getPfiLabLaftStaff() {
		return pfiLabLaftStaff;
	}
	public void setPfiLabLaftStaff(String pfiLabLaftStaff) {
		this.pfiLabLaftStaff = pfiLabLaftStaff;
	}
	public String getPfiLabApprovalStatus() {
		return pfiLabApprovalStatus;
	}
	public void setPfiLabApprovalStatus(String pfiLabApprovalStatus) {
		this.pfiLabApprovalStatus = pfiLabApprovalStatus;
	}
	/*
	 * public List<LAFTCountReconcileResolutionVO> getCountResolutionList() { return
	 * countResolutionList; } public void
	 * setCountResolutionList(List<LAFTCountReconcileResolutionVO>
	 * countResolutionList) { this.countResolutionList = countResolutionList; }
	 */
	public LAFTCountReconcileResolutionVO getCountResolutionVO() {
		return countResolutionVO;
	}
	public void setCountResolutionVO(LAFTCountReconcileResolutionVO countResolutionVO) {
		this.countResolutionVO = countResolutionVO;
	}
	public String getCollectionPeriod() {
		return collectionPeriod;
	}
	public void setCollectionPeriod(String collectionPeriod) {
		this.collectionPeriod = collectionPeriod;
	}
	
	
	
	
	
}
