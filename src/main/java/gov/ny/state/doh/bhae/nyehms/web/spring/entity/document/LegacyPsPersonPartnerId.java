package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link LegacyPsPersonPartner} (assumed, see that class).
 */
public record LegacyPsPersonPartnerId(Integer partnrid, Integer assignmentId) implements Serializable {
}
