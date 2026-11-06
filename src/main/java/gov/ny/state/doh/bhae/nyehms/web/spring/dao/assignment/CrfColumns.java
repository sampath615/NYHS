package gov.ny.state.doh.bhae.nyehms.web.spring.dao.assignment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.StandardBasicTypes;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;

/**
 * The columns the case report form statements write (formerly MyBatis inserts and updates of several hundred columns),
 * as lists of entity attributes with their values: the JPQL insert / update is built from such a list.
 * <p>
 * Each value is bound with the type MyBatis bound it with (the VO property's type: setString, setInt, setBoolean,
 * a CLOB for {@code jdbcType=CLOB}, ...), also when it is null. What the former SQL did to a value is done here in
 * Java the way Oracle did it: {@link #upper} (TRIM(UPPER(x))), {@link #trimmed} (TRIM(x)), {@link #toDate}
 * (TO_DATE(x, 'MM/DD/YYYY')). TRIM(UPPER(x)) of a number or a boolean wrote the same value as x itself, so those are
 * bound as they are.
 */
final class CrfColumns {

	static final BasicTypeReference<String> STRING = StandardBasicTypes.STRING;
	static final BasicTypeReference<Integer> INTEGER = StandardBasicTypes.INTEGER;
	static final BasicTypeReference<Boolean> BOOLEAN = StandardBasicTypes.BOOLEAN;
	static final BasicTypeReference<Float> FLOAT = StandardBasicTypes.FLOAT;
	static final BasicTypeReference<java.util.Date> TIMESTAMP = StandardBasicTypes.TIMESTAMP;
	/** #{x:CLOB}: MyBatis' ClobTypeHandler (a character stream). */
	static final BasicTypeReference<String> CLOB = StandardBasicTypes.MATERIALIZED_CLOB;

	private CrfColumns() {
	}

	/**
	 * A written column: an entity attribute and either its value (from the VO, bound with a type) or an HQL literal
	 * ({@code 'N'}, {@code 2023}, {@code sysdate}).
	 */
	record Column<V>(String attribute, BasicTypeReference<?> type, Function<V, Object> value, String literal) {
	}

	static <V> Column<V> column(String attribute, BasicTypeReference<?> type, Function<V, Object> value) {
		return new Column<>(attribute, type, value, null);
	}

	static <V> Column<V> literal(String attribute, String literal) {
		return new Column<>(attribute, null, null, literal);
	}

	/** A value bound with its type, also when it is null. */
	static TypedParameterValue<?> typed(BasicTypeReference<?> type, Object value) {
		@SuppressWarnings({"unchecked", "rawtypes"})
		TypedParameterValue<?> typed = new TypedParameterValue(type, value);
		return typed;
	}

	/**
	 * A value bound as MyBatis bound a map value: with the type of its class (setInt for an Integer, setString for a
	 * String, ...), a null as the given type.
	 */
	static TypedParameterValue<?> byClass(Object value, BasicTypeReference<?> nullType) {
		if (value instanceof Integer) return typed(INTEGER, value);
		if (value instanceof Long) return typed(StandardBasicTypes.LONG, value);
		if (value instanceof BigDecimal) return typed(StandardBasicTypes.BIG_DECIMAL, value);
		if (value instanceof Boolean) return typed(BOOLEAN, value);
		if (value instanceof Character character) return typed(STRING, character.toString());
		if (value == null) return typed(nullType, null);
		return typed(STRING, value.toString());
	}

	/**
	 * Inserts one row: {@code insert into <entity> (attributes) values (...)}.
	 *
	 * @return the number of rows inserted (1)
	 */
	static <V> int insert(JpqlStatements jpql, String entity, List<Column<V>> columns, V vo) {
		StringBuilder names = new StringBuilder();
		StringBuilder values = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		for (Column<V> column : columns) {
			if (names.length() > 0) {
				names.append(", ");
				values.append(", ");
			}
			names.append(column.attribute());
			values.append(value(column, vo, parameters));
		}
		return jpql.update("insert into " + entity + " (" + names + ") values (" + values + ")", parameters.toArray());
	}

