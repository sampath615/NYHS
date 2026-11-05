package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One row of the view of raw provider addresses not yet standardized (NYEHMS_RAW_PROVS_NO_MATCH_VW), read as a
 * projection so that every row stays a row of its own (whatever its key).
 */
public record RawProvsNoMatchRow(Long rawProviderAddressId,
		String countyFips,
		String address,
		String city,
		String state,
		String zipcode,
		String plus4,
		String npi,
		String phone,
		String providerFullname,
		String providerFname,
		String providerMname,
		String providerLname,
		String providerSuffix) {
}
