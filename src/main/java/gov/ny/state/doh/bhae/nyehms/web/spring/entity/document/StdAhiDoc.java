package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An AHI (acute HIV infection) form (table STD_AHI_DOC): inserted from the AHI data entry screen
 * (AHIDocumentDAO.insertAHIDocData), read by the AHI document screen. The columns the former statements used.
 * <p>
 * <b>DOCUMENT_ID as the key is assumed</b> (given by the application). The conversions the former insert left to the
 * database stay there (@ColumnTransformer: TRIM(UPPER(...)) and FN_TO_DATE of the form's date texts); the three date
 * columns are held as the texts the screen shows (read with the former TO_CHAR masks, written with FN_TO_DATE and
 * the same masks). CREATE_DATE and PROCESS_DATE are the database's SYSDATE (left null on insert).
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "STD_AHI_DOC")
public class StdAhiDoc {

	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;

	@Column(name = "FNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String fname;

	@Column(name = "MNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mname;

	@Column(name = "LNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String lname;

	@Column(name = "DOB")
	@ColumnTransformer(read = "TO_CHAR(DOB,'MM/DD/YYYY')", write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	private String dob;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "PERSON_ADDRESS")
	@ColumnTransformer(write = "trim(upper(?))")
	private String personAddress;

	@Column(name = "PERSON_CITY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String personCity;

	@Column(name = "PERSON_STATE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String personState;

	@Column(name = "PERSON_ZIP")
	@ColumnTransformer(write = "trim(upper(?))")
	private String personZip;

	@Column(name = "PERSON_COUNTY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String personCounty;

	@Column(name = "CI_REASON_PATIENT_TESTED")
	@ColumnTransformer(write = "trim(upper(?))")
	private String ciReasonPatientTested;

	@Column(name = "CI_REASON_PATIENT_TESTED_OTHR")
	@ColumnTransformer(write = "trim(upper(?))")
	private String ciReasonPatientTestedOthr;

	@Column(name = "MFA_REFSD_LNK_TO_900_OMC")
	private String mfaRefsdLnkTo900Omc;

	@Column(name = "MFA_REFSD_LNK_TO_900_OMC_RESN")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaRefsdLnkTo900OmcResn;

	@Column(name = "MFA_REFSD_LNK_TO_900_OMC_OTHR")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaRefsdLnkTo900OmcOthr;

	@Column(name = "MFA_NEXT_900_RELATD_APPT_DATE")
	@ColumnTransformer(read = "TO_CHAR(MFA_NEXT_900_RELATD_APPT_DATE,'MM/DD/YYYY HH:MI:SS AM')", write = "FN_TO_DATE(?,'MM/DD/YYYY HH:MI:SS AM')")
	private String mfaNext900RelatdApptDate;

	@Column(name = "MFA_PROVIDER_NAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaProviderName;

	@Column(name = "MFA_CLINIC_ADDRESS")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaClinicAddress;

	@Column(name = "MFA_CLINIC_CITY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaClinicCity;

	@Column(name = "MFA_CLINIC_STATE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaClinicState;

	@Column(name = "MFA_CLINIC_ZIP")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaClinicZip;

	@Column(name = "MFA_CLINIC_COUNTY_FIPS")
	@ColumnTransformer(write = "trim(upper(?))")
	private String mfaClinicCountyFips;

	@Column(name = "EVIDENCE_AHI_REPTD_SYMPTOMS")
	private String evidenceAhiReptdSymptoms;

	@Column(name = "AHI_SYMPTOMS_REPTD_NOT_SPECFD")
	private String ahiSymptomsReptdNotSpecfd;

	@Column(name = "AHI_SYMPTOMS_ABDOMINAL_PAIN")
	private String ahiSymptomsAbdominalPain;

	@Column(name = "AHI_SYMPTOMS_ARTHRALGIAS")
	private String ahiSymptomsArthralgias;

	@Column(name = "AHI_SYMPTOMS_EXDA_PHRYNGTIS")
	private String ahiSymptomsExdaPhryngtis;

	@Column(name = "AHI_SYMPTOMS_FATIGUE")
	private String ahiSymptomsFatigue;

	@Column(name = "AHI_SYMPTOMS_FEVER")
	private String ahiSymptomsFever;

	@Column(name = "AHI_SYMPTOMS_FLU_LIKE_SYMPTOM")
	private String ahiSymptomsFluLikeSymptom;

	@Column(name = "AHI_SYMPTOMS_GENITAL_ULCERS")
	private String ahiSymptomsGenitalUlcers;

	@Column(name = "AHI_SYMPTOMS_HEADACHE")
	private String ahiSymptomsHeadache;

	@Column(name = "AHI_SYMPTOMS_LYMPHADENCPATHY")
	private String ahiSymptomsLymphadencpathy;

	@Column(name = "AHI_SYMPTOMS_MYALGIAS")
	private String ahiSymptomsMyalgias;

	@Column(name = "AHI_SYMPTOMS_NAUSEA")
	private String ahiSymptomsNausea;

	@Column(name = "AHI_SYMPTOMS_NIGHT_SWEATS")
	private String ahiSymptomsNightSweats;

	@Column(name = "AHI_SYMPTOMS_ORAL_ULCERS")
	private String ahiSymptomsOralUlcers;

	@Column(name = "AHI_SYMPTOMS_PHOTOPHOBIA")
	private String ahiSymptomsPhotophobia;

	@Column(name = "AHI_SYMPTOMS_RASH")
	private String ahiSymptomsRash;

	@Column(name = "AHI_SYMPTOMS_RECTAL_ULCERS")
	private String ahiSymptomsRectalUlcers;

	@Column(name = "AHI_SYMPTOMS_SORE_THROAT")
	private String ahiSymptomsSoreThroat;

	@Column(name = "AHI_SYMPTOMS_STIFF_NECK")
	private String ahiSymptomsStiffNeck;

	@Column(name = "AHI_SYMPTOMS_VOMITING")
	private String ahiSymptomsVomiting;

	@Column(name = "AHI_SYMPTOMS_WEIGHT_LOSS")
	private String ahiSymptomsWeightLoss;

	@Column(name = "AHI_SYMPTOMS_DIARRHEA")
	private String ahiSymptomsDiarrhea;

	@Column(name = "AHI_SYMPTOMS_OTHER")
	private String ahiSymptomsOther;

	@Column(name = "AHI_SYMPTOMS_OTHER_TEXT")
	@ColumnTransformer(write = "trim(upper(?))")
	private String ahiSymptomsOtherText;

	@Column(name = "EVIDENCE_AHI_PROV_DX_OF_AHI")
	private String evidenceAhiProvDxOfAhi;

	@Column(name = "EVIDENCE_AHI_PROV_DX_DATE")
	@ColumnTransformer(read = "TO_CHAR(EVIDENCE_AHI_PROV_DX_DATE,'MM/DD/YYYY')", write = "FN_TO_DATE(?,'MM/DD/YYYY')")
	private String evidenceAhiProvDxDate;

	@Column(name = "FACILITY_EHARS_UID")
	private String facilityEharsUid;

	@Column(name = "FACILITY_ID")
	private BigDecimal facilityId;

	@Column(name = "FACILITY_NAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityName;

	@Column(name = "FACILITY_PHONE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityPhone;

	@Column(name = "FACILITY_ADDRESS")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityAddress;

	@Column(name = "FACILITY_CITY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityCity;

	@Column(name = "FACILITY_STATE")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityState;

	@Column(name = "FACILITY_COUNTRY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityCountry;

	@Column(name = "FACILITY_ZIP")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityZip;

	@Column(name = "FACILITY_PLUS4")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityPlus4;

	@Column(name = "FACILITY_COUNTY")
	@ColumnTransformer(write = "trim(upper(?))")
	private String facilityCounty;

	@Column(name = "DIAGNOSIS_PROVIDER_FNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String diagnosisProviderFname;

	@Column(name = "DIAGNOSIS_PROVIDER_MNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String diagnosisProviderMname;

	@Column(name = "DIAGNOSIS_PROVIDER_LNAME")
	@ColumnTransformer(write = "trim(upper(?))")
	private String diagnosisProviderLname;

	@Column(name = "DIAGNOSIS_PROVIDER_SUFFIX")
	@ColumnTransformer(write = "trim(upper(?))")
	private String diagnosisProviderSuffix;

	@Column(name = "DIAGNOSIS_PROVIDER_NOTES")
	@ColumnTransformer(write = "trim(upper(?))")
	private String diagnosisProviderNotes;

	@Column(name = "CURRENT_PROCESS_STATUS")
	private Integer currentProcessStatus;

	@Column(name = "CREATE_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp createDate;

	@Column(name = "CREATE_UID")
	private Long createUid;

	@Column(name = "PROCESS_DATE")
	@ColumnTransformer(write = "coalesce(?, SYSDATE)")
	private Timestamp processDate;
}
