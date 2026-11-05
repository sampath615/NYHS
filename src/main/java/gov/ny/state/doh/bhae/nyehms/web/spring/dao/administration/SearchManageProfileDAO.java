package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.ModuleAndPermissionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsPermission;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsUsersActivity;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.CodedBureauNameRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.NyehmsGraphPriorityVwRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedAdminCountyRegionRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedRoleRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersActivityRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.UserRegionRepository;
import lombok.RequiredArgsConstructor;

/**
 * The DAO for search manage profile: permissions by role, notification lists, user comments and code lists (formerly
 * a MyBatis mapper with the same name and methods).
 * <ul>
 * <li>Most statements: Spring Data repositories, and JPQL built here for the notification column picked by the
 * screen.</li>
 * <li>The three permission lists: native SQL, unchanged except that the role column is checked first - the role's
 * column of PERMISSION (formerly {@code ${p_roleName}}) is picked at run time from CODED_ROLE, so it cannot be an
 * entity attribute.</li>
 * </ul>
 * Names that used to be pasted into the SQL ({@code ${}}) are checked against allow-lists: the notification column
 * (ACTION_NAME) against the notification screen's list {@link #NOTIFICATION_COLUMNS}, the role column against the
 * role names the service derives from CODED_ROLE. Anything else fails with an InvalidDataAccessApiUsageException (it
 * failed with an SQL error before, or was injected into the SQL). "Now" in the end-date conditions and the date of a
 * new comment are the application server's clock (formerly SYSDATE).
 */
@Repository
@RequiredArgsConstructor
public class SearchManageProfileDAO {

	/**
	 * The e-mail notification columns of NYEHMS_USERS the notification screen offers (systemNotifications.jsp), with
	 * their NyehmsUsers attributes.
	 */
	static final Map<String, String> NOTIFICATION_COLUMNS = notificationColumns("ACRF_RISK", "acrfRisk", "AHI", "ahi",
			"AHI_NOTIFICATION", "ahiNotification", "AIRS_AGENCY", "airsAgency", "AIRS_UPLOAD_ERR", "airsUploadErr", "ASSIGNMENT", "assignment",
			"ASSIGNMENT_AHI", "assignmentAhi", "COMATCH", "comatch", "EHARS_NOTIFICATION", "eharsNotification", "EPRF_OOC_SUMMARY", "eprfOocSummary",
			"FILE_REMINDER", "fileReminder", "HIT_NOTIFICATION", "hitNotification", "MONTHLY_LOINC", "monthlyLoinc", "NIGHTLY_JOB", "nightlyJob",
			"PP_ERR", "ppErr", "PP_OOC_SUB", "ppOocSub", "PRF_REVIEW", "prfReview", "PS_MSG_DEAD_DISPO", "psMsgDeadDispo", "PS_MESSAGE", "psMessage",
			"PS_MSG_UPLOAD", "psMsgUpload", "PS_NYC_PROV_FAC", "psNycProvFac", "RAS_DOC_ERR", "rasDocErr", "RAS_NOTIFICATION", "rasNotification",
			"RECORD_SEARCH_UPLOAD", "recordSearchUpload", "RES_LINKING_ERR", "resLinkingErr", "WEEKLY_LOINC", "weeklyLoinc",
			"LAFT_RESOLVE_COMMENTS", "laftResolveComments");

