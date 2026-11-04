package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.ActiveSystemMessageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.InactiveSystemMessageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.SystemMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.SystemMessageAudit;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.SystemMessageAuditRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.SystemMessageListRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence.SystemMessageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * NYEHMS banner messages ("System Message Manage" page and the scrolling text on every page).
 * <p>
 * Which messages are active/inactive, the user names and the history are defined by the database
 * views and triggers; this service only reads them and saves the table.
 */
@Service
@Transactional(readOnly = true)
public class SystemMessageService {

    /** Same order the views use. */
    private static final Sort NEWEST_FIRST = Sort.by(Sort.Order.desc("id"), Sort.Order.desc("lastUpdatedDate"));

    private final SystemMessageRepository messages;
    private final ActiveSystemMessageRepository activeMessages;
    private final InactiveSystemMessageRepository inactiveMessages;
    private final SystemMessageAuditRepository history;

    public SystemMessageService(SystemMessageRepository messages,
                                ActiveSystemMessageRepository activeMessages,
                                InactiveSystemMessageRepository inactiveMessages,
                                SystemMessageAuditRepository history) {
        this.messages = messages;
        this.activeMessages = activeMessages;
        this.inactiveMessages = inactiveMessages;
        this.history = history;
    }

    /** Active = running now or starting later. Inactive = deleted or ended. */
    public List<SystemMessageDto> getMessages(boolean active) {
        List<? extends SystemMessageListRow> rows = active
                ? activeMessages.findAll(NEWEST_FIRST)
                : inactiveMessages.findAll(NEWEST_FIRST);
        return rows.stream().map(SystemMessageService::toDto).toList();
    }

    /** All versions of one message, newest first; empty when the message has no history rows. */
    public Optional<SystemMessageHistory> getHistory(long messageId) {
        List<SystemMessageDto> versions = history.findByMessageIdOrderByLastUpdatedDbDateDesc(messageId)
                .stream().map(SystemMessageService::toDto).toList();
        return versions.isEmpty() ? Optional.empty() : Optional.of(new SystemMessageHistory(messageId, versions));
    }

    /** Texts of the messages that are running right now (for the banner). */
    public List<String> getRunningMessageTexts() {
        return activeMessages.findStartedMessageBodies();
    }

    @Transactional
    public void create(String body, LocalDateTime start, LocalDateTime end, UserObject user) {
        messages.save(SystemMessage.create(body, start, end, user.getWorkerId()));
    }

    /**
     * Saves new text/dates; with {@code delete} also marks the message deleted (the page sends the
     * current text and dates along with a delete).
     *
     * @return false when the message does not exist (any more)
     */
    @Transactional
    public boolean update(long messageId, String body, LocalDateTime start, LocalDateTime end,
                          boolean delete, UserObject user) {
        Optional<SystemMessage> found = messages.findById(messageId);
        found.ifPresent(message -> {
            message.edit(body, start, end, user.getWorkerId());
            if (delete) {
                message.markDeleted(user.getWorkerId());
            }
        });
        return found.isPresent();
    }

    private static SystemMessageDto toDto(SystemMessageListRow row) {
        return new SystemMessageDto(row.getId(), row.getBody(), row.getCreatedByName(), row.getCreatedByShortName(),
                row.getEffectiveStartDate(), row.getEffectiveEndDate(), row.getLastUpdatedDate(),
                row.getLastUpdatedBy(), row.getLastUpdatedByName(), row.getLastUpdatedShortName(),
                row.getFlagHistoryExist(), row.getFlagDeleted());
    }

    private static SystemMessageDto toDto(SystemMessageAudit row) {
        return new SystemMessageDto(row.getMessageId(), row.getBody(), row.getCreatedByName(), row.getCreatedByShortName(),
                row.getEffectiveStartDate(), row.getEffectiveEndDate(), row.getLastUpdatedDate(),
                row.getLastUpdatedBy(), row.getLastUpdatedByName(), row.getLastUpdatedShortName(),
                null, row.getFlagDeleted());
    }
}
