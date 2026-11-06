package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;
import java.sql.Timestamp;

/** The key of {@link NyehmsNoncaseStatenos} (assumed, see there). */
public record NyehmsNoncaseStatenosId(String flaggedStateno, Timestamp flaggedDate) implements Serializable {
}
