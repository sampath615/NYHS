package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment.reports;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AhiAssignmentRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.SurveillanceAssignmentRepository;
import lombok.RequiredArgsConstructor;

/**
 * The assignment reports (formerly a MyBatis mapper with the same name and methods). Rows are maps keyed by the former
 * column labels (null values left out, a row without values null).
 * <p>
 * The AHI assignment list is Spring Data JPQL (SurveillanceAssignment, DocumentLocator). The other statements stay
 * native, their SQL unchanged and their values bound:
 * <ul>
 * <li>the status reports and document lists read the standardized table of the document type (the former
 * {@code ${stdTableName}}) and its file-name column ({@code ${FileNameReferenceColumn}}), so the table differs per call,
 * and they use Oracle (+) outer joins (also an INDEX hint and DUAL rows); the two names are checked against the names
 * LabCommonUtils.getStdTableName / getStdReferenceColumnNames give (a missing name is an empty text, as before);</li>
 * <li>the AHI report uses PIVOT;</li>
 * <li>the closure report is the procedure {@code pr_get_closure_activity_by_users} with a cursor.</li>
 * </ul>
 *
 * @author Sampath K Anumolu
 */
@Repository
@RequiredArgsConstructor
public class AssignmentReportsDAO {

	/** The tables LabCommonUtils.getStdTableName gives. */
	private static final Set<String> STD_TABLES = Set.of("STD_HIV_ECLRS_DOC", "STD_STD_ECLRS_DOC", "STD_TB_DOC",
			"STD_SYPHILIS_DOC", "STD_DEATH_DOC", "STD_CASA_DOC", "STD_DOCCS_DOC", "STD_DIN_DOCCS_DOC", "STD_PRF_DOC",
			"STD_NYCPRF_DOC", "STD_ACRF_DOC", "STD_AIRS_DOC", "STD_ADAP_DOC", "STD_SSDM_DOC", "STD_NDI_DOC", "STD_NYCIJR_DOC",
			"STD_NYC_DEATH_DOC", "STD_MEDICAID_DOC", "STD_OLD_CASES_DOC", "STD_MED_OLD_DOC", "STD_EPRF_OOC_UPLOAD",
			"STD_CCD_DOC", "STD_HL7_DOC", "STD_EXP_DOC", "STD_NYC_EHARS_DOC", "STD_NYC_EHARS_LAB_DOC", "STD_NDI_CAUSE_DOC",
			"STD_INS_DEATH_DOC", "STD_STD_OLD_DOC", "STD_HIE_DEATH_DOC", "STD_AHI_DOC", "STD_CLUSTER_DOC",
			"STD_CLUSTER_PARTNERS", "STD_STD_CONFIRMED_DOC", "STD_PCRF_DOC", "STD_BLACK_BOX_DOC", "STD_ICCR_DOC",
			"STD_QUALITY_CARE_DOC", "TRACKING_USER_ENTERED_ALIAS");

	/** The FILE_NAME columns LabCommonUtils.getStdReferenceColumnNames gives. */
	private static final Set<String> FILE_NAME_COLUMNS = Set.of("FILENAME", "MEDICAID_FILE", "ADAP_FILE", "DEATH_FILE",
			"TB_FILE", "DOCCS_FILE", "UPLOAD_FILENAME", "FILE_NAME", "STD_FILE");

	private static final String REGION_COUNTS = """
			SUM(cap_dist_cnt) as CAPITAL_DISTRICT,
			    SUM (syr_cnt) as SYRACUSE,
			    SUM (new_roch_cnt) as NEW_ROCHELLE,
			    SUM (long_is_cnt) as LONG_ISLAND,
			    SUM (roch_cnt) as ROCHESTER,
			    SUM( buff_cnt) as BUFFALO,
			    SUM (nyc_cnt ) as NEW_YORK_CITY,
			    SUM (co_cnt) as CENTRAL_OFFICE,
			    SUM(cap_dist_cnt)+ SUM (syr_cnt) + SUM (new_roch_cnt) + SUM (long_is_cnt) + SUM (roch_cnt) + SUM( buff_cnt) + SUM (nyc_cnt )+ SUM ( co_cnt) TOTAL,
			""";

