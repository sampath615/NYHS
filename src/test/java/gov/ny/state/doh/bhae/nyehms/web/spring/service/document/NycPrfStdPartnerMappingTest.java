package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;

/**
 * The standardized NYC PRF partner built from a received partner (the rules of the former SQL
 * INSERT INTO STD_NYCPRF_PARTNER ... SELECT ... FROM RAW_NYCPRF_PARTNER).
 */
class NycPrfStdPartnerMappingTest {

	@Test
	void cleansTheReceivedPartner() {
		StdNycprfPartner std = NycPrfStdPartnerMapping.toStdPartner(raw(
				"patientId", " p1 ", "nycprfDocumentId", 700L, "prtlname", " lee ", "prtage", " 34 ",
				"source", " as is ", "p", "x", "prtcity", "  "));

		assertThat(std.getPatientId()).isEqualTo("p1");                    // trimmed, not upper-cased
		assertThat(std.getNycprfDocumentId()).isEqualTo("700");
		assertThat(std.getLastName()).isEqualTo("LEE");
		assertThat(std.getAge()).isEqualTo("34");
		assertThat(std.getSource()).isEqualTo(" as is ");                 // SOURCE and P are copied as they are
		assertThat(std.getPartners()).isEqualTo("x");
		assertThat(std.getCity()).isNull();                               // blank text is stored as null
		assertThat(std.getCreateUid()).isEqualTo("900");
		assertThat(std.getCreateDate()).isNotNull();
	}

	@Test
	void codesTheRace() {
		assertRace(" white ", "1", "0", "0", "0", "0", "0", "0", "01");
		assertRace("3", "0", "0", "0", "1", "0", "0", "0", "06");
		assertRace("Native Hawaiian", "0", "0", "0", "0", "0", "1", "0", "04");
		assertRace("5", "0", "0", "0", "0", "0", "0", "1", "05");
		assertRace("x", "0", "0", "0", "0", "0", "0", "0", null);
		assertRace(null, "0", "0", "0", "0", "0", "0", "0", null);
	}

	private static void assertRace(String received, String white, String black, String asian, String other,
			String unknown, String pacific, String indian, String code) {
		StdNycprfPartner std = NycPrfStdPartnerMapping.toStdPartner(raw("prtrace", received));
		assertThat(new String[] {std.getRaceWhite(), std.getRaceBlack(), std.getRaceAsian(), std.getRaceOther(),
				std.getRaceUnknown(), std.getRacePacific(), std.getRaceIndian(), std.getRaceCd()})
				.as("race %s", received)
				.containsExactly(white, black, asian, other, unknown, pacific, indian, code);
	}

	@Test
	void codesGenderEthnicityAndHivStatus() {
		StdNycprfPartner mtf = NycPrfStdPartnerMapping.toStdPartner(raw("prtcurgender", " mtf ", "prfHivstus", "Positive"));
		assertThat(mtf.getCurrentGender()).isEqualTo("F");
		assertThat(mtf.getEthnicity()).isNull();
		assertThat(mtf.getPartnerStatus()).isEqualTo("P");

		StdNycprfPartner y = NycPrfStdPartnerMapping.toStdPartner(raw("prtcurgender", "y", "prfHivstus", "?"));
		assertThat(y.getCurrentGender()).isEqualTo("Y");
		assertThat(y.getEthnicity()).isEqualTo("01");                     // the gender field also carries ethnicity
		assertThat(y.getPartnerStatus()).isNull();

		assertThat(NycPrfStdPartnerMapping.toStdPartner(raw("prtrace", "3", "prtcurgender", "N")).getEthnicity()).isEqualTo("01");
	}

	@Test
	void convertsTheFormDates() {
		StdNycprfPartner std = NycPrfStdPartnerMapping.toStdPartner(raw("updated", " 3/4/2023 ", "pridtinf", "02/30/2023"));

		assertThat(std.getUpdatedDate()).isEqualTo(Timestamp.valueOf("2023-03-04 00:00:00"));
		assertThat(std.getInformedDate()).isNull();                       // FN_TO_DATE of an invalid date
	}

	/** A received partner with the given fields (the entity is read-only: no builder, no setters). */
	private static RawNycprfPartner raw(Object... fieldsAndValues) {
		try {
			Constructor<RawNycprfPartner> constructor = RawNycprfPartner.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			RawNycprfPartner raw = constructor.newInstance();
			for (int i = 0; i < fieldsAndValues.length; i += 2) {
				Field field = RawNycprfPartner.class.getDeclaredField((String) fieldsAndValues[i]);
				field.setAccessible(true);
				field.set(raw, fieldsAndValues[i + 1]);
			}
			return raw;
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}
}
