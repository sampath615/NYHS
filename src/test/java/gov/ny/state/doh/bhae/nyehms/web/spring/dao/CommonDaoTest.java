package gov.ny.state.doh.bhae.nyehms.web.spring.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.common.AddressFieldVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.webservice.oauth.WebServiceConstants;

/**
 * The common DAO: the JPQL statements, the Java replacements of the LISTAGGs, the county list (native) and the
 * function call against an in-memory H2 database in Oracle mode (db/common.sql), next to their original SQL where H2
 * can run it; the procedure and the Oracle-only native statements checked without a database.
 */
@SpringJUnitConfig(CommonDaoTest.TestConfig.class)
public class CommonDaoTest {                                                              // public: H2 calls the function stand-in reflectively

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, CommonDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("commondao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/common.sql").build();
		}
	}

	/** The stand-in for pkg_standardization.fn_improve_std_address (db/common.sql). */
	public static String improveAddress(String address) {
		return address == null ? null : address.trim().toUpperCase() + " (STD)";
	}

	@Autowired CommonDAO dao;
	@Autowired NativeStatements statements;

	// ---- the login ------------------------------------------------------------------------------------------------------

	@Test
	void userObjectPermissionsTrainingDateAndLoginTimeAsBefore() {
		UserObject user = dao.getUserObject("JDOE");                                     // ignoring case, as before
		assertThat(user.getUserFirstName()).isEqualTo("Jane");
		assertThat(user.getUserLastName()).isEqualTo("Doe");
		assertThat(user.getUserName()).isEqualTo("Jane Doe");
		assertThat(user.getWorkerId()).isEqualTo(1);
		assertThat(user.getUserId()).isEqualTo("jdoe");
		assertThat(user.getUserFullName()).isEqualTo("Doe, Jane");
		assertThat(user.getLastLogin()).isEqualTo("2024-01-02 03:04:05");                 // the date as the driver's text, as before
		assertThat(user.getEmailAddress()).isEqualTo("jdoe@example.org");
		assertThat(dao.getUserObject("old")).isNull();                                   // ended
		assertThat(dao.getUserObject("nobody")).isNull();

		Map<Object, Object> permission = new HashMap<>();
		permission.put("workeId", 1);
		permission.put("moduleName", "PERSON");
		permission.put("permissionId", 10);
		assertThat(dao.getPermission(permission)).isEqualTo(2);
		permission.put("permissionId", 30);
		assertThat(dao.getPermission(permission)).isZero();

		Map<Object, Object> worker = new HashMap<>();
		worker.put("workerId", 1);
		worker.put("workerRegionId", "");
		assertThat(dao.getUserPermissions(worker)).containsExactlyInAnyOrder(Map.of("permissionId", "10", "permission", "VIEW"),
				Map.of("permissionId", "20", "permission", "EDIT"));
		worker.put("workerRegionId", 1);
		assertThat(dao.getUserPermissions(worker)).containsExactly(Map.of("permissionId", "10", "permission", "VIEW"));

		assertThat(dao.getCNSTrainingDate("jdoe")).isEqualTo(new Date(Timestamp.valueOf("2023-06-07 00:00:00").getTime())).isExactlyInstanceOf(Date.class);
		assertThat(dao.getCNSTrainingDate("jroe")).isNull();
		assertThat(dao.getCNSTrainingDate("old")).isNull();

		assertThat(dao.setLoginTimeStamp(2)).isEqualTo(1);
		assertThat(statements.one("select LAST_LOGIN_DATE from NYEHMS_USERS where WORKER_ID = 2", Rows.scalar(Timestamp.class)))
				.isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);
		assertThat(dao.setLoginTimeStamp(99)).isZero();

		Map<String, Object> agreed = new HashMap<>();
		agreed.put("WORKER_ID", "1");
		assertThat(dao.insertNyehmsAgreedTime(agreed)).isEqualTo(1);
		Map<String, Object> audit = statements.one("select WORKER_ID, AGREED_DATE from USER_ACCESS_AUDIT_LOG", Rows.map());
		assertThat(audit.get("WORKER_ID")).hasToString("1");
		assertThat((Timestamp) audit.get("AGREED_DATE")).isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);
	}

	@Test
	void errorLogIsWrittenWithASequenceKeyPutBackIntoTheMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put("p_log_date", new Timestamp(Timestamp.valueOf("2024-03-04 05:06:07").getTime()));
		map.put("p_user_id", "jdoe");
		map.put("p_session_id", null);
		map.put("p_log_message", "boom");
		map.put("p_log_error", "java.lang.IllegalStateException: boom");
		map.put("p_log_stacktrace", "x".repeat(10_000));
		dao.writeLogToDB(map);
		assertThat(map.get("appLogId")).isInstanceOf(Long.class);
		Map<String, Object> row = statements.one("select * from NYEHMS_APP_ERROR_LOG", Rows.map());
		assertThat(new java.math.BigDecimal(row.get("APP_LOG_ID").toString())).isEqualByComparingTo(new java.math.BigDecimal(map.get("appLogId").toString()));
		assertThat(row.get("APP_LOG_DATE")).isEqualTo(Timestamp.valueOf("2024-03-04 05:06:07"));
		assertThat(row.get("APP_USER_ID")).isEqualTo("jdoe");
		assertThat(row).doesNotContainKey("APP_SESSION_ID");
		assertThat(row.get("APP_LOG_MSG")).isEqualTo("boom");
		assertThat(row.get("APP_LOG_STACKTRACE")).isEqualTo("x".repeat(10_000));
	}

	@Test
	void functionAndProcedureCalls() {
		Map<Object, Object> map = new HashMap<>();
		map.put("customAddress", " 1 main st ");
		assertThat(dao.getImprovedStandardAddress(map)).isEqualTo("1 MAIN ST (STD)");
		assertThat(map).containsEntry("standardAddress", "1 MAIN ST (STD)");

		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.call(any(), any(CallParam[].class))).thenReturn(new ArrayList<>());
		new CommonDAO(mocked, null).setClientSession();
		ArgumentCaptor<String> procedure = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<CallParam[]> parameters = ArgumentCaptor.forClass(CallParam[].class);
		verify(mocked).call(procedure.capture(), parameters.capture());
		assertThat(procedure.getValue()).isEqualTo("Pkg_trk_common.pr_set_client_session");
		assertThat(parameters.getValue()).isEmpty();
	}

	// ---- addresses and code lists ----------------------------------------------------------------------------------------

	private static AddressFieldVO address(String fieldName, String city, String zip, String state, String county) {
		AddressFieldVO vo = new AddressFieldVO();
		vo.setFieldName(fieldName);
		vo.setCity(city);
		vo.setZip(zip);
		vo.setState(state);
		vo.setCounty(county);
		return vo;
	}

	private static List<String> fieldNames(List<AddressFieldVO> rows) {
		List<String> names = new ArrayList<>();
		for (AddressFieldVO row : rows) {
			names.add(row.getFieldName() + (row.getCountyFipsCd() == null || row.getCountyFipsCd().isEmpty() ? "" : "/" + row.getCountyFipsCd()));   // "" when not selected: the VO's default, as before
		}
		return names;
	}

	@Test
	void addressDropDownsMatchTheOriginalBranches() {
		// counties of a New York city / ZIP, with their FIPS codes
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("COUNTY", "alb", "12", null, null)))).containsExactly("Albany/001", "Rensselaer/083");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("county", null, null, null, null)))).containsExactly("Albany/001", "New York/061", "Rensselaer/083");
		// out of state, no state given: every state but NY
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("CITY", "a", "", "", "Out of State")))).containsExactly("ATLANTA", "AVENEL");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("STATE_CD", "", "", "", "Out of State")))).containsExactly("GA", "NJ");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("STATE_CD", "", "0", "", "Out of State")))).containsExactly("NJ");
		// a state given: the county filter unless 'Out of State' / 'Unknown'
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("CITY", "", "", "nj", "Out of State")))).containsExactly("AVENEL", "BAYONNE");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("ZIP_CD", "", "", "nj", "Unknown")))).containsExactly("07001", "07002");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("CITY", "", "", "ny", "alb")))).containsExactly("ALBANY", "MENANDS");
		assertThat(fieldNames(dao.getAddressDropDownInfo(address("CITY", "", "", null, null)))).hasSize(6);   // a null state / county matched everything, as NULL || '%'
		assertThatThrownBy(() -> dao.getAddressDropDownInfo(address("CITY; drop table X", "", "", "", "")))
				.isInstanceOf(InvalidDataAccessApiUsageException.class);

		assertThat(dao.getAddressFieldsCombination(address(null, "albany", "12201", "ny", "001"))).isEqualTo(1);
		assertThat(dao.getAddressFieldsCombination(address(null, "albany", "12201", "ny", "999"))).isEqualTo(1);   // 998 / 999: no county filter
		assertThat(dao.getAddressFieldsCombination(address(null, "albany", "12201", "ny", "083"))).isZero();
		assertThat(dao.getAddressFieldsCombination(address(null, "albany", "12201", "ny", null))).isZero();      // = UPPER(NULL): no row, as before
	}

	@Test
	void codeListsMatchTheOriginalSelects() {
		assertThat(dao.getStateCodes()).containsExactly(Map.of("code", "GA", "value", "GA"), Map.of("code", "NJ", "value", "NJ"), Map.of("code", "NY", "value", "NY"));
		List<Map<String, String>> countries = dao.getCountryCodes();
		assertThat(countries).containsExactlyInAnyOrder(Map.of("CODE", "XX"), Map.of("CODE", "CA", "VALUE", "Canada"), Map.of("CODE", "US", "VALUE", "United States"));
		assertThat(countries.indexOf(Map.of("CODE", "CA", "VALUE", "Canada"))).isLessThan(countries.indexOf(Map.of("CODE", "US", "VALUE", "United States")));

		List<Map<String, String>> counties = dao.getCountyCodes();
		assertThat(counties).isEqualTo(statements.list(CommonSql.COUNTY_CODES, Rows.textMap("COUNTYFIPSCD", "code", "COUNTYNAME", "value")));
		assertThat(counties).extracting(county -> county.get("code")).containsExactly("1", "83", "998", "999");   // table order, 998 and 999 last
		assertThat(counties.get(0)).containsEntry("value", "Albany");

		assertThat(dao.getCountyCodeReferenceData()).containsExactlyInAnyOrder(Map.of("COUNTY_CD", "ALB", "COUNTY_NAME", "Albany"),
				Map.of("COUNTY_CD", "UNK", "COUNTY_NAME", "Unknown"), Map.of("COUNTY_CD", "REN", "COUNTY_NAME", "Rensselaer"));
		assertThat(dao.getCountyNameByZip("12201")).isEqualTo(Map.of("COUNTY_FIPS_CD", "001", "STATE_CD", "NY", "CITY", "Albany"));
		assertThat(dao.getCountyNameByZip("07001")).isEqualTo(Map.of("COUNTY_FIPS_CD", "999", "STATE_CD", "NJ", "CITY", "Avenel"));
		assertThat(dao.getCountyNameByZip("00000")).isNull();
		assertThat(dao.getZipCodeReferenceData()).hasSize(6).contains(Map.of("ZIP_CD", "12203", "CITY", "Albany", "COUNTY", "Rensselaer", "COUNTY_FIPS_CD", "083", "STATE_CD", "NY"));

		List<JSONObject> types = dao.getPsMessageTypes();
		assertThat(types).hasSize(2);
		assertThat(types.get(0)).containsEntry("PS_MSG_TYPE", "VL").doesNotContainKey("DOCUMENT_TYPE_CODE");
		assertThat(types.get(1)).containsEntry("PS_MSG_TYPE", "NEW_DX").containsEntry("DOCUMENT_TYPE_CODE", "HIV");

		assertThat(dao.getCRFReqActivityList()).containsExactly(Map.of("activityCd", "A1", "activityDesc", "Interview"));

		Map<Object, Object> ws = new HashMap<>();
		ws.put("userStage", "test");
		ws.put("function", "permissions");
		WebServiceConstants constants = dao.getWebServiceConstants(ws);
		assertThat(constants.getBaseUrl()).isEqualTo("https://token");
		assertThat(constants.getClientSecret()).isEqualTo("secret2");
		assertThat(constants.getHcsEndPointUri()).isEqualTo("https://hcs/permissions");
		ws.put("userStage", "prod");
		assertThat(dao.getWebServiceConstants(ws)).isNull();
	}

	// ---- state numbers, notification lists -------------------------------------------------------------------------------

	@Test
	void eharsLinksAreJoinedAsListaggDid() {
		assertThat(dao.getEharsStateno("S1")).isEqualTo("E1, E2");
		assertThat(dao.getEharsStateno("S2")).isNull();                                   // only a null: LISTAGG gave NULL
		assertThat(dao.getEharsStateno("S9")).isNull();
		assertThat(dao.getNYCStatenoDetails("S1")).isEqualTo(Map.of("EHARS_STATENO", "N1", "CITYNO", "C1, C2"));
		assertThat(dao.getNYCStatenoDetails("S9")).isNull();
		assertThat(dao.checkNYSEharsDataExists()).isEqualTo(1);
		assertThat(dao.checkNYCEharsDataExists()).isEqualTo(1);
		assertThat(dao.getSoundexValue("Robert")).isEqualTo("R163");
	}

	@Test
	void notificationListsAndRecipientsAsBefore() {
		Map<String, String> input = new HashMap<>();
		input.put("COLUMN_NAME", "AHI");
		List<Map<String, String>> ahi = dao.getNotificationList(input);
		assertThat(ahi).containsExactlyInAnyOrder(Map.of("NOTIFICATION_TYPE", "T", "EMAIL_ADDRS", "jdoe@example.org"),
				Map.of("NOTIFICATION_TYPE", "C", "EMAIL_ADDRS", "jroe@example.org"));                  // the ended worker left out; no address: no EMAIL_ADDRS
		input.put("COLUMN_NAME", "NIGHTLY_JOB");
		assertThat(dao.getNotificationList(input)).containsExactlyInAnyOrder(Map.of("NOTIFICATION_TYPE", "T", "EMAIL_ADDRS", "jdoe@example.org"),
				Map.of("NOTIFICATION_TYPE", "C"));
		input.put("COLUMN_NAME", "EMAIL_ADDR");
		assertThatThrownBy(() -> dao.getNotificationList(input)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		List<JSONObject> recipients = dao.getReciepientDetails();
		assertThat(recipients).hasSize(1);                                                // locked, ended and no-login workers left out
		assertThat(recipients.get(0)).containsEntry("USER_ID", "jdoe").containsEntry("FULL_NAME", "Doe, Jane").containsEntry("WORKER_ID", 1L);
	}

	// ---- the Oracle-only native statements, without a database -------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	void oracleOnlyStatementsKeepTheirSqlAndBindTheirValue() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		CommonDAO dao = new CommonDAO(mocked, null);
		assertThat(dao.getCurrentActiveStateNo("S1")).isNull();
		assertThat(dao.getCryptoImageName()).isNull();
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(2)).one(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(0)).contains("PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (?1) CURRENT_STATENO from DUAL");
		assertThat(parameters.getAllValues().get(0)).containsExactly("S1");
		assertThat(sql.getAllValues().get(1)).contains("select get_crypto_card_image from dual");
		assertThat(parameters.getAllValues().get(1)).isEmpty();

		assertThat(dao.getDocumentListForReminder()).isEmpty();
		assertThat(dao.nyehmsInactiveUserDetailsList()).isEmpty();
		assertThat(dao.nyehmsTrainingUsersExpireList()).isEmpty();
		ArgumentCaptor<String> listSql = ArgumentCaptor.forClass(String.class);
		verify(mocked, org.mockito.Mockito.times(3)).list(listSql.capture(), any(RowMapper.class), any(Object[].class));
		assertThat(listSql.getAllValues().get(0)).contains("FROM coded_file_frequency_reminder").contains("paramter_name = 'FILE_NOTIFICATION_DAYS'");
		assertThat(listSql.getAllValues().get(1)).contains("REGEXP_LIKE (user_type, 'APP')").contains("ROUND (SYSDATE - last_login_date) > 90 - 15");
		assertThat(listSql.getAllValues().get(2)).contains("REGEXP_LIKE (user_type, 'APP|BACKEND')").contains("ROUND(ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12)-sysdate) < 30");
	}
}
