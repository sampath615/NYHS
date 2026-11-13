package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NYCLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The data the document screens share: code lists, a document's person association and assignment, the deletion and
 * edit of a document (raw and standardized tables, locator, person tables, messages), the partner services message
 * details of a document, the NYC eHARS lab document (formerly a MyBatis mapper with the same name and methods; the
 * callers are unchanged).
 * <p>
 * JPQL over the code-table entities ({@code entity.document.Coded*}), DocumentLocator, PersonDocAssociation,
 * DocumentPersonAttr, DocumentRisks, DocumentRace, NyehmsStdMessageSent, Person, NyehmsDuplicateStatenos,
 * NyehmsUnmergingStatenos, CdcRidrInfo, DeletedDocHistory, SurveillanceAssignment, AssignmentAddress, NyehmsRiaDoc,
 * ErPersonClustersStage and the code tables it joins. The former {@code LISTAGG} of the drop-down dispositions is a
 * Java grouping. "Now" (formerly SYSDATE) is the application server's clock. The procedure
 * {@code PKG_DOCUMENT_MANAGEMENT.pr_insert_raw_to_std} runs through {@link NativeStatements#call}. Native SQL
 * ({@link CommonDocLabsSql}, the mapper's text with bound values), with the reason:
 * <ul>
 * <li>{@link #updateRawDocumentStatusCode}, {@link #updateStdDocumentStatusCode}, {@link #deleteDocumentFromStdTable},
 * {@link #updateDocumentsStatus}, {@link #getDocData}, {@link #getStatenoAndSourceCodeFromStdDocData},
 * {@link #getColumnDataFromTable}: the table (and column) is chosen per call; the names are allow-listed in
 * {@link StdTableNames} (the raw and standardized document tables, the source-id columns; a column the code base does
 * not enumerate must at least be shaped like one), anything else is refused;</li>
 * <li>{@link #getDocumentTypes}: the pipelined table function {@code TABLE(get_user_permissions(...))};</li>
 * <li>{@link #getPSMessageDetails}: a CTE with a union, five {@code (+)} joins, {@code re.*} over the returned message's
 * columns, {@code TO_CHAR} formats, read into the former nested result map ({@link PsMessageRows});</li>
 * <li>{@link #getNycLabDoc}: the raw and standardized NYC eHARS lab tables have no entities (a 96-property VO).</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class CommonDocLabsDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	// ---- the code lists (JSON, as the screens read them) -----------------------------------------------------------

	public List<JSONObject> getCodedArvs() {
		return jpql.list("select distinct a.lookupCd as LOOKUP_CD, a.arvDesc as ARV_DESC from CodedArvs a where a.lookupCd is not null order by a.arvDesc", Rows.json());
	}

	public List<JSONObject> getCodedProfessionValues() {
		return jpql.list("select p.profCode as PROF_CODE, p.profDesc as PROF_DESC from CodedProvProf p order by p.profDesc", Rows.json());
	}

	public List<JSONObject> getCodedHivFacilityTypes() {
		return jpql.list("select f.facilityCode as FACILITY_CODE, f.facilityDesc as FACILITY_DESC from CodedHivTestFacility f order by f.facilityDesc", Rows.json());
	}

	public List<JSONObject> getCOSCodesData() {
		return jpql.list("select c.provCosCode as PROV_COS_CODE, c.provCosCodeDesc as PROV_COS_CODE_DESC from CodedCosCodeReference c order by c.provCosCodeDesc", Rows.json());
	}

	public List<JSONObject> getPartnerAssistanceStatus() {
		return jpql.list("select s.provStatusCode as PROV_STATUS_CODE, s.provStatusDesc as PROV_STATUS_DESC from CodedProviderStatus s order by s.sortOrder, s.provStatusCode", Rows.json());
	}

	public List<JSONObject> getCodedInvestigationStatus() {
		return jpql.list("select s.investigationStatusCd as INVESTIGATION_STATUS_CD, s.investigationStatusDesc as INVESTIGATION_STATUS_DESC from CodedInvestigationStatus s order by s.investigationStatusDesc", Rows.json());
	}

	/**
	 * The sent-message dispositions: with filterRows, the drop-down ones grouped by description and sort order, the
	 * codes of a group comma-joined (formerly LISTAGG): DISPOSITION_CODE, DISPOSITION_DESC, SORT_ORDER; otherwise all of
	 * them by description: DISPOSITION_CODE, DISPOSITION_DESC, RECORD_SEARCH_FLAG.
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getDispositionCodeValues(Map<String, Boolean> inputParameterMap) {
		if (Boolean.TRUE.equals(inputParameterMap.get("filterRows"))) {
			List<Row> rows = jpql.list("select d.sentDispositionCode, d.sentDispositionDesc, d.sortOrder from CodedSentMessageDisposition d where d.dropDown = 'Y' order by d.sortOrder asc",
					row -> row);
			Map<List<Object>, JSONObject> groups = new LinkedHashMap<>();
			for (Row row : rows) {
				List<Object> key = java.util.Arrays.asList(row.get(1), row.get(2));
				JSONObject group = groups.get(key);
				if (group == null) {
					group = new JSONObject();
					putIfPresent(group, "DISPOSITION_DESC", row.get(1));
					putIfPresent(group, "SORT_ORDER", row.get(2));
					groups.put(key, group);
				}
				if (row.get(0) != null) {
					Object codes = group.get("DISPOSITION_CODE");
					group.put("DISPOSITION_CODE", codes == null ? Values.text(row.get(0)) : codes + "," + row.get(0));
				}
			}
			return new ArrayList<>(groups.values());
		}
		return jpql.list("select d.sentDispositionCode as DISPOSITION_CODE, d.sentDispositionDesc as DISPOSITION_DESC, d.recordSearchFlag as RECORD_SEARCH_FLAG "
				+ "from CodedSentMessageDisposition d order by d.sentDispositionDesc", Rows.json());
	}

	public List<JSONObject> getCodedMaritalStatus() {
		return jpql.list("select m.maritalStatusCode as MARITAL_STATUS_CODE, m.maritalStatusDesc as MARITAL_STATUS_DESC from CodedMaritalStatus m order by m.sortOrder", Rows.json());
	}

	public List<JSONObject> getFileNamesForDocumentType(String _documentType) {
		return jpql.list("select f.fileName as FILE_NAME, f.displayName as DISPLAY_NAME from CodedDisplayFileName f where f.documentTypeCode = ?1 order by f.displayName asc",
				Rows.json(), _documentType);
	}

	public List<JSONObject> getAddressTypes() {
		return jpql.list("select t.addressTypeCode as ADDRESS_TYPE_CODE, t.addressTypeDescription as ADDRESS_TYPE_DESCRIPTION from CodedAddressType t order by t.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedSexAtBirthValues() {
		return jpql.list("select s.sexCode as SEX_CODE, s.sexDescription as SEX_DESCRIPTION from CodedSexAtBirth s order by s.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedSexualOrientationValues() {
		return jpql.list("select o.sexOrientCd as SEX_ORIENT_CD, o.sexOrientDesc as SEX_ORIENT_DESC from CodedSexualOrientation o", Rows.json());
	}

	public List<JSONObject> getCodedSexualOrientationPrfValues() {
		return jpql.list("select o.sexOrientCd as SEX_ORIENT_CD, o.sexOrientDesc as SEX_ORIENT_DESC from CodedSexualOrientation o where o.prf = 'Y' order by o.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedYesNoUnkownValues() {
		return jpql.list("select a.ansCode as ANS_CODE, a.ansDesc as ANS_DESC from CodedYesNoUnk a order by a.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedPrimaryLanguageValues() {
		return jpql.list("select l.langCode as LANG_CODE, l.langDesc as LANG_DESC from CodedPrimaryLanguage l order by l.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedYesNoRefUnkownValues() {
		return jpql.list("select a.ansCode as ANS_CODE, a.ansDesc as ANS_DESC from CodedYesNoRefUnk a order by a.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedTelephoneTypeValues() {
		return jpql.list("select t.ansCode as ANS_CODE, t.ansDesc as ANS_DESC from CodedTelephoneType t order by t.sortOrder", Rows.json());
	}

	public List<JSONObject> getCodedFacilityTypeValues() {
		return jpql.list("select f.facilityTypeCode as FACILITY_TYPE_CODE, f.eprfFacilityTypeDescription as EPRF_FACILITY_TYPE_DESCRIPTION from CodedEharsFacility f "
				+ "where f.eprfSortOrder is not null order by f.eprfSortOrder", Rows.json());
	}

	public List<JSONObject> getLAFTReasonslist() {
		return jpql.list("select r.reasonId as REASON_ID, r.reasonDesc as REASON_DESC from CodedLaftPsReasons r order by r.reasonId", Rows.json());
	}

	public List<JSONObject> getExpandedRaceList() {
		return jpql.list("select r.eharsRaceCode as EHARS_RACE_CODE, r.eharsRaceDesc as EHARS_RACE_DESC from CodedEharsRace r where r.expandedRace = 'Y' order by r.eharsRaceDesc asc", Rows.json());
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getHIVCategories() {
		return (List) jpql.list("select c.hivCateg as HIV_CATEG, c.hivCategDesc as HIV_CATEG_DESC from CodedHivCategory c", Rows.map());
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getAIDSCategories() {
		return (List) jpql.list("select c.aidsCateg as AIDS_CATEG, c.aidsCategDesc as AIDS_CATEG_DESC from CodedAidsCategory c", Rows.map());
	}

	/** The raw / standardized column pairs the column viewer shows for a document type: RAW_COLUMN, STD_COLUMN. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getStdToRawColumnList(String documentType) {
		return (List) jpql.list("select m.rawColumn as RAW_COLUMN, m.stdColumn as STD_COLUMN from CodedRawStdColMapping m where m.documentTypeCode = ?1 and m.columnViewerFlag = 'Y'",
				Rows.map(), documentType);
	}

	public List<String> getUnitsOfMeasurement() {
		return jpql.list("select distinct u.eharsUnitsCode from CodedUnits u order by u.eharsUnitsCode asc", Rows.scalar(String.class));
	}

	/** The codes and names of a partner services message column: LOOKUP_CD, LOOKUP_NAME. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getPSMessageReferenceData(String columnName) {
		return (List) jpql.list("select d.lookupCd as LOOKUP_CD, d.lookupName as LOOKUP_NAME from CodedPsMessageRefData d where d.refColumn = ?1 order by d.lookupName",
				Rows.map(), columnName);
	}

	/** The document types with surveillance assignments the worker may see. @param map p_module_id, p_worker_id */
	public List<String> getDocumentTypes(Map<String, Object> map) {
		return statements.list(CommonDocLabsSql.DOCUMENT_TYPES, Rows.scalar(String.class), map.get("p_module_id"), map.get("p_worker_id"));
	}

	// ---- the document locator and person tables -------------------------------------------------------------------

	/** Deactivates a document in the locator. @return the rows changed */
	public int deleteDocFromDocLocator(int docId) {
		return jpql.update("update DocumentLocator d set d.isActive = 'N' where d.documentId = ?1", docId);
	}

	/** Deactivates documents in the locator. @param inputOutputParameterMap documentIds (an array or collection; an empty one fails, as IN () did) */
	public void inActivateDocument(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String ids = placeholders(inList(inputOutputParameterMap.get("documentIds"), "documentIds"), parameters, BoundValues::integer);
		jpql.update("update DocumentLocator d set d.isActive = 'N' where d.documentId in (" + ids + ")", parameters.toArray());
	}

	public String getSubDocumentType(Integer documentId) {
		return jpql.one("select d.testTypeCd from DocumentLocator d where d.documentId = ?1", Rows.scalar(String.class), documentId);
	}

	public String getDocumentType(Integer documentId) {
		return jpql.one("select d.documentTypeCode from DocumentLocator d where d.documentId = ?1", Rows.scalar(String.class), documentId);
	}

	/**
	 * Ends the current associations of documents: disassociated now, with a status and the worker.
	 *
	 * @param inputOutputParameterMap associationStatus, p_user_id, p_documentId_list (an array or collection)
	 * @return the rows changed
	 */
	public int deleteEntryInPersonDocAssociation(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(now());
		parameters.add(BoundValues.text(inputOutputParameterMap.get("associationStatus")));
		parameters.add(BoundValues.longValue(inputOutputParameterMap.get("p_user_id")));
		String ids = placeholders(inList(inputOutputParameterMap.get("p_documentId_list"), "p_documentId_list"), parameters, BoundValues::integer);
		return jpql.update("update PersonDocAssociation a set a.dissassociationDate = ?1, a.associationStatusCode = ?2, a.workerId = ?3"
				+ " where a.dissassociationDate is null and a.documentId in (" + ids + ")", parameters.toArray());
	}

	public int deleteEntryInDocumentPersonAttr(int documentId) {
		return jpql.update("delete from DocumentPersonAttr d where d.documentId = ?1", documentId);
	}

	public void deleteEntryInDocumentRiskTable(int documentId) {
		jpql.update("delete from DocumentRisks r where r.documentId = ?1", (long) documentId);
	}

	public int deleteEntryInDocumentRace(int documentId) {
		return jpql.update("delete from DocumentRace r where r.documentId = ?1", (long) documentId);
	}

	/** Sets the surveillance assessment status of a document's association. @param inputOutputParameterMap assignProcess, p_user_id, documentId */
	public int updateSurvellianceAssignmentStatus(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update PersonDocAssociation a set a.survAssessStatus = ?1, a.workerId = ?2 where a.documentId = ?3",
				BoundValues.integer(inputOutputParameterMap.get("assignProcess")), BoundValues.longValue(inputOutputParameterMap.get("p_user_id")),
				BoundValues.integer(inputOutputParameterMap.get("documentId")));
	}

	/** Sets the partner services assessment status of a document's association. @param inputOutputParameterMap pS_ASSIGN_STATUS, documentId */
	public int updateDocumentPsAssignStatus(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update PersonDocAssociation a set a.psAssessStatus = ?1 where a.documentId = ?2",
				BoundValues.integer(inputOutputParameterMap.get("pS_ASSIGN_STATUS")), BoundValues.integer(inputOutputParameterMap.get("documentId")));
	}

	/** The current association of a document: STATENO, PS_ASSESS_STATUS, SURV_ASSIGNMENT_ID, SURV_ASSESS_STATUS, DISCARD_STATUS (numbers as BigDecimals, as before); null if none. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<Object, Object> getPersonDocData(int documentId) {
		return (Map) jpql.one("""
				select a.stateno as STATENO, a.psAssessStatus as PS_ASSESS_STATUS, a.survAssignmentId as SURV_ASSIGNMENT_ID, a.survAssessStatus as SURV_ASSESS_STATUS,
				       a.discardStatus as DISCARD_STATUS from PersonDocAssociation a where a.documentId = ?1 and a.dissassociationDate is null""",
				NUMBERS_AS_BIG_DECIMAL, documentId);
	}

	/** The current association of a document: STATENO, PS_ASSESS_STATUS (a BigDecimal, as before); null if none. */
	public Map<String, Object> getStatenoDetailsByDocumentId(int document_ID) {
		return jpql.one("select a.stateno as STATENO, a.psAssessStatus as PS_ASSESS_STATUS from PersonDocAssociation a where a.documentId = ?1 and a.dissassociationDate is null",
				NUMBERS_AS_BIG_DECIMAL, document_ID);
	}

	public int checkForActiveDocumentInPersonTables(int documentId) {
		return jpql.one("select count(a) from PersonDocAssociation a where a.documentId = ?1 and a.dissassociationDate is null", Rows.scalar(Integer.class), documentId);
	}

	/** Deactivates a state number that has no current document. @param inputOutputParameterMap p_stateno */
	public int checkAndInactiveStateno(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update Person p set p.flagActive = 'N' where p.stateno = ?1 and 0 = (select count(a) from PersonDocAssociation a where a.stateno = ?1 and a.dissassociationDate is null)",
				BoundValues.text(inputOutputParameterMap.get("p_stateno")));
	}

	/** Rejects the new merge pairs of a state number. @param inputOutputParameterMap p_reviewComments, p_user_id, p_stateno */
	public void rejectMergePairForInactiveStateno(Map<Object, Object> inputOutputParameterMap) {
		jpql.update("update NyehmsDuplicateStatenos d set d.statusCd = 'REJECT', d.reviewComments = ?1, d.lastUpdatedBy = ?2, d.lastUpdatedDate = ?3"
				+ " where d.statusCd = 'NEW' and (d.stateno1 = ?4 or d.stateno2 = ?4)", BoundValues.text(inputOutputParameterMap.get("p_reviewComments")),
				BoundValues.longValue(inputOutputParameterMap.get("p_user_id")), now(), BoundValues.text(inputOutputParameterMap.get("p_stateno")));
	}

	/** Rejects the new bad-merge flags of a state number. @param inputOutputParameterMap p_reviewComments, p_user_id, p_stateno */
	public void rejectBadMergeStatneForInactiveStateno(Map<Object, Object> inputOutputParameterMap) {
		jpql.update("update NyehmsUnmergingStatenos u set u.statusCd = 'REJECT', u.reviewComments = ?1, u.lastUpdatedBy = ?2, u.lastUpdatedDate = ?3"
				+ " where u.statusCd = 'NEW' and u.stateno = ?4", BoundValues.text(inputOutputParameterMap.get("p_reviewComments")),
				BoundValues.longValue(inputOutputParameterMap.get("p_user_id")), now(), BoundValues.text(inputOutputParameterMap.get("p_stateno")));
	}

	// ---- the partner services messages --------------------------------------------------------------------------------

	/**
	 * The partner services messages of a document (documentType PS_MSG_LIST: the follow-up messages of the document),
	 * each with the returned message, the review comments and the updated dispositions (the former nested result map).
	 *
	 * @param inputOutputParameterMap documentId, documentType
	 */
	public List<PSMessageVO> getPSMessageDetails(Map<Object, Object> inputOutputParameterMap) {
		String condition = "PS_MSG_LIST".equals(inputOutputParameterMap.get("documentType")) ? "FOLLOW_UP_DOC_ID =  ?1" : "DOCUMENT_ID =  ?1";
		String sql = String.format(CommonDocLabsSql.PS_MESSAGE_DETAILS, condition, condition, condition);
		return PsMessageRows.group(statements.list(sql, PsMessageRows.PART, inputOutputParameterMap.get("documentId")));
	}

	/** Points the messages following a document to its edited copy. @param inputOutputParameterMap newDocumentId, oldDocumentId */
	public int updateTriggeringDocumentIdInNyehmsMessage(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update NyehmsStdMessageSent s set s.followUpDocId = ?1, s.editedFlag = 'Y' where s.followUpDocId = ?2",
				BoundValues.longValue(inputOutputParameterMap.get("newDocumentId")), BoundValues.longValue(inputOutputParameterMap.get("oldDocumentId")));
	}

	/** Marks the messages following a deleted document as deleted, reviewed by the worker now. @param inputOutputParameterMap p_user_id, p_documentId */
	public void deleteIndicatePsMessage(Map<Object, Object> inputOutputParameterMap) {
		jpql.update("update NyehmsStdMessageSent s set s.deleteInd = 'Y', s.reviewComments = 'Triggering Document Deleted', s.reviewDate = ?1, s.reviewUid = ?2 where s.followUpDocId = ?3",
				now(), BoundValues.text(inputOutputParameterMap.get("p_user_id")), BoundValues.longValue(inputOutputParameterMap.get("p_documentId")));
	}

	/** Ends the associations of the message documents following a deleted document. @param inputOutputParameterMap associationStatus, p_user_id, p_documentId */
	public void deletePsMessageForTriggeringDocument(Map<Object, Object> inputOutputParameterMap) {
		jpql.update("update PersonDocAssociation a set a.dissassociationDate = ?1, a.associationStatusCode = ?2, a.workerId = ?3"
				+ " where a.documentId in (select s.documentId from NyehmsStdMessageSent s where s.followUpDocId = ?4) and a.dissassociationDate is null",
				now(), BoundValues.text(inputOutputParameterMap.get("associationStatus")), BoundValues.longValue(inputOutputParameterMap.get("p_user_id")),
				BoundValues.longValue(inputOutputParameterMap.get("p_documentId")));
	}

	// ---- the raw and standardized tables (native, per-call table) ------------------------------------------------

	/** PKG_DOCUMENT_MANAGEMENT.pr_insert_raw_to_std. @param inputOutputParameterMap p_documentId, p_document_type, p_user_id, p_stateno, p_called_from; afterwards also p_code_out, p_msg_out */
	public void moveStdDocumentToDocAttrTables(Map<Object, Object> inputOutputParameterMap) {
		Map<Object, Object> map = inputOutputParameterMap;
		List<Object> results = statements.call("PKG_DOCUMENT_MANAGEMENT.pr_insert_raw_to_std",
				param(map.get("p_documentId"), String.class), param(map.get("p_document_type"), String.class), param(map.get("p_user_id"), String.class),
				param(map.get("p_stateno"), String.class), out(Integer.class), out(String.class), param(map.get("p_called_from"), String.class));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/** Sets the process status (and date, now) of a raw or standardized document. @param inputOutputParameterMap tableName (allow-listed), statusCode, document_ID */
	public int updateRawDocumentStatusCode(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("update " + StdTableNames.rawOrStdTable(inputOutputParameterMap.get("tableName"))
				+ " set CURRENT_PROCESS_STATUS = ?1, PROCESS_DATE = SYSDATE where DOCUMENT_ID = ?2",
				inputOutputParameterMap.get("statusCode"), inputOutputParameterMap.get("document_ID"));
	}

	/** Deletes a document from a standardized table. @param inputOutputParameterMap tableName (allow-listed), document_ID */
	public int deleteDocumentFromStdTable(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("delete from " + StdTableNames.table(inputOutputParameterMap.get("tableName")) + " where DOCUMENT_ID = ?1",
				inputOutputParameterMap.get("document_ID"));
	}

	/** Sets the process status of a standardized document. @param inputOutputParameterMap tableName (allow-listed), statusCode, document_ID */
	public int updateStdDocumentStatusCode(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("update " + StdTableNames.table(inputOutputParameterMap.get("tableName")) + " set CURRENT_PROCESS_STATUS = ?1 where DOCUMENT_ID = ?2",
				inputOutputParameterMap.get("statusCode"), inputOutputParameterMap.get("document_ID"));
	}

	/**
	 * Sets the process status, reviewer and dates (now) of raw documents.
	 *
	 * @param inputOutputParameterMap rawTableName (allow-listed), processStatus, workerId, documentIds (an array or collection)
	 */
	public void updateDocumentsStatus(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(inputOutputParameterMap.get("processStatus"));
		parameters.add(inputOutputParameterMap.get("workerId"));
		String ids = placeholders(inList(inputOutputParameterMap.get("documentIds"), "documentIds"), parameters, value -> value);
		statements.update("update " + StdTableNames.rawTable(inputOutputParameterMap.get("rawTableName"))
				+ " set CURRENT_PROCESS_STATUS = ?1, PROCESS_DATE = SYSDATE, REVIEW_UID = ?2, REVIEW_DATE = SYSDATE  where DOCUMENT_ID in (" + ids + ")",
				parameters.toArray());
	}

	/** A raw or standardized document's row, every column (as the former select * gave it). @param inputOutputParameterMap tableName (allow-listed), document_ID */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<Object, Object> getDocData(Map<Object, Object> inputOutputParameterMap) {
		return (Map) statements.one("select * from " + StdTableNames.rawOrStdTable(inputOutputParameterMap.get("tableName")) + " where DOCUMENT_ID =  ?1",
				Rows.map(), inputOutputParameterMap.get("document_ID"));
	}

	/**
	 * The matched state number and source id of a standardized document: STATENO, SOURCE_ID.
	 *
	 * @param inputOutputParameterMap tableName (allow-listed), STATENO (the column; the callers never give one, so it is
	 *        the literal null, as the former {@code ${STATENO}} rendered it), SOURCE_ID (a source-id column), documentId
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getStatenoAndSourceCodeFromStdDocData(Map<Object, Object> inputOutputParameterMap) {
		Object stateno = inputOutputParameterMap.get("STATENO");
		String statenoColumn = stateno == null || "null".equalsIgnoreCase(stateno.toString()) ? "null" : StdTableNames.identifier(stateno);
		return (Map) statements.one("select  " + statenoColumn + " STATENO,  " + StdTableNames.sourceIdColumn(inputOutputParameterMap.get("SOURCE_ID"))
				+ " SOURCE_ID  from " + StdTableNames.table(inputOutputParameterMap.get("tableName")) + " where DOCUMENT_ID =  ?1",
				Rows.map(), inputOutputParameterMap.get("documentId"));
	}

	/** A column of a raw or standardized table's row found by another column. @param inputOutputParameterMap tableName (allow-listed), targetColumn, refColumn (column names), refValue */
	public String getColumnDataFromTable(Map<Object, Object> inputOutputParameterMap) {
		return statements.one("select " + StdTableNames.identifier(inputOutputParameterMap.get("targetColumn")) + " from "
				+ StdTableNames.rawOrStdTable(inputOutputParameterMap.get("tableName")) + " where " + StdTableNames.identifier(inputOutputParameterMap.get("refColumn"))
				+ " =  ?1", Rows.scalar(String.class), inputOutputParameterMap.get("refValue"));
	}

	/** The NYC eHARS lab document with its raw address and person association. */
	public NYCLabDocVO getNycLabDoc(int docId) {
		return statements.one(CommonDocLabsSql.NYC_LAB_DOC, Rows.bean(NYCLabDocVO.class), docId);
	}

	// ---- the rest ---------------------------------------------------------------------------------------------------

	/** Resolves a CDC RIDR record with an ACRF, now. @param inputOutputParameterMap acrfDocId, workerId, ridrDocumentId */
	public int updateRIDRDocumentWithAcrf(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update CdcRidrInfo r set r.resolvedDocId = ?1, r.resolvedDate = ?2, r.resolvedUid = ?3, r.resolvedDocType = 'A'"
				+ " where r.ridrId = ?4 and r.resolvedDocId is null and r.discardDate is null", BoundValues.longValue(inputOutputParameterMap.get("acrfDocId")), now(),
				BoundValues.text(inputOutputParameterMap.get("workerId")), BoundValues.longValue(inputOutputParameterMap.get("ridrDocumentId")));
	}

	/** Copies the non-case history of a document to its edited copy. @param inputParameterMap newDocumentId, oldDocumentId */
	public void createNonCaseWithNewDocument(Map<String, Integer> inputParameterMap) {
		jpql.update("""
				insert into DeletedDocHistory (triggeringDocId, createId, createDate, flagActive, lastUpdateDate, lastUpdateId, reasonCode, comments)
				select cast(?1 as Integer), d.createId, d.createDate, d.flagActive, d.lastUpdateDate, d.lastUpdateId, d.reasonCode, d.comments from DeletedDocHistory d where d.triggeringDocId = ?2""",
				inputParameterMap.get("newDocumentId"), inputParameterMap.get("oldDocumentId"));
	}

	/** The provider and facility address ids of a document: PROVIDER_ADDRESS_ID, FACILITY_ADDRESS_ID (absent when null). */
	public Map<String, Long> getTriggeringDocumentAddressIds(int newDocumentId) {
		return jpql.one("select d.providerId as PROVIDER_ADDRESS_ID, d.facilityId as FACILITY_ADDRESS_ID from DocumentPersonAttr d where d.documentId = ?1", LONGS, newDocumentId);
	}

	public List<Integer> getOpenAssignmentsForTriggeringDocIds(int newDocumentId) {
		return jpql.list("select s.documentId from SurveillanceAssignment s where s.triggeringDocId = ?1", Rows.scalar(Integer.class), newDocumentId);
	}

	/** The current provider and facility address ids of an assignment: PROVIDER_ADDRESS_ID, FACILITY_ADDRESS_ID (absent when null). */
	public Map<String, Long> getAssignmentAddessIds(Integer assignmentId) {
		return jpql.one("select a.providerAddressId as PROVIDER_ADDRESS_ID, a.facilityAddressId as FACILITY_ADDRESS_ID from AssignmentAddress a where a.endDate is null and a.documentId = ?1",
				LONGS, BoundValues.longValue(assignmentId));
	}

	/** Who discarded a document, when and why: CREATE_ID (the worker's full name), CREATE_DATE (MM/DD/YYYY), REASON_DESC. */
	public Map<String, Object> getDiscardedDocumentDetails(int docId) {
		return jpql.one("""
				select u.fullName as CREATE_ID, format(d.createDate as 'MM/dd/yyyy') as CREATE_DATE, r.reasonDesc as REASON_DESC
				from DeletedDocHistory d, CodedNonCaseReason r, NyehmsUsersVw u
				where d.triggeringDocId = ?1 and d.reasonCode = r.reasonCode and u.workerId = d.createId""", Rows.map(), docId);
	}

	/** A staged clerical document shown as a person (its gender, county and ethnicity names looked up where coded; the person phone as home and cell phone). */
	public PersonViewVO getClericalDocumentAsPerson(int documentId) {
		PersonViewVO person = jpql.one("""
				select s.personFirstName as FNAME, s.personMiddleName as MNAME, s.personLastName as LNAME, s.personSuffix as SUFFIX, g.genderDescription as GENDER,
				       s.birthSex as birthSex, s.dodText as dateOfDeath, s.ssn as SSN, s.nysid as NYSID, s.din as DIN, s.medRecordNumber as MED_RECORD_NUMBER,
				       s.medicaidNumber as MEDICAID_NUMBER, s.insuranceNumber as INSURANCE_NUMBER, s.personDobText as DOB, s.birthCountry as BIRTH_COUNTRY,
				       e.ethnicityDescription as ETHNICITY, s.personAddress as PERSON_ADDRESS, s.personState as PERSON_STATE, s.personCity as PERSON_CITY,
				       c.countyName as PERSON_COUNTY, s.personZip as PERSON_ZIP, s.plus4 as PERSON_PLUS4, s.personPhone as homePhone,
				       s.personWorkPhone as workPhone
				from ErPersonClustersStage s left join CodedGender g on s.genderCode = g.genderCode left join CodedCounty c on s.personCounty = c.countyFipsCode
				     left join CodedEthnicity e on s.ethnicityCode = e.ethnicityCode
				where s.documentId = ?1""", Rows.bean(PersonViewVO.class), (long) documentId);
		if (person != null) {
			person.setCellPhone(person.getHomePhone());               // PERSON_PHONE was selected twice (cellPhone too); a column selected twice loses its first alias in a JPQL tuple
		}
		return person;
	}

	/** Points the open RIA document of a lab document to its edited copy. @param inputMap NEW_DOCUMENTID, OLD_DOCUMENTID */
	public void updateRiaLabDocument(Map<String, Integer> inputMap) {
		jpql.update("update NyehmsRiaDoc r set r.labDocId = ?1 where r.labDocId = ?2 and r.completeDate is null and r.mergedRiaStateno is null",
				BoundValues.text(inputMap.get("NEW_DOCUMENTID")), BoundValues.text(inputMap.get("OLD_DOCUMENTID")));
	}

	// ---- helpers ------------------------------------------------------------------------------------------------------

	/** A row as a map, its numbers as BigDecimals (as the former getObject gave them), nulls left out, a row of nulls null. */
	static final RowMapper<Map<String, Object>> NUMBERS_AS_BIG_DECIMAL = row -> {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < row.size(); i++) {
			Object value = row.get(i);
			if (value != null && row.indexOf(row.labels().get(i)) == i) {
				map.put(row.labels().get(i), value instanceof Number number && !(value instanceof BigDecimal) ? new BigDecimal(number.toString()) : value);
			}
		}
		return map.isEmpty() ? null : map;
	};

	/** A row as a map of Longs (the former result map with javaType Long), nulls left out, a row of nulls null. */
	static final RowMapper<Map<String, Long>> LONGS = row -> {
		Map<String, Long> map = new HashMap<>();
		for (int i = 0; i < row.size(); i++) {
			if (row.get(i) != null) {
				map.put(row.labels().get(i), Values.longValue(row.get(i)));
			}
		}
		return map.isEmpty() ? null : map;
	};

	/** An IN parameter bound as MyBatis bound it: with the value's own type, a null with the mapper's jdbcType. */
	private static CallParam param(Object value, Class<?> nullType) {
		return in(value, value == null ? nullType : value.getClass());
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	@SuppressWarnings("unchecked")
	private static void putIfPresent(JSONObject object, String key, Object value) {
		if (value != null) {
			object.put(key, value);
		}
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

	/** {@code ?n, ?n+1, ...} for the values (converted), which are added to the parameters. */
	private static String placeholders(List<Object> values, List<Object> parameters, java.util.function.Function<Object, Object> conversion) {
		StringBuilder text = new StringBuilder();
		for (Object value : values) {
			parameters.add(conversion.apply(value));
			text.append(text.length() > 0 ? ", " : "").append('?').append(parameters.size());
		}
		return text.toString();
	}
}
