package gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads;

public class ClusterTemplateVO {
	
	public String nyehmsStateno;
	public String identificationMethod;
	public String identificationDate;
	public long demoDocumentId;
	
	public String getNyehmsStateno() {
		return nyehmsStateno;
	}
	public void setNyehmsStateno(String nyehmsStateno) {
		this.nyehmsStateno = nyehmsStateno;
	}
	public String getIdentificationMethod() {
		return identificationMethod;
	}
	public void setIdentificationMethod(String identificationMethod) {
		this.identificationMethod = identificationMethod;
	}
	public String getIdentificationDate() {
		return identificationDate;
	}
	public void setIdentificationDate(String identificationDate) {
		this.identificationDate = identificationDate;
	}
	public long getDemoDocumentId() {
		return demoDocumentId;
	}
	public void setDemoDocumentId(long demoDocumentId) {
		this.demoDocumentId = demoDocumentId;
	}
		
	
}
