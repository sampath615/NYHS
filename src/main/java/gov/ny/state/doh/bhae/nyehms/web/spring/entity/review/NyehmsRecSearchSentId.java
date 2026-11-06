package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;

/** The key of {@link NyehmsRecSearchSent} (assumed, see there). */
public record NyehmsRecSearchSentId(String sentFileName, Long sentFileSeq) implements Serializable {
}
