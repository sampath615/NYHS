package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import static gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility.FacilityCalls.in;
import static gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements.out;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility.SubFacilityVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.NativeStatements;
import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.Row;
import lombok.RequiredArgsConstructor;

/**
 * Sub facilities without a master facility (formerly a MyBatis mapper with the same name and methods): the list is a
 * JPQL projection of NYEHMS_SUB_FACILITY_ORPHAN_VW (one row per phone) grouped as the former result map grouped it,
 * the link is a PKG_STANDARDIZATION procedure called through {@link NativeStatements#call}.
 */
@Repository
@RequiredArgsConstructor
public class OrphanSubFacilityDAO {

	private static final String ORPHAN_SUB_FACILITIES = """
			select v.subFacilityId as SUB_FACILITY_ID, v.facilityTypeCode as FACILITY_TYPE_CODE, v.facilityName as FACILITY_NAME,
			       v.facilityDepartment as FACILITY_DEPARTMENT, v.address as ADDRESS, v.city as CITY, v.state as STATE,
			       v.zipcode as ZIPCODE, v.phone as PHONE
			from SubFacilityOrphanView v
			""";

	private final JpqlStatements jpql;
	private final NativeStatements statements;

	/** The orphan sub facilities, each with its phones (rows of one SUB_FACILITY_ID grouped, in row order). */
	public List<SubFacilityVO> getOrphanSubFacilities() {
		return FacilityResultMaps.orphanSubFacilities(jpql.list(ORPHAN_SUB_FACILITIES, (Row row) -> row));
	}

	/**
	 * Links an orphan sub facility to a master facility (PKG_STANDARDIZATION.PR_LINK_SUB_MASTER_FACILITY).
	 *
	 * @param inOutParameterMap subFacilityId, masterFacilityId; gets statusCode (Integer), statusMessage
	 */
	public void linkOrphanSubFacilityToMasterFacility(Map<Object, Object> inOutParameterMap) {
		Map<Object, Object> map = inOutParameterMap;
		List<Object> results = statements.call("PKG_STANDARDIZATION.PR_LINK_SUB_MASTER_FACILITY",
				in(map, "subFacilityId", Long.class),
				in(map, "masterFacilityId", Long.class),
				out(Integer.class), out(String.class));
		map.put("statusCode", results.get(2));
		map.put("statusMessage", results.get(3));
	}
}
