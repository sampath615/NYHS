package gov.ny.state.doh.bhae.nyehms.web.spring.service.messageBox;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;


import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;

/**
 * This is interface which defines methods to interact with MessageBox
 * 
 * @author vxp06
 */
public interface MessageBoxService {
   
    /**
     * This method will accept userId and return list of all the messages associated with it.
     * 
     * @param userID
     * @param result
     * @param userObject
     * @param sessionID
     * @return 
     */
    public JSONObject getAllMessages(String userID, String messageType, BindingResult result, UserObject userObject);
    
    /**
     * This method will accept userId and MessageVO and save it to the database table.
     * 
     * @param userId
     * @param receiver
     * @param messageBody
     * @param result
     * @param userObject
     * @param sessionID 
     */
    public void sendMessage(String userId, String  receiver, String messageBody,BindingResult result, UserObject userObject);
    
    
    
    /**
     * This method will mark the given message as read.
     * 
     * @param userId
     * @param messageId
     * @param result
     * @param userObject
     * @param sessionID 
     */
    public int setMessageRead(String userId,String messageId, BindingResult result, UserObject userObject);
    
    
    
    /**
     * This method will mark given message as deleted.
     * 
     * @param userId
     * @param messageId
     * @param result
     * @param userObject
     * @param sessionID 
     */
    public void deleteMessage(String userId,String messageId, BindingResult result, UserObject userObject);
    
    
    /**
     * This method will return number of unread messages for a given user
     * 
     * @param userId
     * @param result
     * @param userObject
     * @param sessionID 
     * @return  
     */
    public int getNumUnreadMessages(String userId, BindingResult result, UserObject userObject);
    

}
