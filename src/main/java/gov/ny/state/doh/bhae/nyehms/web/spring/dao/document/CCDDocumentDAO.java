package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDDiagnosisVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDEncounterVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDMedVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPayerVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProblemVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.StdCCDDocVo;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The CCD (continuity of care) documents received through UPHN: the loader's raw writes and its raw-to-standardized
 * copy, the document viewer's lists and the experimental CCD table pages (formerly a MyBatis mapper with the same name
 * and methods; the callers are unchanged). The interface methods that had no statement and no caller
 * ({@code insertRawCcdPersonAddress}, {@code insertRawCcdProvider}, {@code insertRawCcdPayer},
 * {@code insertRawCcdLabTest}, {@code insertRawCcdEncounter}, {@code insertRawCcdMed}, {@code insertHitLoadLog},
 * {@code insertHitError}, {@code insertEXPFromRAWToSTD}, {@code updateRawProcessDate(int, String, Date)}) are gone.
 * <p>
 * JPQL: {@link #getCCDDoc} and {@link #getStdCCDDoc} (over StdCcdDoc, RawCcdDoc and PersonDocAssociation). Native SQL
 * ({@link CCDDocumentSql}, the mapper's text), with the reason:
 * <ul>
 * <li>the raw inserts ({@link #insertRawCcdDocData}, {@link #insertRawCcdLabResult}, the nine {@code insertCcd*List}
 * methods) and the copies {@link #insertCCDFromRAWToSTD}, {@link #insertDiagnosisFromRAWToSTD}: the conversions stay in
 * the database ({@code upper(trim(...))}, {@code substr}, {@code TO_CHAR(TO_DATE(...))}, {@code FN_TO_DATE}), the keys
 * come from sequences inside the statement ({@code seq.NEXTVAL, d.*}); the raw tables have no entities. The former
 * {@code foreach ... union all} inserts are one insert per item, in one transaction (a missing or empty list fails, as
 * the empty {@code union all} did);</li>
 * <li>{@link #getEncounterSequences}, {@link #getLabTestSequences}: {@code CONNECT BY LEVEL} over a sequence;</li>
 * <li>{@link #getNewRawCCDList}: {@code ROW_NUMBER() OVER} with the {@code (+)} join, whole rows of the raw tables;</li>
 * <li>the viewer lists ({@code getCCD*List}, {@link #getDiagnosisList}, {@link #getLabResultsList}): Oracle text-to-date
 * parsing ({@code TO_DATE(substr(x, 0, 8), 'YYYYMMDD')}), {@code NVL}/{@code ||} composites, {@code (+)} and
 * {@code CASE} over facility names;</li>
 * <li>{@link #getAllCcdCountIndex}: nine {@code LEFT JOIN}ed count subqueries with {@code rownum};</li>
 * <li>{@link #updateRawReviewedStatusCode}, {@link #updateRawDiagnosisMovedToSTDStatusCode},
 * {@link #updateRawProcessDateInp}, {@link #getChildEntryData}: the table (and column) are chosen by the caller, now
 * allow-listed (the raw CCD tables / {@link StdTableNames#RAW_TABLES}), the values bound;</li>
 * <li>{@link #addUphnRecord}, {@link #updateUphnRecord}: the received time is bound as the caller gives it (formerly a
 * {@code VARCHAR} binding into a DATE column, converted by Oracle) and the payload's binary column has no entity
 * mapping.</li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class CCDDocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	// ---- the loader: raw writes ---------------------------------------------------------------------------------------

	/** Writes a received CCD's person data (RAW_CCD_DOC). */
	public void insertRawCcdDocData(RawCCDPersonDocVO rcdVO) throws DataAccessException {
		statements.update(CCDDocumentSql.RAW_CCD_DOC_DATA, CCDDocumentSql.insertRawCcdDocDataArgs(rcdVO));
	}

	/** Writes a received CCD's lab result (RAW_CCD_LAB_RESULT, key from its sequence). */
	public void insertRawCcdLabResult(RawCCDLabResultVO theLabResultVO) throws DataAccessException {
		statements.update(CCDDocumentSql.RAW_CCD_LAB_RESULT, CCDDocumentSql.insertRawCcdLabResultArgs(theLabResultVO));
	}

	/** Writes a CCD's addresses. @param inputParameterMapAddress ccdDocId, addressesList @return the rows written */
	@Transactional
	public int insertCcdAddressList(Map<String, Object> inputParameterMapAddress) {
		return perItem(inputParameterMapAddress, "addressesList", CCDDocumentSql.CCD_ADDRESS_ITEM, CCDDocumentSql::insertCcdAddressListItemArgs);
	}

	/** Writes a CCD's providers. @param inputParameterMapProvider ccdDocId, providersList */
	@Transactional
	public int insertCcdProvidersList(Map<String, Object> inputParameterMapProvider) {
		return perItem(inputParameterMapProvider, "providersList", CCDDocumentSql.CCD_PROVIDER_ITEM, CCDDocumentSql::insertCcdProvidersListItemArgs);
	}

	/** Writes a CCD's payers. @param inputParameterMapPayer ccdDocId, payersList */
	@Transactional
	public int insertCcdPayersList(Map<String, Object> inputParameterMapPayer) {
		return perItem(inputParameterMapPayer, "payersList", CCDDocumentSql.CCD_PAYER_ITEM, CCDDocumentSql::insertCcdPayersListItemArgs);
	}

	/** Writes a CCD's medications. @param inputParameterMapMeds ccdDocId, medsList */
	@Transactional
	public int insertCcdMedsList(Map<String, Object> inputParameterMapMeds) {
		return perItem(inputParameterMapMeds, "medsList", CCDDocumentSql.CCD_MED_ITEM, CCDDocumentSql::insertCcdMedsListItemArgs);
	}

	/** Writes a CCD's problems. @param inputParameterMapProblems ccdDocId, problemsList */
	@Transactional
	public int insertCcdProblemsList(Map<String, Object> inputParameterMapProblems) {
		return perItem(inputParameterMapProblems, "problemsList", CCDDocumentSql.CCD_PROBLEM_ITEM, CCDDocumentSql::insertCcdProblemsListItemArgs);
	}

	/** Writes a CCD's lab results. @param inputParameterMapLabRes ccdDocId, labResultsList */
	@Transactional
	public int insertCcdLabResultsList(Map<String, Object> inputParameterMapLabRes) {
		return perItem(inputParameterMapLabRes, "labResultsList", CCDDocumentSql.CCD_LAB_RESULT_ITEM, CCDDocumentSql::insertCcdLabResultsListItemArgs);
	}

	/** Writes a CCD's encounter diagnoses. @param diagnosisMap diagnosisList */
	@Transactional
	public int insertCcdDiagnosisList(Map<String, Object> diagnosisMap) {
		return perItem(diagnosisMap, "diagnosisList", CCDDocumentSql.CCD_DIAGNOSIS_ITEM, CCDDocumentSql::insertCcdDiagnosisListItemArgs);
	}

	/** Writes a CCD's encounters (keys given by the caller, see {@link #getEncounterSequences}). @param inputParameterMapEncounters ccdDocId, encounterList */
	@Transactional
	public int insertCcdEncounterList(Map<String, Object> inputParameterMapEncounters) {
		return perItem(inputParameterMapEncounters, "encounterList", CCDDocumentSql.CCD_ENCOUNTER_ITEM, CCDDocumentSql::insertCcdEncounterListItemArgs);
	}

	/** Writes a CCD's lab tests (keys given by the caller, see {@link #getLabTestSequences}). @param inputParameterMapLabs ccdDocId, LabsList */
	@Transactional
	public int insertCcdLabTestList(Map<String, Object> inputParameterMapLabs) {
		return perItem(inputParameterMapLabs, "LabsList", CCDDocumentSql.CCD_LAB_TEST_ITEM, CCDDocumentSql::insertCcdLabTestListItemArgs);
	}

	/** The next keys of RAW_CCD_ENCOUNTER, one per encounter. */
	public List<Integer> getEncounterSequences(int encounterLength) {
		return statements.list(CCDDocumentSql.ENCOUNTER_SEQUENCES, Rows.scalar(Integer.class), encounterLength);
	}

	/** The next keys of RAW_CCD_LAB_TEST, one per lab test. */
	public List<Integer> getLabTestSequences(int labTestlength) {
		return statements.list(CCDDocumentSql.LAB_TEST_SEQUENCES, Rows.scalar(Integer.class), labTestlength);
	}

	// ---- the loader: raw to standardized ----------------------------------------------------------------------------

	/** Copies a raw CCD's person data to STD_CCD_DOC, converted as the mapper did. @return the rows written */
	public int insertCCDFromRAWToSTD(Map<Object, Object> stdDocData) {
		return statements.update(CCDDocumentSql.CCD_FROM_RAW_TO_STD, CCDDocumentSql.insertCCDFromRAWToSTDArgs(stdDocData));
	}

	/** Copies a raw diagnosis to STD_CCD_DIAGNOSIS (its encounter date parsed from START_DATE_TEXT). @return the rows written */
	public int insertDiagnosisFromRAWToSTD(Map<Object, Object> stdDiagnosisData) {
		return statements.update(CCDDocumentSql.DIAGNOSIS_FROM_RAW_TO_STD, CCDDocumentSql.insertDiagnosisFromRAWToSTDArgs(stdDiagnosisData));
	}

	/** The tables with a DIAGNOSIS_ID the reviewed / moved flags are set in. */
	static final Set<String> DIAGNOSIS_TABLES = Set.of("RAW_CCD_DIAGNOSIS", "STD_CCD_DIAGNOSIS");

	/** Sets the REVIEWED flag of a diagnosis. @param inputOutputParameterMap tableName (allow-listed), statusCode, diagnosisID @return the rows changed */
	public int updateRawReviewedStatusCode(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("update " + allowed(inputOutputParameterMap.get("tableName"), DIAGNOSIS_TABLES, "diagnosis table") + " set REVIEWED = ?1 where DIAGNOSIS_ID = ?2",
				BoundValues.text(inputOutputParameterMap.get("statusCode")), inputOutputParameterMap.get("diagnosisID"));
	}

	/** Sets the MOVED_TO_STD flag of a diagnosis. @param inputOutputParameterMap tableName (allow-listed), statusCode, diagnosisID @return the rows changed */
	public int updateRawDiagnosisMovedToSTDStatusCode(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("update " + allowed(inputOutputParameterMap.get("tableName"), DIAGNOSIS_TABLES, "diagnosis table") + " set MOVED_TO_STD = ?1 where DIAGNOSIS_ID = ?2",
				BoundValues.text(inputOutputParameterMap.get("statusCode")), inputOutputParameterMap.get("diagnosisID"));
	}

	/** Sets the process date of a raw document. @param inputOutputParameterMap tableName (a raw document table), processDate, document_ID @return the rows changed */
	public int updateRawProcessDateInp(Map<Object, Object> inputOutputParameterMap) {
		return statements.update("update " + StdTableNames.rawTable(inputOutputParameterMap.get("tableName")) + " set PROCESS_DATE = ?1 where DOCUMENT_ID = ?2",
				inputOutputParameterMap.get("processDate"), inputOutputParameterMap.get("document_ID"));
	}

	/** The raw CCDs awaiting standardization (status 1 or 4), each with its first address (by use, then sequence): whole rows. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getNewRawCCDList() {
		return (List) statements.list(CCDDocumentSql.NEW_RAW_CCD_LIST, Rows.map());
	}

	// ---- the document viewer ---------------------------------------------------------------------------------------------

	/** A standardized CCD's person data as the raw VO (the columns the VO has: ids, DOB text, gender, ethnicity, language, phones, MPI); null if none. */
	public RawCCDPersonDocVO getCCDDoc(int docId) {
		return jpql.one("""
				select s.documentId as DOCUMENT_ID, s.fname as FNAME, s.mname as MNAME, s.lname as LNAME, s.dobText as DOB_TEXT, s.gender as GENDER,
				       s.ethnicity as ETHNICITY, s.raceWhite as RACE_WHITE, s.raceBlack as RACE_BLACK, s.raceAsian as RACE_ASIAN, s.raceIndian as RACE_INDIAN,
				       s.racePacific as RACE_PACIFIC, s.raceUnknown as RACE_UNKNOWN, s.raceOther as RACE_OTHER, s.raceOtherText as RACE_OTHER_TEXT,
				       s.language as LANGUAGE, s.personAddress as PERSON_ADDRESS, s.personCity as PERSON_CITY, s.personState as PERSON_STATE,
				       s.personZip as PERSON_ZIP, s.personPlus4 as PERSON_PLUS4, s.personCounty as PERSON_COUNTY, s.personPhone as PERSON_PHONE,
				       s.personAddressId as PERSON_ADDRESS_ID, s.personCountry as PERSON_COUNTRY, s.personPhoneHome as PERSON_PHONE_HOME,
				       s.personPhoneCell as PERSON_PHONE_CELL, s.personPhoneWork as PERSON_PHONE_WORK, s.addressUse as ADDRESS_USE,
				       s.useablePeriodLow as USEABLE_PERIOD_LOW, s.useablePeriodHigh as USEABLE_PERIOD_HIGH, s.createDate as CREATE_DATE, s.mpi as MPI,
				       r.uphnTranxInId as UPHN_TRANX_IN_ID
				from StdCcdDoc s left join RawCcdDoc r on s.documentId = r.documentId
				where s.documentId = ?1""", Rows.bean(RawCCDPersonDocVO.class), docId);
	}

	/** A standardized CCD with its current person association (state number, discard status); null if none. */
	public StdCCDDocVo getStdCCDDoc(int docId) {
		return jpql.one("""
				select s.documentId as documentId, s.fname as fName, s.mname as mName, s.lname as lName, s.dobText as dobText, s.gender as gender,
				       s.ethnicity as ethinicity, s.raceWhite as raceWhite, s.raceBlack as raceBlack, s.raceAsian as raceAsian, s.raceIndian as raceIndian,
				       s.racePacific as racePacific, s.raceUnknown as raceUnknown, s.raceOther as raceOther, s.raceOtherText as raceOtherText,
				       s.language as language, s.personAddress as personAddress, s.personCity as personCity, s.personState as personState,
				       s.personZip as personZip, s.personPlus4 as personPlus4, s.personCounty as personCounty, s.personAddressId as personAddressId,
				       s.personCountry as personCountry, s.personPhoneHome as personPhoneHome, s.personPhoneCell as personPhoneCell,
				       s.personPhoneWork as personPhoneWork, s.addressUse as addressUse, s.useablePeriodLow as useablePeriodLow,
				       s.useablePeriodHigh as useablePeriodHigh, s.createDate as createDate, s.eharsImportDate as eharsImportDate,
				       s.eharsStatusCd as eharsStatusCd, p.stateno as stateno, p.discardStatus as DISCARD_STATUS, s.mpi as mpi,
				       s.uphnTranxInId as UPHN_TRANX_IN_ID
				from StdCcdDoc s left join PersonDocAssociation p on s.documentId = p.documentId and p.dissassociationDate is null
				where s.documentId = ?1""", Rows.bean(StdCCDDocVo.class), docId);
	}

	/** A CCD's distinct addresses (upper case, quotes removed), by address. */
	public List<RawCCDPersonAddressVO> getCCDAddressList(int docId) {
		return statements.list(CCDDocumentSql.CCD_ADDRESS_LIST, Rows.bean(RawCCDPersonAddressVO.class), docId);
	}

	/** A CCD's distinct providers (dates as MM/DD/YYYY, name and address composed), by start date. */
	public List<RawCCDProviderVO> getCCDProvidersList(int docId) {
		return statements.list(CCDDocumentSql.CCD_PROVIDERS_LIST, Rows.bean(RawCCDProviderVO.class), docId);
	}

	/** A CCD's distinct problems. */
	public List<RawCCDProblemVO> getCCDProblemsList(int docId) {
		return statements.list(CCDDocumentSql.CCD_PROBLEMS_LIST, Rows.bean(RawCCDProblemVO.class), docId);
	}

	/** A CCD's distinct encounters with the facility resolved through CODED_HIE_FACILITY, by start date. */
	public List<RawCCDEncounterVO> getCCDEncountersList(int docId) {
		return statements.list(CCDDocumentSql.CCD_ENCOUNTERS_LIST, Rows.bean(RawCCDEncounterVO.class), docId);
	}

	/** An encounter's distinct diagnoses (text or code present). */
	public List<RawCCDDiagnosisVO> getDiagnosisList(int encounterDocId) {
		return statements.list(CCDDocumentSql.DIAGNOSIS_LIST, Rows.bean(RawCCDDiagnosisVO.class), encounterDocId);
	}

	/** A CCD's lab tests (date as MM/DD/YYYY, display name without a leading dot), by date and code. */
	public List<RawCCDLabTestVO> getCCDLabTestsList(int docId) {
		return statements.list(CCDDocumentSql.CCD_LAB_TESTS_LIST, Rows.bean(RawCCDLabTestVO.class), docId);
	}

	/** A lab test's results, by result date. */
	public List<RawCCDLabResultVO> getLabResultsList(int labTestDocId) {
		return statements.list(CCDDocumentSql.LAB_RESULTS_LIST, Rows.bean(RawCCDLabResultVO.class), labTestDocId);
	}

	/** A CCD's distinct medications, by start date. */
	public List<RawCCDMedVO> getCCDMedicationsList(int docId) {
		return statements.list(CCDDocumentSql.CCD_MEDICATIONS_LIST, Rows.bean(RawCCDMedVO.class), docId);
	}

	/** A CCD's distinct payers. */
	public List<RawCCDPayerVO> getCCDPayersList(int docId) {
		return statements.list(CCDDocumentSql.CCD_PAYERS_LIST, Rows.bean(RawCCDPayerVO.class), docId);
	}

	// ---- the experimental CCD table pages -------------------------------------------------------------------------------

	/** The first 1000 raw CCDs with the count of their rows in each child table (ADDR, PROV, PAYER, LABS, ENCOUNTER, MED, DIAG, LAB_RES, PROB). */
	public List<Map<String, Object>> getAllCcdCountIndex() throws DataAccessException {
		return statements.list(CCDDocumentSql.ALL_CCD_COUNT_INDEX, Rows.map());
	}

	/** The tables the experimental page shows, each with the column its rows are picked by. */
	static final Map<String, String> CHILD_TABLES = Map.ofEntries(Map.entry("RAW_CCD_DOC", "DOCUMENT_ID"), Map.entry("UPHN_TRANX_IN", "UPHN_TRANX_IN_ID"),
			Map.entry("RAW_CCD_PERSON_ADDRESS", "CCD_DOC_ID"), Map.entry("RAW_CCD_PROVIDER", "CCD_DOC_ID"), Map.entry("RAW_CCD_PAYER", "CCD_DOC_ID"),
			Map.entry("RAW_CCD_LAB_TEST", "CCD_DOC_ID"), Map.entry("RAW_CCD_ENCOUNTER", "CCD_DOC_ID"), Map.entry("RAW_CCD_MED", "CCD_DOC_ID"),
			Map.entry("RAW_CCD_DIAGNOSIS", "CCD_DOC_ID"), Map.entry("RAW_CCD_LAB_RESULT", "CCD_DOC_ID"), Map.entry("RAW_CCD_PROBLEM", "CCD_DOC_ID"));

	/**
	 * The whole rows of a table for a document.
	 *
	 * @param args table (one of {@link #CHILD_TABLES}), parameter (that table's id column) and docId (bound as given);
	 *        anything else is refused (formerly pasted into the SQL)
	 */
	public List<Map<String, Object>> getChildEntryData(Map<String, Object> args) {
		String table = allowed(args.get("table"), CHILD_TABLES.keySet(), "CCD table");
		String column = CHILD_TABLES.get(table.toUpperCase(Locale.ENGLISH));
		if (!(args.get("parameter") instanceof String parameter) || !parameter.trim().equalsIgnoreCase(column)) {
			throw new InvalidDataAccessApiUsageException("Not the id column of " + table + ": " + args.get("parameter"));
		}
		return statements.list("select * from " + table + " where " + parameter.trim() + " = ?1", Rows.map(), args.get("docId"));
	}

	// ---- the UPHN payload rows -------------------------------------------------------------------------------------------

	/**
	 * Adds a UPHN payload row (topic DEERPROD, last update now by the database); its key, from UPHN_TRANX_IN_SEQ_ID, is
	 * put into the map as {@code uphnId} (a Long; the former generated key was the driver's number).
	 *
	 * @param uphnData p_fileName, p_hieName, p_dateCreated (the received time, bound as given)
	 */
	@Transactional
	public void addUphnRecord(Map<String, Object> uphnData) {
		Long uphnId = statements.one(CCDDocumentSql.NEXT_UPHN_TRANX_IN_ID, Rows.scalar(Long.class));
		statements.update(CCDDocumentSql.ADD_UPHN_RECORD, CCDDocumentSql.addUphnRecordArgs(uphnId, uphnData));
		uphnData.put("uphnId", uphnId);
	}

	/** Stores a payload's content on its UPHN row. @param uphnData p_ccdDocument (the bytes), uphnId */
	public void updateUphnRecord(Map<String, Object> uphnData) {
		statements.update(CCDDocumentSql.UPDATE_UPHN_RECORD, CCDDocumentSql.updateUphnRecordArgs(uphnData));
	}

	// ---- helpers ----------------------------------------------------------------------------------------------------------

	/** Runs a former {@code foreach ... union all} insert once per item of a list. @return the rows written */
	private int perItem(Map<String, Object> map, String listKey, String sql, BiFunction<Map<?, ?>, Object, Object[]> itemArgs) {
		Object list = map.get(listKey);
		Collection<?> items = list instanceof Object[] array ? Arrays.asList(array) : list instanceof Collection<?> collection ? collection : null;
		if (items == null || items.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No " + listKey + " to insert: " + list);
		}
		int count = 0;
		for (Object item : items) {
			count += statements.update(sql, itemArgs.apply(map, item));
		}
		return count;
	}

	/** A name as the caller gave it, if it is one of the allowed ones (ignoring case); otherwise the call fails. */
	private static String allowed(Object name, Set<String> names, String what) {
		if (name instanceof String text && names.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text.trim();
		}
		throw new InvalidDataAccessApiUsageException("Not a " + what + ": " + name);
	}
}