	private static final String SORT_ORDER = """
			(case status
			            when 'Open' then 1
			            when 'Reopened' then 2
			            when 'Needs assignment' then 3
			            when 'Reassignment requested' then 4
			            when 'Pending ACRF review' then 5
			            when 'Rejected' then 6
			            else 7
			      end) as sort_order
			""";

	private static final String STATUS_AND_REGIONS = """
			CASE WHEN b.CURRENT_ASSIGNMENT_STATUS  NOT IN ('CLOSED','CLS_ADM') THEN s.status_description  ELSE s.activity_type_description  END STATUS,
			           CASE WHEN b.CURRENT_ASSIGNMENT_STATUS  NOT IN ('CLOSED','CLS_ADM') THEN b.CURRENT_ASSIGNMENT_STATUS  ELSE s.ACTIVITY_TYPE_CODE   END STATUS_CD,
			       (case when b.current_region_cd = '1' then 1 else 0 end) as cap_dist_cnt,
			       (case when b.current_region_cd = '2' then 1 else 0 end) as syr_cnt,
			       (case when b.current_region_cd = '3' then 1 else 0 end) as new_roch_cnt,
			       (case when b.current_region_cd = '4' then 1 else 0 end) as long_is_cnt,
			       (case when b.current_region_cd = '5' then 1 else 0 end) as roch_cnt,
			       (case when b.current_region_cd = '6' then 1 else 0 end) as buff_cnt,
			       (case when b.current_region_cd = '7' then 1 else 0 end) as nyc_cnt,
			       (case when b.current_region_cd = '9' then 1 else 0 end) as co_cnt
			""";

	private static final String EHARS_CATEGORY = """
			 case when
			                 d.hiv_aids_cdc is null and e.hiv_cdc is null and e.aids_cdc is null then 'Not In eHARS'
			           when
			                (d.hiv_aids_cdc = 'N' and e.hiv_cdc = 'N' and e.aids_cdc = 'N') or
			                (d.hiv_aids_cdc = 'N' and e.hiv_cdc is null and e.aids_cdc is null) or
			                (d.hiv_aids_cdc is null and e.hiv_cdc = 'N' and e.aids_cdc = 'N') then 'Does Not Meet Case Definition'
			           when
			                (d.hiv_aids_cdc = 'Y' or e.hiv_cdc = 'Y' or e.aids_cdc = 'Y') and
			                (d.hiv_aids_cdc_elig is null or d.hiv_aids_cdc_elig = 'N') and
			                (d.hiv_aids_cdc_elig is null or e.hiv_aids_cdc_elig = 'N') then 'Meets Case Definition Does Not Meet Eligibility'
			            when
			                d.hiv_aids_cdc_elig = 'Y' or e.hiv_aids_cdc_elig = 'Y' then 'Meets Case Definition Meets Eligibility'
			                ELSE NULL END CATEGORY_DESCRIPTION
			""";

	private final NativeStatements statements;
	private final SurveillanceAssignmentRepository assignments;

