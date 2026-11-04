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
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdTbDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TbDocData;

/**
 * The TB records (moved from TBDocumentDAO to Spring Data JPA), against an in-memory H2 database in Oracle mode
 * (db/tb.sql).
 */
@SpringJUnitConfig(TbDocRepositoryTest.TestConfig.class)
class TbDocRepositoryTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("tb;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/tb.sql")
					.build();
		}
	}

	@Autowired
	TbDocRepository tbDocRepository;

	@Test
	void readsARecordWithItsReceivedAddressesAndItsCurrentMatch() {
		TbDocData document = tbDocRepository.findDocument(100);

		StdTbDoc std = document.stdTbDoc();
		assertThat(std.getDocumentId()).isEqualTo(100);
		assertThat(std.getLname()).isEqualTo("DOE");
		assertThat(std.getMi()).isEqualTo("Q");
		assertThat(std.getRaceWhite()).isEqualTo("1");
		assertThat(std.getDxDate()).isEqualTo(Timestamp.valueOf("2021-03-04 00:00:00"));
		assertThat(std.getSerialno()).isEqualTo(77L);
		assertThat(document.rawFacilityAddress()).isEqualTo("1 fac st");
		assertThat(document.rawProviderAddress()).isEqualTo("2 prov st");
		assertThat(document.rawPersonAddress()).isEqualTo("3 main st");
		assertThat(document.stateno()).isEqualTo("0022222");          // the current match, not the dissociated one
		assertThat(document.discardStatus()).isEqualTo("N");
		assertThat(document.parentDocumentId()).isEqualTo(90);
	}

	@Test
	void showsZeroForAnEmptyParentAndNothingWithoutAMatch() {
		TbDocData document = tbDocRepository.findDocument(200);

		assertThat(document.stateno()).isNull();
		assertThat(document.parentDocumentId()).isZero();
	}

	@Test
	void needsTheDocumentLocatorRow() {
		assertThat(tbDocRepository.findDocument(300)).isNull();   // inner join, as before
		assertThat(tbDocRepository.findDocument(999)).isNull();
	}

	@Test
	void hasAnEmptyFormForAMissingDocument() {
		TbDocData empty = TbDocData.empty();

		assertThat(empty.stdTbDoc().getDocumentId()).isZero();
		assertThat(empty.stdTbDoc().getLname()).isNull();
		assertThat(empty.parentDocumentId()).isZero();
	}
}
