package gov.ny.state.doh.bhae.nyehms.web.spring.domain.person;

import java.io.Serializable;

public class PersonDocHistoryVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String documentId="";
	private String documentType="";
	private String documentDescription="";
	private String assignmentId="";
	private String receivedDate="";
	private String associationDate="";
	private String firstName="";
	private String lastName="";
	private String dateOfBirth="";
	private String dateOfDeath="";
	private String ssn="";
	private String race="";
	private String ethnicity="";
	private String address="";
	private String collectionDate="";
	private String recievedDate = "";
	private String providerAddress = "";
	private String providerName = "";
	private String sentMessageFlag;
	private String returnMessageFlag;
	private String assignmentStatus;
	private String facilityName;
	private String facilityAddress;
	private String testResult;
	private String accessionNumber;
	private String fileType;
	private String altValues;
	private String associationStatus;
	private String stateno;
	private String rasDocId="";
	private String discardStatus;
	private String hl7Exists;
	private String nycRejectDocInd="";
	private String demoDocId="";	
	private String laftFollowupExists="";
	private String sierraFlag;
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentDescription() {
		return documentDescription;
	}
	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAssociationDate() {
		return associationDate;
	}
	public void setAssociationDate(String associationDate) {
		this.associationDate = associationDate;
	}
	
	
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getRecievedDate() {
		return recievedDate;
	}
	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}
	public String getProviderAddress() {
		return providerAddress;
	}
	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	
	
	
	
	public String getSentMessageFlag() {
		return sentMessageFlag;
	}
	public void setSentMessageFlag(String sentMessageFlag) {
		this.sentMessageFlag = sentMessageFlag;
	}
	public String getReturnMessageFlag() {
		return returnMessageFlag;
	}
	public void setReturnMessageFlag(String returnMessageFlag) {
		this.returnMessageFlag = returnMessageFlag;
	}
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityAddress() {
		return facilityAddress;
	}
	public void setFacilityAddress(String facilityAddress) {
		this.facilityAddress = facilityAddress;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getAccessionNumber() {
		return accessionNumber;
	}
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getAltValues() {
		return altValues;
	}
	public void setAltValues(String altValues) {
		this.altValues = altValues;
	}
	public String getAssociationStatus() {
		return associationStatus;
	}
	public void setAssociationStatus(String associationStatus) {
		this.associationStatus = associationStatus;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getRasDocId() {
		return rasDocId;
	}
	public void setRasDocId(String rasDocId) {
		this.rasDocId = rasDocId;
	}
	public String getDiscardStatus() {
		return discardStatus;
	}
	public void setDiscardStatus(String discardStatus) {
		this.discardStatus = discardStatus;
	}
	public String getHl7Exists() {
		return hl7Exists;
	}
	public void setHl7Exists(String hl7Exists) {
		this.hl7Exists = hl7Exists;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getNycRejectDocInd() {
		return nycRejectDocInd;
	}
	public void setNycRejectDocInd(String nycRejectDocInd) {
		this.nycRejectDocInd = nycRejectDocInd;
	}
	public String getDemoDocId() {
		return demoDocId;
	}
	public void setDemoDocId(String demoDocId) {
		this.demoDocId = demoDocId;
	}
	public String getLaftFollowupExists() {
		return laftFollowupExists;
	}
	public void setLaftFollowupExists(String laftFollowupExists) {
		this.laftFollowupExists = laftFollowupExists;
	}
	public String getSierraFlag() {
		return sierraFlag;
	}
	public void setSierraFlag(String sierraFlag) {
		this.sierraFlag = sierraFlag;
	}
	
}
