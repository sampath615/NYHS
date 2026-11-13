package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The AIDA data of a client, read from the URS database (formerly a MyBatis mapper with the same name and method on
 * the URS session factory; the caller is unchanged).
 * <p>
 * Plain JDBC ({@link JdbcTemplate} on {@code ursDS}): the URS database has no JPA unit and no entities — the table and
 * the select list come from the environment properties ({@code <parameter>_TABLE}, {@code <parameter>_COLUMNS}: a SQL
 * fragment with conversions and aliases), as the former {@code ${columns}} / {@code ${tableName}} took them; they are
 * run as configured (a missing one is refused; it failed with a SQL error before), the client id is bound. A null
 * column is left out of its row, as MyBatis left it out.
 */
@Repository
public class AIDADataDAO {

	private final JdbcTemplate urs;

	public AIDADataDAO(@Qualifier("ursDS") DataSource ursDS) {
		this.urs = new JdbcTemplate(ursDS);
	}

	/**
	 * The configured columns of the configured table for a client, one map per row (the column labels as keys).
	 *
	 * @param inOutParameterMap tableName, columns (from the configuration), tc_id
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> loadAirsAIDAData(Map<String, String> inOutParameterMap) {
		String columns = configured(inOutParameterMap.get("columns"), "columns");
		String table = configured(inOutParameterMap.get("tableName"), "tableName");
		List<Map<String, Object>> rows = urs.queryForList("select " + columns + " from " + table + " where tc_id = ?", inOutParameterMap.get("tc_id"));
		for (Map<String, Object> row : rows) {
			row.values().removeIf(value -> value == null);
		}
		return (List) rows;
	}

	/** A configured SQL fragment, which must be present and must not end or extend the statement. */
	private static String configured(String fragment, String what) {
		if (fragment == null || fragment.isBlank() || fragment.contains(";") || fragment.contains("--") || fragment.contains("/*")) {
			throw new InvalidDataAccessApiUsageException("No usable " + what + " configured for the AIDA data: " + fragment);
		}
		return fragment.trim();
	}
}