	/**
	 * Assignment counts per status and region of one document type's documents, of one file or created between two days;
	 * a TOTALS row and, for a file, a WAITING IN THE WINGS row.
	 *
	 * @param inputParameterMap documentType, stdTableName, fileName + FileNameReferenceColumn, or fromDate + toDate
	 *        (MM/DD/YYYY)
	 */
	public List<Map<String, String>> getAssignmentStatusReportByDocumentByRegion(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		String table = table(inputParameterMap);
		sql.add("SELECT STATUS, STATUS_CD,\n").param(inputParameterMap.get("documentType")).add(" as DOCUMENT_TYPE,\n")
				.add(REGION_COUNTS).add(SORT_ORDER)
				.add("FROM (select ").add(STATUS_AND_REGIONS)
				.add("      from ").add(table).add(" a, surveillance_assignment b, coded_assignment_status s, coded_activity_type s\n")
				.add("      where\n");
		if (present(inputParameterMap.get("fileName"))) {
			sql.add("trim(a.").add(fileNameColumn(inputParameterMap)).add(") = ").param(inputParameterMap.get("fileName")).add("\n");
		} else {
			sql.add("b.CREATE_DATE between TO_DATE(").param(inputParameterMap.get("fromDate")).add(",'MM/DD/YYYY') and TO_DATE(")
					.param(inputParameterMap.get("toDate")).add(",'MM/DD/YYYY')+1\n");
		}
		sql.add("""
				      and a.document_id = b.triggering_doc_id
				      and b.current_assignment_status = s.status_cd
				      and b.current_activity_type_code = s.activity_type_code (+) )
				GROUP BY status,status_CD
				UNION
				select 'TOTALS' as status,null,null,null,null,null,null,null,null,null,null,null,9 as sort_order from dual
				""");
		if (present(inputParameterMap.get("fileName"))) {
			sql.add("UNION\nselect 'WAITING IN THE WINGS' as status,\n'WAITING IN THE WINGS',\n").param(inputParameterMap.get("documentType"))
					.add("""
							 as DOCUMENT_TYPE,
							null,
							null,
							null,
							null,
							NULL,
							null,
							null,
							null,
							count(*) TOTAL,
							10 as sort_order
							from person_doc_association
							where surv_assess_status = '2'
							   and dissassociation_date is null
							   and document_id in (select a.document_id
							                        from\s""").add(table).add(" a\n where\n trim(a.").add(fileNameColumn(inputParameterMap))
					.add(") = ").param(inputParameterMap.get("fileName")).add("\n)\n");
		}
		sql.add("order by sort_order, status");
		return maps(sql);
	}

	/**
	 * Assignment counts per status, region and test type of the HIV lab documents created between two days (optionally
	 * of one test type), and a TOTALS row.
	 *
	 * @param inputParameterMap fromDate, toDate (MM/DD/YYYY), testType
	 */
	public List<Map<String, String>> getAssignmentStatusReportByDocumentByRegionForHIV_LAB(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		sql.add("SELECT STATUS, STATUS_CD, TEST_TYPE_CD as DOCUMENT_TYPE,\n").add(REGION_COUNTS).add(SORT_ORDER)
				.add("FROM (select /*+ INDEX(D,DOCUMENT_LOCATOR_PK)*/ ").add(STATUS_AND_REGIONS)
				.add("""
						,
						       D.TEST_TYPE_CD
						      from
						       surveillance_assignment b, coded_assignment_status s, coded_activity_type s , DOCUMENT_LOCATOR D
						      where
						              b.CREATE_DATE  BETWEEN TO_DATE(""").param(inputParameterMap.get("fromDate"))
				.add(",'MM/DD/YYYY') AND TO_DATE(").param(inputParameterMap.get("toDate")).add("""
						,'MM/DD/YYYY') + 1
						      and b.current_assignment_status = s.status_cd
						      AND d.document_type_code ='HIV_LAB'
						""");
		if (present(inputParameterMap.get("testType"))) {
			sql.add("AND d.TEST_TYPE_CD = ").param(inputParameterMap.get("testType")).add("\n");
		}
		sql.add("""
				      AND b.triggering_doc_id  = d.DOCUMENT_ID
				      and b.current_activity_type_code = s.activity_type_code (+) )
				GROUP BY status,status_CD,TEST_TYPE_CD
				UNION
				select 'TOTALS' as status,null,null,null,null,null,null,null,null,null,null,null,10 as sort_order from dual
				order by sort_order, status,DOCUMENT_TYPE""");
		return maps(sql);
	}

