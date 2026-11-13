package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.ecrls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;

/**
 * The eCLRS scheduler DAO: the batch, key-list and person-update reads against an in-memory H2 database in Oracle
 * mode (db/ecrls.sql) next to their data; the reassignment MERGE and the 30-day read (Oracle date arithmetic) by their
 * SQL text and bound values without a database; the allow-lists.
 */
@SpringJUnitConfig(EcrlsDataDaoTest.TestConfig.class)
class EcrlsDataDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, EcrlsDataDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("ecrlsdao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/ecrls.sql").build();
		}
	}

	@Autowired EcrlsDataDAO dao;

	@Test
	void batchesAndKeyListsReadTheViewsAsBefore() {
		Map<Object, Object> batch = new HashMap<>();
		batch.put("sourceTable", "vallh");
		batch.put("lastInsertedId", 1L);
		batch.put("fetchRowCount", 3);
		List<EcrlsLabDocVO> rows = dao.getEclrsRawDatatoClean(batch);
		assertThat(rows).extracting(EcrlsLabDocVO::getOBXOBSERVATIONRESULTKEY).containsExactly(2L, 3L);   // after key 1, ROWNUM < 3
		assertThat(rows.get(0).getMaxOBXOBSERVATIONRESULTKEY()).isEqualTo(3);
		assertThat(rows.get(0).getMinOBXOBSERVATIONRESULTKEY()).isEqualTo(2);
		assertThat(rows.get(0).getCURRENT_PROCESS_STATUS()).isEqualTo(1);
		assertThat(rows.get(1).getPIDFIRSTNAME()).isEqualTo("CARL");
		batch.put("sourceTable", "VALLS");
		assertThat(dao.getEclrsRawDatatoClean(batch)).extracting(EcrlsLabDocVO::getPIDFIRSTNAME).containsExactly("EVE");
		batch.put("sourceTable", "VALLH where 1 = 1 --");
		assertThatThrownBy(() -> dao.getEclrsRawDatatoClean(batch)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<Object, Object> updated = new HashMap<>();
		updated.put("sourceTable", "vallh");
		updated.put("lastInsertedId", 3L);
		updated.put("lastPidUpdateDate", Timestamp.valueOf("2024-01-15 00:00:00"));
		rows = dao.getPIDUpdatedRecordsFromEclrs(updated);
		assertThat(rows).extracting(EcrlsLabDocVO::getOBXOBSERVATIONRESULTKEY).containsExactlyInAnyOrder(2L, 3L);   // up to key 3, changed after Jan 15
		assertThat(rows.get(0).getMaxPIDUPDATEDATE()).isEqualTo(Timestamp.valueOf("2024-03-02 00:00:00"));

		Map<String, Object> keys = new HashMap<>();
		keys.put("tableName", "VALLH");
		keys.put("erroredDocumentsList", List.of(1L, 4L));
		assertThat(dao.getErroredPIDDocumentsListFromEclrs(keys)).extracting(EcrlsLabDocVO::getPIDFIRSTNAME).containsExactlyInAnyOrder("ANN", "DAN");
		keys.put("erroredDocumentsList", List.of());
		assertThatThrownBy(() -> dao.getErroredPIDDocumentsListFromEclrs(keys)).isInstanceOf(InvalidDataAccessResourceUsageException.class);   // IN () failed too
	}

	@SuppressWarnings("unchecked")
	@Test
	void reassignmentStatementsKeepTheirSqlAndTheRecordTypeCondition() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		EcrlsDataDAO dao = new EcrlsDataDAO(mocked);
		Map<Object, Object> map = new HashMap<>();
		map.put("tableName", "VALLH");
		map.put("NYEHMS_TABLE", "raw_hiv_eclrs_doc");
		map.put("recordType", "HIV");
		dao.updateEcrlsProcessedRecords(map);
		assertThat(dao.getEcrlsReAssignDocData(map)).isEmpty();
		map.put("tableName", "VALLS");
		map.put("NYEHMS_TABLE", "raw_std_eclrs_doc");
		map.put("recordType", "STD");
		dao.updateEcrlsProcessedRecords(map);
		assertThat(dao.getEcrlsReAssignDocData(map)).isEmpty();

		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(2)).update(sql.capture(), parameters.capture());
		assertThat(sql.getAllValues().get(0)).contains("MERGE INTO lab_eclrs_reassign a").contains("FROM raw_hiv_eclrs_doc a, lab_eclrs_reassign b")
				.contains("AND mshcreatedate >= sysdate - 365").contains("and b.hiv_std = ?1) b").contains("WHEN MATCHED THEN\nUPDATE SET process_date = sysdate\nWHERE a.process_date IS NULL");
		assertThat(parameters.getAllValues().get(0)).containsExactly("HIV");
		assertThat(sql.getAllValues().get(1)).contains("FROM raw_std_eclrs_doc a, lab_eclrs_reassign b").doesNotContain("mshcreatedate");
		assertThat(parameters.getAllValues().get(1)).containsExactly("STD");

		verify(mocked, org.mockito.Mockito.times(2)).list(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(2)).contains("from VALLH vall").contains("OBXDISEASE = 428  and").contains("OBXCREATEDATE  > SYSDATE - 30 and")
				.contains("where b.hiv_std = ?1").contains("(b.src_id = vall.OBROBSERVATIONREQUESTKEY AND obx_obr = 'OBR')");
		assertThat(parameters.getAllValues().get(2)).containsExactly("HIV");
		assertThat(sql.getAllValues().get(3)).contains("from VALLS vall").doesNotContain("OBXDISEASE");
		assertThat(parameters.getAllValues().get(3)).containsExactly("STD");

		map.put("NYEHMS_TABLE", "PERSON");
		assertThatThrownBy(() -> dao.updateEcrlsProcessedRecords(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		map.put("tableName", null);
		assertThatThrownBy(() -> dao.getEcrlsReAssignDocData(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}
}
