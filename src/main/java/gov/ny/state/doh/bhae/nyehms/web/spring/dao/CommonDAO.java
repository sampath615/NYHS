package gov.ny.state.doh.bhae.nyehms.web.spring.dao;

import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.in;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.administration.SearchManageProfileDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.NyehmsAppErrorLog;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.BoundValues;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.WebServiceConstants;
import lombok.RequiredArgsConstructor;

/**
 * The shared lookups and writes of the application: the login (user object, permissions, login time, training date,
 * access agreement), the error log, the address drop-downs and code lists, the eHARS links of a state number, the
 * notification and reminder lists (formerly a MyBatis mapper with the same name and methods; the callers are
 * unchanged).
 * <p>
 * JPQL over the entities NyehmsUsersVw, NyehmsUsers, NyehmsWorkerPermissionVw, NyehmsCountyReference, CodedCountry,
 * CodedCounty, CodedPsMsgType, CodedActivityType, CodedWebServiceConstants, NyehmsEharsStatenoMap and EharsPersonNyc;
 * the error log is the entity NyehmsAppErrorLog (its key from APP_ERROR_LOG_SEQ, as before); the access agreement an
 * HQL insert (UserAccessAuditLog). The former {@code LISTAGG}s are joined in Java. "Now" in the end-date conditions,
 * the login time and the agreement time are the application server's clock (formerly SYSDATE). Native SQL
 * ({@link CommonSql}, the mapper's text with bound values), with the reason:
 * <ul>
 * <li>{@link #getCountyCodes}: ordered by {@code DECODE(..., ROWNUM)};</li>
 * <li>{@link #getSoundexValue}, {@link #getCurrentActiveStateNo}, {@link #getCryptoImageName}: a function selected
 * {@code FROM DUAL} ({@code SOUNDEX}, a PL/SQL function);</li>
 * <li>{@link #getDocumentListForReminder}: Oracle date arithmetic with a scalar subquery;</li>
 * <li>{@link #nyehmsInactiveUserDetailsList}, {@link #nyehmsTrainingUsersExpireList}: a PL/SQL function, date
 * arithmetic, {@code TO_CHAR} day and month names, {@code REGEXP_LIKE}.</li>
 * </ul>
 * The PL/SQL function and procedure run through {@link NativeStatements#callFunction} / {@link NativeStatements#call}.
 * The column names the callers choose are allow-listed: the address field (the columns of NYEHMS_COUNTY_REFERENCE) and
 * the notification column ({@link SearchManageProfileDAO#NOTIFICATION_COLUMNS}); anything else is refused.
 */
@Repository
@RequiredArgsConstructor
public class CommonDAO {

	private final NativeStatements statements;
	private final JpqlStatements jpql;

	/** The address fields a drop-down may list (the former {@code ${fieldName}}): the column, its entity attribute. */
	static final Map<String, String> ADDRESS_FIELDS = Map.of("CITY", "city", "ZIP_CD", "zipCd", "STATE_CD", "stateCd", "COUNTY", "county",
			"COUNTY_FIPS_CD", "countyFipsCd");

	/** A row's first two columns as CODE and VALUE (resultType {@code java.util.Map}; a null is not put). */
	static final RowMapper<Map<String, String>> CODE_VALUE = row -> {
		Map<String, String> values = new HashMap<>();
		if (row.get(0) != null) {
			values.put("CODE", Values.text(row.get(0)));
		}
		if (row.get(1) != null) {
			values.put("VALUE", Values.text(row.get(1)));
		}
		return values.isEmpty() ? null : values;
	};

	// ---- the login ----------------------------------------------------------------------------------------------------

