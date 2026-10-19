package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * The value conversions the former SQL inserts of the imports left to Oracle (UPPER, TRIM, the
 * function FN_TO_DATE, implicit number/text/date conversions), done in Java before an entity is
 * saved. An empty text becomes null, as Oracle stores it.
 */
final class OracleConversions {

	/** The date format of the forms (FN_TO_DATE(..., 'MM/DD/YYYY')). */
	private static final DateTimeFormatter FORM_DATE = DateTimeFormatter.ofPattern("M/d/uuuu").withResolverStyle(ResolverStyle.STRICT);

	private OracleConversions() {
	}

	/** UPPER(TRIM(value)) as Oracle computes it; null when empty. */
	static String upper(Object value) {
		String text = trimmed(value);
		return text == null ? null : text.toUpperCase(Locale.ROOT);
	}

	/** TRIM(value) as Oracle computes it (spaces only); null when empty, as Oracle stores it. */
	static String trimmed(Object value) {
		String text = oracleText(value);
		if (text == null) {
			return null;
		}
		int start = 0;
		int end = text.length();
		while (start < end && text.charAt(start) == ' ') {
			start++;
		}
		while (end > start && text.charAt(end - 1) == ' ') {
			end--;
		}
		return start == end ? null : text.substring(start, end);
	}

	/** A value as text the way Oracle converts it implicitly: numbers without trailing zeros (0.5 is ".5"). */
	static String oracleText(Object value) {
		if (value == null || value instanceof String) {
			return (String) value;
		}
		if (value instanceof java.util.Date) {
			// Oracle would have formatted it with the session's NLS date format
			throw new IllegalArgumentException("A date where text is expected: " + value);
		}
		if (value instanceof Number number) {
			String text = new BigDecimal(number.toString()).stripTrailingZeros().toPlainString();
			if (text.startsWith("0.")) {
				return text.substring(1);
			}
			return text.startsWith("-0.") ? "-" + text.substring(2) : text;
		}
		return value.toString();
	}

	/** A number column written as TRIM(value): the whole number, or null when empty. */
	static Integer toInteger(Object value) {
		String text = trimmed(value);
		return text == null ? null : new BigDecimal(text).intValueExact();
	}

	/** A number column written as TRIM(value): the whole number, or null when empty. */
	static Long toLong(Object value) {
		String text = trimmed(value);
		return text == null ? null : new BigDecimal(text).longValueExact();
	}

	/** NVL(value, 0) of a number column written as text: the value as text, or "0" when empty. */
	static String textOrZero(Object value) {
		String text = oracleText(value);
		return text == null || text.isEmpty() ? "0" : text;
	}

	/** A number column: the number (text converted as Oracle does), or null when empty. */
	static BigDecimal toDecimal(Object value) {
		String text = trimmed(value);
		return text == null ? null : new BigDecimal(text);
	}

	/** FN_TO_DATE(TRIM(value), 'MM/DD/YYYY'): the date, or null when it is not a valid date. */
	static Timestamp fnToDate(Object value) {
		String text = trimmed(value);
		if (text == null) {
			return null;
		}
		try {
			return Timestamp.valueOf(LocalDate.parse(text, FORM_DATE).atStartOfDay());
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/** A date column: a date as it is (with its time); text as MM/DD/YYYY or YYYY-MM-DD. */
	static Timestamp date(Object value) {
		if (value == null || value instanceof Timestamp) {
			return (Timestamp) value;
		}
		if (value instanceof java.util.Date date) {
			return new Timestamp(date.getTime());
		}
		String text = trimmed(value);
		if (text == null) {
			return null;
		}
		try {
			return Timestamp.valueOf(LocalDate.parse(text, text.contains("/") ? FORM_DATE : DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Not a date: " + value, e);
		}
	}
}
