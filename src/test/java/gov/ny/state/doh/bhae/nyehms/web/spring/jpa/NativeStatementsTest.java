package gov.ny.state.doh.bhae.nyehms.web.spring.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;

/**
 * SQL statements run as JPA native queries with the former MyBatis result rules, against an in-memory H2 database
 * in Oracle mode (db/native-statements.sql).
 */
@SpringJUnitConfig(NativeStatementsTest.TestConfig.class)
class NativeStatementsTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, NativeStatements.class})
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("nativestatements;MODE=Oracle").generateUniqueName(false)
					.addScript("db/native-statements.sql").build();
		}
	}

	/** A VO as the former MyBatis results filled them. */
	public static class SampleVO {
		private int ID;
		private String code;
		private String FLAG;
		private String name;
		private BigDecimal amount;
		private double ratio;
		private java.sql.Date created;
		private String note;
		private Timestamp createdAt;
		boolean nameSet;

		public void setID(int iD) { ID = iD; }
		public void setCode(String code) { this.code = code; }
		public void setFLAG(String fLAG) { FLAG = fLAG; }
		public void setName(String name) { this.name = name; nameSet = true; }
		public void setAmount(BigDecimal amount) { this.amount = amount; }
		public void setRatio(double ratio) { this.ratio = ratio; }
		public void setCreated(java.sql.Date created) { this.created = created; }
		public void setNote(String note) { this.note = note; }
		public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
	}

	@Autowired NativeStatements statements;

	@Test
	void typesValuesAsGetObjectDoes() {
		Map<String, Object> row = statements.one("select ID, CODE, FLAG, AMOUNT, RATIO, CREATED, NOTE from NATIVE_TEST where ID = ?1", Rows.map(), 1);

		assertThat(row.get("ID")).isEqualTo(new BigDecimal("1"));                // not an Integer
		assertThat(row.get("CODE")).isEqualTo(new BigDecimal("1234567890123"));  // not a Long
		assertThat(row.get("FLAG")).isEqualTo("Y");                              // not a Character
		assertThat(row.get("AMOUNT")).isInstanceOf(BigDecimal.class);
		assertThat(row.get("RATIO")).isInstanceOf(BigDecimal.class);             // not a Double
		assertThat(row.get("CREATED")).isEqualTo(Timestamp.valueOf("2021-03-04 10:11:12"));
		assertThat(row.get("NOTE")).isEqualTo("a note");                         // a CLOB read as text
		assertThat(row).isInstanceOf(java.util.HashMap.class);
	}

	@Test
	void fillsBeansAsMyBatisAutoMappingDid() {
		SampleVO vo = statements.one("select ID, CODE, FLAG, NAME, AMOUNT, RATIO, CREATED, NOTE, CREATED createdAt, 'x' NO_SUCH_PROPERTY from NATIVE_TEST where ID = ?1",
				Rows.bean(SampleVO.class), 1);

		assertThat(vo.ID).isEqualTo(1);
		assertThat(vo.code).isEqualTo("1234567890123");      // a number read as text
		assertThat(vo.FLAG).isEqualTo("Y");
		assertThat(vo.name).isEqualTo("one");
		assertThat(vo.amount).isEqualByComparingTo("12.5");
		assertThat(vo.ratio).isEqualTo(0.25);
		assertThat(vo.created).isEqualTo(java.sql.Date.valueOf("2021-03-04"));   // getDate: the day only
		assertThat(vo.createdAt).isEqualTo(Timestamp.valueOf("2021-03-04 10:11:12"));
		assertThat(vo.note).isEqualTo("a note");

		// nulls are not set; a row that sets nothing is null
		SampleVO empty = statements.one("select NAME, FLAG from NATIVE_TEST where ID = 2", Rows.bean(SampleVO.class));
		assertThat(empty).isNull();
		SampleVO partly = statements.one("select ID, NAME from NATIVE_TEST where ID = 2", Rows.bean(SampleVO.class));
		assertThat(partly.ID).isEqualTo(2);
		assertThat(partly.nameSet).isFalse();
	}

	@Test
	void readsJsonAndScalars() {
		List<JSONObject> rows = statements.list("select ID, NAME \"name\" from NATIVE_TEST order by ID", Rows.json());
		assertThat(rows.get(0)).containsEntry("name", "one");
		assertThat(rows.get(1)).containsOnlyKeys("ID");

		assertThat(statements.list("select NAME from NATIVE_TEST order by ID", Rows.scalar(String.class))).containsExactly("one", null);
		assertThat(statements.one("select count(*) from NATIVE_TEST", Rows.scalar(Integer.class))).isEqualTo(2);
		assertThat(statements.one("select NAME X, ID X from NATIVE_TEST where ID = 1", Rows.json())).containsOnlyKeys("X").containsEntry("X", "one");
		assertThatThrownBy(() -> statements.one("select ID from NATIVE_TEST", Rows.scalar(Integer.class)))
				.isInstanceOf(IncorrectResultSizeDataAccessException.class);
	}

	@Test
	void writesWithBoundAndTypedNullParameters() {
		int inserted = statements.update("insert into NATIVE_TEST (ID, NAME, CREATED) values (?1, ?2, ?3)", 3,
				NativeStatements.text(null), NativeStatements.timestamp(null));
		assertThat(inserted).isEqualTo(1);
		assertThat(statements.update("update NATIVE_TEST set NAME = ?1 where ID = ?2", "o'neil; drop table x", 3)).isEqualTo(1);
		assertThat(statements.one("select NAME from NATIVE_TEST where ID = 3", Rows.scalar(String.class))).isEqualTo("o'neil; drop table x");
		assertThat(statements.update("delete from NATIVE_TEST where ID = ?1", 3)).isEqualTo(1);
	}
}
