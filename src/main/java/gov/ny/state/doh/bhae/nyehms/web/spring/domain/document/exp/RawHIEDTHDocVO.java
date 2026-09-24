package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.exp;

import java.sql.Date;

public class RawHIEDTHDocVO 
{
	//private int serialID;
    private int documentId;
    private String sourceFacility;
    private String MRN;
    private String HXMPIID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String deathDate;
    private String deathTime;
    private String currentProcessStatus;
    private long maxUTIICHECKEDRESULTKEY;
    private long minUTIICHECKEDRESULTKEY;
    private Date receivedDate;
    private String hieName;
    
	
	
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

	public String getCurrentProcessStatus() {
		return currentProcessStatus;
	}

	public void setCurrentProcessStatus(String currentProcessStatus) {
		this.currentProcessStatus = currentProcessStatus;
	}

	public long getMaxUTIICHECKEDRESULTKEY() {
		return maxUTIICHECKEDRESULTKEY;
	}

	public void setMaxUTIICHECKEDRESULTKEY(long maxUTIICHECKEDRESULTKEY) {
		this.maxUTIICHECKEDRESULTKEY = maxUTIICHECKEDRESULTKEY;
	}

	public long getMinUTIICHECKEDRESULTKEY() {
		return minUTIICHECKEDRESULTKEY;
	}

	public void setMinUTIICHECKEDRESULTKEY(long minUTIICHECKEDRESULTKEY) {
		this.minUTIICHECKEDRESULTKEY = minUTIICHECKEDRESULTKEY;
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
	
	private String DISCARD_STATUS;
	
	public String getDISCARD_STATUS() {
		return DISCARD_STATUS;
	}

	public void setDISCARD_STATUS(String dISCARD_STATUS) {
		DISCARD_STATUS = dISCARD_STATUS;;
	}
	

}
