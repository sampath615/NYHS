package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence.PortalModuleRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence.PortalOutageMessage;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence.PortalOutageMessageRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * Provider Portal outage notices (Administration → Portal System Message page).
 * The Provider Portal shows these to its users; this app only maintains them.
 */
@Service
@Transactional(readOnly = true)
public class PortalOutageMessageService {

    private final PortalOutageMessageRepository messages;
    private final PortalModuleRepository modules;

    public PortalOutageMessageService(PortalOutageMessageRepository messages, PortalModuleRepository modules) {
        this.messages = messages;
        this.modules = modules;
    }

    /** Active = not deleted and not ended. Inactive = deleted or ended. */
    public List<PortalOutageMessageDto> getMessages(boolean active) {
        List<PortalOutageMessage> rows = active ? messages.findActive() : messages.findInactive();
        return rows.stream().map(PortalOutageMessageService::toDto).toList();
    }

    public List<PortalModuleOption> getModules() {
        return modules.findAllByOrderByCodeAsc().stream()
                .map(module -> new PortalModuleOption(module.getCode(), module.getName()))
                .toList();
    }

    @Transactional
    public void create(String moduleCode, LocalDateTime start, LocalDateTime end, String displayMsg, UserObject user) {
        messages.save(PortalOutageMessage.create(moduleCode, start, end, displayMsg, user.getUserId()));
    }

    /**
     * Edit, or with {@code delete} only mark deleted (as before, a delete ignores the other fields).
     *
     * @return false when the message does not exist
     */
    @Transactional
    public boolean update(long msgId, String moduleCode, LocalDateTime start, LocalDateTime end, String displayMsg,
                          boolean delete, UserObject user) {
        Optional<PortalOutageMessage> found = messages.findById(msgId);
        found.ifPresent(message -> {
            if (delete) {
                message.markDeleted(user.getUserId());
            } else {
                message.edit(moduleCode, start, end, displayMsg, user.getUserId());
            }
        });
        return found.isPresent();
    }

    private static PortalOutageMessageDto toDto(PortalOutageMessage m) {
        return new PortalOutageMessageDto(m.getId(), m.getModuleColumn(),
                m.getAdmin(), m.getPrfDe(), m.getOocInq(), m.getDnltcInq(), m.getSnpId(), m.getAirsEpi(),
                m.getStartDate(), m.getEndDate(), m.getDisplayMsg(), m.getFlagDeleted(),
                m.getCreateBy(), m.getCreateDate(),
                m.getUpdateBy() == null ? " " : m.getUpdateBy(), m.getUpdateDate());
    }
}
