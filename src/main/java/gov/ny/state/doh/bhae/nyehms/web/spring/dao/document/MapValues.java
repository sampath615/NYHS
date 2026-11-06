package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The values the callers of the former MyBatis document mappers pass in their maps, converted to entity attribute
 * types the way the database received them when MyBatis bound them as they were (the type handler chosen by the
 * value's class).
 */
final class MapValues {

	private MapValues() {
	}

	/**
	 * A value written to a text column (or into a SQL expression such as UPPER(TRIM(?))), as Oracle converted what
	 * MyBatis bound: text as it is, a number as its text (no trailing zeros, 0.5 as ".5"), a character as its text. A
	 * date would have been formatted with the session's NLS format, so it is refused.
	 */
	static String text(Object value) {
		if (value == null || value instanceof String) {
			return (String) value;
		}
		if (value instanceof java.util.Date) {
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

	/**
	 * A value written to a date column as MyBatis bound #{x,jdbcType=DATE}: a java.util.Date (the class itself) as its
	 * day only (MyBatis' DateOnlyTypeHandler), a java.sql.Date as its day, a Timestamp with its time; null as null.
	 * Text is refused (Oracle would have read it with the session's NLS format).
	 */
	static Timestamp date(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Timestamp timestamp) {
			return timestamp;
		}
		if (value instanceof java.sql.Date day) {
			return Timestamp.valueOf(day.toLocalDate().atStartOfDay());
		}
		if (value instanceof java.util.Date date) {
			return value.getClass() == java.util.Date.class
					? Timestamp.valueOf(new java.sql.Date(date.getTime()).toLocalDate().atStartOfDay())
					: new Timestamp(date.getTime());
		}
		throw new IllegalArgumentException("Not a date: " + value);
	}

	/**
	 * A value written to a date column as MyBatis bound a parameter without jdbcType (or with jdbcType VARCHAR): a
	 * java.util.Date with its time (DateTypeHandler), a java.sql.Date as its day, a Timestamp as it is.
	 */
	static Timestamp timestamp(Object value) {
		if (value != null && value.getClass() == java.util.Date.class) {
			return new Timestamp(((java.util.Date) value).getTime());
		}
		return date(value);
	}

	/** A value written to a number column: a number as it is, a text as the number it holds; null or '' as null. */
	static BigDecimal decimal(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		return value instanceof BigDecimal number ? number : new BigDecimal(value.toString().trim());
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	static Integer integer(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.intValueExact();
	}

	/** A whole-number column (a fraction fails rather than being cut). */
	static Long longValue(Object value) {
		BigDecimal number = decimal(value);
		return number == null ? null : number.longValueExact();
	}
}
