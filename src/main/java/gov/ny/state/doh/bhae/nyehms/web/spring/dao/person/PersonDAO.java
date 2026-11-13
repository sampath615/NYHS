package gov.ny.state.doh.bhae.nyehms.web.spring.dao.person;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.textCursor;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldDocsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonRisksVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterUploadVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.UpdatedDispositionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The person screen: a person's details, documents, field values and risks, the eHARS and NYC links, the partner
 * services messages of a document (formerly a MyBatis mapper with the same name and methods; the callers are
 * unchanged).
 * <p>
 * The procedures of {@code PKG_PERSON}, {@code pkg_consolidation}, {@code PKG_DOCUMENT_MANAGEMENT} and
 * {@code PKG_PS_MESSAGE_PROCESS} run through {@link NativeStatements#call}; their cursor rows and OUT values are put
 * back into the caller's map under the former keys, the cursors mapped as the former result maps mapped them (read as
 * text, as every property of these VOs is text; {@link PersonResultMaps}). JPQL over the entities Person,
 * NyehmsEharsStatenoMap, EharsPersonNyc, PersonDocAssociation, CodedMergeOrigins, NyehmsStdMessageSent,
 * UpdatePsReturnMessage, NyehmsStdMessageReturn, DocumentPersonAttr and NyehmsRiaDoc. Native SQL ({@link PersonSql},
 * the mapper's text with bound values), with the reason:
 * <ul>
 * <li>{@link #getPersonDocumentDetails}, {@link #getHIVDocumentDetails}: views without an entity, filtered by the
 * pipelined table function {@code TABLE(get_user_permissions(...))};</li>
 * <li>{@link #getDocumentDetailsFromStageTable}, {@link #getNBSDocumentDetails},
 * {@link #getClusterDocumentDetailsFromStageTable}, {@link #getLabDocumentDetails}: DECODE race flags, (+) joins, a
 * table of another schema, a PL/SQL function, a table name chosen per call (allow-listed);</li>
 * <li>{@link #getStatenoClusterAssociation}: CTEs with NOT EXISTS and a UNION.</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class PersonDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	/** The standardized tables {@link #getLabDocumentDetails} may read (the former {@code ${tableName}}). */
	static final Set<String> LAB_DOCUMENT_TABLES = Set.of("STD_HIV_ECLRS_DOC", "STD_STD_ECLRS_DOC");

	// ---- the person procedures --------------------------------------------------------------------------------------

	/**
	 * PKG_PERSON.PR_NYEHMS_PERSON_DETAILS: a person's details at a date.
	 *
	 * @param map p_data_req_type, p_state_no, p_date (a date, with its time), p_calling_app_name, p_module_id,
	 *        p_worker_id; afterwards also refCursor (PersonViewVOs) and p_doc_timeline (PersonDocHistoryVOs)
	 * @return an empty list (the rows are in the map)
	 */
	public List<PersonViewVO> getPersonViewData(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_PERSON.PR_NYEHMS_PERSON_DETAILS",
				param(map.get("p_data_req_type"), String.class), param(map.get("p_state_no"), String.class),
				param(map.get("p_date"), Timestamp.class),
				textCursor(PersonResultMaps.PERSON_VIEW), textCursor(PersonResultMaps.PERSON_DOC_HISTORY),
				param(map.get("p_calling_app_name"), String.class), param(map.get("p_module_id"), Integer.class),
				param(map.get("p_worker_id"), String.class));
		map.put("refCursor", results.get(3));
		map.put("p_doc_timeline", results.get(4));
		return new ArrayList<>();
	}

	/**
	 * PKG_PERSON.PR_SHOW_MULTIVALUES: the values a field had across the person's documents.
	 *
	 * @param map tblField, stateNo, p_calling_app_name, p_module_id, p_worker_id; afterwards also refCursor (PersonFieldVOs)
	 * @return an empty list (the rows are in the map)
	 */
	public List<PersonFieldVO> getFieldValsWithBestIndicator(Map<Object, Object> map) throws DataAccessException {
		map.put("refCursor", showMultivalues(map, PersonResultMaps.PERSON_FIELD));
		return new ArrayList<>();
	}

	/**
	 * PKG_PERSON.PR_SHOW_MULTIVALUES: the documents that gave a field its values.
	 *
	 * @param map as for {@link #getFieldValsWithBestIndicator}; afterwards also refCursor (PersonFieldDocsVOs)
	 * @return an empty list (the rows are in the map)
	 */
	public List<PersonFieldDocsVO> getFieldDocsWithBestIndicator(Map<Object, Object> map) throws DataAccessException {
		map.put("refCursor", showMultivalues(map, PersonResultMaps.PERSON_FIELD_DOCS));
		return new ArrayList<>();
	}

	private Object showMultivalues(Map<Object, Object> map, RowMapper<?> rows) {
		return statements.call("PKG_PERSON.PR_SHOW_MULTIVALUES",
				param(map.get("tblField"), String.class), param(map.get("stateNo"), String.class),
				param(map.get("p_calling_app_name"), String.class), textCursor(rows),
				param(map.get("p_module_id"), Integer.class), param(map.get("p_worker_id"), String.class)).get(3);
	}

	/**
	 * PKG_PERSON.PR_GETRACE: the person's race at a date.
	 *
	 * @param map p_state_no, p_date; afterwards also refCursor (PersonRaceVOs)
	 * @return an empty list (the rows are in the map; the declared element type is the mapper's, as before)
	 */
	public List<PersonRisksVO> getPersonRaceData(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_PERSON.PR_GETRACE",
				param(map.get("p_state_no"), Integer.class), param(map.get("p_date"), Timestamp.class),
				textCursor(PersonResultMaps.PERSON_RACE));
		map.put("refCursor", results.get(2));
		return new ArrayList<>();
	}

	/**
	 * PKG_PERSON.PR_GETRISKS: the person's risk factors at a date.
	 *
	 * @param map p_state_no, p_date, p_calling_app_name; afterwards also refCursor (PersonRisksVOs)
	 * @return an empty list (the rows are in the map)
	 */
	public List<PersonRisksVO> getPersonRisksData(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_PERSON.PR_GETRISKS",
				param(map.get("p_state_no"), String.class), param(map.get("p_date"), Timestamp.class),
				textCursor(PersonResultMaps.PERSON_RISKS), param(map.get("p_calling_app_name"), String.class));
		map.put("refCursor", results.get(2));
		return new ArrayList<>();
	}

	/**
	 * pkg_consolidation.pr_get_stateno_history: the former state numbers of a person.
	 *
	 * @param map p_state_no; afterwards also p_stateno_hist (Strings)
	 * @return an empty list (the rows are in the map)
	 */
	public List<String> getStatenoHistory(Map<Object, Object> map) {
		List<Object> results = statements.call("pkg_consolidation.pr_get_stateno_history",
				param(map.get("p_state_no"), String.class), textCursor(PersonResultMaps.COLUMN_VALUE));
		map.put("p_stateno_hist", results.get(1));
		return new ArrayList<>();
	}

	/**
	 * PKG_DOCUMENT_MANAGEMENT.pr_delete_indicate_document: deletes a document.
	 *
	 * @param inOutParameterMap p_document_id, p_worker_id; afterwards also p_code_out (an Integer) and p_msg_out
	 */
	public void deleteDocument(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		List<Object> results = statements.call("PKG_DOCUMENT_MANAGEMENT.pr_delete_indicate_document",
				param(inOutParameterMap.get("p_document_id"), String.class), param(inOutParameterMap.get("p_worker_id"), Integer.class),
				out(Integer.class), out(String.class));
		inOutParameterMap.put("p_code_out", results.get(2));
		inOutParameterMap.put("p_msg_out", results.get(3));
	}

	/**
	 * PKG_PS_MESSAGE_PROCESS.pr_manual_create_ps_message: triggers a partner services message for a document.
	 *
	 * @param inOutParameterMap p_stateno, p_document_id, p_document_type, p_TRIGGER_MSG_TYPE, p_workerId,
	 *        p_test_type_code, p_interpret_cd, p_change_address, p_new_address, p_new_state, p_new_city, p_new_zip,
	 *        p_new_county_fips; afterwards also p_code_out (an Integer) and p_msg_out
	 */
	public void triggerPsMessage(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_PS_MESSAGE_PROCESS.pr_manual_create_ps_message",
				param(map.get("p_stateno"), String.class), param(map.get("p_document_id"), Integer.class),
				param(map.get("p_document_type"), String.class), param(map.get("p_TRIGGER_MSG_TYPE"), String.class),
				param(map.get("p_workerId"), String.class), out(Integer.class), out(String.class),
				param(map.get("p_test_type_code"), String.class), param(map.get("p_interpret_cd"), String.class),
				param(map.get("p_change_address"), String.class), param(map.get("p_new_address"), String.class),
				param(map.get("p_new_state"), String.class), param(map.get("p_new_city"), String.class),
				param(map.get("p_new_zip"), String.class), param(map.get("p_new_county_fips"), String.class));
		map.put("p_code_out", results.get(5));
		map.put("p_msg_out", results.get(6));
	}

	// ---- the document lists -----------------------------------------------------------------------------------------

	/** getPersonDocumentDetails as the mapper had it; the optional document type filter is appended before the order by. */
	private static final String PERSON_DOCUMENT_DETAILS = """
			select DOCUMENT_ID documentId, DOCUMENT_TYPE_CODE documentType, TO_CHAR(RECIEVE_DT, 'MM/DD/YYYY') recievedDate,PROVIDER_NAME providerName,PROVIDER_ADDRESS providerAddress, ASSOCIATION_DATE associationDate, FIRST_NAME firstName, LAST_NAME lastName, DATE_OF_BIRTH dateOfBirth, DATE_OF_DEATH dateOfDeath, SSN ssn, ETHNICITY ethnicity, RACE race, ADDRESS address, SURV_ASSIGNMENT_ID assignmentId, SURV_STATUS assignmentStatus, SENT_MESSAGE_FLAG sentMessageFlag, RETURN_MESSAGE_FLAG returnMessageFlag, ALT_ACRF_INFO altValues, ASSOCIATION_STATUS_CODE associationStatus, RAS_DOC_ID rasDocId, SIERRA_FLAG sierraFlag, DISCARD_STATUS discardStatus, HL7_EXISTS hl7Exists, nyc_reject_doc_ind nycRejectDocInd, DEMO_DOCUMENT_ID demoDocId, LAFT_FOLLOWUP_EXISTS laftFollowupExists from  NYEHMS_DOCUMENT_TIMELINE_VW
			  where  STATENO = ?1
			  and doc_type_code IN (SELECT *
			                     FROM TABLE(get_user_permissions(?2, ?3)))
			""";

	/**
	 * The documents of a person the worker may see (NYEHMS_DOCUMENT_TIMELINE_VW), latest received first.
	 *
	 * @param inputParameterMap p_stateNo, p_module_id, p_worker_id, p_documentType (a document type to filter by, or
	 *        empty / missing for all)
	 */
	public List<PersonDocHistoryVO> getPersonDocumentDetails(Map<String, Object> inputParameterMap) {
		Object documentType = inputParameterMap.get("p_documentType");
		StringBuilder sql = new StringBuilder(PERSON_DOCUMENT_DETAILS);
		List<Object> parameters = new ArrayList<>(List.of(value(inputParameterMap.get("p_stateNo")),
				value(inputParameterMap.get("p_module_id")), value(inputParameterMap.get("p_worker_id"))));
		if (documentType != null && !"".equals(documentType)) {
			parameters.add(documentType);
			sql.append("          and doc_type_code = ?4\n");
		}
		sql.append("          order by RECIEVE_DT desc nulls last\n");
		return statements.list(sql.toString(), Rows.bean(PersonDocHistoryVO.class), parameters.toArray());
	}

	/** The HIV lab documents of a person the worker may see (NYEHMS_DOC_TIMELINE_HIV_VW), latest collection first. @param inputParameterMap p_stateNo, p_module_id, p_worker_id */
	public List<PersonDocHistoryVO> getHIVDocumentDetails(Map<String, Object> inputParameterMap) {
		return statements.list(PersonSql.HIV_DOCUMENT_DETAILS, Rows.bean(PersonDocHistoryVO.class), PersonSql.getHIVDocumentDetailsArgs(inputParameterMap));
	}

	/** A document's details from the quality-stage table (er_person_clusters_stage). @param map documentId, documentType */
	public List<PersonDocHistoryVO> getDocumentDetailsFromStageTable(Map<Object, Object> map) {
		return statements.list(PersonSql.DOCUMENT_DETAILS_FROM_STAGE, Rows.bean(PersonDocHistoryVO.class), PersonSql.getDocumentDetailsFromStageTableArgs(map));
	}

	/** A newborn screening document's details (STD_NBS_DOC). @param map documentId */
	public List<PersonDocHistoryVO> getNBSDocumentDetails(Map<Object, Object> map) {
		return statements.list(PersonSql.NBS_DOCUMENT_DETAILS, Rows.bean(PersonDocHistoryVO.class), value(map.get("documentId")));
	}

	/** A cluster partner's details (nyehms_cluster_partners). @param map documentId (the partner id) */
	public List<PersonDocHistoryVO> getClusterDocumentDetailsFromStageTable(Map<Object, Object> map) {
		return statements.list(PersonSql.CLUSTER_DOCUMENT_DETAILS_FROM_STAGE, Rows.bean(PersonDocHistoryVO.class), value(map.get("documentId")));
	}

	/**
	 * An eCLRS lab document's details from its standardized table.
	 *
	 * @param map tableName (STD_HIV_ECLRS_DOC or STD_STD_ECLRS_DOC; another name fails), documentType, documentId
	 */
	public List<PersonDocHistoryVO> getLabDocumentDetails(Map<Object, Object> map) {
		String sql = PersonSql.LAB_DOCUMENT_DETAILS.replace("${tableName}", allowedTable(map.get("tableName")));
		return statements.list(sql, Rows.bean(PersonDocHistoryVO.class), PersonSql.getLabDocumentDetailsArgs(map));
	}

	/** A table name from the caller, as it was given, if it is one of {@link #LAB_DOCUMENT_TABLES} (ignoring case); otherwise the call fails. */
	private static String allowedTable(Object name) {
		if (name instanceof String text && LAB_DOCUMENT_TABLES.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text;
		}
		throw new InvalidDataAccessApiUsageException("Not a lab document table: " + name);
	}

	/** Had no statement in the mapper; the call still fails, as before. */
	public List<PersonDocHistoryVO> getDocumentDetails(Map<Object, Object> map) {
		throw new InvalidDataAccessApiUsageException("PersonDAO.getDocumentDetails has no statement");
	}

	// ---- the person and its links -----------------------------------------------------------------------------------

	/** FLAG_ACTIVE of a person, or null if there is none. */
	public String checkStatenoIsActive(String stateNo) {
		return jpql.one("select p.flagActive as FLAG_ACTIVE from Person p where p.stateno = ?1", Rows.scalar(String.class), stateNo);
	}

	/** The eHARS state numbers mapped to a state number (status not D, M or P). */
	public List<String> getEharsStateno(String stateNo) {
		return jpql.list("""
				select distinct m.eharsStateno as EHARS_STATENO from NyehmsEharsStatenoMap m
				where m.nyehmsStateno = ?1 and coalesce(m.statusFlag, 'XX') not in ('D', 'M', 'P')""", Rows.scalar(String.class), stateNo);
	}

	/**
	 * The eHARS state numbers mapped to state numbers (status not D, M or P): EHARS_STATENO, NYEHMS_STATENO.
	 *
	 * @param inOutParameterMap statenos (an array or collection; an empty one fails, as {@code IN ()} did)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getEharsStatenoMappings(Map<Object, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String statenos = placeholders(inList(inOutParameterMap.get("statenos"), "statenos"), parameters);
		return (List) jpql.list("select m.eharsStateno as EHARS_STATENO, m.nyehmsStateno as NYEHMS_STATENO from NyehmsEharsStatenoMap m "
				+ "where m.nyehmsStateno in (" + statenos + ") and coalesce(m.statusFlag, 'XX') not in ('D', 'M', 'P')", Rows.map(), parameters.toArray());
	}

	/** The state number a document is associated with (any one association, as {@code rownum < 2} chose), or null. */
	public String getNyehmsStatenoForTheDocument(int docId) {
		return jpql.one("""
				select a.stateno as STATENO from PersonDocAssociation a where a.documentId = ?1 and a.associationStatusCode = 'ASSOC'
				fetch first 1 rows only""", Rows.scalar(String.class), docId);
	}

	/** The NYC eHARS state numbers of a state number. */
	public List<String> getNycEharsDetails(String stateNo) {
		return jpql.list("select distinct e.eharsStateno as EHARS_STATENO from EharsPersonNyc e where e.nyehmsStateno = ?1", Rows.scalar(String.class), stateNo);
	}

	/** The NYC city numbers of a state number. */
	public List<String> getNycCityNoDetails(String stateno) {
		return jpql.list("select distinct e.cityno as CITYNO from EharsPersonNyc e where e.nyehmsStateno = ?1", Rows.scalar(String.class), stateno);
	}

	/** The merge origins in force with a type, by sort order: ORIGIN_CODE, ORIGIN_DESC, ORIGIN_TYPE. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getOriginCategories() {
		return (List) jpql.list("""
				select o.originCode as ORIGIN_CODE, o.originDesc as ORIGIN_DESC, o.originType as ORIGIN_TYPE from CodedMergeOrigins o
				where o.originType is not null and o.endDate is null order by o.sortOrder""", Rows.map());
	}

	/** The diagnosis stages of a state number in eHARS (status not D, M or P; any one row): STAGE_ZERO_DX, STAGE_CD4_OI_DX; null if none. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getNYSEharsDxDetails(String stateno) {
		return (Map) jpql.one("""
				select m.stageZeroDx as STAGE_ZERO_DX, m.stageCd4OiDx as STAGE_CD4_OI_DX from NyehmsEharsStatenoMap m
				where m.nyehmsStateno in (?1) and coalesce(m.statusFlag, 'XX') not in ('D', 'M', 'P')
				fetch first 1 rows only""", Rows.map(), stateno);
	}

	/** The diagnosis stages of a state number in the NYC eHARS (status not D, M or P; any one row): STAGE_ZERO_DX, STAGE_CD4_OI_DX; null if none. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getNYCEharsDxDetails(String stateno) {
		return (Map) jpql.one("""
				select e.stageZeroDx as STAGE_ZERO_DX, e.stageCd4OiDx as STAGE_CD4_OI_DX from EharsPersonNyc e
				where e.nyehmsStateno in (?1) and coalesce(e.statusFlag, 'XX') not in ('D', 'M', 'P')
				fetch first 1 rows only""", Rows.map(), stateno);
	}

	/** The clusters a person is in (with the current state number) and the other clusters (without): curr_stateno, clusterId, clusterType, clusterDate. */
	public List<ClusterUploadVO> getStatenoClusterAssociation(String stateNo) {
		return statements.list(PersonSql.STATENO_CLUSTER_ASSOCIATION, Rows.bean(ClusterUploadVO.class), stateNo);
	}

	// ---- partner services messages ----------------------------------------------------------------------------------

	/**
	 * The message types triggered for a person (stateno given) or for a follow-up document (documentId).
	 *
	 * @param inputParameterMap stateno (the former OGNL test: not null and not empty), documentId
	 */
	public List<String> getTriggeredMessageTypesForDocument(Map<Object, Object> inputParameterMap) {
		Object stateno = inputParameterMap.get("stateno");
		if (stateno != null && !"".equals(stateno)) {
			return jpql.list("select distinct s.triggerMsgType as TRIGGER_MSG_TYPE from NyehmsStdMessageSent s where s.stateno = ?1",
					Rows.scalar(String.class), BoundValues.text(stateno));
		}
		return jpql.list("select distinct s.triggerMsgType as TRIGGER_MSG_TYPE from NyehmsStdMessageSent s where s.followUpDocId = ?1",
				Rows.scalar(String.class), BoundValues.longValue(inputParameterMap.get("documentId")));
	}

	/** The document ids among the given ones that have an updated return message. @param inputParameterMap returnMessageDocIds */
	public List<Integer> checkForExistingUpdatedMessageDocIds(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String ids = placeholders(integers(inputParameterMap.get("returnMessageDocIds"), "returnMessageDocIds"), parameters);
		return jpql.list("select u.documentId as DOCUMENT_ID from UpdatePsReturnMessage u where u.documentId in (" + ids + ")",
				Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The document ids of the return messages that are not among the given ones. @param inputParameterMap returnMessageDocIds */
	public List<Integer> getReturnDocIdNotExistInTable(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String ids = placeholders(integers(inputParameterMap.get("returnMessageDocIds"), "returnMessageDocIds"), parameters);
		return jpql.list("select r.documentId as DOCUMENT_ID from NyehmsStdMessageReturn r where r.documentId not in (" + ids + ")",
				Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The rows of {@link #checkExistingDateOfDeathForMessageDocIds}, the ids as BigDecimals (getObject types, as before). */
	static final RowMapper<Map<String, Object>> DATE_OF_DEATH_ROWS = row -> {
		Map<String, Object> values = new HashMap<>();
		String stateno = Values.text(row.get("STATENO"));
		if (stateno != null) {
			values.put("STATENO", stateno);
		}
		for (String column : List.of("DOCUMENT_ID", "FOLLOW_UP_DOC_ID")) {
			Long value = Values.longValue(row.get(column));
			if (value != null) {
				values.put(column, BigDecimal.valueOf(value));
			}
		}
		return values.isEmpty() ? null : values;
	};

	/**
	 * The given documents (with a current person) whose person attributes carry a date of death and that have a sent
	 * message: STATENO, DOCUMENT_ID, FOLLOW_UP_DOC_ID.
	 *
	 * @param inputParameterMap docIdsDateOfDeathNull
	 */
	public List<Map<String, Object>> checkExistingDateOfDeathForMessageDocIds(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String ids = placeholders(integers(inputParameterMap.get("docIdsDateOfDeathNull"), "docIdsDateOfDeathNull"), parameters);
		return jpql.list("""
				select distinct p.stateno as STATENO, d.documentId as DOCUMENT_ID, sent.followUpDocId as FOLLOW_UP_DOC_ID
				from PersonDocAssociation p, DocumentPersonAttr d, NyehmsStdMessageSent sent
				where d.dateOfDeath is not null and p.documentId = d.documentId and d.documentId = sent.documentId
				and d.documentId in (""" + ids + ") and p.dissassociationDate is null", DATE_OF_DEATH_ROWS, parameters.toArray());
	}

	/** Had a statement in the mapper but no caller, and its VO lacks the DATE_OF_DEATH property the statement bound: the call still fails, as before. */
	public int checkForDuplicateDispositionCode(UpdatedDispositionsVO updatedDispositionsVO) {
		throw new InvalidDataAccessApiUsageException("PersonDAO.checkForDuplicateDispositionCode: UpdatedDispositionsVO has no DATE_OF_DEATH");
	}

	/** Retires the pending ('R') messages of a deleted triggering document: PROCESS_IND 'R_X', the worker, now (the database's SYSDATE). @param inputParameterMap p_workerId, p_document_id */
	public void deletePendingPSMessageDocuments(Map<String, Object> inputParameterMap) {
		jpql.update("""
				update NyehmsStdMessageSent s set s.processInd = 'R_X', s.reviewComments = 'Triggering Document Deleted', s.reviewUid = ?1,
				s.reviewDate = sysdate where s.followUpDocId = ?2 and s.processInd = 'R'""",
				BoundValues.text(inputParameterMap.get("p_workerId")), BoundValues.longValue(inputParameterMap.get("p_document_id")));
	}

	/** Closes the open RIA document of a deleted lab document: STATENO_DELETED 'Y', the worker, now (the database's SYSDATE). @param inputParameterMap p_workerId, p_document_id */
	public void deletePendingRiaDocument(Map<String, Object> inputParameterMap) {
		jpql.update("""
				update NyehmsRiaDoc r set r.statenoDeleted = 'Y', r.comments = 'Triggering Document Deleted', r.lastUpdatedBy = ?1,
				r.lastUpdatedDate = sysdate where r.labDocId = ?2 and r.completeDate is null""",
				BoundValues.text(inputParameterMap.get("p_workerId")), BoundValues.text(inputParameterMap.get("p_document_id")));
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

	/** A value bound to a query as it is (a java.util.Date as a timestamp). */
	private static Object value(Object value) {
		return value != null && value.getClass() == java.util.Date.class ? new Timestamp(((java.util.Date) value).getTime()) : value;
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

	/** The values of an id list as Integers (the entities' document ids). */
	private static List<Object> integers(Object values, String name) {
		List<Object> ids = new ArrayList<>();
		for (Object value : inList(values, name)) {
			ids.add(BoundValues.integer(value));
		}
		return ids;
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
