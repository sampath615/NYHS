package gov.ny.state.doh.bhae.nyehms.web.spring.controller.messageBox;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.messageBox.MessageBoxService;

/**
 * This is controller for handling messages of MessageBox functionality
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Controller
public class MessageBoxController {


    private final MessageBoxService messageBoxService;

    /**
     * This method will return MessageBox.jsp page
     *
     * @return
     */
    @RequestMapping("/messageBox")
    public String messageBox() {

        return "messageBox/messageBox";
    }

    /**
     * This method returns all the messages for a given userId.
     *
     * @param map map of all the values
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllMessages")
    public String getAllMessages(Map<String, Object> map, HttpServletRequest request, @RequestParam String messageType, UserObject userObject) {


        Map<String, String> targetMap = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

        JSONObject messageListVO = messageBoxService.getAllMessages(userObject.getUserId(), messageType, bindingResult, userObject);
        
        
        map.put("messageType", messageType);
        map.put("stdMessageListVO", messageListVO);

        return "messageBox/messageListContent";
    }


    


    /**
     * This method will be called when a message is read and we will update the
     * status of the message to read.
     *
     * @param request
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/setMessageRead", method = RequestMethod.POST)
    public @ResponseBody
    Integer setMessageRead(HttpServletRequest request, @RequestParam String messageId, UserObject userObject) {


        Map<String, String> targetMap = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
        
        int resultCount = messageBoxService.setMessageRead(userObject.getUserId(), messageId, bindingResult, userObject);
        
        return resultCount;
    }

    /**
     * This function will send a new message to the recepient. Same function
     * will be used for Reply, Forward and Create new messages.
     *
     * @param request
     * @param receiver
     * @param messageBody
     * @return
     */
    @RequestMapping(value = "/sendNewMessage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Map<String, String> sendNewMessage(HttpServletRequest request, @RequestParam String receiver, @RequestParam String messageBody, UserObject userObject) {


        Map<String, String> targetMap = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
        
        messageBoxService.sendMessage(userObject.getUserId(), receiver, messageBody, bindingResult, userObject);

        return targetMap;
    }

    /**
     * This function will change status of the message read to N
     *
     * @param request
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Map<String, String> deleteMessage(HttpServletRequest request, @RequestParam String messageId, UserObject userObject) {


        Map<String, String> targetMap = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
        
        messageBoxService.deleteMessage(userObject.getUserId(), messageId, bindingResult, userObject);

        return targetMap;
    }

    /**
     * This method returns number of unread messages( e.g. similar to
     * notifications) for a given user.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getNumUnreadMessages", method = RequestMethod.POST, produces = "application/text")
    public @ResponseBody
    String getNumUnreadMessages(HttpServletRequest request, UserObject userObject) {


        Map<String, String> targetMap = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");
        
        int newMsgCount = messageBoxService.getNumUnreadMessages(userObject.getUserId(), bindingResult, userObject);

        String msgCnt = Integer.toString(newMsgCount);
        return msgCnt;
    }

}
