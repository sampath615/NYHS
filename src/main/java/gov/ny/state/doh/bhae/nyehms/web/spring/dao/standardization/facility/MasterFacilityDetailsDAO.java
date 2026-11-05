package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.rows;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.RawSubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Rows;
import lombok.RequiredArgsConstructor;

/**
 * Facility searches (formerly a MyBatis mapper with the same name and methods).
 * <p>
 * The four searches are PKG_STANDARDIZATION procedures, called through {@link NativeStatements#call}; their cursor
 * rows are grouped into master facilities with their sub facilities (phones, raw facilities) as the former result map
 * masterFacilitiesWithSubFacilities grouped them ({@link FacilityResultMaps#masterFacilities}) and put into the
 * caller's map under the former keys. The other two reads are JPQL over the MASTER_FACILITY, SUB_FACILITY and
 * RAW_FACILITY entities.
 */
@Repository
@RequiredArgsConstructor
public class MasterFacilityDetailsDAO {

	private static final String SUB_FACILITIES_FOR_SUB_IDS = """
			select mf.masterFacilityId as MASTER_FACILITY_ID, mf.masterFacilityName as MASTER_FACILITY_NAME,
			       sf.subFacilityId as SUB_FACILITY_ID, sf.facilityTypeCode as FACILITY_TYPE_CODE, sf.facilityName as FACILITY_NAME,
			       rf.rawSubFacilityId as RAW_SUB_FACILITY_ID
			from MasterFacility mf, SubFacility sf, RawFacility rf
			where mf.masterFacilityId = sf.masterFacilityId and sf.subFacilityId = rf.subFacilityId and sf.subFacilityId in (%s)
			""";

	private static final String ASSIGNMENT_FACILITY_DETAILS = """
			select r.rawSubFacilityId as rawSubFacilityId, r.facilityTypeCode as facilityTypeCode, r.facilityName as facilityName,
			       r.facilityDepartment as facilityDepartment, r.address as address, r.city as city, r.state as state,
			       r.zipcode as zipcode, r.plus4 as plus4, r.phone as phone, r.countyFips as countyFips, r.npi as npi
			from RawFacility r""";

