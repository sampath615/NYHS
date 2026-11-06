package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * Values the callers of the former MyBatis mappers pass (map values, VO properties) converted to entity attribute types
 * the way the database stored what MyBatis bound: the bound Java type decided the JDBC setter (setString, setInt,
 * setBoolean, ...), the database converted that value to the column's type. See also {@link Values}.
 */
public final class BoundValues {

	private BoundValues() {
	}

	/**
	 * A value written to a text column: a text as it is, a number as Oracle writes it as text (no trailing zeros, 0.5
	 * as ".5"), a character as its text, a boolean as Oracle's setBoolean wrote it (1 / 0). A date would have been
	 * formatted with the session's NLS format, so it is refused.
	 */
	public static String text(Object value) {
		if (value == null || value instanceof String) {
			return (String) value;
		}
		if (value instanceof java.util.Date) {
			throw new IllegalArgumentException("A date where text is expected: " + value);
		}
		if (value instanceof Boolean flag) {
			return flag ? "1" : "0";
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

	/** A value written to a whole-number column: null and '' (NULL in Oracle) as null, a text converted as Oracle did. */
	public static Integer integer(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.intValueExact();
	}

	/** A value written to a whole-number column: null and '' (NULL in Oracle) as null, a text converted as Oracle did. */
	public static Long longValue(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.longValueExact();
	}

	/** A value written to a number column: null and '' (NULL in Oracle) as null, a text converted as Oracle did. */
	public static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value instanceof BigDecimal number) {
			return number;
		}
		if (value instanceof Boolean flag) {
			return flag ? BigDecimal.ONE : BigDecimal.ZERO;
		}
		if (value instanceof Number number) {
			return new BigDecimal(number.toString());
		}
		return new BigDecimal(value.toString().trim());
	}

	/**
	 * A value written to a date column with its time (#{x} or #{x,jdbcType=TIMESTAMP}); a java.sql.Date as its day (it
	 * was bound with setDate); null and '' as null.
	 */
	public static Timestamp timestamp(Object value) {
		if (value instanceof java.sql.Date day) {
			return Timestamp.valueOf(day.toLocalDate().atStartOfDay());
		}
		return Values.timestamp(value);
	}

	/** TRIM(value) as Oracle computes it (spaces only, the value converted to text first); null when empty. */
	public static String trim(Object value) {
		String text = text(value);
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

	/** UPPER(TRIM(value)) as Oracle computes it; null when empty. */
	public static String upperTrim(Object value) {
		String text = trim(value);
		return text == null ? null : text.toUpperCase(Locale.ROOT);
	}

	/** UPPER(TRIM(value)) written to a number column: Oracle converted the text back to a number. */
	public static BigDecimal upperTrimDecimal(Object value) {
		return decimal(upperTrim(value));
	}

	/** UPPER(TRIM(value)) written to a whole-number column. */
	public static Integer upperTrimInteger(Object value) {
		return integer(upperTrim(value));
	}

	/** UPPER(TRIM(value)) written to a whole-number column. */
	public static Long upperTrimLong(Object value) {
		return longValue(upperTrim(value));
	}

	/**
	 * UPPER(TRIM(value)) written to a date column: Oracle converted the text back to a date with the session's NLS
	 * format; a text date is read as MM/DD/YYYY or YYYY-MM-DD (<b>assumed</b>, as the earlier imports assumed). A date
	 * value is refused: Oracle formatted it as text first (NLS_TIMESTAMP_FORMAT) and could not read that text back as a
	 * date with the default formats.
	 */
	public static Timestamp upperTrimDate(Object value) {
		return textDate(upperTrim(value));
	}

	/** TO_DATE(value, 'MM/DD/YYYY'): null for null or ''; a text that is no such date is refused, as Oracle refused it. */
	public static Timestamp toDate(Object value) {
		String text = text(value);
		if (text == null || text.isEmpty()) {
			return null;
		}
		try {
			return Timestamp.valueOf(LocalDate.parse(text.trim(), FORM_DATE).atStartOfDay());
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Not a date (MM/DD/YYYY): " + value, e);
		}
	}

	private static Timestamp textDate(String text) {
		if (text == null) {
			return null;
		}
		try {
			return Timestamp.valueOf(LocalDate.parse(text, text.contains("/") ? FORM_DATE : DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Not a date: " + text, e);
		}
	}

	/** The form date format (MM/DD/YYYY; Oracle also accepts one-digit months and days). */
	private static final DateTimeFormatter FORM_DATE = DateTimeFormatter.ofPattern("M/d/uuuu").withResolverStyle(ResolverStyle.STRICT);

	/**
	 * A value written to a date column as MyBatis bound #{x,jdbcType=DATE}: a java.util.Date (the class itself) as its
	 * day only (MyBatis' DateOnlyTypeHandler), a java.sql.Date as its day, a Timestamp with its time; null and '' as null.
	 */
	public static Timestamp dateOnly(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return Timestamp.valueOf(new java.sql.Date(((java.util.Date) value).getTime()).toLocalDate().atStartOfDay());
		}
		return timestamp(value);
	}
}
