package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * A facility of the death records (CODED_DEATH_PFI), as the death imports copy it into the FACILITY_* columns;
 * replaces the former map with FACILITY_TYPE, FACILITY_NAME, ...
 */
public record DeathFacility(String facilityType, String facilityName, String facilityAddress, String facilityCity,
		String facilityState, String facilityZip, String facilityPlus4, String facilityPhone, String facilityCounty) {

	/** Whether no facility column has a value (MyBatis returned no map for such a row). */
	public boolean isEmpty() {
		return facilityType == null && facilityName == null && facilityAddress == null && facilityCity == null && facilityState == null
				&& facilityZip == null && facilityPlus4 == null && facilityPhone == null && facilityCounty == null;
	}
}
