package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import java.sql.Date;

public class StdHL7DocVO {

	private int documentId;
	private int uphnId;
	private String din;
	private String ethinicity;
	private String fName;
	private String gender;
	private String lName;
	private String medRecordNos;
	private String mName;
	//private int mpi;
	private String mpi;
	private int nysid;
	private String personAddress;
	private String personCity;
	private String personCounty;
	private String personExternalID;
	private String personInternalId;
	private String personPhone;
	private String personPlus4;
	private String personState;
	private String personZip;
	private String providerFname;
	private String providerID;
	private String providerIDAuth;
	private String providerLname;
	private String raceAsian;
	private String raceBlack;
	private String raceIndian;
	private String raceOther;
	private String racePacific;
	private String raceWhite;
	private String ssn;
	private String suffix;
	private String birthCountry;
	private int currentProcessStatus;
	private Date dob;
	private Date dateReceived;
	private String eharsImportDate;
	private String eharsStatusCD;
	private String facilityCode;
	private String facilityName;
	private String hl7MessageType;
	private String insuranceNos;
	private String medicaidNos;
	private String nycShipDate;
	private String provCounty;	
	private String raceUnknown;
	private String veteranStatus;
	private String dobText;
	private int ccdMatchDocId;
	private String ccdMatchedStateNo;
	private Date matchProcessDate;
	private String personCountyFIPS;
	private String facilityAddress;
	private String facilityCity;
	private String facilityState;
	private String facilityZip;
	private String raceUntranslatable;
	private String raceOtherText;
	private String facilityCountyFips;
	private String stateno;
	
