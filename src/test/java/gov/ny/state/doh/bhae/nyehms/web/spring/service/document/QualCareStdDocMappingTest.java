package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdQualityCareDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.QualCareRefCode;

/**
 * The standardized quality of care record built from a received record (the rules of the former
 * QualCareDocumentService.writeRawQualCareDocDataToStdTable plus the cleaning its SQL insert did in Oracle), and the
 * sub-group names of the document screen (the former LISTAGG over XMLTABLE).
 */
class QualCareStdDocMappingTest {

	@Test
	void takesTheRenamedFields() {
		StdQualityCareDoc std = QualCareStdDocMapping.toStdDoc(raw("DOCUMENT_ID", new BigDecimal("12"), "FIRST_NAME", " ann ",
				"LAST_NAME", "lee", "MRN", "m1", "STREET", " 1 main st ", "CITY", "albany", "ZIP", "12208", "HOUSING", "2",
				"ORG_NAME", "org", "CLINIC_NAME", "clinic", "QUAL_CARE_FILE", " q.txt "));

		assertThat(std.getDocumentId()).isEqualTo(12);
		assertThat(std.getFname()).isEqualTo("ANN");
		assertThat(std.getLname()).isEqualTo("LEE");
		assertThat(std.getFacilityAddress()).isEqualTo("1 MAIN ST");
		assertThat(std.getFacilityCity()).isEqualTo("ALBANY");
		assertThat(std.getFacilityZip()).isEqualTo("12208");
		assertThat(std.getHousingStatus()).isEqualTo("2");
		assertThat(std.getOrganizationName()).isEqualTo("ORG");
		assertThat(std.getFacilityName()).isEqualTo("CLINIC");
		assertThat(std.getQualCareFile()).isEqualTo(" q.txt ");            // written as received
		assertThat(std.getCurrentProcessStatus()).isEqualTo(5);
	}

	@Test
	void setsTheListedRaceFlagsAndKeepsTheOthers() {
		StdQualityCareDoc std = QualCareStdDocMapping.toStdDoc(raw("RACE", " w, nhpi ,X", "RACE_BLACK", "0", "RACE_OTHER", "1"));

		assertThat(std.getRaceWhite()).isEqualTo("1");
		assertThat(std.getRacePacific()).isEqualTo("1");
		assertThat(std.getRaceBlack()).isEqualTo("0");      // not listed: the field as received
		assertThat(std.getRaceAsian()).isNull();
		assertThat(std.getRaceOther()).isEqualTo("1");      // no race code: always the field
	}

	@Test
	void removesWhitespaceFromTheServiceLine() {
		assertThat(QualCareStdDocMapping.toStdDoc(raw("SERVICE_LINE", " a b\tc ")).getServiceLine()).isEqualTo("ABC");
		assertThat(QualCareStdDocMapping.toStdDoc(raw("SERVICE_LINE", "  ")).getServiceLine()).isNull();
	}

	@Test
	void writesTheDateOfBirthAsText() {
		assertThat(QualCareStdDocMapping.toStdDoc(raw("DOB", Timestamp.valueOf("1980-01-02 10:11:12"))).getDobText()).isEqualTo("01/02/1980");
		assertThat(QualCareStdDocMapping.toStdDoc(raw("DOB", null)).getDobText()).isNull();
		assertThatThrownBy(() -> QualCareStdDocMapping.toStdDoc(raw("DOB", "01/02/1980"))).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void writesTheDatesAndNumbers() {
		StdQualityCareDoc std = QualCareStdDocMapping.toStdDoc(raw("HIV_CARE_DATE", Timestamp.valueOf("2021-04-05 06:07:08"),
				"MATCH_WEIGHT", new BigDecimal("12.5"), "AGE", new BigDecimal("41"), "ID", "7"));

		assertThat(std.getHivCareDateValue()).isEqualTo(Timestamp.valueOf("2021-04-05 06:07:08"));
		assertThat(std.getHivCareDate()).isNull();          // the screen's text attribute is never written
		assertThat(std.getMatchWeight()).isEqualByComparingTo("12.5");
		assertThat(std.getAge()).isEqualTo(41);
		assertThat(std.getRecordId()).isEqualTo(7);
	}

	@Test
	void doesNotChangeTheReceivedRecord() {
		Map<Object, Object> raw = raw("FIRST_NAME", "ann", "RACE", "W", "SERVICE_LINE", "a b", "STREET", "x");
		Map<Object, Object> copy = new HashMap<>(raw);

		QualCareStdDocMapping.toStdDoc(raw);

		assertThat(raw).isEqualTo(copy);
	}

	@Test
	void namesTheSubGroupCodesByCode() {
		List<QualCareRefCode> codes = List.of(new QualCareRefCode("ASIAN_SUB_GROUP", "10", "Other Asian"),
				new QualCareRefCode("ASIAN_SUB_GROUP", "01", "Chinese"), new QualCareRefCode("ASIAN_SUB_GROUP", "02", "Indian"),
				new QualCareRefCode("HISPANIC_SUB_GROUP", "02", "Puerto Rican"), new QualCareRefCode("ASIAN_SUB_GROUP", "03", null));

		assertThat(QualCareDocumentService.subGroupNames("10, 01,02,99,01", "ASIAN_SUB_GROUP", codes))
				.isEqualTo("Chinese, Chinese, Indian, Other Asian");
		assertThat(QualCareDocumentService.subGroupNames("02", "HISPANIC_SUB_GROUP", codes)).isEqualTo("Puerto Rican");
		assertThat(QualCareDocumentService.subGroupNames("99,,03", "ASIAN_SUB_GROUP", codes)).isNull();   // no named code
		assertThat(QualCareDocumentService.subGroupNames(null, "ASIAN_SUB_GROUP", codes)).isNull();
	}

	private static Map<Object, Object> raw(Object... keysAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			raw.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return raw;
	}
}
