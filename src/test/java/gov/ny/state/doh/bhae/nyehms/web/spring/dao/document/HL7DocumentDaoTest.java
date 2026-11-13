package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.document.CCDDocumentDaoTest.assertNumber;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
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
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7AddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7DocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7INSVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.RawHL7ObrVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7.StdHL7RaceVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.CallParam;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import jakarta.persistence.ParameterMode;

/**
 * The HL7 document DAO: the raw and standardized writes, the JPQL status updates and the MPI pair insert, the viewer
 * queries against an in-memory H2 database in Oracle mode (db/hl7.sql), next to their data; the Oracle-only statements
 * (sequences by {@code CONNECT BY}, the PL/SQL block, {@code ROWID}, {@code FIRST_VALUE ... IGNORE NULLS},
 * {@code REGEXP_SUBSTR}) by their SQL text, the procedure by its parameters and outputs, and the former
 * {@code foreach ... union all} inserts by their one-item SQL and bound values, without a database.
 */
@SpringJUnitConfig(HL7DocumentDaoTest.TestConfig.class)
public class HL7DocumentDaoTest {                                                         // public: H2 calls the stand-in functions reflectively

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, HL7DocumentDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("hl7dao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/hl7.sql").build();
		}
	}

	/** The stand-in for FN_TO_DATE(text, 'MM/DD/YYYY'): the date, or null for a text that is not one. */
	public static java.sql.Date fnToDate(String text, String format) {
		try {
			return java.sql.Date.valueOf(LocalDate.parse(text, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		} catch (RuntimeException e) {
			return null;
		}
	}

	/** The stand-in for TO_CLOB(text). */
	public static String toClob(String text) {
		return text;
	}

	@Autowired HL7DocumentDAO dao;
	@Autowired NativeStatements statements;

	// ---- the loader's writes ------------------------------------------------------------------------------------------

	@Test
	@Transactional                                                                       // rolled back: the other tests count on the script's rows
	void rawMessageAndStandardizedCopyAreWrittenAsTheMapperDid() {
		RawHL7DocVO vo = new RawHL7DocVO();                                              // the segment VOs left null: nulls on their paths
		vo.setDocumentId(9);
		vo.setUphnId(5);
		vo.setCurrentProcessStatus(1);
		vo.setMshMessageType("ADT^A04");
		dao.insertRawHL7DocData(vo);
		Map<String, Object> row = statements.one("select * from RAW_HL7_DOC where DOCUMENT_ID = 9", Rows.map());
		assertThat(row).containsEntry("MSHMESSAGETYPE", "ADT^A04").doesNotContainKey("PIDMPI").doesNotContainKey("MSHCREATEDATE");
		assertNumber(row.get("UPHN_TRANX_IN_ID"), 5);
		assertNumber(row.get("CURRENT_PROCESS_STATUS"), 1);

		Map<Object, Object> std = new HashMap<>();
		std.put("DOCUMENT_ID", 9);
		std.put("UPHN_TRANX_IN_ID", 5);
		std.put("FNAME", " al ");
		std.put("LNAME", "bee");
		std.put("CURRENT_PROCESS_STATUS", "10");
		std.put("DOB_TEXT", "01/02/1980");
		std.put("DATE_RECEIVED", new java.util.Date(Timestamp.valueOf("2024-05-06 07:08:09").getTime()));   // jdbcType=DATE: the day only
		std.put("RACE_ASIAN", "1");
		std.put("CCD_MATCH_DOC_ID", 100);
		std.put("MPI", "MPI9");
		assertThat(dao.insertHL7FromRAWToSTD(std)).isEqualTo(1);
		row = statements.one("select * from STD_HL7_DOC where DOCUMENT_ID = 9", Rows.map());
		assertThat(row).containsEntry("FNAME", "AL").containsEntry("LNAME", "BEE").containsEntry("MPI", "MPI9").containsEntry("DOB_TEXT", "01/02/1980")
				.containsEntry("DATE_OF_BIRTH", Timestamp.valueOf("1980-01-02 00:00:00")).containsEntry("DATE_RECEIVED", Timestamp.valueOf("2024-05-06 00:00:00"))
				.doesNotContainKey("EVENT_DATE");
		assertNumber(row.get("RACE_ASIAN"), 1);
		assertNumber(row.get("CURRENT_PROCESS_STATUS"), 10);
	}

	// ---- the loader's status updates (JPQL and native) ------------------------------------------------------------------

	private int status(String table, int documentId) {
		return statements.one("select CURRENT_PROCESS_STATUS from " + table + " where DOCUMENT_ID = " + documentId, Rows.scalar(Integer.class));
	}

	@Test
	@Transactional
	void loaderStatusUpdatesRunOnH2() {
		dao.updateCPSinRAWfromNonMatchToMatchCCD();
		assertThat(status("RAW_HL7_DOC", 1)).isEqualTo(1);                                // MPI1's CCD is matched
		assertThat(status("RAW_HL7_DOC", 2)).isEqualTo(36);                               // MPI9 has no CCD

		dao.updateCurrentProcessStatusInRawForMPIChange();
		assertThat(status("RAW_HL7_DOC", 3)).isEqualTo(41);                               // OLD1 became NEW1, whose CCD is matched
		assertThat(status("RAW_HL7_DOC", 4)).isEqualTo(37);

		dao.updateCPSinSTDNotMatchOnParameters();
		assertThat(status("STD_HL7_DOC", 5)).isEqualTo(60);

		dao.insertMPIPairsCheckingHL7andCCD();
		List<Map<String, Object>> pairs = statements.list("select CURRENT_MPI, OLD_MPI, DATE_RECEIVED from MPI_CHANGE order by CURRENT_MPI, OLD_MPI", Rows.map());
		assertThat(pairs).extracting(p -> p.get("CURRENT_MPI") + "/" + p.get("OLD_MPI"))
				.containsExactly("MPI5/MPI2", "MPI5/MPI3", "MPI6/MPI2", "MPI6/MPI3", "NEW1/OLD1");   // names equal without spaces, also swapped; MPI6/MPI3 already there
		assertThat((Timestamp) pairs.get(0).get("DATE_RECEIVED")).isCloseTo(new Timestamp(System.currentTimeMillis()), 60_000);

		dao.updateCPSAndDOCidinRAWHL7fromSTDHL7DOC();
		Map<String, Object> raw = statements.one("select CURRENT_PROCESS_STATUS, CCD_MATCH_DOC_ID from RAW_HL7_DOC where DOCUMENT_ID = 3", Rows.map());
		assertNumber(raw.get("CURRENT_PROCESS_STATUS"), 18);
		assertNumber(raw.get("CCD_MATCH_DOC_ID"), 100);
		assertThat(statements.one("select CCD_MATCH_DOC_ID from RAW_HL7_DOC where DOCUMENT_ID = 1", Rows.scalar(Integer.class))).isNull();

		dao.deleteRowsFromSTDHL7AfterBackedUpToRawHL7();
		assertThat(statements.one("select count(*) from STD_HL7_DOC", Rows.scalar(Long.class))).isEqualTo(4L);
		assertThat(statements.one("select count(*) from STD_HL7_DOC where DOCUMENT_ID = 3", Rows.scalar(Long.class))).isZero();
	}

	// ---- the HL7 viewer of a CCD document ------------------------------------------------------------------------------

	private static Map<Object, Object> between(String startDate, String endDate) {
		Map<Object, Object> map = new HashMap<>();
		map.put("docId", 100);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}

	@Test
	void viewerQueriesRunOnH2() {
		List<RawHL7ObrVO> tests = dao.getHL7LabTestList(between("01/01/2024", "12/31/2024"));
		assertThat(tests).extracting(RawHL7ObrVO::getHl7labTestId).containsExactly(10, 11);     // by collection date; the empty OBR left out
		assertThat(tests.get(0).getObrProviderName()).isEqualTo("ANN SMITH");
		assertThat(tests.get(0).getObrLoincCode()).isEqualTo("T1");                       // the test code when there is no LOINC
		assertThat(tests.get(0).getObrCollectionDateStr()).isEqualTo("01/05/2024");
		assertThat(tests.get(0).getObrCollectionDate()).isEqualTo(Timestamp.valueOf("2024-01-05 00:00:00"));
		assertThat(tests.get(1).getObrLoincCode()).isEqualTo("L1");
		assertThat(dao.getHL7LabTestList(between("02/01/2024", "12/31/2024"))).extracting(RawHL7ObrVO::getHl7labTestId).containsExactly(11);
		assertThat(dao.getHL7LabResultList(10)).singleElement().satisfies(result -> {
			assertThat(result.getObxLoincCode()).isEqualTo("C1");
			assertThat(result.getObxObservationResultText()).isEqualTo("500 CELLS");
			assertThat(result.getHl7labTestId()).isEqualTo(10);
		});

		List<Map<Object, Object>> medications = dao.getHL7Medications(between("01/01/2024", "12/31/2024"));
		assertThat(medications).singleElement().satisfies(medication -> assertThat(medication).containsEntry("MEDICATIONNAME", "TRUVADA")
				.containsEntry("ORDERPROVIDERNAME", "ANN SMITH").containsEntry("ORDERINGFACILITYNAME", "PHARMACY A").doesNotContainKey("MEDICATIONDATE"));
		List<Map<Object, Object>> encounters = dao.getHL7Encounter(between("01/01/2024", "12/31/2024"));
		assertThat(encounters).hasSize(2);
		assertThat(encounters.get(0)).containsEntry("DATERECEIVED", "01/10/2024").containsEntry("EVNEVENTTYPE", "REGISTER A PATIENT").containsEntry("FACILITYNAME", "CLINIC A")
				.containsEntry("ENCOUNTERID", "ACC1").containsEntry("PROVIDERNAME", "ANN SMITH");
		assertThat(encounters.get(1)).containsEntry("ENCOUNTERID", "ACC2").doesNotContainKey("PROVIDERNAME");   // trim(' ') is null (H2 in Oracle mode too)

		StdHL7RaceVO race = dao.getHL7RaceVO(100);
		assertThat(race.getRaceAsian()).isEqualTo(1);
		assertThat(race.getRaceWhite()).isEqualTo(1);
		assertThat(race.getRaceBlack()).isZero();                                         // null: the int stays 0
		assertThat(race.getRaceOtherText()).contains("other a").contains("other b");
		assertThat(dao.getHL7RaceVO(999)).isNull();
		StdHL7RaceVO filtered = dao.getFilteredRaceVO(between("01/01/2024", "02/01/2024"));
		assertThat(filtered.getRaceAsian()).isEqualTo(1);
		assertThat(filtered.getRaceWhite()).isZero();
		assertThat(filtered.getRaceOtherText()).isEqualTo("other a");

		Map<String, Object> all = new HashMap<>();
		all.put("docId", 100);
		all.put("startDate", "");
		all.put("endDate", null);
		List<RawHL7AddressVO> addresses = dao.getFilteredHL7AddressList(all);
		assertThat(addresses).extracting(RawHL7AddressVO::getPersonAddress).containsExactly("1 MAIN ST", "2 ELM ST");
		assertThat(addresses.get(0).getPersonZip()).isEqualTo("12201-1234");
		assertThat(addresses.get(0).getPersonCity()).isEqualTo("ALBANY");
		assertThat(addresses.get(1).getPersonZip()).isEqualTo("12180");
		all.put("startDate", "02/01/2024");
		assertThat(dao.getFilteredHL7AddressList(all)).extracting(RawHL7AddressVO::getPersonAddress).containsExactly("2 ELM ST");
		all.put("startDate", null);
		all.put("endDate", "02/01/2024");
		assertThat(dao.getFilteredHL7AddressList(all)).extracting(RawHL7AddressVO::getPersonAddress).containsExactly("1 MAIN ST");

		all.put("endDate", "");
		List<RawHL7INSVO> insurances = dao.getFilteredHL7InsuranceList(all);
		assertThat(insurances).extracting(RawHL7INSVO::getCompanyName).containsExactlyInAnyOrder("BLUE PLAN", "RED PLAN");
		assertThat(insurances.get(0).getInsuredFName()).isEqualTo("JOHN DOE");
		assertThat(insurances.get(0).getPolicyNumber()).startsWith("P");
		all.put("startDate", "02/01/2024");
		assertThat(dao.getFilteredHL7InsuranceList(all)).extracting(RawHL7INSVO::getCompanyName).containsExactly("RED PLAN");

		Map<Object, Object> fields = between("01/01/2024", "12/31/2024");
		fields.put("colsName", "ETHNICITY");
		assertThat(dao.getHL7DemogrphicFieldMultiValues(fields)).extracting(f -> f.get("FIELD")).containsExactlyInAnyOrder("HISPANIC", "NOT HISPANIC");
		fields.put("colsName", "fname");
		assertThat(dao.getHL7DemogrphicFieldMultiValues(fields)).extracting(f -> f.get("FIELD")).containsExactly("JOHN");
		fields.put("colsName", "DATE_OF_BIRTH");
		assertThat(dao.getHL7DOBDemogrphicFieldMultiValues(fields)).extracting(f -> f.get("FIELD")).containsExactly("01/02/1980");
		fields.put("colsName", "FNAME from STD_HL7_DOC; --");
		assertThatThrownBy(() -> dao.getHL7DemogrphicFieldMultiValues(fields)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		assertThatThrownBy(() -> dao.getHL7DOBDemogrphicFieldMultiValues(fields)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	// ---- the Oracle-only statements, the procedure and the per-item inserts, without a database --------------------------

	@SuppressWarnings("unchecked")
	@Test
	void oracleOnlyStatementsKeepTheirSql() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		HL7DocumentDAO dao = new HL7DocumentDAO(mocked, null);
		assertThat(dao.getHL7LabTestSequences(3)).isEmpty();
		assertThat(dao.getHL7Diagnosis(between("01/01/2024", "12/31/2024"))).isEmpty();
		Map<String, Object> statuses = new HashMap<>();
		statuses.put("p_process_status", "5,60");
		assertThat(dao.getTotalCountForEachCPS(statuses)).isEmpty();
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(3)).list(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(0).trim()).isEqualTo("SELECT raw_hl7_lab_test_seq.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1");
		assertThat(parameters.getAllValues().get(0)).containsExactly(3);
		assertThat(sql.getAllValues().get(1)).contains("from raw_hl7_adt_dg1 ADT, STD_HL7_DOC STD").contains("and STD.ccd_match_doc_id = ?1")
				.contains("and STD.DATE_RECEIVED >= TO_DATE(?2, 'MM/DD/YYYY')").contains("and ADT.DX_DATE_TEXT is not null").contains("ORDER BY diagnosisDate");
		assertThat(parameters.getAllValues().get(1)).containsExactly(100, "01/01/2024", "12/31/2024");
		assertThat(sql.getAllValues().get(2)).contains("SELECT Trim(regexp_substr(?1, '[^,]+', 1, level)) process_status")
				.contains("CONNECT BY regexp_substr(?2, '[^,]+', 1, level) IS NOT NULL").contains("WHERE a.current_process_status(+) = b.process_status");
		assertThat(parameters.getAllValues().get(2)).extracting(p -> ((Param) p).value()).containsExactly("5,60", "5,60");

		assertThat(dao.getStdHL7Doc(100)).isNull();
		assertThat(dao.getFilteredHL7Doc(between("01/01/2024", "12/31/2024"))).isNull();
		verify(mocked, org.mockito.Mockito.times(2)).one(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(3)).contains("FIRST_VALUE (std.fname IGNORE NULLS) OVER ( PARTITION BY std.ccd_match_doc_id")
				.contains("where std.ccd_match_doc_id = ?1 AND STD.CURRENT_PROCESS_STATUS = 10").contains("WHERE rownum < 2");
		assertThat(parameters.getAllValues().get(3)).containsExactly(100);
		assertThat(sql.getAllValues().get(4)).contains("and STD.DATE_RECEIVED <= TO_DATE(?3, 'MM/DD/YYYY')").contains("WHERE rownum < 2");
		assertThat(parameters.getAllValues().get(4)).containsExactly(100, "01/01/2024", "12/31/2024");

		dao.updateCurrentProcessStatusInRaw();
		dao.deleteDuplicatesRowsInRawHL7Insuarance();
		verify(mocked, org.mockito.Mockito.times(2)).update(sql.capture(), any(Object[].class));
		assertThat(sql.getAllValues().get(5)).contains("DECLARE").contains("BULK COLLECT INTO v_doc_id,v_status LIMIT  10000")
				.contains("FORALL indx IN INDICES OF v_doc_id").contains("AND  R.PIDMPI=C.MPI (+);").contains("CLOSE LCU;");
		assertThat(sql.getAllValues().get(6)).contains("WHERE rowid NOT IN (").contains("SELECT Min(rowid) row_id FROM raw_hl7_insurance GROUP BY plan_id, company_id");
	}

	@Test
	void matchProcedureBindsItsValuesAndPutsTheOutputsBack() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.call(any(), any(CallParam[].class))).thenReturn(Arrays.asList(null, null, "0", "matched 3"));
		HL7DocumentDAO dao = new HL7DocumentDAO(mocked, null);
		Map<String, Object> input = new HashMap<>();
		input.put("p_days", "7");                                                         // the scheduler's texts, bound as texts (as MyBatis did)
		input.put("p_process_status", "5,60");
		dao.updateDataSTDMatchorNonMatchtoCCD(input);
		ArgumentCaptor<String> procedure = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<CallParam[]> parameters = ArgumentCaptor.forClass(CallParam[].class);
		verify(mocked).call(procedure.capture(), parameters.capture());
		assertThat(procedure.getValue()).isEqualTo("match_ccd_hl7_records");
		CallParam[] call = parameters.getValue();
		assertThat(call).hasSize(4);
		assertThat(call[0].mode()).isEqualTo(ParameterMode.IN);
		assertThat(call[0].type()).isEqualTo(String.class);
		assertThat(call[0].value()).isEqualTo("7");
		assertThat(call[1].value()).isEqualTo("5,60");
		assertThat(call[2].mode()).isEqualTo(ParameterMode.OUT);
		assertThat(call[2].type()).isEqualTo(String.class);
		assertThat(call[3].mode()).isEqualTo(ParameterMode.OUT);
		assertThat(input).containsEntry("p_code_out", "0").containsEntry("p_error_msg", "matched 3");

		input.put("p_days", null);
		dao.updateDataSTDMatchorNonMatchtoCCD(input);
		verify(mocked, org.mockito.Mockito.times(2)).call(procedure.capture(), parameters.capture());
		assertThat(parameters.getValue()[0].type()).isEqualTo(Integer.class);               // a null: the mapper's INTEGER
		assertThat(parameters.getValue()[0].value()).isNull();
	}

	/** A former {@code foreach ... union all} insert: the method, its list key and a sample item. */
	private record ItemInsert(String sql, String listKey, Function<Map<String, Object>, Integer> method, Map<String, Object> item) {
	}

	private static List<ItemInsert> itemInserts(HL7DocumentDAO dao) {
		return List.of(
				new ItemInsert(HL7DocumentSql.RAW_HL7_OBR_ITEM, "hl7LabTestList", dao::insertRawHL7OBR,
						Map.of("hl7labTestId", 10, "uphnTranxInId", 5, "obrSetId", 1, "obrTestCode", "T1", "obrCollectionDate", new java.util.Date(Timestamp.valueOf("2024-01-05 10:00:00").getTime()))),
				new ItemInsert(HL7DocumentSql.RAW_HL7_OBX_ITEM, "hl7LabResultList", dao::insertRawHL7OBX,
						Map.of("hl7labTestId", 10, "uphnTranxInId", 5, "obxSetId", 1, "obxObservationResultText", "500")),
				new ItemInsert(HL7DocumentSql.DG1_ITEM, "hl7DG1List", dao::insertDG1List, Map.of("uphnId", 5, "dxCodeId", "B20")),
				new ItemInsert(HL7DocumentSql.ORC_ITEM, "hl7ORCList", dao::insertORCList, Map.of("uphnId", 5, "orderControl", "NW", "rxoMedName", "truvada")),
				new ItemInsert(HL7DocumentSql.INS_ITEM, "hl7INSList", dao::insertINSList, Map.of("uphnId", 5, "setId", 1, "companyName", "blue", "groupName", "g")));
	}

	@Test
	void perItemInsertsBindOneItemAtATime() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.update(any(), any(Object[].class))).thenReturn(1);
		HL7DocumentDAO dao = new HL7DocumentDAO(mocked, null);
		int inserts = 0;
		for (ItemInsert insert : itemInserts(dao)) {
			Map<String, Object> map = new HashMap<>();
			map.put("hl7DocId", 1);
			map.put(insert.listKey(), List.of(insert.item(), insert.item()));
			assertThat(insert.method().apply(map)).as(insert.listKey()).isEqualTo(2);     // one insert per item
			inserts += 2;
			map.put(insert.listKey(), List.of());
			assertThatThrownBy(() -> insert.method().apply(map)).as(insert.listKey()).isInstanceOf(InvalidDataAccessResourceUsageException.class);
		}
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(inserts)).update(sql.capture(), parameters.capture());
		List<ItemInsert> expected = itemInserts(dao);
		for (int i = 0; i < inserts; i++) {
			assertThat(sql.getAllValues().get(i)).isEqualTo(expected.get(i / 2).sql());
			CCDDocumentDaoTest.checkItemInsert(sql.getAllValues().get(i), parameters.getAllValues().get(i));
		}
		Object[] obr = parameters.getAllValues().get(0);                                 // the item's key, the map's hl7DocId, then the item's values
		assertThat(((Param) obr[0]).value()).isEqualTo(10);
		assertThat(((Param) obr[1]).value()).isEqualTo(1);
		assertThat(((Param) obr[6]).value()).isInstanceOf(java.sql.Date.class).hasToString("2024-01-05");   // jdbcType=DATE: the day only
		assertThat(((Param) obr[7]).value()).isNull();
		Object[] obx = parameters.getAllValues().get(2);                                 // hl7DocId first (the key comes from the sequence)
		assertThat(((Param) obx[0]).value()).isEqualTo(1);
		assertThat(((Param) obx[1]).value()).isEqualTo(10);
		assertThat(((Param) obx[13]).value()).isEqualTo("500");
		Object[] ins = parameters.getAllValues().get(8);
		assertThat(((Param) ins[5]).value()).isEqualTo("blue");
		assertThat(((Param) ins[15]).value()).isEqualTo("g");
	}

	@Test
	void countsAreJsonObjects() {
		NativeStatements mocked = mock(NativeStatements.class);
		JSONObject count = new JSONObject();
		count.put("PROCESS_STATUS", "5");
		count.put("COUNT", 2);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>(List.of(count)));
		HL7DocumentDAO dao = new HL7DocumentDAO(mocked, null);
		Map<String, Object> input = new HashMap<>();
		input.put("p_process_status", "5");
		assertThat(dao.getTotalCountForEachCPS(input)).containsExactly(count);
	}
}
