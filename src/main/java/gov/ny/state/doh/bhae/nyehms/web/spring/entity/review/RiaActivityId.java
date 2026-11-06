package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;
import java.sql.Timestamp;

/** The key of {@link RiaActivity} (assumed, see there). */
public record RiaActivityId(Long documentId, String activityDesc, Long activityBy, Timestamp activityDate) implements Serializable {
}
