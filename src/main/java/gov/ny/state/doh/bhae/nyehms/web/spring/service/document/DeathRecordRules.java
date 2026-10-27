package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

/**
 * The rules the death and NYC death imports share when they build the standardized record map (column name to
 * value) from a received record (RAW_DEATH_DOC / RAW_NYC_DEATH_DOC), and the cause-of-death text of their
 * document screens.
 */
final class DeathRecordRules {

	/**
	 * The cause columns after ICD10 and UNDERLYING, read until the first empty one. <b>As before, CAUSE13 is
	 * missing and CAUSE14 is listed twice</b> (so CAUSE13 is never stored and the causes after it are read only if
	 * CAUSE14 is filled in); kept to leave the stored data unchanged.
	 */
	static final List<String> CAUSE_COLUMNS = List.of(
			"CAUSE1", "CAUSE2", "CAUSE3", "CAUSE4", "CAUSE5", "CAUSE6", "CAUSE7", "CAUSE8",
			"CAUSE9", "CAUSE10", "CAUSE11", "CAUSE12", "CAUSE14", "CAUSE14", "CAUSE15", "CAUSE16", "CAUSE17",
			"CAUSE18", "CAUSE19", "CAUSE20");

	/** ICD-10 code prefixes of an HIV ("H") or AIDS-defining ("A") cause of death. */
	private static final Map<String, String> CAUSE_PREFIXES = Map.ofEntries(
			Map.entry("B20", "H"), Map.entry("B21", "H"), Map.entry("B22", "H"), Map.entry("B23", "H"),
			Map.entry("B24", "H"), Map.entry("R75", "H"), Map.entry("Z21", "H"),
			Map.entry("A15", "A"), Map.entry("A16", "A"), Map.entry("A17", "A"), Map.entry("A18", "A"),
			Map.entry("A19", "A"), Map.entry("A31", "A"), Map.entry("B58", "A"), Map.entry("B59", "A"),
			Map.entry("C46", "A"));

	private static final String HIV_CAUSE = "H";
	private static final String AIDS_CAUSE = "A";
	private static final String YES = "Y";
	private static final String NO = "N";

	/** The PFI given when it is not known. */
	static final String UNKNOWN_PFI = "9999";

	private DeathRecordRules() {
	}

	/** A text field of a record. */
	static String text(Map<Object, Object> record, String field) {
		return (String) record.get(field);
	}

	/**
	 * Copies ICD10, UNDERLYING and the cause columns (as "A12.3" when longer than three characters) and sets
	 * H_CAUSE ("Y" if a cause is an HIV code) and OI_CAUSE ("Y" if a cause is an AIDS-defining code).
	 */
	static void putCauses(Map<Object, Object> raw, Map<Object, Object> std) {
		boolean hivCause = false;
		boolean aidsCause = false;
		for (String column : causeColumnsToRead(raw)) {
			String kind = putCause(column, text(raw, column), std);
			hivCause |= HIV_CAUSE.equals(kind);
			aidsCause |= AIDS_CAUSE.equals(kind);
		}
		std.put("H_CAUSE", hivCause ? YES : NO);
		std.put("OI_CAUSE", aidsCause ? YES : NO);
	}

	/** ICD10 and UNDERLYING when filled in, then the cause columns up to the first empty one. */
	private static List<String> causeColumnsToRead(Map<Object, Object> raw) {
		List<String> columns = new java.util.ArrayList<>();
		for (String column : List.of("ICD10", "UNDERLYING")) {
			if (CommonUtil.isNotNullAndEmpty(text(raw, column))) {
				columns.add(column);
			}
		}
		for (String column : CAUSE_COLUMNS) {
			if (CommonUtil.isNullOrEmpty(text(raw, column))) {
				break;
			}
			columns.add(column);
		}
		return columns;
	}

	/** Stores a cause code and returns its kind: "H", "A" or null. */
	private static String putCause(String column, String code, Map<Object, Object> std) {
		if (code.length() > 3) {
			String prefix = code.substring(0, 3);
			std.put(column, prefix + "." + code.substring(3));
			return CAUSE_PREFIXES.get(prefix);
		}
		std.put(column, code);
		return CAUSE_PREFIXES.get(code);
	}

	/**
	 * The resident street address before cleaning: street number, street, street type and " APT " apartment, the
	 * filled-in parts each followed by a space (RES_STREET_DIRECT is not used).
	 */
	static String residentAddress(Map<Object, Object> raw) {
		return withSpace(text(raw, "RES_STREET_NO")) + withSpace(text(raw, "RES_STREET")) + withSpace(text(raw, "RES_STREET_TYPE"))
				+ (CommonUtil.isNotNullAndEmpty(text(raw, "RES_APT_NO")) ? " APT " + text(raw, "RES_APT_NO") : "");
	}

	private static String withSpace(String part) {
		return CommonUtil.isNotNullAndEmpty(part) ? part + " " : "";
	}

	/**
	 * A zip code split into the 5-digit zip and the plus-4 part.
	 *
	 * @param zip the 5-digit zip, or null when the code is empty
	 * @param plus4 the digits after the first five, or null when there are none
	 */
	record ZipCode(String zip, String plus4) {

		/** The zip code of a raw value, formatted as {@link CommonUtil#formatZipAndPlus4Code} does. */
		static ZipCode of(String rawZip) {
			String formatted = CommonUtil.formatZipAndPlus4Code(rawZip);
			if (CommonUtil.isNullOrEmpty(formatted)) {
				return new ZipCode(null, null);
			}
			return formatted.length() > 5 ? new ZipCode(formatted.substring(0, 5), formatted.substring(5)) : new ZipCode(formatted, null);
		}
	}

	/**
	 * The PFI of a record: PFI, or PFI_NEW when PFI is empty or 9999; a 6-character PFI without its two leading
	 * zeros.
	 */
	static String pfi(Map<Object, Object> raw) {
		String pfi = text(raw, "PFI");
		if (CommonUtil.isNullOrEmpty(pfi) || UNKNOWN_PFI.equals(pfi)) {
			pfi = text(raw, "PFI_NEW");
		}
		return CommonUtil.isNotNullAndEmpty(pfi) && pfi.length() == 6 ? pfi.substring(2) : pfi;
	}

	/**
	 * The cause of death shown on the document screen: from H_CAUSE and OI_CAUSE, else "Meets Case Definition"
	 * when the matched person meets the CDC case definition.
	 *
	 * @param meetsCaseDefinition asked only when H_CAUSE and OI_CAUSE do not decide
	 * @return the text, or null when there is none
	 */
	static String causeOfDeath(String hCause, String oiCause, BooleanSupplier meetsCaseDefinition) {
		if (YES.equals(hCause) && YES.equals(oiCause)) {
			return "Opportunistic Infection (OI) Cause of Death";
		}
		if (YES.equals(hCause) && NO.equals(oiCause)) {
			return "HIV Cause of Death";
		}
		return meetsCaseDefinition.getAsBoolean() ? "Meets Case Definition" : null;
	}
}
