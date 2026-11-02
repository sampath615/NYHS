package gov.ny.state.doh.bhae.nyehms.web.spring.repository.upload;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload.CdcRidrInfo;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.LegacyPsPersonPartnerRepository;

/**
 * The uploaded CDC RIDR rows and the legacy partner list (moved from raw JDBC through ConnectionDAO to Spring Data
 * JPA), against an in-memory H2 database in Oracle mode (db/upload-partners.sql). The CDC rows are compared with the
 * rows the old insert statement writes.
 */
@SpringJUnitConfig(CdcRidrInfoAndPartnerRepositoriesTest.TestConfig.class)
class CdcRidrInfoAndPartnerRepositoriesTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("uploadpartners;MODE=Oracle")
					.generateUniqueName(false).addScript("db/upload-partners.sql").build();
		}
	}

	/** The fields of a CDC RIDR line, in the order of the old insert's parameters. */
	private static final List<String> FIELDS = List.of("record_no", "NYS_newly_reported_cdc", "NYS_Reporting_Jurisdiction", "NYS_stateno",
			"NYS_Last_Name_Soundex", "NYS_DATE_OF_BIRTH", "NYS_SEX_AT_BIRTH", "NYS_DATE_OF_DEATH", "NYS_Transmission_Category",
			"NYS_HIV_Case_Def_Category", "NYS_Date_HIV_Infection_Diag", "NYS_Resi_HIV_Infection_Diag", "NYS_AIDS_Case_Def_Category",
			"NYS_Date_AIDS_Diag", "NYS_Resi_at_AIDS_Diag", "NYS_duplicate_status", "newly_reported_cdc", "Reporting_Jurisdiction", "stateno",
			"DATE_OF_DEATH", "Transmission_Category", "HIV_Case_Def_Category", "Date_HIV_Infection_Diag", "Resi_HIV_Infection_Diag",
			"AIDS_Case_Def_Category", "Date_AIDS_Diag", "Resi_at_AIDS_Diag", "duplicate_status", "merged_cdc");

	/** The old statement (FileUploadService.uploadRidrDataAsBatch, from Git). */
	private static final String OLD_INSERT = "insert into CDC_RIDR_INFO(RIDR_ID, " + String.join(", ", FIELDS)
			+ ", CREATED_DT, CREATED_UID, FILE_NAME) values(CDC_RIDR_INFO_SEQ.nextval, " + "?, ".repeat(FIELDS.size()) + "SYSDATE, '7', ?)";

	@Autowired CdcRidrInfoRepository cdcRidrInfoRepository;
	@Autowired LegacyPsPersonPartnerRepository partnerRepository;
	@Autowired PlatformTransactionManager transactionManager;
	@Autowired DataSource dataSource;

	@Test
	void writesTheUploadedRowsAsTheOldInsertDid() {
		List<Map<String, Object>> lines = new ArrayList<>();
		for (int line = 0; line < 3; line++) {
			Map<String, Object> row = new HashMap<>();
			for (int i = 0; i < FIELDS.size(); i++) {
				row.put(FIELDS.get(i), i % 7 == line ? null : FIELDS.get(i).substring(0, 3) + line + (i % 5 == 0 ? "  " : ""));
			}
			lines.add(row);
		}
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		for (Map<String, Object> row : lines) {
			List<Object> params = new ArrayList<>();
			FIELDS.forEach(field -> params.add(row.get(field)));
			params.add("old.txt");
			jdbc.update(OLD_INSERT, params.toArray());
		}
		new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
			List<CdcRidrInfo> rows = lines.stream().map(row -> builderOf(row)).toList();
			cdcRidrInfoRepository.saveAll(rows);
			cdcRidrInfoRepository.flush();
		});

		List<Map<String, Object>> stored = jdbc.queryForList("select * from CDC_RIDR_INFO order by RIDR_ID");
		assertThat(stored).hasSize(6);
		for (int line = 0; line < 3; line++) {
			Map<String, Object> before = withoutGenerated(stored.get(line)), after = withoutGenerated(stored.get(line + 3));
			before.put("FILE_NAME", "new.txt");
			assertThat(after).isEqualTo(before);
			assertThat(stored.get(line + 3).get("CREATED_DT")).isNotNull();   // the database clock, as before
		}
		assertThat(stored.stream().map(row -> ((Number) row.get("RIDR_ID")).longValue()).toList()).containsExactly(1L, 2L, 3L, 4L, 5L, 6L);
	}

	private static CdcRidrInfo builderOf(Map<String, Object> row) {
		Map<String, Object> copy = new LinkedHashMap<>(row);
		return gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads.CdcRidrInfoTestAccess.toRow(copy, "new.txt", 7);
	}

	private static Map<String, Object> withoutGenerated(Map<String, Object> row) {
		Map<String, Object> copy = new LinkedHashMap<>(row);
		copy.remove("RIDR_ID");
		copy.remove("CREATED_DT");
		return copy;
	}

	@Test
	void listsThePartnerIdsOfAnAssignmentWithZeroForAMissingId() {
		assertThat(partnerRepository.findPartnerIdsOfAssignment(10)).containsExactlyInAnyOrder(1, 2, 0);
		assertThat(partnerRepository.findPartnerIdsOfAssignment(99)).isEmpty();
	}
}
