package gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.EharsJpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.EharsVariableConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Runs the eHARS person query in the eHARS persistence unit. The SQL comes from the database at runtime
 * (AssignmentDAO.eharsPersonViewQuery), with columns not known here, and every value is shown as the database's text of
 * it, so it is run on the session's connection and read with getString: a JPA native query would give typed values and
 * change how dates appear on the screen.
 */
@Component
public class EharsPersonViewReader {

	@PersistenceContext(unitName = "ehars")
	private EntityManager entityManager;

	/**
	 * The result of the query, by screen field name (EharsVariableConstants.EHARS_VAR_NAME_MAP); the last row wins.
	 *
	 * @param sqlQuery the generated query
	 * @return the values; empty if there is no row
	 */
	@Transactional(transactionManager = EharsJpaConfig.TRANSACTION_MANAGER, readOnly = true)
	public Map<String, String> read(String sqlQuery) {
		return entityManager.unwrap(Session.class).doReturningWork(connection -> {
			Map<String, String> values = new LinkedHashMap<>();
			try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlQuery)) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				while (resultSet.next()) {
					for (int column = 1; column <= metaData.getColumnCount(); column++) {
						String columnName = metaData.getColumnName(column);
						values.put(EharsVariableConstants.EHARS_VAR_NAME_MAP.get(columnName), resultSet.getString(columnName));
					}
				}
			}
			return values;
		});
	}
}
