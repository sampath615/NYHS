package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdStdConfirmedDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * The standardized confirmed STD report built from a received record: the rules of the former
 * STDConfDocumentService.writeRawSTDConfDocDataToStdTable plus the cleaning its SQL insert did in Oracle
 * (upper/trim, empty text stored as null).
 */
class StdConfDocMappingTest {

	/** Stands in for CommonDocumentService.validateState (a database lookup of state names). */
	private static final UnaryOperator<String> STATES = state -> "New York".equals(state) ? "NY" : state;

	@Test
	void copiesAndCleansTheReceivedFields() {
		StdStdConfirmedDoc std = StdConfDocMapping.toStdDoc(raw(
				"DOCUMENT_ID", new BigDecimal("12"), "CASEKEY", " 7 ", "HINSERIALNO", new BigDecimal("1234567890"),
				"FNAME", "jo_ann", "LNAME", " smith ", "PERSON_CITY", " albany ", "PERSON_STATE", "New York", "PERSON_ZIP", "  ",
				"FIELDNOTE", "seen", "MATCH_WEIGHT", new BigDecimal("12.50"), "FLUX_MATCH_CD", " a ",
				"DOB", Timestamp.valueOf("1980-01-02 10:11:12")), STATES);

		assertThat(std.getDocumentId()).isEqualTo(12);
		assertThat(std.getCasekey()).isEqualTo(7);
		assertThat(std.getHinserialno()).isEqualTo(1234567890L);
		assertThat(std.getFname()).isEqualTo("JO ANN");                  // CommonUtil.NameValidation
		assertThat(std.getLname()).isEqualTo("SMITH");
		assertThat(std.getPersonCity()).isEqualTo("ALBANY");
		assertThat(std.getPersonState()).isEqualTo("NY");                // CommonDocumentService.validateState
		assertThat(std.getPersonZip()).isNull();                          // blank text is stored as null
		assertThat(std.getFieldnote()).isEqualTo("SEEN");
		assertThat(std.getMatchWeight()).isEqualByComparingTo("12.5");
		assertThat(std.getFluxMatchCd()).isEqualTo(" a ");                // copied as is (no TRIM/UPPER in the former insert)
		assertThat(std.getDob()).isEqualTo(Timestamp.valueOf("1980-01-02 10:11:12"));
		assertThat(std.getCurrentProcessStatus()).isEqualTo("5");
	}

	@Test
	void takesTheProviderStateFromProvSt() {
		assertThat(StdConfDocMapping.toStdDoc(raw("PROV_ST", "New York", "PROV_STATE", "XX"), STATES).getProvState()).isEqualTo("NY");
		assertThat(StdConfDocMapping.toStdDoc(raw("PROV_STATE", "xx"), STATES).getProvState()).isEqualTo("XX");   // no PROV_ST
		assertThat(StdConfDocMapping.toStdDoc(raw("PERSON_STATE", ""), STATES).getPersonState()).isNull();
	}

	@Test
	void codesRaceRiskAndSex() {
		StdStdConfirmedDoc std = StdConfDocMapping.toStdDoc(raw("RACE_AMERIND", "Y", "RACE_INDIAN", "N", "RISK_SEX_MSM", "1",
				"ETHNICITY", "2", "BIRTH_SEX", "F", "CURRENT_GENDER", "M", "RISK_DRUGS_USED", " y "), STATES);

		assertThat(std.getRaceIndian()).isEqualTo(upper(LabCommonUtils.checkRaceData("Y")));   // from RACE_AMERIND
		assertThat(std.getRiskSexMsm()).isEqualTo(upper(LabCommonUtils.checkRiskData("1")));
		assertThat(std.getEthnicity()).isEqualTo(upper(LabCommonUtils.checkEthnicityData("2")));
		assertThat(std.getBirthSex()).isEqualTo(upper(LabCommonUtils.checkBirthSexData("F")));
		assertThat(std.getCurrentGender()).isEqualTo(upper(LabCommonUtils.checkCurrentGenderData("M")));
		assertThat(std.getRiskDrugsUsed()).isEqualTo("Y");                 // copied (the former service coded RISK_DRUG_USED, which is not written)
	}

	@Test
	void flagsTheOtherRiskAndTheListedDrugs() {
		StdStdConfirmedDoc std = StdConfDocMapping.toStdDoc(raw("RISK_OTHER_TEXT", "needles", "RISK_DRUG_USED_LIST", "1,3,14, 2,1",
				"RISK_DRUG_USED_ALCOHOL", "n"), STATES);

		assertThat(std.getRiskOther()).isEqualTo("Y");
		assertThat(std.getRiskOtherText()).isEqualTo("NEEDLES");
		assertThat(std.getRiskDrugUsedCrack()).isEqualTo("Y");            // 1
		assertThat(std.getRiskDrugUsedHeroin()).isEqualTo("Y");           // 3
		assertThat(std.getRiskDrugUsedPrescriptionOpiods()).isEqualTo("Y");   // 14
		assertThat(std.getRiskDrugUsedCocaine()).isNull();                // " 2" is not "2": entries are not trimmed, as before
		assertThat(std.getRiskDrugUsedAlcohol()).isEqualTo("N");          // not listed: the field as received
		assertThat(StdConfDocMapping.toStdDoc(raw(), STATES).getRiskOther()).isEqualTo("N");
	}

	@Test
	void doesNotChangeTheReceivedRecord() {
		Map<Object, Object> raw = raw("FNAME", "jo_ann", "RISK_OTHER_TEXT", "x", "RISK_DRUG_USED_LIST", "1");
		Map<Object, Object> copy = new HashMap<>(raw);

		StdConfDocMapping.toStdDoc(raw, STATES);

		assertThat(raw).isEqualTo(copy);
	}

	private static String upper(String value) {
		return OracleConversions.upper(value);
	}

	private static Map<Object, Object> raw(Object... keysAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			raw.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return raw;
	}
}
