package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

import java.util.HashMap;
import java.util.Map;

public class RawAcrfLabsVO {

	private int labSrcId;
	private int documentId;
	
	private int acrfSrcId;
	private String parentTestType="";
	private String acrfTestCode="";
	public String acrfResultCode="";
	private String collectionDate="";
	private boolean rapidTest;
	/*ACRF 2023 revision*/
	private String rapidTestType;
	private String interpretation;
	private String analyteInterpretation;
	private String hiv12AbAnalyteResult;
	private String hiv1CmlAnalyteResult;
	private String hiv1LogAnalyteResult;
	
	private String testSection;
	/*ACRF 2023 revision*/
	
	private String copiesMl="";
	private String log="";
	private String otherTest="";
	private String manufacturer="";
	private Map<Object,Object> labResultMap= new HashMap<>();
	private String isDeleted="";
	private String labName;
	private String clia;
	private String facilityName;
	private String facilityEharsUid;
	private String providerName;
	private String providerFname;
	private String providerLname;
	private String providerMname;
	private String providerSuffix;
	private String indexValue;
	private String dignosticAlgorithm;
	
	private String hiv1AbAnalyteResult;
	private String hiv1AbAnalyteResultIndex;
	private String hiv2AbAnalyteResult;
	private String hiv2AbAnalyteResultIndex;
	private String hiv1AgAnalyteResult;
	private String hiv1AgAnalyteResultIndex;
	
	public String getParentTestType() {
		return parentTestType;
	}
	public void setParentTestType(String parentTestType) {
		this.parentTestType = parentTestType;
	}
	public String getAcrfTestCode() {
		return acrfTestCode;
	}
	public void setAcrfTestCode(String acrfTestCode) {
		this.acrfTestCode = acrfTestCode;
	}
	public String getAcrfResultCode() {
		return acrfResultCode;
	}
	public void setAcrfResultCode(String acrfResultCode) {
		this.acrfResultCode = acrfResultCode;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	
	public String getCopiesMl() {
		return copiesMl;
	}
	public void setCopiesMl(String copiesMl) {
		this.copiesMl = copiesMl;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getOtherTest() {
		return otherTest;
	}
	public void setOtherTest(String otherTest) {
		this.otherTest = otherTest;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Map<Object,Object> getLabResultMap() {
		return labResultMap;
	}
	public void setLabResultMap(Map<Object,Object> labResultMap) {
		this.labResultMap = labResultMap;
	}
	
	public boolean isRapidTest() {
		return rapidTest;
	}
	public void setRapidTest(boolean rapidTest) {
		this.rapidTest = rapidTest;
	}
	
	
	
	public int getLabSrcId() {
		return labSrcId;
	}
	
	public String getRapidTestType() {
		return rapidTestType;
	}
	public void setRapidTestType(String rapidTestType) {
		this.rapidTestType = rapidTestType;
	}
	public String getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}
	public String getAnalyteInterpretation() {
		return analyteInterpretation;
	}
	public void setAnalyteInterpretation(String analyteInterpretation) {
		this.analyteInterpretation = analyteInterpretation;
	}
	public void setLabSrcId(int labSrcId) {
		this.labSrcId = labSrcId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	@Override
	public String toString() {
		return "RawAcrfLabsVO [labSrcId=" + labSrcId + ", documentId=" + documentId + ", acrfSrcId=" + acrfSrcId + ", parentTestType=" + parentTestType + ", acrfTestCode=" + acrfTestCode + ", acrfResultCode=" + acrfResultCode + ", collectionDate=" + collectionDate + ", rapidTest=" + rapidTest + ", copiesMl=" + copiesMl + ", log=" + log + ", otherTest=" + otherTest + ", labResultMap=" + labResultMap + "]";
	}
	public int getAcrfSrcId() {
		return acrfSrcId;
	}
	public void setAcrfSrcId(int acrfSrcId) {
		this.acrfSrcId = acrfSrcId;
	}
	
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getIndexValue() {
		return indexValue;
	}
	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}
	public String getDignosticAlgorithm() {
		return dignosticAlgorithm;
	}
	public void setDignosticAlgorithm(String dignosticAlgorithm) {
		this.dignosticAlgorithm = dignosticAlgorithm;
	}
	public String getHiv1AbAnalyteResult() {
		return hiv1AbAnalyteResult;
	}
	public void setHiv1AbAnalyteResult(String hiv1AbAnalyteResult) {
		this.hiv1AbAnalyteResult = hiv1AbAnalyteResult;
	}
	public String getHiv1AbAnalyteResultIndex() {
		return hiv1AbAnalyteResultIndex;
	}
	public void setHiv1AbAnalyteResultIndex(String hiv1AbAnalyteResultIndex) {
		this.hiv1AbAnalyteResultIndex = hiv1AbAnalyteResultIndex;
	}
	public String getHiv2AbAnalyteResult() {
		return hiv2AbAnalyteResult;
	}
	public void setHiv2AbAnalyteResult(String hiv2AbAnalyteResult) {
		this.hiv2AbAnalyteResult = hiv2AbAnalyteResult;
	}
	public String getHiv2AbAnalyteResultIndex() {
		return hiv2AbAnalyteResultIndex;
	}
	public void setHiv2AbAnalyteResultIndex(String hiv2AbAnalyteResultIndex) {
		this.hiv2AbAnalyteResultIndex = hiv2AbAnalyteResultIndex;
	}
	public String getHiv1AgAnalyteResult() {
		return hiv1AgAnalyteResult;
	}
	public void setHiv1AgAnalyteResult(String hiv1AgAnalyteResult) {
		this.hiv1AgAnalyteResult = hiv1AgAnalyteResult;
	}
	public String getHiv1AgAnalyteResultIndex() {
		return hiv1AgAnalyteResultIndex;
	}
	public void setHiv1AgAnalyteResultIndex(String hiv1AgAnalyteResultIndex) {
		this.hiv1AgAnalyteResultIndex = hiv1AgAnalyteResultIndex;
	}
	
	
	public String getHiv1CmlAnalyteResult() {
		return hiv1CmlAnalyteResult;
	}
	public void setHiv1CmlAnalyteResult(String hiv1CmlAnalyteResult) {
		this.hiv1CmlAnalyteResult = hiv1CmlAnalyteResult;
	}
	public String getHiv1LogAnalyteResult() {
		return hiv1LogAnalyteResult;
	}
	public void setHiv1LogAnalyteResult(String hiv1LogAnalyteResult) {
		this.hiv1LogAnalyteResult = hiv1LogAnalyteResult;
	}
	public String getHiv12AbAnalyteResult() {
		return hiv12AbAnalyteResult;
	}
	public void setHiv12AbAnalyteResult(String hiv12AbAnalyteResult) {
		this.hiv12AbAnalyteResult = hiv12AbAnalyteResult;
	}
	public String getClia() {
		return clia;
	}
	public void setClia(String clia) {
		this.clia = clia;
	}
	public String getFacilityEharsUid() {
		return facilityEharsUid;
	}
	public void setFacilityEharsUid(String facilityEharsUid) {
		this.facilityEharsUid = facilityEharsUid;
	}
	public String getProviderFname() {
		return providerFname;
	}
	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}
	public String getProviderLname() {
		return providerLname;
	}
	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}
	public String getProviderMname() {
		return providerMname;
	}
	public void setProviderMname(String providerMname) {
		this.providerMname = providerMname;
	}
	public String getProviderSuffix() {
		return providerSuffix;
	}
	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}
	public String getTestSection() {
		return testSection;
	}
	public void setTestSection(String testSection) {
		this.testSection = testSection;
	}
	
	
	
	
	
}
