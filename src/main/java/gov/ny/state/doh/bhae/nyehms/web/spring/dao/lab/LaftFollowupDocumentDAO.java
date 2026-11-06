package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.id;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.now;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftFollowupDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab.NyehmsFlagLaftFollowup;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.CodedLaftFollowupReasonRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.CodedLaftFollowupResolutionRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.NyehmsFlagLaftFollowupRepository;
import lombok.RequiredArgsConstructor;

/**
 * The documents flagged for LAFT follow-up, their comments and resolution (formerly a MyBatis mapper with the same name
 * and methods), through Spring Data repositories and JPQL over the entities of {@code entity.lab} (the former Oracle
 * {@code (+)} joins are JPQL left joins).
 * <p>
 * Dates are shown as the former TO_CHAR(..., 'MM/DD/YYYY') showed them; the dates the former SQL set with SYSDATE are
 * the application server's clock. <b>Bug kept:</b> the follow-up details of a document are sorted by the MM/DD/YYYY
 * texts of the resolution and create dates (a text sort, not a date sort), as before.
 */
@Repository
@RequiredArgsConstructor
public class LaftFollowupDocumentDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY HH:MI:SS AM') */
	private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);

	/** The follow-up columns of the screens' VO, aliased as the former labels (resolution columns appended where read). */
	private static final String FOLLOWUP_COLUMNS = """
			f.followupIdPk as followupidpk, f.documentId as documentid, pdc.stateno as stateno, f.reasonId as reasonid,
			frea.reasonDesc as reasondesc, f.createUid as createuid, usr.fullName as createname, format(f.createDate as 'MM/dd/yyyy') as createdate""";

	private static final String RESOLUTION_COLUMNS = """
			, f.resolutionId as resolutionid, fres.resolutionDesc as resolutiondesc, f.resolutionUid as resolutionuid, usr2.fullName as resolvername,
			format(f.resolutionDate as 'MM/dd/yyyy') as resolutiondate""";

	private static final String RESOLVED_JOINS = """
			from NyehmsFlagLaftFollowup f
			join PersonDocAssociation pdc on f.documentId = pdc.documentId
			left join NyehmsUsersVw usr on f.createUid = usr.workerId
			left join CodedLaftFollowupReason frea on f.reasonId = frea.reasonId
			left join CodedLaftFollowupResolution fres on f.resolutionId = fres.resolutionId
			left join NyehmsUsersVw usr2 on f.resolutionUid = usr2.workerId
			""";

	/** Bug kept: ordered by the date texts (MM/DD/YYYY), descending. */
	private static final String FOLLOWUP_DETAILS = "select distinct " + FOLLOWUP_COLUMNS + RESOLUTION_COLUMNS + "\n" + RESOLVED_JOINS + """
			where f.documentId = ?1 and pdc.associationStatusCode not in ('DISASSOC_UNMRG', 'DISASSOC_MRG')
			order by resolutiondate desc, createdate desc""";

	private static final String FLAGGED_FOLLOWUPS = "select distinct " + FOLLOWUP_COLUMNS + """

			from NyehmsFlagLaftFollowup f
			join PersonDocAssociation pdc on f.documentId = pdc.documentId
			left join NyehmsUsersVw usr on f.createUid = usr.workerId
			left join CodedLaftFollowupReason frea on f.reasonId = frea.reasonId
			where f.resolutionId is null and pdc.dissassociationDate is null""";

	private static final String RESOLVED_FOLLOWUPS = "select " + FOLLOWUP_COLUMNS + RESOLUTION_COLUMNS + "\n" + RESOLVED_JOINS + """
			where f.resolutionId is not null and pdc.associationStatusCode not in ('DISASSOC_UNMRG', 'DISASSOC_MRG')""";

	private static final String RESOLVE_AND_COMMENTS = """
			select nf.documentId as DOCUMENT_ID, nf.stateno as STATENO, nu.emailAddr as EMAIL_ADDR
			from NyehmsFlagLaftFollowup nf left join NyehmsUsersVw nu on nf.createUid = nu.workerId
			where nf.followupIdPk = ?1""";

	/** The parts of a comment line: the worker's name, the comment date, the comment (the users' view joined as before). */
	private static final String COMMENTS = """
			select usr.fullName, fc.commentDate, fc.comments
			from NyehmsLaftFollowupComments fc left join NyehmsUsersVw usr on fc.laftUser = usr.workerId
			where fc.followupIdPk = ?1 and %s
			order by fc.commentDate desc""";

	private static final String INSERT_COMMENT = """
			insert into NyehmsLaftFollowupComments (followupIdPk, comments, laftUser, commentDate, initialComment)
			values (?1, ?2, ?3, ?4, ?5)""";

	private final JpqlStatements jpql;
	private final NyehmsFlagLaftFollowupRepository followups;
	private final CodedLaftFollowupReasonRepository reasons;
	private final CodedLaftFollowupResolutionRepository resolutions;

	/** The follow-up reasons: REASON_ID, REASON_DESC. */
	public List<Map<String, String>> getLaftFollowupReasons() {
		List<Map<String, String>> rows = new ArrayList<>();
		reasons.findAll().forEach(reason -> rows.add(codeRow("REASON_ID", reason.getReasonId(), "REASON_DESC", reason.getReasonDesc())));
		return rows;
	}

	/**
	 * Flags a document for LAFT follow-up (FLAGGED_DOC_ID and DOCUMENT_ID are both the triggering document, created now)
	 * and puts the generated FOLLOWUP_ID_PK into the map as "followupId" (a BigDecimal, as the former generated-key read
	 * returned the NUMBER column).
	 *
	 * @param inputParameterMap triggeringDocId, triggeringStateno, reason, workerId
	 */
	@Transactional
	public int flagLaftFollowupDocument(Map<String, Object> inputParameterMap) {
		NyehmsFlagLaftFollowup followup = followups.saveAndFlush(NyehmsFlagLaftFollowup.builder()
				.flaggedDocId(id(inputParameterMap.get("triggeringDocId")))
				.documentId(id(inputParameterMap.get("triggeringDocId")))
				.stateno(text(inputParameterMap.get("triggeringStateno")))
				.reasonId(id(inputParameterMap.get("reason")))
				.createUid(id(inputParameterMap.get("workerId")))
				.createDate(now())
				.build());
		inputParameterMap.put("followupId", followup.getFollowupIdPk() == null ? null : BigDecimal.valueOf(followup.getFollowupIdPk()));
		return 1;
	}

	/** The number of the document's follow-ups that are not resolved. */
	public int isLaftFollowupActive(int documentId) {
		return (int) followups.countByDocumentIdAndResolutionIdIsNull((long) documentId);
	}

	/** The follow-ups of a document (current associations only), sorted by the date texts as before (bug kept). */
	public List<LaftFollowupDetailsVO> getLAFTFollowupDetails(int documentId) {
		return jpql.list(FOLLOWUP_DETAILS, Rows.bean(LaftFollowupDetailsVO.class), (long) documentId);
	}

	/** The follow-ups that are not resolved, of currently associated documents. */
	public List<LaftFollowupDetailsVO> getFlaggedLaftFollowupData() throws DataAccessException {
		return jpql.list(FLAGGED_FOLLOWUPS, Rows.bean(LaftFollowupDetailsVO.class));
	}

	/** The document, state number and the flagging worker's e-mail of a follow-up (DOCUMENT_ID, STATENO, EMAIL_ADDR), or null. */
	public Map<String, Object> getResolveAndCommentsData(int followupIdPk) {
		return jpql.one(RESOLVE_AND_COMMENTS, LaftRows.map(), (long) followupIdPk);
	}

	/** The resolved follow-ups. */
	public List<LaftFollowupDetailsVO> getFlaggedLaftResolvedData() throws DataAccessException {
		return jpql.list(RESOLVED_FOLLOWUPS, Rows.bean(LaftFollowupDetailsVO.class));
	}

	/** The follow-up resolutions other than 12 (the automatic resolution of deleted documents): RESOLUTION_ID, RESOLUTION_DESC. */
	public List<Map<String, String>> getLaftFollowupResolutions() {
		List<Map<String, String>> rows = new ArrayList<>();
		resolutions.findByResolutionIdNot(12L)
				.forEach(resolution -> rows.add(codeRow("RESOLUTION_ID", resolution.getResolutionId(), "RESOLUTION_DESC", resolution.getResolutionDesc())));
		return rows;
	}

	/**
	 * Resolves a follow-up that is not resolved yet (resolved now).
	 *
	 * @param resolveLaftFollowupDocumentsMap resolutionId, workerId, followupIdPk
	 * @return the number of follow-ups resolved
	 */
	public int resolveLaftFollowupDocuments(Map<String, Object> resolveLaftFollowupDocumentsMap) {
		return jpql.update("""
				update NyehmsFlagLaftFollowup f set f.resolutionId = ?1, f.resolutionUid = ?2, f.resolutionDate = ?3
				where f.followupIdPk = ?4 and f.resolutionId is null""",
				id(resolveLaftFollowupDocumentsMap.get("resolutionId")), id(resolveLaftFollowupDocumentsMap.get("workerId")), now(),
				id(resolveLaftFollowupDocumentsMap.get("followupIdPk")));
	}

	/**
	 * Adds a comment to a follow-up (dated now).
	 *
	 * @param inputParameterMap followupIdPk, comments, workerId, initialComments ('Y' for an initial comment, else null)
	 */
	public int LaftFollowupCommentsUpdate(Map<String, Object> inputParameterMap) {
		return jpql.update(INSERT_COMMENT, id(inputParameterMap.get("followupIdPk")), text(inputParameterMap.get("comments")),
				id(inputParameterMap.get("workerId")), now(), text(inputParameterMap.get("initialComments")));
	}

	/** The review comments of a follow-up, newest first: "name MM/DD/YYYY HH:MI:SS AM  comment". */
	public List<String> getFlaggedLaftFollowupDocumentComments(int followupIdPk) {
		return jpql.list(String.format(COMMENTS, "fc.initialComment is null"), LaftFollowupDocumentDAO::commentLine, (long) followupIdPk);
	}

	/** The initial comments of a follow-up, newest first: "MM/DD/YYYY HH:MI:SS AM  comment". */
	public List<String> getFlaggedLaftFollowupInitialComments(int followupIdPk) {
		return jpql.list(String.format(COMMENTS, "fc.initialComment = 'Y'"), row -> concat(dateTime(row.get(1)), "  ", row.get(2)),
				(long) followupIdPk);
	}

	/**
	 * Moves the follow-ups of a document to another document.
	 *
	 * @param inputOutputParameterMap flaggedDocumentId, newDocumentId
	 */
	public int updateLaftFollowupDocumentId(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update NyehmsFlagLaftFollowup f set f.documentId = ?1 where f.documentId = ?2",
				id(inputOutputParameterMap.get("newDocumentId")), id(inputOutputParameterMap.get("flaggedDocumentId")));
	}

	/**
	 * Resolves the open follow-ups of a deleted document with resolution 12 (resolved now).
	 *
	 * @param inputOutputParameterMap documentId, workerId
	 */
	public int updateLaftFollowupResolutionForDeletedDocs(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("""
				update NyehmsFlagLaftFollowup f set f.resolutionId = 12, f.resolutionUid = ?1, f.resolutionDate = ?2
				where f.documentId = ?3 and f.resolutionId is null""",
				id(inputOutputParameterMap.get("workerId")), now(), id(inputOutputParameterMap.get("documentId")));
	}

	/** usr.full_name || ' ' || to_char(comment_date, ...) || '  ' || comments */
	private static String commentLine(Row row) {
		return concat(row.get(0), " ", dateTime(row.get(1)), "  ", row.get(2));
	}

	/** Oracle's ||: a NULL part adds nothing ('' is NULL on Oracle, so an empty result would be NULL). */
	private static String concat(Object... parts) {
		StringBuilder text = new StringBuilder();
		for (Object part : parts) {
			if (part != null) {
				text.append(part);
			}
		}
		return text.isEmpty() ? null : text.toString();
	}

	private static String dateTime(Object time) {
		return time == null ? null : DATE_TIME.format(((Timestamp) time).toLocalDateTime());
	}

	/** A code row as the former Map result: a NUMBER code as a BigDecimal, null values left out, a row of nulls null. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map<String, String> codeRow(String codeKey, Long code, String descKey, String desc) {
		Map row = new HashMap<>();
		if (code != null) {
			row.put(codeKey, BigDecimal.valueOf(code));
		}
		if (desc != null) {
			row.put(descKey, desc);
		}
		return row.isEmpty() ? null : row;
	}
}
