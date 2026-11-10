package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

/**
 * Runs SQL statements of the main database (appDS) through the JPA persistence context: the replacement of the former
 * MyBatis mappers whose SQL is kept as it was (Oracle syntax, sequences, SYSDATE, triggers).
 * <p>
 * Parameters are positional ({@code ?1}, {@code ?2}, ...) and always bound, never pasted into the SQL; a value that
 * may be null is passed as {@link #param(Object, BasicTypeReference)} (or {@link #text}, {@link #number},
 * {@link #timestamp}) so it is bound with its type, as MyBatis bound {@code #{x,jdbcType=...}}. Result rows are
 * turned into objects by a {@link RowMapper} ({@link Rows} has the MyBatis rules).
 * <p>
 * Queries run as JDBC statements on the persistence context's connection and their columns are read by position, as
 * MyBatis read them: typed as plain JDBC {@code getObject} types them (any NUMBER a {@code BigDecimal}, text and CLOBs
 * a {@code String}, DATE / TIMESTAMP a {@code Timestamp}, see also {@link GetObjectTypes}) or, for a
 * {@link TypedRowMapper}, with the getter of each property's type (getString, getInt, ...). Of two columns with the
 * same label the first is the one a mapper reads ({@link Row#get(String)}); Hibernate's own native result mapping
 * refuses a repeated label, which the former mappers' SQL has. Writes run as JPA native queries. Reads join the
 * caller's transaction or run read-only on their own; writes join the caller's transaction or run in their own
 * (committed at once, as a MyBatis statement outside a transaction was).
 */
@Component
public class NativeStatements {

	/** Turns one result row into an object. */
	@FunctionalInterface
	public interface RowMapper<T> {
		T map(Row row);
	}

	/**
	 * A row mapper that reads each column it uses with its target type, as a MyBatis type handler did (getString for
	 * a String property, getInt for an int, ...): {@link Rows#bean} and {@link Rows#scalar}.
	 */
	public interface TypedRowMapper<T> extends RowMapper<T> {

		/** The type a column is read as, or null if the column is not used. */
		Class<?> typeOf(String label, int index);
	}

	/** A parameter bound with its type (for null values). */
	public record Param(Object value, BasicTypeReference<?> type) {
	}

	public static Param param(Object value, BasicTypeReference<?> type) {
		return new Param(value, type);
	}

	/** A text parameter (#{x,jdbcType=VARCHAR}). */
	public static Param text(Object value) {
		return new Param(value, StandardBasicTypes.STRING);
	}

	/** A number parameter (#{x,jdbcType=DECIMAL/NUMERIC/INTEGER}). */
	public static Param number(Object value) {
		return new Param(value, StandardBasicTypes.BIG_DECIMAL);
	}

	/** A date parameter (#{x,jdbcType=DATE/TIMESTAMP}); its time is kept. */
	public static Param timestamp(Object value) {
		return new Param(value, StandardBasicTypes.TIMESTAMP);
	}

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The rows of a query.
	 *
	 * @param sql the query, {@code ?1}, {@code ?2}, ... for its parameters
	 * @param mapper turns a row into an object (a row may become null, as with MyBatis)
	 * @param parameters the parameter values, in order (plain values or {@link Param}s)
	 * @return the objects, in a modifiable list
	 */
	@Transactional(readOnly = true)
	public <T> List<T> list(String sql, RowMapper<T> mapper, Object... parameters) {
		TypedRowMapper<?> typed = mapper instanceof TypedRowMapper<T> typedMapper ? typedMapper : null;
		List<T> results = new ArrayList<>();
		for (Row row : rows(sql, parameters, typed)) {
			results.add(mapper.map(row));
		}
		return results;
	}

