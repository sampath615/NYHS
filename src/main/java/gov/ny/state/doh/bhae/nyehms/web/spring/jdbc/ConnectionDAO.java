package gov.ny.state.doh.bhae.nyehms.web.spring.jdbc;

/**
 * This class is used to define the Database connection object details
 *
 * @author ska03
 * 
 */
import java.sql.Connection;

public interface ConnectionDAO {
	/**
	 * Rendering the weblogic datasource connection(Oracle) object
	 * 
	 * @return connection object
	 */
	public Connection getConnectionObj();

	/**
	 * Rendering the SQL server database connection object
	 * 
	 * @return Connection object
	 */
	public Connection getSqlServerConnectionObj();
}
