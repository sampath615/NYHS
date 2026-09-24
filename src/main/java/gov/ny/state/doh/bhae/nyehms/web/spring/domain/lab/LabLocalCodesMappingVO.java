package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class LabLocalCodesMappingVO implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String localCodeMapId;
	private String localCode;
	private String localCodeFlagActive;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String dateValidFrom;
	private String dateValidTill;
 
	
	private List<LabLoincMappingVO> loincMappingDetails;
	private List<LabTestMappingVO> testMappingDetails;
	
	public String getLocalCodeMapId() {
		return localCodeMapId;
	}
	public void setLocalCodeMapId(String localCodeMapId) {
		this.localCodeMapId = localCodeMapId;
	}
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	public String getLocalCodeFlagActive() {
		return localCodeFlagActive;
	}
	public void setLocalCodeFlagActive(String localCodeFlagActive) {
		this.localCodeFlagActive = localCodeFlagActive;
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
	public List<LabLoincMappingVO> getLoincMappingDetails() {
		return loincMappingDetails;
	}
	public void setLoincMappingDetails(List<LabLoincMappingVO> loincMappingDetails) {
		this.loincMappingDetails = loincMappingDetails;
	}
	public List<LabTestMappingVO> getTestMappingDetails() {
		return testMappingDetails;
	}
	public void setTestMappingDetails(List<LabTestMappingVO> testMappingDetails) {
		this.testMappingDetails = testMappingDetails;
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