	/**
	 * The row of a query that returns at most one row (as MyBatis selectOne).
	 *
	 * @return the object, or null if there is no row
	 * @throws IncorrectResultSizeDataAccessException if there is more than one row
	 */
	@Transactional(readOnly = true)
	public <T> T one(String sql, RowMapper<T> mapper, Object... parameters) {
		List<T> results = list(sql, mapper, parameters);
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Runs an insert, update, delete or PL/SQL block.
	 *
	 * @return the number of rows changed (as MyBatis returned it)
	 */
	@Transactional
	public int update(String sql, Object... parameters) {
		Query query = entityManager.createNativeQuery(sql);
		bind(query, parameters);
		return query.executeUpdate();
	}

	// ---- reads: JDBC on the persistence context's connection -----------------------------------------------------

	private static final Pattern POSITIONAL = Pattern.compile("\\?(\\d+)");

	/** The rows of a query, every column read by position (a typed mapper's unused columns, and repeated labels, left null). */
	private List<Row> rows(String sql, Object[] parameters, TypedRowMapper<?> typed) {
		List<Integer> positions = new ArrayList<>();           // the parameter of each '?' of the JDBC text, in order
		Matcher matcher = POSITIONAL.matcher(sql);
		StringBuilder jdbcSql = new StringBuilder();
		while (matcher.find()) {
			positions.add(Integer.parseInt(matcher.group(1)));
			matcher.appendReplacement(jdbcSql, "?");
		}
		matcher.appendTail(jdbcSql);
		return entityManager.unwrap(Session.class).doReturningWork(connection -> {
			try (PreparedStatement statement = connection.prepareStatement(jdbcSql.toString())) {
				for (int i = 0; i < positions.size(); i++) {
					int index = positions.get(i);
					if (index < 1 || index > parameters.length) {
						throw new IllegalArgumentException("No value for ?" + index + " in: " + sql);
					}
					bindJdbc(statement, i + 1, parameters[index - 1]);
				}
				try (ResultSet resultSet = statement.executeQuery()) {
					ResultSetMetaData metaData = resultSet.getMetaData();
					int count = metaData.getColumnCount();
					List<String> labels = new ArrayList<>(count);
					int[] jdbcTypes = new int[count];
					Class<?>[] readAs = new Class<?>[count];
					boolean[] read = new boolean[count];
					Set<String> seen = new HashSet<>();
					for (int c = 0; c < count; c++) {
						String label = metaData.getColumnLabel(c + 1);
						labels.add(label);
						jdbcTypes[c] = metaData.getColumnType(c + 1);
						boolean first = seen.add(label.toUpperCase(Locale.ENGLISH));
						readAs[c] = typed == null ? null : typed.typeOf(label, c);
						read[c] = first && (typed == null || readAs[c] != null);
					}
					List<Row> rows = new ArrayList<>();
					while (resultSet.next()) {
						List<Object> values = new ArrayList<>(count);
						for (int c = 0; c < count; c++) {
							values.add(read[c] ? read(resultSet, c + 1, jdbcTypes[c], readAs[c]) : null);
						}
						rows.add(new Row(List.copyOf(labels), values));
					}
					return rows;
				}
			}
		});
	}

	/** Binds a value as MyBatis bound it by its Java type; a {@link Param} that is null as a null of its type, a plain null as OTHER. */
	private static void bindJdbc(PreparedStatement statement, int index, Object value) throws SQLException {
		if (value instanceof Param param) {
			if (param.value() == null) {
				statement.setNull(index, param.type().getSqlTypeCode());
				return;
			}
			value = param.value();
		}
		if (value == null) {
			statement.setNull(index, Types.OTHER);
		} else if (value instanceof Timestamp || value instanceof java.sql.Date || value instanceof java.sql.Time) {
			statement.setObject(index, value);
		} else if (value instanceof java.util.Date date) {
			statement.setTimestamp(index, new Timestamp(date.getTime()));
		} else if (value instanceof Character character) {
			statement.setString(index, character.toString());
		} else if (value instanceof BigInteger number) {
			statement.setBigDecimal(index, new BigDecimal(number));
		} else if (value instanceof Enum<?> constant) {
			statement.setString(index, constant.name());
		} else {
			statement.setObject(index, value);
		}
	}

	/**
	 * A column's value: with the getter of the type a mapper reads it as (MyBatis' type handler for that property
	 * type), or as getObject types it when the mapper has no type (any NUMBER a BigDecimal, text and CLOBs a String,
	 * DATE / TIMESTAMP a Timestamp or Date, anything else as the driver gives it).
	 */
	private static Object read(ResultSet resultSet, int column, int jdbcType, Class<?> as) throws SQLException {
		if (as == null) {
			return switch (jdbcType) {
				case Types.NUMERIC, Types.DECIMAL -> resultSet.getBigDecimal(column);
				case Types.CHAR, Types.VARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB ->
					resultSet.getString(column);
				case Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> resultSet.getTimestamp(column);
				case Types.DATE -> resultSet.getDate(column);
				case Types.TIME -> resultSet.getTime(column);
				default -> object(resultSet, column);
			};
		}
		if (as == String.class || as == char.class || as == Character.class) {
			return resultSet.getString(column);
		}
		if (as == int.class || as == Integer.class) {
			int value = resultSet.getInt(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == long.class || as == Long.class) {
			long value = resultSet.getLong(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == short.class || as == Short.class) {
			short value = resultSet.getShort(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == byte.class || as == Byte.class) {
			byte value = resultSet.getByte(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == double.class || as == Double.class) {
			double value = resultSet.getDouble(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == float.class || as == Float.class) {
			float value = resultSet.getFloat(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == boolean.class || as == Boolean.class) {
			boolean value = resultSet.getBoolean(column);
			return resultSet.wasNull() ? null : value;
		}
		if (as == BigDecimal.class) {
			return resultSet.getBigDecimal(column);
		}
		if (as == BigInteger.class) {
			BigDecimal value = resultSet.getBigDecimal(column);
			return value == null ? null : value.toBigInteger();
		}
		if (as == java.sql.Date.class) {
			return resultSet.getDate(column);
		}
		if (as == Timestamp.class || as == java.util.Date.class) {
			return resultSet.getTimestamp(column);
		}
		if (as == LocalDate.class) {
			java.sql.Date value = resultSet.getDate(column);
			return value == null ? null : value.toLocalDate();
		}
		if (as == LocalDateTime.class) {
			Timestamp value = resultSet.getTimestamp(column);
			return value == null ? null : value.toLocalDateTime();
		}
		return object(resultSet, column);
	}

	/** getObject, a CLOB as its text. */
	private static Object object(ResultSet resultSet, int column) throws SQLException {
		Object value = resultSet.getObject(column);
		return value instanceof java.sql.Clob clob ? clob.getSubString(1, (int) clob.length()) : value;
	}

	// ---- stored procedures -------------------------------------------------------------------------------------------

	/** A parameter of a stored procedure call. */
	public record CallParam(ParameterMode mode, Class<?> type, Object value, RowMapper<?> cursorRows) {
	}

	/** An IN parameter (bound with its type, also when null). */
	public static CallParam in(Object value, Class<?> type) {
		return new CallParam(ParameterMode.IN, type, value, null);
	}

	/** An OUT parameter; its value is returned by {@link #call}. */
	public static CallParam out(Class<?> type) {
		return new CallParam(ParameterMode.OUT, type, null, null);
	}

	/** An IN OUT parameter. */
	public static CallParam inOut(Object value, Class<?> type) {
		return new CallParam(ParameterMode.INOUT, type, value, null);
	}

	/** An OUT cursor (SYS_REFCURSOR); {@link #call} returns its rows, mapped; the values are read with getObject. */
	public static CallParam cursor(RowMapper<?> rows) {
		return new CallParam(ParameterMode.REF_CURSOR, null, null, rows);
	}

	/**
	 * An OUT cursor whose columns are all read as text (getString, as MyBatis read every column of a result map into
	 * String properties: dates and ROWIDs in the driver's text form); {@link #call} returns its rows, mapped.
	 */
	public static CallParam textCursor(RowMapper<?> rows) {
		return new CallParam(ParameterMode.REF_CURSOR, String.class, null, rows);
	}

	/**
	 * Calls a stored procedure (formerly a MyBatis {@code statementType="CALLABLE"} statement).
	 * <p>
	 * Without cursors it is a JPA {@link StoredProcedureQuery}. With an OUT cursor the call runs on the persistence
	 * context's connection ({@code Session.doReturningWork}, in the same transaction): JPA returns the rows of a
	 * REF_CURSOR without their column names, and the rows are mapped by label as MyBatis mapped them.
	 *
	 * @param procedure the procedure's name, e.g. {@code PKG_STANDARDIZATION.PR_MERGE}
	 * @param parameters the parameters, in order
	 * @return one value per parameter, in order: the value after the call for OUT / IN OUT parameters, the list of
	 *         mapped rows for cursors, null for IN parameters
	 */
	@Transactional
	public List<Object> call(String procedure, CallParam... parameters) {
		boolean cursors = java.util.Arrays.stream(parameters).anyMatch(p -> p.mode() == ParameterMode.REF_CURSOR);
		return cursors ? callWithCursors(procedure, parameters) : callProcedure(procedure, parameters);
	}

	private List<Object> callProcedure(String procedure, CallParam[] parameters) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedure);
		for (int i = 0; i < parameters.length; i++) {
			CallParam parameter = parameters[i];
			query.registerStoredProcedureParameter(i + 1, parameter.type(), parameter.mode());
			if (parameter.mode() == ParameterMode.IN || parameter.mode() == ParameterMode.INOUT) {
				query.setParameter(i + 1, parameter.value());
			}
		}
		query.execute();
		List<Object> results = new ArrayList<>();
		for (int i = 0; i < parameters.length; i++) {
			ParameterMode mode = parameters[i].mode();
			results.add(mode == ParameterMode.OUT || mode == ParameterMode.INOUT ? query.getOutputParameterValue(i + 1) : null);
		}
		return results;
	}

	private List<Object> callWithCursors(String procedure, CallParam[] parameters) {
		String sql = "{call " + procedure + "(" + String.join(",", java.util.Collections.nCopies(parameters.length, "?")) + ")}";
		return entityManager.unwrap(Session.class).doReturningWork(connection -> {
			try (CallableStatement statement = connection.prepareCall(sql)) {
				for (int i = 0; i < parameters.length; i++) {
					CallParam parameter = parameters[i];
					if (parameter.mode() == ParameterMode.REF_CURSOR) {
						statement.registerOutParameter(i + 1, Types.REF_CURSOR);
						continue;
					}
					if (parameter.mode() == ParameterMode.IN || parameter.mode() == ParameterMode.INOUT) {
						if (parameter.value() == null) {
							statement.setNull(i + 1, sqlType(parameter.type()));
						} else {
							statement.setObject(i + 1, parameter.value());
						}
					}
					if (parameter.mode() == ParameterMode.OUT || parameter.mode() == ParameterMode.INOUT) {
						statement.registerOutParameter(i + 1, sqlType(parameter.type()));
					}
				}
				statement.execute();
				List<Object> results = new ArrayList<>();
				for (int i = 0; i < parameters.length; i++) {
					CallParam parameter = parameters[i];
					switch (parameter.mode()) {
						case REF_CURSOR -> results.add(cursorRows(statement.getObject(i + 1, ResultSet.class), parameter.cursorRows(),
								parameter.type() == String.class));
						case OUT, INOUT -> results.add(Rows.convert(statement.getObject(i + 1), parameter.type()));
						default -> results.add(null);
					}
				}
				return results;
			}
		});
	}

	/** The rows of a cursor, mapped; the values read with getString ({@code text}) or getObject. A null cursor has no rows. */
	private static List<Object> cursorRows(ResultSet resultSet, RowMapper<?> mapper, boolean text) throws SQLException {
		List<Object> rows = new ArrayList<>();
		if (resultSet == null) {
			return rows;
		}
		try (resultSet) {
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<String> labels = new ArrayList<>();
			for (int column = 1; column <= metaData.getColumnCount(); column++) {
				labels.add(metaData.getColumnLabel(column));
			}
			while (resultSet.next()) {
				List<Object> values = new ArrayList<>(labels.size());
				for (int column = 1; column <= labels.size(); column++) {
					if (text) {
						values.add(resultSet.getString(column));
						continue;
					}
					values.add(object(resultSet, column));
				}
				rows.add(mapper.map(new Row(labels, values)));
			}
		}
		return rows;
	}

	/** The JDBC type of a parameter's Java type (for setNull and registerOutParameter). */
	private static int sqlType(Class<?> type) {
		if (type == String.class) {
			return Types.VARCHAR;
		}
		if (Number.class.isAssignableFrom(type) || type.isPrimitive()) {
			return Types.NUMERIC;
		}
		if (type == java.sql.Date.class) {
			return Types.DATE;                                       // #{x,jdbcType=DATE}: a null is a DATE null
		}
		if (java.util.Date.class.isAssignableFrom(type)) {
			return Types.TIMESTAMP;
		}
		return Types.OTHER;
	}

	private static void bind(Query query, Object[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Object value = parameters[i];
			if (value instanceof Param param) {
				if (param.value() == null) {
					query.setParameter(i + 1, new TypedParameterValue<>(param.type(), null));
				} else {
					query.setParameter(i + 1, param.value());
				}
			} else {
				query.setParameter(i + 1, value);
			}
		}
	}
}
