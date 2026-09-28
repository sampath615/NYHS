package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LabProcessDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.AddLabProficiencyDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabPFIVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabProficiencyConditionVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabProficiencyVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.UpdateLabProficiencyDataVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This is implementation of the LabProficiencyService interface. This will
 * provide the methods for Lab Proficiency functionality
 *
 * @author vxp06
 */
@Component
public class LabProficiencyService {

    @Autowired private ConnectionDAO connectionDAO;
    
    @Autowired LabProcessDAO labProcessDAO;

	/**
	 * @Inheritdoc
	 * 
	 * @return
	 */
	public JsonObject getProficiencyTests() {
		
		Connection conn = null;
		JsonObject jsonObj = new JsonObject();
		try {
			conn = connectionDAO.getConnectionObj();
			LabProficiencyVO instance = new LabProficiencyVO();
			instance.setDatabaseConnection(conn);
			JsonArray jsonArray = instance.getLabProficiencyList();
			jsonObj.add("data", jsonArray);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return jsonObj;
	}

    /**
     *  @Inheritdoc
     *
     * @return
     */
    public JsonObject getProficiencyConditions() {
        Connection conn = null;
        JsonObject jsonObj = new JsonObject();

        try {
            conn = connectionDAO.getConnectionObj();

            LabProficiencyConditionVO instance = new LabProficiencyConditionVO();
            instance.setDatabaseConnection(conn);

            JsonArray jsonArray = instance.getLabProficiencyConditionList();
            jsonObj.add("data", jsonArray);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return jsonObj;
    }

    /**
     *  @Inheritdoc
     * @param pfi
     * @param filterNo
     * @return
     */
    public List<JSONObject> getProficiencyTests(String pfi, String filterNo) {
        Connection conn = null;
        List<JSONObject> jsonArray = null;
        try {
            conn = connectionDAO.getConnectionObj();

            LabProficiencyVO instance = new LabProficiencyVO();
            instance.setDatabaseConnection(conn);

            jsonArray = instance.getLabProficiencyList(pfi, filterNo);
        
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return jsonArray;
    }

    /**
     * @Inheritdoc
     *
     * @param editData
     * @return
     */
    public String updateLabProficiencyData(JsonArray editData, String filterType, String UserId) {

        Connection conn = null;
        String res = "failure";

        try {
            conn = connectionDAO.getConnectionObj();

            UpdateLabProficiencyDataVO instance = new UpdateLabProficiencyDataVO();
            instance.setDatabaseConnection(conn);

            res = instance.updateLabProficiencyData(editData, filterType, UserId);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return res;
    }

    /**
     *  @Inheritdoc
     *
     * @return
     */
    public List<Map<String, String>> getLabPfiList() {
    	Connection conn = null;
        //JsonObject jsonObj = new JsonObject();
        List<Map<String, String>> pfiAndLabNameMap = null;
        try {
            conn = connectionDAO.getConnectionObj();

            LabPFIVO instance = new LabPFIVO();
            instance.setDatabaseConnection(conn);

            pfiAndLabNameMap = instance.getLabPfiList();
            //jsonObj.add("data", jsonArray);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pfiAndLabNameMap;

    }

    /**
     *
     * @Inheritdoc
     *
     * @param editData
     * @param userId
     * @return
     */
    public String addLabProficiencyData(JsonArray editData, String filterType, String userId) {
        Connection conn = null;
        String res = "failure";

        try {
            conn = connectionDAO.getConnectionObj();

            AddLabProficiencyDataVO instance = new AddLabProficiencyDataVO();
            instance.setDatabaseConnection(conn);

            res = instance.addLabProficiencyData(editData, filterType, userId);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return res;
    }

    /**
     *  @Inheritdoc
     *
     * @param filterNo
     * @param pfi
     * @param flagActive
     * @param UserId
     * @return
     */
    public String updateProficiencyFilter(String filterNo, String pfi, String flagActive, String UserId) {
        Connection conn = null;
        String res = "failure";

        try {
            conn = connectionDAO.getConnectionObj();

            LabProficiencyVO instance = new LabProficiencyVO();
            instance.setDatabaseConnection(conn);

            res = instance.updateLabProfFilter(filterNo, pfi, flagActive, UserId);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return res;
    }
    
    public List<JSONObject> getProfieciencyFilteredata(String pfi, List<String> filterNumbers, UserObject userObject, Integer documentId){
    	
    	Connection conn = null;
        String res = "failure";
        
        List<JSONObject> filteredData = null;

        try {
            conn = connectionDAO.getConnectionObj();

            LabProficiencyVO instance = new LabProficiencyVO();
            instance.setDatabaseConnection(conn);

            List<List<JSONObject>> filterTypeConditions = new ArrayList<>();
            
            for (String  filterNumber : filterNumbers) {
            	List<JSONObject> jsonArray = instance.getLabProficiencyList(pfi, filterNumber);
            	
            	filterTypeConditions.add(jsonArray);
			}
            
            
            if(filterTypeConditions.size() > 0){
            	
            	StringBuffer queryString =  instance.prepareQueryStringUsingProcessFilters(filterTypeConditions);
            	
            	filteredData = instance.getProfieciencyFilteredata(pfi, queryString, documentId);
            }

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return filteredData;
    	
    }
    
    public List<JSONObject> getProfieciencyFilteredataWithCustomQueryCriteria(String pfi, JsonArray queryCriteria){
    	
    	Connection conn = null;
        String res = "failure";
        
        List<JSONObject> queryCriteriaList = new ArrayList<>();
        
        List<JSONObject> filteredData = null;
        

        try {
            conn = connectionDAO.getConnectionObj();

            LabProficiencyVO instance = new LabProficiencyVO();
            instance.setDatabaseConnection(conn);
            
            Gson gson = new Gson();
            for (JsonElement jsonElement : queryCriteria) {
				
            	JSONObject jsonObject = gson.fromJson(jsonElement.toString(), JSONObject.class);
            	
            	queryCriteriaList.add(jsonObject);
            	
			}
            
            
            if(queryCriteriaList.size() > 0){
            	filteredData = instance.getProfieciencyFilteredataForOneCondition(pfi, queryCriteriaList, null);
            }

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LabProficiencyService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return filteredData;
    			
    }
    
    public List<Map<String, String>> getFilterTypeConditions(){
    	return labProcessDAO.getFilterTypeConditions();
    }
    
    public List<Map<String, String>> getPfiLabNames(){
    	return labProcessDAO.getPfiLabNames();
    }
}
