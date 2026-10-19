package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AcrfTestResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrTestData;

/**
 * The ICCR (Infection Control) report tables (moved from ICCRDocumentDAO to Spring Data JPA), against an
 * in-memory H2 database in Oracle mode (db/iccr.sql). The expected values are what the old MyBatis
 * statements returned and wrote for the same data (compared side by side when they were migrated).
 */
@SpringJUnitConfig(IccrRepositoriesTest.TestConfig.class)
class IccrRepositoriesTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("iccr;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/iccr.sql")
					.build();
		}
	}

	@Autowired
	AcrfTestResultRepository acrfTestResultRepository;

	@Autowired
	IccrTestRepository iccrTestRepository;

	@Autowired
	RawIccrDocRepository rawIccrDocRepository;

	@Autowired
	IccrDocRepository iccrDocRepository;

	@Autowired
	DataSource dataSource;

	@Test
	void listsTheResultsATestTypeCanHaveNow() {
		assertThat(acrfTestResultRepository.findCurrentResultsOfTest("VL")).containsExactlyInAnyOrder(
				new AcrfTestResult("DET", "Detectable"),
				new AcrfTestResult("ND", "Not Detectable"));                       // not the ended OLD
		assertThat(acrfTestResultRepository.findCurrentResultsOfTest("CD4")).contains(new AcrfTestResult("LOW", null));
		assertThat(acrfTestResultRepository.findCurrentResultsOfTest("NONE")).isEmpty();
	}

	@Test
	void listsTheTestsOfAReportWithTheNamesOfTheirCodes() {
		assertThat(iccrTestRepository.findTestsOfDocument(100)).containsExactlyInAnyOrder(
				new IccrTestData("03/04/2023", "Viral Load", "Detectable", "200", "2.3"),
				new IccrTestData("03/04/2023", "Viral Load", "Detected", "200", "2.3"),   // DET is a result of CD4 too, as before
				new IccrTestData(null, null, null, null, null),                          // codes not in the code lists
				new IccrTestData("12/31/2022", "CD4 Count", null, null, null),
				new IccrTestData("12/31/2022", "CD4 Count", null, null, null));          // entered twice
		assertThat(iccrTestRepository.findTestsOfDocument(300)).isEmpty();
	}

	@Test
	@Transactional
	void addsTheSameTestTwice() {
		Timestamp date = Timestamp.valueOf("2023-05-06 00:00:00");

		assertThat(iccrTestRepository.insertTest(300, date, "VL", "ND", "", "1.5")).isEqualTo(1);
		assertThat(iccrTestRepository.insertTest(300, date, "VL", "ND", "", "1.5")).isEqualTo(1);

		assertThat(iccrTestRepository.findTestsOfDocument(300)).containsExactly(
				new IccrTestData("05/06/2023", "Viral Load", "Not Detectable", null, "1.5"),   // empty text is stored as null
				new IccrTestData("05/06/2023", "Viral Load", "Not Detectable", null, "1.5"));
	}

	@Test
	@Transactional
	void savesAnEnteredReport() {
		rawIccrDocRepository.saveAndFlush(RawIccrDoc.builder()
				.documentId(400)
				.firstName("JANE")
				.dob(Timestamp.valueOf("2023-03-04 00:00:00"))
				.createUid(900)
				.createDate(Timestamp.valueOf("2024-01-02 03:04:05"))
				.currentProcessStatus(1)
				.build());

		Map<String, Object> row = new JdbcTemplate(dataSource).queryForMap("select * from RAW_ICCR_DOC where DOCUMENT_ID = 400");
		assertThat(row.get("FIRST_NAME")).isEqualTo("JANE");
		assertThat(row.get("LAST_NAME")).isNull();
		assertThat(row.get("DOB")).isEqualTo(Timestamp.valueOf("2023-03-04 00:00:00"));
		assertThat(row.get("CREATE_UID")).hasToString("900");
		assertThat(row.get("CURRENT_PROCESS_STATUS")).hasToString("1");
	}

	@Test
	@Transactional
	void savesAStandardizedReport() {
		iccrDocRepository.saveAndFlush(StdIccrDoc.builder()
				.documentId(400)
				.fname("JANE")
				.matchWeight("12.5")
				.createUid("900")
				.currentProcessStatus("5")
				.build());

		Map<String, Object> row = new JdbcTemplate(dataSource).queryForMap("select * from STD_ICCR_DOC where DOCUMENT_ID = 400");
		assertThat(row.get("FNAME")).isEqualTo("JANE");
		assertThat(row.get("LNAME")).isNull();
		assertThat(row.get("MATCH_WEIGHT")).hasToString("12.5");         // text written to a number column, converted by the database
		assertThat(row.get("CREATE_UID")).hasToString("900");
		assertThat(row.get("CURRENT_PROCESS_STATUS")).hasToString("5");
	}
}
