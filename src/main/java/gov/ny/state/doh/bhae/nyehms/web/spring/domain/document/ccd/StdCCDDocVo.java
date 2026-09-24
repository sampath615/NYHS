package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import java.sql.Date;

public class StdCCDDocVo {
	
		private int documentId;
		private String fName;
		private String mName;
		private String lName;
		private String dobText;
		private String gender;
		private String ethinicity;
		private String raceWhite;
		private String raceBlack;
		private String raceAsian;
		private String raceIndian;
		private String racePacific;
		private String raceUnknown;
		private String raceOther;
		private String raceOtherText;
		private String language;
		private String personAddress;
		private String personCity;
		private String personState;
		private String personZip;
		private String personPlus4;
		private String personCounty;
		//private String PERSON_TELEPHONE;
		private String personLatitude;
		private String personLongitude;
		private String personCensusTract;
		private int personAddressId;
		private String personCountry;
		private String personPhoneHome;
		private String personPhoneCell;
		private String personPhoneWork;
		private String addressUse;
		private String useablePeriodLow;
		private String useablePeriodHigh;
		private Date createDate;
		private String mpi;
		private String matchCd;
		private int currentProcessStatus;
		private int matchPass;
		private int matchWeight;
		private String matchedStateno;
		private int qsParentDocId;
		private Date matchedProcessDate;
		private String stateno;
		private Date eharsImportDate;
		private String eharsStatusCd; 
		
		public int getDocumentId() {
			return documentId;
		}
		public void setDocumentId(int documentId) {
			this.documentId = documentId;
		}
		public String getfName() {
			return fName;
		}
		public void setfName(String fName) {
			this.fName = fName;
		}
		public String getmName() {
			return mName;
		}
		public void setmName(String mName) {
			this.mName = mName;
		}
		public String getlName() {
			return lName;
		}
		public void setlName(String lName) {
			this.lName = lName;
		}
		public String getDobText() {
			return dobText;
		}
		public void setDobText(String dobText) {
			this.dobText = dobText;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getEthinicity() {
			return ethinicity;
		}
		public void setEthinicity(String ethinicity) {
			this.ethinicity = ethinicity;
		}
		public String getRaceWhite() {
			return raceWhite;
		}
		public void setRaceWhite(String raceWhite) {
			this.raceWhite = raceWhite;
		}
		public String getRaceBlack() {
			return raceBlack;
		}
		public void setRaceBlack(String raceBlack) {
			this.raceBlack = raceBlack;
		}
		public String getRaceAsian() {
			return raceAsian;
		}
		public void setRaceAsian(String raceAsian) {
			this.raceAsian = raceAsian;
		}
		public String getRaceIndian() {
			return raceIndian;
		}
		public void setRaceIndian(String raceIndian) {
			this.raceIndian = raceIndian;
		}
		public String getRacePacific() {
			return racePacific;
		}
		public void setRacePacific(String racePacific) {
			this.racePacific = racePacific;
		}
		public String getRaceUnknown() {
			return raceUnknown;
		}
		public void setRaceUnknown(String raceUnknown) {
			this.raceUnknown = raceUnknown;
		}
		public String getRaceOther() {
			return raceOther;
		}
		public void setRaceOther(String raceOther) {
			this.raceOther = raceOther;
		}
		public String getRaceOtherText() {
			return raceOtherText;
		}
		public void setRaceOtherText(String raceOtherText) {
			this.raceOtherText = raceOtherText;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
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
		public String getPersonCensusTract() {
			return personCensusTract;
		}
		public void setPersonCensusTract(String personCensusTract) {
			this.personCensusTract = personCensusTract;
		}
		public int getPersonAddressId() {
			return personAddressId;
		}
		public void setPersonAddressId(int personAddressId) {
			this.personAddressId = personAddressId;
		}
		public String getPersonCountry() {
			return personCountry;
		}
		public void setPersonCountry(String personCountry) {
			this.personCountry = personCountry;
		}
		public String getPersonPhoneHome() {
			return personPhoneHome;
		}
		public void setPersonPhoneHome(String personPhoneHome) {
			this.personPhoneHome = personPhoneHome;
		}
		public String getPersonPhoneCell() {
			return personPhoneCell;
		}
		public void setPersonPhoneCell(String personPhoneCell) {
			this.personPhoneCell = personPhoneCell;
		}
		public String getPersonPhoneWork() {
			return personPhoneWork;
		}
		public void setPersonPhoneWork(String personPhoneWork) {
			this.personPhoneWork = personPhoneWork;
		}
		public String getAddressUse() {
			return addressUse;
		}
		public void setAddressUse(String addressUse) {
			this.addressUse = addressUse;
		}
		public String getUseablePeriodLow() {
			return useablePeriodLow;
		}
		public void setUseablePeriodLow(String useablePeriodLow) {
			this.useablePeriodLow = useablePeriodLow;
		}
		public String getUseablePeriodHigh() {
			return useablePeriodHigh;
		}
		public void setUseablePeriodHigh(String useablePeriodHigh) {
			this.useablePeriodHigh = useablePeriodHigh;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getMpi() {
			return mpi;
		}
		public void setMpi(String mpi) {
			this.mpi = mpi;
		}
		public String getMatchCd() {
			return matchCd;
		}
		public void setMatchCd(String matchCd) {
			this.matchCd = matchCd;
		}
		public int getCurrentProcessStatus() {
			return currentProcessStatus;
		}
		public void setCurrentProcessStatus(int currentProcessStatus) {
			this.currentProcessStatus = currentProcessStatus;
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
		public String getMatchedStateno() {
			return matchedStateno;
		}
		public void setMatchedStateno(String matchedStateno) {
			this.matchedStateno = matchedStateno;
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
		public String getStateno() {
			return stateno;
		}

		public void setStateno(String stateno) {
			this.stateno = stateno;
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

		private String DISCARD_STATUS;

		public String getDISCARD_STATUS() {
			return DISCARD_STATUS;
		}
		public void setDISCARD_STATUS(String dISCARD_STATUS) {
			DISCARD_STATUS = dISCARD_STATUS;
		}
		

}
