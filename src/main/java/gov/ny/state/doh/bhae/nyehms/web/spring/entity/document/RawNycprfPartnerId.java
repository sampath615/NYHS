package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link RawNycprfPartner} (assumed, see that class).
 */
public record RawNycprfPartnerId(String patientId, Long nycprfDocumentId) implements Serializable {
}
