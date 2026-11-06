package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocPSMessageVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocReportVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ClusterIdReportDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClusterTemplateVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.uploads.ClustereHARSImportFlagVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUploadCluster;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawClusterDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.StdClusterDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedClustMolRelatednessRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedClustMolTimeframeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedClusterIdentificationMethodRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedEharsEthnicityRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.cluster.CodedTransmissionCategoryRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedEharsRaceRepository;
import lombok.RequiredArgsConstructor;

/**
 * The cluster documents: the cluster upload (uploaded clusters, files, space-time criteria, raw and standardized
 * records), the cluster document screen, the cluster reports and the manual association of a state number with a
 * cluster (formerly a MyBatis mapper with the same name and methods).
 * <ul>
 * <li>Code lists: Spring Data repositories with projections.</li>
 * <li>Most reads, the inserts and the updates: JPQL over the entities RawClusterDoc, StdClusterDoc,
 * NyehmsUploadCluster, NyehmsUploadClusterFiles, BhaeTsClusterCriteria, BhaeTsClusterManual, DocumentLocator,
 * PersonDocAssociation, DocumentPersonAttr, AllStdSentRecvMsgVw, NyehmsUsers, RawAcrfRidrInfo, StdAcrfDoc,
 * NyehmsEharsStatenoMap and EharsPersonNyc. The dates the former SQL set with SYSDATE are the database's SYSDATE
 * ({@code sysdate} in JPQL, {@code coalesce(?, SYSDATE)} on inserted entities).</li>
 * <li>Native SQL, unchanged (values bound), where JPQL cannot express the statement: see each statement's comment.</li>
 * <li>The procedures pr_get_cluster_data (with its cursor) and process_timespace_clusters:
 * {@link NativeStatements#call}.</li>
 * </ul>
 * <b>Kept as before:</b> {@link #getClusterReportsByTypeAndId} had no mapped statement and still fails;
 * {@link #getClusterPkNumber} fails when the cluster does not exist (null cannot be returned as an int).
 */
@Repository
@RequiredArgsConstructor
public class ClusterDocumentDAO {

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final CodedClustMolTimeframeRepository timeframes;
	private final CodedClustMolRelatednessRepository relatednessValues;
	private final CodedClusterIdentificationMethodRepository identificationMethods;
	private final CodedTransmissionCategoryRepository transmissionCategories;
	private final CodedEharsRaceRepository eharsRaces;
	private final CodedEharsEthnicityRepository eharsEthnicities;

