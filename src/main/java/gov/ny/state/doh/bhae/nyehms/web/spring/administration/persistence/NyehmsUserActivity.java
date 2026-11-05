package gov.ny.state.doh.bhae.nyehms.web.spring.administration.persistence;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * One admin comment about a user (table NYEHMS_USERS_ACTIVITY).
 * Rows are only inserted and read, never updated or deleted.
 * No primary key in the table, so worker + date + author act as the key.
 */


@Entity
@Table(name="NYEHMS_USER_ACTIVITY")
@IdClass(NyehmsUserActivityId.class)
@Getter
public class NyehmsUserActivity {

    @Id
    @Column(name = "WORKER_ID")
    private Long workerId;

    @Id
    @Column(name = "ACTIVITY_DATE")
    private LocalDateTime activityDate;

    @Id
    @Column(name = "ACTIVITY_BY", length = 10)
    private String activityBy;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    protected NyehmsUserActivity() {
        // for JPA
    }

    public static NyehmsUserActivity create(Long workerId, String activityBy, String comments) {
        NyehmsUserActivity activity = new NyehmsUserActivity();
        activity.workerId = workerId;
        // Oracle DATE keeps whole seconds; truncate so the key in memory matches the row
        activity.activityDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        activity.activityBy = activityBy;
        activity.comments = comments;
        return activity;
    }
}
