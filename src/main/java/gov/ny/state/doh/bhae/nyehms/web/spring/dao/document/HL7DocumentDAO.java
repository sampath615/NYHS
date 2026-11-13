package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObxVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7RaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The HL7 messages received through UPHN: the loader's raw writes, its raw-to-standardized copy and status updates,
 * the match against the CCD documents, and the HL7 viewer of a CCD document (formerly a MyBatis mapper with the same
 * name and methods; the callers are unchanged, except that {@link #getHL7DemogrphicFieldMultiValues} and
 * {@link #getHL7DOBDemogrphicFieldMultiValues} now declare the maps they always returned). The interface methods that
 * had no statement and no caller ({@code insertDiagnosisFromRAWToSTD}, {@code getHL7LabData}) are gone.
 * <p>
 * JPQL over RawHl7Doc, StdHl7Doc, StdCcdDoc and MpiChange: {@link #updateCPSinRAWfromNonMatchToMatchCCD},
 * {@link #updateCPSinSTDNotMatchOnParameters}, {@link #updateCPSAndDOCidinRAWHL7fromSTDHL7DOC},
 * {@link #deleteRowsFromSTDHL7AfterBackedUpToRawHL7} and the HQL insert-select {@link #insertMPIPairsCheckingHL7andCCD}
 * (its time is the database's {@code sysdate}, as before). Native SQL ({@link HL7DocumentSql}, the mapper's text), with
 * the reason:
 * <ul>
 * <li>the raw inserts ({@link #insertRawHL7DocData}, the five {@code insert*List} / {@code insertRawHL7OB*} methods)
 * and the copy {@link #insertHL7FromRAWToSTD}: the conversions stay in the database ({@code upper(trim(...))},
 * {@code substr}, {@code TO_CLOB}, {@code FN_TO_DATE}, {@code TO_CHAR}), keys from a sequence inside the statement; the
 * raw segment tables have no entities. The former {@code foreach ... union all} inserts are one insert per item, in one
 * transaction (a missing or empty list fails, as the empty {@code union all} did);</li>
 * <li>{@link #getHL7LabTestSequences}: {@code CONNECT BY LEVEL} over a sequence;</li>
 * <li>{@link #updateCurrentProcessStatusInRaw}: a PL/SQL block (bulk collect, FORALL, its own commits);</li>
 * <li>{@link #updateCurrentProcessStatusInRawForMPIChange}: a {@code UNION} of correlated {@code EXISTS} subqueries
 * inside {@code IN};</li>
 * <li>{@link #deleteDuplicatesRowsInRawHL7Insuarance}: picks the rows by {@code ROWID};</li>
 * <li>{@link #getTotalCountForEachCPS}: {@code REGEXP_SUBSTR ... CONNECT BY} over a list in one text, {@code (+)};</li>
 * <li>{@link #getStdHL7Doc}, {@link #getFilteredHL7Doc}: {@code FIRST_VALUE ... IGNORE NULLS OVER} with
 * {@code rownum};</li>
 * <li>the viewer lists ({@link #getHL7Diagnosis}, {@link #getHL7Medications}, {@link #getHL7Encounter},
 * {@link #getHL7LabTestList}, {@link #getHL7LabResultList}, {@link #getFilteredHL7AddressList},
 * {@link #getFilteredHL7InsuranceList}): Oracle text-to-date parsing ({@code TO_DATE(substr(x, 0, 8), 'YYYYMMDD')}),
 * {@code NVL}/{@code NVL2}/{@code ||} composites over the raw segment tables (no entities);</li>
 * <li>{@link #getHL7RaceVO}, {@link #getFilteredRaceVO}: {@code SUM ... OVER (PARTITION BY)} and {@code LISTAGG};</li>
 * <li>{@link #getHL7DemogrphicFieldMultiValues}, {@link #getHL7DOBDemogrphicFieldMultiValues}: the column is chosen by
 * the caller, now allow-listed ({@link #STD_HL7_COLUMNS}), the values bound.</li>
 * </ul>
 * The procedure {@code match_ccd_hl7_records} is called through {@link NativeStatements#call}.
 */
@Repository
@RequiredArgsConstructor
public class HL7DocumentDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	// ---- the loader: raw writes ---------------------------------------------------------------------------------------

	/** Writes a received HL7 message's header, patient and visit segments (RAW_HL7_DOC). */
	public void insertRawHL7DocData(RawHL7DocVO rhdVO) throws DataAccessException {
		statements.update(HL7DocumentSql.RAW_HL7_DOC_DATA, HL7DocumentSql.insertRawHL7DocDataArgs(rhdVO));
	}

	/** Writes a message's OBR segments (keys given by the caller, see {@link #getHL7LabTestSequences}). @param inputParameterMapRawHl7LabTest hl7DocId, hl7LabTestList @return the rows written */
	@Transactional
	public int insertRawHL7OBR(Map<String, Object> inputParameterMapRawHl7LabTest) {
		return perItem(inputParameterMapRawHl7LabTest, "hl7LabTestList", HL7DocumentSql.RAW_HL7_OBR_ITEM, HL7DocumentSql::insertRawHL7OBRItemArgs);
	}

	/** Writes a message's OBX segments (keys from RAW_HL7_LAB_RESULT_SEQ). @param inputParameterMapRawHl7LabResult hl7DocId, hl7LabResultList */
	@Transactional
	public int insertRawHL7OBX(Map<String, Object> inputParameterMapRawHl7LabResult) {
		return perItem(inputParameterMapRawHl7LabResult, "hl7LabResultList", HL7DocumentSql.RAW_HL7_OBX_ITEM, HL7DocumentSql::insertRawHL7OBXItemArgs);
	}

	/** Writes a message's DG1 segments. @param inputParameterMapDG1List hl7DocId, hl7DG1List */
	@Transactional
	public int insertDG1List(Map<String, Object> inputParameterMapDG1List) {
		return perItem(inputParameterMapDG1List, "hl7DG1List", HL7DocumentSql.DG1_ITEM, HL7DocumentSql::insertDG1ListItemArgs);
	}

	/** Writes a message's ORC (pharmacy order) segments. @param inputParameterMapORCList hl7DocId, hl7ORCList */
	@Transactional
	public int insertORCList(Map<String, Object> inputParameterMapORCList) {
		return perItem(inputParameterMapORCList, "hl7ORCList", HL7DocumentSql.ORC_ITEM, HL7DocumentSql::insertORCListItemArgs);
	}

	/** Writes a message's IN1 (insurance) segments. @param inputParameterMapINSList hl7DocId, hl7INSList */
	@Transactional
	public int insertINSList(Map<String, Object> inputParameterMapINSList) {
		return perItem(inputParameterMapINSList, "hl7INSList", HL7DocumentSql.INS_ITEM, HL7DocumentSql::insertINSListItemArgs);
	}

	/** The next keys of RAW_HL7_LAB_TEST, one per OBR segment. */
	public List<Integer> getHL7LabTestSequences(int labTestLength) {
		return statements.list(HL7DocumentSql.HL7_LAB_TEST_SEQUENCES, Rows.scalar(Integer.class), labTestLength);
	}

	// ---- the loader: raw to standardized and the statuses ------------------------------------------------------------

	/** Copies a raw message to STD_HL7_DOC, converted as the mapper did. @return the rows written */
	public int insertHL7FromRAWToSTD(Map<Object, Object> stdHL7DocinsertVo) {
		return statements.update(HL7DocumentSql.HL7_FROM_RAW_TO_STD, HL7DocumentSql.insertHL7FromRAWToSTDArgs(stdHL7DocinsertVo));
	}

	/** Sets the status of the raw messages in status 1, 37 or 4 from their MPI's CCD match (a PL/SQL block committing in batches of 10,000). */
	public void updateCurrentProcessStatusInRaw() {
		statements.update(HL7DocumentSql.UPDATE_CURRENT_PROCESS_STATUS_IN_RAW);
	}

	/** Puts the raw messages in status 36 whose MPI now has a matched CCD back to status 1. */
	public void updateCPSinRAWfromNonMatchToMatchCCD() {
		jpql.update("""
				update RawHl7Doc r set r.currentProcessStatus = 1
				where exists (select c.documentId from StdCcdDoc c where c.mpi = r.pidmpi and c.fluxMatchCd in ('M', 'C_M', 'P_M'))
				and r.currentProcessStatus = 36""");
	}

	/** Puts the raw messages in status 37 whose MPI changed (MPI_CHANGE) to a matched CCD's MPI into status 41. */
	public void updateCurrentProcessStatusInRawForMPIChange() {
		statements.update(HL7DocumentSql.UPDATE_CPS_IN_RAW_FOR_MPI_CHANGE);
	}

	/** Puts the standardized messages in status 5 into status 60. */
	public void updateCPSinSTDNotMatchOnParameters() {
		jpql.update("update StdHl7Doc s set s.currentProcessStatus = 60 where s.currentProcessStatus = 5");
	}

	/** Deletes the duplicate insurance rows (same values in 24 columns), keeping the first ROWID of each group. */
	public void deleteDuplicatesRowsInRawHL7Insuarance() {
		statements.update(HL7DocumentSql.DELETE_DUPLICATE_RAW_HL7_INSURANCE);
	}

	/** Adds to MPI_CHANGE the (HL7 MPI, CCD MPI) pairs of same-named persons with different MPIs, not yet recorded in either order. */
	public void insertMPIPairsCheckingHL7andCCD() {
		jpql.update("""
				insert into MpiChange (currentMpi, oldMpi, dateReceived)
				select distinct h.mpi, c.mpi, sysdate
				from StdHl7Doc h, StdCcdDoc c
				where h.currentProcessStatus in (5, 60)
				and ((replace(h.fname, ' ', '') = replace(c.fname, ' ', '') and replace(h.lname, ' ', '') = replace(c.lname, ' ', ''))
				     or (replace(h.lname, ' ', '') = replace(c.fname, ' ', '') and replace(h.fname, ' ', '') = replace(c.lname, ' ', '')))
				and h.dobText = c.dobText and h.mpi <> c.mpi and c.currentProcessStatus in (10, 14)
				and not exists (select m.currentMpi from MpiChange m where m.oldMpi || m.currentMpi = h.mpi || c.mpi)
				and not exists (select m2.currentMpi from MpiChange m2 where m2.currentMpi || m2.oldMpi = h.mpi || c.mpi)""");
	}

	/**
	 * Matches the standardized messages of the given statuses against the CCD documents (procedure
	 * {@code match_ccd_hl7_records}); the outputs are put into the map as {@code p_code_out} and {@code p_error_msg}.
	 *
	 * @param input p_days, p_process_status (bound as given, a null as the mapper's types)
	 */
	public void updateDataSTDMatchorNonMatchtoCCD(Map<String, Object> input) {
		List<Object> results = statements.call("match_ccd_hl7_records", param(input.get("p_days"), Integer.class),
				param(input.get("p_process_status"), String.class), out(String.class), out(String.class));
		input.put("p_code_out", results.get(2));
		input.put("p_error_msg", results.get(3));
	}

	/** The count of standardized messages in each of the statuses of a comma-separated list: PROCESS_STATUS, COUNT. @param input p_process_status */
	public List<JSONObject> getTotalCountForEachCPS(Map<String, Object> input) {
		return statements.list(HL7DocumentSql.TOTAL_COUNT_FOR_EACH_CPS, Rows.json(), HL7DocumentSql.getTotalCountForEachCPSArgs(input));
	}

	/** Copies the matched CCD document id of the standardized messages in status 14 back to their raw rows, in status 18. */
	public void updateCPSAndDOCidinRAWHL7fromSTDHL7DOC() {
		jpql.update("""
				update RawHl7Doc r set r.currentProcessStatus = 18,
				    r.ccdMatchDocId = (select s.ccdMatchDocId from StdHl7Doc s where s.documentId = r.documentId and s.currentProcessStatus = 14)
				where r.documentId in (select s2.documentId from StdHl7Doc s2 where s2.documentId = r.documentId and s2.currentProcessStatus = 14)""");
	}

	/** Deletes the standardized messages in status 14 (backed up to their raw rows). */
	public void deleteRowsFromSTDHL7AfterBackedUpToRawHL7() {
		jpql.update("delete from StdHl7Doc s where s.currentProcessStatus = 14");
	}

	// ---- the HL7 viewer of a CCD document ------------------------------------------------------------------------------

	/** The latest non-null person values over a CCD document's messages (status 10), with the counts of distinct values; null if none. */
	public StdHL7DocVO getStdHL7Doc(int docId) {
		return statements.one(HL7DocumentSql.STD_HL7_DOC, Rows.bean(StdHL7DocVO.class), docId);
	}

	/** As {@link #getStdHL7Doc}, over the messages received between two dates. @param inputOutputParameterMap docId, startDate, endDate (MM/DD/YYYY) */
	public StdHL7DocVO getFilteredHL7Doc(Map<Object, Object> inputOutputParameterMap) {
		return statements.one(HL7DocumentSql.FILTERED_HL7_DOC, Rows.bean(StdHL7DocVO.class), HL7DocumentSql.getFilteredHL7DocArgs(inputOutputParameterMap));
	}

	/** The race flags (1 or null) summed over a CCD document's messages, with the other-race texts listed. */
	public StdHL7RaceVO getHL7RaceVO(int docId) {
		return statements.one(HL7DocumentSql.HL7_RACE, Rows.bean(StdHL7RaceVO.class), docId);
	}

	/** As {@link #getHL7RaceVO}, over the messages received between two dates. @param inputOutputParameterMap docId, startDate, endDate */
	public StdHL7RaceVO getFilteredRaceVO(Map<Object, Object> inputOutputParameterMap) {
		return statements.one(HL7DocumentSql.FILTERED_RACE, Rows.bean(StdHL7RaceVO.class), HL7DocumentSql.getFilteredRaceVOArgs(inputOutputParameterMap));
	}

	/** The OBR segments of a CCD document's messages received between two dates, by collection date. @param inputOutputParameterMap docId, startDate, endDate */
	public List<RawHL7ObrVO> getHL7LabTestList(Map<Object, Object> inputOutputParameterMap) {
		return statements.list(HL7DocumentSql.HL7_LAB_TEST_LIST, Rows.bean(RawHL7ObrVO.class), HL7DocumentSql.getHL7LabTestListArgs(inputOutputParameterMap));
	}

	/** The OBX segments of an OBR segment (code, result with unit, description). */
	public List<RawHL7ObxVO> getHL7LabResultList(int labtestId) {
		return statements.list(HL7DocumentSql.HL7_LAB_RESULT_LIST, Rows.bean(RawHL7ObxVO.class), labtestId);
	}

	/** The distinct diagnoses of the messages received between two dates: DXCODEID, DXCODETEXT, DIAGNOSISDATE, DXDATETEXT, PROVIDERNAME, FACNAME. @param inputOutputParameterMap docId, startDate, endDate */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getHL7Diagnosis(Map<Object, Object> inputOutputParameterMap) {
		return (List) statements.list(HL7DocumentSql.HL7_DIAGNOSIS, Rows.map(), HL7DocumentSql.getHL7DiagnosisArgs(inputOutputParameterMap));
	}

	/** The distinct medications of the messages received between two dates: MEDICATIONDATE, DATETIMEOFTRANSACTION, ORDERPROVIDERNAME, ORDERINGFACILITYNAME, MEDICATIONNAME. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getHL7Medications(Map<Object, Object> inputOutputParameterMap) {
		return (List) statements.list(HL7DocumentSql.HL7_MEDICATIONS, Rows.map(), HL7DocumentSql.getHL7MedicationsArgs(inputOutputParameterMap));
	}

	/** The distinct encounters of the messages received between two dates: ENCOUNTERDATE, DATERECEIVED, EVNEVENTTYPE, FACILITYNAME, ENCOUNTERID, PROVIDERNAME. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<Object, Object>> getHL7Encounter(Map<Object, Object> inputOutputParameterMap) {
		return (List) statements.list(HL7DocumentSql.HL7_ENCOUNTER, Rows.map(), HL7DocumentSql.getHL7EncounterArgs(inputOutputParameterMap));
	}

	/** The SQL of {@code getFilteredHL7AddressList} up to its optional date conditions, as the former mapper had it. */
	static final String FILTERED_HL7_ADDRESS_LIST = """
			select  distinct UPPER(trim(replace(PERSON_ADDRESS,'"',''))) PERSONADDRESS,
			                 UPPER(trim(replace(PERSON_CITY,'"',''))) PERSONCITY,
			                 UPPER(trim(replace(PERSON_STATE,'"',''))) PERSONSTATE,
			                 NVL(trim(std.PERSON_ZIP),'') || NVL2(trim(std.PERSON_PLUS4),'-'|| NVL(trim(std.PERSON_PLUS4),''),'') as PERSONZIP
			from std_hl7_doc std where std.ccd_match_doc_id = ?1
			        AND STD.CURRENT_PROCESS_STATUS = 10
			        and (std.PERSON_ADDRESS is not null
			        or std.PERSON_CITY is not null
			        or std.PERSON_PLUS4 is not null
			        or std.PERSON_STATE is not null
			        or std.PERSON_ZIP is not null)
			""";

	/** The distinct addresses of a CCD document's messages (quotes removed, ZIP with plus-4), between two dates when given, by address. @param inputOutputParameterMap docId, startDate, endDate */
	public List<RawHL7AddressVO> getFilteredHL7AddressList(Map<String, Object> inputOutputParameterMap) {
		return statements.list(FILTERED_HL7_ADDRESS_LIST + dateConditions(inputOutputParameterMap) + " order by PERSONADDRESS", Rows.bean(RawHL7AddressVO.class),
				inputOutputParameterMap.get("docId"), inputOutputParameterMap.get("startDate"), inputOutputParameterMap.get("endDate"));
	}

	/** The SQL of {@code getFilteredHL7InsuranceList} up to its optional date conditions, as the former mapper had it. */
	static final String FILTERED_HL7_INSURANCE_LIST = """
			select distinct UPPER(trim(INS.COMPANY_NAME)) as companyName,
			                TO_DATE(substr(REPLACE(trim(INS.PLAN_EFFECTIVE_DATE), '""', NULL),0,8),'YYYYMMDD') insuranceStartDate,
			                NVL(TO_CHAR(TO_DATE(substr(REPLACE(trim(INS.PLAN_EFFECTIVE_DATE), '""', NULL),0,8),'YYYYMMDD'),'MM/DD/YYYY'),'') planEffectiveDate,
			                NVL(TO_CHAR(TO_DATE(substr(REPLACE(trim(INS.PLAN_EXPIRATION_DATE), '""', NULL),0,8),'YYYYMMDD'),'MM/DD/YYYY'),'') planExpirationDate,
			                UPPER(trim(NVL(INS.INSURED_FNAME,'')|| ' '|| NVL(INS.INSURED_LNAME,''))) as insuredFName,
			                NVL(TO_CHAR(TO_DATE(substr(REPLACE(trim(INS.INSURED_DOB_TEXT), '""', NULL),0,8),'YYYYMMDD'),'MM/DD/YYYY'),'') insuredDOBText,
			                NVL(REPLACE(INS.POLICY_NUMBER,'-',''),'') as policyNumber
			from raw_hl7_insurance INS , STD_HL7_DOC STD where INS.HL7_DOC_ID = STD.DOCUMENT_ID and std.ccd_match_doc_id = ?1
			                AND STD.CURRENT_PROCESS_STATUS = 10
			                and (INS.COMPANY_NAME is not null
			                 or INS.PLAN_EFFECTIVE_DATE is not null
			                 or INS.PLAN_EXPIRATION_DATE is not null
			                 or INS.INSURED_FNAME is not null
			                 or INS.INSURED_LNAME is not null
			                 or INS.INSURED_DOB_TEXT is not null
			                 or INS.POLICY_NUMBER is not null)
			""";

	/** The distinct insurances of a CCD document's messages, between two dates when given, by plan start. @param inputOutputParameterMap docId, startDate, endDate */
	public List<RawHL7INSVO> getFilteredHL7InsuranceList(Map<String, Object> inputOutputParameterMap) {
		return statements.list(FILTERED_HL7_INSURANCE_LIST + dateConditions(inputOutputParameterMap) + " order by insuranceStartDate", Rows.bean(RawHL7INSVO.class),
				inputOutputParameterMap.get("docId"), inputOutputParameterMap.get("startDate"), inputOutputParameterMap.get("endDate"));
	}

	/** The former {@code <if>} conditions: each date given and not empty bounds DATE_RECEIVED (?2 the start, ?3 the end). */
	private static String dateConditions(Map<String, Object> map) {
		String conditions = "";
		if (given(map.get("startDate"))) {
			conditions += " and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')";
		}
		if (given(map.get("endDate"))) {
			conditions += " and TO_DATE(?3, 'MM/DD/YYYY') >= STD.DATE_RECEIVED";
		}
		return conditions;
	}

	private static boolean given(Object value) {
		return value != null && !"".equals(value);
	}

	/** The columns of STD_HL7_DOC the viewer may list the distinct values of (the columns the loader's copy writes). */
	static final Set<String> STD_HL7_COLUMNS = Set.of("DOCUMENT_ID", "UPHN_TRANX_IN_ID", "DIN", "ETHNICITY", "FNAME", "GENDER", "LNAME",
			"MED_RECORD_NUMBER", "MNAME", "MPI", "NYSID", "PERSON_ADDRESS", "PERSON_CITY", "PERSON_COUNTRY", "PERSON_EXTERNAL_ID", "PERSON_INTERNAL_ID",
			"PERSON_PHONE", "PERSON_PLUS4", "PERSON_STATE", "PERSON_ZIP", "PROVIDER_FNAME", "PROVIDER_ID", "PROVIDER_ID_AUTH", "PROVIDER_LNAME",
			"RACE_ASIAN", "RACE_BLACK", "RACE_INDIAN", "RACE_OTHER", "RACE_PACIFIC", "RACE_WHITE", "SSN", "SUFFIX", "BIRTH_COUNTRY",
			"CURRENT_PROCESS_STATUS", "DATE_OF_BIRTH", "DATE_RECEIVED", "FACILITY_CODE", "FACILITY_NAME", "HL7_MESSAGE_TYPE", "INSURANCE_NUMBER",
			"MEDICAID_NUMBER", "RACE_UNKNOWN", "VETERAN_STATUS", "DOB_TEXT", "CCD_MATCH_DOC_ID", "CCD_MATCH_STATENO", "MATCH_PROCESS_DATE",
			"PERSON_COUNTY", "FACILITY_ADDRESS", "FACILITY_CITY", "FACILITY_STATE", "FACILITY_ZIP", "RACE_UNTRANSLATABLE", "RACE_OTHER_TEXT",
			"FACILITY_COUNTY", "EVENT_DATE", "EVENT_TYPE", "ENCOUNTER_ID");

	/**
	 * The distinct values (upper case, trimmed; the ethnicity codes as words) of a column over a CCD document's messages
	 * received between two dates, each as a map with the key FIELD.
	 *
	 * @param inputOutputParameterMap colsName (one of {@link #STD_HL7_COLUMNS}, formerly pasted into the SQL), docId,
	 *        startDate, endDate
	 */
	public List<Map<String, Object>> getHL7DemogrphicFieldMultiValues(Map<Object, Object> inputOutputParameterMap) {
		String column = column(inputOutputParameterMap.get("colsName"));
		return statements.list("""
				select distinct UPPER(trim(
				    case ?1 when 'ETHNICITY' then    CASE ETHNICITY WHEN '01' THEN 'Hispanic'
				                                                    WHEN '02' THEN 'Not Hispanic'
				                                                    WHEN '09' THEN 'Unknown' END
				                 else  %s
				           end)) as Field
				from STD_HL7_DOC STD
				WHERE STD.CCD_MATCH_DOC_ID =  ?2
				      AND STD.CURRENT_PROCESS_STATUS = 10
				      and STD.DATE_RECEIVED >= TO_DATE(?3, 'MM/DD/YYYY')
				      and STD.DATE_RECEIVED <= TO_DATE(?4, 'MM/DD/YYYY')
				      and %s is not null""".formatted(column, column), Rows.map(), inputOutputParameterMap.get("colsName"),
				inputOutputParameterMap.get("docId"), inputOutputParameterMap.get("startDate"), inputOutputParameterMap.get("endDate"));
	}

	/** The distinct values of a date column (MM/DD/YYYY) over a CCD document's messages received between two dates, each as a map with the key FIELD. @param inputOutputParameterMap colsName (allow-listed), docId, startDate, endDate */
	public List<Map<String, Object>> getHL7DOBDemogrphicFieldMultiValues(Map<Object, Object> inputOutputParameterMap) {
		String column = column(inputOutputParameterMap.get("colsName"));
		return statements.list("""
				select distinct TO_CHAR(%s,'MM/DD/YYYY') as Field from STD_HL7_DOC STD
				WHERE STD.CCD_MATCH_DOC_ID =  ?1
				      AND STD.CURRENT_PROCESS_STATUS = 10
				      and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')
				      and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')
				      and %s is not null""".formatted(column, column), Rows.map(), inputOutputParameterMap.get("docId"),
				inputOutputParameterMap.get("startDate"), inputOutputParameterMap.get("endDate"));
	}

	// ---- helpers ----------------------------------------------------------------------------------------------------------

	/** A column name as the caller gave it, if it is one of {@link #STD_HL7_COLUMNS} (ignoring case); otherwise the call fails. */
	private static String column(Object name) {
		if (name instanceof String text && STD_HL7_COLUMNS.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text.trim();
		}
		throw new InvalidDataAccessApiUsageException("Not a column of STD_HL7_DOC: " + name);
	}

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

	/** An IN parameter bound as MyBatis bound it: with the value's own type, a null with the mapper's jdbcType. */
	private static CallParam param(Object value, Class<?> nullType) {
		return in(value, value == null ? nullType : value.getClass());
	}
}
