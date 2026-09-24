package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document;

public class LabDocVO {	
	    private int documentId;
	    private String pfi;
	    private Long labSrcId;
	    private String fname="";
	    private String lname="";
	    private String mname="";
	    private String suffix="";
	    private String dobText="";
	    private String age="";
	    private String birthCountry="";
	    private String personAddress="";
	    private String personCity="";
	    private String personState="";
	    private String personZip="";
	    private String personPlus4="";
	    private String personCounty="";
	    private String personPhone="";
	    private String gender="";
	    private boolean raceAsian;
	    private boolean raceBlack;
	    private boolean raceIndian;
	    private boolean raceMultiple;
	    private boolean raceOther;
	    private boolean racePacific;
	    private boolean raceUnknown;
	    private boolean raceWhite;
	    private String ethnicity="";
	    private String medRecordNumber="";
	    private String insuranceCd="";
	    private String insuranceNumber="";
	    private String din="";
	    private String nysid="";
	    private String ssn="";
	    private String provLname="";
	    private String provAddress="";
	    private String provCity="";
	    private String provState="";
	    private String provZip="";
	    private String provPlus4="";
	    private String provPhone="";
	    private String provCounty="";
	    private String facilityName="";
	    private String facilityAddress="";
	    private String facilityCity="";
	    private String facilityState="";
	    private String facilityZip="";
	    private String facilityPlus4="";
	    private String facilityCounty="";
	    private String facilityPhone="";
	    private String drawDate="";
	    private String collectionDate="";
	    private String observation_date = "";
	    private String resultReportDate="";
	    private String loinc="";
	    private String loincDesc="";
	    private String testResult="";
	    private String unitMeasureCode="";
	    private String personInternalId="";
	    private String personExternalId="";
	    private String risk01="";
	    private String risk02="";
	    private String risk03="";
	    private String risk04="";
	    private String risk05="";
	    private String risk06="";
	    private String risk07="";
	    private String risk08="";
	    private String risk09="";
	    private String sendingFacilityName="";
	    private String specimenSourceCd="";
	    private String specimenSourceName="";
	    private String referringFacility="";
	    private String testingFacility="";
	    private String stateno="";
	    private String testResultRaw="";
	    private String provFname="";
	    private String provMname="";
	    private String provSuffix="";
	    private String risk10="";
	    private String risk11="";
	    private String risk12="";
	    private String risk13="";
	    private String risk14="";
	    private String risk15="";
	    private String risk16="";
	    private String hivVaccineRecip="";
	    private String otherImmuneInfection="";
	    private String resideOutsideUsa="";
	    private String areaOfBirth="";
	    private String lastNegHivTestDate="";
	    private String lengthResidencyInUsa="";
	    private String sendingFacilityClia="";
	    private String dateSent="";
	    private String personCountry="";
	    private String accessionNumber="";
	    private String medicaidNumber="";
	    private String localTestCode="";
	    private String localTestDesc="";
	    private String stdFacilityAddress="";
	    private String stdPersonAddress="";
	    private String stdProvAddress="";
	    private String dateReceived="";
	    private String eharsImportDate="";
	    private String nycShipDate="";
	    
		public String getObservation_date() {
			return observation_date;
		}
		public void setObservation_date(String observation_date) {
			this.observation_date = observation_date;
		}
		public int getDocumentId() {
			return documentId;
		}
		public void setDocumentId(int documentId) {
			this.documentId = documentId;
		}
						
