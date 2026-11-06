package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.DocumentLocatorRepository;
import lombok.RequiredArgsConstructor;

/**
 * Methods common to all documents: new document ids, the test type of a document, derived documents, the CDC RIDR
 * search and the CDC case definition check (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * JPQL over DocumentLocator, CdcRidrInfo, NyehmsEharsStatenoMap, EharsPersonNyc, PersonDocAssociation, StdDeathDoc and
 * StdNycDeathDoc; the PL/SQL function PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO is called from JPQL. The only native
 * call is the document id generator, the stored procedure PKG_DOCUMENT_MANAGEMENT.SP_GENERATE_NEW_DOC.
 * <p>
 * <b>Kept as before:</b> {@link #writeRidrData} always fails (its insert listed 35 columns for 32 values; nothing calls
 * it).
 *
 * @author Srikar Kummamuri
 */
@Repository
@RequiredArgsConstructor
public class DocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;
	private final DocumentLocatorRepository locators;

	/**
	 * Generates a new document id (PKG_DOCUMENT_MANAGEMENT.SP_GENERATE_NEW_DOC) and puts the procedure's OUT values
	 * into the map, as before: p_new_doc_id and p_return_code (Integer), p_msg_out (String).
	 *
	 * @param map p_document_type, p_parent_doc_id, p_test_type, p_received_date (each may be missing)
	 */
	@Transactional
	public void getNewDocId(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_DOCUMENT_MANAGEMENT.SP_GENERATE_NEW_DOC",
				in(map.get("p_document_type"), typeOf(map.get("p_document_type"), String.class)),
				in(map.get("p_parent_doc_id"), typeOf(map.get("p_parent_doc_id"), Integer.class)),
				out(Integer.class),
				out(Integer.class),
				out(String.class),
				in(map.get("p_test_type"), typeOf(map.get("p_test_type"), String.class)),
				in(dateValue(map.get("p_received_date")), typeOf(dateValue(map.get("p_received_date")), java.sql.Date.class)));
		map.put("p_new_doc_id", results.get(2));
		map.put("p_return_code", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/** The type a value is bound with: its own class (as MyBatis chose the type handler by the value), else the declared one. */
	private static Class<?> typeOf(Object value, Class<?> declared) {
		return value == null ? declared : value.getClass();
	}

	/** #{x,jdbcType=DATE}: a plain java.util.Date was bound as its day only (MyBatis' DateOnlyTypeHandler). */
	private static Object dateValue(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return new java.sql.Date(((java.util.Date) value).getTime());
		}
		return value;
	}

	/**
	 * Sets the test type of a document.
	 *
	 * @param inputOutputParameterMap documentID, testType
	 * @return the number of rows changed
	 */
	@Transactional
	public int writeDocumentTestType(Map<Object, Object> inputOutputParameterMap) {
		return locators.updateTestType(text(inputOutputParameterMap.get("testType")), integer(inputOutputParameterMap.get("documentID")));
	}

	/**
	 * Formerly inserted RIDR rows into CDC_RIDR_INFO. Its insert named 35 columns but selected 32 values, so it always
	 * failed in the database; kept failing the same way (nothing calls it).
	 *
	 * @throws InvalidDataAccessResourceUsageException always
	 */
	public void writeRidrData(Map<String, List<Map<String, Object>>> inputParameterMap) {
		throw new InvalidDataAccessResourceUsageException(
				"not enough values: the former CDC_RIDR_INFO insert listed 35 columns for 32 values (ORA-00947)");
	}

	/**
	 * The jurisdictions (both the NYS and the CDC one, except 'New York') of the unresolved, not discarded RIDR rows,
	 * once each, ordered.
	 *
	 * @param inputParameterMap fileType: unless null or 'ALL', only rows whose upper-case file name contains it
	 */
	public List<String> getRidrStateCodesToProcess(Map<String, String> inputParameterMap) {
		String fileType = inputParameterMap.get("fileType");
		boolean byFile = fileType != null && !fileType.equals("ALL");
		String fileCondition = byFile ? " and upper(r.fileName) like concat('%', ?1, '%')" : "";
		String query = "select distinct r.nysReportingJurisdiction from CdcRidrInfo r where r.resolvedDocId is null and r.discardDate is null"
				+ " and r.nysReportingJurisdiction != 'New York'" + fileCondition
				+ " union select distinct r.reportingJurisdiction from CdcRidrInfo r where r.resolvedDocId is null and r.discardDate is null"
				+ " and r.reportingJurisdiction != 'New York'" + fileCondition
				+ " order by 1";
		return byFile ? jpql.list(query, Rows.scalar(String.class), fileType) : jpql.list(query, Rows.scalar(String.class));
	}

	/**
	 * The unresolved, not discarded RIDR rows, as maps keyed by the former column labels (a key only when its value is
	 * set; RIDR_ID a BigDecimal, the rest text). NYEHMS_STATENO and NYS_STATENO are the highest NYEHMS / eHARS state
	 * number mapped to the current state number of NYS_STATENO.
	 *
	 * @param inputParameterMap stateValue: unless 'ALL', only rows of that NYS or CDC jurisdiction (null: no rows);
	 *        fileType: unless null or 'ALL', only rows whose upper-case file name contains it
	 */
	public List<Map<String, Object>> getRidrSearchViewResults(Map<String, String> inputParameterMap) {
		String stateValue = inputParameterMap.get("stateValue");
		String fileType = inputParameterMap.get("fileType");
		StringBuilder query = new StringBuilder("""
				select r.ridrId as RIDR_ID, format(r.parentDocCreateDt as 'MM/dd/yyyy') as PARENT_DOC_CREATE_DT,
				r.parentDocExists as PARENT_DOC_EXISTS,
				(select max(m.nyehmsStateno) from NyehmsEharsStatenoMap m
				 where m.nyehmsStateno = function('pkg_consolidation.fn_give_current_stateno' as String, r.nysStateno)) as NYEHMS_STATENO,
				r.nysNewlyReportedCdc as NYS_NEWLY_REPORTED_CDC, r.nysReportingJurisdiction as NYS_REPORTING_JURISDICTION,
				(select max(m.eharsStateno) from NyehmsEharsStatenoMap m
				 where m.nyehmsStateno = function('pkg_consolidation.fn_give_current_stateno' as String, r.nysStateno)) as NYS_STATENO,
				r.nysLastNameSoundex as NYS_LAST_NAME_SOUNDEX, r.nysDateOfBirth as NYS_DATE_OF_BIRTH, r.nysSexAtBirth as NYS_SEX_AT_BIRTH,
				r.nysDateOfDeath as NYS_DATE_OF_DEATH, r.nysTransmissionCategory as NYS_TRANSMISSION_CATEGORY,
				r.nysHivCaseDefCategory as NYS_HIV_CASE_DEF_CATEGORY, r.nysDateHivInfectionDiag as NYS_DATE_HIV_INFECTION_DIAG,
				r.nysResiHivInfectionDiag as NYS_RESI_HIV_INFECTION_DIAG, r.nysAidsCaseDefCategory as NYS_AIDS_CASE_DEF_CATEGORY,
				r.nysDateAidsDiag as NYS_DATE_AIDS_DIAG, r.nysResiAtAidsDiag as NYS_RESI_AT_AIDS_DIAG, r.nysDuplicateStatus as NYS_DUPLICATE_STATUS,
				r.newlyReportedCdc as NEWLY_REPORTED_CDC, r.reportingJurisdiction as REPORTING_JURISDICTION, r.stateno as STATENO,
				r.dateOfDeath as DATE_OF_DEATH, r.transmissionCategory as TRANSMISSION_CATEGORY, r.hivCaseDefCategory as HIV_CASE_DEF_CATEGORY,
				r.dateHivInfectionDiag as DATE_HIV_INFECTION_DIAG, r.resiHivInfectionDiag as RESI_HIV_INFECTION_DIAG,
				r.aidsCaseDefCategory as AIDS_CASE_DEF_CATEGORY, r.dateAidsDiag as DATE_AIDS_DIAG, r.resiAtAidsDiag as RESI_AT_AIDS_DIAG,
				r.duplicateStatus as DUPLICATE_STATUS, r.mergedCdc as MERGED_CDC, r.fileName as FILE_NAME
				from CdcRidrInfo r
				where r.resolvedDocId is null and r.discardDate is null""");
		List<Object> parameters = new ArrayList<>();
		if (!"ALL".equals(stateValue)) {
			parameters.add(stateValue);
			query.append(" and (r.nysReportingJurisdiction = ?1 or r.reportingJurisdiction = ?1)");
		}
		if (fileType != null && !fileType.equals("ALL")) {
			parameters.add(fileType);
			query.append(" and upper(r.fileName) like concat('%', ?").append(parameters.size()).append(", '%')");
		}
		return jpql.list(query.toString(), row -> {
			Map<String, Object> map = Rows.map().map(row);
			if (map != null && map.get("RIDR_ID") != null) {
				map.put("RIDR_ID", new BigDecimal(map.get("RIDR_ID").toString()));    // a NUMBER, as getObject read it
			}
			return map;
		}, parameters.toArray());
	}

	/**
	 * The document derived from a document: its child (PARENT_DOCUMENT_ID) with a higher id.
	 *
	 * @return the id, or null if there is none; more than one fails, as before
	 */
	public Integer getNewDocumentIdUsingParentDocument(Integer derivedDocumentId) {
		return single(locators.findDerivedDocumentIds(derivedDocumentId));
	}

	/**
	 * The parent document of a (multi-spot) document.
	 *
	 * @return the PARENT_DOCUMENT_ID, or null if the document or its parent is not set
	 */
	public Integer getNewDocumentIdUsingMultiSpotDocument(Integer derivedDocumentId) {
		return single(locators.findParentDocumentIds(derivedDocumentId));
	}

	/**
	 * 'Y' if the current person of a death document meets the CDC case definition (an HIV/AIDS diagnosis date in the
	 * state number map or in the NYC eHARS persons), else null.
	 *
	 * @param inputParameterMap docId; TABLE_NAME: STD_DEATH_DOC or STD_NYC_DEATH_DOC (the death document tables the
	 *        callers pass; any other name is refused)
	 */
	public String checkStatenoMeetsCDC(Map<String, Object> inputParameterMap) {
		String entity = deathDocumentEntity(inputParameterMap.get("TABLE_NAME"));
		return jpql.one("select 'Y' from EharsPersonNyc nyc, NyehmsEharsStatenoMap nys, PersonDocAssociation pdoc, " + entity + " std"
				+ " where std.documentId = ?1 and std.documentId = pdoc.documentId and pdoc.dissassociationDate is null"
				+ " and pdoc.stateno = nys.nyehmsStateno and pdoc.stateno = nyc.nyehmsStateno"
				+ " and (nys.hivAidsDxDt is not null or nyc.hivAidsDxDt is not null) fetch first 1 rows only",
				Rows.scalar(String.class), integer(inputParameterMap.get("docId")));
	}

	/** The entity of an allowed death document table (the former ${TABLE_NAME}). */
	private static String deathDocumentEntity(Object tableName) {
		String table = tableName == null ? "" : tableName.toString().trim().toUpperCase(Locale.ENGLISH);
		return switch (table) {
			case "STD_DEATH_DOC" -> "StdDeathDoc";
			case "STD_NYC_DEATH_DOC" -> "StdNycDeathDoc";
			default -> throw new IllegalArgumentException("Not a death document table: " + tableName);
		};
	}

	private static Integer single(List<Integer> values) {
		if (values.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, values.size());
		}
		return values.isEmpty() ? null : values.get(0);
	}

	private static Integer integer(Object value) {
		return value == null || "".equals(value) ? null : new BigDecimal(value.toString().trim()).intValueExact();
	}

	private static String text(Object value) {
		return value == null ? null : value.toString();
	}
}