	// native: CTEs with ROW_NUMBER() OVER (...), LISTAGG ... WITHIN GROUP over XMLTABLE((...)) with
	// (column_value).getstringval(), NVL2 / RPAD / SUBSTR of the eHARS diagnosis date texts and the PL/SQL functions
	// PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO, fn_get_ehars_race and fn_get_ehars_ethnicity
	private static final String CLUSTER_DOC = """
				  WITH nys_ehars AS
				(
				            SELECT a.*, ROW_NUMBER() OVER (PARTITION BY a.nyehms_stateno ORDER BY a.ehars_stateno) nysrn
				              FROM nyehms_ehars_stateno_map a
				), nyc_ehars AS
				(
				            SELECT a.*, ROW_NUMBER() OVER (PARTITION BY a.nyehms_stateno ORDER BY a.cityno) nycrn
				              FROM ehars_person_nyc a
				), data AS
				(
				SELECT s_cluster.DOCUMENT_ID,
					s_cluster.DEMO_DOCUMENT_ID,
				   u_cluster.CLUSTER_ID,
				   u_cluster.CLUSTER_TYPE,
				   CASE
				               WHEN u_cluster.CLUSTER_TYPE = 'BHAE_TS' THEN to_char(u_cluster.CREATE_DATE, 'MM/DD/YYYY')
				               ELSE u_cluster.CLUSTER_IDENTIFY_DATE
				   END CLUSTER_DATE,
				   u_cluster.RELATEDNESS,
				   mt.TIMEFRAME_DESC TIMEFRAME,
				   (SELECT REFERENCE_FILE_NAME FROM NYEHMS_UPLOAD_CLUSTER_FILES WHERE CLUSTER_ID_PK = u_cluster.CLUSTER_ID_PK and UPLOAD_TYPE = 'N') REFERENCE_FILE_NAME,
				   ts_cluster.DX_START_DATE,
				   ts_cluster.DX_END_DATE,
				   to_char(ts_cluster.CLUST_END_DATE, 'MM/DD/YYYY') CLUSTER_END_DATE,
				   s_cluster.INACTIVE_FLAG, to_char(s_cluster.INACTIVE_DATE, 'MM/DD/YYYY') INACTIVE_DATE,
				  (SELECT LISTAGG(c.county_name,', ') WITHIN GROUP (ORDER BY 1)
				  FROM xmltable(('"'||Replace(ts_cluster.cluster_counties, '|', '","')||'"')), coded_county c
				  WHERE (column_value).getstringval() = c.county_fips_code) CLUSTER_COUNTIES,
				   CASE
				               WHEN u_cluster.CLUSTER_TYPE = 'BHAE_TS' THEN ts_cluster.CLUSTER_DESC
				               ELSE (SELECT DESCRIPTION FROM NYEHMS_UPLOAD_CLUSTER_FILES WHERE CLUSTER_ID_PK = u_cluster.CLUSTER_ID_PK
				                            and UPLOAD_DATE = (SELECT max(UPLOAD_DATE) FROM NYEHMS_UPLOAD_CLUSTER_FILES WHERE CLUSTER_ID_PK = u_cluster.CLUSTER_ID_PK))
				   END CLUSTER_DESC,
				   s_cluster.EHARS_STATENO_AT_UPLOAD,
				   s_cluster.NYEHMS_STATENO_AT_UPLOAD,
				   s_cluster.FIRST_NAME,
				   s_cluster.MIDDLE_NAME,
				   s_cluster.LAST_NAME,
				   to_char(s_cluster.DOB, 'MM/DD/YYYY') DOB,
				   to_char(s_cluster.CREATE_DATE, 'MM/DD/YYYY') CREATE_DATE,
				   nvl(PDOC.STATENO, PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO( s_cluster.NYEHMS_STATENO_AT_UPLOAD)) STATENO,
				   PDOC.DISCARD_STATUS,
				   D.PARENT_DOCUMENT_ID,
				   r_cluster.UPLOAD_NYEHMS_STATENO,
				   u.LEGACY_UID ADDED_BY_USER
				           ,c1.IDENTIFICATION_METHOD_DESC IDENTIFICATION_METHOD
				            ,s_cluster.IDENTIFICATION_DATE
				            ,s_cluster.EHARS_IMPORT_DATE
				            , ROW_NUMBER() OVER (PARTITION BY u_cluster.cluster_id ORDER BY ts_cluster.clust_end_date DESC nulls first) rn

				FROM RAW_CLUSTER_DOC r_cluster
				            INNER JOIN STD_CLUSTER_DOC s_cluster
				            ON (s_cluster.DOCUMENT_ID = r_cluster.DOCUMENT_ID and s_cluster.DOCUMENT_ID = ?1)
				            INNER JOIN NYEHMS_UPLOAD_CLUSTER u_cluster
				            ON (u_cluster.CLUSTER_ID_PK = r_cluster.CLUSTER_ID_PK)
				            LEFT OUTER JOIN CODED_CLUST_MOL_TIMEFRAME mt
				            ON (u_cluster.TIMEFRAME_CODE = mt.TIMEFRAME_CODE)
				            LEFT OUTER JOIN BHAE_TS_CLUSTER_CRITERIA ts_cluster
				            ON (ts_cluster.CLUSTER_ID_PK = r_cluster.CLUSTER_ID_PK )
				            LEFT OUTER JOIN CODED_CLUSTER_IDENTIFICATION_METHOD c1
				            ON (s_cluster.identification_method_code = c1.identification_method_code)
				            LEFT OUTER JOIN NYEHMS_USERS u
				            ON (s_cluster.CREATE_UID =  u.WORKER_ID )
				            INNER JOIN document_locator D
				            ON (s_cluster.DOCUMENT_ID = D.document_id)
				            LEFT OUTER JOIN person_doc_association PDOC
				            ON (s_cluster.document_id = PDOC.document_id
				                AND PDOC.dissassociation_date IS NULL)
				    )
				    SELECT a.*,
				            nyehms_ehars_map.NYEHMS_STATENO CURRENT_NYEHMS_STATENO,
				           nyehms_ehars_map.RSH_COUNTY_FIPS,
				           nyehms_ehars_map.RSH_STATE_CD,
				           nyehms_ehars_map.CUR_COUNTY_FIPS,
				           nyehms_ehars_map.CUR_STATE_CD,
				           nvl2(nyehms_ehars_map.HIV_DX_DT, substr(rpad(nyehms_ehars_map.HIV_DX_DT,8,'.'),5,2)||'/'||substr(rpad(replace(nyehms_ehars_map.HIV_DX_DT,'.',''),8,'.'),7,2)||'/'||substr(rpad(replace(nyehms_ehars_map.HIV_DX_DT,'.',''),8,'.'),1,4), null) HIV_DX_DT,
				           nvl2(nyehms_ehars_map.AIDS_DX_DT, substr(rpad( nyehms_ehars_map.AIDS_DX_DT,8,'.'),5,2)||'/'||substr(rpad(replace( nyehms_ehars_map.AIDS_DX_DT,'.',''),8,'.'),7,2)||'/'||substr(rpad(replace( nyehms_ehars_map.AIDS_DX_DT,'.',''),8,'.'),1,4), null) AIDS_DX_DT,
				           nyehms_ehars_map.HIV_AIDS_CDC_ELIG,
				           nyehms_ehars_map.STATUS_FLAG,
				           CASE
				                        WHEN nyehms_ehars_map.race1 IS NOT NULL OR nyehms_ehars_map.race2 IS NOT NULL OR nyehms_ehars_map.race3 IS NOT NULL OR nyehms_ehars_map.race4 IS NOT NULL OR nyehms_ehars_map.race5 IS NOT NULL
				                        THEN RTrim(LTrim(fn_get_ehars_race(nyehms_ehars_map.race1)||','||fn_get_ehars_race(nyehms_ehars_map.race2)||','||fn_get_ehars_race(nyehms_ehars_map.race3)||','||fn_get_ehars_race(nyehms_ehars_map.race4)||','||fn_get_ehars_race(nyehms_ehars_map.race5), ','), ',')
				                        ELSE NULL
				            END race,
				            CASE
				                        WHEN nyehms_ehars_map.ethnicity1 IS NOT NULL
				                        THEN fn_get_ehars_ethnicity(nyehms_ehars_map.ethnicity1)
				                        ELSE NULL
				            END ethnicity,
				            ehars_nyc.cityno CITYNO,
				           ehars_nyc.RSH_COUNTY_FIPS NYC_RSH_COUNTY_FIPS,
				           ehars_nyc.RSH_STATE_CD  NYC_RSH_STATE_CD,
				           ehars_nyc.CUR_COUNTY_FIPS NYC_CUR_COUNTY_FIPS,
				           ehars_nyc.CUR_STATE_CD NYC_CUR_STATE_CD,
				           nvl2(ehars_nyc.hiv_dx_dt, substr(rpad(ehars_nyc.HIV_DX_DT,8,'.'),5,2)||'/'||substr(rpad(replace(ehars_nyc.HIV_DX_DT,'.',''),8,'.'),7,2)||'/'||substr(rpad(replace(ehars_nyc.HIV_DX_DT,'.',''),8,'.'),1,4), null) NYC_HIV_DX_DT,
				           nvl2(ehars_nyc.AIDS_DX_DT, substr(rpad( ehars_nyc.AIDS_DX_DT,8,'.'),5,2)||'/'||substr(rpad(replace( ehars_nyc.AIDS_DX_DT,'.',''),8,'.'),7,2)||'/'||substr(rpad(replace( ehars_nyc.AIDS_DX_DT,'.',''),8,'.'),1,4), null) NYC_AIDS_DX_DT,
				           ehars_nyc.HIV_AIDS_CDC_ELIG NYC_HIV_AIDS_CDC_ELIG,
				           ehars_nyc.STATUS_FLAG NYC_STATUS_FLAG
				           ,CASE
				                                WHEN ehars_nyc.race1 IS NOT NULL OR ehars_nyc.race2 IS NOT NULL OR ehars_nyc.race3 IS NOT NULL OR ehars_nyc.race4 IS NOT NULL OR ehars_nyc.race5 IS NOT NULL
				                                THEN RTrim(LTrim(fn_get_ehars_race(ehars_nyc.race1)||','||fn_get_ehars_race(ehars_nyc.race2)||','||fn_get_ehars_race(ehars_nyc.race3)||','||fn_get_ehars_race(ehars_nyc.race4)||','||fn_get_ehars_race(ehars_nyc.race5), ','), ',')
				                                ELSE NULL
				                    END nyc_race
				           ,CASE
				                        WHEN ehars_nyc.ethnicity1 IS NOT NULL
				                        THEN fn_get_ehars_ethnicity(ehars_nyc.ethnicity1)
				                        ELSE NULL
				            END nyc_ethnicity
				      FROM data a
				      LEFT OUTER JOIN nys_ehars nyehms_ehars_map
				      ON (a.stateno =  nyehms_ehars_map.nyehms_stateno and nysrn = 1 and  nyehms_ehars_map.nyehms_stateno != '-1')
				      LEFT OUTER JOIN nyc_ehars ehars_nyc
				      ON (a.stateno = ehars_nyc.nyehms_stateno and nycrn = 1 and ehars_nyc.nyehms_stateno != '-1')
				     WHERE rn = 1
			""";

	/**
	 * The cluster document screen: the cluster, its upload and criteria, the record's names and state numbers and the
	 * eHARS (NYS and NYC) data of its current state number.
	 *
	 * @param inputData documentId
	 * @return the document, or null if there is none
	 */
	public ClusterDocVO getClusterDoc(Map<Object, Object> inputData) throws DataAccessException {
		return statements.one(CLUSTER_DOC, Rows.bean(ClusterDocVO.class), inputData.get("documentId"));
	}

