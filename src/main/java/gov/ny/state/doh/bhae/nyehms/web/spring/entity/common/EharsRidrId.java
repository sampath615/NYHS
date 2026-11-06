package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;

/** The key of {@link EharsRidr} (assumed, see there). */
public record EharsRidrId(String ridrStateCd, String ridrStateno, String eharsStateno) implements Serializable {
}
