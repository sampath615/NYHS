package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.JavaObjectType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

/**
 * Runs SQL statements of the main database (appDS) as JPA native queries: the replacement of the former MyBatis
 * mappers whose SQL is kept as it was (Oracle syntax, sequences, SYSDATE, triggers).
 * <p>
 * Parameters are positional ({@code ?1}, {@code ?2}, ...) and always bound, never pasted into the SQL; a value that
 * may be null is passed as {@link #param(Object, BasicTypeReference)} (or {@link #text}, {@link #number},
 * {@link #timestamp}) so it is bound with its type, as MyBatis bound {@code #{x,jdbcType=...}}. Result rows are
 * turned into objects by a {@link RowMapper} ({@link Rows} has the MyBatis rules). The values come typed as JDBC
 * {@code getObject} types them (see {@link GetObjectTypes}).
 * <p>
 * Reads join the caller's transaction or run read-only on their own; writes join the caller's transaction or run in
 * their own (committed at once, as a MyBatis statement outside a transaction was).
 */
@Component
public class NativeStatements {

	/** Turns one result row into an object. */
	@FunctionalInterface
	public interface RowMapper<T> {
		T map(Row row);
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
		if (mapper instanceof TypedRowMapper<T> typed) {
			List<String> labels = LABELS.get(sql);
			if (labels == null) {
				// the first run of this SQL text: learn its column labels (they do not change for one text)
				List<Row> rows = tuples(sql, parameters);
				if (rows.isEmpty()) {
					return new ArrayList<>();
				}
				labels = rows.get(0).labels();
				LABELS.put(sql, labels);
			}
			return typed(sql, labels, typed, parameters);
		}
		List<T> results = new ArrayList<>();
		for (Row row : tuples(sql, parameters)) {
			results.add(mapper.map(row));
		}
		return results;
	}

	/**
	 * A row mapper that reads each column it uses with its target type, as a MyBatis type handler did (getString for
	 * a String property, getInt for an int, ...): {@link Rows#bean} and {@link Rows#scalar}.
	 */
	public interface TypedRowMapper<T> extends RowMapper<T> {

		/** The type a column is read as, or null if the column is not used. */
		Class<?> typeOf(String label, int index);
	}

	/** The column labels of each SQL text run with a typed mapper. */
	private static final Map<String, List<String>> LABELS = new ConcurrentHashMap<>();

	private List<Row> tuples(String sql, Object[] parameters) {
		Query query = entityManager.createNativeQuery(sql, Tuple.class);
		bind(query, parameters);
		List<Row> rows = new ArrayList<>();
		for (Object result : query.getResultList()) {
			rows.add(row((Tuple) result));
		}
		return rows;
	}

	private <T> List<T> typed(String sql, List<String> labels, TypedRowMapper<T> mapper, Object[] parameters) {
		// the columns read: the first of each label that the mapper uses
		List<Integer> read = new ArrayList<>();
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < labels.size(); i++) {
			String key = labels.get(i).toUpperCase(Locale.ENGLISH);
			if (seen.add(key) && mapper.typeOf(labels.get(i), i) != null) {
				read.add(i);
			}
		}
		if (read.isEmpty()) {
			// no column is used: each row maps to nothing (null), as MyBatis returned it
			List<T> results = new ArrayList<>();
			for (Row row : tuples(sql, parameters)) {
				results.add(mapper.map(new Row(row.labels(), java.util.Collections.nCopies(row.size(), null))));
			}
			return results;
		}
		Query query = entityManager.createNativeQuery(sql);
		NativeQuery<?> nativeQuery = query.unwrap(NativeQuery.class);
		for (int index : read) {
			addScalar(nativeQuery, labels.get(index), mapper.typeOf(labels.get(index), index));
		}
		bind(query, parameters);
		List<T> results = new ArrayList<>();
		for (Object result : query.getResultList()) {
			Object[] values = read.size() == 1 ? new Object[] {result} : (Object[]) result;
			List<Object> rowValues = new ArrayList<>(java.util.Collections.nCopies(labels.size(), null));
			for (int k = 0; k < read.size(); k++) {
				rowValues.set(read.get(k), values[k]);
			}
			results.add(mapper.map(new Row(labels, rowValues)));
		}
		return results;
	}

	/** Declares a column read with the type handler MyBatis used for this Java type. */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void addScalar(NativeQuery<?> query, String label, Class<?> type) {
		BasicTypeReference reference = SCALAR_TYPES.get(type);
		if (reference != null) {
			query.addScalar(label, reference);
		} else {
			query.addScalar(label, (BasicDomainType) JavaObjectType.INSTANCE);
		}
	}

	private static final Map<Class<?>, BasicTypeReference<?>> SCALAR_TYPES = Map.ofEntries(
			Map.entry(String.class, StandardBasicTypes.STRING),
			Map.entry(int.class, StandardBasicTypes.INTEGER), Map.entry(Integer.class, StandardBasicTypes.INTEGER),
			Map.entry(long.class, StandardBasicTypes.LONG), Map.entry(Long.class, StandardBasicTypes.LONG),
			Map.entry(short.class, StandardBasicTypes.SHORT), Map.entry(Short.class, StandardBasicTypes.SHORT),
			Map.entry(byte.class, StandardBasicTypes.BYTE), Map.entry(Byte.class, StandardBasicTypes.BYTE),
			Map.entry(double.class, StandardBasicTypes.DOUBLE), Map.entry(Double.class, StandardBasicTypes.DOUBLE),
			Map.entry(float.class, StandardBasicTypes.FLOAT), Map.entry(Float.class, StandardBasicTypes.FLOAT),
			Map.entry(boolean.class, StandardBasicTypes.BOOLEAN), Map.entry(Boolean.class, StandardBasicTypes.BOOLEAN),
			Map.entry(java.math.BigDecimal.class, StandardBasicTypes.BIG_DECIMAL),
			Map.entry(java.math.BigInteger.class, StandardBasicTypes.BIG_INTEGER),
			Map.entry(java.util.Date.class, StandardBasicTypes.TIMESTAMP),
			Map.entry(java.sql.Timestamp.class, StandardBasicTypes.TIMESTAMP),
			Map.entry(java.sql.Date.class, StandardBasicTypes.DATE));

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

	/** An OUT cursor (SYS_REFCURSOR); {@link #call} returns its rows, mapped. */
	public static CallParam cursor(RowMapper<?> rows) {
		return new CallParam(ParameterMode.REF_CURSOR, null, null, rows);
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
						case REF_CURSOR -> results.add(cursorRows(statement.getObject(i + 1, ResultSet.class), parameter.cursorRows()));
						case OUT, INOUT -> results.add(Rows.convert(statement.getObject(i + 1), parameter.type()));
						default -> results.add(null);
					}
				}
				return results;
			}
		});
	}

	private static List<Object> cursorRows(ResultSet resultSet, RowMapper<?> mapper) throws SQLException {
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
					Object value = resultSet.getObject(column);
					values.add(value instanceof java.sql.Clob clob ? clob.getSubString(1, (int) clob.length()) : value);
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

	private static Row row(Tuple tuple) {
		List<TupleElement<?>> elements = tuple.getElements();
		List<String> labels = new ArrayList<>(elements.size());
		List<Object> values = new ArrayList<>(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			labels.add(elements.get(i).getAlias());
			values.add(tuple.get(i));
		}
		return new Row(labels, values);
	}
}