	/**
	 * Assignment counts per status and eHARS status (not in eHARS, does not meet the case definition, meets it but not
	 * the eligibility, eligible) of one document type's documents, of one file or created between two days; a TOTALS row
	 * (always 1: count(*) from DUAL, kept as it was) and, for a file, a WAITING IN THE WINGS row.
	 *
	 * @param inputParameterMap documentType, testType, stdTableName, fileName + FileNameReferenceColumn, or fromDate +
	 *        toDate (MM/DD/YYYY)
	 */
	public List<Map<String, String>> getAssignmentStatusReportByEharsStatus(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		String table = table(inputParameterMap);
		sql.add("""
				with main_sql as (
				select b.triggering_doc_id, b.document_id, b.stateno, b.current_assignment_status,
				       case when b.current_assignment_status not in ('CLOSED','CLS_ADM') then s.status_description else c.activity_type_description end status,
				       CASE WHEN b.CURRENT_ASSIGNMENT_STATUS  NOT IN ('CLOSED','CLS_ADM') THEN b.CURRENT_ASSIGNMENT_STATUS  ELSE c.ACTIVITY_TYPE_CODE   END STATUS_CD

				from\s""").add(table).add(" a, surveillance_assignment b, coded_assignment_status s, coded_activity_type c, document_locator doc_loc\nwhere\n");
		if (present(inputParameterMap.get("fileName"))) {
			sql.add("trim(").add(fileNameColumn(inputParameterMap)).add(") = ").param(inputParameterMap.get("fileName")).add("\n");
		} else {
			sql.add("b.CREATE_DATE between TO_DATE(").param(inputParameterMap.get("fromDate")).add(",'MM/DD/YYYY') and TO_DATE(")
					.param(inputParameterMap.get("toDate")).add(",'MM/DD/YYYY')+1\n");
		}
		sql.add("  and doc_loc.document_id = b. triggering_doc_id\n");
		if (present(inputParameterMap.get("testType"))) {
			sql.add("AND doc_loc.TEST_TYPE_CD = ").param(inputParameterMap.get("testType")).add("\n");
		}
		sql.add("""
				  and a.document_id = b.triggering_doc_id
				  and b.current_assignment_status = s.status_cd
				  and b.current_activity_type_code = c.activity_type_code (+)
				)

				  select status, STATUS_CD,
				""").param(inputParameterMap.get("documentType")).add("""
				 as DOCUMENT_TYPE,
				  	     sum(case when
				                 hiv_aids_cdc is null and hiv_cdc is null and aids_cdc is null then 1 else 0 end) as NOT_IN_EHARS_CNT,
				         sum(case when
				                (hiv_aids_cdc = 'N' and hiv_cdc = 'N' and aids_cdc = 'N') or
				                (hiv_aids_cdc = 'N' and hiv_cdc is null and aids_cdc is null) or
				                (hiv_aids_cdc is null and hiv_cdc = 'N' and aids_cdc = 'N') then 1 else 0 end) as DOES_NOT_MCD_CNT,
				         sum(case when
				                (hiv_aids_cdc = 'Y' or hiv_cdc = 'Y' or aids_cdc = 'Y') and
				                (d_hiv_aids_cdc_elig is null or d_hiv_aids_cdc_elig = 'N') and
				                (e_hiv_aids_cdc_elig is null or e_hiv_aids_cdc_elig = 'N') then 1 else 0 end) as MCD_NOT_ELIG_CNT,
				         sum(case when
				                d_hiv_aids_cdc_elig = 'Y' or e_hiv_aids_cdc_elig = 'Y' then 1 else 0 end) as ELIG_CNT,
				         count(*) as TOTAL,
				""").add(SORT_ORDER).add("""
				  from (select m.status, m.STATUS_CD, d.hiv_aids_cdc, e.hiv_cdc, e.aids_cdc,     /* case def */
				               d.hiv_aids_cdc_elig as d_hiv_aids_cdc_elig, e.hiv_aids_cdc_elig as e_hiv_aids_cdc_elig,   /* eligibility */
				               row_number() over (partition by m.stateno, m.document_id  order by m.stateno, m.document_id) as rwnum
				        from main_sql m, ehars_person_nyc d, nyehms_ehars_stateno_map e
				        where m.stateno = d.nyehms_stateno(+)
				          and m.stateno = e.nyehms_stateno(+)
				       )
				  where rwnum=1
				  group by status, STATUS_CD
				  UNION
				select 'TOTALS' as status,null, null,null,null,null,null,count(*),9 as sort_order from dual
				""");
		if (present(inputParameterMap.get("fileName"))) {
			sql.add("UNION\nselect 'WAITING IN THE WINGS' as status, 'WAITING IN THE WINGS',").param(inputParameterMap.get("documentType"))
					.add("""
							 as DOCUMENT_TYPE, null,null,null,null,count(*),10 as sort_order
							from person_doc_association
							where surv_assess_status = '2'
							   and dissassociation_date is null
							   and document_id in (select a.document_id
							                        from\s""").add(table).add(" a\n where\n trim(a.").add(fileNameColumn(inputParameterMap))
					.add(") = ").param(inputParameterMap.get("fileName")).add("\n )\n");
		}
		sql.add(" order by sort_order, status");
		return maps(sql);
	}

