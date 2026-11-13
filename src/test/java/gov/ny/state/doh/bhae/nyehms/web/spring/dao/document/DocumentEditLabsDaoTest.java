package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.hibernate.type.StandardBasicTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.Param;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;

/**
 * The document edit DAO on an in-memory H2 database in Oracle mode (db/document-edit.sql): every copy insert is run
 * against a table created from its column list (typed by its bound values), once with typed nulls and once with
 * values, and the stored row is checked against what each value expression of the mapper promises
 * ({@code upper(trim(...))}, {@code FN_TO_DATE}, the value as it is, {@code SYSDATE}); the association and
 * assignment statements next to their data.
 */
@SpringJUnitConfig(DocumentEditLabsDaoTest.TestConfig.class)
public class DocumentEditLabsDaoTest {                                                    // public: H2 calls the FN_TO_DATE stand-in reflectively

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class, JpqlStatements.class, DocumentEditLabsDAO.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("docedit;MODE=Oracle").generateUniqueName(false)
					.addScript("db/document-edit.sql").build();
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

	@Autowired DocumentEditLabsDAO dao;
	@Autowired NativeStatements statements;

	/** The 29 copy inserts: each SQL with the method that binds a map's values to it. */
	private static Map<String, Function<Map<?, ?>, Object[]>> inserts() {
		Map<String, Function<Map<?, ?>, Object[]>> inserts = new LinkedHashMap<>();
		inserts.put(DocumentEditLabsSql.RAW_EDITED_TB_DOCUMENT, DocumentEditLabsSql::insertRawEditedTbDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_TB_DOCUMENT, DocumentEditLabsSql::insertStdEditedTbDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_AIRS_DOCUMENT, DocumentEditLabsSql::insertRawEditedAirsDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_AIRS_DOCUMENT, DocumentEditLabsSql::insertStdEditedAirsDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_NYC_IJR_DOCUMENT, DocumentEditLabsSql::insertRawEditedNycIJRDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_NYC_IJR_DOCUMENT, DocumentEditLabsSql::insertStdEditedNycIJRDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_NDI_DOCUMENT, DocumentEditLabsSql::insertRawEditedNDIDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_NDI_DOCUMENT, DocumentEditLabsSql::insertStdEditedNDIDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_SSDM_DOCUMENT, DocumentEditLabsSql::insertRawEditedSSDMDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_SSDM_DOCUMENT, DocumentEditLabsSql::insertStdEditedSSDMDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_INS_VITAL_DOCUMENT, DocumentEditLabsSql::insertRawEditedInsVitalDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_INS_VITAL_DOCUMENT, DocumentEditLabsSql::insertStdEditedInsVitalDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_CASA_DOCUMENT, DocumentEditLabsSql::insertStdEditedCasaDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_CASA_DOCUMENT, DocumentEditLabsSql::insertRawEditedCasaDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_EPRF_OOC_DOCUMENT, DocumentEditLabsSql::insertStdEditedEprfOOCDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_EPRF_OOC_DOCUMENT, DocumentEditLabsSql::insertRawEditedEprfOOCDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_NYC_LAB_DOCUMENT, DocumentEditLabsSql::insertStdEditedNycLabDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_NYC_LAB_DOCUMENT, DocumentEditLabsSql::insertRawEditedNycLabDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_NYC_EHARS_DOCUMENT, DocumentEditLabsSql::insertRawEditedNycEharsDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_NYC_EHARS_DOCUMENT, DocumentEditLabsSql::insertStdEditedNycEharsDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_HIV_LAB_DOCUMENT, DocumentEditLabsSql::insertRawEditedHIVLabDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_HIV_LAB_DOCUMENT, DocumentEditLabsSql::insertStdEditedHivLabDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_STD_DOCUMENT, DocumentEditLabsSql::insertRawEditedStdDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_STD_DOCUMENT, DocumentEditLabsSql::insertStdEditedStdDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_CLUSTER_DOCUMENT, DocumentEditLabsSql::insertRawEditedClusterDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_CLUSTER_DOCUMENT, DocumentEditLabsSql::insertStdEditedClusterDocumentArgs);
		inserts.put(DocumentEditLabsSql.RAW_EDITED_OLD_CASES_DOCUMENT, DocumentEditLabsSql::insertRawEditedOldCasesDocumentArgs);
		inserts.put(DocumentEditLabsSql.STD_EDITED_OLD_CASES_DOCUMENT, DocumentEditLabsSql::insertStdEditedOldCasesDocumentArgs);
		inserts.put(DocumentEditLabsSql.EDITED_TRK_ALIAS_DOCUMENT, DocumentEditLabsSql::insertEditedTrkAliasDocumentArgs);
		return inserts;
	}

