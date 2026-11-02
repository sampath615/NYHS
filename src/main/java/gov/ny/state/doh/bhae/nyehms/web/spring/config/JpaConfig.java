package gov.ny.state.doh.bhae.nyehms.web.spring.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;

/**
 * JPA (Hibernate) on the main NYEHMS database, alongside MyBatis while modules move over one by
 * one (see docs/JPA_MIGRATION.md).
 * <p>
 * Defined explicitly because the application has three data sources, so Spring Boot does not
 * auto-configure JPA. Entities and repositories are found anywhere under {@code web.spring}: in
 * {@code web.spring.entity} / {@code web.spring.repository} and in per-module packages such as
 * {@code web.spring.initialize.entity} / {@code web.spring.initialize.repository}. (Only interfaces
 * extending a Spring Data {@code Repository} and {@code @Entity} classes are picked up, so the
 * MyBatis DAOs and the VOs are not affected.)
 */
@Configuration
@EnableJpaRepositories(basePackages = JpaConfig.BASE_PACKAGE,
		entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class JpaConfig {

	/** Where the JPA entities and the Spring Data repositories are looked for. */
	static final String BASE_PACKAGE = "gov.ny.state.doh.bhae.nyehms.web.spring";

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("appDS") DataSource appDS) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("nyehms");
		factory.setDataSource(appDS);
		factory.setPackagesToScan(BASE_PACKAGE);
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaPropertyMap(Map.of(
				// never create, change or validate the schema: the tables are owned by the database team
				"hibernate.hbm2ddl.auto", "none",
				"hibernate.show_sql", "false"));
		return factory;
	}

	/**
	 * The only transaction manager: {@code @Transactional} means the main NYEHMS database (appDS), as
	 * before. It also exposes its JDBC connection to MyBatis and plain JDBC code on appDS
	 * ({@code DataSourceUtils}), so MyBatis and JPA calls inside one {@code @Transactional} method
	 * share one connection and one transaction.
	 */
	@Bean
	@Primary
	public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