	/**
	 * The assignments (STATENO, CATEGORY_DESCRIPTION = region, ASSIGNMENT_STATUS, WORKER_NAME) of one document type's
	 * documents: HIV/STD lab documents by type and creation days, other documents of one file or by creation days;
	 * optionally of one status, activity type and region.
	 *
	 * @param inputParameterMap documentType, testType, stdTableName, fileName + FileNameReferenceColumn, fromDate,
	 *        toDate (MM/DD/YYYY), assignmentStatus, activityTypeCode, regionId
	 */
	public List<Map<String, String>> getAssignmentDocumentsByStatusByDocumentByRegionByFileNameOrByDate(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		sql.add("""
				select s.STATENO, region.REGION_DESCRIPTION CATEGORY_DESCRIPTION, CASE WHEN s.CURRENT_ASSIGNMENT_STATUS  NOT IN ('CLOSED','CLS_ADM') THEN coded_assignment_status.status_description  ELSE coded_activity_type.activity_type_description  END ASSIGNMENT_STATUS, PKG_TRK_COMMON.fn_get_fullname(U.LAST_NAME ,U.FIRST_NAME ,U.MIDDLE_NAME , NULL) WORKER_NAME
				from SURVEILLANCE_ASSIGNMENT s ,  WORKER_ASSIGNMENT W , USER_REGION R , NYEHMS_USERS U , CODED_REGION region, coded_assignment_status coded_assignment_status, coded_activity_type coded_activity_type,
				""");
		documentsOfType(sql, inputParameterMap, true);
		assignmentStatus(sql, inputParameterMap);
		activityType(sql, inputParameterMap);
		if (present(inputParameterMap.get("regionId"))) {
			sql.add("and CURRENT_REGION_CD = ").param(inputParameterMap.get("regionId")).add("\n");
		}
		sql.add("""
				and s.current_assignment_status = coded_assignment_status.status_cd(+)
				        and s.CURRENT_REGION_CD = region.REGION_CODE(+)
				        and s.current_activity_type_code = coded_activity_type.activity_type_code (+)
						AND s.CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID(+)
						AND W.WORKER_REGION_ID = R.WORKER_REGION_ID (+)
						AND R.WORKER_ID = U.WORKER_ID(+) order by REGION_DESCRIPTION, CURRENT_ASSIGNMENT_STATUS, WORKER_NAME asc""");
		return maps(sql);
	}

