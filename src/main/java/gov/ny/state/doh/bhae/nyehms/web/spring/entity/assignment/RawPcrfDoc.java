package gov.ny.state.doh.bhae.nyehms.web.spring.entity.assignment;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * A pediatric case report form as entered (table RAW_PCRF_DOC): the form's columns that PcrfDocDAO reads and writes.
 * <p>
 * Read through JPQL projections and written by JPQL inserts and bulk updates (no entity is loaded or saved). The
 * attribute types follow what MyBatis read and bound: text, Integer for the int form fields, Boolean for the check
 * boxes (bound with setBoolean, read with getBoolean, as before), Timestamp for the date columns.
 * <b>DOCUMENT_ID as the key is assumed.</b>
 */
@Entity
@Getter
@Table(name = "RAW_PCRF_DOC")
public class RawPcrfDoc {

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

	@Column(name = "BTH_ADDRESS_TYPE_CODE")
	private String bthAddressTypeCode;

	@Column(name = "PERINATAL_ADDRESS_TYPE_CODE")
	private String perinatalAddressTypeCode;

	@Column(name = "PEDIATRIC_ADDRESS_TYPE_CODE")
	private String pediatricAddressTypeCode;

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

	@Column(name = "NEW_CASE_INV_CODE")
	private String newCaseInvCode;

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

	@Column(name = "DATE_COMPLETED")
	private Timestamp dateCompleted;

	@Column(name = "WORKER_PHONE")
	private String workerPhone;

	@Column(name = "DIAG_STATUS_AT_REPORT")
	private String diagStatusAtReport;

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

	@Column(name = "LAST_MEDICAL_EVALUATION_DATE")
	private String lastMedicalEvaluationDate;

	@Column(name = "INIT_EVALUATION_HIV_DATE")
	private String initEvaluationHivDate;

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

	@Column(name = "RES_HIV_DX_SAME_AS_CURRENT")
	private Boolean resHivDxSameAsCurrent;

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

	@Column(name = "RES_HIV_ADDRESS_DATE")
	private String resHivAddressDate;

	@Column(name = "RES_AIDS_DX_SAME_AS_CURRENT")
	private Boolean resAidsDxSameAsCurrent;

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

	@Column(name = "RES_AIDS_ADDRESS_DATE")
	private String resAidsAddressDate;

	@Column(name = "RES_PERINATAL_DX_SAME_AS_CURRENT")
	private Boolean resPerinatalDxSameAsCurrent;

	@Column(name = "RES_PERINATAL_ADDRESS")
	private String resPerinatalAddress;

	@Column(name = "RES_PERINATAL_CITY")
	private String resPerinatalCity;

	@Column(name = "RES_PERINATAL_STATE")
	private String resPerinatalState;

	@Column(name = "RES_PERINATAL_COUNTRY")
	private String resPerinatalCountry;

	@Column(name = "RES_PERINATAL_ZIPCODE")
	private String resPerinatalZipcode;

	@Column(name = "RES_PERINATAL_PLUS4")
	private String resPerinatalPlus4;

	@Column(name = "RES_PERINATAL_COUNTY_FIPS")
	private String resPerinatalCountyFips;

	@Column(name = "RES_PERINATAL_ADDRESS_DATE")
	private String resPerinatalAddressDate;

	@Column(name = "RES_PEDIATRIC_SERO_DX_SAME_AS_CURRENT")
	private Boolean resPediatricSeroDxSameAsCurrent;

	@Column(name = "RES_PEDIATRIC_SERO_ADDRESS")
	private String resPediatricSeroAddress;

	@Column(name = "RES_PEDIATRIC_SERO_CITY")
	private String resPediatricSeroCity;

	@Column(name = "RES_PEDIATRIC_SERO_STATE")
	private String resPediatricSeroState;

	@Column(name = "RES_PEDIATRIC_SERO_COUNTRY")
	private String resPediatricSeroCountry;

	@Column(name = "RES_PEDIATRIC_SERO_ZIPCODE")
	private String resPediatricSeroZipcode;

