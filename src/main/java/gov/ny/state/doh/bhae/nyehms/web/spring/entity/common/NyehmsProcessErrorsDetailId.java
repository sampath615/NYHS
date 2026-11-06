package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;

/**
 * Key of {@link NyehmsProcessErrorsDetail} (assumed, see that class).
 */
public record NyehmsProcessErrorsDetailId(Long errorId, Long errorCode) implements Serializable {
}
