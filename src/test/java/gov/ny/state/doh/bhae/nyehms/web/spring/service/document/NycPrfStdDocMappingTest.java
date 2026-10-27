package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * The standardized NYC PRF form built from a received record: the rules of the former
 * NycPRFDocumentService.writeNYCPRFDocAndPartnersData plus the cleaning its SQL insert did
 * in Oracle (upper/trim, FN_TO_DATE, empty text stored as null).
 */
class NycPrfStdDocMappingTest {

	@Test
	void copiesRenamesAndCleansTheReceivedFields() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw(
				"DOCUMENT_ID", new BigDecimal("12"), "PRF", " 7 ", "CITY", " Albany ", "PROFALITY", "clinic", "ZIP", "  "));

		assertThat(std.getDocumentId()).isEqualTo(12);
		assertThat(std.getPrfSrcId()).isEqualTo(7L);
		assertThat(std.getPersonCity()).isEqualTo("ALBANY");               // UPPER(TRIM(...))
		assertThat(std.getProvFacility()).isEqualTo("CLINIC");
		assertThat(std.getPersonZip()).isNull();                            // blank text is stored as null
		assertThat(std.getPersonState()).isNull();                          // missing field
	}

	@Test
	void setsTheImportColumns() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw());

		assertThat(std.getCreateUid()).isEqualTo("900");
		assertThat(std.getCurrentProcessStatus()).isEqualTo("5");
		assertThat(std.getCreateDate()).isNotNull();
		assertThat(std.getRaceCd()).isNull();
	}

	@Test
	void convertsTheDates() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw(
				"DOB", Timestamp.valueOf("1980-05-06 13:45:00"),        // a date read from the raw table keeps its time
				"VISITDT", " 3/4/2023 ",                                 // FN_TO_DATE
				"MDSIGN", "02/30/2023",                                  // FN_TO_DATE of an invalid date
				"FIELDPROCDATE", "01/02/2023",
				"ENTER_DT", "2023-05-06"));

		assertThat(std.getDob()).isEqualTo(Timestamp.valueOf("1980-05-06 13:45:00"));
		assertThat(std.getAdmissionDate()).isEqualTo(Timestamp.valueOf("2023-03-04 00:00:00"));
		assertThat(std.getSigDate()).isNull();
		assertThat(std.getFieldProcDate()).isEqualTo(Timestamp.valueOf("2023-01-02 00:00:00"));
		assertThat(std.getEnterDate()).isEqualTo(Timestamp.valueOf("2023-05-06 00:00:00"));
		assertThatThrownBy(() -> NycPrfStdDocMapping.toStdDoc(raw("DOB", "May 6"))).hasMessageContaining("Not a date");
	}

	@Test
	void writesNumbersAsOracleTextAndRefusesDatesForText() {
		assertThat(OracleConversions.oracleText(5)).isEqualTo("5");
		assertThat(OracleConversions.oracleText(new BigDecimal("12.50"))).isEqualTo("12.5");
		assertThat(OracleConversions.oracleText(new BigDecimal("0.50"))).isEqualTo(".5");
		assertThat(OracleConversions.oracleText(new BigDecimal("-0.25"))).isEqualTo("-.25");
		assertThat(OracleConversions.oracleText(new BigDecimal("1E+3"))).isEqualTo("1000");
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("CITYNO", new BigDecimal("12345"))).getCityno()).isEqualTo("12345");
		// Oracle would have formatted it with its session date format
		assertThatThrownBy(() -> NycPrfStdDocMapping.toStdDoc(raw("DOD", Timestamp.valueOf("2023-03-04 00:00:00"))))
				.hasMessageContaining("A date where text is expected");
	}

	@Test
	void trimsSpacesOnly() {
		assertThat(OracleConversions.trimmed("  a b  ")).isEqualTo("a b");
		assertThat(OracleConversions.trimmed("\ta\t")).isEqualTo("\ta\t");   // Oracle TRIM removes spaces only
		assertThat(OracleConversions.upper(" o'neil ")).isEqualTo("O'NEIL");
	}

	@Test
	void marksTheRacesOfTheRaceList() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw("RACE", "white,American Indian,Native Hawaiian"));

		assertThat(std.getRaceWhite()).isEqualTo("1");
		assertThat(std.getRaceIndian()).isEqualTo("1");
		assertThat(std.getRacePacific()).isEqualTo("1");
		// entries are not trimmed: " BLACK" is not "BLACK"
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("RACE", "WHITE, BLACK")).getRaceBlack()).isNotEqualTo("1");
	}

	@Test
	void takesTheSecondFieldWhenTheFirstIsEmpty() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw(
				"LASTNGTSTDT", " ", "LSTNGTSTDT", "01/02/2023", "RECOMD", "r", "PRTHIVPOS", "ignored"));

		assertThat(std.getLastNegDateText()).isEqualTo("01/02/2023");
		assertThat(std.getRsonFirstPosPrtnr()).isEqualTo("R");

		// with a conversion: the second field's value is converted
		StdNycprfDoc converted = NycPrfStdDocMapping.toStdDoc(raw("EVRNGTST", "", "TSTNGBEF", "y", "EXPHIV", "n", "EXPSIX", "y"));
		assertThat(converted.getEverTestNeg()).isEqualTo(OracleConversions.upper(LabCommonUtils.checkTestDataValues("y"))).isNotNull();
		assertThat(converted.getRsonFirstPosExp()).isEqualTo(OracleConversions.upper(LabCommonUtils.checkTestDataValues("n"))).isNotNull();
	}

	@Test
	void describesTheOtherRisk() {
		StdNycprfDoc withRisk = NycPrfStdDocMapping.toStdDoc(raw("OTHERRISK", "tattoo"));
		assertThat(withRisk.getRiskOther()).isEqualTo("Y");
		assertThat(withRisk.getRiskOtherText()).isEqualTo("TATTOO");

		StdNycprfDoc withoutRisk = NycPrfStdDocMapping.toStdDoc(raw());
		assertThat(withoutRisk.getRiskOther()).isEqualTo("N");
		assertThat(withoutRisk.getRiskOtherText()).isNull();
	}

	@Test
	void separatesMedicationNamesWithBars() {
		StdNycprfDoc std = NycPrfStdDocMapping.toStdDoc(raw("TREATMMED", "a,b,c", "ARVMED", ""));

		assertThat(std.getPtArvName()).isEqualTo("A|B|C");
		assertThat(std.getArvPrevenprepMedNames()).isNull();
	}

	@Test
	void cleansTheNumberOfTests() {
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("NUMTSTING", "0_5")).getNumberTest2Yrs()).isEqualTo("5");
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("NUMTSTING", "0")).getNumberTest2Yrs()).isEqualTo("0");
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("NUMTSTING", "10")).getNumberTest2Yrs()).isEqualTo("10");
		assertThat(NycPrfStdDocMapping.toStdDoc(raw("NUMTSTING", " ", "NGTST", new BigDecimal("3"))).getNumberTest2Yrs()).isEqualTo("3");
	}

	private static Map<Object, Object> raw(Object... fieldsAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < fieldsAndValues.length; i += 2) {
			raw.put(fieldsAndValues[i], fieldsAndValues[i + 1]);
		}
		return raw;
	}
}
