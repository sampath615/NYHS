package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.spi.JdbcTypeRegistry;

/**
 * Hibernate's H2 dialect (the tests' in-memory databases), with native query results typed as JDBC getObject types
 * them, as on Oracle ({@link GetObjectTypes}).
 */
public class NyehmsH2Dialect extends H2Dialect {

	public NyehmsH2Dialect() {
	}

	public NyehmsH2Dialect(DialectResolutionInfo info) {
		super(info);
	}

	@Override
	public JdbcType resolveSqlTypeDescriptor(String columnTypeName, int jdbcTypeCode, int precision, int scale, JdbcTypeRegistry registry) {
		JdbcType type = GetObjectTypes.resolve(jdbcTypeCode);
		return type != null ? type : super.resolveSqlTypeDescriptor(columnTypeName, jdbcTypeCode, precision, scale, registry);
	}
}
