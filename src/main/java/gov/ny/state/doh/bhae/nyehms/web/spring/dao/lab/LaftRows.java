package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.simple.JSONObject;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;

/**
 * Helpers shared by the LAFT / lab DAOs of this package (formerly MyBatis mappers): the MyBatis rules for map and JSON
 * rows read from JPQL, and the conversion of the callers' parameter values.
 */
final class LaftRows {

	private LaftRows() {
	}

	/**
	 * Map rows (resultType {@code java.util.Map}) from a JPQL query, with the whole numbers as BigDecimals: the former
	 * statements read every NUMBER column (and COUNT / SUM) with getObject, which gives a BigDecimal on Oracle.
	 */
	static RowMapper<Map<String, Object>> map() {
		return row -> decimals(Rows.map().map(row));
	}

	/**
	 * Map rows from a JPQL query whose select items are named here, in order (the former column labels), with the
	 * whole numbers as BigDecimals (see {@link #map()}); null values are left out and a row of nulls is null.
	 */
	static RowMapper<Map<String, Object>> map(String... keys) {
		return row -> {
			Map<String, Object> map = new java.util.HashMap<>();
			for (int i = 0; i < keys.length; i++) {
				if (row.get(i) != null) {
					map.put(keys[i], decimal(row.get(i)));
				}
			}
			return map.isEmpty() ? null : map;
		};
	}

	/** JSONObject rows from a JPQL query, with the whole numbers as BigDecimals (see {@link #map()}). */
	static RowMapper<JSONObject> json() {
		return row -> decimals(Rows.json().map(row));
	}

	/** The whole-number values of a row (Long, Integer, Short, BigInteger) as BigDecimals; null stays null. */
	static <M extends Map<String, Object>> M decimals(M row) {
		if (row != null) {
			row.replaceAll((key, value) -> decimal(value));
		}
		return row;
	}

	/** A whole-number value as a BigDecimal (as getObject read a NUMBER); other values as they are. */
	static Object decimal(Object value) {
		if (value instanceof Long || value instanceof Integer || value instanceof Short || value instanceof Byte) {
			return BigDecimal.valueOf(((Number) value).longValue());
		}
		if (value instanceof BigInteger integer) {
			return new BigDecimal(integer);
		}
		return value;
	}

	/**
	 * The values of a former {@code <foreach>} collection (a Collection, an array or a primitive array), converted.
	 *
	 * @throws InvalidDataAccessApiUsageException if there is no value: the former statement then rendered {@code in ()}
	 *         (or failed on the null collection), which the database rejected
	 */
	static <T> List<T> inList(Object values, String name, Function<Object, T> convert) {
		List<T> list = new ArrayList<>();
		if (values instanceof Collection<?> collection) {
			collection.forEach(value -> list.add(convert.apply(value)));
		} else if (values != null && values.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(values); i++) {
				list.add(convert.apply(Array.get(values, i)));
			}
		} else if (values != null) {
			throw new InvalidDataAccessApiUsageException("Not a collection: " + name);
		}
		if (list.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("No values for the IN list " + name + " (the former SQL failed too)");
		}
		return list;
	}

	/** The values of a former {@code <foreach>} as whole numbers (the database converted text values the same way). */
	static List<Long> longs(Object values, String name) {
		return inList(values, name, Values::longValue);
	}

	/** OGNL {@code x != null and x != ''}. */
	static boolean present(Object value) {
		return value != null && !"".equals(value);
	}

	/** The application server's clock (the former SYSDATE). */
	static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/** A whole-number column value of the callers' maps / VOs (null, and an empty text, as null). */
	static Long id(Object value) {
		return Values.longValue(value);
	}
}
