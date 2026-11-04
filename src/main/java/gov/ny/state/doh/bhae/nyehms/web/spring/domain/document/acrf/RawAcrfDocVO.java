package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

import java.io.Serializable;
import java.util.List;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

public class RawAcrfDocVO implements Serializable{ 

	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int acrfSrcId;
	private int documentId;
	public String fname="";
	private String mname="";
	private String lname="";
	private String nameTypeCode="";
	private String altFname="";
	private String altMname="";
	private String altLname="";
	private String addressTypeCode="";
	private String hivAddressTypeCode="";
	private String aidsAddressTypeCode="";
	private String bthAddressTypeCode="";
	private String pediatricAddressTypeCode="";
	private String perinatalAddressTypeCode="";
	private String address="";
	private String city="";
	private String state="";
	private String zipcode="";
	private String plus4="";
	private String countyFips="";
	private String country="";
	private String telephone="";
	private String medRecordNumber="";
	private String idTypeCode="";
	private String otherIdNumber="";
	private String receivedDate;
	private String eharsDocId="";
	private String stateno="";
	private String documentSource="";
	private String surveillanceMethodCode="";
	private String newCaseCode="";
	private String reportMediumCode="";
	private String providingFacilityName="";
	private String providingFacilityAddress="";
	private String providingFacilityCity="";
	private String providingFacilityState="";
	private String providingFacilityCountry="";
	private String providingFacilityZipcode="";
	private String providingFacilityPlus4="";
	private String providingFacilityCountyFips="";
	private String providingFacilityPhone="";
	private String providingFacilityTypeCode="";
	private String dateCompleted;
	private String personCompleting;
	private String workerPhone="";
	private String birthSex="";
	private String birthCountry="";
	private String birthCountryOther="";
	private String dateOfBirth="";
	private String aliasDateOfBirth="";
	private String vitalStatisticsCode="";
	private String dateOfDeath="";
	private String stateOfDeath="";
	private String genderCode="";
	/*ACRF 2023 revision*/
	private String genderIdentifiedDate="";
	private String sexOrientation;
	private String additionalsexOrientation;
	private String sexOrientationDate;
	private String posHivTestSelfTest;
	private String negHivTestSelfTest;
	private String numNegTestBySelfTestUnkRef;
	private String rapidTestType;
	private Integer numNegTestBySelfTest;
	/*ACRF 2023 revision*/
	private String additionalGender="";
	private String ethnicityCode="";
	private String expandedEthnicity="";
	private boolean raceIndian;
	private boolean raceAsian;
	private boolean raceBlack;
	private boolean racePacific;
	private boolean raceWhite;
	private boolean raceUnknown;
	private String expandedRace="";
	private boolean resHivDx;
	private boolean resAidsDx;
	private boolean resDxSameAsCurrent;
	private boolean resDxSameAsCurrentAids;
	private String resHivAddress="";
	private String resHivCity="";
	private String resHivState="";
	private String resHivCountry="";
	private String resHivZipcode="";
	private String resHivPlus4="";
	private String resHivCountyFips="";
	private String resAidsAddress="";
	private String resAidsCity="";
	private String resAidsState="";
	private String resAidsCountry="";
	private String resAidsZipcode="";
	private String resAidsPlus4="";
	private String resAidsCountyFips="";
	
	private String slProviderLname="";
	private String slProviderFname="";
	private String slProviderMname="";
	private String slProviderSuffix="";
	private String slProviderPhone="";
	private String slMedRecordNum="";
	private String slFacilityName="";
	private String slWorkerUid="";
	private String slWorkerPhone="";
	
	private boolean facilityDxHiv;
	private String facilityHivDxId;
	private String facilityHivDxEharsUid;
	private String facilityAidsDxId;
	private String facilityAidsDxEharsUid;
	private String providingFacilityId;
	private String providingEharsFacilityEharsUid;
	private boolean facilityDxAids;
	private boolean facilityDxSameAsProviding;
	private boolean facilityDxSameAsProvidingAids;
	private String facilityHivDxName="";
	private String facilityAddressHivDx="";
	private String facilityCityHivDx="";
	private String facilityStateHivDx="";
	private String facilityCountryHivDx="";
	private String facilityZipcodeHivDx="";
	private String facilityPlus4HivDx="";
	private String facilityCountyFipsHivDx="";
	private String facilityPhoneHivDx="";
	private String facilityHivDxTypeCode="";
	
	private String facilityHivDxTypeOther="";
	private String facilityAidsDxTypeOther="";
	private String providingFacilityTypeOther="";
		
	private String providerFnameHivDx="";
	private String providerMnameHivDx="";
	private String providerLnameHivDx="";
	private String providerSuffixHivDx="";
		
	private String providerPhoneHivDx="";
	private String providerSpecialty="";
	private String facilityAidsDxName="";
	private String facilityAddressAidsDx="";
	private String facilityCityAidsDx="";
	private String facilityStateAidsDx="";
	private String facilityCountryAidsDx="";
	private String facilityZipcodeAidsDx="";
	private String facilityPlus4AidsDx="";
	private String facilityCountyFipsAidsDx="";
	private String facilityPhoneAidsDx="";
	private String facilityAidsDxTypeCode="";
	
	
	private String providerFnameAidsDx="";
	private String providerMnameAidsDx="";
	private String providerLnameAidsDx="";
	private String providerSuffixAidsDx="";
	
	private String providerPhoneAidsDx="";
	private String providerSpecialtyAidsDx="";
	private String pediatricRisk;
	private String sexWithMale="";
	private String sexWithFemale="";
	private String idu="";
	private String recvClottingFactor="";
	private String clottingFactorRecv="";
	private String dateRecvClottingFactor="";
	private String heterosexWithIdu="";
	private String heterosexWithMsm="";
	private String heterosexWithHemophiliac="";
	private String heterosexWithTransfusion="";
	private String heterosexWithTransplant="";
	private String heterosexWithHiv="";
	private String recvBloodTransfusion="";
	private String firstDateRecvBlood="";
	private String lastDateRecvBlood="";
	private String recvTransplant="";
	private String healthcareWorker="";
	private String occupation="";
	private String otherRisk="";
	private String otherRiskDetails="";
	private String firstLowCd4Count="";
	private String firstLowCd4Percent="";
	private String firstLowCd4Date;
	private String lastNegHivDate="";
	private String lastNegHivTest="";
	private String hivDiagnosisByPhysician="";
	private String phyisicianDiagnosisDate="";
	private String candidiasisBronchiDate;
	private String candidiasisEsophDate;
	private String carcinomaDate;
	private String coccidiodomycosisDate;
	private String cryptococcosisDate;
	private String cryptosporidiosisDate;
	private String cytomegaloDiseaseDate;
	private String cytomegaloRetinitisDate;
	private String hivEncephDate;
	private String herpesDate;
	private String histoplasmosisDate;
	private String isosporiasisDate;
	private String kaposisSarcomaDate;
	private String lymphomaBurkitts1;
	private String lymphomaImmunoDate;
	private String lymphomaBrainDate;
	private String MAviumOrKansasiiDate;
	private String tbPulmonary1;
	private String tbExtrapulmonaryDate;
	private String mycobacteriumOtherDate;
	private String pnuemocystisDate;
	private String pneumoniaRecurrentDate;
	private String pmlDate;
	private String salmonellaSepticDate;
	private String toxoplasmosisBrainDate;
	private String wastingSyndromeDate;
	private String tbRvctCaseNumber="";
	private String informedOfHiv="";
	private String partnersNotifiedBy="";
	private String refferedForObgyn="";
	private String currentlyPregnant="";
	private String liveBirth="";
	
	private String comments;
	private String localOptionalFields;
	
	//Std Table Fields
	private String stdAddress="";
	private String stdAddressTypeCode="";
	private String stdFaculityAddressAidsDx="";
	private String stdFacilityAddressHivDx="";
	private String stdProvidingFacilityAddress="";
	private String stdResAidsAddress="";
	private String stdResHivAddress="";
	
	private String childName;                
	private String childSoundex;                 
	private String childDob;                     
	private String childCodedId;                 
	private String childStateno;                 
	private String childrenFacilityName;                 
	private String childrenFacilityPhone;                
	private String childrenFacilityZipCode;              
	private String childrenFacilityStreetAddress;        
	private String childrenFacilityCity;                 
	private String childrenFacilityCounty;               
	private String childrenFacilityCountry;         
	private String labResultMeetHivDiagCriteria=""; 
	private String dateEarliestPositive="";
	private String reportingHealthDeptCityCounty;
	private String cityCountyNumber;            
	private String  lastnameSoundex;              
	
	
	private String testTreatmentHistorySource="";
	private String dateReported;
	private String prevPosHivTest="";
	private String dateFirstPosHivTest;
	private String negHivTest="";
	private String dateLastNegHivTest;
	private String numNegTestUnkRef="";
	private Integer numNegTestBeforePos;
	private String takenArvs="";
	private String arvMeds="";
	private String dateArvsBegan="";
	private String dateLastUsedArvs="";
	
	private String dxAlgorithm="";
	private String dxAlgorithmDate="";
	private String currentArvs="N"; 
	
	
	
	private String resAidsAddressDate="";
	private String resHivAddressDate="";
	private String patientAddressDate="";
	private String childrenFacilityType="";
	private String childrenFacilityState="";
	
	private String childrenFacilityId="";
	private String childrenFacilityEharsUid="";
	private String mainARVSource="";
	private String arvPatientDateReported="";
	
	private String isOldAcrf="Y";
	private String MATCHED_STATENO;
	private String EHARS_IMPORT_DATE;
	
	
	private String suspectAhi;
	private String ahiSymptoms;
	private String ahiSymptomsDate;
	private String ahiEvidence;
	private String ahiEvidenceDate;
	private String nonLabMedCare;
	private String nonLabMedCareDate;
	private String ahiEvidenceNotes;
	private int acrfRevison;
	
	
	
	
	
	
	//PCRF Fields start
	private String diagStatusAtReport;
	private String lastMedicalEvaluationDate;
	private String initEvaluationHivDate;
	private boolean resPerinatalDxSameAsCurrent;
	private String resPerinatalAddress;
	private String resPerinatalCity;
	private String resPerinatalState;
	private String resPerinatalCountry;
	private String resPerinatalZipcode;
	private String resPerinatalPlus4;
	private String resPerinatalCountyFips;
	private String resPerinatalAddressDate;
	private boolean resPediatricSeroDxSameAsCurrent;
	private String resPediatricSeroAddress;
	private String resPediatricSeroCity;
	private String resPediatricSeroState;
	private String resPediatricSeroCountry;
	private String resPediatricSeroZipcode;
	private String resPediatricSeroPlus4;
	private String resPediatricSeroCountyFips;
	private String resPediatricSeroAddressDate;
	private boolean facilityDxSameAsProvidingPerinatal;
	private String facilityPerinatalDxName;
	private String facilityAddressPerinatalDx;
	private String facilityCityPerinatalDx;
	private String facilityStatePerinatalDx;
	private String facilityCountryPerinatalDx;
	private String facilityZipcodePerinatalDx;
	private String facilityPlus4PerinatalDx;
	private String facilityCountyFipsPerinatalDx;
	private String facilityPhonePerinatalDx;
	private String facilityPerinatalDxTypeCode;
	private String facilityPerinatalDxTypeOther;
	private String facilityPerinatalDxId;
	private String facilityPerinatalDxEharsUid;
	private String providerFnamePerinatalDx;
	private String providerMnamePerinatalDx;
	private String providerLnamePerinatalDx;
	private String providerSuffixPerinatalDx;
	private String providerPhonePerinatalDx;
	private String providerSpecialtyPerinatalDx;
	private String childMomHivInfectionStatus;
	private String motherFirstPosTestDate;
	private String motherCounseledHivPregnancy;
	private String perinatalHivInfection;
	private String motherRecvBloodTransfusion;
	private String motherFirstRecvBloodDate;
	private String motherLastRecvBloodDate;
	private String motherRecvTransplant;
	private String iduBeforeDiag;
	private String recvClottingFactorBeforeDiag;
	private String clottingFactorRecvBeforeDiag;
	private String clottingFactorBeforeDiagRecvDate;
	private String recvBloodTransfusionBeforeDiag;
	private String firstRecvBloodBeforeDiagDate;
	private String lastRecvBloodBeforeDiagDate;
	private String recvTransplantBeforeDiag;
	private String sexWithMaleBeforeDiag;
	private String sexWithFemaleBeforeDiag;
	private String otherRiskBeforeDiag;
	private String bacterialInfectionDate;
	private String lymphomaInterstitialPneumonia;
	private String hivInfectedNotDocumented;
	private String hivInfectedNotDocumentedDate;
	private String nonHivInfectInformByPhy;
	private String nonHivInfectInformByPhyDate;
	private String birthHistAvailable;
	private boolean resBthSameAsCurrent;
	private String resBthAddress;
	private String resBthCity;
	private String resBthState;
	private String resBthCountry;
	private String resBthZipcode;
	private String resBthPlus4;
	private String resBthCountyFips;
	private boolean facilityAtBthSameAsProviding;
	private String facilityAtBthDxName;
	private String facilityAddressAtBthDx;
	private String facilityCityAtBthDx;
	private String facilityStateAtBthDx;
	private String facilityCountryAtBthDx;
	private String facilityZipcodeAtBthDx;
	private String facilityPlus4AtBthDx;
	private String facilityCountyFipsAtBthDx;
	private String facilityPhoneAtBthDx;
	private String facilityAtBthDxTypeCode;
	private String facilityAtBthDxTypeOther;
	private String facilityAtBthDxEharsUid;
	private int birthWeightLbs;
	private int birthWeightOz;
	private int birthWeightGram;
	private String birthType;
	private String birthDelivery;
	private String birthDefects;
	private String birthDefectsTypes;
	private String neonatalStatus;
	private String neonatalGestAgeInWeeks;
	private String prenatalCareBeginMonth;
	private String prenatalCareNoVisits;
	private String motherRecvArvPriorPregnancy;
	private String motherRecvArvPriorPregnancyNotes;
	private String arvPriorPregnancyBeganDate;
	private String arvPriorPregnancyLastUseDate;
	private String motherRecvArvDuringPregnancy;
	private String motherRecvArvDuringPregnancyNotes;
	private String arvDuringPregnancyBeganDate;
	private String arvDuringPregnancyLastUseDate;
	private String motherRecvArvLaborPregnancy;
	private String motherRecvArvLaborPregnancyNotes;
	private String arvLaborPregnancyBeganDate;
	private String arvLaborPregnancyLastUseDate;
	private String maternalDob;
	private String maternalLastNameSndx;
	private String maternalStateIdNumber;
	private String maternalCountryOfBirth;
	private String otherMaternalId;
	private String otherMaternalIdType;
	private String childEverTakenArvs;
	private String childEverTakenPcpProphylaxis;
	private String prophylaxisBeganDate;
	private String prophylaxisLastUseDate;
	private String wasChildBreastfed;
	private String childPrimaryCaretaker;
	private String numPrevPreg;
	//PCRF fields end;
	private String childBreastfedBp;
	private String childBreastfedStartDate;
	private String childBreastfedStopDate;
	private String childPrechewedFoodBp;
	private String breastfedNonBp;
	private String prechewedFoodNonBp;
	private boolean csRsnHiv;
	private boolean csRsnLongLabor;
	private boolean csRsnAbruptiaPrevia;
	private boolean csRsnNotSpecified;
	
