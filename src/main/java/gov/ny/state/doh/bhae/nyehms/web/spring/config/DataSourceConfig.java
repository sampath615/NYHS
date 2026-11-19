package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Data sources and transaction management.
 * <p>
 * Replaces spring-datasource.xml and the transactionManager and tx:annotation-driven
 * entries of spring-config.xml. The transaction manager itself is the JPA one
 * (JpaConfig); plain JDBC connections come from JpaConnections.
 * <p>
 * The three pools are container managed: Tomcat defines them in
 * META-INF/context.xml and the connection settings come from catalina.properties
 * (see docs/TOMCAT_MIGRATION.md). The names are looked up under java:comp/env/.
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /*
     * destroyMethod = "" on the three data sources: the pools belong to Tomcat, so
     * Spring must not call close() on them when the application context shuts down
     * (without it Spring infers and calls the pool's close() method).
     */

    /**
     * Oracle: main NYEHMS database (was WebLogic data source BHAE_NYEHMS_DS_ONE_GL).
     * Used by the main JPA unit (JpaConfig) and the plain JDBC connections of
     * JpaConnections.
     */
    @Bean(destroyMethod = "")
    @Primary
    public DataSource appDS() {
        return jndiDataSource("jdbc/BHAE_NYEHMS_DS_ONE_GL");
    }

    /**
     * URS database (was WebLogic data source BHAE_NYEHMS_URS); read by the JdbcTemplate of AIDADataDAO.
     */
    @Bean(destroyMethod = "")
    public DataSource ursDS() {
        return jndiDataSource("jdbc/BHAE_NYEHMS_URS");
    }

    /**
     * eHARS database, SQL Server (was WebLogic data source BHAE_EHARS_DS_ONE); used by
     * the eHARS JPA unit (EharsJpaConfig) and JpaConnections.eharsConnection().
     */
    @Bean(destroyMethod = "")
    public DataSource eharsDS() {
        return jndiDataSource("jdbc/BHAE_EHARS_DS_ONE");
    }

    /*
     * Same as <jee:jndi-lookup resource-ref="true" expected-type="javax.sql.DataSource">:
     * JndiDataSourceLookup prefixes java:comp/env/ and fails at startup if the name is missing.
     */
    private static DataSource jndiDataSource(String jndiName) {
        return new JndiDataSourceLookup().getDataSource(jndiName);
    }
}
