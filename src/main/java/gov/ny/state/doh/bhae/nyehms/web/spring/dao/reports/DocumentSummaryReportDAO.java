package gov.ny.state.doh.bhae.nyehms.web.spring.dao.reports;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * The weekly document summary report (formerly a MyBatis mapper with the same name and method).
 */
@Repository
@RequiredArgsConstructor
public class DocumentSummaryReportDAO {

	private final NativeStatements statements;

	/**
	 * Runs a query of the report configuration and returns its rows as maps (keys = the column labels, null values left
	 * out, a row of nulls null), as the former {@code ${SQL_QUERY}} statement did.
	 * <p>
	 * Native: the SQL text is not in the application; it comes from the report configuration (sql.json on the report
	 * server) and is run unchanged.
	 *
	 * @param inputParameterMap SQL_QUERY: the query (null runs an empty statement, which fails, as before)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Map<String, String>> getDocumentSummaryData(Map<String, String> inputParameterMap) {
		String sql = inputParameterMap.get("SQL_QUERY");
		return (List) statements.list(sql == null ? "" : sql, Rows.map());
	}
}
