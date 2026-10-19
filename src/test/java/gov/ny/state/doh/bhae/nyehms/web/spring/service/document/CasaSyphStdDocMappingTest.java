package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdCasaDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdSyphilisDoc;

/**
 * The standardized CASA and syphilis records built from a received record: the rules of the former
 * CASADocumentService.writeRawCasaDocDataToStdTable and SYPHDocumentService.writeRawSYPHDocDataToStdTable plus
 * the cleaning their SQL inserts did in Oracle.
 */
class CasaSyphStdDocMappingTest {

	@Test
	void casaCodesTheRaceAndEthnicity() {
		StdCasaDoc std = CasaStdDocMapping.toStdDoc(raw("DOCUMENT_ID", new BigDecimal("12"), "RACE", " 2 ", "ETHNICITY", "x"));

		assertThat(std.getRaceBlack()).isEqualTo("1");
		assertThat(std.getRaceWhite()).isEqualTo("0");
		assertThat(std.getRaceMultiple()).isEqualTo("0");
		assertThat(std.getEthnicity()).isEqualTo("02");
		assertThat(CasaStdDocMapping.toStdDoc(raw("RACE", "0")).getRaceUnknown()).isEqualTo("1");      // 0 and 5 are unknown
		assertThat(CasaStdDocMapping.toStdDoc(raw("RACE", "7", "ETHNICITY", " x ")).getEthnicity()).isEqualTo("X");   // not a code: the field
	}

	@Test
	void casaCopiesAndCleansTheReceivedFields() {
		StdCasaDoc std = CasaStdDocMapping.toStdDoc(raw("DOB", "01/02/1980", "COUNTY_FIPS", "001", "FNAME", "jo_ann",
				"INPAT", "", "OUTPAT", new BigDecimal("3"), "CASA_FILE", " f.txt ", "CREATE_DATE", Timestamp.valueOf("2023-03-04 10:11:12")));

		assertThat(std.getDobText()).isEqualTo("01/02/1980");
		assertThat(std.getPersonCounty()).isEqualTo("001");
		assertThat(std.getFname()).isEqualTo("JO ANN");
		assertThat(std.getInpat()).isEqualTo("0");                        // NVL(..., 0)
		assertThat(std.getOutpat()).isEqualTo("3");
		assertThat(std.getArv()).isEqualTo("0");                          // missing count
		assertThat(std.getCasaFile()).isEqualTo(" f.txt ");               // copied as is
		assertThat(std.getCurrentProcessStatus()).isEqualTo(5);
	}

	@Test
	void aCasaRecordNeedsADateOfBirthAnSsnOrAName() {
		assertThat(CasaStdDocMapping.isIdentifiable(raw())).isFalse();                                   // stays at the raw layer
		assertThat(CasaStdDocMapping.isIdentifiable(raw("DOB", " ", "SSN", "", "FNAME", null, "MNAME", "x"))).isFalse();
		assertThat(CasaStdDocMapping.isIdentifiable(raw("DOB", "01/02/1980"))).isTrue();
		assertThat(CasaStdDocMapping.isIdentifiable(raw("SSN", "123456789"))).isTrue();
		assertThat(CasaStdDocMapping.isIdentifiable(raw("FNAME", "jo"))).isTrue();
		assertThat(CasaStdDocMapping.isIdentifiable(raw("LNAME", "smith"))).isTrue();
	}

	@Test
	void syphilisRenamesTheReceivedFields() {
		StdSyphilisDoc std = SyphStdDocMapping.toStdDoc(raw("FIRSTNAME", "jo_ann", "LOCALITY", " troy ", "DISEASE_CD", " 710 ",
				"DIAGNOSIS_DATE", Timestamp.valueOf("2021-05-06 07:08:09"), "RACE_CD", "3", "PROVIDERZIPCODE", "12207-1234"));

		assertThat(std.getFname()).isEqualTo("JO ANN");
		assertThat(std.getPersonCity()).isEqualTo("TROY");
		assertThat(std.getDiseaseCode()).isEqualTo("710");                // trimmed, not upper-cased
		assertThat(std.getSyphilisDiagnosisDate()).isEqualTo(Timestamp.valueOf("2021-05-06 07:08:09"));
		assertThat(std.getRaceIndian()).isEqualTo("1");                   // 3 is American Indian here
		assertThat(std.getRaceAsian()).isEqualTo("0");
		assertThat(std.getProvZip()).isEqualTo("122071234");              // digits only
		assertThat(std.getCurrentProcessStatus()).isEqualTo(5);
	}

	@Test
	void syphilisBuildsThePhoneAndDropsUnknownProviders() {
		assertThat(SyphStdDocMapping.toStdDoc(raw("PHONE", "555-1234", "AREA_CD", "(518)")).getPersonPhone()).isEqualTo("5185551234");
		assertThat(SyphStdDocMapping.toStdDoc(raw("PHONE", "(518) 555 1234")).getPersonPhone()).isEqualTo("5185551234");
		assertThat(SyphStdDocMapping.toStdDoc(raw("PHONE", "555-1234")).getPersonPhone()).isNull();   // no area code
		assertThat(SyphStdDocMapping.toStdDoc(raw("PROVIDERFNAME", "unknown", "PROVIDERLNAME", "smith")).getProvFname()).isNull();
		assertThat(SyphStdDocMapping.toStdDoc(raw("PROVIDERLNAME", "smith")).getProvLname()).isEqualTo("SMITH");
	}

	private static Map<Object, Object> raw(Object... keysAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			raw.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return raw;
	}
}