	/**
	 * Writes an application error to NYEHMS_APP_ERROR_LOG with a key from APP_ERROR_LOG_SEQ.
	 *
	 * @param map p_log_date, p_user_id, p_session_id, p_log_message, p_log_error, p_log_stacktrace; afterwards also
	 *        appLogId (the key, a Long)
	 */
	public void writeLogToDB(Map<Object, Object> map) throws DataAccessException {
		NyehmsAppErrorLog entry = NyehmsAppErrorLog.builder().appLogDate(BoundValues.timestamp(map.get("p_log_date")))
				.appUserId(BoundValues.text(map.get("p_user_id"))).appSessionId(BoundValues.text(map.get("p_session_id")))
				.appLogMsg(BoundValues.text(map.get("p_log_message"))).appLogError(BoundValues.text(map.get("p_log_error")))
				.appLogStacktrace(BoundValues.text(map.get("p_log_stacktrace"))).build();
		jpql.insert(entry);
		map.put("appLogId", entry.getAppLogId());
	}

	/** The number of rows granting a worker a permission on a module. @param map workeId, moduleName, permissionId */
	public int getPermission(Map<Object, Object> map) throws DataAccessException {
		Long count = jpql.one("select count(p) from NyehmsWorkerPermissionVw p where p.workerId = ?1 and p.moduleName = ?2 and p.permissionId = ?3",
				Rows.scalar(Long.class), BoundValues.longValue(map.get("workeId")), BoundValues.text(map.get("moduleName")),
				BoundValues.longValue(map.get("permissionId")));
		return count.intValue();
	}

	/** The permissions of a worker (in a region, if one is given): permissionId, permission (texts). @param map workerId, workerRegionId */
	public List<Map<String, String>> getUserPermissions(Map<Object, Object> map) throws DataAccessException {
		Object region = map.get("workerRegionId");
		List<Object> parameters = new ArrayList<>();
		parameters.add(BoundValues.longValue(map.get("workerId")));
		String jpqlText = "select distinct p.permissionId as PERMISSION_ID, p.permission as PERMISSION from NyehmsWorkerPermissionVw p where p.workerId = ?1";
		if (region != null && !"".equals(region)) {
			jpqlText += " and p.workerRegionId = ?2";
			parameters.add(BoundValues.text(region));
		}
		return jpql.list(jpqlText, Rows.textMap("PERMISSION_ID", "permissionId", "PERMISSION", "permission"), parameters.toArray());
	}

	/** The user object of a current (not ended) worker by login id (ignoring case), or null. */
	public UserObject getUserObject(String userId) throws DataAccessException {
		return jpql.one("""
				select u.firstName as USERFIRSTNAME, u.lastName as USERLASTNAME, concat(u.firstName, ' ', u.lastName) as USERNAME,
				       u.workerId as WORKERID, u.legacyUid as USERID, u.fullName as USERFULLNAME, u.lastLoginDate as LASTLOGIN,
				       u.emailAddr as emailAddress
				from NyehmsUsersVw u where upper(u.legacyUid) = upper(?1) and (u.endDate is null or u.endDate > ?2)""",
				Rows.bean(UserObject.class), userId, now());
	}

	/** The last confidentiality/security training date of a current worker, or null. */
	public Date getCNSTrainingDate(String userId) throws DataAccessException {
		return jpql.one("select u.lastConfSecurityTrngDate from NyehmsUsersVw u where u.legacyUid = ?1 and (u.endDate is null or u.endDate > ?2)",
				Rows.scalar(Date.class), userId, now());
	}

	/** Sets the worker's last login to now. @return the rows changed */
	public int setLoginTimeStamp(int workerId) throws DataAccessException {
		return jpql.update("update NyehmsUsers u set u.lastLoginDate = ?1 where u.workerId = ?2", now(), (long) workerId);
	}

	/** Records that a worker agreed to the access terms now (USER_ACCESS_AUDIT_LOG). @param inputParameter WORKER_ID */
	public int insertNyehmsAgreedTime(Map<String, Object> inputParameter) {
		return jpql.update("insert into UserAccessAuditLog (workerId, agreedDate) values (?1, ?2)", BoundValues.longValue(inputParameter.get("WORKER_ID")), now());
	}

	/** pkg_standardization.fn_improve_std_address: standardizes an address. @param map customAddress; afterwards also standardAddress */
	public String getImprovedStandardAddress(Map<Object, Object> map) throws DataAccessException {
		Object standardAddress = statements.callFunction("pkg_standardization.fn_improve_std_address", String.class,
				in(BoundValues.text(map.get("customAddress")), String.class));
		map.put("standardAddress", standardAddress);
		return (String) standardAddress;
	}

