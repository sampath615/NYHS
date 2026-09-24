package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms.EharsDataLoaderDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.jdbc.ConnectionDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;


@Service
public class EharsDataLoaderServiceImpl implements EharsDataLoaderService {

	private static Logger log = LoggerFactory.getLogger(EharsDataLoaderServiceImpl.class);
	
	
	@Autowired
	private ConnectionDAO connectionDAO;
	
	@Autowired
	private EharsDataLoaderDAO eharsDataLoaderDAO;
	
	@Autowired
	private EharsDataLoaderTransactionHelper eharsDataLoaderTransactionHelper;
	
	
	@Override
	public void loadRidrDataToNYEHMS(List<String> notificationObj) throws SQLException {
		
		Statement sqlStatement = null;
		ResultSet sqlResultSet = null;
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		Connection oracleConnection = null;
		
		try {
			
			sqlConnection = connectionDAO.getSqlServerConnectionObj();
			
			if(sqlConnection == null) {
				log.debug("EHARS Datasource connection not available. ");
				notificationObj.add("EHARS Datasource connection not available");
				throw new DataRetrievalFailureException("EHARS Datasource connection not available");
			}
			
			oracleConnection = connectionDAO.getConnectionObj();
			
			if(oracleConnection == null) {
				log.debug("Oracle Datasource connection not available. ");
				notificationObj.add("Oracle Datasource connection not available");
				throw new DataRetrievalFailureException("Oracle Datasource connection not available");
			}
				
			sqlStatement = sqlConnection.createStatement();
			
			if(log.isDebugEnabled()){
				log.debug("Statement object for SQL server database {}",sqlStatement);
			}
			
			String sqlQuery = "select ridr.stateno RIDR_STATENO, ridr.state_cd RIDR_STATE_CD, id1.id_value EHARS_STATENO from RIDR ridr, id id1, document doc where ridr.document_uid = id1.document_uid and id1.id_cd = '165' and ridr.document_uid = doc.document_uid and doc.status_flag not in ('D', 'P', 'R') and ridr.duplicate_Status = '1'";
			
			sqlResultSet = sqlStatement.executeQuery(sqlQuery);
			
			notificationObj.add("Fetched the EHARS RIDR same as data");
			
			preparedStatement = oracleConnection.prepareStatement("insert into EHARS_RIDR(RIDR_STATENO, RIDR_STATE_CD, EHARS_STATENO) values(?,?, ?)");
			
			
			int index = 0;
			
			while(sqlResultSet.next()) {
				
				index++;
				
				preparedStatement.setString(1, sqlResultSet.getString("RIDR_STATENO"));
				preparedStatement.setString(2, sqlResultSet.getString("RIDR_STATE_CD"));
				preparedStatement.setString(3, sqlResultSet.getString("EHARS_STATENO"));
				
				preparedStatement.addBatch();
				
				if (index % 5000 == 0) {
					preparedStatement.executeBatch();
				}
				
			}
			
			preparedStatement.executeBatch();
			
			if(index == 0) {
				notificationObj.add("There is no RIDR same as Data");
			}
			
			notificationObj.add("Loaded the RIDR same as data in NYEHMS EHARS_RIDR table");
			
			
		} finally {
			try {
				if (sqlConnection != null) {
					sqlConnection.close();
				}
				if (sqlStatement != null) {
					sqlStatement.close();
				}
				if (oracleConnection != null) {
					oracleConnection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (sqlResultSet != null) {
					sqlResultSet.close();
				}

			} catch (SQLException sqlException) {
				log.error("Exception while closing the connection {}"
						, sqlException.getMessage());
			}
		}

		
	}
	
//	@Override
//				log.error("Exception while closing the connection"
	
	@Override
	public boolean loadEharsDataInToNyehmsTables(List<String> notificationObj) {
		
		boolean isError = false; 
		try{
			
			eharsDataLoaderDAO.deleteRIDREharsData();
			
			notificationObj.add("Deleted the data in EHARS_RIDR table");
			
			eharsDataLoaderTransactionHelper.loadEharsDataInToNyehmsTables(notificationObj);
			
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			log.error("Failed while loading EHARS data into NYEHMS tables");
			log.error(dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			log.error("Failed while loading EHARS data into NYEHMS tables");
			log.error(exception.getMessage());
		}
		
		return isError;
	}
	
	@Override
	public void deleteRIDREharsData(List<String> notificationObj) {
		
		eharsDataLoaderDAO.deleteRIDREharsData();
		
		notificationObj.add("Deleted the data in EHARS_RIDR table");
		
	}
}