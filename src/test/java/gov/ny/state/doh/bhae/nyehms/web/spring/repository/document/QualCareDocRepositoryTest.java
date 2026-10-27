package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdQualityCareDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareRefCode;

/**
 * The quality of care records (moved from QualCareDocumentDAO to Spring Data JPA), against an in-memory H2 database
 * in Oracle mode (db/qual-care.sql).
 */
@SpringJUnitConfig(QualCareDocRepositoryTest.TestConfig.class)
class QualCareDocRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("qualcare;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/qual-care.sql")
					.build();
		}
	}

	@Autowired
	QualCareDocRepository qualCareDocRepository;

	@Test
	void readsARecordWithTheNamesOfItsCodesAndItsCurrentMatch() {
		QualCareDocData document = qualCareDocRepository.findDocument(100);

		StdQualityCareDoc std = document.stdQualityCareDoc();
		assertThat(std.getDocumentId()).isEqualTo(100);
		assertThat(std.getRecordId()).isEqualTo(7);
		assertThat(std.getFname()).isEqualTo("JANE");
		assertThat(std.getDobText()).isEqualTo("01/02/1980");
		assertThat(std.getAge()).isEqualTo(41);
		assertThat(std.getHivCareDate()).startsWith("2021-04-05");        // the database's text of the date, as before
		assertThat(std.getAsianSubGroup()).isEqualTo("10, 01,02,99,01");   // the codes; the service adds the names
		assertThat(document.rawfacilityaddress()).isEqualTo("1 main st");
		assertThat(document.housingStatus()).isEqualTo("Unstable");
		assertThat(document.insurance()).isEqualTo("Medicaid");
		assertThat(document.enrollment()).isEqualTo("Enrolled");
		assertThat(document.diagnosis()).isEqualTo("HIV");
		assertThat(document.diagnosisDate()).isEqualTo("03/04/2021");
		assertThat(document.dischargeDate()).isEqualTo("03/05/2021");
		assertThat(document.stateno()).isEqualTo("0022222");              // not the dissociated match
		assertThat(document.asianSubGroup()).isNull();
		assertThat(document.facilityState()).isNull();
	}

	@Test
	void aCodeWithoutANameAndARecordWithoutAMatchReadAsNull() {
		QualCareDocData document = qualCareDocRepository.findDocument(200);

		assertThat(document.housingStatus()).isNull();
		assertThat(document.rawfacilityaddress()).isNull();
		assertThat(document.diagnosisDate()).isNull();
		assertThat(document.stateno()).isNull();
	}

	@Test
	void noRecordWithoutADocumentLocatorOrStandardizedRow() {
		assertThat(qualCareDocRepository.findDocument(300)).isNull();      // no DOCUMENT_LOCATOR row
		assertThat(qualCareDocRepository.findDocument(999)).isNull();
	}

	@Test
	void readsTheSubGroupCodesOnly() {
		assertThat(qualCareDocRepository.findSubGroupCodes()).containsExactlyInAnyOrder(
				new QualCareRefCode("ASIAN_SUB_GROUP", "01", "Chinese"),
				new QualCareRefCode("ASIAN_SUB_GROUP", "02", "Indian"),
				new QualCareRefCode("ASIAN_SUB_GROUP", "10", "Other Asian"),
				new QualCareRefCode("HISPANIC_SUB_GROUP", "01", "Mexican"),
				new QualCareRefCode("HISPANIC_SUB_GROUP", "03", "Cuban"));
	}

	@Test
	void savesAStandardizedRecord() {
		qualCareDocRepository.saveAndFlush(StdQualityCareDoc.builder().documentId(500).fname("ANN").currentProcessStatus(5)
				.hivCareDateValue(Timestamp.valueOf("2022-01-02 03:04:05")).build());

		StdQualityCareDoc saved = qualCareDocRepository.findById(500).orElseThrow();
		assertThat(saved.getFname()).isEqualTo("ANN");
		assertThat(saved.getCurrentProcessStatus()).isEqualTo(5);
		assertThat(saved.getHivCareDateValue()).isEqualTo(Timestamp.valueOf("2022-01-02 03:04:05"));
	}
}