	/** Pkg_trk_common.pr_set_client_session: marks the session on the database connection. */
	public void setClientSession() throws DataAccessException {
		statements.call("Pkg_trk_common.pr_set_client_session");
	}

	// ---- addresses and code lists -------------------------------------------------------------------------------------

	/**
	 * The values of an address field for a drop-down: the counties (with their FIPS codes) of a city/ZIP in New York,
	 * or the distinct upper-cased values of the field for a city/ZIP out of state (county 'Out of State', no state)
	 * or in a state (county too, unless 'Out of State' or 'Unknown'). A missing city, ZIP, state or county matches
	 * everything, as the former {@code NULL || '%'} did.
	 *
	 * @param addressFieldVO fieldName (CITY, ZIP_CD, STATE_CD, COUNTY or COUNTY_FIPS_CD; another name fails), city, zip,
	 *        state, county
	 * @return rows with fieldName (and countyFipsCd for counties)
	 */
	public List<AddressFieldVO> getAddressDropDownInfo(AddressFieldVO addressFieldVO) throws DataAccessException {
		String fieldName = addressFieldVO.getFieldName();
		String city = nullToEmpty(addressFieldVO.getCity());
		String zip = nullToEmpty(addressFieldVO.getZip());
		if ("county".equals(fieldName) || "COUNTY".equals(fieldName)) {
			return jpql.list("""
					select distinct c.county as FIELDNAME, c.countyFipsCd as COUNTYFIPSCD from NyehmsCountyReference c
					where upper(c.city) like ?1 escape '!' and c.stateCd = 'NY' and c.zipCd like ?2 escape '!' order by c.county""",
					Rows.bean(AddressFieldVO.class), upperPrefix(city), prefix(zip));
		}
		String field = addressField(fieldName);
		String county = addressFieldVO.getCounty();
		if ("Out of State".equals(county) && "".equals(addressFieldVO.getState())) {
			return jpql.list("select distinct upper(c." + field + ") as FIELDNAME from NyehmsCountyReference c"
					+ " where upper(c.city) like ?1 escape '!' and c.stateCd <> 'NY' and c.zipCd like ?2 escape '!' order by FIELDNAME",
					Rows.bean(AddressFieldVO.class), upperPrefix(city), prefix(zip));
		}
		List<Object> parameters = new ArrayList<>(List.of(upperPrefix(city), upperPrefix(addressFieldVO.getState()), prefix(zip)));
		String jpqlText = "select distinct upper(c." + field + ") as FIELDNAME from NyehmsCountyReference c"
				+ " where upper(c.city) like ?1 escape '!' and upper(c.stateCd) like ?2 escape '!' and c.zipCd like ?3 escape '!'";
		if (!"Out of State".equals(county) && !"Unknown".equals(county)) {
			jpqlText += " and upper(c.county) like ?4 escape '!'";
			parameters.add(upperPrefix(county));
		}
		return jpql.list(jpqlText + " order by FIELDNAME", Rows.bean(AddressFieldVO.class), parameters.toArray());
	}

	/** The number of ZIP reference rows matching a city, state and ZIP (and county FIPS code, unless 998 or 999). @param addressFieldVO city, state, zip, county */
	public int getAddressFieldsCombination(AddressFieldVO addressFieldVO) throws DataAccessException {
		List<Object> parameters = new ArrayList<>(java.util.Arrays.asList(addressFieldVO.getCity(), addressFieldVO.getState(), addressFieldVO.getZip()));
		String jpqlText = "select count(c.zipCd) from NyehmsCountyReference c where upper(c.city) = upper(?1) and upper(c.stateCd) = upper(?2) and c.zipCd = upper(?3)";
		String county = addressFieldVO.getCounty();
		if (!"998".equals(county) && !"999".equals(county)) {
			jpqlText += " and c.countyFipsCd = upper(?4)";
			parameters.add(county);
		}
		return jpql.one(jpqlText, Rows.scalar(Integer.class), parameters.toArray());
	}

