package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The parameter values the callers of the former MyBatis mappers pass (map values, VO properties), converted to
 * entity attribute types the way the database stored them when MyBatis bound them as they were.
 */
public final class Values {

	private Values() {
	}

	/** A date column: a date with its time; null, and an empty text (Oracle stored '' as NULL), as null. */
	public static Timestamp timestamp(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value instanceof Timestamp timestamp) {
			return timestamp;
		}
		if (value instanceof java.util.Date date) {
			return new Timestamp(date.getTime());
		}
		throw new IllegalArgumentException("Not a date: " + value);
	}

	/** A whole-number column; null, and an empty text, as null. */
	public static Long longValue(Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value instanceof Number number) {
			return new BigDecimal(number.toString()).longValueExact();
		}
		return new BigDecimal(value.toString().trim()).longValueExact();
	}

	/** A text column; a character as its one-character text (MyBatis bound a Character with setString). */
	public static String text(Object value) {
		return value == null ? null : value.toString();
	}
}
