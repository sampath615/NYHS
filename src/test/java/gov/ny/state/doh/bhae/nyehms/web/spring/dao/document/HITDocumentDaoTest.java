package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
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
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitErrorVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.HitLoadLogVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The HIT document DAO: the JPQL statements, the entity inserts and the live-event counts (native) against an
 * in-memory H2 database in Oracle mode (db/hit.sql), next to their data; the three Oracle-only native statements
 * checked by their SQL text without a database.
 */
@SpringJUnitConfig(HITDocumentDaoTest.TestConfig.class)
class HITDocumentDaoTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, HITDocumentDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("hitdao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/hit.sql").build();
		}
	}

	@Autowired HITDocumentDAO dao;
	@Autowired NativeStatements statements;

	@Test
	void loaderLogIsWrittenWithASequenceKeyAndUpdatedByIt() {
		assertThat(dao.getMaxUphnTranxInId()).isEqualTo(12);
		assertThat(dao.getMaxUphnTranxInIdCheckedFromLog("CCD")).isEqualTo(9);
		assertThat(dao.getMaxUphnTranxInIdCheckedFromLog("HL7")).isEqualTo(7);
		assertThatThrownBy(() -> dao.getMaxUphnTranxInIdCheckedFromLog("EXP")).isInstanceOf(DataRetrievalFailureException.class);   // no run yet: no value, as the former int result

		HitLoadLogVO run = new HitLoadLogVO();
		run.setDocType("EXP");
		run.setMinUtiiChecked(8);
		run.setCreateDate(new java.util.Date(Timestamp.valueOf("2024-01-05 06:07:08").getTime()));
		dao.insertHitLoadLog(run);
		assertThat(run.getLogID()).isEqualTo(100);                                        // from HIT_LOAD_LOG_SEQ, put into the VO
		Map<String, Object> row = statements.one("select * from HIT_LOAD_LOG where LOG_ID = 100", Rows.map());
		assertThat(row).containsEntry("DOC_TYPE", "EXP").containsEntry("CREATE_ID", "NYEHMS").containsEntry("CREATE_DATE", Timestamp.valueOf("2024-01-05 06:07:08"));
		assertThat(row.get("MIN_UTII_CHECKED")).hasToString("8");
		assertThat(row.get("NO_OF_FAILED_DOCS")).hasToString("0");

		run.setMinUtiiRetrieved(8);
		run.setMaxUtiiRetrieved(11);
		run.setMaxUtiiChecked(12);
		run.setNoOfRetrievedDocs(4);
		dao.updateHitLoadLogAfterOneRow(run);
		row = statements.one("select * from HIT_LOAD_LOG where LOG_ID = 100", Rows.map());
		assertThat(row.get("MIN_UTII_RETRIEVED")).hasToString("8");
		assertThat(row.get("MAX_UTII_RETRIEVED")).hasToString("11");
		assertThat(row.get("MAX_UTII_CHECKED")).hasToString("12");
		assertThat(row.get("NO_OF_RETRIEVED_DOCS")).hasToString("4");
		assertThat(row.get("NO_OF_SUCCESSFUL_DOCS")).hasToString("0");

		run.setNoOfFailedDocs(1);
		run.setNoOfSuccessfulDocs(3);
		dao.updateHitLoadLogAfterAllRows(run);
		row = statements.one("select * from HIT_LOAD_LOG where LOG_ID = 100", Rows.map());
		assertThat(row.get("NO_OF_FAILED_DOCS")).hasToString("1");
		assertThat(row.get("NO_OF_SUCCESSFUL_DOCS")).hasToString("3");
		assertThat(dao.getMaxUphnTranxInIdCheckedFromLog("EXP")).isEqualTo(12);
	}

	@Test
	void loadErrorsAreWrittenListedAndReprised() {
		HitErrorVO error = new HitErrorVO();
		error.setUphnTRANXINID(13);
		error.setPayloadCLEARTEXT("<ClinicalDocument/>");
		error.setErrorMESSAGE("x".repeat(500));
		error.setCreateDATETIME(new java.sql.Date(Timestamp.valueOf("2024-01-06 07:08:09").getTime()));
		error.setRecordType("CCD");
		error.setComments("first try");
		dao.insertHitError(error);
		Map<String, Object> row = statements.one("select * from HIT_ERROR where HIT_ERROR_ID = 500", Rows.map());   // from HIT_ERROR_SEQ
		assertThat(row.get("UPHN_TRANX_IN_ID")).hasToString("13");
		assertThat(row).containsEntry("PAYLOAD_CLEARTEXT", "<ClinicalDocument/>").containsEntry("RECORD_TYPE", "CCD").containsEntry("COMMENTS", "first try")
				.containsEntry("CREATE_DATETIME", Timestamp.valueOf("2024-01-06 00:00:00")).doesNotContainKey("REPRISE_FLAG");   // jdbcType=DATE: the day

		List<Map<Object, Object>> all = dao.getHitErrorRepriseValue("ALL");
		assertThat(all).extracting(e -> e.get("HIT_ERROR_ID")).containsExactlyInAnyOrder(1L, 3L, 500L);   // 2 is flagged; Longs now (BigDecimals before)
		Map<Object, Object> first = all.stream().filter(e -> e.get("HIT_ERROR_ID").equals(1L)).findFirst().orElseThrow();
		assertThat(first).containsEntry("UPHN_TRANX_IN_ID", 10).containsEntry("ERROR", "bad CCD").containsEntry("CREATE_DATETIME", "01/02/2024")
				.containsEntry("RECORD_TYPE", "CCD").doesNotContainKey("REPRISE_FLAG").doesNotContainKey("DATE_OF_REPRISE").doesNotContainKey("NOTES");
		Map<Object, Object> added = all.stream().filter(e -> e.get("HIT_ERROR_ID").equals(500L)).findFirst().orElseThrow();
		assertThat((String) added.get("ERROR")).hasSize(400);                                  // substr(..., 0, 400)
		assertThat(added).containsEntry("NOTES", "first try");
		assertThat(dao.getHitErrorRepriseValue("EXP")).extracting(e -> e.get("HIT_ERROR_ID")).containsExactly(3L);
		assertThat(dao.getHitErrorRepriseValue("NONE")).isEmpty();

		Map<Object, Object> reprise = new HashMap<>();
		reprise.put("statusCode", "N");
		reprise.put("uphnID", 12);
		assertThat(dao.updateHITErrorRepriseFlagStatus(reprise)).isEqualTo(1);           // only the error flagged 'Y'
		row = statements.one("select REPRISE_FLAG, DATE_OF_REPRISE from HIT_ERROR where HIT_ERROR_ID = 2", Rows.map());
		assertThat(row).containsEntry("REPRISE_FLAG", "N");
		assertThat((Timestamp) row.get("DATE_OF_REPRISE")).isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);
		reprise.put("uphnID", 10);
		assertThat(dao.updateHITErrorRepriseFlagStatus(reprise)).isZero();

		Map<Object, Object> status = new HashMap<>();
		status.put("hitErrorId", 3L);
		status.put("actionName", "Y");
		assertThat(dao.updateHitDocumentsStatusinHITERRROR(status)).isEqualTo(1);
		assertThat(statements.one("select REPRISE_FLAG from HIT_ERROR where HIT_ERROR_ID = 3", Rows.scalar(String.class))).isEqualTo("Y");
		Map<Object, Object> notes = new HashMap<>();
		notes.put("hiterrorid", 1L);
		notes.put("notes", "checked");
		assertThat(dao.updateHitErrorComments(notes)).isEqualTo(1);
		assertThat(statements.one("select COMMENTS from HIT_ERROR where HIT_ERROR_ID = 1", Rows.scalar(String.class))).isEqualTo("checked");
	}

	@Test
	void raceValuesAreListedAddedAndMapped() {
		assertThat(dao.getRaceCodes()).containsExactly(Map.of("RACE_VALUE", "ASIAN", "RACE", "A", "ETHNICITY", "NH"), Map.of("RACE_VALUE", "BLACK"),
				Map.of("RACE_VALUE", "WHITE", "RACE", "W", "ETHNICITY", "NH"));
		assertThat(dao.getHitRaceCodeMapperData()).containsExactly(Map.of("RACE_VALUE", "BLACK"), Map.of("RACE_VALUE", "WHITE", "RACE", "W", "ETHNICITY", "NH"));
		assertThat(dao.getHitRaceValue("WHITE")).isEqualTo(Map.of("RACE", "W", "ETHNICITY", "NH"));
		assertThat(dao.getHitRaceValue("BLACK")).isNull();                               // a row of nulls was null, as before
		assertThat(dao.getHitRaceValue("OTHER")).isNull();
		assertThat(dao.getHitEthnicityData()).isEqualTo(Map.of("ETHNICITY", "NH"));
		assertThatThrownBy(dao::getHitRaceData).isInstanceOf(IncorrectResultSizeDataAccessException.class);   // two distinct races: selectOne failed before too
		assertThat(dao.checkIfRaceExists("ASIAN")).isEqualTo(1);
		assertThat(dao.checkIfRaceExists("OTHER")).isZero();

		Map<String, Object> codes = new HashMap<>();
		codes.put("raceCodeErrorsList", List.of("OTHER", "MIXED"));
		assertThat(dao.insertCodedRace(codes)).isEqualTo(2);
		List<Map<String, Object>> added = statements.list("select * from CODED_HIT_RACE_DESC where RACE_VALUE in ('OTHER', 'MIXED') order by RACE_VALUE", Rows.map());
		assertThat(added).hasSize(2);
		assertThat(added.get(0)).containsEntry("RACE_VALUE", "MIXED").containsEntry("CREATE_DATE", Timestamp.valueOf(LocalDate.now().atStartOfDay()))
				.doesNotContainKey("RACE");
		codes.put("raceCodeErrorsList", new ArrayList<>());
		assertThatThrownBy(() -> dao.insertCodedRace(codes)).isInstanceOf(InvalidDataAccessResourceUsageException.class);

		Map<Object, Object> mapping = new HashMap<>();
		mapping.put("parameterName", "BLACK");
		mapping.put("parameterValue1", "B,O");
		mapping.put("parameterValue2", "NH");
		mapping.put("userId", "jroe");
		assertThat(dao.updateHitaRaceValue(mapping)).isEqualTo(1);
		Map<String, Object> row = statements.one("select * from CODED_HIT_RACE_DESC where RACE_VALUE = 'BLACK'", Rows.map());
		assertThat(row).containsEntry("RACE", "B,O").containsEntry("ETHNICITY", "NH").containsEntry("UPDATED_BY", "jroe")
				.containsEntry("UPDATED_DATE", Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		assertThat(dao.getHitRaceCodeMapperData()).extracting(r -> r.get("RACE_VALUE")).containsExactly("MIXED", "OTHER", "WHITE");
	}

	@Test
	@Transactional                                                                       // rolled back: the other tests count on the script's rows
	void liveEventCountsRunAsBefore() {
		statements.update("insert into UPHN_TRANX_IN values (20, 'DEERPROD', 'today1.xml', SYSDATE)");
		statements.update("insert into UPHN_TRANX_IN values (21, 'DEERPROD', 'today1.xml', SYSDATE)");
		statements.update("insert into UPHN_TRANX_IN values (22, 'DEERHL7PROD', 'today.txt', SYSDATE)");
		statements.update("insert into UPHN_TRANX_IN values (23, 'DEERHL7PROD', 'today.csv', SYSDATE)");
		statements.update("insert into UPHN_TRANX_IN values (24, 'DEERHL7PROD', 'other.dat', SYSDATE)");
		List<Map<String, Object>> counts = dao.getCountsForHITLiveEvent();
		assertThat(counts).hasSize(3);
		assertThat(counts).extracting(c -> c.get("TYPE")).containsExactly("CCD", "EXP", "HL7");    // by day, then type
		Map<String, Object> ccd = counts.get(0);
		assertThat(ccd.get("TOT")).hasToString("2");
		assertThat(ccd.get("DIST")).hasToString("1");
		assertThat(ccd.get("DUPES")).hasToString("1");
		assertThat(ccd.get("MAX_UPHN_TRANX_IN_ID")).hasToString("21");
		assertThat(counts.get(1).get("MAX_UPHN_TRANX_IN_ID")).hasToString("23");
		assertThat(counts.get(2).get("MAX_UPHN_TRANX_IN_ID")).hasToString("22");
	}

	@SuppressWarnings("unchecked")
	@Test
	void oracleOnlyStatementsKeepTheirSql() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		HITDocumentDAO dao = new HITDocumentDAO(mocked, null);
		dao.insertHitMessageCount();
		dao.deleteFromUphnTranxPropsIn();
		ArgumentCaptor<String> updates = ArgumentCaptor.forClass(String.class);
		verify(mocked, org.mockito.Mockito.times(2)).update(updates.capture(), any(Object[].class));
		assertThat(updates.getAllValues().get(0)).contains("INSERT INTO hit_message_counts").contains("WITH hit_data AS")
				.contains("SELECT hit_message_count_seq.nextval hit_id, a.type, a.receivedday, a.tot, a.dist, a.dupes, a.max_uphn_tranx_in_id");
		assertThat(updates.getAllValues().get(1).replaceAll("\\s+", " ")).isEqualTo(
				"DELETE UPHN_TRANX_PROPS_IN UTPI WHERE EXISTS (SELECT 1 FROM UPHN_TRANX_IN UTI WHERE UTPI.UPHN_TRANX_IN_ID = UTI.UPHN_TRANX_IN_ID AND Trunc(uti.receivedtime) < SYSDATE - 30) ");

		assertThat(dao.getCountsForTodayMail()).isEmpty();
		ArgumentCaptor<String> query = ArgumentCaptor.forClass(String.class);
		verify(mocked).list(query.capture(), any(RowMapper.class), any(Object[].class));
		assertThat(query.getValue()).contains("ROW_NUMBER() OVER (PARTITION BY a.type ORDER BY a.received_date DESC) rn")
				.contains("Trunc(next_day(sysdate,'MONDAY') - 7)").contains("FROM hits_recd a RIGHT OUTER JOIN distinct_type b").contains("ORDER BY slno");
	}
}
