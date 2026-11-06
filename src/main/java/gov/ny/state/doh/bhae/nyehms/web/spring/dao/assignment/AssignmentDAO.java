package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentActivityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentWorkerRegionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment.AssignmentsListVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.person.PersonDocHistoryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.AssignmentActivity;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.TypedRowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import lombok.RequiredArgsConstructor;

/**
 * The surveillance assignments: lists, details, assign / reassign / reject / revoke / reopen / close, the reviews of
 * closed assignments, the reabstraction and its comparison of the two case report forms, and the viral load review
 * (formerly a MyBatis mapper with the same name and methods; the callers are unchanged).
 * <ul>
 * <li>JPQL over the entities (SurveillanceAssignment, NyehmsWorkerRegionsVw, UserRegion, CodedRegion,
 * CodedActivityType, DocumentLocator, AssignmentActivity, CodedDisplayFileName and the assignment code tables and
 * views of {@code entity.assignment}) for the code lists, the worker and region lists, the activity lists, the
 * review / AHI / reabstraction updates, the activity insert (key from ASSIGNMENT_ACTIVITY_SEQ, ACTIVITY_DATE the
 * database's SYSDATE) and the compare rows (a JPQL insert: CO_ACRF_REAB_COL has no known key). The PL/SQL functions
 * PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO and pkg_trk_common.fn_get_fullname are called by name, SYSDATE is the
 * database's; the TO_CHAR(..., 'MM/DD/YYYY[ HH:MI:SS AM]') of the activity and viral load dates is done in Java.</li>
 * <li>The 14 stored procedure calls (PKG_ASSIGNMENT.*, PKG_CREATE_ASSINGMENT_MANUAL, pr_nyehms_ehars_person_view)
 * through {@link NativeStatements#call}; OUT values and cursor rows (mapped as the former result maps mapped them) are
 * put into the caller's map under the same keys as before.</li>
 * <li>Native SQL, unchanged, values bound: loadClosedAssignmentsForReview, getAssignmentAndAddressDetails and
 * getPriorityAssignmentsList join the pipelined PL/SQL table function TABLE(pkg_assignment.fn_assignment_address(...))
 * (and TABLE(get_user_permissions(...))), which JPQL cannot express; getunknownOutOfStateProviderDocuments keeps its
 * Oracle index hint; checkForNonCaseClosureCateogory selects a PL/SQL function FROM DUAL (JPQL has no FROM-less
 * select); the reabstraction reads (getRawCRF*, get*DiffSrcId) read a table, columns and key column chosen at runtime
 * (the former ${...}; the names are now checked: the tables against those the reabstraction passes, the columns and
 * the document type as plain identifiers) and SELECT * of them; getOpenCaseloadSummaryDataReport is SELECT * of a view
 * whose columns go to the browser as they are.</li>
 * </ul>
 * Behaviour kept as it was, including these bugs: loadClosedAssignmentsForReview labels both ACRF_DOCUMENT_ID and
 * PCRF_DOCUMENT_ID as ACRF_DOC_ID (the first wins, PCRF ids are never returned); getAllRegionsList joins USER_REGION and
 * CODED_REGION without a join condition (every active SURV region once per matching USER_REGION row);
 * getLabFullDiffSrcId / getCD4FullDiffSrcId return the first column of SELECT * (whatever it is); getRawCRFDocData,
 * getRawCRFData and getRawCRFDataObj are the same statement. Changed on purpose: getPriorityAssignmentsList's region
 * code (from the request) was pasted into the SQL (${p_regionCode}); it is now a bound number (a region code that is
 * not a number is refused instead of being run as SQL).
 */
@Repository
@RequiredArgsConstructor
public class AssignmentDAO {

	/** TO_CHAR(x, 'MM/DD/YYYY HH:MI:SS AM') */
	private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	// ---------------------------------------------------------------------------------------------------------------
	// assignment lists (stored procedures)

