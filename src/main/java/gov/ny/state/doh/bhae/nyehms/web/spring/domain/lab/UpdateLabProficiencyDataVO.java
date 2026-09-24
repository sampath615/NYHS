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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vxp06
 */
public class UpdateLabProficiencyDataVO {

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

    public String updateLabProficiencyData(JsonArray editData, String filterType, String UserId) {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.createStatement();

            for (int i = 0; i < editData.size(); i++) {

                JsonObject jsonObject = editData.get(i).getAsJsonObject();

                String FILTER_NUMBER = jsonObject.get("FILTER_NUMBER").getAsString();
                String FIELD_NAME = jsonObject.get("FIELD_NAME").getAsString();
                String CONDITION = jsonObject.get("CONDITION").getAsString();
                String VALUE = jsonObject.get("VALUE").getAsString();
                String OPERATOR = jsonObject.get("OPERATOR").getAsString();
                String CONDITION_ID = jsonObject.get("CONDITION_ID").getAsString();
                System.out.println("condition id "+CONDITION_ID);
                String query="";
               
                if (jsonObject.get("processed").getAsString().equals("N")) {
                    System.out.println("in new row query");
                    // its a newly added row
                     query = "INSERT INTO LAB_PROCESS_TEST_FILTERS  "
                            + "                (FILTER_NUMBER,FIELD_NAME,CONDITION,VALUE,OPERATOR,FLAG_ACTIVE,CREATED_BY,CREATED_DT, FILTER_TYPE) "
                            + "                VALUES "
                            + "                (" + FILTER_NUMBER + ",'" + FIELD_NAME + "','" + CONDITION + "','" + VALUE + "','" + OPERATOR + "','" +'Y'+ "','" +UserId+ "',SYSDATE,"+filterType+") ";

                } else if (jsonObject.get("processed").getAsString().equals("U")) {
                    // this is updated row.
                    System.out.println("in update row query");
                    query = "  UPDATE LAB_PROCESS_TEST_FILTERS  "
                            + "                SET FIELD_NAME = '"+FIELD_NAME+"', "
                            + "                 FILTER_TYPE ="+filterType+", "
                            + "                 CONDITION = '"+CONDITION+"', "
                            + "                 VALUE ='"+VALUE+"', "
                            + "                 OPERATOR ='"+OPERATOR+"', "
                            + "                 LAST_UPDATED_BY ="+UserId+","
                            + "                 LAST_UPDATED_DT = SYSDATE" 
                            + "                WHERE FILTER_NUMBER ="+ FILTER_NUMBER
                            + "                AND CONDITION_ID ="+CONDITION_ID;  
                    
                } else if(jsonObject.get("processed").getAsString().equals("D") ){
                    // delete the row
                    System.out.println("in delete row query");
                    if(!CONDITION_ID.isEmpty() && !CONDITION_ID.equals(null)){
                     
                        query = "  UPDATE LAB_PROCESS_TEST_FILTERS  "
                            + "                SET FLAG_ACTIVE = 'N', "
                            + "                 FILTER_TYPE ="+filterType+", "
                            + "                 LAST_UPDATED_BY ="+UserId+", "
                            + "                 LAST_UPDATED_DT = SYSDATE "
                            + "                WHERE FILTER_NUMBER ="+ FILTER_NUMBER
                            + "                AND CONDITION_ID ="+CONDITION_ID;  
                        
                    }
                            
                }else{
                	
                	query = "  UPDATE LAB_PROCESS_TEST_FILTERS  "
                            + "                SET FILTER_TYPE = '"+filterType+"', "
                            + "                 LAST_UPDATED_BY ="+UserId+", "
                            + "                 LAST_UPDATED_DT = SYSDATE "
                            + "                WHERE FILTER_NUMBER ="+ FILTER_NUMBER
                            + "                AND CONDITION_ID ="+CONDITION_ID;
                	
                }
                
                System.out.println("query "+query);
                if(!query.equals("")){
                    statement.executeUpdate(query);
                     conn.commit();
                }
                    
            }
            

          
        } catch (SQLException ex) {
            Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error "+ex.getMessage();
        } finally {
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
        return "Test Filters Successfully Updated.";
    }
}
