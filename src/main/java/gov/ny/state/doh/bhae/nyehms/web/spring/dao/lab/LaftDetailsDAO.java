package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.id;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LaftRows.now;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LaftDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawAddrCanbeRes;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawAddrCanbeResRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * The LAFT lab error screens and reports: the documents that failed validation, their notes and status changes, the
 * addresses that can be a residence, and the error reports (formerly a MyBatis mapper with the same name and methods).
 * JPQL over the entities of {@code entity.common} / {@code entity.lab} (the former Oracle {@code (+)} joins are JPQL
 * left joins, {@code TO_CHAR(l.PFI)} and {@code To_Date} stay the database's functions).
 * <p>
 * Native SQL, unchanged except that the values are bound, and why:
 * <ul>
 * <li>the four validation-failed lists: the raw document table is a parameter ({@code ${rawTableName}}, now checked
 * against the RAW_ tables LabCommonUtils.getRawTableName gives; null renders empty as before), with an INDEX hint and
 * PKG_TRK_COMMON.fn_get_fullname;</li>
 * <li>{@link #getLabErrorReportsByStatus}: ISO weeks and NLS month / day names of TO_CHAR ('IW', 'MON', 'DAY');</li>
 * <li>the six status changes: PL/SQL procedures of pkg_hiv_load_process taking an Oracle array (NUM_ARR) the caller
 * creates; they run on the persistence context's connection (bound with setArray, as MyBatis' ArrayTypeHandler did) and
 * their OUT values are put back into the map as p_code_out (Integer) and p_msg_out.</li>
 * </ul>
 * The dates the former SQL set with SYSDATE are the application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class LaftDetailsDAO {

	/** The raw document tables LabCommonUtils.getRawTableName gives (the former ${rawTableName}). */
	private static final Set<String> RAW_TABLES = Set.of("RAW_HIV_ECLRS_DOC", "RAW_STD_ECLRS_DOC", "RAW_TB_DOC", "RAW_SYPHILIS_DOC",
			"RAW_DEATH_DOC", "RAW_CASA_DOC", "RAW_DOCCS_DOC", "RAW_DIN_DOCCS_DOC", "RAW_PRF_DOC", "RAW_NYCPRF_DOC", "RAW_ACRF_DOC", "RAW_PCRF_DOC",
			"RAW_AIRS_DOC", "RAW_ADAP_DOC", "RAW_SSDM_DOC", "RAW_NDI_DOC", "RAW_NYCIJR_DOC", "RAW_NYC_DEATH_DOC", "RAW_MEDICAID_DOC",
			"RAW_OLD_CASES_DOC", "RAW_MED_OLD_DOC", "RAW_EPRF_OOC_UPLOAD", "RAW_CCD_DOC", "RAW_HL7_DOC", "RAW_EXP_DOC", "RAW_NYC_EHARS_DOC",
			"RAW_NYC_EHARS_LAB_DOC", "RAW_NDI_CAUSE_DOC", "RAW_INS_DEATH_DOC", "RAW_STD_OLD_DOC", "RAW_HIE_DEATH_DOC", "RAW_CLUSTER_DOC",
			"RAW_STD_CONFIRMED_DOC", "RAW_BLACK_BOX_DOC", "RAW_ICCR_DOC", "RAW_QUALITY_CARE_DOC");

	private static final String VALIDATION_FAILED_LAB = """
			SELECT DOCUMENT_ID documentId, ?1 documentType,
			ERROR_DESC errorDesc, NOTES notes, CREATEID createdBy, CREATE_DATE createdDate, PROCESS_STATUS processStatus,
			      PREVIOUS_STATUS previousStatus, OBRACCESSIONNUM obraccessionnum, MSHSENDINGFACILITYCLIA mshsendingfacilityclia,
			      MSHFILENAME mshFileName,MSHLABPFI  mshLabPfi, OBXLOINCCODE loincCode, CNT_REPRISED count, MAX_REPRISED_DATE lastReprisedDate, provAddress, provLname, provFname, provMname, provSuffix, providerName, provPhone, provNPI  FROM (
			WITH main_sql AS (
			SELECT /* + INDEX( E LAB_DOC_LAFT_PROCES_IDX ) */
			           E.document_id,E.ERROR_DESC,E.CREATEID,E.CREATE_DATE,E.PREVIOUS_STATUS,E.NOTES,E.PROCESS_STATUS,D.OBRACCESSIONNUM ,D.MSHSENDINGFACILITYCLIA
			             ,D.MSHFILENAME ,D. MSHLABPFI  ,D.OBXLOINCCODE,
			             D.ZLRPROVIDERADDRESSLINE1 || ' ' || D.ZLRPROVIDERADDRESSLINE2 || ', ' || D.ZLRPROVIDERCITY || ', ' || D.ZLRPROVIDERSTATE || ', ' || D.ZLRPROVIDERZIPCODE provAddress,
			             D.OBRPROVIDERLASTNAME provLname,
			             D.OBRPROVIDERFIRSTNAME provFname,
			             D.OBRPROVIDERMIDDLENAME provMname,
			             D.OBRPROVIDERNAMESUFFIX provSuffix,
			             D.OBRCALLBACKPHONE provPhone,
			             pkg_trk_common.fn_get_fullname (D.OBRPROVIDERLASTNAME,
			                                    D.OBRPROVIDERFIRSTNAME,
			                                    D.OBRPROVIDERMIDDLENAME,
			                                    D.OBRPROVIDERNAMESUFFIX) as providerName,
			             D.OBRPROVIDERID provNPI
			 FROM NYEHMS_PROCESS_ERRORS  E , %s D
			WHERE E.document_id=D.document_id
			AND  DOCUMENT_TYPE='HIV_LAB'
			AND  ACTIVE_FLAG ='Y'
			AND PROCESS_STATUS IN (4,7,30)
			                    )
			SELECT C.*, SUM(CASE WHEN M.PREVIOUS_STATUS IN (6,15,11) THEN 1 ELSE 0 END) OVER (PARTITION BY M.document_id) cnt_reprised,
			            MAX(CASE WHEN M.PROCESS_STATUS IN (6,15,11) THEN M.UPDATE_DATE ELSE NULL END) OVER (PARTITION BY C.document_id) MAX_reprised_date,
			            ROW_NUMBER () OVER (PARTITION BY M.document_id ORDER BY M.CREATE_DATE ) rwnum
			FROM  NYEHMS_PROCESS_ERRORS M , main_sql C
			WHERE M.document_id=C.document_id
			 )
			WHERE rwnum = 1
			ORDER BY CREATE_DATE DESC
			""";

	private static final String VALIDATION_FAILED_AIRS = """
			SELECT DOCUMENT_ID documentId, ?1 documentType,
			ERROR_DESC errorDesc, NOTES notes, CREATEID createdBy, CREATE_DATE createdDate, PROCESS_STATUS processStatus, PREVIOUS_STATUS previousStatus, CNT_REPRISED count, MAX_REPRISED_DATE lastReprisedDate  FROM (
			WITH main_sql AS (
			SELECT /* + INDEX( E LAB_DOC_LAFT_PROCES_IDX ) */
			           E.document_id,E.ERROR_DESC,E.CREATEID,E.CREATE_DATE,E.PREVIOUS_STATUS,E.NOTES,E.PROCESS_STATUS
			 FROM NYEHMS_PROCESS_ERRORS  E , %s D
			WHERE E.document_id=D.document_id
			AND  DOCUMENT_TYPE=?2
			AND  ACTIVE_FLAG ='Y'
			AND PROCESS_STATUS IN (4,25,26,27,28,29,32)
			                    )
			SELECT C.*, SUM(CASE WHEN M.PREVIOUS_STATUS IN (6,15) THEN 1 ELSE 0 END) OVER (PARTITION BY M.document_id) cnt_reprised,
			            MAX(CASE WHEN M.PROCESS_STATUS IN (6,15) THEN M.UPDATE_DATE ELSE NULL END) OVER (PARTITION BY C.document_id) MAX_reprised_date,
			            ROW_NUMBER () OVER (PARTITION BY M.document_id ORDER BY M.CREATE_DATE ) rwnum
			FROM  NYEHMS_PROCESS_ERRORS M , main_sql C
			WHERE M.document_id=C.document_id
			 )
			WHERE rwnum = 1
			ORDER BY CREATE_DATE DESC
			""";

	private static final String VALIDATION_FAILED_DELETED_LAB = """
			SELECT DOCUMENT_ID documentId, ?1 documentType,
			COMMENTS errorDesc, NOTES notes, CREATEID createdBy, CREATE_DATE createdDate, PROCESS_STATUS processStatus, PREVIOUS_STATUS previousStatus, OBRACCESSIONNUM obraccessionnum, MSHSENDINGFACILITYCLIA mshsendingfacilityclia,  MSHFILENAME mshFileName,MSHLABPFI  mshLabPfi, OBXLOINCCODE loincCode, CNT_REPRISED count, MAX_REPRISED_DATE lastReprisedDate  FROM (
			WITH main_sql AS (
			SELECT /* + INDEX( E LAB_DOC_LAFT_PROCES_IDX ) */
			           E.document_id,E.COMMENTS,E.CREATEID,E.CREATE_DATE,E.PREVIOUS_STATUS,E.NOTES,E.PROCESS_STATUS,D.OBRACCESSIONNUM ,D.MSHSENDINGFACILITYCLIA
			             ,D.MSHFILENAME ,D. MSHLABPFI  ,D.OBXLOINCCODE
			 FROM NYEHMS_PROCESS_ERRORS  E , %s D
			WHERE E.document_id=D.document_id
			AND  DOCUMENT_TYPE='HIV_LAB'
			AND  ACTIVE_FLAG ='N'
			AND PROCESS_STATUS IN (33)
			                    )
			SELECT C.*, SUM(CASE WHEN M.PREVIOUS_STATUS IN (6,15,11) THEN 1 ELSE 0 END) OVER (PARTITION BY M.document_id) cnt_reprised,
			            MAX(CASE WHEN M.PROCESS_STATUS IN (6,15,11) THEN M.UPDATE_DATE ELSE NULL END) OVER (PARTITION BY C.document_id) MAX_reprised_date,
			            ROW_NUMBER () OVER (PARTITION BY M.document_id ORDER BY M.CREATE_DATE ) rwnum
			FROM  NYEHMS_PROCESS_ERRORS M , main_sql C
			WHERE M.document_id=C.document_id
			 )
			WHERE rwnum = 1
			ORDER BY CREATE_DATE DESC
			""";

	private static final String VALIDATION_FAILED_DELETED_AIRS = """
			SELECT DOCUMENT_ID documentId, ?1 documentType,
			COMMENTS errorDesc, NOTES notes, CREATEID createdBy, CREATE_DATE createdDate, PROCESS_STATUS processStatus, PREVIOUS_STATUS previousStatus, CNT_REPRISED count, MAX_REPRISED_DATE lastReprisedDate  FROM (
			WITH main_sql AS (
			SELECT /* + INDEX( E LAB_DOC_LAFT_PROCES_IDX ) */
			           E.document_id,E.COMMENTS,E.CREATEID,E.CREATE_DATE,E.PREVIOUS_STATUS,E.NOTES,E.PROCESS_STATUS
			 FROM NYEHMS_PROCESS_ERRORS  E , %s D
			WHERE E.document_id=D.document_id
			AND  DOCUMENT_TYPE=?2
			AND  ACTIVE_FLAG ='N'
			AND PROCESS_STATUS IN (33)
			                    )
			SELECT C.*, SUM(CASE WHEN M.PREVIOUS_STATUS IN (6,15) THEN 1 ELSE 0 END) OVER (PARTITION BY M.document_id) cnt_reprised,
			            MAX(CASE WHEN M.PROCESS_STATUS IN (6,15) THEN M.UPDATE_DATE ELSE NULL END) OVER (PARTITION BY C.document_id) MAX_reprised_date,
			            ROW_NUMBER () OVER (PARTITION BY M.document_id ORDER BY M.CREATE_DATE ) rwnum
			FROM  NYEHMS_PROCESS_ERRORS M , main_sql C
			WHERE M.document_id=C.document_id
			 )
			WHERE rwnum = 1
			ORDER BY CREATE_DATE DESC
			""";

	/** Native: ISO week ('IW') and NLS month / day names ('MON', 'DAY') of TO_CHAR, ordered by to_date of the date text. */
	private static final String LAB_ERROR_REPORTS_BY_STATUS = """
			            SELECT CREATE_DATE, case when mon_name = 'DEC' and week = '01' then to_number(Year) + 1 else to_number(year) end
			        || week YEAR_WEEK, MON_NAME, DAY_NAME, REPRISE_DELETE, UPDATEID, TO_CHAR(count(*)) COUNT
			  FROM (
			SELECT to_char(n.UPDATE_DATE, 'MM/DD/YYYY') CREATE_DATE,
			            to_char(n.UPDATE_DATE, 'YYYY') YEAR,
			            to_char(n.UPDATE_DATE, 'IW') WEEK,
			            to_char(n.UPDATE_DATE, 'MON') MON_NAME,
			            to_char(n.UPDATE_DATE, 'DAY') DAY_NAME,
			            CASE WHEN PROCESS_STATUS IN (6,15) THEN 'REPRISE' WHEN PROCESS_STATUS IN (20) then 'EDIT' ELSE 'DELETE' END REPRISE_DELETE,
			            usr.LEGACY_UID UPDATEID
			            --TO_CHAR(count(*)) COUNT

			            FROM  NYEHMS_PROCESS_ERRORS N, nyehms_users usr
			            WHERE
			            n.UPDATEID = usr.worker_id and
			            N.PROCESS_STATUS IN (6,15,33,20) and
			            n.document_Type = 'HIV_LAB' and
			            usr.LEGACY_UID not in ('SKA03', 'NYEHMS', 'JAS22', 'JLC16','VZG03') and
			            UPDATE_DATE between To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY')+1
			)
				group by  create_date,  case when mon_name = 'DEC' and week = '01' then to_number(Year) + 1 else to_number(year) end
				        || week, mon_name , day_name, REPRISE_DELETE, UPDATEID
				order by to_date(create_date, 'MM/DD/YYYY')
			""";

	private static final String DOCUMENTS_WITH_STATUS = """
			select e.documentId from NyehmsProcessErrors e
			where e.processStatus = ?1 and e.activeFlag = '%s' and e.documentId in ?2""";

	private static final String NOTES_CONDITION = """
			((e.processStatus in (4, 7, 25, 26, 27, 28, 29, 30, 32) and e.activeFlag = 'Y') or (e.processStatus in (33) and e.activeFlag = 'N'))""";

	private static final String HED_STATUS_DOCUMENTS = """
			select s.documentId as documentId, 'HIV_LAB' as documentType, ps.processesStatusDesc as processStatusDesc,
			ps.processesStatusCode as processStatus
			from StdHivEclrsDoc s, CodedLabProcessesStatus ps
			where ps.processesStatusCode = s.currentProcessStatus and s.currentProcessStatus in (7, 8, 9, 10)""";

	private static final String ADDRESS_SAME_AS_ENTRIES = """
			select r.addressId, r.address, r.flagActive, r.filterType, format(r.createDate as 'MM/dd/yyyy'), format(r.endDate as 'MM/dd/yyyy')
			from RawAddrCanbeRes r""";

	private static final String LAB_ERROR_REPORTS_BY_TYPE = """
			select c.errorCategory, c.errorType, c.errorDesc, count(a.documentId)
			from NyehmsProcessErrors a, NyehmsProcessErrorsDetail b, CodedProcessErrors c
			where a.errorId = b.errorId and b.errorCode = c.errorCode and a.activeFlag = 'Y' and a.processStatus in (4, 7, 30)
			group by c.errorCategory, c.errorType, c.errorDesc
			order by c.errorCategory""";

	private static final String LAB_ERROR_REPORTS_BY_FACILITY = """
			select l.laftStaff, raw1.mshsendingfacilityclia, raw1.mshlabpfi, l.labName, count(*)
			from RawHivEclrsDoc raw1 left join LabPhysicalFacility l on raw1.mshlabpfi = function('TO_CHAR' as String, l.pfi)
			where exists (select 1 from NyehmsProcessErrors e where e.activeFlag = 'Y' and e.processStatus in (4, 7, 30)
				and e.errorDesc is not null and e.documentType = 'HIV_LAB' and raw1.documentId = e.documentId)
			group by l.laftStaff, raw1.mshsendingfacilityclia, raw1.mshlabpfi, l.labName
			order by l.labName""";

	private static final String LAB_ERROR_STATUS_BY_LAB = """
			select l.labName, r.mshsendingfacilityclia, r.mshlabpfi, usr.legacyUid,
			sum(case when n.processStatus in (6, 15) then 1 else 0 end),
			sum(case when n.processStatus in (33) then 1 else 0 end),
			sum(case when n.processStatus in (20) then 1 else 0 end)
			from RawHivEclrsDoc r
			join NyehmsProcessErrors n on r.documentId = n.documentId
			join NyehmsUsers usr on n.updateid = usr.workerId
			left join LabPhysicalFacility l on r.mshlabpfi = function('TO_CHAR' as String, l.pfi)
			where n.processStatus in (6, 15, 33, 20)
			and n.updateDate between function('To_Date' as Timestamp, ?1, 'MM/DD/YYYY') and function('To_Date' as Timestamp, ?2, 'MM/DD/YYYY') + 1 day
			group by l.labName, r.mshsendingfacilityclia, r.mshlabpfi, usr.legacyUid
			order by l.labName""";

	private static final String DOCUMENT_ERRORS_BY_CATEGORY = """
			select errDtl.errorId, errDtl.errorCode, codedErr.errorDesc
			from NyehmsProcessErrors err, NyehmsProcessErrorsDetail errDtl, CodedProcessErrors codedErr
			where err.documentId = ?1 and err.errorId = errDtl.errorId and err.activeFlag = 'Y' and err.processStatus = 4
			and codedErr.errorCode = errDtl.errorCode""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final RawAddrCanbeResRepository addresses;

	@PersistenceContext
	private EntityManager entityManager;

	/** The HIV lab documents that failed validation (status 4, 7, 30), one row per document, newest first. */
	public List<LaftDetailsVO> getValidationFailedLabDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return statements.list(String.format(VALIDATION_FAILED_LAB, rawTable(inOutParameterMap)), Rows.bean(LaftDetailsVO.class),
				inOutParameterMap.get("documentType"));
	}

	/** The documents of a type other than HIV lab (e.g. AIRS) that failed validation, one row per document, newest first. */
	public List<LaftDetailsVO> getValidationFailedAIRSDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return statements.list(String.format(VALIDATION_FAILED_AIRS, rawTable(inOutParameterMap)), Rows.bean(LaftDetailsVO.class),
				inOutParameterMap.get("documentType"), inOutParameterMap.get("documentType"));
	}

	/** The HIV lab documents that failed validation and were flagged deleted (status 33), newest first. */
	public List<LaftDetailsVO> getValidationFailedDeletedLabDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return statements.list(String.format(VALIDATION_FAILED_DELETED_LAB, rawTable(inOutParameterMap)), Rows.bean(LaftDetailsVO.class),
				inOutParameterMap.get("documentType"));
	}

	/** The documents of a type other than HIV lab that failed validation and were flagged deleted (status 33). */
	public List<LaftDetailsVO> getValidationFailedDeletedAIRSDocumentDetails(Map<Object, Object> inOutParameterMap) throws DataAccessException {
		return statements.list(String.format(VALIDATION_FAILED_DELETED_AIRS, rawTable(inOutParameterMap)), Rows.bean(LaftDetailsVO.class),
				inOutParameterMap.get("documentType"), inOutParameterMap.get("documentType"));
	}

	/** The HIV lab documents standardized, on hold, not matched or in clerical review (status 7 to 10). */
	public List<LaftDetailsVO> getHEDStatusDocuments() throws DataAccessException {
		return jpql.list(HED_STATUS_DOCUMENTS, Rows.bean(LaftDetailsVO.class));
	}

	/** Reprises documents: calls {@code pkg_hiv_load_process.pr_process_doc_reprise} (see {@link #changeStatus}). */
	@Transactional
	public void repriseErrorDocuments(Map<Object, Object> map) throws DataAccessException {
		changeStatus("pkg_hiv_load_process.pr_process_doc_reprise", map, true, true);
	}

	/** Deletes documents: calls {@code pkg_hiv_load_process.pr_process_doc_delete} (see {@link #changeStatus}). */
	@Transactional
	public void deleteErrorDocuments(Map<Object, Object> map) throws DataAccessException {
		changeStatus("pkg_hiv_load_process.pr_process_doc_delete", map, true, true);
	}

	/** Undeletes documents: calls {@code pkg_hiv_load_process.pr_process_doc_undelete} (see {@link #changeStatus}). */
	@Transactional
	public void undeleteErrorDocuments(Map<Object, Object> map) throws DataAccessException {
		changeStatus("pkg_hiv_load_process.pr_process_doc_undelete", map, true, true);
	}

	/** Puts documents on hold: calls {@code pkg_hiv_load_process.pr_process_doc_hold} (no document type parameter). */
	@Transactional
	public void holdLabDocuments(Map<Object, Object> map) throws DataAccessException {
		changeStatus("pkg_hiv_load_process.pr_process_doc_hold", map, true, false);
	}

	/** Takes documents off hold: calls {@code pkg_hiv_load_process.pr_process_doc_unhold} (no status or document type). */
	@Transactional
	public void unholdLabDocuments(Map<Object, Object> map) throws DataAccessException {
		changeStatus("pkg_hiv_load_process.pr_process_doc_unhold", map, false, false);
	}

	/** Sets documents to "do not report": calls {@code pkg_hiv_load_process.pr_process_doc_do_not_report}. */
	@Transactional
	public void updateStatusToDonotReport(Map<Object, Object> inOutParameterMap) {
		changeStatus("pkg_hiv_load_process.pr_process_doc_do_not_report", inOutParameterMap, true, true);
	}

	/**
	 * Calls a status change procedure of pkg_hiv_load_process and puts its OUT values into the map as p_code_out
	 * (Integer) and p_msg_out, as the MyBatis CALLABLE statements did.
	 *
	 * @param map p_document_list (a java.sql.Array of document ids, created by the caller), p_current_status (when
	 *        {@code withStatus}), p_user_id, p_comments, p_document_type (when {@code withType})
	 */
	private void changeStatus(String procedure, Map<Object, Object> map, boolean withStatus, boolean withType) {
		List<CallParam> parameters = new ArrayList<>();
		parameters.add(in(map.get("p_document_list"), java.sql.Array.class));
		if (withStatus) {
			parameters.add(in(map.get("p_current_status"), Integer.class));
		}
		parameters.add(in(map.get("p_user_id"), String.class));
		parameters.add(in(map.get("p_comments"), String.class));
		if (withType) {
			parameters.add(in(map.get("p_document_type"), String.class));
		}
		parameters.add(out(Integer.class));
		parameters.add(out(String.class));
		List<Object> results = callWithArray(procedure, parameters.toArray(CallParam[]::new));
		map.put("p_code_out", results.get(results.size() - 2));
		map.put("p_msg_out", results.get(results.size() - 1));
	}

	/**
	 * Calls a procedure with an Oracle array parameter on the persistence context's connection (a JPA
	 * StoredProcedureQuery cannot bind a java.sql.Array): an Array value is bound with setArray (MyBatis'
	 * ArrayTypeHandler), other IN values with setObject, nulls with setNull; OUT values are read as Integer (getInt,
	 * null when SQL NULL) or String.
	 *
	 * @return one value per parameter: the OUT values, null for IN parameters
	 */
	protected List<Object> callWithArray(String procedure, CallParam... parameters) {
		String sql = "{call " + procedure + "(" + String.join(",", java.util.Collections.nCopies(parameters.length, "?")) + ")}";
		return entityManager.unwrap(Session.class).doReturningWork(connection -> {
			try (CallableStatement statement = connection.prepareCall(sql)) {
				for (int i = 0; i < parameters.length; i++) {
					CallParam parameter = parameters[i];
					if (parameter.mode() == ParameterMode.IN) {
						if (parameter.value() == null) {
							statement.setNull(i + 1, sqlType(parameter.type()));
						} else if (parameter.value() instanceof java.sql.Array array) {
							statement.setArray(i + 1, array);
						} else {
							statement.setObject(i + 1, parameter.value());
						}
					} else {
						statement.registerOutParameter(i + 1, sqlType(parameter.type()));
					}
				}
				statement.execute();
				List<Object> results = new ArrayList<>();
				for (int i = 0; i < parameters.length; i++) {
					CallParam parameter = parameters[i];
					if (parameter.mode() == ParameterMode.IN) {
						results.add(null);
					} else if (parameter.type() == Integer.class) {
						int value = statement.getInt(i + 1);
						results.add(statement.wasNull() ? null : value);
					} else {
						results.add(statement.getString(i + 1));
					}
				}
				return results;
			}
		});
	}

	private static int sqlType(Class<?> type) {
		if (type == Integer.class) {
			return Types.INTEGER;
		}
		if (type == java.sql.Array.class) {
			return Types.ARRAY;
		}
		return Types.VARCHAR;
	}

	/**
	 * The documents (of the given ones) with a process error in a status; ACTIVE_FLAG 'N' for status 33, else 'Y' (the
	 * former OGNL {@code statusCode == 33}).
	 *
	 * @param map statusCode, documentIds (not empty: the former statement failed then too)
	 */
	public List<Long> getDocumentWithTheSpecificStatusCode(Map<Object, Object> map) throws DataAccessException {
		Object statusCode = map.get("statusCode");
		String activeFlag = isThirtyThree(statusCode) ? "N" : "Y";
		return jpql.list(String.format(DOCUMENTS_WITH_STATUS, activeFlag), row -> (Long) row.get(0), id(statusCode),
				LaftRows.longs(map.get("documentIds"), "documentIds"));
	}

	/** OGNL {@code statusCode == 33}: a number (or a numeric text) equal to 33. */
	private static boolean isThirtyThree(Object value) {
		if (value instanceof Number number) {
			return number.doubleValue() == 33;
		}
		if (value instanceof String text) {
			try {
				return Double.parseDouble(text.trim()) == 33;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Sets the notes of a document's error records (open errors, or deleted ones), updated now.
	 *
	 * @param inOutParameterMap p_documentId, p_notes, p_worker_id
	 */
	public int updateLabNotes(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update NyehmsProcessErrors e set e.notes = ?1, e.updateid = ?2, e.updateDate = ?3 where e.documentId = ?4 and "
				+ NOTES_CONDITION, text(inOutParameterMap.get("p_notes")), id(inOutParameterMap.get("p_worker_id")), now(),
				id(inOutParameterMap.get("p_documentId")));
	}

	/**
	 * Sets the notes of several documents' error records (open errors, or deleted ones), updated now.
	 *
	 * @param map p_documentIds (not empty), p_notes, p_worker_id
	 */
	public int addNotesToProcessErrors(Map<Object, Object> map) {
		return jpql.update("update NyehmsProcessErrors e set e.notes = ?1, e.updateid = ?2, e.updateDate = ?3 where " + NOTES_CONDITION
				+ " and e.documentId in ?4", text(map.get("p_notes")), id(map.get("p_worker_id")), now(), LaftRows.longs(map.get("p_documentIds"), "p_documentIds"));
	}

	/** The addresses that can be a residence: ADDRESS_ID, ADDRESS, FLAG_ACTIVE, FILTER_TYPE, CREATE_DATE, END_DATE (MM/DD/YYYY). */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getAddressSameAsEntries() {
		return jpql.list(ADDRESS_SAME_AS_ENTRIES, row -> {
			Map<String, Object> map = LaftRows.map("ADDRESS_ID", "ADDRESS", "FLAG_ACTIVE", "FILTER_TYPE", "CREATE_DATE", "END_DATE").map(row);
			if (map == null) {
				return null;
			}
			JSONObject json = new JSONObject();
			json.putAll(map);
			return json;
		});
	}

	/**
	 * Adds an address that can be a residence (active, created now; stored as upper(trim(address)), as before).
	 *
	 * @param inOutParameterMap p_address, p_filterType, p_workerId
	 */
	@Transactional
	public int addRawProviderFacilityAddressSameAsResidence(Map<Object, Object> inOutParameterMap) {
		addresses.saveAndFlush(RawAddrCanbeRes.builder()
				.address(text(inOutParameterMap.get("p_address")))
				.flagActive("Y")
				.filterType(text(inOutParameterMap.get("p_filterType")))
				.createUid(text(inOutParameterMap.get("p_workerId")))
				.createDate(now())
				.build());
		return 1;
	}

	/**
	 * Sets the active flag of an address that can be a residence (ended now).
	 *
	 * @param inOutParameterMap addressId, flag_active, p_workerId
	 */
	public int updateAddressSameAsStatus(Map<Object, Object> inOutParameterMap) {
		return jpql.update("update RawAddrCanbeRes r set r.flagActive = ?1, r.endDate = ?2, r.lastUpdatedBy = ?3 where r.addressId = ?4",
				text(inOutParameterMap.get("flag_active")), now(), text(inOutParameterMap.get("p_workerId")), id(inOutParameterMap.get("addressId")));
	}

	/** The error description of one of a document's active error records (any one, as the former rownum < 2), or null. */
	public String getErrorMessage(int docId) {
		return jpql.one("select e.errorDesc from NyehmsProcessErrors e where e.documentId = ?1 and e.activeFlag = 'Y' fetch first 1 rows only",
				row -> (String) row.get(0), (long) docId);
	}

	/**
	 * The number of active addresses equal to upper(trim(p_address)) of a filter type.
	 *
	 * @param inOutParameterMap p_address, p_filterType
	 */
	public int checkCanBeResidenceAddressAlreadyAdded(Map<Object, Object> inOutParameterMap) {
		Long count = jpql.one("select count(*) from RawAddrCanbeRes r where r.flagActive = 'Y' and r.address = upper(trim(both from ?1)) and r.filterType = ?2",
				row -> (Long) row.get(0), text(inOutParameterMap.get("p_address")), text(inOutParameterMap.get("p_filterType")));
		return count == null ? 0 : count.intValue();
	}

	/** The open HIV lab errors counted by error: ERROR_CATEGORY, ERROR_TYPE, ERROR_DESC, COUNT (a BigDecimal), by category. */
	public List<Map<String, Object>> getLabErrorReportsByType(Map<String, String> inOutParameterMap) {
		return jpql.list(LAB_ERROR_REPORTS_BY_TYPE, LaftRows.map("ERROR_CATEGORY", "ERROR_TYPE", "ERROR_DESC", "COUNT"));
	}

	/** The raw HIV lab documents with open errors counted by lab: LAFT_STAFF, CLIA, PFI, LAB_NAME, COUNT, by lab name. */
	public List<Map<String, Object>> getLabErrorReportsByFacilityName(Map<String, String> inOutParameterMap) {
		return jpql.list(LAB_ERROR_REPORTS_BY_FACILITY, LaftRows.map("LAFT_STAFF", "CLIA", "PFI", "LAB_NAME", "COUNT"));
	}

	/**
	 * The reprised / deleted / edited HIV lab errors by worker and day: CREATE_DATE, YEAR_WEEK, MON_NAME, DAY_NAME,
	 * REPRISE_DELETE, UPDATEID, COUNT (all text).
	 *
	 * @param inOutParameterMap fromDate, toDate (MM/DD/YYYY; the day after toDate included, as before)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getLabErrorReportsByStatus(Map<String, String> inOutParameterMap) {
		return (List) statements.list(LAB_ERROR_REPORTS_BY_STATUS, Rows.map(), inOutParameterMap.get("fromDate"), inOutParameterMap.get("toDate"));
	}

	/**
	 * The reprised / deleted / edited HIV lab errors by lab and worker: LAB_NAME, CLIA, PFI, UPDATEID, REPRISE_CNT,
	 * DELETE_CNT, EDIT_CNT, by lab name.
	 *
	 * @param inOutParameterMap fromDate, toDate (MM/DD/YYYY; the day after toDate included, as before)
	 */
	public List<Map<String, Object>> getLabErrorStatusReportByLab(Map<String, String> inOutParameterMap) {
		return jpql.list(LAB_ERROR_STATUS_BY_LAB, LaftRows.map("LAB_NAME", "CLIA", "PFI", "UPDATEID", "REPRISE_CNT", "DELETE_CNT", "EDIT_CNT"),
				inOutParameterMap.get("fromDate"), inOutParameterMap.get("toDate"));
	}

	/** The error codes of a document's open validation errors (status 4): ERROR_ID, ERROR_CODE, ERROR_DESC. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getDocumentLabErrorsByCategory(String documentId) {
		return (List) jpql.list(DOCUMENT_ERRORS_BY_CATEGORY, LaftRows.map("ERROR_ID", "ERROR_CODE", "ERROR_DESC"), id(documentId));
	}

	/**
	 * Marks error codes of a document's open validation errors (status 4) as bypassed.
	 *
	 * @param inputParameterMap documentId, errorCodeList (the codes as text; not empty)
	 * @return the number of error code rows changed
	 */
	public int byPassDocumentError(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				update NyehmsProcessErrorsDetail d set d.bypassError = 'Y' where d.errorCode in ?1
				and d.errorId in (select e.errorId from NyehmsProcessErrors e where e.documentId = ?2 and e.activeFlag = 'Y' and e.processStatus = 4)""",
				LaftRows.longs(inputParameterMap.get("errorCodeList"), "errorCodeList"), id(inputParameterMap.get("documentId")));
	}

	/** {@code ${rawTableName}}: one of the raw document tables (null an empty text, as MyBatis rendered it). */
	private static String rawTable(Map<Object, Object> map) {
		Object name = map.get("rawTableName");
		if (name == null) {
			return "";
		}
		if (!RAW_TABLES.contains(name.toString())) {
			throw new IllegalArgumentException("Not a known rawTableName: " + name);
		}
		return name.toString();
	}
}
