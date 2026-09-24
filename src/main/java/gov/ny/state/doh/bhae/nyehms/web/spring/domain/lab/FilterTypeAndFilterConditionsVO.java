package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

public class FilterTypeAndFilterConditionsVO {

	private int FILTER_TYPE;
	private List<FilterConditionsVO> filterConditions;
	
	public int getFILTER_TYPE() {
		return FILTER_TYPE;
	}
	public void setFILTER_TYPE(int fILTER_TYPE) {
		FILTER_TYPE = fILTER_TYPE;
	}
	public List<FilterConditionsVO> getFilterConditions() {
		return filterConditions;
	}
	public void setFilterConditions(List<FilterConditionsVO> filterConditions) {
		this.filterConditions = filterConditions;
	}
	
	
		
}
