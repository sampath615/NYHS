package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class ClinicalLaboratoryVO {
	
	private String clia;
	private List<LabFacilitiesVO> labFacilities;
	
	
	public String getClia() {
		return clia;
	}
	public void setClia(String clia) {
		this.clia = clia;
	}
	public List<LabFacilitiesVO> getLabFacilities() {
		return labFacilities;
	}
	public void setLabFacilities(List<LabFacilitiesVO> labFacilities) {
		this.labFacilities = labFacilities;
	}
	
	


}
