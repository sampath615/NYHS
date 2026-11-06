package gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra;

import java.io.Serializable;

/** The key of {@link RasSiDrugPartialScore} (assumed, see there). */
public record RasSiDrugPartialScoreId(Integer seqResultId, Integer drugId, String mutation) implements Serializable {
}
