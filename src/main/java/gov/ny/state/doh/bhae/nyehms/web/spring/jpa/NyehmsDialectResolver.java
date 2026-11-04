package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

/**
 * Picks the dialect of the main persistence unit from the database it connects to: {@link NyehmsOracleDialect} for
 * Oracle, {@link NyehmsH2Dialect} for the tests' H2; anything else is left to Hibernate. Registered with
 * {@code hibernate.dialect_resolvers} in JpaConfig.
 */
public class NyehmsDialectResolver implements DialectResolver {

	@Override
	public Dialect resolveDialect(DialectResolutionInfo info) {
		String database = info.getDatabaseName();
		if ("Oracle".equalsIgnoreCase(database)) {
			return new NyehmsOracleDialect(info);
		}
		if ("H2".equalsIgnoreCase(database)) {
			return new NyehmsH2Dialect(info);
		}
		return null;
	}
}
