package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The eCLRS lab results the schedulers read from the eCLRS views of the main database (VALLH for HIV, VALLS for STD)
 * and the reassignment of results (formerly a MyBatis mapper with the same name and methods on its own session
 * factory over the same data source; the callers are unchanged).
 * <p>
 * Native SQL throughout (the mapper's text with bound values), with the reason: every select is a {@code select *}
 * over the ~250 columns of an eCLRS view into the VO (no entity has these columns), with Oracle's {@code ROWNUM},
 * analytic {@code MAX / MIN ... OVER} and {@code SYSDATE - n} arithmetic; the reassignment is a {@code MERGE}. The
 * view ({@code ${sourceTable}} / {@code ${tableName}}) and the raw table ({@code ${NYEHMS_TABLE}}) chosen per call are
 * allow-listed ({@link #ECLRS_VIEWS}, {@link #RAW_TABLES}); anything else is refused. The former {@code <if>} on the
 * record type is a Java condition.
 */
@Repository
@RequiredArgsConstructor
public class EcrlsDataDAO {

	private final NativeStatements statements;

	/** The eCLRS views the schedulers read (the former {@code ${sourceTable}} / {@code ${tableName}}). */
	static final Set<String> ECLRS_VIEWS = Set.of("VALLH", "VALLS");

	/** The raw lab tables the reassignment reads (the former {@code ${NYEHMS_TABLE}}). */
	static final Set<String> RAW_TABLES = Set.of("RAW_HIV_ECLRS_DOC", "RAW_STD_ECLRS_DOC");

	/**
	 * The next results of a view after a key, oldest first (at most fetchRowCount - 1 rows, as {@code ROWNUM < n}
	 * gives), each with the highest and lowest key of the batch.
	 *
	 * @param inOutParameterMap sourceTable (a view), lastInsertedId, fetchRowCount
	 */
	public List<EcrlsLabDocVO> getEclrsRawDatatoClean(Map<Object, Object> inOutParameterMap) {
		String sql = """
				select rowData.*,1 as CURRENT_PROCESS_STATUS, max(OBXOBSERVATIONRESULTKEY) over(partition by 1 ) as maxOBXOBSERVATIONRESULTKEY, min(OBXOBSERVATIONRESULTKEY) over(partition by 1 ) as minOBXOBSERVATIONRESULTKEY
				                      FROM (SELECT * from %s where OBXOBSERVATIONRESULTKEY > ?1 and rownum < ?2 order by OBXOBSERVATIONRESULTKEY) rowData"""
				.formatted(view(inOutParameterMap.get("sourceTable")));
		return statements.list(sql, Rows.bean(EcrlsLabDocVO.class), inOutParameterMap.get("lastInsertedId"), inOutParameterMap.get("fetchRowCount"));
	}

	/**
	 * Marks the reassignment requests of a record type whose result exists in a raw lab table as processed (for HIV,
	 * only results created in the last 365 days).
	 *
	 * @param inOutParameterMap NYEHMS_TABLE (a raw lab table), recordType
	 */
	public void updateEcrlsProcessedRecords(Map<Object, Object> inOutParameterMap) {
		Object recordType = inOutParameterMap.get("recordType");
		String sql = """
				MERGE INTO lab_eclrs_reassign a
				USING (SELECT Distinct CASE OBX_OBR WHEN 'OBX' THEN obxobservationresultkey WHEN 'OBR' THEN obrobservationrequestkey END src_id
				  FROM %s a, lab_eclrs_reassign b
				WHERE ((b.OBX_OBR = 'OBX' AND b.src_id = a.obxobservationresultkey)
				        OR
				       (b.OBX_OBR = 'OBR' AND b.src_id = a.obrobservationrequestkey)
				       )
				%s  and b.hiv_std = ?1) b
				ON (a.src_id = b.src_id)
				WHEN MATCHED THEN
				UPDATE SET process_date = sysdate
				WHERE a.process_date IS NULL"""
				.formatted(rawTable(inOutParameterMap.get("NYEHMS_TABLE")), "HIV".equals(recordType) ? "   AND mshcreatedate >= sysdate - 365\n" : "");
		statements.update(sql, recordType);
	}

	/**
	 * The results of a view created in the last 30 days (HIV: of disease 428) with an unprocessed reassignment request
	 * of the record type.
	 *
	 * @param inOutParameterMap tableName (a view), recordType
	 */
	public List<EcrlsLabDocVO> getEcrlsReAssignDocData(Map<Object, Object> inOutParameterMap) {
		Object recordType = inOutParameterMap.get("recordType");
		String sql = """
				select vall.*,1 as CURRENT_PROCESS_STATUS
				  from %s vall
				where
				%sOBXCREATEDATE  > SYSDATE - 30 and
				exists (select 1
				                 from LAB_ECLRS_REASSIGN b
				                where b.hiv_std = ?1
				                  and process_date is null
				                  and ((b.src_id = vall.OBXOBSERVATIONRESULTKEY AND obx_obr = 'OBX') OR (b.src_id = vall.OBROBSERVATIONREQUESTKEY AND obx_obr = 'OBR'))
				               )"""
				.formatted(view(inOutParameterMap.get("tableName")), "HIV".equals(recordType) ? "OBXDISEASE = 428  and\n" : "");
		return statements.list(sql, Rows.bean(EcrlsLabDocVO.class), recordType);
	}

	/**
	 * The results of a view with the given keys.
	 *
	 * @param inOutParameterMap tableName (a view), erroredDocumentsList (a collection or array of keys; an empty or
	 *        missing one fails, as {@code IN ()} did)
	 */
	public List<EcrlsLabDocVO> getErroredPIDDocumentsListFromEclrs(Map<String, Object> inOutParameterMap) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder placeholders = new StringBuilder();
		for (Object key : inList(inOutParameterMap.get("erroredDocumentsList"), "erroredDocumentsList")) {
			parameters.add(key);
			placeholders.append(placeholders.length() > 0 ? "," : "").append('?').append(parameters.size());
		}
		String sql = "select vall.*,1 as CURRENT_PROCESS_STATUS from " + view(inOutParameterMap.get("tableName")) + " vall where OBXOBSERVATIONRESULTKEY in ("
				+ placeholders + ")";
		return statements.list(sql, Rows.bean(EcrlsLabDocVO.class), parameters.toArray());
	}

	/**
	 * The results of a view up to a key whose person data changed after a date, each with the latest change date of
	 * the batch.
	 *
	 * @param inOutParameterMap sourceTable (a view), lastInsertedId, lastPidUpdateDate
	 */
	public List<EcrlsLabDocVO> getPIDUpdatedRecordsFromEclrs(Map<Object, Object> inOutParameterMap) {
		String sql = """
				select rowData.*,1 as CURRENT_PROCESS_STATUS, max(PIDUPDATEDATE) over(partition by 1 ) as maxPIDUPDATEDATE
				                      FROM (SELECT * from %s where OBXOBSERVATIONRESULTKEY <= ?1 and PIDUPDATEDATE > ?2) rowData"""
				.formatted(view(inOutParameterMap.get("sourceTable")));
		return statements.list(sql, Rows.bean(EcrlsLabDocVO.class), inOutParameterMap.get("lastInsertedId"), inOutParameterMap.get("lastPidUpdateDate"));
	}

	// ---- helpers ----------------------------------------------------------------------------------------------------------

	/** A view name as the caller gave it, if it is one of {@link #ECLRS_VIEWS} (ignoring case); otherwise the call fails. */
	static String view(Object name) {
		return allowed(name, ECLRS_VIEWS, "eCLRS view");
	}

	/** A raw lab table name as the caller gave it, if it is one of {@link #RAW_TABLES} (ignoring case); otherwise the call fails. */
	static String rawTable(Object name) {
		return allowed(name, RAW_TABLES, "raw lab table");
	}

	private static String allowed(Object name, Set<String> names, String what) {
		if (name instanceof String text && names.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text.trim();
		}
		throw new InvalidDataAccessApiUsageException("Not an " + what + ": " + name);
	}

	/** The values of an array or collection for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static List<Object> inList(Object values, String name) {
		List<Object> list = new ArrayList<>();
		if (values instanceof Collection<?> collection) {
			list.addAll(collection);
		} else if (values instanceof Object[] array) {
			list.addAll(Arrays.asList(array));
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No values for IN () in " + name + ": " + values);
		}
		return list;
	}
}
