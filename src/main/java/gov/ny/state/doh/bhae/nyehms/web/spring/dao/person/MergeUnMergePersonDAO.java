package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.textCursor;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.BadlyMergedStatenosVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.FlaggedStatenoVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * Merging and un-merging persons: the flagged pairs and bad merges waiting for review, the consolidation procedures,
 * the merge reports (formerly a MyBatis mapper with the same name and methods; the callers are unchanged).
 * <p>
 * JPQL over the entities NyehmsNewFlaggedSameVw, NyehmsNewFlaggedBadmergeVw, NyehmsDuplicateStatenos, RawAcrfRidrInfo
 * and NyehmsEharsStatenoMap. The procedures of {@code pkg_consolidation} (and {@code pkg_document_management}) run
 * through {@link NativeStatements#call}; their OUT values and cursor rows are put back into the caller's map under
 * the former keys, the cursors mapped as the former result maps mapped them (read as text, as every property of these
 * VOs is text; {@link PersonResultMaps} for the person cursors). Native SQL ({@link MergeUnMergePersonSql}, the
 * mapper's text with bound values), with the reason:
 * <ul>
 * <li>{@link #addACRFForRejectedStateno}: an insert ... select with SOUNDEX, TO_CHAR, TRUNC(SYSDATE) and ROWNUM;</li>
 * <li>{@link #validateDodAgainstCollectionDate}, {@link #validateDodAgainstCollectionDateBeforeMerge}: CTEs over
 * tables of other schemas with (+) joins and date functions;</li>
 * <li>{@link #getMergeReportsByOrigin}, {@link #getMergeReportsByBadMerges}: FULL OUTER JOINs of weekly counts with
 * {@code trunc(sysdate, 'd')} arithmetic and quoted aliases;</li>
 * <li>{@link #getMergeReportsByDailyStaff}, {@link #getBadMergeReportsByDailyStaff}: TO_CHAR / TO_DATE formats (ISO
 * week, day name);</li>
 * <li>{@link #getDependentDocumentList}: UNPIVOT and UNIONs;</li>
 * <li>{@link #commitCurrentTransaction}: a PL/SQL block committing the database transaction (kept as it was).</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class MergeUnMergePersonDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	/** The former result map stateNoPairs: a flagged pair. */
	static final TypedRowMapper<FlaggedStatenoVO> STATENO_PAIRS = Rows.resultMap(FlaggedStatenoVO.class,
			"duplication_id", "duplicationId", "flagged_by", "flaggedBy", "flagged_date", "flaggedDate", "request_comments", "requestComments",
			"review_comments", "reviewComments", "status_cd", "statusCode", "stateno_1", "stateno1", "stateno_2", "stateno2",
			"flagged_earlier", "flaggedEarlier", "origin", "originCategorieVal", "REVIEWED_BY", "reviewedBy", "REVIEW_DATE", "reviewedDate",
			"STATENO_1_BAD_FLAG", "stateno1BadFlag", "STATENO_2_BAD_FLAG", "stateno2BadFlag", "FOLLOWUP", "followUp");

	/** The former result map badlyMergedStateNos: a state number flagged as a bad merge. */
	static final TypedRowMapper<BadlyMergedStatenosVO> BADLY_MERGED_STATENOS = Rows.resultMap(BadlyMergedStatenosVO.class,
			"unmerge_id", "unMergeId", "flagged_by", "flaggedBy", "flagged_date", "flaggedDate", "request_comments", "requestComments",
			"status_cd", "statusCode", "stateno", "stateno", "flagged_earlier", "flaggedEarlier", "review_comments", "reviewComments",
			"REVIEWED_BY", "reviewedBy", "REVIEW_DATE", "reviewedDate");

	/** The former result map stateNoHistoryRecords (type String): the sno column of each row. */
	static final RowMapper<String> STATENO_HISTORY_RECORDS = row -> (String) row.get("sno");

	// ---- the review lists ---------------------------------------------------------------------------------------------

	/** The pairs flagged as the same person and waiting for review (NYEHMS_NEW_FLAGGED_SAME_VW), in the database's order. */
	public List<FlaggedStatenoVO> getFlaggedStatenosList() throws DataAccessException {
		return jpql.list("""
				select v.duplicationId as DUPLICATION_ID, v.flaggedBy as FLAGGED_BY, v.flaggedDate as FLAGGED_DATE,
				       v.requestComments as REQUEST_COMMENTS, v.stateno1 as STATENO_1, v.stateno2 as STATENO_2,
				       v.flaggedEarlier as FLAGGED_EARLIER, v.stateno1BadFlag as STATENO_1_BAD_FLAG, v.stateno2BadFlag as STATENO_2_BAD_FLAG,
				       v.origin as ORIGIN, v.followup as FOLLOWUP
				from NyehmsNewFlaggedSameVw v""", STATENO_PAIRS);
	}

	/** The state numbers flagged as bad merges and waiting for review (NYEHMS_NEW_FLAGGED_BADMERGE_VW), in the database's order. */
	public List<BadlyMergedStatenosVO> getBadlyMergedStatenosList() throws DataAccessException {
		return jpql.list("""
				select v.unmergeId as UNMERGE_ID, v.stateno as STATENO, v.flaggedBy as FLAGGED_BY, v.flaggedDate as FLAGGED_DATE,
				       v.statusCd as STATUS_CD, v.requestComments as REQUEST_COMMENTS, v.flaggedEarlier as FLAGGED_EARLIER
				from NyehmsNewFlaggedBadmergeVw v""", BADLY_MERGED_STATENOS);
	}

	// ---- the consolidation procedures ------------------------------------------------------------------------------

	/**
	 * pkg_consolidation.pr_flag_same: flags two state numbers as the same person.
	 *
	 * @param map p_stateno1, p_stateno2, p_flagged_by, p_request_comments, p_originCategorie; afterwards also
	 *        p_duplication_id, p_code_out (Integers) and p_msg_out
	 */
	public void flagStatenoPairsAsSame(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_flag_same",
				param(map.get("p_stateno1"), String.class), param(map.get("p_stateno2"), String.class),
				param(map.get("p_flagged_by"), Integer.class), param(map.get("p_request_comments"), String.class),
				param(map.get("p_originCategorie"), Integer.class),
				out(Integer.class), out(Integer.class), out(String.class));
		map.put("p_duplication_id", results.get(5));
		map.put("p_code_out", results.get(6));
		map.put("p_msg_out", results.get(7));
	}

	/**
	 * pkg_consolidation.PR_MERGE_STATENOS: merges a flagged pair into the chosen state number.
	 *
	 * @param map p_duplication_id, p_review_comments, p_worker_id, p_mergedState_No; afterwards also
	 *        p_similar_pairs_exist, p_code_out (an Integer) and p_msg_out
	 */
	public void mergeStatenos(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.PR_MERGE_STATENOS",
				param(map.get("p_duplication_id"), Integer.class), param(map.get("p_review_comments"), String.class),
				param(map.get("p_worker_id"), Integer.class), param(map.get("p_mergedState_No"), String.class),
				out(String.class), out(Integer.class), out(String.class));
		map.put("p_similar_pairs_exist", results.get(4));
		map.put("p_code_out", results.get(5));
		map.put("p_msg_out", results.get(6));
	}

	/**
	 * pkg_consolidation.pr_get_flag_history: the flag history of two state numbers.
	 *
	 * @param map p_stateno_1, p_stateno_2; afterwards also p_flag_history (the rows, FlaggedStatenoVOs), p_code_out (an
	 *        Integer) and p_msg_out
	 */
	public void flagHistory(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_get_flag_history",
				param(map.get("p_stateno_1"), String.class), param(map.get("p_stateno_2"), String.class),
				textCursor(STATENO_PAIRS), out(Integer.class), out(String.class));
		map.put("p_flag_history", results.get(2));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/**
	 * pkg_consolidation.pr_reject_flag_same: rejects a flagged pair.
	 *
	 * @param map p_duplication_id, p_review_comments, p_worker_id; afterwards also p_similar_pairs_exist, p_code_out (an
	 *        Integer) and p_msg_out
	 */
	public void rejectStatenoFlagPair(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_reject_flag_same",
				param(map.get("p_duplication_id"), Integer.class), param(map.get("p_review_comments"), String.class),
				param(map.get("p_worker_id"), Integer.class),
				out(String.class), out(Integer.class), out(String.class));
		map.put("p_similar_pairs_exist", results.get(3));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/** Commits the database transaction from inside it (a PL/SQL block, kept as it was). */
	public void commitCurrentTransaction() {
		statements.update(MergeUnMergePersonSql.COMMIT_CURRENT_TRANSACTION);
	}

	/**
	 * pkg_consolidation.pr_fetch_to_merge_statenos: the two persons of a flagged pair for the merge screen.
	 *
	 * @param map p_duplication_id, p_module_id, p_access_user_id; afterwards also the ten cursors as lists —
	 *        p_stateno_1_details / p_stateno_2_details (PersonViewVOs), p_doc_1_timeline / p_doc_2_timeline
	 *        (PersonDocHistoryVOs), p_stateno_1_race / p_stateno_2_race (PersonRaceVOs), p_stateno_1_risks /
	 *        p_stateno_2_risks (PersonRisksVOs), p_stateno_1_mrg_history / p_stateno_2_mrg_history (Strings) — and
	 *        p_code_out (an Integer) and p_msg_out
	 */
	public void fetchPersonObjects(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_fetch_to_merge_statenos",
				param(map.get("p_duplication_id"), Integer.class),
				textCursor(PersonResultMaps.PERSON_VIEW), textCursor(PersonResultMaps.PERSON_VIEW),
				textCursor(PersonResultMaps.PERSON_DOC_HISTORY), textCursor(PersonResultMaps.PERSON_DOC_HISTORY),
				textCursor(PersonResultMaps.PERSON_RACE), textCursor(PersonResultMaps.PERSON_RACE),
				textCursor(PersonResultMaps.PERSON_RISKS), textCursor(PersonResultMaps.PERSON_RISKS),
				textCursor(STATENO_HISTORY_RECORDS), textCursor(STATENO_HISTORY_RECORDS),
				out(Integer.class), out(String.class),
				param(map.get("p_module_id"), Integer.class), param(map.get("p_access_user_id"), String.class));
		String[] keys = {null, "p_stateno_1_details", "p_stateno_2_details", "p_doc_1_timeline", "p_doc_2_timeline", "p_stateno_1_race",
				"p_stateno_2_race", "p_stateno_1_risks", "p_stateno_2_risks", "p_stateno_1_mrg_history", "p_stateno_2_mrg_history",
				"p_code_out", "p_msg_out"};
		for (int i = 1; i < keys.length; i++) {
			map.put(keys[i], results.get(i));
		}
	}

	/**
	 * pkg_consolidation.pr_flag_bad_merge: flags a state number as a bad merge.
	 *
	 * @param map p_stateno, p_flagged_by, p_request_comments; afterwards also p_unmerge_id, p_code_out (Integers) and
	 *        p_msg_out
	 */
	public void flagBadMerge(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_flag_bad_merge",
				param(map.get("p_stateno"), String.class), param(map.get("p_flagged_by"), Integer.class),
				param(map.get("p_request_comments"), String.class),
				out(Integer.class), out(Integer.class), out(String.class));
		map.put("p_unmerge_id", results.get(3));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/**
	 * pkg_consolidation.pr_unflag_bad_merge: takes the bad-merge flag off a state number.
	 *
	 * @param map p_stateno, p_worker_id, p_review_comments; afterwards also p_code_out (an Integer) and p_msg_out
	 */
	public void unflagBadMerge(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_unflag_bad_merge",
				param(map.get("p_stateno"), String.class), param(map.get("p_worker_id"), Integer.class),
				param(map.get("p_review_comments"), String.class),
				out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/**
	 * pkg_consolidation.pr_get_similar_flagged_pairs: the other flagged pairs of a pair's state numbers.
	 *
	 * @param map p_duplication_id; afterwards also p_similar_pairs_to_review (the rows, FlaggedStatenoVOs)
	 */
	public void getSimilarFlaggedPairs(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_get_similar_flagged_pairs",
				param(map.get("p_duplication_id"), Integer.class), textCursor(STATENO_PAIRS));
		map.put("p_similar_pairs_to_review", results.get(1));
	}

	/**
	 * pkg_consolidation.pr_get_bad_merge_flag_history: the bad-merge flag history of a state number.
	 *
	 * @param map p_stateno; afterwards also p_flag_history (the rows, BadlyMergedStatenosVOs), p_code_out (an Integer)
	 *        and p_msg_out
	 */
	public void getBadMergeFlagHistory(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.pr_get_bad_merge_flag_history",
				param(map.get("p_stateno"), String.class), textCursor(BADLY_MERGED_STATENOS), out(Integer.class), out(String.class));
		map.put("p_flag_history", results.get(1));
		map.put("p_code_out", results.get(2));
		map.put("p_msg_out", results.get(3));
	}

	/**
	 * pkg_consolidation.PR_UNMERGE_DOCUMENTS: moves documents from one state number to another.
	 *
	 * @param map p_document_ids, p_from_stateno, p_to_stateno, p_worker_id, p_doccs_document_list; afterwards also
	 *        p_code_out (an Integer) and p_msg_out
	 */
	public void unMergeTheDocuments(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_consolidation.PR_UNMERGE_DOCUMENTS",
				param(map.get("p_document_ids"), String.class), param(map.get("p_from_stateno"), String.class),
				param(map.get("p_to_stateno"), String.class), param(map.get("p_worker_id"), Integer.class),
				out(Integer.class), out(String.class), param(map.get("p_doccs_document_list"), String.class));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/**
	 * pkg_document_management.pr_generate_new_stateno: a new state number.
	 *
	 * @param map p_caller; afterwards also p_new_stateno_out
	 */
	public void createNewStateno(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pkg_document_management.pr_generate_new_stateno",
				param(map.get("p_caller"), String.class), out(String.class));
		map.put("p_new_stateno_out", results.get(1));
	}

	// ---- updates and inserts ------------------------------------------------------------------------------------------

	/**
	 * Sets the follow-up of a flagged pair, by a worker, now (the database's SYSDATE).
	 *
	 * @param inOutParameterMap duplicationId, followUp, workerId
	 * @return the rows changed
	 */
	public int updateMergeFollowUp(Map<Object, Object> inOutParameterMap) {
		return jpql.update("""
				update NyehmsDuplicateStatenos d set d.followup = ?1, d.lastUpdatedBy = ?2, d.lastUpdatedDate = sysdate
				where d.duplicationId = ?3""",
				BoundValues.text(inOutParameterMap.get("followUp")), BoundValues.longValue(inOutParameterMap.get("workerId")),
				BoundValues.longValue(inOutParameterMap.get("duplicationId")));
	}

	/**
	 * Adds a raw case report form for the state number of a rejected pair, copied from the person's latest associated
	 * document (DOCUMENT_PERSON_ATTR; nothing is added when the person has none).
	 *
	 * @param inputParameter DOCUMENT_ID, DOCUMENT_SOURCE, PERSON_COMPLETING, ASSOCIATE_STATENO
	 * @return the rows inserted
	 */
	public int addACRFForRejectedStateno(Map<String, Object> inputParameter) {
		return statements.update(MergeUnMergePersonSql.ADD_ACRF_FOR_REJECTED_STATENO, MergeUnMergePersonSql.addACRFForRejectedStatenoArgs(inputParameter));
	}

	/**
	 * Adds the duplicate-review RIDR row of a case report form (status 2, NY, the highest eHARS state number mapped to
	 * the NYEHMS state number).
	 *
	 * @param inputParameter DOCUMENT_ID, NYEHMS_STATENO
	 */
	public void addACRFDuplicateReviewData(Map<String, Object> inputParameter) {
		jpql.update("""
				insert into RawAcrfRidrInfo (acrfDocId, duplicateStatus, stateCd, comments, stateno)
				select cast(?1 as Long), 2, 'NY', 'Duplicate Review NYEHMS', max(m.eharsStateno)
				from NyehmsEharsStatenoMap m where m.nyehmsStateno = ?2""",
				BoundValues.longValue(inputParameter.get("DOCUMENT_ID")), BoundValues.text(inputParameter.get("NYEHMS_STATENO")));
	}

	// ---- validations and reports (native) ----------------------------------------------------------------------------

	/** validateDodAgainstCollectionDate as the mapper had it; the two IN lists are placeholders. */
	private static final String VALIDATE_DOD_HEAD = """
			WITH doccs_doc AS
			(
			    SELECT document_id
			      FROM nyehms_owner.std_din_doccs_doc
			     WHERE doccs_doc_id IN  (%s)
			), data AS
			(
			SELECT MAX (a.date_of_death) dod, MAX(s.calc_collection_date) hiv_col_date, MAX(e.calc_collection_date) std_col_date
			  FROM nyehms_owner.document_person_attr a,
			       nyehms_owner. person_doc_association p,
			       doccs_doc d,
			       nyehms_owner.std_hiv_eclrs_doc s,
			       nyehms_owner.std_std_eclrs_doc e
			WHERE a.document_id = p.document_id
			   AND p.dissassociation_date IS NULL
			   AND a.document_id = d.document_id(+)
			   AND p.document_id = s.document_id(+)
			   AND p.document_id = e.document_id(+)
			   AND (a.document_id IN (%s)
			        OR p.stateno = %s)
			)
			SELECT 'Y'
			FROM data
			WHERE GREATEST(Nvl(hiv_col_date,To_Date('01/01/1900','MM/DD/YYYY')), Nvl(std_col_date,To_Date('01/01/1900','MM/DD/YYYY'))) > Nvl(dod,To_Date('12/31/2999','MM/DD/YYYY'))
			""";

	/**
	 * 'Y' when a collection date of the documents moved to a state number (or already its) is later than a date of
	 * death among them; null otherwise.
	 *
	 * @param inputParameterMap doccsDocumentIds, unmergeDocuments (arrays of ids; an empty or missing list fails, as
	 *        {@code IN ()} did), stateno_target
	 */
	public String validateDodAgainstCollectionDate(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String doccs = placeholders(inList(inputParameterMap.get("doccsDocumentIds"), "doccsDocumentIds"), parameters);
		String moved = placeholders(inList(inputParameterMap.get("unmergeDocuments"), "unmergeDocuments"), parameters);
		parameters.add(inputParameterMap.get("stateno_target"));
		String sql = String.format(VALIDATE_DOD_HEAD, doccs, moved, "?" + parameters.size());
		return statements.one(sql, Rows.scalar(String.class), parameters.toArray());
	}

	/**
	 * 'Y' when, among the documents of two state numbers, a date of death is earlier than a collection date; null
	 * otherwise.
	 *
	 * @param inputParameterMap stateno1, stateno2
	 */
	public String validateDodAgainstCollectionDateBeforeMerge(Map<String, Object> inputParameterMap) {
		return statements.one(MergeUnMergePersonSql.VALIDATE_DOD_BEFORE_MERGE, Rows.scalar(String.class),
				MergeUnMergePersonSql.validateDodAgainstCollectionDateBeforeMergeArgs(inputParameterMap));
	}

	/** The merge report by origin: the weekly counts of new pairs of the last eight weeks and the pairs now NEW (the map is not used). */
	public List<Map<String, Object>> getMergeReportsByOrigin(Map<String, String> inOutParameterMap) {
		return statements.list(MergeUnMergePersonSql.MERGE_REPORTS_BY_ORIGIN, Rows.map());
	}

	/** The bad-merge report: the weekly counts of new bad merges of the last eight weeks and the ones now NEW (the map is not used). */
	public List<Map<String, Object>> getMergeReportsByBadMerges(Map<String, String> inOutParameterMap) {
		return statements.list(MergeUnMergePersonSql.MERGE_REPORTS_BY_BAD_MERGES, Rows.map());
	}

	/** The merges accepted / rejected per day and worker between two dates (MM/DD/YYYY): WEEKOF, UPDATED_DATE, DAY_NAME, UPDATED_USER, STATUS, COUNT. */
	public List<Map<String, Object>> getMergeReportsByDailyStaff(Map<String, String> inOutParameterMap) {
		return statements.list(MergeUnMergePersonSql.MERGE_REPORTS_BY_DAILY_STAFF, Rows.map(),
				MergeUnMergePersonSql.getMergeReportsByDailyStaffArgs(inOutParameterMap));
	}

	/** The bad merges accepted / rejected per day and worker between two dates (MM/DD/YYYY), as {@link #getMergeReportsByDailyStaff}. */
	public List<Map<String, Object>> getBadMergeReportsByDailyStaff(Map<String, String> inOutParameterMap) {
		return statements.list(MergeUnMergePersonSql.BAD_MERGE_REPORTS_BY_DAILY_STAFF, Rows.map(),
				MergeUnMergePersonSql.getBadMergeReportsByDailyStaffArgs(inOutParameterMap));
	}

	/** The documents that depend on a document (cluster demo documents, resistance documents, RIA and AHI links). */
	public List<Integer> getDependentDocumentList(int documentId) {
		return statements.list(MergeUnMergePersonSql.DEPENDENT_DOCUMENTS, Rows.scalar(Integer.class), documentId);
	}

	// ---- helpers -----------------------------------------------------------------------------------------------------

	/**
	 * An IN parameter bound as MyBatis bound it: with the value's own type (a text for a number parameter is converted
	 * by the database, a java.util.Date bound as a timestamp), a null with the mapper's jdbcType.
	 */
	private static CallParam param(Object value, Class<?> nullType) {
		Object bound = value != null && value.getClass() == java.util.Date.class ? new Timestamp(((java.util.Date) value).getTime()) : value;
		return in(bound, bound == null ? nullType : bound.getClass());
	}

	/** The values of an array (also of a primitive type) or collection for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static List<Object> inList(Object values, String name) {
		List<Object> list = new ArrayList<>();
		if (values instanceof Collection<?> collection) {
			list.addAll(collection);
		} else if (values != null && values.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(values); i++) {
				list.add(Array.get(values, i));
			}
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No values for IN () in " + name + ": " + values);
		}
		return list;
	}

	/** {@code ?n, ?n+1, ...} for the values, which are added to the parameters. */
	private static String placeholders(List<Object> values, List<Object> parameters) {
		StringBuilder text = new StringBuilder();
		for (Object value : values) {
			parameters.add(value);
			text.append(text.length() > 0 ? ", " : "").append('?').append(parameters.size());
		}
		return text.toString();
	}
}