	/**
	 * Updates rows: {@code update <entity> e set e.a = ..., ... where <where>}; the where clause names the entity
	 * {@code e} and its parameters follow the set clause's ({@code ?n} numbered from {@code whereStart}).
	 *
	 * @param where the condition, its parameters written as {@code ?%d}, {@code ?%d}, ... (filled in here)
	 * @return the number of rows changed
	 */
	static <V> int update(JpqlStatements jpql, String entity, List<Column<V>> columns, V vo, String where, Object... whereParameters) {
		StringBuilder set = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		for (Column<V> column : columns) {
			if (set.length() > 0) {
				set.append(", ");
			}
			set.append("e.").append(column.attribute()).append(" = ").append(value(column, vo, parameters));
		}
		Object[] positions = new Object[whereParameters.length];
		for (int i = 0; i < whereParameters.length; i++) {
			parameters.add(whereParameters[i]);
			positions[i] = parameters.size();
		}
		return jpql.update("update " + entity + " e set " + set + " where " + String.format(where, positions), parameters.toArray());
	}

	private static <V> String value(Column<V> column, V vo, List<Object> parameters) {
		if (column.literal() != null) {
			return column.literal();
		}
		parameters.add(typed(column.type(), column.value().apply(vo)));
		return "?" + parameters.size();
	}

	/** TRIM(UPPER(value)) as Oracle computes it: only spaces are trimmed; an empty result is null (Oracle stores '' as NULL). */
	static String upper(String value) {
		String text = trimmed(value);
		if (text == null) {
			return null;
		}
		StringBuilder upper = new StringBuilder(text.length());
		text.codePoints().forEach(c -> upper.appendCodePoint(Character.toUpperCase(c)));
		return upper.toString();
	}

	/** TRIM(value) as Oracle computes it (spaces only); null when empty. */
	static String trimmed(String value) {
		if (value == null) {
			return null;
		}
		int start = 0;
		int end = value.length();
		while (start < end && value.charAt(start) == ' ') {
			start++;
		}
		while (end > start && value.charAt(end - 1) == ' ') {
			end--;
		}
		return start == end ? null : value.substring(start, end);
	}

	private static final Pattern FORM_DATE = Pattern.compile("(\\d{1,2})[^A-Za-z0-9](\\d{1,2})[^A-Za-z0-9](\\d{1,4})");

	/**
	 * TO_DATE(value, 'MM/DD/YYYY') as Oracle reads it: month, day and year as 1-2, 1-2 and 1-4 digits with any
	 * separator, blanks around them ignored; null for null or empty text. A text Oracle would refuse (ORA-018xx) is
	 * refused.
	 *
	 * @throws IllegalArgumentException if the text is not such a date
	 */
	static Timestamp toDate(String value) {
		String text = value == null ? null : value.strip();
		if (text == null || text.isEmpty()) {
			return null;
		}
		Matcher m = FORM_DATE.matcher(text);
		if (!m.matches()) {
			throw new IllegalArgumentException("Not a date in the format MM/DD/YYYY: " + value);
		}
		int month = Integer.parseInt(m.group(1));
		int day = Integer.parseInt(m.group(2));
		int year = Integer.parseInt(m.group(3));
		if (month < 1 || month > 12 || year < 1 || day < 1 || day > Year.of(year).atMonth(month).lengthOfMonth()) {
			throw new IllegalArgumentException("Not a valid date: " + value);
		}
		return Timestamp.valueOf(LocalDate.of(year, month, day).atStartOfDay());
	}

	/**
	 * TRIM(TO_DATE(value, 'MM/DD/YYYY')) written into a date column: Oracle turns the date into text and back with the
	 * session's date format, which is assumed to be the default DD-MON-RR. The day stays; the year keeps its last two
	 * digits and gets the century of the RR rule (for this century: 00-49 -> 20xx, 50-99 -> 19xx).
	 */
	static Timestamp toDateThroughText(String value) {
		Timestamp date = toDate(value);
		if (date == null) {
			return null;
		}
		LocalDate day = date.toLocalDateTime().toLocalDate();
		int twoDigits = day.getYear() % 100;
		int current = Year.now().getValue();
		int century = current / 100 * 100;
		if (current % 100 < 50 && twoDigits >= 50) {
			century -= 100;
		} else if (current % 100 >= 50 && twoDigits < 50) {
			century += 100;
		}
		int year = century + twoDigits;
		int dayOfMonth = Math.min(day.getDayOfMonth(), Year.of(year).atMonth(day.getMonthValue()).lengthOfMonth());
		if (dayOfMonth != day.getDayOfMonth()) {
			throw new IllegalArgumentException("Not a valid date after the RR conversion: " + value);
		}
		return Timestamp.valueOf(LocalDate.of(year, day.getMonthValue(), dayOfMonth).atStartOfDay());
	}
}