	/**
	 * The assignments (STATENO, WORKER_NAME, ASSIGNMENT_STATUS, CATEGORY_DESCRIPTION = eHARS status) of one document
	 * type's documents, as {@link #getAssignmentDocumentsByStatusByDocumentByRegionByFileNameOrByDate}, optionally of one
	 * eHARS status. Kept as it was: the "Does Not Meet Eligibility" label tests {@code d.hiv_aids_cdc_elig is null or
	 * e.hiv_aids_cdc_elig = 'N'}, the matching filter {@code e.hiv_aids_cdc_elig is null or ...}.
	 *
	 * @param inputParameterMap as for the region list, and eharsStatus
	 */
	public List<Map<String, String>> getEharsAssignmentDocumentsByStatusByDocumentByFileNameOrByDate(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		sql.add("""
				SELECT S.stateno,PKG_TRK_COMMON.fn_get_fullname(U.LAST_NAME ,U.FIRST_NAME ,U.MIDDLE_NAME , NULL) WORKER_NAME, CASE WHEN s.CURRENT_ASSIGNMENT_STATUS  NOT IN ('CLOSED','CLS_ADM') THEN coded_assignment_status.status_description  ELSE coded_activity_type.activity_type_description  END ASSIGNMENT_STATUS,
				""").add(EHARS_CATEGORY).add("""
				FROM SURVEILLANCE_ASSIGNMENT S , WORKER_ASSIGNMENT W ,  USER_REGION R ,NYEHMS_USERS U , ehars_person_nyc d, nyehms_ehars_stateno_map e,  coded_activity_type coded_activity_type, coded_assignment_status coded_assignment_status,
				""");
		documentsOfType(sql, inputParameterMap, false);
		sql.add("""
				and s.current_assignment_status = coded_assignment_status.status_cd(+)
				AND    S.CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID (+)
				AND    W.WORKER_REGION_ID = R.WORKER_REGION_ID (+)
				AND R.WORKER_ID = U.worker_id(+)
				and s.current_activity_type_code = coded_activity_type.activity_type_code (+)
				AND S.stateno = d.nyehms_stateno(+)
				AND S.stateno = e.nyehms_stateno(+)
				""");
		assignmentStatus(sql, inputParameterMap);
		activityType(sql, inputParameterMap);
		Object eharsStatus = inputParameterMap.get("eharsStatus");
		if ("Not In eHARS".equals(eharsStatus)) {
			sql.add("""
					AND case when
					                d.hiv_aids_cdc is null and e.hiv_cdc is null and e.aids_cdc is null then 1 else 0 end =1
					""");
		}
		if ("Does Not Meet Case Definition".equals(eharsStatus)) {
			sql.add("""
					AND case when
					               (d.hiv_aids_cdc = 'N' and e.hiv_cdc = 'N' and e.aids_cdc = 'N') or
					               (d.hiv_aids_cdc = 'N' and e.hiv_cdc is null and e.aids_cdc is null) or
					               (d.hiv_aids_cdc is null and e.hiv_cdc = 'N' and e.aids_cdc = 'N') then 1 else 0 end = 1
					""");
		}
		if ("Meets Case Definition Does Not Meet Eligibility".equals(eharsStatus)) {
			sql.add("""
					AND case when
					               (d.hiv_aids_cdc = 'Y' or e.hiv_cdc = 'Y' or e.aids_cdc = 'Y') and
					               (d.hiv_aids_cdc_elig is null or d.hiv_aids_cdc_elig = 'N') and
					               (e.hiv_aids_cdc_elig is null or e.hiv_aids_cdc_elig = 'N') then 1 else 0 end =1
					""");
		}
		if ("Meets Case Definition Meets Eligibility".equals(eharsStatus)) {
			sql.add("""
					AND case when
					               d.hiv_aids_cdc_elig = 'Y' or e.hiv_aids_cdc_elig = 'Y' then 1 else 0 end = 1
					""");
		}
		return maps(sql);
	}

	/**
	 * The STATENOs waiting in the wings (surveillance assessment status 2, still associated) of one file's active
	 * documents.
	 *
	 * @param inputParameterMap stdTableName, FileNameReferenceColumn, fileName
	 */
	public List<Map<String, String>> getWaitingForWingsStatenosByDocumentByFileName(Map<Object, Object> inputParameterMap) {
		Sql sql = new Sql();
		sql.add("""
				SELECT STATENO
				from person_doc_association
				where surv_assess_status = '2'
				   and dissassociation_date is null
				   and document_id in (select a.document_id
				                        from\s""").add(table(inputParameterMap))
				.add(" a, document_locator b\n where a.document_id = b.document_id\n and  trim(a.").add(fileNameColumn(inputParameterMap)).add(") = ")
				.param(inputParameterMap.get("fileName")).add("\n and b.is_active = 'Y')");
		return maps(sql);
	}