	private static final Pattern INSERT = Pattern.compile("(?is)\\s*insert\\s+into\\s+(\\w+)\\s*\\((.*?)\\)\\s*values\\s*\\((.*)\\)\\s*");
	private static final Pattern PLACEHOLDER = Pattern.compile("\\?(\\d+)");
	private static final Object NOT_NULL = new Object();

	/** The expressions of a comma-separated list, split at the commas outside parentheses and quotes. */
	static List<String> splitTopLevel(String list) {
		List<String> parts = new ArrayList<>();
		int depth = 0;
		boolean quoted = false;
		StringBuilder part = new StringBuilder();
		for (char c : list.toCharArray()) {
			if (c == '\'') {
				quoted = !quoted;
			} else if (!quoted && c == '(') {
				depth++;
			} else if (!quoted && c == ')') {
				depth--;
			}
			if (c == ',' && depth == 0 && !quoted) {
				parts.add(part.toString().trim());
				part.setLength(0);
			} else {
				part.append(c);
			}
		}
		parts.add(part.toString().trim());
		return parts;
	}

	/** The H2 column type for a bound value's type. */
	private static String columnType(Param param) {
		if (param.type() == StandardBasicTypes.BIG_DECIMAL) {
			return "NUMBER";
		}
		if (param.type() == StandardBasicTypes.DATE) {
			return "DATE";
		}
		if (param.type() == StandardBasicTypes.TIMESTAMP) {
			return "TIMESTAMP";
		}
		assertThat(param.type()).isEqualTo(StandardBasicTypes.STRING);
		return "VARCHAR2(4000)";
	}

