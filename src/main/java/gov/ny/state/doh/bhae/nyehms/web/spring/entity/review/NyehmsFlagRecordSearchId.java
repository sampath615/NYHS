package gov.ny.state.doh.bhae.nyehms.web.spring.entity.review;

import java.io.Serializable;
import java.sql.Timestamp;

/** The key of {@link NyehmsFlagRecordSearch} (assumed, see there). */
public record NyehmsFlagRecordSearchId(String stateno, Timestamp createDate) implements Serializable {
}
