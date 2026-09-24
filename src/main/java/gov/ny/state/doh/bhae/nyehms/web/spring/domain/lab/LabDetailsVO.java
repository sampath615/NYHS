package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class LabDetailsVO {
	
	private List<LabPermitsVO> labPermitsVO;
	private List<LabLocalCodesMappingVO> labLocalCodesMappingVO;
	private List<LabLoincMappingVO> labLoincMappingVO;
	public List<LabPermitsVO> getLabPermitsVO() {
		return labPermitsVO;
	}
	public void setLabPermitsVO(List<LabPermitsVO> labPermitsVO) {
		this.labPermitsVO = labPermitsVO;
	}
	public List<LabLocalCodesMappingVO> getLabLocalCodesMappingVO() {
		return labLocalCodesMappingVO;
	}
	public void setLabLocalCodesMappingVO(
			List<LabLocalCodesMappingVO> labLocalCodesMappingVO) {
		this.labLocalCodesMappingVO = labLocalCodesMappingVO;
	}
	public List<LabLoincMappingVO> getLabLoincMappingVO() {
		return labLoincMappingVO;
	}
	public void setLabLoincMappingVO(List<LabLoincMappingVO> labLoincMappingVO) {
		this.labLoincMappingVO = labLoincMappingVO;
	}
	
	
	

}
