package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.OracleDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.spi.JdbcTypeRegistry;

/** Hibernate's Oracle dialect, with native query results typed as JDBC getObject types them ({@link GetObjectTypes}). */
public class NyehmsOracleDialect extends OracleDialect {

	public NyehmsOracleDialect() {
	}

	public NyehmsOracleDialect(DatabaseVersion version) {
		super(version);
	}

	public NyehmsOracleDialect(DialectResolutionInfo info) {
		super(info);
	}

	@Override
	public JdbcType resolveSqlTypeDescriptor(String columnTypeName, int jdbcTypeCode, int precision, int scale, JdbcTypeRegistry registry) {
		JdbcType type = GetObjectTypes.resolve(jdbcTypeCode);
		return type != null ? type : super.resolveSqlTypeDescriptor(columnTypeName, jdbcTypeCode, precision, scale, registry);
	}
}
