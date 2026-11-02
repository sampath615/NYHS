package gov.ny.state.doh.bhae.nyehms.web.spring.repository.assignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.DocumentLocator;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClosedAssignmentCount;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.OpenAssignmentCount;

/**
 * The assignment report queries (replaces the SQL that AssignmentReportsService built with string concatenation on a raw
 * connection). Native SQL: they use an Oracle package function, an optimizer hint, (+) outer joins and a materialized
 * view. Values are bound parameters; {@code region} "ALL" means every region, as before. The entity type is only the
 * repository's anchor: these queries return rows, not entities.
 */
public interface AssignmentReportRepository extends JpaRepository<DocumentLocator, Integer> {

	/** Open assignments per worker, region and document type (rows: see {@link OpenAssignmentCount#of}). */
	@Query(nativeQuery = true, value = """
			SELECT /*+ INDEX (W,WORKER_ASSIGNMENT_SH_PK) */ CASE WHEN N.LEGACY_UID IS NULL THEN 'Open Not Assigned' ELSE
			pkg_trk_common.fn_get_fullname (N.last_name, N.first_name, N.middle_name, NULL) END WORKER_NAME, U.worker_id,
			CURRENT_REGION_CD, D.DOCUMENT_TYPE_CODE, COUNT (*) COUNT, SUM(CASE WHEN CREATE_DATE < TO_DATE('01/01/2025',
			'MM/DD/YYYY') and CREATE_DATE > SYSDATE - 60 THEN 0 WHEN CREATE_DATE < SYSDATE-30 THEN 1 ELSE 0 END ) "30DAYS"
			FROM SURVEILLANCE_ASSIGNMENT S, WORKER_ASSIGNMENT W, USER_REGION U, NYEHMS_USERS N, document_locator D WHERE
			S.TRIGGERING_DOC_ID = D.DOCUMENT_ID AND S.CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID(+) AND
			CURRENT_ASSIGNMENT_STATUS IN ('OPEN', 'REOPEN', 'OPEN_ND', 'REASSGN') AND W.WORKER_REGION_ID =
			U.WORKER_REGION_ID(+) AND U.WORKER_ID = N.WORKER_ID(+) and (:region = 'ALL' or CURRENT_REGION_CD = :region)
			and NOT EXISTS (SELECT 1 FROM coded_master_setup m WHERE m.variable_name = 'DOC_TYPE_EXCLUDE_VIEW_R' AND
			m.variable_code = D.document_type_code) GROUP BY CASE WHEN N.LEGACY_UID IS NULL THEN 'Open Not Assigned' ELSE
			pkg_trk_common.fn_get_fullname (N.last_name, N.first_name, N.middle_name, NULL) END, U.worker_id,
			CURRENT_REGION_CD, D.DOCUMENT_TYPE_CODE
			""")
	List<Object[]> findOpenAssignments(String region);

	/** Closed assignments per region, worker and month between two dates (MM/DD/YYYY, both included). */
	@Query(nativeQuery = true, value = """
			SELECT ACTUAL_REGION_CD , pkg_trk_common.fn_get_fullname(LAST_NAME,FIRST_NAME,MIDDLE_NAME,NULL ) WORKER_NAME,
			CLOSED_CNT, CLS_ADM_CNT, CLOSED_UNASSIGNED_CNT, MONTH FROM ( SELECT ACTUAL_REGION_CD,ACTIVITY_DONE_BY, TO_CHAR
			(activity_date, 'MON') || TO_CHAR (activity_date, 'YYYY') MONTH, sum(case when ACTUAL_STATUS = 'CLOSED' then 1
			else 0 end) CLOSED_CNT , sum(case when ACTUAL_STATUS = 'CLS_ADM' then 1 else 0 end) CLS_ADM_CNT, sum(case when
			ACTUAL_STATUS = 'CLOSED_UNASSIGNED' then 1 else 0 end) CLOSED_UNASSIGNED_CNT FROM assign_report_mview WHERE
			ACTIVITY_DATE BETWEEN TO_DATE (:fromDate, 'MM/DD/YYYY') AND TO_DATE (:toDate, 'MM/DD/YYYY')+1 AND ACTUAL_STATUS
			in ('CLOSED', 'CLS_ADM', 'CLOSED_UNASSIGNED') and (:region = 'ALL' or ACTUAL_REGION_CD = :region) GROUP BY
			ACTUAL_REGION_CD,ACTIVITY_DONE_BY, TO_CHAR (activity_date, 'MON') || TO_CHAR (activity_date, 'YYYY'))M,
			NYEHMS_USERS U WHERE M.ACTIVITY_DONE_BY = U.worker_id
			""")
	List<Object[]> findClosedAssignments(String fromDate, String toDate, String region);

	default List<OpenAssignmentCount> findOpenAssignmentCounts(String region) {
		return findOpenAssignments(region).stream().map(OpenAssignmentCount::of).toList();
	}

	default List<ClosedAssignmentCount> findClosedAssignmentCounts(String fromDate, String toDate, String region) {
		return findClosedAssignments(fromDate, toDate, region).stream().map(ClosedAssignmentCount::of).toList();
	}
}
