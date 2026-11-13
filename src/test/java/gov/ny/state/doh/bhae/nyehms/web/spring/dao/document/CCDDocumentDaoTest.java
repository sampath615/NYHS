package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabResultVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDLabTestVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDNameVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonAddressVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDPersonDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.RawCCDProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd.StdCCDDocVo;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.RowMapper;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The CCD document DAO: the JPQL reads, the raw and standardized writes, the flag updates, the UPHN rows and the
 * viewer lists against an in-memory H2 database in Oracle mode (db/ccd.sql), next to their data; the Oracle-only
 * statements (sequences by {@code CONNECT BY}, {@code (+)} joins) by their SQL text, and the former
 * {@code foreach ... union all} inserts by their one-item SQL and bound values, without a database (H2 cannot type a
 * {@code select ?1, ?2 ... from dual}).
 */
@SpringJUnitConfig(CCDDocumentDaoTest.TestConfig.class)
public class CCDDocumentDaoTest {                                                         // public: H2 calls the FN_TO_DATE stand-in reflectively

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, CCDDocumentDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("ccddao;MODE=Oracle").generateUniqueName(false)
					.addScript("db/ccd.sql").build();
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

	@Autowired CCDDocumentDAO dao;
	@Autowired NativeStatements statements;

	/** A NUMBER read by getObject is a BigDecimal (H2 renders 100 as 1E+2): compared numerically. */
	static void assertNumber(Object actual, long expected) {
		assertThat(new java.math.BigDecimal(String.valueOf(actual))).isEqualByComparingTo(java.math.BigDecimal.valueOf(expected));
	}

	// ---- the document viewer's document (JPQL) ------------------------------------------------------------------------

	@Test
	void standardizedDocumentsAreReadWithTheirAssociation() {
		RawCCDPersonDocVO raw = dao.getCCDDoc(100);
		assertThat(raw.getDOCUMENT_ID()).isEqualTo(100);
		assertThat(raw.getUPHN_TRANX_IN_ID()).isEqualTo(12);                             // from the raw row
		assertThat(raw.getMPI()).isEqualTo("MPI1");
		assertThat(raw.getGENDER()).isEqualTo("M");
		assertThat(raw.getName()).isNull();                                               // FNAME... are no properties of the VO: unmapped, as before
		assertThat(dao.getCCDDoc(101).getUPHN_TRANX_IN_ID()).isZero();                    // no raw row: the int stays 0
		assertThat(dao.getCCDDoc(999)).isNull();

		StdCCDDocVo std = dao.getStdCCDDoc(100);
		assertThat(std.getDobText()).isEqualTo("01/02/1980");
		assertThat(std.getEthinicity()).isEqualTo("NOT HISPANIC");
		assertThat(std.getRaceWhite()).isEqualTo("Y");
		assertThat(std.getPersonAddress()).isEqualTo("1 MAIN ST");
		assertThat(std.getPersonCity()).isEqualTo("ALBANY");
		assertThat(std.getPersonAddressId()).isEqualTo(77);
		assertThat(std.getCreateDate()).hasToString("2024-01-02");                          // a java.sql.Date property: the day only, as before
		assertThat(std.getEharsImportDate()).hasToString("2024-02-03");
		assertThat(std.getStateno()).isEqualTo("S1");                                     // the current association only
		assertThat(std.getDISCARD_STATUS()).isEqualTo("N");
		assertThat(std.getMpi()).isEqualTo("MPI1");
		StdCCDDocVo other = dao.getStdCCDDoc(101);
		assertThat(other.getStateno()).isNull();
		assertThat(other.getPersonAddressId()).isZero();
		assertThat(dao.getStdCCDDoc(999)).isNull();
	}

	// ---- the loader's writes ------------------------------------------------------------------------------------------

