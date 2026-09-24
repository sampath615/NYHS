package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

import java.util.List;

import org.json.simple.JSONObject;

public class MailStatusVO {

	private String fileName;
	private int totalCount;
	private List<JSONObject> loincCodeData;
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<JSONObject> getLoincCodeData() {
		return loincCodeData;
	}
	public void setLoincCodeData(List<JSONObject> loincCodeData) {
		this.loincCodeData = loincCodeData;
	}
	
	
}
