package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.util.List;

import org.json.simple.JSONObject;

public class FilterConditionsVO {

	private int FILTER_NUMBER;
	private List<JSONObject> filterConditions;
	public int getFILTER_NUMBER() {
		return FILTER_NUMBER;
	}
	public void setFILTER_NUMBER(int fILTER_NUMBER) {
		FILTER_NUMBER = fILTER_NUMBER;
	}
	public List<JSONObject> getFilterConditions() {
		return filterConditions;
	}
	public void setFilterConditions(List<JSONObject> filterConditions) {
		this.filterConditions = filterConditions;
	}
	
	
		
}
