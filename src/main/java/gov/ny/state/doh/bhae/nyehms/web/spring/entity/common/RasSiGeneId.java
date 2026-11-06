package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;

/** The key of {@link RasSiGene} (assumed, see there). */
public record RasSiGeneId(Integer seqResultId, Integer geneSeqId) implements Serializable {
}
