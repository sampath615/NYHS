package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.lang.reflect.Array;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The eCLRS HIV / STD lab documents: the document screen, the copies of an edited document, the HIV import's
 * standardized record and resistance sequences, the multi-spot (grouped LOINC) documents (formerly a MyBatis mapper
 * with the same name and methods; the callers are unchanged).
 * <p>
 * JPQL over the entities GenSequence, RawHivEclrsDoc, StdHivEclrsDoc, DocumentLocator, CodedOosClia and
 * CodedLoincGrouping. Native SQL ({@link LabDocumentSql}, the mapper's text with bound values), with the reason:
 * <ul>
 * <li>{@link #getLabDoc}: the raw and standardized table are chosen per call (HIV or STD; allow-listed), ~120 columns
 * of tables without a full entity;</li>
 * <li>{@link #getHivLabDoc}, {@link #getRawDataAsObject}, {@link #getStdDataAsObject}, {@link #getRawDocumentsData}:
 * {@code select *} over the ~250 columns of the raw / standardized HIV tables, read into the VO / maps by column
 * name;</li>
 * <li>the copies and imports ({@link #insertLabDocData}, {@link #insertRawHivDocument}, {@link #insertRawStdDocument},
 * {@link #insertStdHivDocument}, {@link #insertStdStdDocument}, {@link #insertRawEditedHIVLabDocumentWithUpdateData},
 * {@link #insertStdEditedHivLabDocumentWithUpdateData}, {@link #insertStandardEcrlsRecord}): inserts of 80 to 230
 * columns from maps / VOs with {@code trim(upper(...))}, {@code TO_DATE} and {@code SYSDATE} computed by the database,
 * into tables without a full entity;</li>
 * <li>{@link #updateHashUniqueNumber}: {@code ORA_HASH}, {@code TO_CLOB};</li>
 * <li>{@link #getMultiSpotDocuments}: a CTE, {@code MAX ... OVER (PARTITION BY ...)}, {@code REGEXP_LIKE}, a row-value
 * {@code IN (select ... from dual)};</li>
 * <li>{@link #restageStdDocumentNonMatches}: the SQL comes from the scheduler configuration (kept as it was).</li>
 * </ul>
 * Dates the former SQL set with {@code SYSDATE} are still the database's time.
 */
@Repository
@RequiredArgsConstructor
public class LabDocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	// ---- the lab document screen -----------------------------------------------------------------------------------

	/** The raw / standardized tables {@link #getLabDoc} may read (the former {@code ${raw_table_name}} / {@code ${std_table_name}}). */
	static final Set<String> LAB_TABLES = Set.of("RAW_HIV_ECLRS_DOC", "STD_HIV_ECLRS_DOC", "RAW_STD_ECLRS_DOC", "STD_STD_ECLRS_DOC");

	/**
	 * A lab document for the screen: the standardized row, the raw addresses, the raw result text and note, the current
	 * person and the parent document (the first of two columns named PFI, MEDICAID_NUMBER and NYC_SHIP_DATE is read, as
	 * before).
	 *
	 * @param map raw_table_name, std_table_name (one of {@link #LAB_TABLES}, in any case; another name fails), docId
	 * @return the document, or null if there is none
	 */
	public StdEcrlsLabDocVO getLabDoc(Map<Object, Object> map) throws DataAccessException {
		String sql = LabDocumentSql.GET_LAB_DOC.replace("${raw_table_name}", allowedTable(map.get("raw_table_name")))
				.replace("${std_table_name}", allowedTable(map.get("std_table_name")));
		return statements.one(sql, Rows.bean(StdEcrlsLabDocVO.class), map.get("docId"));
	}

	/** A table name from the caller, as it was given, if it is one of {@link #LAB_TABLES} (ignoring case); otherwise the call fails. */
	private static String allowedTable(Object name) {
		if (name instanceof String text && LAB_TABLES.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text;
		}
		throw new InvalidDataAccessApiUsageException("Not a lab document table: " + name);
	}

	/** The raw HIV lab document (every column of RAW_HIV_ECLRS_DOC), or null. */
	public EcrlsLabDocVO getHivLabDoc(int docId) {
		return statements.one(LabDocumentSql.GET_HIV_LAB_DOC, Rows.bean(EcrlsLabDocVO.class), docId);
	}

	/** The raw HIV lab document's row as a map (column name to value, nulls left out), or null. */
	public Map<String, Object> getRawDataAsObject(Integer documentId) {
		return statements.one(LabDocumentSql.GET_RAW_DATA, Rows.map(), documentId);
	}

	/** The standardized HIV lab document's row as a map (column name to value, nulls left out), or null. */
	public Map<String, Object> getStdDataAsObject(Integer documentId) {
		return statements.one(LabDocumentSql.GET_STD_DATA, Rows.map(), documentId);
	}

	/** The raw HIV lab documents with the given ids (every column), in the database's order. */
	public List<EcrlsLabDocVO> getRawDocumentsData(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String sql = "select * from RAW_HIV_ECLRS_DOC where document_id in ("
				+ placeholders(inList(inputOutputParameterMap.get("documentIds"), "documentIds"), parameters) + ")";
		return statements.list(sql, Rows.bean(EcrlsLabDocVO.class), parameters.toArray());
	}

	// ---- copies of a document and the HIV import ---------------------------------------------------------------------

	/** Writes an old-format lab document (RAW_LAB_DOC) from the VO; DATE_RECEIVED and DATE_SENT are MM/DD/YYYY texts. */
	public void insertLabDocData(LabDocVO stdLabDocVO) throws DataAccessException {
		statements.update(LabDocumentSql.INSERT_LAB_DOC, LabDocumentSql.insertLabDocDataArgs(stdLabDocVO));
	}

	/** Writes the raw row of a copied HIV lab document (PROCESS_DATE the database's SYSDATE). @return rows inserted */
	public int insertRawHivDocument(Map<Object, Object> rawDocData) {
		return statements.update(LabDocumentSql.INSERT_RAW_HIV_DOCUMENT, LabDocumentSql.insertRawHivDocumentArgs(rawDocData));
	}

	/** Writes the raw row of a copied STD lab document (PROCESS_DATE the database's SYSDATE). @return rows inserted */
	public int insertRawStdDocument(Map<Object, Object> rawDocData) {
		return statements.update(LabDocumentSql.INSERT_RAW_STD_DOCUMENT, LabDocumentSql.insertRawStdDocumentArgs(rawDocData));
	}

	/** Writes the standardized row of a copied HIV lab document. @return rows inserted */
	public int insertStdHivDocument(Map<Object, Object> stdDocData) {
		return statements.update(LabDocumentSql.INSERT_STD_HIV_DOCUMENT, LabDocumentSql.insertStdHivDocumentArgs(stdDocData));
	}

	/** Writes the standardized row of a copied STD lab document. @return rows inserted */
	public int insertStdStdDocument(Map<Object, Object> stdDocData) {
		return statements.update(LabDocumentSql.INSERT_STD_STD_DOCUMENT, LabDocumentSql.insertStdStdDocumentArgs(stdDocData));
	}

	/**
	 * Writes the raw row of an edited HIV lab document (the original row's values, changed by the upload;
	 * PIDADDRESSLINE2 is written as NULL, as before). @return rows inserted
	 */
	public int insertRawEditedHIVLabDocumentWithUpdateData(Map<String, Object> originalRawData) {
		return statements.update(LabDocumentSql.INSERT_RAW_EDITED_HIV_DOCUMENT, LabDocumentSql.insertRawEditedHIVLabDocumentWithUpdateDataArgs(originalRawData));
	}

	/** Writes the standardized row of an edited HIV lab document. @return rows inserted */
	public int insertStdEditedHivLabDocumentWithUpdateData(Map<String, Object> originalStdData) {
		return statements.update(LabDocumentSql.INSERT_STD_EDITED_HIV_DOCUMENT, LabDocumentSql.insertStdEditedHivLabDocumentWithUpdateDataArgs(originalStdData));
	}

	/** Writes the standardized record of an imported eCLRS HIV lab document from the VO. @return rows inserted */
	public int insertStandardEcrlsRecord(StdEcrlsLabDocVO stdEcrlsLabDocVO) {
		return statements.update(LabDocumentSql.INSERT_STANDARD_ECLRS_RECORD, LabDocumentSql.insertStandardEcrlsRecordArgs(stdEcrlsLabDocVO));
	}

	/** Recomputes HASH_UNIQUE_NUMBER of a raw HIV lab document (ORA_HASH over its columns, in the database). */
	public void updateHashUniqueNumber(Integer documentId) {
		statements.update(LabDocumentSql.UPDATE_HASH_UNIQUE_NUMBER, documentId);
	}

	/**
	 * Runs the restage / rollback statement of the quality-stage non-matches as the scheduler configuration holds it
	 * (STD_NON_MATCHES_RESTAGE_QUERY / STD_NON_MATCHES_ROLLBACK_QUERY — SQL from the database, run as it is, as before).
	 *
	 * @param inputParameterMap sqlQuery
	 * @return the rows changed
	 */
	public int restageStdDocumentNonMatches(Map<String, String> inputParameterMap) {
		return statements.update(inputParameterMap.get("sqlQuery"));
	}

	// ---- resistance sequences (GEN_SEQUENCE) -------------------------------------------------------------------------

	/** Deletes the sequence rows of a lab document that were not yet linked (VLINK_DATE is null). */
	public void deleteResistanceData(int documentId) {
		jpql.update("delete from GenSequence g where g.labDocId = ?1 and g.vlinkDate is null", documentId);
	}

	/**
	 * Adds one segment of a document's resistance sequence.
	 *
	 * @param inputOutputParameterMap documentId, sequence (the segment's text), sequence_length, eclrs_key (SRC_ID_NUM),
	 *        currentIndex (SEGMENTNO), collection_date (a date; a java.util.Date as its day, as {@code jdbcType=DATE} bound
	 *        it), rasElig
	 * @return 1, the rows inserted
	 */
	public int insertStandardHIVResistanceResult(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("""
				insert into GenSequence (labDocId, genSeq, seqLen, srcIdNum, segmentno, specimenDate, rasElig)
				values (?1, ?2, ?3, ?4, ?5, ?6, ?7)""",
				BoundValues.integer(inputOutputParameterMap.get("documentId")),
				BoundValues.text(inputOutputParameterMap.get("sequence")),
				BoundValues.integer(inputOutputParameterMap.get("sequence_length")),
				BoundValues.longValue(inputOutputParameterMap.get("eclrs_key")),
				BoundValues.integer(inputOutputParameterMap.get("currentIndex")),
				BoundValues.dateOnly(inputOutputParameterMap.get("collection_date")),
				BoundValues.text(inputOutputParameterMap.get("rasElig")));
	}

	/**
	 * Copies the sequence rows of a document for a new document (the other columns as they are).
	 *
	 * @param inputParameterMap NEW_DOCUMENT_ID, CURRENT_DOCUMENT_ID
	 * @return the rows copied
	 */
	public int insertStandardHIVResistanceResultFromOldDocument(Map<String, Integer> inputParameterMap) {
		return jpql.update("""
				insert into GenSequence (labDocId, seqLen, srcIdNum, specimenDate, segmentno, stdrptsId, vlinkDate, rasElig, genSeq)
				select cast(?1 as Integer), g.seqLen, g.srcIdNum, g.specimenDate, g.segmentno, g.stdrptsId, g.vlinkDate, g.rasElig, g.genSeq
				from GenSequence g where g.labDocId = ?2""",
				BoundValues.integer(inputParameterMap.get("NEW_DOCUMENT_ID")), BoundValues.integer(inputParameterMap.get("CURRENT_DOCUMENT_ID")));
	}

	// ---- multi-spot (grouped LOINC) documents ------------------------------------------------------------------------

	/**
	 * The raw HIV documents of a laboratory whose LOINC codes form a group in force (CODED_LOINC_GROUPING), in status
	 * 11, ranked by the highest document id of each identical patient / provider / facility (every column of the raw
	 * row, the dates as MM/DD/YYYY, plus RANKING), as maps.
	 *
	 * @param inputParameterMap loincCodes (the four codes of the group, 'X' for a missing one), pfiList (null for all
	 *        laboratories)
	 */
	public List<Map<String, Object>> getMultiSpotDocuments(Map<String, Object> inputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		String pfiFilter = "";
		if (inputParameterMap.get("pfiList") != null) {
			pfiFilter = String.format(LabDocumentSql.MULTI_SPOT_PFI_FILTER, placeholders(inList(inputParameterMap.get("pfiList"), "pfiList"), parameters));
		}
		String loincs = placeholders(inList(inputParameterMap.get("loincCodes"), "loincCodes"), parameters);
		return statements.list(String.format(LabDocumentSql.MULTI_SPOT_DOCUMENTS, pfiFilter, loincs), Rows.map(), parameters.toArray());
	}

	/**
	 * Sets the process status (when one is given) and PROCESS_DATE (the database's SYSDATE) of raw HIV documents.
	 *
	 * @param inputOutputParameterMap statusCode (left unchanged when null, empty or 0 — the former OGNL test
	 *        {@code statusCode != null and statusCode != ''}), documentIds
	 * @return the rows changed
	 */
	public int updateStatusCodeForHivDocuments(Map<Object, Object> inputOutputParameterMap) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder update = new StringBuilder("update RawHivEclrsDoc r set ");
		Object statusCode = inputOutputParameterMap.get("statusCode");
		if (ognlNotEmpty(statusCode)) {
			parameters.add(BoundValues.longValue(statusCode));
			update.append("r.currentProcessStatus = ?1, ");
		}
		List<Object> ids = new ArrayList<>();
		for (Object id : inList(inputOutputParameterMap.get("documentIds"), "documentIds")) {
			ids.add(BoundValues.longValue(id));
		}
		update.append("r.processDate = sysdate where r.documentId in (").append(placeholders(ids, parameters)).append(")");
		return jpql.update(update.toString(), parameters.toArray());
	}

	/** The parent document of a document (DOCUMENT_LOCATOR), or null. */
	public Integer getMultiStepDocumentId(Integer documentId) {
		return jpql.one("select distinct d.parentDocumentId as PARENT_DOCUMENT_ID from DocumentLocator d where d.documentId = ?1",
				Rows.scalar(Integer.class), documentId);
	}

	/**
	 * The process status of a raw HIV document and of its standardized row: DOCUMENT_ID, CURRENT_PROCESS_STATUS_RAW,
	 * CURRENT_PROCESS_STATUS_STD (Integers), MATCH_CD (FLUX_MATCH_CD); a null value is left out; null if there is no raw
	 * document.
	 */
	public Map<String, Object> hivDocumentCurrentProcessStatusDetails(Integer documentId) {
		return jpql.one("""
				select r.documentId as DOCUMENT_ID, r.currentProcessStatus as CURRENT_PROCESS_STATUS_RAW,
				       s.currentProcessStatus as CURRENT_PROCESS_STATUS_STD, s.fluxMatchCd as MATCH_CD
				from RawHivEclrsDoc r left join StdHivEclrsDoc s on s.documentId = r.documentId
				where r.documentId = ?1""", STATUS_DETAILS, BoundValues.longValue(documentId));
	}

	/** The former result map nyehmsDocumentByCurrentStatusDetails: three Integer columns and a text, nulls left out. */
	static final RowMapper<Map<String, Object>> STATUS_DETAILS = row -> {
		Map<String, Object> details = new HashMap<>();
		for (String column : List.of("DOCUMENT_ID", "CURRENT_PROCESS_STATUS_RAW", "CURRENT_PROCESS_STATUS_STD")) {
			Long value = Values.longValue(row.get(column));
			if (value != null) {
				details.put(column, Math.toIntExact(value));
			}
		}
		String matchCd = Values.text(row.get("MATCH_CD"));
		if (matchCd != null) {
			details.put("MATCH_CD", matchCd);
		}
		return details.isEmpty() ? null : details;
	};

	// ---- code tables -----------------------------------------------------------------------------------------------

	/** The out-of-state CLIA of a facility (CODED_OOS_CLIA, by its upper-case name): FACILITY_NAME, CLIA; null if unknown. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getOOSCliaDetails(String FACILITY_NAME) {
		return (Map) jpql.one("select c.facilityName as FACILITY_NAME, c.clia as CLIA from CodedOosClia c where c.facilityName = upper(?1)",
				Rows.map(), FACILITY_NAME);
	}

	/**
	 * The LOINC groups in force, by category name: LOINCS (the four codes comma-separated, 'X' for a missing third or
	 * fourth), GROUP_COUNT, CATEGORY_NAME, PFI, GROUP_ID, NEW_DOCUMENT.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getLabCombiningDocData() {
		return (List) jpql.list("""
				select distinct concat(g.loinc1, ',', g.loinc2, ',', coalesce(g.loinc3, 'X'), ',', coalesce(g.loinc4, 'X')) as LOINCS,
				       g.groupCount as GROUP_COUNT, g.categoryName as CATEGORY_NAME, g.pfi as PFI, g.loincGroupId as GROUP_ID,
				       g.newDocument as NEW_DOCUMENT
				from CodedLoincGrouping g where g.endDate is null or g.endDate > sysdate
				order by g.categoryName asc""", Rows.map());
	}

	// ---- helpers -----------------------------------------------------------------------------------------------------

	/**
	 * OGNL's {@code x != null and x != ''} of the former dynamic SQL: false for null, for an empty text and for a number
	 * (or a numeric text) equal to 0, which OGNL compared equal to ''.
	 */
	static boolean ognlNotEmpty(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof Number number) {
			return number.doubleValue() != 0;
		}
		String text = value.toString();
		if (text.isEmpty()) {
			return false;
		}
		try {
			return Double.parseDouble(text) != 0;
		} catch (NumberFormatException e) {
			return true;
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