	/** The clusters of a type that have standardized records: JSONObjects with CLUSTER_ID, by CLUSTER_ID. */
	public List<JSONObject> getClusterIdsByType(String clusterType) {
		return jpql.list("""
				select distinct up.clusterId as CLUSTER_ID from NyehmsUploadCluster up, StdClusterDoc std
				where up.clusterType = ?1 and std.clusterIdPk = up.clusterIdPk order by up.clusterId""", Rows.json(), clusterType);
	}

	/**
	 * The uploaded clusters of a type (a space-time cluster only if it has criteria): JSONObjects with CLUSTER_ID, by
	 * CLUSTER_ID.
	 *
	 * @param existingClusterType clusterType
	 */
	public List<JSONObject> getExistingClusterIdsByType(Map<String, String> existingClusterType) {
		Object clusterType = existingClusterType.get("clusterType");
		if ("BHAE_TS".equals(clusterType)) {
			return jpql.list("""
					select distinct up.clusterId as CLUSTER_ID from NyehmsUploadCluster up, BhaeTsClusterCriteria ts
					where up.clusterType = ?1 and up.clusterIdPk = ts.clusterIdPk order by up.clusterId""", Rows.json(), clusterType);
		}
		return jpql.list("select distinct up.clusterId as CLUSTER_ID from NyehmsUploadCluster up where up.clusterType = ?1 order by up.clusterId",
				Rows.json(), clusterType);
	}

	// native: ROW_NUMBER() OVER (...) over Oracle (+) outer joins, and the JSONObject values typed as JDBC getObject
	// types them for columns whose types are not known (DX_START_DATE, DX_END_DATE, AGE_FROM, AGE_TO, ...)
	private static final String CLUSTER_DATA = """
				SELECT cluster_id, last_upload_date, cluster_identify_date, relatedness, timeframe, reference_file_name, description,cluster_counties, dx_start_date, dx_end_date, clust_end_date
						, trans_risk, birth_sex, race, ethnicity, age_from, age_to
				  FROM (
				SELECT CASE
				          WHEN uc.cluster_type = 'BHAE_TS' THEN uc.create_date
				          ELSE ucf.upload_date
				       END last_upload_date
				       , uc.cluster_identify_date
				       , uc.relatedness
				       , timeframe_code timeframe
				       , ucf.reference_file_name
				       , CASE
				            WHEN uc.cluster_type = 'BHAE_TS' THEN b.cluster_desc
				            ELSE ucf.description
				         END description, uc.cluster_id
				       , b.cluster_counties, b.dx_start_date, b.dx_end_date, to_char( b.clust_end_date, 'MM/DD/YYYY') clust_end_date
				       , b.trans_risk, b.birth_sex, b.race, b.ethnicity, b.age_from, b.age_to
				       , ROW_NUMBER() OVER (PARTITION BY uc.cluster_id ORDER BY b.clust_end_date DESC nulls first) rn
				  FROM nyehms_upload_cluster uc, nyehms_upload_cluster_files ucf, bhae_ts_cluster_criteria b
				WHERE uc.cluster_id_pk = ucf.cluster_id_pk(+)
				   AND uc.cluster_id_pk = b.cluster_id_pk(+)
				   AND uc.cluster_id = ?1)
				WHERE rn = 1
			""";

	/** A cluster with its latest file and criteria (a JSONObject with the upper-case column names), or null. */
	public JSONObject getClusterData(String clusterId) {
		return statements.one(CLUSTER_DATA, Rows.json(), NativeStatements.text(clusterId));
	}

	/**
	 * Had no mapped statement in the former MyBatis mapper (MyBatis failed with "Invalid bound statement"); it still
	 * fails (kept as it was). Its only caller is never called.
	 */
	public List<ClusterDocReportVO> getClusterReportsByTypeAndId(Map<String, String> ReportInputData) throws DataAccessException {
		throw new InvalidDataAccessApiUsageException(
				"Invalid bound statement (not found): " + ClusterDocumentDAO.class.getName() + ".getClusterReportsByTypeAndId");
	}

	/**
	 * The cluster report: calls pr_get_cluster_data and puts its rows (ClusterIdReportDataVO, mapped as the former
	 * result map clusterIdReport) into the map as p_clust_data.
	 *
	 * @param map p_cluster_id
	 */
	public void getClusterIdReport(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("pr_get_cluster_data", in(map.get("p_cluster_id"), String.class), cursor(CLUSTER_ID_REPORT_ROWS));
		map.put("p_clust_data", results.get(1));
	}

	/** The type of the cluster of a standardized cluster record, or null. */
	public String getDocumentClusterType(int documentId) throws DataAccessException {
		return jpql.one("""
				select upload.clusterType from StdClusterDoc std, NyehmsUploadCluster upload
				where std.documentId = ?1 and std.clusterIdPk = upload.clusterIdPk""", Rows.scalar(String.class), (long) documentId);
	}

	/** 'Y' if the state number has an associated, active-association HIV resistance lab document, otherwise null. */
	public String isResistanceRecord(String stateno) throws DataAccessException {
		return jpql.one("""
				select distinct 'Y' from PersonDocAssociation a where a.stateno = ?1 and a.dissassociationDate is null
				and exists (select 1 from DocumentLocator b where b.documentId = a.documentId and b.documentTypeCode = 'HIV_LAB'
				and b.testTypeCd = 'RESISTANCE')""", Rows.scalar(String.class), stateno);
	}

	/**
	 * Inserts an uploaded cluster record.
	 *
	 * @param clusterDataDetails documentId, uploadNyehmsStateno, nyehmsStateno, identificationMethodCode,
	 *        identificationDate (text), currrentProcessStatus, userId, clusterIdPk, demoDocumentId
	 * @return 1
	 */
	public int insertDataIntoClusterRaw(Map<String, Object> clusterDataDetails) {
		return jpql.insert(RawClusterDoc.builder()
				.documentId(longValue(clusterDataDetails.get("documentId")))
				.uploadNyehmsStateno(text(clusterDataDetails.get("uploadNyehmsStateno")))
				.uploadCurrentNyehmsStateno(text(clusterDataDetails.get("nyehmsStateno")))
				.identificationMethodCode(text(clusterDataDetails.get("identificationMethodCode")))
				.identificationDate(text(clusterDataDetails.get("identificationDate")))
				.currentProcessStatus(intValue(clusterDataDetails.get("currrentProcessStatus")))
				.createUid(text(clusterDataDetails.get("userId")))
				.clusterIdPk(intValue(clusterDataDetails.get("clusterIdPk")))
				.demoDocumentId(longValue(clusterDataDetails.get("demoDocumentId")))
				.build());
	}

