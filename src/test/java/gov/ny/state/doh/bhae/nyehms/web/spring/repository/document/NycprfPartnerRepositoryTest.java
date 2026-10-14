package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycPrfPartnerData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
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
		List<NycPrfPartnerData> partners = repository.findPartnersOfDocument(100);


		// P1 was received twice with the same address (DISTINCT); P4 has no standardized row
		assertThat(partners).extracting(p -> p.stdNycprfPartner().getPatientId()).containsExactlyInAnyOrder("P1", "P2");
	}

	@Test
	void convertsValuesLikeTheMyBatisMapper() {
        NycPrfPartnerData p1 = partner(100, "P1");

		assertThat(p1.stdNycprfPartner().getFirstName()).isEqualTo("Ann");
		assertThat(p1.stdNycprfPartner().getAddress()).isEqualTo("1 MAIN ST");     // std.ADDRESS as STD_ADDRESS
		assertThat(p1.rawNycprfPartner().getAddress()).isEqualTo("1 Main St");     // raw1.PRTADDRESS as raw_address
		assertThat(p1.stdNycprfPartner().getAge()).isEqualTo("34");                    // NUMBER column read as text
		assertThat(p1.stdNycprfPartner().getPartnerTestDateText()).isEqualTo("01/02/2023");
		assertThat(p1.stdNycprfPartner().getHdispDate()).isEqualTo(Date.valueOf("2023-05-06")); // DATE read as date only
		assertThat(p1.stdNycprfPartner().getCreateDate()).isEqualTo(Date.valueOf("2023-05-01"));
		assertThat(p1.stdNycprfPartner().getInformedDate()).isNull();

        NycPrfPartnerData p2 = partner(100, "P2");
		assertThat(p2.rawNycprfPartner().getAddress()).isNull();
		assertThat(p2.stdNycprfPartner().getAge()).isNull();
		assertThat(partner(200, "P3").stdNycprfPartner().getAge()).isEqualTo("0");
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

	private NycPrfPartnerData partner(int documentId, String patientId) {
		return repository.findPartnersOfDocument(documentId).stream()
				.filter(p -> patientId.equals(p.stdNycprfPartner().getPatientId()))
				.findFirst()
				.orElseThrow(null);
	}
}
