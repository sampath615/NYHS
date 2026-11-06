package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHICiStiVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHIDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.AHINegInd900TestHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AHIAlertPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.AhiAlertTrackingVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.AhiAlertFollowup;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.AhiAlertTracking;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiCiStiCondition;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdAhiNegInd900TestHist;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.RowBuilder;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertDetailsRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAlertListRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiDocRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.AllStdSentRecvMsgVwRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.AllStdSentRecvMsgVwRepository.SentMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedPsMsgTypeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.SurveillanceAssignmentRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.AhiAlertTrackingRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedActivePassiveRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedAhiAlertSrcRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedAhiCiReasonTestedRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedAhiCiStiDxRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedAhiPrfFollowupRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedAhiTestTypeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.CodedYesNoRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdAhiCiStiConditionRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdAhiCiStiConditionRepository.CiStiCondition;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdAhiDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdAhiNegInd900TestHistRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.StdAhiNegInd900TestHistRepository.NegInd900Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The AHI (acute HIV infection) forms and alerts (formerly a MyBatis mapper with the same name and methods): the AHI
 * data entry and document screens with their code lists, the AHI flag of an assignment, the comparison with an ACRF
 * document, and the AHI alert screens. Through Spring Data repositories over StdAhiDoc, StdAhiCiStiCondition,
 * StdAhiNegInd900TestHist, AhiAlertTracking, AhiAlertFollowup, SurveillanceAssignment, AllStdSentRecvMsgVw and the
 * code tables.
 * <p>
 * Native SQL (values bound) only for validateAHIDataWithACRF: the compared table is chosen at runtime (the former
 * ${tableName}, now checked against the names LabCommonUtils.getRawTableName returns), and the comparison
 * NVL(TO_CHAR(ahi.DOB, 'MM/DD/YYYY'), sysdate) = NVL(acrf.DATE_OF_BIRTH, sysdate) relies on Oracle's implicit
 * text/date conversion. Done in Java: TO_CHAR(date, 'MM/DD/YYYY') of the alert dates, the latest follow-up step of each
 * alert (the former ROW_NUMBER() ... ORDER BY FOLLOW_UP_DATE DESC NULLS FIRST, then ordered by FOLLOW_UP_CODE), the VO
 * and map mapping (MyBatis' rules, {@link Rows}). Kept in the database: TRIM/UPPER and FN_TO_DATE of the form's values,
 * TO_CHAR of the form's dates (entity transformers), PKG_TRK_COMMON.fn_get_fullname, SYSDATE.
 * <p>
 * Bug kept as it was: getAhiAlertDetails reads triggeringDocType for the hard-coded person '0010930066' (not the
 * alert's person); it also joins NYEHMS_USERS_VW twice without using it (which can repeat rows).
 */
@Repository
@RequiredArgsConstructor
public class AHIDocumentDAO {

	/** The tables validateAHIDataWithACRF may compare with: those LabCommonUtils.getRawTableName returns. */
	private static final Set<String> ACRF_TABLES = Set.of("RAW_HIV_ECLRS_DOC", "RAW_STD_ECLRS_DOC", "RAW_TB_DOC", "RAW_SYPHILIS_DOC",
			"RAW_DEATH_DOC", "RAW_CASA_DOC", "RAW_DOCCS_DOC", "RAW_DIN_DOCCS_DOC", "RAW_PRF_DOC", "RAW_NYCPRF_DOC", "RAW_ACRF_DOC",
			"RAW_PCRF_DOC", "RAW_AIRS_DOC", "RAW_ADAP_DOC", "RAW_SSDM_DOC", "RAW_NDI_DOC", "RAW_NYCIJR_DOC", "RAW_NYC_DEATH_DOC",
			"RAW_MEDICAID_DOC", "RAW_OLD_CASES_DOC", "RAW_MED_OLD_DOC", "RAW_EPRF_OOC_UPLOAD", "RAW_CCD_DOC", "RAW_HL7_DOC", "RAW_EXP_DOC",
			"RAW_NYC_EHARS_DOC", "RAW_NYC_EHARS_LAB_DOC", "RAW_NDI_CAUSE_DOC", "RAW_INS_DEATH_DOC", "RAW_STD_OLD_DOC", "RAW_HIE_DEATH_DOC",
			"RAW_CLUSTER_DOC", "RAW_STD_CONFIRMED_DOC", "RAW_BLACK_BOX_DOC", "RAW_ICCR_DOC", "RAW_QUALITY_CARE_DOC");

	/** The former validateAHIDataWithACRF; %s is the allow-listed table name (native: see the class comment). */
	private static final String VALIDATE_WITH_ACRF = """
			select count(*) from %s acrf, STD_AHI_DOC ahi where acrf.document_id = ?1 and ahi.document_id = ?2
			         and NVL(ahi.FNAME, 'XX') =      NVL(acrf.FNAME, 'XX')
			    and NVL(ahi.MNAME, 'XX') =     NVL( acrf.MNAME, 'XX')
			    and NVL(ahi.LNAME, 'XX') =       NVL(acrf.LNAME, 'XX')
			    and NVL(TO_CHAR(ahi.DOB, 'MM/DD/YYYY'), sysdate) =       NVL(acrf.DATE_OF_BIRTH,  sysdate)
			    and NVL(ahi.PERSON_ADDRESS, 'XX') =       NVL(acrf.RES_HIV_ADDRESS, 'XX')
			    and NVL(ahi.PERSON_CITY, 'XX') =      NVL(acrf.RES_HIV_CITY, 'XX')
			    and NVL(ahi.PERSON_STATE, 'XX') =      NVL(acrf.RES_HIV_STATE, 'XX')
			    and NVL(ahi.PERSON_ZIP, 'XX') =     NVL(acrf.RES_HIV_ZIPCODE, 'XX')
			    and NVL(ahi.PERSON_COUNTY, 'XX') =   NVL(acrf.RES_HIV_COUNTY_FIPS, 'XX')
			    and NVL(ahi.FACILITY_EHARS_UID, 'XX') =    NVL(acrf.FACILITY_EHARS_UID_HIV_DX, 'XX')
			     and NVL(ahi.DIAGNOSIS_PROVIDER_FNAME, 'XX') =   NVL(acrf.PROVIDER_FNAME_HIV_DX, 'XX')
			    and NVL(ahi.DIAGNOSIS_PROVIDER_MNAME, 'XX') =   NVL(acrf.PROVIDER_MNAME_HIV_DX, 'XX')
			    and NVL(ahi.DIAGNOSIS_PROVIDER_LNAME, 'XX') =   NVL(acrf.PROVIDER_LNAME_HIV_DX, 'XX')
			    and NVL(ahi.DIAGNOSIS_PROVIDER_SUFFIX, 'XX') =   NVL(acrf.PROVIDER_SUFFIX_HIV_DX, 'XX')
			     and case when  acrf.ID_TYPE_CODE = 3 then ahi.SSN else 'x' end  = case when  acrf.ID_TYPE_CODE = 3 then acrf.OTHER_ID_NUMBER else 'x' end
			""";

	/** TO_CHAR(date, 'MM/DD/YYYY'). */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final NativeStatements statements;
	private final StdAhiDocRepository ahiDocs;
	private final StdAhiCiStiConditionRepository ciStiConditions;
	private final StdAhiNegInd900TestHistRepository negInd900Tests;
	private final CodedAhiCiStiDxRepository ciStiDiagnoses;
	private final CodedAhiCiReasonTestedRepository testedReasons;
	private final CodedAhiTestTypeRepository testTypes;
	private final SurveillanceAssignmentRepository assignments;
	private final AhiAlertTrackingRepository alerts;
	private final CodedAhiAlertSrcRepository alertSources;
	private final CodedYesNoRepository yesNo;
	private final CodedActivePassiveRepository activePassive;
	private final CodedAhiPrfFollowupRepository prfFollowups;
	private final CodedPsMsgTypeRepository psMessageTypes;
	private final AllStdSentRecvMsgVwRepository sentMessages;

	@PersistenceContext
	private EntityManager entityManager;

	/** The concurrent STI diagnosis codes: DIAGNOSIS_CODE, DIAGNOSIS_DESC (nulls left out, a row of nulls null). */
	public List<Map<String, String>> getAHICISTIConditionList() {
		List<Map<String, String>> rows = new ArrayList<>();
		ciStiDiagnoses.findAllBy().forEach(c -> rows.add(map("DIAGNOSIS_CODE", c.diagnosisCode(), "DIAGNOSIS_DESC", c.diagnosisDesc())));
		return rows;
	}

	/** The reasons a patient was tested: TESTED_CODE, TESTED_DESC. */
	public List<Map<String, String>> getAHICIReasonPatientTestedList() {
		List<Map<String, String>> rows = new ArrayList<>();
		testedReasons.findAllBy().forEach(c -> rows.add(map("TESTED_CODE", c.testedCode(), "TESTED_DESC", c.testedDesc())));
		return rows;
	}

	/** The test types: TEST_TYPE_CODE, TEST_TYPE_DESC. */
	public List<Map<String, String>> getAHINegInd900TestTypeList() {
		List<Map<String, String>> rows = new ArrayList<>();
		testTypes.findAllBy().forEach(c -> rows.add(map("TEST_TYPE_CODE", c.testTypeCode(), "TEST_TYPE_DESC", c.testTypeDesc())));
		return rows;
	}

	/**
	 * Inserts an AHI form (status 10, created now); the texts are trimmed and upper-cased and the date texts read with
	 * FN_TO_DATE by the database, as before.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertAHIDocData(AHIDocVO ahiDocVO) throws DataAccessException {
		AHIDocVO v = ahiDocVO;
		insert(StdAhiDoc.builder()
				.documentId(v.getDOCUMENT_ID() == null ? null : v.getDOCUMENT_ID().longValue())
				.fname(v.getFNAME())
				.mname(v.getMNAME())
				.lname(v.getLNAME())
				.dob(v.getDOB())
				.ssn(v.getSSN())
				.personAddress(v.getPERSON_ADDRESS())
				.personCity(v.getPERSON_CITY())
				.personState(v.getPERSON_STATE())
				.personZip(v.getPERSON_ZIP())
				.personCounty(v.getPERSON_COUNTY())
				.ciReasonPatientTested(v.getCI_REASON_PATIENT_TESTED())
				.ciReasonPatientTestedOthr(v.getCI_REASON_PATIENT_TESTED_OTHR())
				.mfaRefsdLnkTo900Omc(v.getMFA_REFSD_LNK_TO_900_OMC())
				.mfaRefsdLnkTo900OmcResn(v.getMFA_REFSD_LNK_TO_900_OMC_RESN())
				.mfaRefsdLnkTo900OmcOthr(v.getMFA_REFSD_LNK_TO_900_OMC_OTHR())
				.mfaNext900RelatdApptDate(v.getMFA_NEXT_900_RELATD_APPT_DATE())
				.mfaProviderName(v.getMFA_PROVIDER_NAME())
				.mfaClinicAddress(v.getMFA_CLINIC_ADDRESS())
				.mfaClinicCity(v.getMFA_CLINIC_CITY())
				.mfaClinicState(v.getMFA_CLINIC_STATE())
				.mfaClinicZip(v.getMFA_CLINIC_ZIP())
				.mfaClinicCountyFips(v.getMFA_CLINIC_COUNTY_FIPS())
				.evidenceAhiReptdSymptoms(v.getEVIDENCE_AHI_REPTD_SYMPTOMS())
				.ahiSymptomsReptdNotSpecfd(v.getAHI_SYMPTOMS_REPTD_NOT_SPECFD())
				.ahiSymptomsAbdominalPain(v.getAHI_SYMPTOMS_ABDOMINAL_PAIN())
				.ahiSymptomsArthralgias(v.getAHI_SYMPTOMS_ARTHRALGIAS())
				.ahiSymptomsExdaPhryngtis(v.getAHI_SYMPTOMS_EXDA_PHRYNGTIS())
				.ahiSymptomsFatigue(v.getAHI_SYMPTOMS_FATIGUE())
				.ahiSymptomsFever(v.getAHI_SYMPTOMS_FEVER())
				.ahiSymptomsFluLikeSymptom(v.getAHI_SYMPTOMS_FLU_LIKE_SYMPTOM())
				.ahiSymptomsGenitalUlcers(v.getAHI_SYMPTOMS_GENITAL_ULCERS())
				.ahiSymptomsHeadache(v.getAHI_SYMPTOMS_HEADACHE())
				.ahiSymptomsLymphadencpathy(v.getAHI_SYMPTOMS_LYMPHADENCPATHY())
				.ahiSymptomsMyalgias(v.getAHI_SYMPTOMS_MYALGIAS())
				.ahiSymptomsNausea(v.getAHI_SYMPTOMS_NAUSEA())
				.ahiSymptomsNightSweats(v.getAHI_SYMPTOMS_NIGHT_SWEATS())
				.ahiSymptomsOralUlcers(v.getAHI_SYMPTOMS_ORAL_ULCERS())
				.ahiSymptomsPhotophobia(v.getAHI_SYMPTOMS_PHOTOPHOBIA())
				.ahiSymptomsRash(v.getAHI_SYMPTOMS_RASH())
				.ahiSymptomsRectalUlcers(v.getAHI_SYMPTOMS_RECTAL_ULCERS())
				.ahiSymptomsSoreThroat(v.getAHI_SYMPTOMS_SORE_THROAT())
				.ahiSymptomsStiffNeck(v.getAHI_SYMPTOMS_STIFF_NECK())
				.ahiSymptomsVomiting(v.getAHI_SYMPTOMS_VOMITING())
				.ahiSymptomsWeightLoss(v.getAHI_SYMPTOMS_WEIGHT_LOSS())
				.ahiSymptomsDiarrhea(v.getAHI_SYMPTOMS_DIARRHEA())
				.ahiSymptomsOther(v.getAHI_SYMPTOMS_OTHER())
				.ahiSymptomsOtherText(v.getAHI_SYMPTOMS_OTHER_TEXT())
				.evidenceAhiProvDxOfAhi(v.getEVIDENCE_AHI_PROV_DX_OF_AHI())
				.evidenceAhiProvDxDate(v.getEVIDENCE_AHI_PROV_DX_DATE())
				.facilityEharsUid(v.getFACILITY_EHARS_UID())
				.facilityId(decimal(v.getFACILITY_ID()))
				.facilityName(v.getFACILITY_NAME())
				.facilityPhone(v.getFACILITY_PHONE())
				.facilityAddress(v.getFACILITY_ADDRESS())
				.facilityCity(v.getFACILITY_CITY())
				.facilityState(v.getFACILITY_STATE())
				.facilityCountry(v.getFACILITY_COUNTRY())
				.facilityZip(v.getFACILITY_ZIP())
				.facilityPlus4(v.getFACILITY_PLUS4())
				.facilityCounty(v.getFACILITY_COUNTY())
				.diagnosisProviderFname(v.getDIAGNOSIS_PROVIDER_FNAME())
				.diagnosisProviderMname(v.getDIAGNOSIS_PROVIDER_MNAME())
				.diagnosisProviderLname(v.getDIAGNOSIS_PROVIDER_LNAME())
				.diagnosisProviderSuffix(v.getDIAGNOSIS_PROVIDER_SUFFIX())
				.diagnosisProviderNotes(v.getDIAGNOSIS_PROVIDER_NOTES())
				.currentProcessStatus(10)
				.createUid((long) v.getCREATE_UID())
				.build());
		return 1;
	}

	/**
	 * Inserts the concurrent STI conditions ({@code finalAHICIStiList}, AHICiStiVOs) of a form ({@code AHI_DOCUMENT_ID}).
	 *
	 * @return the number of rows inserted
	 * @throws InvalidDataAccessApiUsageException if there are none (the former SQL was invalid then)
	 */
	@Transactional
	public int insertAHICiStiData(Map<String, Object> inputParameterMap) throws DataAccessException {
		Long documentId = longValue(inputParameterMap.get("AHI_DOCUMENT_ID"));
		Collection<?> conditions = list(inputParameterMap.get("finalAHICIStiList"));
		for (Object o : conditions) {
			AHICiStiVO c = (AHICiStiVO) o;
			insert(StdAhiCiStiCondition.builder().ahiDocumentId(documentId).ciStiConditionCode(c.getCI_STI_CONDITION_CODE())
					.ciStiConditionDxDate(c.getCI_STI_CONDITION_DX_DATE()).build());
		}
		return conditions.size();
	}

	/**
	 * Inserts the negative / indeterminate test history ({@code finalAhiNegInd900TestHistoryList}) of a form
	 * ({@code AHI_DOCUMENT_ID}).
	 *
	 * @return the number of rows inserted
	 * @throws InvalidDataAccessApiUsageException if there are none (the former SQL was invalid then)
	 */
	@Transactional
	public int insertAHINegInd900TestHistoryData(Map<String, Object> inputParameterMap) throws DataAccessException {
		Long documentId = longValue(inputParameterMap.get("AHI_DOCUMENT_ID"));
		Collection<?> tests = list(inputParameterMap.get("finalAhiNegInd900TestHistoryList"));
		for (Object o : tests) {
			AHINegInd900TestHistoryVO t = (AHINegInd900TestHistoryVO) o;
			insert(StdAhiNegInd900TestHist.builder().ahiDocumentId(documentId).lastNegIndDate(t.getLAST_NEG_IND_DATE())
					.negIndResult(t.getNEG_IND_RESULT()).negIndTestTypeCode(t.getNEG_IND_TEST_TYPE_CODE())
					.negIndTestTypeOther(t.getNEG_IND_TEST_TYPE_OTHER()).negIndLabReport(t.getNEG_IND_LAB_REPORT())
					.negIndLastTestProvName(t.getNEG_IND_LAST_TEST_PROV_NAME()).negIndProvNameUnknown(t.getNEG_IND_PROV_NAME_UNKNOWN())
					.build());
		}
		return tests.size();
	}

	/**
	 * Sets the AHI flag ({@code ahiFlagStatus}) of an open assignment ({@code assignmentId}) by {@code p_workerId}, now.
	 *
	 * @return the number of rows changed (0 if the assignment is closed)
	 */
	@Transactional
	public int changeAhiFlagStatusForAssignment(Map<String, Object> inputParameterMap) {
		return assignments.changeAhiFlag(text(inputParameterMap.get("ahiFlagStatus")), text(inputParameterMap.get("p_workerId")),
				longValue(inputParameterMap.get("assignmentId")));
	}

	/**
	 * The AHI document screen: the form with its document's PARENT_DOCUMENT_ID and the AHI_FLAG of its assignment.
	 *
	 * @return the form, or null if there is none; more than one row (several open associations) fails, as before
	 */
	public AHIDocVO getAHIDoc(int documentId) throws DataAccessException {
		AhiDocRow row = one(ahiDocs.findDocument((long) documentId));
		if (row == null) {
			return null;
		}
		return Rows.bean(AHIDocVO.class).map(new RowBuilder()
				.columns(row.doc(), StdAhiDoc.class, "DOCUMENT_ID", "FNAME", "MNAME", "LNAME", "DOB", "SSN", "PERSON_ADDRESS", "PERSON_CITY",
						"PERSON_STATE", "PERSON_ZIP", "PERSON_COUNTY", "CI_REASON_PATIENT_TESTED", "CI_REASON_PATIENT_TESTED_OTHR",
						"MFA_REFSD_LNK_TO_900_OMC", "MFA_REFSD_LNK_TO_900_OMC_RESN", "MFA_REFSD_LNK_TO_900_OMC_OTHR",
						"MFA_NEXT_900_RELATD_APPT_DATE", "MFA_PROVIDER_NAME", "MFA_CLINIC_ADDRESS", "MFA_CLINIC_CITY", "MFA_CLINIC_STATE",
						"MFA_CLINIC_ZIP", "MFA_CLINIC_COUNTY_FIPS", "EVIDENCE_AHI_REPTD_SYMPTOMS", "AHI_SYMPTOMS_REPTD_NOT_SPECFD",
						"AHI_SYMPTOMS_ABDOMINAL_PAIN", "AHI_SYMPTOMS_ARTHRALGIAS", "AHI_SYMPTOMS_EXDA_PHRYNGTIS", "AHI_SYMPTOMS_FATIGUE",
						"AHI_SYMPTOMS_FEVER", "AHI_SYMPTOMS_FLU_LIKE_SYMPTOM", "AHI_SYMPTOMS_GENITAL_ULCERS", "AHI_SYMPTOMS_HEADACHE",
						"AHI_SYMPTOMS_LYMPHADENCPATHY", "AHI_SYMPTOMS_MYALGIAS", "AHI_SYMPTOMS_NAUSEA", "AHI_SYMPTOMS_NIGHT_SWEATS",
						"AHI_SYMPTOMS_ORAL_ULCERS", "AHI_SYMPTOMS_PHOTOPHOBIA", "AHI_SYMPTOMS_RASH", "AHI_SYMPTOMS_RECTAL_ULCERS",
						"AHI_SYMPTOMS_SORE_THROAT", "AHI_SYMPTOMS_STIFF_NECK", "AHI_SYMPTOMS_VOMITING", "AHI_SYMPTOMS_WEIGHT_LOSS",
						"AHI_SYMPTOMS_OTHER", "EVIDENCE_AHI_PROV_DX_OF_AHI", "EVIDENCE_AHI_PROV_DX_DATE", "AHI_SYMPTOMS_DIARRHEA",
						"AHI_SYMPTOMS_OTHER_TEXT", "FACILITY_EHARS_UID", "FACILITY_ID", "FACILITY_NAME", "FACILITY_PHONE", "FACILITY_ADDRESS",
						"FACILITY_CITY", "FACILITY_STATE", "FACILITY_COUNTRY", "FACILITY_ZIP", "FACILITY_PLUS4", "FACILITY_COUNTY",
						"DIAGNOSIS_PROVIDER_FNAME", "DIAGNOSIS_PROVIDER_MNAME", "DIAGNOSIS_PROVIDER_LNAME", "DIAGNOSIS_PROVIDER_SUFFIX",
						"DIAGNOSIS_PROVIDER_NOTES")
				.put("PARENT_DOCUMENT_ID", row.parentDocumentId())
				.put("AHI_FLAG", row.ahiFlag())
				.build());
	}

	/** The concurrent STI conditions of a form (the date as MM/DD/YYYY). */
	public List<AHICiStiVO> getCiStiConditionData(int documentId) throws DataAccessException {
		List<AHICiStiVO> rows = new ArrayList<>();
		for (CiStiCondition c : ciStiConditions.findByAhiDocumentId((long) documentId)) {
			rows.add(Rows.bean(AHICiStiVO.class).map(new RowBuilder().put("AHI_DOCUMENT_ID", c.ahiDocumentId())
					.put("CI_STI_CONDITION_CODE", c.ciStiConditionCode()).put("CI_STI_CONDITION_DX_DATE", c.ciStiConditionDxDate()).build()));
		}
		return rows;
	}

	/** The negative / indeterminate test history of a form. */
	public List<AHINegInd900TestHistoryVO> getNegInd900TestHistoryData(int documentId) throws DataAccessException {
		List<AHINegInd900TestHistoryVO> rows = new ArrayList<>();
		for (NegInd900Test t : negInd900Tests.findByAhiDocumentId((long) documentId)) {
			rows.add(Rows.bean(AHINegInd900TestHistoryVO.class).map(new RowBuilder().put("AHI_DOCUMENT_ID", t.ahiDocumentId())
					.put("LAST_NEG_IND_DATE", t.lastNegIndDate()).put("NEG_IND_RESULT", t.negIndResult())
					.put("NEG_IND_TEST_TYPE_CODE", t.negIndTestTypeCode()).put("NEG_IND_TEST_TYPE_OTHER", t.negIndTestTypeOther())
					.put("NEG_IND_LAB_REPORT", t.negIndLabReport()).put("NEG_IND_LAST_TEST_PROV_NAME", t.negIndLastTestProvName())
					.put("NEG_IND_PROV_NAME_UNKNOWN", t.negIndProvNameUnknown()).build()));
		}
		return rows;
	}

	/**
	 * 1 (or more) if the AHI form ({@code ahiDocId}) has the same person, address, facility and provider as the
	 * document {@code acrfDocId} of table {@code tableName}, else 0.
	 *
	 * @throws InvalidDataAccessApiUsageException if the table is not one of the document tables (before: invalid SQL)
	 */
	public int validateAHIDataWithACRF(Map<String, Object> inputParameterMap) {
		Object table = inputParameterMap.get("tableName");
		if (!(table instanceof String name) || !ACRF_TABLES.contains(name)) {
			throw new InvalidDataAccessApiUsageException("Not a document table: " + table);
		}
		return statements.one(VALIDATE_WITH_ACRF.formatted(name), Rows.scalar(Integer.class), inputParameterMap.get("acrfDocId"),
				inputParameterMap.get("ahiDocId"));
	}

	/**
	 * The AHI alerts, each with its latest follow-up step (by FOLLOW_UP_DATE, an undated step first; any one of equal
	 * dates), ordered by that step's code (alerts without a step last).
	 */
	public List<AhiAlertTrackingVO> getAhiFlaggedStatenoData() throws DataAccessException {
		Map<Long, AhiAlertListRow> latest = new LinkedHashMap<>();
		for (AhiAlertListRow row : alerts.findAllWithFollowUps()) {
			Long id = row.alert().getAhiAlertSeqId();
			AhiAlertListRow current = latest.get(id);
			if (current == null || isLater(row.followUpDate(), current.followUpDate())) {
				latest.put(id, row);
			}
		}
		List<AhiAlertListRow> rows = new ArrayList<>(latest.values());
		rows.sort(Comparator.comparing(AhiAlertListRow::followUpCode, Comparator.nullsLast(Comparator.naturalOrder())));
		List<AhiAlertTrackingVO> vos = new ArrayList<>();
		for (AhiAlertListRow row : rows) {
			AhiAlertTracking t = row.alert();
			vos.add(Rows.bean(AhiAlertTrackingVO.class).map(new RowBuilder()
					.put("ahiAlertSeqId", t.getAhiAlertSeqId()).put("stateno", t.getStateno()).put("ahiFlag", t.getAhiFlag())
					.put("ahiDocId", t.getAhiDocumentId()).put("ahiFlagSetBy", row.flagSetBy())
					.put("ahiFlagSetDate", format(t.getAhiCreateDate())).put("alertSource", t.getAhiAlertCode())
					.put("alertSourceDesc", row.alertSourceDesc()).put("prfCollection", t.getPrfCollection())
					.put("prfCollectionDesc", row.prfCollectionDesc()).put("prfCollectionDate", format(t.getPrfCollectionDate()))
					.put("prfFlaggedAhi", t.getPrfFlaggedAhi()).put("lastUpdatedBy", row.lastUpdatedBy())
					.put("lastUpdatedDate", format(t.getLastUpdatedDate())).put("alertCompletedBy", row.completedBy())
					.put("alertCompletedDate", format(t.getAlertCompletedDate())).put("followupStepTakenCode", row.followupStepTakenCode())
					.put("followupStepTaken", row.followupStepTaken()).put("rn", BigDecimal.ONE).put("psMsgExist", row.psMsgExist())
					.put("ahiAlertStatus", t.getAhiAlertStatus()).put("ahiAlertReviewedDate", format(t.getAhiAlertReviewedDate()))
					.put("ahiAlertReviewedBy", row.reviewedBy())
					.build()));
		}
		return vos;
	}

	/** Whether a follow-up date comes first in the former ORDER BY FOLLOW_UP_DATE DESC NULLS FIRST (ties keep the first). */
	private static boolean isLater(Timestamp candidate, Timestamp current) {
		if (current == null) {
			return false;
		}
		return candidate == null || candidate.after(current);
	}

	/** The alert sources: AHI_ALERT_CODE, AHI_ALERT_DESC. */
	public List<Map<String, String>> getAhiAlertSrc() {
		List<Map<String, String>> rows = new ArrayList<>();
		alertSources.findAllBy().forEach(c -> rows.add(map("AHI_ALERT_CODE", c.ahiAlertCode(), "AHI_ALERT_DESC", c.ahiAlertDesc())));
		return rows;
	}

	/** The yes / no answers: ANS_CODE, ANS_DESC. */
	public List<Map<String, String>> getYesNo() {
		List<Map<String, String>> rows = new ArrayList<>();
		yesNo.findAllBy().forEach(c -> rows.add(map("ANS_CODE", c.ansCode(), "ANS_DESC", c.ansDesc())));
		return rows;
	}

	/** The active / passive answers except 'N': ANS_CODE, ANS_DESC. */
	public List<Map<String, String>> getActivePassive() {
		List<Map<String, String>> rows = new ArrayList<>();
		activePassive.findByAnsCodeNot("N").forEach(c -> rows.add(map("ANS_CODE", c.ansCode(), "ANS_DESC", c.ansDesc())));
		return rows;
	}

	/** The follow-up steps of the drop-down, by sort order: FOLLOW_UP_CODE, FOLLOW_UP_DESC. */
	public List<Map<String, String>> getAhiPrfFollowup() {
		List<Map<String, String>> rows = new ArrayList<>();
		prfFollowups.findByDropDownOrderBySortOrder("Y").forEach(c -> rows.add(map("FOLLOW_UP_CODE", c.followUpCode(), "FOLLOW_UP_DESC", c.followUpDesc())));
		return rows;
	}

	/** The partner services message types in use, by description: PS_MSG_TYPE, PS_MSG_TYPE_DESCRIPTION. */
	public List<Map<String, String>> getPSMessageType() {
		List<Map<String, String>> rows = new ArrayList<>();
		psMessageTypes.findByEndDateIsNullOrderByPsMsgTypeDescription()
				.forEach(c -> rows.add(map("PS_MSG_TYPE", c.psMsgType(), "PS_MSG_TYPE_DESCRIPTION", c.psMsgTypeDescription())));
		return rows;
	}

	/**
	 * Records an alert's PRF collection ({@code prfCollection}) by {@code lastUpdatedBy} now; with
	 * {@code alertCompletedBy}, also completes the alert now.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateAhiAlertTrackingData(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		if (m.get("alertCompletedBy") != null) {
			return alerts.updatePrfCollectionAndComplete(text(m.get("prfCollection")), longValue(m.get("lastUpdatedBy")),
					longValue(m.get("alertCompletedBy")), longValue(m.get("ahiAlertSeqId")));
		}
		return alerts.updatePrfCollection(text(m.get("prfCollection")), longValue(m.get("lastUpdatedBy")), longValue(m.get("ahiAlertSeqId")));
	}

	/**
	 * Inserts a follow-up step ({@code followupStepTakenCode}, {@code userNotes}) of an alert ({@code ahiAlertSeqId}) by
	 * {@code userNotesBy}, now.
	 *
	 * @return 1
	 */
	@Transactional
	public int insertAhiAlertTrackingFollowupData(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		insert(AhiAlertFollowup.builder().ahiAlertSeqId(longValue(m.get("ahiAlertSeqId"))).followUpCode(text(m.get("followupStepTakenCode")))
				.userNotes(text(m.get("userNotes"))).followUpUid(longValue(m.get("userNotesBy"))).build());
		return 1;
	}

	/** An alert for its tracking form (null if none; several rows fail, as before). Bug kept: see the class comment. */
	public AhiAlertTrackingVO getAhiAlertDetails(int ahiAlertSeqId) {
		AhiAlertDetailsRow row = one(alerts.findDetails((long) ahiAlertSeqId));
		if (row == null) {
			return null;
		}
		AhiAlertTracking t = row.alert();
		return Rows.bean(AhiAlertTrackingVO.class).map(new RowBuilder()
				.put("ahiAlertSeqId", t.getAhiAlertSeqId()).put("stateno", t.getStateno()).put("ahiDocId", t.getAhiDocumentId())
				.put("ahiFlagSetDate", format(t.getAhiCreateDate())).put("ahiFlagSetBy", row.flagSetBy())
				.put("alertSource", t.getAhiAlertCode()).put("alertSourceDesc", row.alertSourceDesc()).put("ahiFlag", t.getAhiFlag())
				.put("prfCollection", t.getPrfCollection()).put("prfCollectionDesc", row.prfCollectionDesc())
				.put("prfCollectionDate", format(t.getPrfCollectionDate())).put("prfFlaggedAhi", t.getPrfFlaggedAhi())
				.put("lastUpdatedBy", row.lastUpdatedBy()).put("lastUpdatedDate", format(t.getLastUpdatedDate()))
				.put("alertCompletedBy", t.getAlertCompletedBy()).put("alertCompletedDate", format(t.getAlertCompletedDate()))
				.put("ahiAlertStatus", t.getAhiAlertStatus()).put("ahiAlertReviewedDate", format(t.getAhiAlertReviewedDate()))
				.put("ahiAlertReviewedBy", t.getAhiAlertReviewedBy()).put("assignmentId", row.assignmentId())
				.put("triggeringDocType", row.triggeringDocType())
				.build());
	}

	/** The processed partner services messages of a person (send and return dates as MM/DD/YYYY). */
	public List<AHIAlertPSMessageVO> getAHIAlertPSMessageInfo(String stateno) throws DataAccessException {
		List<AHIAlertPSMessageVO> rows = new ArrayList<>();
		if (stateno == null) {
			return rows;
		}
		for (SentMessage s : sentMessages.findByCurrentStatenoAndProcessInd(stateno, "processed")) {
			rows.add(Rows.bean(AHIAlertPSMessageVO.class).map(new RowBuilder().put("TRIGGER_MSG_TYPE", s.triggerMsgType())
					.put("SEND_DATE", format(s.messageEnteredDate())).put("RETURN_DATE", format(s.returnMessageEnteredDate()))
					.put("LATEST_DISPOSITION_DESC", s.latestDispositionDesc()).build()));
		}
		return rows;
	}

	/**
	 * Marks an alert ({@code ahiAlertSeqId}) reviewed by {@code ahiAlertReviewedBy} now.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateAhiAlertStatus(Map<String, Object> inputParameterMap) {
		return alerts.markReviewed(longValue(inputParameterMap.get("ahiAlertReviewedBy")), longValue(inputParameterMap.get("ahiAlertSeqId")));
	}

	/** Inserts an entity at once and detaches it (rows with a repeating declared id can follow; no stale reads). */
	private void insert(Object entity) {
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.detach(entity);
	}

	/** A list parameter of a former foreach insert. */
	private static Collection<?> list(Object value) {
		if (!(value instanceof Collection<?> collection) || collection.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No rows given: " + value);
		}
		return collection;
	}

	/** A code-list row as MyBatis mapped it: a HashMap without the null values, null if both are null. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map<String, String> map(String codeLabel, String code, String descLabel, String desc) {
		return (Map) Rows.map().map(new RowBuilder().put(codeLabel, code).put(descLabel, desc).build());
	}

	private static <T> T one(List<T> rows) {
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : rows.get(0);
	}

	/** TO_CHAR(date, 'MM/DD/YYYY'); null stays null. */
	private static String format(Timestamp date) {
		return date == null ? null : date.toLocalDateTime().format(DATE);
	}

	/** A text parameter: text as it is ('' is NULL in Oracle), anything else as its text. */
	private static String text(Object value) {
		if (value == null) {
			return null;
		}
		String text = value.toString();
		return text.isEmpty() ? null : text;
	}

	/** A value written to a number column: a number as it is, a text as the number it holds; null or '' as null. */
	private static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		return value instanceof BigDecimal number ? number : new BigDecimal(value.toString().trim());
	}

	/** A whole-number value (a fraction fails rather than being cut). */
	private static Long longValue(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.longValueExact();
	}
}