	@Test
	@Transactional                                                                       // rolled back: the other tests count on the script's rows
	void rawAndStandardizedWritesRunAsTheMapperDid() {
		RawCCDPersonDocVO vo = new RawCCDPersonDocVO();
		vo.setDOCUMENT_ID(200);
		vo.setUPHN_TRANX_IN_ID(13);
		vo.setMPI("MPI200");
		RawCCDNameVO name = new RawCCDNameVO();
		name.setFirstName("Al");
		vo.setName(name);
		vo.setCURRENT_PROCESS_STATUS(1);
		vo.setHieName("HIE");
		vo.setPayloadname("p.xml");
		dao.insertRawCcdDocData(vo);
		Map<String, Object> row = statements.one("select * from RAW_CCD_DOC where DOCUMENT_ID = 200", Rows.map());
		assertThat(row).containsEntry("FNAME", "Al").containsEntry("MPI", "MPI200").containsEntry("HIE_NAME", "HIE").containsEntry("PAYLOADNAME", "p.xml")
				.doesNotContainKey("LNAME");                                                 // a null on the path: a VARCHAR null
		assertNumber(row.get("UPHN_TRANX_IN_ID"), 13);

		RawCCDLabResultVO result = new RawCCDLabResultVO();
		result.setCcdLabTestId(50);
		result.setCcdDocID(200);
		result.setComponentCode("C9");
		result.setResultValue(" 7 ");
		result.setUphnId(13);
		dao.insertRawCcdLabResult(result);
		row = statements.one("select * from RAW_CCD_LAB_RESULT where CCD_DOC_ID = 200", Rows.map());
		assertNumber(row.get("CCD_LAB_RESULT_ID"), 1000);                       // from RAW_CCD_LAB_RESULT_SEQ
		assertThat(row).containsEntry("COMPONENT_CODE", "C9").containsEntry("RESULT_VALUE", " 7 ");
		assertNumber(row.get("CCD_LAB_TEST_ID"), 50);

		Map<Object, Object> std = new HashMap<>();
		std.put("DOCUMENT_ID", 200);
		std.put("FNAME", " al ");
		std.put("LNAME", "bee");
		std.put("GENDER", "m");
		std.put("PERSON_ADDRESS_ID", 5);
		std.put("CURRENT_PROCESS_STATUS", 1);
		std.put("CREATE_DATE", new java.util.Date(Timestamp.valueOf("2024-05-06 07:08:09").getTime()));   // jdbcType=DATE: the day only
		std.put("MPI", "MPI200");
		std.put("UPHN_TRANX_IN_ID", 13);
		std.put("HIE_NAME", "HIE");                                                     // USEABLE_PERIOD_LOW / DOB_TEXT left null: H2's substr(x, 0, 8) gives 7 characters, Oracle's 8
		assertThat(dao.insertCCDFromRAWToSTD(std)).isEqualTo(1);
		row = statements.one("select * from STD_CCD_DOC where DOCUMENT_ID = 200", Rows.map());
		assertThat(row).containsEntry("FNAME", "AL").containsEntry("LNAME", "BEE").containsEntry("GENDER", "M").containsEntry("MPI", "MPI200")
				.containsEntry("CREATE_DATE", Timestamp.valueOf("2024-05-06 00:00:00")).doesNotContainKey("DOB_TEXT");
		assertNumber(row.get("PERSON_ADDRESS_ID"), 5);

		Map<Object, Object> diagnosis = new HashMap<>();
		diagnosis.put("DIAGNOSIS_ID", 9);
		diagnosis.put("ENCOUNTER_DOC_ID", 70);
		diagnosis.put("DIAGNOSIS_TEXT", "x");
		diagnosis.put("CCD_DOC_ID", 200);
		assertThat(dao.insertDiagnosisFromRAWToSTD(diagnosis)).isEqualTo(1);
		row = statements.one("select * from STD_CCD_DIAGNOSIS where DIAGNOSIS_ID = 9", Rows.map());
		assertThat(row).containsEntry("DIAGNOSIS_TEXT", "x").doesNotContainKey("ENCOUNTER_DATE");

		Map<Object, Object> flag = new HashMap<>();
		flag.put("tableName", "RAW_CCD_DIAGNOSIS");
		flag.put("statusCode", 'Y');                                                      // a char, as the service gives it
		flag.put("diagnosisID", 1);
		assertThat(dao.updateRawReviewedStatusCode(flag)).isEqualTo(1);
		flag.put("statusCode", 'M');
		assertThat(dao.updateRawDiagnosisMovedToSTDStatusCode(flag)).isEqualTo(1);
		assertThat(statements.one("select REVIEWED, MOVED_TO_STD from RAW_CCD_DIAGNOSIS where DIAGNOSIS_ID = 1", Rows.map())).containsEntry("REVIEWED", "Y")
				.containsEntry("MOVED_TO_STD", "M");
		flag.put("tableName", "RAW_CCD_DIAGNOSIS; drop table RAW_CCD_DIAGNOSIS");
		assertThatThrownBy(() -> dao.updateRawReviewedStatusCode(flag)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<Object, Object> processed = new HashMap<>();
		processed.put("tableName", "RAW_CCD_DOC");
		processed.put("processDate", new java.util.Date());
		processed.put("document_ID", 200);
		assertThat(dao.updateRawProcessDateInp(processed)).isEqualTo(1);
		assertThat(statements.one("select PROCESS_DATE from RAW_CCD_DOC where DOCUMENT_ID = 200", Rows.scalar(Timestamp.class))).isNotNull();
		processed.put("tableName", "STD_CCD_DOC");                                         // not a raw document table
		assertThatThrownBy(() -> dao.updateRawProcessDateInp(processed)).isInstanceOf(InvalidDataAccessApiUsageException.class);

		Map<String, Object> uphn = new HashMap<>();
		uphn.put("p_fileName", "b.xml");
		uphn.put("p_hieName", "HIE");
		uphn.put("p_dateCreated", java.sql.Date.valueOf("2024-02-03"));
		dao.addUphnRecord(uphn);
		assertThat(uphn).containsEntry("uphnId", 500L);                                   // from UPHN_TRANX_IN_SEQ_ID
		row = statements.one("select * from UPHN_TRANX_IN where UPHN_TRANX_IN_ID = 500", Rows.map());
		assertThat(row).containsEntry("PAYLOADNAME", "b.xml").containsEntry("ORGNAME", "HIE").containsEntry("TOPIC", "DEERPROD")
				.containsEntry("RECEIVEDTIME", Timestamp.valueOf("2024-02-03 00:00:00"));
		assertThat(row.get("LASTUPDATETIME")).as("SYSDATE").isNotNull();
		uphn.put("p_ccdDocument", "<ClinicalDocument/>".getBytes());
		dao.updateUphnRecord(uphn);
		assertThat(statements.one("select OCTET_LENGTH(PAYLOADBINARYCONTENT) from UPHN_TRANX_IN where UPHN_TRANX_IN_ID = 500", Rows.scalar(Long.class))).isEqualTo(19L);

		Map<String, Object> child = new HashMap<>();
		child.put("docId", "100");
		child.put("table", "RAW_CCD_PERSON_ADDRESS");
		child.put("parameter", "ccd_doc_id");
		assertThat(dao.getChildEntryData(child)).hasSize(3);
		child.put("table", "");                                                             // an unknown page: failed before (no table), refused now
		assertThatThrownBy(() -> dao.getChildEntryData(child)).isInstanceOf(InvalidDataAccessApiUsageException.class);
		child.put("table", "RAW_CCD_PERSON_ADDRESS");
		child.put("parameter", "1 = 1 or ccd_doc_id");
		assertThatThrownBy(() -> dao.getChildEntryData(child)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	// ---- the document viewer's lists ------------------------------------------------------------------------------------

	@Test
	void viewerListsRunOnH2() {
		List<RawCCDPersonAddressVO> addresses = dao.getCCDAddressList(100);
		assertThat(addresses).hasSize(1);                                                 // distinct; the empty address left out
		assertThat(addresses.get(0).getPersonAddress()).isEqualTo("1 MAIN ST APT 2");
		assertThat(addresses.get(0).getCity()).isEqualTo("ALBANY");
		assertThat(addresses.get(0).getZip()).isEqualTo("12201");

		List<RawCCDProviderVO> providers = dao.getCCDProvidersList(100);
		assertThat(providers).hasSize(1);
		assertThat(providers.get(0).getProviderFullName()).isEqualTo("ANN  SMITH");       // the empty middle name keeps its spaces, as before
		assertThat(providers.get(0).getProviderAddress()).isEqualTo("2 ELM ST  TROY NY 12180");
		assertThat(providers.get(0).getPhone()).isEqualTo("5554444");
		assertThat(providers.get(0).getStartDateText()).isNull();

		assertThat(dao.getCCDProblemsList(100)).singleElement().satisfies(problem -> {
			assertThat(problem.getFacilityName()).isEqualTo("CLINIC A");
			assertThat(problem.getProviderFullName()).isEqualTo("ANN SMITH");
			assertThat(problem.getProblemCode()).isEqualTo("B20");
			assertThat(problem.getEncounterId()).isEqualTo("E1");
		});
		assertThat(dao.getDiagnosisList(70)).singleElement().satisfies(diagnosis -> {        // the empty diagnosis left out
			assertThat(diagnosis.getDiagnosisText()).isEqualTo("HIV DISEASE");
			assertThat(diagnosis.getDiagnosisCode()).isEqualTo("B20");
		});
		assertThat(dao.getDiagnosisList(71)).isEmpty();

		List<RawCCDLabTestVO> tests = dao.getCCDLabTestsList(100);
		assertThat(tests).extracting(RawCCDLabTestVO::getTestCode).containsExactly("1234-5", "5678-9");   // by date (none), then code
		assertThat(tests.get(0).getTestCodeDisplayName()).isEqualTo("VIRAL LOAD");         // the comments when there is no display name
		assertThat(tests.get(1).getTestCodeDisplayName()).isEqualTo("CD4 COUNT");          // the leading dot removed
		assertThat(tests.get(1).getSpecimen()).isEqualTo("BLOOD");
		assertThat(tests.get(1).getCcdLabTestId()).isEqualTo(50);
		assertThat(dao.getLabResultsList(50)).singleElement().satisfies(result -> {
			assertThat(result.getComponentCode()).isEqualTo("C1");
			assertThat(result.getResultValue()).isEqualTo("500");
			assertThat(result.getResultStatus()).isEqualTo("FINAL");
		});
		assertThat(dao.getCCDMedicationsList(100)).singleElement().satisfies(medication -> {
			assertThat(medication.getOrderedMedicationName()).isEqualTo("TRUVADA");
			assertThat(medication.getProviderFullName()).isEqualTo("ANN SMITH");
		});
		assertThat(dao.getCCDPayersList(100)).singleElement().satisfies(payer -> {
			assertThat(payer.getPolicyName()).isEqualTo("BLUE PLAN");
			assertThat(payer.getMemberId()).isEqualTo("1234");
			assertThat(payer.getMemberFullName()).isEqualTo("JOHN DOE");
		});

		List<Map<String, Object>> counts = dao.getAllCcdCountIndex();
		assertThat(counts).hasSize(1);
		Map<String, Object> count = counts.get(0);
		assertNumber(count.get("DOCUMENT_ID"), 100);
		assertNumber(count.get("ADDR"), 3);
		assertNumber(count.get("PROV"), 2);
		assertNumber(count.get("PAYER"), 1);
		assertNumber(count.get("LABS"), 2);
		assertNumber(count.get("ENCOUNTER"), 1);
		assertNumber(count.get("MED"), 1);
		assertNumber(count.get("DIAG"), 2);
		assertNumber(count.get("LAB_RES"), 1);
		assertNumber(count.get("PROB"), 1);
	}

	// ---- the Oracle-only statements and the per-item inserts, without a database ---------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	void oracleOnlyStatementsKeepTheirSql() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.list(any(), any(), any(Object[].class))).thenReturn(new ArrayList<>());
		CCDDocumentDAO dao = new CCDDocumentDAO(mocked, null);
		assertThat(dao.getEncounterSequences(3)).isEmpty();
		assertThat(dao.getLabTestSequences(2)).isEmpty();
		assertThat(dao.getNewRawCCDList()).isEmpty();
		assertThat(dao.getCCDEncountersList(100)).isEmpty();
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(4)).list(sql.capture(), any(RowMapper.class), parameters.capture());
		assertThat(sql.getAllValues().get(0).trim()).isEqualTo("SELECT RAW_CCD_ENCOUNTER_SEQ.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1");
		assertThat(parameters.getAllValues().get(0)).containsExactly(3);
		assertThat(sql.getAllValues().get(1).trim()).isEqualTo("SELECT RAW_CCD_LAB_TEST_SEQ.NEXTVAL FROM dual CONNECT BY LEVEL <= ?1");
		assertThat(parameters.getAllValues().get(1)).containsExactly(2);
		assertThat(sql.getAllValues().get(2)).contains("ROW_NUMBER () OVER (PARTITION BY c.ccd_doc_id ORDER BY c.address_use DESC, c.address_seq_no) rn")
				.contains("WHERE r.document_id = c.ccd_doc_id(+)").contains("AND r.current_process_status IN (1,4)").contains("WHERE rn = 1");
		assertThat(sql.getAllValues().get(3)).contains("WHERE a.text = b.FACILITY_CODE(+) order by a.START_DATE").contains("FROM RAW_CCD_ENCOUNTER a WHERE CCD_DOC_ID = ?1");
		assertThat(parameters.getAllValues().get(3)).containsExactly(100);
	}

	/** A former {@code foreach ... union all} insert: the method, its list key and a sample item. */
	private record ItemInsert(String sql, String listKey, Function<Map<String, Object>, Integer> method, Map<String, Object> item) {
	}

	private static List<ItemInsert> itemInserts(CCDDocumentDAO dao) {
		Map<String, Object> name = Map.of("firstName", "Ann", "lastName", "Smith");
		Map<String, Object> address = Map.of("street1", "1 Main", "city", "Albany", "zip", "12201");
		return List.of(
				new ItemInsert(CCDDocumentSql.CCD_ADDRESS_ITEM, "addressesList", dao::insertCcdAddressList,
						Map.of("theCCDAddrObject", Map.of("ccdUse", "HP", "street1", "1 Main"), "useableStartTime", "20200101", "uphnId", 5, "addressSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_PROVIDER_ITEM, "providersList", dao::insertCcdProvidersList,
						Map.of("providerTypeCode", "PCP", "providerName", name, "address", address, "phone", "555", "uphnId", 5, "providerSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_PAYER_ITEM, "payersList", dao::insertCcdPayersList,
						Map.of("policyName", "blue", "policyAddress", address, "memberName", name, "memberAddress", address, "holderName", name, "holderAddress", address,
								"uphnId", 5, "payerSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_LAB_TEST_ITEM, "LabsList", dao::insertCcdLabTestList,
						Map.of("ccdLabTestId", 50, "testCode", "1234-5", "testDate", "20200101", "uphnId", 5, "testSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_LAB_RESULT_ITEM, "labResultsList", dao::insertCcdLabResultsList,
						Map.of("ccdLabTestId", 50, "componentCode", "C1", "resultValue", "500", "uphnId", 5, "testSeqNo", 1, "resultSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_MED_ITEM, "medsList", dao::insertCcdMedsList,
						Map.of("orderedMedicationName", "truvada", "orderingProvName", name, "uphnId", 5, "medSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_PROBLEM_ITEM, "problemsList", dao::insertCcdProblemsList,
						Map.of("problemCode", "B20", "providerName", name, "uphnId", 5, "problemSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_ENCOUNTER_ITEM, "encounterList", dao::insertCcdEncounterList,
						Map.of("encounterDocId", 70, "encounterId", "E1", "providerName", name, "uphnId", 5, "encounterSeqNo", 1)),
				new ItemInsert(CCDDocumentSql.CCD_DIAGNOSIS_ITEM, "diagnosisList", dao::insertCcdDiagnosisList,
						Map.of("encounterDocId", 70, "diagnosisCode", "B20", "uphnId", 5, "ccdDocID", 100, "encounterSeqNo", 1, "diagnosisSeqNo", 1)));
	}

	@Test
	void perItemInsertsBindOneItemAtATime() {
		NativeStatements mocked = mock(NativeStatements.class);
		when(mocked.update(any(), any(Object[].class))).thenReturn(1);
		CCDDocumentDAO dao = new CCDDocumentDAO(mocked, null);
		int inserts = 0;
		for (ItemInsert insert : itemInserts(dao)) {
			Map<String, Object> map = new HashMap<>();
			map.put("ccdDocId", 100);
			map.put(insert.listKey(), List.of(insert.item(), insert.item()));
			assertThat(insert.method().apply(map)).as(insert.listKey()).isEqualTo(2);     // one insert per item
			inserts += 2;
			map.put(insert.listKey(), List.of());
			assertThatThrownBy(() -> insert.method().apply(map)).as(insert.listKey()).isInstanceOf(InvalidDataAccessResourceUsageException.class);
			map.remove(insert.listKey());
			assertThatThrownBy(() -> insert.method().apply(map)).as(insert.listKey()).isInstanceOf(InvalidDataAccessResourceUsageException.class);
		}
		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> parameters = ArgumentCaptor.forClass(Object[].class);
		verify(mocked, org.mockito.Mockito.times(inserts)).update(sql.capture(), parameters.capture());
		List<ItemInsert> expected = itemInserts(dao);
		for (int i = 0; i < inserts; i++) {
			assertThat(sql.getAllValues().get(i)).isEqualTo(expected.get(i / 2).sql());
			checkItemInsert(sql.getAllValues().get(i), parameters.getAllValues().get(i));
		}
		Object[] address = parameters.getAllValues().get(0);                             // ccdDocId from the map, then the item's paths
		assertThat(((Param) address[0]).value()).isEqualTo(100);
		assertThat(((Param) address[1]).value()).isEqualTo("HP");
		assertThat(((Param) address[2]).value()).isEqualTo("1 Main");
		assertThat(((Param) address[3]).value()).isNull();                                 // street2: a null on the path
		assertThat(((Param) address[10]).value()).isEqualTo(5);
		assertThat(((Param) address[11]).value()).isEqualTo(1);
		Object[] labTest = parameters.getAllValues().get(6);                             // the item's key first, then ccdDocId
		assertThat(((Param) labTest[0]).value()).isEqualTo(50);
		assertThat(((Param) labTest[1]).value()).isEqualTo(100);
		assertThat(((Param) labTest[2]).value()).isEqualTo("1234-5");
		Object[] diagnosis = parameters.getAllValues().get(16);                          // no ccdDocId: the item's own
		assertThat(((Param) diagnosis[0]).value()).isEqualTo(70);
		assertThat(((Param) diagnosis[6]).value()).isEqualTo(100);
	}

	private static final Pattern ITEM_INSERT = Pattern.compile(
			"(?is)\\s*insert\\s+into\\s+(\\w+)\\s*\\((.*?)\\)\\s*(?:select\\s+(?:(\\w+)\\.NEXTVAL\\s*,\\s*)?d\\.\\*\\s+from\\s*\\(\\s*)?select\\s+(.*?)\\s+from\\s+dual\\s*(?:\\)\\s*d)?\\s*");
	private static final Pattern PLACEHOLDER = Pattern.compile("\\?(\\d+)");

	/** The one-item SQL has a value per column (plus the sequence) and a placeholder ?1..?n, in order, per bound value. */
	static void checkItemInsert(String sql, Object[] parameters) {
		Matcher parsed = ITEM_INSERT.matcher(sql);
		assertThat(parsed.matches()).as(sql).isTrue();
		String table = parsed.group(1);
		int columns = parsed.group(2).split(",").length;
		int values = DocumentEditLabsDaoTest.splitTopLevel(parsed.group(4)).size();
		assertThat(values + (parsed.group(3) == null ? 0 : 1)).as(table + " values for its columns").isEqualTo(columns);
		List<Integer> placeholders = new ArrayList<>();
		Matcher placeholder = PLACEHOLDER.matcher(sql);
		while (placeholder.find()) {
			placeholders.add(Integer.parseInt(placeholder.group(1)));
		}
		assertThat(placeholders).as(table + " placeholders").hasSize(parameters.length);
		for (int i = 0; i < placeholders.size(); i++) {
			assertThat(placeholders.get(i)).as(table + " placeholder " + i).isEqualTo(i + 1);
			assertThat(parameters[i]).as(table + " value " + i).isInstanceOf(Param.class);
		}
	}
}
