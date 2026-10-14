package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.NycprfPartner;

/**
 * NYC PRF partner list (moved from MyBatis NycPRFPartnerDAO to Spring Data JPA), against an
 * in-memory H2 database in Oracle mode. The expected values are what the old MyBatis mapper
 * returned for the same data (compared side by side when the module was migrated).
 */
@SpringJUnitConfig(NycprfPartnerRepositoryTest.TestConfig.class)
class NycprfPartnerRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("nycprf;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/nycprf-partner.sql")
					.build();
		}

		@Bean
		JdbcTemplate jdbcTemplate(DataSource appDS) {
			return new JdbcTemplate(appDS);
		}
	}

	@Autowired
	NycprfPartnerRepository repository;

	@Autowired
	PlatformTransactionManager transactionManager;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void returnsEachStandardizedPartnerOfTheDocumentOnce() {
		List<NycprfPartner> partners = repository.findPartnersOfDocument(100);

		// P1 was received twice with the same address (DISTINCT); P4 has no standardized row
		assertThat(partners).extracting(NycprfPartner::PATIENT_ID).containsExactlyInAnyOrder("P1", "P2");
	}

	@Test
	void convertsValuesLikeTheMyBatisMapper() {
		NycprfPartner p1 = partner(100, "P1");

		assertThat(p1.FNAME()).isEqualTo("Ann");
		assertThat(p1.STD_ADDRESS()).isEqualTo("1 MAIN ST");     // std.ADDRESS as STD_ADDRESS
		assertThat(p1.raw_address()).isEqualTo("1 Main St");     // raw1.PRTADDRESS as raw_address
		assertThat(p1.AGE()).isEqualTo("34");                    // NUMBER column read as text
		assertThat(p1.PARTNER_TEST_DATE_TEXT()).isEqualTo("01/02/2023");
		assertThat(p1.HDISP_DT()).isEqualTo(Date.valueOf("2023-05-06")); // DATE read as date only
		assertThat(p1.CREATE_DATE()).isEqualTo(Date.valueOf("2023-05-01"));
		assertThat(p1.INFORMED_DATE()).isNull();

		NycprfPartner p2 = partner(100, "P2");
		assertThat(p2.raw_address()).isNull();
		assertThat(p2.AGE()).isNull();
		assertThat(partner(200, "P3").AGE()).isEqualTo("0");
	}

	@Test
	void returnsAnEmptyListForADocumentWithoutPartners() {
		assertThat(repository.findPartnersOfDocument(999)).isEmpty();
	}

	@Test
	void sharesTheTransactionWithJdbcCode() {
		// MyBatis and the raw JDBC code reach appDS the same way as JdbcTemplate (DataSourceUtils)
		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER VALUES ('T1', 300, 'tx')");
			jdbcTemplate.update("INSERT INTO STD_NYCPRF_PARTNER (PATIENT_ID, NYCPRF_DOCUMENT_ID) VALUES ('T1', '300')");

			assertThat(repository.findPartnersOfDocument(300)).hasSize(1);   // sees the uncommitted rows
			status.setRollbackOnly();
		});

		assertThat(repository.findPartnersOfDocument(300)).isEmpty();        // rolled back
	}

	private NycprfPartner partner(int documentId, String patientId) {
		return repository.findPartnersOfDocument(documentId).stream()
				.filter(p -> patientId.equals(p.PATIENT_ID()))
				.findFirst()
				.orElseThrow();
	}
}
