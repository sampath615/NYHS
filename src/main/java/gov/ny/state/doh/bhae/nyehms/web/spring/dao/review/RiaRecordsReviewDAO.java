package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.RiaDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaFollowupDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaInterventionDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ria.RiaProviderFollowupVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.RiaFollowUp;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The RIA (recent infection / linkage) records: the review lists and reports, the RIA document with its follow-ups,
 * interventions, provider follow-up calls and activity, and its save and close (formerly a MyBatis mapper with the
 * same name and methods). JPQL over the entities of {@code entity.common} (NYEHMS_RIA_DOC, ALL_RIA_VW, NYEHMS_USERS)
 * and {@code entity.review} (CODED_RIA_*, RIA_*, FOLLOW_UP_INTERVENTION_MAP); the former Oracle {@code (+)} joins are
 * JPQL left joins, TO_CHAR of a date is {@code format(...)}, fn_to_date / TO_DATE / TRIM / UPPER stay the database's
 * functions, and SYSDATE is the database's SYSDATE.
 * <p>
 * Native SQL, unchanged except that the values are bound, and why:
 * <ul>
 * <li>the review list and the reports ({@link #getRiaRecordsForReview}, {@link #getRiaReportByFollowup},
 * {@link #getRiaEligibilityRecordsByReport}, {@link #getRiaReportByTreatment}, {@link #getRiaTreatmentReport},
 * {@link #getRiaFollowUpReport}, {@link #getRiaEligibilityReport}, {@link #getRiaActivityReportData},
 * {@link #getRiaNoLinkedtoCareDataReport}): Oracle date arithmetic ({@code TO_DATE(...) + 1}, date subtraction),
 * LISTAGG, PIVOT, analytic functions over the PL/SQL function calc_ehars_dx_dt, {@code (+)} joins in CTEs;</li>
 * <li>{@link #saveRiaDoc}: the UPDATE statement is built by the caller (RiaReviewService) as text and run as it
 * is.</li>
 * </ul>
 * The column names the former SQL took from the request with {@code ${PARAMETER_NAME}} are checked against the names
 * the screens pass (see {@link #REVIEW_FILTER_COLUMNS} and {@link #FOLLOW_UP_PROGRAMS}); any other name fails, as an
 * unknown column failed before.
 * <p>
 * Kept as they were: saveRiaDoc runs the caller's text, whose date values RiaReviewService pastes into TO_DATE('...')
 * unescaped; getNextFollowUpId never had a statement and fails; the follow-up report matches follow-up codes by
 * {@code like '%PROGRAM%'}.
 */
@Repository
@RequiredArgsConstructor
public class RiaRecordsReviewDAO {

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/** The select list of an RIA document (dates as TO_CHAR(x, 'MM/DD/YYYY')), over alias r. */
	private static final String DOC_COLUMNS = """
			format(r.addressDate as 'MM/dd/yyyy') as ADDRESS_DATE,
			format(r.completeDate as 'MM/dd/yyyy') as COMPLETE_DATE,
			format(r.firstGenotypeDate as 'MM/dd/yyyy') as FIRST_GENOTYPE_DATE,
			format(r.firstPrfDate as 'MM/dd/yyyy') as FIRST_PRF_DATE,
			format(r.initialAppointmentDate as 'MM/dd/yyyy') as INITIAL_APPOINTMENT_DATE,
			format(r.initialCollectionDate as 'MM/dd/yyyy') as INITIAL_COLLECTION_DATE,
			format(r.lastUpdatedDate as 'MM/dd/yyyy') as LAST_UPDATED_DATE,
			format(r.movedFromNyDate as 'MM/dd/yyyy') as MOVED_FROM_NY_DATE,
			format(r.movedToNyDate as 'MM/dd/yyyy') as MOVED_TO_NY_DATE,
			format(r.psClosureDate as 'MM/dd/yyyy') as PS_CLOSURE_DATE,
			format(r.psLastUpdateDate as 'MM/dd/yyyy') as PS_LAST_UPDATE_DATE,
			format(r.psMessageSentDate as 'MM/dd/yyyy') as PS_MESSAGE_SENT_DATE,
			format(r.recentCd4Date as 'MM/dd/yyyy') as RECENT_CD4_DATE,
			format(r.recentVlDate as 'MM/dd/yyyy') as RECENT_VL_DATE,
			format(r.riaEligibilityDate as 'MM/dd/yyyy') as RIA_ELIGIBILITY_DATE,
			format(r.riaFlaggedDate as 'MM/dd/yyyy') as RIA_FLAGGED_DATE,
			format(r.soundexCheckDate as 'MM/dd/yyyy') as SOUNDEX_CHECK_DATE,
			format(r.firstVlDate as 'MM/dd/yyyy') as FIRST_VL_DATE,
			r.arvMedication as ARV_MEDICATION,
			r.cdesscasekey as CDESSCASEKEY,
			r.deathSource as DEATH_SOURCE,
			r.labDocId as LAB_DOC_ID,
			r.treatmentSource as TREATMENT_SOURCE,
			r.ahiFlag as AHI_FLAG,
			r.airsDoc as AIRS_DOC,
			r.arvsTaken as ARVS_TAKEN,
			r.eharsElig as EHARS_ELIG,
			r.pkpFlag as PKP_FLAG,
			r.ptOnPrepFlag as PT_ON_PREP_FLAG,
			r.nonPromptFlag as NON_PROMPT_FLAG,
			r.deceased as DECEASED,
			r.federalFacility as FEDERAL_FACILITY,
			r.ndvlAtDx as NDVL_AT_DX,
			r.perinatal as PERINATAL,
			r.notHivFlag as NOT_HIV_FLAG,
			r.oojCaseFlag as OOJ_CASE_FLAG,
			r.oojProviderFlag as OOJ_PROVIDER_FLAG,
			r.prepTaken as PREP_TAKEN,
			r.noPreviousTest as NO_PREVIOUS_TEST,
			r.raceAsian as RACE_ASIAN,
			r.raceBlack as RACE_BLACK,
			r.raceIndian as RACE_INDIAN,
			r.racePacific as RACE_PACIFIC,
			r.raceWhite as RACE_WHITE,
			r.rapProvider as RAP_PROVIDER,
			r.tthRapProvider as TTH_RAP_PROVIDER,
			r.recordSearch as RECORD_SEARCH,
			r.riaEligible as RIA_ELIGIBLE,
			r.arvBeginDate as ARV_BEGIN_DATE,
			r.lipDateText as LIP_DATE_TEXT,
			r.burklDateText as BURKL_DATE_TEXT,
			r.calcEharsHivDxDt as CALC_EHARS_HIV_DX_DT,
			r.candeDateText as CANDE_DATE_TEXT,
			r.candlungDateText as CANDLUNG_DATE_TEXT,
			r.cervcancDateText as CERVCANC_DATE_TEXT,
			r.cmvDateText as CMV_DATE_TEXT,
			r.cmvretDateText as CMVRET_DATE_TEXT,
			r.cocciDateText as COCCI_DATE_TEXT,
			r.cryptocoDateText as CRYPTOCO_DATE_TEXT,
			r.cryptospDateText as CRYPTOSP_DATE_TEXT,
			r.currNyehmsStateno as CURR_NYEHMS_STATENO,
			r.diagnosisSettingType as DIAGNOSIS_SETTING_TYPE,
			r.dobText as DOB_TEXT,
			r.dodText as DOD_TEXT,
			r.herpesDateText as HERPES_DATE_TEXT,
			r.histoDateText as HISTO_DATE_TEXT,
			r.hivncephDateText as HIVNCEPH_DATE_TEXT,
			r.isoDateText as ISO_DATE_TEXT,
			r.ksDateText as KS_DATE_TEXT,
			r.lastUpdatedBy as LAST_UPDATED_BY,
			r.lymphbDateText as LYMPHB_DATE_TEXT,
			r.lymphimDateText as LYMPHIM_DATE_TEXT,
			r.mycoavDateText as MYCOAV_DATE_TEXT,
			r.mycodismDateText as MYCODISM_DATE_TEXT,
			r.mycopulmDateText as MYCOPULM_DATE_TEXT,
			r.mycothDateText as MYCOTH_DATE_TEXT,
			r.nycEharsHivDxDt as NYC_EHARS_HIV_DX_DT,
			r.nyehmsStateno as NYEHMS_STATENO,
			r.nysEharsHivDxDt as NYS_EHARS_HIV_DX_DT,
			r.nysLastNegDt as NYS_LAST_NEG_DT,
			r.pcpDateText as PCP_DATE_TEXT,
			r.pmlDateText as PML_DATE_TEXT,
			r.pneumDateText as PNEUM_DATE_TEXT,
			r.treatmentFacTypeOtherText as TREATMENT_FAC_TYPE_OTHER_TEXT,
			r.prepNonAdherRsnOtherText as PREP_NON_ADHER_RSN_OTHER_TEXT,
			r.recentCd4Value as RECENT_CD4_VALUE,
			r.recentVlValue as RECENT_VL_VALUE,
			r.firstVlUnits as FIRST_VL_UNITS,
			r.firstVlValue as FIRST_VL_VALUE,
			r.riaEligibilityUid as RIA_ELIGIBILITY_UID,
			r.salmnelaDateText as SALMNELA_DATE_TEXT,
			r.toxoDateText as TOXO_DATE_TEXT,
			r.wastingDateText as WASTING_DATE_TEXT,
			r.airsFacility as AIRS_FACILITY,
			r.airsTcId as AIRS_TC_ID,
			r.initialProvAddress as INITIAL_PROV_ADDRESS,
			r.initialProvname as INITIAL_PROVNAME,
			r.personAddress as PERSON_ADDRESS,
			r.psClosureDispo as PS_CLOSURE_DISPO,
			r.recentVlProvAddress as RECENT_VL_PROV_ADDRESS,
			r.recentVlProvname as RECENT_VL_PROVNAME,
			r.ridrInfo as RIDR_INFO,
			r.psWorkerAssigned as PS_WORKER_ASSIGNED,
			r.disnumber as DISNUMBER,
			r.birthSex as BIRTH_SEX,
			r.currentGender as CURRENT_GENDER,
			r.foodInsecurity as FOOD_INSECURITY,
			r.healthLiteracy as HEALTH_LITERACY,
			r.housingInstability as HOUSING_INSTABILITY,
			r.incarceration as INCARCERATION,
			r.initialProvState as INITIAL_PROV_STATE,
			r.insuranceBarrier as INSURANCE_BARRIER,
			r.languageBarrier as LANGUAGE_BARRIER,
			r.mentalHealthBarrier as MENTAL_HEALTH_BARRIER,
			r.personState as PERSON_STATE,
			r.pkpDxState as PKP_DX_STATE,
			r.recvCareOos as RECV_CARE_OOS,
			r.recvCareStateCd as RECV_CARE_STATE_CD,
			r.oosFacilityName as OOS_FACILITY_NAME,
			r.prepAdherence as PREP_ADHERENCE,
			r.prepNonAdherenceReason as PREP_NON_ADHERENCE_REASON,
			r.pkpDxDate as PKP_DX_DATE,
			r.recentVlProvState as RECENT_VL_PROV_STATE,
			r.substanceUse as SUBSTANCE_USE,
			r.transportationBarriers as TRANSPORTATION_BARRIERS,
			r.rapProviderMname as RAP_PROVIDER_MNAME,
			r.tthRapProviderMname as TTH_RAP_PROVIDER_MNAME,
			r.birthCountry as BIRTH_COUNTRY,
			r.ethnicity as ETHNICITY,
			r.initialFacilityCounty as INITIAL_FACILITY_COUNTY,
			r.initialProvCounty as INITIAL_PROV_COUNTY,
			r.personCounty as PERSON_COUNTY,
			r.pkpDxCountry as PKP_DX_COUNTRY,
			r.initialProvCity as INITIAL_PROV_CITY,
			r.recentVlProvCity as RECENT_VL_PROV_CITY,
			r.firstName as FIRST_NAME,
			r.lastName as LAST_NAME,
			r.initialProvZip as INITIAL_PROV_ZIP,
			r.personZip as PERSON_ZIP,
			r.recentCd4Units as RECENT_CD4_UNITS,
			r.recentVlProvZip as RECENT_VL_PROV_ZIP,
			r.recentVlUnits as RECENT_VL_UNITS,
			r.barriersToCareOtherText as BARRIERS_TO_CARE_OTHER_TEXT,
			r.adverseEffectsToMeds as ADVERSE_EFFECTS_TO_MEDS,
			r.treatmentFacilityName as TREATMENT_FACILITY_NAME,
			r.treatmentFacilityType as TREATMENT_FACILITY_TYPE,
			r.caseClosureCd as CASE_CLOSURE_CD,
			r.deathSourceOtherText as DEATH_SOURCE_OTHER_TEXT,
			r.diagnosisFacility as DIAGNOSIS_FACILITY,
			r.diagnosisSettingOtherText as DIAGNOSIS_SETTING_OTHER_TEXT,
			r.nycDiseaseCategDx as NYC_DISEASE_CATEG_DX,
			r.nysDiseaseCategDx as NYS_DISEASE_CATEG_DX,
			r.personCity as PERSON_CITY,
			r.rapProviderFname as RAP_PROVIDER_FNAME,
			r.rapProviderLname as RAP_PROVIDER_LNAME,
			r.tthRapProviderFname as TTH_RAP_PROVIDER_FNAME,
			r.tthRapProviderLname as TTH_RAP_PROVIDER_LNAME,
			r.treatmentSourceOtherText as TREATMENT_SOURCE_OTHER_TEXT,
			r.bhaeNotes as BHAE_NOTES,
			r.omdNotes as OMD_NOTES,
			r.psNotes as PS_NOTES,
			r.ssn as SSN,
			r.documentId as DOCUMENT_ID
			""";

	/**
	 * The column names the review list filters by with {@code nvl(${PARAMETER_NAME}, '0') not in ('0')}: the
	 * NYEHMS_RIA_DOC columns the RIA screen offers (and the other columns of the RIA document).
	 */
	static final Set<String> REVIEW_FILTER_COLUMNS = columnNames();

	/** The follow-up programs ({@code ${PARAMETER_NAME}_NOTES}, {@code like '%${PARAMETER_NAME}%'}). */
	static final Set<String> FOLLOW_UP_PROGRAMS = Set.of("BHAE", "OMD", "PS");

	private static Set<String> columnNames() {
		Set<String> names = new TreeSet<>();
		for (String line : DOC_COLUMNS.split("\n")) {
			String item = line.trim();
			if (!item.isEmpty()) {
				names.add(item.substring(item.lastIndexOf(" as ") + 4).replace(",", "").trim());
			}
		}
		names.addAll(Set.of("MERGED_RIA_STATENO", "STATENO_DELETED"));
		return Set.copyOf(names);
	}

	/** A column name from the request, as it was given, if it is allowed; otherwise the call fails. */
	private static String allowed(Object name, Set<String> allowed) {
		if (name instanceof String text && allowed.contains(text.toUpperCase(Locale.ENGLISH))) {
			return text;
		}
		throw new InvalidDataAccessApiUsageException("Not an allowed RIA column / program: " + name);
	}

	// ---------------------------------------------------------------- review list and reports (native)

	private static final String REVIEW_COLUMNS = """
			 select * from (
			select distinct ria.DOCUMENT_ID RIA_DOCUMENT_ID, test_type_Cd as "documentType", LAB_DOC_ID as "documentId", CURR_NYEHMS_STATENO as "stateno", ria.FIRST_NAME as "firstName", ria.LAST_NAME as "lastName", DOB_TEXT as "dateOfBirth", INITIAL_PROVNAME "providerName", INITIAL_PROV_ADDRESS  || ', ' || INITIAL_PROV_CITY || ', ' || INITIAL_PROV_STATE || ', ' || INITIAL_PROV_ZIP  "providerAddress",
			PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP as "address",
			TO_CHAR(RIA_FLAGGED_DATE, 'MM/DD/YYYY') as "flaggedDate",
			TO_CHAR(complete_Date, 'MM/DD/YYYY') as "completedDate",
			""";

	private static final String INELIGIBLE_REASON = """
			case when NOT_HIV_FLAG is not null then 'Not HIV'
				when OOJ_CASE_FLAG is not null then 'OOJ Case'
				when OOJ_PROVIDER_FLAG is not null then 'OOJ Provider'
				when PT_ON_PREP_FLAG is not null then 'PREP'
				when NON_PROMPT_FLAG is not null then 'Non Prompt'
				when DECEASED is not null then 'Deceased'
				when FEDERAL_FACILITY is not null then 'Federal Facility'
				when NDVL_AT_DX is not null then 'NDVL at time of dx'
				when PERINATAL is not null then 'Perinatal'
				when PKP_FLAG is not null then 'PKP'
				else null end RIA_INELIGIBLE_REASON
			""";

	private static final String CALC_DX_DATE = """
			,TO_CHAR (calc_ehars_dx_dt (m.hiv_dx_dt, n.hiv_dx_dt),
			                         'mm/dd/yyyy')
			                   AS CALC_EHARS_HIV_DX_DT
			                   ,
			          row_number() over (partition by ria.DOCUMENT_ID order by TO_CHAR (calc_ehars_dx_dt (m.hiv_dx_dt, n.hiv_dx_dt), 'mm/dd/yyyy')) rn
			""";

	private static final String RIA_FROM = """
			  from nyehms_ria_doc ria
			  JOIN document_locator doc
			  ON (ria.lab_doc_id = doc.document_id)
			  LEFT OUTER JOIN ehars_person_nyc n
			  ON (ria.curr_nyehms_stateno = n.nyehms_stateno AND n.status_flag NOT IN ('D', 'P') AND n.hiv_aids_cdc = 'Y')
			    LEFT OUTER JOIN nyehms_ehars_stateno_map m
			  ON (ria.curr_nyehms_stateno = m.nyehms_stateno AND m.status_flag NOT IN ('D', 'P') AND m.hiv_aids_cdc = 'Y')
			  where ria.MERGED_RIA_STATENO IS NULL and nvl(STATENO_DELETED, 'N') != 'Y'
			""";

	/** The former sql fragment riaIneligibleReasonAndOpenRecords (two values: riaFlaggedFromDate, riaFlaggedToDate). */
	private static final String INELIGIBLE_REASON_AND_OPEN_RECORDS = INELIGIBLE_REASON.stripTrailing() + CALC_DX_DATE.stripLeading() + RIA_FROM + """
			  and complete_Date is null
			  and RIA_FLAGGED_DATE between TO_DATE(?, 'MM/DD/YYYY')  and TO_DATE(?, 'MM/DD/YYYY')+1
			""";

	private static final String FIRST_GENOTYPE_DATE = """
			and NVL(RIA_ELIGIBLE, 'Y') = 'Y' and curr_nyehms_stateno not in (select stateno from (
			SELECT p.stateno,
			                  MIN (calc_collection_date) AS min_seq_collection_date
			             FROM std_hiv_eclrs_doc s,
			                  person_doc_association p,
			                  nyehms_ria_doc nr
			            WHERE     s.document_id = p.document_id
			                  AND p.dissassociation_date IS NULL
			                  AND p.stateno = nr.curr_nyehms_stateno
			                  AND interpret_cd LIKE 'VALID%'
			                  AND test_result != 'NO RESULT'
			         GROUP BY p.stateno) where min_seq_collection_date is not null)
			""";

	private static final String ARV_BEGIN_DATE = """
			and NVL(RIA_ELIGIBLE, 'Y') = 'Y' and ARV_BEGIN_DATE is null and curr_nyehms_Stateno not in (select stateno from (
			SELECT stateno,
			                arv_begin_date
			           FROM (SELECT stateno,
			                        arv_begin_date,
			                        ROW_NUMBER ()
			                        OVER (
			                           PARTITION BY STATENO
			                           ORDER BY
			                                 SUBSTR (arv_begin_date, 7, 4)
			                              || SUBSTR (REPLACE (arv_begin_date, '.', '9'),
			                                         1,
			                                         2)
			                              || SUBSTR (REPLACE (arv_begin_date, '.', '9'),
			                                         4,
			                                         2))
			                           RWNUM
			                   FROM (SELECT p.stateno,
			                                ra.arv_begin_date
			                           FROM raw_acrf_doc rd,
			                                raw_acrf_arvs ra,
			                                person_doc_association p,
			                                nyehms_ria_doc nr
			                          WHERE     ra.acrf_doc_id = p.DOCUMENT_ID
			                                AND p.dissassociation_date IS NULL
			                                AND ra.acrf_doc_id = rd.document_id
			                                AND P.stateno = nr.curr_nyehms_stateno
			                                AND ra.arv_use_type = 'HIV Tx'
			                                AND ra.ARV_BEGIN_DATE IS NOT NULL
			                         UNION
			                         SELECT pda.stateno,
			                                s.pt_arv_start_date_text AS arv_start_date
			                           FROM std_prf_doc s,
			                                person_doc_association pda,
			                                nyehms_ria_doc nr
			                          WHERE     s.document_id = pda.document_id
			                                AND pda.dissassociation_date IS NULL
			                                AND PDA.stateno = nr.curr_nyehms_stateno
			                                AND LENGTH (pt_arv_start_date_text) > 3
			                                AND (arv_treatment = 'Y' OR PT_ARV_6_MO = 'Y')))
			          WHERE rwnum = 1) where arv_begin_date is not null)
			""";

	/** The follow-up notes and descriptions of a program (BHAE, OMD or PS, allow-listed), without leading comma. */
	private static String followUpNotes(String program) {
		return program + "_NOTES NOTES , (select LISTAGG(follow_up_desc, ', ')  WITHIN GROUP (ORDER BY RIA_DOC_ID) from RIA_FOLLOW_UP followup,"
				+ " CODED_RIA_FOLLOW_UP_CODES coded  where ria_doc_id = ria.DOCUMENT_ID and followup.follow_up_cd like  '%" + program
				+ "%' and coded.FOLLOW_UP_CD =followup.follow_up_cd) FOLLOW_UP_CD";
	}

	/**
	 * Native (LISTAGG, calc_ehars_dx_dt, analytic functions): the RIA records of a review tab / filter
	 * (PARAMETER_NAME), one row per RIA document.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> getRiaRecordsForReview(Map<String, String> inputParameterMap) {
		Object name = inputParameterMap.get("PARAMETER_NAME");
		boolean program = "BHAE".equals(name) || "OMD".equals(name) || "PS".equals(name);
		boolean completed = "COMPLETED".equals(name) || "RIA_INELIGIBLE_COMPLETED".equals(name);
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(REVIEW_COLUMNS).append(INELIGIBLE_REASON);
		if (program) {
			sql.append(", ").append(followUpNotes((String) name)).append('\n');
		}
		if (completed) {
			sql.append("""
					,(select LOOKUP_NAME   from CODED_RIA_DOC where REF_COLUMN = 'CASE_CLOSURE_CD' and LOOKUP_CD = ria.CASE_CLOSURE_CD)  CASE_CLOSURE_CD
					,CALC_EHARS_HIV_DX_DT
					,row_number() over (partition by ria.DOCUMENT_ID order by CALC_EHARS_HIV_DX_DT) rn
					""");
		} else {
			sql.append(CALC_DX_DATE);
		}
		sql.append(RIA_FROM);
		sql.append(completed ? " and complete_Date is not null\n" : " and complete_Date is null\n");
		if ("TELE_HEALTH".equals(name)) {
			sql.append(" and NVL(DIAGNOSIS_SETTING_TYPE, 'X') = 'ONLINE_TEST'\n");
		} else if ("NO_PS_MSG".equals(name)) {
			sql.append(" and not exists (select 1 from NYEHMS_STD_MESSAGE_SENT sent, person_doc_association pdoc where sent.follow_up_doc_id = pdoc.document_id"
					+ " and pdoc.dissassociation_date is null and pdoc.stateno = ria.curr_nyehms_stateno and sent.cdess_delete_date is null )\n");
		} else if ("ALL_ELIG".equals(name)) {
			sql.append(" and NVL(RIA_ELIGIBLE, 'Y') = 'Y'\n");
		} else if ("FIRST_GENOTYPE_DATE".equals(name)) {
			sql.append(FIRST_GENOTYPE_DATE);
		} else if ("ARV_BEGIN_DATE".equals(name)) {
			sql.append(ARV_BEGIN_DATE);
		} else if ("ALL_INELIG".equals(name)) {
			sql.append(" and RIA_ELIGIBLE = 'N'\n");
		} else if ("COMPLETED".equals(name)) {
			sql.append(" and RIA_ELIGIBLE = 'Y'\n");
		} else if ("RIA_INELIGIBLE_COMPLETED".equals(name)) {
			sql.append(" and RIA_ELIGIBLE = 'N'\n");
		} else if ("SOUNDEX_CHECK_DATE".equals(name)) {
			sql.append(" and SOUNDEX_CHECK_DATE is not null\n");
		} else if (program) {
			parameters.add(NativeStatements.text(name));
			sql.append(" and NVL(RIA_ELIGIBLE, 'Y') = 'Y' and exists (select 1 from RIA_FOLLOW_UP followUp, CODED_RIA_FOLLOW_UP_CODES coded_followUp"
					+ "  where  RIA_DOC_ID = ria.DOCUMENT_ID and coded_followUp.FOLLOW_UP_CD = followUp.FOLLOW_UP_CD and PROGRAM_TYPE = ?1"
					+ " and not exists (select 1 from RIA_INTERVENTION where followUp.RIA_FOLLOW_UP_ID = RIA_FOLLOW_UP_ID))\n");
		} else {
			sql.append(" and nvl(").append(allowed(name, REVIEW_FILTER_COLUMNS)).append(", '0')  not in ('0')\n");
		}
		sql.append(" ) where rn = 1");
		return (List) statements.list(sql.toString(), Rows.json(), parameters.toArray());
	}

	/** The RIA columns of the reports, with the ineligible reason and the open records flagged in the date range. */
	private static String reportColumnsAndOpenRecords() {
		return REVIEW_COLUMNS + INELIGIBLE_REASON_AND_OPEN_RECORDS;
	}

	/**
	 * Native (LISTAGG, calc_ehars_dx_dt, TO_DATE + 1): the open RIA records flagged from riaFlaggedFromDate to
	 * riaFlaggedToDate with a follow-up of the program PARAMETER_NAME (BHAE, OMD, PS), with (PARAMETER_VALUE Y) or
	 * without interventions.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> getRiaReportByFollowup(Map<String, Object> inputParameterMap) {
		String program = allowed(inputParameterMap.get("PARAMETER_NAME"), FOLLOW_UP_PROGRAMS);
		String sql = REVIEW_COLUMNS + followUpNotes(program) + ",\n" + INELIGIBLE_REASON_AND_OPEN_RECORDS
				+ "  and exists (select 1 from RIA_FOLLOW_UP followUp, CODED_RIA_FOLLOW_UP_CODES coded_followUp  where  RIA_DOC_ID = ria.DOCUMENT_ID"
				+ " and coded_followUp.FOLLOW_UP_CD = followUp.FOLLOW_UP_CD and PROGRAM_TYPE = ?3\n"
				+ ("Y".equals(inputParameterMap.get("PARAMETER_VALUE"))
						? "  and exists (select 1 from RIA_INTERVENTION where followUp.RIA_FOLLOW_UP_ID = RIA_FOLLOW_UP_ID))\n"
						: "  and not exists (select 1 from RIA_INTERVENTION where followUp.RIA_FOLLOW_UP_ID = RIA_FOLLOW_UP_ID))\n")
				+ " ) where rn = 1";
		return (List) statements.list(numbered(sql), Rows.json(), NativeStatements.text(inputParameterMap.get("riaFlaggedFromDate")),
				NativeStatements.text(inputParameterMap.get("riaFlaggedToDate")), NativeStatements.text(inputParameterMap.get("PARAMETER_NAME")));
	}

	/**
	 * Native (fn_to_date, TO_DATE + 1): the number of RIA records flagged in the date range without treatment and with
	 * a treatment begun before the diagnosis (ARV_TREATMENT, COUNT as text).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaTreatmentReport(Map<String, Object> inputParameterMap) {
		return (List) statements.list("""
				select ARV_TREATMENT, TO_CHAR(count(*)) count from (
				select case when arv_begin_date is null then 'NO_TREATMENT'
				            when arv_begin_date is not null then 'PRIOR_TREATMENT' end ARV_TREATMENT  from all_ria_vw
				where RIA_FLAGGED_DATE between TO_DATE(?1, 'MM/DD/YYYY')  and TO_DATE(?2, 'MM/DD/YYYY')+1
				and (arv_begin_date is null or fn_to_date(arv_begin_date, 'MM/DD/YYYY') <  fn_to_date(CALC_EHARS_HIV_DX_DT, 'MM/DD/YYYY'))) group by ARV_TREATMENT
				""", Rows.map(), dateRange(inputParameterMap));
	}

	/**
	 * Native (calc_ehars_dx_dt, TO_DATE + 1): the open RIA records flagged in the date range, of the person counties
	 * (unless ALL_COUNTIES), by eligibility (PARAMETER_NAME ALL_ELIG with PARAMETER_VALUE Y / N, or ALL_INELIG).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> getRiaEligibilityRecordsByReport(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>(Arrays.asList(dateRange(inputParameterMap)));
		StringBuilder sql = new StringBuilder(reportColumnsAndOpenRecords());
		if (Boolean.FALSE.equals(inputParameterMap.get("ALL_COUNTIES"))) {
			sql.append(" AND person_county IN ").append(inPlaceholders(inList(inputParameterMap.get("personCounties"), "personCounties"), parameters)).append('\n');
		}
		Object name = inputParameterMap.get("PARAMETER_NAME");
		Object value = inputParameterMap.get("PARAMETER_VALUE");
		if ("ALL_ELIG".equals(name) && "Y".equals(value)) {
			sql.append(" and RIA_ELIGIBLE = 'Y'\n");
		} else if ("ALL_INELIG".equals(name)) {
			sql.append(" and RIA_ELIGIBLE = 'N'\n");
		} else if ("ALL_ELIG".equals(name) && "N".equals(value)) {
			sql.append(" and RIA_ELIGIBLE is null\n");
		}
		sql.append(" ) where rn = 1");
		return (List) statements.list(numbered(sql.toString()), Rows.json(), parameters.toArray());
	}

	/**
	 * Native (fn_to_date, TO_DATE + 1): the RIA records (ALL_RIA_VW) flagged in the date range without treatment
	 * (PARAMETER_VALUE NO_TREATMENT) or with a treatment begun before the diagnosis.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> getRiaReportByTreatment(Map<String, Object> inputParameterMap) {
		String sql = """
				select distinct ria.DOCUMENT_ID RIA_DOCUMENT_ID, test_type_Cd as "documentType", LAB_DOC_ID as "documentId", CURR_NYEHMS_STATENO as "stateno", ria.FIRST_NAME as "firstName", ria.LAST_NAME as "lastName", DOB_TEXT as "dateOfBirth", INITIAL_PROVNAME "providerName", INITIAL_PROV_ADDRESS  || ', ' || INITIAL_PROV_CITY || ', ' || INITIAL_PROV_STATE || ', ' || INITIAL_PROV_ZIP  "providerAddress",
				PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP as "address",
				TO_CHAR(RIA_FLAGGED_DATE, 'MM/DD/YYYY') as "flaggedDate",
				TO_CHAR(complete_Date, 'MM/DD/YYYY') as "completedDate",
				CALC_EHARS_HIV_DX_DT,
				ARV_BEGIN_DATE,
				""" + INELIGIBLE_REASON + """
				         from ALL_RIA_VW ria
				  JOIN document_locator doc
				  ON (ria.lab_doc_id = doc.document_id)
				  where  RIA_FLAGGED_DATE between TO_DATE(?1, 'MM/DD/YYYY')  and TO_DATE(?2, 'MM/DD/YYYY')+1
				""" + ("NO_TREATMENT".equals(inputParameterMap.get("PARAMETER_VALUE"))
						? "  and ARV_BEGIN_DATE is null"
						: "  and fn_to_date(arv_begin_date, 'MM/DD/YYYY') <  fn_to_date(CALC_EHARS_HIV_DX_DT, 'MM/DD/YYYY')");
		return (List) statements.list(sql, Rows.json(), dateRange(inputParameterMap));
	}

	/**
	 * Native (TO_DATE + 1): the number of open RIA records flagged in the date range with follow-ups of each program,
	 * with and without interventions (PROGRAM_TYPE {program}_COMPLETED / _NOT_COMPLETED, COUNT as text).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaFollowUpReport(Map<String, Object> inputParameterMap) {
		String part = """
				  FROM RIA_FOLLOW_UP follow, CODED_RIA_FOLLOW_UP_CODES coded, nyehms_ria_doc ria
				 WHERE  ria.document_id = follow.ria_doc_id and
				 RIA_FLAGGED_DATE between TO_DATE(?%d, 'MM/DD/YYYY')  and TO_DATE(?%d, 'MM/DD/YYYY')+1 and
				  ria.MERGED_RIA_STATENO IS NULL and nvl(STATENO_DELETED, 'N') != 'Y' and ria.complete_date is null and
				 coded.FOLLOW_UP_CD=follow.FOLLOW_UP_CD and %s EXISTS
				          (SELECT 1
				             FROM RIA_INTERVENTION
				            WHERE RIA_FOLLOW_UP_ID = follow.RIA_FOLLOW_UP_ID)
				            group by PROGRAM_TYPE
				""";
		String sql = "SELECT PROGRAM_TYPE || '_COMPLETED' PROGRAM_TYPE, TO_CHAR(count(distinct RIA_DOC_ID)) count\n" + part.formatted(1, 2, "")
				+ "Union\nSELECT PROGRAM_TYPE || '_NOT_COMPLETED' PROGRAM_TYPE, TO_CHAR(count(distinct RIA_DOC_ID)) count\n" + part.formatted(3, 4, "not");
		Object[] range = dateRange(inputParameterMap);
		return (List) statements.list(sql, Rows.map(), range[0], range[1], range[0], range[1]);
	}

	/**
	 * Native (PIVOT, TO_DATE + 1): the number of open RIA records flagged in the date range, of the person counties
	 * (unless ALL_COUNTIES), that are eligible, not eligible and not decided (RIA_ELIGIBLE, RIA_NOT_ELIGIBLE,
	 * RIA_ELIGIBILITY_NOT_SELECTED).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, Integer> getRiaEligibilityReport(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>(Arrays.asList(dateRange(inputParameterMap)));
		StringBuilder sql = new StringBuilder("""
				SELECT nvl("RIA_ELIGIBLE", 0) as "RIA_ELIGIBLE", nvl("RIA_NOT_ELIGIBLE", 0) as "RIA_NOT_ELIGIBLE", nvl("RIA_ELIGIBILITY_NOT_SELECTED", 0)  as "RIA_ELIGIBILITY_NOT_SELECTED"
				              FROM (
				            select case when RIA_ELIGIBLE = 'Y' then 'RIA_ELIGIBLE'
				                    when RIA_ELIGIBLE = 'N' then 'RIA_NOT_ELIGIBLE'
				                    when RIA_ELIGIBLE is null then 'RIA_ELIGIBILITY_NOT_SELECTED' end RIA_ELIGIBILITY , count(*) cnt from nyehms_ria_doc ria, document_locator doc
				                    where ria.lab_doc_id =  doc.document_id and
				                    RIA_FLAGGED_DATE between TO_DATE(?1, 'MM/DD/YYYY')  and TO_DATE(?2, 'MM/DD/YYYY')+1
				                     and complete_Date is null and MERGED_RIA_STATENO is null and nvl(STATENO_DELETED, 'N') != 'Y'
				""");
		if (Boolean.FALSE.equals(inputParameterMap.get("ALL_COUNTIES"))) {
			sql.append(" AND person_county IN ").append(inPlaceholders(inList(inputParameterMap.get("personCounties"), "personCounties"), parameters)).append('\n');
		}
		sql.append("""
				                     group by RIA_ELIGIBLE
				            )
				            PIVOT
				            (
				                SUM(cnt) FOR RIA_ELIGIBILITY IN ('RIA_ELIGIBLE' as "RIA_ELIGIBLE", 'RIA_NOT_ELIGIBLE' as "RIA_NOT_ELIGIBLE", 'RIA_ELIGIBILITY_NOT_SELECTED'  as "RIA_ELIGIBILITY_NOT_SELECTED")
				            )
				""");
		return (Map) statements.one(sql.toString(), Rows.map(), parameters.toArray());
	}

	/** Native (TO_DATE + 1, analytic COUNT): the RIA document changes by worker from p_fromDate to p_toDate. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaActivityReportData(Map<String, String> inputParameterMap) {
		return (List) statements.list("""
				SELECT a.curr_nyehms_stateno, b.legacy_uid, b.last_name||', '||b.first_name user_name, to_char(a.TRANSACTION_DATE, 'MM/DD/YYYY HH:MI:SS AM') last_updated_date
				         , to_char(COUNT(*) OVER (PARTITION BY a.TRANSACTION_BY)) user_activity_count
				    FROM aud_nyehms_ria_doc a, nyehms_users b
				   WHERE Trunc(a.TRANSACTION_DATE) BETWEEN To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY')+1
				     AND a.TRANSACTION_BY = b.worker_id
				     AND a.TRANSACTION_BY != '900'
				ORDER BY user_name, a.TRANSACTION_DATE
				""", Rows.map(), NativeStatements.text(inputParameterMap.get("p_fromDate")), NativeStatements.text(inputParameterMap.get("p_toDate")));
	}

	/**
	 * Native (CTEs with (+) and date subtraction): the eligible RIA records with an initial collection in the date
	 * range and whether a lab followed within 7, 14, 21, 30 or more days.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> getRiaNoLinkedtoCareDataReport(Map<String, Object> inputParameterMap) {
		return (List) statements.list("""
				with ria as
				(
				Select Min(Trunc(initial_collection_date)) initial_collection_date , doc.TEST_TYPE_CD as "DOC_TYPE",
				curr_nyehms_stateno,n.DOCUMENT_ID as "DOCUMENT_ID", LAB_DOC_ID
				from
				nyehms_ria_doc n, document_locator doc
				where (ria_eligible = 'Y' or ria_eligible is null)
				and n.lab_doc_id = doc.DOCUMENT_ID
				and initial_collection_date BETWEEN TO_DATE(?1,'MM/DD/YYYY') and TO_DATE(?2,'MM/DD/YYYY')
				group by curr_nyehms_stateno, n.DOCUMENT_ID, LAB_DOC_ID,doc.TEST_TYPE_CD
				), labs as
				(
				select p.stateno, Min(trunc(calc_collection_date)) calc_collection_date
				from std_hiv_eclrs_doc s, person_doc_association p, ria r
				where s.document_id = p.document_id
				  and p.dissassociation_date is null
				  and p.stateno = r.curr_nyehms_stateno
				  and calc_collection_date > r.initial_collection_date
				GROUP BY p.stateno
				), data as
				(
				SELECT r.DOCUMENT_ID as document_id, r.LAB_DOC_ID as lab_doc_id , r.CURR_NYEHMS_STATENO as stateno, r.DOC_TYPE as DOC_TYPE,r.initial_collection_date, calc_collection_date, ABS(calc_collection_date - initial_collection_date) diff
				  FROM ria r, labs l
				WHERE r.CURR_NYEHMS_STATENO = l.stateno (+)
				)
				select stateno, document_id, lab_doc_id, TO_CHAR(TO_DATE(initial_collection_date),'MM/DD/YYYY')  as initial_collection_date, DOC_TYPE,
				case when diff <= 7 then 'Y' else 'N' end as lab7days,
				case when diff <= 14 then 'Y' else 'N' end as lab14days,
				case when diff <= 21 then 'Y' else 'N' end as lab21days,
				case when diff <= 30 then 'Y' else 'N' end as lab30days,
				case when diff > 30 then 'Y' else 'N' end as labover30days
				from data
				""", Rows.json(), dateRange(inputParameterMap));
	}

	/** riaFlaggedFromDate, riaFlaggedToDate as bound text. */
	private static Object[] dateRange(Map<String, ?> inputParameterMap) {
		return new Object[] {NativeStatements.text(inputParameterMap.get("riaFlaggedFromDate")), NativeStatements.text(inputParameterMap.get("riaFlaggedToDate"))};
	}

	/** Numbers the plain {@code ?} placeholders of a native statement ?1, ?2, ... in order. */
	private static String numbered(String sql) {
		StringBuilder text = new StringBuilder();
		int n = 0;
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			text.append(c);
			if (c == '?' && (i + 1 >= sql.length() || !Character.isDigit(sql.charAt(i + 1)))) {
				text.append(++n);
			} else if (c == '?') {
				// an already numbered placeholder: renumber it in order
				int j = i + 1;
				while (j < sql.length() && Character.isDigit(sql.charAt(j))) {
					j++;
				}
				text.append(++n);
				i = j - 1;
			}
		}
		return text.toString();
	}

	// ---------------------------------------------------------------- the RIA document

	/** The RIA document (dates as MM/DD/YYYY), or null. */
	public RiaDocVO getRiaDoc(int docId) {
		return jpql.one("select " + DOC_COLUMNS + " from NyehmsRiaDoc r where r.documentId = ?1", Rows.bean(RiaDocVO.class), (long) docId);
	}

	/** The RIA document as ALL_RIA_VW shows it (the same columns as {@link #getRiaDoc}), as a map; or null. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, Object> getNyehmsDataforRiaDoc(int docId) {
		return (Map) jpql.one("select " + DOC_COLUMNS + " from AllRiaVw r where r.documentId = ?1", Rows.map(), BigDecimal.valueOf(docId));
	}

	/** The drop-down codes of the RIA document fields: REF_COLUMN, LOOKUP_CD, LOOKUP_NAME. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaCodedDropDownData() {
		return (List) jpql.list("""
				select c.refColumn as REF_COLUMN, c.lookupCd as LOOKUP_CD, c.lookupName as LOOKUP_NAME from CodedRiaDoc c
				order by c.refColumn, c.sortOrder asc""", Rows.map());
	}

	/** The interventions of each follow-up code: FOLLOW_UP_CD, INTERVENTION_CD, INTERVENTION_DESC. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaFollowUpDropDownData() {
		return (List) jpql.list("""
				select m.followUpCd as FOLLOW_UP_CD, i.interventionCd as INTERVENTION_CD, i.interventionDesc as INTERVENTION_DESC
				from CodedRiaInterventionCodes i, FollowUpInterventionMap m where i.interventionCd = m.interventionCd
				order by m.followUpCd asc, m.sortOrder asc""", Rows.map());
	}

	/**
	 * Every follow-up code with the document's follow-ups of it and their interventions, grouped as the former nested
	 * result map grouped them: one follow-up per distinct set of follow-up values (in first-appearance order), its
	 * interventions in order, one per INTERVENTION_CD (an intervention without code is added each time it appears, one
	 * without any value not at all); the list of interventions is empty, not null, when there is none.
	 */
	public List<RiaFollowupDocVO> getRiaFollowUpData(int docId) {
		List<Row> rows = jpql.list("""
				select f.riaFollowUpId as RIA_FOLLOW_UP_ID, c.programType as PROGRAM_TYPE, c.followUpCd as FOLLOW_UP_CD, c.followUpDesc as FOLLOW_UP_DESC,
				f.followupOtherText as FOLLOWUP_OTHER_TEXT, i.interventionCd as INTERVENTION_CD, format(i.interventionDate as 'MM/dd/yyyy') as INTERVENTION_DATE,
				i.interventionOtherText as INTERVENTION_OTHER_TEXT
				from CodedRiaFollowUpCodes c
				left join RiaFollowUp f on c.followUpCd = f.followUpCd and f.riaDocId = ?1
				left join RiaIntervention i on f.riaFollowUpId = i.riaFollowUpId
				order by c.programType, c.sortOrder asc""", row -> row, (long) docId);
		Map<List<Object>, RiaFollowupDocVO> followUps = new LinkedHashMap<>();
		Map<List<Object>, Set<String>> interventionCodes = new LinkedHashMap<>();
		for (Row row : rows) {
			List<Object> key = Arrays.asList(row.get("FOLLOW_UP_CD"), row.get("RIA_FOLLOW_UP_ID"), row.get("FOLLOWUP_OTHER_TEXT"),
					row.get("PROGRAM_TYPE"), row.get("FOLLOW_UP_DESC"));
			RiaFollowupDocVO followUp = followUps.get(key);
			if (followUp == null) {
				followUp = new RiaFollowupDocVO();
				if (row.get("FOLLOW_UP_CD") != null) followUp.setFOLLOW_UP_CD((String) row.get("FOLLOW_UP_CD"));
				if (row.get("RIA_FOLLOW_UP_ID") != null) followUp.setRIA_FOLLOW_UP_ID(((Number) row.get("RIA_FOLLOW_UP_ID")).intValue());
				if (row.get("FOLLOWUP_OTHER_TEXT") != null) followUp.setFOLLOWUP_OTHER_TEXT((String) row.get("FOLLOWUP_OTHER_TEXT"));
				if (row.get("PROGRAM_TYPE") != null) followUp.setPROGRAM_TYPE((String) row.get("PROGRAM_TYPE"));
				if (row.get("FOLLOW_UP_DESC") != null) followUp.setFOLLOW_UP_DESC((String) row.get("FOLLOW_UP_DESC"));
				followUp.setInterventionDocList(new ArrayList<>());
				followUps.put(key, followUp);
				interventionCodes.put(key, new java.util.HashSet<>());
			}
			String code = (String) row.get("INTERVENTION_CD");
			Object date = row.get("INTERVENTION_DATE");
			Object otherText = row.get("INTERVENTION_OTHER_TEXT");
			if (code == null && date == null && otherText == null) {
				continue;                                           // no intervention values: none added
			}
			if (code != null && !interventionCodes.get(key).add(code)) {
				continue;                                           // this intervention is already in the list
			}
			RiaInterventionDocVO intervention = new RiaInterventionDocVO();
			if (code != null) intervention.setINTERVENTION_CD(code);
			if (date != null) intervention.setINTERVENTION_DATE((String) date);
			if (otherText != null) intervention.setINTERVENTION_OTHER_TEXT((String) otherText);
			followUp.getInterventionDocList().add(intervention);
		}
		return new ArrayList<>(followUps.values());
	}

	/** The RIA field columns whose property is S. */
	public List<String> getRiaFieldProperties() {
		return jpql.list("select p.refColumn from RiaFieldProperty p where p.property = 'S'", Rows.scalar(String.class));
	}

	/** The RIA field properties: REF_COLUMN, PROPERTY, COLUMN_TYPE. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaFieldColumnProperties() {
		return (List) jpql.list("select p.refColumn as REF_COLUMN, p.property as PROPERTY, p.columnType as COLUMN_TYPE from RiaFieldProperty p", Rows.map());
	}

	/**
	 * Completes an open RIA document with the form's values (texts trimmed and upper-cased, dates fn_to_Date'd by the
	 * database, LAST_UPDATED_DATE and COMPLETE_DATE now).
	 *
	 * @return the number of documents completed (0 if it was already complete)
	 */
	public int closeRiaDoc(RiaDocVO vo) {
		return jpql.update("""
				update NyehmsRiaDoc r set
				r.ssn = trim(upper(?1)),
				r.bhaeNotes = trim(upper(?2)),
				r.psNotes = trim(upper(?3)),
				r.omdNotes = trim(upper(?4)),
				r.personCity = trim(upper(?5)),
				r.nysDiseaseCategDx = trim(upper(?6)),
				r.nycDiseaseCategDx = trim(upper(?7)),
				r.personZip = trim(upper(?8)),
				r.initialProvZip = trim(upper(?9)),
				r.recentVlUnits = trim(upper(?10)),
				r.recentVlProvZip = trim(upper(?11)),
				r.recentCd4Units = trim(upper(?12)),
				r.firstName = trim(upper(?13)),
				r.lastName = trim(upper(?14)),
				r.initialProvCity = trim(upper(?15)),
				r.recentVlProvCity = trim(upper(?16)),
				r.birthCountry = trim(upper(?17)),
				r.ethnicity = trim(upper(?18)),
				r.personCounty = trim(upper(?19)),
				r.initialProvCounty = trim(upper(?20)),
				r.initialFacilityCounty = trim(upper(?21)),
				r.birthSex = trim(upper(?22)),
				r.currentGender = trim(upper(?23)),
				r.personState = trim(upper(?24)),
				r.initialProvState = trim(upper(?25)),
				r.recentVlProvState = trim(upper(?26)),
				r.personAddress = trim(upper(?27)),
				r.initialProvname = trim(upper(?28)),
				r.initialProvAddress = trim(upper(?29)),
				r.recentVlProvname = trim(upper(?30)),
				r.recentVlProvAddress = trim(upper(?31)),
				r.airsFacility = trim(upper(?32)),
				r.airsTcId = trim(upper(?33)),
				r.psClosureDispo = trim(upper(?34)),
				r.riaEligibilityUid = trim(upper(?35)),
				r.dobText = trim(upper(?36)),
				r.dodText = trim(upper(?37)),
				r.recentVlValue = trim(upper(?38)),
				r.firstVlUnits = trim(upper(?39)),
				r.firstVlValue = trim(upper(?40)),
				r.recentCd4Value = trim(upper(?41)),
				r.nysEharsHivDxDt = trim(upper(?42)),
				r.nycEharsHivDxDt = trim(upper(?43)),
				r.calcEharsHivDxDt = trim(upper(?44)),
				r.nysLastNegDt = trim(upper(?45)),
				r.lipDateText = trim(upper(?46)),
				r.candlungDateText = trim(upper(?47)),
				r.candeDateText = trim(upper(?48)),
				r.cervcancDateText = trim(upper(?49)),
				r.cocciDateText = trim(upper(?50)),
				r.cryptocoDateText = trim(upper(?51)),
				r.cryptospDateText = trim(upper(?52)),
				r.cmvDateText = trim(upper(?53)),
				r.cmvretDateText = trim(upper(?54)),
				r.hivncephDateText = trim(upper(?55)),
				r.herpesDateText = trim(upper(?56)),
				r.histoDateText = trim(upper(?57)),
				r.isoDateText = trim(upper(?58)),
				r.ksDateText = trim(upper(?59)),
				r.burklDateText = trim(upper(?60)),
				r.lymphimDateText = trim(upper(?61)),
				r.lymphbDateText = trim(upper(?62)),
				r.mycoavDateText = trim(upper(?63)),
				r.mycopulmDateText = trim(upper(?64)),
				r.mycodismDateText = trim(upper(?65)),
				r.mycothDateText = trim(upper(?66)),
				r.pcpDateText = trim(upper(?67)),
				r.pneumDateText = trim(upper(?68)),
				r.treatmentFacTypeOtherText = trim(upper(?69)),
				r.prepNonAdherRsnOtherText = trim(upper(?70)),
				r.pmlDateText = trim(upper(?71)),
				r.salmnelaDateText = trim(upper(?72)),
				r.toxoDateText = trim(upper(?73)),
				r.wastingDateText = trim(upper(?74)),
				r.arvBeginDate = trim(upper(?75)),
				r.disnumber = trim(upper(?76)),
				r.psWorkerAssigned = trim(upper(?77)),
				r.lastUpdatedBy = trim(upper(?78)),
				r.riaEligible = trim(upper(?79)),
				r.raceAsian = trim(upper(?80)),
				r.raceBlack = trim(upper(?81)),
				r.raceIndian = trim(upper(?82)),
				r.racePacific = trim(upper(?83)),
				r.raceWhite = trim(upper(?84)),
				r.eharsElig = trim(upper(?85)),
				r.ahiFlag = trim(upper(?86)),
				r.airsDoc = trim(upper(?87)),
				r.recordSearch = trim(upper(?88)),
				r.ridrInfo = trim(upper(?89)),
				r.arvsTaken = trim(upper(?90)),
				r.prepTaken = trim(upper(?91)),
				r.noPreviousTest = trim(upper(?92)),
				r.pkpFlag = trim(upper(?93)),
				r.ptOnPrepFlag = trim(upper(?94)),
				r.nonPromptFlag = trim(upper(?95)),
				r.deceased = trim(upper(?96)),
				r.ndvlAtDx = trim(upper(?97)),
				r.perinatal = trim(upper(?98)),
				r.federalFacility = trim(upper(?99)),
				r.notHivFlag = trim(upper(?100)),
				r.oojCaseFlag = trim(upper(?101)),
				r.oojProviderFlag = trim(upper(?102)),
				r.deathSource = trim(upper(?103)),
				r.deathSourceOtherText = trim(upper(?104)),
				r.rapProvider = trim(upper(?105)),
				r.rapProviderFname = trim(upper(?106)),
				r.rapProviderMname = trim(upper(?107)),
				r.rapProviderLname = trim(upper(?108)),
				r.tthRapProvider = trim(upper(?109)),
				r.tthRapProviderFname = trim(upper(?110)),
				r.tthRapProviderMname = trim(upper(?111)),
				r.tthRapProviderLname = trim(upper(?112)),
				r.cdesscasekey = trim(upper(?113)),
				r.substanceUse = trim(upper(?114)),
				r.transportationBarriers = trim(upper(?115)),
				r.treatmentSourceOtherText = trim(upper(?116)),
				r.diagnosisSettingOtherText = trim(upper(?117)),
				r.diagnosisSettingType = trim(upper(?118)),
				r.diagnosisFacility = trim(upper(?119)),
				r.arvMedication = trim(upper(?120)),
				r.treatmentSource = trim(upper(?121)),
				r.labDocId = trim(upper(?122)),
				r.insuranceBarrier = trim(upper(?123)),
				r.languageBarrier = trim(upper(?124)),
				r.mentalHealthBarrier = trim(upper(?125)),
				r.barriersToCareOtherText = trim(upper(?126)),
				r.adverseEffectsToMeds = trim(upper(?127)),
				r.treatmentFacilityName = trim(upper(?128)),
				r.treatmentFacilityType = trim(upper(?129)),
				r.caseClosureCd = trim(upper(?130)),
				r.foodInsecurity = trim(upper(?131)),
				r.healthLiteracy = trim(upper(?132)),
				r.housingInstability = trim(upper(?133)),
				r.incarceration = trim(upper(?134)),
				r.pkpDxCountry = trim(upper(?135)),
				r.pkpDxDate = trim(upper(?136)),
				r.pkpDxState = trim(upper(?137)),
				r.recvCareOos = trim(upper(?138)),
				r.recvCareStateCd = trim(upper(?139)),
				r.oosFacilityName = trim(upper(?140)),
				r.prepAdherence = trim(upper(?141)),
				r.prepNonAdherenceReason = trim(upper(?142)),
				r.soundexCheckDate = function('FN_TO_DATE' as Timestamp, ?143, 'MM/DD/YYYY'),
				r.riaFlaggedDate = function('FN_TO_DATE' as Timestamp, ?144, 'MM/DD/YYYY'),
				r.riaEligibilityDate = function('FN_TO_DATE' as Timestamp, ?145, 'MM/DD/YYYY'),
				r.addressDate = function('FN_TO_DATE' as Timestamp, ?146, 'MM/DD/YYYY'),
				r.initialCollectionDate = function('FN_TO_DATE' as Timestamp, ?147, 'MM/DD/YYYY'),
				r.recentVlDate = function('FN_TO_DATE' as Timestamp, ?148, 'MM/DD/YYYY'),
				r.recentCd4Date = function('FN_TO_DATE' as Timestamp, ?149, 'MM/DD/YYYY'),
				r.firstGenotypeDate = function('FN_TO_DATE' as Timestamp, ?150, 'MM/DD/YYYY'),
				r.firstPrfDate = function('FN_TO_DATE' as Timestamp, ?151, 'MM/DD/YYYY'),
				r.firstVlDate = function('FN_TO_DATE' as Timestamp, ?152, 'MM/DD/YYYY'),
				r.psMessageSentDate = function('FN_TO_DATE' as Timestamp, ?153, 'MM/DD/YYYY'),
				r.psClosureDate = function('FN_TO_DATE' as Timestamp, ?154, 'MM/DD/YYYY'),
				r.psLastUpdateDate = function('FN_TO_DATE' as Timestamp, ?155, 'MM/DD/YYYY'),
				r.initialAppointmentDate = function('FN_TO_DATE' as Timestamp, ?156, 'MM/DD/YYYY'),
				r.movedToNyDate = function('FN_TO_DATE' as Timestamp, ?157, 'MM/DD/YYYY'),
				r.movedFromNyDate = function('FN_TO_DATE' as Timestamp, ?158, 'MM/DD/YYYY'),
				r.lastUpdatedDate = sysdate,
				r.completeDate = sysdate
				where r.documentId = ?159 and r.completeDate is null
				""",
				text(vo.getSSN()),
				text(vo.getBHAE_NOTES()),
				text(vo.getPS_NOTES()),
				text(vo.getOMD_NOTES()),
				text(vo.getPERSON_CITY()),
				text(vo.getNYS_DISEASE_CATEG_DX()),
				text(vo.getNYC_DISEASE_CATEG_DX()),
				text(vo.getPERSON_ZIP()),
				text(vo.getINITIAL_PROV_ZIP()),
				text(vo.getRECENT_VL_UNITS()),
				text(vo.getRECENT_VL_PROV_ZIP()),
				text(vo.getRECENT_CD4_UNITS()),
				text(vo.getFIRST_NAME()),
				text(vo.getLAST_NAME()),
				text(vo.getINITIAL_PROV_CITY()),
				text(vo.getRECENT_VL_PROV_CITY()),
				text(vo.getBIRTH_COUNTRY()),
				text(vo.getETHNICITY()),
				text(vo.getPERSON_COUNTY()),
				text(vo.getINITIAL_PROV_COUNTY()),
				text(vo.getINITIAL_FACILITY_COUNTY()),
				text(vo.getBIRTH_SEX()),
				text(vo.getCURRENT_GENDER()),
				text(vo.getPERSON_STATE()),
				text(vo.getINITIAL_PROV_STATE()),
				text(vo.getRECENT_VL_PROV_STATE()),
				text(vo.getPERSON_ADDRESS()),
				text(vo.getINITIAL_PROVNAME()),
				text(vo.getINITIAL_PROV_ADDRESS()),
				text(vo.getRECENT_VL_PROVNAME()),
				text(vo.getRECENT_VL_PROV_ADDRESS()),
				text(vo.getAIRS_FACILITY()),
				text(vo.getAIRS_TC_ID()),
				text(vo.getPS_CLOSURE_DISPO()),
				text(vo.getRIA_ELIGIBILITY_UID()),
				text(vo.getDOB_TEXT()),
				text(vo.getDOD_TEXT()),
				text(vo.getRECENT_VL_VALUE()),
				text(vo.getFIRST_VL_UNITS()),
				text(vo.getFIRST_VL_VALUE()),
				text(vo.getRECENT_CD4_VALUE()),
				text(vo.getNYS_EHARS_HIV_DX_DT()),
				text(vo.getNYC_EHARS_HIV_DX_DT()),
				text(vo.getCALC_EHARS_HIV_DX_DT()),
				text(vo.getNYS_LAST_NEG_DT()),
				text(vo.getLIP_DATE_TEXT()),
				text(vo.getCANDLUNG_DATE_TEXT()),
				text(vo.getCANDE_DATE_TEXT()),
				text(vo.getCERVCANC_DATE_TEXT()),
				text(vo.getCOCCI_DATE_TEXT()),
				text(vo.getCRYPTOCO_DATE_TEXT()),
				text(vo.getCRYPTOSP_DATE_TEXT()),
				text(vo.getCMV_DATE_TEXT()),
				text(vo.getCMVRET_DATE_TEXT()),
				text(vo.getHIVNCEPH_DATE_TEXT()),
				text(vo.getHERPES_DATE_TEXT()),
				text(vo.getHISTO_DATE_TEXT()),
				text(vo.getISO_DATE_TEXT()),
				text(vo.getKS_DATE_TEXT()),
				text(vo.getBURKL_DATE_TEXT()),
				text(vo.getLYMPHIM_DATE_TEXT()),
				text(vo.getLYMPHB_DATE_TEXT()),
				text(vo.getMYCOAV_DATE_TEXT()),
				text(vo.getMYCOPULM_DATE_TEXT()),
				text(vo.getMYCODISM_DATE_TEXT()),
				text(vo.getMYCOTH_DATE_TEXT()),
				text(vo.getPCP_DATE_TEXT()),
				text(vo.getPNEUM_DATE_TEXT()),
				text(vo.getTREATMENT_FAC_TYPE_OTHER_TEXT()),
				text(vo.getPREP_NON_ADHER_RSN_OTHER_TEXT()),
				text(vo.getPML_DATE_TEXT()),
				text(vo.getSALMNELA_DATE_TEXT()),
				text(vo.getTOXO_DATE_TEXT()),
				text(vo.getWASTING_DATE_TEXT()),
				text(vo.getARV_BEGIN_DATE()),
				text(vo.getDISNUMBER()),
				text(vo.getPS_WORKER_ASSIGNED()),
				text(vo.getLAST_UPDATED_BY()),
				text(vo.getRIA_ELIGIBLE()),
				text(vo.getRACE_ASIAN()),
				text(vo.getRACE_BLACK()),
				text(vo.getRACE_INDIAN()),
				text(vo.getRACE_PACIFIC()),
				text(vo.getRACE_WHITE()),
				text(vo.getEHARS_ELIG()),
				text(vo.getAHI_FLAG()),
				text(vo.getAIRS_DOC()),
				text(vo.getRECORD_SEARCH()),
				text(vo.getRIDR_INFO()),
				text(vo.getARVS_TAKEN()),
				text(vo.getPREP_TAKEN()),
				text(vo.getNO_PREVIOUS_TEST()),
				text(vo.getPKP_FLAG()),
				text(vo.getPT_ON_PREP_FLAG()),
				text(vo.getNON_PROMPT_FLAG()),
				text(vo.getDECEASED()),
				text(vo.getNDVL_AT_DX()),
				text(vo.getPERINATAL()),
				text(vo.getFEDERAL_FACILITY()),
				text(vo.getNOT_HIV_FLAG()),
				text(vo.getOOJ_CASE_FLAG()),
				text(vo.getOOJ_PROVIDER_FLAG()),
				text(vo.getDEATH_SOURCE()),
				text(vo.getDEATH_SOURCE_OTHER_TEXT()),
				text(vo.getRAP_PROVIDER()),
				text(vo.getRAP_PROVIDER_FNAME()),
				text(vo.getRAP_PROVIDER_MNAME()),
				text(vo.getRAP_PROVIDER_LNAME()),
				text(vo.getTTH_RAP_PROVIDER()),
				text(vo.getTTH_RAP_PROVIDER_FNAME()),
				text(vo.getTTH_RAP_PROVIDER_MNAME()),
				text(vo.getTTH_RAP_PROVIDER_LNAME()),
				text(vo.getCDESSCASEKEY()),
				text(vo.getSUBSTANCE_USE()),
				text(vo.getTRANSPORTATION_BARRIERS()),
				text(vo.getTREATMENT_SOURCE_OTHER_TEXT()),
				text(vo.getDIAGNOSIS_SETTING_OTHER_TEXT()),
				text(vo.getDIAGNOSIS_SETTING_TYPE()),
				text(vo.getDIAGNOSIS_FACILITY()),
				text(vo.getARV_MEDICATION()),
				text(vo.getTREATMENT_SOURCE()),
				text(vo.getLAB_DOC_ID()),
				text(vo.getINSURANCE_BARRIER()),
				text(vo.getLANGUAGE_BARRIER()),
				text(vo.getMENTAL_HEALTH_BARRIER()),
				text(vo.getBARRIERS_TO_CARE_OTHER_TEXT()),
				text(vo.getADVERSE_EFFECTS_TO_MEDS()),
				text(vo.getTREATMENT_FACILITY_NAME()),
				text(vo.getTREATMENT_FACILITY_TYPE()),
				text(vo.getCASE_CLOSURE_CD()),
				text(vo.getFOOD_INSECURITY()),
				text(vo.getHEALTH_LITERACY()),
				text(vo.getHOUSING_INSTABILITY()),
				text(vo.getINCARCERATION()),
				text(vo.getPKP_DX_COUNTRY()),
				text(vo.getPKP_DX_DATE()),
				text(vo.getPKP_DX_STATE()),
				text(vo.getRECV_CARE_OOS()),
				text(vo.getRECV_CARE_STATE_CD()),
				text(vo.getOOS_FACILITY_NAME()),
				text(vo.getPREP_ADHERENCE()),
				text(vo.getPREP_NON_ADHERENCE_REASON()),
				text(vo.getSOUNDEX_CHECK_DATE()),
				text(vo.getRIA_FLAGGED_DATE()),
				text(vo.getRIA_ELIGIBILITY_DATE()),
				text(vo.getADDRESS_DATE()),
				text(vo.getINITIAL_COLLECTION_DATE()),
				text(vo.getRECENT_VL_DATE()),
				text(vo.getRECENT_CD4_DATE()),
				text(vo.getFIRST_GENOTYPE_DATE()),
				text(vo.getFIRST_PRF_DATE()),
				text(vo.getFIRST_VL_DATE()),
				text(vo.getPS_MESSAGE_SENT_DATE()),
				text(vo.getPS_CLOSURE_DATE()),
				text(vo.getPS_LAST_UPDATE_DATE()),
				text(vo.getINITIAL_APPOINTMENT_DATE()),
				text(vo.getMOVED_TO_NY_DATE()),
				text(vo.getMOVED_FROM_NY_DATE()),
				longValue(vo.getDOCUMENT_ID()));
	}

	/** Native: runs the UPDATE statement the caller built (updateQuery), as it is. */
	public int saveRiaDoc(Map<String, String> inputParameterMap) {
		return statements.update(inputParameterMap.get("updateQuery"));
	}

	// ---------------------------------------------------------------- follow-ups and interventions

	/**
	 * Adds a follow-up to an RIA document (DOCUMENT_ID, FOLLOW_UP_CD, FOLLOW_UP_OTHER_TEXT, WORKER_ID); its new key
	 * (from RIA_FOLLOWUP_SEQ) is put into the map as RIA_FOLLOW_UP_ID, as the former generated key was.
	 */
	public void insertFollowUpData(Map<String, Object> inputParameterMap) {
		RiaFollowUp followUp = RiaFollowUp.builder()
				.riaDocId(longValue(inputParameterMap.get("DOCUMENT_ID")))
				.followUpCd(text(inputParameterMap.get("FOLLOW_UP_CD")))
				.followupOtherText(text(inputParameterMap.get("FOLLOW_UP_OTHER_TEXT")))
				.createUid(longValue(inputParameterMap.get("WORKER_ID")))
				.build();
		jpql.insert(followUp);
		inputParameterMap.put("RIA_FOLLOW_UP_ID", BigDecimal.valueOf(followUp.getRiaFollowUpId()));
	}

	/** There was no statement for this method; it fails, as the former mapper method failed. */
	public String getNextFollowUpId() {
		throw new InvalidDataAccessApiUsageException("No statement getNextFollowUpId (it had no SQL in the former mapper)");
	}

	/**
	 * Adds interventions (interventionsList: maps with CODE, DATE (MM/DD/YYYY), OTHER_TEXT) to the follow-up
	 * RIA_FOLLOW_UP_ID: WORKER_ID; one statement, as before.
	 */
	public void insertInterventions(Map<String, Object> interventionMap) {
		List<?> interventions = rowList(interventionMap.get("interventionsList"), "interventionsList");
		StringBuilder hql = new StringBuilder("""
				insert into RiaIntervention (riaFollowUpId, interventionCd, interventionUid, interventionDate, interventionOtherText) values """);
		List<Object> parameters = new ArrayList<>();
		for (Object element : interventions) {
			Map<?, ?> intervention = (Map<?, ?>) element;
			int n = parameters.size();
			hql.append(n == 0 ? "" : ", ").append("(?").append(n + 1).append(", ?").append(n + 2).append(", ?").append(n + 3)
					.append(", function('TO_DATE' as Timestamp, ?").append(n + 4).append(", 'MM/DD/YYYY'), ?").append(n + 5).append(')');
			parameters.add(longValue(interventionMap.get("RIA_FOLLOW_UP_ID")));
			parameters.add(text(intervention.get("CODE")));
			parameters.add(longValue(interventionMap.get("WORKER_ID")));
			parameters.add(text(intervention.get("DATE")));
			parameters.add(text(intervention.get("OTHER_TEXT")));
		}
		jpql.update(hql.toString(), parameters.toArray());
	}

	/** The intervention codes of interventionsList the follow-up RIA_FOLLOW_UP_ID already has. */
	public List<String> getExistingInterventions(Map<String, Object> interventionMap) {
		return jpql.list("select i.interventionCd from RiaIntervention i where i.riaFollowUpId = ?1 and i.interventionCd in ?2",
				Rows.scalar(String.class), longValue(interventionMap.get("RIA_FOLLOW_UP_ID")),
				inList(interventionMap.get("interventionsList"), "interventionsList"));
	}

	/** The follow-up calls to providers of an RIA document that are not ended, oldest first. */
	public List<RiaProviderFollowupVO> getRiaProviderFollowUpData(int docId) {
		return jpql.list("""
				select c.riaProviderFollowUpId as RIA_PROVIDER_FOLLOW_UP_ID, format(c.dateOfContact as 'MM/dd/yyyy') as DATE_OF_CONTACT,
				c.stateno as STATENO, c.contactReason as CONTACT_REASON, c.provider as PROVIDER, c.providerRole as PROVIDER_ROLE,
				c.facility as FACILITY, c.contactPerson as CONTACT_PERSON, c.riaMaterialsProvided as RIA_MATERIALS_PROVIDED,
				c.createDateText as CREATE_DATE
				from RiaProviderFollowUpCall c where c.riaDocId = ?1 and c.endDate is null order by c.createDate asc""",
				Rows.bean(RiaProviderFollowupVO.class), (long) docId);
	}

	/** Adds a follow-up call to a provider (DATE_OF_CONTACT as MM/DD/YYYY, ..., RIA_DOC_ID, WORKER_ID). */
	public void insertProvFollowUpData(Map<String, Object> parameterMap) {
		jpql.update("""
				insert into RiaProviderFollowUpCall (dateOfContact, contactReason, provider, providerRole, facility, contactPerson,
				riaMaterialsProvided, createDate, riaDocId, createdBy)
				values (function('FN_TO_DATE' as Timestamp, ?1, 'MM/DD/YYYY'), ?2, ?3, ?4, ?5, ?6, ?7, sysdate, ?8, ?9)""",
				text(parameterMap.get("DATE_OF_CONTACT")), text(parameterMap.get("CONTACT_REASON")), text(parameterMap.get("PROVIDER")),
				text(parameterMap.get("PROVIDER_ROLE")), text(parameterMap.get("FACILITY")), text(parameterMap.get("CONTACT_PERSON")),
				text(parameterMap.get("RIA_MATERIALS_PROVIDED")), longValue(parameterMap.get("RIA_DOC_ID")), longValue(parameterMap.get("WORKER_ID")));
	}

	/** Changes a follow-up call to a provider (RIA_PROVIDER_FOLLOW_UP_ID). */
	public void updateProvFollowUpData(Map<String, Object> parameterMap) {
		jpql.update("""
				update RiaProviderFollowUpCall c set c.dateOfContact = function('FN_TO_DATE' as Timestamp, ?1, 'MM/DD/YYYY'), c.contactReason = ?2, c.provider = ?3,
				c.providerRole = ?4, c.facility = ?5, c.contactPerson = ?6, c.riaMaterialsProvided = ?7, c.updatedDate = sysdate,
				c.riaDocId = ?8, c.updatedBy = ?9
				where c.riaProviderFollowUpId = ?10""",
				text(parameterMap.get("DATE_OF_CONTACT")), text(parameterMap.get("CONTACT_REASON")), text(parameterMap.get("PROVIDER")),
				text(parameterMap.get("PROVIDER_ROLE")), text(parameterMap.get("FACILITY")), text(parameterMap.get("CONTACT_PERSON")),
				text(parameterMap.get("RIA_MATERIALS_PROVIDED")), longValue(parameterMap.get("RIA_DOC_ID")), longValue(parameterMap.get("WORKER_ID")),
				longValue(parameterMap.get("RIA_PROVIDER_FOLLOW_UP_ID")));
	}

	/** Ends a follow-up call to a provider (RIA_PROVIDER_FOLLOW_UP_ID): WORKER_ID. */
	public void deleteProvFollowUpData(Map<String, Object> parameterMap) {
		jpql.update("update RiaProviderFollowUpCall c set c.endDate = sysdate, c.updatedBy = ?1 where c.riaProviderFollowUpId = ?2",
				longValue(parameterMap.get("WORKER_ID")), longValue(parameterMap.get("RIA_PROVIDER_FOLLOW_UP_ID")));
	}

	// ---------------------------------------------------------------- activity

	/** Records an activity (ACTIVITY_TYPE) of a worker (WORKER_ID) on an RIA document (DOCUMENT_ID), now. */
	public void addRiaActivityType(Map<String, Object> inputParameterMap) {
		jpql.update("insert into RiaActivity (documentId, activityDesc, activityBy, activityDate) values (?1, ?2, ?3, sysdate)",
				longValue(inputParameterMap.get("DOCUMENT_ID")), text(inputParameterMap.get("ACTIVITY_TYPE")), longValue(inputParameterMap.get("WORKER_ID")));
	}

	/**
	 * The activities on an RIA document (DOCUMENT_ID) since the worker's (WORKER_ID) last one: ACTIVITY_DESC
	 * (VIEWED as Viewing), WORKER_NAME (last, first name); distinct.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaAcitity(Map<String, Integer> inputParameterMap) {
		return (List) jpql.list("""
				select distinct case when a.activityDesc = 'VIEWED' then 'Viewing' else a.activityDesc end as ACTIVITY_DESC,
				concat(u.lastName, ', ', u.firstName) as WORKER_NAME
				from RiaActivity a, NyehmsUsers u where u.workerId = a.activityBy and a.activityDate > (
				select max(l.activityDate) from RiaActivity l where l.activityBy = ?1 and l.documentId = ?2) and a.documentId = ?3""",
				Rows.map(), longValue(inputParameterMap.get("WORKER_ID")), longValue(inputParameterMap.get("DOCUMENT_ID")),
				longValue(inputParameterMap.get("DOCUMENT_ID")));
	}

	/** The activities on an RIA document, newest first: ACTIVITY_DESC, WORKER_NAME, ACTIVITY_DATE (MM/DD/YYYY HH:MI:SS AM). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRiaDocActivity(Integer dOCUMENT_ID) {
		return (List) jpql.list("""
				select a.activityDesc as ACTIVITY_DESC, concat(u.lastName, ', ', u.firstName) as WORKER_NAME,
				format(a.activityDate as 'MM/dd/yyyy hh:mm:ss a') as ACTIVITY_DATE
				from RiaActivity a, NyehmsUsers u where u.workerId = a.activityBy and a.documentId = ?1 order by a.activityDate desc""",
				Rows.map(), longValue(dOCUMENT_ID));
	}

	/** The number of open, unmerged RIA documents of stateno1 or stateno2. */
	public int statenosFlaggedForRia(Map<String, Object> inputParameterMap) {
		return jpql.one("""
				select count(*) from NyehmsRiaDoc r where r.currNyehmsStateno in (?1, ?2) and (r.completeDate is null and r.mergedRiaStateno is null)""",
				Rows.scalar(Integer.class), text(inputParameterMap.get("stateno1")), text(inputParameterMap.get("stateno2")));
	}

	// ---------------------------------------------------------------- helpers

	/** The values of an array or collection for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static List<Object> inList(Object values, String name) {
		List<Object> list = new ArrayList<>();
		if (values instanceof Object[] array) {
			list.addAll(Arrays.asList(array));
		} else if (values instanceof Collection<?> collection) {
			list.addAll(collection);
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No values for IN () in " + name + ": " + values);
		}
		return list;
	}

	/** The rows of a multi-row insert: an empty or missing list failed as an INSERT without rows. */
	private static List<?> rowList(Object rows, String name) {
		if (!(rows instanceof Collection<?> collection) || collection.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No rows to insert in " + name + ": " + rows);
		}
		return new ArrayList<>(collection);
	}

	/** (?n, ?n+1, ...) for the values, which are added to the parameters (as text, as MyBatis bound the strings). */
	private static String inPlaceholders(List<Object> values, List<Object> parameters) {
		StringBuilder text = new StringBuilder("(");
		for (Object value : values) {
			parameters.add(value);
			text.append(text.length() > 1 ? ", " : "").append('?').append(parameters.size());
		}
		return text.append(")").toString();
	}
}
