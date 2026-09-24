package gov.ny.state.doh.bhae.nyehms.web.spring.service.dashboard;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.dashboard.GraphDataDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is implementation of the GraphDataService interface. This will provide
 * method for displaying graphs on homepage.
 *
 * @author vxp06
 */
@Component
public class GraphDataServiceImpl implements GraphDataService {
//
//    @Autowired
//    private ConnectionDAO connectionDAO;
	
	@Autowired
	GraphDataDAO graphDataDAO;

    @Override
    public List<JSONObject> getWorkerTallyCounts(UserObject userObject){
    	
        
//        partnerListQuery.append("SELECT REGION_DESCRIPTION , COUNT(*) OPEN_CNT , SUM(CASE WHEN s.CREATE_DATE < SYSDATE-90 THEN 1 ELSE 0 END ) CNT_90, MAX(fn_to_date(E.HIV_DX_DT ,'MMDDYYYY')) hiv_dx_date ");
//        
//        partnerListQuery.append("FROM SURVEILLANCE_ASSIGNMENT s  , WORKER_ASSIGNMENT W , coded_region r, NYEHMS_EHARS_STATENO_MAP E , USER_REGION U  ").
//        append("WHERE CURRENT_ASSIGNMENT_STATUS IN ('OPEN','REOPEN')  ").
//        append("AND W.WORKER_ASSIGNMENT_ID = s.CURRENT_WORKER_ASSIGNMENT_ID  "). 
//		append("AND s.current_region_cd =r.REGION_CODE   ").
//		append("AND s.stateno = E.NYEHMS_STATENO(+)   "). 
//		append("AND U.END_DATE IS NULL   ").
//		append("AND U.worker_region_id = w.worker_region_id   "). 
//		append("and U.worker_id =  "+userObject.getWorkerId()).
//		append("GROUP BY REGION_DESCRIPTION ");
    	
    	
    	Map<String, Integer> inputParameterMap =new HashMap<String, Integer>();
    	
    	Calendar calendar = Calendar.getInstance();
		
    	inputParameterMap.put("currentYear", calendar.get(Calendar.YEAR));
    	inputParameterMap.put("previousYear", calendar.get(Calendar.YEAR)-1);
    	inputParameterMap.put("workerId", userObject.getWorkerId());
    	
    	inputParameterMap.put("PRIORITY_ASSIGNMENT_PERIOD", Integer.parseInt(EnvironmentProperties.envProperties.getProperty("PRIORITY_ASSIGNMENT_PERIOD")));
		
    	
    	List<Map<String, Object>> workerTallyGraphData = graphDataDAO.getWorkerTallyGraphData(inputParameterMap);
    	
    	
    	List<JSONObject> allRegionsAndWorkersList = new ArrayList<JSONObject>();
    	
    	for (Map<String, Object> map : workerTallyGraphData) {
			
    		String REGION_DESCRIPTION = (String)map.get("REGION_DESCRIPTION");
    		Integer PRI_OPEN_CNT = (Integer)map.get("PRI_OPEN_CNT");
    		Integer OPEN_CNT = (Integer)map.get("OPEN_CNT");
    		Integer CNT_90 = (Integer)map.get("CNT_90");
    		
    		if(CommonUtil.isNullOrEmpty(REGION_DESCRIPTION) || (0 == PRI_OPEN_CNT && 0 == OPEN_CNT && 0 == CNT_90)){
    			
    			  break;
    		}
    		
    		JSONObject regionAndWorkersList = new JSONObject();
    		
    		List<Integer> assignmentCountsByRegion = new ArrayList<Integer>();
    		
    		assignmentCountsByRegion.add(OPEN_CNT);
    		assignmentCountsByRegion.add(CNT_90);
    		assignmentCountsByRegion.add(PRI_OPEN_CNT);
    		
    		
    		 regionAndWorkersList.put("name",REGION_DESCRIPTION);
             regionAndWorkersList.put("data", assignmentCountsByRegion);

             
             allRegionsAndWorkersList.add(regionAndWorkersList);
		}
 
    	
    	
    	
    	return allRegionsAndWorkersList;
    	
    	
		
//
//        
////        String partnerListQuery = "SELECT NVL(REGION_DESC,'') REGION_DESC, ALL_OPEN_COUNT ,  ALL_REOPEN_COUNT , "
////                + " PENDING_REASSIGN_COUNT, NEW_COUNT, ADMIN_CLOSED_COUNT, CLOSED_90_DAYS"
    }
}
