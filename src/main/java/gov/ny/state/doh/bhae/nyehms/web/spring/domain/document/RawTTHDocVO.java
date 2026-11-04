package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class RawTTHDocVO { 
	
	
	private int acrfDocId;
	private int prfDocId;
	private int airsDocId;
	private int documentId;
	private String testTreatmentHistorySource="";
	private String dateReported;
	private String prevPosHivTest="";
	private String dateFirstPosHivTest;
	private String negHivTest="";
	private String dateLastNegHivTest;
	private String numNegTestUnkRef="";
	private int numNegTestBeforePos;
	private String takenArvs="";
	private String arvMeds="";
	private String dateArvsBegan;
	private String dateLastUsedArvs;
	
	public String getTestTreatmentHistorySource() {
		return testTreatmentHistorySource;
	}
	public void setTestTreatmentHistorySource(String testTreatmentHistorySource) {
		this.testTreatmentHistorySource = testTreatmentHistorySource;
	}
	public String getDateReported() {
		return dateReported;
	}
	public void setDateReported(String dateReported) {
		this.dateReported = dateReported;
	}
	public String getPrevPosHivTest() {
		return prevPosHivTest;
	}
	public void setPrevPosHivTest(String prevPosHivTest) {
		this.prevPosHivTest = prevPosHivTest;
	}
	public String getDateFirstPosHivTest() {
		return dateFirstPosHivTest;
	}
	public void setDateFirstPosHivTest(String dateFirstPosHivTest) {
		this.dateFirstPosHivTest = dateFirstPosHivTest;
	}
	public String getNegHivTest() {
		return negHivTest;
	}
	public void setNegHivTest(String negHivTest) {
		this.negHivTest = negHivTest;
	}
	public String getDateLastNegHivTest() {
		return dateLastNegHivTest;
	}
	public void setDateLastNegHivTest(String dateLastNegHivTest) {
		this.dateLastNegHivTest = dateLastNegHivTest;
	}
	public String getNumNegTestUnkRef() {
		return numNegTestUnkRef;
	}
	public void setNumNegTestUnkRef(String numNegTestUnkRef) {
		this.numNegTestUnkRef = numNegTestUnkRef;
	}
	public int getNumNegTestBeforePos() {
		return numNegTestBeforePos;
	}
	public void setNumNegTestBeforePos(int numNegTestBeforePos) {
		this.numNegTestBeforePos = numNegTestBeforePos;
	}
	public String getTakenArvs() {
		return takenArvs;
	}
	public void setTakenArvs(String takenArvs) {
		this.takenArvs = takenArvs;
	}
	public String getArvMeds() {
		return arvMeds;
	}
	public void setArvMeds(String arvMeds) {
		this.arvMeds = arvMeds;
	}
	public String getDateArvsBegan() {
		return dateArvsBegan;
	}
	public void setDateArvsBegan(String dateArvsBegan) {
		this.dateArvsBegan = dateArvsBegan;
	}
	public String getDateLastUsedArvs() {
		return dateLastUsedArvs;
	}
	public void setDateLastUsedArvs(String dateLastUsedArvs) {
		this.dateLastUsedArvs = dateLastUsedArvs;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	
	
	public int getAcrfDocId() {
		return acrfDocId;
	}
	public void setAcrfDocId(int acrfDocId) {
		this.acrfDocId = acrfDocId;
	}
	public int getPrfDocId() {
		return prfDocId;
	}
	public void setPrfDocId(int prfDocId) {
		this.prfDocId = prfDocId;
	}
	public int getAirsDocId() {
		return airsDocId;
	}
	public void setAirsDocId(int airsDocId) {
		this.airsDocId = airsDocId;
	}
	@Override
	public String toString() {
		return "RawAcrfDocVO [acrfDocId=" + acrfDocId + ", prfDocId=" + prfDocId + ", airsDocId=" + airsDocId + ", documentId=" + documentId + ", testTreatmentHistorySource=" + testTreatmentHistorySource + ", dateReported=" + dateReported
				+ ", prevPosHivTest=" + prevPosHivTest + ", dateFirstPosHivTest=" + dateFirstPosHivTest + ", negHivTest="
				+ negHivTest + ", dateLastNegHivTest=" + dateLastNegHivTest + ", numNegTestUnkRef=" + numNegTestUnkRef
				+ ", numNegTestBeforePos=" + numNegTestBeforePos + ", takenArvs=" + takenArvs + ", arvMeds=" + arvMeds
				+ ", dateArvsBegan=" + dateArvsBegan + ", dateLastUsedArvs=" + dateLastUsedArvs + "]";
	}
	
	
}
