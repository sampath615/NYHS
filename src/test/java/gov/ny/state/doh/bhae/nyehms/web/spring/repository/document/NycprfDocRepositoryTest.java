package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfDoc;
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

		@Bean
		JdbcTemplate jdbcTemplate(DataSource appDS) {
			return new JdbcTemplate(appDS);
		}
	}

	@Autowired
	NycprfDocRepository repository;

	@Autowired
	PlatformTransactionManager transactionManager;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void savesANewStandardizedDocument() {
		StdNycprfDoc doc = StdNycprfDoc.builder()
				.documentId(900)
				.prfSrcId(7L)
				.fname("ANN")
				.dob(Timestamp.valueOf("1980-05-06 13:45:00"))
				.fieldProcDate(Timestamp.valueOf("2023-01-02 00:00:00"))
				.printedDateValue(Timestamp.valueOf("2023-02-03 04:05:06"))
				.currentProcessStatus("5")
				.build();

		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			repository.saveAndFlush(doc);

			Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM STD_NYCPRF_DOC WHERE DOCUMENT_ID = 900");
			assertThat(row).containsEntry("PRF_SRC_ID", new BigDecimal("7"))
					.containsEntry("FNAME", "ANN")
					.containsEntry("LNAME", null)
					.containsEntry("DOB", Timestamp.valueOf("1980-05-06 13:45:00"))        // keeps the time
					.containsEntry("FIELD_PROC_DATE", Timestamp.valueOf("2023-01-02 00:00:00"))
					.containsEntry("PRINTED_DATE", Timestamp.valueOf("2023-02-03 04:05:06"))
					.containsEntry("CURRENT_PROCESS_STATUS", new BigDecimal("5"));
			status.setRollbackOnly();
		});
	}

	@Test
	void readsTheDocumentWithItsCurrentMatch() {
		NycPrfDocData doc = repository.findDocument(100);

		assertThat(doc.stdNycprfDoc().getDocumentId()).isEqualTo(100);
		assertThat(doc.stdNycprfDoc().getPrfSrcId()).isEqualTo(7L);
		assertThat(doc.stdNycprfDoc().getFname()).isEqualTo("FNAME");                      // standardized text column
		assertThat(doc.rawPersonAddress()).isEqualTo("12 Raw St");       // RAW_NYCPRF_DOC.ADDRESS
		assertThat(doc.stateno()).isEqualTo("1234");                     // only the current association
		assertThat(doc.discardStatus()).isEqualTo("Y");
		assertThat(doc.partnerList()).isEmpty();                         // partners are added by the service
	}

	@Test
	void readsDatesWithTheirTime() {
		NycPrfDocData doc = repository.findDocument(100);

		assertThat(doc.stdNycprfDoc().getDob()).isEqualTo(Timestamp.valueOf("1980-05-06 13:45:00"));   // the JSP shows MM/dd/yyyy
		assertThat(doc.stdNycprfDoc().getFieldProcDate()).isEqualTo(Timestamp.valueOf("2023-01-02 00:00:00"));
		assertThat(doc.stdNycprfDoc().getAdmissionDate()).isEqualTo(Timestamp.valueOf("2023-03-04 10:11:12"));
		assertThat(doc.stdNycprfDoc().getCreateDate()).isEqualTo(Timestamp.valueOf("2023-03-05 08:00:00"));
		assertThat(doc.stdNycprfDoc().getReviewDate()).isNull();
	}

	@Test
	void documentWithoutMatchOrDates() {
		NycPrfDocData doc = repository.findDocument(200);

		assertThat(doc.stateno()).isNull();
		assertThat(doc.rawPersonAddress()).isNull();
		assertThat(doc.stdNycprfDoc().getDob()).isNull();
		assertThat(doc.stdNycprfDoc().getFieldProcDate()).isNull();
	}

	@Test
	void returnsNullForAnUnknownDocument() {
		assertThat(repository.findDocument(300)).isNull();
	}
}
