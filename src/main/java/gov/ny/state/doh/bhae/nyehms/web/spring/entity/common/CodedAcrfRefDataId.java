package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;

/**
 * Key of {@link CodedAcrfRefData} (assumed, see that class).
 */
public record CodedAcrfRefDataId(String refColumn, String lookupCd) implements Serializable {
}
