package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.JavaObjectType;
import org.hibernate.type.StandardBasicTypes;
import org.json.simple.JSONObject;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * Runs a native SQL query of the main database (appDS) through JPA and returns its rows as {@link JSONObject}s, as the
 * former MyBatis statements with {@code resultType="org.json.simple.JSONObject"} did: one key per column label (as the
 * query names it: upper case unless quoted), a null value left out, and a row whose values are all null returned as a
 * {@code null} element.
 * <p>
 * Used for report queries whose Oracle SQL (CTEs, window functions, date arithmetic) has no JPQL form; their values
 * are bound as parameters ({@code ?1}, {@code ?2}, ...). Each query declares its columns with their types: without
 * them Hibernate would pick a column's Java type from the dialect's reading of the JDBC metadata, which may differ from
 * what MyBatis returned (a {@code BigDecimal} for any Oracle NUMBER) and so change the JSON.
 */
@Component
public class JsonRows {

	/** A selected column: its label and the type its value is read as. */
	public record Column(String label, BasicTypeReference<?> type, BasicDomainType<?> domainType) {

		Column(String label, BasicTypeReference<?> type) {
			this(label, type, null);
		}
	}

	/** A text column (VARCHAR2, CHAR, TO_CHAR(...)). */
	public static Column text(String label) {
		return new Column(label, StandardBasicTypes.STRING);
	}

	/** A number column (any Oracle NUMBER), read as a BigDecimal as MyBatis read it. */
	public static Column number(String label) {
		return new Column(label, StandardBasicTypes.BIG_DECIMAL);
	}

	/**
	 * A column read with plain JDBC {@code getObject}, exactly as MyBatis read the columns of a map result: for a
	 * column whose type in the database is not known here.
	 */
	public static Column value(String label) {
		// the type instance: the type reference OBJECT_TYPE would let Hibernate infer a type from the metadata (a
		// one-character text would come back as a Character)
		return new Column(label, null, JavaObjectType.INSTANCE);
	}

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The rows of a query.
	 *
	 * @param sql the query, with {@code ?1}, {@code ?2}, ... for its parameters
	 * @param columns the selected columns, in any order (looked up by label)
	 * @param parameters the parameter values, in order
	 * @return one JSONObject per row
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<JSONObject> list(String sql, List<Column> columns, Object... parameters) {
		Query query = entityManager.createNativeQuery(sql);
		NativeQuery<?> nativeQuery = query.unwrap(NativeQuery.class);
		for (Column column : columns) {
			if (column.domainType() != null) {
				nativeQuery.addScalar(column.label(), (BasicDomainType) column.domainType());
			} else {
				nativeQuery.addScalar(column.label(), (BasicTypeReference) column.type());
			}
		}
		for (int i = 0; i < parameters.length; i++) {
			query.setParameter(i + 1, parameters[i]);
		}
		return ((List<Object>) query.getResultList()).stream()
				.map(row -> toJson(columns, columns.size() == 1 ? new Object[] {row} : (Object[]) row))
				.collect(Collectors.toCollection(ArrayList::new));    // a modifiable list (may hold null), as MyBatis returned
	}

	/**
	 * The row of a query that returns at most one row (as MyBatis selectOne).
	 *
	 * @return the row, or null if there is none (or all its values are null)
	 * @throws IncorrectResultSizeDataAccessException if there is more than one row
	 */
	@Transactional(readOnly = true)
	public JSONObject one(String sql, List<Column> columns, Object... parameters) {
		List<JSONObject> rows = list(sql, columns, parameters);
		if (rows.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, rows.size());
		}
		return rows.isEmpty() ? null : rows.get(0);
	}

	@SuppressWarnings("unchecked")
	private static JSONObject toJson(List<Column> columns, Object[] values) {
		JSONObject row = new JSONObject();
		for (int i = 0; i < columns.size(); i++) {
			if (values[i] != null) {
				row.put(columns.get(i).label(), values[i]);
			}
		}
		return row.isEmpty() ? null : row;
	}
}