	private boolean csRsnPrevCs;
	private boolean csRsnPref;
	private boolean csRsnOther;
	
	
	private boolean csRsnMalpresentation; 
	private boolean csRsnFetalDistress;
	
	
	private String csRsnOtherSpecify;
	private String ruptureDt;
	private String deliveryDate;
	private String ruptureTime;
	private String deliveryTime;
	private String congenitalDisorders;
	private String congenitalDisordersCdl5;
	private String screenSubstance;
	private String bpCityNumber;
	private String pregBefore;
	private String bpCd4Test;
	private String bpVlTest;
	private String bpArvPregDrugRsn;
	private String bpArvPregDrugRsnOther;
	private String bpArvLaborDrugRsn;
	private String bpArvLaborDrugRsnOther;
	private String bpUseSubstance;
	private String bpScreenSubstance;
	
	
	
	private List<PcrfSubstanceDocVO> screenSubstanceList;
	private List<PcrfSubstanceDocVO> bpUseSubstanceList;
	private List<PcrfSubstanceDocVO> bpScreenSubstanceList;
	private List<PcrfSubstanceDocVO> bpScreenConditionsList;
	private List<PcrfSubstanceDocVO> bpDxScreenConditionsList;
	
	private List<PcrfSubstanceDocVO> bpPregOutcomeList;
	
	
	private String workerId;
	
	/*
	 *Pediatric risk fields
	 */
	
