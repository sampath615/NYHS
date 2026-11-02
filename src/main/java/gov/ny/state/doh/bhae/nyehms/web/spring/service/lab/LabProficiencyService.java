package gov.ny.state.doh.bhae.nyehms.web.spring.service.lab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.lab.LabProcessDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.lab.LabProficiencyRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.scheduler.LabCommonUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

/**
 * Lab proficiency filters: the lab's filters and their conditions, their maintenance, and the eCLRS lab reports a
 * filter matches.
 * <p>
 * All SQL is in {@link LabProficiencyRepository} (or built here for the filter search) with bound values; before, the
 * request's values were concatenated into the SQL. Writes run in one transaction per request: all rows or none.
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Component
public class LabProficiencyService {

	private static final Logger log = LoggerFactory.getLogger(LabProficiencyService.class);

	private static final String FILTERS_UPDATED = "Test Filters Successfully Updated.";
	private static final String FILTERS_ADDED = "Test Filters Successfully Added.";
	/** The filter search is widened 30 days at a time until it finds 100 reports or covers two years. */
	private static final int SEARCH_DAYS_STEP = 30;
	private static final int SEARCH_DAYS_MAX = 730;
	private static final int SEARCH_ENOUGH_RESULTS = 100;
	/** A PFI of -999 means every lab. */
	private static final String ALL_LABS = "-999";

	private final LabProficiencyRepository labProficiencyRepository;
	private final LabProcessDAO labProcessDAO;
	private final PlatformTransactionManager transactionManager;

	@PersistenceContext
	private EntityManager entityManager;

	/** Every lab's filters with their conditions combined, for the filter grid ({"data": [...]}). */
	public JsonObject getProficiencyTests() {
		JsonArray proficiencyTestList = new JsonArray();
		try {
			for (Object[] row : labProficiencyRepository.findAllFilterConditions()) {
				String value = text(row[8]) == null ? " " : text(row[8]);
				JsonObject jsonObj = new JsonObject();
				jsonObj.add("CLIA", new JsonPrimitive(text(row[0])));
				jsonObj.add("PFI", new JsonPrimitive(text(row[1])));
				jsonObj.add("LAB_NAME", new JsonPrimitive(text(row[2])));
				jsonObj.add("FILTER_NUMBER", new JsonPrimitive(number(row[3])));
				jsonObj.add("CONDITION_ID", new JsonPrimitive(number(row[4])));
				jsonObj.add("FIELD_NAME", new JsonPrimitive(text(row[5])));
				jsonObj.add("CONDITION", new JsonPrimitive(text(row[7])));
				jsonObj.add("VALUE", new JsonPrimitive(value));
				jsonObj.add("OPERATOR", new JsonPrimitive(text(row[9])));
				jsonObj.add("FILTER_FLAG_ACTIVE", new JsonPrimitive(text(row[10])));
				jsonObj.add("FILTER_TYPE", new JsonPrimitive(text(row[6])));
				proficiencyTestList.add(jsonObj);
			}
		} catch (DataAccessException ex) {
			log.error("sql exception-", ex);
		}
		JsonObject jsonObj = new JsonObject();
		jsonObj.add("data", processJsonObj(proficiencyTestList));
		return jsonObj;
	}

	/** The active condition kinds ({"data": [{"CONDITION": ...}]}). */
	public JsonObject getProficiencyConditions() {
		JsonArray proficiencyConditionList = new JsonArray();
		try {
			for (String condition : labProficiencyRepository.findActiveConditions()) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.add("CONDITION", new JsonPrimitive(condition));
				proficiencyConditionList.add(jsonObj);
			}
		} catch (DataAccessException ex) {
			log.error("sql exception-", ex);
		}
		JsonObject jsonObj = new JsonObject();
		jsonObj.add("data", proficiencyConditionList);
		return jsonObj;
	}

	/** The conditions of a filter, for the edit dialog (the PFI is not used, as before). */
	@SuppressWarnings("unchecked")
	public List<JSONObject> getProficiencyTests(String pfi, String filterNo) {
		List<JSONObject> proficiencyTestList = new ArrayList<>();
		try {
			for (Object[] row : labProficiencyRepository.findConditionsOfFilter(filterNo)) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("PFI", text(row[0]));
				jsonObj.put("FILTER_NUMBER", number(row[1]));
				jsonObj.put("CONDITION_ID", number(row[3]));
				jsonObj.put("FIELD_NAME", text(row[4]));
				jsonObj.put("CONDITION", text(row[5]));
				jsonObj.put("VALUE", text(row[6]) == null ? "" : text(row[6]));
				jsonObj.put("OPERATOR", text(row[7]));
				jsonObj.put("FILTER_FLAG_ACTIVE", text(row[8]));
				jsonObj.put("FILTER_TYPE", text(row[2]));
				proficiencyTestList.add(jsonObj);
			}
		} catch (DataAccessException ex) {
			log.error("sql exception -", ex);
		}
		return proficiencyTestList;
	}

	/** The active labs as select options: value PFI, label "CLIA / PFI- (lab name)". */
	public List<Map<String, String>> getLabPfiList() {
		List<Map<String, String>> pfiAndLabNameMap = new ArrayList<>();
		try {
			for (Object[] row : labProficiencyRepository.findActiveLabs()) {
				String pfi = text(row[1]);
				Map<String, String> pfiMap = new HashMap<>();
				pfiMap.put("value", pfi);
				pfiMap.put("label", new StringBuilder(text(row[0])).append(" / ").append(pfi).append("- (").append(text(row[2])).append(")").toString());
				pfiAndLabNameMap.add(pfiMap);
			}
		} catch (Exception ex) {   // a lab without a CLIA ends the list, as before
			log.error("sql exception -", ex);
		}
		return pfiAndLabNameMap;
	}

	/**
	 * Saves the edited conditions of a filter: new ("N"), changed ("U") and removed ("D") rows; any other row gets the
	 * filter type.
	 *
	 * @return the message for the screen
	 */
	public String updateLabProficiencyData(JsonArray editData, String filterType, String userId) {
		return inTransaction(FILTERS_UPDATED, () -> {
			for (JsonElement element : editData) {
				JsonObject row = element.getAsJsonObject();
				String filterNumber = row.get("FILTER_NUMBER").getAsString();
				String fieldName = row.get("FIELD_NAME").getAsString();
				String condition = row.get("CONDITION").getAsString();
				String value = row.get("VALUE").getAsString();
				String operator = row.get("OPERATOR").getAsString();
				String conditionId = row.get("CONDITION_ID").getAsString();
				switch (row.get("processed").getAsString()) {
					case "N" -> labProficiencyRepository.insertCondition(filterNumber, fieldName, condition, value, operator, userId, filterType);
					case "U" -> labProficiencyRepository.updateCondition(fieldName, filterType, condition, value, operator, userId, filterNumber, conditionId);
					case "D" -> {
						if (!conditionId.isEmpty()) {
							labProficiencyRepository.deactivateCondition(filterType, userId, filterNumber, conditionId);
						}
					}
					default -> labProficiencyRepository.updateConditionFilterType(filterType, userId, filterNumber, conditionId);
				}
			}
		});
	}

	/**
	 * Adds a filter to a lab: a new filter number from the database procedure, then the new ("N") conditions.
	 *
	 * @return the message for the screen
	 */
	public String addLabProficiencyData(JsonArray editData, String filterType, String userId) {
		String pfi = editData.get(0).getAsJsonObject().get("PFI").getAsString();
		return inTransaction(FILTERS_ADDED, () -> {
			String filterNumber = Integer.toString(newFilterNumber(pfi, userId));
			for (JsonElement element : editData) {
				JsonObject row = element.getAsJsonObject();
				String fieldName = row.get("FIELD_NAME").getAsString();
				String condition = row.get("CONDITION").getAsString();
				String value = row.get("VALUE").getAsString();
				String operator = row.get("OPERATOR").getAsString();
				row.get("CONDITION_ID").getAsString();   // required, as before
				if (row.get("processed").getAsString().equals("N")) {
					labProficiencyRepository.insertCondition(filterNumber, fieldName, condition, value, operator, userId, filterType);
				}
			}
		});
	}

	/** pkg_lab_maintenance.pr_get_filter_id_PROFICIENCY: a new filter number for the lab (0 if none is returned). */
	private int newFilterNumber(String pfi, String userId) {
		StoredProcedureQuery procedure = entityManager.createStoredProcedureQuery("pkg_lab_maintenance.pr_get_filter_id_PROFICIENCY");
		procedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		procedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		procedure.registerStoredProcedureParameter(3, java.math.BigDecimal.class, ParameterMode.OUT);
		procedure.registerStoredProcedureParameter(4, java.math.BigDecimal.class, ParameterMode.OUT);
		procedure.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT);
		procedure.setParameter(1, pfi);
		procedure.setParameter(2, userId);
		procedure.execute();
		return number(procedure.getOutputParameterValue(3));
	}

	/** Activates or deactivates a lab's filter. */
	public String updateProficiencyFilter(String filterNo, String pfi, String flagActive, String userId) {
		return inTransaction(FILTERS_UPDATED, () -> labProficiencyRepository.updateFilterActive(flagActive, userId, filterNo, pfi));
	}

	/** Runs the writes in one transaction; the message on success, else "Error " and the reason (nothing is saved). */
	private String inTransaction(String success, Runnable writes) {
		try {
			new TransactionTemplate(transactionManager).executeWithoutResult(status -> writes.run());
			return success;
		} catch (DataAccessException | TransactionException | jakarta.persistence.PersistenceException ex) {
			log.error("sql exception -", ex);
			return "Error " + ex.getMessage();
		}
	}

	/** The eCLRS lab reports that match the given filters of a lab (or one report, if a document id is given). */
	public List<JSONObject> getProfieciencyFilteredata(String pfi, List<String> filterNumbers, UserObject userObject, Integer documentId) {
		List<List<JSONObject>> filterTypeConditions = new ArrayList<>();
		for (String filterNumber : filterNumbers) {
			filterTypeConditions.add(getProficiencyTests(pfi, filterNumber));
		}
		if (filterTypeConditions.isEmpty()) {
			return null;
		}
		return filteredData(pfi, filterTypeConditions, documentId);
	}

	/** The eCLRS lab reports that match one set of conditions given on the screen. */
	public List<JSONObject> getProfieciencyFilteredataWithCustomQueryCriteria(String pfi, JsonArray queryCriteria) {
		Gson gson = new Gson();
		List<JSONObject> queryCriteriaList = new ArrayList<>();
		for (JsonElement jsonElement : queryCriteria) {
			queryCriteriaList.add(gson.fromJson(jsonElement.toString(), JSONObject.class));
		}
		if (queryCriteriaList.isEmpty()) {
			return null;
		}
		return filteredData(pfi, List.of(queryCriteriaList), null);
	}

	public List<Map<String, String>> getFilterTypeConditions() {
		return labProcessDAO.getFilterTypeConditions();
	}

	public List<Map<String, String>> getPfiLabNames() {
		return labProcessDAO.getPfiLabNames();
	}

	/**
	 * The reports matching any of the condition groups: for a document id, that report; else the newest, searching
	 * back 30 days at a time until 100 are found or two years are covered.
	 */
	private List<JSONObject> filteredData(String pfi, List<List<JSONObject>> filterTypeConditions, Integer documentId) {
		List<Object> conditionValues = new ArrayList<>();
		String conditions = conditions(filterTypeConditions, conditionValues);
		if (documentId != null) {
			return reportsMatching(pfi, conditions, conditionValues, documentId, null);
		}
		int days = 0;
		List<JSONObject> reports = null;
		while (days < SEARCH_DAYS_MAX && (reports == null || reports.size() < SEARCH_ENOUGH_RESULTS)) {
			days += SEARCH_DAYS_STEP;
			reports = reportsMatching(pfi, conditions, conditionValues, null, days);
		}
		return reports;
	}

	/** The condition groups joined with OR (each group's values are added to {@code values}). */
	private static String conditions(List<List<JSONObject>> filterTypeConditions, List<Object> values) {
		StringBuilder queryString = new StringBuilder();
		int index = 0;
		for (List<JSONObject> group : filterTypeConditions) {
			String condition = LabCommonUtils.getQueryCondition(group, values);
			if (!condition.isEmpty()) {
				if (index != 0) {
					queryString.append(" OR ");
				}
				queryString.append(" (").append(condition).append(") ");
			}
			index++;
		}
		return queryString.toString();
	}

	/** The eCLRS reports (RAW_HIV_ECLRS_DOC) matching the conditions; none if there are no conditions. */
	private List<JSONObject> reportsMatching(String pfi, String conditions, List<Object> conditionValues, Integer documentId, Integer createdWithinDays) {
		if (conditions.isEmpty()) {
			return new ArrayList<>();
		}
		StringBuilder sqlQuery = new StringBuilder("select /*+ FIRST_ROWS(100) */ DOCUMENT_ID, OBRPROVIDERLASTNAME, PIDPATIENTIDINTERNAL, OBRPROVIDERFIRSTNAME, ZLRFACILITYNAME, OBXOBSERVATIONRESULTTEXT, OBRACCESSIONNUM, PIDMIDDLENAME,  PIDFIRSTNAME, PIDLASTNAME, to_char(PIDDATEOFBIRTH, 'MM/DD/YYYY') PIDDATEOFBIRTH, ZLRFACILITYADDRESSLINE1, ZLRFACILITYADDRESSLINE2 , ZLRFACILITYCITY ,ZLRPROVIDERADDRESSLINE1 , ZLRPROVIDERADDRESSLINE2 , ZLRPROVIDERCITY, PIDADDRESSLINE1, PIDADDRESSLINE2, PIDCITY, PIDSTATE, OBXLOINCCODE   from RAW_HIV_ECLRS_DOC where CURRENT_PROCESS_STATUS not in (")
				.append(CommonConstants.RAW_DUPLICATION_RECORD_PROCESS_STATUS).append(",").append(CommonConstants.RAW_RECORD_DELETED_PROCESS_STATUS).append(",")
				.append(CommonConstants.RAW_RECORD_MULTISTEP_DELETED_PROCESS_STATUS).append(",").append(CommonConstants.DOCUMENT_EDITED_PROCESS_STATUS).append(") AND ");
		List<Object> values = new ArrayList<>();
		if (!ALL_LABS.equals(pfi)) {
			sqlQuery.append(" MSHLABPFI = ? and ");
			values.add(pfi);
		}
		if (documentId != null) {
			sqlQuery.append(" DOCUMENT_ID = ?");
			values.add(documentId);
		} else {
			// a number of days the service computes (not a request value)
			sqlQuery.append(" MSHCREATEDATE > SYSDATE -").append(createdWithinDays.intValue()).append(" and ROWNUM < 101 ");
		}
		sqlQuery.append(" and (").append(conditions).append(")");
		values.addAll(conditionValues);
		log.debug("Executing  {}", sqlQuery);

		TransactionTemplate readOnly = new TransactionTemplate(transactionManager);
		readOnly.setReadOnly(true);
		return readOnly.execute(status -> entityManager.unwrap(Session.class).doReturningWork(connection -> {
			List<JSONObject> filterData = new ArrayList<>();
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery.toString())) {
				for (int i = 0; i < values.size(); i++) {
					statement.setObject(i + 1, values.get(i));
				}
				try (ResultSet rs = statement.executeQuery()) {
					while (rs.next()) {
						filterData.add(report(rs));
					}
				}
			} catch (SQLException ex) {
				log.error("sql exception -", ex);
			}
			log.debug("Query returned documents size  {}", filterData.size());
			return filterData;
		}));
	}

	@SuppressWarnings("unchecked")
	private static JSONObject report(ResultSet rs) throws SQLException {
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
		return rawRecord;
	}

	/** A column as getString read it (whole numbers without a decimal part). */
	private static String text(Object value) {
		if (value instanceof java.math.BigDecimal number) {
			return number.toPlainString();
		}
		return value == null ? null : value.toString();
	}

	/** A number column as getInt read it: 0 for null. */
	private static int number(Object value) {
		return value == null ? 0 : ((Number) value).intValue();
	}

	/**
	 * Combines the conditions of each filter into one text and groups the filters by lab (moved unchanged from the
	 * former LabProficiencyVO).
	 */
	static JsonArray processJsonObj(JsonArray jsonArray) {

	    JsonArray resArray = new JsonArray();

	    // 1. concatenate the FIELD_NAME, CONDITION VALUE
	    for (int i = 0; i < jsonArray.size(); i++) {
	        JsonObject j = new JsonObject();
	        j.add("CLIA", jsonArray.get(i).getAsJsonObject().get("CLIA"));
	        j.add("PFI", jsonArray.get(i).getAsJsonObject().get("PFI"));
	        j.add("LAB_NAME", jsonArray.get(i).getAsJsonObject().get("LAB_NAME"));
	        j.add("FILTER_NUMBER", jsonArray.get(i).getAsJsonObject().get("FILTER_NUMBER"));
	        j.add("CONDITION_ID", jsonArray.get(i).getAsJsonObject().get("CONDITION_ID"));
	        StringBuilder queryCondition = new StringBuilder("");
	        
	        
	        
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
}
