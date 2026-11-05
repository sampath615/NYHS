package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawFacilityRepository;
import lombok.RequiredArgsConstructor;

/**
 * Raw facilities (formerly a MyBatis mapper with the same name and methods): the reads are JPQL projections of the
 * views and tables (mapped into RawSubFacilityVO as the former result map rawSubFacilityResult mapped them), the
 * insert is a RAW_FACILITY entity, and the three PKG_STANDARDIZATION procedures are called through
 * {@link NativeStatements#call} with their OUT values and cursor rows put back into the caller's map.
 * <p>
 * The dates the former SQL set with SYSDATE are the application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class RawSubFacilityDAO {

	private static final String NO_MATCH_FACILITIES = """
			select v.rawSubFacilityId as rawSubFacilityId, v.facilityTypeDesc as facilityTypeDesc, v.facilityName as facilityName,
			       v.facilityDepartment as facilityDepartment, v.legacyFacilityId as legacyFacilityId, v.address as address,
			       v.city as city, v.state as state, v.zipcode as zipcode, v.plus4 as plus4, v.phone as phone, v.npi as npi
			from RawFacsNoMatchView v""";

	private static final String CLERICAL_REVIEW_FACILITIES = """
			select v.rawSubFacilityId as rawSubFacilityId, v.facilityTypeDesc as facilityTypeDesc, v.facilityName as facilityName,
			       v.facilityDepartment as facilityDepartment, v.legacyFacilityId as legacyFacilityId, v.address as address,
			       v.city as city, v.state as state, v.zipcode as zipcode, v.plus4 as plus4, v.phone as phone
			from RawFacsClericReviewView v
			""";

	/** RAW_FACILITY_ID is selected too, but nothing reads it (no property of that name; the result map reads POT_RAW_FACILITY_ID). */
	private static final String POTENTIAL_MATCHES = """
			select m.potRawFacilityId as pmRawFacilityId from RawFacilityMatchResults m where m.rawFacilityId = ?1
			""";

	/** The searchable grid columns (searchField -> attribute of NYEHMS_RAW_FACS_NO_MATCH_VW), as the former choose listed them. */
	private static final Map<String, String> SEARCH_FIELDS = new LinkedHashMap<>();

	static {
		for (String field : new String[] {"facilityName", "facilityTypeDesc", "facilityDepartment", "address", "city", "state", "zipcode",
				"phone"}) {
			SEARCH_FIELDS.put(field, field);
		}
	}

	/** The search operators (searchOper -> condition after "upper(field)"; %s is the parameter), except in / ni. */
	private static final Map<String, String> SEARCH_OPERATORS = new LinkedHashMap<>();

	static {
		SEARCH_OPERATORS.put("eq", "= upper(%s)");
		SEARCH_OPERATORS.put("ne", "!= upper(%s)");
		SEARCH_OPERATORS.put("bw", "like upper(%s) || '%%'");
		SEARCH_OPERATORS.put("bn", "not like upper(%s) || '%%'");
		SEARCH_OPERATORS.put("ew", "like '%%' || upper(%s)");
		SEARCH_OPERATORS.put("en", "not like '%%' || upper(%s)");
		SEARCH_OPERATORS.put("cn", "like '%%' || upper(%s) || '%%'");
		SEARCH_OPERATORS.put("nc", "not like '%%' || upper(%s) || '%%'");
	}

	private final JpqlStatements jpql;
	private final NativeStatements statements;
	private final RawFacilityRepository rawFacilities;

	/**
	 * The raw facilities without a match for the new-page grid: the first 100 rows, or, when searching, every row whose
	 * field (upper case) compares with the search string (upper case) as the jqGrid operator says.
	 * <p>
	 * <b>Kept as it was:</b> a search over a known field has no 100-row cap; a search with an unknown searchField and a
	 * known searchOper, or with a known searchField and an unknown searchOper, fails (the former SQL was invalid:
	 * "where = UPPER(?)", "where UPPER(col) ROWNUM &lt;= 100"); an unknown field with an unknown operator gives the first
	 * 100 rows. in / ni need a list or array (a text fails, an empty list fails). _search, searchField and searchOper are
	 * compared as the former OGNL tests compared them. The search values are bound as text (upper() takes text; the
	 * callers pass texts).
	 *
	 * @param inOutParameterMap _search, searchField, searchOper, searchString (an array for in / ni)
	 */
	public List<RawSubFacilityVO> getRawSubFacilityListNewPage(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		if (!OgnlTests.equalsTrue(map.get("_search"))) {
			return jpql.list(NO_MATCH_FACILITIES + " fetch first 100 rows only", Rows.bean(RawSubFacilityVO.class));
		}
		String field = null;
		for (String known : SEARCH_FIELDS.keySet()) {
			if (OgnlTests.equalsText(map.get("searchField"), known)) {
				field = SEARCH_FIELDS.get(known);
				break;
			}
		}
		Object oper = map.get("searchOper");
		String condition = null;
		List<Object> parameters = new ArrayList<>();
		for (Map.Entry<String, String> known : SEARCH_OPERATORS.entrySet()) {
			if (OgnlTests.equalsText(oper, known.getKey())) {
				parameters.add(text(map.get("searchString")));
				condition = known.getValue().formatted("?1");
				break;
			}
		}
		if (condition == null && (OgnlTests.equalsText(oper, "in") || OgnlTests.equalsText(oper, "ni"))) {
			List<Object> items = MasterFacilityDetailsDAO.items(map.get("searchString"), "searchString");
			if (items.isEmpty()) {
				throw new InvalidDataAccessResourceUsageException("Invalid search SQL: an empty in / ni list");
			}
			List<String> values = new ArrayList<>();
			for (Object item : items) {
				parameters.add(text(item));
				values.add("upper(?" + parameters.size() + ")");
			}
			condition = (OgnlTests.equalsText(oper, "in") ? "in (" : "not in (") + String.join(", ", values) + ")";
		}
		if (field == null && condition == null) {
			// "where ROWNUM <= 100"
			return jpql.list(NO_MATCH_FACILITIES + " fetch first 100 rows only", Rows.bean(RawSubFacilityVO.class));
		}
		if (field == null || condition == null) {
			throw new InvalidDataAccessResourceUsageException("Invalid search SQL (searchField " + map.get("searchField") + ", searchOper "
					+ oper + "): " + (field == null ? "where " + condition : "where UPPER(" + field + ") ROWNUM <= 100"));
		}
		return jpql.list(NO_MATCH_FACILITIES + " where upper(v." + field + ") " + condition, Rows.bean(RawSubFacilityVO.class),
				parameters.toArray());
	}

	/** The raw facilities for clerical (manual) review. */
	public List<RawSubFacilityVO> getRawSubFacilityListManualReview() {
		return jpql.list(CLERICAL_REVIEW_FACILITIES, Rows.bean(RawSubFacilityVO.class));
	}

	/**
	 * The raw facilities of a sub facility (PKG_STANDARDIZATION.PR_RAW_FACILITIES_BY_SUB): the cursor rows
	 * (rawSubFacilityResult) are put into the map as rawFacilityRefCursor.
	 *
	 * @param map subFacilityId, rawFacilityIds (comma-separated, optional)
	 * @return an empty list (the rows come through the map)
	 */
	public List<RawSubFacilityVO> getRawListByStdFacilityId(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_RAW_FACILITIES_BY_SUB",
				in(map, "subFacilityId", Long.class),
				in(map, "rawFacilityIds", String.class),
				cursor(FacilityResultMaps::rawSubFacility));
		map.put("rawFacilityRefCursor", results.get(2));
		return new ArrayList<>();
	}

	/**
	 * Links a raw facility to a sub facility (PKG_STANDARDIZATION.PR_LINK_RAW_SUB_FACILITY).
	 *
	 * @param map rawSubFacilityId, subFacilityId; gets p_code_out (Integer), p_msg_out
	 */
	public void updateStandardize(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_LINK_RAW_SUB_FACILITY",
				in(map, "rawSubFacilityId", Long.class),
				in(map, "subFacilityId", Long.class),
				out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(2));
		map.put("p_msg_out", results.get(3));
	}

	/**
	 * Manual review: the potential matches of a raw facility, each as a RawSubFacilityVO with only pmRawFacilityId set
	 * (a match without POT_RAW_FACILITY_ID is a null element, as before).
	 */
	public List<RawSubFacilityVO> getPotentialRawFacList(long rawSubFacilityId) {
		return jpql.list(POTENTIAL_MATCHES, Rows.bean(RawSubFacilityVO.class), rawSubFacilityId);
	}

	/**
	 * Turns a facility into a provider (PKG_STANDARDIZATION.pr_flip_to_provider).
	 *
	 * @param inOutParameterMap facilityId, workerId; gets statusCode (Integer), statusMessage
	 */
	public void flagFacilityAsProvider(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_flip_to_provider",
				in(map, "facilityId", Long.class),
				in(map, "workerId", Long.class),
				out(Integer.class), out(String.class));
		map.put("statusCode", results.get(2));
		map.put("statusMessage", results.get(3));
	}

	/**
	 * The id of a raw facility with the same name, address, city, phone, plus4, state and zipcode (an empty value
	 * matches a NULL column), or 0 if there is none.
	 * <p>
	 * <b>Kept as it was:</b> of several such raw facilities it is the first one found (the former NVL(MAX(...), 0) was
	 * taken over "rownum &lt; 2", i.e. over one row), not the highest id.
	 */
	public int checkRawFacilityExistance(RawSubFacilityVO rawSubFacilityVO) {
		RawSubFacilityVO vo = rawSubFacilityVO;
		List<String> conditions = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();
		equalOrNull(conditions, parameters, "facilityName", vo.getFacilityName());
		equalOrNull(conditions, parameters, "address", vo.getAddress());
		equalOrNull(conditions, parameters, "city", vo.getCity());
		equalOrNull(conditions, parameters, "phone", vo.getPhone());
		equalOrNull(conditions, parameters, "plus4", vo.getPlus4());
		equalOrNull(conditions, parameters, "state", vo.getState());
		equalOrNull(conditions, parameters, "zipcode", vo.getZipcode());
		List<Long> ids = jpql.list("select r.rawSubFacilityId as ID from RawFacility r where " + String.join(" and ", conditions)
				+ " fetch first 1 rows only", Rows.scalar(Long.class), parameters.toArray());
		Long id = ids.isEmpty() ? null : ids.get(0);
		return id == null ? 0 : Math.toIntExact(id);
	}

	private static void equalOrNull(List<String> conditions, List<Object> parameters, String attribute, String value) {
		if (value != null && !value.isEmpty()) {
			parameters.add(value);
			conditions.add("r." + attribute + " = ?" + parameters.size());
		} else {
			conditions.add("r." + attribute + " is null");
		}
	}

	/**
	 * Inserts a raw facility (new id from RAW_FACILITY_SEQ) and puts its id into the map as rawFacilityId (a
	 * BigDecimal, as the former generated key was).
	 *
	 * @param inOutParameterMap p_subfacility_id, p_facility_fname, p_address, p_city, p_state, p_zipcode, p_plus4,
	 *        p_userId, p_phone, p_county
	 * @return 1
	 */
	@Transactional
	public int createRawFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		RawFacility raw = rawFacilities.saveAndFlush(RawFacility.builder()
				.subFacilityId(longValue(map.get("p_subfacility_id")))
				.facilityName(text(map.get("p_facility_fname")))
				.address(text(map.get("p_address")))
				.city(text(map.get("p_city")))
				.state(text(map.get("p_state")))
				.zipcode(text(map.get("p_zipcode")))
				.plus4(text(map.get("p_plus4")))
				.createUid(text(map.get("p_userId")))
				.dateCreated(new Timestamp(System.currentTimeMillis()))
				.phone(text(map.get("p_phone")))
				.countyFips(text(map.get("p_county")))
				.build());
		map.put("rawFacilityId", BigDecimal.valueOf(raw.getRawSubFacilityId()));
		return 1;
	}

	/** The former OGNL tests of the search parameters. */
	static final class OgnlTests {

		private OgnlTests() {
		}

		/**
		 * {@code value == true}: a Boolean as it is, a number equal to 1, a text read as a number equal to 1 (a text that
		 * is not a number fails, as the OGNL comparison did), null false.
		 */
		static boolean equalsTrue(Object value) {
			if (value == null) {
				return false;
			}
			if (value instanceof Boolean flag) {
				return flag;
			}
			if (value instanceof Double || value instanceof Float) {
				return ((Number) value).doubleValue() == 1.0;
			}
			if (value instanceof Number number) {
				return new BigDecimal(number.toString()).compareTo(BigDecimal.ONE) == 0;
			}
			if (value instanceof Character character) {
				return character.charValue() == 1;
			}
			String text = value.toString().trim();
			try {
				return (text.isEmpty() ? 0.0 : Double.parseDouble(text)) == 1.0;
			} catch (NumberFormatException e) {
				throw new InvalidDataAccessApiUsageException("Error evaluating expression '_search == true': " + e.getMessage(), e);
			}
		}

		/** {@code value == 'text'} for a text of several characters: a String equal to it, null false; other types fail. */
		static boolean equalsText(Object value, String text) {
			if (value == null) {
				return false;
			}
			if (value instanceof String string) {
				return string.equals(text);
			}
			throw new InvalidDataAccessApiUsageException("Error evaluating expression '== " + text + "': cannot compare " + value.getClass().getName());
		}
	}
}
