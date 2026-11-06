package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;

/** The key of {@link NyehmsRecSearchReturn} (assumed, see there). */
public record NyehmsRecSearchReturnId(String sentFileName, Long sentFileSeq, String stateCd) implements Serializable {
}