		public Long getLabSrcId() {
			return labSrcId;
		}
		public void setLabSrcId(Long labSrcId) {
			this.labSrcId = labSrcId;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getLname() {
			return lname;
		}
		public void setLname(String lname) {
			this.lname = lname;
		}
		public String getMname() {
			return mname;
		}
		public void setMname(String mname) {
			this.mname = mname;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public String getDobText() {
			return dobText;
		}
		public void setDobText(String dobText) {
			this.dobText = dobText;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getBirthCountry() {
			return birthCountry;
		}
		public void setBirthCountry(String birthCountry) {
			this.birthCountry = birthCountry;
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
		public String getPersonPhone() {
			return personPhone;
		}
		public void setPersonPhone(String personPhone) {
			this.personPhone = personPhone;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public boolean isRaceAsian() {
			return raceAsian;
		}
		public void setRaceAsian(boolean raceAsian) {
			this.raceAsian = raceAsian;
		}
		public boolean isRaceBlack() {
			return raceBlack;
		}
		public void setRaceBlack(boolean raceBlack) {
			this.raceBlack = raceBlack;
		}
		public boolean isRaceIndian() {
			return raceIndian;
		}
		public void setRaceIndian(boolean raceIndian) {
			this.raceIndian = raceIndian;
		}
		public boolean isRaceMultiple() {
			return raceMultiple;
		}
		public void setRaceMultiple(boolean raceMultiple) {
			this.raceMultiple = raceMultiple;
		}
		public boolean isRaceOther() {
			return raceOther;
		}
		public void setRaceOther(boolean raceOther) {
			this.raceOther = raceOther;
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
		public boolean isRaceWhite() {
			return raceWhite;
		}
		public void setRaceWhite(boolean raceWhite) {
			this.raceWhite = raceWhite;
		}
		public String getEthnicity() {
			return ethnicity;
		}
		public void setEthnicity(String ethnicity) {
			this.ethnicity = ethnicity;
		}
		public String getMedRecordNumber() {
			return medRecordNumber;
		}
		public void setMedRecordNumber(String medRecordNumber) {
			this.medRecordNumber = medRecordNumber;
		}
		public String getInsuranceCd() {
			return insuranceCd;
		}
		public void setInsuranceCd(String insuranceCd) {
			this.insuranceCd = insuranceCd;
		}
		public String getInsuranceNumber() {
			return insuranceNumber;
		}
		public void setInsuranceNumber(String insuranceNumber) {
			this.insuranceNumber = insuranceNumber;
		}
		public String getDin() {
			return din;
		}
		public void setDin(String din) {
			this.din = din;
		}
		public String getNysid() {
			return nysid;
		}
		public void setNysid(String nysid) {
			this.nysid = nysid;
		}
		public String getSsn() {
			return ssn;
		}
		public void setSsn(String ssn) {
			this.ssn = ssn;
		}
		public String getProvLname() {
			return provLname;
		}
		public void setProvLname(String provLname) {
			this.provLname = provLname;
		}
		public String getProvAddress() {
			return provAddress;
		}
		public void setProvAddress(String provAddress) {
			this.provAddress = provAddress;
		}
		public String getProvCity() {
			return provCity;
		}
		public void setProvCity(String provCity) {
			this.provCity = provCity;
		}
		public String getProvState() {
			return provState;
		}
		public void setProvState(String provState) {
			this.provState = provState;
		}
		public String getProvZip() {
			return provZip;
		}
		public void setProvZip(String provZip) {
			this.provZip = provZip;
		}
		public String getProvPlus4() {
			return provPlus4;
		}
		public void setProvPlus4(String provPlus4) {
			this.provPlus4 = provPlus4;
		}
		public String getProvPhone() {
			return provPhone;
		}
		public void setProvPhone(String provPhone) {
			this.provPhone = provPhone;
		}
		public String getProvCounty() {
			return provCounty;
		}
		public void setProvCounty(String provCounty) {
			this.provCounty = provCounty;
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
		public String getFacilityPhone() {
			return facilityPhone;
		}
		public void setFacilityPhone(String facilityPhone) {
			this.facilityPhone = facilityPhone;
		}
		public String getDrawDate() {
			return drawDate;
		}
		public void setDrawDate(String drawDate) {
			this.drawDate = drawDate;
		}
		public String getCollectionDate() {
			return collectionDate;
		}
		public void setCollectionDate(String collectionDate) {
			this.collectionDate = collectionDate;
		}
		public String getResultReportDate() {
			return resultReportDate;
		}
		public void setResultReportDate(String resultReportDate) {
			this.resultReportDate = resultReportDate;
		}
		public String getLoinc() {
			return loinc;
		}
		public void setLoinc(String loinc) {
			this.loinc = loinc;
		}
		public String getLoincDesc() {
			return loincDesc;
		}
		public void setLoincDesc(String loincDesc) {
			this.loincDesc = loincDesc;
		}
		public String getTestResult() {
			return testResult;
		}
		public void setTestResult(String testResult) {
			this.testResult = testResult;
		}
		public String getUnitMeasureCode() {
			return unitMeasureCode;
		}
		public void setUnitMeasureCode(String unitMeasureCode) {
			this.unitMeasureCode = unitMeasureCode;
		}
		public String getPersonInternalId() {
			return personInternalId;
		}
		public void setPersonInternalId(String personInternalId) {
			this.personInternalId = personInternalId;
		}
		public String getPersonExternalId() {
			return personExternalId;
		}
		public void setPersonExternalId(String personExternalId) {
			this.personExternalId = personExternalId;
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
		public String getSendingFacilityName() {
			return sendingFacilityName;
		}
		public void setSendingFacilityName(String sendingFacilityName) {
			this.sendingFacilityName = sendingFacilityName;
		}
		public String getSpecimenSourceCd() {
			return specimenSourceCd;
		}
		public void setSpecimenSourceCd(String specimenSourceCd) {
			this.specimenSourceCd = specimenSourceCd;
		}
		public String getSpecimenSourceName() {
			return specimenSourceName;
		}
		public void setSpecimenSourceName(String specimenSourceName) {
			this.specimenSourceName = specimenSourceName;
		}
		public String getReferringFacility() {
			return referringFacility;
		}
		public void setReferringFacility(String referringFacility) {
			this.referringFacility = referringFacility;
		}
		public String getTestingFacility() {
			return testingFacility;
		}
		public void setTestingFacility(String testingFacility) {
			this.testingFacility = testingFacility;
		}
		public String getStateno() {
			return stateno;
		}
		public void setStateno(String stateno) {
			this.stateno = stateno;
		}
		public String getTestResultRaw() {
			return testResultRaw;
		}
		public void setTestResultRaw(String testResultRaw) {
			this.testResultRaw = testResultRaw;
		}
		public String getProvFname() {
			return provFname;
		}
		public void setProvFname(String provFname) {
			this.provFname = provFname;
		}
		public String getProvMname() {
			return provMname;
		}
		public void setProvMname(String provMname) {
			this.provMname = provMname;
		}
		public String getProvSuffix() {
			return provSuffix;
		}
		public void setProvSuffix(String provSuffix) {
			this.provSuffix = provSuffix;
		}
		public String getRisk10() {
			return risk10;
		}
		public void setRisk10(String risk10) {
			this.risk10 = risk10;
		}
		public String getRisk11() {
			return risk11;
		}
		public void setRisk11(String risk11) {
			this.risk11 = risk11;
		}
		public String getRisk12() {
			return risk12;
		}
		public void setRisk12(String risk12) {
			this.risk12 = risk12;
		}
		public String getRisk13() {
			return risk13;
		}
		public void setRisk13(String risk13) {
			this.risk13 = risk13;
		}
		public String getRisk14() {
			return risk14;
		}
		public void setRisk14(String risk14) {
			this.risk14 = risk14;
		}
		public String getRisk15() {
			return risk15;
		}
		public void setRisk15(String risk15) {
			this.risk15 = risk15;
		}
		public String getRisk16() {
			return risk16;
		}
		public void setRisk16(String risk16) {
			this.risk16 = risk16;
		}
		public String getHivVaccineRecip() {
			return hivVaccineRecip;
		}
		public void setHivVaccineRecip(String hivVaccineRecip) {
			this.hivVaccineRecip = hivVaccineRecip;
		}
		public String getOtherImmuneInfection() {
			return otherImmuneInfection;
		}
		public void setOtherImmuneInfection(String otherImmuneInfection) {
			this.otherImmuneInfection = otherImmuneInfection;
		}
		public String getResideOutsideUsa() {
			return resideOutsideUsa;
		}
		public void setResideOutsideUsa(String resideOutsideUsa) {
			this.resideOutsideUsa = resideOutsideUsa;
		}
		public String getAreaOfBirth() {
			return areaOfBirth;
		}
		public void setAreaOfBirth(String areaOfBirth) {
			this.areaOfBirth = areaOfBirth;
		}
		public String getLastNegHivTestDate() {
			return lastNegHivTestDate;
		}
		public void setLastNegHivTestDate(String lastNegHivTestDate) {
			this.lastNegHivTestDate = lastNegHivTestDate;
		}
		public String getLengthResidencyInUsa() {
			return lengthResidencyInUsa;
		}
		public void setLengthResidencyInUsa(String lengthResidencyInUsa) {
			this.lengthResidencyInUsa = lengthResidencyInUsa;
		}
		public String getSendingFacilityClia() {
			return sendingFacilityClia;
		}
		public void setSendingFacilityClia(String sendingFacilityClia) {
			this.sendingFacilityClia = sendingFacilityClia;
		}
		public String getDateSent() {
			return dateSent;
		}
		public void setDateSent(String dateSent) {
			this.dateSent = dateSent;
		}
		public String getPersonCountry() {
			return personCountry;
		}
		public void setPersonCountry(String personCountry) {
			this.personCountry = personCountry;
		}
		public String getAccessionNumber() {
			return accessionNumber;
		}
		public void setAccessionNumber(String accessionNumber) {
			this.accessionNumber = accessionNumber;
		}
		public String getMedicaidNumber() {
			return medicaidNumber;
		}
		public void setMedicaidNumber(String medicaidNumber) {
			this.medicaidNumber = medicaidNumber;
		}
		public String getLocalTestCode() {
			return localTestCode;
		}
		public void setLocalTestCode(String localTestCode) {
			this.localTestCode = localTestCode;
		}
		public String getLocalTestDesc() {
			return localTestDesc;
		}
		public void setLocalTestDesc(String localTestDesc) {
			this.localTestDesc = localTestDesc;
		}
		public String getStdFacilityAddress() {
			return stdFacilityAddress;
		}
		public void setStdFacilityAddress(String stdFacilityAddress) {
			this.stdFacilityAddress = stdFacilityAddress;
		}
		public String getStdPersonAddress() {
			return stdPersonAddress;
		}
		public void setStdPersonAddress(String stdPersonAddress) {
			this.stdPersonAddress = stdPersonAddress;
		}
		public String getStdProvAddress() {
			return stdProvAddress;
		}
		public void setStdProvAddress(String stdProvAddress) {
			this.stdProvAddress = stdProvAddress;
		}
		public String getDateReceived() {
			return dateReceived;
		}
		public void setDateReceived(String dateReceived) {
			this.dateReceived = dateReceived;
		}
	
		public String getEharsImportDate() {
			return eharsImportDate;
		}
		public void setEharsImportDate(String eharsImportDate) {
			this.eharsImportDate = eharsImportDate;
		}
		
		public String getNycShipDate() {
			return nycShipDate;
		}
		public void setNycShipDate(String nycShipDate) {
			this.nycShipDate = nycShipDate;
		}
		
		public String getPfi() {
			return pfi;
		}
		public void setPfi(String pfi) {
			this.pfi = pfi;
		}
		@Override
		public String toString() {
			return "LabDocVO [documentId=" + documentId + ", labSrcId=" + labSrcId + ", fname=" + fname + ", lname=" + lname
					+ ", mname=" + mname + ", suffix=" + suffix + ", dobText=" + dobText + ", age=" + age + ", birthCountry="
					+ birthCountry + ", personAddress=" + personAddress + ", personCity=" + personCity + ", personState="
					+ personState + ", personZip=" + personZip + ", personPlus4=" + personPlus4 + ", personCounty=" + personCounty
					+ ", personPhone=" + personPhone + ", gender=" + gender + ", raceAsian=" + raceAsian + ", raceBlack="
					+ raceBlack + ", raceIndian=" + raceIndian + ", raceMultiple=" + raceMultiple + ", raceOther=" + raceOther
					+ ", racePacific=" + racePacific + ", raceUnknown=" + raceUnknown + ", raceWhite=" + raceWhite + ", ethnicity="
					+ ethnicity + ", medRecordNumber=" + medRecordNumber + ", insuranceCd=" + insuranceCd + ", insuranceNumber="
					+ insuranceNumber + ", din=" + din + ", nysid=" + nysid + ", ssn=" + ssn + ", provLname=" + provLname
					+ ", provAddress=" + provAddress + ", provCity=" + provCity + ", provState=" + provState + ", provZip="
					+ provZip + ", provPlus4=" + provPlus4 + ", provPhone=" + provPhone + ", provCounty=" + provCounty
					+ ", facilityName=" + facilityName + ", facilityAddress=" + facilityAddress + ", facilityCity=" + facilityCity
					+ ", facilityState=" + facilityState + ", facilityZip=" + facilityZip + ", facilityPlus4=" + facilityPlus4
					+ ", facilityCounty=" + facilityCounty + ", facilityPhone=" + facilityPhone + ", drawDate=" + drawDate
					+ ", collectionDate=" + collectionDate + ", resultReportDate=" + resultReportDate + ", loinc=" + loinc
					+ ", loincDesc=" + loincDesc + ", testResult=" + testResult + ", unitMeasureCode=" + unitMeasureCode
					+ ", personInternalId=" + personInternalId + ", personExternalId=" + personExternalId + ", risk01=" + risk01
					+ ", risk02=" + risk02 + ", risk03=" + risk03 + ", risk04=" + risk04 + ", risk05=" + risk05 + ", risk06="
					+ risk06 + ", risk07=" + risk07 + ", risk08=" + risk08 + ", risk09=" + risk09 + ", sendingFacilityName="
					+ sendingFacilityName + ", specimenSourceCd=" + specimenSourceCd + ", specimenSourceName=" + specimenSourceName
					+ ", referringFacility=" + referringFacility + ", testingFacility=" + testingFacility + ", stateno=" + stateno
					+ ", testResultRaw=" + testResultRaw + ", provFname=" + provFname + ", provMname=" + provMname
					+ ", provSuffix=" + provSuffix + ", risk10=" + risk10 + ", risk11=" + risk11 + ", risk12=" + risk12
					+ ", risk13=" + risk13 + ", risk14=" + risk14 + ", risk15=" + risk15 + ", risk16=" + risk16
					+ ", hivVaccineRecip=" + hivVaccineRecip + ", otherImmuneInfection=" + otherImmuneInfection
					+ ", resideOutsideUsa=" + resideOutsideUsa + ", areaOfBirth=" + areaOfBirth + ", lastNegHivTestDate="
					+ lastNegHivTestDate + ", lengthResidencyInUsa=" + lengthResidencyInUsa + ", sendingFacilityClia="
					+ sendingFacilityClia + ", dateSent=" + dateSent + ", personCountry=" + personCountry + ", accessionNumber="
					+ accessionNumber + ", medicaidNumber=" + medicaidNumber + ", localTestCode=" + localTestCode
					+ ", localTestDesc=" + localTestDesc + ", stdFacilityAddress=" + stdFacilityAddress + ", stdPersonAddress="
					+ stdPersonAddress + ", stdProvAddress=" + stdProvAddress + ", dateReceived="
					+ dateReceived + ", eharsImportDate="
					+ eharsImportDate + "]";
		}
		
	
	
}
