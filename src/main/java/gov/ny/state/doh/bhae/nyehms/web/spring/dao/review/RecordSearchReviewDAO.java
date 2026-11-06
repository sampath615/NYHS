package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.review.RecordSearchResultsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The CDC record search: STATENOs flagged for a search, the files sent to the CDC and the files the reporting states
 * returned, and their resolution (formerly a MyBatis mapper with the same name and methods). JPQL over the entities
 * of {@code entity.review} (NYEHMS_FLAG_RECORD_SEARCH, NYEHMS_REC_SEARCH_SENT, NYEHMS_REC_SEARCH_RETURN) and
 * {@code entity.common} / {@code entity.document}; the former Oracle {@code (+)} joins are JPQL left joins, the
 * multi-row {@code INSERT ... SELECT ... FROM dual UNION ALL} inserts are multi-row HQL inserts (one statement, as
 * before), fn_to_date / TO_CHAR / PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO stay the database's functions.
 * <p>
 * Native SQL, unchanged except that the values are bound, and why:
 * <ul>
 * <li>{@link #generateCDCReturnData}: CONNECT BY LEVEL (a row generator), SOUNDEX, ADD_MONTHS, analytic MAX and
 * ROWNUM;</li>
 * <li>{@link #getRecordSearchViewResults}, {@link #getSentFileName}, {@link #discardCDCFeedbackRecords},
 * {@link #updateSameAsRecordsReturnFile}, {@link #updateRecordsWorkedPreviously}: the return rows are identified by
 * their Oracle ROWID (the screen sends it back), which JPQL has no notion of;</li>
 * <li>{@link #identifyCDCNoMatches}, {@link #identifyCDCReturnFileDuplicates}: Oracle MERGE (the latter also by
 * ROWID; it was declared as a select, it runs as the update it is);</li>
 * <li>{@link #getRecordSearchStatenoHistory}: LISTAGG.</li>
 * </ul>
 * The dates the former SQL set with SYSDATE are still the database's SYSDATE. A list the former SQL rendered as
 * {@code IN (...)} fails when it is empty or missing, as the former {@code IN ()} did.
 * <p>
 * Kept as they were: the three-digit years of getRecordSearchStatenos / getRecordSearchStatenoFlagHistory (former
 * TO_CHAR(x, 'MM/DD/YYY')); the never-called insertAutoRecordSearchEntry and checkRecordSearchStatenoExists. The
 * NVL(MAX(GENERATE_DATE), '01-Jan-1900') of checkNewRecordsForTheSTATENO is the date that text stands for (it no longer
 * depends on the session's NLS date format).
 */
@Repository
@RequiredArgsConstructor
public class RecordSearchReviewDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	/** TO_CHAR(x, 'MM/DD') of the former 'MM/DD/YYY' (the year follows as its last three digits). */
	private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MM/dd", Locale.US);

	/** NVL(MAX(GENERATE_DATE), '01-Jan-1900'): the date the text stood for (DD-MON-YYYY in the session's format). */
	private static final Timestamp JAN_1_1900 = Timestamp.valueOf("1900-01-01 00:00:00");

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/** Unflags STATENOs (p_stateno_list) that are flagged: p_workerId, p_requestComments. */
	public int changeRecordSearchStatus(Map<Object, Object> inOutParameterMap) {
		return jpql.update("""
				update NyehmsFlagRecordSearch r set r.unflagUid = ?1, r.unflagComments = ?2, r.unflagDate = sysdate
				where r.stateno in ?3 and r.unflagDate is null""",
				longValue(inOutParameterMap.get("p_workerId")), text(inOutParameterMap.get("p_requestComments")),
				inList(inOutParameterMap.get("p_stateno_list"), "p_stateno_list"));
	}

	/** The number of open flags of p_stateno. */
	public int checkForExistingRecordSearchStateno(Map<Object, Object> inOutParameterMap) {
		return jpql.one("select count(*) from NyehmsFlagRecordSearch r where r.stateno = ?1 and r.unflagDate is null",
				Rows.scalar(Integer.class), text(inOutParameterMap.get("p_stateno")));
	}

	/** Flags p_stateno: FLAG_ORIGIN, p_requestComments, p_workerId. */
	public int addRecordSearchFlagStatusEntry(Map<Object, Object> inOutParameterMap) {
		return jpql.update("""
				insert into NyehmsFlagRecordSearch (stateno, flagOrigin, comments, createUid, createDate)
				values (?1, ?2, ?3, ?4, sysdate)""",
				text(inOutParameterMap.get("p_stateno")), text(inOutParameterMap.get("FLAG_ORIGIN")),
				text(inOutParameterMap.get("p_requestComments")), longValue(inOutParameterMap.get("p_workerId")));
	}

	/** Flags STATENO automatically: p_requestComments, p_workerId. */
	public void insertAutoRecordSearchEntry(Map<String, String> inOutParameters) {
		jpql.update("""
				insert into NyehmsFlagRecordSearch (stateno, flagOrigin, comments, createUid, createDate)
				values (?1, 'Automatic', ?2, ?3, sysdate)""",
				text(inOutParameters.get("STATENO")), text(inOutParameters.get("p_requestComments")),
				longValue(inOutParameters.get("p_workerId")));
	}

	/**
	 * The current STATENO of a sent PS message's person and whether it is flagged: STATENO, FLAG (Y / N); null if the
	 * message does not exist.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> checkRecordSearchStatenoExists(UpdatedDispositionsVO updatedDispositionsVO) {
		return (Map) jpql.one("""
				select function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO', s.stateno) as STATENO,
				case when r.stateno is null or r.unflagDate is not null then 'N' else 'Y' end as FLAG
				from NyehmsStdMessageSent s
				left join NyehmsFlagRecordSearch r on function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO', s.stateno) = r.stateno
				and r.unflagDate is null
				where s.documentId = ?1""", Rows.map(), longValue(updatedDispositionsVO.getDOCUMENT_ID()));
	}

	/** Adds p_requestComments to the comments of STATENO's open flag. */
	public void updateAutoRecordSearchEntry(Map<String, String> inOutParameters) {
		jpql.update("update NyehmsFlagRecordSearch r set r.comments = concat(r.comments, '   |    ', ?1) where r.stateno = ?2 and r.unflagDate is null",
				text(inOutParameters.get("p_requestComments")), text(inOutParameters.get("STATENO")));
	}

	/**
	 * The open flags: STATENO, FLAG_ORIGIN, NO_CDC_DATA, COMMENTS, CREATE_UID (the flagging worker's name), CREATE_DATE
	 * (as the former TO_CHAR(x, 'MM/DD/YYY') wrote it: a three-digit year, kept as it was), HISTORY (the number of
	 * flags of the STATENO).
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getRecordSearchStatenos() {
		List<Row> rows = jpql.list("""
				select r.stateno as STATENO, r.flagOrigin as FLAG_ORIGIN, r.noCdcData as NO_CDC_DATA, r.comments as COMMENTS,
				u.fullName as CREATE_UID, r.createDate as CREATE_DATE,
				(select count(*) from NyehmsFlagRecordSearch h where h.stateno = r.stateno) as HISTORY
				from NyehmsFlagRecordSearch r, NyehmsUsersVw u
				where r.createUid = u.workerId and r.unflagDate is null""", row -> row);
		List<JSONObject> results = new ArrayList<>();
		for (Row row : rows) {
			JSONObject json = new JSONObject();
			for (String label : row.labels()) {
				Object value = row.get(label);
				if ("CREATE_DATE".equals(label)) {
					value = threeDigitYear((Timestamp) value);
				} else if ("HISTORY".equals(label) && value != null) {
					value = new BigDecimal(value.toString());          // count(*) as JDBC getObject typed it (NUMBER)
				}
				if (value != null) {
					json.put(label, value);
				}
			}
			results.add(json.isEmpty() ? null : json);
		}
		return results;
	}

	/**
	 * The flags of a STATENO, oldest first: FLAG_ORIGIN, COMMENTS, CREATE_UID / UNFLAG_UID (the workers' names),
	 * CREATE_DATE / UNFLAG_DATE (TO_CHAR(x, 'MM/DD/YYY'), a three-digit year as before), UNFLAG_COMMENTS.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRecordSearchStatenoFlagHistory(String stateno) {
		List<Row> rows = jpql.list("""
				select r.flagOrigin as FLAG_ORIGIN, r.comments as COMMENTS,
				(select u.fullName from NyehmsUsersVw u where u.workerId = r.createUid) as CREATE_UID,
				r.createDate as CREATE_DATE, r.unflagComments as UNFLAG_COMMENTS,
				(select u2.fullName from NyehmsUsersVw u2 where u2.workerId = r.unflagUid) as UNFLAG_UID,
				r.unflagDate as UNFLAG_DATE
				from NyehmsFlagRecordSearch r where r.stateno = ?1 order by r.createDate asc""", row -> row, stateno);
		List<Map<String, String>> results = new ArrayList<>();
		for (Row row : rows) {
			Map<String, Object> map = new HashMap<>();
			for (String label : row.labels()) {
				Object value = row.get(label);
				if ("CREATE_DATE".equals(label) || "UNFLAG_DATE".equals(label)) {
					value = threeDigitYear((Timestamp) value);
				}
				if (value != null) {
					map.put(label, value);
				}
			}
			results.add(map.isEmpty() ? null : (Map) map);
		}
		return results;
	}

	/** TO_CHAR(x, 'MM/DD/YYY'): month / day and the last three digits of the year. */
	private static String threeDigitYear(Timestamp value) {
		if (value == null) {
			return null;
		}
		var dateTime = value.toLocalDateTime();
		return MONTH_DAY.format(dateTime) + "/" + String.format(Locale.US, "%03d", dateTime.getYear() % 1000);
	}

	/** Native: CONNECT BY LEVEL, SOUNDEX, ADD_MONTHS, analytic functions and ROWNUM. */
	private static final String CDC_RETURN_DATA_1 = """
			WITH query1 AS
			(
			SELECT  Distinct doc_attr.last_name, SOUNDEX(doc_attr.last_name) lname_soundex,
			        date_of_birth,
			        CASE
			            WHEN doc_attr.date_of_birth IS NOT NULL THEN To_Char(doc_attr.date_of_birth, 'YYYYMMDD')
			            ELSE doc_attr.dob_text
			        END dob_text,
			        doc_attr.birth_sex,
			        pdoc.stateno,
			        pdoc.association_date,
			        Add_Months(Max(pdoc.association_date) over (partition by pdoc.stateno), -24)  max_assoc_dt,
			        Nvl(Max(rec.generate_date) over (partition by pdoc.stateno),to_date('01/01/1900', 'MM/DD/YYYY')) file_gen_dt
			        ,(SELECT m.cityno
			           FROM nyehms_ehars_stateno_map m
			          WHERE m.nyehms_stateno = pdoc.stateno
			            AND m.cityno IS NOT NULL
			            AND ROWNUM = 1) cityno ,
			         (SELECT m.EHARS_STATENO
			           FROM nyehms_ehars_stateno_map m
			          WHERE m.nyehms_stateno = pdoc.stateno
			            AND m.EHARS_STATENO IS NOT NULL
			            AND ROWNUM = 1) EHARS_STATENO
			  FROM document_person_attr doc_attr, person_doc_association pdoc, nyehms_flag_record_search rec
			WHERE pdoc.document_id = doc_attr.document_id
			   AND (doc_attr.last_name IS NOT NULL
			        AND doc_attr.date_of_birth IS NOT NULL)
			   AND pdoc.stateno IN
			""";

	private static final String CDC_RETURN_DATA_2 = """
			AND pdoc.dissassociation_date IS null
			  AND pdoc.stateno = rec.stateno(+)
			),
			query2 AS (SELECT level l FROM dual CONNECT BY LEVEL <=2)
			,query3 AS (
			SELECT Distinct a.lname_soundex,
			        a.dob_text,
			        case when birth_sex is null and l = 1 then 'M'
			            when birth_sex is null and l != 1 then 'F'
			            when birth_sex is not null then birth_sex
			        end birth_sex,
			        a.stateno,
			        date_of_birth
			        ,cityno
			        , EHARS_STATENO
			  FROM query1 a, query2 where   a.lname_soundex is not null
			   AND a.association_date > max_assoc_dt
			   AND a.association_date > file_gen_dt
			ORDER BY lname_soundex, date_of_birth
			)
			SELECT ROWNUM, a.*
			FROM query3 a
			""";

	/**
	 * The rows of a new CDC file, numbered (ROWNUM): for all flagged STATENOs when selectedStatenos is ALL, otherwise
	 * for p_stateno_list.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> generateCDCReturnData(Map<String, Object> inParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String statenos;
		if ("ALL".equals(inParameterMap.get("selectedStatenos"))) {
			statenos = " (select STATENO from NYEHMS_FLAG_RECORD_SEARCH where UNFLAG_DATE is null) ";
		} else {
			statenos = inPlaceholders(inList(inParameterMap.get("p_stateno_list"), "p_stateno_list"), parameters);
		}
		return (List) statements.list(CDC_RETURN_DATA_1 + statenos + CDC_RETURN_DATA_2, Rows.map(), parameters.toArray());
	}

	/** Stores the rows of a new CDC file (cdcReturnData, from generateCDCReturnData) as sent: fileName, workerId. */
	public void storeCDCReturnData(Map<String, Object> inParameterMap) {
		List<?> rows = rowList(inParameterMap.get("cdcReturnData"), "cdcReturnData");
		StringBuilder hql = new StringBuilder("""
				insert into NyehmsRecSearchSent (sentFileName, sentFileSeq, lnameSoundex, dobText, birthSex, stateno, cityno, eharsStateno,
				createUid, createDate) values """);
		List<Object> parameters = new ArrayList<>();
		for (Object element : rows) {
			Map<?, ?> row = (Map<?, ?>) element;
			hql.append(parameters.isEmpty() ? "" : ", ").append(values(parameters, 9)).append(", sysdate)");
			parameters.add(text(inParameterMap.get("fileName")));
			parameters.add(longValue(row.get("ROWNUM")));
			parameters.add(text(row.get("LNAME_SOUNDEX")));
			parameters.add(text(row.get("DOB_TEXT")));
			parameters.add(text(row.get("BIRTH_SEX")));
			parameters.add(text(row.get("STATENO")));
			parameters.add(text(row.get("CITYNO")));
			parameters.add(text(row.get("EHARS_STATENO")));
			parameters.add(longValue(inParameterMap.get("workerId")));
		}
		jpql.update(hql.toString(), parameters.toArray());
	}

	/** Marks the open flags of p_stateno_list as sent in fileName: workerId. */
	public void updateRecordSearchStatenosWithFileName(Map<String, Object> inParameterMap) {
		jpql.update("""
				update NyehmsFlagRecordSearch r set r.sentFileName = ?1, r.generateUid = ?2, r.generateDate = sysdate, r.unflagDate = sysdate,
				r.unflagComments = 'Waiting for CDC match' where r.stateno in ?3 and r.unflagDate is null""",
				text(inParameterMap.get("fileName")), longValue(inParameterMap.get("workerId")),
				inList(inParameterMap.get("p_stateno_list"), "p_stateno_list"));
	}

	/** The sent rows of a CDC file: SENT_FILE_NAME, LNAME_SOUNDEX, DOB_TEXT (MM/DD/YYYY), BIRTH_SEX, STATENO, CITYNO, EHARS_STATENO. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getCDCPendingReturnData(String cdcFileName) {
		return (List) jpql.list("""
				select s.sentFileName as SENT_FILE_NAME, s.lnameSoundex as LNAME_SOUNDEX,
				to_char(fn_to_date(s.dobText, 'MM/DD/YYYY'), 'MM/DD/YYYY') as DOB_TEXT, s.birthSex as BIRTH_SEX, s.stateno as STATENO,
				s.cityno as CITYNO, s.eharsStateno as EHARS_STATENO
				from NyehmsRecSearchSent s where s.sentFileName = ?1""", Rows.map(), cdcFileName);
	}

	/**
	 * The CDC files not uploaded back yet, newest day first: SENT_FILE_NAME, GENERATEDATE (MM/DD/YYYY), the day of the
	 * former unaliased {@code TRUNC(rec_search.GENERATE_DATE)} column and GENERATEDBY (last, first name); one row per
	 * distinct combination, as the former SELECT DISTINCT. The day is kept as MyBatis stored that column: its label
	 * contains a dot, so it went into a nested map, {@code "TRUNC(REC_SEARCH" -> {"GENERATE_DATE)" -> day}}. The rows
	 * are ordered by the generate date (descending, the database's place for nulls); the former order by the day left
	 * the order within a day open, this one gives the later rows of a day first.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getCDCPendingReturnFileNames() {
		List<Row> rows = jpql.list("""
				select r.sentFileName as SENT_FILE_NAME, r.generateDate as GENERATE_DATE, u.lastName as LAST_NAME, u.firstName as FIRST_NAME
				from NyehmsFlagRecordSearch r left join NyehmsUsers u on r.generateUid = u.workerId
				where r.uploadDate is null and r.sentFileName is not null order by r.generateDate desc""", row -> row);
		Set<List<Object>> distinct = new LinkedHashSet<>();
		for (Row row : rows) {
			Timestamp generated = (Timestamp) row.get("GENERATE_DATE");
			Timestamp day = generated == null ? null : Timestamp.valueOf(generated.toLocalDateTime().toLocalDate().atStartOfDay());
			distinct.add(Arrays.asList(row.get("SENT_FILE_NAME"), generated == null ? null : DATE.format(generated.toLocalDateTime()), day,
					oracleConcat(row.get("LAST_NAME"), ", ", row.get("FIRST_NAME"))));
		}
		List<Map<String, String>> results = new ArrayList<>();
		for (List<Object> values : distinct) {
			Map<String, Object> map = new HashMap<>();
			if (values.get(0) != null) map.put("SENT_FILE_NAME", values.get(0));
			if (values.get(1) != null) map.put("GENERATEDATE", values.get(1));
			if (values.get(2) != null) {
				Map<String, Object> trunc = new HashMap<>();
				trunc.put("GENERATE_DATE)", values.get(2));
				map.put("TRUNC(REC_SEARCH", trunc);
			}
			if (values.get(3) != null) map.put("GENERATEDBY", values.get(3));
			results.add(map.isEmpty() ? null : (Map) map);
		}
		return results;
	}

	/** a || b || c in Oracle: a null is an empty text, and the result is null only if it is empty. */
	private static String oracleConcat(Object... parts) {
		StringBuilder text = new StringBuilder();
		for (Object part : parts) {
			if (part != null) {
				text.append(part);
			}
		}
		return text.isEmpty() ? null : text.toString();
	}

	/**
	 * Stores the rows of a returned CDC file (fileDataList: maps with the file's column names, sentFileSeq) under
	 * fileName; the values are trimmed and the date of birth normalized to MM/DD/YYYY by the database, as before.
	 */
	public void uploadCDCFileData(Map<String, Object> inputParameterMap) {
		List<?> rows = rowList(inputParameterMap.get("fileDataList"), "fileDataList");
		StringBuilder hql = new StringBuilder("""
				insert into NyehmsRecSearchReturn (sentFileName, sentFileSeq, lnameSoundex, dobText, birthSex, stateno, stateCd, cityno, transCateg,
				hivCateg, hivDxDt, rshStateCd, aidsCateg, aidsDxDt, rsaStateCd, dod, radStateCd, deathStateCd, curStateCd, curAddressDt,
				cd4cntpctVlRecentDt, careNolabDt, ridrDuplicateStatusWarningName) values """);
		String[] trimmed = {"cityno_x", "trans_categ", "hiv_categ", "hiv_dx_dt", "rsh_state_cd", "aids_categ", "aids_dx_dt", "rsa_state_cd",
				"dod", "rad_state_cd", "death_state_cd", "cur_state_cd", "cur_address_dt", "cd4cntpct_vl_recent_dt", "care_nolab_dt",
				"duplicate_status_warning"};
		List<Object> parameters = new ArrayList<>();
		for (Object element : rows) {
			Map<?, ?> row = (Map<?, ?>) element;
			int n = parameters.size();
			hql.append(n == 0 ? "" : ", ").append("(?").append(n + 1).append(", ?").append(n + 2).append(", ?").append(n + 3)
					.append(", function('TO_CHAR' as String, fn_to_date(trim(both from ?").append(n + 4).append("), 'MM/DD/YYYY'), 'MM/DD/YYYY'), ?").append(n + 5)
					.append(", ?").append(n + 6).append(", ?").append(n + 7);
			for (int i = 0; i < trimmed.length; i++) {
				hql.append(", trim(both from ?").append(n + 8 + i).append(')');
			}
			hql.append(')');
			parameters.add(text(inputParameterMap.get("fileName")));
			parameters.add(longValue(row.get("sentFileSeq")));
			parameters.add(text(row.get("last_name_sndx_x")));
			parameters.add(text(row.get("dob_x")));
			parameters.add(text(row.get("sex_x")));
			parameters.add(text(row.get("stateno_x")));
			parameters.add(text(row.get("report_state_cd")));
			for (String key : trimmed) {
				parameters.add(text(row.get(key)));
			}
		}
		jpql.update(hql.toString(), parameters.toArray());
	}

	/** Marks fileName as uploaded (if it is not yet): workerId. */
	public int updateCDCReturnDataInfo(Map<String, Object> inputParameterMap) {
		return jpql.update("update NyehmsFlagRecordSearch r set r.uploadUid = ?1, r.uploadDate = sysdate where r.uploadDate is null and r.sentFileName = ?2",
				longValue(inputParameterMap.get("workerId")), text(inputParameterMap.get("fileName")));
	}

	/** The reporting states with unresolved returned rows, in order. */
	public List<String> getCDCReportingStateCodesToProcess() {
		return jpql.list("""
				select distinct r.stateCd as state from NyehmsRecSearchReturn r
				where (r.acrfDocId is null and r.discardedDate is null and r.ridrSameAs is null) order by r.stateCd asc""",
				Rows.scalar(String.class));
	}

	private static final String RECORD_SEARCH_VIEW_RESULTS = """
			select rec_return.ROWID recordSearchReturnRowId, rec_sent.STATENO nysStateno, rec_sent.EHARS_STATENO eharsStateno, rec_sent.SENT_FILE_NAME fileName, rec_sent.LNAME_SOUNDEX nysLnameSoundex, rec_sent.DOB_TEXT nysDobText, rec_sent.BIRTH_SEX nysBirthSex,
			                  rec_return.STATENO  reportingStateno, rec_return.STATE_CD  reportingStatecd, rec_return.LNAME_SOUNDEX  reportingLnameSoundex, rec_return.DOB_TEXT  reportingDobText, rec_return.BIRTH_SEX  reportingBirthSex, rec_return.TYPE   reportingType,
			                  rec_search.COMMENTS comments, usr.FULL_NAME creatUid ,
			                  TO_CHAR(rec_return.PARENT_DOC_CREATE_DT, 'MM/DD/YYYY') parentDocCreatedDate,
			                  PARENT_DOC_EXISTS parentDocExists
			                  from
			                  NYEHMS_REC_SEARCH_RETURN rec_return,
			                  NYEHMS_REC_SEARCH_SENT rec_sent
			                   ,NYEHMS_FLAG_RECORD_SEARCH rec_search , NYEHMS_USERS_VW usr
			                   where
			                       rec_return.SENT_FILE_NAME = rec_sent.SENT_FILE_NAME
			                        and rec_sent.SENT_FILE_NAME = rec_search.SENT_FILE_NAME(+)
			                        and rec_sent.STATENO = rec_search.STATENO (+)
			                        and rec_search.CREATE_UID = usr.WORKER_ID(+)
			                  and rec_sent.SENT_FILE_SEQ = rec_return.SENT_FILE_SEQ
			                  and (rec_return.ACRF_DOC_ID is null and rec_return.DISCARDED_DATE is null and rec_return.RIDR_SAME_AS is null)
			""";

	/** Native (ROWID): the unresolved returned rows with their sent rows, of one reporting state (stateValue) or ALL. */
	public List<RecordSearchResultsVO> getRecordSearchViewResults(Map<String, String> inputParameterMap) {
		Object stateValue = inputParameterMap.get("stateValue");
		if (!"ALL".equals(stateValue)) {
			return statements.list(RECORD_SEARCH_VIEW_RESULTS + " and rec_return.STATE_CD = ?1", Rows.bean(RecordSearchResultsVO.class),
					NativeStatements.text(stateValue));
		}
		return statements.list(RECORD_SEARCH_VIEW_RESULTS, Rows.bean(RecordSearchResultsVO.class));
	}

	/** Resolves the returned rows of sentFileName for duplicateStatenos with an ACRF document: documentId, p_workerId. */
	public int updateRecordSearchDocumentWithAcrf(Map<Object, Object> inputParameterMap) {
		return jpql.update("""
				update NyehmsRecSearchReturn r set r.acrfDocId = ?1, r.createUid = ?2, r.createDate = sysdate
				where r.sentFileName = ?3 and r.stateno in ?4""",
				longValue(inputParameterMap.get("documentId")), longValue(inputParameterMap.get("p_workerId")),
				text(inputParameterMap.get("sentFileName")), inList(inputParameterMap.get("duplicateStatenos"), "duplicateStatenos"));
	}

	/** The sent rows of a CDC file in file order: LNAME_SOUNDEX, DOB_TEXT (MM/DD/YYYY), BIRTH_SEX, STATENO, CITYNO, EHARS_STATENO. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> downloadCDCReturnFile(String cdcFileName) {
		return (List) jpql.list("""
				select s.lnameSoundex as LNAME_SOUNDEX, to_char(fn_to_date(s.dobText, 'MM/DD/YYYY'), 'MM/DD/YYYY') as DOB_TEXT,
				s.birthSex as BIRTH_SEX, s.stateno as STATENO, s.cityno as CITYNO, s.eharsStateno as EHARS_STATENO
				from NyehmsRecSearchSent s where s.sentFileName = ?1 order by s.sentFileSeq""", Rows.map(), cdcFileName);
	}

	/** 1 if a flag was sent in fileName, otherwise 0 (the former count with rownum &lt; 2). */
	public int checkFileNameExists(Map<String, Object> inParameterMap) {
		return jpql.list("select 1 from NyehmsFlagRecordSearch r where r.sentFileName = ?1 fetch first 1 rows only", row -> row,
				text(inParameterMap.get("fileName"))).size();
	}

	/** Native (ROWID): the file name of a returned row. */
	public String getSentFileName(String acrfReferenceRowId) {
		return statements.one("select SENT_FILE_NAME from NYEHMS_REC_SEARCH_RETURN where ROWID = ?1", Rows.scalar(String.class), NativeStatements.text(acrfReferenceRowId));
	}

	/** Native (ROWID): discards the returned rows p_cdcRecords (their ROWIDs): p_workerId. */
	public int discardCDCFeedbackRecords(Map<Object, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(inOutParameterMap.get("p_workerId"));
		String rowIds = inPlaceholders(inList(inOutParameterMap.get("p_cdcRecords"), "p_cdcRecords"), parameters);
		return statements.update("update NYEHMS_REC_SEARCH_RETURN set DISCARDED_DATE = SYSDATE, DISCARDED_BY = ?1 where ROWID in " + rowIds,
				parameters.toArray());
	}

	/** The reporting states with unresolved returned rows of nyehmsStateno in sentFileName. */
	public List<String> getPendingResolutionStates(Map<Object, Object> inputParameterMap) {
		return jpql.list("""
				select distinct r.stateCd from NyehmsRecSearchReturn r where r.sentFileName = ?1 and r.sentFileSeq in (
				select s.sentFileSeq from NyehmsRecSearchSent s where s.stateno = ?2 and s.sentFileName = ?3)
				and (r.acrfDocId is null and r.discardedDate is null and r.ridrSameAs is null)""", Rows.scalar(String.class),
				text(inputParameterMap.get("sentFileName")), text(inputParameterMap.get("nyehmsStateno")), text(inputParameterMap.get("sentFileName")));
	}

	/** Moves an open, unsent flag to another STATENO: recSearchSourceStateno, recSearchStatenoToReplace, recSearchComments. */
	public int replaceRecordSearchStateno(Map<Object, Object> inputParameterMap) {
		return jpql.update("""
				update NyehmsFlagRecordSearch r set r.stateno = ?1, r.comments = concat(r.comments, '   |    ', ?2)
				where r.stateno = ?3 and r.unflagDate is null and r.sentFileName is null""",
				text(inputParameterMap.get("recSearchStatenoToReplace")), text(inputParameterMap.get("recSearchComments")),
				text(inputParameterMap.get("recSearchSourceStateno")));
	}

	/** Ends the sent rows of a CDC file not uploaded: cdcFileName, workerId. */
	public int rejectCDCFileFromSentTable(Map<String, Object> inputParameterMap) {
		return jpql.update("update NyehmsRecSearchSent s set s.endDate = sysdate, s.endDatedUid = ?1 where s.sentFileName = ?2 and s.uploadDate is null",
				longValue(inputParameterMap.get("workerId")), text(inputParameterMap.get("cdcFileName")));
	}

	/** Takes the flags of a CDC file not uploaded out of the file: cdcFileName, workerId. */
	public int rejectCDCFileFromFlaggingTable(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				update NyehmsFlagRecordSearch r set r.sentFileName = null, r.generateUid = ?1, r.generateDate = sysdate
				where r.sentFileName = ?2 and r.uploadDate is null""",
				longValue(inputParameterMap.get("workerId")), text(inputParameterMap.get("cdcFileName")));
	}

	/** Takes the flags of fileName without sent rows out of the file (NO_CDC_DATA = Y). */
	public void updateNoCDCRecordsInFlaggingTable(Map<String, Object> inParameterMap) {
		jpql.update("""
				update NyehmsFlagRecordSearch f set f.noCdcData = 'Y', f.unflagDate = null
				where not exists (select 1 from NyehmsRecSearchSent s where s.stateno = f.stateno and s.sentFileName = ?1)
				and f.sentFileName = ?2""", text(inParameterMap.get("fileName")), text(inParameterMap.get("fileName")));
	}

	/** The number of documents of p_stateno associated after its last CDC file. */
	public int checkNewRecordsForTheSTATENO(Map<Object, Object> inOutParameterMap) {
		return jpql.one("""
				select count(*) from PersonDocAssociation p where p.stateno = ?1 and p.dissassociationDate is null
				and p.associationDate > coalesce((select max(f.generateDate) from NyehmsFlagRecordSearch f where f.stateno = ?2), ?3)""",
				Rows.scalar(Integer.class), text(inOutParameterMap.get("p_stateno")), text(inOutParameterMap.get("p_stateno")), JAN_1_1900);
	}

	/** Marks all open flags as sent in fileName: workerId. */
	public void updateRecordSearchStatenosWithFileNameForAllRecords(Map<String, Object> inParameterMap) {
		jpql.update("""
				update NyehmsFlagRecordSearch r set r.sentFileName = ?1, r.generateUid = ?2, r.generateDate = sysdate, r.unflagDate = sysdate,
				r.unflagComments = 'Waiting for CDC match' where r.unflagDate is null""",
				text(inParameterMap.get("fileName")), longValue(inParameterMap.get("workerId")));
	}

	/** Native: LISTAGG. */
	private static final String STATENO_HISTORY = """
			WITH data AS
			(
			SELECT DISTINCT a.STATENO,To_Char(a.UNFLAG_DATE, 'MM/DD/YYYY hh:mi:ss AM') UNFLAG_DATE, UNFLAG_COMMENTS, To_Char(a.create_date, 'MM/DD/YYYY hh:mi:ss AM') CREATE_DATE, a.COMMENTS,
			        To_Char(a.generate_date,  'MM/DD/YYYY  hh:mi:ss AM') GENERATE_DATE, a.SENT_FILE_NAME
			        , (SELECT Count(*)
			             FROM nyehms_rec_search_sent d
			            WHERE d.sent_file_name = a.sent_file_name
			              AND d.stateno = a.stateno) SENT_COUNT
			        , c.STATE_CD
			FROM nyehms_flag_record_search a
			    left outer JOIN nyehms_rec_search_sent b
			    ON (b.sent_file_name = a.sent_file_name
			        AND b.stateno = a.stateno)
			    left outer JOIN nyehms_rec_search_return c
			    ON (c.sent_file_name = b.sent_file_name
			        AND c.sent_file_seq = b.sent_file_seq)
			WHERE a.stateno = ?1
			)
			SELECT stateno, UNFLAG_DATE, UNFLAG_COMMENTS, sent_file_name, create_date, comments, generate_date, sent_count
			        ,listagg(state_cd, ',') within GROUP (order by stateno) AS state_cd
			FROM data
			GROUP BY stateno, UNFLAG_DATE, UNFLAG_COMMENTS, sent_file_name, create_date, comments, generate_date, sent_count
			ORDER BY create_date DESC
			""";

	/** Native (LISTAGG): the flags of a STATENO with their files and the states that returned rows, newest first. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRecordSearchStatenoHistory(String stateNo) {
		return (List) statements.list(STATENO_HISTORY, Rows.map(), stateNo);
	}

	/** Native (Oracle MERGE): the flags of fileName that no state returned a row for: 'No match found'. */
	public void identifyCDCNoMatches(Map<String, Object> inputParameterMap) {
		statements.update("""
				MERGE INTO nyehms_flag_record_search a
				USING (
				WITH data AS
				(
				SELECT Distinct s.sent_file_name, s.stateno sent_stateno, FIRST_VALUE(r.stateno IGNORE NULLS) OVER (PARTITION BY s.stateno) ret_stateno
				  FROM nyehms_flag_record_search a
				  JOIN nyehms_rec_search_sent s ON s.sent_file_name = a.sent_file_name AND s.stateno = a.stateno and s.sent_file_name = ?1
				  LEFT JOIN nyehms_rec_search_return r ON r.sent_file_name = s.sent_file_name AND s.sent_file_seq = r.sent_file_seq
				)
				SELECT *
				  FROM data
				WHERE ret_stateno IS NULL
				) b
				ON (b.sent_file_name = a.sent_file_name AND b.sent_stateno = a.stateno)
				WHEN MATCHED THEN
				UPDATE SET unflag_comments = 'No match found'
				WHERE unflag_date IS NOT NULL
				""", NativeStatements.text(inputParameterMap.get("fileName")));
	}

	/** The flags of fileName waiting for a match: 'CDC match'. */
	public void identifyCDCMatches(Map<String, Object> inputParameterMap) {
		jpql.update("""
				update NyehmsFlagRecordSearch r set r.unflagComments = 'CDC match'
				where r.unflagComments = 'Waiting for CDC match' and r.sentFileName = ?1""", text(inputParameterMap.get("fileName")));
	}

	/**
	 * Native (Oracle MERGE by ROWID): marks the unresolved returned rows of fileName that have a recent RIDR ACRF of the
	 * person (PARENT_DOC_EXISTS, PARENT_DOC_CREATE_DT).
	 */
	public void identifyCDCReturnFileDuplicates(Map<String, Object> inputParameterMap) {
		statements.update("""
				MERGE INTO NYEHMS_OWNER.NYEHMS_REC_SEARCH_RETURN  out
				                USING (
				                        SELECT *
				                        FROM
				                        (
				                        SELECT a.rowid row_id ,  pdoc.ASSOCIATION_DATE
				                              , ROW_NUMBER() OVER (PARTITION BY pdoc.stateno, A.STATE_CD, A.STATENO   ORDER BY pdoc.ASSOCIATION_DATE  DESC) rn
				                          FROM NYEHMS_REC_SEARCH_RETURN   a, NYEHMS_REC_SEARCH_SENT    sent, RAW_ACRF_RIDR_INFO  b, RAW_ACRF_DOC  c , person_doc_association  pdoc
				                        WHERE
				                            a.sent_file_name = ?1
				                            and a.ACRF_DOC_ID is null and a.DISCARDED_DATE is null and  a.RIDR_SAME_AS is null
				                            and b.ACRF_DOC_ID = c.document_id
				                            and c.DOCUMENT_SOURCE in ('A06.18', 'A06.29')
				                            and c.document_id = pdoc.document_id
				                            and pdoc.DISSASSOCIATION_DATE is null
				                            AND ABS(Trunc(pdoc.ASSOCIATION_DATE ) - Trunc(SYSDATE)) <= 180
				                            and a.sent_file_name = sent.sent_File_name
				                            and a.sent_file_seq = sent.sent_file_Seq
				                            and b.STATE_CD =  a.STATE_CD
				                            and b.STATENO  =  a.STATENO
				                            and pdoc.stateno =  PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (sent.STATENO )
				                        )
				                        WHERE rn = 1
				                ) m
				                ON (out.rowid = m.row_id)
				                WHEN MATCHED THEN
				                UPDATE SET PARENT_DOC_EXISTS = 'Y', PARENT_DOC_CREATE_DT = ASSOCIATION_DATE
				""", NativeStatements.text(inputParameterMap.get("fileName")));
	}

	/** The number of rows in EHARS_RIDR. */
	public int checkForRIDREharsData() {
		return jpql.one("select count(*) from EharsRidr e", Rows.scalar(Integer.class));
	}

	/** Native (ROWID): discards the unresolved returned rows eHARS already knows as 'same as' (RIDR_SAME_AS = Y, by 900). */
	public int updateSameAsRecordsReturnFile() {
		return statements.update("""
				update NYEHMS_REC_SEARCH_RETURN set ridr_same_as = 'Y', DISCARDED_DATE = SYSDATE, DISCARDED_BY = '900'  where rowid in (
				SELECT REC_RETN.rowId row_id
				  FROM NYEHMS_REC_SEARCH_SENT rec_sent,
				       NYEHMS_REC_SEARCH_RETURN rec_retn,
				       EHARS_RIDR ehars
				 WHERE     rec_sent.sent_file_name = rec_retn.sent_file_name
				       AND rec_sent.sent_file_seq = rec_retn.sent_file_seq
				       AND rec_retn.acrf_doc_id IS NULL
				       AND rec_retn.DISCARDED_DATE IS NULL
				       AND rec_retn.RIDR_SAME_AS IS NULL
				       AND rec_retn.STATE_CD = ehars.RIDR_STATE_CD
				       AND REC_RETN.STATENO = ehars.RIDR_STATENO
				       AND REC_SENT.EHARS_STATENO = ehars.EHARS_STATENO)
				""");
	}

	/**
	 * The unresolved row of the sent file with this name and the soundex, date of birth (dob), sex and eHARS STATENO
	 * (stateno; empty: none) and CITYNO (cityno, if given) of a returned row: its SENT_FILE_SEQ, or null.
	 */
	public Integer getRecordSearchSentSeq(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>(Arrays.asList(text(inputParameterMap.get("fileName")), text(inputParameterMap.get("last_name_sndx")),
				text(inputParameterMap.get("dob")), text(inputParameterMap.get("sex"))));
		StringBuilder hql = new StringBuilder("""
				select s.sentFileSeq from NyehmsRecSearchSent s
				where s.sentFileName = ?1 and s.lnameSoundex = ?2 and s.dobText = ?3 and s.birthSex = ?4""");
		Object stateno = inputParameterMap.get("stateno");
		if (stateno != null && !"".equals(stateno)) {
			parameters.add(stateno);
			hql.append(" and s.eharsStateno = ?").append(parameters.size());
		} else {
			hql.append(" and s.eharsStateno is null");
		}
		Object cityno = inputParameterMap.get("cityno");
		if (cityno != null && !"".equals(cityno)) {
			parameters.add(cityno);
			hql.append(" and s.cityno = ?").append(parameters.size());
		}
		return jpql.one(hql.toString(), Rows.scalar(Integer.class), parameters.toArray());
	}

	/** Native (ROWID): discards the unresolved returned rows already worked in an earlier file (by 900). */
	public void updateRecordsWorkedPreviously() {
		statements.update("""
				update NYEHMS_REC_SEARCH_RETURN set DISCARDED_DATE = SYSDATE, DISCARDED_BY = '900'  where rowid in (
				SELECT REC_RETN.rowId row_id
				  FROM NYEHMS_REC_SEARCH_SENT rec_sent,
				       NYEHMS_REC_SEARCH_RETURN rec_retn,
				       NYEHMS_REC_SEARCH_RETURN rec_retn_old,
				       NYEHMS_REC_SEARCH_SENT rec_sent_old
				 WHERE     rec_sent.sent_file_name = rec_retn.sent_file_name
				       AND rec_sent.sent_file_seq = rec_retn.sent_file_seq
				       AND rec_sent_old.sent_file_name = rec_retn_old.sent_file_name
				       AND rec_sent_old.sent_file_seq = rec_retn_old.sent_file_seq
				       AND rec_retn.acrf_doc_id IS NULL
				       AND rec_retn.DISCARDED_DATE IS NULL
				       AND rec_retn.STATE_CD = rec_retn_old.STATE_CD
				       AND rec_retn.STATENO = rec_retn_old.STATENO
				       AND rec_sent.EHARS_STATENO = rec_sent_old.EHARS_STATENO
				       and rec_retn_old.create_uid != '900')
				""");
	}

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

	/** (?n, ?n+1, ...) for the values, which are added to the parameters. */
	private static String inPlaceholders(List<Object> values, List<Object> parameters) {
		StringBuilder text = new StringBuilder("(");
		for (Object value : values) {
			parameters.add(value);
			text.append(text.length() > 1 ? ", " : "").append('?').append(parameters.size());
		}
		return text.append(")").toString();
	}

	/** (?n+1, ..., ?n+count for a row of a multi-row insert, without its closing parenthesis. */
	private static String values(List<Object> parameters, int count) {
		StringBuilder text = new StringBuilder("(");
		for (int i = 1; i <= count; i++) {
			text.append(i > 1 ? ", " : "").append('?').append(parameters.size() + i);
		}
		return text.toString();
	}
}
