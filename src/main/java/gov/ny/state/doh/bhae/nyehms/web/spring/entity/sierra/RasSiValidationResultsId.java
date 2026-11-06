package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.io.Serializable;

/** The key of {@link RasSiValidationResults} (assumed, see there). */
public record RasSiValidationResultsId(Integer seqResultId, String validationLevel, String message) implements Serializable {
}
