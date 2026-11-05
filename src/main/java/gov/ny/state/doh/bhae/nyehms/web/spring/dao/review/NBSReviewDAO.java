package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NBSDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdNbsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.CodedPerinatalReviewSql;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.RawNbsDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.review.StdClimsDocReview;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.StdNbsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.CodedHivFinalStatusRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.CodedPerinatalReviewSqlRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.RawNbsDocRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.review.StdClimsDocReviewRepository;
import lombok.RequiredArgsConstructor;

/**
 * The newborn screening (NBS) / CLIMS perinatal review: the review screens' records, the NBS document, the matches to
 * a STATENO or a CLIMS patient, the exposure status and the review comments (formerly a MyBatis mapper with the same
 * name and methods).
 * <p>
 * Through Spring Data repositories over the entities RawNbsDoc, StdNbsDoc, StdClimsDocReview, CodedPerinatalReviewSql
 * and CodedHivFinalStatus: reads by key, a search query built from the given filters, load-change-save for the
 * updates (with the former conditions checked on the loaded row), and the former TO_CHAR / CASE / concatenations in
 * Java. Native SQL (unchanged, values bound) where JPQL cannot express the statement: see each method.
 * <p>
 * The dates the former SQL set with SYSDATE are the application server's clock (to the second, as an Oracle DATE).
 * The former comment concatenations keep Oracle's rules: a null part counts as empty, and an empty result is null.
 * <p>
 * <b>Open security point (not changed here):</b> the screen SQL run by {@link #getPerinatalReviewRecords} and
 * {@link #getNBSYearListToReview} comes from CODED_PERINATAL_REVIEW_SQL and is completed by NBSReviewService, which
 * appends the request's birth year into the SQL text ({@code "... = '" + birthYear + "'"}); it should be bound or
 * checked against a list of years.
 */