	/**
	 * Inserts a standardized cluster record; DOB (MM/DD/YYYY text) is converted by FN_TO_DATE as before.
	 *
	 * @param clusterDataDetails documentId, nyehmsStateno, identificationMethodCode, identificationDate, firstName,
	 *        middleName, lastName, dob, currrentProcessStatus, demo_data_src, userId, clusterIdPk, eharsStateno, cityno,
	 *        demoDocumentId
	 * @return 1
	 */
	public int insertDataIntoClusterStd(Map<String, Object> clusterDataDetails) {
		return jpql.insert(StdClusterDoc.builder()
				.documentId(longValue(clusterDataDetails.get("documentId")))
				.nyehmsStatenoAtUpload(text(clusterDataDetails.get("nyehmsStateno")))
				.identificationMethodCode(text(clusterDataDetails.get("identificationMethodCode")))
				.identificationDate(text(clusterDataDetails.get("identificationDate")))
				.firstName(text(clusterDataDetails.get("firstName")))
				.middleName(text(clusterDataDetails.get("middleName")))
				.lastName(text(clusterDataDetails.get("lastName")))
				.dob(text(clusterDataDetails.get("dob")))
				.currentProcessStatus(intValue(clusterDataDetails.get("currrentProcessStatus")))
				.demoDataSource(text(clusterDataDetails.get("demo_data_src")))
				.createUid(text(clusterDataDetails.get("userId")))
				.clusterIdPk(intValue(clusterDataDetails.get("clusterIdPk")))
				.eharsStateno(text(clusterDataDetails.get("eharsStateno")))
				.cityno(text(clusterDataDetails.get("cityno")))
				.demoDocumentId(longValue(clusterDataDetails.get("demoDocumentId")))
				.build());
	}

	/**
	 * Inserts an uploaded cluster (CLUSTER_ID_PK is filled by the database).
	 *
	 * @param clusterDataDetails clusterId, clusterType, relatedness, moleTimeFrame, clusterIdentifyDate (text)
	 * @return 1
	 */
	public int insertNyehmsUploadCluster(Map<String, Object> clusterDataDetails) {
		return jpql.insert(NyehmsUploadCluster.builder()
				.clusterId(text(clusterDataDetails.get("clusterId")))
				.clusterType(text(clusterDataDetails.get("clusterType")))
				.relatedness(text(clusterDataDetails.get("relatedness")))
				.timeframeCode(text(clusterDataDetails.get("moleTimeFrame")))
				.clusterIdentifyDate(text(clusterDataDetails.get("clusterIdentifyDate")))
				.build());
	}

	/**
	 * Inserts an uploaded cluster file (UPLOAD_DATE = SYSDATE).
	 *
	 * @param clusterDataDetails clusterIdPk, clusterFileName, workerId, uploadType, description
	 * @return 1
	 */
	public int insertNyehmsUploadClusterFiles(Map<String, Object> clusterDataDetails) {
		return jpql.update("""
				insert into NyehmsUploadClusterFiles (clusterIdPk, referenceFileName, uploadUid, uploadDate, uploadType, description)
				values (?1, ?2, ?3, sysdate, ?4, ?5)""",
				intValue(clusterDataDetails.get("clusterIdPk")), text(clusterDataDetails.get("clusterFileName")),
				longValue(clusterDataDetails.get("workerId")), text(clusterDataDetails.get("uploadType")),
				text(clusterDataDetails.get("description")));
	}

	/** The PS messages of a state number whose trigger documents are active (dates as MM/DD/YYYY). */
	public List<ClusterDocPSMessageVO> getClusterPSMessageInfo(String stateno) throws DataAccessException {
		return jpql.list("""
				select coalesce(d.testTypeCd, d.documentTypeCode) as document_type, v.documentId as DOCUMENT_ID,
				v.triggerDocId as TRIGGER_DOC_ID, format(v.messageEnteredDate as 'MM/dd/yyyy') as SEND_DATE,
				format(v.returnMessageEnteredDate as 'MM/dd/yyyy') as RETURN_DATE,
				coalesce(v.latestDispositionDesc, v.returnDispositionDesc) as RECENT_RETURN_DISPO
				from AllStdSentRecvMsgVw v, DocumentLocator d
				where v.currentStateno = ?1 and v.triggerDocId = d.documentId and d.isActive = 'Y'""",
				Rows.bean(ClusterDocPSMessageVO.class), stateno);
	}

	/**
	 * The key of an uploaded cluster.
	 *
	 * @throws InvalidDataAccessApiUsageException if there is no such cluster (as before: null cannot be returned as an
	 *         int)
	 */
	public int getClusterPkNumber(String clusterId) {
		Integer clusterIdPk = jpql.one("select c.clusterIdPk from NyehmsUploadCluster c where c.clusterId = ?1", Rows.scalar(Integer.class), clusterId);
		if (clusterIdPk == null) {
			throw new InvalidDataAccessApiUsageException("Mapper method 'getClusterPkNumber' attempted to return null from a method"
					+ " with a primitive return type (int).");
		}
		return clusterIdPk;
	}

	/** The molecular cluster relatedness values (no sort, as before). */
	public List<String> getRelatednessData() {
		return relatednessValues.findAllBy().stream().map(CodedClustMolRelatednessRepository.Relatedness::relatedness).collect(toList());
	}

	/** The molecular cluster time frames by code: maps with code, value (a null value left out). */
	public List<Map<String, String>> getMoleTimeFrameData() throws DataAccessException {
		return timeframes.findAllByOrderByTimeframeCode().stream().map(t -> textMap("code", t.timeframeCode(), "value", t.timeframeDesc()))
				.collect(toList());
	}

	/**
	 * Inserts the criteria of a space-time cluster (CRITERIA_CREATE_DATE = SYSDATE).
	 *
	 * @param clusterDataDetails clusterIdPk, clusterCounties, spaceTimeDxStartDate, spaceTimeDxEndDate, description,
	 *        workerId, transmissionRisk, birthSex, race, ethnicity, ageRangeFrom, ageRangeTo
	 * @return 1
	 */
	public int insertNyehmsTimeSpaceCriteria(Map<String, Object> clusterDataDetails) {
		return jpql.update("""
				insert into BhaeTsClusterCriteria (clusterIdPk, clusterCounties, dxStartDate, dxEndDate, clusterDesc, criteriaCreateUid,
				criteriaCreateDate, transRisk, birthSex, race, ethnicity, ageFrom, ageTo)
				values (?1, ?2, ?3, ?4, ?5, ?6, sysdate, ?7, ?8, ?9, ?10, ?11, ?12)""",
				intValue(clusterDataDetails.get("clusterIdPk")), text(clusterDataDetails.get("clusterCounties")),
				text(clusterDataDetails.get("spaceTimeDxStartDate")), text(clusterDataDetails.get("spaceTimeDxEndDate")),
				text(clusterDataDetails.get("description")), longValue(clusterDataDetails.get("workerId")),
				text(clusterDataDetails.get("transmissionRisk")), text(clusterDataDetails.get("birthSex")),
				text(clusterDataDetails.get("race")), text(clusterDataDetails.get("ethnicity")),
				text(clusterDataDetails.get("ageRangeFrom")), text(clusterDataDetails.get("ageRangeTo")));
	}

