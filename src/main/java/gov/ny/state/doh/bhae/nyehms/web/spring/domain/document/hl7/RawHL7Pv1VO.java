package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawHL7Pv1VO {

	private RawHL7XpnVO providerName;
	private int providerCount;
	private String providerJson;
	private String providerFirstId;
	private String pv1ProviderAuth;
	private String patientClass;
	private String visitNumber;
	private String pv1AttendingProviderList;
	private String pv1ProviderID;
	private String pv1ReferringProviderList;
	//

	public RawHL7Pv1VO() {
		// TODO Auto-generated constructor stub
	}

	public RawHL7XpnVO getProviderName() {
		return providerName;
	}

	public void setProviderName(RawHL7XpnVO providerName) {
		this.providerName = providerName;
	}

	public int getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(int providerCount) {
		this.providerCount = providerCount;
	}

	public String getProviderJson() {
		return providerJson;
	}

	public void setProviderJson(String providerJson) {
		this.providerJson = providerJson;
	}

	public String getProviderFirstId() {
		return providerFirstId;
	}

	public void setProviderFirstId(String providerFirstId) {
		this.providerFirstId = providerFirstId;
	}

	public String getPv1ProviderAuth() {
		return pv1ProviderAuth;
	}

	public void setPv1ProviderAuth(String pv1ProviderAuth) {
		this.pv1ProviderAuth = pv1ProviderAuth;
	}

	public String getPatientClass() {
		return patientClass;
	}

	public void setPatientClass(String patientClass) {
		this.patientClass = patientClass;
	}

	public String getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(String visitNumber) {
		this.visitNumber = visitNumber;
	}

	public String getPv1AttendingProviderList() {
		return pv1AttendingProviderList;
	}

	public void setPv1AttendingProviderList(String pv1AttendingProvider) {
		this.pv1AttendingProviderList = pv1AttendingProvider;
	}

	public String getPv1ProviderID() {
		return pv1ProviderID;
	}

	public void setPv1ProviderID(String pv1ProviderID) {
		this.pv1ProviderID = pv1ProviderID;
	}

	public String getPv1ReferringProviderList() {
		return pv1ReferringProviderList;
	}

	public void setPv1ReferringProviderList(String pv1ReferringProviderList) {
		this.pv1ReferringProviderList = pv1ReferringProviderList;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
