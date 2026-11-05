package gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.sql.Timestamp;
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
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsUsersVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.NyehmsWorkerRegionsViewVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.administration.UserRegionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedRole;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.RegionRoleRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.CodedNyehmsUserFacilityVwRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.CodedNyehmsUserTypeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.administration.CodedRegionRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedRoleRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsParameterRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsUsersVwRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.NyehmsWorkerRegionsVwRepository;
import lombok.RequiredArgsConstructor;

/**
 * Users, regions and roles of the administration screens (formerly a MyBatis mapper with the same name and methods).
 * <ul>
 * <li>Code lists, the region/role list, the user check and the profile save: Spring Data repositories.</li>
 * <li>User search, add / terminate user, add / retire region role, permissions and the profile with its two cursors:
 * the PKG_ADMINISTRATION procedures through {@link NativeStatements#call}; OUT values and cursor rows are put into
 * the caller's map under the same keys as before.</li>
 * <li>The six training / telecommute reports and the user details: native SQL, unchanged (values bound) - they use
 * Oracle date arithmetic (ADD_MONTHS(...) - n, a date minus SYSDATE compared with a number), LISTAGG ... WITHIN
 * GROUP over a CTE, ORDER BY a CASE over a select alias and the PL/SQL function pkg_trk_common.fn_get_fullname,
 * which JPQL cannot express as they are.</li>
 * </ul>
 * "Now" in the end-date conditions of the repository reads is the application server's clock (formerly SYSDATE).
 */
@Repository
@RequiredArgsConstructor
public class NyehmsWorkerRegionsViewDAO {

	private final NativeStatements statements;
	private final CodedRegionRepository regions;
	private final CodedRoleRepository roles;
	private final NyehmsParameterRepository parameters;
	private final NyehmsWorkerRegionsVwRepository workerRegions;
	private final NyehmsUsersVwRepository usersView;
	private final NyehmsUsersRepository users;
	private final CodedNyehmsUserTypeRepository userTypes;
	private final CodedNyehmsUserFacilityVwRepository userFacilities;

	/** The surveillance regions not ended: maps with regionCode, regionDescription (a null value left out). */
	public List<Map<String, String>> getSearchRegionDescription() throws DataAccessException {
		return regions.findByAssignmentTypeCodeAndEndDateIsNullOrAssignmentTypeCodeAndEndDateAfterOrderByRegionDescription("SURV", "SURV", now())
				.stream().map(region -> textMap("regionCode", region.regionCode(), "regionDescription", region.regionDescription()))
				.collect(toList());
	}

	/** All roles by description. */
	public List<NyehmsRole> getSearchRoleDescription() throws DataAccessException {
		return roles.findAll(Sort.by("roleDescription")).stream().map(NyehmsWorkerRegionsViewDAO::toRole).collect(toList());
	}

	/** The user statuses (parameter USER_STATUS). */
	public List<String> getSearchstatusDescription() throws DataAccessException {
		return parameters.findByParamName("USER_STATUS").stream().map(NyehmsParameterRepository.ParamValue::paramValue).collect(toList());
	}

