package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * Columns shared by the read-only views NYEHMS_ACTIVE_SYSTEM_MSG_VW and NYEHMS_INACTIVE_SYSTEM_MSG_VW.
 * The views decide which messages are active/inactive and add the user names and the history flag.
 */
@Getter
@MappedSuperclass
public abstract class SystemMessageListRow {

    @Id
    @Column(name = "MSG_ID")
    private Long id;

    @Column(name = "MSG_BODY")
    private String body;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_BY_NAME")
    private String createdByName;

    @Column(name = "CREATED_BY_SHORT_NAME")
    private String createdByShortName;

    @Column(name = "EFFECTIVE_START_DATE")
    private LocalDateTime effectiveStartDate;

    @Column(name = "EFFECTIVE_END_DATE")
    private LocalDateTime effectiveEndDate;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    @Column(name = "LAST_UPDATED_BY_NAME")
    private String lastUpdatedByName;

    @Column(name = "LAST_UPDATED_SHORT_NAME")
    private String lastUpdatedShortName;

    /** 'Y' when the message has been changed after it was created (more than one history row). */
    @Column(name = "FLAG_HISTORY_EXIST")
    private String flagHistoryExist;

    protected SystemMessageListRow() {
    }

    /** Only the inactive view has FLAG_DELETED. */
    public abstract String getFlagDeleted();

}
