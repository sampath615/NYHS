package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Key of {@link NyehmsLaftFollowupComments} (assumed, see that class).
 */
public record NyehmsLaftFollowupCommentsId(Long followupIdPk, Timestamp commentDate) implements Serializable {
}
