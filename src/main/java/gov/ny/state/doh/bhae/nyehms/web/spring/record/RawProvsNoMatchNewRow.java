package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One row of the view of raw provider addresses not yet standardized, new screen (NYEHMS_RAW_PROVS_NO_MATCH_NEW_VW),
 * read as a projection so that every row stays a row of its own (whatever its key).
 */
public record RawProvsNoMatchNewRow(Long rawProviderAddressId,
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
		String providerLname) {
}