	private String nirBeforeDiag;
	private String motherPerinatalHivInfection;
	private String motherIdu;
	private String motherHeterosexWithIdu;
	private String motherHeterosexWithMsm;
	private String motherHeterosexWithHemophiliac;
	private String motherHeterosexWithTransfusion;
	private String motherHeterosexWithHiv;
	private String motherHeterosexWithTransplant;


	
	
	
	public String getMainARVSource() {
		return mainARVSource;
	}
	public void setMainARVSource(String mainARVSource) {
		this.mainARVSource = mainARVSource;
	}
	public String getArvPatientDateReported() {
		return arvPatientDateReported;
	}
	public void setArvPatientDateReported(String arvPatientDateReported) {
		this.arvPatientDateReported = formatMaskDate(arvPatientDateReported);
	}
	public String getIsOldAcrf() {
		return isOldAcrf;
	}
	public void setIsOldAcrf(String isOldAcrf) {
		this.isOldAcrf = isOldAcrf;
	}
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
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
	public String getNameTypeCode() {
		return nameTypeCode;
	}
	public void setNameTypeCode(String nameTypeCode) {
		this.nameTypeCode = nameTypeCode;
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
	public String getAddressTypeCode() {
		return addressTypeCode;
	}
	public void setAddressTypeCode(String addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPlus4() {
		return plus4;
	}
	public void setPlus4(String plus4) {
		this.plus4 = plus4;
	}
	public String getCountyFips() {
		return countyFips;
	}
	public void setCountyFips(String countyFips) {
		this.countyFips = countyFips;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = CommonUtil.toPhoneSearchPattern(telephone);
	}
	public String getMedRecordNumber() {
		return medRecordNumber;
	}
	public void setMedRecordNumber(String medRecordNumber) {
		this.medRecordNumber = medRecordNumber;
	}
	public String getIdTypeCode() {
		return idTypeCode;
	}
	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}
	public String getOtherIdNumber() {
		return otherIdNumber;
	}
	public void setOtherIdNumber(String otherIdNumber) {
		this.otherIdNumber = otherIdNumber;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = formatMaskDate(receivedDate);
	}
	public String getEharsDocId() {
		return eharsDocId;
	}
	public void setEharsDocId(String eharsDocId) {
		this.eharsDocId = eharsDocId;
	}
	public String getStateno() {
		return stateno;
	}
	public void setStateno(String stateno) {
		this.stateno = stateno;
	}
	public String getDocumentSource() {
		return documentSource;
	}
	public void setDocumentSource(String documentSource) {
		this.documentSource = documentSource;
	}
	public String getSurveillanceMethodCode() {
		return surveillanceMethodCode;
	}
	public void setSurveillanceMethodCode(String surveillanceMethodCode) {
		this.surveillanceMethodCode = surveillanceMethodCode;
	}
	public String getNewCaseCode() {
		return newCaseCode;
	}
	public void setNewCaseCode(String newCaseCode) {
		this.newCaseCode = newCaseCode;
	}
	public String getReportMediumCode() {
		return reportMediumCode;
	}
	public void setReportMediumCode(String reportMediumCode) {
		this.reportMediumCode = reportMediumCode;
	}
	public String getProvidingFacilityName() {
		return providingFacilityName;
	}
	public void setProvidingFacilityName(String providingFacilityName) {
		this.providingFacilityName = providingFacilityName;
	}
	public String getProvidingFacilityAddress() {
		return providingFacilityAddress;
	}
	public void setProvidingFacilityAddress(String providingFacilityAddress) {
		this.providingFacilityAddress = providingFacilityAddress;
	}
	public String getProvidingFacilityCity() {
		return providingFacilityCity;
	}
	public void setProvidingFacilityCity(String providingFacilityCity) {
		this.providingFacilityCity = providingFacilityCity;
	}
	public String getProvidingFacilityState() {
		return providingFacilityState;
	}
	public void setProvidingFacilityState(String providingFacilityState) {
		this.providingFacilityState = providingFacilityState;
	}
	public String getProvidingFacilityCountry() {
		return providingFacilityCountry;
	}
	public void setProvidingFacilityCountry(String providingFacilityCountry) {
		this.providingFacilityCountry = providingFacilityCountry;
	}
	public String getProvidingFacilityZipcode() {
		return providingFacilityZipcode;
	}
	public void setProvidingFacilityZipcode(String providingFacilityZipcode) {
		this.providingFacilityZipcode = providingFacilityZipcode;
	}
	public String getProvidingFacilityPlus4() {
		return providingFacilityPlus4;
	}
	public void setProvidingFacilityPlus4(String providingFacilityPlus4) {
		this.providingFacilityPlus4 = providingFacilityPlus4;
	}
	public String getProvidingFacilityCountyFips() {
		return providingFacilityCountyFips;
	}
	public void setProvidingFacilityCountyFips(String providingFacilityCountyFips) {
		this.providingFacilityCountyFips = providingFacilityCountyFips;
	}
	public String getProvidingFacilityPhone() {
		return providingFacilityPhone;
	}
	public void setProvidingFacilityPhone(String providingFacilityPhone) {
		this.providingFacilityPhone = providingFacilityPhone;
	}
	public String getProvidingFacilityTypeCode() {
		return providingFacilityTypeCode;
	}
	public void setProvidingFacilityTypeCode(String providingFacilityTypeCode) {
		this.providingFacilityTypeCode = providingFacilityTypeCode;
	}
	public String getProvidingFacilityTypeOther() {
		return providingFacilityTypeOther;
	}
	public void setProvidingFacilityTypeOther(String providingFacilityTypeOther) {
		this.providingFacilityTypeOther = providingFacilityTypeOther;
	}
	public String getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = formatMaskDate(dateCompleted);
	}
	public String getPersonCompleting() {
		return personCompleting;
	}
	public void setPersonCompleting(String personCompleting) {
		this.personCompleting = personCompleting;
	}
	public String getWorkerPhone() {
		return workerPhone;
	}
	public void setWorkerPhone(String workerPhone) {
		this.workerPhone = CommonUtil.toPhoneSearchPattern(workerPhone);
	}
	public String getBirthSex() {
		return birthSex;
	}
	public void setBirthSex(String birthSex) {
		this.birthSex = birthSex;
	}
	public String getBirthCountry() {
		return birthCountry;
	}
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	public String getBirthCountryOther() {
		return birthCountryOther;
	}
	public void setBirthCountryOther(String birthCountryOther) {
		this.birthCountryOther = birthCountryOther;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = formatMaskDate(dateOfBirth);
	}
	public String getAliasDateOfBirth() {
		return aliasDateOfBirth;
	}
	public void setAliasDateOfBirth(String aliasDateOfBirth) {
		this.aliasDateOfBirth = aliasDateOfBirth;
	}
	public String getVitalStatisticsCode() {
		return vitalStatisticsCode;
	}
	public void setVitalStatisticsCode(String vitalStatisticsCode) {
		this.vitalStatisticsCode = vitalStatisticsCode;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getStateOfDeath() {
		return stateOfDeath;
	}
	public void setStateOfDeath(String stateOfDeath) {
		this.stateOfDeath = stateOfDeath;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getAdditionalGender() {
		return additionalGender;
	}
	public void setAdditionalGender(String additionalGender) {
		this.additionalGender = additionalGender;
	}
	public String getEthnicityCode() {
		return ethnicityCode;
	}
	public void setEthnicityCode(String ethnicityCode) {
		this.ethnicityCode = ethnicityCode;
	}
	public String getExpandedEthnicity() {
		return expandedEthnicity;
	}
	public void setExpandedEthnicity(String expandedEthnicity) {
		this.expandedEthnicity = expandedEthnicity;
	}
	public boolean isRaceIndian() {
		return raceIndian;
	}
	public void setRaceIndian(boolean raceIndian) {
		this.raceIndian = raceIndian;
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
	public boolean isRacePacific() {
		return racePacific;
	}
	public void setRacePacific(boolean racePacific) {
		this.racePacific = racePacific;
	}
	public boolean isRaceWhite() {
		return raceWhite;
	}
	public void setRaceWhite(boolean raceWhite) {
		this.raceWhite = raceWhite;
	}
	public boolean isRaceUnknown() {
		return raceUnknown;
	}
	public void setRaceUnknown(boolean raceUnknown) {
		this.raceUnknown = raceUnknown;
	}
	public String getExpandedRace() {
		return expandedRace;
	}
	public void setExpandedRace(String expandedRace) {
		this.expandedRace = expandedRace;
	}
	public boolean isResHivDx() {
		return resHivDx;
	}
	public void setResHivDx(boolean resHivDx) {
		this.resHivDx = resHivDx;
	}
	public boolean isResAidsDx() {
		return resAidsDx;
	}
	public void setResAidsDx(boolean resAidsDx) {
		this.resAidsDx = resAidsDx;
	}
	public boolean isResDxSameAsCurrent() {
		return resDxSameAsCurrent;
	}
	public void setResDxSameAsCurrent(boolean resDxSameAsCurrent) {
		this.resDxSameAsCurrent = resDxSameAsCurrent;
	}
	public String getResHivAddress() {
		return resHivAddress;
	}
	public void setResHivAddress(String resHivAddress) {
		this.resHivAddress = resHivAddress;
	}
	public String getResHivCity() {
		return resHivCity;
	}
	public void setResHivCity(String resHivCity) {
		this.resHivCity = resHivCity;
	}
	public String getResHivState() {
		return resHivState;
	}
	public void setResHivState(String resHivState) {
		this.resHivState = resHivState;
	}
	public String getResHivCountry() {
		return resHivCountry;
	}
	public void setResHivCountry(String resHivCountry) {
		this.resHivCountry = resHivCountry;
	}
	public String getResHivZipcode() {
		return resHivZipcode;
	}
	public void setResHivZipcode(String resHivZipcode) {
		this.resHivZipcode = resHivZipcode;
	}
	public String getResHivPlus4() {
		return resHivPlus4;
	}
	public void setResHivPlus4(String resHivPlus4) {
		this.resHivPlus4 = resHivPlus4;
	}
	public String getResHivCountyFips() {
		return resHivCountyFips;
	}
	public void setResHivCountyFips(String resHivCountyFips) {
		this.resHivCountyFips = resHivCountyFips;
	}
	public String getResAidsAddress() {
		return resAidsAddress;
	}
	public void setResAidsAddress(String resAidsAddress) {
		this.resAidsAddress = resAidsAddress;
	}
	public String getResAidsCity() {
		return resAidsCity;
	}
	public void setResAidsCity(String resAidsCity) {
		this.resAidsCity = resAidsCity;
	}
	public String getResAidsState() {
		return resAidsState;
	}
	public void setResAidsState(String resAidsState) {
		this.resAidsState = resAidsState;
	}
	public String getResAidsCountry() {
		return resAidsCountry;
	}
	public void setResAidsCountry(String resAidsCountry) {
		this.resAidsCountry = resAidsCountry;
	}
	public String getResAidsZipcode() {
		return resAidsZipcode;
	}
	public void setResAidsZipcode(String resAidsZipcode) {
		this.resAidsZipcode = resAidsZipcode;
	}
	public String getResAidsPlus4() {
		return resAidsPlus4;
	}
	public void setResAidsPlus4(String resAidsPlus4) {
		this.resAidsPlus4 = resAidsPlus4;
	}
	public String getResAidsCountyFips() {
		return resAidsCountyFips;
	}
	public void setResAidsCountyFips(String resAidsCountyFips) {
		this.resAidsCountyFips = resAidsCountyFips;
	}
	public String getSlProviderLname() {
		return slProviderLname;
	}
	public void setSlProviderLname(String slProviderLname) {
		this.slProviderLname = slProviderLname;
	}
	public String getSlProviderFname() {
		return slProviderFname;
	}
	public void setSlProviderFname(String slProviderFname) {
		this.slProviderFname = slProviderFname;
	}
	public String getSlProviderMname() {
		return slProviderMname;
	}
	public void setSlProviderMname(String slProviderMname) {
		this.slProviderMname = slProviderMname;
	}
	public String getSlProviderSuffix() {
		return slProviderSuffix;
	}
	public void setSlProviderSuffix(String slProviderSuffix) {
		this.slProviderSuffix = slProviderSuffix;
	}
	public String getSlProviderPhone() {
		return slProviderPhone;
	}
	public void setSlProviderPhone(String slProviderPhone) {
		this.slProviderPhone = CommonUtil.toPhoneSearchPattern(slProviderPhone);
	}
	public String getSlMedRecordNum() {
		return slMedRecordNum;
	}
	public void setSlMedRecordNum(String slMedRecordNum) {
		this.slMedRecordNum = slMedRecordNum;
	}
	public String getSlFacilityName() {
		return slFacilityName;
	}
	public void setSlFacilityName(String slFacilityName) {
		this.slFacilityName = slFacilityName;
	}
	public String getSlWorkerUid() {
		return slWorkerUid;
	}
	public void setSlWorkerUid(String slWorkerUid) {
		this.slWorkerUid = slWorkerUid;
	}
	public String getSlWorkerPhone() {
		return slWorkerPhone;
	}
	public void setSlWorkerPhone(String slWorkerPhone) {
		this.slWorkerPhone = slWorkerPhone;
	}
	public boolean isFaciltyDxHiv() {
		return facilityDxHiv;
	}
	public void setFaciltyDxHiv(boolean facilityDxHiv) {
		this.facilityDxHiv = facilityDxHiv;
	}
	public boolean isFacilityDxAids() {
		return facilityDxAids;
	}
	public void setFacilityDxAids(boolean facilityDxAids) {
		this.facilityDxAids = facilityDxAids;
	}
	public boolean isFacilityDxSameAsProviding() {
		return facilityDxSameAsProviding;
	}
	public void setFacilityDxSameAsProviding(boolean facilityDxSameAsProviding) {
		this.facilityDxSameAsProviding = facilityDxSameAsProviding;
	}
	
	public String getFacilityHivDxName() {
		return facilityHivDxName;
	}
	public void setFacilityHivDxName(String facilityHivDxName) {
		this.facilityHivDxName = facilityHivDxName;
	}
	public String getFacilityAddressHivDx() {
		return facilityAddressHivDx;
	}
	public void setFacilityAddressHivDx(String facilityAddressHivDx) {
		this.facilityAddressHivDx = facilityAddressHivDx;
	}
	public String getFacilityCityHivDx() {
		return facilityCityHivDx;
	}
	public void setFacilityCityHivDx(String facilityCityHivDx) {
		this.facilityCityHivDx = facilityCityHivDx;
	}
	public String getFacilityStateHivDx() {
		return facilityStateHivDx;
	}
	public void setFacilityStateHivDx(String facilityStateHivDx) {
		this.facilityStateHivDx = facilityStateHivDx;
	}
	public String getFacilityCountryHivDx() {
		return facilityCountryHivDx;
	}
	public void setFacilityCountryHivDx(String facilityCountryHivDx) {
		this.facilityCountryHivDx = facilityCountryHivDx;
	}
	public String getFacilityZipcodeHivDx() {
		return facilityZipcodeHivDx;
	}
	public void setFacilityZipcodeHivDx(String facilityZipcodeHivDx) {
		this.facilityZipcodeHivDx = facilityZipcodeHivDx;
	}
	public String getFacilityPlus4HivDx() {
		return facilityPlus4HivDx;
	}
	public void setFacilityPlus4HivDx(String facilityPlus4HivDx) {
		this.facilityPlus4HivDx = facilityPlus4HivDx;
	}
	public String getFacilityCountyFipsHivDx() {
		return facilityCountyFipsHivDx;
	}
	public void setFacilityCountyFipsHivDx(String facilityCountyFipsHivDx) {
		this.facilityCountyFipsHivDx = facilityCountyFipsHivDx;
	}
	public String getFacilityPhoneHivDx() {
		return facilityPhoneHivDx;
	}
	public void setFacilityPhoneHivDx(String facilityPhoneHivDx) {
		this.facilityPhoneHivDx = facilityPhoneHivDx;
	}
	
	public String getFacilityHivDxTypeCode() {
		return facilityHivDxTypeCode;
	}
	public void setFacilityHivDxTypeCode(String facilityHivDxTypeCode) {
		this.facilityHivDxTypeCode = facilityHivDxTypeCode;
	}
	public String getFacilityAidsDxTypeCode() {
		return facilityAidsDxTypeCode;
	}
	public void setFacilityAidsDxTypeCode(String facilityAidsDxTypeCode) {
		this.facilityAidsDxTypeCode = facilityAidsDxTypeCode;
	}
	
	public String getProviderFnameHivDx() {
		return providerFnameHivDx;
	}
	public void setProviderFnameHivDx(String providerFnameHivDx) {
		this.providerFnameHivDx = providerFnameHivDx;
	}
	public String getProviderMnameHivDx() {
		return providerMnameHivDx;
	}
	public void setProviderMnameHivDx(String providerMnameHivDx) {
		this.providerMnameHivDx = providerMnameHivDx;
	}
	public String getProviderLnameHivDx() {
		return providerLnameHivDx;
	}
	public void setProviderLnameHivDx(String providerLnameHivDx) {
		this.providerLnameHivDx = providerLnameHivDx;
	}
	public String getProviderSuffixHivDx() {
		return providerSuffixHivDx;
	}
	public void setProviderSuffixHivDx(String providerSuffixHivDx) {
		this.providerSuffixHivDx = providerSuffixHivDx;
	}
	public String getProviderPhoneHivDx() {
		return providerPhoneHivDx;
	}
	public void setProviderPhoneHivDx(String providerPhoneHivDx) {
		this.providerPhoneHivDx = CommonUtil.toPhoneSearchPattern(providerPhoneHivDx);
	}
	public String getProviderSpecialty() {
		return providerSpecialty;
	}
	public void setProviderSpecialty(String providerSpecialty) {
		this.providerSpecialty = providerSpecialty;
	}
	
	public String getFacilityAidsDxName() {
		return facilityAidsDxName;
	}
	public void setFacilityAidsDxName(String facilityAidsDxName) {
		this.facilityAidsDxName = facilityAidsDxName;
	}
	public String getFacilityAddressAidsDx() {
		return facilityAddressAidsDx;
	}
	public void setFacilityAddressAidsDx(String facilityAddressAidsDx) {
		this.facilityAddressAidsDx = facilityAddressAidsDx;
	}
	public String getFacilityCityAidsDx() {
		return facilityCityAidsDx;
	}
	public void setFacilityCityAidsDx(String facilityCityAidsDx) {
		this.facilityCityAidsDx = facilityCityAidsDx;
	}
	public String getFacilityStateAidsDx() {
		return facilityStateAidsDx;
	}
	public void setFacilityStateAidsDx(String facilityStateAidsDx) {
		this.facilityStateAidsDx = facilityStateAidsDx;
	}
	public String getFacilityCountryAidsDx() {
		return facilityCountryAidsDx;
	}
	public void setFacilityCountryAidsDx(String facilityCountryAidsDx) {
		this.facilityCountryAidsDx = facilityCountryAidsDx;
	}
	public String getFacilityZipcodeAidsDx() {
		return facilityZipcodeAidsDx;
	}
	public void setFacilityZipcodeAidsDx(String facilityZipcodeAidsDx) {
		this.facilityZipcodeAidsDx = facilityZipcodeAidsDx;
	}
	public String getFacilityPlus4AidsDx() {
		return facilityPlus4AidsDx;
	}
	public void setFacilityPlus4AidsDx(String facilityPlus4AidsDx) {
		this.facilityPlus4AidsDx = facilityPlus4AidsDx;
	}
	public String getFacilityCountyFipsAidsDx() {
		return facilityCountyFipsAidsDx;
	}
	public void setFacilityCountyFipsAidsDx(String facilityCountyFipsAidsDx) {
		this.facilityCountyFipsAidsDx = facilityCountyFipsAidsDx;
	}
	public String getFacilityPhoneAidsDx() {
		return facilityPhoneAidsDx;
	}
	public void setFacilityPhoneAidsDx(String facilityPhoneAidsDx) {
		this.facilityPhoneAidsDx = facilityPhoneAidsDx;
	}
	
	public String getFacilityHivDxTypeOther() {
		return facilityHivDxTypeOther;
	}
	public void setFacilityHivDxTypeOther(String facilityHivDxTypeOther) {
		this.facilityHivDxTypeOther = facilityHivDxTypeOther;
	}
	public String getFacilityAidsDxTypeOther() {
		return facilityAidsDxTypeOther;
	}
	public void setFacilityAidsDxTypeOther(String facilityAidsDxTypeOther) {
		this.facilityAidsDxTypeOther = facilityAidsDxTypeOther;
	}
	public String getProviderFnameAidsDx() {
		return providerFnameAidsDx;
	}
	public void setProviderFnameAidsDx(String providerFnameAidsDx) {
		this.providerFnameAidsDx = providerFnameAidsDx;
	}
	public String getProviderMnameAidsDx() {
		return providerMnameAidsDx;
	}
	public void setProviderMnameAidsDx(String providerMnameAidsDx) {
		this.providerMnameAidsDx = providerMnameAidsDx;
	}
	public String getProviderLnameAidsDx() {
		return providerLnameAidsDx;
	}
	public void setProviderLnameAidsDx(String providerLnameAidsDx) {
		this.providerLnameAidsDx = providerLnameAidsDx;
	}
	public String getProviderSuffixAidsDx() {
		return providerSuffixAidsDx;
	}
	public void setProviderSuffixAidsDx(String providerSuffixAidsDx) {
		this.providerSuffixAidsDx = providerSuffixAidsDx;
	}
	public String getProviderPhoneAidsDx() {
		return providerPhoneAidsDx;
	}
	public void setProviderPhoneAidsDx(String providerPhoneAidsDx) {
		this.providerPhoneAidsDx = CommonUtil.toPhoneSearchPattern(providerPhoneAidsDx);
	}
	public String getProviderSpecialtyAidsDx() {
		return providerSpecialtyAidsDx;
	}
	public void setProviderSpecialtyAidsDx(String providerSpecialtyAidsDx) {
		this.providerSpecialtyAidsDx = providerSpecialtyAidsDx;
	}
	public String getPediatricRisk() {
		return pediatricRisk;
	}
	public void setPediatricRisk(String pediatricRisk) {
		this.pediatricRisk = pediatricRisk;
	}
	public String getSexWithMale() {
		return sexWithMale;
	}
	public void setSexWithMale(String sexWithMale) {
		this.sexWithMale = sexWithMale;
	}
	public String getSexWithFemale() {
		return sexWithFemale;
	}
	public void setSexWithFemale(String sexWithFemale) {
		this.sexWithFemale = sexWithFemale;
	}
	public String getIdu() {
		return idu;
	}
	public void setIdu(String idu) {
		this.idu = idu;
	}
	public String getRecvClottingFactor() {
		return recvClottingFactor;
	}
	public void setRecvClottingFactor(String recvClottingFactor) {
		this.recvClottingFactor = recvClottingFactor;
	}
	public String getClottingFactorRecv() {
		return clottingFactorRecv;
	}
	public void setClottingFactorRecv(String clottingFactorRecv) {
		this.clottingFactorRecv = clottingFactorRecv;
	}
	public String getDateRecvClottingFactor() {
		return dateRecvClottingFactor;
	}
	public void setDateRecvClottingFactor(String dateRecvClottingFactor) {
		this.dateRecvClottingFactor = dateRecvClottingFactor;
	}
	public String getHeterosexWithIdu() {
		return heterosexWithIdu;
	}
	public void setHeterosexWithIdu(String heterosexWithIdu) {
		this.heterosexWithIdu = heterosexWithIdu;
	}
	public String getHeterosexWithMsm() {
		return heterosexWithMsm;
	}
	public void setHeterosexWithMsm(String heterosexWithMsm) {
		this.heterosexWithMsm = heterosexWithMsm;
	}
	public String getHeterosexWithHemophiliac() {
		return heterosexWithHemophiliac;
	}
	public void setHeterosexWithHemophiliac(String heterosexWithHemophiliac) {
		this.heterosexWithHemophiliac = heterosexWithHemophiliac;
	}
	public String getHeterosexWithTransfusion() {
		return heterosexWithTransfusion;
	}
	public void setHeterosexWithTransfusion(String heterosexWithTransfusion) {
		this.heterosexWithTransfusion = heterosexWithTransfusion;
	}
	public String getHeterosexWithTransplant() {
		return heterosexWithTransplant;
	}
	public void setHeterosexWithTransplant(String heterosexWithTransplant) {
		this.heterosexWithTransplant = heterosexWithTransplant;
	}
	public String getHeterosexWithHiv() {
		return heterosexWithHiv;
	}
	public void setHeterosexWithHiv(String heterosexWithHiv) {
		this.heterosexWithHiv = heterosexWithHiv;
	}
	public String getRecvBloodTransfusion() {
		return recvBloodTransfusion;
	}
	public void setRecvBloodTransfusion(String recvBloodTransfusion) {
		this.recvBloodTransfusion = recvBloodTransfusion;
	}
	public String getFirstDateRecvBlood() {
		return firstDateRecvBlood;
	}
	public void setFirstDateRecvBlood(String firstDateRecvBlood) {
		this.firstDateRecvBlood = firstDateRecvBlood;
	}
	public String getLastDateRecvBlood() {
		return lastDateRecvBlood;
	}
	public void setLastDateRecvBlood(String lastDateRecvBlood) {
		this.lastDateRecvBlood = lastDateRecvBlood;
	}
	public String getRecvTransplant() {
		return recvTransplant;
	}
	public void setRecvTransplant(String recvTransplant) {
		this.recvTransplant = recvTransplant;
	}
	public String getHealthcareWorker() {
		return healthcareWorker;
	}
	public void setHealthcareWorker(String healthcareWorker) {
		this.healthcareWorker = healthcareWorker;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getOtherRisk() {
		return otherRisk;
	}
	public void setOtherRisk(String otherRisk) {
		this.otherRisk = otherRisk;
	}
	public String getOtherRiskDetails() {
		return otherRiskDetails;
	}
	public void setOtherRiskDetails(String otherRiskDetails) {
		this.otherRiskDetails = otherRiskDetails;
	}
	public String getFirstLowCd4Count() {
		return firstLowCd4Count;
	}
	public void setFirstLowCd4Count(String firstLowCd4Count) {
		this.firstLowCd4Count = firstLowCd4Count;
	}
	public String getFirstLowCd4Percent() {
		return firstLowCd4Percent;
	}
	public void setFirstLowCd4Percent(String firstLowCd4Percent) {
		this.firstLowCd4Percent = firstLowCd4Percent;
	}
	public String getFirstLowCd4Date() {
		return firstLowCd4Date;
	}
	public void setFirstLowCd4Date(String firstLowCd4Date) {
		this.firstLowCd4Date = firstLowCd4Date;
	}
	public String getLastNegHivDate() {
		return lastNegHivDate;
	}
	public void setLastNegHivDate(String lastNegHivDate) {
		this.lastNegHivDate = lastNegHivDate;
	}
	public String getLastNegHivTest() {
		return lastNegHivTest;
	}
	public void setLastNegHivTest(String lastNegHivTest) {
		this.lastNegHivTest = lastNegHivTest;
	}
	public String getHivDiagnosisByPhysician() {
		return hivDiagnosisByPhysician;
	}
	public void setHivDiagnosisByPhysician(String hivDiagnosisByPhysician) {
		this.hivDiagnosisByPhysician = hivDiagnosisByPhysician;
	}
	public String getPhyisicianDiagnosisDate() {
		return phyisicianDiagnosisDate;
	}
	public void setPhyisicianDiagnosisDate(String phyisicianDiagnosisDate) {
		this.phyisicianDiagnosisDate = phyisicianDiagnosisDate;
	}
	public String getCandidiasisBronchiDate() {
		return candidiasisBronchiDate;
	}
	public void setCandidiasisBronchiDate(String candidiasisBronchiDate) {
		this.candidiasisBronchiDate = candidiasisBronchiDate;
	}
	
	public String getCandidiasisEsophDate() {
		return candidiasisEsophDate;
	}
	public void setCandidiasisEsophDate(String candidiasisEsophDate) {
		this.candidiasisEsophDate = candidiasisEsophDate;
	}
	public String getCarcinomaDate() {
		return carcinomaDate;
	}
	public void setCarcinomaDate(String carcinomaDate) {
		this.carcinomaDate = carcinomaDate;
	}
	public String getCoccidiodomycosisDate() {
		return coccidiodomycosisDate;
	}
	public void setCoccidiodomycosisDate(String coccidiodomycosisDate) {
		this.coccidiodomycosisDate = coccidiodomycosisDate;
	}
	public String getCryptococcosisDate() {
		return cryptococcosisDate;
	}
	public void setCryptococcosisDate(String cryptococcosisDate) {
		this.cryptococcosisDate = cryptococcosisDate;
	}
	public String getCryptosporidiosisDate() {
		return cryptosporidiosisDate;
	}
	public void setCryptosporidiosisDate(String cryptosporidiosisDate) {
		this.cryptosporidiosisDate = cryptosporidiosisDate;
	}
	public String getCytomegaloDiseaseDate() {
		return cytomegaloDiseaseDate;
	}
	public void setCytomegaloDiseaseDate(String cytomegaloDiseaseDate) {
		this.cytomegaloDiseaseDate = cytomegaloDiseaseDate;
	}
	public String getCytomegaloRetinitisDate() {
		return cytomegaloRetinitisDate;
	}
	public void setCytomegaloRetinitisDate(String cytomegaloRetinitisDate) {
		this.cytomegaloRetinitisDate = cytomegaloRetinitisDate;
	}
	public String getHivEncephDate() {
		return hivEncephDate;
	}
	public void setHivEncephDate(String hivEncephDate) {
		this.hivEncephDate = hivEncephDate;
	}
	public String getHerpesDate() {
		return herpesDate;
	}
	public void setHerpesDate(String herpesDate) {
		this.herpesDate = herpesDate;
	}
	public String getHistoplasmosisDate() {
		return histoplasmosisDate;
	}
	public void setHistoplasmosisDate(String histoplasmosisDate) {
		this.histoplasmosisDate = histoplasmosisDate;
	}
	public String getIsosporiasisDate() {
		return isosporiasisDate;
	}
	public void setIsosporiasisDate(String isosporiasisDate) {
		this.isosporiasisDate = isosporiasisDate;
	}
	public String getKaposisSarcomaDate() {
		return kaposisSarcomaDate;
	}
	public void setKaposisSarcomaDate(String kaposisSarcomaDate) {
		this.kaposisSarcomaDate = kaposisSarcomaDate;
	}
	public String getLymphomaBurkitts1() {
		return lymphomaBurkitts1;
	}
	public void setLymphomaBurkitts1(String lymphomaBurkitts1) {
		this.lymphomaBurkitts1 = lymphomaBurkitts1;
	}
	public String getLymphomaImmunoDate() {
		return lymphomaImmunoDate;
	}
	public void setLymphomaImmunoDate(String lymphomaImmunoDate) {
		this.lymphomaImmunoDate = lymphomaImmunoDate;
	}
	public String getLymphomaBrainDate() {
		return lymphomaBrainDate;
	}
	public void setLymphomaBrainDate(String lymphomaBrainDate) {
		this.lymphomaBrainDate = lymphomaBrainDate;
	}
	public String getMAviumOrKansasiiDate() {
		return MAviumOrKansasiiDate;
	}
	public void setMAviumOrKansasiiDate(String mAviumOrKansasiiDate) {
		MAviumOrKansasiiDate = mAviumOrKansasiiDate;
	}
	public String getTbPulmonary1() {
		return tbPulmonary1;
	}
	public void setTbPulmonary1(String tbPulmonary1) {
		this.tbPulmonary1 = tbPulmonary1;
	}
	public String getTbExtrapulmonaryDate() {
		return tbExtrapulmonaryDate;
	}
	public void setTbExtrapulmonaryDate(String tbExtrapulmonaryDate) {
		this.tbExtrapulmonaryDate = tbExtrapulmonaryDate;
	}
	public String getMycobacteriumOtherDate() {
		return mycobacteriumOtherDate;
	}
	public void setMycobacteriumOtherDate(String mycobacteriumOtherDate) {
		this.mycobacteriumOtherDate = mycobacteriumOtherDate;
	}
	public String getPnuemocystisDate() {
		return pnuemocystisDate;
	}
	public void setPnuemocystisDate(String pnuemocystisDate) {
		this.pnuemocystisDate = pnuemocystisDate;
	}
	public String getPneumoniaRecurrentDate() {
		return pneumoniaRecurrentDate;
	}
	public void setPneumoniaRecurrentDate(String pneumoniaRecurrentDate) {
		this.pneumoniaRecurrentDate = pneumoniaRecurrentDate;
	}
	public String getPmlDate() {
		return pmlDate;
	}
	public void setPmlDate(String pmlDate) {
		this.pmlDate = pmlDate;
	}
	public String getSalmonellaSepticDate() {
		return salmonellaSepticDate;
	}
	public void setSalmonellaSepticDate(String salmonellaSepticDate) {
		this.salmonellaSepticDate = salmonellaSepticDate;
	}
	public String getToxoplasmosisBrainDate() {
		return toxoplasmosisBrainDate;
	}
	public void setToxoplasmosisBrainDate(String toxoplasmosisBrainDate) {
		this.toxoplasmosisBrainDate = toxoplasmosisBrainDate;
	}
	public String getWastingSyndromeDate() {
		return wastingSyndromeDate;
	}
	public void setWastingSyndromeDate(String wastingSyndromeDate) {
		this.wastingSyndromeDate = wastingSyndromeDate;
	}
	public String getTbRvctCaseNumber() {
		return tbRvctCaseNumber;
	}
	public void setTbRvctCaseNumber(String tbRvctCaseNumber) {
		this.tbRvctCaseNumber = tbRvctCaseNumber;
	}
	public String getInformedOfHiv() {
		return informedOfHiv;
	}
	public void setInformedOfHiv(String informedOfHiv) {
		this.informedOfHiv = informedOfHiv;
	}
	public String getPartnersNotifiedBy() {
		return partnersNotifiedBy;
	}
	public void setPartnersNotifiedBy(String partnersNotifiedBy) {
		this.partnersNotifiedBy = partnersNotifiedBy;
	}
	public String getRefferedForObgyn() {
		return refferedForObgyn;
	}
	public void setRefferedForObgyn(String refferedForObgyn) {
		this.refferedForObgyn = refferedForObgyn;
	}
	public String getCurrentlyPregnant() {
		return currentlyPregnant;
	}
	public void setCurrentlyPregnant(String currentlyPregnant) {
		this.currentlyPregnant = currentlyPregnant;
	}
	public String getLiveBirth() {
		return liveBirth;
	}
	public void setLiveBirth(String liveBirth) {
		this.liveBirth = liveBirth;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public int getAcrfSrcId() {
		return acrfSrcId;
	}
	public void setAcrfSrcId(int acrfSrcId) {
		this.acrfSrcId = acrfSrcId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public boolean isFacilityDxHiv() {
		return facilityDxHiv;
	}
	public void setFacilityDxHiv(boolean facilityDxHiv) {
		this.facilityDxHiv = facilityDxHiv;
	}	
	public String getStdAddress() {
		return stdAddress;
	}
	public void setStdAddress(String stdAddress) {
		this.stdAddress = stdAddress;
	}
	public String getStdAddressTypeCode() {
		return stdAddressTypeCode;
	}
	public void setStdAddressTypeCode(String stdAddressTypeCode) {
		this.stdAddressTypeCode = stdAddressTypeCode;
	}
	public String getStdFaculityAddressAidsDx() {
		return stdFaculityAddressAidsDx;
	}
	public void setStdFaculityAddressAidsDx(String stdFaculityAddressAidsDx) {
		this.stdFaculityAddressAidsDx = stdFaculityAddressAidsDx;
	}
	public String getStdFacilityAddressHivDx() {
		return stdFacilityAddressHivDx;
	}
	public void setStdFacilityAddressHivDx(String stdFacilityAddressHivDx) {
		this.stdFacilityAddressHivDx = stdFacilityAddressHivDx;
	}
	public String getStdProvidingFacilityAddress() {
		return stdProvidingFacilityAddress;
	}
	public void setStdProvidingFacilityAddress(String stdProvidingFacilityAddress) {
		this.stdProvidingFacilityAddress = stdProvidingFacilityAddress;
	}
	public String getStdResAidsAddress() {
		return stdResAidsAddress;
	}
	public void setStdResAidsAddress(String stdResAidsAddress) {
		this.stdResAidsAddress = stdResAidsAddress;
	}
	public String getStdResHivAddress() {
		return stdResHivAddress;
	}
	public void setStdResHivAddress(String stdResHivAddress) {
		this.stdResHivAddress = stdResHivAddress;
	}
	
	public String getLocalOptionalFields() {
		return localOptionalFields;
	}
	public void setLocalOptionalFields(String localOptionalFields) {
		this.localOptionalFields = localOptionalFields;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getChildSoundex() {
		return childSoundex;
	}
	public void setChildSoundex(String childSoundex) {
		this.childSoundex = childSoundex;
	}
	public String getChildDob() {
		return childDob;
	}
	public void setChildDob(String childDob) {
		this.childDob = childDob;
	}
	public String getChildCodedId() {
		return childCodedId;
	}
	public void setChildCodedId(String childCodedId) {
		this.childCodedId = childCodedId;
	}
	public String getChildStateno() {
		return childStateno;
	}
	public void setChildStateno(String childStateno) {
		this.childStateno = childStateno;
	}
	
	public String getLabResultMeetHivDiagCriteria() {
		return labResultMeetHivDiagCriteria;
	}
	public void setLabResultMeetHivDiagCriteria(String labResultMeetHivDiagCriteria) {
		this.labResultMeetHivDiagCriteria = labResultMeetHivDiagCriteria;
	}
	public String getDateEarliestPositive() {
		return dateEarliestPositive;
	}
	public void setDateEarliestPositive(String dateEarliestPositive) {
		this.dateEarliestPositive = dateEarliestPositive;
	}
	public String getReportingHealthDeptCityCounty() {
		return reportingHealthDeptCityCounty;
	}
	public void setReportingHealthDeptCityCounty(
			String reportingHealthDeptCityCounty) {
		this.reportingHealthDeptCityCounty = reportingHealthDeptCityCounty;
	}
	public String getCityCountyNumber() {
		return cityCountyNumber;
	}
	public void setCityCountyNumber(String cityCountyNumber) {
		this.cityCountyNumber = cityCountyNumber;
	}
	public String getLastnameSoundex() {
		return lastnameSoundex;
	}
	public void setLastnameSoundex(String lastnameSoundex) {
		this.lastnameSoundex = lastnameSoundex;
	}
	
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
	public Integer getNumNegTestBeforePos() {
		return numNegTestBeforePos;
	}
	public void setNumNegTestBeforePos(Integer numNegTestBeforePos) {
		this.numNegTestBeforePos = numNegTestBeforePos;
	}
	public String getTakenArvs() {
		return takenArvs;
	}
	public void setTakenArvs(String takenArvs) {
		this.takenArvs = takenArvs;
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
	
	
	public String getDxAlgorithm() {
		return dxAlgorithm;
	}
	public void setDxAlgorithm(String dxAlgorithm) {
		this.dxAlgorithm = dxAlgorithm;
	}
	public String getDxAlgorithmDate() {
		return dxAlgorithmDate;
	}
	public void setDxAlgorithmDate(String dxAlgorithmDate) {
		this.dxAlgorithmDate = dxAlgorithmDate;
	}
	
	public String getCurrentArvs() {
		return currentArvs;
	}
	public void setCurrentArvs(String currentArvs) {
		if(currentArvs == null){
			currentArvs = "N";
		}
		this.currentArvs = currentArvs;
	}
	
	
	public boolean isResDxSameAsCurrentAids() {
		return resDxSameAsCurrentAids;
	}
	public void setResDxSameAsCurrentAids(boolean resDxSameAsCurrentAids) {
		this.resDxSameAsCurrentAids = resDxSameAsCurrentAids;
	}
	public boolean isFacilityDxSameAsProvidingAids() {
		return facilityDxSameAsProvidingAids;
	}
	public void setFacilityDxSameAsProvidingAids(
			boolean facilityDxSameAsProvidingAids) {
		this.facilityDxSameAsProvidingAids = facilityDxSameAsProvidingAids;
	}
	
	public String getFacilityHivDxId() {
		return facilityHivDxId;
	}
	public void setFacilityHivDxId(String facilityHivDxId) {
		this.facilityHivDxId = facilityHivDxId;
	}

	public String getFacilityAidsDxId() {
		return facilityAidsDxId;
	}
	public void setFacilityAidsDxId(String facilityAidsDxId) {
		this.facilityAidsDxId = facilityAidsDxId;
	}

	public String getProvidingFacilityId() {
		return providingFacilityId;
	}
	public void setProvidingFacilityId(String providingFacilityId) {
		this.providingFacilityId = providingFacilityId;
	}
	
	public String getFacilityHivDxEharsUid() {
		return facilityHivDxEharsUid;
	}
	public void setFacilityHivDxEharsUid(String facilityHivDxEharsUid) {
		this.facilityHivDxEharsUid = facilityHivDxEharsUid;
	}
	public String getFacilityAidsDxEharsUid() {
		return facilityAidsDxEharsUid;
	}
	public void setFacilityAidsDxEharsUid(String facilityAidsDxEharsUid) {
		this.facilityAidsDxEharsUid = facilityAidsDxEharsUid;
	}
	public String getProvidingEharsFacilityEharsUid() {
		return providingEharsFacilityEharsUid;
	}
	public void setProvidingEharsFacilityEharsUid(
			String providingEharsFacilityEharsUid) {
		this.providingEharsFacilityEharsUid = providingEharsFacilityEharsUid;
	}
	
	public String getArvMeds() {
		return arvMeds;
	}
	public void setArvMeds(String arvMeds) {
		if(arvMeds == null){
			arvMeds = "";
		}
		this.arvMeds = arvMeds;
	}
	
	
	
	public String getResAidsAddressDate() {
		return resAidsAddressDate;
	}
	public void setResAidsAddressDate(String resAidsAddressDate) {
		this.resAidsAddressDate = formatMaskDate(resAidsAddressDate);
	}
	public String getResHivAddressDate() {
		return resHivAddressDate;
	}
	public void setResHivAddressDate(String resHivAddressDate) {
		this.resHivAddressDate = formatMaskDate(resHivAddressDate);
	}
	public String getPatientAddressDate() {
		return patientAddressDate;
	}
	public void setPatientAddressDate(String patientAddressDate) {
		this.patientAddressDate = formatMaskDate(patientAddressDate);
	}
	
	
	public String getChildrenFacilityName() {
		return childrenFacilityName;
	}
	public void setChildrenFacilityName(String childrenFacilityName) {
		this.childrenFacilityName = childrenFacilityName;
	}
	public String getChildrenFacilityPhone() {
		return childrenFacilityPhone;
	}
	public void setChildrenFacilityPhone(String childrenFacilityPhone) {
		this.childrenFacilityPhone = childrenFacilityPhone;
	}
	public String getChildrenFacilityZipCode() {
		return childrenFacilityZipCode;
	}
	public void setChildrenFacilityZipCode(String childrenFacilityZipCode) {
		this.childrenFacilityZipCode = childrenFacilityZipCode;
	}
	public String getChildrenFacilityStreetAddress() {
		return childrenFacilityStreetAddress;
	}
	public void setChildrenFacilityStreetAddress(
			String childrenFacilityStreetAddress) {
		this.childrenFacilityStreetAddress = childrenFacilityStreetAddress;
	}
	public String getChildrenFacilityCity() {
		return childrenFacilityCity;
	}
	public void setChildrenFacilityCity(String childrenFacilityCity) {
		this.childrenFacilityCity = childrenFacilityCity;
	}
	public String getChildrenFacilityCounty() {
		return childrenFacilityCounty;
	}
	public void setChildrenFacilityCounty(String childrenFacilityCounty) {
		this.childrenFacilityCounty = childrenFacilityCounty;
	}
	public String getChildrenFacilityCountry() {
		return childrenFacilityCountry;
	}
	public void setChildrenFacilityCountry(String childrenFacilityCountry) {
		this.childrenFacilityCountry = childrenFacilityCountry;
	}
	public String getChildrenFacilityType() {
		return childrenFacilityType;
	}
	public void setChildrenFacilityType(String childrenFacilityType) {
		this.childrenFacilityType = childrenFacilityType;
	}
	public String getChildrenFacilityState() {
		return childrenFacilityState;
	}
	public void setChildrenFacilityState(String childrenFacilityState) {
		this.childrenFacilityState = childrenFacilityState;
	}
	public String getChildrenFacilityId() {
		return childrenFacilityId;
	}
	public void setChildrenFacilityId(String childrenFacilityId) {
		this.childrenFacilityId = childrenFacilityId;
	}
	public String getChildrenFacilityEharsUid() {
		return childrenFacilityEharsUid;
	}
	public void setChildrenFacilityEharsUid(String childrenFacilityEharsUid) {
		this.childrenFacilityEharsUid = childrenFacilityEharsUid;
	}
	private String formatMaskDate(String _date) {
		
		if("../../....".equals(_date)){
			return null;
		}else{
			return _date;
		}
	}
	private String formatMaskTime(String _date) {
		
		if("..:..".equals(_date) || "xx:xx".equals(_date)){
			return null;
		}else{
			return _date;
		}
	}
	public String getMATCHED_STATENO() {
		return MATCHED_STATENO;
	}
	public void setMATCHED_STATENO(String mATCHED_STATENO) {
		MATCHED_STATENO = mATCHED_STATENO;
	}
	public String getEHARS_IMPORT_DATE() {
		return EHARS_IMPORT_DATE;
	}
	public void setEHARS_IMPORT_DATE(String eHARS_IMPORT_DATE) {
		EHARS_IMPORT_DATE = eHARS_IMPORT_DATE;
	}
	public String getSuspectAhi() {
		return suspectAhi;
	}
	public void setSuspectAhi(String suspectAhi) {
		this.suspectAhi = suspectAhi;
	}
	public String getAhiSymptoms() {
		return ahiSymptoms;
	}
	public void setAhiSymptoms(String ahiSymptoms) {
		this.ahiSymptoms = ahiSymptoms;
	}
	public String getAhiSymptomsDate() {
		return ahiSymptomsDate;
	}
	public void setAhiSymptomsDate(String ahiSymptomsDate) {
		this.ahiSymptomsDate = ahiSymptomsDate;
	}
	public String getAhiEvidence() {
		return ahiEvidence;
	}
	public void setAhiEvidence(String ahiEvidence) {
		this.ahiEvidence = ahiEvidence;
	}
	public String getAhiEvidenceDate() {
		return ahiEvidenceDate;
	}
	public void setAhiEvidenceDate(String ahiEvidenceDate) {
		this.ahiEvidenceDate = ahiEvidenceDate;
	}
	public String getNonLabMedCare() {
		return nonLabMedCare;
	}
	public void setNonLabMedCare(String nonLabMedCare) {
		this.nonLabMedCare = nonLabMedCare;
	}
	public String getNonLabMedCareDate() {
		return nonLabMedCareDate;
	}
	public void setNonLabMedCareDate(String nonLabMedCareDate) {
		this.nonLabMedCareDate = nonLabMedCareDate;
	}
	public int getAcrfRevison() {
		return acrfRevison;
	}
	public void setAcrfRevison(int acrfRevison) {
		this.acrfRevison = acrfRevison;
	}
	public String getAhiEvidenceNotes() {
		return ahiEvidenceNotes;
	}
	public void setAhiEvidenceNotes(String ahiEvidenceNotes) {
		this.ahiEvidenceNotes = ahiEvidenceNotes;
	}
	public String getDiagStatusAtReport() {
		return diagStatusAtReport;
	}
	public void setDiagStatusAtReport(String diagStatusAtReport) {
		this.diagStatusAtReport = diagStatusAtReport;
	}
	public String getLastMedicalEvaluationDate() {
		return lastMedicalEvaluationDate;
	}
	public void setLastMedicalEvaluationDate(String lastMedicalEvaluationDate) {
		this.lastMedicalEvaluationDate = lastMedicalEvaluationDate;
	}
	public String getInitEvaluationHivDate() {
		return initEvaluationHivDate;
	}
	public void setInitEvaluationHivDate(String initEvaluationHivDate) {
		this.initEvaluationHivDate = initEvaluationHivDate;
	}

	public boolean isResPerinatalDxSameAsCurrent() {
		return resPerinatalDxSameAsCurrent;
	}
	public void setResPerinatalDxSameAsCurrent(boolean resPerinatalDxSameAsCurrent) {
		this.resPerinatalDxSameAsCurrent = resPerinatalDxSameAsCurrent;
	}
	public void setResPediatricSeroDxSameAsCurrent(
			boolean resPediatricSeroDxSameAsCurrent) {
		this.resPediatricSeroDxSameAsCurrent = resPediatricSeroDxSameAsCurrent;
	}
	public void setFacilityDxSameAsProvidingPerinatal(
			boolean facilityDxSameAsProvidingPerinatal) {
		this.facilityDxSameAsProvidingPerinatal = facilityDxSameAsProvidingPerinatal;
	}
	public void setResBthSameAsCurrent(boolean resBthSameAsCurrent) {
		this.resBthSameAsCurrent = resBthSameAsCurrent;
	}
	public void setFacilityAtBthSameAsProviding(boolean facilityAtBthSameAsProviding) {
		this.facilityAtBthSameAsProviding = facilityAtBthSameAsProviding;
	}
	public String getResPerinatalAddress() {
		return resPerinatalAddress;
	}
	public void setResPerinatalAddress(String resPerinatalAddress) {
		this.resPerinatalAddress = resPerinatalAddress;
	}
	public String getResPerinatalCity() {
		return resPerinatalCity;
	}
	public void setResPerinatalCity(String resPerinatalCity) {
		this.resPerinatalCity = resPerinatalCity;
	}
	public String getResPerinatalState() {
		return resPerinatalState;
	}
	public void setResPerinatalState(String resPerinatalState) {
		this.resPerinatalState = resPerinatalState;
	}
	public String getResPerinatalCountry() {
		return resPerinatalCountry;
	}
	public void setResPerinatalCountry(String resPerinatalCountry) {
		this.resPerinatalCountry = resPerinatalCountry;
	}
	public String getResPerinatalZipcode() {
		return resPerinatalZipcode;
	}
	public void setResPerinatalZipcode(String resPerinatalZipcode) {
		this.resPerinatalZipcode = resPerinatalZipcode;
	}
	public String getResPerinatalPlus4() {
		return resPerinatalPlus4;
	}
	public void setResPerinatalPlus4(String resPerinatalPlus4) {
		this.resPerinatalPlus4 = resPerinatalPlus4;
	}
	public String getResPerinatalCountyFips() {
		return resPerinatalCountyFips;
	}
	public void setResPerinatalCountyFips(String resPerinatalCountyFips) {
		this.resPerinatalCountyFips = resPerinatalCountyFips;
	}
	public String getResPerinatalAddressDate() {
		return resPerinatalAddressDate;
	}
	public void setResPerinatalAddressDate(String resPerinatalAddressDate) {
		this.resPerinatalAddressDate = resPerinatalAddressDate;
	}
	public String getResPediatricSeroAddress() {
		return resPediatricSeroAddress;
	}
	public void setResPediatricSeroAddress(String resPediatricSeroAddress) {
		this.resPediatricSeroAddress = resPediatricSeroAddress;
	}
	public String getResPediatricSeroCity() {
		return resPediatricSeroCity;
	}
	public void setResPediatricSeroCity(String resPediatricSeroCity) {
		this.resPediatricSeroCity = resPediatricSeroCity;
	}
	public String getResPediatricSeroState() {
		return resPediatricSeroState;
	}
	public void setResPediatricSeroState(String resPediatricSeroState) {
		this.resPediatricSeroState = resPediatricSeroState;
	}
	public String getResPediatricSeroCountry() {
		return resPediatricSeroCountry;
	}
	public void setResPediatricSeroCountry(String resPediatricSeroCountry) {
		this.resPediatricSeroCountry = resPediatricSeroCountry;
	}
	public String getResPediatricSeroZipcode() {
		return resPediatricSeroZipcode;
	}
	public void setResPediatricSeroZipcode(String resPediatricSeroZipcode) {
		this.resPediatricSeroZipcode = resPediatricSeroZipcode;
	}
	public String getResPediatricSeroPlus4() {
		return resPediatricSeroPlus4;
	}
	public void setResPediatricSeroPlus4(String resPediatricSeroPlus4) {
		this.resPediatricSeroPlus4 = resPediatricSeroPlus4;
	}
	public String getResPediatricSeroCountyFips() {
		return resPediatricSeroCountyFips;
	}
	public void setResPediatricSeroCountyFips(String resPediatricSeroCountyFips) {
		this.resPediatricSeroCountyFips = resPediatricSeroCountyFips;
	}
	public String getResPediatricSeroAddressDate() {
		return resPediatricSeroAddressDate;
	}
	public void setResPediatricSeroAddressDate(String resPediatricSeroAddressDate) {
		this.resPediatricSeroAddressDate = resPediatricSeroAddressDate;
	}
	public String getFacilityPerinatalDxName() {
		return facilityPerinatalDxName;
	}
	public void setFacilityPerinatalDxName(String facilityPerinatalDxName) {
		this.facilityPerinatalDxName = facilityPerinatalDxName;
	}
	public String getFacilityPerinatalDxTypeCode() {
		return facilityPerinatalDxTypeCode;
	}
	public void setFacilityPerinatalDxTypeCode(String facilityPerinatalDxTypeCode) {
		this.facilityPerinatalDxTypeCode = facilityPerinatalDxTypeCode;
	}
	public String getFacilityPerinatalDxTypeOther() {
		return facilityPerinatalDxTypeOther;
	}
	public void setFacilityPerinatalDxTypeOther(String facilityPerinatalDxTypeOther) {
		this.facilityPerinatalDxTypeOther = facilityPerinatalDxTypeOther;
	}
	public String getFacilityPerinatalDxId() {
		return facilityPerinatalDxId;
	}
	public void setFacilityPerinatalDxId(String facilityPerinatalDxId) {
		this.facilityPerinatalDxId = facilityPerinatalDxId;
	}
	public String getFacilityAddressPerinatalDx() {
		return facilityAddressPerinatalDx;
	}
	public void setFacilityAddressPerinatalDx(String facilityAddressPerinatalDx) {
		this.facilityAddressPerinatalDx = facilityAddressPerinatalDx;
	}
	public String getFacilityCityPerinatalDx() {
		return facilityCityPerinatalDx;
	}
	public void setFacilityCityPerinatalDx(String facilityCityPerinatalDx) {
		this.facilityCityPerinatalDx = facilityCityPerinatalDx;
	}
	public String getFacilityStatePerinatalDx() {
		return facilityStatePerinatalDx;
	}
	public void setFacilityStatePerinatalDx(String facilityStatePerinatalDx) {
		this.facilityStatePerinatalDx = facilityStatePerinatalDx;
	}
	public String getFacilityCountryPerinatalDx() {
		return facilityCountryPerinatalDx;
	}
	public void setFacilityCountryPerinatalDx(String facilityCountryPerinatalDx) {
		this.facilityCountryPerinatalDx = facilityCountryPerinatalDx;
	}
	public String getFacilityZipcodePerinatalDx() {
		return facilityZipcodePerinatalDx;
	}
	public void setFacilityZipcodePerinatalDx(String facilityZipcodePerinatalDx) {
		this.facilityZipcodePerinatalDx = facilityZipcodePerinatalDx;
	}
	public String getFacilityPlus4PerinatalDx() {
		return facilityPlus4PerinatalDx;
	}
	public void setFacilityPlus4PerinatalDx(String facilityPlus4PerinatalDx) {
		this.facilityPlus4PerinatalDx = facilityPlus4PerinatalDx;
	}
	public String getFacilityCountyFipsPerinatalDx() {
		return facilityCountyFipsPerinatalDx;
	}
	public void setFacilityCountyFipsPerinatalDx(
			String facilityCountyFipsPerinatalDx) {
		this.facilityCountyFipsPerinatalDx = facilityCountyFipsPerinatalDx;
	}
	public String getFacilityPhonePerinatalDx() {
		return facilityPhonePerinatalDx;
	}
	public void setFacilityPhonePerinatalDx(String facilityPhonePerinatalDx) {
		this.facilityPhonePerinatalDx = facilityPhonePerinatalDx;
	}
	public String getFacilityPerinatalDxEharsUid() {
		return facilityPerinatalDxEharsUid;
	}
	public void setFacilityPerinatalDxEharsUid(String facilityPerinatalDxEharsUid) {
		this.facilityPerinatalDxEharsUid = facilityPerinatalDxEharsUid;
	}
	public String getProviderFnamePerinatalDx() {
		return providerFnamePerinatalDx;
	}
	public void setProviderFnamePerinatalDx(String providerFnamePerinatalDx) {
		this.providerFnamePerinatalDx = providerFnamePerinatalDx;
	}
	public String getProviderMnamePerinatalDx() {
		return providerMnamePerinatalDx;
	}
	public void setProviderMnamePerinatalDx(String providerMnamePerinatalDx) {
		this.providerMnamePerinatalDx = providerMnamePerinatalDx;
	}
	public String getProviderLnamePerinatalDx() {
		return providerLnamePerinatalDx;
	}
	public void setProviderLnamePerinatalDx(String providerLnamePerinatalDx) {
		this.providerLnamePerinatalDx = providerLnamePerinatalDx;
	}
	public String getProviderSuffixPerinatalDx() {
		return providerSuffixPerinatalDx;
	}
	public void setProviderSuffixPerinatalDx(String providerSuffixPerinatalDx) {
		this.providerSuffixPerinatalDx = providerSuffixPerinatalDx;
	}
	public String getProviderPhonePerinatalDx() {
		return providerPhonePerinatalDx;
	}
	public void setProviderPhonePerinatalDx(String providerPhonePerinatalDx) {
		this.providerPhonePerinatalDx = providerPhonePerinatalDx;
	}
	public String getProviderSpecialtyPerinatalDx() {
		return providerSpecialtyPerinatalDx;
	}
	public void setProviderSpecialtyPerinatalDx(String providerSpecialtyPerinatalDx) {
		this.providerSpecialtyPerinatalDx = providerSpecialtyPerinatalDx;
	}
	public String getChildMomHivInfectionStatus() {
		return childMomHivInfectionStatus;
	}
	public void setChildMomHivInfectionStatus(String childMomHivInfectionStatus) {
		this.childMomHivInfectionStatus = childMomHivInfectionStatus;
	}
	public String getMotherFirstPosTestDate() {
		return motherFirstPosTestDate;
	}
	public void setMotherFirstPosTestDate(String motherFirstPosTestDate) {
		this.motherFirstPosTestDate = motherFirstPosTestDate;
	}
	public String getMotherCounseledHivPregnancy() {
		return motherCounseledHivPregnancy;
	}
	public void setMotherCounseledHivPregnancy(String motherCounseledHivPregnancy) {
		this.motherCounseledHivPregnancy = motherCounseledHivPregnancy;
	}
	public String getPerinatalHivInfection() {
		return perinatalHivInfection;
	}
	public void setPerinatalHivInfection(String perinatalHivInfection) {
		this.perinatalHivInfection = perinatalHivInfection;
	}
	public String getMotherRecvBloodTransfusion() {
		return motherRecvBloodTransfusion;
	}
	public void setMotherRecvBloodTransfusion(String motherRecvBloodTransfusion) {
		this.motherRecvBloodTransfusion = motherRecvBloodTransfusion;
	}
	public String getMotherFirstRecvBloodDate() {
		return motherFirstRecvBloodDate;
	}
	public void setMotherFirstRecvBloodDate(String motherFirstRecvBloodDate) {
		this.motherFirstRecvBloodDate = motherFirstRecvBloodDate;
	}
	public String getMotherLastRecvBloodDate() {
		return motherLastRecvBloodDate;
	}
	public void setMotherLastRecvBloodDate(String motherLastRecvBloodDate) {
		this.motherLastRecvBloodDate = motherLastRecvBloodDate;
	}
	public String getMotherRecvTransplant() {
		return motherRecvTransplant;
	}
	public void setMotherRecvTransplant(String motherRecvTransplant) {
		this.motherRecvTransplant = motherRecvTransplant;
	}
	public String getIduBeforeDiag() {
		return iduBeforeDiag;
	}
	public void setIduBeforeDiag(String iduBeforeDiag) {
		this.iduBeforeDiag = iduBeforeDiag;
	}
	public String getRecvClottingFactorBeforeDiag() {
		return recvClottingFactorBeforeDiag;
	}
	public void setRecvClottingFactorBeforeDiag(String recvClottingFactorBeforeDiag) {
		this.recvClottingFactorBeforeDiag = recvClottingFactorBeforeDiag;
	}
	public String getClottingFactorRecvBeforeDiag() {
		return clottingFactorRecvBeforeDiag;
	}
	public void setClottingFactorRecvBeforeDiag(String clottingFactorRecvBeforeDiag) {
		this.clottingFactorRecvBeforeDiag = clottingFactorRecvBeforeDiag;
	}
	public String getClottingFactorBeforeDiagRecvDate() {
		return clottingFactorBeforeDiagRecvDate;
	}
	public void setClottingFactorBeforeDiagRecvDate(
			String clottingFactorBeforeDiagRecvDate) {
		this.clottingFactorBeforeDiagRecvDate = clottingFactorBeforeDiagRecvDate;
	}
	public String getRecvBloodTransfusionBeforeDiag() {
		return recvBloodTransfusionBeforeDiag;
	}
	public void setRecvBloodTransfusionBeforeDiag(
			String recvBloodTransfusionBeforeDiag) {
		this.recvBloodTransfusionBeforeDiag = recvBloodTransfusionBeforeDiag;
	}
	public String getFirstRecvBloodBeforeDiagDate() {
		return firstRecvBloodBeforeDiagDate;
	}
	public void setFirstRecvBloodBeforeDiagDate(String firstRecvBloodBeforeDiagDate) {
		this.firstRecvBloodBeforeDiagDate = firstRecvBloodBeforeDiagDate;
	}
	public String getLastRecvBloodBeforeDiagDate() {
		return lastRecvBloodBeforeDiagDate;
	}
	public void setLastRecvBloodBeforeDiagDate(String lastRecvBloodBeforeDiagDate) {
		this.lastRecvBloodBeforeDiagDate = lastRecvBloodBeforeDiagDate;
	}
	public String getRecvTransplantBeforeDiag() {
		return recvTransplantBeforeDiag;
	}
	public void setRecvTransplantBeforeDiag(String recvTransplantBeforeDiag) {
		this.recvTransplantBeforeDiag = recvTransplantBeforeDiag;
	}
	public String getSexWithMaleBeforeDiag() {
		return sexWithMaleBeforeDiag;
	}
	public void setSexWithMaleBeforeDiag(String sexWithMaleBeforeDiag) {
		this.sexWithMaleBeforeDiag = sexWithMaleBeforeDiag;
	}
	public String getSexWithFemaleBeforeDiag() {
		return sexWithFemaleBeforeDiag;
	}
	public void setSexWithFemaleBeforeDiag(String sexWithFemaleBeforeDiag) {
		this.sexWithFemaleBeforeDiag = sexWithFemaleBeforeDiag;
	}
	public String getOtherRiskBeforeDiag() {
		return otherRiskBeforeDiag;
	}
	public void setOtherRiskBeforeDiag(String otherRiskBeforeDiag) {
		this.otherRiskBeforeDiag = otherRiskBeforeDiag;
	}
	public String getBacterialInfectionDate() {
		return bacterialInfectionDate;
	}
	public void setBacterialInfectionDate(String bacterialInfectionDate) {
		this.bacterialInfectionDate = bacterialInfectionDate;
	}
	public String getLymphomaInterstitialPneumonia() {
		return lymphomaInterstitialPneumonia;
	}
	public void setLymphomaInterstitialPneumonia(
			String lymphomaInterstitialPneumonia) {
		this.lymphomaInterstitialPneumonia = lymphomaInterstitialPneumonia;
	}
	public String getHivInfectedNotDocumented() {
		return hivInfectedNotDocumented;
	}
	public void setHivInfectedNotDocumented(String hivInfectedNotDocumented) {
		this.hivInfectedNotDocumented = hivInfectedNotDocumented;
	}
	public String getHivInfectedNotDocumentedDate() {
		return hivInfectedNotDocumentedDate;
	}
	public void setHivInfectedNotDocumentedDate(String hivInfectedNotDocumentedDate) {
		this.hivInfectedNotDocumentedDate = hivInfectedNotDocumentedDate;
	}
	public String getNonHivInfectInformByPhy() {
		return nonHivInfectInformByPhy;
	}
	public void setNonHivInfectInformByPhy(String nonHivInfectInformByPhy) {
		this.nonHivInfectInformByPhy = nonHivInfectInformByPhy;
	}
	public String getNonHivInfectInformByPhyDate() {
		return nonHivInfectInformByPhyDate;
	}
	public void setNonHivInfectInformByPhyDate(String nonHivInfectInformByPhyDate) {
		this.nonHivInfectInformByPhyDate = nonHivInfectInformByPhyDate;
	}
	public String getBirthHistAvailable() {
		return birthHistAvailable;
	}
	public void setBirthHistAvailable(String birthHistAvailable) {
		this.birthHistAvailable = birthHistAvailable;
	}
	public String getResBthAddress() {
		return resBthAddress;
	}
	public void setResBthAddress(String resBthAddress) {
		this.resBthAddress = resBthAddress;
	}
	public String getResBthCity() {
		return resBthCity;
	}
	public void setResBthCity(String resBthCity) {
		this.resBthCity = resBthCity;
	}
	public String getResBthState() {
		return resBthState;
	}
	public void setResBthState(String resBthState) {
		this.resBthState = resBthState;
	}
	public String getResBthCountry() {
		return resBthCountry;
	}
	public void setResBthCountry(String resBthCountry) {
		this.resBthCountry = resBthCountry;
	}
	public String getResBthZipcode() {
		return resBthZipcode;
	}
	public void setResBthZipcode(String resBthZipcode) {
		this.resBthZipcode = resBthZipcode;
	}
	public String getResBthPlus4() {
		return resBthPlus4;
	}
	public void setResBthPlus4(String resBthPlus4) {
		this.resBthPlus4 = resBthPlus4;
	}
	public String getResBthCountyFips() {
		return resBthCountyFips;
	}
	public void setResBthCountyFips(String resBthCountyFips) {
		this.resBthCountyFips = resBthCountyFips;
	}
	
	public boolean isResPediatricSeroDxSameAsCurrent() {
		return resPediatricSeroDxSameAsCurrent;
	}
	public boolean isFacilityDxSameAsProvidingPerinatal() {
		return facilityDxSameAsProvidingPerinatal;
	}
	public boolean isResBthSameAsCurrent() {
		return resBthSameAsCurrent;
	}
	public boolean isFacilityAtBthSameAsProviding() {
		return facilityAtBthSameAsProviding;
	}
	public String getFacilityAtBthDxName() {
		return facilityAtBthDxName;
	}
	public void setFacilityAtBthDxName(String facilityAtBthDxName) {
		this.facilityAtBthDxName = facilityAtBthDxName;
	}
	public String getFacilityAddressAtBthDx() {
		return facilityAddressAtBthDx;
	}
	public void setFacilityAddressAtBthDx(String facilityAddressAtBthDx) {
		this.facilityAddressAtBthDx = facilityAddressAtBthDx;
	}
	public String getFacilityCityAtBthDx() {
		return facilityCityAtBthDx;
	}
	public void setFacilityCityAtBthDx(String facilityCityAtBthDx) {
		this.facilityCityAtBthDx = facilityCityAtBthDx;
	}
	public String getFacilityStateAtBthDx() {
		return facilityStateAtBthDx;
	}
	public void setFacilityStateAtBthDx(String facilityStateAtBthDx) {
		this.facilityStateAtBthDx = facilityStateAtBthDx;
	}
	public String getFacilityCountryAtBthDx() {
		return facilityCountryAtBthDx;
	}
	public void setFacilityCountryAtBthDx(String facilityCountryAtBthDx) {
		this.facilityCountryAtBthDx = facilityCountryAtBthDx;
	}
	public String getFacilityZipcodeAtBthDx() {
		return facilityZipcodeAtBthDx;
	}
	public void setFacilityZipcodeAtBthDx(String facilityZipcodeAtBthDx) {
		this.facilityZipcodeAtBthDx = facilityZipcodeAtBthDx;
	}
	public String getFacilityPlus4AtBthDx() {
		return facilityPlus4AtBthDx;
	}
	public void setFacilityPlus4AtBthDx(String facilityPlus4AtBthDx) {
		this.facilityPlus4AtBthDx = facilityPlus4AtBthDx;
	}
	public String getFacilityCountyFipsAtBthDx() {
		return facilityCountyFipsAtBthDx;
	}
	public void setFacilityCountyFipsAtBthDx(String facilityCountyFipsAtBthDx) {
		this.facilityCountyFipsAtBthDx = facilityCountyFipsAtBthDx;
	}
	public String getFacilityPhoneAtBthDx() {
		return facilityPhoneAtBthDx;
	}
	public void setFacilityPhoneAtBthDx(String facilityPhoneAtBthDx) {
		this.facilityPhoneAtBthDx = facilityPhoneAtBthDx;
	}
	public String getFacilityAtBthDxTypeCode() {
		return facilityAtBthDxTypeCode;
	}
	public void setFacilityAtBthDxTypeCode(String facilityAtBthDxTypeCode) {
		this.facilityAtBthDxTypeCode = facilityAtBthDxTypeCode;
	}
	public String getFacilityAtBthDxTypeOther() {
		return facilityAtBthDxTypeOther;
	}
	public void setFacilityAtBthDxTypeOther(String facilityAtBthDxTypeOther) {
		this.facilityAtBthDxTypeOther = facilityAtBthDxTypeOther;
	}
	public String getFacilityAtBthDxEharsUid() {
		return facilityAtBthDxEharsUid;
	}
	public void setFacilityAtBthDxEharsUid(String facilityAtBthDxEharsUid) {
		this.facilityAtBthDxEharsUid = facilityAtBthDxEharsUid;
	}
	public int getBirthWeightLbs() {
		return birthWeightLbs;
	}
	public void setBirthWeightLbs(int birthWeightLbs) {
		this.birthWeightLbs = birthWeightLbs;
	}
	public int getBirthWeightOz() {
		return birthWeightOz;
	}
	public void setBirthWeightOz(int birthWeightOz) {
		this.birthWeightOz = birthWeightOz;
	}
	public int getBirthWeightGram() {
		return birthWeightGram;
	}
	public void setBirthWeightGram(int birthWeightGram) {
		this.birthWeightGram = birthWeightGram;
	}
	public String getBirthType() {
		return birthType;
	}
	public void setBirthType(String birthType) {
		this.birthType = birthType;
	}
	public String getBirthDelivery() {
		return birthDelivery;
	}
	public void setBirthDelivery(String birthDelivery) {
		this.birthDelivery = birthDelivery;
	}
	public String getBirthDefects() {
		return birthDefects;
	}
	public void setBirthDefects(String birthDefects) {
		this.birthDefects = birthDefects;
	}
	public String getBirthDefectsTypes() {
		return birthDefectsTypes;
	}
	public void setBirthDefectsTypes(String birthDefectsTypes) {
		this.birthDefectsTypes = birthDefectsTypes;
	}
	public String getNeonatalStatus() {
		return neonatalStatus;
	}
	public void setNeonatalStatus(String neonatalStatus) {
		this.neonatalStatus = neonatalStatus;
	}
	public String getNeonatalGestAgeInWeeks() {
		return neonatalGestAgeInWeeks;
	}
	public void setNeonatalGestAgeInWeeks(String neonatalGestAgeInWeeks) {
		this.neonatalGestAgeInWeeks = neonatalGestAgeInWeeks;
	}
	public String getPrenatalCareBeginMonth() {
		return prenatalCareBeginMonth;
	}
	public void setPrenatalCareBeginMonth(String prenatalCareBeginMonth) {
		this.prenatalCareBeginMonth = prenatalCareBeginMonth;
	}
	public String getPrenatalCareNoVisits() {
		return prenatalCareNoVisits;
	}
	public void setPrenatalCareNoVisits(String prenatalCareNoVisits) {
		this.prenatalCareNoVisits = prenatalCareNoVisits;
	}
	public String getMotherRecvArvPriorPregnancy() {
		return motherRecvArvPriorPregnancy;
	}
	public void setMotherRecvArvPriorPregnancy(String motherRecvArvPriorPregnancy) {
		this.motherRecvArvPriorPregnancy = motherRecvArvPriorPregnancy;
	}
	public String getMotherRecvArvPriorPregnancyNotes() {
		return motherRecvArvPriorPregnancyNotes;
	}
	public void setMotherRecvArvPriorPregnancyNotes(
			String motherRecvArvPriorPregnancyNotes) {
		this.motherRecvArvPriorPregnancyNotes = motherRecvArvPriorPregnancyNotes;
	}
	public String getArvPriorPregnancyBeganDate() {
		return arvPriorPregnancyBeganDate;
	}
	public void setArvPriorPregnancyBeganDate(String arvPriorPregnancyBeganDate) {
		this.arvPriorPregnancyBeganDate = arvPriorPregnancyBeganDate;
	}
	public String getArvPriorPregnancyLastUseDate() {
		return arvPriorPregnancyLastUseDate;
	}
	public void setArvPriorPregnancyLastUseDate(String arvPriorPregnancyLastUseDate) {
		this.arvPriorPregnancyLastUseDate = arvPriorPregnancyLastUseDate;
	}
	public String getMotherRecvArvDuringPregnancy() {
		return motherRecvArvDuringPregnancy;
	}
	public void setMotherRecvArvDuringPregnancy(String motherRecvArvDuringPregnancy) {
		this.motherRecvArvDuringPregnancy = motherRecvArvDuringPregnancy;
	}
	public String getMotherRecvArvDuringPregnancyNotes() {
		return motherRecvArvDuringPregnancyNotes;
	}
	public void setMotherRecvArvDuringPregnancyNotes(
			String motherRecvArvDuringPregnancyNotes) {
		this.motherRecvArvDuringPregnancyNotes = motherRecvArvDuringPregnancyNotes;
	}
	public String getArvDuringPregnancyBeganDate() {
		return arvDuringPregnancyBeganDate;
	}
	public void setArvDuringPregnancyBeganDate(String arvDuringPregnancyBeganDate) {
		this.arvDuringPregnancyBeganDate = arvDuringPregnancyBeganDate;
	}
	public String getArvDuringPregnancyLastUseDate() {
		return arvDuringPregnancyLastUseDate;
	}
	public void setArvDuringPregnancyLastUseDate(
			String arvDuringPregnancyLastUseDate) {
		this.arvDuringPregnancyLastUseDate = arvDuringPregnancyLastUseDate;
	}
	public String getMotherRecvArvLaborPregnancy() {
		return motherRecvArvLaborPregnancy;
	}
	public void setMotherRecvArvLaborPregnancy(String motherRecvArvLaborPregnancy) {
		this.motherRecvArvLaborPregnancy = motherRecvArvLaborPregnancy;
	}
	public String getMotherRecvArvLaborPregnancyNotes() {
		return motherRecvArvLaborPregnancyNotes;
	}
	public void setMotherRecvArvLaborPregnancyNotes(
			String motherRecvArvLaborPregnancyNotes) {
		this.motherRecvArvLaborPregnancyNotes = motherRecvArvLaborPregnancyNotes;
	}
	public String getArvLaborPregnancyBeganDate() {
		return arvLaborPregnancyBeganDate;
	}
	public void setArvLaborPregnancyBeganDate(String arvLaborPregnancyBeganDate) {
		this.arvLaborPregnancyBeganDate = arvLaborPregnancyBeganDate;
	}
	public String getArvLaborPregnancyLastUseDate() {
		return arvLaborPregnancyLastUseDate;
	}
	public void setArvLaborPregnancyLastUseDate(String arvLaborPregnancyLastUseDate) {
		this.arvLaborPregnancyLastUseDate = arvLaborPregnancyLastUseDate;
	}
	public String getMaternalDob() {
		return maternalDob;
	}
	public void setMaternalDob(String maternalDob) {
		this.maternalDob = maternalDob;
	}
	public String getMaternalLastNameSndx() {
		return maternalLastNameSndx;
	}
	public void setMaternalLastNameSndx(String maternalLastNameSndx) {
		this.maternalLastNameSndx = maternalLastNameSndx;
	}
	public String getMaternalStateIdNumber() {
		return maternalStateIdNumber;
	}
	public void setMaternalStateIdNumber(String maternalStateIdNumber) {
		this.maternalStateIdNumber = maternalStateIdNumber;
	}
	public String getMaternalCountryOfBirth() {
		return maternalCountryOfBirth;
	}
	public void setMaternalCountryOfBirth(String maternalCountryOfBirth) {
		this.maternalCountryOfBirth = maternalCountryOfBirth;
	}
	public String getOtherMaternalId() {
		return otherMaternalId;
	}
	public void setOtherMaternalId(String otherMaternalId) {
		this.otherMaternalId = otherMaternalId;
	}
	public String getOtherMaternalIdType() {
		return otherMaternalIdType;
	}
	public void setOtherMaternalIdType(String otherMaternalIdType) {
		this.otherMaternalIdType = otherMaternalIdType;
	}
	public String getChildEverTakenArvs() {
		return childEverTakenArvs;
	}
	public void setChildEverTakenArvs(String childEverTakenArvs) {
		this.childEverTakenArvs = childEverTakenArvs;
	}
	public String getChildEverTakenPcpProphylaxis() {
		return childEverTakenPcpProphylaxis;
	}
	public void setChildEverTakenPcpProphylaxis(String childEverTakenPcpProphylaxis) {
		this.childEverTakenPcpProphylaxis = childEverTakenPcpProphylaxis;
	}
	public String getProphylaxisBeganDate() {
		return prophylaxisBeganDate;
	}
	public void setProphylaxisBeganDate(String prophylaxisBeganDate) {
		this.prophylaxisBeganDate = prophylaxisBeganDate;
	}
	public String getProphylaxisLastUseDate() {
		return prophylaxisLastUseDate;
	}
	public void setProphylaxisLastUseDate(String prophylaxisLastUseDate) {
		this.prophylaxisLastUseDate = prophylaxisLastUseDate;
	}
	public String getWasChildBreastfed() {
		return wasChildBreastfed;
	}
	public void setWasChildBreastfed(String wasChildBreastfed) {
		this.wasChildBreastfed = wasChildBreastfed;
	}
	public String getChildPrimaryCaretaker() {
		return childPrimaryCaretaker;
	}
	public void setChildPrimaryCaretaker(String childPrimaryCaretaker) {
		this.childPrimaryCaretaker = childPrimaryCaretaker;
	}
	public String getHivAddressTypeCode() {
		return hivAddressTypeCode;
	}
	public void setHivAddressTypeCode(String hivAddressTypeCode) {
		this.hivAddressTypeCode = hivAddressTypeCode;
	}
	public String getAidsAddressTypeCode() {
		return aidsAddressTypeCode;
	}
	public void setAidsAddressTypeCode(String aidsAddressTypeCode) {
		this.aidsAddressTypeCode = aidsAddressTypeCode;
	}
	public String getBthAddressTypeCode() {
		return bthAddressTypeCode;
	}
	public void setBthAddressTypeCode(String bthAddressTypeCode) {
		this.bthAddressTypeCode = bthAddressTypeCode;
	}
	public String getPediatricAddressTypeCode() {
		return pediatricAddressTypeCode;
	}
	public void setPediatricAddressTypeCode(String pediatricAddressTypeCode) {
		this.pediatricAddressTypeCode = pediatricAddressTypeCode;
	}
	public String getPerinatalAddressTypeCode() {
		return perinatalAddressTypeCode;
	}
	public void setPerinatalAddressTypeCode(String perinatalAddressTypeCode) {
		this.perinatalAddressTypeCode = perinatalAddressTypeCode;
	}
	public String getGenderIdentifiedDate() {
		return genderIdentifiedDate;
	}
	public void setGenderIdentifiedDate(String genderIdentifiedDate) {
		this.genderIdentifiedDate = genderIdentifiedDate;
	}
	public String getSexOrientation() {
		return sexOrientation;
	}
	public void setSexOrientation(String sexOrientation) {
		this.sexOrientation = sexOrientation;
	}
	public String getAdditionalsexOrientation() {
		return additionalsexOrientation;
	}
	public void setAdditionalsexOrientation(String additionalsexOrientation) {
		this.additionalsexOrientation = additionalsexOrientation;
	}
	public String getSexOrientationDate() {
		return sexOrientationDate;
	}
	public void setSexOrientationDate(String sexOrientationDate) {
		this.sexOrientationDate = sexOrientationDate;
	}
	public String getPosHivTestSelfTest() {
		return posHivTestSelfTest;
	}
	public void setPosHivTestSelfTest(String posHivTestSelfTest) {
		this.posHivTestSelfTest = posHivTestSelfTest;
	}
	public String getNegHivTestSelfTest() {
		return negHivTestSelfTest;
	}
	public void setNegHivTestSelfTest(String negHivTestSelfTest) {
		this.negHivTestSelfTest = negHivTestSelfTest;
	}
	
	public Integer getNumNegTestBySelfTest() {
		return numNegTestBySelfTest;
	}
	public void setNumNegTestBySelfTest(Integer numNegTestBySelfTest) {
		this.numNegTestBySelfTest = numNegTestBySelfTest;
	}
	public String getNumNegTestBySelfTestUnkRef() {
		return numNegTestBySelfTestUnkRef;
	}
	public void setNumNegTestBySelfTestUnkRef(String numNegTestBySelfTestUnkRef) {
		this.numNegTestBySelfTestUnkRef = numNegTestBySelfTestUnkRef;
	}
	public String getRapidTestType() {
		return rapidTestType;
	}
	public void setRapidTestType(String rapidTestType) {
		this.rapidTestType = rapidTestType;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getChildBreastfedBp() {
		return childBreastfedBp;
	}
	public void setChildBreastfedBp(String childBreastfedBp) {
		this.childBreastfedBp = childBreastfedBp;
	}
	
	public String getChildBreastfedStartDate() {
		return childBreastfedStartDate;
	}
	public void setChildBreastfedStartDate(String childBreastfedStartDate) {
		this.childBreastfedStartDate = childBreastfedStartDate;
	}
	public String getChildBreastfedStopDate() {
		return childBreastfedStopDate;
	}
	public void setChildBreastfedStopDate(String childBreastfedStopDate) {
		this.childBreastfedStopDate = childBreastfedStopDate;
	}
	public String getChildPrechewedFoodBp() {
		return childPrechewedFoodBp;
	}
	public void setChildPrechewedFoodBp(String childPrechewedFoodBp) {
		this.childPrechewedFoodBp = childPrechewedFoodBp;
	}
	public String getBreastfedNonBp() {
		return breastfedNonBp;
	}
	public void setBreastfedNonBp(String breastfedNonBp) {
		this.breastfedNonBp = breastfedNonBp;
	}
	public String getPrechewedFoodNonBp() {
		return prechewedFoodNonBp;
	}
	public void setPrechewedFoodNonBp(String prechewedFoodNonBp) {
		this.prechewedFoodNonBp = prechewedFoodNonBp;
	}
	

	public boolean isCsRsnHiv() {
		return csRsnHiv;
	}
	public void setCsRsnHiv(boolean csRsnHiv) {
		this.csRsnHiv = csRsnHiv;
	}
	public boolean isCsRsnLongLabor() {
		return csRsnLongLabor;
	}
	public void setCsRsnLongLabor(boolean csRsnLongLabor) {
		this.csRsnLongLabor = csRsnLongLabor;
	}
	public boolean isCsRsnAbruptiaPrevia() {
		return csRsnAbruptiaPrevia;
	}
	public void setCsRsnAbruptiaPrevia(boolean csRsnAbruptiaPrevia) {
		this.csRsnAbruptiaPrevia = csRsnAbruptiaPrevia;
	}
	public boolean isCsRsnNotSpecified() {
		return csRsnNotSpecified;
	}
	public void setCsRsnNotSpecified(boolean csRsnNotSpecified) {
		this.csRsnNotSpecified = csRsnNotSpecified;
	}
	public boolean isCsRsnPrevCs() {
		return csRsnPrevCs;
	}
	public void setCsRsnPrevCs(boolean csRsnPrevCs) {
		this.csRsnPrevCs = csRsnPrevCs;
	}
	public boolean isCsRsnPref() {
		return csRsnPref;
	}
	public void setCsRsnPref(boolean csRsnPref) {
		this.csRsnPref = csRsnPref;
	}
	public boolean isCsRsnOther() {
		return csRsnOther;
	}
	public void setCsRsnOther(boolean csRsnOther) {
		this.csRsnOther = csRsnOther;
	}
	public boolean isCsRsnMalpresentation() {
		return csRsnMalpresentation;
	}
	public void setCsRsnMalpresentation(boolean csRsnMalpresentation) {
		this.csRsnMalpresentation = csRsnMalpresentation;
	}
	public boolean isCsRsnFetalDistress() {
		return csRsnFetalDistress;
	}
	public void setCsRsnFetalDistress(boolean csRsnFetalDistress) {
		this.csRsnFetalDistress = csRsnFetalDistress;
	}
	public String getCsRsnOtherSpecify() {
		return csRsnOtherSpecify;
	}
	public void setCsRsnOtherSpecify(String csRsnOtherSpecify) {
		this.csRsnOtherSpecify = csRsnOtherSpecify;
	}
	public String getRuptureDt() {
		return ruptureDt;
	}
	public void setRuptureDt(String ruptureDt) {
		this.ruptureDt = ruptureDt;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getRuptureTime() {
		return ruptureTime;
	}
	public void setRuptureTime(String ruptureTime) {
		this.ruptureTime = formatMaskTime(ruptureTime);
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = formatMaskTime(deliveryTime);
	}
	public String getCongenitalDisorders() {
		return congenitalDisorders;
	}
	public void setCongenitalDisorders(String congenitalDisorders) {
		this.congenitalDisorders = congenitalDisorders;
	}
	public String getCongenitalDisordersCdl5() {
		return congenitalDisordersCdl5;
	}
	public void setCongenitalDisordersCdl5(String congenitalDisordersCdl5) {
		this.congenitalDisordersCdl5 = congenitalDisordersCdl5;
	}
	public String getScreenSubstance() {
		return screenSubstance;
	}
	public void setScreenSubstance(String screenSubstance) {
		this.screenSubstance = screenSubstance;
	}
	
	public List<PcrfSubstanceDocVO> getBpScreenSubstanceList() {
		return bpScreenSubstanceList;
	}
	public void setBpScreenSubstanceList(List<PcrfSubstanceDocVO> bpScreenSubstanceList) {
		this.bpScreenSubstanceList = bpScreenSubstanceList;
	}
	
	public List<PcrfSubstanceDocVO> getScreenSubstanceList() {
		return screenSubstanceList;
	}
	public void setScreenSubstanceList(List<PcrfSubstanceDocVO> screenSubstanceList) {
		this.screenSubstanceList = screenSubstanceList;
	}
	public List<PcrfSubstanceDocVO> getBpUseSubstanceList() {
		return bpUseSubstanceList;
	}
	public void setBpUseSubstanceList(List<PcrfSubstanceDocVO> bpUseSubstanceList) {
		this.bpUseSubstanceList = bpUseSubstanceList;
	}
	public List<PcrfSubstanceDocVO> getBpScreenConditionsList() {
		return bpScreenConditionsList;
	}
	public void setBpScreenConditionsList(List<PcrfSubstanceDocVO> bpScreenConditionsList) {
		this.bpScreenConditionsList = bpScreenConditionsList;
	}
	public List<PcrfSubstanceDocVO> getBpDxScreenConditionsList() {
		return bpDxScreenConditionsList;
	}
	public void setBpDxScreenConditionsList(List<PcrfSubstanceDocVO> bpDxScreenConditionsList) {
		this.bpDxScreenConditionsList = bpDxScreenConditionsList;
	}
	public String getBpCityNumber() {
		return bpCityNumber;
	}
	public void setBpCityNumber(String bpCityNumber) {
		this.bpCityNumber = bpCityNumber;
	}
	public String getPregBefore() {
		return pregBefore;
	}
	public void setPregBefore(String pregBefore) {
		this.pregBefore = pregBefore;
	}
	public String getBpCd4Test() {
		return bpCd4Test;
	}
	public void setBpCd4Test(String bpCd4Test) {
		this.bpCd4Test = bpCd4Test;
	}
	public String getBpVlTest() {
		return bpVlTest;
	}
	public void setBpVlTest(String bpVlTest) {
		this.bpVlTest = bpVlTest;
	}
	public String getBpArvPregDrugRsn() {
		return bpArvPregDrugRsn;
	}
	public void setBpArvPregDrugRsn(String bpArvPregDrugRsn) {
		this.bpArvPregDrugRsn = bpArvPregDrugRsn;
	}
	public String getBpArvPregDrugRsnOther() {
		return bpArvPregDrugRsnOther;
	}
	public void setBpArvPregDrugRsnOther(String bpArvPregDrugRsnOther) {
		this.bpArvPregDrugRsnOther = bpArvPregDrugRsnOther;
	}
	public String getBpArvLaborDrugRsn() {
		return bpArvLaborDrugRsn;
	}
	public void setBpArvLaborDrugRsn(String bpArvLaborDrugRsn) {
		this.bpArvLaborDrugRsn = bpArvLaborDrugRsn;
	}
	public String getBpArvLaborDrugRsnOther() {
		return bpArvLaborDrugRsnOther;
	}
	public void setBpArvLaborDrugRsnOther(String bpArvLaborDrugRsnOther) {
		this.bpArvLaborDrugRsnOther = bpArvLaborDrugRsnOther;
	}
	public String getBpUseSubstance() {
		return bpUseSubstance;
	}
	public void setBpUseSubstance(String bpUseSubstance) {
		this.bpUseSubstance = bpUseSubstance;
	}
	public String getBpScreenSubstance() {
		return bpScreenSubstance;
	}
	public void setBpScreenSubstance(String bpScreenSubstance) {
		this.bpScreenSubstance = bpScreenSubstance;
	}
	
	public String getNumPrevPreg() {
		return numPrevPreg;
	}
	public void setNumPrevPreg(String numPrevPreg) {
		this.numPrevPreg = numPrevPreg;
	}
	public List<PcrfSubstanceDocVO> getBpPregOutcomeList() {
		return bpPregOutcomeList;
	}
	public void setBpPregOutcomeList(List<PcrfSubstanceDocVO> bpPregOutcomeList) {
		this.bpPregOutcomeList = bpPregOutcomeList;
	}
	public String getNirBeforeDiag() {
		return nirBeforeDiag;
	}
	public void setNirBeforeDiag(String nirBeforeDiag) {
		this.nirBeforeDiag = nirBeforeDiag;
	}
	public String getMotherPerinatalHivInfection() {
		return motherPerinatalHivInfection;
	}
	public void setMotherPerinatalHivInfection(String motherPerinatalHivInfection) {
		this.motherPerinatalHivInfection = motherPerinatalHivInfection;
	}
	public String getMotherIdu() {
		return motherIdu;
	}
	public void setMotherIdu(String motherIdu) {
		this.motherIdu = motherIdu;
	}
	public String getMotherHeterosexWithIdu() {
		return motherHeterosexWithIdu;
	}
	public void setMotherHeterosexWithIdu(String motherHeterosexWithIdu) {
		this.motherHeterosexWithIdu = motherHeterosexWithIdu;
	}
	public String getMotherHeterosexWithMsm() {
		return motherHeterosexWithMsm;
	}
	public void setMotherHeterosexWithMsm(String motherHeterosexWithMsm) {
		this.motherHeterosexWithMsm = motherHeterosexWithMsm;
	}
	public String getMotherHeterosexWithHemophiliac() {
		return motherHeterosexWithHemophiliac;
	}
	public void setMotherHeterosexWithHemophiliac(String motherHeterosexWithHemophiliac) {
		this.motherHeterosexWithHemophiliac = motherHeterosexWithHemophiliac;
	}
	public String getMotherHeterosexWithTransfusion() {
		return motherHeterosexWithTransfusion;
	}
	public void setMotherHeterosexWithTransfusion(String motherHeterosexWithTransfusion) {
		this.motherHeterosexWithTransfusion = motherHeterosexWithTransfusion;
	}
	public String getMotherHeterosexWithHiv() {
		return motherHeterosexWithHiv;
	}
	public void setMotherHeterosexWithHiv(String motherHeterosexWithHiv) {
		this.motherHeterosexWithHiv = motherHeterosexWithHiv;
	}
	public String getMotherHeterosexWithTransplant() {
		return motherHeterosexWithTransplant;
	}
	public void setMotherHeterosexWithTransplant(String motherHeterosexWithTransplant) {
		this.motherHeterosexWithTransplant = motherHeterosexWithTransplant;
	}
			
	//PCRF fields
	
	
}
