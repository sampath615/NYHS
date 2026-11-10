package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.EcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.LabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.StdEcrlsLabDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The lab document DAO against an in-memory H2 database in Oracle mode (db/labdoc-schema.sql, generated from the former
 * mapper's insert statements, and db/labdoc.sql). The copies and imports are checked generically: every generated insert
 * runs with a value for each of its columns and the stored row is compared column by column with what the statement's
 * own expression promises (the value as it is, {@code trim(upper(...))}, {@code TO_DATE}, {@code SYSDATE}, {@code null});
 * the reads, the JPQL statements and the dynamic SQL are checked on sample rows, two of them next to their original SQL.
 */
@SpringJUnitConfig(LabDocumentDaoTest.TestConfig.class)
public class LabDocumentDaoTest {          // public: H2 calls the TO_CLOB stand-in below by reflection

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, LabDocumentDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("labdoc;MODE=Oracle").generateUniqueName(false)
					.addScript("db/labdoc-schema.sql").addScript("db/labdoc.sql").build();
		}
	}

	/** H2 stand-in for Oracle's TO_CLOB. */
	public static String toClob(String text) {
		return text;
	}

	@Autowired LabDocumentDAO dao;
	@Autowired NativeStatements statements;
	@Autowired DataSource dataSource;

	// ---- the lab document screen -----------------------------------------------------------------------------------

	@Test
	void labDocumentScreenReadsTheHivAndTheStdTables() {
		Map<Object, Object> map = new HashMap<>();
		map.put("raw_table_name", "raw_hiv_eclrs_doc");         // as the service passes them, in lower case
		map.put("std_table_name", "std_hiv_eclrs_doc");
		map.put("docId", 100);
		StdEcrlsLabDocVO doc = dao.getLabDoc(map);

		assertThat(doc.getDOCUMENT_ID()).isEqualTo(100);
		assertThat(doc.getLAB_SRC_ID()).isEqualTo(5L);
		assertThat(doc.getFNAME()).isEqualTo("JOHN");
		assertThat(doc.getAGE()).isEqualTo(42);                                    // text column into an Integer property
		assertThat(doc.getRAWPERSONADDRESS()).isEqualTo("A B");                    // RAW1.PIDADDRESSLINE1 || ' ' || RAW1.PIDADDRESSLINE2
		assertThat(doc.getRAWPROVADDRESS()).isEqualTo("P1 P2");
		assertThat(doc.getRAWFACILITYADDRESS()).isEqualTo("F1 F2");
		assertThat(doc.getPFI()).isEqualTo("STDPFI");                               // the first of two PFI columns (STD1.PFI, not RAW1.MSHLABPFI)
		assertThat(doc.getMEDICAID_NUMBER()).isEqualTo("M1");
		assertThat(doc.getTEST_RESULT()).isEqualTo("POS");
		assertThat(doc.getCOLLECTION_DATE()).isEqualTo(java.sql.Date.valueOf("2021-03-04"));  // java.sql.Date: the day only
		assertThat(doc.getNYC_SHIP_DATE()).isEqualTo(java.sql.Date.valueOf("2021-05-06"));
		assertThat(doc.getMATCH_CD()).isNull();
		assertThat(doc.getSTATENO()).isEqualTo("S1");                               // the current association, not the disassociated one
		assertThat(doc.getDISCARD_STATUS()).isEqualTo("N");
		assertThat(doc.getPARENT_DOCUMENT_ID()).isEqualTo(90);
		assertThat(doc.getRAW_OBXOBSERVATIONRESULTTEXT()).isEqualTo("DETECTED");
		assertThat(doc.getRAW_OBXNOTE()).isEqualTo("NOTE");

		map.put("raw_table_name", "raw_std_eclrs_doc");
		map.put("std_table_name", "std_std_eclrs_doc");
		map.put("docId", 200);
		StdEcrlsLabDocVO std = dao.getLabDoc(map);
		assertThat(std.getFNAME()).isEqualTo("JANE");
		assertThat(std.getRAWPERSONADDRESS()).isEqualTo("S1 S2");
		assertThat(std.getSTATENO()).isEqualTo("S2");
		assertThat(std.getPARENT_DOCUMENT_ID()).isEqualTo(190);

		map.put("docId", 999);
		assertThat(dao.getLabDoc(map)).isNull();

		map.put("raw_table_name", "raw_hiv_eclrs_doc; drop table person");
		assertThatThrownBy(() -> dao.getLabDoc(map)).isInstanceOf(InvalidDataAccessApiUsageException.class);
	}

	@Test
	void hivDocumentReadsEveryColumnIntoTheVoAndIntoMaps() {
		EcrlsLabDocVO vo = dao.getHivLabDoc(100);
		assertThat(vo.getDOCUMENT_ID()).isEqualTo(100);
		assertThat(vo.getCURRENT_PROCESS_STATUS()).isEqualTo(11);
		assertThat(vo.getMSHLABPFI()).isEqualTo("RAWPFI");
		assertThat(vo.getPIDDATEOFBIRTH()).isEqualTo(Timestamp.valueOf("1980-01-02 00:00:00"));
		assertThat(vo.getPIDADDRESSLINE2()).isEqualTo("B");
		assertThat(vo.getPIDCITY()).isEmpty();                                       // a null column: the VO's "" initial value stays, as before
		assertThat(dao.getHivLabDoc(999)).isNull();

		Map<String, Object> raw = dao.getRawDataAsObject(100);
		assertNumber(raw.get("DOCUMENT_ID"), 100);                                  // getObject types (a BigDecimal), as before
		assertThat(raw.get("OBXNOTE")).isEqualTo("NOTE");
		assertThat(raw).doesNotContainKey("PIDCITY");                               // nulls left out
		assertThat(dao.getRawDataAsObject(999)).isNull();

		Map<String, Object> std = dao.getStdDataAsObject(100);
		assertThat(std.get("COLLECTION_DATE")).isEqualTo(Timestamp.valueOf("2021-03-04 10:11:12"));
		assertThat(std.get("FNAME")).isEqualTo("JOHN");

		Map<Object, Object> ids = new HashMap<>();
		ids.put("documentIds", new int[] {101, 100, 999});                         // a primitive array, as the caller passes it
		List<EcrlsLabDocVO> docs = dao.getRawDocumentsData(ids);
		assertThat(docs).extracting(EcrlsLabDocVO::getDOCUMENT_ID).containsExactlyInAnyOrder(100, 101);
	}

	// ---- the copies and imports: every generated insert, column by column -------------------------------------------

	/** A former insert statement: its table, and for each column the expression the value came from. */
	record Insert(String table, List<String> columns, List<String> expressions) {
		static Insert parse(String sql) {
			Matcher m = Pattern.compile("(?i)insert into (\\w+) ?\\((.*?)\\) ?values ?\\((.*)\\) *$").matcher(sql.replaceAll("\\s+", " ").trim());
			assertThat(m.matches()).as("an insert statement").isTrue();
			Insert insert = new Insert(m.group(1).toUpperCase(Locale.ENGLISH), splitTopLevel(m.group(2)), splitTopLevel(m.group(3)));
			assertThat(insert.expressions()).hasSameSizeAs(insert.columns());
			return insert;
		}
	}

	/** Splits at the commas outside parentheses and quotes. */
	static List<String> splitTopLevel(String text) {
		List<String> items = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		boolean quoted = false;
		for (char c : text.toCharArray()) {
			if (c == '\'') {
				quoted = !quoted;
			}
			if (!quoted && (c == '(')) {
				depth++;
			} else if (!quoted && c == ')') {
				depth--;
			}
			if (c == ',' && depth == 0 && !quoted) {
				items.add(current.toString().trim());
				current.setLength(0);
			} else {
				current.append(c);
			}
		}
		items.add(current.toString().trim());
		return items;
	}

	private static final Pattern PLACEHOLDER = Pattern.compile("\\?\\d+");

	/** The JDBC types of a table's columns (H2 metadata). */
	private Map<String, Integer> columnTypes(String table) throws Exception {
		Map<String, Integer> types = new LinkedHashMap<>();
		try (var connection = dataSource.getConnection(); var columns = connection.getMetaData().getColumns(null, null, table, null)) {
			while (columns.next()) {
				types.put(columns.getString("COLUMN_NAME").toUpperCase(Locale.ENGLISH), columns.getInt("DATA_TYPE"));
			}
		}
		assertThat(types).as("columns of " + table).isNotEmpty();
		return types;
	}

	private static int nextDocumentId = 5000;

	/**
	 * Runs an insert with a distinct value for every bound column (under a new DOCUMENT_ID) and checks the stored row:
	 * a bound value as it is, {@code trim(upper(...))} / {@code upper(trim(...))} of a text, {@code trim(...)},
	 * {@code TO_DATE(..., 'MM/DD/YYYY')} of a text date, {@code SYSDATE} a date, a {@code null} literal nothing.
	 *
	 * @param propertyType the type a VO holds a column's value as (null for a map insert, or an unknown property)
	 * @param run runs the insert with the values (by column name) and returns the rows inserted
	 */
	private void checkInsert(String sql, Function<String, Class<?>> propertyType, Function<Map<String, Object>, Integer> run) throws Exception {
		Insert insert = Insert.parse(sql);
		Map<String, Integer> types = columnTypes(insert.table());
		Map<String, Object> values = new LinkedHashMap<>();
		Map<String, Object> expected = new LinkedHashMap<>();
		int documentId = nextDocumentId++;
		int i = 0;
		for (int k = 0; k < insert.columns().size(); k++) {
			String column = insert.columns().get(k).toUpperCase(Locale.ENGLISH);
			String expression = insert.expressions().get(k);
			assertThat(types).as("column " + column + " of " + insert.table()).containsKey(column);
			i++;
			if (expression.equalsIgnoreCase("null")) {
				expected.put(column, null);
				continue;
			}
			if (expression.equalsIgnoreCase("sysdate")) {
				expected.put(column, "SYSDATE");
				continue;
			}
			assertThat(PLACEHOLDER.matcher(expression).find()).as("expression " + expression + " of " + column).isTrue();
			boolean toDate = expression.toUpperCase(Locale.ENGLISH).startsWith("TO_DATE(");
			boolean upper = expression.toLowerCase(Locale.ENGLISH).contains("upper(");
			boolean trim = expression.toLowerCase(Locale.ENGLISH).contains("trim(");
			int type = types.get(column);
			Class<?> property = propertyType.apply(column);
			boolean numeric = type == Types.NUMERIC || type == Types.DECIMAL || property == int.class || property == Integer.class
					|| property == long.class || property == Long.class || property == BigDecimal.class;
			boolean date = !numeric && (type == Types.TIMESTAMP || type == Types.DATE || (property != null && java.util.Date.class.isAssignableFrom(property)));
			Object value;
			Object stored;
			if (column.equals("DOCUMENT_ID")) {
				value = new BigDecimal(documentId);
				stored = value;
			} else if (property == boolean.class || property == Boolean.class) {
				value = Boolean.TRUE;                                                 // bound with setBoolean, as MyBatis bound a boolean property
				stored = "BOOLEAN_TRUE";
			} else if (toDate) {
				value = "03/" + String.format("%02d", 1 + i % 27) + "/2021";
				stored = Timestamp.valueOf(LocalDate.of(2021, 3, 1 + i % 27).atStartOfDay());
			} else if (numeric) {
				value = new BigDecimal(1000 + i);
				stored = value;
			} else if (date) {
				LocalDateTime time = LocalDateTime.of(2021, 3, 4, 10, 11, 12).plusDays(i % 20);
				value = Timestamp.valueOf(property == java.sql.Date.class ? time.toLocalDate().atStartOfDay() : time);   // a java.sql.Date property: the day
				stored = value;
			} else {
				value = "  aB" + i + " x ";                                            // spaces and lower case: shows trim / upper
				String text = (String) value;
				stored = upper ? text.trim().toUpperCase(Locale.ENGLISH) : trim ? text.trim() : text;
			}
			values.put(column, value);
			expected.put(column, stored);
		}
		assertThat(run.apply(values)).isEqualTo(1);
		Map<String, Object> row = statements.one("select * from " + insert.table() + " where DOCUMENT_ID = ?1", Rows.map(), documentId);
		assertThat(row).as(insert.table() + " row " + documentId).isNotNull();
		for (Map.Entry<String, Object> e : expected.entrySet()) {
			Object stored = row.get(e.getKey());
			String what = insert.table() + "." + e.getKey();
			if (e.getValue() == null) {
				assertThat(stored).as(what).isNull();
			} else if ("SYSDATE".equals(e.getValue())) {
				assertThat(stored).as(what).isInstanceOf(Timestamp.class);
			} else if ("BOOLEAN_TRUE".equals(e.getValue())) {
				assertThat(String.valueOf(stored).toUpperCase(Locale.ENGLISH)).as(what).isIn("TRUE", "1");   // the database's text of a bound true
			} else if (stored instanceof BigDecimal number) {
				assertThat(number).as(what).isEqualByComparingTo(new BigDecimal(e.getValue().toString().trim()));
			} else if (stored instanceof String text && e.getValue() instanceof Number number) {
				assertThat(text).as(what).isEqualTo(number.toString());               // a number held as text by the VO, in a text column
			} else {
				assertThat(stored).as(what).isEqualTo(e.getValue());
			}
		}
	}

	/** No VO: the values are typed by the columns. */
	private static final Function<String, Class<?>> MAP_INSERT = column -> null;

	/** The setter of a VO for a column: its name is the column name without underscores (ignoring case), or null. */
	private static Method setter(Class<?> type, String column) {
		String wanted = "set" + column.replace("_", "").toLowerCase(Locale.ENGLISH);
		return Arrays.stream(type.getMethods())
				.filter(m -> m.getParameterCount() == 1 && m.getName().replace("_", "").toLowerCase(Locale.ENGLISH).equals(wanted)).findFirst()
				.orElse(null);
	}

	/** The types of a VO's properties, by column. */
	private static Function<String, Class<?>> propertyTypes(Class<?> type) {
		return column -> {
			Method setter = setter(type, column);
			return setter == null ? null : setter.getParameterTypes()[0];
		};
	}

	/** Sets the values on a VO through the setters of the same names (see {@link #setter}). */
	private static <T> T vo(Class<T> type, Map<String, Object> values) throws Exception {
		T vo = type.getDeclaredConstructor().newInstance();
		for (Map.Entry<String, Object> e : values.entrySet()) {
			Method setter = setter(type, e.getKey());
			assertThat(setter).as("setter for " + e.getKey() + " in " + type.getSimpleName()).isNotNull();
			Class<?> target = setter.getParameterTypes()[0];
			Object value = e.getValue();
			if (target == String.class && !(value instanceof String)) {
				value = value instanceof Timestamp t ? t.toString() : value.toString();
			} else if ((target == int.class || target == Integer.class) && value instanceof Number n) {
				value = n.intValue();
			} else if ((target == long.class || target == Long.class) && value instanceof Number n) {
				value = n.longValue();
			} else if (target == java.sql.Date.class && value instanceof Timestamp t) {
				value = new java.sql.Date(t.getTime());                                 // Oracle DATE: the VO's day-only date
			} else if (target == java.util.Date.class && value instanceof Timestamp t) {
				value = new java.util.Date(t.getTime());
			}
			setter.invoke(vo, value);
		}
		return vo;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map<Object, Object> objectMap(Map<String, Object> values) {
		return (Map) values;
	}

	@Test
	void copiesOfLabDocumentsWriteEveryColumnAsTheMapperDid() throws Exception {
		checkInsert(LabDocumentSql.INSERT_RAW_HIV_DOCUMENT, MAP_INSERT, values -> dao.insertRawHivDocument(objectMap(values)));
		checkInsert(LabDocumentSql.INSERT_RAW_STD_DOCUMENT, MAP_INSERT, values -> dao.insertRawStdDocument(objectMap(values)));
		checkInsert(LabDocumentSql.INSERT_STD_HIV_DOCUMENT, MAP_INSERT, values -> dao.insertStdHivDocument(objectMap(values)));
		checkInsert(LabDocumentSql.INSERT_STD_STD_DOCUMENT, MAP_INSERT, values -> dao.insertStdStdDocument(objectMap(values)));
	}

	@Test
	void editedHivDocumentsWriteEveryColumnAsTheMapperDid() throws Exception {
		checkInsert(LabDocumentSql.INSERT_RAW_EDITED_HIV_DOCUMENT, MAP_INSERT, values -> dao.insertRawEditedHIVLabDocumentWithUpdateData(values));
		checkInsert(LabDocumentSql.INSERT_STD_EDITED_HIV_DOCUMENT, MAP_INSERT, values -> dao.insertStdEditedHivLabDocumentWithUpdateData(values));
	}

	@Test
	void importsFromVosWriteEveryColumnAsTheMapperDid() throws Exception {
		checkInsert(LabDocumentSql.INSERT_STANDARD_ECLRS_RECORD, propertyTypes(StdEcrlsLabDocVO.class), values -> {
			try {
				return dao.insertStandardEcrlsRecord(vo(StdEcrlsLabDocVO.class, values));
			} catch (Exception e) {
				throw new AssertionError(e);
			}
		});
		checkInsert(LabDocumentSql.INSERT_LAB_DOC, propertyTypes(LabDocVO.class), values -> {
			try {
				dao.insertLabDocData(vo(LabDocVO.class, values));
				return 1;
			} catch (Exception e) {
				throw new AssertionError(e);
			}
		});
	}

	@Test
	void hashUpdateAndConfiguredSqlRun() {
		dao.updateHashUniqueNumber(100);
		assertThat(statements.one("select HASH_UNIQUE_NUMBER from RAW_HIV_ECLRS_DOC where DOCUMENT_ID = 100", Rows.scalar(Long.class))).isNotNull();

		Map<String, String> query = new HashMap<>();
		query.put("sqlQuery", "update RAW_HIV_ECLRS_DOC set CURRENT_PROCESS_STATUS = 11 where DOCUMENT_ID = 102 and CURRENT_PROCESS_STATUS = 5");
		assertThat(dao.restageStdDocumentNonMatches(query)).isEqualTo(1);
		query.put("sqlQuery", "update RAW_HIV_ECLRS_DOC set CURRENT_PROCESS_STATUS = 5 where DOCUMENT_ID = 102");
		assertThat(dao.restageStdDocumentNonMatches(query)).isEqualTo(1);
	}

	// ---- resistance sequences ---------------------------------------------------------------------------------------

	@Test
	void resistanceSequencesAreAddedCopiedAndDeleted() {
		Map<Object, Object> segment = new HashMap<>();
		segment.put("documentId", new BigDecimal("400"));
		segment.put("sequence", "ACGT".repeat(1000));
		segment.put("sequence_length", 4000);
		segment.put("eclrs_key", new BigDecimal("88"));
		segment.put("currentIndex", 1);
		segment.put("collection_date", new java.util.Date(Timestamp.valueOf("2021-06-07 15:30:00").getTime()));   // jdbcType=DATE: the day only
		segment.put("rasElig", "Y");
		assertThat(dao.insertStandardHIVResistanceResult(segment)).isEqualTo(1);

		Map<String, Object> row = statements.one("select * from GEN_SEQUENCE where LAB_DOC_ID = 400", Rows.map());
		assertThat(row.get("GEN_SEQ")).isEqualTo("ACGT".repeat(1000));
		assertNumber(row.get("SEQ_LEN"), 4000);
		assertNumber(row.get("SRC_ID_NUM"), 88);
		assertNumber(row.get("SEGMENTNO"), 1);
		assertThat(row.get("SPECIMEN_DATE")).isEqualTo(Timestamp.valueOf("2021-06-07 00:00:00"));
		assertThat(row.get("RAS_ELIG")).isEqualTo("Y");
		assertThat(row).doesNotContainKeys("VLINK_DATE", "STDRPTS_ID");

		Map<String, Integer> copy = new HashMap<>();
		copy.put("CURRENT_DOCUMENT_ID", 300);
		copy.put("NEW_DOCUMENT_ID", 401);
		assertThat(dao.insertStandardHIVResistanceResultFromOldDocument(copy)).isEqualTo(2);
		List<Map<String, Object>> copied = statements.list("select * from GEN_SEQUENCE where LAB_DOC_ID = 401 order by SEGMENTNO", Rows.map());
		assertThat(copied).hasSize(2);
		assertThat(copied.get(0)).containsEntry("GEN_SEQ", "AAAA").doesNotContainKey("VLINK_DATE");
		assertNumber(copied.get(0).get("STDRPTS_ID"), 9);
		assertThat(copied.get(1)).containsEntry("GEN_SEQ", "BBBB").containsEntry("VLINK_DATE", Timestamp.valueOf("2022-01-01 00:00:00"));

		dao.deleteResistanceData(401);                                                 // only the unlinked segment goes
		assertThat(statements.list("select GEN_SEQ from GEN_SEQUENCE where LAB_DOC_ID = 401", Rows.scalar(String.class))).containsExactly("BBBB");
		dao.deleteResistanceData(999);
	}

	// ---- multi-spot documents ---------------------------------------------------------------------------------------

	@Test
	void multiSpotDocumentsAreTheGroupedRawDocumentsOfALaboratory() {
		Map<String, Object> input = new HashMap<>();
		input.put("loincCodes", "L1,L2,L8,L9".split(","));                            // group 4 (all four codes given: H2 concatenates NULL differently from Oracle)
		input.put("pfiList", "RAWPFI".split(","));
		List<Map<String, Object>> rows = dao.getMultiSpotDocuments(input);

		assertThat(rows).extracting(row -> ((BigDecimal) row.get("DOCUMENT_ID")).intValue()).containsExactlyInAnyOrder(100, 101);
		for (Map<String, Object> row : rows) {
			assertNumber(row.get("RANKING"), 101);                                     // the highest id of the identical patient rows
			assertThat(row.get("PIDDATEOFBIRTH")).isEqualTo("01/02/1980");             // TO_CHAR'd in the SQL
			assertThat(row.get("MSHLABPFI")).isEqualTo("RAWPFI");
		}
		input.put("pfiList", "OTHERPFI".split(","));
		assertThat(dao.getMultiSpotDocuments(input)).isEmpty();
		input.remove("pfiList");                                                       // no PFI filter: every laboratory
		assertThat(dao.getMultiSpotDocuments(input)).hasSize(2);
	}

	@Test
	void statusUpdateFollowsTheFormerDynamicSql() {
		Map<Object, Object> input = new HashMap<>();
		input.put("statusCode", 12);
		input.put("documentIds", List.of(101, 102));
		assertThat(dao.updateStatusCodeForHivDocuments(input)).isEqualTo(2);
		assertThat(statements.list("select CURRENT_PROCESS_STATUS, PROCESS_DATE from RAW_HIV_ECLRS_DOC where DOCUMENT_ID in (101, 102)", Rows.map()))
				.allSatisfy(row -> {
					assertNumber(row.get("CURRENT_PROCESS_STATUS"), 12);
					assertThat(row.get("PROCESS_DATE")).isInstanceOf(Timestamp.class);
				});

		input.put("statusCode", 0);                                                    // OGNL: 0 != '' was false, so only the date changed
		input.put("documentIds", List.of(101));
		assertThat(dao.updateStatusCodeForHivDocuments(input)).isEqualTo(1);
		assertThat(statements.one("select CURRENT_PROCESS_STATUS from RAW_HIV_ECLRS_DOC where DOCUMENT_ID = 101", Rows.scalar(Integer.class))).isEqualTo(12);

		input.put("statusCode", 11);
		input.put("documentIds", List.of(101));
		dao.updateStatusCodeForHivDocuments(input);
		input.put("statusCode", 5);
		input.put("documentIds", List.of(102));
		dao.updateStatusCodeForHivDocuments(input);

		assertThat(LabDocumentDAO.ognlNotEmpty(null)).isFalse();
		assertThat(LabDocumentDAO.ognlNotEmpty("")).isFalse();
		assertThat(LabDocumentDAO.ognlNotEmpty("0")).isFalse();
		assertThat(LabDocumentDAO.ognlNotEmpty(7)).isTrue();
		assertThat(LabDocumentDAO.ognlNotEmpty("abc")).isTrue();
	}

	// ---- lookups ---------------------------------------------------------------------------------------------------

	@Test
	void documentLookupsMatchTheFormerStatements() {
		assertThat(dao.getMultiStepDocumentId(100)).isEqualTo(90);
		assertThat(dao.getMultiStepDocumentId(101)).isNull();                          // a null parent
		assertThat(dao.getMultiStepDocumentId(999)).isNull();                          // no row

		Map<String, Object> details = dao.hivDocumentCurrentProcessStatusDetails(100);
		assertThat(details).containsOnly(Map.entry("DOCUMENT_ID", 100), Map.entry("CURRENT_PROCESS_STATUS_RAW", 11),
				Map.entry("CURRENT_PROCESS_STATUS_STD", 11), Map.entry("MATCH_CD", "M"));
		assertThat(details.get("DOCUMENT_ID")).isExactlyInstanceOf(Integer.class);
		assertThat(dao.hivDocumentCurrentProcessStatusDetails(101)).containsOnlyKeys("DOCUMENT_ID", "CURRENT_PROCESS_STATUS_RAW");  // no standardized row
		assertThat(dao.hivDocumentCurrentProcessStatusDetails(999)).isNull();

		assertThat(dao.getOOSCliaDetails("lab one")).containsOnly(Map.entry("FACILITY_NAME", "LAB ONE"), Map.entry("CLIA", "33D1"));
		assertThat(dao.getOOSCliaDetails("LAB TWO")).containsEntry("CLIA", "33D2");
		assertThat(dao.getOOSCliaDetails("nobody")).isNull();
	}

	@Test
	void loincGroupsMatchTheOriginalSql() {
		List<Map<String, String>> groups = dao.getLabCombiningDocData();

		List<Map<String, Object>> original = statements.list("""
				select distinct LOINC_1||','||LOINC_2||','||NVL(LOINC_3, 'X')||','||NVL(LOINC_4,'X') loincs, GROUP_COUNT, CATEGORY_NAME, PFI, LOINC_GROUP_ID GROUP_ID, NEW_DOCUMENT
				from CODED_LOINC_GROUPING where (end_date is null or end_Date > SYSDATE)  order by CATEGORY_NAME asc""", Rows.map());
		assertThat(groups).hasSameSizeAs(original).hasSize(3);
		for (int i = 0; i < groups.size(); i++) {
			Map<String, String> group = groups.get(i);
			Map<String, Object> expected = original.get(i);
			assertThat(group.keySet()).isEqualTo(expected.keySet());
			for (String key : expected.keySet()) {
				Object was = expected.get(key);
				String wasText = was instanceof BigDecimal number ? number.toPlainString() : String.valueOf(was);
				assertThat(String.valueOf(group.get(key))).as(key).isEqualTo(wasText);   // GROUP_COUNT / GROUP_ID: Long here, BigDecimal before
			}
		}
		assertThat(groups.get(0)).containsEntry("CATEGORY_NAME", "Another group").containsEntry("LOINCS", "L1,L2,L7,X");
		assertThat(groups.get(1)).containsEntry("CATEGORY_NAME", "Full group").containsEntry("LOINCS", "L1,L2,L8,L9");
		assertThat(groups.get(2)).containsEntry("CATEGORY_NAME", "HIV combo").containsEntry("LOINCS", "L1,L2,X,X").containsEntry("NEW_DOCUMENT", "Y");
	}

	/** A number as H2 returns it (a BigDecimal, possibly in exponent form such as 4E+3) equals the expected value. */
	private static void assertNumber(Object actual, long expected) {
		assertThat(actual).isInstanceOf(BigDecimal.class);
		assertThat((BigDecimal) actual).isEqualByComparingTo(BigDecimal.valueOf(expected));
	}
}
