package gov.ny.state.doh.bhae.nyehms.web.spring.dao.messageBox;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;

/**
 * This is DAO for messageBox functionality.
 *
 * @author vxp06
 */
public interface MessageBoxDAO {

    /**
     * This function will accept a parameter documentId and will query
     *
     * @param userID
     * @return
     */
    public List<JSONObject> getAllMessages(String userID)  throws DataAccessException;

     /**
     * This function will accept a parameter documentId and will query
     *
     * @param userID
     * @return
     */
    public List<JSONObject> getSentMessages(String userID)  throws DataAccessException;
    
    /**
     * This function will accept a parameter userID and will query table and 
     * return total number of unread messages for the given user id
     *
     * @param userID
     * @return
     */
    public int getNumUnreadMessages(String userID)  throws DataAccessException; 
    
    
    /**
     * This function will accept a parameters userID and messageId 
     * set the status of IS_READ to Y
     *
     * @param map
     * @return 
     */
    public int setMessageRead(Map<String,String> map)  throws DataAccessException;
    
     /**
     * This function will accept a parameters userID and messageId 
     * set the status of IS_READ to Y
     *
     * @param map
     * @return 
     */
    public int deleteMessage(Map<String,String> map) throws DataAccessException;
        
     /**
     * This function will insert contents for a new message into table.
     *
     * @param map
     * @return 
     */
    public int sendMessage(Map<Object,Object> map) throws DataAccessException;       
    
    
     
    
}
