package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.integer;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues.text;

import java.util.List;
import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NonCaseDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * Non-case documents and persons: a document deleted / discarded or flagged as a non-case (DELETED_DOC_HISTORY with
 * its comments), the persons flagged as non-cases (NYEHMS_NONCASE_STATENOS) and their histories (formerly a MyBatis
 * mapper with the same name and methods), through JPQL over DeletedDocHistory, CodedNonCaseReason,
 * NyehmsNoncaseStatenos, PersonDocAssociation, NyehmsUsers, NyehmsUsersVw and CodedActivityType.
 * <p>
 * Select items carry the former column labels, so the rows fill the VO and the maps as MyBatis filled them
 * ({@link Rows}). The dates the former SQL took from SYSDATE (also the time written into the comments) stay the
 * database's SYSDATE; TO_CHAR(..., 'MM/DD/YYYY HH:MI:SS AM') is {@code format(... as 'MM/dd/yyyy hh:mm:ss a')}. The
 * database functions PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO and FN_TO_DATE are called from JPQL.
 * <p>
 * Native SQL (one statement): {@link #getNonCaseEharsStatenos}, which splits the comma-separated EHARS_STATENO list
 * with Oracle's {@code xmltable(...)} / {@code getstringval()}, which JPQL cannot express; the former SQL unchanged.
 * <p>
 * Kept as before (an open security point): the comments of a non-case document are HTML built from the worker's name
 * and the comments the worker typed, <b>without escaping</b> (stored HTML / script injection).
 */
@Repository
@RequiredArgsConstructor
public class NonCaseDocumentDAO {

	/** The time of a comment: TO_CHAR(SYSDATE, 'MM/DD/YYYY HH:MI:SS AM'). */
	private static final String NOW_TEXT = "format(sysdate as 'MM/dd/yyyy hh:mm:ss a')";

	/** The former createNonCaseDocument: the first comment is the whole COMMENTS. */
	private static final String CREATE = """
			insert into DeletedDocHistory (triggeringDocId, createId, createDate, flagActive, reasonCode, comments)
			values (?1, ?2, sysdate, 'Y', ?3, concat('<div class="nonCaseComment ">(<span class="nonCaseCreateId">', ?4,
					'</span>            <span class="pL30 nonCaseCreateDate">', %s, ')<span class="pL50 nonCaseComments">', ?5,
					'</span></div>'))
			""".formatted(NOW_TEXT);

	/** The former addCommentsToNonCaseDoc: a comment appended. */
	private static final String ADD_COMMENTS = """
			update DeletedDocHistory doc
			set doc.comments = concat(doc.comments, '<div class="nonCaseComment pT20">(<span class="nonCaseCreateId">', ?1,
					'</span>            <span class="pL30 nonCaseCreateDate">', %s, ')<span class="pL50 nonCaseComments">', ?2,
					'</span></div>')
			where doc.triggeringDocId = ?3
			""".formatted(NOW_TEXT);

	private static final String NON_CASE_DOC = """
			select doc.triggeringDocId as TRIGGERING_DOC_ID, format(doc.createDate as 'MM/dd/yyyy hh:mm:ss a') as CREATE_DATE,
					doc.flagActive as FLAG_ACTIVE, doc.comments as COMMENTS, coded.reasonDesc as REASON_DESC,
					pdoc.stateno as STATENO
			from DeletedDocHistory doc
			join CodedNonCaseReason coded on doc.reasonCode = coded.reasonCode
			left join PersonDocAssociation pdoc on doc.triggeringDocId = pdoc.documentId and pdoc.dissassociationDate is null
			where doc.triggeringDocId = ?1
			""";

	private static final String CHANGE_ACTIVE_FLAG = """
			update DeletedDocHistory doc set doc.flagActive = ?1, doc.lastUpdateDate = sysdate, doc.lastUpdateId = ?2
			where doc.triggeringDocId = ?3
			""";

	private static final String REASONS = """
			select reason.reasonCode as REASON_CODE, reason.reasonDesc as REASON_DESC, reason.deleteFlag as FLAG,
					reason.docFlag as DOC_FLAG
			from CodedNonCaseReason reason where reason.docFlag != 'NON_CASE'
			""";

	private static final String FLAG = """
			insert into NyehmsNoncaseStatenos (currentStateno, flaggedStateno, reasonCode, flaggedBy, flaggedDate, nonCaseFlag,
					requestComments, eharsStateno, nycStateno, nycCityno)
			values (?1, ?1, ?2, ?3, sysdate, 'Y', ?4, ?5, ?6, ?7)
			""";

	private static final String UNFLAG = """
			update NyehmsNoncaseStatenos nonCase set nonCase.currentStateno = ?1, nonCase.nonCaseFlag = 'N',
					nonCase.reviewComments = ?2, nonCase.lastUpdatedBy = ?3, nonCase.lastUpdatedDate = sysdate
			where nonCase.currentStateno = ?4 and nonCase.nonCaseFlag = 'Y'
			""";

	private static final String ACTIVE_NON_CASE_COUNT = """
			select count(*) as NON_CASE_COUNT from NyehmsNoncaseStatenos nonCase
			where nonCase.nonCaseFlag = 'Y'
			and function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO' as String, nonCase.currentStateno) = ?1
			""";

	private static final String NON_CASE_STATUS_IN_PDOC = """
			update PersonDocAssociation pdoc set pdoc.discardStatus = ?1,
					pdoc.survAssessStatus = case when pdoc.survAssessStatus = 2 then 3 else pdoc.survAssessStatus end
			where pdoc.documentId = ?2 and pdoc.dissassociationDate is null
			""";

	private static final String DISCARD = """
			insert into DeletedDocHistory (triggeringDocId, createId, createDate, flagActive, reasonCode, comments, nyehmsStateno,
					eharsStateno, nycStateno, nycCityno)
			values (?1, ?2, sysdate, 'Y', ?3, ?4, ?5, ?6, ?7, ?8)
			""";

	private static final String NON_CASE_DOC_HISTORY = """
			select nonCase.currentStateno as CURRENT_STATENO, nonCase.flaggedStateno as ACTUAL_STATENO,
					nonCaseReason.reasonDesc as REASON_DESC,
					(select flagger.fullName from NyehmsUsersVw flagger where flagger.workerId = nonCase.flaggedBy) as FLAGGED_BY,
					format(nonCase.flaggedDate as 'MM/dd/yyyy') as FLAGGED_DATE, nonCase.requestComments as REQUEST_COMMENTS,
					nonCase.reviewComments as REVIEW_COMMENTS,
					(select updater.fullName from NyehmsUsersVw updater where updater.workerId = nonCase.lastUpdatedBy)
						as LAST_UPDATED_BY,
					format(nonCase.lastUpdatedDate as 'MM/dd/yyyy') as LAST_UPDATED_DATE, nonCase.eharsStateno as EHARS_STATENO,
					nonCase.nycStateno as NYC_STATENO, nonCase.nycCityno as NYC_CITYNO
			from NyehmsNoncaseStatenos nonCase
			left join CodedNonCaseReason nonCaseReason on nonCase.reasonCode = nonCaseReason.reasonCode
			where function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO' as String, nonCase.currentStateno) = ?1
			order by nonCase.flaggedDate desc
			""";

	private static final String DEL_DISCARD_HISTORY = """
			select format(doc.createDate as 'MM/dd/yyyy') as CREATE_DATE, doc.comments as COMMENTS,
					coded.reasonDesc as REASON_DESC,
					case when coded.deleteFlag = 'Y' then 'Deleted' else 'Discarded' end as DEL_DISCARD,
					(select vw.fullName from NyehmsUsersVw vw where vw.workerId = nu.workerId) as CREATE_ID
			from DeletedDocHistory doc, CodedNonCaseReason coded, NyehmsUsers nu
			where doc.triggeringDocId = ?1 and doc.flagActive = 'Y'
			and doc.reasonCode = coded.reasonCode
			and nu.workerId = doc.createId
			""";

	/** The former getNonCaseEharsStatenos up to its &lt;choose&gt; (native: xmltable / getstringval). */
	private static final String EHARS_STATENOS = """
			select
			CURRENT_STATENO, usr.LAST_NAME || ', ' || usr.FIRST_NAME FLAGGED_BY, TO_CHAR(FLAGGED_DATE, 'MM/DD/YYYY') FLAGGED_DATE, REQUEST_COMMENTS, ehars.EHARS_STATENO, REASON_DESC
			  FROM nyehms_noncase_statenos a, xmltable(('"'||Replace(a.ehars_stateno, ', ', '","')||'"')), NYEHMS_EHARS_STATENO_MAP ehars, CODED_NON_CASE_REASON reason, nyehms_users usr
			 WHERE (column_value).getstringval() = ehars.EHARS_STATENO and NON_CASE_FLAG = 'Y' and reason.REASON_CODE = a.REASON_CODE and usr.worker_id = FLAGGED_BY
			""";

	private static final String FLAGGED_HISTORY = """
			select nonCase.currentStateno as stateno, nonCase.eharsStateno as EHARS_STATENO, nonCase.nycStateno as NYC_STATENO,
					nonCase.nycCityno as NYC_CITYNO, nonCase.nonCaseFlag as NON_CASE_FLAG, reason.reasonDesc as REASON_DESC,
					nonCase.requestComments as REQUEST_COMMENTS, concat(usr1.lastName, ', ', usr1.firstName) as FLAGGED_BY,
					format(nonCase.flaggedDate as 'MM/dd/yyyy') as FLAGGED_DATE, nonCase.reviewComments as REVIEW_COMMENTS,
					case when nonCase.lastUpdatedBy is not null then concat(usr2.lastName, ', ', usr2.firstName) else null end
						as LAST_UPDATED_BY,
					format(nonCase.lastUpdatedDate as 'MM/dd/yyyy') as LAST_UPDATED_DATE
			from NyehmsNoncaseStatenos nonCase
			join CodedNonCaseReason reason on nonCase.reasonCode = reason.reasonCode
			left join NyehmsUsers usr1 on nonCase.flaggedBy = usr1.workerId
			left join NyehmsUsers usr2 on nonCase.lastUpdatedBy = usr2.workerId
			where nonCase.nonCaseFlag = ?1
			""";

	private static final String FLAGGED_HISTORY_DATES = """
			and nonCase.flaggedDate between function('FN_TO_DATE' as Timestamp, ?2, 'MM/DD/YYYY')
					and function('FN_TO_DATE' as Timestamp, ?3, 'MM/DD/YYYY') + 1 day
			""";

	private static final String FLAGGED_HISTORY_ORDER = "order by nonCase.currentStateno, nonCase.flaggedDate asc";

	private static final String REASON_CODE_FOR_NON_CASE = """
			select reason.reasonCode as REASON_CODE, reason.docFlag as NON_CASE_FLAG
			from CodedActivityType activity, CodedNonCaseReason reason
			where activity.activityTypeCode = ?1 and reason.reasonDesc like concat('%', activity.activityTypeDescription)
			""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/**
	 * Flags a document as a non-case: its history row, with the first comment (HTML: the worker's name, the time, the
	 * comments, unescaped as before).
	 *
	 * @param inputParameterMap triggeringDocId, workerId, reason, workerFullName, comments
	 * @return 1
	 */
	@Transactional
	public int createNonCaseDocument(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		return jpql.update(CREATE, integer(m.get("triggeringDocId")), longValue(m.get("workerId")), text(m.get("reason")),
				text(m.get("workerFullName")), text(m.get("comments")));
	}

	/**
	 * A non-case document with its reason and the STATENO of its current person match.
	 *
	 * @return the document, or null if there is none (or its reason is not in CODED_NON_CASE_REASON)
	 * @throws IncorrectResultSizeDataAccessException if there are several rows (history rows or current matches), as
	 *         before
	 */
	public NonCaseDocVO getNonCaseDoc(int docId) {
		return jpql.one(NON_CASE_DOC, Rows.bean(NonCaseDocVO.class), docId);
	}

	/**
	 * Appends a comment (HTML, unescaped as before) to the history rows of a document.
	 *
	 * @param inputParameterMap triggeringDocId, comments, workerFullName
	 */
	@Transactional
	public void addCommentsToNonCaseDoc(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		jpql.update(ADD_COMMENTS, text(m.get("workerFullName")), text(m.get("comments")), integer(m.get("triggeringDocId")));
	}

	/**
	 * Sets FLAG_ACTIVE of the history rows of a document, with the worker and the time.
	 *
	 * @param inputParameterMap triggeringDocId, flagActive, workerId
	 */
	@Transactional
	public void changeNonCaseActiveFlagStatus(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		jpql.update(CHANGE_ACTIVE_FLAG, text(m.get("flagActive")), longValue(m.get("workerId")), integer(m.get("triggeringDocId")));
	}

	/** The delete / discard reasons (not the non-case ones): maps of REASON_CODE, REASON_DESC, FLAG, DOC_FLAG. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getNonCaseReasons() {
		return (List) jpql.list(REASONS, Rows.map());
	}

	/**
	 * Flags a person as a non-case.
	 *
	 * @param inOutParameterMap p_stateno, p_reason, p_flagged_by, p_request_comments, p_eharsStateno, p_nycStateno,
	 *        p_cityno
	 * @return 1
	 */
	@Transactional
	public int flagAsNonCase(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> m = inOutParameterMap;
		return jpql.update(FLAG, text(m.get("p_stateno")), text(m.get("p_reason")), longValue(m.get("p_flagged_by")),
				text(m.get("p_request_comments")), text(m.get("p_eharsStateno")), text(m.get("p_nycStateno")),
				text(m.get("p_cityno")));
	}

	/**
	 * Unflags a person flagged as a non-case.
	 *
	 * @param inOutParameterMap p_current_stateno (the new current STATENO), p_review_comments, p_worker_id,
	 *        p_flagged_stateno
	 * @return the number of rows changed
	 */
	@Transactional
	public int unflagAsNonCase(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> m = inOutParameterMap;
		return jpql.update(UNFLAG, text(m.get("p_current_stateno")), text(m.get("p_review_comments")),
				longValue(m.get("p_worker_id")), text(m.get("p_flagged_stateno")));
	}

	/** The number of active non-case flags of a person (by its current STATENO). */
	public int checkForActiveNonCaseStateno(String stateno) {
		return jpql.one(ACTIVE_NON_CASE_COUNT, Rows.scalar(Integer.class), stateno);
	}

	/**
	 * Sets DISCARD_STATUS of the current person match of a document, and its surveillance assessment status 2 to 3.
	 *
	 * @param inputParameterMap triggeringDocId, flagActive
	 * @return the number of rows changed
	 */
	@Transactional
	public int updateNonCaseStatusInPDOC(Map<String, Object> inputParameterMap) {
		return jpql.update(NON_CASE_STATUS_IN_PDOC, text(inputParameterMap.get("flagActive")),
				integer(inputParameterMap.get("triggeringDocId")));
	}

	/**
	 * Adds the history row of a discarded document (the comments as they are).
	 *
	 * @param inputParameterMap triggeringDocId, workerId, reason, comments, NYEHMS_STATENO, EHARS_STATENO, NYC_STATENO,
	 *        NYC_CITYNO
	 * @return 1
	 */
	@Transactional
	public int discardNonCaseDocument(Map<String, Object> inputParameterMap) {
		Map<String, Object> m = inputParameterMap;
		return jpql.update(DISCARD, integer(m.get("triggeringDocId")), longValue(m.get("workerId")), text(m.get("reason")),
				text(m.get("comments")), text(m.get("NYEHMS_STATENO")), text(m.get("EHARS_STATENO")), text(m.get("NYC_STATENO")),
				text(m.get("NYC_CITYNO")));
	}

	/** The non-case flags of a person (by its current STATENO), newest first. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getNonCaseDocHistory(String stateNo) {
		return (List) jpql.list(NON_CASE_DOC_HISTORY, Rows.map(), stateNo);
	}

	/**
	 * The active delete / discard row of a document: CREATE_DATE, COMMENTS, REASON_DESC, DEL_DISCARD ('Deleted' /
	 * 'Discarded'), CREATE_ID (the worker's full name).
	 *
	 * @return the row, or null
	 * @throws IncorrectResultSizeDataAccessException if there are several, as before
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getDocDelDiscardHistory(int documentId) {
		return (Map) jpql.one(DEL_DISCARD_HISTORY, Rows.map(), documentId);
	}

	/**
	 * The active non-case persons known in eHARS, one row per eHARS STATENO of their list: with the perinatal exposure
	 * reasons (29, 30) only when p_nonCasePernitalExpOnly is "Yes", otherwise without them.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getNonCaseEharsStatenos(Map<String, String> inputParameterMap) {
		String reasons = "Yes".equals(inputParameterMap.get("p_nonCasePernitalExpOnly"))
				? " and reason.reason_code in ('29', '30')"
				: " and reason.reason_code not in ('29', '30')";
		return (List) statements.list(EHARS_STATENOS + reasons, Rows.map());
	}

	/**
	 * The persons with the given non-case flag, flagged between FROM_DATE and TO_DATE (MM/DD/YYYY, TO_DATE inclusive)
	 * when FROM_DATE is given; by current STATENO and flag date.
	 *
	 * @param inputParameterMap ACTIVE_FLAG, FROM_DATE, TO_DATE
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getNonCaseFlaggedHistory(Map<String, String> inputParameterMap) {
		String from = inputParameterMap.get("FROM_DATE");
		if (from != null && !from.equals("")) {
			return (List) jpql.list(FLAGGED_HISTORY + FLAGGED_HISTORY_DATES + FLAGGED_HISTORY_ORDER, Rows.map(),
					inputParameterMap.get("ACTIVE_FLAG"), from, inputParameterMap.get("TO_DATE"));
		}
		return (List) jpql.list(FLAGGED_HISTORY + FLAGGED_HISTORY_ORDER, Rows.map(), inputParameterMap.get("ACTIVE_FLAG"));
	}

	/**
	 * The non-case reason of a closure (activity) type: REASON_CODE and NON_CASE_FLAG of the reason whose description
	 * ends with the activity type's description.
	 *
	 * @return the row, or null
	 * @throws IncorrectResultSizeDataAccessException if several reasons match, as before
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<Object, Object> getReasonCodeForNonCase(String colsureCategory) {
		return (Map) jpql.one(REASON_CODE_FOR_NON_CASE, Rows.map(), colsureCategory);
	}
}
