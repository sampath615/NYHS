package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.DateJdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.NumericJdbcType;
import org.hibernate.type.descriptor.jdbc.TimeJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.type.spi.TypeConfiguration;

/**
 * How the dialects of the main persistence unit type the columns of a native query result, so that the values are
 * what plain JDBC {@code getObject} returns, as the former MyBatis map and bean results had them:
 * <ul>
 * <li>any NUMBER a {@code BigDecimal} (Hibernate's Oracle dialect would read NUMBER(p,0) as Integer or Long, a
 * float-like NUMBER as Float or Double);</li>
 * <li>text always a {@code String} (Hibernate would read a one-character column as a Character), a CLOB too (MyBatis
 * read CLOBs of map and text results with its ClobTypeHandler);</li>
 * <li>a DATE / TIMESTAMP a {@code java.sql.Timestamp}, a DATE reported as such a {@code java.sql.Date} (Hibernate 7
 * would use java.time types).</li>
 * </ul>
 * This only affects values Hibernate types from the result set metadata (native queries without declared column
 * types); entity attributes and declared scalars keep their own types.
 */
public final class GetObjectTypes {

	private static final JdbcType NUMBER = new NumericJdbcType() {
		@Override
		public JavaType<?> getRecommendedJavaType(Integer precision, Integer scale, TypeConfiguration typeConfiguration) {
			return typeConfiguration.getJavaTypeRegistry().resolveDescriptor(BigDecimal.class);
		}
	};

	private static final JdbcType TEXT = new VarcharJdbcType() {
		@Override
		public JavaType<?> getRecommendedJavaType(Integer length, Integer scale, TypeConfiguration typeConfiguration) {
			return typeConfiguration.getJavaTypeRegistry().resolveDescriptor(String.class);
		}
	};

	private static final JdbcType TIMESTAMP = new TimestampJdbcType() {
		@Override
		public JavaType<?> getRecommendedJavaType(Integer length, Integer scale, TypeConfiguration typeConfiguration) {
			return typeConfiguration.getJavaTypeRegistry().resolveDescriptor(Timestamp.class);
		}
	};

	private static final JdbcType DATE = new DateJdbcType() {
		@Override
		public JavaType<?> getRecommendedJavaType(Integer length, Integer scale, TypeConfiguration typeConfiguration) {
			return typeConfiguration.getJavaTypeRegistry().resolveDescriptor(java.sql.Date.class);
		}
	};

	private static final JdbcType TIME = new TimeJdbcType() {
		@Override
		public JavaType<?> getRecommendedJavaType(Integer length, Integer scale, TypeConfiguration typeConfiguration) {
			return typeConfiguration.getJavaTypeRegistry().resolveDescriptor(Time.class);
		}
	};

	private GetObjectTypes() {
	}

	/**
	 * The JDBC type for a result column, or null to let the dialect decide.
	 *
	 * @param jdbcTypeCode the column's {@link Types} code from the result set metadata
	 */
	static JdbcType resolve(int jdbcTypeCode) {
		return switch (jdbcTypeCode) {
			case Types.NUMERIC, Types.DECIMAL -> NUMBER;
			case Types.CHAR, Types.VARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB -> TEXT;
			case Types.TIMESTAMP -> TIMESTAMP;
			case Types.DATE -> DATE;
			case Types.TIME -> TIME;
			default -> null;
		};
	}
}
