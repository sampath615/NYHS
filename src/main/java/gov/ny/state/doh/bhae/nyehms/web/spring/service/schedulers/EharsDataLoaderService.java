package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.sql.SQLException;
import java.util.List;

/**
 * This service interface is used to load the ehars data into NYEHMS tables
 * 
 * @author ska03
 * 
 */
public interface EharsDataLoaderService {

	public void loadRidrDataToNYEHMS(List<String> notificationObj) throws SQLException;

	public boolean loadEharsDataInToNyehmsTables(List<String> notificationObj) ;

	//public void loadRidrDataToNYEHMS(ResultSet sqlDataResultSet, List<String> notificationObj) throws SQLException;

	public void deleteRIDREharsData(List<String> notificationObj);

}