	/**
	 * The assignments of the worker and supervisor roles: calls PKG_assignment.SP_assignment_list and puts its rows
	 * (AssignmentsListVO) into the map as refCursor.
	 *
	 * @return an empty list (the rows come through the map, as before)
	 */
	public List<AssignmentsListVO> getAssignmentsList(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_assignment.SP_assignment_list",
				param(map.get("p_workerId"), String.class),
				param(map.get("p_workerRegionId"), String.class),
				param(map.get("p_assignmentType"), String.class),
				param(map.get("p_startDate"), Timestamp.class),
				param(map.get("p_endDate"), Timestamp.class),
				cursor(ASSIGNMENTS_LIST),
				param(map.get("p_calling_app_name"), String.class),
				param(map.get("p_module_id"), Integer.class),
				param(map.get("p_access_user_id"), String.class));
		map.put("refCursor", results.get(5));
		return new ArrayList<>();
	}

	/**
	 * The assignments of the CO viewer and coordinator roles: calls PKG_ASSIGNMENT.pr_co_surv_assignment_list and puts
	 * its rows into the map as refCursor.
	 */
	public List<AssignmentsListVO> getAssignmentsListForViewerOrCoordinatiorRole(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_ASSIGNMENT.pr_co_surv_assignment_list",
				param(map.get("p_workerId"), Integer.class),
				param(map.get("p_roleCode"), Integer.class),
				param(map.get("p_regionCode"), String.class),
				param(map.get("p_assignmentType"), String.class),
				param(map.get("p_startDate"), Timestamp.class),
				param(map.get("p_endDate"), Timestamp.class),
				cursor(ASSIGNMENTS_LIST),
				param(map.get("p_module_id"), Integer.class),
				param(map.get("p_access_user_id"), String.class));
		map.put("refCursor", results.get(6));
		return new ArrayList<>();
	}

	/** The reabstraction assignments: calls PKG_assignment.sp_build_assign_list and puts its rows into the map as refCursor. */
	public List<AssignmentsListVO> getAssignmentsListOfReabstract(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_assignment.sp_build_assign_list",
				param(map.get("p_regionCode"), String.class),
				param(map.get("p_workerId"), Integer.class),
				param(map.get("p_roleCode"), Integer.class),
				param(map.get("p_assignmentType"), String.class),
				param(map.get("p_startDate"), Timestamp.class),
				param(map.get("p_endDate"), Timestamp.class),
				cursor(ASSIGNMENTS_LIST),
				param(map.get("p_module_id"), Integer.class),
				param(map.get("p_access_user_id"), String.class));
		map.put("refCursor", results.get(6));
		return new ArrayList<>();
	}

	// ---------------------------------------------------------------------------------------------------------------
	// assign, reassign, reject, revoke, reopen, close (stored procedures; p_code_out / p_msg_out put into the map)

	/** Requests the reassignment of assignments: PKG_ASSIGNMENT.PR_REQUEST_REASSIGN. */
	public void requestReassign(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.PR_REQUEST_REASSIGN", param(map.get("p_assignment_list"), String.class),
				param(map.get("p_worker_id"), Integer.class), param(map.get("p_comments"), String.class));
	}

	/** Revokes a reassignment request: PKG_ASSIGNMENT.pr_revoke_reassign. */
	public void revokeReassign(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.pr_revoke_reassign", param(map.get("p_assignment_list"), String.class),
				param(map.get("p_worker_id"), Integer.class), param(map.get("p_comments"), String.class));
	}

	/** Assigns or reassigns assignments to a worker or supervisor: PKG_ASSIGNMENT.PR_MAKE_ASSIGNMENTS. */
	public void makeAssignments(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.PR_MAKE_ASSIGNMENTS", param(map.get("p_assignment_list"), String.class),
				param(map.get("p_supervisor_region_id"), Integer.class), param(map.get("p_worker_id"), Integer.class),
				param(map.get("p_comments"), String.class));
	}

	/** Rejects assignments: PKG_ASSIGNMENT.PR_REJECT_REASSIGN. */
	public void rejectReassign(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.PR_REJECT_REASSIGN", param(map.get("p_assignment_list"), String.class),
				param(map.get("p_supervisor_region_id"), Integer.class), param(map.get("p_comments"), String.class));
	}

	/**
	 * Reopens closed assignments: PKG_ASSIGNMENT.PR_REOPEN_ASSIGNMENTS (its OUT parameters come before p_review,
	 * p_workerId and p_deleted_crf, as before).
	 */
	public void reOpenAssignments(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_ASSIGNMENT.PR_REOPEN_ASSIGNMENTS",
				param(map.get("p_assignment_list"), String.class),
				param(map.get("p_supervisor_region_id"), Integer.class),
				param(map.get("p_comments"), String.class),
				out(Integer.class),
				out(String.class),
				param(map.get("p_review"), String.class),
				param(map.get("p_workerId"), String.class),
				param(map.get("p_deleted_crf"), String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/** Closes an assignment or adds a comment to it: PKG_ASSIGNMENT.pr_update_assignment. */
	public void updateAssignment(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.pr_update_assignment", param(map.get("p_document_id"), Long.class),
				param(map.get("p_user_id"), Integer.class), param(map.get("p_closure_category"), String.class),
				param(map.get("p_comments"), String.class), param(map.get("p_reab_yn"), String.class));
	}

	/** Changes the address (provider, facility or person) of an assignment: PKG_ASSIGNMENT.PR_CHANGE_ASSIGNMENT_ADDRESS. */
	public void changeAssignmentAddressDetails(Map<Object, Object> map) {
		codeAndMessage(map, "PKG_ASSIGNMENT.PR_CHANGE_ASSIGNMENT_ADDRESS", param(map.get("p_document_id"), Integer.class),
				param(map.get("p_assign_address_type"), String.class), param(map.get("p_prov_fac_person_id"), Integer.class),
				param(map.get("p_county_code"), String.class), param(map.get("p_worker_id"), Integer.class),
				param(map.get("p_Call_From"), String.class), param(map.get("p_closure_category"), String.class),
				param(map.get("p_supervisor_region_id"), Integer.class));
	}

	/** Adds a reabstraction activity to assignments: PKG_ASSIGNMENT.pr_reabstraction_activity. */
	public void reabAssignAssignments(Map<Object, Object> map) throws DataAccessException {
		codeAndMessage(map, "PKG_ASSIGNMENT.pr_reabstraction_activity", param(map.get("p_assignment_list"), String.class),
				param(map.get("p_worker_id"), Integer.class), param(map.get("p_activity_type_cd"), String.class),
				param(map.get("p_reab_status_cd"), String.class), param(map.get("p_comments"), String.class),
				param(map.get("p_activity_done_by"), Integer.class));
	}

	/**
	 * Creates a surveillance assignment for a document by hand: PKG_CREATE_ASSINGMENT_MANUAL.pr_create_assingment_manual;
	 * puts p_code (Integer) and p_msg into the map.
	 */
	public void triggerSurvAssignment(Map<String, Object> inputParameterMap) {
		List<Object> results = statements.call("PKG_CREATE_ASSINGMENT_MANUAL.pr_create_assingment_manual",
				param(inputParameterMap.get("p_document_id"), Integer.class),
				param(inputParameterMap.get("p_document_type_code"), String.class),
				param(inputParameterMap.get("p_USER_INPUT"), String.class),
				out(Integer.class),
				out(String.class),
				param(inputParameterMap.get("p_AHI_FLAG"), String.class),
				param(inputParameterMap.get("p_worker_id"), String.class));
		inputParameterMap.put("p_code", results.get(3));
		inputParameterMap.put("p_msg", results.get(4));
	}

	/**
	 * The details of an assignment: calls PKG_ASSIGNMENT.pr_getassignmentdetails and puts its three cursors into the
	 * map: details (AssignmentDetailsVO), docs (the DOCUMENT_IDs as text) and activity (AssignmentActivityVO).
	 *
	 * @param map assignmentId, workerId
	 * @return an empty list (the rows come through the map, as before)
	 */
	public List<List<ArrayList<Object>>> getAssignmentDetails(Map<Object, Object> map) throws DataAccessException {
		List<Object> results = statements.call("PKG_ASSIGNMENT.pr_getassignmentdetails",
				param(map.get("assignmentId"), Integer.class),
				param(map.get("workerId"), Integer.class),
				cursor(ASSIGNMENT_DETAILS),
				cursor(ASSIGNMENT_DOCS),
				cursor(ASSIGNMENT_ACTIVITIES));
		map.put("details", results.get(2));
		map.put("docs", results.get(3));
		map.put("activity", results.get(4));
		return new ArrayList<>();
	}

	/** The SQL that reads a person's eHARS data: pr_nyehms_ehars_person_view; puts p_ehars_sql into the map. */
	public void eharsPersonViewQuery(Map<String, String> inputParameterMap) {
		List<Object> results = statements.call("pr_nyehms_ehars_person_view",
				param(inputParameterMap.get("p_stateno"), String.class),
				out(String.class));
		inputParameterMap.put("p_ehars_sql", (String) results.get(1));
	}

	// ---------------------------------------------------------------------------------------------------------------
	// workers, regions and roles

	/**
	 * The regions and roles of a worker (region role not ended), distinct, by region description.
	 *
	 * @param workerId the worker id as text
	 */
	public List<AssignmentWorkerRegionsVO> getAssignmentWorkerRegions(String workerId) throws DataAccessException {
		return jpql.list("""
				select distinct v.workerRegionId as WORKERREGIONID, v.workerId as WORKERID, v.regionDescription as WORKERREGIONDESCRIPTION,
				       v.roleCode as WORKERROLECODE, v.regionCode as REGIONCODE, v.roleDesc as WORKERROLEDESCRIPTION
				from NyehmsWorkerRegionsVw v
				where v.workerId = ?1 and (v.wrEndDate is null or v.wrEndDate > sysdate)
				order by v.regionDescription
				""", Rows.bean(AssignmentWorkerRegionsVO.class), Values.longValue(workerId));
	}

	/** The workers and supervisors (roles 1, 2) of a region, not ended: maps with workerId, fullName. */
	public List<Map<String, String>> getWorkerNamesByRegion(String regionCode) throws DataAccessException {
		return jpql.list("""
				select distinct v.workerId as WORKERID, v.fullName as FULL_NAME
				from NyehmsWorkerRegionsVw v
				where v.regionCode = ?1 and v.roleCode in (2, 1) and (v.endDate is null or v.endDate > sysdate)
				  and (v.wrEndDate is null or v.wrEndDate > sysdate)
				order by v.fullName asc
				""", row -> textMap(row, "workerId", "WORKERID", "fullName", "FULL_NAME"), regionCode);
	}

	/** The workers of a region (roles 1, 2, 4) with permission 148 (reabstraction): maps with workerId, fullName. */
	public List<Map<String, String>> getReabstractionWorkerNamesByRegion(String regionCode) throws DataAccessException {
		return jpql.list("""
				select distinct wr.workerId as WORKERID, wr.fullName as FULL_NAME
				from NyehmsWorkerRegionsVw wr, NyehmsWorkerPermissionVw wp
				where wr.workerId = wp.workerId and wp.permissionId = 148
				  and wr.regionCode = ?1 and wr.roleCode in (2, 1, 4) and (wr.endDate is null or wr.endDate > sysdate)
				  and (wr.wrEndDate is null or wr.wrEndDate > sysdate)
				order by wr.fullName asc
				""", row -> textMap(row, "workerId", "WORKERID", "fullName", "FULL_NAME"), regionCode);
	}

	/** The assignment roles of a worker (1 to 5, region role not ended): maps with ROLE_CODE, ROLE_DESC (text). */
	public List<Map<String, String>> getUserRoleCodes(int workerId) throws DataAccessException {
		return jpql.list("""
				select v.roleCode as ROLE_CODE, v.roleDesc as ROLE_DESC
				from NyehmsWorkerRegionsVw v
				where v.workerId = ?1 and (v.wrEndDate is null or v.wrEndDate > sysdate) and v.roleCode in (1, 2, 3, 4, 5)
				""", row -> textMap(row, "ROLE_CODE", "ROLE_CODE", "ROLE_DESC", "ROLE_DESC"), (long) workerId);
	}

	/** The central office roles (1, 2, 4 in region 9) of a worker: maps with code, desc. */
	public List<Map<String, String>> getUserCORoleCodes(int workerId) throws DataAccessException {
		return jpql.list("""
				select v.roleCode as ROLE_CODE, v.roleDesc as ROLE_DESC
				from NyehmsWorkerRegionsVw v
				where v.workerId = ?1 and (v.wrEndDate is null or v.wrEndDate > sysdate) and v.roleCode in (1, 2, 4) and cast(v.regionCode as Integer) = 9
				""", row -> textMap(row, "desc", "ROLE_DESC", "code", "ROLE_CODE"), (long) workerId);
	}

	/**
	 * The worker region id (as text) of a worker's role in the central office (region 9), not ended.
	 *
	 * @param map workerId, roleCode
	 * @return the id, or null; an error if there are several (as before)
	 */
	public String getWorkerCORegionId(Map<Object, Object> map) throws DataAccessException {
		return jpql.one("""
				select v.workerRegionId as WORKERREGIONID
				from NyehmsWorkerRegionsVw v
				where cast(v.regionCode as Integer) = 9 and v.workerId = ?1 and v.roleCode = ?2
				  and (v.endDate is null or v.endDate > sysdate) and (v.wrEndDate is null or v.wrEndDate > sysdate)
				""", Rows.scalar(String.class), Values.longValue(map.get("workerId")), Values.longValue(map.get("roleCode")));
	}

	/** A worker's regions for a role (region role not ended): maps with workerRegionId, regionDescription, regionCode. */
	public List<Map<String, String>> getRegionsMapForSelectedRole(Map<Object, Object> map) throws DataAccessException {
		return jpql.list("""
				select v.workerRegionId as WORKERREGIONID, v.regionDescription as REGION_DESCRIPTION, v.regionCode as REGION_CODE
				from NyehmsWorkerRegionsVw v
				where v.workerId = ?1 and (v.wrEndDate is null or v.wrEndDate > sysdate) and v.roleCode = ?2
				order by v.regionDescription
				""", AssignmentDAO::regionMap, Values.longValue(map.get("workerId")), Values.longValue(map.get("roleCode")));
	}

	/**
	 * All active surveillance regions for the central office roles, with the worker region id of the worker's role:
	 * maps with workerRegionId, regionDescription, regionCode. The two tables are joined without a condition, as
	 * before (each region once per matching USER_REGION row).
	 */
	public List<Map<String, String>> getAllRegionsList(Map<Object, Object> map) throws DataAccessException {
		return jpql.list("""
				select u.workerRegionId as WORKERREGIONID, r.regionCode as REGION_CODE, r.regionDescription as REGION_DESCRIPTION
				from UserRegion u, CodedRegion r
				where u.workerId = ?1 and (u.endDate is null or u.endDate > sysdate) and (r.endDate is null or r.endDate > sysdate)
				  and u.roleCode = ?2 and r.assignmentTypeCode = 'SURV'
				order by r.regionDescription
				""", AssignmentDAO::regionMap, Values.longValue(map.get("workerId")), Values.longValue(map.get("roleCode")));
	}

	// ---------------------------------------------------------------------------------------------------------------
	// statuses and activity types

	/** The assignment statuses but REJECT and SUB_ACRF (and OPEN_ND for role 2), by sort order: maps with code, desc. */
	public List<Map<String, String>> getAssignmentStatusByRole(Map<Object, Object> map) throws DataAccessException {
		String excluded = ognlEquals(map.get("roleCode"), 2) ? "'REJECT', 'SUB_ACRF', 'OPEN_ND'" : "'REJECT', 'SUB_ACRF'";
		return jpql.list("select s.statusCd as STATUS_CD, s.statusDescription as STATUS_DESCRIPTION from CodedAssignmentStatus s"
				+ " where s.statusCd not in (" + excluded + ") order by s.sortOrder",
				row -> textMap(row, "code", "STATUS_CD", "desc", "STATUS_DESCRIPTION"));
	}

	/**
	 * The closure categories of the closed-assignment review (access level R) with the number of closed, unreviewed
	 * REFLEX assignments of each (as text), the ended ones only if they have assignments: maps with code, desc, count.
	 */
	public List<Map<String, String>> getReviewActivityTypeCodes(Map<String, Object> map) {
		return jpql.list("""
				select act.activityTypeDescription as ACTIVITY_TYPE_DESCRIPTION, act.activityTypeCode as ACTIVITY_TYPE_CODE,
				       sum(case when a.documentId is null then 0 else 1 end) as CNT
				from CodedActivityType act
				left join SurveillanceAssignment a on (act.activityTypeCode = a.currentActivityTypeCode
				     and a.currentAssignmentStatus = 'CLOSED' and a.reviewCategory like '%REFLEX%' and a.reviewDate is null)
				where act.accessLevel = 'R'
				group by act.activityTypeDescription, act.activityTypeCode, act.endDate
				having act.endDate is null or sum(case when a.documentId is null then 0 else 1 end) > 0
				order by act.activityTypeCode asc
				""", row -> textMap(row, "code", "ACTIVITY_TYPE_CODE", "desc", "ACTIVITY_TYPE_DESCRIPTION", "count", "CNT"));
	}

	/**
	 * The assignment closure categories (ACLS) by description: all of them, or (includeAllClosureStatus false) the
	 * active SURV ones of an access level (closureStatusCategoryType; 'ALL' = A and R): maps with code, desc, needAcrf.
	 */
	public List<Map<String, String>> getAssignmentClosureStatus(Map<String, Object> inputParameterMap) throws DataAccessException {
		StringBuilder query = new StringBuilder("select a.activityTypeCode as ACTIVITY_TYPE_CODE, a.activityTypeDescription as ACTIVITY_TYPE_DESCRIPTION,"
				+ " a.needAcrf as NEED_ACRF from CodedActivityType a where a.activityCategoryCode = 'ACLS'");
		List<Object> parameters = new ArrayList<>();
		if (Boolean.FALSE.equals(inputParameterMap.get("includeAllClosureStatus"))) {
			query.append(" and a.assignmentTypeCd = 'SURV' and a.endDate is null");
			if ("ALL".equals(inputParameterMap.get("closureStatusCategoryType"))) {
				query.append(" and a.accessLevel in ('A', 'R')");
			} else {
				query.append(" and a.accessLevel = ?1");
				parameters.add(Values.text(inputParameterMap.get("closureStatusCategoryType")));
			}
		}
		query.append(" order by a.activityTypeDescription");
		return jpql.list(query.toString(),
				row -> textMap(row, "code", "ACTIVITY_TYPE_CODE", "desc", "ACTIVITY_TYPE_DESCRIPTION", "needAcrf", "NEED_ACRF"),
				parameters.toArray());
	}

	/** The active reabstraction activities (AREAB but 01, 07, 08) by code: maps with code, desc. */
	public List<Map<String, String>> getReabActivities() throws DataAccessException {
		return jpql.list("""
				select a.activityTypeCode as ACTIVITY_TYPE_CODE, a.activityTypeDescription as ACTIVITY_TYPE_DESCRIPTION
				from CodedActivityType a
				where a.activityCategoryCode = 'AREAB' and a.activityTypeCode not in ('AREAB01', 'AREAB07', 'AREAB08') and a.endDate is null
				order by a.activityTypeCode
				""", row -> textMap(row, "code", "ACTIVITY_TYPE_CODE", "desc", "ACTIVITY_TYPE_DESCRIPTION"));
	}

	/**
	 * The reabstraction statuses by sort order (role 2: without REAB_R, role 0: none): JSONObjects with REAB_STATUS_CODE,
	 * REAB_STATUS_CODE_DESC.
	 */
	public List<JSONObject> getReabstractAssignmentStatusByRole(Map<Object, Object> map) throws DataAccessException {
		StringBuilder query = new StringBuilder("select s.reabStatusCode as REAB_STATUS_CODE, s.reabStatusCodeDesc as REAB_STATUS_CODE_DESC"
				+ " from CodedReabStatus s");
		Object roleCode = map.get("roleCode");
		if (ognlEquals(roleCode, 2)) {
			query.append(" where s.reabStatusCode not in ('REAB_R')");
		}
		if (ognlEquals(roleCode, 0)) {
			query.append(" where 1 = 2");
		}
		query.append(" order by s.sortOrder");
		return jpql.list(query.toString(), Rows.json());
	}

	/** The review actions of the viral load review offered in the drop-down, by sort order: maps with REVIEW_ACTION, REVIEW_DESC. */
	public List<Map<String, String>> getVlDocumentReviewCategories() {
		return jpql.list("""
				select c.reviewAction as REVIEW_ACTION, c.reviewDesc as REVIEW_DESC
				from CodedVlAssignReview c where c.dropDown = 'Y' order by c.sortOrder
				""", row -> textMap(row, "REVIEW_ACTION", "REVIEW_ACTION", "REVIEW_DESC", "REVIEW_DESC"));
	}

	/** 'Y' if the closure category is a non-case one (the database function is_closure_category_noncase). */
	public String checkForNonCaseClosureCateogory(String closureCategory) {
		// native: a PL/SQL function selected FROM DUAL (JPQL has no select without an entity)
		return statements.one("SELECT is_closure_category_noncase (?1) nonCase from DUAL", Rows.scalar(String.class),
				NativeStatements.text(closureCategory));
	}

	// ---------------------------------------------------------------------------------------------------------------
	// one assignment

	/** The current status of an assignment, or null. */
	public String getCurrentAssignmentStatus(long assignmentId) {
		return jpql.one("select s.currentAssignmentStatus as CURRENT_ASSIGNMENT_STATUS from SurveillanceAssignment s where s.documentId = ?1",
				Rows.scalar(String.class), assignmentId);
	}

	/** The current state number of an assignment's state number (PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO), or null. */
	public String getStatenoForAssignment(long DOCUMENT_ID) {
		return jpql.one("""
				select function('PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO' as String, s.stateno) as STATENO
				from SurveillanceAssignment s where s.documentId = ?1
				""", Rows.scalar(String.class), DOCUMENT_ID);
	}

	/** The open assignment triggered by a document, or null. */
	public Integer getOpenAssignmentIdByTrgDoc(int documentId) {
		return jpql.one("select s.documentId as DOCUMENT_ID from SurveillanceAssignment s where s.triggeringDocId = ?1 and s.closedDate is null",
				Rows.scalar(Integer.class), documentId);
	}

	/** The open assignment with this AHI document, or null. */
	public Integer getOpenAssignmentIdByAhiDoc(int documentId) {
		return jpql.one("select s.documentId as DOCUMENT_ID from SurveillanceAssignment s where s.ahiDocId = ?1 and s.closedDate is null",
				Rows.scalar(Integer.class), (long) documentId);
	}

	/** The triggering document of an assignment: a map with DOCUMENT_ID (a BigDecimal), DOCUMENT_TYPE_CODE; or null. */
	public Map<String, Object> getTriggeringDocumentDetails(int assignmentId) {
		return jpql.one("""
				select d.documentId as DOCUMENT_ID, d.documentTypeCode as DOCUMENT_TYPE_CODE
				from DocumentLocator d
				where d.documentId in (select s.triggeringDocId from SurveillanceAssignment s where s.documentId = ?1)
				""", row -> Rows.map().map(numbers(row, "DOCUMENT_ID")), (long) assignmentId);
	}

	/** The status and closure category of the (first) assignment with this ACRF: a map with CURRENT_ASSIGNMENT_STATUS, CURRENT_ACTIVITY_TYPE_CODE; or null. */
	public Map<String, String> getAssignmentStatusByACRF(int acrfDocId) {
		Map<String, Object> row = jpql.one("""
				select s.currentAssignmentStatus as CURRENT_ASSIGNMENT_STATUS, s.currentActivityTypeCode as CURRENT_ACTIVITY_TYPE_CODE
				from SurveillanceAssignment s where s.acrfDocumentId = ?1
				fetch first 1 rows only
				""", Rows.map(), (long) acrfDocId);
		return textValues(row);
	}

	/**
	 * The field staff's and the reabstraction's case report form and closure category of an assignment: a map with
	 * STATENO, DOCUMENT_TYPE ('ACRF' / 'PCRF'), FS_DOCUMENT_ID, CURRENT_ACTIVITY_TYPE_CODE, FS_CLOSURE_CATEGORY,
	 * REAB_CRF_DOCUMENT_ID, REAB_CURRENT_ACTIVITY_TYPE_CODE, REAB_CLOSURE_CATEGORY (numbers as BigDecimals); or null.
	 */
	public Map<String, Object> getReabAssignmentClosureCategories(int assignmentId) {
		return jpql.one("""
				select s.stateno as STATENO, case when s.acrfDocumentId is not null then 'ACRF' else 'PCRF' end as DOCUMENT_TYPE,
				       coalesce(s.acrfDocumentId, s.pcrfDocumentId) as FS_DOCUMENT_ID,
				       s.currentActivityTypeCode as CURRENT_ACTIVITY_TYPE_CODE, ca.activityTypeDescription as FS_CLOSURE_CATEGORY,
				       s.reabCrfDocumentId as REAB_CRF_DOCUMENT_ID, s.reabCurrentActivityTypeCode as REAB_CURRENT_ACTIVITY_TYPE_CODE,
				       ca2.activityTypeDescription as REAB_CLOSURE_CATEGORY
				from SurveillanceAssignment s, CodedActivityType ca, CodedActivityType ca2
				where s.documentId = ?1
				  and s.currentActivityTypeCode = ca.activityTypeCode
				  and s.reabCurrentActivityTypeCode = ca2.activityTypeCode
				""", row -> Rows.map().map(numbers(row, "FS_DOCUMENT_ID", "REAB_CRF_DOCUMENT_ID")), (long) assignmentId);
	}

	/**
	 * The activities of an assignment but the 'viewed' ones (AG01), without the reabstraction ones unless reabstract
	 * is 1, the latest first; ACTIVITY_DATE as MM/DD/YYYY HH:MI:SS AM.
	 */
	public List<AssignmentActivityVO> getAssignmentEditActionCommentDetails(Map<String, Object> map) throws DataAccessException {
		String query = ACTIVITIES + " where v.documentId = ?1 and v.activityTypeCode <> 'AG01'"
				+ (ognlEquals(map.get("reabstract"), 0) ? " and v.activityTypeCode not like '%AREAB%'" : "")
				+ " order by v.activityDate desc";
		return jpql.list(query, AssignmentDAO::activity, Values.longValue(map.get("assignmentId")));
	}

	/** The 'viewed' activities (AG01) of an assignment, the latest first. */
	public List<AssignmentActivityVO> getAssignmentViewedCommentDetails(long assignmentId) throws DataAccessException {
		return jpql.list(ACTIVITIES + " where v.documentId = ?1 and v.activityTypeCode = 'AG01' order by v.activityDate desc",
				AssignmentDAO::activity, assignmentId);
	}

	private static final String ACTIVITIES = "select v.activityDate as ACTIVITY_DATE, v.activityDoneBy as DONE_BY,"
			+ " v.activityDoneByShortName as DONE_BY_SHORT_NAME, v.activityTypeDescription as ACTIVITY_DESCRIPTION,"
			+ " v.comments as COMMENTS from NyehmsAssgnAllActivityVw v";

	/** An activity row as the former result map assignmentsActivityList read it, the date as TO_CHAR(..., 'MM/DD/YYYY HH:MI:SS AM'). */
	private static AssignmentActivityVO activity(Row row) {
		Timestamp date = (Timestamp) row.get("ACTIVITY_DATE");
		List<Object> values = new ArrayList<>(row.values());
		values.set(row.indexOf("ACTIVITY_DATE"), date == null ? null : DATE_TIME.format(date.toLocalDateTime()));
		return ASSIGNMENT_ACTIVITIES.map(new Row(row.labels(), values));
	}

	// ---------------------------------------------------------------------------------------------------------------
	// native reads with the pipelined table function pkg_assignment.fn_assignment_address

	/**
	 * The triggering document, address, creation date and worker of an assignment (the first row): a map with the
	 * upper-case column labels, values as JDBC returns them; or null.
	 */
	public Map<String, String> getAssignmentAndAddressDetails(int assignmentId) {
		// native: joins the PL/SQL table function TABLE(PKG_ASSIGNMENT.FN_ASSIGNMENT_ADDRESS(...)) with (+)
		@SuppressWarnings({"unchecked", "rawtypes"})
		Map<String, String> row = (Map) statements.one(ASSIGNMENT_AND_ADDRESS, Rows.map(), assignmentId);
		return row;
	}

	private static final String ASSIGNMENT_AND_ADDRESS = """
			SELECT S.TRIGGERING_DOC_ID,S.ASSIGN_ADDRESS_TYPE  ,V.PROVIDER_FACILITY_NAME,PROVIDER_FACILITY_ADDR, PROVIDER_FACILITY_COUNTY, PROVIDER_FACILITY_PHONE,
			       TO_CHAR(S.CREATE_DATE,'MM/DD/YYYY') ASSIGN_CREATE_DATE,
			       NU.LEGACY_UID WORKER_NAME,
			       TO_CHAR(W.START_DATE,'MM/DD/YYYY') WORKER_ASSIGN_DATE
			  FROM SURVEILLANCE_ASSIGNMENT S,
			  TABLE(PKG_ASSIGNMENT.FN_ASSIGNMENT_ADDRESS (S.DOCUMENT_ID,S.ASSIGN_ADDRESS_TYPE )) V,
			       WORKER_ASSIGNMENT W,
			       USER_REGION U,
			       NYEHMS_USERS NU
			WHERE  S.document_id=v.document_id(+)
			       AND S.CURRENT_WORKER_ASSIGNMENT_ID = W.WORKER_ASSIGNMENT_ID
			       AND W.WORKER_REGION_ID = U.WORKER_REGION_ID
			       AND U.WORKER_ID = NU.WORKER_ID
			       AND TRIGGERING_DOC_ID IS NOT NULL and S.DOCUMENT_ID = ?1 and rownum < 2
			""";

	/**
	 * The closed assignments not yet reviewed of a closure category (OOJ: the OOJ review, else the REFLEX review of that
	 * category), closed from 2015 on (ACL62: from November 2015), whose triggering document type the worker may see.
	 *
	 * @param inputParameterMap closureCategory, p_module_id, p_worker_id
	 */
	public List<AssignmentsListVO> loadClosedAssignmentsForReview(Map<String, Object> inputParameterMap) {
		// native: joins the PL/SQL table functions TABLE(pkg_assignment.fn_assignment_address(...)) and
		// TABLE(get_user_permissions(...)), with (+)
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(CLOSED_FOR_REVIEW_HEAD);
		Object closureCategory = inputParameterMap.get("closureCategory");
		if ("OOJ".equals(closureCategory)) {
			sql.append(" AND REVIEW_CATEGORY like '%OOJ%'");
		} else {
			parameters.add(bound(closureCategory));
			sql.append(" AND CURRENT_ACTIVITY_TYPE_CODE =?").append(parameters.size()).append(" and REVIEW_CATEGORY like '%REFLEX%'");
		}
		if ("ACL62".equals(closureCategory)) {
			sql.append(" and S.CLOSED_DATE >= TO_DATE('11/01/2015', 'MM/DD/YYYY')");
		} else {
			sql.append(" and S.CLOSED_DATE >= TO_DATE('01/01/2015', 'MM/DD/YYYY')");
		}
		parameters.add(bound(inputParameterMap.get("p_module_id")));
		parameters.add(bound(inputParameterMap.get("p_worker_id")));
		sql.append(CLOSED_FOR_REVIEW_TAIL.replace("?A", "?" + (parameters.size() - 1)).replace("?B", "?" + parameters.size()));
		return statements.list(sql.toString(), ASSIGNMENTS_LIST, parameters.toArray());
	}

	private static final String CLOSED_FOR_REVIEW_HEAD = """
			SELECT s.stateno,
			                      S.DOCUMENT_ID,
			                      s.triggering_doc_id AS TRIGG_DOC,
			                   DECODE(D.DOCUMENT_TYPE_CODE,'HIV_LAB',D.TEST_TYPE_CD,D.DOCUMENT_TYPE_CODE) AS TRIGG_DOC_TYPE,
			                      to_char (W.START_DATE, 'MM/DD/YYYY')
			                         AS WORKER_ASSIGNED_DATE,
			                      v.provider_facility_name AS PROVIDER_FACILITY_NAME,
			                      v.provider_facility_addr AS ASSIGN_ADDRESS,
			                      to_char (S.CREATE_DATE, 'MM/DD/YYYY') AS CREATE_DATE,
			                      A.STATUS_DESCRIPTION CURRENT_ASSIGNMENT_STATUS,
			                      pkg_trk_common.fn_get_fullname (nu.LAST_NAME,
			                                                      nu.FIRST_NAME,
			                                                      nu.MIDDLE_NAME,
			                                                      NULL)
			                         WORKER_NAME,
			                      S.ACRF_DOCUMENT_ID AS ACRF_DOC_ID,
			                      S.PCRF_DOCUMENT_ID AS ACRF_DOC_ID,
			                     doc.LAST_NAME ,
			                     doc.FIRST_NAME ,
			                     to_char (DOC.DATE_OF_BIRTH  , 'MM/DD/YYYY') AS DATE_OF_BIRTH  ,
			                   v.provider_facility_county,
			                   v.provider_facility_phone,
			                   CURRENT_ACTIVITY_TYPE_CODE
			                     FROM SURVEILLANCE_ASSIGNMENT S ,
			                        worker_assignment W ,
			                         nyehms_users nu ,
			                         user_region U2,
			                         TABLE(pkg_assignment.fn_assignment_address (s.document_id,s.ASSIGN_ADDRESS_TYPE )) v,
			                         CODED_ASSIGNMENT_STATUS a,
			                         DOCUMENT_LOCATOR D
			                         ,DOCUMENT_PERSON_ATTR doc
			             WHERE   CURRENT_ASSIGNMENT_STATUS  ='CLOSED'
			""";

	private static final String CLOSED_FOR_REVIEW_TAIL = """

			             AND    S.current_worker_assignment_id = W.worker_assignment_id
			             AND     W.WORKER_REGION_ID = U2.WORKER_REGION_ID
			             AND   U2.WORKER_ID = nu.WORKER_ID
			             AND   S.DOCUMENT_ID = V.DOCUMENT_ID (+)
			             AND  S.CURRENT_ASSIGNMENT_STATUS = A.STATUS_CD
			              AND s.triggering_doc_id = D.DOCUMENT_ID and s.REVIEW_DATE is null
			             AND s.triggering_doc_id =doc.document_id
			             and D.document_type_code IN (SELECT *
			                             FROM TABLE(get_user_permissions(?A, ?B)))
			             AND
			             NOT EXISTS
			                (SELECT 1
			                   FROM coded_master_setup m
			                  WHERE     m.variable_name = 'DOC_TYPE_EXCLUDE_VIEW_R'
			                        AND m.variable_code = D.document_type_code)
			""";

	/**
	 * The priority assignments (HIV labs and PRFs of CDC cases diagnosed this year, last year, both, or without a
	 * diagnosis date, not negative) of a status in a region, of one worker unless p_workerId is -1.
	 *
	 * @param map p_assignmentType, p_regionCode (a number), p_workerId, priorityType (C_P / P_P / other), currentYear,
	 *        previousYear
	 */
	public List<AssignmentsListVO> getPriorityAssignmentsList(Map<String, Object> map) {
		// native: joins the PL/SQL table function TABLE(pkg_assignment.fn_assignment_address(...)) and uses (+) on a
		// NOT IN / NOT LIKE condition of an outer-joined table
		List<Object> parameters = new ArrayList<>();
		parameters.add(bound(map.get("p_assignmentType")));
		StringBuilder sql = new StringBuilder(PRIORITY_HEAD);
		Object priorityType = map.get("priorityType");
		if ("C_P".equals(priorityType)) {
			parameters.add(bound(map.get("currentYear")));
			sql.append(" ?").append(parameters.size());
		} else if ("P_P".equals(priorityType)) {
			parameters.add(bound(map.get("previousYear")));
			sql.append(" ?").append(parameters.size());
		} else {
			parameters.add(bound(map.get("currentYear")));
			sql.append(" ?").append(parameters.size()).append(",");
			parameters.add(bound(map.get("previousYear")));
			sql.append(" ?").append(parameters.size());
		}
		// the former ${p_regionCode} (pasted into the SQL as a number): now bound
		parameters.add(regionNumber(map.get("p_regionCode")));
		sql.append(" )) OR (m.hiv_aids_dx_dt IS NULL)) AND s.CURRENT_REGION_CD = ?").append(parameters.size());
		Object workerId = map.get("p_workerId");
		if (!ognlEquals(workerId, -1)) {
			parameters.add(bound(workerId));
			sql.append(" AND r.worker_id = ?").append(parameters.size());
		}
		return statements.list(sql.toString(), ASSIGNMENTS_LIST, parameters.toArray());
	}

	private static final String PRIORITY_HEAD = """
			SELECT s.STATENO,
			       s.DOCUMENT_ID,
			       s.triggering_doc_id TRIGG_DOC,
			       Nvl2(d.test_type_cd, d.test_type_cd, d.document_type_code) as TRIGG_DOC_TYPE,
			       to_char(w.start_date,'MM/DD/YYYY') WORKER_ASSIGNED_DATE,
			       v.provider_facility_name LOCATION,
			       v.provider_facility_addr ADDRESS,
			       to_char (s.create_date, 'MM/DD/YYYY') EVENT_DATE,
			       a.status_description CURRENT_ASSIGNMENT_STATUS,
			       pkg_trk_common.fn_get_fullname(nu.LAST_NAME,
			                                        nu.FIRST_NAME,
			                                        nu.MIDDLE_NAME,
			                                        NULL) WORKER_NAME,
			       s.acrf_document_id ACRF_DOC_ID,
			       s.pcrf_document_id PCRF_DOC_ID,
			       doc.LAST_NAME,
			       doc.FIRST_NAME,
			       to_char(doc.date_of_birth, 'MM/DD/YYYY') DATE_OF_BIRTH,
			       v.PROVIDER_FACILITY_COUNTY,
			       v.PROVIDER_FACILITY_PHONE,
			       s.CURRENT_ACTIVITY_TYPE_CODE,
			       (SELECT flag_active
			          FROM person
			         WHERE stateno = s.stateno) STATENO_ACTIVE,
			       d.is_active  DOCUMENT_STATUS,
			       s.AHI_FLAG
			  FROM surveillance_assignment s,
			       worker_assignment w,
			       user_region r,
			       TABLE(pkg_assignment.fn_assignment_address (s.document_id,s.assign_address_type)) v,
			       coded_assignment_status a
			       ,document_locator d
			       ,document_person_attr doc
			       ,std_hiv_eclrs_doc hiv
			       ,nyehms_ehars_stateno_map m
			       ,nyehms_users nu
			WHERE r.worker_region_id(+) = w.worker_region_id
			   AND w.worker_assignment_id(+) = s.current_worker_assignment_id
			   AND w.document_id = v.document_id(+)
			   AND s.current_assignment_status = a.status_cd
			   AND s.triggering_doc_id = d.document_id
			   AND s.triggering_doc_id = doc.document_id
			   AND s.triggering_doc_id = hiv.document_id(+)
			   AND s.stateno = m.nyehms_stateno(+)
			  AND r.worker_id = nu.worker_id(+)
			  AND s.current_assignment_status = ?1
			   AND hiv.test_result(+) NOT IN ('NEG',
			                                'NO RESULT',
			                                'HIV NEGATIVE',
			                                '0')
			   AND hiv.test_result(+) NOT LIKE '<%'
			   AND d.document_type_code IN ('HIV_LAB','PRF')
			   AND (m.hiv_cdc = 'Y' OR m.aids_cdc = 'Y')


			   AND ((SUBSTR (m.hiv_aids_dx_dt, 1, 4) in (
			""";

	/** The region code as the number the former SQL compared CURRENT_REGION_CD with. */
	private static BigDecimal regionNumber(Object regionCode) {
		try {
			return new BigDecimal(regionCode.toString().trim());
		} catch (RuntimeException e) {
			throw new InvalidDataAccessApiUsageException("Not a region code: " + regionCode, e);
		}
	}

	/**
	 * The documents of a type whose person association has a surveillance assessment status, with their person,
	 * provider and facility, the latest association first.
	 *
	 * @param inputParameterMap documentType, processCode
	 */
	public List<PersonDocHistoryVO> getunknownOutOfStateProviderDocuments(Map<Object, Object> inputParameterMap) {
		// native: keeps the Oracle index hint, (+) joins and PL/SQL functions of the former SQL
		return statements.list(UNKNOWN_OUT_OF_STATE_PROVIDER_DOCUMENTS, Rows.bean(PersonDocHistoryVO.class),
				bound(inputParameterMap.get("documentType")), bound(inputParameterMap.get("processCode")));
	}

	private static final String UNKNOWN_OUT_OF_STATE_PROVIDER_DOCUMENTS = """
			SELECT     /*+  INDEX (P RAW_PROV_ADDR_SEARCH_IDX)*/
			            t.document_id documentId,
			            p.stateno stateno,
			           CASE
			               WHEN d.document_type_code = 'HIV_LAB' THEN d.test_type_cd
			               ELSE d.document_type_code
			            END
			              AS documentType,
			            TO_CHAR (association_date, 'MM/DD/YYYY') associationDate,
			            first_name firstName,
			            last_name lastName,
			            TO_CHAR (date_of_birth, 'MM/DD/YYYY') dateOfBirth,
			            ssn ssn,
			            pkg_person.fn_get_eth_desc (t.ethnicity_code) AS ethnicity,
			            pkg_person.fn_getrace_abbrev (d.document_id) race,
			               t.address
			            || ', '
			            || t.address_city
			            || ', '
			            || t.address_state
			            || ', '
			            || t.address_zipcode
			            || ' '
			            || t.plus4
			               AS address
			            ,TO_CHAR (D.RECEIVED_DATE, 'MM/DD/YYYY') recievedDate,
			            pkg_trk_common.fn_get_fullname (R.PROVIDER_LNAME,
			                                            R.PROVIDER_FNAME,
			                                            R.PROVIDER_MNAME,
			                                            NULL)
			               providerName,
			            R.address || ', ' || R.city || ', ' || R.state || ', ' || R.zipcode
			               AS providerAddress,
			               F.FACILITY_NAME facilityName,
			               F.address || ', ' || F.city || ', ' || F.state || ', ' || F.zipcode as facilityAddress
			       FROM document_person_attr t, document_locator d,person_doc_association p,RAW_PROVIDER_ADDRESS R, RAW_FACILITY F
			      WHERE  t.document_id = d.document_id
			      AND  t.document_id = p.document_id
			      AND T.PROVIDER_ID = R.RAW_PROVIDER_ADDRESS_ID(+)
			      AND T.FACILITY_ID = F.RAW_SUB_FACILITY_ID(+)
			      AND p.dissassociation_date IS NULL
			      AND DOCUMENT_TYPE_CODE =?1
			       and P.SURV_ASSESS_STATUS = ?2
			              ORDER BY p.association_date DESC
			""";

	// ---------------------------------------------------------------------------------------------------------------
	// writes

	/** Marks an assignment reviewed by a worker, now (the database's SYSDATE). */
	public int updateTheReviewStatusInSurvAssignment(Map<Object, Object> inputOutputParameterMap) {
		return jpql.update("update SurveillanceAssignment s set s.reviewId = ?1, s.reviewDate = sysdate where s.documentId = ?2",
				Values.longValue(inputOutputParameterMap.get("workerId")), Values.longValue(inputOutputParameterMap.get("assignmentId")));
	}

	/** Marks reviewed all closed (CLOSED, CLS_ADM), not yet reviewed assignments triggered by a document. */
	public void updateReviewedDateForAllClosedAssignments(Map<String, Object> inputParameterMap) {
		jpql.update("""
				update SurveillanceAssignment s set s.reviewId = ?1, s.reviewDate = sysdate
				where s.triggeringDocId = ?2 and s.reviewDate is null and s.currentAssignmentStatus in ('CLOSED', 'CLS_ADM')
				""", Values.longValue(inputParameterMap.get("p_workerId")), integer(inputParameterMap.get("p_document_id")));
	}

	/**
	 * Adds an activity to an assignment (ACTIVITY_DATE the database's SYSDATE).
	 *
	 * @param inputOutputParameterMap activityType, comments, workerId, assignmentId
	 * @return 1
	 */
	public int addAssignmentActivity(Map<Object, Object> inputOutputParameterMap) {
		return jpql.insert(AssignmentActivity.builder()
				.activityTypeCode(Values.text(inputOutputParameterMap.get("activityType")))
				.comments(Values.text(inputOutputParameterMap.get("comments")))
				.activityDoneBy(Values.longValue(inputOutputParameterMap.get("workerId")))
				.documentId(Values.longValue(inputOutputParameterMap.get("assignmentId")))
				.build());
	}

	/** Sets the AHI document of an assignment (by a worker, now). */
	public int updateAssignmentWithAHIDocument(Map<String, Object> inputParameterMap) {
		return jpql.update("update SurveillanceAssignment s set s.ahiDocId = ?1, s.ahiUpdateUid = ?2, s.ahiUpdateDate = sysdate where s.documentId = ?3",
				Values.longValue(inputParameterMap.get("ahiDocumentId")), Values.text(inputParameterMap.get("workerId")),
				Values.longValue(inputParameterMap.get("assignmentIdOfAHI")));
	}

	/** Replaces an AHI document (parentDocumentId) of the assignments by its edited one (by a worker, now). */
	public int updateAssignmentWithEditAHIDocument(Map<String, Object> inputParameterMap) {
		return jpql.update("update SurveillanceAssignment s set s.ahiDocId = ?1, s.ahiUpdateUid = ?2, s.ahiUpdateDate = sysdate where s.ahiDocId = ?3",
				Values.longValue(inputParameterMap.get("ahiDocumentId")), Values.text(inputParameterMap.get("workerId")),
				Values.longValue(inputParameterMap.get("parentDocumentId")));
	}

	/** Removes the ACRF and PCRF of an assignment (by lastUpdatedBy, now). */
	public void discardAcrfDocIdFromSurveillanceAssignment(Map<Object, Object> docIdMap) {
		jpql.update("""
				update SurveillanceAssignment s set s.acrfDocumentId = null, s.pcrfDocumentId = null, s.lastUpdatedBy = ?1, s.lastUpdatedDate = sysdate
				where s.documentId = ?2
				""", Values.text(docIdMap.get("lastUpdatedBy")), Values.longValue(docIdMap.get("documentId")));
	}

	/** Removes the reabstracted case report form of an assignment. */
	public void discardReabAcrfDocIdFromSurveillanceAssignment(Map<Object, Object> docIdMap) {
		jpql.update("update SurveillanceAssignment s set s.reabCrfDocumentId = null where s.documentId = ?1",
				Values.longValue(docIdMap.get("documentId")));
	}

	/** Changes the display name of a file of a document type (by a worker, now). */
	public int updateDocumentFileDisplayName(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				update CodedDisplayFileName c set c.displayName = ?1, c.lastUpdatedBy = ?2, c.lastUpdatedDate = sysdate
				where c.fileName = ?3 and c.documentTypeCode = ?4
				""", Values.text(inputParameterMap.get("displayName")), Values.longValue(inputParameterMap.get("workerId")),
				Values.text(inputParameterMap.get("fileName")), Values.text(inputParameterMap.get("documentType")));
	}

	/**
	 * Sets the reabstraction status of an assignment; except for REAB_CR also its reabstraction closure category and
	 * completion date (a date, or "" for none).
	 */
	public int updateReabStatus(Map<Object, Object> inputOutputParameterMap) {
		Object reabStatus = inputOutputParameterMap.get("reabStatus");
		Long assignmentId = Values.longValue(inputOutputParameterMap.get("assignmentId"));
		if ("REAB_CR".equals(reabStatus)) {
			return jpql.update("update SurveillanceAssignment s set s.reabStatusCode = ?1 where s.documentId = ?2",
					Values.text(reabStatus), assignmentId);
		}
		return jpql.update("""
				update SurveillanceAssignment s set s.reabStatusCode = ?1, s.reabCurrentActivityTypeCode = ?2, s.reabCompletedDate = ?3
				where s.documentId = ?4
				""", Values.text(reabStatus), Values.text(inputOutputParameterMap.get("reabClosureCategory")),
				Values.timestamp(inputOutputParameterMap.get("reabCompletedDate")), assignmentId);
	}

	/** Changes the viral load review of a state number (by a worker, now). */
	public int updateVlDocumentStatus(Map<String, Object> inputParameterMap) {
		return jpql.update("""
				update NyehmsVlAssignReview r set r.reviewBy = ?1, r.reviewDate = sysdate, r.reviewComments = ?2, r.reviewAction = ?3
				where r.stateno = ?4
				""", Values.longValue(inputParameterMap.get("workerId")), Values.text(inputParameterMap.get("requestComments")),
				Values.text(inputParameterMap.get("reviewCategory")), Values.text(inputParameterMap.get("stateno")));
	}

	// ---------------------------------------------------------------------------------------------------------------
	// viral load review

	/**
	 * The state numbers of the viral load review: all of them (reviewCategory null or empty: with the review date,
	 * description and reviewer), or the unreviewed ones ('Review'), or all without the review columns (any other):
	 * JSONObjects with stateno, flaggedDate[, reviewDate, reviewDesc, reviewBy].
	 */
	public List<JSONObject> getVlStatenosToReview(Map<String, Object> inputParameterMap) {
		Object reviewCategory = inputParameterMap.get("reviewCategory");
		boolean reviewColumns = reviewCategory == null || "".equals(reviewCategory);
		StringBuilder query = new StringBuilder("select r.stateno as stateno, r.flaggedDate as flaggedDate");
		if (reviewColumns) {
			query.append(", r.reviewDate as reviewDate, c.reviewDesc as reviewDesc,"
					+ " trim(function('pkg_trk_common.fn_get_fullname' as String, u.lastName, u.firstName, u.middleName, cast(null as String))) as reviewBy");
		}
		query.append(" from NyehmsVlAssignReview r left join CodedVlAssignReview c on r.reviewAction = c.reviewAction"
				+ " left join NyehmsUsers u on u.workerId = r.reviewBy");
		if (reviewCategory != null && "Review".equals(reviewCategory)) {
			query.append(" where r.reviewDate is null");
		}
		return jpql.list(query.toString(), row -> {
			List<Object> values = new ArrayList<>(row.values());
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) instanceof Timestamp date) {
					values.set(i, DATE.format(date.toLocalDateTime()));       // to_char(..., 'MM/DD/YYYY')
				}
			}
			return Rows.json().map(new Row(row.labels(), values));
		});
	}

	// ---------------------------------------------------------------------------------------------------------------
	// reabstraction: the columns compared and the comparison of the two case report forms

	/** The columns of a raw table the reabstraction compares. */
	public List<String> getReabComparingVariables(String tableName) throws DataAccessException {
		return jpql.list("select c.columnName as COLUMNNAME from ReabCrfCompareCols c where c.tableName = ?1", Rows.scalar(String.class), tableName);
	}

	/**
	 * Saves one compare row (CO_ACRF_REAB_COL): assignmentId, diffFields, tabName, arvUseType, fsSrcId, reabSrcId
	 * (a number, or "" for none), sameValues.
	 */
	public void saveFsReabCRFDiff(Map<String, Object> map) {
		jpql.update("""
				insert into CoAcrfReabCol (assignmentId, differentColumnNames, tabName, arvUseType, srcIdMain, srcIdSec, sameValues)
				values (?1, ?2, ?3, ?4, ?5, ?6, ?7)
				""", Values.longValue(map.get("assignmentId")), Values.text(map.get("diffFields")), Values.text(map.get("tabName")),
				Values.text(map.get("arvUseType")), decimal(map.get("fsSrcId")), decimal(map.get("reabSrcId")), Values.text(map.get("sameValues")));
	}

	/**
	 * The compare rows of an assignment: maps with TAB_NAME, ARV_USE_TYPE, FS_SRC_ID, REAB_SRC_ID (BigDecimals, 0 for
	 * none), SAME_VALUES, DIFFERENT_COLUMN_NAMES.
	 */
	public List<Map<String, Object>> getAssignmentCompareData(int assignmentId) {
		return jpql.list(COMPARE_DATA, row -> Rows.map().map(compareRow(row)), (long) assignmentId);
	}

	/** As {@link #getAssignmentCompareData}, as JSONObjects. */
	public List<JSONObject> getReabstractionDiffData(int assignmentId) {
		return jpql.list(COMPARE_DATA, row -> Rows.json().map(compareRow(row)), (long) assignmentId);
	}

	private static final String COMPARE_DATA = "select c.tabName as TAB_NAME, c.arvUseType as ARV_USE_TYPE, c.srcIdMain as FS_SRC_ID,"
			+ " c.srcIdSec as REAB_SRC_ID, c.sameValues as SAME_VALUES, c.differentColumnNames as DIFFERENT_COLUMN_NAMES"
			+ " from CoAcrfReabCol c where c.assignmentId = ?1";

	/** nvl(SRC_ID_MAIN, 0), nvl(SRC_ID_SEC, 0). */
	private static Row compareRow(Row row) {
		List<Object> values = new ArrayList<>(row.values());
		for (String label : List.of("FS_SRC_ID", "REAB_SRC_ID")) {
			int index = row.indexOf(label);
			values.set(index, values.get(index) == null ? BigDecimal.ZERO : decimal(values.get(index)));
		}
		return new Row(row.labels(), values);
	}

	/**
	 * One row of a raw case report form table: SELECT selectColumns FROM tableName WHERE document_id = documentId, as
	 * a map with the upper-case column labels (values as JDBC returns them); or null.
	 */
	public Map<String, Object> getRawCRFDocData(Map<String, Object> map) {
		return statements.one(rawCrfSql(map), Rows.map(), bound(map.get("documentId")));
	}

	/** The rows of a raw case report form table (see {@link #getRawCRFDocData}). */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getRawCRFData(Map<String, Object> map) {
		return (List) statements.list(rawCrfSql(map), Rows.map(), bound(map.get("documentId")));
	}

	/** The rows of a raw case report form table (see {@link #getRawCRFDocData}). */
	public List<Map<String, Object>> getRawCRFDataObj(Map<String, Object> map) {
		return statements.list(rawCrfSql(map), Rows.map(), bound(map.get("documentId")));
	}

	/** The former SELECT ${selectColumns} FROM ${tableName} WHERE ${document_id} = #{documentId}, the names checked. */
	private static String rawCrfSql(Map<String, Object> map) {
		// native: the table and columns are chosen at runtime
		return "SELECT " + columnList(map.get("selectColumns")) + " FROM " + table(map.get("tableName")) + " WHERE "
				+ identifier(map.get("document_id")) + " = ?1";
	}

	/**
	 * The source ids (the first column of the row, as text) of the labs of documentId1 that have no lab with the same
	 * parent test type, test code and result code in documentId2.
	 *
	 * @param map tableName (RAW_xCRF_LABS), docType (ACRF / PCRF), documentId1, documentId2
	 */
	public List<String> getLabFullDiffSrcId(Map<String, Object> map) throws DataAccessException {
		// native: the table and columns are chosen at runtime
		String table = table(map.get("tableName"));
		String docType = identifier(map.get("docType"));
		String sql = "SELECT * FROM " + table + " lab1 WHERE  document_id = ?1"
				+ " AND NOT EXISTS (SELECT 1 FROM " + table + " WHERE lab1.PARENT_TEST_TYPE=PARENT_TEST_TYPE"
				+ " AND lab1." + docType + "_TEST_CODE=" + docType + "_TEST_CODE AND NVL(lab1." + docType + "_RESULT_CODE, 'X') = NVL("
				+ docType + "_RESULT_CODE, 'X')  AND document_id = ?2)";
		return statements.list(sql, Rows.scalar(String.class), bound(map.get("documentId1")), bound(map.get("documentId2")));
	}

	/**
	 * For each pair of labs (documentId2's, documentId1's) with the same parent test type, test code and result code,
	 * not among the given source ids, that differ in a compared column: one map with 'true' / 'false' per column
	 * (false = different) and LAB_SRC_ID1, LAB_SRC_ID2.
	 *
	 * @param map tableName, docType, columnNames, documentId1, documentId2, fsLabSrcIds, reabLabSrcIds
	 */
	public List<Map<String, Object>> getLabPartialDiffSrcId(Map<String, Object> map) {
		// native: the table and columns are chosen at runtime
		return statements.list(partialDiffSql(map, "lab1", "lab2", "'X'", "lab_Src_id", "lab_Src_id lab_Src_id1", "lab_Src_id lab_Src_id2",
				"lab1.PARENT_TEST_TYPE=lab2.PARENT_TEST_TYPE AND lab1.%1$s_TEST_CODE=lab2.%1$s_TEST_CODE"
						+ " AND NVL(lab1.%1$s_RESULT_CODE, 'X') = NVL(lab2.%1$s_RESULT_CODE, 'X')"),
				Rows.map(), partialDiffParameters(map));
	}

	/**
	 * The source ids (the first column of the row, as text) of the CD4 rows (with a collection date) of documentId1
	 * that have no CD4 row with the same "closest to current diagnosis" flag and collection date in documentId2.
	 */
	public List<String> getCD4FullDiffSrcId(Map<String, Object> map) throws DataAccessException {
		// native: the table is chosen at runtime
		String table = table(map.get("tableName"));
		String sql = "SELECT * FROM " + table + " cd4_1 WHERE  document_id = ?1 AND COLLECTION_DATE != '../../....'"
				+ " AND NOT EXISTS (SELECT 1 FROM " + table + " WHERE COLLECTION_DATE != '../../....' AND cd4_1.CLOSEST_TO_CURRENT_DX = CLOSEST_TO_CURRENT_DX"
				+ " AND NVL(cd4_1.COLLECTION_DATE, 'X') = NVL(COLLECTION_DATE, 'X')  AND document_id = ?2)";
		return statements.list(sql, Rows.scalar(String.class), bound(map.get("documentId1")), bound(map.get("documentId2")));
	}

	/** As {@link #getLabPartialDiffSrcId} for the CD4 rows: CD4_SRC_ID1, CD4_SRC_ID2. */
	public List<Map<String, Object>> getCD4PartialDiffSrcId(Map<String, Object> map) {
		// native: the table and columns are chosen at runtime
		return statements.list(partialDiffSql(map, "cd4_1", "cd4_2", "'0'", "CD4_SRC_ID", "CD4_SRC_ID CD4_SRC_ID1", "CD4_SRC_ID CD4_SRC_ID2",
				"cd4_1.CLOSEST_TO_CURRENT_DX = cd4_2.CLOSEST_TO_CURRENT_DX AND NVL(cd4_1.COLLECTION_DATE, 'X') = NVL(cd4_2.COLLECTION_DATE, 'X')"),
				Rows.map(), partialDiffParameters(map));
	}

	/**
	 * The former getLabPartialDiffSrcId / getCD4PartialDiffSrcId SQL: the documentId2 parameter is ?1, the fs source
	 * ids follow, then documentId1, then the reabstraction source ids (the order of the former #{...}).
	 */
	private static String partialDiffSql(Map<String, Object> map, String a, String b, String nullValue, String srcId,
			String srcId1, String srcId2, String join) {
		String table = table(map.get("tableName"));
		List<String> columns = identifiers(map.get("columnNames"));
		StringBuilder sql = new StringBuilder("SELECT * FROM ( SELECT ");
		for (String column : columns) {
			sql.append(" CASE WHEN  NVL(").append(a).append('.').append(column).append(", ").append(nullValue).append(") != NVL(")
					.append(b).append('.').append(column).append(", ").append(nullValue).append(") THEN 'false' ELSE 'true' END ")
					.append(column).append(',');
		}
		sql.append(' ').append(a).append('.').append(srcId1).append(", ").append(b).append('.').append(srcId2);
		sql.append(" FROM ").append(table).append(' ').append(a).append(" , ").append(table).append(' ').append(b)
				.append(" WHERE ").append(a).append(".document_id in (?1)");
		int index = 1;
		Collection<?> fsSrcIds = (Collection<?>) map.get("fsLabSrcIds");
		if (fsSrcIds != null && !fsSrcIds.isEmpty()) {
			sql.append(" and ").append(a).append('.').append(srcId).append(" not in ");
			index = placeholders(sql, index, fsSrcIds.size());
		}
		sql.append(" AND ").append(b).append(".document_id IN (?").append(++index).append(')');
		Collection<?> reabSrcIds = (Collection<?>) map.get("reabLabSrcIds");
		if (reabSrcIds != null && !reabSrcIds.isEmpty()) {
			sql.append(" and ").append(b).append('.').append(srcId).append(" not in ");
			index = placeholders(sql, index, reabSrcIds.size());
		}
		String docType = map.get("docType") == null ? null : identifier(map.get("docType"));
		sql.append(" AND ").append(join.contains("%1$s") ? String.format(join, docType) : join);
		sql.append(" ) WHERE ");
		if (!columns.isEmpty()) {
			sql.append(columns.stream().map(column -> column + " = 'false'").collect(Collectors.joining(" or ", "(", ")")));
		}
		return sql.toString();
	}

	/** Appends (?n+1, ?n+2, ...) for count values; returns the last parameter number. */
	private static int placeholders(StringBuilder sql, int last, int count) {
		sql.append('(');
		for (int i = 0; i < count; i++) {
			sql.append(i == 0 ? "" : ",").append('?').append(++last);
		}
		sql.append(')');
		return last;
	}

	private static Object[] partialDiffParameters(Map<String, Object> map) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(bound(map.get("documentId2")));
		Collection<?> fsSrcIds = (Collection<?>) map.get("fsLabSrcIds");
		if (fsSrcIds != null && !fsSrcIds.isEmpty()) {
			fsSrcIds.forEach(id -> parameters.add(bound(id)));
		}
		parameters.add(bound(map.get("documentId1")));
		Collection<?> reabSrcIds = (Collection<?>) map.get("reabLabSrcIds");
		if (reabSrcIds != null && !reabSrcIds.isEmpty()) {
			reabSrcIds.forEach(id -> parameters.add(bound(id)));
		}
		return parameters.toArray();
	}

	/** The open caseload summary (all columns of FIELD_STAFF_CASELOAD_SUMMARY, as the browser reads them). */
	public List<JSONObject> getOpenCaseloadSummaryDataReport() {
		// native: SELECT * of a view whose columns go to the browser as they are
		return statements.list("select * from field_staff_caseload_summary", Rows.json());
	}

	// ---------------------------------------------------------------------------------------------------------------
	// the names allowed in the former ${...}

	/** The raw tables the reabstraction reads: the case report form tables and their labs, CD4 and ARV tables. */
	static final Set<String> RAW_CRF_TABLES = Set.of("RAW_ACRF_DOC", "RAW_PCRF_DOC", "RAW_ACRF_LABS", "RAW_PCRF_LABS", "RAW_ACRF_CD4",
			"RAW_PCRF_CD4", "RAW_ACRF_ARVS", "RAW_PCRF_ARVS");

	private static final Pattern IDENTIFIER = Pattern.compile("[A-Za-z][A-Za-z0-9_$#]*");

	private static String table(Object name) {
		if (name == null || !RAW_CRF_TABLES.contains(name.toString().toUpperCase(Locale.ENGLISH))) {
			throw new InvalidDataAccessApiUsageException("Not a table the reabstraction compares: " + name);
		}
		return name.toString();
	}

	/** A column name or the document type (ACRF / PCRF): a plain SQL identifier. */
	private static String identifier(Object name) {
		if (name == null || !IDENTIFIER.matcher(name.toString()).matches()) {
			throw new InvalidDataAccessApiUsageException("Not a column name: " + name);
		}
		return name.toString();
	}

	private static List<String> identifiers(Object names) {
		List<String> checked = new ArrayList<>();
		for (Object name : (Collection<?>) names) {
			checked.add(identifier(name));
		}
		return checked;
	}

	/** A comma-separated list of column names (spaces around the names allowed). */
	private static String columnList(Object names) {
		if (names == null) {
			throw new InvalidDataAccessApiUsageException("No columns");
		}
		for (String name : names.toString().split(",", -1)) {
			identifier(name.trim());
		}
		return names.toString();
	}

	// ---------------------------------------------------------------------------------------------------------------
	// the cursor rows, mapped as the former result maps mapped them

	/** The assignment list rows (former result map assignmentsList). */
	static final RowMapper<AssignmentsListVO> ASSIGNMENTS_LIST = new ResultMapRows<>(AssignmentsListVO.class,
			"WORKER_ID", "workerId", "WORKER_SHORT_NAME", "workerShortName", "WORKER_NAME", "workerName", "WORKER_REGION_ID", "workerRegionId",
			"DOCUMENT_ID", "assignmentId", "STATENO", "stateNo", "CURRENT_ACTIVITY_TYPE_CODE", "assignmentActivityCd",
			"STATENO_ACTIVE", "statenoActiveStatus", "CURRENT_ASSIGNMENT_STATUS", "assignmentStatus", "CREATE_DATE", "createDate",
			"WORKER_ASSIGNED_DATE", "workerAssignedDate", "ASSIGN_ADDRESS", "assignAddress", "PROVIDER_FACILITY_NAME", "providerFacilityName",
			"ACRF_DOC_ID", "acrfDocId", "PCRF_DOC_ID", "pcrfDocId", "LAST_ACTIVITY_DATE", "lastActivityDate",
			"LAST_ACTIVITY_DESC", "lastActivityDesc", "ACTIVITY_DONE_BY", "activityDoneBy", "LAST_REOPEN_DATE", "lastReopenDate",
			"LAST_CLOSED_DATE", "lastClosedDate", "REJECTION_DATE", "rejectionDate", "REJECTION_REASON", "rejectionReason",
			"COMMENTS", "comments", "CLOSURE_DATE", "closureDate", "CLOSURE_CATEGORY_CD", "closureCategoryCd",
			"CLOSURE_ACTIVITY", "closureActivity", "CURRENT_WORKER", "currentWorker", "CURRENT_STATUS", "currentStatus",
			"LOCATION", "location", "ADDRESS", "address", "EVENT_DATE", "eventDate", "TRIGG_DOC", "documentId",
			"TRIGG_DOC_TYPE", "documentType", "FIRST_NAME", "triggeringDocumentFirstName", "LAST_NAME", "triggeringDocumentLastName",
			"DATE_OF_BIRTH", "triggeringDocumentDob", "DOCUMENT_STATUS", "documentStatus", "AHI_FLAG", "ahiFlag", "CRF_DIFFS", "crfDiffs",
			"closed_by", "closedBy");

	/** The assignment details row (former result map assignmentsDetails). */
	static final RowMapper<AssignmentDetailsVO> ASSIGNMENT_DETAILS = new ResultMapRows<>(AssignmentDetailsVO.class,
			"DOCUMENT_ID", "assignmentId", "STATENO", "stateno", "CURRENT_STATUS", "currentAssignmentStatus", "STATUS_DESC", "assignmentStatusDesc",
			"ASSIGNED_TO", "workerName", "REGION", "regionDescription", "SUPERVISOR_NAME", "supervisorName",
			"ASSIGNMENT_CREATE_DATE", "assignedDate", "MISSING_VARIABLES", "missingVariables", "CRF_DOC_ID", "crfDocId",
			"CLOSED_DATE", "closedDate", "CLOSURE_CATEGORY", "closureCategory", "DATE_COMPLETED", "crfDateCompleted",
			"CRF_DOC_TYPE", "crfDocType", "SUPERVISOR_REGION_ID", "supervisorRegionId", "AHI_FLAG", "ahiFlag", "AHI_DOC_ID", "ahiDocId",
			"REAB_ASSIGNED_TO", "reabWorkerName", "REAB_ASSIGNED_BY", "reabSupervisorName", "REAB_ASSIGNED_DATE", "reabAssignedDate",
			"CURRENT_REAB_STATUS", "reabCurrentStatus", "CURRENT_REAB_STATUS_DESC", "reabCurrentStatusDesc",
			"CURRENT_REAB_ACTIVITY", "reabCurrentActivity", "CURRENT_REAB_ACTIVITY_DESC", "reabCurrentActivityDesc",
			"REAB_CRF_DOCUMENT_ID", "reabCrfDocId", "REAB_COMPLETED_DATE", "reabCrfCompletedDate", "REAB_CRF_DOC_TYPE", "reabCrfDocType",
			"REAB_CURRENT_ACTIVITY_TYPE_CODE", "reabClosureCategory", "ehars_import_date", "eharsImportDate",
			"REAB_REVIEW_BY", "reabReviewBy", "REAB_REVIEW_DATE", "reabReviewDate", "REAB_REVIEW_COMMENTS", "reabReviewComments",
			"AHI_REVIEWED", "ahiReviewed");

	/** The assignment activity rows (former result map assignmentsActivityList). */
	static final RowMapper<AssignmentActivityVO> ASSIGNMENT_ACTIVITIES = new ResultMapRows<>(AssignmentActivityVO.class,
			"ACTIVITY_DATE", "activityDate", "DONE_BY", "doneBY", "DONE_BY_SHORT_NAME", "doneBYShortName",
			"ACTIVITY_DESCRIPTION", "activityDesc", "comments", "comments");

	/** The assignment's document rows (former result map assignmentsDocsList, type String): DOCUMENT_ID as text. */
	static final RowMapper<String> ASSIGNMENT_DOCS = row -> text(row.get("DOCUMENT_ID"));

	/**
	 * Rows mapped as a MyBatis result map without nested mappings (default settings) mapped them: first the columns
	 * the result map does not name are auto-mapped to the property of the same name (ignoring case) unless the result
	 * map maps that property itself, then the named columns are set on their properties; a null value is not set (a
	 * later non-null one wins), each value is read as its property's type (a query's columns with the property's type
	 * handler, as MyBatis read them; a cursor's values as JDBC getObject gives them, converted), and a row that sets
	 * nothing is null.
	 */
	private static final class ResultMapRows<T> implements TypedRowMapper<T> {

		private final TypedRowMapper<T> bean;
		private final List<String[]> mappings = new ArrayList<>();
		private final Set<String> mappedColumns;
		private final Set<String> mappedProperties;

		ResultMapRows(Class<T> type, String... columnsAndProperties) {
			bean = (TypedRowMapper<T>) Rows.bean(type);
			for (int i = 0; i < columnsAndProperties.length; i += 2) {
				mappings.add(new String[] {columnsAndProperties[i], columnsAndProperties[i + 1]});
			}
			mappedColumns = mappings.stream().map(m -> upper(m[0])).collect(Collectors.toSet());
			mappedProperties = mappings.stream().map(m -> upper(m[1])).collect(Collectors.toSet());
		}

		@Override
		public Class<?> typeOf(String label, int index) {
			for (String[] mapping : mappings) {
				if (mapping[0].equalsIgnoreCase(label)) {
					return bean.typeOf(mapping[1], index);
				}
			}
			return mappedProperties.contains(upper(label)) ? null : bean.typeOf(label, index);
		}

		@Override
		public T map(Row row) {
			Map<String, Object> values = new LinkedHashMap<>();       // property (upper case) -> value
			for (int i = 0; i < row.size(); i++) {
				String label = row.labels().get(i);
				if (row.indexOf(label) == i && !mappedColumns.contains(upper(label)) && !mappedProperties.contains(upper(label))
						&& row.get(i) != null) {
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

	// ---------------------------------------------------------------------------------------------------------------
	// helpers

	/** A map result of a former result map of java.util.Map with String values: key / label pairs, nulls left out, a row of nulls null. */
	private static Map<String, String> textMap(Row row, String... keysAndLabels) {
		Map<String, String> map = new java.util.HashMap<>();
		for (int i = 0; i < keysAndLabels.length; i += 2) {
			String value = text(row.get(keysAndLabels[i + 1]));
			if (value != null) {
				map.put(keysAndLabels[i], value);
			}
		}
		return map.isEmpty() ? null : map;
	}

	private static Map<String, String> regionMap(Row row) {
		return textMap(row, "workerRegionId", "WORKERREGIONID", "regionDescription", "REGION_DESCRIPTION", "regionCode", "REGION_CODE");
	}

	/** A map result's values as text (they are text columns), or null. */
	private static Map<String, String> textValues(Map<String, Object> row) {
		if (row == null) {
			return null;
		}
		Map<String, String> map = new java.util.HashMap<>();
		row.forEach((key, value) -> map.put(key, text(value)));
		return map;
	}

	/** The row with the given columns' numbers as BigDecimals (as JDBC getObject returns an Oracle NUMBER). */
	private static Row numbers(Row row, String... labels) {
		List<Object> values = new ArrayList<>(row.values());
		for (String label : labels) {
			int index = row.indexOf(label);
			values.set(index, decimal(values.get(index)));
		}
		return new Row(row.labels(), values);
	}

	/** A value read as text (MyBatis' StringTypeHandler: getString). */
	private static String text(Object value) {
		if (value == null) {
			return null;
		}
		return value instanceof BigDecimal number ? number.toPlainString() : value.toString();
	}

	/** A number as a BigDecimal; null and "" (stored as NULL) as null. */
	private static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value instanceof BigDecimal number) {
			return number;
		}
		return new BigDecimal(value.toString().trim());
	}

	private static Integer integer(Object value) {
		Long number = Values.longValue(value);
		return number == null ? null : Math.toIntExact(number);
	}

	/** A native query value: a java.util.Date bound as a timestamp (as MyBatis did), null with a type. */
	private static Object bound(Object value) {
		if (value == null) {
			return NativeStatements.text(null);
		}
		if (value.getClass() == java.util.Date.class) {
			return new Timestamp(((java.util.Date) value).getTime());
		}
		return value;
	}

	/** An IN parameter bound with its value's type (the declared type when null); a java.util.Date as a timestamp. */
	private static CallParam param(Object value, Class<?> nullType) {
		Object bound = value != null && value.getClass() == java.util.Date.class ? new Timestamp(((java.util.Date) value).getTime()) : value;
		return in(bound, bound == null ? nullType : bound.getClass());
	}

	/** Calls a procedure whose last two parameters are p_code_out (Integer) and p_msg_out, and puts them into the map. */
	private void codeAndMessage(Map<Object, Object> map, String procedure, CallParam... inParameters) {
		CallParam[] parameters = java.util.Arrays.copyOf(inParameters, inParameters.length + 2);
		parameters[inParameters.length] = out(Integer.class);
		parameters[inParameters.length + 1] = out(String.class);
		List<Object> results = statements.call(procedure, parameters);
		map.put("p_code_out", results.get(inParameters.length));
		map.put("p_msg_out", results.get(inParameters.length + 1));
	}

	/**
	 * The former OGNL test {@code value == number}: false for null, numbers compared by value, a text read as a
	 * number ('' is 0, other non-numbers an error), a boolean as 1 / 0.
	 */
	static boolean ognlEquals(Object value, long number) {
		if (value == null) {
			return false;
		}
		double actual;
		if (value instanceof Number n) {
			return new BigDecimal(n.toString()).compareTo(BigDecimal.valueOf(number)) == 0;
		} else if (value instanceof Boolean flag) {
			actual = flag ? 1 : 0;
		} else if (value instanceof Character character) {
			actual = character;
		} else {
			String text = value.toString().trim();
			try {
				actual = text.isEmpty() ? 0 : Double.parseDouble(text);
			} catch (NumberFormatException e) {
				throw new InvalidDataAccessApiUsageException("Not a number: " + value, e);
			}
		}
		return actual == number;
	}
}