	private int fname_count;
	private int mname_count;
	private int lname_count;
	private int ethnicity_count;
	private int mpi_count;
	private int ssn_count;
	private int dob_count;
	private int gender_count;
	private int mrn_count;
	private String startDate;
	private String endDate;	
	
	
	
	
	public String getMpi() {
		return mpi;
	}
	public void setMpi(String mpi) {
		this.mpi = mpi;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getUphnId() {
		return uphnId;
	}
	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}
	public String getDin() {
		return din;
	}
	public void setDin(String din) {
		this.din = din;
	}
	public String getEthinicity() {
		return ethinicity;
	}
	public void setEthinicity(String ethinicity) {
		this.ethinicity = ethinicity;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getMedRecordNos() {
		return medRecordNos;
	}
	public void setMedRecordNos(String medRecordNos) {
		this.medRecordNos = medRecordNos;
	}
//	
//	public int getMpi() {
//		return mpi;
//	}
//	public void setMpi(int mpi) {
//		this.mpi = mpi;
//	}
	public int getNysid() {
		return nysid;
	}
	public void setNysid(int nysid) {
		this.nysid = nysid;
	}
	public String getPersonAddress() {
		return personAddress;
	}
	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}
	public String getPersonCity() {
		return personCity;
	}
	public void setPersonCity(String personCity) {
		this.personCity = personCity;
	}
	public String getPersonCounty() {
		return personCounty;
	}
	public void setPersonCounty(String personCounty) {
		this.personCounty = personCounty;
	}
	public String getPersonExternalID() {
		return personExternalID;
	}
	public void setPersonExternalID(String personExternalID) {
		this.personExternalID = personExternalID;
	}
	public String getPersonInternalId() {
		return personInternalId;
	}
	public void setPersonInternalId(String personInternalId) {
		this.personInternalId = personInternalId;
	}
	public String getPersonPhone() {
		return personPhone;
	}
	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}
	public String getPersonPlus4() {
		return personPlus4;
	}
	public void setPersonPlus4(String personPlus4) {
		this.personPlus4 = personPlus4;
	}
	public String getPersonState() {
		return personState;
	}
	public void setPersonState(String personState) {
		this.personState = personState;
	}
	public String getPersonZip() {
		return personZip;
	}
	public void setPersonZip(String personZip) {
		this.personZip = personZip;
	}
	public String getProviderFname() {
		return providerFname;
	}
	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getProviderIDAuth() {
		return providerIDAuth;
	}
	public void setProviderIDAuth(String providerIDAuth) {
		this.providerIDAuth = providerIDAuth;
	}
	public String getProviderLname() {
		return providerLname;
	}
	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}
	public String getRaceAsian() {
		return raceAsian;
	}
	public void setRaceAsian(String raceAsian) {
		this.raceAsian = raceAsian;
	}
	public String getRaceBlack() {
		return raceBlack;
	}
	public void setRaceBlack(String raceBlack) {
		this.raceBlack = raceBlack;
	}
	public String getRaceIndian() {
		return raceIndian;
	}
	public void setRaceIndian(String raceIndian) {
		this.raceIndian = raceIndian;
	}
	public String getRaceOther() {
		return raceOther;
	}
	public void setRaceOther(String raceOther) {
		this.raceOther = raceOther;
	}
	public String getRacePacific() {
		return racePacific;
	}
	public void setRacePacific(String racePacific) {
		this.racePacific = racePacific;
	}
	public String getRaceWhite() {
		return raceWhite;
	}
	public void setRaceWhite(String raceWhite) {
		this.raceWhite = raceWhite;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getBirthCountry() {
		return birthCountry;
	}
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	public int getCurrentProcessStatus() {
		return currentProcessStatus;
	}
	public void setCurrentProcessStatus(int currentProcessStatus) {
		this.currentProcessStatus = currentProcessStatus;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public String getEharsImportDate() {
		return eharsImportDate;
	}
	public void setEharsImportDate(String eharsImportDate) {
		this.eharsImportDate = eharsImportDate;
	}
	public String getEharsStatusCD() {
		return eharsStatusCD;
	}
	public void setEharsStatusCD(String eharsStatusCD) {
		this.eharsStatusCD = eharsStatusCD;
	}
	public String getFacilityCode() {
		return facilityCode;
	}
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getHl7MessageType() {
		return hl7MessageType;
	}
	public void setHl7MessageType(String hl7MessageType) {
		this.hl7MessageType = hl7MessageType;
	}
	public String getInsuranceNos() {
		return insuranceNos;
	}
	public void setInsuranceNos(String insuranceNos) {
		this.insuranceNos = insuranceNos;
	}
	public String getMedicaidNos() {
		return medicaidNos;
	}
	public void setMedicaidNos(String medicaidNos) {
		this.medicaidNos = medicaidNos;
	}
	public String getNycShipDate() {
		return nycShipDate;
	}
	public void setNycShipDate(String nycShipDate) {
		this.nycShipDate = nycShipDate;
	}
	public String getProvCounty() {
		return provCounty;
	}
	public void setProvCounty(String provCounty) {
		this.provCounty = provCounty;
	}
	public String getRaceUnknown() {
		return raceUnknown;
	}
	public void setRaceUnknown(String raceUnknown) {
		this.raceUnknown = raceUnknown;
	}
	public String getVeteranStatus() {
		return veteranStatus;
	}
	public void setVeteranStatus(String veteranStatus) {
		this.veteranStatus = veteranStatus;
	}
	public String getDobText() {
		return dobText;
	}
	public void setDobText(String dobText) {
		this.dobText = dobText;
	}
	public int getCcdMatchDocId() {
		return ccdMatchDocId;
	}
	public void setCcdMatchDocId(int ccdMatchDocId) {
		this.ccdMatchDocId = ccdMatchDocId;
	}
	public String getCcdMatchedStateNo() {
		return ccdMatchedStateNo;
	}
	public void setCcdMatchedStateNo(String ccdMatchedStateNo) {
		this.ccdMatchedStateNo = ccdMatchedStateNo;
	}
	public Date getMatchProcessDate() {
		return matchProcessDate;
	}
	public void setMatchProcessDate(Date matchProcessDate) {
		this.matchProcessDate = matchProcessDate;
	}
	public String getPersonCountyFIPS() {
		return personCountyFIPS;
	}
	public void setPersonCountyFIPS(String personCountyFIPS) {
		this.personCountyFIPS = personCountyFIPS;
	}
	public String getFacilityAddress() {
		return facilityAddress;
	}
	public void setFacilityAddress(String facilityAddress) {
		this.facilityAddress = facilityAddress;
	}
	public String getFacilityCity() {
		return facilityCity;
	}
	public void setFacilityCity(String facilityCity) {
		this.facilityCity = facilityCity;
	}
	public String getFacilityState() {
		return facilityState;
	}
	public void setFacilityState(String facilityState) {
		this.facilityState = facilityState;
	}
	public String getFacilityZip() {
		return facilityZip;
	}
	public void setFacilityZip(String facilityZip) {
		this.facilityZip = facilityZip;
	}
	public String getRaceUntranslatable() {
		return raceUntranslatable;
	}
	public void setRaceUntranslatable(String raceUntranslatable) {
		this.raceUntranslatable = raceUntranslatable;
	}
	public String getRaceOtherText() {
		return raceOtherText;
	}
	public void setRaceOtherText(String raceOtherText) {
		this.raceOtherText = raceOtherText;
	}
	public String getFacilityCountyFips() {
		return facilityCountyFips;
	}
	public void setFacilityCountyFips(String facilityCountyFips) {
		this.facilityCountyFips = facilityCountyFips;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public int getFname_count() {
		return fname_count;
	}
	public void setFname_count(int fname_count) {
		this.fname_count = fname_count;
	}
	
	public int getLname_count() {
		return lname_count;
	}
	public void setLname_count(int lname_count) {
		this.lname_count = lname_count;
	}
	public int getEthnicity_count() {
		return ethnicity_count;
	}
	public void setEthnicity_count(int ethnicity_count) {
		this.ethnicity_count = ethnicity_count;
	}
	public int getMpi_count() {
		return mpi_count;
	}
	public void setMpi_count(int mpi_count) {
		this.mpi_count = mpi_count;
	}
	public int getSsn_count() {
		return ssn_count;
	}
	public void setSsn_count(int ssn_count) {
		this.ssn_count = ssn_count;
	}
	public int getDob_count() {
		return dob_count;
	}
	public void setDob_count(int dob_count) {
		this.dob_count = dob_count;
	}
	public int getGender_count() {
		return gender_count;
	}
	public void setGender_count(int gender_count) {
		this.gender_count = gender_count;
	}
	public int getMrn_count() {
		return mrn_count;
	}
	public void setMrn_count(int mrn_count) {
		this.mrn_count = mrn_count;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getMname_count() {
		return mname_count;
	}
	public void setMname_count(int mname_count) {
		this.mname_count = mname_count;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
