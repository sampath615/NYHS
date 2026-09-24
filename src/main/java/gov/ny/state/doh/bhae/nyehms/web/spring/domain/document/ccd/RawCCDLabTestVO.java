package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.util.ArrayList;
import java.util.List;

public class RawCCDLabTestVO {

	private int uphnId;
	private int ccdDocID;
	private int testSeqNo;
	private int ccdLabTestId;
	private String testCode;
	private String testCodeCodeSystem;
	private String testFacilityCode;
	private String testFacilityCodeSystem;
	private String testCodeDisplayName;
	private String testDate;
	private String testEncounterNumber;
	private String specimen;
	private String testComments;
	private String testResults;
	private String resultsComments;
	private String filleridRoot;
	private String fillerIdExtension;
	private String fillerIdAuth;
	private String placerIdRoot;
	private String placerIdExtension;
	private String placerIdAuth;
	private List<RawCCDLabResultVO> theLabResult = new ArrayList<RawCCDLabResultVO>();
	private String testCodeSystemName;
	
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
	public int getCcdLabTestId() {
		return ccdLabTestId;
	}
	public void setCcdLabTestId(int ccdLabTestId) {
		this.ccdLabTestId = ccdLabTestId;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getTestCodeCodeSystem() {
		return testCodeCodeSystem;
	}
	public void setTestCodeCodeSystem(String testCodeCodeSystem) {
		this.testCodeCodeSystem = testCodeCodeSystem;
	}
	public String getTestFacilityCode() {
		return testFacilityCode;
	}
	public void setTestFacilityCode(String testFacilityCode) {
		this.testFacilityCode = testFacilityCode;
	}
	
	public String getTestFacilityCodeSystem() {
		return testFacilityCodeSystem;
	}
	public void setTestFacilityCodeSystem(String testFacilityCodeSystem) {
		this.testFacilityCodeSystem = testFacilityCodeSystem;
	}
	public String getTestCodeDisplayName() {
		return testCodeDisplayName;
	}
	public void setTestCodeDisplayName(String testCodeDisplayName) {
		this.testCodeDisplayName = testCodeDisplayName;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTestEncounterNumber() {
		return testEncounterNumber;
	}
	public void setTestEncounterNumber(String testEncounterNumber) {
		this.testEncounterNumber = testEncounterNumber;
	}
	public String getSpecimen() {
		return specimen;
	}
	public void setSpecimen(String specimen) {
		this.specimen = specimen;
	}
	public String getTestComments() {
		return testComments;
	}
	public void setTestComments(String testComments) {
		this.testComments = testComments;
	}
	public String getTestResults() {
		return testResults;
	}
	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}
	public String getResultsComments() {
		return resultsComments;
	}
	public void setResultsComments(String resultsComments) {
		this.resultsComments = resultsComments;
	}
	
	public String getFilleridRoot() {
		return filleridRoot;
	}
	public void setFilleridRoot(String filleridRoot) {
		this.filleridRoot = filleridRoot;
	}
	public String getFillerIdExtension() {
		return fillerIdExtension;
	}
	public void setFillerIdExtension(String fillerIdExtension) {
		this.fillerIdExtension = fillerIdExtension;
	}
	public String getFillerIdAuth() {
		return fillerIdAuth;
	}
	public void setFillerIdAuth(String fillerIdAuth) {
		this.fillerIdAuth = fillerIdAuth;
	}
	public String getPlacerIdRoot() {
		return placerIdRoot;
	}
	public void setPlacerIdRoot(String placerIdRoot) {
		this.placerIdRoot = placerIdRoot;
	}
	public String getPlacerIdExtension() {
		return placerIdExtension;
	}
	public void setPlacerIdExtension(String placerIdExtension) {
		this.placerIdExtension = placerIdExtension;
	}
	public String getPlacerIdAuth() {
		return placerIdAuth;
	}
	public void setPlacerIdAuth(String placerIdAuth) {
		this.placerIdAuth = placerIdAuth;
	}
	
	public List<RawCCDLabResultVO> getTheLabResult() {
		return theLabResult;
	}
	public void setTheLabResult(List<RawCCDLabResultVO> theLabResult) {
		this.theLabResult = theLabResult;
	}
	public String getTestCodeSystemName() {
		return testCodeSystemName;
	}
	public void setTestCodeSystemName(String testCodeSystemName) {
		this.testCodeSystemName = testCodeSystemName;
	}
}
