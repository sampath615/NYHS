package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import com.zaxxer.hikari.HikariDataSource;

import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.impl.JdbcConnectionDAO;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;

import org.springframework.core.env.Environment;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**

 * Data sources, transaction management and the raw JDBC connection DAO.
 * <p>
 * The three pools are application managed (HikariCP), built from
 * nyehms.db.* in application-{profile}.yml. Replaces the container-managed
 * JNDI pools from META-INF/context.xml + catalina.properties.
 */

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /** Oracle: main NYEHMS database (was BHAE_NYEHMS_DS_ONE_GL). */

    @Bean
    @Primary
    public DataSource appDS(Environment env) {

        HikariDataSource ds = pool(env, "app", "oracle.jdbc.OracleDriver", 2);
        ds.setConnectionInitSql("alter session set current_schema=" + env.getRequiredProperty("nyehms.db.app.schema"));

        return ds;

    }

    /** Oracle: URS database (was BHAE_NYEHMS_URS). */

    @Bean
    public DataSource ursDS(Environment env) {

        return pool(env, "urs", "oracle.jdbc.OracleDriver", 3);
    }

    /** SQL Server: eHARS database (was BHAE_EHARS_DS_ONE). */

    @Bean
    public DataSource eharsDS(Environment env) {
        return pool(env, "ehars", "com.microsoft.sqlserver.jdbc.SQLServerDriver", 20);
    }

    @Bean
    public JdbcConnectionDAO connectionDAO(@Qualifier("appDS") DataSource appDS,
                                           @Qualifier("eharsDS") DataSource eharsDS) {

        JdbcConnectionDAO connectionDAO = new JdbcConnectionDAO();
        connectionDAO.setDataSource(appDS);
        connectionDAO.setSqlDataSource(eharsDS);
        return connectionDAO;
    }

    private static HikariDataSource pool(Environment env, String name, String driver, int maxSize) {

        String p = "nyehms.db." + name + ".";
        HikariDataSource ds = new HikariDataSource();
        ds.setPoolName(name + "DS");
        ds.setDriverClassName(driver);
        ds.setJdbcUrl(env.getRequiredProperty(p + "url"));
        ds.setUsername(env.getRequiredProperty(p + "user"));
        ds.setPassword(env.getRequiredProperty(p + "password"));
        ds.setMaximumPoolSize(maxSize);
        ds.setConnectionTimeout(30_000);   // was maxWaitMillis
        return ds;

    }

}
