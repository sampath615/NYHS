package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;

/**
 * A partner listed on an NYC PRF document, as the NYC PRF document screen shows it
 * ({@code partnerList[i].stdNycprfPartner.lastName} etc.).
 *
 * @param stdNycprfPartner the standardized partner (STD_NYCPRF_PARTNER)
 * @param rawAddress       the address as received (RAW_NYCPRF_PARTNER.PRTADDRESS). A value, not the
 *                         RawNycprfPartner entity: the same partner can be received more than once with
 *                         different addresses, and those rows share RawNycprfPartner's (assumed) key, so
 *                         loading them as entities would return the first address for every row.
 */
public record NycPrfPartnerData(StdNycprfPartner stdNycprfPartner, String rawAddress) {
}
