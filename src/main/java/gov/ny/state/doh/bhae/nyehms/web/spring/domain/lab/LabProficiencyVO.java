package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;

/**
 * This class provides methods for displaying data in lab proficiency JQGrid and
 * editing it.
 *
 * @author vxp06
 */
public class LabProficiencyVO {

	private static Logger log = LoggerFactory.getLogger(LabProficiencyVO.class);
	
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
    
    public JsonArray getLabProficiencyList() {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;

        String proficiencyTestListQuery = " SELECT "
                + "              CLIA,PFI,LAB_NAME,FILTER_NUMBER,CONDITION_ID,FIELD_NAME, FILTER_TYPE, CONDITION,VALUE,NVL(OPERATOR,' ') OPERATOR, FILTER_FLAG_ACTIVE FROM "
                + "                 NYEHMS_PROCESS_FILTER_VW  "
                + "                 ORDER BY PFI,FILTER_NUMBER,operator DESC";

        JsonArray proficiencyTestList = new JsonArray();

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(proficiencyTestListQuery);

            while (rs.next()) {

                JsonObject jsonObj = new JsonObject();
                String value="";
                if(rs.getString("VALUE")==null){
                    value=" ";
                }   else {
                    value=rs.getString("VALUE");
                } 
                jsonObj.add("CLIA", new JsonPrimitive(rs.getString("CLIA")));
                jsonObj.add("PFI", new JsonPrimitive(rs.getString("PFI")));
                jsonObj.add("LAB_NAME", new JsonPrimitive(rs.getString("LAB_NAME")));
                jsonObj.add("FILTER_NUMBER", new JsonPrimitive(rs.getInt("FILTER_NUMBER")));
                jsonObj.add("CONDITION_ID", new JsonPrimitive(rs.getInt("CONDITION_ID")));
                jsonObj.add("FIELD_NAME", new JsonPrimitive(rs.getString("FIELD_NAME")));
                jsonObj.add("CONDITION", new JsonPrimitive(rs.getString("CONDITION")));
                jsonObj.add("VALUE", new JsonPrimitive(value));
                jsonObj.add("OPERATOR", new JsonPrimitive(rs.getString("OPERATOR")));
                jsonObj.add("FILTER_FLAG_ACTIVE", new JsonPrimitive(rs.getString("FILTER_FLAG_ACTIVE")));
                jsonObj.add("FILTER_TYPE", new JsonPrimitive(rs.getString("FILTER_TYPE")));

                // add the array to the object
                proficiencyTestList.add(jsonObj);

            }
        } catch (SQLException ex) {
        	log.error("sql exception-", ex);
        } finally {
            try {
            	if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqlException) {
            	log.error("sql exception-", sqlException);
            }
        }

