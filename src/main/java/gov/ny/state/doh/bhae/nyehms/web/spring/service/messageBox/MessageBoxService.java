package gov.ny.state.doh.bhae.nyehms.web.spring.service.messageBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.messageBox.MessageBoxDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;

/**
 * This is implementation of the MessageBoxService interface. {@inheritDoc}
 *
 * @author vxp06
 */
@Service
public class MessageBoxService {

	private static Logger log = LoggerFactory.getLogger(MessageBoxService.class);
    

    @Autowired
    private CommonExceptionHandler commonExceptionHandler;
    @Autowired
    private MessageBoxDAO messageBoxDAO;

    /**
     * {@inheritDoc}
     *
     */
    public JSONObject getAllMessages(String userID, String messageType, BindingResult result, UserObject userObject) {

        boolean rollbackFlag = false;
        List<JSONObject> allMessageList = null;
        
        JSONObject resultObj = new JSONObject();
        
        try {
        	 
        	if("RECIEVER".equals(messageType)){
        		 allMessageList = messageBoxDAO.getAllMessages(userID.toUpperCase());
        	}else{
        		allMessageList = messageBoxDAO.getSentMessages(userID.toUpperCase());
        	}
        	
        	List<Object> listOfMessages = new ArrayList<>();
        	
        	for (JSONObject messageVO : allMessageList) {
        		
        		listOfMessages.add(messageVO.get("MESSAGEID"));
        		
            	resultObj.put(messageVO.get("MESSAGEID"), messageVO);
			}
        	
        	resultObj.put("listOfMessages", listOfMessages);
        	

        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject,userObject.getSessionId());
        } catch (Exception e) {
            rollbackFlag = true;
            log.error(e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } finally {

            if (rollbackFlag) {
            	commonExceptionHandler.inspectDBTransacionResult(-1, "Messagebox is down, unable to get the message(s)", result);
            }else{
            	commonExceptionHandler.inspectDBTransacionResult(0, "Successfully rendered the messages", result);
            }
        }

        return resultObj;
    }

    /**
     * {@inheritDoc}
     *
     */
    public void sendMessage(String userId, String receiver, String messageBody, BindingResult result, UserObject userObject) {
        boolean rollbackFlag = false;

        try {
        // create a map with all the parameters to pass to mybatis 
        	
        	
        	String[] receiversList = receiver.split(",");

            Map<Object, Object> map = new HashMap<>();

            map.put("userID", userId.toUpperCase());
            map.put("receiversList", receiversList);
            map.put("messageBody", messageBody);

            messageBoxDAO.sendMessage(map);

        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } catch (Exception e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } finally {
        	if (rollbackFlag) {
            	commonExceptionHandler.inspectDBTransacionResult(-1, "Messagebox is down, unable to send the message(s)", result);
            }else{
            	commonExceptionHandler.inspectDBTransacionResult(0, "Successfully sent the messages", result);
            }
        }

    }

    /**
     * {@inheritDoc}
     *
     */
    public int setMessageRead(String userId, String messageId, BindingResult result, UserObject userObject) {
        boolean rollbackFlag = false;
        
        int resultCount = 0;
        
        try {
            // create a map with all the parameters to pass to mybatis 
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId.toUpperCase());
            map.put("messageId", messageId);
            resultCount = messageBoxDAO.setMessageRead(map);

        } catch (DataAccessException e) {
            rollbackFlag = true;
            resultCount = 0;
            log.error(e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } catch (Exception e) {
            rollbackFlag = true;
            resultCount = 0;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        }  finally {
        	if (rollbackFlag) {
            	commonExceptionHandler.inspectDBTransacionResult(-1, "Messagebox is down, Unable to read message", result);
            }else{
            	commonExceptionHandler.inspectDBTransacionResult(0, "Successfully read the message", result);
            }
        }
        
        return 0;

    }

    /**
     * {@inheritDoc}
     *
     */
    public void deleteMessage(String userId, String messageId, BindingResult result, UserObject userObject) {
        boolean rollbackFlag = false;

        try {
            // create a map with all the parameters to pass to mybatis 
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId.toUpperCase());
            map.put("messageId", messageId);
            messageBoxDAO.deleteMessage(map);

        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } catch (Exception e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } finally {
        	if (rollbackFlag) {
            	commonExceptionHandler.inspectDBTransacionResult(-1, "Messagebox is down, Unable to delete message", result);
            }else{
            	commonExceptionHandler.inspectDBTransacionResult(0, "Successfully deleted the message", result);
            }
        }

    }

    /**
     * {@inheritDoc}
     *
     */
    public int getNumUnreadMessages(String userId, BindingResult result, UserObject userObject) {

        boolean rollbackFlag = false;
        int newMsgCount = 0;

        try {

            newMsgCount = messageBoxDAO.getNumUnreadMessages(userId.toUpperCase());

        } catch (DataAccessException e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } catch (Exception e) {
            rollbackFlag = true;
            log.error( e.getMessage());
            //commonExceptionHandler.handleException(e, userObject, userObject.getSessionId());
        } finally {
        	if (rollbackFlag) {
            	commonExceptionHandler.inspectDBTransacionResult(-1, "Messagebox is down, Unable to get the unread message(s)", result);
            }
        }

        return newMsgCount;
    }
    
}
