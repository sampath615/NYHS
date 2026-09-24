package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDLabResultVO {

	private int uphnId;
	private int ccdDocID;
	private int testSeqNo;
	private int resultSeqNo;
	private int ccdLabResultId;
	private int ccdLabTestId;
	private String componentCode;
	private String componentCodeSystem;
	private String componentDisplayName;
	private String resultDateText;
	private String resultValue;
	private String resultStatus;
	private String referenceRangeLow;
	private String referenceRangeHigh;
	private String resultComments;
	private String componentCodeSystemName;
	private String referenceRange;
	
	
	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public int getCcdDocID() {
		return ccdDocID;
	}
	public void setCcdDocID(int ccdDocID) {
		this.ccdDocID = ccdDocID;
	}
	public int getTestSeqNo() {
		return testSeqNo;
	}
	public void setTestSeqNo(int testSeqNo) {
		this.testSeqNo = testSeqNo;
	}
	public int getResultSeqNo() {
		return resultSeqNo;
	}
	public void setResultSeqNo(int resultSeqNo) {
		this.resultSeqNo = resultSeqNo;
	}
	public int getCcdLabResultId() {
		return ccdLabResultId;
	}
	public void setCcdLabResultId(int ccdLabResultId) {
		this.ccdLabResultId = ccdLabResultId;
	}
	public int getCcdLabTestId() {
		return ccdLabTestId;
	}
	public void setCcdLabTestId(int ccdLabTestId) {
		this.ccdLabTestId = ccdLabTestId;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentCodeSystem() {
		return componentCodeSystem;
	}
	public void setComponentCodeSystem(String componentCodeSystem) {
		this.componentCodeSystem = componentCodeSystem;
	}
	public String getComponentDisplayName() {
		return componentDisplayName;
	}
	public void setComponentDisplayName(String componentDisplayName) {
		this.componentDisplayName = componentDisplayName;
	}
	public String getResultDateText() {
		return resultDateText;
	}
	public void setResultDateText(String resultDateText) {
		this.resultDateText = resultDateText;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getReferenceRangeLow() {
		return referenceRangeLow;
	}
	public void setReferenceRangeLow(String referenceRangeLow) {
		this.referenceRangeLow = referenceRangeLow;
	}
	public String getReferenceRangeHigh() {
		return referenceRangeHigh;
	}
	public void setReferenceRangeHigh(String referenceRangeHigh) {
		this.referenceRangeHigh = referenceRangeHigh;
	}
	public String getResultComments() {
		return resultComments;
	}
	public void setResultComments(String resultComments) {
		this.resultComments = resultComments;
	}
	public String getComponentCodeSystemName() {
		return componentCodeSystemName;
	}
	public void setComponentCodeSystemName(String componentCodeSystemName) {
		this.componentCodeSystemName = componentCodeSystemName;
	}
	public String getReferenceRange() {
		return referenceRange;
	}
	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}
	
}
