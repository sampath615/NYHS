package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link CodedQualCareRefData} (assumed, see that class).
 */
public record CodedQualCareRefDataId(String refColumn, String lookupCd) implements Serializable {
}
