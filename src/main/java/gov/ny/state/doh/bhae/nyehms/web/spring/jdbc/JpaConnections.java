package gov.ny.state.doh.bhae.nyehms.web.spring.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

/**
 * Plain JDBC connections from the JPA persistence units, for the code that still needs one (replaces ConnectionDAO):
 * reads streamed row by row into handlers, SQL known only at runtime, Oracle arrays, and batch loads into tables
 * without a known key.
 * <p>
 * A connection comes from the unit's connection provider (its data source) and is not part of any Spring transaction:
 * it is auto-commit as the pool gives it, exactly as before. The caller closes it, which returns it to the pool.
 */
@Component
public class JpaConnections {

	private static final Logger log = LoggerFactory.getLogger(JpaConnections.class);

	private final EntityManagerFactory appEntityManagerFactory;
	private final EntityManagerFactory eharsEntityManagerFactory;

	public JpaConnections(@Qualifier("entityManagerFactory") EntityManagerFactory appEntityManagerFactory,
			@Qualifier("eharsEntityManagerFactory") EntityManagerFactory eharsEntityManagerFactory) {
		this.appEntityManagerFactory = appEntityManagerFactory;
		this.eharsEntityManagerFactory = eharsEntityManagerFactory;
	}

	/** A connection to the main NYEHMS database (appDS); null if none can be had (the error is logged). */
	public Connection appConnection() {
		return connection(appEntityManagerFactory);
	}

	/** A connection to the eHARS database (eharsDS); null if none can be had (the error is logged). */
	public Connection eharsConnection() {
		return connection(eharsEntityManagerFactory);
	}

	private static Connection connection(EntityManagerFactory entityManagerFactory) {
		try {
			return entityManagerFactory.unwrap(SessionFactoryImplementor.class).getServiceRegistry().requireService(ConnectionProvider.class)
					.getConnection();
		} catch (SQLException sqlException) {
			log.error("Unable to get the connection from DataSource : {}", sqlException.getMessage());
			return null;
		}
	}
}
