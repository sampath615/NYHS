package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import lombok.RequiredArgsConstructor;

/**
 * Merging and unlinking facilities (formerly a MyBatis mapper with the same name and methods): all four are
 * PKG_STANDARDIZATION procedures taking comma-separated id lists, called through {@link NativeStatements#call}; their
 * p_code_out (Integer) and p_msg_out are put back into the caller's map.
 */
@Repository
@RequiredArgsConstructor
public class MergeAndUnlinkDAO {

	private final NativeStatements statements;

	/** Unlinks raw facilities from their sub facility (pr_unlink_raw_facilities): p_fac_id, p_parent_id, p_user_id. */
	public void unlinkRawFacilities(Map<Object, Object> map) {
		unlink("PKG_STANDARDIZATION.pr_unlink_raw_facilities", map);
	}

	/**
	 * Unlinks sub facilities from their master facility (pr_unlink_sub_facilities): p_fac_id, p_parent_id, p_user_id.
	 * The procedure name keeps the former space after the package name (Oracle accepts it).
	 */
	public void unlinkSubFacilities(Map<Object, Object> map) {
		unlink("PKG_STANDARDIZATION. pr_unlink_sub_facilities", map);
	}

	/**
	 * Merges sub facilities (pr_merge_sub_facilities): p_sub_facility_list, p_retained_sub_facility,
	 * p_master_facility, p_user_id.
	 */
	public void mergeSubFacilities(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_merge_sub_facilities",
				in(map, "p_sub_facility_list", String.class),
				in(map, "p_retained_sub_facility", Long.class),
				in(map, "p_master_facility", Long.class),
				in(map, "p_user_id", Long.class),
				out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(4));
		map.put("p_msg_out", results.get(5));
	}

	/** Merges master facilities (pr_merge_master_facilities): p_master_facility_list, p_retained_facility, p_user_id. */
	public void mergeGrandFacilities(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_merge_master_facilities",
				in(map, "p_master_facility_list", String.class),
				in(map, "p_retained_facility", Long.class),
				in(map, "p_user_id", Long.class),
				out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}

	private void unlink(String procedure, Map<Object, Object> map) {
		List<Object> results = statements.call(procedure,
				in(map, "p_fac_id", String.class),
				in(map, "p_parent_id", Long.class),
				in(map, "p_user_id", Long.class),
				out(Integer.class), out(String.class));
		map.put("p_code_out", results.get(3));
		map.put("p_msg_out", results.get(4));
	}
}
