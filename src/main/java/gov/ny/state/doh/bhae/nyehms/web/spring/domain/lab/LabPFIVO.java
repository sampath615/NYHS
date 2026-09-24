package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vxp06
 */
public class LabPFIVO {
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

    
    public List<Map<String, String>> getLabPfiList() {
        
        
       if(conn == null){
            throw new IllegalArgumentException("database connection is null");
        }
        
       Statement statement = null;
        ResultSet rs = null;
        
     
        String labPfiListQuery =  "SELECT clia, pfi,NVL(lab_name,' ') lab_name FROM lab_physical_facility WHERE flag_active = 'Y' order by lab_name asc";
        //JsonArray labPfiList = new JsonArray();
        
        List<Map<String, String>> pfiAndLabNameMap = new ArrayList<Map<String, String>>();
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(labPfiListQuery);
            
            while (rs.next()) {
            	
            	Map<String, String> pfiMap = new HashMap<String, String>();
            	
            	String _pfi = rs.getString("pfi");
            	
            	StringBuffer _name = new StringBuffer(rs.getString("clia"));
            	
            	_name.append(" / ").append(_pfi).append("- (").append(rs.getString("lab_name")).append(")");
            	
            	
            	pfiMap.put("value", _pfi);
            	pfiMap.put("label", _name.toString());
            	
            	pfiAndLabNameMap.add(pfiMap);
//                JsonObject jsonObj = new JsonObject();
//               
//                jsonObj.add("pfi",new JsonPrimitive(rs.getString("pfi")));
//                jsonObj.add("lab_name",new JsonPrimitive(rs.getString("lab_name")));
                
                // add the array to the object
                
               // labPfiList.add(jsonObj);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
	        try {
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
        return pfiAndLabNameMap;
    }    
}