	/**
	 * The user search: calls PKG_ADMINISTRATION.pr_user_search and puts its rows (NyehmsWorkerRegionsViewVO) into the
	 * map as p_users_out.
	 *
	 * @param map p_fname, p_lname, p_mname, p_shortname, p_email, p_region_cd (texts), p_role_cd (a whole number)
	 */
	public void getUsersList(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_user_search",
				param(map.get("p_fname"), String.class),
				param(map.get("p_lname"), String.class),
				param(map.get("p_mname"), String.class),
				param(map.get("p_shortname"), String.class),
				param(map.get("p_email"), String.class),
				param(map.get("p_region_cd"), String.class),
				param(map.get("p_role_cd"), Integer.class),
				cursor(USER_SEARCH_ROWS));
		map.put("p_users_out", results.get(7));
	}

	/** A worker's regions and roles, the dates as MM/DD/YYYY text, sorted by the end date text (empty first). */
	public List<UserRegionVO> getRegionRoleByUserIdList(long workerId) {
		return workerRegions.findRegionRoles(workerId).stream().map(NyehmsWorkerRegionsViewDAO::toUserRegion).collect(toList());
	}

	/**
	 * The worker id (as text) of the user with this short name (ignoring case), not ended.
	 *
	 * @return the id, or null if there is none
	 * @throws IncorrectResultSizeDataAccessException if there are several (as before)
	 */
	public String isUserExists(String legacyUid) {
		if (legacyUid == null) {
			return null;                    // UPPER(LEGACY_UID) = UPPER(NULL) matched nothing
		}
		List<String> ids = usersView.findByLegacyUidIgnoreCaseAndEndDateIsNullOrLegacyUidIgnoreCaseAndEndDateAfter(legacyUid, legacyUid, now())
				.stream().map(row -> row.workerId() == null ? null : row.workerId().toString()).toList();
		return one(ids);
	}

	/**
	 * Creates a user: calls PKG_ADMINISTRATION.pr_add_new_user and puts p_code_out (Integer) and p_msg_out into the
	 * map.
	 */
	public void creatingNewUser(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_add_new_user",
				param(map.get("p_legacy_uid"), String.class),
				param(map.get("p_first_name"), String.class),
				param(map.get("p_last_name"), String.class),
				param(map.get("p_middle_name"), String.class),
				param(map.get("p_start_date"), Timestamp.class),
				param(map.get("p_email_addr"), String.class),
				param(map.get("p_last_conf_secure_trng_date"), Timestamp.class),
				param(map.get("p_mac_address"), String.class),
				param(map.get("p_region_code"), String.class),
				param(map.get("p_role_code"), Integer.class),
				param(map.get("p_created_by"), Integer.class),
				param(map.get("p_permissions_list"), String.class),
				out(Integer.class),
				out(String.class),
				param(map.get("p_vpn"), String.class),
				param(map.get("p_vpn_approve_date"), Timestamp.class),
				param(map.get("p_telecommute"), String.class),
				param(map.get("p_telecommute_trng_date"), Timestamp.class),
				param(map.get("p_user_type"), String.class),
				param(map.get("p_facility_group_cd"), String.class),
				param(map.get("p_bureau_start_date"), String.class),
				param(map.get("p_position_start_date"), String.class),
				param(map.get("p_pc_access_at_location"), String.class),
				param(map.get("p_bureau_name"), String.class),
				param(map.get("p_remote_terminal_access"), String.class),
				param(map.get("p_comments"), String.class),
				param(map.get("p_tlcmmtTrngAtstn"), String.class),
				param(map.get("p_cnfSecTrngAtstn"), String.class),
				param(map.get("p_tlcmmtTrngBy"), String.class),
				param(map.get("p_cnfSecTrngBy"), String.class),
				param(map.get("p_reg_office"), String.class));
		map.put("p_code_out", results.get(12));
		map.put("p_msg_out", results.get(13));
	}

	/**
	 * Adds a region and role to a user: calls PKG_ADMINISTRATION.pr_add_user_region_role and puts p_code_out and
	 * p_msg_out into the map.
	 */
	public void addingNewRegionRole(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_add_user_region_role",
				param(map.get("p_user_id"), Integer.class),
				param(map.get("p_region_code"), String.class),
				param(map.get("p_role_code"), Integer.class),
				param(map.get("p_created_by"), Integer.class),
				param(map.get("p_permissions_list"), String.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(5));
		map.put("p_msg_out", results.get(6));
	}

	/**
	 * Retires a region and role of a user: calls PKG_ADMINISTRATION.pr_end_user_region and puts p_code_out and
	 * p_msg_out into the map.
	 */
	public void retireRegionRole(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_end_user_region",
				param(map.get("p_user_region_id"), Integer.class),
				param(map.get("P_END_DATE"), Timestamp.class),
				param(map.get("p_terminated_by"), String.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/**
	 * Saves a user's profile (the current row of the worker): names, e-mail, VPN, telecommute, bureau and training
	 * values; the three dates (lastConfidentialityAndSecurityTrainingDate, vpnApproveDate, telecommuteTrainingDate)
	 * are MM/DD/YYYY text, an empty one clears the date.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int saveUserDetails(Map<Object, Object> map) {
		return users.updateProfile(text(map.get("firstName")), text(map.get("lastName")), longValue(map.get("lastUpdatedBy")),
				text(map.get("emailAddress")), text(map.get("vpnAccess")), text(map.get("telecommute")),
				text(map.get("bureauStartDate")), text(map.get("positionStartDate")), text(map.get("pcAccessAtLocation")),
				text(map.get("bureauName")), text(map.get("remoteTerminalAccess")), text(map.get("p_tlcmmtTrngAtstn")),
				text(map.get("p_cnfSecTrngAtstn")), text(map.get("p_tlcmmtTrngBy")), text(map.get("p_cnfSecTrngBy")),
				text(map.get("regOffice")), dateText(map.get("lastConfidentialityAndSecurityTrainingDate")),
				dateText(map.get("vpnApproveDate")), dateText(map.get("telecommuteTrainingDate")), text(map.get("isLocked")),
				longValue(map.get("workerId")));
	}

	/** Terminates a user: calls PKG_ADMINISTRATION.pr_terminate_user and puts p_code_out and p_msg_out into the map. */
	public void terminateUser(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_terminate_user",
				param(map.get("p_user_id"), Integer.class),
				param(map.get("p_terminated_by"), Integer.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(2));
		map.put("p_msg_out", results.get(3));
	}

	/**
	 * A user's profile: calls PKG_ADMINISTRATION.pr_get_user_profile and puts its two cursors into the map,
	 * p_user_basic_out (NyehmsUsersVO) and p_region_roles_out (UserRegionVO).
	 *
	 * @param map p_worker_id (a whole number)
	 */
	public void getUsersMacAddressAndRegionDetails(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_get_user_profile",
				param(map.get("p_worker_id"), Long.class),
				cursor(USER_BASIC_ROWS),
				cursor(REGION_ROLE_ROWS));
		map.put("p_user_basic_out", results.get(1));
		map.put("p_region_roles_out", results.get(2));
	}

	/**
	 * Changes the permissions of a worker region: calls PKG_ADMINISTRATION.pr_edit_user_region_perms and puts
	 * p_code_out and p_msg_out into the map.
	 */
	public void editUserPermissions(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_ADMINISTRATION.pr_edit_user_region_perms",
				param(map.get("p_worker_region_id"), Integer.class),
				param(map.get("p_updated_by"), Integer.class),
				param(map.get("p_permissions_list"), String.class),
				out(Integer.class),
				out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	/** The user types: maps with userTypeCode, userTypeDesc (a null value left out). */
	public List<Map<String, String>> getUserTypes() throws DataAccessException {
		return userTypes.findAllBy().stream().map(type -> textMap("userTypeCode", type.userTypeCode(), "userTypeDesc", type.userTypeDesc()))
				.collect(toList());
	}

	/** The facility groups: maps with userFacilityGroupCode, userFacilityGroupDesc (a null value left out). */
	public List<Map<String, String>> getUserFacilityNames() throws DataAccessException {
		return userFacilities.findAllBy().stream()
				.map(group -> textMap("userFacilityGroupCode", group.facilityGroupCd(), "userFacilityGroupDesc", group.facilityDispName()))
				.collect(toList());
	}

	/** The confidentiality and security training report (dates MM/DD/YYYY text): JSONObjects with the upper-case column labels. */
	public List<JSONObject> getAdminModuleReportData(Map<Object, Object> map) {
		return statements.list(ADMIN_MODULE_REPORT, Rows.json(), map.get("p_from_date"), map.get("p_to_date"));
	}

	/** One user's details and comments (as one NyehmsUsersVO, auto-mapped by the column labels as before), or null. */
	public NyehmsUsersVO getUserDetailsData(int workerId) {
		return statements.one(USER_DETAILS, Rows.bean(NyehmsUsersVO.class), workerId, workerId);
	}

	/** The telecommute training report for telecommuters (dates MM/DD/YYYY text). */
	public List<JSONObject> getTeleCommuteReportData(Map<Object, Object> map) {
		return statements.list(TELECOMMUTE_REPORT, Rows.json(), map.get("p_from_date"), map.get("p_to_date"));
	}

	/** The training report of the users who do not telecommute. */
	public List<JSONObject> getNonTeleCommuteReportData(Map<Object, Object> map) {
		return statements.list(NON_TELECOMMUTE_REPORT, Rows.json());
	}

	/** The users without a confidentiality and security training attestation. */
	public List<JSONObject> getTrainingAttstData() {
		return statements.list(TRAINING_ATTESTATION, Rows.json());
	}

	/** The telecommuters without a telecommute training attestation whose training is over a year old. */
	public List<JSONObject> getTeleworkAttstData() {
		return statements.list(TELEWORK_ATTESTATION, Rows.json());
	}

	// ---------------------------------------------------------------------------------------------------------------
	// the cursor rows, mapped as the former result maps mapped them

	/** The user search rows (former result map getUsersResult). */
	static final RowMapper<NyehmsWorkerRegionsViewVO> USER_SEARCH_ROWS = new ResultMapRows<>(NyehmsWorkerRegionsViewVO.class,
			"WORKERREGIONID", "workerRegionId", "WORKER_ID", "workerId", "ROLE_CODE", "roleCode", "ROLE_DESC", "roleDescription",
			"REGION_CODE", "regionCode", "REGION_DESCRIPTION", "regionDescription", "FULL_NAME", "fullName", "FIRST_NAME", "firstName",
			"LAST_NAME", "lastName", "MIDDLE_NAME", "middleName", "LEGACY_UID", "legacyUid", "BUREAU_START_DATE", "bureauStartDate",
			"POSITION_START_DATE", "positionStartDate", "PC_ACCESS_AT_LOCATION", "pcAccessAtLocation", "BUREAU_NAME", "bureauName",
			"REMOTE_TERMINAL_ACCESS", "remoteTerminalAccess", "TELECOMMUTE_TRNG_ATTEST_REC", "tlcmmtTrngAtstn",
			"CONF_SECURITY_TRNG_ATTEST_REC", "cnfSecTrngAtstn", "TELECOMMUTE_TRNG_BY", "tlcmmtTrngBy", "CONF_SECURITY_TRNG_BY", "cnfSecTrngBy",
			"CREATED_DATE", "createdDate", "END_DATE", "endDate", "WR_START_DATE", "wrStartDate", "WR_END_DATE", "wrEndDate",
			"EMAIL_ADDR", "emailAddress", "START_DATE", "effectiveStartDate",
			"LAST_CONF_SECURITY_TRNG_DATE", "lastConfidentialityAndSecurityTrainingDate", "LAST_LOGIN_DATE", "lastLoginDate",
			"IS_LOCKED", "isLocked");

	/** The profile's basic row (former result map getNyehmsUserDetails). */
	static final RowMapper<NyehmsUsersVO> USER_BASIC_ROWS = new ResultMapRows<>(NyehmsUsersVO.class,
			"WORKER_ID", "workerId", "FIRST_NAME", "firstName", "LAST_NAME", "lastName", "MIDDLE_NAME", "middleName",
			"LEGACY_UID", "legacyUid", "EMAIL_ADDR", "emailAddress", "IS_LOCKED", "isLocked",
			"LAST_CONF_SECURITY_TRNG_DATE", "lastConfidentialityAndSecurityTrainingDate", "VPN", "vpnAccess",
			"VPN_APPROVE_DATE", "vpnApproveDate", "TELECOMMUTE", "telecommute", "TELECOMMUTE_TRNG_DATE", "telecommuteTrainingDate",
			"USER_TYPE", "userType", "FACILITY_GROUP_CD", "userFacilityGroupCode", "BUREAU_START_DATE", "bureauStartDate",
			"POSITION_START_DATE", "positionStartDate", "PC_ACCESS_AT_LOCATION", "pcAccessAtLocation", "BUREAU_NAME", "bureauName",
			"ADMIN_COUNTY_REGION_CD", "regOffice", "REMOTE_TERMINAL_ACCESS", "remoteTerminalAccess",
			"TELECOMMUTE_TRNG_ATTEST_REC", "tlcmmtTrngAtstn", "CONF_SECURITY_TRNG_ATTEST_REC", "cnfSecTrngAtstn",
			"TELECOMMUTE_TRNG_BY", "tlcmmtTrngBy", "CONF_SECURITY_TRNG_BY", "cnfSecTrngBy");

	/** The profile's region and role rows (former result map getRegionRoleDetails). */
	static final RowMapper<UserRegionVO> REGION_ROLE_ROWS = new ResultMapRows<>(UserRegionVO.class,
			"worker_region_id", "workerRegionId", "ROLE_DESC", "roleDescription", "REGION_DESCRIPTION", "regionDescription",
			"START_DATE", "startDate", "END_DATE", "endDate");

	/**
	 * Rows mapped as a MyBatis result map without nested mappings (default settings) mapped them: first the columns
	 * the result map does not name are auto-mapped to the property of the same name (ignoring case) unless the result
	 * map maps that property itself, then the named columns are set on their properties; a null value is not set (a
	 * later non-null one wins), each value is read as its property's type, and a row that sets nothing is null.
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

	// ---------------------------------------------------------------------------------------------------------------

	private static NyehmsRole toRole(CodedRole role) {
		NyehmsRole vo = new NyehmsRole();
		boolean set = false;
		if (role.getRoleCode() != null) { vo.setRoleCode(role.getRoleCode()); set = true; }
		if (role.getRoleDescription() != null) { vo.setRoleDescription(role.getRoleDescription()); set = true; }
		if (role.getIsExpandable() != null && !role.getIsExpandable().isEmpty()) { vo.setIsExpandable(role.getIsExpandable().charAt(0)); set = true; }
		return set ? vo : null;
	}

	private static UserRegionVO toUserRegion(RegionRoleRow row) {
		UserRegionVO vo = new UserRegionVO();
		boolean set = false;
		if (row.workerRegionId() != null) { vo.setWorkerRegionId(row.workerRegionId()); set = true; }
		if (row.roleDesc() != null) { vo.setRoleDescription(row.roleDesc()); set = true; }
		if (row.regionDescription() != null) { vo.setRegionDescription(row.regionDescription()); set = true; }
		if (row.startDate() != null) { vo.setStartDate(row.startDate()); set = true; }
		if (row.endDate() != null) { vo.setEndDate(row.endDate()); set = true; }
		return set ? vo : null;
	}

	/** A map of two text entries, a null value left out; null if both are null (as a MyBatis map result). */
	private static Map<String, String> textMap(String key1, String value1, String key2, String value2) {
		Map<String, String> map = new HashMap<>();
		if (value1 != null) map.put(key1, value1);
		if (value2 != null) map.put(key2, value2);
		return map.isEmpty() ? null : map;
	}

	/** The text of a MM/DD/YYYY date: null when null or empty (the former choose wrote '', i.e. NULL). */
	private static String dateText(Object value) {
		return value == null || "".equals(value) ? null : value.toString();
	}

	/** An IN parameter bound with its value's type (the declared type when null). */
	private static CallParam param(Object value, Class<?> nullType) {
		return in(value, value == null ? nullType : value.getClass());
	}

	/** The single result of a former selectOne: null for none, an error for several. */
	private static <T> T one(List<T> results) {
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.isEmpty() ? null : results.get(0);
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	private static <T> java.util.stream.Collector<T, ?, List<T>> toList() {
		return Collectors.toCollection(ArrayList::new);
	}

	// ---------------------------------------------------------------------------------------------------------------
	// the native reports: the former SQL unchanged, #{...} bound as ?1, ?2

	private static final String ADMIN_MODULE_REPORT = """
			WITH data AS
			(
			  SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			  FROM nyehms_users_activity a
			  LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			), final_notes AS
			(
			  SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			    FROM data
			GROUP BY worker_id
			)
			SELECT a.worker_id, legacy_uid,
			     pkg_trk_common.fn_get_fullname (last_name,
			                                    first_name,
			                                    middle_name,
			                                    NULL) user_name, last_name, first_name, email_addr, bureau_name,
			    last_login_date, is_locked, end_date, TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') SNC_TRNG_DT, CONF_SECURITY_TRNG_BY, CONF_SECURITY_TRNG_ATTEST_REC,
			    telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') TELE_TRNG_DT, TELECOMMUTE_TRNG_BY, TELECOMMUTE_TRNG_ATTEST_REC,
			     user_type, vpn,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpn_approve_date, pc_access_at_location, remote_terminal_access,
			     CASE
			         WHEN is_locked = 'Y'  and user_type = 'APP'
			          THEN 'User_Inactive'
			         WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) <= TRUNC(SYSDATE)
			         THEN 'C&S_Due_Now'
			         WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 30 < TRUNC(SYSDATE)
			         THEN 'C&S_Due_In_30Days'
			         WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 60 < TRUNC(SYSDATE)
			         THEN 'C&S_Due_In_60Days'
			         WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 90 < TRUNC(SYSDATE)
			         THEN 'C&S_Due_In_90Days'
			         ELSE 'Active'
			     END snc_due_in_days,
			     b.notes,
			     a.admin_county_region_cd, c.admin_county_region_desc county_region
			FROM nyehms_users a
			LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			LEFT JOIN coded_admin_county_region c ON (c.admin_county_region_cd = a.admin_county_region_cd)
			WHERE a.END_DATE is null AND
				(Trunc(last_conf_security_trng_date) BETWEEN To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY')

				or

				Add_Months(TRUNC(last_conf_security_trng_date), 12) - sysdate < 90 )

				ORDER BY
				CASE WHEN snc_due_in_days = 'User_Inactive' THEN '5'
					 WHEN snc_due_in_days = 'C&S_Due_In_30Days' THEN '2'
					 WHEN snc_due_in_days = 'C&S_Due_In_60Days' THEN '3'
					 WHEN snc_due_in_days = 'C&S_Due_In_90Days' THEN '4'
					 WHEN snc_due_in_days = 'C&S_Due_Now' THEN '1'
				 END,
				last_conf_security_trng_date desc
			""";

	private static final String TRAINING_ATTESTATION = """
			WITH data AS
			(
			  SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			  FROM nyehms_users_activity a
			  LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			), final_notes AS
			(
			  SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			    FROM data
			GROUP BY worker_id
			)
			SELECT a.worker_id, legacy_uid,
			     pkg_trk_common.fn_get_fullname (last_name,
			                                    first_name,
			                                    middle_name,
			                                    NULL) user_name, last_name, first_name, email_addr, bureau_name,
			    last_login_date, is_locked, end_date, TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') SNC_TRNG_DT, CONF_SECURITY_TRNG_BY, CONF_SECURITY_TRNG_ATTEST_REC,
			    telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') TELE_TRNG_DT, TELECOMMUTE_TRNG_BY, TELECOMMUTE_TRNG_ATTEST_REC,
			     user_type, vpn,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpn_approve_date, pc_access_at_location, remote_terminal_access,
			     b.notes
			FROM nyehms_users a
			LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			WHERE a.END_DATE is null AND nvl(CONF_SECURITY_TRNG_ATTEST_REC, 'N') = 'N'
				order by
				user_name asc
			""";

	private static final String TELEWORK_ATTESTATION = """
			WITH data AS
			(
			  SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			  FROM nyehms_users_activity a
			  LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			), final_notes AS
			(
			  SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			    FROM data
			GROUP BY worker_id
			)
			SELECT a.worker_id, legacy_uid,
			     pkg_trk_common.fn_get_fullname (last_name,
			                                    first_name,
			                                    middle_name,
			                                    NULL) user_name, last_name, first_name, email_addr, bureau_name,
			    last_login_date, is_locked, end_date, TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') SNC_TRNG_DT, CONF_SECURITY_TRNG_BY, CONF_SECURITY_TRNG_ATTEST_REC,
			    telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') TELE_TRNG_DT, TELECOMMUTE_TRNG_BY, TELECOMMUTE_TRNG_ATTEST_REC,
			     user_type, vpn,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpn_approve_date, pc_access_at_location, remote_terminal_access,
			     b.notes,
			     a.admin_county_region_cd, c.admin_county_region_desc county_region
			FROM nyehms_users a
			LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			LEFT JOIN coded_admin_county_region c ON (c.admin_county_region_cd = a.admin_county_region_cd)
			WHERE a.END_DATE is null AND nvl(TELECOMMUTE_TRNG_ATTEST_REC, 'N') = 'N'
			and nvl(telecommute, 'X') = 'Y'
			and Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) - sysdate < 0
				order by
				user_name asc
			""";

	private static final String TELECOMMUTE_REPORT = """
			WITH data AS
			(
			  SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			  FROM nyehms_users_activity a
			  LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			), final_notes AS
			(
			  SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			    FROM data
			GROUP BY worker_id
			)
			SELECT a.worker_id, legacy_uid,
			     pkg_trk_common.fn_get_fullname (last_name,
			                                    first_name,
			                                    middle_name,
			                                    NULL) user_name, last_name, first_name, email_addr, bureau_name,
			    last_login_date, is_locked, end_date, TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') SNC_TRNG_DT, CONF_SECURITY_TRNG_BY, CONF_SECURITY_TRNG_ATTEST_REC,
			    telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') TELE_TRNG_DT, TELECOMMUTE_TRNG_BY, TELECOMMUTE_TRNG_ATTEST_REC,
			     user_type, vpn,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpn_approve_date, pc_access_at_location, remote_terminal_access,
			     CASE
			         WHEN is_locked = 'Y'
			          THEN 'User_Inactive'
			         WHEN Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) <= TRUNC(SYSDATE)
			         THEN 'Due_Now'
			         WHEN Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) - 30 < TRUNC(SYSDATE)
			         THEN 'Due_In_30Days'
			         WHEN Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) - 60 < TRUNC(SYSDATE)
			         THEN 'Due_In_60Days'
			         WHEN Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) - 90 < TRUNC(SYSDATE)
			         THEN 'Due_In_90Days'
			         ELSE 'Active'
			     END snc_due_in_days,
			     b.notes,
			     a.admin_county_region_cd, c.admin_county_region_desc county_region
			FROM nyehms_users a
			LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			LEFT JOIN coded_admin_county_region c ON (c.admin_county_region_cd = a.admin_county_region_cd)
			WHERE a.END_DATE is null AND
			    nvl(TELECOMMUTE, 'X') = 'Y' and
				(Trunc(TELECOMMUTE_TRNG_DATE) BETWEEN To_Date(?1, 'MM/DD/YYYY') AND To_Date(?2, 'MM/DD/YYYY')
				or

				Add_Months(TRUNC(TELECOMMUTE_TRNG_DATE), 12) - sysdate < 90 )

				ORDER BY
				CASE WHEN snc_due_in_days = 'User_Inactive' THEN '5'
					 WHEN snc_due_in_days = 'Due_In_30Days' THEN '2'
					 WHEN snc_due_in_days = 'Due_In_60Days' THEN '3'
					 WHEN snc_due_in_days = 'Due_In_90Days' THEN '4'
					 WHEN snc_due_in_days = 'Due_Now' THEN '1'
				 END,
				TELECOMMUTE_TRNG_DATE desc
			""";

	private static final String NON_TELECOMMUTE_REPORT = """
			WITH data AS
			(
			  SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			  FROM nyehms_users_activity a
			  LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			), final_notes AS
			(
			  SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			    FROM data
			GROUP BY worker_id
			)
			SELECT a.worker_id, legacy_uid,
			     pkg_trk_common.fn_get_fullname (last_name,
			                                    first_name,
			                                    middle_name,
			                                    NULL) user_name, last_name, first_name, email_addr, bureau_name,
			    last_login_date, is_locked, end_date, TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') SNC_TRNG_DT, CONF_SECURITY_TRNG_BY, CONF_SECURITY_TRNG_ATTEST_REC,
			    telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') TELE_TRNG_DT, TELECOMMUTE_TRNG_BY, TELECOMMUTE_TRNG_ATTEST_REC,
			     user_type, vpn,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpn_approve_date, pc_access_at_location, remote_terminal_access,
			     b.notes,
			     a.admin_county_region_cd, c.admin_county_region_desc county_region
			FROM nyehms_users a
			LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			LEFT JOIN coded_admin_county_region c ON (c.admin_county_region_cd = a.admin_county_region_cd)
			WHERE a.END_DATE is null AND
				nvl(TELECOMMUTE, 'X') != 'Y'
			""";

	private static final String USER_DETAILS = """
			    WITH data AS
			    (
			      SELECT a.worker_id, activity_date, To_Char(a.activity_date,'MM/DD/YYYY HH:MI:SS AM') || ' - '||
			            Nvl(Trim(pkg_trk_common.fn_get_fullname (b.last_name,
			                                           b.first_name,
			                                           b.middle_name,
			                                           NULL)), a.activity_by)
			            ||' - '|| a.comments notes
			      FROM nyehms_users_activity a
			      LEFT JOIN nyehms_users b ON (b.legacy_uid = a.activity_by AND b.end_date IS NULL)
			      WHERE a.worker_id = ?1
			    ), final_notes AS
			    (
			      SELECT worker_id, LISTAGG(notes, chr(10)) WITHIN GROUP (ORDER BY activity_date) notes
			        FROM data
			    GROUP BY worker_id
			    )
			    SELECT a.worker_id workerId, legacy_uid legacyUid , last_name lastName, first_name firstName,
			         pkg_trk_common.fn_get_fullname (last_name,
			                                        first_name,
			                                        middle_name,
			                                        NULL) user_name, email_addr emailAddress, bureau_name bureauName,
			        last_login_date lastLoginDate, is_locked, end_date,
			        TO_CHAR(last_conf_security_trng_date, 'MM/DD/YYYY') lastConfidentialityAndSecurityTrainingDate, CONF_SECURITY_TRNG_BY cnfSecTrngBy,
			        telecommute, TO_CHAR(telecommute_trng_date, 'MM/DD/YYYY') telecommuteTrainingDate, TELECOMMUTE_TRNG_BY tlcmmtTrngBy,
			        user_type userType, vpn vpnAccess,  TO_CHAR(vpn_approve_date, 'MM/DD/YYYY') vpnApproveDate, pc_access_at_location pcAccessAtLocation,
			        remote_terminal_access remoteTerminalAccess, CONF_SECURITY_TRNG_ATTEST_REC cnfSecTrngAtstn, TELECOMMUTE_TRNG_ATTEST_REC tlcmmtTrngAtstn,
			         CASE
			             WHEN is_locked = 'Y'
			              THEN 'User_inactive'
			             WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) <= TRUNC(SYSDATE)
			             THEN 'SnC_due_now'
			             WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 30 < TRUNC(SYSDATE)
			             THEN 'SnC_due_in_30days'
			             WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 60 < TRUNC(SYSDATE)
			             THEN 'SnC_due_in_60days'
			             WHEN Add_Months(TRUNC(last_conf_security_trng_date), 12) - 90 < TRUNC(SYSDATE)
			             THEN 'SnC_due_in_90days'
			             ELSE 'Active'
			         END snc_due_in_days,
			         b.notes userComments
			    FROM nyehms_users a
			    LEFT JOIN final_notes b ON (b.worker_id = a.worker_id)
			    WHERE a.worker_id = ?2
			""";
}
