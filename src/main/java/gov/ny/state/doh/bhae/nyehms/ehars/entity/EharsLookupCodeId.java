package gov.ny.state.doh.bhae.nyehms.ehars.entity;

import java.io.Serializable;

/**
 * Key of {@link EharsLookupCode} (assumed, see that class).
 */
public record EharsLookupCodeId(String lookupType, String lookupCd) implements Serializable {
}
