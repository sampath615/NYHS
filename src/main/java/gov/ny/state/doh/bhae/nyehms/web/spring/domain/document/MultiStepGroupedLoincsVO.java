package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;


public class MultiStepGroupedLoincsVO {

	private String pfi;
	private int groupCount;
	private List<String> loincCodes;
	
	
	public String getPfi() {
		return pfi;
	}
	public void setPfi(String pfi) {
		this.pfi = pfi;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	public List<String> getLoincCodes() {
		return loincCodes;
	}
	public void setLoincCodes(List<String> loincCodes) {
		this.loincCodes = loincCodes;
	}
	
	
}
