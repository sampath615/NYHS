package gov.ny.state.doh.bhae.nyehms.web.spring.dao;

/**
 * The SQL of the {@link CommonDAO} statements that stay native, taken unchanged from the former MyBatis mapper
 * ({@code #{...}} replaced by {@code ?1}); why each one stays native is said at its DAO method.
 */
final class CommonSql {

	private CommonSql() {
	}

	/** The SQL of {@code getCountyCodes}, as the former mapper had it. */
	static final String COUNTY_CODES = """
			select COUNTYNAME,COUNTYFIPSCD from
				(SELECT DECODE (COUNTY_FIPS_CODE,  998,100,  999, 101,  ROWNUM) AS rowNumber,
			       COUNTY_NAME COUNTYNAME, COUNTY_FIPS_CODE COUNTYFIPSCD  FROM CODED_COUNTY)
			  			order by rowNumber
			""";

	/** The SQL of {@code getSoundexValue}, as the former mapper had it (1 bound value). */
	static final String SOUNDEX_VALUE = """
			select soundex(?1) from dual
			""";

	/** The SQL of {@code getCurrentActiveStateNo}, as the former mapper had it (1 bound value). */
	static final String CURRENT_ACTIVE_STATENO = """
			SELECT  PKG_CONSOLIDATION.FN_GIVE_CURRENT_STATENO (?1) CURRENT_STATENO from DUAL
			""";

	/** The SQL of {@code getDocumentListForReminder}, as the former mapper had it. */
	static final String DOCUMENT_LIST_FOR_REMINDER = """
			SELECT file_type
				FROM coded_file_frequency_reminder
				WHERE Trunc(sysdate) + (select PARAMTER_VALUE from lab_scheduler_parameters where paramter_name = 'FILE_NOTIFICATION_DAYS') = To_Date(receive_months, 'MM')
			""";

	/** The SQL of {@code nyehmsInactiveUserDetailsList}, as the former mapper had it. */
	static final String INACTIVE_USER_DETAILS = """
			SELECT
			       EMAIL_ADDR,
			       initcap(first_name) first_name,
			       pkg_trk_common.fn_get_fullname (last_name,
			                                       first_name,
			                                       middle_name,
			                                       NULL) userName,
				   to_char(ROUND (SYSDATE - last_login_date)) daysSinceLoggedIn,
			       initcap(trim(TO_CHAR (SYSDATE + (90 - ROUND (SYSDATE - last_login_date)),
			                'DAY'))) weekname,
			       trim(TO_CHAR (SYSDATE + (90 - ROUND (SYSDATE - last_login_date)),
			                'DD')) day,
			        initcap(trim(TO_CHAR (SYSDATE + (90 - ROUND (SYSDATE - last_login_date)),
			                'MONTH'))) month
			  FROM nyehms_users
			 WHERE     REGEXP_LIKE (user_type, 'APP')
			       AND NVL (is_locked, 'N') = 'N'
			       AND end_date IS NULL
			       AND ROUND (SYSDATE - last_login_date) > 90 - 15
			""";

	/** The SQL of {@code nyehmsTrainingUsersExpireList}, as the former mapper had it. */
	static final String TRAINING_USERS_EXPIRE = """
			SELECT
			       EMAIL_ADDR,
			       initcap(first_name) first_name,
			       pkg_trk_common.fn_get_fullname (last_name,
			                                       first_name,
			                                       middle_name,
			                                       NULL) userName,
			                                       LAST_CONF_SECURITY_TRNG_DATE,
			                                       to_char(ROUND(ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12)-sysdate)) daysToRenew,
			       initcap(trim(TO_CHAR (ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12),
			                'DAY'))) weekname,
			       trim(TO_CHAR (ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12),
			                'DD')) day,
			        initcap(trim(TO_CHAR (ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12),
			                'MONTH'))) month,
			                initcap(trim(TO_CHAR (ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12),
			                'YYYY'))) year
			  FROM nyehms_users
			 WHERE     REGEXP_LIKE (user_type, 'APP|BACKEND')
			       AND end_date IS NULL
			       AND ROUND(ADD_MONTHS(LAST_CONF_SECURITY_TRNG_DATE, 12)-sysdate) < 30
			""";

	/** The SQL of {@code getCryptoImageName}, as the former mapper had it. */
	static final String CRYPTO_IMAGE_NAME = """
			select get_crypto_card_image from dual
			""";
}