@Repository
@RequiredArgsConstructor
public class NBSReviewDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	// native: the NBS record as received, read through the PL/SQL function fn_to_char and joined with LPAD to the
	// facility names and twice to the county reference by zip code (the joined tables are also read with select * and
	// their columns are not otherwise known); the record is put into the VO by its property types, as before
	private static final String IND_NBS_DOC = """
			SELECT document_id,
			       TRIM (labid) labid,
			       TRIM (labno) labno,
			       TRIM (link) link,
			       TRIM (medrecno) medrecno,
			       TRIM (fname) infant_fname,
			       TRIM (lname) infant_lname,
			       TRIM (aka) aka,
			       fn_to_char (TRIM (birthdt), 'MM/DD/YYYY') BIRTH_DATE,
			       TRIM (birthtm) BIRTH_TIME,
			       TRIM (birthwt) BIRTH_WEIGHT,
			       CASE WHEN TRIM (race) = '1' THEN 1 ELSE 0 END race_white,
			       CASE WHEN TRIM (race) = '2' THEN 1 ELSE 0 END race_black,
			       CASE WHEN TRIM (race) = '4' THEN 1 ELSE 0 END race_asian,
			       CASE WHEN TRIM (race) = '5' THEN 1 ELSE 0 END race_other,
			       CASE WHEN TRIM (race) = '6' THEN 1 ELSE 0 END race_indian,
			       CASE WHEN TRIM (race) = '3' THEN '01' ELSE NULL END ethnicity,
			       case when TRIM (sex) = '1' then 'M'
			       		when TRIM (sex) = '2' then 'F' else null end  sex,
			       TRIM (mfname) mother_fname,
			       TRIM (mlname) mother_lname,
			       TRIM (motherage) mother_age,
			       fn_to_char (TRIM (motherdob), 'MM/DD/YYYY') mother_dob,
			       TRIM (street) mother_address,
			       TRIM (r.city) mother_city,
			       TRIM (r.state) mother_state,
			       TRIM (r.zipcode) mother_zip,
			       n.county_fips_cd mother_county             -- Check if Lpad is required
			                                     ,
			       CASE
			          WHEN n.county_fips_cd IN ('005',
			                                    '047',
			                                    '061',
			                                    '081',
			                                    '085')
			          THEN
			             'NYC'
			          WHEN     TRIM (r.state) = 'NY'
			               AND n.county_fips_cd NOT IN ('005',
			                                            '047',
			                                            '061',
			                                            '081',
			                                            '085')
			          THEN
			             'NYS'
			          ELSE
			             'OOS'
			       END
			          MOTHER_REGION,
			       TRIM (phone) mother_phone,
			       fn_to_char (TRIM (dtcoll), 'MM/DD/YYYY') collection_date,
			       fn_to_char (TRIM (dtrecv), 'MM/DD/YYYY') date_received,
			       fn_to_char (TRIM (dtrptd), 'MM/DD/YYYY') date_reported,
			       TRIM (init_rpt) init_rpt,
			       TRIM (birthhosp) birthhosp,
			       TRIM (r.pfi) pfi,
			       TRIM (p.name) facility_name,
			       TRIM (p.address) facility_address,
			       TRIM (p.city) facility_city,
			       NULL facility_state,
			       TRIM (p.zipcode) facility_zip,
			       TRIM (p.county_cd) facility_county,
			       CASE
			          WHEN n1.county_fips_cd IN ('005',
			                                     '047',
			                                     '061',
			                                     '081',
			                                     '085')
			          THEN
			             'NYC'
			          WHEN     TRIM (p.state) = 'NY'
			               AND n1.county_fips_cd NOT IN ('005',
			                                             '047',
			                                             '061',
			                                             '081',
			                                             '085')
			          THEN
			             'NYS'
			          ELSE
			             'OOS'
			       END
			          facility_region,
			       TRIM (twin) twin_status,
			       CASE
			          WHEN TRIM (twin) IS NULL THEN NULL
			          WHEN TRIM (twin) IN ('0', '1') THEN 'Y'
			          ELSE 'NT'
			       END
			          trans_calc_keep,
			       clims_recid,
			       patientid,
			       memo,
			       clims_match_dt
			  FROM raw_nbs_doc r
			       LEFT OUTER JOIN coded_nbs_pfi_names p
			          ON (TRIM (r.pfi) = LPAD (p.pfi, 6, '0'))
			       LEFT OUTER JOIN nyehms_county_reference n
			          ON (    TRIM (r.zipcode) = n.zip_cd
			              AND TRIM (r.state) = n.state_cd
			              AND n.num_city = 1)
			       LEFT OUTER JOIN nyehms_county_reference n1
			          ON (    TRIM (p.zipcode) = n1.zip_cd
			              AND TRIM (p.state) = n1.state_cd
			              AND n1.num_city = 1)
			 WHERE   document_id = ?1
			""";

	// native: the current STATENO comes from the PL/SQL function PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO, applied to
	// every CLIMS row
	private static final String CLIMS_PATIENT_ID = """
			WITH clims AS
			(
			    SELECT Distinct pkg_consolidation.fn_give_current_stateno(matched_stateno) infant_stateno, patientId, colldate
			      FROM std_clims_doc
			     WHERE pkg_consolidation.fn_give_current_stateno(matched_stateno) = PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (?1) AND patientid IS NOT NULL
			), final_clims AS
			(
			    SELECT patientId, colldate, ROW_NUMBER() OVER (PARTITION BY infant_stateno ORDER BY colldate desc ) rn
			      FROM clims
			)
			SELECT patientId
			  FROM final_clims
			 WHERE rn = 1
			""";

	/** The columns of the former getNBSDoc, in its order (the VO is filled from them as MyBatis filled it). */
	private static final List<String> NBS_DOC_COLUMNS = List.of("DOCUMENT_ID", "NYS_ID", "LABID", "LABNO", "LINK", "MEDRECNO", "INFANT_FNAME",
			"INFANT_LNAME", "AKA", "BIRTH_DATE", "BIRTH_TIME", "BIRTH_WEIGHT", "RACE_WHITE", "RACE_BLACK", "RACE_ASIAN", "RACE_OTHER", "RACE_INDIAN",
			"ETHNICITY", "SEX", "MOTHER_FNAME", "MOTHER_LNAME", "MOTHER_AGE", "MOTHER_DOB", "MOTHER_ADDRESS", "MOTHER_CITY", "MOTHER_STATE",
			"MOTHER_ZIP", "MOTHER_COUNTY", "MOTHER_REGION", "MOTHER_PHONE", "MOTHER_LATITUDE", "MOTHER_LONGITUDE", "MOTHER_CENSUS_TRACT",
			"COLLECTION_DATE", "DATE_RECEIVED", "DATE_REPORTED", "INIT_RPT", "BIRTH_HOSPITAL_CODE", "PFI", "FACILITY_NAME", "FACILITY_ADDRESS",
			"FACILITY_CITY", "FACILITY_STATE", "FACILITY_ZIP", "FACILITY_COUNTY", "FACILITY_REGION", "FACILITY_LATITUDE", "FACILITY_LONGITUDE",
			"FACILITY_CENSUS_TRACT", "TWIN_STATUS", "HIV_FINAL_STATUS", "TRANS_CALC_KEEP", "CLIMS_RECID", "PATIENTID", "MEMO", "CLIMS_MATCH_UID",
			"CLIMS_MATCH_DT", "CURRENT_PROCESS_STATUS", "INFANT_STATENO", "MOTHER_STATENO", "POT_MOTHER_STATENO", "FACILITY_PHONE",
			"FACILITY_EHARS_UID");

	private static final NativeStatements.RowMapper<NBSDocVO> NBS_DOC = Rows.bean(NBSDocVO.class);

	private final NativeStatements statements;
	private final JpqlStatements jpql;
	private final RawNbsDocRepository rawNbsDocs;
	private final StdNbsDocRepository stdNbsDocs;
	private final StdClimsDocReviewRepository climsDocs;
	private final CodedPerinatalReviewSqlRepository screenSql;
	private final CodedHivFinalStatusRepository hivFinalStatuses;

	/**
	 * The records of a review screen (JSON rows with the screen SQL's column labels as keys).
	 * <p>
	 * Native: the SQL (SQL_QUERY) is the screen's SQL stored in CODED_PERINATAL_REVIEW_SQL, completed by the caller; it
	 * is run as given (see the security note of this class).
	 */
	public List<JSONObject> getPerinatalReviewRecords(Map<String, String> inputParameterMap) {
		return statements.list(inputParameterMap.get("SQL_QUERY"), Rows.json());
	}

	/** The NBS document (standardized), or null if there is none; its dates as MM/DD/YYYY. */
	public NBSDocVO getNBSDoc(int docId) {
		Optional<StdNbsDoc> doc = stdNbsDocs.findById((long) docId);
		return doc.map(NBSReviewDAO::toNbsDocVO).orElse(null);
	}

	/**
	 * Matches a standardized NBS document to an infant STATENO and a CLIMS patient id by manual review (MEMO 'MANUAL'),
	 * appending the review comments.
	 *
	 * @param inOutParameterMap p_documentId, stateno, climsPatientId, p_reviewComments, p_worker_id
	 * @return 1, or 0 if there is no such document
	 */
	@Transactional
	public int matchNBSDocumentToStateno(Map<String, Object> inOutParameterMap) {
		Map<String, Object> p = inOutParameterMap;
		Optional<StdNbsDoc> doc = findStd(p.get("p_documentId"));
		doc.ifPresent(d -> d.matchStateno(text(p.get("stateno")), text(p.get("climsPatientId")),
				concat(d.getReviewComments(), "     ", text(p.get("p_reviewComments"))), longValue(p.get("p_worker_id")), now()));
		return saved(doc, stdNbsDocs);
	}

	/** The NBS record as received (the individual exposure review), or null if there is none. */
	public NBSDocVO getIndNbsDoc(int docId) {
		return statements.one(IND_NBS_DOC, NBS_DOC, docId);
	}

	/**
	 * Saves the exposure review of an NBS record as received: its new status, the reviewer and the appended comments;
	 * with exposureStatus "Y" only if the record has been moved to the standardized table.
	 *
	 * @param inOutParameterMap p_documentId, exposureStatus, currentProcessStatus, comments, p_worker_id
	 * @return 1, or 0 if the record was not changed
	 */
	@Transactional
	public int updateNBSExposureStatus(Map<String, Object> inOutParameterMap) {
		Map<String, Object> p = inOutParameterMap;
		Long documentId = longValue(p.get("p_documentId"));
		Optional<RawNbsDoc> doc = documentId == null ? Optional.empty() : rawNbsDocs.findById(documentId);
		Object exposureStatus = p.get("exposureStatus");
		if (exposureStatus != null && "Y".equals(exposureStatus.toString()) && doc.isPresent() && !stdNbsDocs.existsById(documentId)) {
			doc = Optional.empty();
		}
		doc.ifPresent(d -> d.review(integer(p.get("currentProcessStatus")), longValue(p.get("p_worker_id")), now(),
				concat(d.getReviewComments(), " ", text(p.get("comments")))));
		return saved(doc, rawNbsDocs);
	}

	/**
	 * Moves an NBS record as received to the standardized table (PL/SQL procedure perinatal_process.nbs_move_raw_to_std);
	 * p_error_cd and p_err_msg are put back into the map.
	 */
	public void moveDocToStdNBS(Map<String, Object> inOutParameterMap) {
		Object documentId = inOutParameterMap.get("p_documentId");
		List<Object> results = statements.call("perinatal_process.nbs_move_raw_to_std",
				in(documentId, documentId == null ? String.class : documentId.getClass()), out(Integer.class), out(String.class));
		inOutParameterMap.put("p_error_cd", results.get(1));
		inOutParameterMap.put("p_err_msg", results.get(2));
	}

	/**
	 * The birth years (or other single values) a review screen offers.
	 * <p>
	 * Native: the SQL (SQL_QUERY) is the screen's SQL stored in CODED_PERINATAL_REVIEW_SQL, completed by the caller.
	 */
	public List<String> getNBSYearListToReview(Map<String, String> inputParameterMap) {
		return statements.list(inputParameterMap.get("SQL_QUERY"), Rows.scalar(String.class));
	}

	/**
	 * Sets the mother's STATENO of a standardized NBS document that has none yet, reviewed by the worker, appending
	 * the comments (trimmed).
	 *
	 * @param inputParameterMap DOCUMENT_ID, STATENO, p_comments, p_worker_id
	 * @return 1, or 0 if there is no such document or it has a mother's STATENO
	 */
	@Transactional
	public int updateNBSMotherStatenoDetails(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Optional<StdNbsDoc> doc = findStd(p.get("DOCUMENT_ID")).filter(d -> d.getMotherStateno() == null);
		doc.ifPresent(d -> d.motherStateno(text(p.get("STATENO")), longValue(p.get("p_worker_id")), now(),
				oracleTrim(concat(d.getReviewComments(), "     ", text(p.get("p_comments"))))));
		return saved(doc, stdNbsDocs);
	}

	/** The latest CLIMS patient id of the current STATENO of a STATENO (null if none). */
	public String getClimsPatientId(String stateno) {
		return statements.one(CLIMS_PATIENT_ID, Rows.scalar(String.class), NativeStatements.text(stateno));
	}

	/** The match review details of a standardized NBS document (DOC_TYPE 'NBS'), or null if there is none. */
	public Map<String, Object> getNBSDocumentDetailsForMatchReview(Integer documentId) {
		Optional<StdNbsDoc> doc = documentId == null ? Optional.empty() : stdNbsDocs.findById(documentId.longValue());
		return doc.map(d -> matchReviewDetails(d.getDocumentId(), "C", d.getPotMotherStateno(), "NBS")).orElse(null);
	}

	/** The HIV final statuses by description: STATUSCD, STATUSDESC. */
	public List<Map<String, String>> getHivFinalStatusList() {
		List<Map<String, String>> rows = new ArrayList<>();
		hivFinalStatuses.findAll(Sort.by("hivFinalStatusDesc")).forEach(status -> {
			Map<String, String> row = new HashMap<>();
			putIfSet(row, "STATUSCD", status.getHivFinalStatusCd());
			putIfSet(row, "STATUSDESC", status.getHivFinalStatusDesc());
			rows.add(row.isEmpty() ? null : row);
		});
		return rows;
	}

	/**
	 * Saves the reviewer's HIV final status of a standardized NBS document with the review comments (replacing them).
	 *
	 * @param inputParameterMap p_documentId, p_reviewComments, p_hivFinalStatus, p_workerId
	 * @return 1, or 0 if there is no such document
	 */
	@Transactional
	public int updateHivFinalStatus(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Optional<StdNbsDoc> doc = findStd(p.get("p_documentId"));
		doc.ifPresent(d -> {
			d.comment(longValue(p.get("p_workerId")), now(), emptyAsNull(p.get("p_reviewComments")));
			d.hivFinalStatusUser(emptyAsNull(p.get("p_hivFinalStatus")));
		});
		return saved(doc, stdNbsDocs);
	}

	/**
	 * Replaces the review comments of an NBS record as received.
	 *
	 * @param inputParameterMap p_documentId, p_reviewComments, p_workerId
	 * @return 1, or 0 if there is no such record
	 */
	@Transactional
	public int updateRawNBSData(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Long documentId = longValue(p.get("p_documentId"));
		Optional<RawNbsDoc> doc = documentId == null ? Optional.empty() : rawNbsDocs.findById(documentId);
		doc.ifPresent(d -> d.comment(longValue(p.get("p_workerId")), now(), emptyAsNull(p.get("p_reviewComments"))));
		return saved(doc, rawNbsDocs);
	}

	/** The match review details of a standardized CLIMS document (DOC_TYPE 'CLIMS'), or null if there is none. */
	public Map<String, Object> getClimsDocumentDetailsForMatchReview(Integer documentId) {
		Optional<StdClimsDocReview> doc = documentId == null ? Optional.empty() : climsDocs.findById(documentId);
		return doc.map(d -> matchReviewDetails(d.getDocumentId().longValue(), "N", null, "CLIMS")).orElse(null);
	}

	/**
	 * The NBS records as received without a CLIMS patient id that match the given filters (each one optional; LIKE
	 * patterns, compared in upper case where the former SQL did): DOCUMENT_ID, LABID, LABNO, FNAME, LNAME, MEDRECNO,
	 * DATE_OF_BIRTH (MM/DD/YYYY), GENDER ('Male' / 'Female'), WEIGHT, PFI, ZIPCODE.
	 */
	public List<Map<String, Object>> getNBSSearchViewResults(Map<String, String> inputParameterMap) {
		StringBuilder query = new StringBuilder("select r as doc from RawNbsDoc r where r.patientid is null");
		List<Object> values = new ArrayList<>();
		filter(query, values, inputParameterMap.get("p_labId"), "upper(r.labid) like trim(upper(?))");
		filter(query, values, inputParameterMap.get("p_firstName"), "upper(r.fname) like trim(upper(?))");
		filter(query, values, inputParameterMap.get("p_lastName"), "upper(r.lname) like trim(upper(?))");
		filter(query, values, inputParameterMap.get("p_labNo"), "upper(r.labno) like trim(upper(?))");
		filter(query, values, inputParameterMap.get("p_medRecordNumber"), "upper(r.medrecno) like trim(upper(?))");
		filter(query, values, inputParameterMap.get("p_dateOfBirth"), "format(r.birthdt as 'MM/dd/yyyy') like ?");
		filter(query, values, inputParameterMap.get("p_weight"), "r.birthwt = ?");
		filter(query, values, inputParameterMap.get("p_gender"), "r.sex = trim(both ' ' from ?)");
		filter(query, values, inputParameterMap.get("p_pfi"), "r.pfi like upper(?)");
		List<Map<String, Object>> rows = new ArrayList<>();
		for (RawNbsDoc doc : jpql.list(query.toString(), row -> (RawNbsDoc) row.get(0), values.toArray())) {
			Map<String, Object> row = new HashMap<>();
			putIfSet(row, "DOCUMENT_ID", doc.getDocumentId() == null ? null : BigDecimal.valueOf(doc.getDocumentId()));
			putIfSet(row, "LABID", doc.getLabid());
			putIfSet(row, "LABNO", doc.getLabno());
			putIfSet(row, "FNAME", doc.getFname());
			putIfSet(row, "LNAME", doc.getLname());
			putIfSet(row, "MEDRECNO", doc.getMedrecno());
			putIfSet(row, "DATE_OF_BIRTH", date(doc.getBirthdt()));
			putIfSet(row, "GENDER", "1".equals(doc.getSex()) ? "Male" : "2".equals(doc.getSex()) ? "Female" : null);
			putIfSet(row, "WEIGHT", doc.getBirthwt());
			putIfSet(row, "PFI", doc.getPfi());
			putIfSet(row, "ZIPCODE", doc.getZipcode());
			rows.add(row.isEmpty() ? null : row);
		}
		return rows;
	}

	/**
	 * Matches an NBS record as received without a CLIMS patient id to a CLIMS patient by manual review (status 16,
	 * MEMO 'MANUAL_REVIEW'), appending the review comments.
	 *
	 * @param inputParameterMap p_nbsDocumentId, p_patientId, p_reviewComments, p_workerId
	 * @return 1, or 0 if there is no such record or it has a patient id
	 */
	@Transactional
	public int matchClimsDocumentToNBS(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Long documentId = longValue(p.get("p_nbsDocumentId"));
		Optional<RawNbsDoc> doc = (documentId == null ? Optional.<RawNbsDoc> empty() : rawNbsDocs.findById(documentId))
				.filter(d -> d.getPatientid() == null);
		doc.ifPresent(d -> d.matchClims(text(p.get("p_patientId")), longValue(p.get("p_workerId")), now(),
				concat(d.getReviewComments(), "       ", text(p.get("p_reviewComments")))));
		return saved(doc, rawNbsDocs);
	}

	/**
	 * Replaces the review comments of a standardized CLIMS document.
	 *
	 * @param inputParameterMap p_documentId, p_reviewComments, p_workerId
	 * @return 1, or 0 if there is no such document
	 */
	@Transactional
	public int updateClimsReviewComments(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Long documentId = longValue(p.get("p_documentId"));
		Optional<StdClimsDocReview> doc = documentId == null ? Optional.empty() : climsDocs.findById(Math.toIntExact(documentId));
		doc.ifPresent(d -> d.comment(longValue(p.get("p_workerId")), now(), emptyAsNull(p.get("p_reviewComments"))));
		return saved(doc, climsDocs);
	}

	/**
	 * Replaces the review comments of a standardized NBS document.
	 *
	 * @param inputParameterMap p_documentId, p_reviewComments, p_workerId
	 * @return 1, or 0 if there is no such document
	 */
	@Transactional
	public int updateStdNbsReviewComments(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Optional<StdNbsDoc> doc = findStd(p.get("p_documentId"));
		doc.ifPresent(d -> d.comment(longValue(p.get("p_workerId")), now(), emptyAsNull(p.get("p_reviewComments"))));
		return saved(doc, stdNbsDocs);
	}

	/** The stored SQL of a review screen: SCREEN_SQL, ORDERBY (null if there is no such screen). */
	public Map<String, String> getPerinatalReviewQuery(String screenName) {
		Optional<CodedPerinatalReviewSql> sql = screenName == null ? Optional.empty() : screenSql.findById(screenName);
		return sql.map(s -> {
			Map<String, String> row = new HashMap<>();
			putIfSet(row, "SCREEN_SQL", s.getScreenSql());
			putIfSet(row, "ORDERBY", s.getOrderby());
			return row.isEmpty() ? null : row;
		}).orElse(null);
	}

	/**
	 * Matches the standardized NBS document without a CLIMS patient id to a CLIMS patient by manual review (MEMO
	 * 'MANUAL_REVIEW'), appending the review comments; the same values as {@link #matchClimsDocumentToNBS}.
	 */
	@Transactional
	public void updateClimsDocumentToStdNBS(Map<String, Object> inputParameterMap) {
		Map<String, Object> p = inputParameterMap;
		Optional<StdNbsDoc> doc = findStd(p.get("p_nbsDocumentId")).filter(d -> d.getPatientid() == null);
		doc.ifPresent(d -> d.matchClims(text(p.get("p_patientId")), longValue(p.get("p_workerId")), now(),
				concat(d.getReviewComments(), "       ", text(p.get("p_reviewComments")))));
		saved(doc, stdNbsDocs);
	}

	/**
	 * The birth years (YYYY, latest first) of the standardized NBS documents from 2021 on still to match to CLIMS.
	 * (No caller uses it: NBSReviewService.getClimsNonMatchBirthYearList runs the screen SQL 'CLIMS_MATCH_YR'.)
	 */
	public List<String> getClimsNonMatchBirthYearList() {
		return new ArrayList<>(stdNbsDocs.findClimsNonMatchBirthYears());
	}

	private Optional<StdNbsDoc> findStd(Object documentId) {
		Long id = longValue(documentId);
		return id == null ? Optional.empty() : stdNbsDocs.findById(id);
	}

	/** Writes the changed row at once (as the former update was executed at once): 1 if there was a row, else 0. */
	private static <T> int saved(Optional<T> row, org.springframework.data.jpa.repository.JpaRepository<T, ?> repository) {
		row.ifPresent(repository::saveAndFlush);
		return row.isPresent() ? 1 : 0;
	}

	/** The former match review row: DOCUMENT_ID and CLUSTER_ID the document id (a NUMBER), FUZZY_CLUSTER_ID null. */
	private static Map<String, Object> matchReviewDetails(Long documentId, String matchCd, String potStateno, String docType) {
		Map<String, Object> row = new HashMap<>();
		BigDecimal id = documentId == null ? null : BigDecimal.valueOf(documentId);
		putIfSet(row, "DOCUMENT_ID", id);
		row.put("MATCH_CD", matchCd);
		putIfSet(row, "CLUSTER_ID", id);
		putIfSet(row, "POT_STATENO", potStateno);
		row.put("DOC_TYPE", docType);
		return row;
	}

	/** The standardized NBS document as the former auto-mapping filled the VO from the getNBSDoc columns. */
	private static NBSDocVO toNbsDocVO(StdNbsDoc d) {
		List<Object> values = Arrays.asList(d.getDocumentId(), d.getNysId(), d.getLabid(), d.getLabno(), d.getLink(), d.getMedrecno(),
				d.getInfantFname(), d.getInfantLname(), d.getAka(), date(d.getBirthDate()), d.getBirthTime(), d.getBirthWeight(), d.getRaceWhite(),
				d.getRaceBlack(), d.getRaceAsian(), d.getRaceOther(), d.getRaceIndian(), d.getEthnicity(), d.getSex(), d.getMotherFname(),
				d.getMotherLname(), d.getMotherAge(), date(d.getMotherDob()), d.getMotherAddress(), d.getMotherCity(), d.getMotherState(),
				d.getMotherZip(), d.getMotherCounty(), d.getMotherRegion(), d.getMotherPhone(), d.getMotherLatitude(), d.getMotherLongitude(),
				d.getMotherCensusTract(), date(d.getCollectionDate()), date(d.getDateReceived()), date(d.getDateReported()), d.getInitRpt(),
				d.getBirthHospitalCode(), d.getPfi(), d.getFacilityName(), d.getFacilityAddress(), d.getFacilityCity(), d.getFacilityState(),
				d.getFacilityZip(), d.getFacilityCounty(), d.getFacilityRegion(), d.getFacilityLatitude(), d.getFacilityLongitude(),
				d.getFacilityCensusTract(), d.getTwinStatus(), d.getHivFinalStatus(), d.getTransCalcKeep(), d.getClimsRecid(), d.getPatientid(),
				d.getMemo(), d.getClimsMatchUid(), d.getClimsMatchDt(), d.getCurrentProcessStatus(), d.getInfantStateno(), d.getMotherStateno(),
				d.getPotMotherStateno(), d.getFacilityPhone(), d.getFacilityEharsUid());
		return NBS_DOC.map(new Row(NBS_DOC_COLUMNS, values));
	}

	/** Adds " and <condition>" with its value when the value is set (the former {@code x != null and x != ''}). */
	private static void filter(StringBuilder query, List<Object> values, String value, String condition) {
		if (value != null && !value.isEmpty()) {
			values.add(value);
			query.append(" and ").append(condition.replace("?", "?" + values.size()));
		}
	}

	/** Oracle's || : null parts count as empty, an empty result is null. */
	private static String concat(String... parts) {
		StringBuilder text = new StringBuilder();
		for (String part : parts) {
			if (part != null) {
				text.append(part);
			}
		}
		return text.length() == 0 ? null : text.toString();
	}

	/** Oracle's TRIM: leading and trailing spaces removed, an empty result is null. */
	private static String oracleTrim(String text) {
		if (text == null) {
			return null;
		}
		int start = 0;
		int end = text.length();
		while (start < end && text.charAt(start) == ' ') {
			start++;
		}
		while (end > start && text.charAt(end - 1) == ' ') {
			end--;
		}
		return start == end ? null : text.substring(start, end);
	}

	/** A text value as Oracle stored it ('' as NULL). */
	private static String emptyAsNull(Object value) {
		String text = text(value);
		return text == null || text.isEmpty() ? null : text;
	}

	private static Integer integer(Object value) {
		Long number = longValue(value);
		return number == null ? null : Math.toIntExact(number);
	}

	private static String date(Timestamp time) {
		return time == null ? null : DATE.format(time.toLocalDateTime());
	}

	private static <V> void putIfSet(Map<String, V> row, String key, V value) {
		if (value != null) {
			row.put(key, value);
		}
	}

	/** SYSDATE: the application server's clock, to the second. */
	private static Timestamp now() {
		return Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS));
	}
}
