package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.DeathRecordRules.ZipCode;

/**
 * The rules the death and NYC death imports share (formerly copied in DeathDocumentService and
 * NycDeathDocumentService).
 */
class DeathRecordRulesTest {

	@Test
	void copiesTheCausesAndFlagsHivAndAidsDefiningOnes() {
		Map<Object, Object> std = new HashMap<>();
		DeathRecordRules.putCauses(record("ICD10", "B201", "UNDERLYING", "I10", "CAUSE1", "A150", "CAUSE2", "X99"), std);

		assertThat(std).containsEntry("ICD10", "B20.1").containsEntry("UNDERLYING", "I10")
				.containsEntry("CAUSE1", "A15.0").containsEntry("CAUSE2", "X99")
				.containsEntry("H_CAUSE", "Y")      // B20: HIV
				.containsEntry("OI_CAUSE", "Y");    // A15: AIDS-defining
	}

	@Test
	void readsTheCausesUpToTheFirstEmptyOneAndSkipsCause13AsBefore() {
		Map<Object, Object> raw = record("CAUSE1", "I10", "CAUSE2", "", "CAUSE3", "B20");
		Map<Object, Object> std = new HashMap<>();
		DeathRecordRules.putCauses(raw, std);
		assertThat(std).doesNotContainKey("CAUSE3").containsEntry("H_CAUSE", "N").containsEntry("OI_CAUSE", "N");

		Map<Object, Object> all = new HashMap<>();
		for (int i = 1; i <= 20; i++) {
			all.put("CAUSE" + i, "I1" + i);
		}
		all.put("CAUSE13", "B20");
		Map<Object, Object> stdAll = new HashMap<>();
		DeathRecordRules.putCauses(all, stdAll);
		assertThat(stdAll).doesNotContainKey("CAUSE13").containsEntry("CAUSE20", "I12.0").containsEntry("H_CAUSE", "N");
	}

	@Test
	void buildsTheResidentAddress() {
		assertThat(DeathRecordRules.residentAddress(record("RES_STREET_NO", "12", "RES_STREET", "MAIN", "RES_STREET_TYPE", "ST", "RES_APT_NO", "4B")))
				.isEqualTo("12 MAIN ST  APT 4B");
		assertThat(DeathRecordRules.residentAddress(record("RES_STREET", "MAIN", "RES_APT_NO", " "))).isEqualTo("MAIN ");
		assertThat(DeathRecordRules.residentAddress(record())).isEmpty();
	}

	@Test
	void splitsTheZipCode() {
		assertThat(ZipCode.of("12207")).isEqualTo(new ZipCode("12207", null));
		assertThat(ZipCode.of(null)).isEqualTo(new ZipCode(null, null));
	}

	@Test
	void takesPfiNewWhenThePfiIsUnknownAndDropsTwoLeadingZeros() {
		assertThat(DeathRecordRules.pfi(record("PFI", "1234", "PFI_NEW", "005678"))).isEqualTo("1234");
		assertThat(DeathRecordRules.pfi(record("PFI", "9999", "PFI_NEW", "005678"))).isEqualTo("5678");
		assertThat(DeathRecordRules.pfi(record("PFI", "", "PFI_NEW", null))).isNull();
	}

	@Test
	void tellsTheCauseOfDeath() {
		assertThat(DeathRecordRules.causeOfDeath("Y", "Y", () -> { throw new AssertionError("not asked"); }))
				.isEqualTo("Opportunistic Infection (OI) Cause of Death");
		assertThat(DeathRecordRules.causeOfDeath("Y", "N", () -> { throw new AssertionError("not asked"); })).isEqualTo("HIV Cause of Death");
		assertThat(DeathRecordRules.causeOfDeath("N", "Y", () -> true)).isEqualTo("Meets Case Definition");
		assertThat(DeathRecordRules.causeOfDeath(null, null, () -> false)).isNull();
	}

	private static Map<Object, Object> record(Object... keysAndValues) {
		Map<Object, Object> record = new HashMap<>();
		for (int i = 0; i < keysAndValues.length; i += 2) {
			record.put(keysAndValues[i], keysAndValues[i + 1]);
		}
		return record;
	}
}
