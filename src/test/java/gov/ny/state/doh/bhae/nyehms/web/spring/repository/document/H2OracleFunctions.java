package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Stand-ins for the NYEHMS Oracle database functions used by the queries, for the H2 test
 * databases (registered with CREATE ALIAS in the test schemas).
 */
public final class H2OracleFunctions {

	private static final DateTimeFormatter MM_DD_YYYY = DateTimeFormatter.ofPattern("M/d/uuuu").withResolverStyle(ResolverStyle.STRICT);

	private H2OracleFunctions() {
	}

	/**
	 * FN_TO_DATE(text, 'MM/DD/YYYY'): the date, or null if the text is not a valid date (assumed
	 * behaviour of the Oracle function; only the MM/DD/YYYY format is used by these queries).
	 */
	public static Date fnToDate(String text, String format) {
		if (text == null || !"MM/DD/YYYY".equalsIgnoreCase(format)) {
			return null;
		}
		try {
			return Date.valueOf(LocalDate.parse(text.trim(), MM_DD_YYYY));
		} catch (DateTimeParseException e) {
			return null;
		}
	}
}
