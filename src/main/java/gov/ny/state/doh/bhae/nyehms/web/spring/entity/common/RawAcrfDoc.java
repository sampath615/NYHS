package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * An adult case report form as entered (table RAW_ACRF_DOC): the form's columns that AcrfDocDAO reads and writes, and
 * the HIV testing and treatment history (TTH) columns and form ids that TTHDocumentDAO.updateTTHDocData changes.
 * <p>
 * Read through JPQL projections and written by JPQL inserts and bulk updates (no entity is loaded or saved). The
 * attribute types follow what MyBatis read and bound: text, Integer for the int / Integer form fields, Boolean for the
 * check boxes (bound with setBoolean, read with getBoolean, as before), Timestamp for the date columns.
 * <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "RAW_ACRF_DOC")
public class RawAcrfDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;

	@Column(name = "FNAME")
	private String fname;

	@Column(name = "MNAME")
	private String mname;

	@Column(name = "LNAME")
	private String lname;

	@Column(name = "NAME_TYPE_CODE")
	private String nameTypeCode;

	@Column(name = "ALT_FNAME")
	private String altFname;

	@Column(name = "ALT_MNAME")
	private String altMname;

	@Column(name = "ALT_LNAME")
	private String altLname;

	@Column(name = "ADDRESS_TYPE_CODE")
	private String addressTypeCode;

	@Column(name = "HIV_ADDRESS_TYPE_CODE")
	private String hivAddressTypeCode;

	@Column(name = "AIDS_ADDRESS_TYPE_CODE")
	private String aidsAddressTypeCode;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "PLUS4")
	private String plus4;

	@Column(name = "COUNTY_FIPS")
	private String countyFips;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "TELEPHONE")
	private String telephone;

	@Column(name = "MED_RECORD_NUMBER")
	private String medRecordNumber;

	@Column(name = "ID_TYPE_CODE")
	private String idTypeCode;

	@Column(name = "OTHER_ID_NUMBER")
	private String otherIdNumber;

	@Column(name = "RECEIVED_DATE")
	private Timestamp receivedDate;

	@Column(name = "EHARS_DOC_ID")
	private String eharsDocId;

	@Column(name = "STATENO")
	private String stateno;

	@Column(name = "DOCUMENT_SOURCE")
	private String documentSource;

	@Column(name = "SURVEILLANCE_METHOD_CODE")
	private String surveillanceMethodCode;

	@Column(name = "NEW_CASE_CODE")
	private String newCaseCode;

	@Column(name = "REPORT_MEDIUM_CODE")
	private String reportMediumCode;

	@Column(name = "PROVIDING_FACILITY_NAME")
	private String providingFacilityName;

	@Column(name = "PROVIDING_FACILITY_ADDRESS")
	private String providingFacilityAddress;

	@Column(name = "PROVIDING_FACILITY_CITY")
	private String providingFacilityCity;

	@Column(name = "PROVIDING_FACILITY_STATE")
	private String providingFacilityState;

	@Column(name = "PROVIDING_FACILITY_COUNTRY")
	private String providingFacilityCountry;

	@Column(name = "PROVIDING_FACILITY_ZIPCODE")
	private String providingFacilityZipcode;

	@Column(name = "PROVIDING_FACILITY_PLUS4")
	private String providingFacilityPlus4;

	@Column(name = "PROVIDING_FACILITY_COUNTY_FIPS")
	private String providingFacilityCountyFips;

	@Column(name = "PROVIDING_FACILITY_PHONE")
	private String providingFacilityPhone;

	@Column(name = "PROVIDING_FACILITY_TYPE_CODE")
	private String providingFacilityTypeCode;

	@Column(name = "PROVIDING_FACILTY_TYPE_OTHER")
	private String providingFaciltyTypeOther;

	@Column(name = "DATE_COMPLETED")
	private Timestamp dateCompleted;

	@Column(name = "PERSON_COMPLETING")
	private String personCompleting;

	@Column(name = "WORKER_PHONE")
	private String workerPhone;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "BIRTH_COUNTRY_OTHER")
	private String birthCountryOther;

	@Column(name = "DATE_OF_BIRTH")
	private String dateOfBirth;

	@Column(name = "ALIAS_DATE_OF_BIRTH")
	private String aliasDateOfBirth;

	@Column(name = "VITAL_STATISTICS_CODE")
	private String vitalStatisticsCode;

	@Column(name = "DATE_OF_DEATH")
	private String dateOfDeath;

	@Column(name = "STATE_OF_DEATH")
	private String stateOfDeath;

	@Column(name = "GENDER_CODE")
	private String genderCode;

	@Column(name = "ADDITIONAL_GENDER")
	private String additionalGender;

	@Column(name = "ETHNICITY_CODE")
	private String ethnicityCode;

	@Column(name = "EXPANDED_ETHNICITY")
	private String expandedEthnicity;

	@Column(name = "RACE_INDIAN")
	private Boolean raceIndian;

	@Column(name = "RACE_ASIAN")
	private Boolean raceAsian;

	@Column(name = "RACE_BLACK")
	private Boolean raceBlack;

	@Column(name = "RACE_PACIFIC")
	private Boolean racePacific;

	@Column(name = "RACE_WHITE")
	private Boolean raceWhite;

	@Column(name = "RACE_UNKNOWN")
	private Boolean raceUnknown;

	@Column(name = "EXPANDED_RACE")
	private String expandedRace;

	@Column(name = "RES_HIV_DX")
	private Boolean resHivDx;

	@Column(name = "RES_AIDS_DX")
	private Boolean resAidsDx;

	@Column(name = "RES_DX_SAME_AS_CURRENT")
	private Boolean resDxSameAsCurrent;

	@Column(name = "RES_DX_SAME_AS_CURRENT_AIDS")
	private Boolean resDxSameAsCurrentAids;

	@Column(name = "RES_HIV_ADDRESS")
	private String resHivAddress;

	@Column(name = "RES_HIV_CITY")
	private String resHivCity;

	@Column(name = "RES_HIV_STATE")
	private String resHivState;

	@Column(name = "RES_HIV_COUNTRY")
	private String resHivCountry;

	@Column(name = "RES_HIV_ZIPCODE")
	private String resHivZipcode;

	@Column(name = "RES_HIV_PLUS4")
	private String resHivPlus4;

	@Column(name = "RES_HIV_COUNTY_FIPS")
	private String resHivCountyFips;

	@Column(name = "RES_AIDS_ADDRESS")
	private String resAidsAddress;

	@Column(name = "RES_AIDS_CITY")
	private String resAidsCity;

	@Column(name = "RES_AIDS_STATE")
	private String resAidsState;

	@Column(name = "RES_AIDS_COUNTRY")
	private String resAidsCountry;

	@Column(name = "RES_AIDS_ZIPCODE")
	private String resAidsZipcode;

	@Column(name = "RES_AIDS_PLUS4")
	private String resAidsPlus4;

	@Column(name = "RES_AIDS_COUNTY_FIPS")
	private String resAidsCountyFips;

	@Column(name = "SL_PROVIDER_LNAME")
	private String slProviderLname;

	@Column(name = "SL_PROVIDER_FNAME")
	private String slProviderFname;

	@Column(name = "SL_PROVIDER_MNAME")
	private String slProviderMname;

	@Column(name = "SL_PROVIDER_SUFFIX")
	private String slProviderSuffix;

	@Column(name = "SL_PROVIDER_PHONE")
	private String slProviderPhone;

	@Column(name = "SL_MED_RECORD_NUM")
	private String slMedRecordNum;

	@Column(name = "SL_FACILITY_NAME")
	private String slFacilityName;

	@Column(name = "SL_PERSON_COMPLETING")
	private String slPersonCompleting;

	@Column(name = "SL_WORKER_PHONE")
	private String slWorkerPhone;

	@Column(name = "FACILITY_DX_HIV")
	private Boolean facilityDxHiv;

	@Column(name = "FACILITY_DX_AIDS")
	private Boolean facilityDxAids;

	@Column(name = "FACILITY_DX_SAME_AS_PROVIDING")
	private Boolean facilityDxSameAsProviding;

	@Column(name = "FAC_DX_SAMEAS_PROVIDING_AIDS")
	private Boolean facDxSameasProvidingAids;

	@Column(name = "FACILITY_NAME_HIV_DX")
	private String facilityNameHivDx;

	@Column(name = "FACILITY_ADDRESS_HIV_DX")
	private String facilityAddressHivDx;

	@Column(name = "FACILITY_CITY_HIV_DX")
	private String facilityCityHivDx;

	@Column(name = "FACILITY_STATE_HIV_DX")
	private String facilityStateHivDx;

	@Column(name = "FACILITY_COUNTRY_HIV_DX")
	private String facilityCountryHivDx;

	@Column(name = "FACILITY_ZIPCODE_HIV_DX")
	private String facilityZipcodeHivDx;

	@Column(name = "FACILITY_PLUS4_HIV_DX")
	private String facilityPlus4HivDx;

	@Column(name = "FACILITY_COUNTY_FIPS_HIV_DX")
	private String facilityCountyFipsHivDx;

	@Column(name = "FACILITY_PHONE_HIV_DX")
	private String facilityPhoneHivDx;

	@Column(name = "FACILITY_TYPE_CODE_HIV_DX")
	private String facilityTypeCodeHivDx;

	@Column(name = "FACILITY_TYPE_OTHER_HIV_DX")
	private String facilityTypeOtherHivDx;

	@Column(name = "PROVIDER_FNAME_HIV_DX")
	private String providerFnameHivDx;

	@Column(name = "PROVIDER_MNAME_HIV_DX")
	private String providerMnameHivDx;

	@Column(name = "PROVIDER_LNAME_HIV_DX")
	private String providerLnameHivDx;

	@Column(name = "PROVIDER_SUFFIX_HIV_DX")
	private String providerSuffixHivDx;

	@Column(name = "PROVIDER_PHONE_HIV_DX")
	private String providerPhoneHivDx;

	@Column(name = "PROVIDER_SPECIALTY")
	private String providerSpecialty;

	@Column(name = "FACILITY_NAME_AIDS_DX")
	private String facilityNameAidsDx;

	@Column(name = "FACILITY_ADDRESS_AIDS_DX")
	private String facilityAddressAidsDx;

	@Column(name = "FACILITY_CITY_AIDS_DX")
	private String facilityCityAidsDx;

	@Column(name = "FACILITY_STATE_AIDS_DX")
	private String facilityStateAidsDx;

	@Column(name = "FACILITY_COUNTRY_AIDS_DX")
	private String facilityCountryAidsDx;

	@Column(name = "FACILITY_ZIPCODE_AIDS_DX")
	private String facilityZipcodeAidsDx;

	@Column(name = "FACILITY_PLUS4_AIDS_DX")
	private String facilityPlus4AidsDx;

	@Column(name = "FACILITY_COUNTY_FIPS_AIDS_DX")
	private String facilityCountyFipsAidsDx;

	@Column(name = "FACILITY_PHONE_AIDS_DX")
	private String facilityPhoneAidsDx;

	@Column(name = "FACILITY_TYPE_CODE_AIDS_DX")
	private String facilityTypeCodeAidsDx;

	@Column(name = "FACILITY_TYPE_OTHER_AIDS_DX")
	private String facilityTypeOtherAidsDx;

	@Column(name = "PROVIDER_FNAME_AIDS_DX")
	private String providerFnameAidsDx;

	@Column(name = "PROVIDER_MNAME_AIDS_DX")
	private String providerMnameAidsDx;

	@Column(name = "PROVIDER_LNAME_AIDS_DX")
	private String providerLnameAidsDx;

	@Column(name = "PROVIDER_SUFFIX_AIDS_DX")
	private String providerSuffixAidsDx;

	@Column(name = "PROVIDER_PHONE_AIDS_DX")
	private String providerPhoneAidsDx;

	@Column(name = "PROVIDER_SPECIALTY_AIDS_DX")
	private String providerSpecialtyAidsDx;

	@Column(name = "PEDIATRIC_RISK")
	private String pediatricRisk;

	@Column(name = "SEX_WITH_MALE")
	private String sexWithMale;

	@Column(name = "SEX_WITH_FEMALE")
	private String sexWithFemale;

	@Column(name = "IDU")
	private String idu;

	@Column(name = "RECV_CLOTTING_FACTOR")
	private String recvClottingFactor;

	@Column(name = "CLOTTING_FACTOR_RECV")
	private String clottingFactorRecv;

	@Column(name = "DATE_RECV_CLOTTING_FACTOR")
	private String dateRecvClottingFactor;

	@Column(name = "HETEROSEX_WITH_IDU")
	private String heterosexWithIdu;

	@Column(name = "HETEROSEX_WITH_MSM")
	private String heterosexWithMsm;

	@Column(name = "HETEROSEX_WITH_HEMOPHILIAC")
	private String heterosexWithHemophiliac;

	@Column(name = "HETEROSEX_WITH_TRANSFUSION")
	private String heterosexWithTransfusion;

	@Column(name = "HETEROSEX_WITH_TRANSPLANT")
	private String heterosexWithTransplant;

	@Column(name = "HETEROSEX_WITH_HIV")
	private String heterosexWithHiv;

	@Column(name = "RECV_BLOOD_TRANSFUSION")
	private String recvBloodTransfusion;

	@Column(name = "FIRST_DATE_RECV_BLOOD")
	private String firstDateRecvBlood;

	@Column(name = "LAST_DATE_RECV_BLOOD")
	private String lastDateRecvBlood;

	@Column(name = "RECV_TRANSPLANT")
	private String recvTransplant;

	@Column(name = "HEALTHCARE_WORKER")
	private String healthcareWorker;

	@Column(name = "OCCUPATION")
	private String occupation;

	@Column(name = "OTHER_RISK")
	private String otherRisk;

	@Column(name = "OTHER_RISK_DETAILS")
	private String otherRiskDetails;

	@Column(name = "LAST_NEG_HIV_DATE")
	private String lastNegHivDate;

	@Column(name = "LAST_NEG_HIV_TEST")
	private String lastNegHivTest;

	@Column(name = "HIV_DIAGNOSIS_BY_PHYSICIAN")
	private String hivDiagnosisByPhysician;

	@Column(name = "PHYISICIAN_DIAGNOSIS_DATE")
	private String phyisicianDiagnosisDate;

	@Column(name = "CANDIDIASIS_BRONCHI_DATE")
	private String candidiasisBronchiDate;

	@Column(name = "CANDIDIASIS_ESOPH_DATE")
	private String candidiasisEsophDate;

	@Column(name = "CARCINOMA_DATE")
	private String carcinomaDate;

	@Column(name = "COCCIDIODOMYCOSIS_DATE")
	private String coccidiodomycosisDate;

	@Column(name = "CRYPTOCOCCOSIS_DATE")
	private String cryptococcosisDate;

	@Column(name = "CRYPTOSPORIDIOSIS_DATE")
	private String cryptosporidiosisDate;

	@Column(name = "CYTOMEGALO_DISEASE_DATE")
	private String cytomegaloDiseaseDate;

	@Column(name = "CYTOMEGALO_RETINITIS_DATE")
	private String cytomegaloRetinitisDate;

	@Column(name = "HIV_ENCEPH_DATE")
	private String hivEncephDate;

	@Column(name = "HERPES_DATE")
	private String herpesDate;

	@Column(name = "HISTOPLASMOSIS_DATE")
	private String histoplasmosisDate;

	@Column(name = "ISOSPORIASIS_DATE")
	private String isosporiasisDate;

	@Column(name = "KAPOSIS_SARCOMA_DATE")
	private String kaposisSarcomaDate;

	@Column(name = "LYMPHOMA_BURKITTS_1")
	private String lymphomaBurkitts1;

	@Column(name = "LYMPHOMA_IMMUNO_DATE")
	private String lymphomaImmunoDate;

	@Column(name = "LYMPHOMA_BRAIN_DATE")
	private String lymphomaBrainDate;

	@Column(name = "M_AVIUM_OR_KANSASII_DATE")
	private String mAviumOrKansasiiDate;

	@Column(name = "TB_PULMONARY_1")
	private String tbPulmonary1;

	@Column(name = "TB_EXTRAPULMONARY_DATE")
	private String tbExtrapulmonaryDate;

	@Column(name = "MYCOBACTERIUM_OTHER_DATE")
	private String mycobacteriumOtherDate;

	@Column(name = "PNUEMOCYSTIS_DATE")
	private String pnuemocystisDate;

	@Column(name = "PNEUMONIA_RECURRENT_DATE")
	private String pneumoniaRecurrentDate;

	@Column(name = "PML_DATE")
	private String pmlDate;

	@Column(name = "SALMONELLA_SEPTIC_DATE")
	private String salmonellaSepticDate;

	@Column(name = "TOXOPLASMOSIS_BRAIN_DATE")
	private String toxoplasmosisBrainDate;

	@Column(name = "WASTING_SYNDROME_DATE")
	private String wastingSyndromeDate;

	@Column(name = "TB_RVCT_CASE_NUMBER")
	private String tbRvctCaseNumber;

	@Column(name = "INFORMED_OF_HIV")
	private String informedOfHiv;

	@Column(name = "PARTNERS_NOTIFIED_BY")
	private String partnersNotifiedBy;

	@Column(name = "REFFERED_FOR_OBGYN")
	private String refferedForObgyn;

	@Column(name = "CURRENTLY_PREGNANT")
	private String currentlyPregnant;

	@Column(name = "LIVE_BIRTH")
	private String liveBirth;

	@Column(name = "MAIN_TTH_SOURCE")
	private String mainTthSource;

	@Column(name = "PT_INTERVIEW_DATE")
	private String ptInterviewDate;

	@Column(name = "EVER_HAD_POS")
	private String everHadPos;

	@Column(name = "FIRST_HIV_TEST_EVER")
	private String firstHivTestEver;

	@Column(name = "EVER_HAD_NEG")
	private String everHadNeg;

	@Column(name = "LAST_NEG_DATE")
	private String lastNegDate;

	@Column(name = "RESP_HIV_TEST_2YRS")
	private String respHivTest2yrs;

	@Column(name = "COUNT_HIV_TEST_2YRS")
	private Integer countHivTest2yrs;

	@Column(name = "PT_ARV_6_MO")
	private String ptArv6Mo;

	@Column(name = "PT_ARV_NAME")
	private String ptArvName;

	@Column(name = "PT_ARV_START_DATE")
	private String ptArvStartDate;

	@Column(name = "PT_ARV_END_DATE")
	private String ptArvEndDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "ACRF_SRC_ID")
	private Integer acrfSrcId;

	@Column(name = "LOCAL_OPTIONAL_FIELDS")
	private String localOptionalFields;

	@Column(name = "CHILD_NAME")
	private String childName;

	@Column(name = "CHILD_SOUNDEX")
	private String childSoundex;

	@Column(name = "CHILD_DOB")
	private String childDob;

	@Column(name = "CHILD_CODED_ID")
	private String childCodedId;

	@Column(name = "CHILD_STATENO")
	private String childStateno;

	@Column(name = "CHILD_FACILITY_NAME")
	private String childFacilityName;

	@Column(name = "CHILD_FACILITY_PHONE")
	private String childFacilityPhone;

	@Column(name = "CHILD_FACILITY_ZIP_CODE")
	private String childFacilityZipCode;

	@Column(name = "CHILD_FACILITY_STREET_ADDRESS")
	private String childFacilityStreetAddress;

	@Column(name = "CHILD_FACILITY_CITY")
	private String childFacilityCity;

	@Column(name = "CHILD_FACILITY_COUNTY")
	private String childFacilityCounty;

	@Column(name = "CHILD_FACILITY_CNTRY")
	private String childFacilityCntry;

	@Column(name = "CHILD_FACILITY_ID")
	private String childFacilityId;

	@Column(name = "CHILD_FACILITY_EHARS_UID")
	private String childFacilityEharsUid;

	@Column(name = "CHILD_FACILITY_TYPE")
	private String childFacilityType;

	@Column(name = "CHILD_FACILITY_STATE")
	private String childFacilityState;

	@Column(name = "LAB_RESULT_MEET_HIV_DIAG_CRIT")
	private String labResultMeetHivDiagCrit;

	@Column(name = "DATE_EARLIEST_POSITIVE")
	private String dateEarliestPositive;

	@Column(name = "REPT_HEALTH_DEPT_CITY_COUNTY")
	private String reptHealthDeptCityCounty;

	@Column(name = "CITY_COUNTY_NUMBER")
	private String cityCountyNumber;

	@Column(name = "LNAME_SOUNDEX")
	private String lnameSoundex;

	@Column(name = "DX_ALGORITHM")
	private String dxAlgorithm;

	@Column(name = "DX_ALGORITHM_DATE")
	private String dxAlgorithmDate;

	@Column(name = "CURRENT_ARVS1")
	private String currentArvs1;

	@Column(name = "FACILITY_ID_HIV_DX")
	private String facilityIdHivDx;

	@Column(name = "FACILITY_EHARS_UID_HIV_DX")
	private String facilityEharsUidHivDx;

	@Column(name = "FACILITY_ID_AIDS_DX")
	private String facilityIdAidsDx;

	@Column(name = "FACILITY_EHARS_UID_AIDS_DX")
	private String facilityEharsUidAidsDx;

	@Column(name = "PROVIDING_FACILITY_ID")
	private String providingFacilityId;

	@Column(name = "PROVIDING_EHARS_FACILITY_UID")
	private String providingEharsFacilityUid;

	@Column(name = "RES_AIDS_ADDRESS_DATE")
	private String resAidsAddressDate;

	@Column(name = "RES_HIV_ADDRESS_DATE")
	private String resHivAddressDate;

	@Column(name = "ADDRESS_DATE")
	private String addressDate;

	@Column(name = "MAIN_ARV_SOURCE")
	private String mainArvSource;

	@Column(name = "PT_ARV_DATE_REPORTED")
	private String ptArvDateReported;

	@Column(name = "OLD_ACRF")
	private String oldAcrf;

	@Column(name = "ACRF_REVISION")
	private Integer acrfRevision;

	@Column(name = "SUSPECT_AHI")
	private String suspectAhi;

	@Column(name = "AHI_SYMPTOMS")
	private String ahiSymptoms;

	@Column(name = "AHI_SYMPTOMS_DATE")
	private String ahiSymptomsDate;

	@Column(name = "AHI_EVIDENCE")
	private String ahiEvidence;

	@Column(name = "AHI_EVIDENCE_NOTES")
	private String ahiEvidenceNotes;

	@Column(name = "AHI_EVIDENCE_DATE")
	private String ahiEvidenceDate;

	@Column(name = "NON_LAB_MED_CARE")
	private String nonLabMedCare;

	@Column(name = "NON_LAB_MED_CARE_DATE")
	private String nonLabMedCareDate;

	@Column(name = "GENDER_IDEN_DATE")
	private String genderIdenDate;

	@Column(name = "SEX_ORN")
	private String sexOrn;

	@Column(name = "SEX_ORN_ADD")
	private String sexOrnAdd;

	@Column(name = "SEX_ORN_DATE")
	private String sexOrnDate;

	@Column(name = "RESP_POS_SELF_TEST_BY_PAT")
	private String respPosSelfTestByPat;

	@Column(name = "NEG_HIV_BY_SELF_TEST")
	private String negHivBySelfTest;

	@Column(name = "COUNT_NEG_BY_SELF_TEST")
	private Integer countNegBySelfTest;

	@Column(name = "NEG_BY_SELF_TEST_UNK")
	private String negBySelfTestUnk;

	@Column(name = "RAPID_TEST_TYPE")
	private String rapidTestType;

	@Column(name = "OTHER_RISK_BEFORE_DIAG")
	private String otherRiskBeforeDiag;

	@Column(name = "C_BREASTFED_NON_BP")
	private String cBreastfedNonBp;

	@Column(name = "C_PRECHEWED_FOOD_NON_BP")
	private String cPrechewedFoodNonBp;

	@Column(name = "C_BREASTFED_BP")
	private String cBreastfedBp;

	@Column(name = "C_PRECHEWED_FOOD_BP")
	private String cPrechewedFoodBp;

	@Column(name = "SEX_WITH_FEMALE_BEFORE_DIAG")
	private String sexWithFemaleBeforeDiag;

	@Column(name = "SEX_WITH_MALE_BEFORE_DIAG")
	private String sexWithMaleBeforeDiag;

	@Column(name = "RECV_TRANSPLANT_BEFORE_DIAG")
	private String recvTransplantBeforeDiag;

	@Column(name = "RECV_BLOOD_TRANSFUSION_BEFORE_DIAG")
	private String recvBloodTransfusionBeforeDiag;

	@Column(name = "LAST_RECV_BLOOD_BEFORE_DIAG_DATE")
	private String lastRecvBloodBeforeDiagDate;

	@Column(name = "FIRST_RECV_BLOOD_BEFORE_DIAG_DATE")
	private String firstRecvBloodBeforeDiagDate;

	@Column(name = "RECV_CLOTTING_FACTOR_BEFORE_DIAG")
	private String recvClottingFactorBeforeDiag;

	@Column(name = "CLOTTING_FACTOR_BEFORE_DIAG_RECV_DATE")
	private String clottingFactorBeforeDiagRecvDate;

	@Column(name = "CLOTTING_FACTOR_RECV_BEFORE_DIAG")
	private String clottingFactorRecvBeforeDiag;

	@Column(name = "IDU_BEFORE_DIAG")
	private String iduBeforeDiag;

	@Column(name = "MOM_RECV_TRANSPLANT")
	private String momRecvTransplant;

	@Column(name = "MOM_RECV_BLOOD_TRANSFUSION")
	private String momRecvBloodTransfusion;

	@Column(name = "MOM_LAST_RECV_BLOOD_DATE")
	private String momLastRecvBloodDate;

	@Column(name = "MOM_FIRST_RECV_BLOOD_DATE")
	private String momFirstRecvBloodDate;

	@Column(name = "MOM_HETEROSEX_WITH_HIV")
	private String momHeterosexWithHiv;

	@Column(name = "MOM_HETEROSEX_WITH_TRANSPLANT")
	private String momHeterosexWithTransplant;

	@Column(name = "MOM_HETEROSEX_WITH_TRANSFUSION")
	private String momHeterosexWithTransfusion;

	@Column(name = "MOM_HETEROSEX_WITH_HEMOPHILIAC")
	private String momHeterosexWithHemophiliac;

	@Column(name = "MOM_HETEROSEX_WITH_BISEX")
	private String momHeterosexWithBisex;

	@Column(name = "MOM_HETEROSEX_WITH_IDU")
	private String momHeterosexWithIdu;

	@Column(name = "MOM_IDU")
	private String momIdu;

	@Column(name = "MOM_PERINATAL_HIV_INFECTION")
	private String momPerinatalHivInfection;

	@Column(name = "CHILD_MOM_HIV_INFECTION_STATUS")
	private String childMomHivInfectionStatus;

	@Column(name = "MOTHER_FIRST_POS_TEST_DATE")
	private String motherFirstPosTestDate;

	@Column(name = "C_BREASTFED_BP_START_DATE")
	private String cBreastfedBpStartDate;

	@Column(name = "C_BREASTFED_BP_STOP_DATE")
	private String cBreastfedBpStopDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "ENTERED_BY")
	private String enteredBy;

	@Column(name = "ENTERED_DATE")
	private Timestamp enteredDate;

	@Column(name = "ACRF_DOC_ID")
	private Integer acrfDocId;

	@Column(name = "AIRS_DOC_ID")
	private Integer airsDocId;

	@Column(name = "PRF_DOC_ID")
	private Integer prfDocId;
}
