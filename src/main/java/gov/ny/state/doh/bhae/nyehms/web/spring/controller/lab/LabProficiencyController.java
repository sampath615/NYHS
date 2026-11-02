package gov.ny.state.doh.bhae.nyehms.web.spring.controller.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.lab.LabProficiencyService;

@RequiredArgsConstructor
@Controller
public class LabProficiencyController {


    
    private final LabProficiencyService labProficiencyService;

    /**
     * This method will be called from menu bar (Lab -> Proficiency) tab This
     * returns the web page LabProficiencyTestDetails.jsp
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getLabProficiencyDetails")
    public String getLabProficiencyDetails(Map<String, Object> map, HttpServletRequest request) {
    	
    	return "reports/LabProficiencyTestDetails";
    }

    /**
     * This method will be called from the JQ Grid table to add a new condition
     * returns the web page addLabProficiencyCondition.jsp
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/addLabProficiencyCondition")
    public String addLabProficiencyTest(Map<String, Object> map, HttpServletRequest request) {
    	
    	
    	List<Map<String, String>> filterTypeConditions = labProficiencyService.getFilterTypeConditions();
    	
    	map.put("filterTypeConditions", filterTypeConditions);
    	
		return "reports/addLabProficiencyCondition";
    }

    /**
     * This method will be called from the JQ Grid table to add a new condition
     * returns the web page addLabProficiencyCondition.jsp
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/addNewLabProficiencyCondition")
    public String addNewLabProficiencyTest(Map<String, Object> map, HttpServletRequest request) {

        List<Map<String, String>> filterTypeConditions = labProficiencyService.getFilterTypeConditions();
    	
    	map.put("filterTypeConditions", filterTypeConditions);

        return "reports/addNewLabProficiencyCondition";
    }
    
    

    
    
    @RequestMapping(value="/getPfiLabNames.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Map<String, String>> getPfiLabNames(Map<String, Object> map, HttpServletRequest request) {

		
		return labProficiencyService.getLabPfiList();
        
		
	
	}

    /**
     * This will accept a parameter PFI and return all the lab proficiency tests
     * associated with that PFI
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProficiencyTests", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getProficiencyTests(Map<String, Object> map, HttpServletRequest request) {

    	JsonObject jsonObject = labProficiencyService.getProficiencyTests();
        JsonObject jsonObject2 = labProficiencyService.getProficiencyConditions();
        map.put("ProficiencyConditions", jsonObject2);
        return jsonObject.toString();

    }

    /**
     * This method will return all the conditions from the database table
     * CODED_PROCESS_CONDITION
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProficiencyConditions", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getProficiencyConditions(Map<String, Object> map, HttpServletRequest request) {

        JsonObject jsonObject = labProficiencyService.getProficiencyConditions();
        map.put("ProficiencyConditions", jsonObject);
        return jsonObject.toString();

    }

    /**
     * This method will return list of all Pfi with lab name from the database
     * table lab_physical_facility
     *
     * @param request
     * @return
     */
//    @RequestMapping(value = "/getLabPfiData", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody

    /**
     * This method accepts parameters pfi and filterId and returns the relevant
     * data with all the conditions. This is used in the edit functionality for
     * lab proficiency tests
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProficiencyPfiFilterData", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<JSONObject> getProficiencyPfiFilterData(Map<String, Object> map, HttpServletRequest request) {

        String pfi = "";
        String filterNo = "";
        if (request.getParameter("pfi") == null || request.getParameter("filterNo") == null) {
            return null;
        }
        pfi = request.getParameter("pfi");
        filterNo = request.getParameter("filterNo");

        List<JSONObject> jsonObject = labProficiencyService.getProficiencyTests(pfi, filterNo);
        map.put("ProficiencyConditions", jsonObject);
        return jsonObject;

    }

    /**
     * This controller call will be called from edit lab proficiency dialog It
     * will take editData and filter active from request and will execute the
     * functions to update it in database.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateProficiencyFilterData", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String updateProficiencyFilterData(HttpServletRequest request, @RequestParam String filterType, UserObject userObject) {

        String jsonStr = "";
        String filterActive = "";

        if (request.getParameter("editData") == null) {
            return ("editData is null");
        }
        if (request.getParameter("filterActive") == null) {
            return ("filterActive is null");
        }
        jsonStr = request.getParameter("editData");
        filterActive = request.getParameter("filterActive");
        // get the workerID

        String workerId = "";
        workerId = Integer.toString(userObject.getWorkerId());

        String result = "";
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(jsonStr);
        JsonArray editData = jsonElement.getAsJsonArray();
        JsonObject jsonObject = editData.get(0).getAsJsonObject();

        String filterNo = jsonObject.get("FILTER_NUMBER").getAsString();
        String pfi = jsonObject.get("PFI").getAsString();
        String FILTER_FLAG_ACTIVE = jsonObject.get("FILTER_FLAG_ACTIVE").getAsString();

        if (!FILTER_FLAG_ACTIVE.equals(filterActive)) {
        // if the filter active has changed then change it in DB
            //String filterNo, String pfi, String flagActive, String UserId
            result = labProficiencyService.updateProficiencyFilter(filterNo, pfi, filterActive, workerId);
        }
        // update the filter condition rows 
        result = labProficiencyService.updateLabProficiencyData(editData, filterType,  workerId);
        return result;

    }

    /**
     * This method will listen to the calls from add new proficiency filter
     * dialog and it will add the new rows to the database.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addProficiencyFilterData", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String addProficiencyFilterData(HttpServletRequest request, @RequestParam String filterType, UserObject userObject) {

        String jsonStr = "";

        if (request.getParameter("editData") == null) {
            return ("editData is null");
        }
        jsonStr = request.getParameter("editData");
        // get the workerID

        String workerId = "";
        workerId = Integer.toString(userObject.getWorkerId());

        String result = "";
        JsonParser parser = new JsonParser();

        JsonElement jsonElement = parser.parse(jsonStr);
        JsonArray editData = jsonElement.getAsJsonArray();

        result = labProficiencyService.addLabProficiencyData(editData, filterType, workerId);
        return result;

    }
    
    @RequestMapping(value = "/getProfieciencyFilteredata.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<JSONObject> getProfieciencyFilteredata(HttpServletRequest request, @RequestParam  String pfi, @RequestParam String filterNumber, UserObject userObject) {

       
       
        
//
        List<String> filterNumbers = new ArrayList<>();
        
        filterNumbers.add(filterNumber);
        
        List<JSONObject> filteredRawData = labProficiencyService.getProfieciencyFilteredata(pfi, filterNumbers, userObject, null);
        return filteredRawData;

    }
    
    @RequestMapping(value = "/getDataWithCustomProfieciencyFilter.json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<JSONObject> getDataWithCustomProfieciencyFilter(HttpServletRequest request, @RequestParam String pfi, UserObject userObject) {

    	
    	
       
        JsonParser parser = new JsonParser();
        JsonArray facilityDetails = (JsonArray)parser.parse(request.getParameter("filterCriteriaData"));
        
        
       List<JSONObject> filteredRawData = labProficiencyService.getProfieciencyFilteredataWithCustomQueryCriteria(pfi, facilityDetails);
       
        return filteredRawData;

    }
    
    
    
}
