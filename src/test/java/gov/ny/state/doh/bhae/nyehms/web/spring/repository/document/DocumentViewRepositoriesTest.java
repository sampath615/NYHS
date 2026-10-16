package gov.ny.state.doh.bhae.nyehms.web.spring.repository.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.AirsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.BlackBoxRefData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.CasaDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.ClimsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.DoccsDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.IccrDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.MedicaidOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiCauseDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NdiDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.NycDeathDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdConfDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.StdOldDocData;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.SyphDocData;

/**
 * The Medicaid Old, CLIMS, NDI, NDI cause, Black Box, CASA, death, DOCCS, insurance death, NYC
 * death, SSDM, NYC eHARS, STD old, AIRS, ICCR, syphilis and confirmed STD document screens (moved from MyBatis to
 * Spring Data JPA), against an in-memory H2 database in Oracle mode (db/document-views.sql). The
 * expected values are what the old MyBatis mappers returned for the same data (every property was
 * compared side by side when the screens were migrated).
 */
@SpringJUnitConfig(DocumentViewRepositoriesTest.TestConfig.class)
class DocumentViewRepositoriesTest {

	@Configuration
	@EnableTransactionManagement
	@Import(JpaConfig.class)
	static class TestConfig {
		@Bean
		DataSource appDS() {
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.setName("documentviews;MODE=Oracle")
					.generateUniqueName(false)
					.addScript("db/document-views.sql")
					.build();
		}
	}

	@Autowired
	MedicaidOldDocRepository medicaidOldDocRepository;

	@Autowired
	ClimsDocRepository climsDocRepository;

	@Autowired
	NdiDocRepository ndiDocRepository;

	@Autowired
	BlackBoxDocRepository blackBoxDocRepository;

	@Autowired
	CasaDocRepository casaDocRepository;

	@Autowired
	DeathDocRepository deathDocRepository;

	@Autowired
	DoccsDocRepository doccsDocRepository;

	@Autowired
	InsDeathDocRepository insDeathDocRepository;

	@Autowired
	NycDeathDocRepository nycDeathDocRepository;

	@Autowired
	SsdmDocRepository ssdmDocRepository;

	@Autowired
	NycEharsDocRepository nycEharsDocRepository;

	@Autowired
	StdOldDocRepository stdOldDocRepository;

	@Autowired
	AirsDocRepository airsDocRepository;

	@Autowired
	IccrDocRepository iccrDocRepository;

	@Autowired
	SyphDocRepository syphDocRepository;

	@Autowired
	StdConfDocRepository stdConfDocRepository;

	@Test
	void formatsDatesAsTheFormerToChar() {
		AirsDocData airs = airsDocRepository.findDocument(100);
		assertThat(airs.dob()).isEqualTo("03/04/2023");                         // TO_CHAR(DOB, 'MM/DD/YYYY')
		assertThat(airs.documentSourceTable()).isEqualTo("STD");                // a constant of the former query
		assertThat(airs.withAirsVerType("NEW").airsVerType()).isEqualTo("NEW"); // worked out by AIRSDocumentService
		assertThat(airsDocRepository.findDocument(200).dob()).isNull();

		SyphDocData syph = syphDocRepository.findDocument(100);
		assertThat(syph.syphilisDiagnosisDate()).isEqualTo("03/04/2023");
		assertThat(syph.parentDocumentId()).isEqualTo(99);

		StdConfDocData conf = stdConfDocRepository.findDocument(100);
		assertThat(conf.x900dxdate()).isEqualTo("03/04/2023");
		assertThat(conf.stateno()).isEqualTo("1234");
	}

	@Test
	void readsAnIccrReportWithTheDescriptionOfItsCode() {
		IccrDocData doc = iccrDocRepository.findDocument(100);

		assertThat(doc.resultsReceived()).isEqualTo("Yes");                     // CODED_YES_NO_UNK of 'Y'
		assertThat(doc.eharsImportDate()).isNull();                             // never read: shown empty, as before
		assertThat(doc.withIccrTestList(List.of()).iccrTestList()).isEmpty();   // added by ICCRDocumentService
		assertThat(iccrDocRepository.findDocument(200).resultsReceived()).isNull();
	}

	@Test
	void readsACasaReportWithItsTimestampAndParentDocument() {
		CasaDocData doc = casaDocRepository.findDocument(100);

		assertThat(doc.stdCasaDoc().getEharsImportDate()).isEqualTo(Timestamp.valueOf("2023-03-04 10:11:12"));   // with its time, as before
		assertThat(doc.parentDocumentId()).isEqualTo(99);
		assertThat(doc.stateno()).isEqualTo("1234");
		assertThat(casaDocRepository.findDocument(200).parentDocumentId()).isZero();
		assertThat(casaDocRepository.findDocument(300)).isNull();
	}

	@Test
	void readsDeathCertificatesWithWhatWasReceived() {
		DeathDocData death = deathDocRepository.findDocument(100);
		assertThat(death.stdDeathDoc().getHcause()).isEqualTo("H_CAUSE");
		assertThat(death.resStreet()).isEqualTo("RES_STREET");                  // RAW_DEATH_DOC
		assertThat(death.causeOfDeath()).isNull();                              // worked out by DeathDocumentService
		assertThat(death.withCauseOfDeath("HIV Cause of Death").causeOfDeath()).isEqualTo("HIV Cause of Death");

		NycDeathDocData nycDeath = nycDeathDocRepository.findDocument(100);
		assertThat(nycDeath.stdNycDeathDoc().getOiCause()).isEqualTo("OI_CAUSE");
		assertThat(nycDeath.withCauseOfDeath("Meets Case Definition").causeOfDeath()).isEqualTo("Meets Case Definition");
	}

