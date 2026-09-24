package gov.ny.state.doh.bhae.nyehms.web.spring.util;

/*
 * Author : Srikar Kummamuri
 * Date : 4/23/2012
 * Description : This class is a utility class to generate Object Response in web services context.
 */
import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.ajax.json.JsonObject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetGridObject {	

	/*
	 * Reason why we are not using the GridObject as a spring bean by declaring it as a component is, 
	 * Spring AOP logging, which is being used for debugging purpose on entire application, is conflicting
	 * with the JSON response writing and hence we thought of taking that class out as Spring component for now and
	 * use the traditional java new operator to create it.
	 * This problem between JSON and AOP must be resolved in future.
	 */
	GridObject gridObject = new GridObject();
	
		
	public GridObject GetGrid(List rows){
		
		int totalRecords=rows.size();
		int totalPages=1;
		int recordsPerPage=5;
		
			if( (totalRecords/recordsPerPage > 1) ){				
				totalPages= java.lang.Math.round(totalRecords/5) ;				
			}		
			
			gridObject.setCurrentPage(1);
			gridObject.setTotalPages(totalPages);
			gridObject.setRecordsPerPage(recordsPerPage);
			gridObject.setRows(rows);
			gridObject.setTotalRecords(totalRecords);
		
		return gridObject;		
		
	}
	
	
public GridObject getGridDataOnPaginationLoad(List rows, int currentPage, int rowCount, int totalRecordsCount){
		
			int totalPages= java.lang.Math.round(totalRecordsCount/rowCount) + 1 ;				
			
			gridObject.setCurrentPage(currentPage);
			gridObject.setTotalPages(totalPages);
			gridObject.setRecordsPerPage(rowCount);
			gridObject.setRows(rows);
			gridObject.setTotalRecords(totalRecordsCount);
			gridObject.setRecords(totalRecordsCount);
		
		return gridObject;		
		
	}
	
	public JsonObject GetJsonObject(List rows){		
		JsonObject  jsonObject = new JsonObject();		
		jsonObject.setResultList(rows);		
		return jsonObject;
	}
	
	

}
