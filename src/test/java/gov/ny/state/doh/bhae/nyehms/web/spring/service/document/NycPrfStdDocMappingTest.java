package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;

/**
 * The standardized NYC PRF form built from a received record (the rules of the former
 * NycPRFDocumentService.writeRawNycPRFRecordDataToStdTable, compared side by side on 20,000
 * random records when they were moved here).
 */
class NycPrfStdDocMappingTest {

	@Test
	void copiesAndRenamesTheReceivedFields() {
		Map<Object, Object> std = NycPrfStdDocMapping.toStdDocData(raw(
				"DOCUMENT_ID", new BigDecimal("12"), "PRF", "7", "CITY", "Albany", "PROFALITY", "Clinic", "SOURE_CD", "S1"));

		assertThat(std).containsEntry("DOCUMENT_ID", new BigDecimal("12"))
				.containsEntry("PRF_SRC_ID", "7")
				.containsEntry("PERSON_CITY", "Albany")
				.containsEntry("PROV_FACILITY", "Clinic")
				.containsEntry("EHARS_DOCUMENT_SOURCE_CD", "S1")
				.containsEntry("PERSON_ZIP", null);                  // missing field: column present, null
	}

	@Test
	void setsTheImportColumns() {
		Map<Object, Object> std = NycPrfStdDocMapping.toStdDocData(raw());

		assertThat(std).containsEntry("CREATE_UID", "900")
				.containsEntry("CURRENT_PROCESS_STATUS", CommonConstants.RAW_RECORD_VALIDATION_SUCCESS_PROCESS_STATUS);
		assertThat(std.get("CREATE_DATE")).isInstanceOf(Date.class);
	}

	@Test
	void marksTheRacesOfTheRaceList() {
		Map<Object, Object> std = NycPrfStdDocMapping.toStdDocData(raw("RACE", "white,American Indian,Native Hawaiian"));

		assertThat(std).containsEntry("RACE_WHITE", "1").containsEntry("RACE_INDIAN", "1").containsEntry("RACE_PACIFIC", "1")
				.doesNotContainKey("RACE_CD");
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("RACE", " "))).containsEntry("RACE_CD", "");
		// entries are not trimmed: " BLACK" is not "BLACK"
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("RACE", "WHITE, BLACK")).get("RACE_BLACK")).isNotEqualTo("1");
	}

	@Test
	void takesTheSecondFieldWhenTheFirstIsEmpty() {
		Map<Object, Object> std = NycPrfStdDocMapping.toStdDocData(raw(
				"LASTNGTSTDT", " ", "LSTNGTSTDT", "01/02/2023", "RECOMD", "R", "PRTHIVPOS", "ignored"));

		assertThat(std).containsEntry("LAST_NEG_DATE_TEXT", "01/02/2023").containsEntry("RSON_FIRST_POS_PRTNR", "R");
	}

	@Test
	void describesTheOtherRisk() {
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("OTHERRISK", "tattoo")))
				.containsEntry("RISK_OTHER", "Y").containsEntry("RISK_OTHER_TEXT", "tattoo");
		assertThat(NycPrfStdDocMapping.toStdDocData(raw())).containsEntry("RISK_OTHER", "N").doesNotContainKey("RISK_OTHER_TEXT");
	}

	@Test
	void separatesMedicationNamesWithBars() {
		Map<Object, Object> std = NycPrfStdDocMapping.toStdDocData(raw("TREATMMED", "A,B,C", "ARVMED", ""));

		assertThat(std).containsEntry("PT_ARV_NAME", "A|B|C").doesNotContainKey("ARV_PREVENPREP_MED_NAMES");
	}

	@Test
	void cleansTheNumberOfTests() {
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("NUMTSTING", "0_5")).get("NUMBER_TEST_2_YRS")).isEqualTo("5");
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("NUMTSTING", "0")).get("NUMBER_TEST_2_YRS")).isEqualTo("0");
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("NUMTSTING", "10")).get("NUMBER_TEST_2_YRS")).isEqualTo("10");
		assertThat(NycPrfStdDocMapping.toStdDocData(raw("NUMTSTING", " ", "NGTST", "3")).get("NUMBER_TEST_2_YRS")).isEqualTo("3");
	}

	private static Map<Object, Object> raw(Object... fieldsAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < fieldsAndValues.length; i += 2) {
			raw.put(fieldsAndValues[i], fieldsAndValues[i + 1]);
		}
		return raw;
	}
}
