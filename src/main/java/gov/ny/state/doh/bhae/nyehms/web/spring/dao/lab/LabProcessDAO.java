package gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FilterConditionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.FilterTypeAndFilterConditionsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.CodedProcessFilterTypeRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.LabProficiencyRepository;
import lombok.RequiredArgsConstructor;

/**
 * The lab process / proficiency filter conditions the scheduler validates the eCLRS HIV documents against (formerly a
 * MyBatis mapper with the same name and methods), through JPQL over the entities of {@code entity.lab}; the nested
 * result map of the filter conditions is grouped here as MyBatis grouped it.
 * <p>
 * {@link #checkDataAgainstQueryConditions} stays native SQL: its condition is SQL text the scheduler builds from the
 * filter rows (see there).
 */
@Repository
@RequiredArgsConstructor
public class LabProcessDAO {

	private static final String FILTER_CONDITIONS = """
			select f.filterType, f.filterNumber, f.condition, f.fieldName, f.value, coalesce(f.operator, ' ')
			from NyehmsProcessFilterVw f
			where f.filterFlagActive = 'Y' and f.pfi in ?1""";

	/**
	 * Native: the condition is SQL text built by ManualQuartzJobService from the filter rows of NYEHMS_PROCESS_FILTER_VW
	 * (FIELD_NAME / CONDITION / VALUE / OPERATOR), pasted into the WHERE clause as before. <b>Open security point:</b>
	 * whoever can edit the lab proficiency filter conditions can put SQL into this statement (unchanged from MyBatis'
	 * {@code ${queryCondition}}); the document id and PFI are bound.
	 */
	private static final String CHECK_DATA = "select count(*) from RAW_HIV_ECLRS_DOC where DOCUMENT_ID = ?1 and MSHLABPFI = ?2 and (%s)";