	/** The state codes of the ZIP reference, sorted: code, value (both the code). */
	public List<Map<String, String>> getStateCodes() throws DataAccessException {
		return jpql.list("select distinct c.stateCd from NyehmsCountyReference c order by c.stateCd", row -> {
			Map<String, String> state = new HashMap<>();
			if (row.get(0) != null) {
				state.put("code", Values.text(row.get(0)));
				state.put("value", Values.text(row.get(0)));
			}
			return state.isEmpty() ? null : state;
		});
	}

	/** The countries by description: CODE, VALUE. */
	public List<Map<String, String>> getCountryCodes() {
		return jpql.list("select distinct c.countryCode, c.countryDesc from CodedCountry c order by c.countryDesc", CODE_VALUE);
	}

	/** The counties in table order with 998 and 999 last: code (the FIPS code), value (the name). */
	public List<Map<String, String>> getCountyCodes() throws DataAccessException {
		return statements.list(CommonSql.COUNTY_CODES, Rows.textMap("COUNTYFIPSCD", "code", "COUNTYNAME", "value"));
	}

	/** The county codes with their names: COUNTY_CD, COUNTY_NAME. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getCountyCodeReferenceData() {
		return (List) jpql.list("select c.countyCd as COUNTY_CD, c.countyName as COUNTY_NAME from CodedCounty c where c.countyCd is not null", Rows.map());
	}

	/** The main row of a ZIP code (any one): COUNTY_FIPS_CD ('999' if none), STATE_CD, CITY; null if none. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, String> getCountyNameByZip(String _zip) {
		return (Map) jpql.one("""
				select coalesce(c.countyFipsCd, '999') as COUNTY_FIPS_CD, c.stateCd as STATE_CD, c.city as CITY from NyehmsCountyReference c
				where c.zipCd = ?1 and c.numCity = 1 fetch first 1 rows only""", Rows.map(), _zip);
	}

	/** The main rows of all ZIP codes: ZIP_CD, CITY, COUNTY, COUNTY_FIPS_CD, STATE_CD. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getZipCodeReferenceData() {
		return (List) jpql.list("select c.zipCd as ZIP_CD, c.city as CITY, c.county as COUNTY, c.countyFipsCd as COUNTY_FIPS_CD, c.stateCd as STATE_CD "
				+ "from NyehmsCountyReference c where c.numCity = 1", Rows.map());
	}

	/** The partner services message types in force, by sort order: PS_MSG_TYPE, PS_MSG_TYPE_DESCRIPTION, DOCUMENT_TYPE_CODE. */
	public List<JSONObject> getPsMessageTypes() {
		return jpql.list("""
				select t.psMsgType as PS_MSG_TYPE, t.psMsgTypeDescription as PS_MSG_TYPE_DESCRIPTION, t.documentTypeCode as DOCUMENT_TYPE_CODE
				from CodedPsMsgType t where t.endDate is null order by t.sortOrder asc""", Rows.json());
	}

	/** The activity types that need an ACRF (category ACLS, in force): activityCd, activityDesc. */
	public List<Map<String, String>> getCRFReqActivityList() throws DataAccessException {
		return jpql.list("""
				select a.activityTypeCode as ACTIVITY_TYPE_CODE, a.activityTypeDescription as ACTIVITY_TYPE_DESCRIPTION from CodedActivityType a
				where a.activityCategoryCode = 'ACLS' and a.endDate is null and a.needAcrf = 'M'""",
				Rows.textMap("ACTIVITY_TYPE_CODE", "activityCd", "ACTIVITY_TYPE_DESCRIPTION", "activityDesc"));
	}

