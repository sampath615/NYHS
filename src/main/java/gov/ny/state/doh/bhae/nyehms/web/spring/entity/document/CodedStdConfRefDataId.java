package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link CodedStdConfRefData} (assumed, see that class).
 */
public record CodedStdConfRefDataId(String refColumn, String lookupCd) implements Serializable {
}