	private static final String BYPASS_ERROR_CODES = """
			select distinct d.errorCode from NyehmsProcessErrorsDetail d
			where d.bypassError = 'Y'
			and d.errorId in (select e.errorId from NyehmsProcessErrors e where e.documentId = ?1 and e.processStatus in (6, 15))""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final CodedProcessFilterTypeRepository filterTypes;
	private final LabProficiencyRepository labProficiency;

	/**
	 * The active filter conditions of the given PFIs, grouped by filter type and filter number (as the former nested
	 * result map grouped them: a filter type by its value, a filter number within its type, the condition rows of a
	 * filter number without repeats; in the order of their first row). Each condition is a JSONObject with CONDITION,
	 * FIELD_NAME, VALUE (null values left out; NVL(VALUE, '') was NULL on Oracle) and OPERATOR (' ' when null).
	 *
	 * @param inputOutputParamters pfiList (required, not empty), filterTypes and filterNumbers (optional; when given, not
	 *        empty: the former SQL failed then too)
	 */
	public List<FilterTypeAndFilterConditionsVO> getFilterConditionsByFilterTypes(Map<Object, Object> inputOutputParamters) {
		StringBuilder query = new StringBuilder(FILTER_CONDITIONS);
		List<Object> parameters = new ArrayList<>();
		parameters.add(LaftRows.inList(inputOutputParamters.get("pfiList"), "pfiList", Values::text));
		if (inputOutputParamters.get("filterTypes") != null) {
			parameters.add(LaftRows.longs(inputOutputParamters.get("filterTypes"), "filterTypes"));
			query.append(" and f.filterType in ?").append(parameters.size());
		}
		if (inputOutputParamters.get("filterNumbers") != null) {
			parameters.add(LaftRows.longs(inputOutputParamters.get("filterNumbers"), "filterNumbers"));
			query.append(" and f.filterNumber in ?").append(parameters.size());
		}
		query.append(" order by f.filterType, f.filterNumber, coalesce(f.operator, ' ') desc");
		return group(jpql.list(query.toString(), row -> row, parameters.toArray()));
	}

	/** The former nested result map: FILTER_TYPE (no id: its value) -> FILTER_NUMBER (id) -> condition rows (no id: all values). */
	@SuppressWarnings("unchecked")
	private static List<FilterTypeAndFilterConditionsVO> group(List<Row> rows) {
		List<FilterTypeAndFilterConditionsVO> types = new ArrayList<>();
		Map<Object, FilterTypeAndFilterConditionsVO> typesByKey = new HashMap<>();
		Map<List<Object>, FilterConditionsVO> numbersByKey = new HashMap<>();
		Map<List<Object>, JSONObject> conditionsByKey = new HashMap<>();
		for (Row row : rows) {
			Long filterType = (Long) row.get(0);
			Long filterNumber = (Long) row.get(1);
			JSONObject condition = new JSONObject();
			putIfSet(condition, "CONDITION", row.get(2));
			putIfSet(condition, "FIELD_NAME", row.get(3));
			putIfSet(condition, "VALUE", row.get(4));
			putIfSet(condition, "OPERATOR", row.get(5));
			if (condition.isEmpty()) {
				continue;                                    // no condition value: nothing linked (nor its parents created)
			}
			// a null key value is a "null key" in MyBatis: never matched, so such a row gets new objects
			FilterTypeAndFilterConditionsVO type = filterType == null ? null : typesByKey.get(filterType);
			if (type == null) {
				type = new FilterTypeAndFilterConditionsVO();
				if (filterType != null) {
					type.setFILTER_TYPE(filterType.intValue());
					typesByKey.put(filterType, type);
				}
				types.add(type);
			}
			List<Object> numberKey = filterType == null || filterNumber == null ? null : List.of(filterType, filterNumber);
			FilterConditionsVO number = numberKey == null ? null : numbersByKey.get(numberKey);
			if (number == null) {
				number = new FilterConditionsVO();
				if (filterNumber != null) {
					number.setFILTER_NUMBER(filterNumber.intValue());
				}
				if (numberKey != null) {
					numbersByKey.put(numberKey, number);
				}
				if (type.getFilterConditions() == null) {
					type.setFilterConditions(new ArrayList<>());
				}
				type.getFilterConditions().add(number);
			}
			List<Object> conditionKey = numberKey == null ? null
					: java.util.Arrays.asList(filterType, filterNumber, row.get(2), row.get(3), row.get(4), row.get(5));
			if (conditionKey != null && conditionsByKey.containsKey(conditionKey)) {
				continue;                                    // the same condition values again: linked once
			}
			if (conditionKey != null) {
				conditionsByKey.put(conditionKey, condition);
			}
			if (number.getFilterConditions() == null) {
				number.setFilterConditions(new ArrayList<>());
			}
			number.getFilterConditions().add(condition);
		}
		return types;
	}

	/**
	 * The number of matches of a document of a lab against a filter condition (0 or more).
	 *
	 * @param inputOutputParamters documentId, pfi, queryCondition (SQL text, see {@link #CHECK_DATA})
	 */
	public int checkDataAgainstQueryConditions(Map<Object, Object> inputOutputParamters) {
		Object condition = inputOutputParamters.get("queryCondition");
		String sql = String.format(CHECK_DATA, condition == null ? "" : condition.toString());
		Integer count = statements.one(sql, Rows.scalar(Integer.class), inputOutputParamters.get("documentId"), inputOutputParamters.get("pfi"));
		return count == null ? 0 : count;
	}

	/** The filter types (FILTER_TYPE_CODE, FILTER_TYPE_DESC), by description. */
	public List<Map<String, String>> getFilterTypeConditions() {
		List<Map<String, String>> rows = new ArrayList<>();
		filterTypes.findAll(Sort.by("filterTypeDesc")).forEach(type -> {
			Map<String, Object> row = new HashMap<>();
			if (type.getFilterTypeCode() != null) {
				row.put("FILTER_TYPE_CODE", BigDecimal.valueOf(type.getFilterTypeCode()));
			}
			if (type.getFilterTypeDesc() != null) {
				row.put("FILTER_TYPE_DESC", type.getFilterTypeDesc());
			}
			rows.add(row.isEmpty() ? null : stringMap(row));
		});
		return rows;
	}

	/**
	 * The active labs (CLIA, PFI, LAB_NAME; NVL(lab_name, ' ')), by name: the same statement as
	 * {@link LabProficiencyRepository#findActiveLabs()}, whose values are typed as getObject typed them.
	 */
	public List<Map<String, String>> getPfiLabNames() {
		List<Map<String, String>> rows = new ArrayList<>();
		for (Object[] lab : labProficiency.findActiveLabs()) {
			Map<String, Object> row = new HashMap<>();
			putIfSet(row, "CLIA", lab[0]);
			putIfSet(row, "PFI", lab[1]);
			putIfSet(row, "LAB_NAME", lab[2]);
			rows.add(row.isEmpty() ? null : stringMap(row));
		}
		return rows;
	}

	/** The distinct error codes bypassed for a document's process errors in status 6 or 15 (as text). */
	public List<String> getBypassErrorCodes(int documentId) {
		return jpql.list(BYPASS_ERROR_CODES, row -> row.get(0) == null ? null : row.get(0).toString(), (long) documentId);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map<String, String> stringMap(Map<String, Object> row) {
		return (Map) row;                                    // the declared type of the former mapper method; the values are as read
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void putIfSet(Map row, String key, Object value) {
		if (value != null) {
			row.put(key, value);
		}
	}
}
