package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import java.sql.Timestamp;

/** An assignment of the AHI assignment list: its STATENO and when and by whom its AHI flag was last changed. */
public record AhiAssignmentRow(String stateno, Timestamp ahiUpdateDate, String ahiUpdateUid) {
}