	@Column(name = "RES_PEDIATRIC_SERO_PLUS4")
	private String resPediatricSeroPlus4;

	@Column(name = "RES_PEDIATRIC_SERO_COUNTY_FIPS")
	private String resPediatricSeroCountyFips;

	@Column(name = "RES_PEDIATRIC_SERO_ADDRESS_DATE")
	private String resPediatricSeroAddressDate;

	@Column(name = "SL_PROVIDER_LNAME")
	private String slProviderLname;

	@Column(name = "SL_PROVIDER_FNAME")
	private String slProviderFname;

	@Column(name = "SL_PROVIDER_MNAME")
	private String slProviderMname;

	@Column(name = "SL_PROVIDER_SUFFIX")
	private String slProviderSuffix;

	@Column(name = "SL_FACILITY_NAME")
	private String slFacilityName;

	@Column(name = "SL_PROVIDER_PHONE")
	private String slProviderPhone;

	@Column(name = "FACILITY_DX_HIV")
	private Boolean facilityDxHiv;

	@Column(name = "FACILITY_DX_AIDS")
	private Boolean facilityDxAids;

	@Column(name = "FACILITY_HIV_DX_SAME_AS_PROVIDING")
	private Boolean facilityHivDxSameAsProviding;

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

	@Column(name = "FACILITY_ID_HIV_DX")
	private String facilityIdHivDx;

	@Column(name = "FACILITY_EHARS_UID_HIV_DX")
	private String facilityEharsUidHivDx;

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

	@Column(name = "FAC_AIDS_DX_SAME_AS_PROVIDING")
	private Boolean facAidsDxSameAsProviding;

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

	@Column(name = "FACILITY_ID_AIDS_DX")
	private String facilityIdAidsDx;

	@Column(name = "FACILITY_EHARS_UID_AIDS_DX")
	private String facilityEharsUidAidsDx;

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

	@Column(name = "FAC_PERINATAL_DX_SAME_AS_PROVIDING")
	private Boolean facPerinatalDxSameAsProviding;

	@Column(name = "FACILITY_NAME_PERINATAL_DX")
	private String facilityNamePerinatalDx;

	@Column(name = "FACILITY_ADDRESS_PERINATAL_DX")
	private String facilityAddressPerinatalDx;

	@Column(name = "FACILITY_CITY_PERINATAL_DX")
	private String facilityCityPerinatalDx;

	@Column(name = "FACILITY_STATE_PERINATAL_DX")
	private String facilityStatePerinatalDx;

	@Column(name = "FACILITY_COUNTRY_PERINATAL_DX")
	private String facilityCountryPerinatalDx;

	@Column(name = "FACILITY_ZIPCODE_PERINATAL_DX")
	private String facilityZipcodePerinatalDx;

	@Column(name = "FACILITY_PLUS4_PERINATAL_DX")
	private String facilityPlus4PerinatalDx;

	@Column(name = "FACILITY_COUNTY_FIPS_PERINATAL_DX")
	private String facilityCountyFipsPerinatalDx;

	@Column(name = "FACILITY_PHONE_PERINATAL_DX")
	private String facilityPhonePerinatalDx;

	@Column(name = "FACILITY_TYPE_CODE_PERINATAL_DX")
	private String facilityTypeCodePerinatalDx;

	@Column(name = "FACILITY_TYPE_OTHER_PERINATAL_DX")
	private String facilityTypeOtherPerinatalDx;

	@Column(name = "FACILITY_ID_PERINATAL_DX")
	private String facilityIdPerinatalDx;

	@Column(name = "FACILITY_EHARS_UID_PERINATAL_DX")
	private String facilityEharsUidPerinatalDx;

	@Column(name = "PROVIDER_FNAME_PERINATAL_DX")
	private String providerFnamePerinatalDx;

	@Column(name = "PROVIDER_MNAME_PERINATAL_DX")
	private String providerMnamePerinatalDx;

	@Column(name = "PROVIDER_LNAME_PERINATAL_DX")
	private String providerLnamePerinatalDx;

