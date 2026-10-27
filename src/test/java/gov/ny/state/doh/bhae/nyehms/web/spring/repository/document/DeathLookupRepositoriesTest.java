package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.CodedCountyRepository;

/**
 * The code-list lookups of the death imports (moved from DeathDocumentDAO and NycDeathDocumentDAO to Spring Data
 * JPA), against an in-memory H2 database in Oracle mode (db/death-lookups.sql).
 */
@SpringJUnitConfig(DeathLookupRepositoriesTest.TestConfig.class)
class DeathLookupRepositoriesTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("deathlookups;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/death-lookups.sql")
					.build();
		}
	}

	@Autowired
	CodedDeathPfiRepository codedDeathPfiRepository;

	@Autowired
	CodedInstituteRepository codedInstituteRepository;

	@Autowired
	CodedCountyRepository codedCountyRepository;

	@Test
	void findsAFacilityByPfiOrNycFacilityCode() {
		DeathFacility facility = codedDeathPfiRepository.findFacilityByPfi("1234");

		assertThat(facility).isEqualTo(new DeathFacility("H", "ALBANY MED", "43 NEW SCOTLAND AVE", "ALBANY", "NY", "12208", null,
				"518-555-1234", "001"));
		assertThat(codedDeathPfiRepository.findFacilityByNycFacCode("77")).isEqualTo(facility);
		assertThat(codedDeathPfiRepository.findFacilityByPfi("0000")).isNull();
		assertThat(codedDeathPfiRepository.findFacilityByPfi("5678").isEmpty()).isTrue();   // no facility column: treated as none, as before
	}

	@Test
	void findsTheInstitutionOfACodeRange() {
		assertThat(codedInstituteRepository.findDescriptionOfInstitute("150")).isEqualTo("GENERAL HOSPITALS");
		assertThat(codedInstituteRepository.findDescriptionOfInstitute("200")).isEqualTo("NURSING HOMES");   // range ends included
		assertThat(codedInstituteRepository.findDescriptionOfInstitute("300")).isNull();
	}

	@Test
	void findsTheFipsCodeOfACounty() {
		assertThat(codedCountyRepository.findFipsCodeOfCounty("01")).isEqualTo("001");
		assertThat(codedCountyRepository.findFipsCodeOfCounty("02")).isIn("003", "999");   // the first one found, as rownum < 2
		assertThat(codedCountyRepository.findFipsCodeOfCounty("99")).isNull();
	}
}
