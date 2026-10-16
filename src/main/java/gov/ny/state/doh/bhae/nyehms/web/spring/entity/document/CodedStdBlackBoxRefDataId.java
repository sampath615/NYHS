package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link CodedStdBlackBoxRefData} (assumed, see that class).
 */
public record CodedStdBlackBoxRefDataId(String refColumn, String lookupCd) implements Serializable {
}