	@Column(name = "PROVIDER_SUFFIX_PERINATAL_DX")
	private String providerSuffixPerinatalDx;

	@Column(name = "PROVIDER_PHONE_PERINATAL_DX")
	private String providerPhonePerinatalDx;

	@Column(name = "PROVIDER_SPECIALTY_PERINATAL_DX")
	private String providerSpecialtyPerinatalDx;

	@Column(name = "CHILD_MOM_HIV_INFECTION_STATUS")
	private String childMomHivInfectionStatus;

	@Column(name = "MOTHER_FIRST_POS_TEST_DATE")
	private String motherFirstPosTestDate;

	@Column(name = "MOTHER_COUNSELED_HIV_PREGNANCY")
	private String motherCounseledHivPregnancy;

	@Column(name = "MOM_PERINATAL_HIV_INFECTION")
	private String momPerinatalHivInfection;

	@Column(name = "MOM_IDU")
	private String momIdu;

	@Column(name = "MOM_HETEROSEX_WITH_IDU")
	private String momHeterosexWithIdu;

	@Column(name = "MOM_HETEROSEX_WITH_BISEX")
	private String momHeterosexWithBisex;

	@Column(name = "MOM_HETEROSEX_WITH_HEMOPHILIAC")
	private String momHeterosexWithHemophiliac;

	@Column(name = "MOM_HETEROSEX_WITH_TRANSFUSION")
	private String momHeterosexWithTransfusion;

	@Column(name = "MOM_HETEROSEX_WITH_TRANSPLANT")
	private String momHeterosexWithTransplant;

	@Column(name = "MOM_HETEROSEX_WITH_HIV")
	private String momHeterosexWithHiv;

	@Column(name = "MOM_RECV_BLOOD_TRANSFUSION")
	private String momRecvBloodTransfusion;

	@Column(name = "MOM_FIRST_RECV_BLOOD_DATE")
	private String momFirstRecvBloodDate;

	@Column(name = "MOM_LAST_RECV_BLOOD_DATE")
	private String momLastRecvBloodDate;

	@Column(name = "MOM_RECV_TRANSPLANT")
	private String momRecvTransplant;

	@Column(name = "IDU_BEFORE_DIAG")
	private String iduBeforeDiag;

	@Column(name = "RECV_CLOTTING_FACTOR_BEFORE_DIAG")
	private String recvClottingFactorBeforeDiag;

	@Column(name = "CLOTTING_FACTOR_RECV_BEFORE_DIAG")
	private String clottingFactorRecvBeforeDiag;

	@Column(name = "CLOTTING_FACTOR_BEFORE_DIAG_RECV_DATE")
	private String clottingFactorBeforeDiagRecvDate;

	@Column(name = "RECV_BLOOD_TRANSFUSION_BEFORE_DIAG")
	private String recvBloodTransfusionBeforeDiag;

	@Column(name = "FIRST_RECV_BLOOD_BEFORE_DIAG_DATE")
	private String firstRecvBloodBeforeDiagDate;

	@Column(name = "LAST_RECV_BLOOD_BEFORE_DIAG_DATE")
	private String lastRecvBloodBeforeDiagDate;

	@Column(name = "RECV_TRANSPLANT_BEFORE_DIAG")
	private String recvTransplantBeforeDiag;

	@Column(name = "SEX_WITH_MALE_BEFORE_DIAG")
	private String sexWithMaleBeforeDiag;

	@Column(name = "SEX_WITH_FEMALE_BEFORE_DIAG")
	private String sexWithFemaleBeforeDiag;

	@Column(name = "OTHER_RISK_BEFORE_DIAG")
	private String otherRiskBeforeDiag;

	@Column(name = "TB_RVCT_CASE_NUMBER")
	private String tbRvctCaseNumber;

	@Column(name = "LNAME_SOUNDEX")
	private String lnameSoundex;

	@Column(name = "REPT_HEALTH_DEPT_CITY_COUNTY")
	private String reptHealthDeptCityCounty;

	@Column(name = "CITY_COUNTY_NUMBER")
	private String cityCountyNumber;