	/** The optional LIKE filters of getAssignmentFacilityDetails: map key -> attribute, in the former order. */
	private static final String[][] ASSIGNMENT_FILTERS = {
			{"p_facname", "facilityName"}, {"p_fac_dept", "facilityDepartment"}, {"p_county", "countyFips"}, {"p_address", "address"},
			{"p_city", "city"}, {"p_state", "state"}, {"p_zipcode", "zipcode"}, {"p_plus4", "plus4"}, {"p_phone", "phone"},
			{"p_npi", "npi"}};

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/**
	 * The facility search of the unstandardized-facility page (PKG_STANDARDIZATION.pr_facility_search): the grouped
	 * rows are put into the map as p_sub_prov_out.
	 *
	 * @return an empty list (the rows come through the map)
	 */
	@SuppressWarnings("unchecked")
	public List<MasterFacilityDetailsVO> getMasterFacilitiesWithSubFacilities(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_facility_search",
				in(map, "p_master_fac_name", String.class),
				in(map, "p_sub_fac_name", String.class),
				in(map, "p_sub_fac_dept", String.class),
				in(map, "p_county_fips", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_plus4", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_sub_fac_npi", String.class),
				rows());
		map.put("p_sub_prov_out", FacilityResultMaps.masterFacilities((List<Row>) results.get(11)));
		return new ArrayList<>();
	}

	/**
	 * The search by master facility name (PKG_STANDARDIZATION.pr_fac_search_by_master): the grouped rows are put into
	 * the map as p_facilities_out.
	 *
	 * @return an empty list (the rows come through the map)
	 */
	@SuppressWarnings("unchecked")
	public List<MasterFacilityDetailsVO> getFacilityDetailsByMasterFacility(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_fac_search_by_master",
				in(inOutParameterMap, "p_master_fac_name", String.class),
				rows());
		inOutParameterMap.put("p_facilities_out", FacilityResultMaps.masterFacilities((List<Row>) results.get(1)));
		return new ArrayList<>();
	}

	/**
	 * The search by sub facility (PKG_STANDARDIZATION.pr_fac_search_by_sub): the grouped rows are put into the map as
	 * p_facilities_out.
	 *
	 * @return an empty list (the rows come through the map)
	 */
	@SuppressWarnings("unchecked")
	public List<MasterFacilityDetailsVO> getFacilityDetailsBySubFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_fac_search_by_sub",
				in(map, "p_facname", String.class),
				in(map, "p_fac_dept", String.class),
				in(map, "p_county", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_npi", String.class),
				rows());
		map.put("p_facilities_out", FacilityResultMaps.masterFacilities((List<Row>) results.get(9)));
		return new ArrayList<>();
	}

	/**
	 * The search by raw facility (PKG_STANDARDIZATION.pr_fac_search_by_raw): the grouped rows are put into the map as
	 * p_facilities_out.
	 *
	 * @return an empty list (the rows come through the map)
	 */
	@SuppressWarnings("unchecked")
	public List<MasterFacilityDetailsVO> getFacilityDetailsByRawSubFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_fac_search_by_raw",
				in(map, "p_facname", String.class),
				in(map, "p_fac_dept", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_npi", String.class),
				rows());
		map.put("p_facilities_out", FacilityResultMaps.masterFacilities((List<Row>) results.get(8)));
		return new ArrayList<>();
	}

	/**
	 * The master and sub facilities of sub facility ids, with their raw facilities, grouped by the former result map.
	 * <p>
	 * <b>Kept as it was:</b> the query does not select PHONE (nor most sub and raw facility columns, nor
	 * RAW_FACILITY_NAME - the raw facility's FACILITY_NAME was shadowed by the sub facility's, the first column of that
	 * name), and the result map reads the phone list by name, so any found row fails, as before; no row gives an empty
	 * list. An empty or missing id list fails too (the former SQL ended with "in").
	 *
	 * @param inOutParameterMap subIdsList (a list or array of sub facility ids)
	 */
	public List<MasterFacilityDetailsVO> getSubFacListForSubIds(Map<Object, Object> inOutParameterMap) {
		List<Object> ids = items(inOutParameterMap.get("subIdsList"), "subIdsList");
		if (ids.isEmpty()) {
			throw new InvalidDataAccessApiUsageException("Invalid SQL: sub_facility_id in (no ids)");
		}
		List<String> placeholders = new ArrayList<>();
		for (int i = 1; i <= ids.size(); i++) {
			placeholders.add("?" + i);
		}
		List<Row> rows = jpql.list(SUB_FACILITIES_FOR_SUB_IDS.formatted(String.join(", ", placeholders)), (Row row) -> row, ids.toArray());
		return FacilityResultMaps.masterFacilities(rows);
	}

	/**
	 * Raw facilities for the assignment screens, filtered by the given LIKE patterns (every filter optional; none
	 * given: all raw facilities).
	 *
	 * @param inOutParameterMap p_facname, p_fac_dept, p_county, p_address, p_city, p_state, p_zipcode, p_plus4, p_phone,
	 *        p_npi (LIKE patterns, each used when not null)
	 */
	public List<RawSubFacilityVO> getAssignmentFacilityDetails(Map<Object, Object> inOutParameterMap) {
		List<String> conditions = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();
		for (String[] filter : ASSIGNMENT_FILTERS) {
			Object value = inOutParameterMap.get(filter[0]);
			if (value != null) {
				parameters.add(value);
				conditions.add("r." + filter[1] + " like ?" + parameters.size());
			}
		}
		String query = conditions.isEmpty() ? ASSIGNMENT_FACILITY_DETAILS : ASSIGNMENT_FACILITY_DETAILS + " where " + String.join(" and ", conditions);
		return jpql.list(query, Rows.bean(RawSubFacilityVO.class), parameters.toArray());
	}

	/** The items of a former foreach collection (a list, an array or a map's values); null fails as before. */
	static List<Object> items(Object collection, String name) {
		List<Object> items = new ArrayList<>();
		if (collection instanceof Iterable<?> iterable) {
			iterable.forEach(items::add);
		} else if (collection instanceof Map<?, ?> map) {
			items.addAll(map.values());
		} else if (collection != null && collection.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(collection); i++) {
				items.add(Array.get(collection, i));
			}
		} else {
			throw new InvalidDataAccessApiUsageException("The expression '" + name + "' is not a collection: " + collection);
		}
		return items;
	}
}
