package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;

/** The key of {@link FollowUpInterventionMap} (assumed, see there). */
public record FollowUpInterventionMapId(String followUpCd, String interventionCd) implements Serializable {
}