	@Column(name = "PERSON_COMPLETING")
	private String personCompleting;

	@Column(name = "DX_ALGORITHM")
	private String dxAlgorithm;

	@Column(name = "DX_ALGORITHM_DATE")
	private String dxAlgorithmDate;

	@Column(name = "BACTERIAL_INFECTION_DATE")
	private String bacterialInfectionDate;

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

	@Column(name = "LYMPHOMA_INTERSTITIAL_PNEUMONIA")
	private String lymphomaInterstitialPneumonia;

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

	@Column(name = "TOXOPLASMOSIS_BRAIN_DATE")
	private String toxoplasmosisBrainDate;

	@Column(name = "WASTING_SYNDROME_DATE")
	private String wastingSyndromeDate;

	@Column(name = "HIV_INFECTED_NOT_DOCUMENTED")
	private String hivInfectedNotDocumented;

	@Column(name = "HIV_INFECTED_NOT_DOCUMENTED_DATE")
	private String hivInfectedNotDocumentedDate;

	@Column(name = "NON_HIV_INFECT_INFORM_BY_PHY")
	private String nonHivInfectInformByPhy;

	@Column(name = "NON_HIV_INFECT_INFORM_BY_PHY_DATE")
	private String nonHivInfectInformByPhyDate;

	@Column(name = "PROVIDING_FACILITY_ID")
	private String providingFacilityId;

	@Column(name = "PROVIDING_EHARS_FACILITY_UID")
	private String providingEharsFacilityUid;

	@Column(name = "ADDRESS_DATE")
	private String addressDate;

	@Column(name = "BIRTH_HIST_AVAILABLE")
	private String birthHistAvailable;

	@Column(name = "RES_BTH_SAME_AS_CURRENT")
	private Boolean resBthSameAsCurrent;

	@Column(name = "RES_BTH_ADDRESS")
	private String resBthAddress;

	@Column(name = "RES_BTH_CITY")
	private String resBthCity;

	@Column(name = "RES_BTH_STATE")
	private String resBthState;

	@Column(name = "RES_BTH_COUNTRY")
	private String resBthCountry;

	@Column(name = "RES_BTH_ZIPCODE")
	private String resBthZipcode;

	@Column(name = "RES_BTH_PLUS4")
	private String resBthPlus4;

	@Column(name = "RES_BTH_COUNTY_FIPS")
	private String resBthCountyFips;

	@Column(name = "FACILITY_AT_BTH_SAME_AS_PROVIDING")
	private Boolean facilityAtBthSameAsProviding;

	@Column(name = "FACILITY_NAME_AT_BTH")
	private String facilityNameAtBth;

	@Column(name = "FACILITY_ADDRESS_AT_BTH")
	private String facilityAddressAtBth;

	@Column(name = "FACILITY_CITY_AT_BTH")
	private String facilityCityAtBth;

	@Column(name = "FACILITY_STATE_AT_BTH")
	private String facilityStateAtBth;

	@Column(name = "FACILITY_COUNTRY_AT_BTH")
	private String facilityCountryAtBth;

	@Column(name = "FACILITY_ZIPCODE_AT_BTH")
	private String facilityZipcodeAtBth;

	@Column(name = "FACILITY_PLUS4_AT_BTH")
	private String facilityPlus4AtBth;

	@Column(name = "FACILITY_COUNTY_FIPS_AT_BTH")
	private String facilityCountyFipsAtBth;

	@Column(name = "FACILITY_PHONE_AT_BTH")
	private String facilityPhoneAtBth;

	@Column(name = "FACILITY_TYPE_CODE_AT_BTH")
	private String facilityTypeCodeAtBth;

	@Column(name = "FACILITY_TYPE_OTHER_AT_BTH")
	private String facilityTypeOtherAtBth;

	@Column(name = "FACILITY_EHARS_UID_AT_BTH")
	private String facilityEharsUidAtBth;

	@Column(name = "BIRTH_WEIGHT_LBS")
	private Integer birthWeightLbs;

	@Column(name = "BIRTH_WEIGHT_OZ")
	private Integer birthWeightOz;

