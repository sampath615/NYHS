package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ursdb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** The AIDA DAO: the configured select over the URS data source, here an in-memory H2 database (db/aida.sql). */
@SpringJUnitConfig(AIDADataDaoTest.TestConfig.class)
class AIDADataDaoTest {

	@Configuration
	@Import(AIDADataDAO.class)
	static class TestConfig {
		@Bean
		DataSource ursDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("ursdb").generateUniqueName(false).addScript("db/aida.sql").build();
		}
	}

	@Autowired AIDADataDAO dao;

	@Test
	void configuredColumnsOfTheConfiguredTableAreReadForAClient() {
		Map<String, String> input = new HashMap<>();
		input.put("tableName", "AIDA_PREP");
		input.put("columns", "client_id as CLIENT_ID, is_prep as ON_PREP, date_asked as DATE_ASKED");
		input.put("tc_id", "T1");
		List<Map<String, String>> rows = dao.loadAirsAIDAData(input);
		assertThat(rows).hasSize(2);
		assertThat(rows.get(0)).containsEntry("CLIENT_ID", "C1").containsEntry("ON_PREP", "Y").containsKey("DATE_ASKED");
		assertThat(rows.get(1)).containsEntry("CLIENT_ID", "C2").doesNotContainKey("ON_PREP").doesNotContainKey("DATE_ASKED");   // nulls left out, as before
		input.put("tc_id", "T9");
		assertThat(dao.loadAirsAIDAData(input)).isEmpty();

		input.put("columns", null);                                                        // not configured: refused (a SQL error before)
		assertThatThrownBy(() -> dao.loadAirsAIDAData(input)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		input.put("columns", "client_id; drop table AIDA_PREP");
		assertThatThrownBy(() -> dao.loadAirsAIDAData(input)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		input.put("columns", "client_id");
		input.put("tableName", "");
		assertThatThrownBy(() -> dao.loadAirsAIDAData(input)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}
}
