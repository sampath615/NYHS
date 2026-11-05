package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.rows;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.MasterFacilityDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import lombok.RequiredArgsConstructor;

/**
 * The manual review of raw facilities (formerly a MyBatis mapper with the same name and methods): all three are
 * PKG_STANDARDIZATION procedures, called through {@link NativeStatements#call}; their OUT values and cursor rows are
 * put back into the caller's map under the former keys.
 */
@Repository
@RequiredArgsConstructor
public class FacilityManualReviewDAO {

	private final NativeStatements statements;

	/**
	 * Matches a raw facility with another one (PKG_STANDARDIZATION.PR_MATCH_FACILITY).
	 *
	 * @param inOutParameterMap p_raw_facility_id, p_match_raw_facility_id, p_worker_id; gets statusCode (Integer),
	 *        statusMessage
	 */
	public void matchRawFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_MATCH_FACILITY",
				in(map, "p_raw_facility_id", Long.class),
				in(map, "p_match_raw_facility_id", Long.class),
				in(map, "p_worker_id", Long.class),
				out(Integer.class), out(String.class));
		map.put("statusCode", results.get(3));
		map.put("statusMessage", results.get(4));
	}

	/**
	 * Undoes the match of a raw facility (PKG_STANDARDIZATION.PR_UNMATCH_FACILITY).
	 *
	 * @param inOutParameterMap p_raw_facility_id, p_worker_id; gets statusCode (Integer), statusMessage
	 */
	public void unMatchRawFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_UNMATCH_FACILITY",
				in(map, "p_raw_facility_id", Long.class),
				in(map, "p_worker_id", Long.class),
				out(Integer.class), out(String.class));
		map.put("statusCode", results.get(2));
		map.put("statusMessage", results.get(3));
	}

	/**
	 * The facilities a raw facility could be matched with (PKG_STANDARDIZATION.pr_fac_manual_review_list): the cursor
	 * rows, grouped into master facilities with their sub and raw facilities (the former result map
	 * masterFacilitiesWithSubFacilities of MasterFacilityDetailsDAO), are put into the map as p_facilities_out.
	 *
	 * @param inOutParameterMap p_raw_facility_id
	 * @return an empty list (the rows come through the map)
	 */
	@SuppressWarnings("unchecked")
	public List<MasterFacilityDetailsVO> getFacilityDetailsForManualReview(Map<Object, Object> inOutParameterMap) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_fac_manual_review_list",
				in(inOutParameterMap, "p_raw_facility_id", Long.class),
				rows());
		inOutParameterMap.put("p_facilities_out", FacilityResultMaps.masterFacilities((List<Row>) results.get(1)));
		return new ArrayList<>();
	}
}
