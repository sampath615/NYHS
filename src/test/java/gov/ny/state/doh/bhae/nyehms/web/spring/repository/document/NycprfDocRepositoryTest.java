package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfDocData;

/**
 * NYC PRF document (moved from MyBatis NycPRFDocumentDAO.getNycPRFDoc to Spring Data JPA), against
 * an in-memory H2 database in Oracle mode. The expected values are what the old MyBatis mapper
 * returned for the same data (compared side by side when the module was migrated).
 */
@SpringJUnitConfig(NycprfDocRepositoryTest.TestConfig.class)
class NycprfDocRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("nycprfdoc;MODE=Oracle")
					.generateUniqueName(false)
					.addScripts("db/nycprf-doc.sql", "db/nycprf-doc-data.sql")
					.build();
		}
	}

	@Autowired
	NycprfDocRepository repository;

	@Test
	void readsTheDocumentWithItsCurrentMatch() {
		NycPrfDocData doc = repository.findDocument(100);

		assertThat(doc.DOCUMENT_ID()).isEqualTo(100);
		assertThat(doc.PRF_SRC_ID()).isEqualTo(7L);
		assertThat(doc.FNAME()).isEqualTo("FNAME");                      // standardized text column
		assertThat(doc.rawPersonAddress()).isEqualTo("12 Raw St");       // RAW_NYCPRF_DOC.ADDRESS
		assertThat(doc.STATENO()).isEqualTo("1234");                     // only the current association
		assertThat(doc.DISCARD_STATUS()).isEqualTo("Y");
		assertThat(doc.partnerList()).isEmpty();                         // partners are added by the service
	}

	@Test
	void formatsAndReadsDatesLikeTheMyBatisMapper() {
		NycPrfDocData doc = repository.findDocument(100);

		assertThat(doc.DOB()).isEqualTo("05/06/1980");                   // TO_CHAR(DOB, 'MM/DD/YYYY')
		assertThat(doc.FIELD_PROC_DATE()).isEqualTo("01/02/2023");
		assertThat(doc.ADMISSION_DATE()).isEqualTo(Date.valueOf("2023-03-04")); // DATE read as date only
		assertThat(doc.CREATE_DATE()).isEqualTo(Date.valueOf("2023-03-05"));
		assertThat(doc.REVIEW_DATE()).isNull();
	}

	@Test
	void documentWithoutMatchOrDates() {
		NycPrfDocData doc = repository.findDocument(200);

		assertThat(doc.STATENO()).isNull();
		assertThat(doc.rawPersonAddress()).isNull();
		assertThat(doc.DOB()).isNull();
		assertThat(doc.FIELD_PROC_DATE()).isNull();
	}

	@Test
	void returnsNullForAnUnknownDocument() {
		assertThat(repository.findDocument(300)).isNull();
	}
}