	@Column(name = "BIRTH_WEIGHT_GRAM")
	private Integer birthWeightGram;

	@Column(name = "BIRTH_TYPE")
	private String birthType;

	@Column(name = "BIRTH_DELIVERY")
	private String birthDelivery;

	@Column(name = "BIRTH_DEFECTS")
	private String birthDefects;

	@Column(name = "BIRTH_DEFECTS_TYPES")
	private String birthDefectsTypes;

	@Column(name = "NEONATAL_STATUS")
	private String neonatalStatus;

	@Column(name = "NEONATAL_GEST_AGE_IN_WEEKS")
	private String neonatalGestAgeInWeeks;

	@Column(name = "PRENATAL_CARE_BEGIN_MONTH")
	private String prenatalCareBeginMonth;

	@Column(name = "PRENATAL_CARE_NO_VISITS")
	private String prenatalCareNoVisits;

	@Column(name = "MOTHER_RECV_ARV_PRIOR_PREGNANCY")
	private String motherRecvArvPriorPregnancy;

	@Column(name = "MOTHER_RECV_ARV_PRIOR_PREGNANCY_NOTES")
	private String motherRecvArvPriorPregnancyNotes;

	@Column(name = "ARV_PRIOR_PREGNANCY_BEGAN_DATE")
	private String arvPriorPregnancyBeganDate;

	@Column(name = "ARV_PRIOR_PREGNANCY_LAST_USE_DATE")
	private String arvPriorPregnancyLastUseDate;

	@Column(name = "MOTHER_RECV_ARV_DURING_PREGNANCY")
	private String motherRecvArvDuringPregnancy;

	@Column(name = "MOTHER_RECV_ARV_DURING_PREGNANCY_NOTES")
	private String motherRecvArvDuringPregnancyNotes;

	@Column(name = "ARV_DURING_PREGNANCY_BEGAN_DATE")
	private String arvDuringPregnancyBeganDate;

	@Column(name = "ARV_DURING_PREGNANCY_LAST_USE_DATE")
	private String arvDuringPregnancyLastUseDate;

	@Column(name = "MOTHER_RECV_ARV_LABOR_PREGNANCY")
	private String motherRecvArvLaborPregnancy;

	@Column(name = "MOTHER_RECV_ARV_LABOR_PREGNANCY_NOTES")
	private String motherRecvArvLaborPregnancyNotes;

	@Column(name = "ARV_LABOR_PREGNANCY_BEGAN_DATE")
	private String arvLaborPregnancyBeganDate;

	@Column(name = "ARV_LABOR_PREGNANCY_LAST_USE_DATE")
	private String arvLaborPregnancyLastUseDate;

	@Column(name = "MATERNAL_DOB")
	private String maternalDob;

	@Column(name = "MATERNAL_LAST_NAME_SNDX")
	private String maternalLastNameSndx;

	@Column(name = "MATERNAL_STATE_ID_NUMBER")
	private String maternalStateIdNumber;

	@Column(name = "MATERNAL_COUNTRY_OF_BIRTH")
	private String maternalCountryOfBirth;

	@Column(name = "OTHER_MATERNAL_ID")
	private String otherMaternalId;

	@Column(name = "OTHER_MATERNAL_ID_TYPE")
	private String otherMaternalIdType;

	@Column(name = "CHILD_EVER_TAKEN_ARVS")
	private String childEverTakenArvs;

	@Column(name = "CHILD_EVER_TAKEN_PCP_PROPHYLAXIS")
	private String childEverTakenPcpProphylaxis;

	@Column(name = "PROPHYLAXIS_BEGAN_DATE")
	private String prophylaxisBeganDate;

	@Column(name = "PROPHYLAXIS_LAST_USE_DATE")
	private String prophylaxisLastUseDate;

	@Column(name = "WAS_CHILD_BREASTFED")
	private String wasChildBreastfed;

	@Column(name = "CHILD_PRIMARY_CARETAKER")
	private String childPrimaryCaretaker;

	@Column(name = "LOCAL_OPTIONAL_FIELDS")
	private String localOptionalFields;

