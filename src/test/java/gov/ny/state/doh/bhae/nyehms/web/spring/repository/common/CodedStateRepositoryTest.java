package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

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

/**
 * The state lookup (moved from CommonDAO.getStateValue to Spring Data JPA), against an in-memory H2 database in
 * Oracle mode (db/coded-state.sql).
 */
@SpringJUnitConfig(CodedStateRepositoryTest.TestConfig.class)
class CodedStateRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("codedstate;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/coded-state.sql")
					.build();
		}
	}

	@Autowired
	CodedStateRepository codedStateRepository;

	@Test
	void findsTheCodeOfAStateNameIgnoringCase() {
		assertThat(codedStateRepository.findStateCodeByName("NEW YORK")).isEqualTo("NY");    // as the former UPPER(...) = UPPER(...)
		assertThat(codedStateRepository.findStateCodeByName("new jersey")).isEqualTo("NJ");
		assertThat(codedStateRepository.findStateCodeByName("District of Columbia")).isEqualTo("DC");
	}

	@Test
	void anUnknownStateNameHasNoCode() {
		assertThat(codedStateRepository.findStateCodeByName("Albany")).isNull();
		assertThat(codedStateRepository.findStateCodeByName("New")).isNull();                // the whole name, not a part
	}
}