	/**
	 * Ends the open criteria of a space-time cluster (CLUST_END_DATE = SYSDATE); unless tsUpdateRetire is 1 also sets
	 * the cluster description.
	 *
	 * @param clusterDataDetails tsUpdateRetire (1 or 2), workerId, clusterIdPk, description
	 * @return the number of criteria rows ended
	 */
	public int retireNyehmsTimeSpaceCluster(Map<String, Object> clusterDataDetails) {
		if (ognlEqualsNumber(clusterDataDetails.get("tsUpdateRetire"), 1)) {
			return jpql.update("""
					update BhaeTsClusterCriteria c set c.clustEndDate = sysdate, c.clustEndUid = ?1
					where c.clusterIdPk = ?2 and c.clustEndDate is null""",
					longValue(clusterDataDetails.get("workerId")), intValue(clusterDataDetails.get("clusterIdPk")));
		}
		return jpql.update("""
				update BhaeTsClusterCriteria c set c.clustEndDate = sysdate, c.clusterDesc = ?1, c.clustEndUid = ?2
				where c.clusterIdPk = ?3 and c.clustEndDate is null""",
				text(clusterDataDetails.get("description")), longValue(clusterDataDetails.get("workerId")),
				intValue(clusterDataDetails.get("clusterIdPk")));
	}

	/** Runs the space-time cluster process (procedure process_timespace_clusters) for a cluster. */
	public void bhaeTSCriteriaProcess(String clusterIdPk) throws DataAccessException {
		statements.call("process_timespace_clusters", in(clusterIdPk, String.class));
	}

	/** The cluster identification methods: maps with IDENTIFICATION_METHOD_CODE, IDENTIFICATION_METHOD_DESC (no sort). */
	public List<Map<String, String>> getClusterIDentificationMethod() {
		return identificationMethods.findAllBy().stream().map(m -> textMap("IDENTIFICATION_METHOD_CODE", m.identificationMethodCode(),
				"IDENTIFICATION_METHOD_DESC", m.identificationMethodDesc())).collect(toList());
	}

	/** The distinct state numbers, identification methods and dates already uploaded for a cluster. */
	public List<ClusterTemplateVO> getAlreadyUploadedClusterData(int clusterIdPk) throws DataAccessException {
		return jpql.list("""
				select distinct r.uploadNyehmsStateno as nyehmsStateno, r.identificationMethodCode as identificationMethod,
				r.identificationDate as identificationDate from RawClusterDoc r where r.clusterIdPk = ?1""",
				Rows.bean(ClusterTemplateVO.class), clusterIdPk);
	}

	/**
	 * Sets the eHARS import flag of a cluster that does not have it yet (EHARS_IMPORT_FLAG_SET_DATE = SYSDATE).
	 *
	 * @param eHARSImportFlagMap workerId, clusterIdPk
	 * @return the number of clusters changed
	 */
	public int updateClustereHARSImportFlag(Map<String, Object> eHARSImportFlagMap) {
		return jpql.update("""
				update NyehmsUploadCluster c set c.eharsImportFlag = 'Y', c.eharsImportFlagSetId = ?1, c.eharsImportFlagSetDate = sysdate
				where c.clusterIdPk = ?2 and c.eharsImportFlag is null""",
				longValue(eHARSImportFlagMap.get("workerId")), intValue(eHARSImportFlagMap.get("clusterIdPk")));
	}

	/** All uploaded clusters with their type, latest description and eHARS import flag, by key. */
	public List<ClustereHARSImportFlagVO> geteHARSImportFlagData() throws DataAccessException {
		return jpql.list("""
				select uc.clusterIdPk as clusterIdPk, uc.clusterId as clusterName,
				case when uc.clusterType = 'CDC_M' then 'CDC Molecular' when uc.clusterType = 'BHAE_M' then 'BHAE Molecular'
				else 'BHAE Space - Time' end as clusterType,
				case when uc.clusterType = 'BHAE_TS' then
				(select c.clusterDesc from BhaeTsClusterCriteria c where c.clusterIdPk = uc.clusterIdPk
				and c.criteriaCreateDate = (select max(c2.criteriaCreateDate) from BhaeTsClusterCriteria c2 where c2.clusterIdPk = uc.clusterIdPk))
				else (select f.description from NyehmsUploadClusterFiles f where f.clusterIdPk = uc.clusterIdPk
				and f.uploadDate = (select max(f2.uploadDate) from NyehmsUploadClusterFiles f2 where f2.clusterIdPk = uc.clusterIdPk))
				end as clusterDesc,
				format(uc.createDate as 'MM/dd/yyyy') as createDate, uc.eharsImportFlag as eHARSImportFlag,
				nu.firstName || ' ' || nu.middleName || ' ' || nu.lastName as flagSetBy,
				format(uc.eharsImportFlagSetDate as 'MM/dd/yyyy') as flagSetDate
				from NyehmsUploadCluster uc left join NyehmsUsers nu on uc.eharsImportFlagSetId = nu.workerId
				order by uc.clusterIdPk""", Rows.bean(ClustereHARSImportFlagVO.class));
	}

	// native: LISTAGG ... WITHIN GROUP over XMLTABLE((...)) with (column_value).getstringval() (three code lists),
	// ROW_NUMBER() OVER (...) over Oracle (+) outer joins, and the JSONObject values typed as JDBC getObject types them
	// for columns whose types are not known (DX_START_DATE, AGE_FROM, ...)
	private static final String CLUSTER_DISPLAY_INFO = """

			    WITH data AS
			        (    SELECT uc.cluster_id_pk,
			             ts.dx_start_date,
			             ts.dx_end_date,
			               CASE
			                  WHEN uc.cluster_type = 'BHAE_TS' THEN ts.cluster_desc
			                  ELSE ucf.description
			               END cluster_desc,
			               CASE
			                  WHEN uc.cluster_type = 'BHAE_TS' THEN ts.criteria_create_date
			                  ELSE ucf.upload_date
			               END latest_date,
			               to_char(uc.create_date, 'MM/DD/YYYY') create_date,
			               to_char(ts.clust_end_date, 'MM/DD/YYYY') clust_end_date,
			                CASE
			                    WHEN uc.cluster_type = 'BHAE_TS' and ts.clust_end_date is null then 'Yes'
			                    WHEN uc.cluster_type = 'BHAE_TS' and ts.clust_end_date is not null then 'No'
			                    ELSE ''
			                END cluster_active,
			        (SELECT LISTAGG(c.county_name,', ') WITHIN GROUP (ORDER BY 1)
			              FROM xmltable(('"'||Replace(ts.cluster_counties, '|', '","')||'"')), coded_county c
			              WHERE (column_value).getstringval() = c.county_fips_code) cluster_counties,
			        (SELECT LISTAGG(c.categ_desc_short,', ') WITHIN GROUP (ORDER BY 1)
			              FROM xmltable(('"'||Replace(ts.trans_risk, '|', '","')||'"')), coded_transmission_category  c
			              WHERE (column_value).getstringval() = c.category_code) trans_risk,
			        (SELECT LISTAGG(c.ehars_race_desc,', ') WITHIN GROUP (ORDER BY 1)
			              FROM xmltable(('"'||Replace(ts.race, '|', '","')||'"')), coded_ehars_race c
			              WHERE (column_value).getstringval() = c.ehars_race_code ) race,
			              s.sex_description, e.ehars_eth_desc, ts.age_from, ts.age_to
			              ,  CASE
			                    WHEN uc.ehars_import_flag = 'Y' then 'Yes'
			                    ELSE 'No'
			                END ehars_import_flag
			              ,to_char(uc.ehars_import_flag_set_date , 'MM/DD/YYYY') ehars_import_flag_set_date
			               ,ROW_NUMBER() OVER (PARTITION BY uc.cluster_id ORDER BY
			              CASE
			                  WHEN uc.cluster_type = 'BHAE_TS' THEN ts.criteria_create_date
			                  ELSE ucf.upload_date
			               END DESC nulls first) rn
			            FROM bhae_ts_cluster_criteria ts, nyehms_upload_cluster uc, nyehms_upload_cluster_files ucf, CODED_SEX_AT_BIRTH s, CODED_EHARS_ETHNICITY  e
			            WHERE uc.cluster_id = ?1
			                AND uc.cluster_id_pk = ts.cluster_id_pk(+)
			                AND uc.cluster_id_pk = ucf.cluster_id_pk(+)
			                AND ts.birth_sex = s.sex_code (+)
			                AND ts.ethnicity = e.ehars_eth_code (+)
			            ORDER BY ts.criteria_create_date desc
			         )
			     SELECT * FROM data where rn =1

			""";