	@Column(name = "C_BREASTFED_BP")
	private String cBreastfedBp;

	@Column(name = "C_PRECHEWED_FOOD_BP")
	private String cPrechewedFoodBp;

	@Column(name = "C_BREASTFED_NON_BP")
	private String cBreastfedNonBp;

	@Column(name = "C_PRECHEWED_FOOD_NON_BP")
	private String cPrechewedFoodNonBp;

	@Column(name = "CS_RSN_HIV")
	private Boolean csRsnHiv;

	@Column(name = "CS_RSN_PREV_CS")
	private Boolean csRsnPrevCs;

	@Column(name = "CS_RSN_MALPRESENTATION")
	private Boolean csRsnMalpresentation;

	@Column(name = "CS_RSN_LONG_LABOR")
	private Boolean csRsnLongLabor;

	@Column(name = "CS_RSN_PREF")
	private Boolean csRsnPref;

	@Column(name = "CS_RSN_FETAL_DISTRESS")
	private Boolean csRsnFetalDistress;

	@Column(name = "CS_RSN_ABRUPTIA_PREVIA")
	private Boolean csRsnAbruptiaPrevia;

	@Column(name = "CS_RSN_OTHER")
	private Boolean csRsnOther;

	@Column(name = "CS_RSN_OTHER_SPECIFY")
	private String csRsnOtherSpecify;

	@Column(name = "CS_RSN_NOT_SPECIFIED")
	private Boolean csRsnNotSpecified;

	@Column(name = "RUPTURE_DT")
	private String ruptureDt;

	@Column(name = "DELIVERY_DT")
	private String deliveryDt;

	@Column(name = "RUPTURE_TIME")
	private String ruptureTime;

	@Column(name = "DELIVERY_TIME")
	private String deliveryTime;

	@Column(name = "SCREEN_SUBSTANCE")
	private String screenSubstance;

	@Column(name = "BP_CITYNO")
	private String bpCityno;

	@Column(name = "PREG_BEFORE")
	private String pregBefore;

	@Column(name = "BP_CD4_TEST")
	private String bpCd4Test;

	@Column(name = "BP_VL_TEST")
	private String bpVlTest;

	@Column(name = "BP_ARV_PREG_DRUG_RSN")
	private String bpArvPregDrugRsn;

	@Column(name = "BP_ARV_PREG_OTHER_DRUG_RSN")
	private String bpArvPregOtherDrugRsn;

	@Column(name = "BP_ARV_LABOR_DRUG_RSN")
	private String bpArvLaborDrugRsn;

	@Column(name = "BP_ARV_LABOR_OTHER_DRUG_RSN")
	private String bpArvLaborOtherDrugRsn;

	@Column(name = "BP_SCREEN_SUBSTANCE")
	private String bpScreenSubstance;

	@Column(name = "GENDER_IDENTITY_DT")
	private String genderIdentityDt;

	@Column(name = "SEXUAL_ORIENTATION")
	private String sexualOrientation;

	@Column(name = "OTHER_SEXUAL_ORIENTATION")
	private String otherSexualOrientation;

	@Column(name = "SEXUAL_ORIENTATION_DT")
	private String sexualOrientationDt;

	@Column(name = "GENDER_CODE")
	private String genderCode;

	@Column(name = "OTHER_GENDER_IDENTITY")
	private String otherGenderIdentity;

	@Column(name = "PCRF_REVISION")
	private Integer pcrfRevision;

	@Column(name = "CONGENITAL_DISORDERS")
	private String congenitalDisorders;

	@Column(name = "CONGENITAL_DISORDERS_DTL")
	private String congenitalDisordersDtl;

	@Column(name = "BP_USE_SUBSTANCE")
	private String bpUseSubstance;

	@Column(name = "NUM_PREV_PREG")
	private String numPrevPreg;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "C_BREASTFED_BP_START_DATE")
	private String cBreastfedBpStartDate;

	@Column(name = "C_BREASTFED_BP_STOP_DATE")
	private String cBreastfedBpStopDate;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;
}
