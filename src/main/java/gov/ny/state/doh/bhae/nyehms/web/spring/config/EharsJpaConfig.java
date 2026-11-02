package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;

/**
 * The second JPA persistence unit: the eHARS database (SQL Server, {@code eharsDS}), read by NYEHMS.
 * <p>
 * Its entities and repositories are in {@code nyehms.ehars}, outside the package {@link JpaConfig} scans. The unit does
 * not touch the database at startup (no metadata access, the dialect is given), so an eHARS outage only affects the
 * eHARS data, as it did with plain JDBC, and does not stop the application from starting.
 */
@Configuration
@EnableJpaRepositories(basePackages = EharsJpaConfig.BASE_PACKAGE+ ".repository",
		entityManagerFactoryRef = "eharsEntityManagerFactory", transactionManagerRef = EharsJpaConfig.TRANSACTION_MANAGER)
public class EharsJpaConfig {

	/** The transaction manager of the eHARS unit; name it in {@code @Transactional} for code that needs a transaction there. */
	public static final String TRANSACTION_MANAGER = "eharsTransactionManager";

	/** The eHARS unit: its entities anywhere under this package, its repositories in {@code .repository} (appDS has {@link JpaConfig#BASE_PACKAGE}). */
	static final String BASE_PACKAGE = "gov.ny.state.doh.bhae.nyehms.ehars";

	@Bean
	public LocalContainerEntityManagerFactoryBean eharsEntityManagerFactory(@Qualifier("eharsDS") DataSource eharsDS,
			@Value("${ehars.hibernate.dialect:org.hibernate.dialect.SQLServerDialect}") String dialect) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("ehars");
		factory.setDataSource(eharsDS);
		factory.setPackagesToScan(BASE_PACKAGE);
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaPropertyMap(Map.of(
				// never create, change or validate the schema: the eHARS database is not ours
				"hibernate.hbm2ddl.auto", "none",
				"hibernate.show_sql", "false",
				"hibernate.dialect", dialect,
				"hibernate.boot.allow_jdbc_metadata_access", "false"));
		return factory;
	}

	@Bean(name = TRANSACTION_MANAGER)
	public JpaTransactionManager eharsTransactionManager(
			@Qualifier("eharsEntityManagerFactory") EntityManagerFactory eharsEntityManagerFactory) {
		return new JpaTransactionManager(eharsEntityManagerFactory);
	}
}
