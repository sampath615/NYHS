package gov.ny.state.doh.bhae.nyehms.web.spring.repository.ehars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsFacilityDetails;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsCountryCodeRepository;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsFacilityCodeRepository;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsLookupCodeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.EharsJpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.JpaConfig;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.assignment.EharsPersonViewReader;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.EharsDataService;

/**
 * The eHARS persistence unit next to the main one, against in-memory H2 databases (db/ehars.sql). Each query is also
 * compared with the SQL it replaced (raw JDBC through ConnectionDAO).
 */
@SpringJUnitConfig(EharsPersistenceUnitTest.TestConfig.class)
@TestPropertySource(properties = "ehars.hibernate.dialect=org.hibernate.dialect.H2Dialect")
class EharsPersistenceUnitTest {

	@Configuration
	@EnableTransactionManagement
	@Import({JpaConfig.class, EharsJpaConfig.class, EharsDataService.class, EharsPersonViewReader.class})
	static class TestConfig {
		@Bean
		@Primary
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("eharsmain;MODE=Oracle")
					.generateUniqueName(false).build();
		}

		@Bean
		DataSource eharsDS() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("eharsdb")
					.generateUniqueName(false).addScript("db/ehars.sql").build();
		}
	}

	@Autowired EharsCountryCodeRepository countryCodeRepository;
	@Autowired EharsLookupCodeRepository lookupCodeRepository;
	@Autowired EharsFacilityCodeRepository facilityCodeRepository;
	@Autowired EharsDataService eharsDataService;
	@Autowired EharsPersonViewReader personViewReader;
	@Autowired PlatformTransactionManager defaultTransactionManager;
	@Autowired @Qualifier("eharsDS") DataSource eharsDS;

	private List<EharsCode> oldCodes(String sql) {
		return new JdbcTemplate(eharsDS).query(sql, (rs, row) -> new EharsCode(rs.getString(1), rs.getString(2)));
	}

	@Test
	void theMainUnitStaysTheDefaultForTransactions() {
		assertThat(defaultTransactionManager).isNotNull();   // by type: only one is @Primary
	}

	@Test
	void readsCountriesByDescriptionAsBefore() {
		assertThat(countryCodeRepository.findCodesByDescription())
				.isEqualTo(oldCodes("select country_cd, descr from country_code order by descr"))
				.containsExactly(new EharsCode("CA", "Canada"), new EharsCode("US", "United States"));
	}

	@Test
	void readsTheCodesOfAFieldByNameAsBefore() {
		assertThat(lookupCodeRepository.findCodesOfType("OCCUPATION"))
				.isEqualTo(oldCodes("select lookup_cd, lookup_name from lookup_code where lookup_type = 'OCCUPATION' order by lookup_name"))
				.containsExactly(new EharsCode("2", "Clerk"), new EharsCode("1", "Nurse"));
		assertThat(lookupCodeRepository.findCodesOfType("x' or '1'='1")).isEmpty();   // a value, no longer part of the SQL
	}

	@Test
	void readsTheNamesOfTheGivenFacilitiesAsBefore() {
		assertThat(eharsDataService.getFacilityNames(new String[] {"F2", "F1", "F9"}))
				.isEqualTo(oldCodes("select facility_uid, name1 from facility_code where facility_uid in ('F2','F1','F9') order by name1"))
				.containsExactly(new EharsCode("F1", "Albany Med"), new EharsCode("F2", "Bellevue"));
		assertThat(eharsDataService.getFacilityNames(new String[0])).isEmpty();
		assertThat(eharsDataService.getFacilityNames(null)).isEmpty();
	}

	@Test
	void readsTheDetailsOfAFacilityAsBefore() {
		assertThat(eharsDataService.getFacilityDetails("F1")).contains(
				new EharsFacilityDetails("43 New Scotland", "Albany", "001", "NY", "12208", "5185551234", "US", "Hospital description"));
		assertThat(eharsDataService.getFacilityDetails("F3")).isEmpty();   // its type has no FACILITY_TYPE code
		assertThat(eharsDataService.getFacilityDetails("none")).isEmpty();
	}

	@Test
	void runsTheGeneratedPersonQueryAndShowsTheDatabaseTextOfItsValues() {
		Map<String, String> values = personViewReader.read("select facility_uid, name1 from facility_code where facility_uid = 'F1'");
		assertThat(values).hasSize(1);          // neither column is in EHARS_VAR_NAME_MAP: one null key, the last column wins
		assertThat(values.get(null)).isEqualTo("Albany Med");
		assertThat(personViewReader.read("select facility_uid from facility_code where facility_uid = 'none'")).isEmpty();
	}
}