	/** The HCS web service settings of a stage and function (an endpoint ending with the function), or null. @param map userStage, function */
	public WebServiceConstants getWebServiceConstants(Map<Object, Object> map) {
		return jpql.one("""
				select w.appName as APP_NAME, w.appStage as APP_STAGE, w.urlAccessToken as URL_ACCESS_TOKEN, w.clientSecret as CLIENT_SECRET, w.endpoints as ENDPOINTS
				from CodedWebServiceConstants w where w.appName = 'nyehms' and w.appStage = ?1 and w.endpoints like ?2 escape '!'""",
				Rows.resultMap(WebServiceConstants.class, "URL_ACCESS_TOKEN", "baseUrl", "CLIENT_SECRET", "clientSecret", "ENDPOINTS", "hcsEndPointUri"),
				BoundValues.text(map.get("userStage")), "%" + nullToEmpty(BoundValues.text(map.get("function"))));
	}

	// ---- state numbers and their links ------------------------------------------------------------------------------

	/** SOUNDEX of a name. */
	public String getSoundexValue(String name) {
		return statements.one(CommonSql.SOUNDEX_VALUE, Rows.scalar(String.class), name);
	}

	/** PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO: the current state number of a (possibly merged) one. */
	public String getCurrentActiveStateNo(String _stateno) {
		return statements.one(CommonSql.CURRENT_ACTIVE_STATENO, Rows.scalar(String.class), _stateno);
	}

	/** 1 if NYEHMS_EHARS_STATENO_MAP has any row, else 0. */
	public int checkNYSEharsDataExists() {
		return jpql.list("select 1 from NyehmsEharsStatenoMap m fetch first 1 rows only", row -> row).size();
	}

	/** 1 if EHARS_PERSON_NYC has any row, else 0. */
	public int checkNYCEharsDataExists() {
		return jpql.list("select 1 from EharsPersonNyc e fetch first 1 rows only", row -> row).size();
	}

	/** The eHARS state numbers mapped to a state number, comma-separated (formerly LISTAGG); null if none. */
	public String getEharsStateno(String stateno) {
		return joined(jpql.list("select m.eharsStateno from NyehmsEharsStatenoMap m where m.nyehmsStateno = ?1", Rows.scalar(String.class), stateno));
	}

	/** The NYC eHARS state numbers and city numbers of a state number, comma-separated (formerly LISTAGG): EHARS_STATENO, CITYNO; null if neither. */
	public Map<String, String> getNYCStatenoDetails(String stateno1) {
		List<Row> rows = jpql.list("select e.eharsStateno, e.cityno from EharsPersonNyc e where e.nyehmsStateno = ?1", row -> row, stateno1);
		List<String> eharsStatenos = new ArrayList<>();
		List<String> citynos = new ArrayList<>();
		for (Row row : rows) {
			eharsStatenos.add(Values.text(row.get(0)));
			citynos.add(Values.text(row.get(1)));
		}
		Map<String, String> details = new HashMap<>();
		if (joined(eharsStatenos) != null) {
			details.put("EHARS_STATENO", joined(eharsStatenos));
		}
		if (joined(citynos) != null) {
			details.put("CITYNO", joined(citynos));
		}
		return details.isEmpty() ? null : details;
	}

	// ---- notification lists ---------------------------------------------------------------------------------------------

	/** The current, unlocked workers with a login id, by full name: USER_ID, FULL_NAME, WORKER_ID. */
	public List<JSONObject> getReciepientDetails() throws DataAccessException {
		return jpql.list("""
				select u.legacyUid as USER_ID, u.fullName as FULL_NAME, u.workerId as WORKER_ID from NyehmsUsersVw u
				where (u.endDate is null or u.endDate > ?1) and u.legacyUid is not null and u.isLocked <> 'Y' order by u.fullName asc""",
				Rows.json(), now());
	}

