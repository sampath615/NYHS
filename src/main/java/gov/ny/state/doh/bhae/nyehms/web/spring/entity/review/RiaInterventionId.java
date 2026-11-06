package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;

/** The key of {@link RiaIntervention} (assumed, see there). */
public record RiaInterventionId(Long riaFollowUpId, String interventionCd) implements Serializable {
}
