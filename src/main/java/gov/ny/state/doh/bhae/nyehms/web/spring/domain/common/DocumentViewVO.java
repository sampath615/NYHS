package gov.ny.state.doh.bhae.nyehms.web.spring.domain.common;

public class DocumentViewVO {	
	private int documentID;         
    private String providerID="";         
    private String FacilityID="";
	public int getDocumentID() {
		return documentID;
	}
	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getFacilityID() {
		return FacilityID;
	}
	public void setFacilityID(String facilityID) {
		FacilityID = facilityID;
	}    
    
}
