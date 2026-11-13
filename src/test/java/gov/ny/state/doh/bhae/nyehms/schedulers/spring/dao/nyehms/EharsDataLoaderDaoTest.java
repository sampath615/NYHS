package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/** The eHARS loader DAO: the RIDR delete against an in-memory H2 database in Oracle mode (db/ehars-loader.sql). */
@SpringJUnitConfig(EharsDataLoaderDaoTest.TestConfig.class)
class EharsDataLoaderDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, EharsDataLoaderDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("eharsloaderdao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/ehars-loader.sql").build();
		}
	}

	@Autowired EharsDataLoaderDAO dao;
	@Autowired NativeStatements statements;

	@Test
	void ridrRecordsAreDeleted() {
		assertThat(statements.one("select count(*) from EHARS_RIDR", Rows.scalar(Long.class))).isEqualTo(2L);
		dao.deleteRIDREharsData();
		assertThat(statements.one("select count(*) from EHARS_RIDR", Rows.scalar(Long.class))).isZero();
		dao.deleteRIDREharsData();                                                       // nothing left: no error
	}
}
