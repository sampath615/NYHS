package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

/**
 * View NYEHMS_SYSTEM_MSG_HISTORY_VW: one row per insert/update of a message, written by the
 * DB trigger TRG_AUD_NYEHMS_SYS_MSG into AUD_NYEHMS_SYSTEM_MESSAGES. Read-only.
 */
@Getter
@Entity
@Immutable
@Table(name = "NYEHMS_SYSTEM_MSG_HISTORY_VW")
public class SystemMessageAudit {

    @Id
    @Column(name = "AUD_SEQ_ID")
    private Long id;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "MSG_ID")
    private Long messageId;

    @Column(name = "MSG_BODY")
    private String body;

    @Column(name = "CREATED_BY_NAME")
    private String createdByName;

    @Column(name = "CREATED_BY_SHORT_NAME")
    private String createdByShortName;

    @Column(name = "EFFECTIVE_START_DATE")
    private LocalDateTime effectiveStartDate;

    @Column(name = "EFFECTIVE_END_DATE")
    private LocalDateTime effectiveEndDate;

    @Column(name = "FLAG_DELETED")
    private String flagDeleted;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    @Column(name = "LAST_UPDATED_BY_NAME")
    private String lastUpdatedByName;

    @Column(name = "LAST_UPDATED_SHORT_NAME")
    private String lastUpdatedShortName;

    /** When the audit row was written (the view orders by it). */
    @Column(name = "LAST_UPDATED_DB_DATE")
    private LocalDateTime lastUpdatedDbDate;

    protected SystemMessageAudit() {
    }

}
