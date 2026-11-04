package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import javax.sql.DataSource;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycijrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycIjrDocData;

/**
 * The NYC IJR records (moved from NycIJRDocumentDAO to Spring Data JPA), against an in-memory H2 database in Oracle
 * mode (db/nycijr.sql).
 */
@SpringJUnitConfig(NycijrDocRepositoryTest.TestConfig.class)
class NycijrDocRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("nycijr;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/nycijr.sql")
					.build();
		}
	}

	@Autowired
	NycijrDocRepository nycijrDocRepository;

	@Test
	void readsARecordWithItsReceivedAddressesAndItsCurrentMatch() {
		NycIjrDocData document = nycijrDocRepository.findDocument(100);

		StdNycijrDoc std = document.stdNycijrDoc();
		assertThat(std.getDocumentId()).isEqualTo(100);
		assertThat(std.getLname()).isEqualTo("DOE");
		assertThat(std.getDobText()).isEqualTo("01/02/1980");
		assertThat(std.getAge()).isEqualTo(41);
		assertThat(std.getRaceWhite()).isEqualTo("1");
		assertThat(std.getFileDate()).isEqualTo(Timestamp.valueOf("2021-03-04 00:00:00"));
		assertThat(document.rawDxAddress()).isEqualTo("1 dx st");
		assertThat(document.rawAddress()).isEqualTo("2 cur st");
		assertThat(document.rawProvAddress()).isEqualTo("3 prov st");
		assertThat(document.stateNo()).isEqualTo("0022222");          // the current match, not the dissociated one
		assertThat(document.discardStatus()).isEqualTo("N");
		assertThat(document.numberOfPartners()).isEqualTo(3);
		assertThat(document.parentDocumentId()).isEqualTo(90);
	}

	@Test
	void showsZeroForEmptyNumbersAndNothingWithoutAMatch() {
		NycIjrDocData document = nycijrDocRepository.findDocument(200);

		assertThat(document.stateNo()).isNull();
		assertThat(document.discardStatus()).isNull();
		assertThat(document.numberOfPartners()).isZero();
		assertThat(document.parentDocumentId()).isZero();
	}

	@Test
	void needsTheDocumentLocatorRow() {
		assertThat(nycijrDocRepository.findDocument(300)).isNull();   // inner join, as before
		assertThat(nycijrDocRepository.findDocument(999)).isNull();
	}

	@Test
	void listsTheFilesWaitingForReview() {
		assertThat(nycijrDocRepository.findFileNamesToReview()).containsExactly("ijr1.txt");
	}
}
