package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.impl.JdbcConnectionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Data sources, transaction management and the raw JDBC connection DAO.
 * <p>
 * Replaces spring-datasource.xml and the transactionManager, tx:annotation-driven
 * and connectionDAO entries of spring-config.xml.
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
     * Used by the MyBatis session factories, the transaction manager and raw JDBC
     * (ConnectionDAO).
     */
    @Bean(destroyMethod = "")
    public DataSource appDS() {
        return jndiDataSource("jdbc/BHAE_NYEHMS_DS_ONE_GL");
    }

    /**
     * Oracle: URS database (was WebLogic data source BHAE_NYEHMS_URS); ursSessionFactory.
     */
    @Bean(destroyMethod = "")
    public DataSource ursDS() {
        return jndiDataSource("jdbc/BHAE_NYEHMS_URS");
    }

    /**
     * eHARS database (was WebLogic data source BHAE_EHARS_DS_ONE); raw JDBC via
     * ConnectionDAO.getSqlServerConnectionObj().
     */
    @Bean(destroyMethod = "")
    public DataSource eharsDS() {
        return jndiDataSource("jdbc/BHAE_EHARS_DS_ONE");
    }

    // The transaction manager (a JpaTransactionManager on appDS) is defined in JpaConfig.

    @Bean
    public JdbcConnectionDAO connectionDAO(@Qualifier("appDS") DataSource appDS,
                                           @Qualifier("eharsDS") DataSource eharsDS) {
        JdbcConnectionDAO connectionDAO = new JdbcConnectionDAO();
        connectionDAO.setDataSource(appDS);
        connectionDAO.setSqlDataSource(eharsDS);
        return connectionDAO;
    }

    /*
     * Same as <jee:jndi-lookup resource-ref="true" expected-type="javax.sql.DataSource">:
     * JndiDataSourceLookup prefixes java:comp/env/ and fails at startup if the name is missing.
     */
    private static DataSource jndiDataSource(String jndiName) {
        return new JndiDataSourceLookup().getDataSource(jndiName);
    }
}

