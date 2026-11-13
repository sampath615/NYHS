package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An RIA (recent infection / linkage) document (table NYEHMS_RIA_DOC): the columns the RIA document screen reads and
 * the RIA close writes (RiaRecordsReviewDAO). Read with projections and changed by a JPQL bulk update only, never
 * loaded or saved as an entity.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (one row per document). <b>Assumed column types</b> (the table definition
 * is not in this repository): the columns the former SQL formatted with TO_CHAR(x, 'MM/DD/YYYY') or wrote with
 * fn_to_Date / SYSDATE are dates; all other columns are mapped as text, as the former statements bound them
 * (jdbcType=VARCHAR) and read them into the document VO (getString), including the number-valued LAB_DOC_ID,
 * CDESSCASEKEY and LAST_UPDATED_BY (Oracle converts the text).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NYEHMS_RIA_DOC")
public class NyehmsRiaDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "ADDRESS_DATE")
	private Timestamp addressDate;

	@Column(name = "COMPLETE_DATE")
	private Timestamp completeDate;

	@Column(name = "FIRST_GENOTYPE_DATE")
	private Timestamp firstGenotypeDate;

	@Column(name = "FIRST_PRF_DATE")
	private Timestamp firstPrfDate;

	@Column(name = "INITIAL_APPOINTMENT_DATE")
	private Timestamp initialAppointmentDate;

	@Column(name = "INITIAL_COLLECTION_DATE")
	private Timestamp initialCollectionDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "MOVED_FROM_NY_DATE")
	private Timestamp movedFromNyDate;

	@Column(name = "MOVED_TO_NY_DATE")
	private Timestamp movedToNyDate;

	@Column(name = "PS_CLOSURE_DATE")
	private Timestamp psClosureDate;

	@Column(name = "PS_LAST_UPDATE_DATE")
	private Timestamp psLastUpdateDate;

	@Column(name = "PS_MESSAGE_SENT_DATE")
	private Timestamp psMessageSentDate;

	@Column(name = "RECENT_CD4_DATE")
	private Timestamp recentCd4Date;

	@Column(name = "RECENT_VL_DATE")
	private Timestamp recentVlDate;

	@Column(name = "RIA_ELIGIBILITY_DATE")
	private Timestamp riaEligibilityDate;

	@Column(name = "RIA_FLAGGED_DATE")
	private Timestamp riaFlaggedDate;

	@Column(name = "SOUNDEX_CHECK_DATE")
	private Timestamp soundexCheckDate;

	@Column(name = "FIRST_VL_DATE")
	private Timestamp firstVlDate;

	@Column(name = "ARV_MEDICATION")
	private String arvMedication;

	@Column(name = "CDESSCASEKEY")
	private String cdesscasekey;

	@Column(name = "DEATH_SOURCE")
	private String deathSource;

	@Column(name = "LAB_DOC_ID")
	private String labDocId;

	@Column(name = "TREATMENT_SOURCE")
	private String treatmentSource;

	@Column(name = "AHI_FLAG")
	private String ahiFlag;

	@Column(name = "AIRS_DOC")
	private String airsDoc;

	@Column(name = "ARVS_TAKEN")
	private String arvsTaken;

	@Column(name = "EHARS_ELIG")
	private String eharsElig;

	@Column(name = "PKP_FLAG")
	private String pkpFlag;

	@Column(name = "PT_ON_PREP_FLAG")
	private String ptOnPrepFlag;

	@Column(name = "NON_PROMPT_FLAG")
	private String nonPromptFlag;

	@Column(name = "DECEASED")
	private String deceased;

	@Column(name = "FEDERAL_FACILITY")
	private String federalFacility;

	@Column(name = "NDVL_AT_DX")
	private String ndvlAtDx;

	@Column(name = "PERINATAL")
	private String perinatal;

	@Column(name = "NOT_HIV_FLAG")
	private String notHivFlag;

	@Column(name = "OOJ_CASE_FLAG")
	private String oojCaseFlag;

	@Column(name = "OOJ_PROVIDER_FLAG")
	private String oojProviderFlag;

	@Column(name = "PREP_TAKEN")
	private String prepTaken;

	@Column(name = "NO_PREVIOUS_TEST")
	private String noPreviousTest;

	@Column(name = "RACE_ASIAN")
	private String raceAsian;

	@Column(name = "RACE_BLACK")
	private String raceBlack;

	@Column(name = "RACE_INDIAN")
	private String raceIndian;

	@Column(name = "RACE_PACIFIC")
	private String racePacific;

	@Column(name = "RACE_WHITE")
	private String raceWhite;

	@Column(name = "RAP_PROVIDER")
	private String rapProvider;

	@Column(name = "TTH_RAP_PROVIDER")
	private String tthRapProvider;

	@Column(name = "RECORD_SEARCH")
	private String recordSearch;

	@Column(name = "RIA_ELIGIBLE")
	private String riaEligible;

	@Column(name = "ARV_BEGIN_DATE")
	private String arvBeginDate;

	@Column(name = "LIP_DATE_TEXT")
	private String lipDateText;

	@Column(name = "BURKL_DATE_TEXT")
	private String burklDateText;

	@Column(name = "CALC_EHARS_HIV_DX_DT")
	private String calcEharsHivDxDt;

	@Column(name = "CANDE_DATE_TEXT")
	private String candeDateText;

	@Column(name = "CANDLUNG_DATE_TEXT")
	private String candlungDateText;

	@Column(name = "CERVCANC_DATE_TEXT")
	private String cervcancDateText;

	@Column(name = "CMV_DATE_TEXT")
	private String cmvDateText;

	@Column(name = "CMVRET_DATE_TEXT")
	private String cmvretDateText;

	@Column(name = "COCCI_DATE_TEXT")
	private String cocciDateText;

	@Column(name = "CRYPTOCO_DATE_TEXT")
	private String cryptocoDateText;

	@Column(name = "CRYPTOSP_DATE_TEXT")
	private String cryptospDateText;

	@Column(name = "CURR_NYEHMS_STATENO")
	private String currNyehmsStateno;

	@Column(name = "DIAGNOSIS_SETTING_TYPE")
	private String diagnosisSettingType;

	@Column(name = "DOB_TEXT")
	private String dobText;

	@Column(name = "DOD_TEXT")
	private String dodText;

	@Column(name = "HERPES_DATE_TEXT")
	private String herpesDateText;

	@Column(name = "HISTO_DATE_TEXT")
	private String histoDateText;

	@Column(name = "HIVNCEPH_DATE_TEXT")
	private String hivncephDateText;

	@Column(name = "ISO_DATE_TEXT")
	private String isoDateText;

	@Column(name = "KS_DATE_TEXT")
	private String ksDateText;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "LYMPHB_DATE_TEXT")
	private String lymphbDateText;

	@Column(name = "LYMPHIM_DATE_TEXT")
	private String lymphimDateText;

	@Column(name = "MYCOAV_DATE_TEXT")
	private String mycoavDateText;

	@Column(name = "MYCODISM_DATE_TEXT")
	private String mycodismDateText;

	@Column(name = "MYCOPULM_DATE_TEXT")
	private String mycopulmDateText;

	@Column(name = "MYCOTH_DATE_TEXT")
	private String mycothDateText;

	@Column(name = "NYC_EHARS_HIV_DX_DT")
	private String nycEharsHivDxDt;

	@Column(name = "NYEHMS_STATENO")
	private String nyehmsStateno;

	@Column(name = "NYS_EHARS_HIV_DX_DT")
	private String nysEharsHivDxDt;

	@Column(name = "NYS_LAST_NEG_DT")
	private String nysLastNegDt;

	@Column(name = "PCP_DATE_TEXT")
	private String pcpDateText;

	@Column(name = "PML_DATE_TEXT")
	private String pmlDateText;

	@Column(name = "PNEUM_DATE_TEXT")
	private String pneumDateText;

	@Column(name = "TREATMENT_FAC_TYPE_OTHER_TEXT")
	private String treatmentFacTypeOtherText;

	@Column(name = "PREP_NON_ADHER_RSN_OTHER_TEXT")
	private String prepNonAdherRsnOtherText;

	@Column(name = "RECENT_CD4_VALUE")
	private String recentCd4Value;

	@Column(name = "RECENT_VL_VALUE")
	private String recentVlValue;

	@Column(name = "FIRST_VL_UNITS")
	private String firstVlUnits;

	@Column(name = "FIRST_VL_VALUE")
	private String firstVlValue;

	@Column(name = "RIA_ELIGIBILITY_UID")
	private String riaEligibilityUid;

	@Column(name = "SALMNELA_DATE_TEXT")
	private String salmnelaDateText;

	@Column(name = "TOXO_DATE_TEXT")
	private String toxoDateText;

	@Column(name = "WASTING_DATE_TEXT")
	private String wastingDateText;

	@Column(name = "AIRS_FACILITY")
	private String airsFacility;

	@Column(name = "AIRS_TC_ID")
	private String airsTcId;

	@Column(name = "INITIAL_PROV_ADDRESS")
	private String initialProvAddress;

	@Column(name = "INITIAL_PROVNAME")
	private String initialProvname;

	@Column(name = "PERSON_ADDRESS")
	private String personAddress;

	@Column(name = "PS_CLOSURE_DISPO")
	private String psClosureDispo;

	@Column(name = "RECENT_VL_PROV_ADDRESS")
	private String recentVlProvAddress;

	@Column(name = "RECENT_VL_PROVNAME")
	private String recentVlProvname;

	@Column(name = "RIDR_INFO")
	private String ridrInfo;

	@Column(name = "PS_WORKER_ASSIGNED")
	private String psWorkerAssigned;

	@Column(name = "DISNUMBER")
	private String disnumber;

	@Column(name = "BIRTH_SEX")
	private String birthSex;

	@Column(name = "CURRENT_GENDER")
	private String currentGender;

	@Column(name = "FOOD_INSECURITY")
	private String foodInsecurity;

	@Column(name = "HEALTH_LITERACY")
	private String healthLiteracy;

	@Column(name = "HOUSING_INSTABILITY")
	private String housingInstability;

	@Column(name = "INCARCERATION")
	private String incarceration;

	@Column(name = "INITIAL_PROV_STATE")
	private String initialProvState;

	@Column(name = "INSURANCE_BARRIER")
	private String insuranceBarrier;

	@Column(name = "LANGUAGE_BARRIER")
	private String languageBarrier;

	@Column(name = "MENTAL_HEALTH_BARRIER")
	private String mentalHealthBarrier;

	@Column(name = "PERSON_STATE")
	private String personState;

	@Column(name = "PKP_DX_STATE")
	private String pkpDxState;

	@Column(name = "RECV_CARE_OOS")
	private String recvCareOos;

	@Column(name = "RECV_CARE_STATE_CD")
	private String recvCareStateCd;

	@Column(name = "OOS_FACILITY_NAME")
	private String oosFacilityName;

	@Column(name = "PREP_ADHERENCE")
	private String prepAdherence;

	@Column(name = "PREP_NON_ADHERENCE_REASON")
	private String prepNonAdherenceReason;

	@Column(name = "PKP_DX_DATE")
	private String pkpDxDate;

	@Column(name = "RECENT_VL_PROV_STATE")
	private String recentVlProvState;

	@Column(name = "SUBSTANCE_USE")
	private String substanceUse;

	@Column(name = "TRANSPORTATION_BARRIERS")
	private String transportationBarriers;

	@Column(name = "RAP_PROVIDER_MNAME")
	private String rapProviderMname;

	@Column(name = "TTH_RAP_PROVIDER_MNAME")
	private String tthRapProviderMname;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Column(name = "ETHNICITY")
	private String ethnicity;

	@Column(name = "INITIAL_FACILITY_COUNTY")
	private String initialFacilityCounty;

	@Column(name = "INITIAL_PROV_COUNTY")
	private String initialProvCounty;

	@Column(name = "PERSON_COUNTY")
	private String personCounty;

	@Column(name = "PKP_DX_COUNTRY")
	private String pkpDxCountry;

	@Column(name = "INITIAL_PROV_CITY")
	private String initialProvCity;

	@Column(name = "RECENT_VL_PROV_CITY")
	private String recentVlProvCity;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "INITIAL_PROV_ZIP")
	private String initialProvZip;

	@Column(name = "PERSON_ZIP")
	private String personZip;

	@Column(name = "RECENT_CD4_UNITS")
	private String recentCd4Units;

	@Column(name = "RECENT_VL_PROV_ZIP")
	private String recentVlProvZip;

	@Column(name = "RECENT_VL_UNITS")
	private String recentVlUnits;

	@Column(name = "BARRIERS_TO_CARE_OTHER_TEXT")
	private String barriersToCareOtherText;

	@Column(name = "ADVERSE_EFFECTS_TO_MEDS")
	private String adverseEffectsToMeds;

	@Column(name = "TREATMENT_FACILITY_NAME")
	private String treatmentFacilityName;

	@Column(name = "TREATMENT_FACILITY_TYPE")
	private String treatmentFacilityType;

	@Column(name = "CASE_CLOSURE_CD")
	private String caseClosureCd;

	@Column(name = "DEATH_SOURCE_OTHER_TEXT")
	private String deathSourceOtherText;

	@Column(name = "DIAGNOSIS_FACILITY")
	private String diagnosisFacility;

	@Column(name = "DIAGNOSIS_SETTING_OTHER_TEXT")
	private String diagnosisSettingOtherText;

	@Column(name = "NYC_DISEASE_CATEG_DX")
	private String nycDiseaseCategDx;

	@Column(name = "NYS_DISEASE_CATEG_DX")
	private String nysDiseaseCategDx;

	@Column(name = "PERSON_CITY")
	private String personCity;

	@Column(name = "RAP_PROVIDER_FNAME")
	private String rapProviderFname;

	@Column(name = "RAP_PROVIDER_LNAME")
	private String rapProviderLname;

	@Column(name = "TTH_RAP_PROVIDER_FNAME")
	private String tthRapProviderFname;

	@Column(name = "TTH_RAP_PROVIDER_LNAME")
	private String tthRapProviderLname;

	@Column(name = "TREATMENT_SOURCE_OTHER_TEXT")
	private String treatmentSourceOtherText;

	@Column(name = "BHAE_NOTES")
	private String bhaeNotes;

	@Column(name = "OMD_NOTES")
	private String omdNotes;

	@Column(name = "PS_NOTES")
	private String psNotes;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "MERGED_RIA_STATENO")
	private String mergedRiaStateno;

	@Column(name = "STATENO_DELETED")
	private String statenoDeleted;

	/** Set by the bulk update that closes the pending RIA document of a deleted lab document (PersonDAO). */
	@Column(name = "COMMENTS")
	private String comments;

}
