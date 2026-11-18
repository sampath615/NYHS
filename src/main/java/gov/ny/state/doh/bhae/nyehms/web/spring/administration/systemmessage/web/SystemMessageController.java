package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.web;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service.SystemMessageDto;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service.SystemMessageHistory;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.service.SystemMessageService;
import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.ActionResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.GridResponse;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * NYEHMS banner messages: the "System Message Manage" page and the banner text on every page.
 * URLs and JSON are unchanged, so systemMessageManage.js and onLoadEvents.js need no changes.
 * (Provider Portal outage messages are still in the old administration SystemMessageController.)
 */
@Controller
public class SystemMessageController {

    /** Separator between banner texts; the page cuts the trailing one off with slice(0, -5). */
    private static final String BANNER_SEPARATOR = "    |    ";

    private final SystemMessageService systemMessageService;

    public SystemMessageController(SystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @RequestMapping("/getSystemMessageManagingPage")
    public String getSystemMessageManagingPage() {
        return "administration/SystemMessageManage";
    }

    /** @param statusFlag "Active" = running or future messages; anything else = deleted or ended. */
    @PostMapping(value = "/getSystemMessages.json", produces = "application/json")
    @ResponseBody
    public GridResponse<SystemMessageDto> getSystemMessages(@RequestParam String statusFlag) {
        return GridResponse.of(systemMessageService.getMessages("Active".equals(statusFlag)));
    }

    @PostMapping(value = "/getSystemMessageArchives.json", produces = "application/json")
    @ResponseBody
    public GridResponse<SystemMessageHistory> getSystemMessageArchives(@RequestParam long messageId) {
        Optional<SystemMessageHistory> history = systemMessageService.getHistory(messageId);
        return GridResponse.of(history.map(List::of).orElse(List.of()));
    }

    @PostMapping(value = "/saveSystemMessage.json", produces = "application/json")
    @ResponseBody
    public ActionResult saveSystemMessage(@ModelAttribute SystemMessageForm form, UserObject userObject) {
        systemMessageService.create(form.messageBody(), form.start(), form.end(), userObject);
        return ActionResult.success("Successfully created the message");
    }

    @PostMapping(value = "/updateSystemMessage.json", produces = "application/json")
    @ResponseBody
    public ActionResult updateSystemMessage(@ModelAttribute SystemMessageForm form, UserObject userObject) {
        boolean updated = form.messageId() != null
                && systemMessageService.update(form.messageId(), form.messageBody(), form.start(), form.end(),
                        form.isDelete(), userObject);
        // old wording kept, including the result code (the page only shows the text)
        return ActionResult.success(updated ? "Successfully updated the message" : "Unable to updated the message");
    }

    /**
     * Banner text for every page (polled every 5 minutes by onLoadEvents.js): the user's
     * confidentiality/security training reminder (set at login) followed by the running messages.
     */
    @PostMapping(value = "/getInProgressProcessingSystemMessages.json", produces = "application/json")
    @ResponseBody
    public ActionResult getInProgressProcessingSystemMessages(HttpSession session) {
        StringBuilder banner = new StringBuilder();
        if (session.getAttribute("confidentialityAndSecurityTrainingDateStatusMessage") instanceof String training
                && !training.isEmpty()) {
            banner.append(training).append(BANNER_SEPARATOR);
        }
        for (String text : systemMessageService.getRunningMessageTexts()) {
            banner.append(text).append(BANNER_SEPARATOR);
        }
        return ActionResult.success(banner.toString());
    }
}
