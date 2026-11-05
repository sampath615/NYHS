package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One row of the view of raw provider addresses waiting for the manual review (NYEHMS_RAW_PROVS_CLERIC_RVW_VW), read
 * as a projection so that every row stays a row of its own (whatever its key).
 */
public record RawProvsClericRvwRow(Long rawProviderAddressId,
		String countyFips,
		String address,
		String city,
		String state,
		String zipcode,
		String plus4,
		String phone,
		String providerFullname,
		String providerFname,
		String providerMname,
		String providerLname,
		String providerSuffix) {
}
