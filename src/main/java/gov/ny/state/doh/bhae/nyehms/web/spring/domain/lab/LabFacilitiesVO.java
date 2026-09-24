package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

import org.json.simple.JSONObject;

public class LabFacilitiesVO {
	
	private String pfi;
	private String address;
	private String laftStaffAssigned;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private String labName;
	private String flagActive;
	private String isNewRecord;
	private String fromSource;
	private String flagServersNYC;
	private String flagBloodBank;
	private String clia;
	private String approvalStatusCode;
	private String refersDTATestingTo;
	private String lowVolume;
	private String physicianOfficeLab;
	private String performsDTA;

	private List<JSONObject> pfiContactDetails;

	
	public String getLaftStaffAssigned() {
		return laftStaffAssigned;
	}
	public void setLaftStaffAssigned(String laftStaffAssigned) {
		this.laftStaffAssigned = laftStaffAssigned;
	}
	public String getPfi() {
		return pfi;
	}
	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getFlagActive() {
		return flagActive;
	}
	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}
	public String getIsNewRecord() {
		return isNewRecord;
	}
	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	public List<JSONObject> getPfiContactDetails() {
		return pfiContactDetails;
	}
	public void setPfiContactDetails(List<JSONObject> pfiContactDetails) {
		this.pfiContactDetails = pfiContactDetails;
	}
	public String getFromSource() {
		return fromSource;
	}
	public void setFromSource(String fromSource) {
		this.fromSource = fromSource;
	}
	public String getFlagServersNYC() {
		return flagServersNYC;
	}
	public void setFlagServersNYC(String flagServersNYC) {
		this.flagServersNYC = flagServersNYC;
	}
	public String getClia() {
		return clia;
	}
	public void setClia(String clia) {
		this.clia = clia;
	}
	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}
	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}
	public String getFlagBloodBank() {
		return flagBloodBank;
	}
	public void setFlagBloodBank(String flagBloodBank) {
		this.flagBloodBank = flagBloodBank;
	}
	public String getRefersDTATestingTo() {
		return refersDTATestingTo;
	}
	public void setRefersDTATestingTo(String refersDTATestingTo) {
		this.refersDTATestingTo = refersDTATestingTo;
	}
	public String getLowVolume() {
		return lowVolume;
	}
	public void setLowVolume(String lowVolume) {
		this.lowVolume = lowVolume;
	}
	public String getPhysicianOfficeLab() {
		return physicianOfficeLab;
	}
	public void setPhysicianOfficeLab(String physicianOfficeLab) {
		this.physicianOfficeLab = physicianOfficeLab;
	}
	public String getPerformsDTA() {
		return performsDTA;
	}
	public void setPerformsDTA(String performsDTA) {
		this.performsDTA = performsDTA;
	}
	
}
