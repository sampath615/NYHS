package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycijrDoc;

/**
 * The standardized NYC IJR record built from the values NycIJRDocumentService prepared, with the cleaning the former
 * SQL insert did in Oracle (upper/trim, FN_TO_DATE, empty text stored as null).
 */
class NycIjrStdDocMappingTest {

	@Test
	void trimsAndUpperCasesTheText() {
		StdNycijrDoc std = NycIjrStdDocMapping.toStdDoc(values("LNAME", " doe ", "DX_PERSON_CITY", "  ", "RACE_BLACK", 1,
				"FILE_NAME", " ijr.txt ", "FLUX_MATCH_CD", " m "));

		assertThat(std.getLname()).isEqualTo("DOE");
		assertThat(std.getDxPersonCity()).isNull();         // blank text is stored as null
		assertThat(std.getRaceBlack()).isEqualTo("1");      // a number written to a text column
		assertThat(std.getFileName()).isEqualTo("ijr.txt"); // trimmed only
		assertThat(std.getFluxMatchCd()).isEqualTo(" m ");  // written as it is
		assertThat(std.getFname()).isNull();                // missing value
	}

	@Test
	void writesTheNumbers() {
		StdNycijrDoc std = NycIjrStdDocMapping.toStdDoc(values("DOCUMENT_ID", new BigDecimal("12"), "AGE", 41,
				"CURRENT_PROCESS_STATUS", 5, "NYC_IJR_SRC_ID", "123456789012", "MATCH_WEIGHT", "12.5", "NUMBEROFPARTNERS", "2"));

		assertThat(std.getDocumentId()).isEqualTo(12);
		assertThat(std.getAge()).isEqualTo(41);
		assertThat(std.getCurrentProcessStatus()).isEqualTo(5);
		assertThat(std.getNycIjrSrcId()).isEqualTo(123456789012L);
		assertThat(std.getMatchWeight()).isEqualByComparingTo("12.5");
		assertThat(std.getNumberofpartners()).isEqualTo(2);
	}

	@Test
	void convertsTheDates() {
		StdNycijrDoc std = NycIjrStdDocMapping.toStdDoc(values(
				"PROV_SIGNED_DATE", "3/4/2023",                                   // FN_TO_DATE
				"COMPLETED_DATE", "02/30/2023",                                   // FN_TO_DATE of an invalid date
				"CREATE_DATE", Timestamp.valueOf("2023-05-06 13:45:00")));          // a date keeps its time

		assertThat(std.getProvSignedDate()).isEqualTo(Timestamp.valueOf("2023-03-04 00:00:00"));
		assertThat(std.getCompletedDate()).isNull();
		assertThat(std.getCreateDate()).isEqualTo(Timestamp.valueOf("2023-05-06 13:45:00"));
		assertThat(std.getFileDate()).isNull();
	}

	private static Map<Object, Object> values(Object... keysAndValues) {
		Map<Object, Object> values = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			values.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return values;
	}
}