	/**
	 * The AHI report: per status (activity type, or assignment status) and document type the assignments of each region
	 * (R1 ... R9) with an AHI flag; {@code ninty}: flagged 'Y' and created in the last 90 days.
	 * <p>
	 * Native: Oracle PIVOT.
	 *
	 * @param inputData ahiFlag
	 */
	public List<Map<String, String>> getAssignmentAHIReports(Map<String, String> inputData) {
		Sql sql = new Sql();
		sql.add("""
				select * from (
				select   current_region_cd ,
				case when ACTIVITY_TYPE_DESCRIPTION is null then current_assignment_status else ACTIVITY_TYPE_DESCRIPTION end current_status,
				case when ACTIVITY_TYPE_CODE is null then current_assignment_status else ACTIVITY_TYPE_CODE end current_status_code,
				case when TEST_TYPE_CD is null then DOCUMENT_TYPE_CODE else TEST_TYPE_CD end doc_type
				from SURVEILLANCE_ASSIGNMENT surv,
				--CODED_REGION,
				CODED_ACTIVITY_TYPE,
				document_locator doc
				where
				""");
		if ("ninty".equals(inputData.get("ahiFlag"))) {
			sql.add("AHI_FLAG = 'Y' and surv.create_date > sysdate-90 and\n");
		} else {
			sql.add("AHI_FLAG = ").param(inputData.get("ahiFlag")).add(" and\n");
		}
		sql.add("""

				-- and region_code = current_region_cd
				CURRENT_ACTIVITY_TYPE_CODE = ACTIVITY_TYPE_CODE (+)
				and TRIGGERING_DOC_ID = doc.document_id )
				PIVOT ( COUNT(*) FOR current_region_cd in (1 R1,2 R2,3 R3,4 R4,5 R5,6 R6,7 R7,9 R9))
				order by current_status_code, doc_type""");
		return maps(sql);
	}

	/**
	 * The AHI assignments of one region, document type and status: STATENO, AHI_UPDATE_DATE, AHI_UPDATE_UID.
	 * {@code ninty}: flagged 'Y' and created in the last 90 days (the application server's clock).
	 *
	 * @param inputData documentType, regionId, assignmentStatus, ahiFlag
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getAHIAssignmentsByRegionAndCategory(Map<String, String> inputData) {
		List<AhiAssignmentRow> rows;
		if ("ninty".equals(inputData.get("ahiFlag"))) {
			rows = assignments.findRecentAhiAssignments(inputData.get("documentType"), inputData.get("regionId"),
					inputData.get("assignmentStatus"), Timestamp.valueOf(LocalDateTime.now().minusDays(90)));
		} else {
			rows = assignments.findAhiAssignments(inputData.get("documentType"), inputData.get("regionId"),
					inputData.get("assignmentStatus"), inputData.get("ahiFlag"));
		}
		List<Map<String, Object>> maps = new ArrayList<>();
		for (AhiAssignmentRow row : rows) {
			Map<String, Object> map = new HashMap<>();
			if (row.stateno() != null) {
				map.put("STATENO", row.stateno());
			}
			if (row.ahiUpdateDate() != null) {
				map.put("AHI_UPDATE_DATE", row.ahiUpdateDate());
			}
			if (row.ahiUpdateUid() != null) {
				map.put("AHI_UPDATE_UID", row.ahiUpdateUid());
			}
			maps.add(map.isEmpty() ? null : map);
		}
		return (List) maps;
	}

	/**
	 * The closure activity report: calls {@code pr_get_closure_activity_by_users} (p_from_date, p_to_date, p_region_cd
	 * as text, p_module_id, p_worker_id as whole numbers) and puts its rows (JSONObjects) into the map as
	 * {@code pcursor}.
	 *
	 * @return an empty list (the procedure returns its rows through the cursor)
	 */
	public List<JSONObject> getAssignmentClosureStatusReport(Map<Object, Object> map) {
		List<Object> results = statements.call("pr_get_closure_activity_by_users",
				in(map.get("p_from_date"), String.class),
				in(map.get("p_to_date"), String.class),
				in(map.get("p_region_cd"), String.class),
				cursor(Rows.json()),
				in(map.get("p_module_id"), Integer.class),
				in(map.get("p_worker_id"), Integer.class));
		map.put("pcursor", results.get(3));
		return new ArrayList<>();
	}

