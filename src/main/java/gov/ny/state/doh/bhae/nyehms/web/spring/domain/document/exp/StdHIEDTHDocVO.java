package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp;

import java.sql.Date;

public class StdHIEDTHDocVO {

	private int documentId;
	private String sourceFacility;
    private String MRN;
    private String HXMPIID;
    private String mpiid;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String deathDate;
    private String deathTime;
    private Date receivedDate;
    private String hieName;
    private String stateno;
    private int currentProcessStatus;
    private String match_cd;
	private int matchPass;
	private int matchWeight;
	private String matchStateNo;
	private int   qsParentDocId;
	private Date matchedProcessDate;
	private Date eharsImportDate;
	private String eharsStatusCd; 
	private Date nycShipDate;
	
	
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getSourceFacility() {
		return sourceFacility;
	}
	public void setSourceFacility(String sourceFacility) {
		this.sourceFacility = sourceFacility;
	}
	public String getMRN() {
		return MRN;
	}
	public void setMRN(String mRN) {
		MRN = mRN;
	}
	public String getHXMPIID() {
		return HXMPIID;
	}
	public void setHXMPIID(String hXMPIID) {
		HXMPIID = hXMPIID;
	}
	public String getMpiid() {
		return mpiid;
	}
	public void setMpiid(String mpiid) {
		this.mpiid = mpiid;
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
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}
	public String getDeathTime() {
		return deathTime;
	}
	public void setDeathTime(String deathTime) {
		this.deathTime = deathTime;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getHieName() {
		return hieName;
	}
	public void setHieName(String hieName) {
		this.hieName = hieName;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public int getCurrentProcessStatus() {
		return currentProcessStatus;
	}
	public void setCurrentProcessStatus(int currentProcessStatus) {
		this.currentProcessStatus = currentProcessStatus;
	}
	
	public String getMatch_cd() {
		return match_cd;
	}
	public void setMatch_cd(String match_cd) {
		this.match_cd = match_cd;
	}
	public int getMatchPass() {
		return matchPass;
	}
	public void setMatchPass(int matchPass) {
		this.matchPass = matchPass;
	}
	public int getMatchWeight() {
		return matchWeight;
	}
	public void setMatchWeight(int matchWeight) {
		this.matchWeight = matchWeight;
	}
	public String getMatchStateNo() {
		return matchStateNo;
	}
	public void setMatchStateNo(String matchStateNo) {
		this.matchStateNo = matchStateNo;
	}
	public int getQsParentDocId() {
		return qsParentDocId;
	}
	public void setQsParentDocId(int qsParentDocId) {
		this.qsParentDocId = qsParentDocId;
	}
	public Date getMatchedProcessDate() {
		return matchedProcessDate;
	}
	public void setMatchedProcessDate(Date matchedProcessDate) {
		this.matchedProcessDate = matchedProcessDate;
	}
	public Date getEharsImportDate() {
		return eharsImportDate;
	}
	public void setEharsImportDate(Date eharsImportDate) {
		this.eharsImportDate = eharsImportDate;
	}
	public String getEharsStatusCd() {
		return eharsStatusCd;
	}
	public void setEharsStatusCd(String eharsStatusCd) {
		this.eharsStatusCd = eharsStatusCd;
	}
	public Date getNycShipDate() {
		return nycShipDate;
	}
	public void setNycShipDate(Date nycShipDate) {
		this.nycShipDate = nycShipDate;
	}
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
}
