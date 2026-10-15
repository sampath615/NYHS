package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycprfPartner;
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
		assertThat(p1.rawAddress()).isEqualTo("1 Main St");     // raw1.PRTADDRESS as raw_address
		assertThat(p1.stdNycprfPartner().getAge()).isEqualTo("34");                    // NUMBER column read as text
		assertThat(p1.stdNycprfPartner().getPartnerTestDateText()).isEqualTo("01/02/2023");
		assertThat(p1.stdNycprfPartner().getHdispDate()).isEqualTo(Date.valueOf("2023-05-06")); // DATE read as date only
		assertThat(p1.stdNycprfPartner().getCreateDate()).isEqualTo(Timestamp.valueOf("2023-05-01 08:00:00"));   // with its time
		assertThat(p1.stdNycprfPartner().getInformedDate()).isNull();

        NycPrfPartnerData p2 = partner(100, "P2");
		assertThat(p2.rawAddress()).isNull();
		assertThat(p2.stdNycprfPartner().getAge()).isNull();
		assertThat(partner(200, "P3").stdNycprfPartner().getAge()).isEqualTo("0");
	}

	@Test
	void keepsEveryReceivedAddressOfAPartner() {
		jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTADDRESS) VALUES ('R6', 'P5', 500, '5 Oak St')");
		jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTADDRESS) VALUES ('R7', 'P5', 500, '7 Pine St')");
		jdbcTemplate.update("INSERT INTO STD_NYCPRF_PARTNER (PARTNRID, PATIENT_ID, NYCPRF_DOCUMENT_ID, FNAME) VALUES ('S4', 'P5', '500', 'Eve')");

		// the old MyBatis query returned one row per received address
		assertThat(repository.findPartnersOfDocument(500)).extracting(NycPrfPartnerData::rawAddress)
				.containsExactlyInAnyOrder("5 Oak St", "7 Pine St");
	}

	@Test
	void returnsAnEmptyListForADocumentWithoutPartners() {
		assertThat(repository.findPartnersOfDocument(999)).isEmpty();
	}

	@Test
	void sharesTheTransactionWithJdbcCode() {
		// MyBatis and the raw JDBC code reach appDS the same way as JdbcTemplate (DataSourceUtils)
		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTADDRESS) VALUES ('R8', 'T1', 300, 'tx')");
			jdbcTemplate.update("INSERT INTO STD_NYCPRF_PARTNER (PARTNRID, PATIENT_ID, NYCPRF_DOCUMENT_ID) VALUES ('S5', 'T1', '300')");

			assertThat(repository.findPartnersOfDocument(300)).hasSize(1);   // sees the uncommitted rows
			status.setRollbackOnly();
		});

		assertThat(repository.findPartnersOfDocument(300)).isEmpty();        // rolled back
	}

	@Test
	void readsTheReceivedPartnersAndSavesNewStandardizedOnes() {
		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTLNAME, PRTRACE) VALUES ('R9', ' w1 ', 700, ' lee ', ' white ')");
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID) VALUES ('R10', 'w2', 700)");
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID) VALUES ('R11', 'other', 701)");

			List<RawNycprfPartner> received = repository.findReceivedPartnersOfDocument(700);
			assertThat(received).extracting(RawNycprfPartner::getPatientId).containsExactlyInAnyOrder(" w1 ", "w2");
			assertThat(received).extracting(RawNycprfPartner::getPrtrace).contains(" white ");   // as received

			repository.saveAllAndFlush(List.of(StdNycprfPartner.builder()
					.partnrid("S700").patientId("w1").nycprfDocumentId("700").lastName("LEE").age("34")
					.createDate(Timestamp.valueOf("2024-07-08 09:10:11"))
					.build()));

			Map<String, Object> w1 = jdbcTemplate.queryForMap("SELECT * FROM STD_NYCPRF_PARTNER WHERE PATIENT_ID = 'w1'");
			assertThat(w1).containsEntry("NYCPRF_DOCUMENT_ID", "700").containsEntry("LNAME", "LEE")
					.containsEntry("AGE", new BigDecimal("34"))                                  // text into a NUMBER column
					.containsEntry("CREATE_DATE", Timestamp.valueOf("2024-07-08 09:10:11"))     // keeps the time
					.containsEntry("FNAME", null);
			status.setRollbackOnly();
		});
	}

	@Test
	void loadsAPartnerReceivedTwiceAsTwoEntities() {
		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTLNAME) VALUES ('D1', 'd', 800, 'first')");
			jdbcTemplate.update("INSERT INTO RAW_NYCPRF_PARTNER (PARTNERID, PATIENTID, NYCPRF_DOCUMENT_ID, PRTLNAME) VALUES ('D2', 'd', 800, 'second')");

			// keyed by PARTNERID, so both rows load (the same patient on one document)
			assertThat(repository.findReceivedPartnersOfDocument(800)).extracting(RawNycprfPartner::getPrtlname)
					.containsExactlyInAnyOrder("first", "second");
			status.setRollbackOnly();
		});
	}

	private NycPrfPartnerData partner(int documentId, String patientId) {
		return repository.findPartnersOfDocument(documentId).stream()
				.filter(p -> patientId.equals(p.stdNycprfPartner().getPatientId()))
				.findFirst()
				.orElseThrow(null);
	}
}
