package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.io.Serializable;

/**
 * Key of {@link LaftLabSurveyResponse} (assumed, see that class).
 */
public record LaftLabSurveyResponseId(String laftReconId, String pfi) implements Serializable {
}
