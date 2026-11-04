package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.service.PortalOutageMessageDto;
import gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.service.PortalOutageMessageService;
import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.ActionResult;
import gov.ny.state.doh.bhae.nyehms.web.spring.platform.web.GridResponse;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

/**
 * Administration → Portal System Message page (Provider Portal outage notices).
 * URLs and JSON unchanged; the JSP drop-down now reads code()/name() of {@code PortalModuleOption}.
 */
@Controller
public class PortalOutageMessageController {

    private final PortalOutageMessageService portalOutageMessageService;

    public PortalOutageMessageController(PortalOutageMessageService portalOutageMessageService) {
        this.portalOutageMessageService = portalOutageMessageService;
    }

    @RequestMapping("/getPortalSystemMessagePage")
    public String getPortalSystemMessagePage(Map<String, Object> model) {
        model.put("moduleNames", portalOutageMessageService.getModules());
        return "administration/PortalSystemMessage";
    }

    /** @param statusFlag "Active" = not deleted and not ended; anything else = deleted or ended. */
    @PostMapping(value = "/getPortalSystemMessages.json", produces = "application/json")
    @ResponseBody
    public GridResponse<PortalOutageMessageDto> getPortalSystemMessages(@RequestParam String statusFlag) {
        return GridResponse.of(portalOutageMessageService.getMessages("Active".equals(statusFlag)));
    }

    @PostMapping(value = "/savePortalSystemMessage.json", produces = "application/json")
    @ResponseBody
    public ActionResult savePortalSystemMessage(@ModelAttribute PortalOutageMessageForm form, UserObject userObject) {
        portalOutageMessageService.create(form.module_name(), form.start(), form.end(), form.display_msg(), userObject);
        return ActionResult.success("Successfully created the message");
    }

    @PostMapping(value = "/updatePortalSystemMessage.json", produces = "application/json")
    @ResponseBody
    public ActionResult updatePortalSystemMessage(@ModelAttribute PortalOutageMessageForm form, UserObject userObject) {
        boolean delete = form.isDelete();
        boolean updated = form.msgId() != null
                && portalOutageMessageService.update(form.msgId(), form.module_name(),
                        delete ? null : form.start(), delete ? null : form.end(),
                        form.display_msg(), delete, userObject);
        return ActionResult.success(updated ? "Successfully updated the message" : "Unable to updated the message");
    }
}
