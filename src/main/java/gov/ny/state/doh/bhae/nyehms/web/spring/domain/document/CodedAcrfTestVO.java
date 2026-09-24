package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class CodedAcrfTestVO {	
	
	private String acrtTestCode ="";
	private String parentTestTypeCode="";
	private String acrtTestDesc ="";
	private String createDate="";
	private String createId ="";
	private String endDate="";
	private String endId="";
	private String testSection="";
	
	public String getAcrtTestCode() {
		return acrtTestCode;
	}
	public void setAcrtTestCode(String acrtTestCode) {
		this.acrtTestCode = acrtTestCode;
	}
	public String getParentTestTypeCode() {
		return parentTestTypeCode;
	}
	public void setParentTestTypeCode(String parentTestTypeCode) {
		this.parentTestTypeCode = parentTestTypeCode;
	}
	public String getAcrtTestDesc() {
		return acrtTestDesc;
	}
	public void setAcrtTestDesc(String acrtTestDesc) {
		this.acrtTestDesc = acrtTestDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndId() {
		return endId;
	}
	public void setEndId(String endId) {
		this.endId = endId;
	}
	
	public String getTestSection() {
		return testSection;
	}
	public void setTestSection(String testSection) {
		this.testSection = testSection;
	}
	@Override
	public String toString() {
		return "CodedAcrfTestVO [acrtTestCode=" + acrtTestCode + ", parentTestTypeCode=" + parentTestTypeCode + ", acrtTestDesc="
				+ acrtTestDesc + ", createDate=" + createDate + ", createId=" + createId + ", endDate=" + endDate + ", testSection=" + testSection + ", endId="
				+ endId + "]";
	}	
	
	
	

}
