package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.dataentry.ICCRDataEntryVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawIccrDoc;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdIccrDoc;

/**
 * The ICCR report rows: as entered on the data entry screen (the former ICCRDocumentDAO.saveICCRDocumentData)
 * and standardized by the import (the former ICCRDocumentService.writeRawICCRDocDataToStdTable and
 * ICCRDocumentDAO.writeStdDocData), including the cleaning their SQL inserts did in Oracle.
 */
class IccrDocMappingTest {

	@Test
	void cleansTheEnteredReport() {
		ICCRDataEntryVO form = new ICCRDataEntryVO();
		form.setDOCUMENT_ID(12);
		form.setFIRST_NAME("  jane ");
		form.setLAST_NAME("o'neil");
		form.setDOB(" 3/4/2023 ");
		form.setPERSON_ZIP("  ");
		form.setCOMMENTS("seen at clinic");
		form.setCREATE_UID(900);

		RawIccrDoc raw = IccrDocMapping.toRawDoc(form);

		assertThat(raw.getDocumentId()).isEqualTo(12);
		assertThat(raw.getFirstName()).isEqualTo("JANE");                                // TRIM(UPPER(...))
		assertThat(raw.getLastName()).isEqualTo("O'NEIL");
		assertThat(raw.getMiddleName()).isNull();
		assertThat(raw.getPersonZip()).isNull();                                         // blank text is stored as null
		assertThat(raw.getComments()).isEqualTo("SEEN AT CLINIC");
		assertThat(raw.getDob()).isEqualTo(Timestamp.valueOf("2023-03-04 00:00:00"));   // FN_TO_DATE(..., 'MM/DD/YYYY')
		assertThat(raw.getCreateUid()).isEqualTo(900);
		assertThat(raw.getCreateDate()).isNotNull();                                     // SYSDATE
		assertThat(raw.getCurrentProcessStatus()).isEqualTo(1);
	}

	@Test
	void anInvalidDateOfBirthIsStoredAsNull() {
		ICCRDataEntryVO form = new ICCRDataEntryVO();
		form.setDOB("02/30/2023");

		assertThat(IccrDocMapping.toRawDoc(form).getDob()).isNull();
	}

	@Test
	void cleansTheNamesAndAddressesOfTheStandardizedReport() {
		Map<Object, Object> raw = raw("DOCUMENT_ID", new BigDecimal("12"), "FIRST_NAME", "jo_ann", "MIDDLE_NAME", "b.", "LAST_NAME", " smith ",
				"PERSON_ADDRESS", "1  main   st!", "PROV_NAME", "dr. who?", "PROV_ADDRESS", "2 elm st", "PERSON_CITY", " albany ", "PERSON_ZIP", "  ");

		StdIccrDoc std = IccrDocMapping.toStdDoc(raw);

		assertThat(std.getDocumentId()).isEqualTo(12);
		assertThat(std.getFname()).isEqualTo("JO ANN");                  // from FIRST_NAME, cleaned (CommonUtil.NameValidation)
		assertThat(std.getMname()).isEqualTo("B.");
		assertThat(std.getLname()).isEqualTo("SMITH");
		assertThat(std.getPersonAddress()).isEqualTo("1 MAIN ST");       // CommonUtil.addressValidation
		assertThat(std.getProvName()).isEqualTo("DR. WHO");
		assertThat(std.getProvAddress()).isEqualTo("2 ELM ST");
		assertThat(std.getPersonCity()).isEqualTo("ALBANY");
		assertThat(std.getPersonZip()).isNull();
		assertThat(std.getProvCity()).isNull();                          // missing field
		assertThat(raw).doesNotContainKeys("FNAME", "MNAME", "LNAME");   // the received record is not changed
	}

	@Test
	void copiesTheOtherColumnsAndSetsTheStatus() {
		Timestamp dob = Timestamp.valueOf("1980-01-02 00:00:00");
		Timestamp created = Timestamp.valueOf("2023-03-04 10:11:12");
		StdIccrDoc std = IccrDocMapping.toStdDoc(raw("CREATE_UID", new BigDecimal("900"), "CREATE_DATE", created, "DOB", dob,
				"MATCH_WEIGHT", new BigDecimal("12.50"), "FLUX_QS_PARENT_DOC_ID", new BigDecimal("77"), "FLUX_MATCH_CD", " a ",
				"CURRENT_PROCESS_STATUS", new BigDecimal("2")));

		assertThat(std.getCreateUid()).isEqualTo("900");                 // as received (text, as the former insert)
		assertThat(std.getCreateDate()).isEqualTo(created);              // as received, with its time
		assertThat(std.getDob()).isEqualTo(dob);
		assertThat(std.getMatchWeight()).isEqualTo("12.5");              // a number as Oracle converts it to text
		assertThat(std.getFluxQsParentDocId()).isEqualTo(77L);
		assertThat(std.getFluxMatchCd()).isEqualTo(" a ");               // copied as is (no TRIM/UPPER in the former insert)
		assertThat(std.getCurrentProcessStatus()).isEqualTo("5");        // standardized
	}

	private static Map<Object, Object> raw(Object... keysAndValues) {
		Map<Object, Object> raw = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			raw.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return raw;
	}
}
