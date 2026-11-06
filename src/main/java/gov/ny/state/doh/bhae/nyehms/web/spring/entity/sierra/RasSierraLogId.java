package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.io.Serializable;

/** The key of {@link RasSierraLog} (assumed, see there). */
public record RasSierraLogId(Integer documentIdMin, Integer documentIdMax) implements Serializable {
}