	/** TO_CHAR(x, 'MM/DD/YYYY') */
	private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);

	private final NativeStatements statements;
	private final JpqlStatements jpql;
	private final CodedRoleRepository roles;
	private final UserRegionRepository userRegions;
	private final NyehmsGraphPriorityVwRepository graphs;
	private final NyehmsUsersRepository users;
	private final NyehmsUsersActivityRepository activities;
	private final CodedBureauNameRepository bureaus;
	private final CodedAdminCountyRegionRepository regionalOffices;

	/*
	 * Get the permissions grouped by module and sub modules
	 * @param inOutParameterMap p_roleName: the role's column of PERMISSION
	 * @return List of ModuleAndPermissionsVO instances
	 */
	public List<ModuleAndPermissionsVO> getModuleAndPermissions(Map<Object, Object> inOutParameterMap) {
		String role = roleColumn(inOutParameterMap);
		return modulesAndPermissions(statements.list("""
				select permission_id, p.module_id, p.sub_module_id, permission_desc, m.module_name, sm.sub_module_name, case when %1$s = 'M' then 'TRUE' else 'FALSE' end flag_sugg   from permission p
				            JOIN module m ON (p.module_id = m.module_id)
				            LEFT OUTER JOIN sub_module sm ON (p.sub_module_id = sm.sub_module_id) where %1$s is not null
				            order by m.MODULE_NAME, sm.SUB_MODULE_NAME, p.PERMISSION_DESC asc
				""".formatted(role), row -> row));
	}

	/** The permissions of the role of a worker region, flagged when the worker region has them. */
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionId(Map<Object, Object> inOutParameterMap) {
		return byWorkerRegion(inOutParameterMap, "LEFT OUTER JOIN");
	}

	/** The permissions of the role of a worker region that the worker region has. */
	public List<ModuleAndPermissionsVO> getModuleAndPermissionsByWorkerRegionIdWithOutFlagSugg(Map<Object, Object> inOutParameterMap) {
		return byWorkerRegion(inOutParameterMap, "JOIN");
	}

	/** The graphs of the worker's lowest current role, in rank order. */
	public List<String> getGraphsHierarchy(Map<Object, Object> inOutParameterMap) {
		return new ArrayList<>(graphs.findGraphsOfWorker(longValue(inOutParameterMap.get("p_worker_id"))));
	}

	/**
	 * The workers not ended that have a value in a notification column: maps with WORKER_ID (a BigDecimal, as the
	 * NUMBER column was read), LAST_NAME, FIRST_NAME, EMAIL_ADDR and NOTIFICATION_STATUS (a null value left out).
	 *
	 * @param inputParameterMap ACTION_NAME: the notification column (allow-listed)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getEmailNotificationList(Map<String, String> inputParameterMap) {
		String column = notificationAttribute(inputParameterMap.get("ACTION_NAME"));
		List<Map<String, Object>> rows = jpql.list("select u.workerId as WORKER_ID, u.lastName as LAST_NAME, u.firstName as FIRST_NAME,"
				+ " u.emailAddr as EMAIL_ADDR, u." + column + " as NOTIFICATION_STATUS from NyehmsUsers u"
				+ " where (u.endDate is null or u.endDate > ?1) and u." + column + " is not null", Rows.map(), now());
		for (Map<String, Object> row : rows) {
			if (row != null && row.get("WORKER_ID") instanceof Long workerId) {
				row.put("WORKER_ID", BigDecimal.valueOf(workerId));
			}
		}
		return (List) rows;
	}

	/** The e-mail addresses of the workers not ended. */
	public List<String> getUsersEmailList() {
		return users.findByEndDateIsNullAndEmailAddrIsNotNullOrEndDateAfterAndEmailAddrIsNotNull(now()).stream()
				.map(NyehmsUsersRepository.EmailAddr::emailAddr).collect(toList());
	}

	/** The short names of the unlocked workers not ended. */
	public List<String> getUserShortNameList() {
		return users.findByEndDateIsNullAndIsLockedOrEndDateAfterAndIsLocked("N", now(), "N").stream()
				.map(NyehmsUsersRepository.LegacyUid::legacyUid).collect(toList());
	}

	/**
	 * The worker id (as text) of the worker not ended with this e-mail address.
	 *
	 * @return the id, or null if there is none
	 * @throws IncorrectResultSizeDataAccessException if there are several (as before)
	 */
	public String getWorkerIdUsingEmail(String emailAddress) {
		if (emailAddress == null) {
			return null;                    // EMAIL_ADDR = NULL matched nothing
		}
		return one(users.findByEndDateIsNullAndEmailAddrOrEndDateAfterAndEmailAddr(emailAddress, now(), emailAddress).stream()
				.map(row -> row.workerId() == null ? null : row.workerId().toString()).toList());
	}

	/** Clears a notification column for all workers (ACTION_NAME, allow-listed). */
	@Transactional
	public void clearNotifications(Map<String, Object> inputParameterMap) {
		String column = notificationAttribute(inputParameterMap.get("ACTION_NAME"));
		jpql.update("update NyehmsUsers u set u." + column + " = null where u." + column + " is not null");
	}

	/** Sets a notification column to 'T' (to) for the workers of TO_LIST (an array of worker ids as text). */
	@Transactional
	public void saveToUpdatedNotifications(Map<String, Object> inputParameterMap) {
		setNotification(inputParameterMap, "TO_LIST", "T");
	}

	/** Sets a notification column to 'C' (copy) for the workers of CC_LIST (an array of worker ids as text). */
	@Transactional
	public void saveCCUpdatedNotifications(Map<String, Object> inputParameterMap) {
		setNotification(inputParameterMap, "CC_LIST", "C");
	}

	/** The description of a role. */
	public String getRoleName(long roleCode) {
		return roles.findById(roleCode).map(CodedRole::getRoleDescription).orElse(null);
	}

	/**
	 * The role description of a worker region.
	 *
	 * @throws IncorrectResultSizeDataAccessException if there are several (as before)
	 */
	public String getRoleNameByWorkerRegionId(long workerRegionId) {
		return one(userRegions.findRoleDescriptions(workerRegionId));
	}

	/** A worker's comments, latest first: JSONObjects with activityDate (MM/DD/YYYY), activityBy, comments. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getUserCommentsList(String workerId) {
		Long worker = longValue(workerId);
		if (worker == null) {
			return new ArrayList<>();       // WORKER_ID = NULL matched nothing
		}
		return activities.findByWorkerIdOrderByActivityDateDesc(worker).stream().map(comment -> {
			JSONObject row = new JSONObject();
			if (comment.activityDate() != null) row.put("activityDate", DATE.format(comment.activityDate().toLocalDateTime()));
			if (comment.activityBy() != null) row.put("activityBy", comment.activityBy());
			if (comment.comments() != null) row.put("comments", comment.comments());
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	/**
	 * Adds a comment to a worker's profile, dated now.
	 *
	 * @param inOutParameterMap workerId, activityBy, comments
	 */
	@Transactional
	public void saveUserComments(Map<Object, Object> inOutParameterMap) {
		jpql.insert(NyehmsUsersActivity.builder()
				.workerId(longValue(inOutParameterMap.get("workerId")))
				.activityDate(now())
				.activityBy(emptyAsNull(inOutParameterMap.get("activityBy")))
				.comments(emptyAsNull(inOutParameterMap.get("comments")))
				.build());
	}

	/** The trainers: JSONObjects with TRAINER_ID (short name) and TRAINER_NAME (pkg_trk_common.fn_get_fullname). */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getTrainerList() {
		return users.findTrainers().stream().map(trainer -> {
			JSONObject row = new JSONObject();
			if (trainer[0] != null) row.put("TRAINER_ID", trainer[0]);
			if (trainer[1] != null) row.put("TRAINER_NAME", trainer[1]);
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	/** The bureaus without an end date, by description: JSONObjects with BUREAU_CD, BUREAU_DESC. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getBureauList() {
		return bureaus.findByEndDateIsNull(Sort.by("bureauDesc")).stream().map(bureau -> {
			JSONObject row = new JSONObject();
			if (bureau.bureauCd() != null) row.put("BUREAU_CD", bureau.bureauCd());
			if (bureau.bureauDesc() != null) row.put("BUREAU_DESC", bureau.bureauDesc());
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	/** The regional offices by description: JSONObjects with ADMIN_COUNTY_REGION_CD, ADMIN_COUNTY_REGION_DESC. */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getRegOfficeList() {
		return regionalOffices.findAllBy(Sort.by("adminCountyRegionDesc")).stream().map(office -> {
			JSONObject row = new JSONObject();
			if (office.adminCountyRegionCd() != null) row.put("ADMIN_COUNTY_REGION_CD", office.adminCountyRegionCd());
			if (office.adminCountyRegionDesc() != null) row.put("ADMIN_COUNTY_REGION_DESC", office.adminCountyRegionDesc());
			return row.isEmpty() ? null : row;
		}).collect(toList());
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * The permissions of a worker region's role (UNION the worker region's permissions outside the role); {@code join}
	 * is how the worker region's permissions are joined in the first part (LEFT OUTER JOIN: all the role's
	 * permissions, flagged; JOIN: only the worker region's). Native: the role column is picked at run time.
	 */
	private List<ModuleAndPermissionsVO> byWorkerRegion(Map<Object, Object> map, String join) {
		String role = roleColumn(map);
		Object workerRegionId = map.get("p_worker_region_id");
		return modulesAndPermissions(statements.list("""
				SELECT p.PERMISSION_ID, p.MODULE_ID,
				p.SUB_MODULE_ID,
				m.MODULE_NAME,
				sm.SUB_MODULE_NAME,
				p.PERMISSION_DESC,
				case when um.permission_id is not null then 'TRUE'
				else 'FALSE' end FLAG_SUGG

				  FROM permission p
				  JOIN module m ON (p.module_id = m.module_id)
				  LEFT OUTER JOIN sub_module sm ON (p.sub_module_id = sm.sub_module_id)
				  %2$s user_permission_mapping um ON (p.permission_id = um.permission_id and EFFECTIVE_END_DATE is null and worker_region_id = ?1)
				  where %1$s is not null
				  UNION
				SELECT p.PERMISSION_ID, p.MODULE_ID,
				        p.SUB_MODULE_ID,
				        m.MODULE_NAME,
				        sm.SUB_MODULE_NAME,
				        p.PERMISSION_DESC,
				        case when um.permission_id is not null then 'TRUE'
				        else 'FALSE' end FLAG_SUGG
				 FROM permission p
				JOIN module m ON (p.module_id = m.module_id)
				LEFT OUTER JOIN sub_module sm ON (p.sub_module_id = sm.sub_module_id)
				 JOIN user_permission_mapping um ON (p.permission_id = um.permission_id and um.EFFECTIVE_END_DATE is null and um.worker_region_id = ?2 and %1$s is null)
				order by MODULE_NAME, SUB_MODULE_NAME, PERMISSION_DESC asc
				""".formatted(role, join), row -> row, workerRegionId, workerRegionId));
	}

	/**
	 * The rows grouped as the former result map moduleAndPermissions grouped them (it has no id): one module per
	 * distinct MODULE_ID + MODULE_NAME in first-appearance order, its permissions in row order without repeated
	 * identical rows.
	 */
	private static List<ModuleAndPermissionsVO> modulesAndPermissions(List<Row> rows) {
		Map<List<Object>, ModuleAndPermissionsVO> modules = new LinkedHashMap<>();
		Map<List<Object>, Set<List<Object>>> seen = new HashMap<>();
		for (Row row : rows) {
			Object moduleId = row.get("MODULE_ID"), moduleName = row.get("MODULE_NAME");
			List<Object> moduleKey = Arrays.asList(moduleId, moduleName);
			ModuleAndPermissionsVO module = modules.computeIfAbsent(moduleKey, key -> {
				ModuleAndPermissionsVO vo = new ModuleAndPermissionsVO();
				if (moduleId != null) vo.setModuleId(((Number) moduleId).longValue());
				if (moduleName != null) vo.setModuleName(moduleName.toString());
				vo.setNyehmsPermissions(new ArrayList<>());
				return vo;
			});
			Object subModuleId = row.get("SUB_MODULE_ID"), subModuleName = row.get("SUB_MODULE_NAME"), permissionId = row.get("PERMISSION_ID"),
					permissionDesc = row.get("PERMISSION_DESC"), flag = row.get("FLAG_SUGG");
			List<Object> permissionKey = Arrays.asList(subModuleId, subModuleName, permissionId, permissionDesc, flag);
			if (permissionKey.stream().allMatch(value -> value == null) || !seen.computeIfAbsent(moduleKey, key -> new LinkedHashSet<>()).add(permissionKey)) {
				continue;
			}
			NyehmsPermission permission = new NyehmsPermission();
			if (subModuleId != null) permission.setSubModuleId(((Number) subModuleId).longValue());
			if (subModuleName != null) permission.setSubModuleName(subModuleName.toString());
			if (permissionId != null) permission.setPermissionId(((Number) permissionId).longValue());
			if (permissionDesc != null) permission.setPermissionDescription(permissionDesc.toString());
			if (flag != null) permission.setIsSuggestedPermission(flag.toString());
			module.getNyehmsPermissions().add(permission);
		}
		return new ArrayList<>(modules.values());
	}

	/**
	 * The role column (p_roleName), checked: one of the role names the service derives from CODED_ROLE (the
	 * description with every non-letter as '_', upper case).
	 */
	private String roleColumn(Map<Object, Object> map) {
		Object name = map.get("p_roleName");
		Set<String> allowed = roles.findAll().stream().map(CodedRole::getRoleDescription).filter(description -> description != null && !description.isEmpty())
				.map(description -> description.replaceAll("[^A-Za-z]", "_").toUpperCase()).collect(Collectors.toSet());
		if (name == null || !name.toString().matches("[A-Z_]+") || !allowed.contains(name.toString())) {
			throw new InvalidDataAccessApiUsageException("Not a role column: " + name);
		}
		return name.toString();
	}

	/** The NyehmsUsers attribute of an allow-listed notification column. */
	private static String notificationAttribute(Object actionName) {
		String attribute = actionName == null ? null : NOTIFICATION_COLUMNS.get(actionName.toString());
		if (attribute == null) {
			throw new InvalidDataAccessApiUsageException("Not a notification column: " + actionName);
		}
		return attribute;
	}

	/** Sets a notification column to a flag for the workers of a list (an array or collection of worker ids). */
	private void setNotification(Map<String, Object> map, String listKey, String flag) {
		String column = notificationAttribute(map.get("ACTION_NAME"));
		List<Long> workerIds = new ArrayList<>();
		Object list = map.get(listKey);
		Collection<?> values = list instanceof Object[] array ? Arrays.asList(array) : list instanceof Collection<?> collection ? collection : null;
		if (values == null || values.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No workers in " + listKey);       // "in ()" failed before
		}
		for (Object value : values) {
			workerIds.add(longValue(value));
		}
		jpql.update("update NyehmsUsers u set u." + column + " = ?1 where u.workerId in (?2)", flag, workerIds);
	}

	private static Map<String, String> notificationColumns(String... columnsAndAttributes) {
		Map<String, String> columns = new LinkedHashMap<>();
		for (int i = 0; i < columnsAndAttributes.length; i += 2) {
			columns.put(columnsAndAttributes[i], columnsAndAttributes[i + 1]);
		}
		return java.util.Collections.unmodifiableMap(columns);
	}

	/** The single result of a former selectOne: null for none, an error for several. */
	private static <T> T one(List<T> results) {
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.isEmpty() ? null : results.get(0);
	}

	/** '' was stored as NULL by Oracle. */
	private static String emptyAsNull(Object value) {
		String text = text(value);
		return text == null || text.isEmpty() ? null : text;
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static <T> java.util.stream.Collector<T, ?, List<T>> toList() {
		return Collectors.toCollection(ArrayList::new);
	}
}
