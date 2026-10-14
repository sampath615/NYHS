package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link PersonDocAssociation} (assumed, see that class).
 */
public record PersonDocAssociationId(Integer documentId, String stateno) implements Serializable {
}
