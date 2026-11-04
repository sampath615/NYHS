package gov.ny.state.doh.bhae.nyehms.web.spring.dao.review;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedCounty;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsStdMessageSent;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.PsMessageComments;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedCountyRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsStdMessageSentRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.PsMessageCommentsRepository;
import lombok.RequiredArgsConstructor;

/**
 * The partner services (PS) message review screen and the PS message reports (formerly a MyBatis mapper with the
 * same name and methods).
 * <p>
 * The message changes, the comments and the county list go through Spring Data repositories over the entities of
 * {@code entity.common} (load, change, save; the comment list joined, de-duplicated and sorted here as the former
 * UNION did). The review list, the weekly/monthly report and the two report procedures stay SQL (native queries /
 * procedure calls, the SQL unchanged, values bound): see each method for why.
 * <p>
 * The dates the former SQL set with SYSDATE are the application server's clock (to the second, as an Oracle DATE).
 */
@Repository
@RequiredArgsConstructor
public class PSMessageReviewDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY HH:MI:SS AM') */
	private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);

	// native: the document types the worker may see come from the pipelined PL/SQL function
	// TABLE(get_user_permissions(...)), which HQL cannot select from
	private static final String REVIEW_DOCUMENTS = """
			select sent.DOCUMENT_ID msgDocId, TRIGGER_MSG_TYPE messageType,
			FOLLOW_UP_DOC_ID documentId, STATENO stateno, FNAME firstName, LNAME
			lastName, DOB dateOfBirth,
			PERSON_ADDRESS || ', ' || PERSON_CITY || ', ' || PERSON_STATE || ', ' || PERSON_ZIP
			address,
			pkg_trk_common.fn_get_fullname (PROV_LNAME, PROV_FNAME, NULL,NULL) as providerName,
			PROV_ADDRESS || ', ' || PROV_CITY || ', ' || PROV_STATE || ', ' || PROV_ZIP AS
			providerAddress,
			TO_CHAR(CREATE_DATE, 'MM/DD/YYYY') createDate, PROCESS_IND processInd
			from NYEHMS_STD_MESSAGE_SENT sent, document_locator doc where
			doc.document_id = sent.follow_up_doc_id
			 and doc.document_type_code IN (SELECT *
			                             FROM TABLE(get_user_permissions(?1, ?2)))
			                             and
			""";

	// native: Oracle date arithmetic on TRUNC(..., 'IW') (the ISO week), TO_CHAR(..., 'Month'), TO_DATE and the
	// analytic sums over chained CTEs have no HQL form (HQL does not allow date - number)
	private static final String REPORT_START = """
			WITH new900 AS
			(
			    SELECT Distinct a.stateno
			           , a.person_county
			           , Trunc(a.create_date) create_date
			           , To_Char(Trunc(a.create_date, 'IW') - 1, 'MM/DD/YYYY')||' - '||To_Char(Trunc(a.create_date, 'IW') + 5, 'MM/DD/YYYY') create_week
			           , Trim(To_Char(a.create_date, 'Month'))||' '||Extract(YEAR FROM a.create_date) create_month
			      FROM nyehms_std_message_sent a
			      JOIN coded_ps_msg_type c ON (a.trigger_msg_type = c.ps_msg_type)
			     WHERE c.include_new900_count = 'Y'
			       AND (
			""";

	private static final String REPORT_MONTHLY_DATES = """
			(Trunc(a.create_date) BETWEEN To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY'))
			""";

	private static final String REPORT_WEEKLY_DATES = """
			(Trunc(a.create_date) BETWEEN Trunc(To_Date(?1, 'MM/DD/YYYY'), 'IW') - 1 AND Trunc(To_Date(?2, 'MM/DD/YYYY'), 'IW') + 5)
			""";

	private static final String REPORT_COUNTIES = """
			      )
			  ORDER BY create_date
			), new900county AS
			(
			    SELECT Distinct a.stateno,
			           CASE
			                WHEN a.person_county IN ('031', '041')
			                THEN 'Essex and Hamilton'
			                WHEN a.person_county IN ('097', '123')
			                THEN 'Schuyler and Yates'
			                WHEN a.person_county IN ('049', '089')
			                THEN 'Lewis and St. Lawrence'
			                ELSE Initcap(b.county_name)
			             END county,
			             a.create_week,
			             a.create_month,
			             a.create_date
			      FROM new900 a, coded_county b
			     WHERE a.person_county = b.county_fips_code
			 ORDER BY create_date
			), final AS
			(
			""";

	private static final String REPORT_MONTHLY = """
			SELECT Distinct
			         'MONTHLY' report
			          ,create_date
			         , county
			         , create_month metric
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY county, create_month) group_count
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY county) county_total
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY create_month) metric_total
			    FROM new900county
			""";

	private static final String REPORT_WEEKLY = """
			SELECT Distinct
			         'WEEKLY' report
			          ,create_date
			         , county
			         , create_week metric
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY county, create_week) group_count
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY county) county_total
			         , SUM(CASE WHEN stateno IS NULL THEN 0 ELSE 1 END) OVER (PARTITION BY create_week) metric_total
			    FROM new900county
			""";

	private static final String REPORT_END = """
			    ORDER BY create_date
			)
			SELECT report, county, metric, group_count, county_total, metric_total
			  FROM final
			""";

	private final NativeStatements statements;
	private final JpqlStatements jpql;
	private final NyehmsStdMessageSentRepository messages;
	private final PsMessageCommentsRepository comments;
	private final NyehmsUsersRepository users;
	private final CodedCountyRepository counties;

	/**
	 * The PS messages of the review grid (JSONObjects with the upper-case keys MSGDOCID, MESSAGETYPE, DOCUMENTID,
	 * STATENO, FIRSTNAME, LASTNAME, DATEOFBIRTH, ADDRESS, PROVIDERNAME, PROVIDERADDRESS, CREATEDATE, PROCESSIND), for
	 * the document types the worker may see. The condition follows the former {@code <choose>}: RESOLVED_STATUS 'YES'
	 * = the reviewed messages; RESOLVED_STATUS 'No' with PARAMETER_NAME LAFT_NEEDS_REVIEWED or one of the MSG_TYPE_NAMEs
	 * = the open messages of that kind; otherwise all open messages.
	 *
	 * @param map RESOLVED_STATUS, PARAMETER_NAME, MSG_TYPE_NAME (texts, may be missing), p_module_id, p_worker_id
	 */
	public List<JSONObject> getPsMessageReviewDocuments(Map<String, Object> map) {
		Object resolved = map.get("RESOLVED_STATUS");
		Object parameterName = map.get("PARAMETER_NAME");
		Object messageType = map.get("MSG_TYPE_NAME");
		String condition;
		if ("YES".equals(resolved)) {
			condition = "REVIEW_DATE is not null and nvl(REVIEW_UID, 'X') != '900'";
		} else if ("LAFT_NEEDS_REVIEWED".equals(parameterName) && "No".equals(resolved)) {
			condition = "PROCESS_IND = 'R' and LAFT_REVIEW_BY is null and TRIGGER_MSG_TYPE = '900  MS Incomp'";
		} else if ("PRF_PS_REQUEST".equals(messageType) && "No".equals(resolved)) {
			condition = "PROCESS_IND = 'R' and TRIGGER_MSG_TYPE = 'PRF PS Request'";
		} else if ("900_MS_INCOMP".equals(messageType) && "No".equals(resolved)) {
			condition = "PROCESS_IND = 'R' and TRIGGER_MSG_TYPE = '900  MS Incomp'";
		} else if ("POSSIBLE_AHI".equals(messageType) && "No".equals(resolved)) {
			condition = "PROCESS_IND = 'R' and TRIGGER_MSG_TYPE = 'Possible AHI'";
		} else {
			condition = "PROCESS_IND = 'R'";
		}
		return statements.list(REVIEW_DOCUMENTS + condition, Rows.json(), map.get("p_module_id"), map.get("p_worker_id"));
	}

	/**
	 * Deletes the open messages (PROCESS_IND 'R') of a triggering document: PROCESS_IND 'R_X', DELETE_IND 'D',
	 * reviewed by the worker now.
	 *
	 * @param map p_trigger_doc_id, p_worker_id
	 * @return the number of messages changed
	 */
	@Transactional
	public int deletePsMessage(Map<Object, Object> map) {
		List<NyehmsStdMessageSent> open = openMessages(map.get("p_trigger_doc_id"));
		Timestamp now = now();
		open.forEach(message -> message.delete(text(map.get("p_worker_id")), now));
		messages.flush();
		return open.size();
	}

	/**
	 * Processes the open messages (PROCESS_IND 'R') of a triggering document: PROCESS_IND 'Y', reviewed by the worker
	 * now.
	 *
	 * @param map p_trigger_doc_id, p_worker_id
	 * @return the number of messages changed
	 */
	@Transactional
	public int processPsMessage(Map<Object, Object> map) {
		List<NyehmsStdMessageSent> open = openMessages(map.get("p_trigger_doc_id"));
		Timestamp now = now();
		open.forEach(message -> message.process(text(map.get("p_worker_id")), now));
		messages.flush();
		return open.size();
	}

	/**
	 * Adds a comment to a message, dated now.
	 *
	 * @param map p_documentId (the message's document id), p_comments, p_workerId
	 */
	@Transactional
	public void savePSMessageComments(Map<Object, Object> map) {
		jpql.insert(comment(longValue(map.get("p_documentId")), map.get("p_comments"), map.get("p_workerId"), now()));
	}

	/**
	 * Saves the LAFT review of a message (comments, reasons, reviewer, now).
	 *
	 * @param map p_documentId (the message's document id), p_comments, p_reason, p_workerId
	 */
	@Transactional
	public void saveLAFTMessageComments(Map<Object, Object> map) {
		Long documentId = longValue(map.get("p_documentId"));
		if (documentId == null) {
			return;                                             // DOCUMENT_ID = NULL changed nothing
		}
		messages.findById(documentId).ifPresent(message -> message.laftReview(text(map.get("p_comments")), text(map.get("p_reason")),
				longValue(map.get("p_workerId")), now()));
		messages.flush();
	}

	/**
	 * The comments of a message and its LAFT review, each with its writer: maps with COMMENTS, CREATE_ID (the writer's
	 * "last,first") and CREATE_DATE (text MM/DD/YYYY HH:MI:SS AM); null values are left out of the maps. As the former
	 * UNION: only comments whose writer is a known worker, duplicates removed, ordered by the CREATE_DATE text (not by
	 * the date; no date last), equal texts by COMMENTS and CREATE_ID.
	 */
	@Transactional(readOnly = true)
	public List<Map<String, String>> getPSMessageComments(Integer dOCUMENT_ID) {
		Long documentId = longValue(dOCUMENT_ID);
		if (documentId == null) {
			return new ArrayList<>();                           // document_id = NULL found nothing
		}
		List<PsMessageCommentsRepository.Comment> messageComments = comments.findCommentsByDocumentId(documentId);
		List<NyehmsStdMessageSentRepository.LaftReview> laftReviews = messages.findLaftReviewsByDocumentId(documentId);
		Set<Long> writers = new LinkedHashSet<>();
		messageComments.forEach(comment -> writers.add(comment.createId()));
		laftReviews.forEach(review -> writers.add(review.laftReviewBy()));
		writers.remove(null);
		Map<Long, List<NyehmsUsersRepository.WorkerName>> names = new HashMap<>();
		if (!writers.isEmpty()) {
			users.findNamesByWorkerIdIn(writers).forEach(name -> names.computeIfAbsent(name.workerId(), id -> new ArrayList<>()).add(name));
		}
		Set<CommentLine> lines = new LinkedHashSet<>();
		for (PsMessageCommentsRepository.Comment comment : messageComments) {
			for (NyehmsUsersRepository.WorkerName name : names.getOrDefault(comment.createId(), List.of())) {
				lines.add(new CommentLine(comment.comments(), fullName(name), dateTime(comment.createDate())));
			}
		}
		for (NyehmsStdMessageSentRepository.LaftReview review : laftReviews) {
			for (NyehmsUsersRepository.WorkerName name : names.getOrDefault(review.laftReviewBy(), List.of())) {
				lines.add(new CommentLine(concat(review.laftComments(), "; ", review.laftReasons()), fullName(name),
						dateTime(review.laftReviewDate())));
			}
		}
		Comparator<String> text = Comparator.nullsLast(Comparator.naturalOrder());
		List<Map<String, String>> results = new ArrayList<>();
		lines.stream().sorted(Comparator.comparing(CommentLine::createDate, text).thenComparing(CommentLine::comments, text)
				.thenComparing(CommentLine::createId, text)).forEach(line -> results.add(line.toMap()));
		return results;
	}

	/**
	 * Adds the review comment to every open message (PROCESS_IND 'R') of a triggering document, dated now.
	 *
	 * @param map p_trigger_doc_id, p_comments, p_worker_id
	 */
	@Transactional
	public void updateReviewComments(Map<Object, Object> map) {
		Timestamp now = now();
		for (NyehmsStdMessageSent message : openMessages(map.get("p_trigger_doc_id"))) {
			jpql.insert(comment(message.getDocumentId(), map.get("p_comments"), map.get("p_worker_id"), now));
		}
	}

	/**
	 * The county names of the PS message report, in PS_SORT_ORDER: three pairs of counties are reported together,
	 * the others by their name with initial capitals (INITCAP); each name and sort order once.
	 */
	@Transactional(readOnly = true)
	public List<String> getCountyCodes() {
		Set<List<Object>> names = new LinkedHashSet<>();
		for (CodedCounty county : counties.findAll(Sort.by("psSortOrder"))) {
			names.add(java.util.Arrays.asList(reportCountyName(county), county.getPsSortOrder()));
		}
		List<String> results = new ArrayList<>();
		names.forEach(name -> results.add((String) name.get(0)));
		return results;
	}

	/**
	 * The new 900 PS message counts by county and week (or month, p_reportType 'Monthly'): maps with REPORT, COUNTY,
	 * METRIC, GROUP_COUNT, COUNTY_TOTAL, METRIC_TOTAL.
	 *
	 * @param map p_fromDate, p_toDate (texts MM/DD/YYYY), p_reportType
	 */
	public List<Map<String, Object>> getPsMessageReport(Map<Object, Object> map) {
		boolean monthly = "Monthly".equals(map.get("p_reportType"));
		String sql = REPORT_START + (monthly ? REPORT_MONTHLY_DATES : REPORT_WEEKLY_DATES) + REPORT_COUNTIES
				+ (monthly ? REPORT_MONTHLY : REPORT_WEEKLY) + REPORT_END;
		return statements.list(sql, Rows.map(), NativeStatements.text(map.get("p_fromDate")), NativeStatements.text(map.get("p_toDate")));
	}

	/**
	 * The PS disposition report: calls {@code ps_disposition_report} (the report is built in PL/SQL) and puts its rows
	 * (maps) into the map as {@code p_ps_disp_report}, as the former CALLABLE statement did.
	 *
	 * @param map fromDate, toDate, messageTypes, messageCounties, messageDispositions (texts)
	 */
	public void getPSDispositionReport(Map<String, Object> map) {
		List<Object> results = statements.call("ps_disposition_report",
				in(map.get("fromDate"), String.class),
				in(map.get("toDate"), String.class),
				in(map.get("messageTypes"), String.class),
				in(map.get("messageCounties"), String.class),
				in(map.get("messageDispositions"), String.class),
				cursor(Rows.map()));
		map.put("p_ps_disp_report", results.get(5));
	}

	/**
	 * The PS out-of-care exposure report: calls {@code ps_ooc_exps_report} (built in PL/SQL) and puts its rows (maps)
	 * into the map as {@code p_ooc_exps_report}, as the former CALLABLE statement did.
	 *
	 * @param map fromDate, toDate, messageCounties (texts)
	 */
	public void getPSOOCExpsReport(Map<String, Object> map) {
		List<Object> results = statements.call("ps_ooc_exps_report",
				in(map.get("fromDate"), String.class),
				in(map.get("toDate"), String.class),
				in(map.get("messageCounties"), String.class),
				cursor(Rows.map()));
		map.put("p_ooc_exps_report", results.get(3));
	}

	/** A line of the comment list (the former UNION's columns). */
	private record CommentLine(String comments, String createId, String createDate) {

		Map<String, String> toMap() {
			Map<String, String> row = new HashMap<>();
			if (comments != null) row.put("COMMENTS", comments);
			if (createId != null) row.put("CREATE_ID", createId);
			if (createDate != null) row.put("CREATE_DATE", createDate);
			return row.isEmpty() ? null : row;
		}
	}

	/** The open messages of a triggering document (FOLLOW_UP_DOC_ID = NULL found none). */
	private List<NyehmsStdMessageSent> openMessages(Object triggerDocId) {
		Long followUpDocId = longValue(triggerDocId);
		return followUpDocId == null ? new ArrayList<>() : messages.findByFollowUpDocIdAndProcessInd(followUpDocId, "R");
	}

	private static PsMessageComments comment(Long documentId, Object comments, Object workerId, Timestamp now) {
		return PsMessageComments.builder().documentId(documentId).comments(text(comments)).createId(longValue(workerId)).createDate(now).build();
	}

	/** LAST_NAME || ',' || FIRST_NAME */
	private static String fullName(NyehmsUsersRepository.WorkerName name) {
		return concat(name.lastName(), ",", name.firstName());
	}

	/** Oracle's || : a null text counts as empty. */
	private static String concat(String first, String separator, String second) {
		return Objects.toString(first, "") + separator + Objects.toString(second, "");
	}

	private static String dateTime(Timestamp time) {
		return time == null ? null : DATE_TIME.format(time.toLocalDateTime());
	}

	/** The former CASE on COUNTY_FIPS_CODE, else INITCAP(COUNTY_NAME). */
	private static String reportCountyName(CodedCounty county) {
		String fips = county.getCountyFipsCode();
		if ("031".equals(fips) || "041".equals(fips)) {
			return "Essex and Hamilton";
		}
		if ("097".equals(fips) || "123".equals(fips)) {
			return "Schuyler and Yates";
		}
		if ("049".equals(fips) || "089".equals(fips)) {
			return "Lewis and St. Lawrence";
		}
		return initcap(county.getCountyName());
	}

	/** Oracle INITCAP: the first letter of each word upper case, the others lower case; words end at non-alphanumerics. */
	private static String initcap(String text) {
		if (text == null) {
			return null;
		}
		StringBuilder result = new StringBuilder(text.length());
		boolean start = true;
		for (char c : text.toCharArray()) {
			result.append(start ? Character.toUpperCase(c) : Character.toLowerCase(c));
			start = !Character.isLetterOrDigit(c);
		}
		return result.toString();
	}

	/** SYSDATE: the application server's clock, to the second. */
	private static Timestamp now() {
		return Timestamp.from(Instant.now().truncatedTo(ChronoUnit.SECONDS));
	}
}
