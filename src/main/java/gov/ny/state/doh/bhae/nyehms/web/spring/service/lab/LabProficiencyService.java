package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * This is an interface for lab proficiency tests service.
 *
 * @author vxp06
 */
public interface LabProficiencyService {

    /**
     * This method accepts PFI as argument and returns the lab proficiency tests
     * associated with the PFI in JSON format.
     *
     * @param pfi
     * @return
     */
    public JsonObject getProficiencyTests();

    /**
     * This method takes pfi and filterno as parameters and returns relevant
     * conditions. This is used in edit lab proficiency conditions dialog.
     *
     * @param pfi
     * @param filterNo
     * @return
     */
    public List<JSONObject> getProficiencyTests(String pfi, String filterNo);

    /**
     * This method returns the lab proficiency conditions in JSON format.
     *
     * @return
     */
    public JsonObject getProficiencyConditions();

    /**
     * This method takes the JsonArray editData and queries database for edited
     * information for lab proficiency filters
     *
     * @param editData
     * @param UserId
     * @return
     */
    public String updateLabProficiencyData(JsonArray editData, String filterType, String UserId);

    /**
     * This method takes the JsonArray editData and queries database for edited
     * information for lab proficiency filters
     *
     * @param editData
     * @param UserId
     * @return
     */
    public String addLabProficiencyData(JsonArray editData, String filterType, String UserId);

    /**
     * This method will return list of all lab pfi with laboratory names
     *
     * @return
     */
    public List<Map<String, String>> getLabPfiList();

    /**
     * This method will activate or deactivate the lab proficiency filters
     * without changing its associated conditions.
     *
     * @param filterNo
     * @param pfi
     * @param flagActive
     * @param UserId
     * @return
     */
    public String updateProficiencyFilter(String filterNo, String pfi, String flagActive, String UserId);
    
    
    //public List<JSONObject> getProfieciencyFilteredata(String pfi, String filterNumber, UserObject userObject, Integer documentId);
    
    //public List<JSONObject> getProfieciencyFilteredata(String pfi, List<String> filterNumber, UserObject userObject, Integer documentId);
    
    public List<JSONObject> getProfieciencyFilteredata(String pfi, List<String> filterNumbers, UserObject userObject, Integer documentId);
    
    public List<JSONObject> getProfieciencyFilteredataWithCustomQueryCriteria(String pfi, JsonArray queryCriteria);

	public List<Map<String, String>> getFilterTypeConditions();

	public List<Map<String, String>> getPfiLabNames();
}
