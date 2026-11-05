package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.math.BigDecimal;

/**
 * A provider of the provider portal reports' provider list.
 *
 * @param portalProvId PORTAL_PROV_ID
 * @param providerName first, middle and last name separated by spaces (a missing name part as an empty text)
 */
public record PortalProviderNameRow(BigDecimal portalProvId, String providerName) {
}