	/** The display data of a cluster (a JSONObject with the upper-case column names), or null. */
	public JSONObject getClusterDisplayInfo(String clusterId) {
		return statements.one(CLUSTER_DISPLAY_INFO, Rows.json(), NativeStatements.text(clusterId));
	}

	/**
	 * The number of active HIV lab or cluster partner documents (or, for identification method 1, HIV resistance
	 * documents) with this document id associated with the current state number.
	 *
	 * @param clusterDataDetails IDENTIFICATION_METHOD_CODE, CURRENT_NYEHMS_STATENO, DOCUMENT_ID (the upload row's values)
	 */
	public int validateClusterDocumentId(Map<String, Object> clusterDataDetails) {
		// the former "#{IDENTIFICATION_METHOD_CODE} = 1" (the database compared the bound text as a number)
		String types = oracleEqualsNumber(clusterDataDetails.get("IDENTIFICATION_METHOD_CODE"), 1)
				? "((d.documentTypeCode = 'HIV_LAB' and d.testTypeCd = 'RESISTANCE') or (d.documentTypeCode in ('HIV_LAB', 'CLUST_PART')))"
				: "(d.documentTypeCode in ('HIV_LAB', 'CLUST_PART'))";
		return jpql.one("select count(*) from DocumentLocator d, PersonDocAssociation c where d.isActive = 'Y' and " + types
				+ " and c.stateno = ?1 and d.documentId = c.documentId and c.dissassociationDate is null and c.documentId = ?2",
				Rows.scalar(Integer.class), text(clusterDataDetails.get("CURRENT_NYEHMS_STATENO")),
				intValue(clusterDataDetails.get("DOCUMENT_ID")));
	}

	// native: ROWNUM = 1 inside the CTEs (Oracle's first row of each eHARS table), the database function FN_TO_DATE and
	// the bound state number as a select item
	private static final String STATENO_DEMOGRAPHIC = """
				WITH person_demo AS
				(
				    SELECT first_name, last_name, to_char(nvl(DATE_OF_BIRTH, FN_TO_DATE(DOB_TEXT, 'MM/DD/YYYY')), 'MM/DD/YYYY') as dob, ?1 stateno
				      FROM document_person_attr
				     WHERE document_id = ?2
				), nys_ehars AS
				(
				    SELECT nyehms_stateno, ehars_stateno, cityno
				      FROM nyehms_ehars_stateno_map
				     WHERE nyehms_stateno = ?3
				       AND ROWNUM = 1
				), nyc_ehars AS
				(
				    SELECT nyehms_stateno, ehars_stateno, cityno
				      FROM ehars_person_nyc
				     WHERE nyehms_stateno = ?4
				       AND ROWNUM = 1
				)
				SELECT first_name, last_name, dob, p.stateno current_nyehms_stateno
				       , CASE WHEN s.nyehms_stateno IS NOT NULL THEN s.ehars_stateno ELSE c.ehars_stateno END ehars_stateno
				       , CASE WHEN s.nyehms_stateno IS NOT NULL THEN s.cityno ELSE c.cityno END cityno
				       , CASE WHEN s.nyehms_stateno IS NOT NULL THEN 'NYS_eHARS' ELSE 'NYC_eHARS' END demo_data_source
				  FROM person_demo p
				  LEFT OUTER JOIN nys_ehars s
				  ON (p.stateno = s.nyehms_stateno)
				  LEFT OUTER JOIN nyc_ehars c
				  ON (p.stateno = c.nyehms_stateno)

			""";

	/**
	 * The names and birth date of a demographic document with the eHARS state number and city number of a NYEHMS state
	 * number (NYS eHARS first, then NYC eHARS).
	 *
	 * @param inputParameter nyehmsStateno, demoDocumentId
	 * @return the values, or null if the document has no person attributes
	 */
	public ClusterDocVO getStatenoDemographic(Map<String, Object> inputParameter) {
		Object stateno = inputParameter.get("nyehmsStateno");
		return statements.one(STATENO_DEMOGRAPHIC, Rows.bean(ClusterDocVO.class), NativeStatements.text(stateno),
				inputParameter.get("demoDocumentId"), NativeStatements.text(stateno), NativeStatements.text(stateno));
	}

	/**
	 * The active documents of a state number that can be a molecular cluster's demographic document (identificationCd
	 * 1: HIV resistance labs; 2: HIV labs and PRFs): JSONObjects with DOCUMENT_ID (a BigDecimal) and DOCUMENT_DETAILS,
	 * newest first.
	 *
	 * @param inputParameter stateno, identificationCd
	 */
	public List<JSONObject> getMolClusterDemoDocumentIds(Map<String, Object> inputParameter) {
		Object identificationCd = inputParameter.get("identificationCd");
		StringBuilder query = new StringBuilder("""
				select pdoc.documentId as DOCUMENT_ID, cast(pdoc.documentId as String) || ' ' ||
				case when d.documentTypeCode = 'HIV_LAB' then d.testTypeCd else d.documentTypeCode end || ' '
				|| dpa.firstName || ' ' || dpa.lastName || ' ' || dpa.dobText as DOCUMENT_DETAILS
				from DocumentLocator d, PersonDocAssociation pdoc, DocumentPersonAttr dpa
				where d.isActive = 'Y'""");
		if (ognlEqualsNumber(identificationCd, 1)) {
			query.append(" and d.documentTypeCode = 'HIV_LAB' and d.testTypeCd = 'RESISTANCE'");
		}
		if (ognlEqualsNumber(identificationCd, 2)) {
			query.append(" and d.documentTypeCode in ('HIV_LAB', 'PRF', 'NYC_PRF')");
		}
		query.append(" and pdoc.stateno = ?1 and d.documentId = pdoc.documentId and pdoc.documentId = dpa.documentId"
				+ " and pdoc.dissassociationDate is null order by pdoc.documentId desc");
		return jpql.list(query.toString(), DOCUMENT_ID_ROWS, text(inputParameter.get("stateno")));
	}