	@Test
	void everyCopyInsertRunsWithTypedNullsAndConvertsItsValuesAsTheMapperDid() {
		int checked = 0;
		for (Map.Entry<String, Function<Map<?, ?>, Object[]>> insert : inserts().entrySet()) {
			String sql = insert.getKey();
			Matcher parsed = INSERT.matcher(sql);
			assertThat(parsed.matches()).as(sql).isTrue();
			String table = parsed.group(1);
			List<String> columns = Arrays.stream(parsed.group(2).split(",")).map(String::trim).toList();
			List<String> expressions = splitTopLevel(parsed.group(3));
			assertThat(expressions).as(table + " columns and values").hasSameSizeAs(columns);

			// the map keys in placeholder order (the Args method reads them in order), and the typed nulls
			List<String> keys = new ArrayList<>();
			Map<Object, Object> recorder = new HashMap<>() {
				@Override
				public Object get(Object key) {
					keys.add((String) key);
					return null;
				}
			};
			Object[] nulls = insert.getValue().apply(recorder);
			assertThat(keys).as(table).hasSize(nulls.length);
			int placeholders = 0;
			Matcher placeholder = PLACEHOLDER.matcher(sql);
			while (placeholder.find()) {
				placeholders = Math.max(placeholders, Integer.parseInt(placeholder.group(1)));
			}
			assertThat(placeholders).as(table + " placeholders").isEqualTo(nulls.length);

			// the table, typed by the values; the insert with typed nulls
			List<String> definitions = new ArrayList<>();
			Map<Object, Object> values = new HashMap<>();
			Map<String, Object> expected = new LinkedHashMap<>();
			Map<String, List<String>> textColumnsOfKey = new HashMap<>();                 // a key bound to text columns (a date key may share it)
			Map<String, Function<String, String>> textRuleOfColumn = new HashMap<>();
			for (int i = 0; i < columns.size(); i++) {
				String expression = expressions.get(i);
				String bare = expression.replaceAll("\\s+", "");
				Matcher used = PLACEHOLDER.matcher(bare);
				if (!used.find()) {
					if (bare.equalsIgnoreCase("sysdate")) {
						definitions.add(columns.get(i) + " DATE");
						expected.put(columns.get(i), NOT_NULL);
					} else if (bare.matches("'[^']*'")) {
						definitions.add(columns.get(i) + " VARCHAR2(4000)");
						expected.put(columns.get(i), bare.substring(1, bare.length() - 1));
					} else if (bare.equalsIgnoreCase("null")) {
						definitions.add(columns.get(i) + " VARCHAR2(4000)");                     // the mapper writes NULL (bug kept)
					} else {
						fail("Unexpected value expression " + expression + " for " + table + "." + columns.get(i));
					}
					continue;
				}
				int n = Integer.parseInt(used.group(1));
				String key = keys.get(n - 1);
				Param param = (Param) nulls[n - 1];
				definitions.add(columns.get(i) + " " + columnType(param));
				String text = values.containsKey(key) ? (String) values.get(key) : " ab c ";   // a key already given a date text keeps it
				if (bare.matches("(?i)(upper\\(trim\\(|trim\\(upper\\()\\?\\d+\\)\\)")) {
					values.put(key, text);
					textRuleOfColumn.put(columns.get(i), value -> value.trim().toUpperCase());
					textColumnsOfKey.computeIfAbsent(key, k -> new ArrayList<>()).add(columns.get(i));
					expected.put(columns.get(i), text.trim().toUpperCase());
				} else if (bare.matches("(?i)trim\\(\\?\\d+\\)")) {
					if (param.type() == StandardBasicTypes.BIG_DECIMAL) {                     // a number trimmed: Oracle converts it to text and back
						values.put(key, 7);
						expected.put(columns.get(i), BigDecimal.valueOf(7));
					} else {
						values.put(key, text);
						textRuleOfColumn.put(columns.get(i), String::trim);
						textColumnsOfKey.computeIfAbsent(key, k -> new ArrayList<>()).add(columns.get(i));
						expected.put(columns.get(i), text.trim());
					}
				} else if (bare.matches("(?i)fn_to_date\\(\\?\\d+,'MM/DD/YYYY'\\)")) {
					values.put(key, "06/07/2021");
					expected.put(columns.get(i), Timestamp.valueOf("2021-06-07 00:00:00"));
				} else if (bare.matches("(?i)fn_to_date\\(trim\\(upper\\(\\?\\d+\\)\\),'MM/DD/YYYY'\\)")) {
					definitions.set(definitions.size() - 1, columns.get(i) + " DATE");          // the key is bound as text (it feeds DOD_TEXT too), the column is a date
					values.put(key, " 06/07/2021 ");                                           // the same key feeds a text column too (DOD_TEXT): its expectation follows
					for (String column : textColumnsOfKey.getOrDefault(key, List.of())) {
						expected.put(column, textRuleOfColumn.get(column).apply(" 06/07/2021 "));
					}
					expected.put(columns.get(i), Timestamp.valueOf("2021-06-07 00:00:00"));
				} else if (bare.matches("(?i)coalesce\\((fn_to_date\\(\\?\\d+,'MM/DD/YYYY'\\),)+\\?\\d+\\)")) {
					values.put(key, "06/07/2021");                                           // the first date given: COALESCE takes it, the others stay null
					expected.put(columns.get(i), Timestamp.valueOf("2021-06-07 00:00:00"));
				} else if (bare.equals("?" + n)) {
					if (param.type() == StandardBasicTypes.BIG_DECIMAL) {
						values.put(key, 7);
						expected.put(columns.get(i), BigDecimal.valueOf(7));
					} else if (param.type() == StandardBasicTypes.DATE) {
						values.put(key, new java.util.Date(Timestamp.valueOf("2021-06-07 15:30:00").getTime()));   // jdbcType=DATE: the day only
						expected.put(columns.get(i), Timestamp.valueOf("2021-06-07 00:00:00"));
					} else if (param.type() == StandardBasicTypes.TIMESTAMP) {
						values.put(key, Timestamp.valueOf("2021-06-07 15:30:00"));
						expected.put(columns.get(i), Timestamp.valueOf("2021-06-07 15:30:00"));
					} else {
						values.put(key, text);
						textRuleOfColumn.put(columns.get(i), value -> value);
						textColumnsOfKey.computeIfAbsent(key, k -> new ArrayList<>()).add(columns.get(i));
						expected.put(columns.get(i), text);
					}
				} else {
					fail("Unexpected value expression " + expression + " for " + table + "." + columns.get(i));
				}
			}
			statements.update("CREATE TABLE " + table + " (" + String.join(", ", definitions) + ")");
			assertThat(statements.update(sql, nulls)).as(table + " with nulls").isEqualTo(1);
			assertThat(statements.one("select count(*) from " + table, Rows.scalar(Long.class))).isEqualTo(1L);
			statements.update("DELETE FROM " + table);

			// the insert with values, checked column by column
			assertThat(statements.update(sql, insert.getValue().apply(values))).as(table + " with values").isEqualTo(1);
			Map<String, Object> row = statements.one("select * from " + table, Rows.map());
			for (Map.Entry<String, Object> column : expected.entrySet()) {
				Object actual = row.get(column.getKey());
				String where = table + "." + column.getKey();
				if (column.getValue() == NOT_NULL) {
					assertThat(actual).as(where).isNotNull();
				} else if (column.getValue() instanceof BigDecimal number) {
					assertThat(new BigDecimal(String.valueOf(actual))).as(where).isEqualByComparingTo(number);
				} else {
					assertThat(actual).as(where).isEqualTo(column.getValue());
				}
			}
			checked++;
		}
		assertThat(checked).isEqualTo(29);
	}

