package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawTbDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdTbDoc;

/**
 * The TB rows built for the import and the document copy, with the cleaning the former SQL inserts did in Oracle
 * (upper/trim, empty text stored as null).
 */
class TbDocMappingTest {

	@Test
	void importsTheProviderFromThePreparedProviderValues() {
		StdTbDoc std = TbDocMapping.toStdDoc(values("PROVIDER_CITY", " albany ", "PROV_CITY", "ignored", "LNAME", " doe ",
				"MNAME", "q", "MATCH_WEIGHT", "12.5", "DOCUMENT_ID", new BigDecimal("7"), "CURRENT_PROCESS_STATUS", 5));

		assertThat(std.getProvCity()).isEqualTo("ALBANY");
		assertThat(std.getLname()).isEqualTo("DOE");
		assertThat(std.getMname()).isNull();                       // the import never wrote MNAME
		assertThat(std.getMatchWeight()).isEqualByComparingTo("12.5");
		assertThat(std.getDocumentId()).isEqualTo(7);
		assertThat(std.getCurrentProcessStatus()).isEqualTo(5);
	}

	@Test
	void copiesTheColumnsOfTheOldRows() {
		StdTbDoc std = TbDocMapping.toStdCopy(values("PROV_CITY", "albany", "MNAME", "q", "SUFFIX", "jr", "SERIALNO",
				new BigDecimal("123456789012"), "DX_DATE", Timestamp.valueOf("2021-03-04 10:11:12"), "LNAME", "  "));

		assertThat(std.getProvCity()).isEqualTo("ALBANY");
		assertThat(std.getMname()).isEqualTo("Q");
		assertThat(std.getSuffix()).isEqualTo("JR");
		assertThat(std.getSerialno()).isEqualTo(123456789012L);
		assertThat(std.getDxDate()).isEqualTo(Timestamp.valueOf("2021-03-04 10:11:12"));
		assertThat(std.getLname()).isNull();                       // blank text is stored as null

		RawTbDoc raw = TbDocMapping.toRawCopy(values("STREET", " 1 main st ", "DOCUMENT_ID", 8,
				"DIAGNOSIS_DATE", new java.sql.Date(Timestamp.valueOf("2021-03-04 00:00:00").getTime())));
		assertThat(raw.getStreet()).isEqualTo("1 MAIN ST");
		assertThat(raw.getDocumentId()).isEqualTo(8);
		assertThat(raw.getDiagnosisDate()).isEqualTo(Timestamp.valueOf("2021-03-04 00:00:00"));
	}

	private static Map<Object, Object> values(Object... keysAndValues) {
		Map<Object, Object> values = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			values.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return values;
	}
}
