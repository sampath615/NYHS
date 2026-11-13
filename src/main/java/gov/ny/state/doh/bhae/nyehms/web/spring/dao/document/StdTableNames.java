package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.Locale;
import java.util.Set;

import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * The names the review DAOs may paste into their SQL (the former MyBatis {@code ${...}} substitutions): the
 * standardized document tables {@code LabCommonUtils.getStdTableName} returns and the file-name reference columns
 * of {@code LabCommonUtils.getStdReferenceColumnNames}. Anything else is refused.
 */
public final class StdTableNames {

	private StdTableNames() {
	}

	/** The standardized document tables (the values of {@code LabCommonUtils.getStdTableName}). */
	public static final Set<String> TABLES = Set.of("STD_HIV_ECLRS_DOC", "STD_STD_ECLRS_DOC", "STD_TB_DOC", "STD_SYPHILIS_DOC", "STD_DEATH_DOC",
			"STD_CASA_DOC", "STD_DOCCS_DOC", "STD_DIN_DOCCS_DOC", "STD_PRF_DOC", "STD_NYCPRF_DOC", "STD_ACRF_DOC", "STD_AIRS_DOC", "STD_ADAP_DOC",
			"STD_SSDM_DOC", "STD_NDI_DOC", "STD_NYCIJR_DOC", "STD_NYC_DEATH_DOC", "STD_MEDICAID_DOC", "STD_OLD_CASES_DOC", "STD_MED_OLD_DOC",
			"STD_EPRF_OOC_UPLOAD", "STD_CCD_DOC", "STD_HL7_DOC", "STD_EXP_DOC", "STD_NYC_EHARS_DOC", "STD_NYC_EHARS_LAB_DOC", "STD_NDI_CAUSE_DOC",
			"STD_INS_DEATH_DOC", "STD_STD_OLD_DOC", "STD_HIE_DEATH_DOC", "STD_AHI_DOC", "STD_CLUSTER_DOC", "STD_CLUSTER_PARTNERS",
			"STD_STD_CONFIRMED_DOC", "STD_PCRF_DOC", "STD_BLACK_BOX_DOC", "STD_ICCR_DOC", "STD_QUALITY_CARE_DOC", "TRACKING_USER_ENTERED_ALIAS");

	/** The raw document tables (the values of {@code LabCommonUtils.getRawTableName}). */
	public static final Set<String> RAW_TABLES = Set.of("RAW_HIV_ECLRS_DOC", "RAW_STD_ECLRS_DOC", "RAW_TB_DOC", "RAW_SYPHILIS_DOC", "RAW_DEATH_DOC",
			"RAW_CASA_DOC", "RAW_DOCCS_DOC", "RAW_DIN_DOCCS_DOC", "RAW_PRF_DOC", "RAW_NYCPRF_DOC", "RAW_ACRF_DOC", "RAW_PCRF_DOC", "RAW_AIRS_DOC",
			"RAW_ADAP_DOC", "RAW_SSDM_DOC", "RAW_NDI_DOC", "RAW_NYCIJR_DOC", "RAW_NYC_DEATH_DOC", "RAW_MEDICAID_DOC", "RAW_OLD_CASES_DOC",
			"RAW_MED_OLD_DOC", "RAW_EPRF_OOC_UPLOAD", "RAW_CCD_DOC", "RAW_HL7_DOC", "RAW_EXP_DOC", "RAW_NYC_EHARS_DOC", "RAW_NYC_EHARS_LAB_DOC",
			"RAW_NDI_CAUSE_DOC", "RAW_INS_DEATH_DOC", "RAW_STD_OLD_DOC", "RAW_HIE_DEATH_DOC", "RAW_CLUSTER_DOC", "RAW_STD_CONFIRMED_DOC",
			"RAW_BLACK_BOX_DOC", "RAW_ICCR_DOC", "RAW_QUALITY_CARE_DOC");

	/** The file-name columns of the standardized tables (the FILE_NAME entries of {@code LabCommonUtils.getStdReferenceColumnNames}). */
	public static final Set<String> FILE_NAME_COLUMNS = Set.of("FILENAME", "FILE_NAME", "MEDICAID_FILE", "ADAP_FILE", "DEATH_FILE", "TB_FILE",
			"DOCCS_FILE", "UPLOAD_FILENAME", "STD_FILE");

	/** The source-id columns of the standardized tables (the SOURCE_ID entries of {@code LabCommonUtils.getStdReferenceColumnNames}). */
	public static final Set<String> SOURCE_ID_COLUMNS = Set.of("LAB_SRC_ID", "PRF_SRC_ID", "AIRS_SRC_ID", "ADAP_SRC_ID", "DEATH_SRC_ID",
			"MEDICAID_SRC_ID", "OLDCASE_SRC_ID", "SSDM_SRC_ID", "SERIALNO");

	/** A table name as the caller gave it, if it is one of {@link #TABLES} (ignoring case); otherwise the call fails. */
	public static String table(Object name) {
		return allowed(name, TABLES, "standardized document table");
	}

	/** A table name as the caller gave it, if it is one of {@link #RAW_TABLES} (ignoring case); otherwise the call fails. */
	public static String rawTable(Object name) {
		return allowed(name, RAW_TABLES, "raw document table");
	}

	/** A table name as the caller gave it, if it is one of {@link #TABLES} or {@link #RAW_TABLES} (ignoring case); otherwise the call fails. */
	public static String rawOrStdTable(Object name) {
		if (name instanceof String text && RAW_TABLES.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text.trim();
		}
		return allowed(name, TABLES, "raw or standardized document table");
	}

	/** A column name as the caller gave it, if it is one of {@link #SOURCE_ID_COLUMNS} (ignoring case); otherwise the call fails. */
	public static String sourceIdColumn(Object name) {
		return allowed(name, SOURCE_ID_COLUMNS, "source-id column");
	}

	/**
	 * A column name as the caller gave it, if it is shaped like one (letters, digits and underscores only; nothing that
	 * could end or extend the SQL); otherwise the call fails. For the statements whose column is chosen by callers the
	 * code base does not enumerate.
	 */
	public static String identifier(Object name) {
		if (name instanceof String text && text.trim().matches("[A-Za-z_][A-Za-z0-9_]*")) {
			return text.trim();
		}
		throw new InvalidDataAccessApiUsageException("Not a column name: " + name);
	}

	/** A column name as the caller gave it, if it is one of {@link #FILE_NAME_COLUMNS} (ignoring case); otherwise the call fails. */
	public static String fileNameColumn(Object name) {
		return allowed(name, FILE_NAME_COLUMNS, "file-name column");
	}

	private static String allowed(Object name, Set<String> names, String what) {
		if (name instanceof String text && names.contains(text.trim().toUpperCase(Locale.ENGLISH))) {
			return text.trim();
		}
		throw new InvalidDataAccessApiUsageException("Not a " + what + ": " + name);
	}
}