	/** The document part of the two assignment lists (from the last table on, the former inner {@code <choose>}). */
	private static void documentsOfType(Sql sql, Map<Object, Object> inputParameterMap, boolean labByTestType) {
		if ("STD_HIV_ECLRS_DOC".equals(inputParameterMap.get("stdTableName"))) {
			sql.add("DOCUMENT_LOCATOR D\n where s.triggering_doc_id  = d.DOCUMENT_ID\n");
			if (!labByTestType || "HIV_LAB".equals(inputParameterMap.get("documentType"))) {
				sql.add("and D.DOCUMENT_TYPE_CODE = ").param(inputParameterMap.get("documentType")).add("\n");
				if (present(inputParameterMap.get("testType"))) {
					sql.add("AND D.TEST_TYPE_CD = ").param(inputParameterMap.get("testType")).add("\n");
				}
			} else {
				sql.add("and D.TEST_TYPE_CD = ").param(inputParameterMap.get("documentType")).add("\n");
			}
			sql.add("and s.CREATE_DATE between TO_DATE(").param(inputParameterMap.get("fromDate")).add(",'MM/DD/YYYY') and TO_DATE(")
					.param(inputParameterMap.get("toDate")).add(labByTestType ? ",'MM/DD/YYYY') +1\n" : ",'MM/DD/YYYY')+1\n");
		} else {
			sql.add(table(inputParameterMap)).add(" std\n WHERE TRIGGERING_DOC_ID = std.document_id\n");
			if (present(inputParameterMap.get("fileName"))) {
				sql.add("and std.").add(fileNameColumn(inputParameterMap)).add(" = ").param(inputParameterMap.get("fileName")).add("\n");
			} else {
				sql.add("and s.CREATE_DATE between TO_DATE(").param(inputParameterMap.get("fromDate")).add(",'MM/DD/YYYY') and TO_DATE(")
						.param(inputParameterMap.get("toDate")).add(",'MM/DD/YYYY')+1\n");
			}
		}
	}

	/** The former {@code <choose>} on assignmentStatus. */
	private static void assignmentStatus(Sql sql, Map<Object, Object> inputParameterMap) {
		Object status = inputParameterMap.get("assignmentStatus");
		if (status != null && status.equals("CLOSED")) {
			sql.add("and CURRENT_ASSIGNMENT_STATUS in ('CLOSED', 'CLS_ADM')\n");
		} else if (present(status)) {
			sql.add("and CURRENT_ASSIGNMENT_STATUS = UPPER(").param(status).add(")\n");
		}
	}

	/** The former {@code <if>} on activityTypeCode (the value bound three times, as before). */
	private static void activityType(Sql sql, Map<Object, Object> inputParameterMap) {
		Object activityType = inputParameterMap.get("activityTypeCode");
		if (present(activityType)) {
			sql.add("AND CASE WHEN ").param(activityType).add(" IS NULL THEN '1' ELSE ").param(activityType)
					.add(" END\n = CASE WHEN ").param(activityType).add(" IS NULL THEN '1' ELSE S.CURRENT_ACTIVITY_TYPE_CODE  END\n");
		}
	}

	/** OGNL {@code x != null and x != ''}. */
	private static boolean present(Object value) {
		return value != null && !"".equals(value);
	}

	/** {@code ${stdTableName}}: one of the standardized document tables (null an empty text, as MyBatis rendered it). */
	private static String table(Map<Object, Object> inputParameterMap) {
		return allowed(inputParameterMap.get("stdTableName"), STD_TABLES, "stdTableName");
	}

	/** {@code ${FileNameReferenceColumn}}: one of the file-name columns (null an empty text, as MyBatis rendered it). */
	private static String fileNameColumn(Map<Object, Object> inputParameterMap) {
		return allowed(inputParameterMap.get("FileNameReferenceColumn"), FILE_NAME_COLUMNS, "FileNameReferenceColumn");
	}

	private static String allowed(Object name, Set<String> names, String what) {
		if (name == null) {
			return "";
		}
		if (!names.contains(name.toString())) {
			throw new IllegalArgumentException("Not a known " + what + ": " + name);
		}
		return name.toString();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private List<Map<String, String>> maps(Sql sql) {
		return (List) statements.list(sql.text.toString(), Rows.map(), sql.values.toArray());
	}

	/** SQL text with its parameters numbered ?1, ?2, ... in order (each bound as text, as the callers' values are). */
	private static final class Sql {

		private final StringBuilder text = new StringBuilder();
		private final List<Object> values = new ArrayList<>();

		Sql add(String part) {
			text.append(part);
			return this;
		}

		Sql param(Object value) {
			values.add(NativeStatements.text(value));
			text.append('?').append(values.size());
			return this;
		}
	}
}
