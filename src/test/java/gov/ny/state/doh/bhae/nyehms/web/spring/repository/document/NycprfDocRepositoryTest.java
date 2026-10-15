package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
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
	void insertsTheStandardizedDocumentLikeTheMyBatisMapper() {
		Map<Object, Object> stdDocData = new HashMap<>();
		stdDocData.put("DOCUMENT_ID", new BigDecimal("900"));                          // as read from the raw table
		stdDocData.put("PRF_SRC_ID", " 7 ");
		stdDocData.put("FNAME", " ann ");
		stdDocData.put("DOB", Timestamp.valueOf("1980-05-06 13:45:00"));
		stdDocData.put("ADMISSION_DATE", " 3/4/2023 ");
		stdDocData.put("SIG_DATE", "not a date");
		stdDocData.put("CURRENT_PROCESS_STATUS", 5);                                   // int constant into trim(...)
		stdDocData.put("FIELD_PROC_DATE", "2023-01-02");                              // text into a DATE column
		stdDocData.put("FLUX_MATCH_CD", " kept as is ");

		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			assertThat(repository.insertStdDocument(stdDocData)).isEqualTo(1);

			Map<String, Object> row = jdbcTemplate.queryForMap("SELECT * FROM STD_NYCPRF_DOC WHERE DOCUMENT_ID = 900");
			assertThat(row).containsEntry("PRF_SRC_ID", new BigDecimal("7"))
					.containsEntry("FNAME", "ANN")                                         // upper(trim(...))
					.containsEntry("LNAME", null)                                          // not in the map
					.containsEntry("DOB", Timestamp.valueOf("1980-05-06 13:45:00"))        // keeps the time
					.containsEntry("ADMISSION_DATE", Timestamp.valueOf("2023-03-04 00:00:00"))  // FN_TO_DATE
					.containsEntry("SIG_DATE", null)
					.containsEntry("CURRENT_PROCESS_STATUS", new BigDecimal("5"))
					.containsEntry("FIELD_PROC_DATE", Timestamp.valueOf("2023-01-02 00:00:00"))
					.containsEntry("FLUX_MATCH_CD", " kept as is ");
			status.setRollbackOnly();
		});
	}

	@Test
	void refusesADateForATextColumn() {
		Map<Object, Object> stdDocData = new HashMap<>();
		stdDocData.put("DOCUMENT_ID", new BigDecimal("901"));
		stdDocData.put("DOD_TEXT", Timestamp.valueOf("2023-03-04 00:00:00"));   // Oracle would have used its NLS date format

		assertThatThrownBy(() -> repository.insertStdDocument(stdDocData)).hasMessageContaining("DOD_TEXT expects text but got a date");
	}

	@Test
	void writesNumbersAsOracleText() {
		assertThat(NycprfDocWriterImpl.oracleText(5)).isEqualTo("5");
		assertThat(NycprfDocWriterImpl.oracleText(new BigDecimal("12.50"))).isEqualTo("12.5");
		assertThat(NycprfDocWriterImpl.oracleText(new BigDecimal("0.50"))).isEqualTo(".5");
		assertThat(NycprfDocWriterImpl.oracleText(new BigDecimal("-0.25"))).isEqualTo("-.25");
		assertThat(NycprfDocWriterImpl.oracleText(new BigDecimal("1E+3"))).isEqualTo("1000");
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
	void readsDatesAsDateOnly() {
		NycPrfDocData doc = repository.findDocument(100);

		assertThat(doc.stdNycprfDoc().getDob()).isEqualTo(Date.valueOf("1980-05-06"));      // the JSP formats it as MM/dd/yyyy
		assertThat(doc.stdNycprfDoc().getFieldProcDate()).isEqualTo(Date.valueOf("2023-01-02"));
		assertThat(doc.stdNycprfDoc().getAdmissionDate()).isEqualTo(Date.valueOf("2023-03-04")); // DATE read as date only
		assertThat(doc.stdNycprfDoc().getCreateDate()).isEqualTo(Date.valueOf("2023-03-05"));
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