	@Test
	void associationAndAssignmentsFollowTheEditedCopy() {
		Map<String, Object> association = new HashMap<>();
		association.put("PS_ASSIGN_STATUS", 1);
		association.put("SURV_ASSIGNMENT_ID", "55");
		association.put("SURV_ASSESS_STATUS", null);
		association.put("DISCARD_STATUS", "N");
		association.put("DOCUMENT_ID", 100);
		assertThat(dao.updatePersonDocAssociationData(association)).isEqualTo(1);
		Map<String, Object> row = statements.one("select PS_ASSESS_STATUS, SURV_ASSIGNMENT_ID, SURV_ASSESS_STATUS, DISCARD_STATUS from PERSON_DOC_ASSOCIATION where DOCUMENT_ID = 100", Rows.map());
		assertThat(row.get("PS_ASSESS_STATUS")).hasToString("1");
		assertThat(row.get("SURV_ASSIGNMENT_ID")).hasToString("55");
		assertThat(row).doesNotContainKey("SURV_ASSESS_STATUS").containsEntry("DISCARD_STATUS", "N");
		association.put("DOCUMENT_ID", 999);
		assertThat(dao.updatePersonDocAssociationData(association)).isZero();

		Map<String, Object> triggering = new HashMap<>();
		triggering.put("NEW_DOCUMENT_ID", 300);
		triggering.put("assignmentIds", List.of(1, 2));
		assertThat(dao.updateTriggeringDocumentIdInAssignments(triggering)).isEqualTo(2);
		assertThat(dao.getAssignmentsCreatedForDocument(300)).containsExactlyInAnyOrder(1, 2);
		assertThat(dao.getAssignmentsCreatedForDocument(100)).isEmpty();
		assertThat(dao.getAssignmentsCreatedForDocument(200)).containsExactly(3);
		triggering.put("assignmentIds", List.of());                                       // IN () failed before
		assertThatThrownBy(() -> dao.updateTriggeringDocumentIdInAssignments(triggering)).isInstanceOf(InvalidDataAccessResourceUsageException.class);

		Map<Object, Object> activity = new HashMap<>();
		activity.put("comments", "Document Edited");
		activity.put("activityType", "AU10");
		activity.put("assignmentIds", List.of(1, 2));
		activity.put("workerId", 42);
		assertThat(dao.addEditedAssignmentActivity(activity)).isEqualTo(2);
		List<Map<String, Object>> activities = statements.list("select * from ASSIGNMENT_ACTIVITY order by DOCUMENT_ID", Rows.map());
		assertThat(activities).hasSize(2);
		for (int i = 0; i < 2; i++) {
			assertThat(((Number) activities.get(i).get("ASSIGNMENT_ACTIVITY_ID")).longValue()).isEqualTo(500 + i);   // from the sequence
			assertThat(activities.get(i)).containsEntry("ACTIVITY_TYPE_CODE", "AU10").containsEntry("COMMENTS", "Document Edited");
			assertThat(activities.get(i).get("ACTIVITY_DONE_BY")).hasToString("42");
			assertThat(activities.get(i).get("DOCUMENT_ID")).hasToString(String.valueOf(i + 1));
			assertThat(activities.get(i).get("ACTIVITY_DATE")).as("SYSDATE").isNotNull();
		}
		activity.put("assignmentIds", List.of());
		assertThatThrownBy(() -> dao.addEditedAssignmentActivity(activity)).isInstanceOf(InvalidDataAccessResourceUsageException.class);   // the empty union all failed too
	}
}
