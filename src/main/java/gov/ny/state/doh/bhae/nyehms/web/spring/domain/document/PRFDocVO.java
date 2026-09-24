package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class PRFDocVO {
    private int documentId;
    private Long prfSrcId;
    private String fname="";
    private String stateno="";
    private String mname="";
    private String lname="";
    private String suffix="";
    private String altFname="";
    private String altMname="";
    private String altLname="";
    private String dobText="";
    private String dodText="";
    private String medRecordNumber="";
    private String din="";
    private String personAddress="";
    private String personCity="";
    private String personState="";
    private String personZip="";
    private String personPlus4="";
    private String personCounty="";
    private String personTelephone="";
    private String gender="";
    private String ethnicity="";
    private boolean raceWhite;
    private boolean raceBlack;
    private boolean raceAsian;
    private boolean raceIndian;
    private boolean racePacific;
    private boolean raceUnknown;
    private boolean raceOther;
    private String raceOtherText="";
    private String birthCountry="";
    private String risk01="";
    private String risk02="";
    private String risk03="";
    private String risk04="";
    private String risk05="";
    private String risk06="";
    private String risk07="";
    private String risk08="";
    private String risk09="";
    private String riskOtherText="";
    private String recvBloodProdDate="";
    private String recvBloodProdCountry="";
    private String admissionDate="";
    private boolean initialHivDiag;
    private String initialHivDiagDate="";
    private String informedHivPos="";
    private String informedHivPosDate="";
    private boolean previousHivDiag;
    private String previousHivDiagDate="";
    private boolean initialAidsDiag;
    private String initialAidsDiagDate="";
    private boolean previousAidsDiag;
    private String previousAidsDiagDate="";
    private String psRequested="";
    private String completerLname="";
    private String completerMi="";
    private String completerFname="";
    private String completerTitle="";
    private String completerPhone="";
    private String completerPhoneExt="";
    private String completerEmail="";
    private String facilityName="";
    private String facilityDept="";
    private String facilityType="";
    private String facilityAddress="";
    private String facilityCity="";
    private String facilityState="";
    private String facilityZip="";
    private String facilityPlus4="";
    private String facilityCounty="";
    private String completedDate="";
    private String contactLname="";
    private String contactFname="";
    private String contactMi="";
    private String contactTitle="";
    private String contactPhone="";
    private String contactPhoneExt="";
    private String provLname="";
    private String provFname="";
    private String provMi="";
    private String provSigned="";
    private String provSignedDate="";
    private String createId="";
    private String createDate="";
    private String entryReviewId="";
    private String entryReviewDate="";
    private String comments;
    private String personCensusTractSuffix="";
    private String personLatitude="";
    private String personLongitude="";
    private String facilityCensusTract="";
    private String facilityCensusTractSuffix="";
    private String facilityLatitude="";
    private String facilityLongitude="";
    private int matchKey;
    private String matchPass="";
    private String matchCd="";
    private String processed="";
    private int crbatchnum;
    private String reviewId="";
    private String reviewDate="";
    private boolean isPartnerAddAccess;
    private String stdFacilityAddress="";
    private String stdPersonAddrress="";    
    
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public Long getPrfSrcId() {
		return prfSrcId;
	}
	public void setPrfSrcId(Long prfSrcId) {
		this.prfSrcId = prfSrcId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getAltFname() {
		return altFname;
	}
	public void setAltFname(String altFname) {
		this.altFname = altFname;
	}
	public String getAltMname() {
		return altMname;
	}
	public void setAltMname(String altMname) {
		this.altMname = altMname;
	}
	public String getAltLname() {
		return altLname;
	}
	public void setAltLname(String altLname) {
		this.altLname = altLname;
	}
	public String getDobText() {
		return dobText;
	}
	public void setDobText(String dobText) {
		this.dobText = dobText;
	}
	public String getDodText() {
		return dodText;
	}
	public void setDodText(String dodText) {
		this.dodText = dodText;
	}
	public String getMedRecordNumber() {
		return medRecordNumber;
	}
	public void setMedRecordNumber(String medRecordNumber) {
		this.medRecordNumber = medRecordNumber;
	}
	public String getDin() {
		return din;
	}
	public void setDin(String din) {
		this.din = din;
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
	public String getPersonPlus4() {
		return personPlus4;
	}
	public void setPersonPlus4(String personPlus4) {
		this.personPlus4 = personPlus4;
	}
	public String getPersonCounty() {
		return personCounty;
	}
	public void setPersonCounty(String personCounty) {
		this.personCounty = personCounty;
	}
	public String getPersonTelephone() {
		return personTelephone;
	}
	public void setPersonTelephone(String personTelephone) {
		this.personTelephone = personTelephone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public boolean isRaceWhite() {
		return raceWhite;
	}
	public void setRaceWhite(boolean raceWhite) {
		this.raceWhite = raceWhite;
	}
	public boolean isRaceBlack() {
		return raceBlack;
	}
	public void setRaceBlack(boolean raceBlack) {
		this.raceBlack = raceBlack;
	}
	public boolean isRaceAsian() {
		return raceAsian;
	}
	public void setRaceAsian(boolean raceAsian) {
		this.raceAsian = raceAsian;
	}
	public boolean isRaceIndian() {
		return raceIndian;
	}
	public void setRaceIndian(boolean raceIndian) {
		this.raceIndian = raceIndian;
	}
	public boolean isRacePacific() {
		return racePacific;
	}
	public void setRacePacific(boolean racePacific) {
		this.racePacific = racePacific;
	}
	public boolean isRaceUnknown() {
		return raceUnknown;
	}
	public void setRaceUnknown(boolean raceUnknown) {
		this.raceUnknown = raceUnknown;
	}
	public boolean isRaceOther() {
		return raceOther;
	}
	public void setRaceOther(boolean raceOther) {
		this.raceOther = raceOther;
	}
	public String getRaceOtherText() {
		return raceOtherText;
	}
	public void setRaceOtherText(String raceOtherText) {
		this.raceOtherText = raceOtherText;
	}
	public String getBirthCountry() {
		return birthCountry;
	}
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	public String getRisk01() {
		return risk01;
	}
	public void setRisk01(String risk01) {
		this.risk01 = risk01;
	}
	public String getRisk02() {
		return risk02;
	}
	public void setRisk02(String risk02) {
		this.risk02 = risk02;
	}
	public String getRisk03() {
		return risk03;
	}
	public void setRisk03(String risk03) {
		this.risk03 = risk03;
	}
	public String getRisk04() {
		return risk04;
	}
	public void setRisk04(String risk04) {
		this.risk04 = risk04;
	}
	public String getRisk05() {
		return risk05;
	}
	public void setRisk05(String risk05) {
		this.risk05 = risk05;
	}
	public String getRisk06() {
		return risk06;
	}
	public void setRisk06(String risk06) {
		this.risk06 = risk06;
	}
	public String getRisk07() {
		return risk07;
	}
	public void setRisk07(String risk07) {
		this.risk07 = risk07;
	}
	public String getRisk08() {
		return risk08;
	}
	public void setRisk08(String risk08) {
		this.risk08 = risk08;
	}
	public String getRisk09() {
		return risk09;
	}
	public void setRisk09(String risk09) {
		this.risk09 = risk09;
	}
	public String getRiskOtherText() {
		return riskOtherText;
	}
	public void setRiskOtherText(String riskOtherText) {
		this.riskOtherText = riskOtherText;
	}
	public String getRecvBloodProdDate() {
		return recvBloodProdDate;
	}
	public void setRecvBloodProdDate(String recvBloodProdDate) {
		this.recvBloodProdDate = recvBloodProdDate;
	}
	public String getRecvBloodProdCountry() {
		return recvBloodProdCountry;
	}
	public void setRecvBloodProdCountry(String recvBloodProdCountry) {
		this.recvBloodProdCountry = recvBloodProdCountry;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public boolean isInitialHivDiag() {
		return initialHivDiag;
	}
	public void setInitialHivDiag(boolean initialHivDiag) {
		this.initialHivDiag = initialHivDiag;
	}
	public String getInitialHivDiagDate() {
		return initialHivDiagDate;
	}
	public void setInitialHivDiagDate(String initialHivDiagDate) {
		this.initialHivDiagDate = initialHivDiagDate;
	}
	public String getInformedHivPos() {
		return informedHivPos;
	}
	public void setInformedHivPos(String informedHivPos) {
		this.informedHivPos = informedHivPos;
	}
	public String getInformedHivPosDate() {
		return informedHivPosDate;
	}
	public void setInformedHivPosDate(String informedHivPosDate) {
		this.informedHivPosDate = informedHivPosDate;
	}
	public boolean isPreviousHivDiag() {
		return previousHivDiag;
	}
	public void setPreviousHivDiag(boolean previousHivDiag) {
		this.previousHivDiag = previousHivDiag;
	}
	public String getPreviousHivDiagDate() {
		return previousHivDiagDate;
	}
	public void setPreviousHivDiagDate(String previousHivDiagDate) {
		this.previousHivDiagDate = previousHivDiagDate;
	}
	public boolean isInitialAidsDiag() {
		return initialAidsDiag;
	}
	public void setInitialAidsDiag(boolean initialAidsDiag) {
		this.initialAidsDiag = initialAidsDiag;
	}
	public String getInitialAidsDiagDate() {
		return initialAidsDiagDate;
	}
	public void setInitialAidsDiagDate(String initialAidsDiagDate) {
		this.initialAidsDiagDate = initialAidsDiagDate;
	}
	public boolean isPreviousAidsDiag() {
		return previousAidsDiag;
	}
	public void setPreviousAidsDiag(boolean previousAidsDiag) {
		this.previousAidsDiag = previousAidsDiag;
	}
	public String getPreviousAidsDiagDate() {
		return previousAidsDiagDate;
	}
	public void setPreviousAidsDiagDate(String previousAidsDiagDate) {
		this.previousAidsDiagDate = previousAidsDiagDate;
	}
	public String getPsRequested() {
		return psRequested;
	}
	public void setPsRequested(String psRequested) {
		this.psRequested = psRequested;
	}
	public String getCompleterLname() {
		return completerLname;
	}
	public void setCompleterLname(String completerLname) {
		this.completerLname = completerLname;
	}
	public String getCompleterMi() {
		return completerMi;
	}
	public void setCompleterMi(String completerMi) {
		this.completerMi = completerMi;
	}
	public String getCompleterFname() {
		return completerFname;
	}
	public void setCompleterFname(String completerFname) {
		this.completerFname = completerFname;
	}
	public String getCompleterTitle() {
		return completerTitle;
	}
	public void setCompleterTitle(String completerTitle) {
		this.completerTitle = completerTitle;
	}
	public String getCompleterPhone() {
		return completerPhone;
	}
	public void setCompleterPhone(String completerPhone) {
		this.completerPhone = completerPhone;
	}
	public String getCompleterPhoneExt() {
		return completerPhoneExt;
	}
	public void setCompleterPhoneExt(String completerPhoneExt) {
		this.completerPhoneExt = completerPhoneExt;
	}
	public String getCompleterEmail() {
		return completerEmail;
	}
	public void setCompleterEmail(String completerEmail) {
		this.completerEmail = completerEmail;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityDept() {
		return facilityDept;
	}
	public void setFacilityDept(String facilityDept) {
		this.facilityDept = facilityDept;
	}
	public String getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
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
	public String getFacilityPlus4() {
		return facilityPlus4;
	}
	public void setFacilityPlus4(String facilityPlus4) {
		this.facilityPlus4 = facilityPlus4;
	}
	public String getFacilityCounty() {
		return facilityCounty;
	}
	public void setFacilityCounty(String facilityCounty) {
		this.facilityCounty = facilityCounty;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getContactLname() {
		return contactLname;
	}
	public void setContactLname(String contactLname) {
		this.contactLname = contactLname;
	}
	public String getContactFname() {
		return contactFname;
	}
	public void setContactFname(String contactFname) {
		this.contactFname = contactFname;
	}
	public String getContactMi() {
		return contactMi;
	}
	public void setContactMi(String contactMi) {
		this.contactMi = contactMi;
	}
	public String getContactTitle() {
		return contactTitle;
	}
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactPhoneExt() {
		return contactPhoneExt;
	}
	public void setContactPhoneExt(String contactPhoneExt) {
		this.contactPhoneExt = contactPhoneExt;
	}
	public String getProvLname() {
		return provLname;
	}
	public void setProvLname(String provLname) {
		this.provLname = provLname;
	}
	public String getProvFname() {
		return provFname;
	}
	public void setProvFname(String provFname) {
		this.provFname = provFname;
	}
	public String getProvMi() {
		return provMi;
	}
	public void setProvMi(String provMi) {
		this.provMi = provMi;
	}
	public String getProvSigned() {
		return provSigned;
	}
	public void setProvSigned(String provSigned) {
		this.provSigned = provSigned;
	}
	public String getProvSignedDate() {
		return provSignedDate;
	}
	public void setProvSignedDate(String provSignedDate) {
		this.provSignedDate = provSignedDate;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEntryReviewId() {
		return entryReviewId;
	}
	public void setEntryReviewId(String entryReviewId) {
		this.entryReviewId = entryReviewId;
	}
	public String getEntryReviewDate() {
		return entryReviewDate;
	}
	public void setEntryReviewDate(String entryReviewDate) {
		this.entryReviewDate = entryReviewDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPersonCensusTractSuffix() {
		return personCensusTractSuffix;
	}
	public void setPersonCensusTractSuffix(String personCensusTractSuffix) {
		this.personCensusTractSuffix = personCensusTractSuffix;
	}
	public String getPersonLatitude() {
		return personLatitude;
	}
	public void setPersonLatitude(String personLatitude) {
		this.personLatitude = personLatitude;
	}
	public String getPersonLongitude() {
		return personLongitude;
	}
	public void setPersonLongitude(String personLongitude) {
		this.personLongitude = personLongitude;
	}
	public String getFacilityCensusTract() {
		return facilityCensusTract;
	}
	public void setFacilityCensusTract(String facilityCensusTract) {
		this.facilityCensusTract = facilityCensusTract;
	}
	public String getFacilityCensusTractSuffix() {
		return facilityCensusTractSuffix;
	}
	public void setFacilityCensusTractSuffix(String facilityCensusTractSuffix) {
		this.facilityCensusTractSuffix = facilityCensusTractSuffix;
	}
	public String getFacilityLatitude() {
		return facilityLatitude;
	}
	public void setFacilityLatitude(String facilityLatitude) {
		this.facilityLatitude = facilityLatitude;
	}
	public String getFacilityLongitude() {
		return facilityLongitude;
	}
	public void setFacilityLongitude(String facilityLongitude) {
		this.facilityLongitude = facilityLongitude;
	}
	public int getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(int matchKey) {
		this.matchKey = matchKey;
	}
	public String getMatchPass() {
		return matchPass;
	}
	public void setMatchPass(String matchPass) {
		this.matchPass = matchPass;
	}
	public String getMatchCd() {
		return matchCd;
	}
	public void setMatchCd(String matchCd) {
		this.matchCd = matchCd;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public int getCrbatchnum() {
		return crbatchnum;
	}
	public void setCrbatchnum(int crbatchnum) {
		this.crbatchnum = crbatchnum;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public boolean isPartnerAddAccess() {
		return isPartnerAddAccess;
	}
	public void setPartnerAddAccess(boolean isPartnerAddAccess) {
		this.isPartnerAddAccess = isPartnerAddAccess;
	}	
	public String getStdFacilityAddress() {
		return stdFacilityAddress;
	}
	public void setStdFacilityAddress(String stdFacilityAddress) {
		this.stdFacilityAddress = stdFacilityAddress;
	}
	public String getStdPersonAddrress() {
		return stdPersonAddrress;
	}
	public void setStdPersonAddrress(String stdPersonAddrress) {
		this.stdPersonAddrress = stdPersonAddrress;
	}
	@Override
	public String toString() {
		return "PRFDocVO [documentId=" + documentId + ", prfSrcId=" + prfSrcId + ", fname=" + fname + ", stateno=" + stateno
				+ ", mname=" + mname + ", lname=" + lname + ", suffix=" + suffix + ", altFname=" + altFname + ", altMname="
				+ altMname + ", altLname=" + altLname + ", dobText=" + dobText + ", dodText=" + dodText + ", medRecordNumber="
				+ medRecordNumber + ", din=" + din + ", personAddress=" + personAddress + ", personCity=" + personCity
				+ ", personState=" + personState + ", personZip=" + personZip + ", personPlus4=" + personPlus4 + ", personCounty="
				+ personCounty + ", personTelephone=" + personTelephone + ", gender=" + gender + ", ethnicity=" + ethnicity
				+ ", raceWhite=" + raceWhite + ", raceBlack=" + raceBlack + ", raceAsian=" + raceAsian + ", raceIndian="
				+ raceIndian + ", racePacific=" + racePacific + ", raceUnknown=" + raceUnknown + ", raceOther=" + raceOther
				+ ", raceOtherText=" + raceOtherText + ", birthCountry=" + birthCountry + ", risk01=" + risk01 + ", risk02="
				+ risk02 + ", risk03=" + risk03 + ", risk04=" + risk04 + ", risk05=" + risk05 + ", risk06=" + risk06 + ", risk07="
				+ risk07 + ", risk08=" + risk08 + ", risk09=" + risk09 + ", riskOtherText=" + riskOtherText
				+ ", recvBloodProdDate=" + recvBloodProdDate + ", recvBloodProdCountry=" + recvBloodProdCountry
				+ ", admissionDate=" + admissionDate + ", initialHivDiag=" + initialHivDiag + ", initialHivDiagDate="
				+ initialHivDiagDate + ", informedHivPos=" + informedHivPos + ", informedHivPosDate=" + informedHivPosDate
				+ ", previousHivDiag=" + previousHivDiag + ", previousHivDiagDate=" + previousHivDiagDate + ", initialAidsDiag="
				+ initialAidsDiag + ", initialAidsDiagDate=" + initialAidsDiagDate + ", previousAidsDiag=" + previousAidsDiag
				+ ", previousAidsDiagDate=" + previousAidsDiagDate + ", psRequested=" + psRequested + ", completerLname="
				+ completerLname + ", completerMi=" + completerMi + ", completerFname=" + completerFname + ", completerTitle="
				+ completerTitle + ", completerPhone=" + completerPhone + ", completerPhoneExt=" + completerPhoneExt
				+ ", completerEmail=" + completerEmail + ", facilityName=" + facilityName + ", facilityDept=" + facilityDept
				+ ", facilityType=" + facilityType + ", facilityAddress=" + facilityAddress + ", facilityCity=" + facilityCity
				+ ", facilityState=" + facilityState + ", facilityZip=" + facilityZip + ", facilityPlus4=" + facilityPlus4
				+ ", facilityCounty=" + facilityCounty + ", completedDate=" + completedDate + ", contactLname=" + contactLname
				+ ", contactFname=" + contactFname + ", contactMi=" + contactMi + ", contactTitle=" + contactTitle
				+ ", contactPhone=" + contactPhone + ", contactPhoneExt=" + contactPhoneExt + ", provLname=" + provLname
				+ ", provFname=" + provFname + ", provMi=" + provMi + ", provSigned=" + provSigned + ", provSignedDate="
				+ provSignedDate + ", createId=" + createId + ", createDate=" + createDate + ", entryReviewId=" + entryReviewId
				+ ", entryReviewDate=" + entryReviewDate + ", comments=" + comments + ", personCensusTractSuffix="
				+ personCensusTractSuffix + ", personLatitude=" + personLatitude + ", personLongitude=" + personLongitude
				+ ", facilityCensusTract=" + facilityCensusTract + ", facilityCensusTractSuffix=" + facilityCensusTractSuffix
				+ ", facilityLatitude=" + facilityLatitude + ", facilityLongitude=" + facilityLongitude + ", matchKey=" + matchKey
				+ ", matchPass=" + matchPass + ", matchCd=" + matchCd + ", processed=" + processed + ", crbatchnum=" + crbatchnum
				+ ", reviewId=" + reviewId + ", reviewDate=" + reviewDate + ", isPartnerAddAccess=" + isPartnerAddAccess
				+ ", stdFacilityAddress=" + stdFacilityAddress + ", stdPersonAddrress=" + stdPersonAddrress + "]";
	}
	
	
	
    
}