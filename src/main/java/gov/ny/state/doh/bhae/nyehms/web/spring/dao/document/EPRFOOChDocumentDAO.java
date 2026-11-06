package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EPRFOOCDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.FileByConclusionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The provider portal EPRF OOC / DNLTC / SNP uploads: the standardized insert of the import, the document screen, the
 * file review lists and status changes, the SNP eligibility file and the OOC/DNLTC follow-up review (formerly a MyBatis
 * mapper with the same name and methods).
 * <p>
 * JPQL over the entities StdEprfOocUpload, RawEprfOocUpload, EprfProvPortalFileUpload (entity.common),
 * PortalProviders, PortalProviderUserMapping, DocumentLocator, PersonDocAssociation, CodedCounty,
 * CodedPpInquriesIntConcl and CodedConclusionSharedProv; the select items are named with the former column labels so
 * that {@link Rows} fills the VOs and maps as MyBatis did. Four statements stay native SQL (unchanged, values bound):
 * {@link #writeStdDocData}, {@link #getOOCDocumentsToReview}, {@link #loadSNPData} and {@link #getEprfDocData}; see
 * each for why. The dates the former SQL set with SYSDATE are the database's SYSDATE ({@code sysdate} in JPQL).
 * <p>
 * <b>Kept as before:</b> {@link #getCompleteEprfDocDataWithHivInfo} lists 'N_M' in both its MATCH and NON MATCH
 * branches (so N_M is MATCH), while {@link #getEprfDocData} has 'N_N' in the NON MATCH branch; the file lists return
 * the day used for sorting as an extra column, under the label Oracle gave the unnamed TRUNC expression, which MyBatis
 * read as a property path (a nested map, see fileListRow); a list
 * statement with an empty file name list fails as the former {@code IN ()} did.
 */
@Repository
@RequiredArgsConstructor
public class EPRFOOChDocumentDAO {

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	// ----------------------------------------------------------------------------------------------------------------
	// writeStdDocData

	/**
	 * The columns of the standardized insert: column, map key, jdbcType of the former binding, and how the value was
	 * written (U = TRIM(UPPER(?)), T = TRIM(?), P = as bound).
	 */
	private static final String[][] STD_COLUMNS = {
			{"UPLOAD_FILE_ID", "UPLOAD_FILE_ID", "VARCHAR", "U"}, {"NYSID", "NYSID", "VARCHAR", "U"}, {"SSN", "SSN", "VARCHAR", "U"},
			{"FACILITY_NAME", "FACILITY_NAME", "VARCHAR", "U"}, {"DIN", "DIN", "VARCHAR", "U"}, {"FACILITY_ZIP", "FACILITY_ZIP", "VARCHAR", "U"},
			{"PERSON_ZIP", "PERSON_ZIP", "VARCHAR", "U"}, {"PROV_ZIP", "PROV_ZIP", "VARCHAR", "U"}, {"SUFFIX", "SUFFIX", "VARCHAR", "U"},
			{"PROV_FNAME", "PROV_FNAME", "VARCHAR", "U"}, {"PROV_LNAME", "PROV_LNAME", "VARCHAR", "U"},
			{"FACILITY_CITY", "FACILITY_CITY", "VARCHAR", "U"}, {"PERSON_CITY", "PERSON_CITY", "VARCHAR", "U"},
			{"PROV_CITY", "PROV_CITY", "VARCHAR", "U"}, {"CURRENT_GENDER", "CURRENT_GENDER", "VARCHAR", "U"},
			{"FACILITY_COUNTY", "FACILITY_COUNTY", "VARCHAR", "U"}, {"MATCH_CD", "MATCH_CD", "VARCHAR", "U"},
			{"PERSON_COUNTY", "PERSON_COUNTY", "VARCHAR", "U"}, {"PROV_COUNTY", "PROV_COUNTY", "VARCHAR", "U"},
			{"MEDICAID_NUMBER", "MEDICAID_NUMBER", "VARCHAR", "U"}, {"RACE_CD", "RACE_CD", "VARCHAR", "U"}, {"DOB", "DOB", "VARCHAR", "U"},
			{"FACILITY_CENSUS_TRACT", "FACILITY_CENSUS_TRACT", "VARCHAR", "U"}, {"FACILITY_LATITUDE", "FACILITY_LATITUDE", "VARCHAR", "U"},
			{"FACILITY_LONGITUDE", "FACILITY_LONGITUDE", "VARCHAR", "U"}, {"FACILITY_PHONE", "FACILITY_PHONE", "VARCHAR", "U"},
			{"PERSON_CENSUS_TRACT", "PERSON_CENSUS_TRACT", "VARCHAR", "U"}, {"PERSON_LATITUDE", "PERSON_LATITUDE", "VARCHAR", "U"},
			{"PERSON_LONGITUDE", "PERSON_LONGITUDE", "VARCHAR", "U"}, {"PERSON_PHONE", "PERSON_PHONE", "VARCHAR", "U"},
			{"PROV_CENSUS_TRACT", "PROV_CENSUS_TRACT", "VARCHAR", "U"}, {"PROV_LATITUDE", "PROV_LATITUDE", "VARCHAR", "U"},
			{"PROV_LICENSE", "PROV_LICENSE", "VARCHAR", "U"}, {"PROV_LONGITUDE", "PROV_LONGITUDE", "VARCHAR", "U"},
			{"PROV_PHONE", "PROV_PHONE", "VARCHAR", "U"}, {"ETHNICITY", "ETHNICITY", "VARCHAR", "U"},
			{"FACILITY_STATE", "FACILITY_STATE", "VARCHAR", "U"}, {"PERSON_STATE", "PERSON_STATE", "VARCHAR", "U"},
			{"PROV_STATE", "PROV_STATE", "VARCHAR", "U"}, {"MED_RECORD_NUMBER", "MED_RECORD_NUMBER", "VARCHAR", "U"},
			{"FACILITY_NPI", "FACILITY_NPI", "VARCHAR", "U"}, {"PROV_NPI", "PROV_NPI", "VARCHAR", "U"},
			{"FACILITY_ADDRESS", "FACILITY_ADDR", "VARCHAR", "U"}, {"FNAME", "FNAME", "VARCHAR", "U"},
			{"LAST_VIRAL_LOAD_RESULT", "LAST_VIRAL_LOAD_RESULT", "VARCHAR", "U"}, {"LNAME", "LNAME", "VARCHAR", "U"},
			{"MATCHED_STATENO", "MATCHED_STATENO", "VARCHAR", "U"}, {"MNAME", "MNAME", "VARCHAR", "U"},
			{"PERSON_ADDRESS", "PERSON_ADDRESS", "VARCHAR", "U"}, {"PROV_ADDRESS", "PROV_ADDR", "VARCHAR", "U"},
			{"UPLOAD_FILENAME", "UPLOAD_FILENAME", "VARCHAR", "T"}, {"REVIEW_UID", "REVIEW_UID", "VARCHAR", "U"},
			{"UPLOAD_ID", "UPLOAD_ID", "VARCHAR", "U"}, {"BIRTH_SEX", "BIRTH_SEX", "VARCHAR", "U"},
			{"FILE_TYPE_CD", "FILE_TYPE_CD", "VARCHAR", "U"}, {"RACE_ASIAN", "RACE_ASIAN", "VARCHAR", "U"},
			{"RACE_BLACK", "RACE_BLACK", "VARCHAR", "U"}, {"RACE_INDIAN", "RACE_INDIAN", "VARCHAR", "U"},
			{"RACE_MULTIPLE", "RACE_MULTIPLE", "VARCHAR", "U"}, {"RACE_OTHER", "RACE_OTHER", "VARCHAR", "U"},
			{"RACE_PACIFIC", "RACE_PACIFIC", "VARCHAR", "U"}, {"RACE_UNKNOWN", "RACE_UNKNOWN", "VARCHAR", "U"},
			{"RACE_WHITE", "RACE_WHITE", "VARCHAR", "U"}, {"MATCH_WEIGHT", "MATCH_WEIGHT", "DECIMAL", "P"},
			{"CURRENT_PROCESS_STATUS", "CURRENT_PROCESS_STATUS", "DECIMAL", "P"}, {"DOCUMENT_ID", "DOCUMENT_ID", "DECIMAL", "P"},
			{"LAST_CD4_RESULT_COUNT", "LAST_CD4_RESULT_COUNT", "DECIMAL", "P"},
			{"LAST_CD4_RESULT_PERCENT", "LAST_CD4_RESULT_PERCENT", "DECIMAL", "P"}, {"MATCH_PASS", "MATCH_PASS", "DECIMAL", "P"},
			{"QS_PARENT_DOC_ID", "QS_PARENT_DOC_ID", "DECIMAL", "P"}, {"EHARS_IMPORT_DATE", "EHARS_IMPORT_DATE", "VARCHAR", "P"},
			{"LAST_CD4_DATE", "LAST_CD4_DATE", "VARCHAR", "P"}, {"LAST_VIRAL_LOAD_DATE", "LAST_VIRAL_LOAD_DATE", "VARCHAR", "P"},
			{"MATCHED_PROCESS_DATE", "MATCHED_PROCESS_DATE", "VARCHAR", "P"}, {"NYC_SHIP_DATE", "NYC_SHIP_DATE", "VARCHAR", "P"},
			{"PROV_LAST_VISIT_DATE", "PROV_LAST_VISIT_DATE", "VARCHAR", "P"}, {"REVIEW_DT", "REVIEW_DT", "VARCHAR", "P"},
			{"UPLOAD_DT", "UPLOAD_DT", "DATE", "P"}, {"PREF_NAME", "PREF_NAME", "VARCHAR", "U"},
			{"FLUX_MATCH_CD", "FLUX_MATCH_CD", "VARCHAR", "P"}, {"FLUX_POT_STATENO", "FLUX_POT_STATENO", "VARCHAR", "P"},
			{"FLUX_MATCHED_STATENO", "FLUX_MATCHED_STATENO", "VARCHAR", "P"},
			{"FLUX_QS_PARENT_DOC_ID", "FLUX_QS_PARENT_DOC_ID", "DECIMAL", "P"},
			{"FLUX_MATCHED_PROCESS_DATE", "FLUX_MATCHED_PROCESS_DATE", "DATE", "P"}};

	/** The standardized insert, built from {@link #STD_COLUMNS} (the former SQL text). */
	private static final String INSERT_STD;

	static {
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for (int i = 0; i < STD_COLUMNS.length; i++) {
			String[] column = STD_COLUMNS[i];
			String parameter = "?" + (i + 1);
			columns.append(i == 0 ? "" : ", ").append(column[0]);
			values.append(i == 0 ? "" : ",").append(switch (column[3]) {
				case "U" -> "trim(upper(" + parameter + "))";
				case "T" -> "trim(" + parameter + ")";
				default -> parameter;
			});
		}
		INSERT_STD = "insert into STD_EPRF_OOC_UPLOAD(" + columns + ")\nvalues\n  (" + values + ")";
	}

	/**
	 * Inserts the standardized record of an upload (the import of a raw record), with the values of the raw record's
	 * map; text values TRIM(UPPER(...)) (UPLOAD_FILENAME only TRIM) as before. FACILITY_ADDRESS / PROV_ADDRESS come from
	 * the keys FACILITY_ADDR / PROV_ADDR.
	 * <p>
	 * Native SQL: the date columns EHARS_IMPORT_DATE, LAST_CD4_DATE, LAST_VIRAL_LOAD_DATE, MATCHED_PROCESS_DATE,
	 * NYC_SHIP_DATE, PROV_LAST_VISIT_DATE and REVIEW_DT were bound as text and converted by Oracle with the session's
	 * NLS date format, and every value was bound with the type of the map's value (text, number or date), not of the
	 * column; JPQL binds each value with its attribute's type, so it cannot keep these conversions. The values are
	 * bound as MyBatis bound them: a value with its own type, a java.util.Date of a DATE parameter as a day
	 * (java.sql.Date), a null with the former jdbcType.
	 *
	 * @return the number of rows inserted
	 */
	public int writeStdDocData(Map<Object, Object> docVO) {
		Object[] parameters = new Object[STD_COLUMNS.length];
		for (int i = 0; i < STD_COLUMNS.length; i++) {
			Object value = docVO.get(STD_COLUMNS[i][1]);
			String jdbcType = STD_COLUMNS[i][2];
			if (value == null) {
				parameters[i] = switch (jdbcType) {
					case "DECIMAL" -> NativeStatements.number(null);
					case "DATE" -> NativeStatements.param(null, StandardBasicTypes.DATE);
					default -> NativeStatements.text(null);
				};
			} else if ("DATE".equals(jdbcType) && value.getClass() == java.util.Date.class) {
				// MyBatis' DateOnlyTypeHandler (a java.util.Date with jdbcType DATE): setDate
				parameters[i] = new java.sql.Date(((java.util.Date) value).getTime());
			} else {
				parameters[i] = value;
			}
		}
		return statements.update(INSERT_STD, parameters);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// the document screen

	private static final String EPRF_OOC_DOC = """
			select s1.prefName as PREF_NAME, pdoc.stateno as STATENO, pdoc.discardStatus as DISCARD_STATUS,
			r1.address as rawPersonAddress, r1.provAddr as rawProviderAddress, r1.facilityAddr as rawFacilityAddress,
			s1.birthSex as BIRTH_SEX, s1.currentGender as CURRENT_GENDER, s1.currentProcessStatus as CURRENT_PROCESS_STATUS,
			s1.din as DIN, s1.dob as DOB, s1.documentId as DOCUMENT_ID, s1.eharsImportDate as EHARS_IMPORT_DATE,
			s1.ethnicity as ETHNICITY, s1.facilityAddress as FACILITY_ADDRESS, s1.facilityCensusTract as FACILITY_CENSUS_TRACT,
			s1.facilityCity as FACILITY_CITY, s1.facilityCounty as FACILITY_COUNTY, s1.facilityLatitude as FACILITY_LATITUDE,
			s1.facilityLongitude as FACILITY_LONGITUDE, s1.facilityName as FACILITY_NAME, s1.facilityNpi as FACILITY_NPI,
			s1.facilityPhone as FACILITY_PHONE, s1.facilityState as FACILITY_STATE, s1.facilityZip as FACILITY_ZIP,
			s1.fileTypeCd as FILE_TYPE_CD, s1.fname as FNAME, s1.lastCd4Date as LAST_CD4_DATE,
			s1.lastCd4ResultCount as LAST_CD4_RESULT_COUNT, s1.lastCd4ResultPercent as LAST_CD4_RESULT_PERCENT,
			s1.lastViralLoadDate as LAST_VIRAL_LOAD_DATE, s1.lastViralLoadResult as LAST_VIRAL_LOAD_RESULT, s1.lname as LNAME,
			s1.matchCd as MATCH_CD, s1.matchPass as MATCH_PASS, s1.matchWeight as MATCH_WEIGHT,
			s1.matchedProcessDate as MATCHED_PROCESS_DATE, s1.matchedStateno as MATCHED_STATENO,
			s1.medRecordNumber as MED_RECORD_NUMBER, s1.medicaidNumber as MEDICAID_NUMBER, s1.mname as MNAME,
			s1.nycShipDate as NYC_SHIP_DATE, s1.nysid as NYSID, s1.personAddress as PERSON_ADDRESS,
			s1.personCensusTract as PERSON_CENSUS_TRACT, s1.personCity as PERSON_CITY, s1.personCounty as PERSON_COUNTY,
			s1.personLatitude as PERSON_LATITUDE, s1.personLongitude as PERSON_LONGITUDE, s1.personState as PERSON_STATE,
			s1.personPhone as PERSON_PHONE, s1.personZip as PERSON_ZIP, s1.provAddress as PROV_ADDRESS,
			s1.provCensusTract as PROV_CENSUS_TRACT, s1.provCity as PROV_CITY, s1.provCounty as PROV_COUNTY,
			s1.provFname as PROV_FNAME, s1.provLastVisitDate as PROV_LAST_VISIT_DATE, s1.provLatitude as PROV_LATITUDE,
			s1.provLicense as PROV_LICENSE, s1.provLname as PROV_LNAME, s1.provLongitude as PROV_LONGITUDE,
			s1.provNpi as PROV_NPI, s1.provPhone as PROV_PHONE, s1.provState as PROV_STATE, s1.provZip as PROV_ZIP,
			s1.qsParentDocId as QS_PARENT_DOC_ID, s1.raceAsian as RACE_ASIAN, s1.raceBlack as RACE_BLACK,
			s1.raceCd as RACE_CD, s1.raceIndian as RACE_INDIAN, s1.raceMultiple as RACE_MULTIPLE, s1.raceOther as RACE_OTHER,
			s1.racePacific as RACE_PACIFIC, s1.raceUnknown as RACE_UNKNOWN, s1.raceWhite as RACE_WHITE,
			s1.reviewDt as REVIEW_DT, s1.reviewUid as REVIEW_UID, s1.ssn as SSN, s1.suffix as SUFFIX,
			s1.uploadDt as UPLOAD_DT, s1.uploadFilename as UPLOAD_FILENAME, s1.uploadId as UPLOAD_ID,
			d.parentDocumentId as PARENT_DOCUMENT_ID
			from RawEprfOocUpload r1
			join StdEprfOocUpload s1 on s1.documentId = r1.documentId and r1.documentId = ?1
			join DocumentLocator d on r1.documentId = d.documentId
			left join PersonDocAssociation pdoc on r1.documentId = pdoc.documentId and pdoc.dissassociationDate is null
			""";

	/**
	 * The document screen: the standardized record, the raw addresses, the current person (STATENO, DISCARD_STATUS) and
	 * the parent document.
	 *
	 * @return the document, or null if there is none
	 */
	public EPRFOOCDocVO getEPRFOOCDoc(int docId) {
		return jpql.one(EPRF_OOC_DOC, Rows.bean(EPRFOOCDocVO.class), docId);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// the file lists

	/** The entities the former {@code ${tableName}} could name (the raw or the standardized uploads). */
	private static final Map<String, String> FILE_LIST_ENTITIES = Map.of("RAW_EPRF_OOC_UPLOAD", "RawEprfOocUpload",
			"STD_EPRF_OOC_UPLOAD", "StdEprfOocUpload");

	/** The file list columns: the file, the upload day as text and as a date (for sorting), who submitted it. */
	private static final String FILE_LIST_SELECT = """
			select distinct e.uploadFilename, format(e.uploadDt as 'MM/dd/yyyy'), trunc(e.uploadDt, day) as uploadDay,
			case when b.providerHcsid is not null then concat(b.providerLname, ', ', b.providerFname)
			else concat(c.userLname, ', ', c.userFname) end
			""";

	private static final String FILE_LIST_JOINS = """
			e left join PortalProviders b on e.uploadId = b.providerHcsid
			left join PortalProviderUserMapping c on e.uploadId = c.userHcsid
			""";

	/**
	 * The files of the raw or standardized uploads with a process status (REVIEW_DT not set for status 17), of a file
	 * type unless it is empty or ALL, latest upload day first: JSONObjects with FILENAME, SUBMITTEDDATE,
	 * {@code TRUNC(STD_EPRF.UPLOAD_DT)} (the day, as Oracle labelled the unnamed sort column, as a nested map) and
	 * SUBMITTEDBY.
	 *
	 * @param inputParameterMap tableName (RAW_EPRF_OOC_UPLOAD or STD_EPRF_OOC_UPLOAD), processStatus, fileType
	 * @throws InvalidDataAccessResourceUsageException for another table name (the former SQL failed for it)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getOOCDocumentFileNames(Map<String, Object> inputParameterMap) {
		Object tableName = inputParameterMap.get("tableName");
		String entity = tableName == null ? null : FILE_LIST_ENTITIES.get(tableName.toString());
		if (entity == null) {
			throw new InvalidDataAccessResourceUsageException("Not a table of the EPRF OOC file list: " + tableName);
		}
		Object processStatus = inputParameterMap.get("processStatus");
		List<Object> parameters = new ArrayList<>();
		StringBuilder query = new StringBuilder(FILE_LIST_SELECT).append("from ").append(entity).append(' ').append(FILE_LIST_JOINS)
				.append("where ");
		if (numberEquals(processStatus, 17)) {
			query.append("e.reviewDt is null and ");
		}
		parameters.add(integer(processStatus));
		query.append("e.currentProcessStatus = ?1");
		Object fileType = inputParameterMap.get("fileType");
		if (fileType != null && !"".equals(fileType) && !"ALL".equals(fileType)) {
			parameters.add(fileType);
			query.append(" and e.fileTypeCd = ?2");
		}
		query.append(" order by uploadDay desc");
		return jpql.list(query.toString(), row -> (JSONObject) fileListRow(row, new JSONObject(), "TRUNC(STD_EPRF.UPLOAD_DT)"),
				parameters.toArray());
	}

	private static final String SNP_FILES = FILE_LIST_SELECT + "from StdEprfOocUpload " + FILE_LIST_JOINS + """
			where e.fileTypeCd = 'P'
			and e.fluxMatchCd in ('M', 'N', 'C_M', 'C_N', 'P_M', 'P_N', 'N_N', 'N_M', 'M_M', 'M_N')
			and e.currentProcessStatus in (10, 14)
			and e.uploadFilename not in (select coalesce(x.uploadFilename, 'XX') from StdEprfOocUpload x where x.currentProcessStatus = 17)
			order by uploadDay desc
			""";

	/**
	 * The SNP files (file type P) matched and not yet reviewed (no record of the file with status 17), latest upload day
	 * first: maps with FILENAME, SUBMITTEDDATE, {@code TRUNC(A.UPLOAD_DT)} and SUBMITTEDBY.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getSNPFileNames() {
		return (List) jpql.list(SNP_FILES, row -> fileListRow(row, new HashMap<>(), "TRUNC(A.UPLOAD_DT)"));
	}

	private static final String OOC_DNLTC_FILES = FILE_LIST_SELECT + "from StdEprfOocUpload " + FILE_LIST_JOINS + """
			where e.fileTypeCd in ('O', 'D')
			and e.fluxMatchCd in ('M', 'N', 'C_M', 'C_N', 'P_M', 'P_N', 'N_N', 'N_M', 'M_M', 'M_N')
			and e.currentProcessStatus in (10, 14)
			and e.uploadFilename not in (select coalesce(x.uploadFilename, 'XX') from StdEprfOocUpload x where x.currentProcessStatus = 17)
			and e.uploadFilename in (select f.filename from EprfProvPortalFileUpload f where f.fileClientFeedbackDate is %s null)
			order by uploadDay desc
			""";

	/**
	 * The OOC and DNLTC files (file types O, D) matched and not yet reviewed, with (origin Review) or without client
	 * feedback, latest upload day first: maps with FILENAME, SUBMITTEDDATE, {@code TRUNC(STD_EPRF.UPLOAD_DT)} and
	 * SUBMITTEDBY.
	 *
	 * @param inputParameter origin
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getOOCDNLTCFileNames(Map<String, String> inputParameter) {
		String query = OOC_DNLTC_FILES.formatted("Review".equals(inputParameter.get("origin")) ? "not" : "");
		return (List) jpql.list(query, row -> fileListRow(row, new HashMap<>(), "TRUNC(STD_EPRF.UPLOAD_DT)"));
	}

	/**
	 * A file list row as the former map: the labels of the former columns, null values left out, a row of nulls null.
	 * MyBatis read a label with a dot as a property path, so the day under {@code TRUNC(STD_EPRF.UPLOAD_DT)} is a nested
	 * HashMap: key {@code TRUNC(STD_EPRF} holding {@code UPLOAD_DT)} (kept as it was).
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map fileListRow(Row row, Map map, String dayLabel) {
		String[] labels = {"FILENAME", "SUBMITTEDDATE", dayLabel, "SUBMITTEDBY"};
		for (int i = 0; i < labels.length; i++) {
			if (row.get(i) != null) {
				String label = labels[i];
				int dot = label.indexOf('.');
				if (dot < 0) {
					map.put(label, row.get(i));
				} else {
					Map nested = new HashMap<>();
					nested.put(label.substring(dot + 1), row.get(i));
					map.put(label.substring(0, dot), nested);
				}
			}
		}
		return map.isEmpty() ? null : map;
	}

	/** All upload file names of a file type (one per raw record, in database order). */
	public List<String> getAllFileNamesByFileType(String documentFileType) {
		return jpql.list("select r.uploadFilename from RawEprfOocUpload r where r.fileTypeCd = ?1", Rows.scalar(String.class),
				documentFileType);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// the review of the raw records

	// native: a map of the raw columns as the database types them (JDBC getObject; the column types of
	// RAW_EPRF_OOC_UPLOAD are not known), one of them under the label Oracle gives the unnamed TO_CHAR expression
	private static final String DOCUMENTS_TO_REVIEW = """
			select DOCUMENT_ID, MEDRECNO, FNAME, MNAME, LNAME, SUFFIX, DOB, SSN, CIN, DIN, NYSID, BIRTH_SEX, GENDER,
			REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(RACE_CD, '01', ' White '), '02', ' Black '), '03', ' Asian'), '05' ,' American Indian / Alaskan Native '), '06' ,' Other '), '09' ,' Unknown '), '04' ,' Hawaiian/Pacific Islander') RACE_CD,
			e.ETHNICITY_DESCRIPTION ETHNICCD,
			 ADDRESS, CITY, STATE, ZIPCODE, TELEPHONE,
			 pkg_trk_common.fn_get_fullname (PROV_LNAME,
			                                            PROV_FNAME,
			                                            NULL,
			                                            NULL)
			                                            PROV_NAME
			 , PROV_NPI, PROV_LICENSE, PROV_ADDR, PROV_CITY, PROV_STATE, PROV_ZIP, PROV_PHONE, TO_CHAR(PROV_LAST_VISIT_DATE, 'MM/DD/YYYY') PROV_LAST_VISIT_DATE, TO_CHAR(LAST_VIRAL_LOAD_DATE, 'MM/DD/YYYY') LAST_VIRAL_LOAD_DATE, TO_CHAR(LAST_CD4_DATE, 'MM/DD/YYYY') LAST_CD4_DATE, FACILITY_NAME, FACILITY_NPI, FACILITY_ADDR, FACILITY_CITY, FACILITY_STATE, FACILITY_ZIP, FACILITY_PHONE, TO_CHAR(UPLOAD_DT, 'MM/DD/YYYY'), UPLOAD_ID, UPLOAD_FILENAME, LAST_VIRAL_LOAD_RESULT, LAST_CD4_RESULT_COUNT, LAST_CD4_RESULT_PERCENT, PREF_NAME, FILE_TYPE_CD
			from RAW_EPRF_OOC_UPLOAD, CODED_ETHNICITY e  where
			current_process_Status = 16
			and ETHNICCD = e.ETHNICITY_CODE (+)
			""";

	/**
	 * The raw records waiting for review (status 16) of a file (all files for ALL) and of a file type (unless it is empty
	 * or ALL), as maps of the raw columns (null values left out).
	 *
	 * @param inputParameterMap documentFileName, documentFileType
	 */
	public List<Map<String, Object>> getOOCDocumentsToReview(Map<String, String> inputParameterMap) {
		StringBuilder sql = new StringBuilder(DOCUMENTS_TO_REVIEW);
		List<Object> parameters = new ArrayList<>();
		String documentFileName = inputParameterMap.get("documentFileName");
		if (!"ALL".equals(documentFileName)) {
			parameters.add(NativeStatements.text(documentFileName));
			sql.append("\t\tand UPLOAD_FILENAME = ?").append(parameters.size()).append('\n');
		}
		String documentFileType = inputParameterMap.get("documentFileType");
		if (documentFileType != null && !documentFileType.isEmpty() && !"ALL".equals(documentFileType)) {
			parameters.add(NativeStatements.text(documentFileType));
			sql.append("\t\t AND FILE_TYPE_CD =  ?").append(parameters.size()).append('\n');
		}
		return statements.list(sql.toString(), Rows.map(), parameters.toArray());
	}

	/**
	 * Sets the process status, the reviewer (REVIEW_ID) and REVIEW_DATE (SYSDATE) of the raw records waiting for review
	 * (status 16) of some files, of a file type unless it is empty or ALL.
	 *
	 * @param inputParameterMap processStatus, workerId, documentFileNames (a list), documentFileType
	 * @return the number of rows changed
	 * @throws InvalidDataAccessResourceUsageException for an empty or missing file list (the former {@code IN ()} failed)
	 */
	public int updateOOCDocumentReviewStatus(Map<String, Object> inputParameterMap) {
		Collection<?> fileNames = fileNames(inputParameterMap.get("documentFileNames"));
		StringBuilder query = new StringBuilder("""
				update RawEprfOocUpload r set r.currentProcessStatus = ?1, r.reviewId = ?2, r.reviewDate = sysdate
				where r.currentProcessStatus = 16 and r.uploadFilename in (?3)""");
		List<Object> parameters = new ArrayList<>(Arrays.asList(integer(inputParameterMap.get("processStatus")),
				integer(inputParameterMap.get("workerId")), fileNames));
		Object documentFileType = inputParameterMap.get("documentFileType");
		if (documentFileType != null && !"".equals(documentFileType) && !"ALL".equals(documentFileType)) {
			parameters.add(documentFileType);
			query.append(" and r.fileTypeCd = ?4");
		}
		return jpql.update(query.toString(), parameters.toArray());
	}

	/**
	 * Sets the facility category (not for SNP files, type P), the accepting worker and the acceptance date (SYSDATE) of
	 * some provider portal files.
	 *
	 * @param inputParameterMap documentFileType, facilityCategory, workerId, documentFileNames (a list)
	 * @return the number of rows changed
	 * @throws InvalidDataAccessResourceUsageException for an empty or missing file list (the former {@code IN ()} failed)
	 */
	public int updateOOCFacilityCategory(Map<String, Object> inputParameterMap) {
		Collection<?> fileNames = fileNames(inputParameterMap.get("documentFileNames"));
		Integer workerId = integer(inputParameterMap.get("workerId"));
		if (!"P".equals(inputParameterMap.get("documentFileType"))) {
			return jpql.update("""
					update EprfProvPortalFileUpload f set f.fileCategory = ?1, f.fileAcceptedUid = ?2, f.fileAcceptedDate = sysdate
					where f.filename in (?3)""", text(inputParameterMap.get("facilityCategory")), workerId, fileNames);
		}
		return jpql.update("""
				update EprfProvPortalFileUpload f set f.fileAcceptedUid = ?1, f.fileAcceptedDate = sysdate
				where f.filename in (?2)""", workerId, fileNames);
	}

	// native: optimizer hints (parallel scans of the eHARS tables) and joins on a PL/SQL function of both sides in a
	// chain of CTEs; the hints have no JPQL form
	private static final String SNP_DATA = """
				WITH snip_data AS
			(
			SELECT p.stateno, s.document_id, s.medicaid_number
			FROM std_eprf_ooc_upload s
			JOIN person_doc_association p
			ON (s.document_id = p.document_id)
			WHERE s.upload_filename = ?1
			AND s.file_type_cd = 'P'
			AND s.flux_match_cd IN ('M','C_M', 'P_M', 'M_M', 'N_M')
			AND p.dissassociation_date IS NULL
			), nys_nyc_ehars AS
			(
			    SELECT /*+ parallel(n,4) parallel(c,4)*/ a.stateno, a.document_id, a.medicaid_number, pkg_consolidation.fn_give_current_stateno(n.nyehms_stateno) n_nyehms_stateno
			            , n.hiv_aids_cdc_elig n_hiv_aids_elig, n.ehars_stateno n_ehars_stateno
			            , pkg_consolidation.fn_give_current_stateno(c.nyehms_stateno) c_nyehms_stateno
			            , c.hiv_aids_cdc_elig c_hiv_aids_elig, c.hiv_cdc_elig c_hiv_cdc_elig, c.aids_cdc_elig c_aids_elig
			    FROM snip_data a
			    LEFT OUTER JOIN nyehms_ehars_stateno_map n
			    ON (pkg_consolidation.fn_give_current_stateno(n.nyehms_stateno) = a.stateno)
			    LEFT OUTER JOIN ehars_person_nyc c
			    ON (pkg_consolidation.fn_give_current_stateno(c.nyehms_stateno) = a.stateno)
			    WHERE (n.hiv_aids_cdc_elig = 'Y' OR c.hiv_cdc_elig = 'Y' OR c.aids_cdc_elig = 'Y')
			    --WHERE Nvl(a.stateno,'-1') != '-1'
			)
			SELECT a.medicaid_number
			        , 'Y' eligibility
			FROM nys_nyc_ehars a
			UNION
			SELECT s.medicaid_number, 'N' eligibility
			FROM std_eprf_ooc_upload s
			WHERE s.upload_filename = ?2
			AND s.file_type_cd = 'P'
			--AND s.match_cd NOT IN ('M','C_M', 'P_M')
			AND NOT EXISTS (SELECT 1 FROM nys_nyc_ehars a WHERE s.medicaid_number = a.medicaid_number)
					order by ELIGIBILITY desc
			""";

	/**
	 * The SNP eligibility of the Medicaid numbers of an SNP file: maps with MEDICAID_NUMBER and ELIGIBILITY (Y for the
	 * matched persons eligible in the NYS or NYC eHARS, N for the others), Y first.
	 */
	public List<Map<String, Object>> loadSNPData(String documentFileName) {
		return statements.list(SNP_DATA, Rows.map(), NativeStatements.text(documentFileName), NativeStatements.text(documentFileName));
	}

	/** The standardized records of a file in follow-up (status 10) not yet completed. */
	public List<Integer> getOOCDNLTCDocumentsFromMatchReview(String documentFileName) {
		return jpql.list("""
				select s.documentId from StdEprfOocUpload s
				where s.currentProcessStatus in (10) and s.uploadFilename = ?1 and s.completeDate is null""",
				Rows.scalar(Integer.class), documentFileName);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// the follow-up review

	// native: the CTE limits its rows with ROWNUM before the analytic ROW_NUMBER() OVER (PARTITION BY 1 ...) of the
	// second CTE, MONTHS_BETWEEN and SELECT M.* over a CTE; HQL cannot select from these CTEs this way
	private static final String EPRF_DOC_DATA = """
			WITH MAIN_SQL
			     AS (SELECT P.stateno AS NYEHMS_STATENO,
			                M.EHARS_STATENO AS "NYS_EHARS_STATENO",
			                N.EHARS_STATENO AS "NYC_EHARS_STATENO",
			                N.CITYNO AS "NYC_CITY_NO",
			                CASE
			                   WHEN M.VITAL_STATUS = '1' THEN 'Alive'
			                   WHEN M.VITAL_STATUS = '2' THEN 'Dead'
			                END
			                   AS "NYS_VITAL_STATUS",
			                CASE
			                   WHEN N.VITAL_STATUS = '1' THEN 'Alive'
			                   WHEN N.VITAL_STATUS = '2' THEN 'Dead'
			                END
			                   AS "NYC_VITAL_STATUS",
			                CASE
			                	WHEN (M.hiv_cdc = 'Y' or M.aids_cdc = 'Y') and (N.hiv_cdc = 'Y' AND N.aids_cdc = 'Y') THEN 'BOTH'
			                   WHEN M.hiv_cdc = 'Y' or M.aids_cdc = 'Y' THEN 'UPSTATE'
			                   WHEN N.hiv_cdc = 'Y' or N.aids_cdc = 'Y' THEN 'NYC'
			                END
			                   AS "MEETS_CASE_DEFINITION_IN",
			                CASE
			                   WHEN M.HIV_AIDS_CDC_ELIG = 'Y' and N.HIV_AIDS_CDC_ELIG = 'Y'  THEN 'BOTH'
			                   WHEN M.HIV_AIDS_CDC_ELIG = 'Y' THEN 'UPSTATE'
			                   WHEN N.HIV_AIDS_CDC_ELIG = 'Y' THEN 'NYC'
			                END
			                   AS "MEETS_CDC_ELIG_ID",
			                s.DOCUMENT_ID,
			                s.MED_RECORD_NUMBER,
			                s.FNAME,
			                s.LNAME,
			                s.DOB,
			                s.FILE_TYPE_CD,
			                TO_CHAR (s.PROV_LAST_VISIT_DATE, 'MM/DD/YYYY')
			                   PROV_LAST_VISIT_DATE,
			                TO_CHAR (s.LAST_VIRAL_LOAD_DATE, 'MM/DD/YYYY')
			                   LAST_VIRAL_LOAD_DATE,
			               s.LAST_VIRAL_LOAD_RESULT,
			                TO_CHAR (s.LAST_CD4_DATE, 'MM/DD/YYYY')
			                   LAST_CD4_DATE,
			                s.LAST_CD4_RESULT_COUNT,
			                s.LAST_CD4_RESULT_PERCENT,
			                C.COUNTY_NAME PROV_COUNTY,
			                CASE
			                   WHEN s.flux_MATCH_CD IN ('M', 'C_M', 'P_M', 'M_M', 'N_M') THEN 'MATCH'
			                   WHEN s.flux_MATCH_CD IN ('N', 'C_N', 'P_N', 'M_N', 'N_N') THEN 'NON MATCH'
			                   WHEN s.flux_MATCH_CD IN ('C') THEN 'CLERICAL MATCH'
			                END
			                   AS MATCH_CD,
			                '' QS_MATCH_CD,
			                s.CONFIRMATORY_RESULT_REPORTED,
			                s.SUBMITTING_PROVIDER_PRESENT,
			                s.REVIEW_COMMENT,
			                s.INTERNAL_CONCLUSION,
			                s.CARE_STATUS,
			                con.INTERNAL_CONCLUSION_DESC,
			                care.CONCLUSION_DESC
			           FROM person_doc_association p,
			                NYEHMS_EHARS_STATENO_MAP M,
			                EHARS_PERSON_NYC N,
			                STD_EPRF_OOC_UPLOAD s,
			                CODED_COUNTY C,
			                CODED_PP_INQURIES_INT_CONCL con,
			                CODED_CONCLUSION_SHARED_PROV care
			          WHERE     s.document_id = ?1
			                AND DISSASSOCIATION_DATE IS NULL
			                AND p.stateno = m.NYEHMS_STATENO(+)
			                AND p.stateno = N.NYEHMS_STATENO(+)
			                AND p.document_id = s.document_id
			                AND s.PROV_COUNTY = c.COUNTY_FIPS_CODE(+)
			                AND s.INTERNAL_CONCLUSION = con.INTERNAL_CONCLUSION_ID(+)
			                AND s.CARE_STATUS = care.CONCLUSION_ID(+)
			                AND ROWNUM < 2),
			     HIV_SQL
			     AS (SELECT s.document_id,
			                ROW_NUMBER ()
			                   OVER (PARTITION BY 1 ORDER BY CALC_COLLECTION_DATE DESC,  CASE WHEN TEST_TYPE_CD ='VL' THEN 1 WHEN   TEST_TYPE_CD = 'CD4' THEN 2 WHEN TEST_TYPE_CD = 'RESISTANCE' THEN 3
			                                                               WHEN TEST_TYPE_CD = 'WB' THEN 4 WHEN TEST_TYPE_CD = 'SCREEN' THEN 5 ELSE 6 END
			                                                         asc)
			                   RWNUM,
			                s.LOINC,
			                s.COLLECTION_DATE,
			                s.TEST_RESULT,
			                p2.stateno,
			                d.TEST_TYPE_CD
			           FROM person_doc_Association p2, STD_HIV_ECLRS_DOC s, main_sql M , document_locator D
			          WHERE     p2.stateno = M.NYEHMS_STATENO
			                AND p2.DISSASSOCIATION_DATE IS NULL
			                AND s.document_id = D.document_id
			                AND p2.document_id = s.document_id)
			SELECT M.*,
			       H.TEST_TYPE_CD AS "LAST_HIV_LAB_TYPE",
			       TO_CHAR (H.COLLECTION_DATE, 'MM/DD/YYYY')
			          AS "LAST_HIV_LAB_DATE",
			       H.test_result AS "LAST_HIV_LAB_RESULT",
			       FLOOR (MONTHS_BETWEEN (SYSDATE, H.COLLECTION_DATE))
			          AS "MONTHS_SINCE_LAST_HIV_LAB"
			  FROM main_sql M,
			       (SELECT *
			          FROM HIV_SQL
			         WHERE rwnum = 1) H
			WHERE m.nyehms_stateno = h.stateno(+)
			""";

	/**
	 * The follow-up data of a standardized record: its person's NYS / NYC eHARS state numbers, vital status and case
	 * definition / CDC eligibility, the record's review values and the person's last HIV lab.
	 *
	 * @return the data, or null if the record has no current person
	 */
	public EPRFFollowUpDocVO getEprfDocData(Integer documentId) {
		return statements.one(EPRF_DOC_DATA, Rows.bean(EPRFFollowUpDocVO.class), NativeStatements.number(documentId));
	}

	private static final String FOLLOW_UP_DATA = """
			select c.countyName as PROV_COUNTY, f.inquiryFacilityName as FACILITY_NAME, f.fileType as FILE_TYPE,
			f.fileCategory as FILE_CATEGORY, f.filename as FILENAME,
			format(f.fileEmailDirectorDate as 'MM/dd/yyyy') as FILE_EMAIL_DIRECTOR_DATE,
			format(f.fileClientFeedbackDate as 'MM/dd/yyyy') as FILE_CLIENT_FEEDBACK_DATE
			from EprfProvPortalFileUpload f left join CodedCounty c on f.inquiryCountyFips = c.countyFipsCode
			where f.filename = ?1
			fetch first 1 rows only
			""";

	/** The provider portal file of a follow-up: inquiry county and facility, type, category and its dates as text. */
	public EPRFFollowUpDocVO getFollowUpData(String documentFileName) {
		return jpql.one(FOLLOW_UP_DATA, Rows.bean(EPRFFollowUpDocVO.class), documentFileName);
	}

	/** The number of standardized records of a file in follow-up (status 10) not yet completed. */
	public int getDocumentsNeedToFollowUpCount(String documentFileName) {
		return jpql.one("""
				select count(*) from StdEprfOocUpload s
				where s.currentProcessStatus in (10) and s.uploadFilename = ?1 and s.completeDate is null""",
				Rows.scalar(Integer.class), documentFileName);
	}

	/**
	 * Not used (its only caller is commented out). Sets FILE_MATCH_REVIEW_COMPLTE_DATE (SYSDATE) of every provider portal
	 * file when the record's file has no record in follow-up (status 10) left to complete.
	 * <p>
	 * <b>Kept as before:</b> the former mapper declared this UPDATE as a {@code <select>} with an int result: MyBatis ran
	 * it (PreparedStatement.execute, the rows were changed), found no result set and then failed because null cannot be
	 * returned as an int. So the update runs and the method then always fails.
	 *
	 * @throws InvalidDataAccessApiUsageException always, after the update
	 */
	public int updateFileMatchReviewDate(long documentId) {
		jpql.update("""
				update EprfProvPortalFileUpload f set f.fileMatchReviewCompleteDate = sysdate
				where 0 = (select count(*) from StdEprfOocUpload s where s.currentProcessStatus in (10)
				and s.uploadFilename = (select x.uploadFilename from StdEprfOocUpload x where cast(x.documentId as Long) = ?1)
				and s.completeDate is null)""", documentId);
		throw new InvalidDataAccessApiUsageException("Mapper method 'updateFileMatchReviewDate' attempted to return null from a method"
				+ " with a primitive return type (int).");
	}

	/** The internal conclusions by description: maps with INTERNAL_CONCLUSION_ID (a BigDecimal) and INTERNAL_CONCLUSION_DESC. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getInternalConclusionList() {
		return (List) jpql.list("""
				select c.internalConclusionId as INTERNAL_CONCLUSION_ID, c.internalConclusionDesc as INTERNAL_CONCLUSION_DESC
				from CodedPpInquriesIntConcl c order by c.internalConclusionDesc""", Rows.map());
	}

	/** The care statuses by description: maps with CONCLUSION_ID (a BigDecimal) and CONCLUSION_DESC. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getCareStatusList() {
		return (List) jpql.list("""
				select c.conclusionId as CONCLUSION_ID, c.conclusionDesc as CONCLUSION_DESC
				from CodedConclusionSharedProv c order by c.conclusionDesc""", Rows.map());
	}

	/**
	 * Saves the follow-up review of a standardized record (by DOCUMENT_ID); for ACTION "complet" also COMPLETE_DATE
	 * (SYSDATE) and the person's eHARS / last HIV lab / eligibility values (TRIM(UPPER(...)), the lab date as
	 * TO_DATE(..., 'MM/DD/YYYY'), which fails on a text that is not such a date, as before).
	 *
	 * @return the number of rows changed
	 */
	public int updateEprfFollowUpData(EPRFFollowUpDocVO vo) {
		List<Object> parameters = new ArrayList<>(Arrays.asList(vo.getCONFIRMATORY_RESULT_REPORTED(),
				vo.getSUBMITTING_PROVIDER_PRESENT(), vo.getREVIEW_COMMENT(), decimal(vo.getINTERNAL_CONCLUSION()),
				decimal(vo.getCARE_STATUS())));
		StringBuilder query = new StringBuilder("""
				update StdEprfOocUpload s set s.confirmatoryResultReported = ?1, s.submittingProviderPresent = ?2,
				s.reviewComment = ?3, s.internalConclusion = ?4, s.careStatus = ?5""");
		if ("complet".equals(vo.getACTION())) {
			query.append("""
					, s.completeDate = sysdate,
					s.nysEharsStateno = trim(upper(?6)), s.nycEharsStateno = trim(upper(?7)), s.nycCityNo = trim(upper(?8)),
					s.lastHivLabType = trim(upper(?9)), s.lastHivLabDate = function('TO_DATE' as Timestamp, trim(upper(?10)), 'MM/DD/YYYY'),
					s.lastHivLabResult = trim(upper(?11)), s.monthsSinceLastHivLab = trim(upper(?12)),
					s.nysVitalStatus = trim(upper(?13)), s.nycVitalStatus = trim(upper(?14)),
					s.meetsCaseDefinitionIn = trim(upper(?15)), s.meetsCdcEligId = trim(upper(?16))""");
			for (String value : new String[] {vo.getNYS_EHARS_STATENO(), vo.getNYC_EHARS_STATENO(), vo.getNYC_CITY_NO(),
					vo.getLAST_HIV_LAB_TYPE(), vo.getLAST_HIV_LAB_DATE(), vo.getLAST_HIV_LAB_RESULT(), vo.getMONTHS_SINCE_LAST_HIV_LAB(),
					vo.getNYS_VITAL_STATUS(), vo.getNYC_VITAL_STATUS(), vo.getMEETS_CASE_DEFINITION_IN(), vo.getMEETS_CDC_ELIG_ID()}) {
				parameters.add(value);
			}
		}
		parameters.add(vo.getDOCUMENT_ID());
		query.append(" where s.documentId = ?").append(parameters.size());
		return jpql.update(query.toString(), parameters.toArray());
	}

	private static final String CARE_STATUS_COUNTS = """
			select c.conclusionDesc, c.conclusionId, count(*)
			from StdEprfOocUpload s join CodedConclusionSharedProv c on s.careStatus = c.conclusionId
			where s.uploadFilename = ?1
			group by c.conclusionId, c.conclusionDesc
			""";

	/** The number of standardized records of a file per care status (description, id). */
	public List<FileByConclusionVO> getPatientCareStatusInformation(String documentFileName) {
		return jpql.list(CARE_STATUS_COUNTS, row -> {
			FileByConclusionVO vo = new FileByConclusionVO();
			boolean set = false;
			if (row.get(0) != null) { vo.setCONCLUSION_DESC(row.get(0).toString()); set = true; }
			if (row.get(1) != null) { vo.setCONCLUSION_ID(((Number) row.get(1)).intValue()); set = true; }
			if (row.get(2) != null) { vo.setCOUNT(((Number) row.get(2)).intValue()); set = true; }
			return set ? vo : null;
		}, documentFileName);
	}

	/** Sets FILE_EMAIL_DIRECTOR_DATE (SYSDATE) of a provider portal file. */
	public int updateBureauDirectorEmailDate(String documentFileName) {
		return jpql.update("update EprfProvPortalFileUpload f set f.fileEmailDirectorDate = sysdate where f.filename = ?1",
				documentFileName);
	}

	/**
	 * Saves the client feedback date of a provider portal file (TO_DATE(..., 'MM/DD/YYYY') when it is not empty);
	 * otherwise only sets FILENAME to itself, as before (the row count tells whether the file exists).
	 */
	public int updateEprfFollowUpFileData(EPRFFollowUpDocVO vo) {
		String feedbackDate = vo.getFILE_CLIENT_FEEDBACK_DATE();
		if (feedbackDate != null && !feedbackDate.isEmpty()) {
			return jpql.update("""
					update EprfProvPortalFileUpload f set f.fileClientFeedbackDate = function('TO_DATE' as Timestamp, ?1, 'MM/DD/YYYY'),
					f.filename = ?2 where f.filename = ?3""", feedbackDate, vo.getFILENAME(), vo.getFILENAME());
		}
		return jpql.update("update EprfProvPortalFileUpload f set f.filename = ?1 where f.filename = ?2", vo.getFILENAME(),
				vo.getFILENAME());
	}

	/** Sets FILE_FOLLOW_UP_COMPLETE_DATE (SYSDATE) of a provider portal file. */
	public void updateFileFollowUpCompleteDate(String filename) {
		jpql.update("update EprfProvPortalFileUpload f set f.fileFollowUpCompleteDate = sysdate where f.filename = ?1", filename);
	}

	private static final String PATIENT_INFORMATION = """
			select sum(case when function('MONTHS_BETWEEN' as BigDecimal, sysdate, s.provLastVisitDate) > 24 then 1 else 0 end),
			sum(case when s.personCounty in ('005', '047', '061', '081', '085') then 1 else 0 end)
			from StdEprfOocUpload s
			where s.careStatus = 8 and s.uploadFilename = ?1
			""";

	/**
	 * The out-of-care (care status 8) records of a file: MONTHS (last visit more than 24 months ago) and
	 * NYC_COUNTY_COUNT (in an NYC county), as Integers; null values left out, null if both are null (no records).
	 */
	public Map<String, Integer> getOOCDocumentPatientInformation(String documentFileName) {
		return jpql.one(PATIENT_INFORMATION, row -> {
			Map<String, Integer> map = new HashMap<>();
			if (row.get(0) != null) {
				map.put("MONTHS", ((Number) row.get(0)).intValue());
			}
			if (row.get(1) != null) {
				map.put("NYC_COUNTY_COUNT", ((Number) row.get(1)).intValue());
			}
			return map.isEmpty() ? null : map;
		}, documentFileName);
	}

	private static final String COMPLETE_DOC_DATA = """
			select s.nysEharsStateno as NYS_EHARS_STATENO, s.nycEharsStateno as NYC_EHARS_STATENO, s.nycCityNo as NYC_CITY_NO,
			s.lastHivLabType as LAST_HIV_LAB_TYPE, format(s.lastHivLabDate as 'MM/dd/yyyy') as LAST_HIV_LAB_DATE,
			s.lastHivLabResult as LAST_HIV_LAB_RESULT, s.monthsSinceLastHivLab as MONTHS_SINCE_LAST_HIV_LAB,
			s.nysVitalStatus as NYS_VITAL_STATUS, s.nycVitalStatus as NYC_VITAL_STATUS,
			s.meetsCaseDefinitionIn as MEETS_CASE_DEFINITION_IN, s.meetsCdcEligId as MEETS_CDC_ELIG_ID,
			s.confirmatoryResultReported as CONFIRMATORY_RESULT_REPORTED, s.submittingProviderPresent as SUBMITTING_PROVIDER_PRESENT,
			s.reviewComment as REVIEW_COMMENT, s.internalConclusion as INTERNAL_CONCLUSION, s.careStatus as CARE_STATUS,
			con.internalConclusionDesc as INTERNAL_CONCLUSION_DESC, care.conclusionDesc as CONCLUSION_DESC,
			s.medRecordNumber as MED_RECORD_NUMBER, s.fname as FNAME, s.lname as LNAME, s.dob as DOB,
			format(s.provLastVisitDate as 'MM/dd/yyyy') as PROV_LAST_VISIT_DATE,
			format(s.lastViralLoadDate as 'MM/dd/yyyy') as LAST_VIRAL_LOAD_DATE, s.lastViralLoadResult as LAST_VIRAL_LOAD_RESULT,
			format(s.lastCd4Date as 'MM/dd/yyyy') as LAST_CD4_DATE, s.lastCd4ResultCountText as LAST_CD4_RESULT_COUNT,
			s.lastCd4ResultPercentText as LAST_CD4_RESULT_PERCENT, c.countyName as PROV_COUNTY,
			case when s.fluxMatchCd in ('M', 'C_M', 'P_M', 'M_M', 'N_M') then 'MATCH'
			when s.fluxMatchCd in ('N', 'C_N', 'P_N', 'M_N', 'N_M') then 'NON MATCH'
			when s.fluxMatchCd in ('C') then 'CLERICAL MATCH' end as MATCH_CD,
			pdoc.stateno as NYEHMS_STATENO
			from StdEprfOocUpload s
			left join CodedCounty c on s.provCounty = c.countyFipsCode
			left join CodedPpInquriesIntConcl con on s.internalConclusion = con.internalConclusionId
			left join CodedConclusionSharedProv care on s.careStatus = care.conclusionId
			join PersonDocAssociation pdoc on pdoc.documentId = s.documentId
			where s.currentProcessStatus = 10 and pdoc.dissassociationDate is null and s.uploadFilename = ?1
			""";

	/**
	 * The completed follow-up data of the records of a file in follow-up (status 10) with their current person (the
	 * former QS_MATCH_CD was '', which Oracle reads as NULL, so it stays unset).
	 * <p>
	 * <b>Kept as before:</b> 'N_M' is in both the MATCH and the NON MATCH list, so it is MATCH.
	 */
	public List<EPRFFollowUpDocVO> getCompleteEprfDocDataWithHivInfo(String documentFileName) {
		return jpql.list(COMPLETE_DOC_DATA, Rows.bean(EPRFFollowUpDocVO.class), documentFileName);
	}

	private static final String REVIEW_STATS = """
			select sum(1),
			sum(case when s.completeDate is null and (s.confirmatoryResultReported is not null or s.submittingProviderPresent is not null
			or s.reviewComment is not null or s.internalConclusion is not null or s.careStatus is not null) then 1 else 0 end),
			sum(case when s.completeDate is not null then 1 else 0 end)
			from StdEprfOocUpload s
			where s.currentProcessStatus in (10) and s.uploadFilename = ?1
			""";

	/**
	 * The review progress of a file in follow-up (status 10): TOTAL_RECORDS, TOTAL_SAVED_RECORDS (saved, not completed)
	 * and TOTAL_COMPLETED_RECORDS, as BigDecimals (as JDBC getObject read the Oracle sums); null values left out, null
	 * if all are null (no records).
	 */
	public Map<String, Integer> getOOCDNLTCReviewStatsByFileName(String documentFileName) {
		return jpql.one(REVIEW_STATS, row -> {
			Map<String, Object> map = new HashMap<>();
			String[] labels = {"TOTAL_RECORDS", "TOTAL_SAVED_RECORDS", "TOTAL_COMPLETED_RECORDS"};
			for (int i = 0; i < labels.length; i++) {
				if (row.get(i) != null) {
					map.put(labels[i], new BigDecimal(row.get(i).toString()));
				}
			}
			@SuppressWarnings({"unchecked", "rawtypes"})
			Map<String, Integer> result = map.isEmpty() ? null : (Map) map;
			return result;
		}, documentFileName);
	}

	// ----------------------------------------------------------------------------------------------------------------

	/** OGNL's {@code x == n} for a number or a text of a number. */
	private static boolean numberEquals(Object value, int number) {
		if (value == null) {
			return false;
		}
		try {
			return new BigDecimal(value.toString().trim()).compareTo(BigDecimal.valueOf(number)) == 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static Integer integer(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		return value instanceof Integer number ? number : new BigDecimal(value.toString().trim()).intValueExact();
	}

	/** A number column written from a VO's text (Oracle converted it; '' was NULL). */
	private static BigDecimal decimal(String value) {
		return value == null || value.trim().isEmpty() ? null : new BigDecimal(value.trim());
	}

	private static String text(Object value) {
		return value == null ? null : value.toString();
	}

	/** A file name list for {@code IN (...)}: an empty or missing one failed as {@code IN ()}. */
	private static Collection<?> fileNames(Object value) {
		if (!(value instanceof Collection<?> fileNames) || fileNames.isEmpty()) {
			throw new InvalidDataAccessResourceUsageException("No file names for IN (): " + value);
		}
		return fileNames;
	}
}