	// native: UNPIVOT of the three resistance document id columns of STD_RAS_DOC (HQL has no UNPIVOT)
	private static final String LINKED_MOL_CLUSTER_GENOTYPE_DOCUMENTS = """
					SELECT document_id
					  FROM (
					            SELECT demo.document_id
					              FROM std_cluster_doc clust, std_cluster_doc demo
					            WHERE clust.document_id = ?1
					               AND clust.demo_document_id = demo.demo_document_id
					            UNION
					            SELECT document_id
					              FROM (
					            SELECT Distinct resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3
					              FROM std_ras_doc ras, std_cluster_doc clust
					            WHERE clust.document_id = ?2
					               AND (ras.resistance_doc_id_1 = clust.demo_document_id
					                    OR ras.resistance_doc_id_2 = clust.demo_document_id
					                    OR ras.resistance_doc_id_3 = clust.demo_document_id))
					            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3))
					            UNION
					            SELECT a.document_id
					              FROM (
					            SELECT ras.resistance_doc_id_1, ras.resistance_doc_id_2, ras.resistance_doc_id_3
					              FROM std_ras_doc ras
					             WHERE ras.resistance_doc_id_1 = ?3
					                    OR ras.resistance_doc_id_2 = ?4
					                    OR ras.resistance_doc_id_3 = ?5)
					            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3)) a
					            LEFT OUTER JOIN std_cluster_doc clust
					            ON (clust.demo_document_id = a.document_id)
					            UNION
					            SELECT clust.document_id
					              FROM (
					            SELECT ras.resistance_doc_id_1, ras.resistance_doc_id_2, ras.resistance_doc_id_3
					              FROM std_ras_doc ras
					            WHERE ras.resistance_doc_id_1 = ?6
					                    OR ras.resistance_doc_id_2 = ?7
					                    OR ras.resistance_doc_id_3 = ?8)
					            UNPIVOT (document_id FOR data IN (resistance_doc_id_1, resistance_doc_id_2, resistance_doc_id_3)) a, std_cluster_doc clust
					            WHERE a.document_id = clust.demo_document_id
					        )
					WHERE document_id != ?9
					UNION
					SELECT demo_document_id from std_cluster_doc where document_id = ?10
		            UNION
		            SELECT document_id from std_cluster_doc where demo_document_id = ?11
			""";

	/** The documents linked with a molecular cluster record through its demographic document and the RAS resistance documents. */
	public List<Integer> getLinkedMolClusterGenoTypeDocuments(int documentId) {
		Object[] values = new Object[11];
		java.util.Arrays.fill(values, documentId);
		return statements.list(LINKED_MOL_CLUSTER_GENOTYPE_DOCUMENTS, Rows.scalar(Integer.class), values);
	}

	/**
	 * The CDC molecular cluster demographic documents found through the RIDR duplicates of a state number's case report
	 * forms: JSONObjects with DOCUMENT_ID (a BigDecimal) and DOCUMENT_DETAILS, newest first.
	 *
	 * @param inputParameter stateno
	 */
	public List<JSONObject> getCDCMolClusterRecordSearchRIDRDemoDocumentIds(Map<String, Object> inputParameter) {
		return jpql.list("""
				select std.documentId as DOCUMENT_ID, cast(std.documentId as String) || ' ' || d.testTypeCd || ' '
				|| dpa.firstName || ' ' || dpa.lastName || ' ' || dpa.dobText || ' Same As ' || ridr.stateCd || ' ' || ridr.stateno
				as DOCUMENT_DETAILS
				from RawAcrfRidrInfo ridr, StdAcrfDoc std, PersonDocAssociation pdoc, DocumentLocator d, DocumentPersonAttr dpa
				where ridr.duplicateStatus = 1 and d.documentId = pdoc.documentId and pdoc.documentId = dpa.documentId
				and ridr.acrfDocId = std.documentId and pdoc.stateno = ?1 and pdoc.documentId = std.documentId
				and pdoc.dissassociationDate is null order by pdoc.documentId desc""", DOCUMENT_ID_ROWS, text(inputParameter.get("stateno")));
	}

	/** The transmission categories by code: maps with CATEGORY_CODE, CATEG_DESC_SHORT. */
	public List<Map<String, String>> getTransmissionCategoryData() throws DataAccessException {
		return transmissionCategories.findAllByOrderByCategoryCode().stream()
				.map(c -> textMap("CATEGORY_CODE", c.categoryCode(), "CATEG_DESC_SHORT", c.categDescShort())).collect(toList());
	}

	/** The eHARS race codes by code: maps with EHARS_RACE_CODE, EHARS_RACE_DESC. */
	public List<Map<String, String>> getEharsRaceData() throws DataAccessException {
		return eharsRaces.findAllByOrderByEharsRaceCode().stream()
				.map(r -> textMap("EHARS_RACE_CODE", r.eharsRaceCode(), "EHARS_RACE_DESC", r.eharsRaceDesc())).collect(toList());
	}

	/** The eHARS ethnicity codes by code: maps with EHARS_ETH_CODE, EHARS_ETH_DESC. */
	public List<Map<String, String>> getEharsEthnicityData() throws DataAccessException {
		return eharsEthnicities.findAllByOrderByEharsEthCode().stream()
				.map(e -> textMap("EHARS_ETH_CODE", e.eharsEthCode(), "EHARS_ETH_DESC", e.eharsEthDesc())).collect(toList());
	}

	/**
	 * The state number if it is in the NYS or the NYC eHARS data (a union: at most once).
	 *
	 * @param inputParameterMap p_stateno
	 */
	public List<String> isEharsStatenoExist(Map<String, Object> inputParameterMap) {
		String stateno = text(inputParameterMap.get("p_stateno"));
		return jpql.list("""
				select m.nyehmsStateno from NyehmsEharsStatenoMap m where m.nyehmsStateno = ?1
				union
				select e.nyehmsStateno from EharsPersonNyc e where e.nyehmsStateno = ?2""", Rows.scalar(String.class), stateno, stateno);
	}

	/**
	 * The number of standardized records of a cluster whose upload state number has the same current state number
	 * (PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO) as the given one.
	 *
	 * @param inputParameterMap p_stateno, clusterIdPk
	 */
	public int isStatenoWithCluster(Map<String, Object> inputParameterMap) {
		return jpql.one("""
				select count(*) from StdClusterDoc s
				where function('pkg_consolidation.fn_give_current_stateno', s.nyehmsStatenoAtUpload)
				= function('pkg_consolidation.fn_give_current_stateno', ?1) and s.clusterIdPk = ?2""",
				Rows.scalar(Integer.class), text(inputParameterMap.get("p_stateno")), intValue(inputParameterMap.get("clusterIdPk")));
	}

