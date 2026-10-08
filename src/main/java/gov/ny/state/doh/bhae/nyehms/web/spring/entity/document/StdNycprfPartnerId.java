package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/**
 * Key of {@link StdNycprfPartner} (assumed, see that class).
 */
public record StdNycprfPartnerId(String patientId, String nycprfDocumentId) implements Serializable {
}
