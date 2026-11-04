package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import static gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.number;
import static gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.JsonRows.text;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
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
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.ViralLoadReportsService;

/**
 * Report rows as JSONObjects (replacing MyBatis statements with resultType JSONObject) and the viral load reports
 * built on them, against an in-memory H2 database in Oracle mode (db/viral-load.sql).
 */
@SpringJUnitConfig(JsonRowsTest.TestConfig.class)
class JsonRowsTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, JsonRows.class, ViralLoadReportsService.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("jsonrows;MODE=Oracle").generateUniqueName(false)
					.addScript("db/viral-load.sql").build();
		}
	}

	@Autowired JsonRows jsonRows;

	@Autowired ViralLoadReportsService viralLoadReportsService;

	@Test
	void keysRowsByColumnLabelAndLeavesOutNullValues() {
		List<JSONObject> rows = jsonRows.list("select ID, NAME \"name\", AMOUNT, ID ID2 from JSON_ROWS_TEST where ID = ?1 or ID is null order by ID nulls last",
				List.of(number("ID"), text("name"), number("AMOUNT"), number("ID2")), 2);

		assertThat(rows).hasSize(2);
		assertThat(rows.get(0)).containsOnlyKeys("ID", "ID2");          // the null columns are left out
		assertThat(rows.get(0).get("ID")).isEqualTo(new BigDecimal("2"));
		assertThat(rows.get(1)).isNull();                                // a row of nulls only is a null element
		rows.add(null);                                                  // a modifiable list, as before

		JSONObject full = jsonRows.list("select NAME \"name\", AMOUNT from JSON_ROWS_TEST where ID = ?1",
				List.of(text("name"), number("AMOUNT")), 1).get(0);
		assertThat(full.get("name")).isEqualTo("one");                  // a quoted label keeps its case
		assertThat(full.get("AMOUNT")).isEqualTo(new BigDecimal("12"));
	}

	@Test
	void readsAValueAsGetObjectDoes() {
		JSONObject row = jsonRows.one("select 'Y' FLAG, AMOUNT, NAME from JSON_ROWS_TEST where ID = ?1",
				List.of(JsonRows.value("FLAG"), JsonRows.value("AMOUNT"), JsonRows.value("NAME")), 1);

		assertThat(row.get("FLAG")).isEqualTo("Y");                     // a String, not a Character
		assertThat(row.get("AMOUNT")).isEqualTo(new BigDecimal("12"));
		assertThat(row.get("NAME")).isEqualTo("one");
		assertThat(jsonRows.one("select NAME from JSON_ROWS_TEST where ID = 99", List.of(JsonRows.value("NAME")))).isNull();
	}

	@Test
	void readsTextColumnsIntoPlainMaps() {
		List<java.util.Map<String, String>> maps = jsonRows.textMaps(
				"select ID \"value\", NAME \"label\" from JSON_ROWS_TEST where ID is not null order by ID", List.of("value", "label"));

		assertThat(maps).hasSize(2);
		assertThat(maps.get(0)).isExactlyInstanceOf(java.util.HashMap.class).containsEntry("value", "1").containsEntry("label", "one");
		assertThat(maps.get(1)).containsOnlyKeys("value");             // a number read as text, the null name left out
	}

	@Test
	void readsASingleColumn() {
		assertThat(jsonRows.list("select NAME from JSON_ROWS_TEST where ID = 1", List.of(text("NAME"))))
				.singleElement().satisfies(row -> assertThat(row.get("NAME")).isEqualTo("one"));
	}

	@Test
	void listsTheLatestNotDetectableViralLoads() {
		List<JSONObject> rows = viralLoadReportsService.loadNdvlReportData(null, null);

		assertThat(rows).hasSize(1);
		JSONObject row = rows.get(0);
		assertThat(row.get("stateno")).isEqualTo("001");
		assertThat(row.get("documentType")).isEqualTo("VL");
		assertThat(row.get("documentId")).isEqualTo("500");
		assertThat(row.get("RIA_DOCUMENT_ID")).isEqualTo(new BigDecimal("10"));
		assertThat(row.get("COLLECTION_DATE")).isEqualTo("02/01/2021");
		assertThat(row.get("DOB_TEXT")).isEqualTo("01/02/1980");
	}
}
