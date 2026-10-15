package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.record.OldCaseDocData;

/**
 * Old Cases document (moved from MyBatis OldCasesDocumentDAO.getOldCaseDocVO to Spring Data JPA),
 * against an in-memory H2 database in Oracle mode. The expected values are what the old MyBatis
 * mapper returned for the same data (compared side by side when the module was migrated).
 */
@SpringJUnitConfig(OldCasesDocRepositoryTest.TestConfig.class)
class OldCasesDocRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("oldcases;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/old-cases-doc.sql")
					.build();
		}
	}

	@Autowired
	OldCasesDocRepository repository;

	@Test
	void readsTheReportWithItsCurrentMatch() {
		OldCaseDocData doc = repository.findDocument(100);

		assertThat(doc.stdOldCasesDoc().getDocumentId()).isEqualTo(100);
		assertThat(doc.stdOldCasesDoc().getOldcaseSrcId()).isEqualTo("7");       // NUMBER read as text
		assertThat(doc.stdOldCasesDoc().getFname()).isEqualTo("ANN");
		assertThat(doc.stdOldCasesDoc().getRaceWhite()).isEqualTo("1");
		assertThat(doc.stdOldCasesDoc().getComments()).isEqualTo("old case");
		assertThat(doc.matchedStateno()).isEqualTo("1234");                       // only the current association
		assertThat(doc.discardStatus()).isEqualTo("Y");
	}

	@Test
	void readsDatesAsTextLikeTheMyBatisMapper() {
		OldCaseDocData doc = repository.findDocument(100);

		assertThat(doc.stdOldCasesDoc().getDateOfBirth()).startsWith("1980-05-06");
		assertThat(doc.stdOldCasesDoc().getCreateDate()).startsWith("2023-03-05 08:09:10");
		assertThat(doc.stdOldCasesDoc().getDateOfDeath()).isNull();
	}

	@Test
	void reportWithoutMatch() {
		OldCaseDocData doc = repository.findDocument(200);

		assertThat(doc.stdOldCasesDoc().getFname()).isNull();
		assertThat(doc.matchedStateno()).isNull();
		assertThat(doc.discardStatus()).isNull();
	}

	@Test
	void returnsNullForAnUnknownReport() {
		assertThat(repository.findDocument(300)).isNull();
	}
}
