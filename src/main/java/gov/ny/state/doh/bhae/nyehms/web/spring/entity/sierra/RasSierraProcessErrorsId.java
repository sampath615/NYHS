package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.sql.Timestamp;
import java.io.Serializable;

/** The key of {@link RasSierraProcessErrors} (assumed, see there). */
public record RasSierraProcessErrorsId(Long documentId, String activeFlag, Timestamp createDate) implements Serializable {
}
