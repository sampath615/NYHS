package gov.ny.state.doh.bhae.nyehms.web.spring.administration.persistence;



import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Composite key of NYEHMS_USERS_ACTIVITY (the table has no primary key).
 * Field names and types must match the @Id fields in NyehmsUserActivity.
 */

public record NyehmsUserActivityId (Long workerId, LocalDateTime activityDate, String activityBy) implements Serializable
{}
