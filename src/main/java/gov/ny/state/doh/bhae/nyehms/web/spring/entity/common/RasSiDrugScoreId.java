package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;

/** The key of {@link RasSiDrugScore} (assumed, see there). */
public record RasSiDrugScoreId(Integer seqResultId, Integer geneSeqId, Integer drugId) implements Serializable {
}
