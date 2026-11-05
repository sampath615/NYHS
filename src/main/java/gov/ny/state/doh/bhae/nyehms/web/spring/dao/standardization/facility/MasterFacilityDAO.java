package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import lombok.RequiredArgsConstructor;

/**
 * Master facilities (formerly a MyBatis mapper with the same name and methods): both are PKG_STANDARDIZATION
 * procedures, called through {@link NativeStatements#call}; their OUT values are put back into the caller's map
 * under the former keys.
 */
@Repository
@RequiredArgsConstructor
public class MasterFacilityDAO {

	private final NativeStatements statements;

	/**
	 * Creates a master facility for a sub facility (PKG_STANDARDIZATION.PR_LINK_SUB_NEW_MAST_FACILITY).
	 *
	 * @param map p_sub_facility_id, p_master_facility_name, p_user_id; gets p_master_facility_id_out (Long),
	 *        p_code_out (Integer), p_msg_out
	 */
	public void insertNewGrdFacility(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_LINK_SUB_NEW_MAST_FACILITY",
				in(map, "p_sub_facility_id", Long.class),
				in(map, "p_master_facility_name", String.class),
				in(map, "p_user_id", Long.class),
				out(Long.class), out(Integer.class), out(String.class));
		map.put("p_master_facility_id_out", results.get(3));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/**
	 * Creates a master facility with a sub facility (PKG_STANDARDIZATION.PR_ADD_FACILITY).
	 *
	 * @param inOutParameterMap p_master_facility_name, p_worker_id, p_facility_name, ..., p_facility_npi; gets
	 *        p_master_facility_id_out, p_sub_facility_id_out (Long), p_code_out (Integer), p_msg_out
	 */
	public void createMasterAndSubFacilities(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_ADD_FACILITY",
				in(map, "p_master_facility_name", String.class),
				in(map, "p_worker_id", Long.class),
				in(map, "p_facility_name", String.class),
				in(map, "p_facility_department", String.class),
				in(map, "p_county_fips", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_plus4", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_facility_npi", String.class),
				out(Long.class), out(Long.class), out(Integer.class), out(String.class));
		map.put("p_master_facility_id_out", results.get(12));
		map.put("p_sub_facility_id_out", results.get(13));
		map.put("p_code_out", results.get(14));
		map.put("p_msg_out", results.get(15));
	}
}
