package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vxp06
 */
public class LabProficiencyConditionVO {
      private Connection conn;

    /**
     * @Inheritdoc
     * This will set the connection object.
     *
     * @param conn
     */
    
    public void setDatabaseConnection(Connection conn) {
        this.conn = conn;
    }

    
    public JsonArray getLabProficiencyConditionList() {
        
        
       if(conn == null){
            throw new IllegalArgumentException("database connection is null");
        }
        
       Statement statement = null;
        ResultSet rs = null;
        
     
        String proficiencyTestListQuery =  "SELECT CONDITION FROM  CODED_PROCESS_CONDITION WHERE ACTIVE_FLAG='Y'";
        JsonArray proficiencyConditionList = new JsonArray();
        
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(proficiencyTestListQuery);
       
            while (rs.next()) {
            	
                
                JsonObject jsonObj = new JsonObject();
               
                jsonObj.add("CONDITION",new JsonPrimitive(rs.getString("CONDITION")));
               
                
                // add the array to the object
                
                proficiencyConditionList.add(jsonObj);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
	        try {
	        	if (conn != null) {
                	conn.close();
                }
	            if (statement != null) {
	            	statement.close();
	            }
	            if (rs != null) {
	            	rs.close();
	            }
	        } catch (SQLException sqlException) {
	        	Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, sqlException);
	        }
		}
        return proficiencyConditionList;
    }    
}
