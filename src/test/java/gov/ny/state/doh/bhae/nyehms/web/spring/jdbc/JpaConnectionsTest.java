package gov.ny.state.doh.bhae.nyehms.web.spring.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.EharsJpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * The plain JDBC connections of the JPA units (replacing ConnectionDAO), and a native query with a bound document id as
 * CommonService.getDocumentData now runs it, against in-memory H2 databases.
 */
@SpringJUnitConfig(JpaConnectionsTest.TestConfig.class)
@TestPropertySource(properties = "ehars.hibernate.dialect=org.hibernate.dialect.H2Dialect")
class JpaConnectionsTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, EharsJpaConfig.class, JpaConnections.class})
	static class TestConfig {
		@Bean
		@Primary
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("connmain;MODE=Oracle").generateUniqueName(false)
					.addScript("db/jpa-connections.sql").build();
		}

		@Bean
		DataSource eharsDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("connehars").generateUniqueName(false)
					.addScript("db/ehars.sql").build();
		}
	}

	@Autowired JpaConnections jpaConnections;

	@PersistenceContext EntityManager entityManager;

	@Test
	void givesAutoCommitConnectionsOfBothDatabases() throws Exception {
		try (Connection app = jpaConnections.appConnection(); Statement statement = app.createStatement();
				ResultSet rs = statement.executeQuery("select LNAME from STD_MEDICAID_DOC where DOCUMENT_ID = 7")) {
			assertThat(app.getAutoCommit()).isTrue();
			assertThat(rs.next()).isTrue();
			assertThat(rs.getString(1)).isEqualTo("DOE");
		}
		try (Connection ehars = jpaConnections.eharsConnection(); Statement statement = ehars.createStatement();
				ResultSet rs = statement.executeQuery("select descr from country_code where country_cd = 'US'")) {
			assertThat(rs.next()).isTrue();
			assertThat(rs.getString(1)).isEqualTo("United States");
		}
	}

	@Test
	void bindsTheDocumentIdOfTheDocumentDataQuery() {
		String sql = "select docTable.FNAME, docTable.MNAME, docTable.LNAME, '' as EHARS_FAC_UID  from STD_MEDICAID_DOC docTable WHERE  docTable.DOCUMENT_ID = :documentId and rownum < 2";

		List<?> found = entityManager.createNativeQuery(sql).setParameter("documentId", "7").getResultList();
		assertThat(found).hasSize(1);
		assertThat((Object[]) found.get(0)).containsExactly("JANE", null, "DOE", null);

		assertThat(entityManager.createNativeQuery(sql).setParameter("documentId", "8").getResultList()).isEmpty();
	}
}
