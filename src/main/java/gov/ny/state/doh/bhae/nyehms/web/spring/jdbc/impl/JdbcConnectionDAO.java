package gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.impl;

/**
 * This class will provide the Database connection object details
 *
 * @author ska03
 * 
 */
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.ConnectionDAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcConnectionDAO implements ConnectionDAO {

	private DataSource dataSource;
	private DataSource sqlDataSource;

	private static Logger log = LoggerFactory.getLogger(JdbcConnectionDAO.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlDataSource(DataSource sqlDataSource) {
		this.sqlDataSource = sqlDataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection getConnectionObj() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();

			if (log.isDebugEnabled()) {
				log.debug("Oracle database Connection {}", connection);
			}

		} catch (SQLException sqlException) {

			log.error("Unable to get the connection from DataSource : {}",sqlException.getMessage());
		}
		return connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection getSqlServerConnectionObj() {
		Connection connection = null;
		try {
			connection = sqlDataSource.getConnection();

			if (log.isDebugEnabled()) {
				log.debug("SQL server Connection {}", connection);
			}

		} catch (SQLException sqlException) {

			log.error("Unable to get the connection from DataSource : {}",sqlException.getMessage());
		}
		return connection;
	}

}