	/**
	 * The e-mail addresses of the current workers per value of a notification column (formerly LISTAGG): NOTIFICATION_TYPE
	 * (the column's value, 'T' or 'C'), EMAIL_ADDRS (comma-separated; absent when no worker has an address).
	 *
	 * @param inputParameterMap COLUMN_NAME: a notification column of NYEHMS_USERS ({@link SearchManageProfileDAO#NOTIFICATION_COLUMNS}; another name fails)
	 */
	public List<Map<String, String>> getNotificationList(Map<String, String> inputParameterMap) {
		String column = inputParameterMap.get("COLUMN_NAME");
		String attribute = column == null ? null : SearchManageProfileDAO.NOTIFICATION_COLUMNS.get(column.trim().toUpperCase(Locale.ENGLISH));
		if (attribute == null) {
			throw new InvalidDataAccessApiUsageException("Not a notification column: " + column);
		}
		List<Row> rows = jpql.list("select u.emailAddr, u." + attribute + " from NyehmsUsers u where u." + attribute
				+ " is not null and (u.endDate is null or u.endDate > ?1)", row -> row, now());
		Map<String, List<String>> groups = new LinkedHashMap<>();
		for (Row row : rows) {
			groups.computeIfAbsent(Values.text(row.get(1)), type -> new ArrayList<>()).add(Values.text(row.get(0)));
		}
		List<Map<String, String>> list = new ArrayList<>();
		groups.forEach((type, emails) -> {
			Map<String, String> group = new HashMap<>();
			group.put("NOTIFICATION_TYPE", type);
			if (joined(emails) != null) {
				group.put("EMAIL_ADDRS", String.join(",", emails.stream().filter(e -> e != null).toList()));
			}
			list.add(group);
		});
		return list;
	}

	/** The file types whose reminder is due today (CODED_FILE_FREQUENCY_REMINDER against the FILE_NOTIFICATION_DAYS parameter). */
	public List<String> getDocumentListForReminder() {
		return statements.list(CommonSql.DOCUMENT_LIST_FOR_REMINDER, Rows.scalar(String.class));
	}

	/** The application users whose account expires within 15 days for inactivity: EMAIL_ADDR, FIRST_NAME, USERNAME, DAYSSINCELOGGEDIN, WEEKNAME, DAY, MONTH. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> nyehmsInactiveUserDetailsList() {
		return (List) statements.list(CommonSql.INACTIVE_USER_DETAILS, Rows.map());
	}

	/** The users whose security training expires within 30 days: EMAIL_ADDR, FIRST_NAME, USERNAME, LAST_CONF_SECURITY_TRNG_DATE, DAYSTORENEW, WEEKNAME, DAY, MONTH, YEAR. */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> nyehmsTrainingUsersExpireList() {
		return (List) statements.list(CommonSql.TRAINING_USERS_EXPIRE, Rows.map());
	}

	/** get_crypto_card_image: the crypto card image name. */
	public String getCryptoImageName() {
		return statements.one(CommonSql.CRYPTO_IMAGE_NAME, Rows.scalar(String.class));
	}

	// ---- helpers ------------------------------------------------------------------------------------------------------

	/** The application server's clock (formerly SYSDATE). */
	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/** A null as '' (bound into {@code x || '%'}, where Oracle treated a NULL as ''). */
	private static String nullToEmpty(String value) {
		return value == null ? "" : value;
	}

	/** The former {@code x || '%'} LIKE pattern, built here (a null matched everything, as NULL || '%'). */
	private static String prefix(String value) {
		return nullToEmpty(value) + "%";
	}

	/**
	 * The former {@code UPPER(x) || '%'} LIKE pattern, built here (the upper-casing in Java instead of the database). The
	 * LIKEs name an escape character ({@code '!'}, which no value contains): without one Hibernate renders
	 * {@code escape ''} on H2, which is NULL in Oracle mode and matches nothing (the tests' database).
	 */
	private static String upperPrefix(String value) {
		return nullToEmpty(value).toUpperCase(Locale.ENGLISH) + "%";
	}

	/** The entity attribute of an allowed address field (ignoring case); another name fails. */
	private static String addressField(String fieldName) {
		String attribute = fieldName == null ? null : ADDRESS_FIELDS.get(fieldName.trim().toUpperCase(Locale.ENGLISH));
		if (attribute == null) {
			throw new InvalidDataAccessApiUsageException("Not an address field: " + fieldName);
		}
		return attribute;
	}

	/** The values joined with ', ' as LISTAGG joined them: nulls left out, null when nothing is left. */
	private static String joined(List<String> values) {
		List<String> present = values.stream().filter(value -> value != null).toList();
		return present.isEmpty() ? null : String.join(", ", present);
	}
}
