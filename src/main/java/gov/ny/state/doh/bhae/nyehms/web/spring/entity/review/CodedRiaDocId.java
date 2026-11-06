package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;

/** The key of {@link CodedRiaDoc} (assumed, see there). */
public record CodedRiaDocId(String refColumn, String lookupCd) implements Serializable {
}
