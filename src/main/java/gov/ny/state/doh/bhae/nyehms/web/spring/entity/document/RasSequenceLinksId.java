package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import java.io.Serializable;

/** The key of {@link RasSequenceLinks} (assumed, see there). */
public record RasSequenceLinksId(Integer vseqid, Integer labDocId) implements Serializable {
}
