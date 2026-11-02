package gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * The lab proficiency filter conditions with bound values ({@link LabCommonUtils#getQueryCondition(List, List)}),
 * against the conditions the text version builds.
 */
class LabFilterConditionTest {

	@SuppressWarnings("unchecked")
	private static JSONObject condition(String field, String condition, String value, String operator) {
		JSONObject json = new JSONObject(new HashMap<>());
		json.put("FIELD_NAME", field);
		json.put("CONDITION", condition);
		if (value != null) {
			json.put("VALUE", value);
		}
		if (operator != null) {
			json.put("OPERATOR", operator);
		}
		return json;
	}

	@Test
	void buildsTheSameConditionsWithTheValuesAsParameters() {
		List<JSONObject> conditions = List.of(condition("LAST NAME", "LIKE", "smi", "AND"), condition("PATIENT CITY", "EQUAL", "albany", "OR"),
				condition("DOB", "STARTWITH", "01/", "AND"), condition("FIRST NAME", "NOTEQUAL", "", " "), condition("LOINC", "NOTLIKE", "x", null));
		List<Object> values = new ArrayList<>();

		String withParameters = LabCommonUtils.getQueryCondition(conditions, values);

		String text = LabCommonUtils.getQueryCondition(conditions);
		String inlined = withParameters;
		for (Object value : values) {
			inlined = inlined.replaceFirst("\\?", "'" + value + "'");
		}
		assertThat(inlined).isEqualTo(text);
		assertThat(values).containsExactly("%SMI%", "ALBANY", "01/%", "%X%");
	}

	@Test
	void aQuoteIsPartOfTheValueNotOfTheSql() {
		List<Object> values = new ArrayList<>();
		String sql = LabCommonUtils.getQueryCondition(List.of(condition("LAST NAME", "EQUAL", "O'Neil') OR (1=1", null)), values);
		assertThat(sql).isEqualTo("(UPPER(PIDLASTNAME) = ?)");
		assertThat(values).containsExactly("O'NEIL') OR (1=1");
	}

	@Test
	void skipsUnknownFieldsAndRefusesOtherOperators() {
		List<Object> values = new ArrayList<>();
		assertThat(LabCommonUtils.getQueryCondition(List.of(condition("NOT A FIELD", "EQUAL", "x", null)), values)).isEmpty();
		assertThat(values).isEmpty();
		assertThatThrownBy(() -> LabCommonUtils.getQueryCondition(List.of(condition("LAST NAME", "EQUAL", "x", "; DROP")), new ArrayList<>()))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
