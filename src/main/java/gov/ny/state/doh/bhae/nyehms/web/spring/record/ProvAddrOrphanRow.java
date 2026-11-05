package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * One row of the orphan provider address view (NYEHMS_PROV_ADDR_ORPHAN_VW: one row per address and phone), read as a
 * projection so that the rows of one address stay separate rows.
 */
public record ProvAddrOrphanRow(Long subProviderAddressId,
		Long providerId,
		String countyFips,
		String address,
		String city,
		String state,
		String zipcode,
		String plus4,
		String phone) {
}