	@Test
	void readsADoccsReport() {
		DoccsDocData doc = doccsDocRepository.findDocument(100);

		assertThat(doc.stdDoccsDoc().getNysid()).isEqualTo("NYSID");
		assertThat(doc.stateno()).isEqualTo("1234");
		assertThat(doc.dinDoccsDocs()).isNull();                                // added by DoccsDocumentService
		assertThat(doc.withDinDoccsDocs(List.of()).dinDoccsDocs()).isEmpty();
	}

	@Test
	void readsInsuranceDeathSsdmAndNycEharsRecords() {
		assertThat(insDeathDocRepository.findDocument(100).currentProcessStatus()).isEqualTo(7);
		assertThat(insDeathDocRepository.findDocument(200).currentProcessStatus()).isZero();   // was an int property
		assertThat(ssdmDocRepository.findDocument(100).stdSsdmDoc().getSsdmSrcId()).isEqualTo(7L);
		assertThat(ssdmDocRepository.findDocument(200).parentDocumentId()).isZero();
		assertThat(nycEharsDocRepository.findDocument(100).rawaddress()).isEqualTo("CURRENT_ADDRESS");   // RAW_NYC_EHARS_DOC.CURRENT_ADDRESS
		assertThat(nycEharsDocRepository.findDocument(100).stateno()).isEqualTo("1234");
	}

	@Test
	void readsAnStdOldReportWithItsFlags() {
		StdOldDocData doc = stdOldDocRepository.findDocument(100);
		assertThat(doc.raceWhite()).isTrue();
		assertThat(doc.age()).isEqualTo(7);
		assertThat(doc.stdSrcId()).isEqualTo(7L);

		StdOldDocData empty = stdOldDocRepository.findDocument(200);
		assertThat(empty.raceWhite()).isFalse();                                // was a boolean property: false, not empty
		assertThat(empty.age()).isZero();
		assertThat(empty.stdSrcId()).isZero();
	}

	@Test
	void readsAMedicaidOldReportWithWhatWasReceived() {
		MedicaidOldDocData doc = medicaidOldDocRepository.findDocument(100);

		assertThat(doc.stdMedOldDoc().getDocumentId()).isEqualTo(100);
		assertThat(doc.stdMedOldDoc().getFname()).isEqualTo("FNAME");
		assertThat(doc.rawAddress()).isEqualTo("ADDRESS");                      // RAW_MED_OLD_DOC
		assertThat(doc.stateno()).isEqualTo("1234");                            // only the current match
		assertThat(doc.stdMedOldDoc().getEharsImportDate()).isEqualTo(Date.valueOf("2023-03-04"));   // date only
		assertThat(medicaidOldDocRepository.findDocument(300)).isNull();
	}

	@Test
	void anEmptyMedicaidOldReportCanBeShown() {
		MedicaidOldDocData empty = MedicaidOldDocData.empty();

		assertThat(empty.stdMedOldDoc()).isNotNull();                           // the form binds to its fields
		assertThat(empty.stdMedOldDoc().getFname()).isNull();
	}

	@Test
	void readsTmaOrElseHiv1PantherForClims() {
		ClimsDocData doc = climsDocRepository.findDocument(100);

		assertThat(doc.tma()).isEqualTo("HIV1PANTHER");                         // TMA is empty in the test row
		assertThat(doc.facaddr1()).isEqualTo("FACADDR1");
		assertThat(doc.stdClimsDoc().getAge()).isEqualTo(12.5);
		assertThat(climsDocRepository.findDocument(200).tma()).isNull();
	}

	@Test
	void readsAnNdiRecordWithItsParentDocument() {
		NdiDocData doc = ndiDocRepository.findDocument(100);

		assertThat(doc.parentDocumentId()).isEqualTo(99);
		assertThat(doc.ndifileCat()).isEqualTo("NDIFILE_CAT");
		assertThat(doc.dodText()).isNull();                                     // built by NDIDocumentService
		assertThat(doc.withDodText("01/02/2003").dodText()).isEqualTo("01/02/2003");
		assertThat(ndiDocRepository.findDocument(200).parentDocumentId()).isZero();   // was an int property: 0, not empty
	}

	@Test
	void readsAnNdiCauseRecord() {
		NdiCauseDocData doc = ndiDocRepository.findCauseDocument(100);

		assertThat(doc.stdNdiCauseDoc().getFname()).isEqualTo("FNAME");
		assertThat(doc.stateno()).isEqualTo("1234");
		assertThat(ndiDocRepository.findCauseDocument(200).stateno()).isNull();
	}

	@Test
	void readsABlackBoxRecordWithItsJoinedAddressesAndCodeLists() {
		BlackBoxDocData doc = blackBoxDocRepository.findDocument(100);

		assertThat(doc.stateno()).isEqualTo("1234");                            // the matched person's, not the column's
		assertThat(doc.curStreetAddress()).isEqualTo("CUR_STREET_ADDRESS1 CUR_STREET_ADDRESS2");
		assertThat(doc.rsdStreetAddress()).isEqualTo("RSD_STREET_ADDRESS1 ");   // second line empty
		assertThat(blackBoxDocRepository.findDocument(200).currentProcessStatus()).isZero();
		assertThat(blackBoxDocRepository.findRefData()).containsExactly(
				new BlackBoxRefData("OI_CD", "Only", "1"),
				new BlackBoxRefData("TRANS_CATEG", "First", "1"),
				new BlackBoxRefData("TRANS_CATEG", "Second", "2"));
	}
}
