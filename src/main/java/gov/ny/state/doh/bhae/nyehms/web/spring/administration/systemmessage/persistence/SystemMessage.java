package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * A banner message (table NYEHMS_SYSTEM_MESSAGES). Used only to create and change messages;
 * the lists are read from the views ({@link ActiveSystemMessage}, {@link InactiveSystemMessage}).
 * <p>
 * Database behaviour this entity relies on (owned by the DB, not repeated here):
 * <ul>
 *   <li>MSG_ID is filled by trigger TRG_SYS_MSG_NEW, so it is left out of the INSERT (IDENTITY strategy).</li>
 *   <li>FLAG_DELETED defaults to 'N'. Set explicitly anyway so the Java object is never half-known.</li>
 *   <li>Trigger TRG_AUD_NYEHMS_SYS_MSG writes every insert/update into AUD_NYEHMS_SYSTEM_MESSAGES
 *       (the history). So every change must go through a normal save, never a bulk update.</li>
 * </ul>
 */
@Entity
@Table(name = "NYEHMS_SYSTEM_MESSAGES")
public class SystemMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSG_ID")
    private Long id;

    @Column(name = "MSG_BODY", length = 4000)
    private String body;

    @Column(name = "CREATED_BY", updatable = false)
    private Long createdBy;

    @Column(name = "EFFECTIVE_START_DATE")
    private LocalDateTime effectiveStartDate;

    @Column(name = "EFFECTIVE_END_DATE")
    private LocalDateTime effectiveEndDate;

    @Column(name = "FLAG_DELETED", length = 1)
    private String flagDeleted;

    /** Database time on every insert and update (was SYSDATE in the old SQL). */
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    /** Worker id of the last person who saved it (NYEHMS_USERS_VW.WORKER_ID). */
    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    protected SystemMessage() {
    }

    public static SystemMessage create(String body, LocalDateTime start, LocalDateTime end, long workerId) {
        SystemMessage message = new SystemMessage();
        message.body = body;
        message.effectiveStartDate = start;
        message.effectiveEndDate = end;
        message.createdBy = workerId;
        message.lastUpdatedBy = workerId;   // the views join on it, so it must never be empty
        message.flagDeleted = "N";
        return message;
    }

    public void edit(String body, LocalDateTime start, LocalDateTime end, long workerId) {
        this.body = body;
        this.effectiveStartDate = start;
        this.effectiveEndDate = end;
        this.lastUpdatedBy = workerId;
    }

    /** Soft delete: the row stays and moves to the inactive list. */
    public void markDeleted(long workerId) {
        this.flagDeleted = "Y";
        this.lastUpdatedBy = workerId;
    }

    public Long getId() {
        return id;
    }
}