        // call the processing function to clean the data 
        JsonArray cleanedList = this.processJsonObj(proficiencyTestList);
        return cleanedList;
    }

    /**
     * Function to process raw Json data and return cleaned data with combined
     * conditions for elemnents with same pfi and fileter number.
     *
     * @param jsonArray
     * @return
     */
    public JsonArray processJsonObj(JsonArray jsonArray) {

        JsonArray resArray = new JsonArray();

        // 1. concatenate the FIELD_NAME, CONDITION VALUE
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject j = new JsonObject();
            j.add("CLIA", jsonArray.get(i).getAsJsonObject().get("CLIA"));
            j.add("PFI", jsonArray.get(i).getAsJsonObject().get("PFI"));
            j.add("LAB_NAME", jsonArray.get(i).getAsJsonObject().get("LAB_NAME"));
            j.add("FILTER_NUMBER", jsonArray.get(i).getAsJsonObject().get("FILTER_NUMBER"));
            j.add("CONDITION_ID", jsonArray.get(i).getAsJsonObject().get("CONDITION_ID"));
            StringBuffer queryCondition = new StringBuffer("");
            
            
            
            queryCondition.append("<span class='fieldName'>").append(jsonArray.get(i).getAsJsonObject().get("FIELD_NAME").getAsString()).append("</span>").append(" ");
            queryCondition.append("<span class='fieldCondition'>").append(jsonArray.get(i).getAsJsonObject().get("CONDITION").getAsString()).append("</span>").append(" ");
            queryCondition.append("<span class='fieldValue'>").append(jsonArray.get(i).getAsJsonObject().get("VALUE").getAsString()).append("</span>");
            
            String cond = queryCondition.toString();
            
            j.add("CONDITION", new JsonPrimitive(cond));
            j.add("OPERATOR", jsonArray.get(i).getAsJsonObject().get("OPERATOR"));
            j.add("processed", new JsonPrimitive("N"));  // variable to keep track if the object is processed
            j.add("FILTER_FLAG_ACTIVE", jsonArray.get(i).getAsJsonObject().get("FILTER_FLAG_ACTIVE"));
            j.add("FILTER_TYPE", jsonArray.get(i).getAsJsonObject().get("FILTER_TYPE"));
            resArray.add(j);
            
        }

        // 2. for a pfi and filter number find all the matching rows
        // also save it to 
        JsonArray resArray2 = new JsonArray();

        for (int i = 0; i < resArray.size(); i++) {
            JsonObject curObj = resArray.get(i).getAsJsonObject();
            String pfi = curObj.get("PFI").getAsString();
            int filterNum = curObj.get("FILTER_NUMBER").getAsInt();
            String cond = curObj.get("CONDITION").getAsString();
            int conditionId = curObj.get("CONDITION_ID").getAsInt();

            // if the current object is not already processed
            if (curObj.get("processed").getAsString().equals("N")) {

                // scan the entire json array for matching objects
                for (int j = 0; j < resArray.size(); j++) {

                    JsonObject curObj2 = resArray.get(j).getAsJsonObject();

                    if (curObj2.get("processed").getAsString().equals("N") && pfi.equals(curObj2.get("PFI").getAsString())  && curObj2.get("FILTER_NUMBER").getAsInt() == filterNum && curObj2.get("CONDITION_ID").getAsInt() != conditionId) {
                        // if the object has operator AND or OR then prefix it else postfix it

                        if (curObj2.get("OPERATOR").getAsString().equals("AND") || curObj2.get("OPERATOR").getAsString().equals("OR")) {
                            cond = curObj2.get("CONDITION").getAsString() + " " + curObj2.get("OPERATOR").getAsString() + " " + cond;
                        } else if (curObj.get("OPERATOR").getAsString().equals("AND") || curObj.get("OPERATOR").getAsString().equals("OR")) {
                            cond = cond + " " + curObj.get("OPERATOR").getAsString() + " " + curObj2.get("CONDITION").getAsString();
                        } else {
                            cond = cond + " " + curObj2.get("CONDITION").getAsString();
                        }

                        // set the processed as N
                        resArray.get(j).getAsJsonObject().add("processed", new JsonPrimitive("Y"));
                    }
                }
                curObj.add("CONDITION", new JsonPrimitive(cond));
                resArray.get(i).getAsJsonObject().add("processed", new JsonPrimitive("Y"));
                resArray2.add(curObj);
            }
        }

        JsonArray resArray3 = new JsonArray();
        // 3. keep only PFI , FILTER_NUMBER , CONDITION remove all other columns

        for (int i = 0; i < resArray2.size(); i++) {
            JsonObject curTempObj = new JsonObject();
            curTempObj.add("CLIA", resArray2.get(i).getAsJsonObject().get("CLIA"));
            curTempObj.add("PFI", resArray2.get(i).getAsJsonObject().get("PFI"));
            curTempObj.add("LAB_NAME", resArray2.get(i).getAsJsonObject().get("LAB_NAME"));
            curTempObj.add("FILTER_NUMBER", resArray2.get(i).getAsJsonObject().get("FILTER_NUMBER"));
            curTempObj.add("CONDITION", resArray2.get(i).getAsJsonObject().get("CONDITION"));
            curTempObj.add("FILTER_FLAG_ACTIVE", resArray2.get(i).getAsJsonObject().get("FILTER_FLAG_ACTIVE"));
            curTempObj.add("FILTER_TYPE", resArray2.get(i).getAsJsonObject().get("FILTER_TYPE"));

            resArray3.add(curTempObj);
        }

        JsonArray resArray4 = new JsonArray();
        
        //4. For subgrid process it in parent child row
        for (int i = 0; i < resArray3.size(); i++) {
            JsonObject curTempObj = new JsonObject();

            String pfi = resArray3.get(i).getAsJsonObject().get("PFI").getAsString();

            boolean presentFlag = false;
            // test if the pfi is already processed
            if (resArray4.size() > 0) {

                for (int k = 0; k < resArray4.size(); k++) {
                    JsonObject j = resArray4.get(k).getAsJsonObject();

                    if (j.has("PFI")) {
                        if (pfi.equals(j.get("PFI").getAsString())) {
                            presentFlag = true;
                            break;
                        }
                    }

                }
            }

            if (presentFlag == false) {
                // if pfi does not already exists in resArray4 then 
            	curTempObj.add("CLIA", resArray3.get(i).getAsJsonObject().get("CLIA"));
                curTempObj.add("PFI", resArray3.get(i).getAsJsonObject().get("PFI"));
                curTempObj.add("LAB_NAME", resArray3.get(i).getAsJsonObject().get("LAB_NAME"));

                JsonArray childFilters = new JsonArray();

                for (int j = 0; j < resArray3.size(); j++) {
                    if (pfi.equals(resArray3.get(j).getAsJsonObject().get("PFI").getAsString())) {
                        JsonObject curTempObjChild = new JsonObject();

                        curTempObjChild.add("PFI", resArray3.get(j).getAsJsonObject().get("PFI"));
                        curTempObjChild.add("LAB_NAME", resArray3.get(j).getAsJsonObject().get("LAB_NAME"));
                        curTempObjChild.add("FILTER_NUMBER", resArray3.get(j).getAsJsonObject().get("FILTER_NUMBER"));
                        curTempObjChild.add("CONDITION", resArray3.get(j).getAsJsonObject().get("CONDITION"));
                        curTempObjChild.add("FILTER_FLAG_ACTIVE", resArray3.get(j).getAsJsonObject().get("FILTER_FLAG_ACTIVE"));
                        curTempObjChild.add("FILTER_TYPE", resArray3.get(j).getAsJsonObject().get("FILTER_TYPE"));
                        childFilters.add(curTempObjChild);
                    }
                }
                curTempObj.add("SUBFilters", childFilters);
                resArray4.add(curTempObj);
            }

        }
        return resArray4;
    }

    /**
     * This method takes pfi and filterno as parameters and returns relevant
     * conditions. This is used in edit lab proficiency conditions dialog.
     *
     * @param pfi
     * @param filterNo
     * @return
     */
    public List<JSONObject> getLabProficiencyList(String pfi, String filterNo) {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;

        String proficiencyTestListQuery = " SELECT pfi,FILTER_NUMBER, FILTER_TYPE, CONDITION_ID,FIELD_NAME,CONDITION,VALUE,NVL(OPERATOR,' ') OPERATOR, FILTER_FLAG_ACTIVE FROM  "
                + "                  NYEHMS_PROCESS_FILTER_VW   "
                + "            where    FILTER_NUMBER=" + filterNo
                + "                 ORDER by pfi,filter_number,operator DESC";

        List<JSONObject> proficiencyTestList = new ArrayList<JSONObject>();

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(proficiencyTestListQuery);

            while (rs.next()) {

                JSONObject jsonObj = new JSONObject();
                String value="";
                if(rs.getString("VALUE")==null){
                    value="";
                }   else {
                    value=rs.getString("VALUE");
                } 
                jsonObj.put("PFI",rs.getString("PFI"));
                jsonObj.put("FILTER_NUMBER", rs.getInt("FILTER_NUMBER"));
                jsonObj.put("CONDITION_ID", rs.getInt("CONDITION_ID"));
                jsonObj.put("FIELD_NAME", rs.getString("FIELD_NAME"));
                jsonObj.put("CONDITION", rs.getString("CONDITION"));
                jsonObj.put("VALUE", value);
                jsonObj.put("OPERATOR", rs.getString("OPERATOR"));
                jsonObj.put("FILTER_FLAG_ACTIVE", rs.getString("FILTER_FLAG_ACTIVE"));
                jsonObj.put("FILTER_TYPE", rs.getString("FILTER_TYPE"));
                

                // add the array to the object
                proficiencyTestList.add(jsonObj);

            }
        } catch (SQLException ex) {
                  log.error("sql exception -", ex);
        } finally {
            try {
            	if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqlException) {
            	  log.error("sql exception -", sqlException);
            }
        }

        return proficiencyTestList;
    }

    /**
     * This function will inactivate the filter in database.
     *
     * @param filterNo
     * @param pfi
     * @param flagActive
     * @param UserId
     * @return
     */
    public String updateLabProfFilter(String filterNo, String pfi, String flagActive, String UserId) {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.createStatement();

            String query = "  UPDATE LAB_PROCESS_PFI "
                    + "                SET FLAG_ACTIVE = '" + flagActive + "', "
                    + "                 LAST_UPDATED_BY =" + UserId + ", "
                    + "                 LAST_UPDATED_DT = SYSDATE "
                    + "                WHERE FILTER_NUMBER =" + filterNo
                    + "                AND PFI ='" + pfi+"'";

            statement.executeUpdate(query);
            conn.commit();

        } catch (SQLException ex) {
        	log.error("sql exception -", ex);
            return "Error " + ex.getMessage();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqlException) {
            	log.error("sql exception -", sqlException);
            }
        }
        return "Test Filters Successfully Updated.";
    }
    
    public List<JSONObject> getProfieciencyFilteredataForOneCondition(String pfi, List<JSONObject> filterTypeCondition, Integer documentId) {
    	
    	
    	List<List<JSONObject>> filterTypeConditions = new ArrayList<List<JSONObject>>();
    	
    	filterTypeConditions.add(filterTypeCondition);
    	
    	StringBuffer queryString =  prepareQueryStringUsingProcessFilters(filterTypeConditions);
    	
    	return getProfieciencyFilteredata(pfi, queryString, documentId);
    	
    }
    
    public StringBuffer prepareQueryStringUsingProcessFilters(List<List<JSONObject>> filterTypeConditions){
    	StringBuffer queryString = new StringBuffer();
    	
    	int _index = 0;
    	for (List<JSONObject> jsonArray : filterTypeConditions) {
    		
    		String _currentStringQuery = LabCommonUtils.getQueryCondition(jsonArray);
    		
    		if(_currentStringQuery != null && !"".equals(_currentStringQuery)){
    			
    			if(_index != 0){
    				queryString.append(" OR ");
    			}
    			queryString.append(" (").append(_currentStringQuery).append(") ");
    		}
    		
    		_index++;
		}
    	
    	return queryString;
    	
    }
    

    public List<JSONObject> getProfieciencyFilteredata(String pfi, StringBuffer queryString, Integer documentId) {
    	
    	if(documentId != null){
    		return queryResultsUsingFilterConditions(pfi, queryString, documentId, null);
    	}else{
    		
    		
    		int createDateByDays = 0;
    		
    		
    		List<JSONObject> queryResults = null;
    		
    		while(createDateByDays < 730 && (queryResults == null  || queryResults.size() < 100)){
    		
    			createDateByDays = createDateByDays+30;
    			
    			queryResults = queryResultsUsingFilterConditions(pfi, queryString, documentId, createDateByDays);
    			
    			
    		}
    		
    		return queryResults;
    		
    		
    		
    	}
    	
    	  
    	
    	
    	
    	
    	
    }
    
    private List<JSONObject> queryResultsUsingFilterConditions(String pfi, StringBuffer queryString, Integer documentId, Integer createDateByDays) {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;
        List<JSONObject> filterData = new ArrayList<JSONObject>();
        
        try {
        	
        	
        	 
            
            boolean hadWhereCondition = false;
            StringBuffer sqlQuery = new StringBuffer(); 
            sqlQuery.append("select /*+ FIRST_ROWS(100) */ DOCUMENT_ID, OBRPROVIDERLASTNAME, PIDPATIENTIDINTERNAL, OBRPROVIDERFIRSTNAME, ZLRFACILITYNAME, OBXOBSERVATIONRESULTTEXT, OBRACCESSIONNUM, PIDMIDDLENAME,  PIDFIRSTNAME, PIDLASTNAME, to_char(PIDDATEOFBIRTH, 'MM/DD/YYYY') PIDDATEOFBIRTH, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2 , ZLRFACILITYCITY ,ZLRPROVIDERADDRESSLINE1 , ZLRPROVIDERADDRESSLINE2 , ZLRPROVIDERCITY, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, OBXLOINCCODE   from RAW_HIV_ECLRS_DOC where CURRENT_PROCESS_STATUS not in ("+CommonConstants.RAW_DUPLICATION_RECORD_PROCESS_STATUS+","+CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS+","+CommonConstants.RAW_RECORD_MULTISTEP_DELETED_PROCESS_STATUS+","+CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS+") AND ");
            
            if(!"-999".equals(pfi)){
            	sqlQuery.append(" MSHLABPFI = '"+pfi+"'");
            	hadWhereCondition = true;
            }
            
            if(documentId != null){
            	
            	 if(hadWhereCondition){
            		 sqlQuery.append(" and ");
            	 }
            	sqlQuery.append(" DOCUMENT_ID = "+documentId);
            	hadWhereCondition = true;
            }else{
            	if(hadWhereCondition){
	           		 sqlQuery.append(" and ");
	           	 }
	           	sqlQuery.append(" MSHCREATEDATE > SYSDATE -").append(createDateByDays);
	           	
	           	sqlQuery.append(" and ROWNUM < 101 ");
	           	
	           	hadWhereCondition = true;
            }
            
            
            		
    		if(!"".equals(queryString.toString())){
    			
    			if(hadWhereCondition){
    				sqlQuery.append(" and ");
           	 	}
    			sqlQuery.append("(").append(queryString).append(")"); 
    		}else{
    			return filterData;
    		}
    		
    		if(log.isDebugEnabled()){
    			log.debug("Executing  {}",sqlQuery);
    		}
    		
    		statement = conn.createStatement();
            rs = statement.executeQuery(sqlQuery.toString());
            
            while(rs.next()){
            	JSONObject rawRecord = new JSONObject();
            	
            	rawRecord.put("firstName", rs.getString("PIDFIRSTNAME"));
            	rawRecord.put("lastName", rs.getString("PIDLASTNAME"));
            	rawRecord.put("dateOfBirth", rs.getString("PIDDATEOFBIRTH"));
            	rawRecord.put("documentId", rs.getString("DOCUMENT_ID"));
            	
            	rawRecord.put("obrProviderLastName", rs.getString("OBRPROVIDERLASTNAME"));
            	rawRecord.put("pidPatientIdInternal", rs.getString("PIDPATIENTIDINTERNAL"));
            	rawRecord.put("obrProviderFirstName", rs.getString("OBRPROVIDERFIRSTNAME"));
            	rawRecord.put("zlrFacilityName", rs.getString("ZLRFACILITYNAME"));
            	rawRecord.put("obxObservationResultText", rs.getString("OBXOBSERVATIONRESULTTEXT"));
            	rawRecord.put("obrAccessionNum", rs.getString("OBRACCESSIONNUM"));
            	rawRecord.put("pidMiddleName", rs.getString("PIDMIDDLENAME"));
            	rawRecord.put("zlrFacilityAddressLine1", rs.getString("ZLRFACILITYADDRESSLINE1"));
            	rawRecord.put("zlrFacilityAddressLine2", rs.getString("ZLRFACILITYADDRESSLINE2"));
            	rawRecord.put("zlrFacilityCity", rs.getString("ZLRFACILITYCITY"));
            	rawRecord.put("zlrProviderAddressLine1", rs.getString("ZLRPROVIDERADDRESSLINE1"));
            	rawRecord.put("zlrProvideraddressline2", rs.getString("ZLRPROVIDERADDRESSLINE2"));
            	rawRecord.put("zlrProviderCity", rs.getString("ZLRPROVIDERCITY"));
            	
            	rawRecord.put("personAddress1", rs.getString("PIDADDRESSLINE1"));
            	rawRecord.put("personAddress2", rs.getString("PIDADDRESSLINE2"));
            	rawRecord.put("personCity", rs.getString("PIDCITY"));
            	rawRecord.put("personState", rs.getString("PIDSTATE"));
            	rawRecord.put("loinc", rs.getString("OBXLOINCCODE"));
            	
            	
            	filterData.add(rawRecord);
            }
            
            if(log.isDebugEnabled()){
            	log.debug("Query returned documents size  {}",filterData.size());
            }
            conn.commit();

        } catch (SQLException ex) {
        	log.error("sql exception -", ex);
        	
            //return "Error " + ex.getMessage();
        } finally {
            try {
            	if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqlException) {
            	log.error("sql exception -", sqlException);
            }
        }
        return filterData;
    }
}
