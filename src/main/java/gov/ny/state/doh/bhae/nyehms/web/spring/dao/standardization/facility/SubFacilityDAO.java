package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.cursor;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.longValue;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Values.text;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityForRawFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.SubFacility;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.RawFacilityRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.SubFacilityRepository;
import lombok.RequiredArgsConstructor;

/**
 * Standardized (sub) facilities (formerly a MyBatis mapper with the same name and methods): the PKG_STANDARDIZATION
 * procedures are called through {@link NativeStatements#call} (their OUT values and cursor rows put back into the
 * caller's map under the former keys), the reads and writes go through the SUB_FACILITY / RAW_FACILITY entities.
 * <p>
 * The dates the former SQL set with SYSDATE are the application server's clock.
 */
@Repository
@RequiredArgsConstructor
public class SubFacilityDAO {

	private final NativeStatements statements;
	private final SubFacilityRepository subFacilities;
	private final RawFacilityRepository rawFacilities;

	/**
	 * Creates a sub facility and links the raw facility to it (PKG_STANDARDIZATION.PR_LINK_RAW_NEW_SUB_FACILITY).
	 *
	 * @param map p_raw_sub_facility_id, p_facility_name, ..., p_facility_npi; gets p_sub_facility_id_out (Long),
	 *        p_code_out (Integer), p_msg_out
	 */
	public void insertNewStdFacility(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_LINK_RAW_NEW_SUB_FACILITY",
				in(map, "p_raw_sub_facility_id", Long.class),
				in(map, "p_facility_name", String.class),
				in(map, "p_facility_department", String.class),
				in(map, "p_county_fips", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_plus4", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_user_id", Long.class),
				in(map, "p_master_facility_id", Long.class),
				in(map, "p_facility_npi", String.class),
				out(Long.class), out(Integer.class), out(String.class));
		map.put("p_sub_facility_id_out", results.get(13));
		map.put("p_code_out", results.get(14));
		map.put("p_msg_out", results.get(15));
	}

	/**
	 * The sub facilities of raw facilities (PKG_STANDARDIZATION.PR_SUB_FACILITIES_BY_RAW): the cursor rows
	 * (SubFacilityForRawFacilityVO) are put into the map as subFacForRawFacRefCursor.
	 *
	 * @param map rawIds (comma-separated)
	 * @return an empty list (the rows come through the map)
	 */
	public List<SubFacilityForRawFacilityVO> getStdIdsForRawIds(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_SUB_FACILITIES_BY_RAW",
				in(map, "rawIds", String.class),
				cursor(FacilityResultMaps::subFacilityForRawFacility));
		map.put("subFacForRawFacRefCursor", results.get(1));
		return new ArrayList<>();
	}

	/**
	 * Not used through this class: the former statement lived in MasterFacilityDetailsDAO's mapper (this interface
	 * method had no statement, so calling it failed). Kept for the same public methods.
	 *
	 * @throws org.springframework.dao.InvalidDataAccessApiUsageException always, as the former mapper did
	 */
	public List<SubFacilityVO> getSubFacListForSubIds(Map<Object, Object> map) {
		throw new org.springframework.dao.InvalidDataAccessApiUsageException(
				"Invalid bound statement (not found): " + SubFacilityDAO.class.getName() + ".getSubFacListForSubIds");
	}

	/**
	 * Adds a sub facility to a master facility (PKG_STANDARDIZATION.pr_add_sub_facility).
	 *
	 * @param map p_master_facility_id, p_facility_name, ..., p_facility_npi; gets p_sub_facility_id_out (Long),
	 *        p_code_out (Integer), p_msg_out
	 */
	public void addNewStdFacility(Map<Object, Object> map) {
		List<Object> results = statements.call("PKG_STANDARDIZATION.pr_add_sub_facility",
				in(map, "p_master_facility_id", Long.class),
				in(map, "p_facility_name", String.class),
				in(map, "p_facility_department", String.class),
				in(map, "p_county_fips", String.class),
				in(map, "p_address", String.class),
				in(map, "p_city", String.class),
				in(map, "p_state", String.class),
				in(map, "p_zipcode", String.class),
				in(map, "p_plus4", String.class),
				in(map, "p_phone", String.class),
				in(map, "p_user_id", Long.class),
				in(map, "p_facility_npi", String.class),
				out(Long.class), out(Integer.class), out(String.class));
		map.put("p_sub_facility_id_out", results.get(12));
		map.put("p_code_out", results.get(13));
		map.put("p_msg_out", results.get(14));
	}

	/**
	 * The id of a sub facility with this eHARS facility UID (the first one found, as the former rownum &lt; 2 took
	 * it), as text; null if there is none.
	 */
	public String getSubFacilityId(String facilityUid) {
		if (facilityUid == null) {
			return null;                                        // EHARS_FAC_UID = NULL matched nothing
		}
		return subFacilities.findFirstByEharsFacUid(facilityUid).map(s -> String.valueOf(s.getSubFacilityId())).orElse(null);
	}

	/** 1 if a raw facility is linked to the sub facility, else 0 (the former count with rownum &lt; 2). */
	public int checkRawFacilitiesForStandardFacilities(int subFacilityId) {
		return rawFacilities.existsBySubFacilityId((long) subFacilityId) ? 1 : 0;
	}

	/**
	 * Creates a raw facility as a copy of a sub facility (the former insert ... select from SUB_FACILITY): the sub
	 * facility's columns, a new id from RAW_FACILITY_SEQ, CREATE_UID the worker id, the given phone.
	 *
	 * @param map standardFacilityId, workerId, phone
	 * @return 1, or 0 if there is no such sub facility
	 */
	@Transactional
	public int createRawFacilitiyForStandardFacility(Map<Object, Object> map) {
		Long subFacilityId = longValue(map.get("standardFacilityId"));
		Optional<SubFacility> found = subFacilityId == null ? Optional.empty() : subFacilities.findById(subFacilityId);
		if (found.isEmpty()) {
			return 0;
		}
		SubFacility sub = found.get();
		rawFacilities.saveAndFlush(RawFacility.builder()
				.subFacilityId(sub.getSubFacilityId())
				.facilityTypeCode(sub.getFacilityTypeCode())
				.facilityName(sub.getFacilityName())
				.facilityDepartment(sub.getFacilityDepartment())
				.legacyFacilityId(sub.getLegacyFacilityId())
				.legacyFacilityType(sub.getLegacyFacilityType())
				.address(sub.getAddress())
				.city(sub.getCity())
				.state(sub.getState())
				.zipcode(sub.getZipcode())
				.plus4(sub.getPlus4())
				.createUid(text(map.get("workerId")))
				.dateCreated(now())
				.phone(text(map.get("phone")))
				.countyFips(sub.getCountyFips())
				.npi(sub.getNpi())
				.build());
		return 1;
	}

	/**
	 * Marks a raw facility as one that cannot be standardized (SUB_FACILITY_ID 0).
	 *
	 * @param map recordId, userId
	 * @return 1, or 0 if there is no such raw facility
	 */
	@Transactional
	public int unableStandardizeRawFacilityRecord(Map<Object, Object> map) {
		Long id = longValue(map.get("recordId"));
		Optional<RawFacility> raw = id == null ? Optional.empty() : rawFacilities.findById(id);
		raw.ifPresent(r -> r.unableToStandardize(text(map.get("userId")), now()));
		return raw.isPresent() ? 1 : 0;
	}

	/**
	 * Marks a sub facility as one that cannot be standardized (MASTER_FACILITY_ID 0).
	 *
	 * @param map recordId, userId
	 * @return 1, or 0 if there is no such sub facility
	 */
	@Transactional
	public int unableStandardizeSubFacilityRecord(Map<Object, Object> map) {
		Long id = longValue(map.get("recordId"));
		Optional<SubFacility> sub = id == null ? Optional.empty() : subFacilities.findById(id);
		sub.ifPresent(s -> s.unableToStandardize(text(map.get("userId")), now()));
		return sub.isPresent() ? 1 : 0;
	}

	private static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
}
