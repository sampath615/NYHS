package gov.ny.state.doh.bhae.nyehms.web.spring.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

/** The key of {@link DeletedDocHistory} (assumed, see there). */
public record DeletedDocHistoryId(Integer triggeringDocId, Timestamp createDate) implements Serializable {
}
