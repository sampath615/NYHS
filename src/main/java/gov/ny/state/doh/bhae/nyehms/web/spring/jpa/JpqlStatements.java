package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.persistence.TypedQuery;

/**
 * Runs JPQL (HQL) statements over the entities of the main persistence unit: the replacement of the former MyBatis
 * statements, with the same shape as {@link NativeStatements} so a migrated DAO reads the same either way.
 * <p>
 * A query names its select items with aliases ({@code select m.id as MSG_ID, ...}); the aliases are the labels the
 * {@link Rows} mappers fill VOs, maps and JSONObjects from, as MyBatis filled them from the column labels. Parameters
 * are positional ({@code ?1}, {@code ?2}, ...). Reads join the caller's transaction or run read-only on their own;
 * writes join it or run in their own.
 */
@Component
public class JpqlStatements {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The rows of a JPQL query.
	 *
	 * @param jpql the query; its select items named with aliases
	 * @param mapper turns a row (labels = the aliases) into an object
	 * @param parameters the values of ?1, ?2, ...
	 * @return the objects, in a modifiable list
	 */
	@Transactional(readOnly = true)
	public <T> List<T> list(String jpql, RowMapper<T> mapper, Object... parameters) {
		TypedQuery<Tuple> query = entityManager.createQuery(jpql, Tuple.class);
		bind(query, parameters);
		List<T> results = new ArrayList<>();
		for (Tuple tuple : query.getResultList()) {
			results.add(mapper.map(row(tuple)));
		}
		return results;
	}

	/**
	 * The row of a query that returns at most one row (as MyBatis selectOne).
	 *
	 * @throws IncorrectResultSizeDataAccessException if there is more than one row
	 */
	@Transactional(readOnly = true)
	public <T> T one(String jpql, RowMapper<T> mapper, Object... parameters) {
		List<T> results = list(jpql, mapper, parameters);
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Runs a JPQL bulk update, delete or insert.
	 *
	 * @return the number of rows changed
	 */
	@Transactional
	public int update(String jpql, Object... parameters) {
		Query query = entityManager.createQuery(jpql);
		bind(query, parameters);
		return query.executeUpdate();
	}

	/**
	 * Inserts a new entity and writes it at once (as a MyBatis insert was executed at once).
	 *
	 * @return 1, the number of rows inserted
	 */
	@Transactional
	public int insert(Object entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return 1;
	}

	private static void bind(Query query, Object[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Object value = parameters[i];
			query.setParameter(i + 1, value instanceof NativeStatements.Param param ? param.value() : value);
		}
	}

	private static Row row(Tuple tuple) {
		List<TupleElement<?>> elements = tuple.getElements();
		List<String> labels = new ArrayList<>(elements.size());
		List<Object> values = new ArrayList<>(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			String alias = elements.get(i).getAlias();
			labels.add(alias != null ? alias : "COLUMN" + (i + 1));
			values.add(tuple.get(i));
		}
		return new Row(labels, values);
	}
}