	/**
	 * Adds a state number to a space-time cluster by hand (CREATE_DATE = SYSDATE).
	 *
	 * @param inputParameterMap clusterIdPk, p_stateno, p_workerId, p_requestComments
	 * @return 1
	 */
	public int associateStatenoWithCluster(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				insert into BhaeTsClusterManual (clusterIdPk, stateno, createUid, createDate, userComment)
				values (?1, ?2, ?3, sysdate, ?4)""",
				intValue(inputParameterMap.get("clusterIdPk")), text(inputParameterMap.get("p_stateno")),
				longValue(inputParameterMap.get("p_workerId")), text(inputParameterMap.get("p_requestComments")));
	}

	// native: SELECT ... FROM DUAL (no entity) comparing the PL/SQL function PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO
	private static final String STATENO_ACTIVE = """
				select count(*) from dual where pkg_consolidation.fn_give_current_stateno(?1) = ?2
			""";

	/**
	 * 1 if the state number is its own current state number, otherwise 0.
	 *
	 * @param inputParameterMap p_stateno
	 */
	public int isStatenoActive(Map<String, Object> inputParameterMap) {
		Object stateno = inputParameterMap.get("p_stateno");
		return statements.one(STATENO_ACTIVE, Rows.scalar(Integer.class), NativeStatements.text(stateno), NativeStatements.text(stateno));
	}

	// ---------------------------------------------------------------------------------------------------------------

	/** The former result map clusterIdReport (ClusterIdReportDataVO). */
	static final RowMapper<ClusterIdReportDataVO> CLUSTER_ID_REPORT_ROWS = new ResultMapRows<>(ClusterIdReportDataVO.class,
			"stateno", "stateno", "cluster_create_date", "cluster_date", "dob", "dob", "race", "race", "ethnicity", "ethnicity",
			"birth_sex", "birth_sex", "current_gender", "current_gender", "transmission_category", "transmission_risk",
			"dissassociation_date", "dissassociation_date", "ehars_hiv_dx_dt", "ehars_hiv_dx_dt", "ehars_aids_dx_dt", "ehars_aids_dx_dt",
			"status_flag", "status_flag", "cityno", "cityno", "vital_status", "vital_status", "cur_county", "cur_county",
			"rsh_county", "rsh_county", "hf_county", "hf_county", "test_result1", "test_result1",
			"calc_collection_date1", "calc_collection_date1", "test_result2", "test_result2", "calc_collection_date2", "calc_collection_date2",
			"recent_surv_assign_dt", "recent_surv_assign_dt", "surv_closure_message", "surv_closure_message",
			"recent_ps_message_sent_dt", "recent_ps_message_sent_dt", "ps_dispo_desc", "ps_dispo_desc",
			"ehars_hiv_dx_dt", "sort_ehars_hiv_dx_dt", "ehars_aids_dx_dt", "sort_ehars_aids_dx_dt",
			"identification_method", "identification_method", "identification_date", "identification_date",
			"identification_date", "sort_identification_date", "demo_document_id", "demo_document_id",
			"document_type_code", "demo_doc_type", "partner_reviewed", "partnerReviewed", "age_at_hiv_dx", "ageAtHIVDx",
			"cluster_id_pk", "clusterId");

	/**
	 * JSONObjects with DOCUMENT_ID and DOCUMENT_DETAILS as the former native rows had them: DOCUMENT_ID a BigDecimal
	 * (a NUMBER read with getObject), null values left out, a row of nulls null.
	 */
	@SuppressWarnings("unchecked")
	private static final RowMapper<JSONObject> DOCUMENT_ID_ROWS = row -> {
		JSONObject json = new JSONObject();
		Object documentId = row.get(0);
		if (documentId != null) {
			json.put("DOCUMENT_ID", new BigDecimal(documentId.toString()));
		}
		if (row.get(1) != null) {
			json.put("DOCUMENT_DETAILS", row.get(1));
		}
		return json.isEmpty() ? null : json;
	};

	/**
	 * Rows mapped as a MyBatis result map without nested mappings (default settings) mapped them: first the columns
	 * the result map does not name are auto-mapped to the property of the same name (ignoring case) unless the result
	 * map maps that property itself, then the named columns are set on their properties; a null value is not set, each
	 * value is read as its property's type, and a row that sets nothing is null.
	 */
	private static final class ResultMapRows<T> implements RowMapper<T> {

		private final RowMapper<T> bean;
		private final List<String[]> mappings = new ArrayList<>();
		private final Set<String> mappedColumns;
		private final Set<String> mappedProperties;

		ResultMapRows(Class<T> type, String... columnsAndProperties) {
			bean = Rows.bean(type);
			for (int i = 0; i < columnsAndProperties.length; i += 2) {
				mappings.add(new String[] {columnsAndProperties[i], columnsAndProperties[i + 1]});
			}
			mappedColumns = mappings.stream().map(m -> upper(m[0])).collect(Collectors.toSet());
			mappedProperties = mappings.stream().map(m -> upper(m[1])).collect(Collectors.toSet());
		}

		@Override
		public T map(Row row) {
			Map<String, Object> values = new LinkedHashMap<>();       // property (upper case) -> value
			for (int i = 0; i < row.size(); i++) {
				String label = row.labels().get(i);
				if (!mappedColumns.contains(upper(label)) && !mappedProperties.contains(upper(label)) && row.get(i) != null) {
					values.put(upper(label), row.get(i));
				}
			}
			for (String[] mapping : mappings) {
				int index = row.indexOf(mapping[0]);
				if (index >= 0 && row.get(index) != null) {
					values.put(upper(mapping[1]), row.get(index));
				}
			}
			return bean.map(new Row(new ArrayList<>(values.keySet()), new ArrayList<>(values.values())));
		}

		private static String upper(String name) {
			return name.toUpperCase(Locale.ENGLISH);
		}
	}

	/**
	 * OGNL's {@code x == n} of the former dynamic SQL: a number compared by value, a text read as a number ('' is 0; a
	 * text that is no number fails, as OGNL did), null not equal.
	 */
	static boolean ognlEqualsNumber(Object value, int number) {
		if (value == null) {
			return false;
		}
		if (value instanceof Number n) {
			return new BigDecimal(n.toString()).compareTo(BigDecimal.valueOf(number)) == 0;
		}
		String text = value.toString();
		return (text.isEmpty() ? 0.0 : Double.parseDouble(text)) == number;
	}

	/**
	 * The database's {@code ? = n} for a bound value: null is not equal, a text is converted to a number (a text that is
	 * no number fails, as Oracle's ORA-01722 did).
	 */
	private static boolean oracleEqualsNumber(Object value, int number) {
		if (value == null || "".equals(value)) {
			return false;
		}
		try {
			return new BigDecimal(value.toString().trim()).compareTo(BigDecimal.valueOf(number)) == 0;
		} catch (NumberFormatException e) {
			throw new DataIntegrityViolationException("invalid number: " + value, e);
		}
	}

	/** A whole-number value as an Integer attribute value; null and '' as null. */
	private static Integer intValue(Object value) {
		Long number = longValue(value);
		return number == null ? null : Math.toIntExact(number);
	}

	/** A map of two text entries, a null value left out; null if both are null (as a MyBatis map result). */
	private static Map<String, String> textMap(String key1, String value1, String key2, String value2) {
		Map<String, String> map = new HashMap<>();
		if (value1 != null) map.put(key1, value1);
		if (value2 != null) map.put(key2, value2);
		return map.isEmpty() ? null : map;
	}

	private static <T> java.util.stream.Collector<T, ?, List<T>> toList() {
		return java.util.stream.Collectors.toCollection(ArrayList::new);
	}
}
