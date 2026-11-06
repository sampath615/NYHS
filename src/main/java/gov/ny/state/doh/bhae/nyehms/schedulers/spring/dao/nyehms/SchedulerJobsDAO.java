package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsReAssignDocs;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsStdDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MailStatusVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.MultiStepDocumentsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsProcessErrors;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * This DAO is used to give the required information for the Quartz scheduler: the eCLRS HIV / STD loaders, the
 * quality-stage (DataFlux) match results of all document types, the multi-step documents, the PID update process and
 * the scheduler status mails (formerly a MyBatis mapper with the same name and methods; callers unchanged). This
 * package lies outside the packages the application scans; the bean is found through SchedulerDaoConfig.
 * <p>
 * <b>JPQL</b> over the entities of {@code entity.common} and {@code entity.scheduler} for the statements on narrow
 * tables whose values the callers pass with the column's type: the scheduler parameters, the reassign list, the
 * coordinators' mail addresses, the code lookups of the validation (interpretation, CLIA, PFI, LOINC, ethnicity,
 * gender, units, local codes, can-be-residence addresses), the error records (NYEHMS_PROCESS_ERRORS and its detail
 * rows, PID_UPDATE_PROCESS_ERRORS), the load log, DOCUMENT_LOCATOR, the raw provider / facility standardization and
 * the multi-step document details. Dates the former SQL compared with {@code TO_CHAR(SYSDATE ...)} are computed from
 * the application server's clock; SYSDATE written by JPQL is the database's.
 * <p>
 * <b>Native</b> (the SQL of {@link SchedulerJobsSql}, unchanged, values bound) where JPQL cannot express the statement
 * or where it would change what is stored: stored procedures; per-call table and column names ({@code ${}}; allowed
 * only from {@link #RAW_TABLES}, {@link #STD_TABLES}, {@link #FILE_NAME_COLUMNS} and the ADAP / DIN DOCCS provider and
 * facility column sets, anything else fails as the former SQL with a wrong name failed); SQL text from the scheduler
 * configuration; {@code select *} over the ~240 columns of the raw eCLRS tables into VOs and maps; the wide eCLRS
 * inserts and copies (ORA_HASH, PL/SQL functions, insert-select); Oracle functions and syntax (REGEXP_LIKE, analytic
 * functions over derived tables, DECODE/NVL with implicit conversions, ROWID, {@code (+)} joins, GROUP BY ROLLUP,
 * TO_CHAR formats, date arithmetic, the cross-schema NYEHMS_SASDB_OWNER tables); and the quality-stage match results,
 * whose values are the text of the DataFlux files bound as they are into columns whose types are not known here (the
 * database converted them; JPQL would bind them with the entity attribute's type).
 * <p>
 * Kept as they were (bugs, not fixed): see {@link #getLoincsForPfiLocalCodeMatch}, {@link #checkForReferenceLoincDocumentsCount},
 * {@link #getStandardInterpretationCodeUsingDescription}.
 */
@Repository
@RequiredArgsConstructor
public class SchedulerJobsDAO {

	/** The raw document tables LabCommonUtils names ({@code getRawTableName}, {@code getRawTableNameFast}). */
	static final Set<String> RAW_TABLES = Set.of("RAW_ACRF_DOC", "RAW_ADAP_DOC", "RAW_AIRS_DOC", "RAW_BLACK_BOX_DOC", "RAW_CASA_DOC",
			"RAW_CCD_DOC", "RAW_CLUSTER_DOC", "RAW_DEATH_DOC", "RAW_DIN_DOCCS_DOC", "RAW_DOCCS_DOC", "RAW_EPRF_OOC_UPLOAD", "RAW_EXP_DOC",
			"RAW_HIE_DEATH_DOC", "RAW_HIV_ECLRS_DOC", "RAW_HL7_DOC", "RAW_ICCR_DOC", "RAW_INS_DEATH_DOC", "RAW_MEDICAID_DOC",
			"RAW_MED_OLD_DOC", "RAW_NDI_CAUSE_DOC", "RAW_NDI_DOC", "RAW_NYCIJR_DOC", "RAW_NYCPRF_DOC", "RAW_NYC_DEATH_DOC",
			"RAW_NYC_EHARS_DOC", "RAW_NYC_EHARS_LAB_DOC", "RAW_OLD_CASES_DOC", "RAW_PCRF_DOC", "RAW_PRF_DOC", "RAW_QUALITY_CARE_DOC",
			"RAW_SSDM_DOC", "RAW_STD_CONFIRMED_DOC", "RAW_STD_ECLRS_DOC", "RAW_STD_OLD_DOC", "RAW_SYPHILIS_DOC", "RAW_TB_DOC");

	/** The standardized document tables LabCommonUtils names ({@code getStdTableName}). */
	static final Set<String> STD_TABLES = Set.of("STD_ACRF_DOC", "STD_ADAP_DOC", "STD_AHI_DOC", "STD_AIRS_DOC", "STD_BLACK_BOX_DOC",
			"STD_CASA_DOC", "STD_CCD_DOC", "STD_CLUSTER_DOC", "STD_CLUSTER_PARTNERS", "STD_DEATH_DOC", "STD_DIN_DOCCS_DOC", "STD_DOCCS_DOC",
			"STD_EPRF_OOC_UPLOAD", "STD_EXP_DOC", "STD_HIE_DEATH_DOC", "STD_HIV_ECLRS_DOC", "STD_HL7_DOC", "STD_ICCR_DOC",
			"STD_INS_DEATH_DOC", "STD_MEDICAID_DOC", "STD_MED_OLD_DOC", "STD_NDI_CAUSE_DOC", "STD_NDI_DOC", "STD_NYCIJR_DOC",
			"STD_NYCPRF_DOC", "STD_NYC_DEATH_DOC", "STD_NYC_EHARS_DOC", "STD_NYC_EHARS_LAB_DOC", "STD_OLD_CASES_DOC", "STD_PCRF_DOC",
			"STD_PRF_DOC", "STD_QUALITY_CARE_DOC", "STD_SSDM_DOC", "STD_STD_CONFIRMED_DOC", "STD_STD_ECLRS_DOC", "STD_STD_OLD_DOC",
			"STD_SYPHILIS_DOC", "STD_TB_DOC", "TRACKING_USER_ENTERED_ALIAS");

	/** The file name columns of the standardized tables (LabCommonUtils.getStdReferenceColumnNames, FILE_NAME). */
	static final Set<String> FILE_NAME_COLUMNS = Set.of("ADAP_FILE", "DEATH_FILE", "DOCCS_FILE", "FILENAME", "FILE_NAME", "MEDICAID_FILE",
			"STD_FILE", "TB_FILE", "UPLOAD_FILENAME");

	/** The ADAP provider columns ManualQuartzJobService chooses from (certified, PCP, prescribing provider). */
	private static final List<Map<String, String>> ADAP_PROVIDER_COLUMNS = columnSets(
			new String[] {"PROV_ADDRESS", "PROV_CITY", "PROV_STATE", "PROV_ZIP", "PROV_PLUS4", "PROV_COUNTY", "PROV_LATITUDE", "PROV_LONGITUDE", "PROV_CENSUS_TRACT"},
			new String[][] {{"CERT_PROV_ADDRESS", "CERT_PROV_CITY", "CERT_PROV_STATE", "CERT_PROV_ZIP", "CERT_PROV_PLUS4", "CERT_PROV_COUNTY",
					"CERT_PROV_LATITUDE", "CERT_PROV_LONGITUDE", "CERT_PROV_CENSUS_TRACT"},
				{"PCP_PROV_ADDRESS", "PCP_PROV_CITY", "PCP_PROV_STATE", "PCP_PROV_ZIP", "PCP_PROV_PLUS4", "PCP_PROV_COUNTY", "PCP_PROV_LATITUDE",
					"PCP_PROV_LONGITUDE", "PROV_CENSUS_TRACT_PCP"},
				{"RX_PROV_ADDRESS", "RX_PROV_CITY", "RX_PROV_STATE", "RX_PROV_ZIP", "RX_PROV_PLUS4", "RX_PROV_COUNTY", "RX_PROV_LATITUDE",
					"RX_PROV_LONGITUDE", "RX_PROV_CENSUS_TRACT"}});

	/** The DIN DOCCS facility columns ManualQuartzJobService chooses from (current or owning facility). */
	private static final List<Map<String, String>> DIN_DOCCS_FACILITY_COLUMNS = columnSets(
			new String[] {"FACILITY_ADDRESS", "FACILITY_CITY", "FACILITY_STATE", "FACILITY_ZIP", "FACILITY_PLUS4", "FACILITY_COUNTY",
				"FACILITY_LATITUDE", "FACILITY_LONGITUDE", "FACILITY_CENSUS_TRACT"},
			new String[][] {{"CURRENT_FACILITY_ADDRESS", "CURRENT_FACILITY_CITY", "CURRENT_FACILITY_STATE", "CURRENT_FACILITY_ZIP",
					"CURRENT_FACILITY_PLUS4", "CURRENT_FACILITY_COUNTY", "CURRENT_FACILITY_LATITUDE", "CURRENT_FACILITY_LONGITUDE",
					"CURRENT_FACILITY_CENSUS_TRACT"},
				{"OWNING_FACILITY_ADDRESS", "OWNING_FACILITY_CITY", "OWNING_FACILITY_STATE", "OWNING_FACILITY_ZIP", "OWNING_FACILITY_PLUS4",
					"OWNING_FACILITY_COUNTY", "OWNING_FACILITY_LATITUDE", "OWNING_FACILITY_LONGITUDE", "OWNING_FACILITY_CENSUS_TRACT"}});

	private static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);

	private static final String MULTI_STEP_DOCUMENT_DETAILS = """
			select r.documentId, r.currentProcessStatus, s.currentProcessStatus, s.fluxMatchCd,
			r.pidaddressline1, r.pidaddressline2, r.pidcity, r.pidstate, r.pidzipcode
			from RawHivEclrsDoc r left join StdHivEclrsDoc s on r.documentId = s.documentId
			where r.obxobservationresultkey in (select x.obxobservationresultkey from RawHivEclrsDoc x where x.documentId = ?1)
			order by case when r.currentProcessStatus in (1, 3, 4, 7, 11, 6, 15, 30, 8, 18) then 1
				when r.currentProcessStatus = 2 then 2
				when r.currentProcessStatus = 19 then 3
				when r.currentProcessStatus = 20 then 4
				when r.currentProcessStatus = 5 then 5
				else 6 end""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/**
	 *
	 * @param userId The value of userObject.getUserId() from the
	 * LEGACY_UID column of the NYEHMS_WORKERS table.
	 * @return the number of USERS_HAVING_SCHEDULERS_ACCESS parameters in the LAB_SCHEDULER_PARAMETERS table whose value
	 * contains the user ID (ignoring case): 1 if the user may use the schedulers; 0 if not.
	 */
	public int havingPermissionsForAccessingQuarzScheduler(String userId) {
		return count("""
				select count(*) from LabSchedulerParameters p where p.paramterName = 'USERS_HAVING_SCHEDULERS_ACCESS'
				and upper(p.paramterValue) like '%' || upper(?1) || '%'""", userId);
	}

	/**
	 * This method is used to get all the Active Coordinators available in the region.
	 *
	 * For Every scheduler action(Manual/Regular) will send the Mail status to Coordinators.
	 * @return the mail addresses of the users with an active coordinator role (ROLE_CODE 4)
	 */
	public String[] getRecipientsEmailsForRawHIVCleanedStatus() {
		List<String> addresses = jpql.list("""
				select u.emailAddr from NyehmsUsers u where u.emailAddr is not null and u.workerId in
				(select r.workerId from UserRegion r where r.roleCode = 4 and (r.endDate is null or r.endDate > sysdate))""",
				row -> (String) row.get(0));
		return addresses.toArray(new String[0]);
	}

	/**
	 * Records a validation error of a document (NYEHMS_PROCESS_ERRORS): ERROR_ID from NYEHMS_PROCESS_ERRORS_SEQ,
	 * CREATE_DATE the database's SYSDATE. The new ERROR_ID is put into the map as processErrorId, as a BigDecimal (the
	 * generated key as the Oracle driver returned it).
	 *
	 * @param inputOutputParamters documentId, errorMessage, currentProcessStatus, activeFlag, createdId, repiseFlag,
	 *        previousProcessStatus, documentType
	 * @return 1
	 */
	public int addValidationError(Map<Object, Object> inputOutputParamters) {
		NyehmsProcessErrors error = NyehmsProcessErrors.builder()
				.documentId(BoundValues.longValue(inputOutputParamters.get("documentId")))
				.errorDesc(BoundValues.text(inputOutputParamters.get("errorMessage")))
				.processStatus(BoundValues.longValue(inputOutputParamters.get("currentProcessStatus")))
				.activeFlag(BoundValues.text(inputOutputParamters.get("activeFlag")))
				.createid(BoundValues.text(inputOutputParamters.get("createdId")))
				.repriseFlag(BoundValues.text(inputOutputParamters.get("repiseFlag")))
				.previousStatus(BoundValues.decimal(inputOutputParamters.get("previousProcessStatus")))
				.documentType(BoundValues.text(inputOutputParamters.get("documentType")))
				.build();
		int count = jpql.insert(error);
		inputOutputParamters.put("processErrorId", BigDecimal.valueOf(error.getErrorId()));
		return count;
	}

	/** Native: about 200 values bound with their VO types into the raw HIV table; see {@link SchedulerJobsSql}. */
	public int insertHivRawEcrlsData(EcrlsLabDocVO ecrlsLabDocVO) {
		return statements.update(SchedulerJobsSql.INSERT_HIV_RAW_ECRLS_DATA, SchedulerJobsSql.insertHivRawEcrlsDataArgs(ecrlsLabDocVO));
	}

	/** Native: about 200 values bound with their VO types into the raw STD table; see {@link SchedulerJobsSql}. */
	public int insertStdRawEcrlsData(EcrlsLabDocVO ecrlsLabDocVO) {
		return statements.update(SchedulerJobsSql.INSERT_STD_RAW_ECRLS_DATA, SchedulerJobsSql.insertStdRawEcrlsDataArgs(ecrlsLabDocVO));
	}

	/** Files a new HIV lab document (DOCUMENT_LOCATOR, type HIV_LAB, received now). */
	public int insertIntoDocumentLocator(int documentId) {
		return jpql.update("insert into DocumentLocator (documentId, documentTypeCode, receivedDate) values (?1, 'HIV_LAB', sysdate)",
				documentId);
	}

	/** The number of clinical laboratories with the CLIA (ignoring case). */
	public int checkForExistingCliaMatches(Map<Object, Object> inputOutputParamters) {
		return count("select count(*) from ClinicalLaboratory c where upper(c.clia) = upper(?1)", Values.text(inputOutputParamters.get("clia")));
	}

	/** The number of active laboratories with the CLIA (ignoring case) and PFI. */
	public int checkForExistingCliaAndPFIMatches(Map<Object, Object> inputOutputParamters) {
		return count("select count(*) from LabPhysicalFacility l where upper(l.clia) = upper(?1) and l.pfi = ?2 and l.flagActive = 'Y'",
				Values.text(inputOutputParamters.get("clia")), Values.text(inputOutputParamters.get("pfi")));
	}

	/** The number of LOINC mappings of the PFI and LOINC valid on the collection date. */
	public int checkForLoincAndPfiMapping(Map<Object, Object> inputOutputParamters) {
		return count("""
				select count(*) from LabLoincMapping m where m.pfi = ?1 and m.loincCd = ?2 and ?3 >= m.startDate
				and (m.endDate is null or m.endDate >= ?3)""", Values.text(inputOutputParamters.get("pfi")),
				Values.text(inputOutputParamters.get("loincCode")), Values.timestamp(inputOutputParamters.get("collectionDate")));
	}

	/** The standard ethnicity codes a PFI's code (raw or translated, ignoring case) maps to, distinct. */
	public List<String> getMatchesForEthnicityCode(Map<Object, Object> inputOutputParamters) {
		return jpql.list("""
				select distinct m.translatedEthnicityCd from LabLoadEthnicityMapping m where m.pfi = ?1
				and (upper(m.rawEthnicityCd) = upper(?2) or upper(m.translatedEthnicityCd) = upper(?2))""", row -> (String) row.get(0),
				Values.text(inputOutputParamters.get("pfi")), Values.text(inputOutputParamters.get("pid_ethnic_cd")));
	}

	/** The active gender code equal to the sex code (ignoring case); more than one fails, as selectOne did. */
	public String getStandardizedGender(String sexCode) {
		return jpql.one("select g.genderCode from CodedGender g where upper(g.genderCode) = upper(?1) and (g.endDate is null or g.endDate > sysdate)",
				row -> (String) row.get(0), sexCode);
	}

	/**
	 * Native: <b>bug kept</b> — the former mapper method passed only the local code map id, so MyBatis bound that one
	 * value to every placeholder, also to the three meant for the collection date: the map id text is compared with
	 * START_DATE / END_DATE, which only the database's implicit conversion does (it fails or filters wrongly, as
	 * before).
	 */
	public List<String> getLoincsForPfiLocalCodeMatch(String localCodeMapId) {
		return statements.list(SchedulerJobsSql.GET_LOINCS_FOR_PFI_LOCAL_CODE_MATCH, Rows.scalar(String.class),
				SchedulerJobsSql.getLoincsForPfiLocalCodeMatchArgs(localCodeMapId));
	}

	/**
	 * Native: a map of the values as JDBC getObject types them (DESCRIPTION, TEST_TYPE, UNIT_OF_RESULT), whose column
	 * types are not known here.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> checkForLoincAndUnitsOfMeasurementCombination(Map<Object, Object> inputOutputParamters) {
		return (Map<String, String>) (Map<?, ?>) statements.one(SchedulerJobsSql.CHECK_FOR_LOINC_AND_UNITS_OF_MEASUREMENT_COMBINATION, Rows.map(),
				SchedulerJobsSql.checkForLoincAndUnitsOfMeasurementCombinationArgs(inputOutputParamters));
	}

	/** Native: ROWNUM over a three-table join, maps of getObject-typed values (RESULTS_STD, RESULT_INTERPRETATION). */
	public List<Map<String, String>> checkForLoincAndObservationResultTextCombination(Map<Object, Object> inputOutputParamters) {
		return stringMaps(statements.list(SchedulerJobsSql.CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_TEXT_COMBINATION, Rows.map(),
				SchedulerJobsSql.checkForLoincAndObservationResultTextCombinationArgs(inputOutputParamters)));
	}

	/** Native: the result text compared with the number range (implicit conversion) and TO_CHAR of the bounds. */
	public List<Map<String, String>> checkForLoincAndObservationResultRangeCombination(Map<Object, Object> inputOutputParamters) {
		return stringMaps(statements.list(SchedulerJobsSql.CHECK_FOR_LOINC_AND_OBSERVATION_RESULT_RANGE_COMBINATION, Rows.map(),
				SchedulerJobsSql.checkForLoincAndObservationResultRangeCombinationArgs(inputOutputParamters)));
	}

	/** The local code map id of a PFI's local code valid on the collection date; more than one fails, as selectOne did. */
	public String getlocalCodeMapIdForPfiLocalCodeMatch(Map<Object, Object> inputOutputParamters) {
		return jpql.one("""
				select c.labLocalCdMapId from LabLocalCodes c where c.pfi = ?1 and c.localCd = ?2 and ?3 >= c.startDate
				and (c.endDate is null or c.endDate >= ?3)""", row -> (String) row.get(0), Values.text(inputOutputParamters.get("pfi")),
				Values.text(inputOutputParamters.get("localCode")), Values.timestamp(inputOutputParamters.get("collectionDate")));
	}

	/** Marks a document as inactive (DOCUMENT_LOCATOR.IS_ACTIVE = 'N'). */
	public int makeDocumentAsInactivate(int documentId) {
		return jpql.update("update DocumentLocator d set d.isActive = 'N' where d.documentId = ?1", documentId);
	}

	/** Native: the raw table is named by the caller (rawTableName, allow-listed); statusCode, documentId. */
	public int writeErrorCode(Map<Object, Object> inputOutputParamters) {
		return statements.update(names(SchedulerJobsSql.WRITE_ERROR_CODE, inputOutputParamters, Map.of("rawTableName", RAW_TABLES)),
				SchedulerJobsSql.writeErrorCodeArgs(inputOutputParamters));
	}

	/**
	 * Native: NVL of a date with an NLS date text ('01-Jan-2017') and RANK() OVER in a CTE. The row with the highest
	 * OBX_MAX of the record type as LAST_MAX_OBX_KEY (Long), LAST_PIDUPDATEDATE (Timestamp) and RN (as read); null if
	 * there is none; two rows of the same rank fail, as selectOne did.
	 */
	public Map<String, Object> getLastInsertedECLRSRecordId(String recordType) {
		return statements.one(SchedulerJobsSql.GET_LAST_INSERTED_ECLRSRECORD_ID, row -> resultMap(row,
				Map.of("LAST_MAX_OBX_KEY", Long.class, "LAST_PIDUPDATEDATE", Timestamp.class)),
				SchedulerJobsSql.getLastInsertedECLRSRecordIdArgs(recordType));
	}

	/** Native: {@code select *} of the raw HIV documents (status 1, 6, 3, 15) into EcrlsLabDocVO (~240 columns). */
	public List<EcrlsLabDocVO> getRawHIVDocsList() {
		return statements.list(SchedulerJobsSql.GET_RAW_HIVDOCS_LIST, Rows.bean(EcrlsLabDocVO.class));
	}

	/** Native: {@code select *} of the raw STD documents (status 1, 3) into EcrlsLabDocVO (~240 columns). */
	public List<EcrlsLabDocVO> getRawStdDocsList() {
		return statements.list(SchedulerJobsSql.GET_RAW_STD_DOCS_LIST, Rows.bean(EcrlsLabDocVO.class));
	}

	/** Logs a load run (LAB_ECLRS_LOAD_LOG) created by NYEHMS now. */
	public int insertSchedulerLog(Map<Object, Object> inOutParameterMap) {
		return jpql.update("""
				insert into LabEclrsLoadLog (obxMin, obxMax, hivStd, totalNoRecords, createId, createDate)
				values (?1, ?2, ?3, ?4, 'NYEHMS', sysdate)""", Values.longValue(inOutParameterMap.get("minOBXOBSERVATIONRESULTKEY")),
				Values.longValue(inOutParameterMap.get("maxOBXOBSERVATIONRESULTKEY")), Values.text(inOutParameterMap.get("recordType")),
				Values.longValue(inOutParameterMap.get("totalNumberOfRecords")));
	}

	/**
	 * Native: DECODE, TRUNC(SYSDATE) and SUM() OVER over a grouped derived table. The files of today's raw HIV documents
	 * that have duplicates (status 2), each with its total and its LOINC codes with their number of duplicates, grouped
	 * as the former nested result map grouped them.
	 */
	public List<MailStatusVO> getDocumentDuplicatedStatus() {
		List<MailStatusVO> files = new ArrayList<>();
		Map<List<Object>, MailStatusVO> filesByKey = new HashMap<>();
		Map<List<Object>, JSONObject> codesByKey = new HashMap<>();
		for (Row row : statements.list(SchedulerJobsSql.GET_DOCUMENT_DUPLICATED_STATUS, row -> row)) {
			String fileName = string(row.get("MSHFILENAME"));
			Integer totalCount = row.get("TOTAL_BY_FILENAME") == null ? null : ((Number) row.get("TOTAL_BY_FILENAME")).intValue();
			// the parent's row key: its non-null mapped values (no <id>); a row with none is a new parent each time
			List<Object> fileKey = keyOf("MSHFILENAME", fileName, "TOTAL_BY_FILENAME", totalCount);
			MailStatusVO file = fileKey == null ? null : filesByKey.get(fileKey);
			boolean known = file != null;
			if (file == null) {
				file = new MailStatusVO();
				if (fileName != null) {
					file.setFileName(fileName);
				}
				if (totalCount != null) {
					file.setTotalCount(totalCount);
				}
				file.setLoincCodeData(new ArrayList<>());
			}
			Object loincCode = row.get("OBXLOINCCODE");
			Object duplicationStatus = row.get("SUM_CURRENT");
			boolean linked = false;
			if (loincCode != null || duplicationStatus != null) {
				List<Object> codeKey = fileKey == null || loincCode == null ? null : List.of(fileKey, loincCode);
				if (codeKey == null || !codesByKey.containsKey(codeKey)) {
					JSONObject code = new JSONObject();
					putIfSet(code, "loincCode", loincCode);
					putIfSet(code, "duplicationStatus", duplicationStatus);
					if (codeKey != null) {
						codesByKey.put(codeKey, code);
					}
					file.getLoincCodeData().add(code);
					linked = true;
				}
			}
			if (!known) {
				if (fileKey != null) {
					filesByKey.put(fileKey, file);
				}
				files.add(fileName != null || totalCount != null || linked ? file : null);
			}
		}
		return files;
	}

	/** The units code (or eHARS units code, ignoring case) of the active units: its eHARS units code, at most one. */
	public List<String> getStandardizedUnitsOfMeasurement(String unitsOfMeasurement) {
		return jpql.list("""
				select u.eharsUnitsCode from CodedUnits u where (upper(u.unitsCode) = upper(?1) or upper(u.eharsUnitsCode) = upper(?1))
				and (u.endDate is null or u.endDate > sysdate) fetch first 1 rows only""", row -> (String) row.get(0), unitsOfMeasurement);
	}

	/**
	 * The unprocessed reassign rows of HIV or STD, grouped by OBX_OBR as the former nested result map grouped them: an
	 * EcrlsReAssignDocs per OBX_OBR (in the order of its first row) with the SRC_IDs of its rows, each once.
	 */
	public List<EcrlsReAssignDocs> getReAssignDocList(String recordType) {
		List<EcrlsReAssignDocs> groups = new ArrayList<>();
		Map<String, EcrlsReAssignDocs> groupsByKey = new HashMap<>();
		Set<List<Object>> linked = new HashSet<>();
		for (Row row : jpql.list("select r.obxObr, r.srcId from LabEclrsReassign r where r.hivStd = ?1 and r.processDate is null", row -> row,
				recordType)) {
			String keyName = (String) row.get(0);
			Long srcId = (Long) row.get(1);
			EcrlsReAssignDocs group = keyName == null ? null : groupsByKey.get(keyName);
			boolean known = group != null;
			if (group == null) {
				group = new EcrlsReAssignDocs();
				group.setKeyName(keyName);
				group.setDocumentIds(new ArrayList<>());
			}
			boolean added = false;
			if (srcId != null && (keyName == null || linked.add(Arrays.asList(keyName, srcId)))) {
				group.getDocumentIds().add(srcId);
				added = true;
			}
			if (!known) {
				if (keyName != null) {
					groupsByKey.put(keyName, group);
				}
				groups.add(keyName != null || added ? group : null);
			}
		}
		return groups;
	}

	/** Marks the reassign rows of an OBX and an OBR key as processed now. */
	public int updateReAssignDocProcessedStatus(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabEclrsReassign r set r.processDate = sysdate where r.srcId in (?1, ?2)",
				Values.longValue(inOutParameterMap.get("OBXOBSERVATIONRESULTKEY")), Values.longValue(inOutParameterMap.get("OBROBSERVATIONREQUESTKEY")));
	}

	/** Sets the number of errors of the load run of an OBX key. */
	public int updateDocumentProcessStatusLog(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update LabEclrsLoadLog l set l.noOfErrors = ?1 where l.obxMax = ?2",
				Values.longValue(inOutParameterMap.get("failedDocumentIdsCount")), Values.longValue(inOutParameterMap.get("OBXOBSERVATIONRESULTKEY")));
	}

	/**
	 * Native: the DataFlux match results (text of the match file) bound as they are, one row per entry of
	 * documentQualityStageData (INSERT ... SELECT FROM DUAL UNION ALL, as before; an empty list fails as before).
	 */
	public int storeDocumentMatchResults(Map<Object, Object> inOutParameterMap) {
		StringBuilder sql = new StringBuilder("insert into RAW_DOCUMENT_MATCH_RESULTS(QS_PARENT_DOC_ID, LOAD_DATE, DOCUMENT_TYPE_CODE, DOCUMENT_ID, "
				+ "POT_STATENO, MATCHING_DATE, MATCH_WEIGHT, MATCH_PASS, MATCH_CD)\n");
		List<Object> values = new ArrayList<>();
		String separator = "";
		for (Object item : list(inOutParameterMap.get("documentQualityStageData"))) {
			Map<?, ?> match = (Map<?, ?>) item;
			int n = values.size();
			sql.append(separator).append("select ?").append(n + 1).append(", SYSDATE, ?").append(n + 2).append(", ?").append(n + 3)
					.append(", ?").append(n + 4).append(", SYSDATE, ?").append(n + 5).append(", ?").append(n + 6).append(", ?").append(n + 7)
					.append(" from dual\n");
			values.addAll(Arrays.asList(match.get("documentId"), match.get("documentType"), match.get("documentId"), match.get("stateno"),
					match.get("matchWeight"), match.get("matchPass"), match.get("flagMatchCd")));
			separator = " Union all ";
		}
		return statements.update(sql.toString(), values.toArray());
	}

	/** Native: the HIV match results (text of the match file) bound as they are; see {@link SchedulerJobsSql}. */
	public int storeDocumentMatchResultsToHIVStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_HIVSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToHIVStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToPRFStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_PRFSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToPRFStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToNYCPRFStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_NYCPRFSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToNYCPRFStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToSTDStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_STDSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToSTDStandardTableArgs(documentMatchData));
	}

	/** Native: the parent and child document ids are text bound as they are (implicit conversion), IN list as before. */
	public int storeDuplicateDocumentSetResults(Map<Object, Object> inputOutputParameterMap) {
		List<Object> values = new ArrayList<>();
		values.add(inputOutputParameterMap.get("parentDocumentId"));
		String in = inList(inputOutputParameterMap.get("duplicateDocumentIds"), values);
		return statements.update("update RAW_DOCUMENT_MATCH_RESULTS set QS_PARENT_DOC_ID  = ?1 where DOCUMENT_ID in " + in, values.toArray());
	}

	/** Native (CALLABLE): pkg_clerical_review.pr_load_doc_match_summary. */
	public void loadDocumentMatchSummary() {
		statements.call("pkg_clerical_review.pr_load_doc_match_summary");
	}

	/** The scheduler parameters (PARAMTER_NAME, PARAMTER_VALUE), by name. */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSchedulerMetaData() {
		return (List<Map<String, String>>) (List<?>) jpql.list(
				"select p.paramterName as PARAMTER_NAME, p.paramterValue as PARAMTER_VALUE from LabSchedulerParameters p order by p.paramterName",
				Rows.map());
	}

	/** Native: the document ids are text bound as they are (implicit conversion), IN list as before. */
	public int deleteChildDocumentsFromMatchResults(Map<Object, Object> inputOutputParameterMap) {
		List<Object> values = new ArrayList<>();
		String in = inList(inputOutputParameterMap.get("duplicateDocumentIds"), values);
		return statements.update("delete from RAW_DOCUMENT_MATCH_RESULTS where DOCUMENT_ID in " + in, values.toArray());
	}

	/** Deactivates the error records of a document. */
	public int disableFlagInErrorTable(int _documentID) {
		return jpql.update("update NyehmsProcessErrors e set e.activeFlag = 'N' where e.documentId = ?1", (long) _documentID);
	}

	/** Sets the value of a scheduler parameter. */
	public int updateSchedulerMetaData(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update LabSchedulerParameters p set p.paramterValue = ?1 where p.paramterName = ?2",
				Values.text(inputOutputParameterMap.get("parameterValue")), Values.text(inputOutputParameterMap.get("parameterName")));
	}

	/** The value of a scheduler parameter (MAX: null if there is none). */
	public String getMetaDataValue(String parameterName) {
		return jpql.one("select max(p.paramterValue) from LabSchedulerParameters p where p.paramterName = ?1", row -> (String) row.get(0),
				parameterName);
	}

	/** Native: the standardized table is named by the caller (tableName, allow-listed). */
	public int loadClericalReviewRecordsForQSProcess(Map<Object, Object> inputOutputParameterMap) {
		return statements.update(names(SchedulerJobsSql.LOAD_CLERICAL_REVIEW_RECORDS_FOR_QSPROCESS, inputOutputParameterMap,
				Map.of("tableName", STD_TABLES)));
	}

	/** The number of error records of a document in status 15 (soft check validations). */
	public int checkDocumentTraversedThroughSoftCheckValidations(int document_ID) {
		return count("select count(*) from NyehmsProcessErrors e where e.documentId = ?1 and e.processStatus = 15", (long) document_ID);
	}

	/**
	 * <b>Bug kept:</b> the former mapper had no statement for this method, so every call failed (MyBatis'
	 * "Invalid bound statement (not found)"); it still always fails. Its only caller is not called.
	 */
	public int checkForReferenceLoincDocumentsCount(Map<Object, Object> inputOutputParameterMap) {
		throw new IllegalStateException("Invalid bound statement (not found): " + SchedulerJobsDAO.class.getName()
				+ ".checkForReferenceLoincDocumentsCount (the former mapper had no statement for it)");
	}

	/** Native: REGEXP_LIKE over the LOINC groups (raw HIV documents of status 1 to status 11). */
	public int changeDocumentsStatusToMultiStepCompressFlag() {
		return statements.update(SchedulerJobsSql.CHANGE_DOCUMENTS_STATUS_TO_MULTI_STEP_COMPRESS_FLAG);
	}

	/**
	 * Native: REGEXP_LIKE, MAX() OVER partitions of ~60 trimmed columns, {@code R.*}. The multi-step documents grouped
	 * by RANKING as the former nested result map grouped them: a MultiStepDocumentsVO per RANKING (in the order of its
	 * first row) with its documents, each built from the ~220 columns the result map mapped (no other column) and
	 * linked once per distinct set of values.
	 */
	public List<MultiStepDocumentsVO> getMultiStepDocuments() {
		RowMapper<EcrlsLabDocVO> bean = Rows.bean(EcrlsLabDocVO.class);
		TypedRowMapper<Row> typed = new TypedRowMapper<>() {
			@Override
			public Class<?> typeOf(String label, int index) {
				return MULTI_STEP_COLUMNS.contains(label.toUpperCase(Locale.ENGLISH)) ? ((TypedRowMapper<?>) bean).typeOf(label, index) : null;
			}

			@Override
			public Row map(Row row) {
				return row;
			}
		};
		List<MultiStepDocumentsVO> groups = new ArrayList<>();
		Map<String, MultiStepDocumentsVO> groupsByKey = new HashMap<>();
		Set<List<Object>> linked = new HashSet<>();
		for (Row row : statements.list(SchedulerJobsSql.GET_MULTI_STEP_DOCUMENTS, typed)) {
			String ranking = (String) row.get("RANKING");
			MultiStepDocumentsVO group = ranking == null ? null : groupsByKey.get(ranking);
			boolean known = group != null;
			if (group == null) {
				group = new MultiStepDocumentsVO();
				group.setRANKING(ranking);
				group.setMultiStepDocuments(new ArrayList<>());
			}
			// the document's row key: the non-null values of its mapped columns (no <id>), within its RANKING
			List<String> labels = new ArrayList<>();
			List<Object> values = new ArrayList<>();
			List<Object> key = new ArrayList<>();
			for (int i = 0; i < row.size(); i++) {
				String label = row.labels().get(i);
				if (row.indexOf(label) == i && MULTI_STEP_COLUMNS.contains(label.toUpperCase(Locale.ENGLISH))) {
					labels.add(label);
					values.add(row.get(i));
					if (row.get(i) != null) {
						key.add(label.toUpperCase(Locale.ENGLISH));
						key.add(row.get(i));
					}
				}
			}
			EcrlsLabDocVO document = bean.map(new Row(labels, values));
			boolean added = false;
			if (document != null && (ranking == null || key.isEmpty() || linked.add(Arrays.asList(ranking, key)))) {
				group.getMultiStepDocuments().add(document);
				added = true;
			}
			if (!known) {
				if (ranking != null) {
					groupsByKey.put(ranking, group);
				}
				groups.add(ranking != null || added ? group : null);
			}
		}
		return groups;
	}

	private static final Set<String> MULTI_STEP_COLUMNS = Set.copyOf(SchedulerJobsSql.MULTI_STEP_DOCUMENT_COLUMNS);

	/** Sets the parent document and the active flag of the multi-step documents (an empty list fails, as IN () did). */
	public int associateParentDocumentForMultiStepDocuments(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update DocumentLocator d set d.parentDocumentId = ?1, d.isActive = ?2 where d.documentId in ?3",
				integer(inputOutputParameterMap.get("parentDocumentId")), Values.text(inputOutputParameterMap.get("statusCode")),
				nonEmpty(list(inputOutputParameterMap.get("documentIds")), "documentIds").stream().map(SchedulerJobsDAO::integer).toList());
	}

	/**
	 * Native: ORA_HASH over a column expression from the scheduler configuration (duplicateColumns, pasted as before —
	 * whoever can edit that configuration can put SQL here) on the raw table named by the caller (tableName,
	 * allow-listed).
	 */
	public int generateHashNumberForNewRecords(Map<Object, Object> inputOutputParameterMap) {
		Object columns = inputOutputParameterMap.get("duplicateColumns");
		String sql = names(SchedulerJobsSql.GENERATE_HASH_NUMBER_FOR_NEW_RECORDS, inputOutputParameterMap, Map.of("tableName", RAW_TABLES));
		return statements.update(sql.replace("${duplicateColumns}", columns == null ? "" : columns.toString()));
	}

	/** Native: ROW_NUMBER() OVER, DECODE and a row-value IN on the raw table named by the caller (tableName, allow-listed). */
	public int checkingDuplicatesInNewRawHIVRecords(Map<Object, Object> inputOutputParameterMap) {
		return statements.update(names(SchedulerJobsSql.CHECKING_DUPLICATES_IN_NEW_RAW_HIVRECORDS, inputOutputParameterMap,
				Map.of("tableName", RAW_TABLES)));
	}

	/** Native: a correlated EXISTS on the raw table named by the caller (tableName, allow-listed). */
	public int checkingDuplicatesInEntireDataWithNewRawHIVData(Map<Object, Object> inputOutputParameterMap) {
		return statements.update(names(SchedulerJobsSql.CHECKING_DUPLICATES_IN_ENTIRE_DATA_WITH_NEW_RAW_HIVDATA, inputOutputParameterMap,
				Map.of("tableName", RAW_TABLES)));
	}

	/** Points the raw provider addresses at a standardized provider address (an empty list fails, as IN () did). */
	public int standardizeRawProviderAddressIdsWithSubProviderId(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("""
				update RawProviderAddress r set r.subProviderAddressId = ?1, r.lastUpdatedDate = sysdate, r.lastUpdatedBy = 'NYEHMS'
				where r.rawProviderAddressId in ?2""", Values.longValue(inputOutputParameterMap.get("standardSubId")),
				longs(inputOutputParameterMap.get("rawIds"), "rawIds"));
	}

	/** Points the raw facilities at a standardized facility (an empty list fails, as IN () did). */
	public int standardizeRawFacilityIdsWithSubFacilityId(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("""
				update RawFacility r set r.subFacilityId = ?1, r.lastUpdatedDate = sysdate, r.lastUpdatedBy = 'NYEHMS'
				where r.rawSubFacilityId in ?2""", Values.longValue(inputOutputParameterMap.get("standardSubId")),
				longs(inputOutputParameterMap.get("rawIds"), "rawIds"));
	}

	/**
	 * 1 (or more) if the scheduler parameter's value (YYYYMMDD) is after yesterday, i.e. the scheduler ran today
	 * (yesterday as the application server's clock gives it).
	 */
	public int isSchedulerRanToday(String schedulerName) {
		return count("select count(*) from LabSchedulerParameters p where p.paramterName = ?1 and p.paramterValue > ?2", schedulerName,
				LocalDate.now().minusDays(1).format(YYYYMMDD));
	}

	/** Sets the scheduler parameter's value to today (YYYYMMDD, the application server's clock). */
	public int updateSchedulerRanDate(String schedulerName) {
		return jpql.update("update LabSchedulerParameters p set p.paramterValue = ?1 where p.paramterName = ?2", LocalDate.now().format(YYYYMMDD),
				schedulerName);
	}

	/**
	 * The highest interpretation code of the given test type, eHARS result and description (each condition only when
	 * set). <b>Bug kept:</b> with none of them set the former SQL ended in "where" and failed; it still fails.
	 */
	public String getStandardInterpretationCodeUsingDescription(Map<Object, Object> inputOutputParameterMap) {
		StringBuilder query = new StringBuilder("select max(c.interpretCd) from CodedHivDocInterpretations c where ");
		List<Object> values = new ArrayList<>();
		String and = "";
		for (String[] condition : new String[][] {{"testType", "c.testTypeCd"}, {"eharsResultcode", "c.eharsResultCd"},
				{"std_interpretation_id", "c.interpretDesc"}}) {
			Object value = inputOutputParameterMap.get(condition[0]);
			if (present(value)) {
				values.add(Values.text(value));
				query.append(and).append(condition[1]).append(" = ?").append(values.size());
				and = " and ";
			}
		}
		if (values.isEmpty()) {
			throw new IllegalArgumentException("getStandardInterpretationCodeUsingDescription: no condition set (the former SQL ended in WHERE)");
		}
		return jpql.one(query.toString(), row -> (String) row.get(0), values.toArray());
	}

	/** The specimen type of a specimen source name (ignoring case), of any one matching row. */
	public String checkSpecimenSourceNameExistance(String specimenSourceName) {
		return jpql.one("select c.specimentype from CodedSpecimenSource c where upper(c.specimenSourceName) = upper(?1) fetch first 1 rows only",
				row -> (String) row.get(0), specimenSourceName);
	}

	/**
	 * Native: about 70 values bound into the standardized STD table through upper(trim(...)) and implicit
	 * conversions of columns whose types are not known here; SYSDATE.
	 */
	public int writeRawStdRecordDataToStdTable(EcrlsStdDocVO ecrlsStdDocVO) {
		return statements.update(SchedulerJobsSql.WRITE_RAW_STD_RECORD_DATA_TO_STD_TABLE,
				SchedulerJobsSql.writeRawStdRecordDataToStdTableArgs(ecrlsStdDocVO));
	}

	/** Sets a raw STD document to status 5 (standardized), processed now. */
	public int updateTheRawStdRecordToStandardize(int document_ID) {
		return jpql.update("update RawStdEclrsDoc d set d.currentProcessStatus = 5, d.processDate = sysdate where d.documentId = ?1",
				(long) document_ID);
	}

	/** Native: the raw table is named by the caller (rawTableName, allow-listed). */
	public int updateTheRawRecordToStandardize(Map<Object, Object> inputOutputParameterMap) {
		return statements.update(names(SchedulerJobsSql.UPDATE_THE_RAW_RECORD_TO_STANDARDIZE, inputOutputParameterMap,
				Map.of("rawTableName", RAW_TABLES)), SchedulerJobsSql.updateTheRawRecordToStandardizeArgs(inputOutputParameterMap));
	}

	/** Deactivates a document's active error records with the worker, process status and comments, now. */
	public int deleteDocumentInErrorTable(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("""
				update NyehmsProcessErrors e set e.activeFlag = 'N', e.updateDate = sysdate, e.updateid = ?1, e.processStatus = ?2,
				e.comments = ?3 where e.documentId = ?4 and e.activeFlag = 'Y'""", Values.longValue(inputOutputParameterMap.get("workerId")),
				Values.longValue(inputOutputParameterMap.get("processStatus")), Values.text(inputOutputParameterMap.get("requestComments")),
				Values.longValue(inputOutputParameterMap.get("documentID")));
	}

	/** Native: FN_TO_DATE(text, 'MM/DD/YYYY'), a PL/SQL function, from DUAL. */
	public Timestamp getDateValueFromString(String _dateValue) {
		return statements.one(SchedulerJobsSql.GET_DATE_VALUE_FROM_STRING, Rows.scalar(Timestamp.class),
				SchedulerJobsSql.getDateValueFromStringArgs(_dateValue));
	}

	/** Native: {@code select *} of the new / errored rows of the raw table named by the caller (rawTableName, allow-listed), as maps. */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> getRawDocsList(Map<Object, Object> inputOutputParamters) {
		return (List<Map<Object, Object>>) (List<?>) statements.list(names(SchedulerJobsSql.GET_RAW_DOCS_LIST, inputOutputParamters,
				Map.of("rawTableName", RAW_TABLES)), Rows.map());
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToAIRSStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_AIRSSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToAIRSStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToMEDStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_MEDSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToMEDStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are (all provider rows of the Medicaid document). */
	public void storeDocumentMatchResultsToMEDProvidersStandardTable(Map<String, Object> documentMatchData) {
		statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_MEDPROVIDERS_STANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToMEDProvidersStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToCCDStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_CCDSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToCCDStandardTableArgs(documentMatchData));
	}

	/**
	 * Native: the match results bound as they are into the provider columns the caller chose (PROV_ADDRESS ... as
	 * CERT_, PCP_ or RX_ columns, allow-listed: one of the three sets).
	 */
	public int storeDocumentMatchResultsToADAPStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(columns(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_ADAPSTANDARD_TABLE, documentMatchData, ADAP_PROVIDER_COLUMNS),
				SchedulerJobsSql.storeDocumentMatchResultsToADAPStandardTableArgs(documentMatchData));
	}

	/** Native: the document id is text bound as it is (implicit conversion). */
	public String getFlagChoseProviderForADAP(Map<String, Object> documentMatchData) {
		return statements.one(SchedulerJobsSql.GET_FLAG_CHOSE_PROVIDER_FOR_ADAP, Rows.scalar(String.class),
				SchedulerJobsSql.getFlagChoseProviderForADAPArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToDEATHStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_DEATHSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToDEATHStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToNycDEATHStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_DEATHSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToNycDEATHStandardTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToCASAStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_CASASTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToCASAStandardTableArgs(documentMatchData));
	}

	/** Records the error codes of an error record (NYEHMS_PROCESS_ERRORS_DETAIL), one row per code. */
	public int writeErrorCodesMasterDetail(Map<Object, Object> inputOutputParamters) {
		List<?> codes = nonEmpty(list(inputOutputParamters.get("errorCodes")), "errorCodes");
		StringBuilder query = new StringBuilder("insert into NyehmsProcessErrorsDetail (errorId, errorCode) values ");
		List<Object> values = new ArrayList<>();
		values.add(Values.longValue(inputOutputParamters.get("processErrorId")));
		for (Object code : codes) {
			values.add(Values.longValue(code));
			query.append(values.size() == 2 ? "" : ", ").append("(?1, ?").append(values.size()).append(')');
		}
		return jpql.update(query.toString(), values.toArray());
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToEPRFOOCStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_EPRFOOCSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToEPRFOOCStandardTableArgs(documentMatchData));
	}

	/**
	 * Native (CALLABLE): PKG_DOCUMENT_MANAGEMENT.pr_dups_processing(p_doc_type_cd, process_date, p_code_out, p_msg_out);
	 * the OUT values are put into the map under p_code_out (Integer) and p_msg_out (String), as MyBatis put them.
	 */
	public void checkDuplicatesinRawDocTable(Map<Object, Object> inputOutputParameterMap) {
		Object processDate = inputOutputParameterMap.get("process_date");
		if (processDate != null && processDate.getClass() == java.util.Date.class) {
			processDate = new java.sql.Date(((java.util.Date) processDate).getTime());   // jdbcType=DATE: the day only
		}
		List<Object> results = statements.call("PKG_DOCUMENT_MANAGEMENT.pr_dups_processing",
				in(inputOutputParameterMap.get("p_doc_type_cd"), String.class),
				in(processDate, processDate instanceof Timestamp ? Timestamp.class : java.sql.Date.class),
				out(Integer.class), out(String.class));
		inputOutputParameterMap.put("p_code_out", results.get(2));
		inputOutputParameterMap.put("p_msg_out", results.get(3));
	}

	/** Native: the document id is text bound as it is (implicit conversion). */
	public int storeDocumentMatchResultsToSSDMStandardTable(Map<String, Object> inputOutputParameterMap) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_SSDMSTANDARD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToSSDMStandardTableArgs(inputOutputParameterMap));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToNycIJRStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_NYC_IJRSTD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToNycIJRStdTableArgs(documentMatchData));
	}

	/** The number of active can-be-residence addresses equal to the address, of the filter type (Facility / Provider). */
	public int checkAddressIsCanBeRes(Map<String, String> inputParamters) {
		return count("select count(*) from RawAddrCanbeRes a where a.address = ?1 and a.flagActive = 'Y' and a.filterType = ?2",
				inputParamters.get("address"), inputParamters.get("FILTER_TYPE"));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToICCRStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_ICCRSTD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToICCRStdTableArgs(documentMatchData));
	}

	/** Native: the document id is text bound as it is (implicit conversion). */
	public String getUnderCustodyForDinDoccs(Map<String, Object> documentMatchData) {
		return statements.one(SchedulerJobsSql.GET_UNDER_CUSTODY_FOR_DIN_DOCCS, Rows.scalar(String.class),
				SchedulerJobsSql.getUnderCustodyForDinDoccsArgs(documentMatchData));
	}

	/**
	 * Native: the match results bound as they are into the facility columns the caller chose (FACILITY_ADDRESS ... as
	 * CURRENT_ or OWNING_ columns, allow-listed: one of the two sets).
	 */
	public int storeDocumentMatchResultsToDinDoccsStandardTable(Map<String, Object> documentMatchData) {
		return statements.update(columns(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_DIN_DOCCS_STANDARD_TABLE, documentMatchData,
				DIN_DOCCS_FACILITY_COLUMNS), SchedulerJobsSql.storeDocumentMatchResultsToDinDoccsStandardTableArgs(documentMatchData));
	}

	/** Native: Oracle date arithmetic (CREATE_DATE > SYSDATE - 1); maps of errorDesc (ERRORDESC) and count (COUNT) as read. */
	public List<Map<String, Object>> getHivErrorsByCategoryAndCount() {
		return statements.list(SchedulerJobsSql.GET_HIV_ERRORS_BY_CATEGORY_AND_COUNT, Rows.map());
	}

	/** 1 (or more) if the scheduler parameter's value (YYYYMMDD) is yesterday or later (the application server's clock). */
	public int isSchedulerRanYesterday(String schedulerName) {
		return count("select count(*) from LabSchedulerParameters p where p.paramterName = ?1 and p.paramterValue >= ?2", schedulerName,
				LocalDate.now().minusDays(1).format(YYYYMMDD));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToTBStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_TBSTD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToTBStdTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToSYPHStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_SYPHSTD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToSYPHStdTableArgs(documentMatchData));
	}

	/** Sets the latest PID update date of the load run of an OBX key. */
	public void updateSchedulerLogWithPidUpdateDate(Map<Object, Object> inOutParameterMap) {
		jpql.update("update LabEclrsLoadLog l set l.lastPidupdatedate = ?1 where l.obxMax = ?2", Values.timestamp(inOutParameterMap.get("maxPidUpdateDate")),
				Values.longValue(inOutParameterMap.get("maxOBXOBSERVATIONRESULTKEY")));
	}

	/**
	 * Native: an outer join with {@code (+)} over the raw and standardized tables named by the caller (RAW_TABLE,
	 * STD_TABLE, allow-listed). The documents of an OBX key, as maps: DOCUMENT_ID, CURRENT_PROCESS_STATUS_RAW,
	 * CURRENT_PROCESS_STATUS_STD (Integer), MATCH_CD and the PID address columns (String), EHARS_IMPORT_DATE as read.
	 */
	public List<Map<String, Object>> getNyehmsDocumentCurrentStatus(Map<String, Object> inputParameterMap) {
		return statements.list(names(SchedulerJobsSql.GET_NYEHMS_DOCUMENT_CURRENT_STATUS, inputParameterMap,
				Map.of("RAW_TABLE", RAW_TABLES, "STD_TABLE", STD_TABLES)), row -> resultMap(row, CURRENT_STATUS_TYPES),
				SchedulerJobsSql.getNyehmsDocumentCurrentStatusArgs(inputParameterMap));
	}

	/** The columns of the former result map nyehmsDocumentByCurrentStatusDetails, with the type each was read as. */
	private static final Map<String, Class<?>> CURRENT_STATUS_TYPES = Map.of("CURRENT_PROCESS_STATUS_RAW", Integer.class,
			"CURRENT_PROCESS_STATUS_STD", Integer.class, "DOCUMENT_ID", Integer.class, "MATCH_CD", String.class, "PIDADDRESSLINE1", String.class,
			"PIDADDRESSLINE2", String.class, "PIDCITY", String.class, "PIDSTATE", String.class, "PIDZIPCODE", String.class);

	/** Native: ORA_HASH of the PID columns, on the raw table named by the caller (RAW_TABLE, allow-listed). */
	public int updatePIDUpdatedDetailsInRaw(Map<String, Object> inputParameterMap) {
		return statements.update(names(SchedulerJobsSql.UPDATE_PIDUPDATED_DETAILS_IN_RAW, inputParameterMap, Map.of("RAW_TABLE", RAW_TABLES)),
				SchedulerJobsSql.updatePIDUpdatedDetailsInRawArgs(inputParameterMap));
	}

	/**
	 * Native: the PL/SQL function pkg_standardization.fn_improve_std_address, on the standardized table named by the
	 * caller (STD_TABLE, allow-listed).
	 */
	public int updatePIDUpdatedDetailsInStd(Map<String, Object> inputParameterMap) {
		return statements.update(names(SchedulerJobsSql.UPDATE_PIDUPDATED_DETAILS_IN_STD, inputParameterMap, Map.of("STD_TABLE", STD_TABLES)),
				SchedulerJobsSql.updatePIDUpdatedDetailsInStdArgs(inputParameterMap));
	}

	/** Native: an insert-select copy of a raw HIV document (~240 columns) with ORA_HASH of the new PID values. */
	public int createNewRawHIVDocumentWithUpdatedPIDData(Map<String, Object> rawTableInputParameterMap) {
		return statements.update(SchedulerJobsSql.CREATE_NEW_RAW_HIVDOCUMENT_WITH_UPDATED_PIDDATA,
				SchedulerJobsSql.createNewRawHIVDocumentWithUpdatedPIDDataArgs(rawTableInputParameterMap));
	}

	/** Native: an insert-select copy of a standardized HIV document through pkg_standardization.fn_improve_std_address. */
	public int createNewStdHIVDocumentWithUpdatedPIDData(Map<String, Object> stdTableInputParameterMap) {
		return statements.update(SchedulerJobsSql.CREATE_NEW_STD_HIVDOCUMENT_WITH_UPDATED_PIDDATA,
				SchedulerJobsSql.createNewStdHIVDocumentWithUpdatedPIDDataArgs(stdTableInputParameterMap));
	}

	/** Native: a self join of the standardized table named by the caller (STD_TABLE, allow-listed); 'Y' or null. */
	public String checkPersonAddressGotEdited(Map<String, Object> inputParameterMap) {
		return statements.one(names(SchedulerJobsSql.CHECK_PERSON_ADDRESS_GOT_EDITED, inputParameterMap, Map.of("STD_TABLE", STD_TABLES)),
				Rows.scalar(String.class), SchedulerJobsSql.checkPersonAddressGotEditedArgs(inputParameterMap));
	}

	/** Native: an insert-select copy of a standardized STD document through pkg_standardization.fn_improve_std_address. */
	public int createNewStdStdDocumentWithUpdatedPIDData(Map<String, Object> stdTableInputParameterMap) {
		return statements.update(SchedulerJobsSql.CREATE_NEW_STD_STD_DOCUMENT_WITH_UPDATED_PIDDATA,
				SchedulerJobsSql.createNewStdStdDocumentWithUpdatedPIDDataArgs(stdTableInputParameterMap));
	}

	/** Native: an insert-select copy of a raw STD document (~240 columns) with ORA_HASH of the new PID values. */
	public int createNewRawSTDDocumentWithUpdatedPIDData(Map<String, Object> rawTableInputParameterMap) {
		return statements.update(SchedulerJobsSql.CREATE_NEW_RAW_STDDOCUMENT_WITH_UPDATED_PIDDATA,
				SchedulerJobsSql.createNewRawSTDDocumentWithUpdatedPIDDataArgs(rawTableInputParameterMap));
	}

	/** Records an error of the PID update process of an OBX key, active, now. */
	public int logPIDProcessError(Map<String, Object> inputParameter) {
		return jpql.update("""
				insert into PidUpdateProcessErrors (obxobservationresultkey, errorDesc, activeFlag, createdDate, documentTypeCode)
				values (?1, ?2, 'Y', sysdate, ?3)""", Values.longValue(inputParameter.get("obxObservationResultKey")),
				Values.text(inputParameter.get("errorMessage")), Values.text(inputParameter.get("document_type_code")));
	}

	/**
	 * The raw HIV documents of the OBX key of a multi-step document, as maps (DOCUMENT_ID, CURRENT_PROCESS_STATUS_RAW,
	 * CURRENT_PROCESS_STATUS_STD as Integer; MATCH_CD and the PID address columns as String; null values left out),
	 * ordered by the status group the former SQL ordered by. NVL(x, '') was NULL on Oracle, so the plain columns are
	 * read.
	 */
	public List<Map<String, Object>> getMultiStepDocumentDetails(Integer multiStepDocumentId) {
		String[] labels = {"DOCUMENT_ID", "CURRENT_PROCESS_STATUS_RAW", "CURRENT_PROCESS_STATUS_STD", "MATCH_CD", "PIDADDRESSLINE1",
			"PIDADDRESSLINE2", "PIDCITY", "PIDSTATE", "PIDZIPCODE"};
		return jpql.list(MULTI_STEP_DOCUMENT_DETAILS, row -> {
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < labels.length; i++) {
				Object value = row.get(i);
				putIfSet(map, labels[i], i < 3 && value != null ? Integer.valueOf(((Number) value).intValue()) : value);
			}
			return map.isEmpty() ? null : map;
		}, multiStepDocumentId);
	}

	/** Native: the raw table is named by the caller (RAW_TABLE, allow-listed). */
	public int checkIfDocumentUpdatedWithPidDetails(Map<String, Object> inputParameterMap) {
		return nativeCount(names(SchedulerJobsSql.CHECK_IF_DOCUMENT_UPDATED_WITH_PID_DETAILS, inputParameterMap, Map.of("RAW_TABLE", RAW_TABLES)),
				SchedulerJobsSql.checkIfDocumentUpdatedWithPidDetailsArgs(inputParameterMap));
	}

	/** The OBX keys of the PID update errors of a record type to process (flag P, not processed yet). */
	public List<Long> getErroredPIDDocumentsList(String recordType) {
		return jpql.list("""
				select e.obxobservationresultkey from PidUpdateProcessErrors e
				where e.activeFlag = 'P' and e.processDate is null and e.documentTypeCode = ?1""", row -> (Long) row.get(0), recordType);
	}

	/** Marks the PID update errors of a record type to process (flag P) as processed now. */
	public void updatePIDErrorDocumentStatus(String recordType) {
		jpql.update("""
				update PidUpdateProcessErrors e set e.processDate = sysdate
				where e.activeFlag = 'P' and e.processDate is null and e.documentTypeCode = ?1""", recordType);
	}

	/** Native: ROWID and analytic functions on the standardized table named by the caller (STD_TABLE, allow-listed). */
	public void checkDuplicatesinStdDocTable(Map<String, String> inputParameterMap) {
		statements.update(names(SchedulerJobsSql.CHECK_DUPLICATESIN_STD_DOC_TABLE, inputParameterMap, Map.of("STD_TABLE", STD_TABLES)));
	}

	/**
	 * Native: the file name column and the standardized table are named by the caller (fileName, stdTableName,
	 * allow-listed); the column is also selected as the value, as before.
	 */
	public void addFileNameToAssignmentReportsTable(Map<String, String> inputParameterMap) {
		statements.update(names(SchedulerJobsSql.ADD_FILE_NAME_TO_ASSIGNMENT_REPORTS_TABLE, inputParameterMap,
				Map.of("fileName", FILE_NAME_COLUMNS, "stdTableName", STD_TABLES)),
				SchedulerJobsSql.addFileNameToAssignmentReportsTableArgs(inputParameterMap));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToSTDConfStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_STDCONF_STD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToSTDConfStdTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToHL7StdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_HL7_STD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToHL7StdTableArgs(documentMatchData));
	}

	/** Native: the match results bound as they are. */
	public int storeDocumentMatchResultsToCOPHIStdTable(Map<String, Object> documentMatchData) {
		return statements.update(SchedulerJobsSql.STORE_DOCUMENT_MATCH_RESULTS_TO_COPHISTD_TABLE,
				SchedulerJobsSql.storeDocumentMatchResultsToCOPHIStdTableArgs(documentMatchData));
	}

	/** Native: the cross-schema NYEHMS_SASDB_OWNER cluster stage, TO_CHAR of the counts; maps (DOC_TYPE, MATCH_CD, DATA_FLUX). */
	public List<Map<String, String>> getDataFluxQSMatchSummary() {
		return stringMaps(statements.list(SchedulerJobsSql.GET_DATA_FLUX_QSMATCH_SUMMARY, Rows.map()));
	}

	/** The error text of any one active error record of a document. */
	public String getDocumentError(int documentId) {
		return jpql.one("select e.errorDesc from NyehmsProcessErrors e where e.documentId = ?1 and e.activeFlag = 'Y' fetch first 1 rows only",
				row -> (String) row.get(0), (long) documentId);
	}

	/** Native: the cross-schema NYEHMS_SASDB_OWNER audit tables, TO_CHAR formats, an outer join with {@code (+)}. */
	public List<Map<String, String>> getDataFluxDetailedStatus(String CYCLE_TYPE) {
		return stringMaps(statements.list(SchedulerJobsSql.GET_DATA_FLUX_DETAILED_STATUS, Rows.map(),
				SchedulerJobsSql.getDataFluxDetailedStatusArgs(CYCLE_TYPE)));
	}

	/** Native: GROUP BY ROLLUP, the cross-schema audit tables; the column labels are what the status mail shows. */
	public List<Map<String, Object>> getAssignmentDetailedStatus() {
		return statements.list(SchedulerJobsSql.GET_ASSIGNMENT_DETAILED_STATUS, Rows.map());
	}

	/** Native: outer joins with {@code (+)}, quoted column labels the status mail shows, the cross-schema audit tables. */
	public List<Map<String, Object>> getAhiReportList() {
		return statements.list(SchedulerJobsSql.GET_AHI_REPORT_LIST, Rows.map());
	}

	/** Native: GROUP BY ROLLUP, quoted column labels the status mail shows, the cross-schema audit tables. */
	public List<Map<String, Object>> getPSMessageReport() {
		return statements.list(SchedulerJobsSql.GET_PSMESSAGE_REPORT, Rows.map());
	}

	/** Native: UNION ALL of three joins, quoted column labels the status mail shows, the cross-schema audit tables. */
	public List<Map<String, Object>> getPSMessageNYCReport() {
		return statements.list(SchedulerJobsSql.GET_PSMESSAGE_NYCREPORT, Rows.map());
	}

	/**
	 * Native: TO_CHAR of the group count, ROWNUM, maps of getObject-typed values. The LOINC group of a PFI and its LOINC
	 * codes (each condition only when set); at most one.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getLoincCombinationData(Map<String, String> inputParameterMap) {
		StringBuilder sql = new StringBuilder(
				"select to_char(GROUP_COUNT) GROUP_COUNT, NEW_DOCUMENT, DONT_RETIRE_OLD, PROCESS_TOGETHER from CODED_LOINC_GROUPING where PFI = ?1\n");
		List<Object> values = new ArrayList<>();
		values.add(inputParameterMap.get("PFI"));
		loincConditions(sql, values, inputParameterMap, "LOINC_1", "LOINC_2", "LOINC_3", "LOINC_4");
		sql.append("and rownum < 2");
		return (Map<String, String>) (Map<?, ?>) statements.one(sql.toString(), Rows.map(), values.toArray());
	}

	/**
	 * Native: ROWNUM, maps of getObject-typed values. The new LOINC and final result of a PFI's LOINC group with its LOINC
	 * codes and test results (each condition only when set); at most one.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getLoincResultCombinationData(Map<String, String> inputParameterMap) {
		StringBuilder sql = new StringBuilder(
				"select NEW_LOINC_CODE, NEW_LOINC_DESC, NEW_LOINC_DESC_SHORT, FINAL_TEST_RESULT from CODED_LOINC_GROUPING where PFI = ?1\n");
		List<Object> values = new ArrayList<>();
		values.add(inputParameterMap.get("PFI"));
		loincConditions(sql, values, inputParameterMap, "LOINC_1", "LOINC_2", "LOINC_3", "LOINC_4", "TEST_RESULT_1", "TEST_RESULT_2",
				"TEST_RESULT_3", "TEST_RESULT_4");
		sql.append("and rownum < 2");
		return (Map<String, String>) (Map<?, ?>) statements.one(sql.toString(), Rows.map(), values.toArray());
	}

	/**
	 * Native: runs the SQL statement of the scheduler parameter SKIP_MULTI_PROCESS_STATUS (sqlQuery) as it is, as before
	 * (whoever can edit LAB_SCHEDULER_PARAMETERS can put SQL here).
	 */
	public int removeMultiProcessStatus(Map<String, String> inputParameterMap) {
		String sql = inputParameterMap.get("sqlQuery");
		return statements.update(sql == null ? "" : sql);
	}

	/** Native (CALLABLE): pkg_hiv_load_process.misc_lab_updates. */
	public void doHivLabUpdates() {
		statements.call("pkg_hiv_load_process.misc_lab_updates");
	}

	// ---- helpers

	private int count(String query, Object... parameters) {
		Long count = jpql.one(query, row -> (Long) row.get(0), parameters);
		return count.intValue();
	}

	private int nativeCount(String sql, Object... parameters) {
		return statements.one(sql, Rows.scalar(Integer.class), parameters);
	}

	/** The ${name} placeholders of a statement replaced by the caller's names, each from its allow-list. */
	private static String names(String sql, Map<?, ?> parameters, Map<String, Set<String>> allowed) {
		String result = sql;
		for (Map.Entry<String, Set<String>> entry : allowed.entrySet()) {
			Object name = parameters.get(entry.getKey());
			if (name == null || !entry.getValue().contains(name.toString())) {
				throw new IllegalArgumentException("Not an allowed name for ${" + entry.getKey() + "}: " + name);
			}
			result = result.replace("${" + entry.getKey() + "}", name.toString());
		}
		return result;
	}

	/**
	 * The ${column} placeholders of an update replaced by the caller's columns, which must be exactly one of the allowed
	 * choices (placeholder -> column for every placeholder).
	 */
	private static String columns(String sql, Map<String, Object> parameters, List<Map<String, String>> choices) {
		for (Map<String, String> choice : choices) {
			if (choice.entrySet().stream().allMatch(e -> e.getValue().equals(parameters.get(e.getKey())))) {
				String result = sql;
				for (Map.Entry<String, String> e : choice.entrySet()) {
					result = result.replace("${" + e.getKey() + "}", e.getValue());
				}
				return result;
			}
		}
		throw new IllegalArgumentException("Not an allowed set of column names: "
				+ choices.get(0).keySet().stream().map(key -> key + "=" + parameters.get(key)).toList());
	}

	/** The allowed choices: for each choice, placeholder i -> column i of the choice. */
	private static List<Map<String, String>> columnSets(String[] placeholders, String[][] choices) {
		List<Map<String, String>> result = new ArrayList<>();
		for (String[] choice : choices) {
			Map<String, String> columns = new LinkedHashMap<>();
			for (int i = 0; i < placeholders.length; i++) {
				columns.put(placeholders[i], choice[i]);
			}
			result.add(columns);
		}
		return List.copyOf(result);
	}

	/** {@code IN (?n, ...)} of the values (bound as they are); an empty list renders nothing, as MyBatis' foreach did. */
	private static String inList(Object values, List<Object> bound) {
		List<?> items = list(values);
		if (items.isEmpty()) {
			return "";
		}
		StringBuilder in = new StringBuilder("(");
		for (Object item : items) {
			bound.add(item);
			in.append(in.length() == 1 ? "" : ",").append('?').append(bound.size());
		}
		return in.append(')').toString();
	}

	private static void loincConditions(StringBuilder sql, List<Object> values, Map<String, String> parameters, String... columns) {
		for (String column : columns) {
			Object value = parameters.get(column);
			if (present(value)) {
				values.add(value);
				sql.append("and ").append(column).append(" = ?").append(values.size()).append('\n');
			}
		}
	}

	/** OGNL {@code x != null and x != ''}. */
	private static boolean present(Object value) {
		return value != null && !"".equals(value);
	}

	private static List<?> list(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("A list is required");
		}
		return value instanceof List<?> items ? items : new ArrayList<>((java.util.Collection<?>) value);
	}

	private static List<?> nonEmpty(List<?> values, String name) {
		if (values.isEmpty()) {
			throw new IllegalArgumentException(name + " is empty (the former SQL failed then)");
		}
		return values;
	}

	private static List<Long> longs(Object values, String name) {
		return nonEmpty(list(values), name).stream().map(Values::longValue).toList();
	}

	private static Integer integer(Object value) {
		Long number = Values.longValue(value);
		return number == null ? null : Math.toIntExact(number);
	}

	private static String string(Object value) {
		if (value == null) {
			return null;
		}
		return value instanceof BigDecimal number ? number.toPlainString() : value.toString();
	}

	/**
	 * A row as the map of a former {@code java.util.Map} result map: the listed columns converted to their type (as
	 * their type handler read them), the other columns as read (auto-mapping); null values left out, null if none.
	 */
	private static Map<String, Object> resultMap(Row row, Map<String, Class<?>> types) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < row.size(); i++) {
			String label = row.labels().get(i);
			if (row.indexOf(label) != i || row.get(i) == null) {
				continue;
			}
			Class<?> type = types.get(label.toUpperCase(Locale.ENGLISH));
			Object value = row.get(i);
			if (type == Integer.class) {
				value = ((Number) value).intValue();
			} else if (type == Long.class) {
				value = ((Number) value).longValue();
			} else if (type == String.class) {
				value = string(value);
			} else if (type == Timestamp.class && !(value instanceof Timestamp)) {
				value = new Timestamp(((java.util.Date) value).getTime());
			}
			map.put(label, value);
		}
		return map.isEmpty() ? null : map;
	}

	private static List<Object> keyOf(Object... labelsAndValues) {
		List<Object> key = new ArrayList<>();
		for (int i = 0; i < labelsAndValues.length; i += 2) {
			if (labelsAndValues[i + 1] != null) {
				key.add(labelsAndValues[i]);
				key.add(labelsAndValues[i + 1]);
			}
		}
		return key.isEmpty() ? null : key;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void putIfSet(Map map, String key, Object value) {
		if (value != null) {
			map.put(key, value);
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static List<Map<String, String>> stringMaps(List<Map<String, Object>> rows) {
		return (List) rows;                                  // the declared type of the former mapper method; the values are as read
	}
}
