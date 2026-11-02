package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.jdbc.JpaConnections;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.person.PersonDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;

/**
* This class is service interface between the user data to Database(MyBatis related configuration)
* 
* It renders the data from Database in the form List, Int, Sting etc uinsg MyBatis configuation file 
*  
* @author  
* @version 
*/
@RequiredArgsConstructor
@Service
public class DatafluxUploadServiceImpl {
	
	private final CommonExceptionHandler commonExceptionHandler;
	
	private final PersonDAO personDAO;
	
	private final CommonDAO commonDAO;
	
	private final JpaConnections jpaConnections;
	

	
	private static final Logger log = LoggerFactory.getLogger(DatafluxUploadServiceImpl.class);
	
	
	
	public List<String> readColumnNamesFromFile(MultipartFile uploadedStatenoFile,
			BindingResult dummyBindingResult, UserObject userObject){
		
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		List<String> columnNames = new ArrayList<>();
		
		try{
			
			
			
			if(uploadedStatenoFile.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no data to match";
				
			}else{
				workbook = new XSSFWorkbook(uploadedStatenoFile.getInputStream());
				
				worksheet = workbook.getSheetAt(0);
			
				XSSFRow row = worksheet.getRow(0);
				
				if(row == null) {
					isError = true;
					transactionMessage = "The file uploaded contains no data to match";
				}else {
					
					boolean hasColData = true; 
					
					int columnIndex = 0;
					
					while (hasColData) {
						
						if(row.getCell(columnIndex) != null) {
							String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
							
							columnNameInSheet = columnNameInSheet.replaceAll("\n", " ").trim();
							
							columnNames.add(columnNameInSheet);
							
						}else {
							hasColData = false;
						}
						columnIndex++;
						
					}
				}
				
				

				
			}
			
		
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while reading the columns names from the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "FFailed while reading the columns names from the file, please contact helpline";
			
			log.error("Failed to read the file.  {}",exception.getMessage());
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, dummyBindingResult);
		}
		
		
		return columnNames;

	}



	public boolean uploadDatafluxMatchingData(UserObject userObject, Map<String, String> data, MultipartFile fileData, BindingResult bindingResult) {
		boolean isError = false; 
		
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet = null;
		
		String transactionMessage = null;
		
		Map<String, Integer> columnIndexMap = new HashMap<>();
		
		List<Map<String, String>> dataMap = new ArrayList<>();
		
		try{
			
			
			
			if(fileData.isEmpty()){
				
				isError = true;
				transactionMessage = "The file uploaded contains no data to match";
				
			}else{
				workbook = new XSSFWorkbook(fileData.getInputStream());
				
				worksheet = workbook.getSheetAt(0);
				
				
				int rowIndex = 0;
			
				XSSFRow row = worksheet.getRow(rowIndex++);
				
				
					
				boolean hasColData = true; 
				
				int columnIndex = 0;
				
				while (hasColData) {
					
					if(row.getCell(columnIndex) != null) {
						String columnNameInSheet = row.getCell(columnIndex).getStringCellValue();
						
						columnNameInSheet = columnNameInSheet.replaceAll("\n", " ").trim();
						
						columnIndexMap.put(columnNameInSheet, columnIndex);
						
					}else {
						hasColData = false;
					}
					columnIndex++;
					
				}
				
				
				
				while (rowIndex <= worksheet.getLastRowNum()) {
					
					XSSFRow rowData = worksheet.getRow(rowIndex++);
					
					if(row == null){
						
						if(log.isDebugEnabled()){
							log.debug("Empty is row is there");
						}
						continue;
					}
					
					
					
					Map<String, String> rowDataMap = new HashMap<>();
					
					for (String excelColumnName : data.keySet()) {
						
						String dataFluxColumnName = data.get(excelColumnName);
						
						if(CommonUtil.isNotNullAndEmpty(dataFluxColumnName)) {
							
							Integer excelColumnIndex = columnIndexMap.get(excelColumnName);
							
							
							if(!(rowData.getCell(excelColumnIndex) == null || CommonUtil.isNullOrEmpty(rowData.getCell(excelColumnIndex).toString()))){
								
								if("DOCUMENT_ID".equals(dataFluxColumnName)) {
									
									if(CommonUtil.isNullOrEmpty(rowData.getCell(excelColumnIndex).getRawValue())) {
										break;
									}
									
									rowDataMap.put(dataFluxColumnName, rowData.getCell(excelColumnIndex).getRawValue().trim());
								}else {
									rowDataMap.put(dataFluxColumnName, rowData.getCell(excelColumnIndex).toString().trim());
								}
								
								
							}
							
							
							
							
						}
						
						
					}
					
					if(rowDataMap != null && rowDataMap.size() > 0) {
						dataMap.add(rowDataMap);
					}
					
				}
				
			}
			
			loadExcelDataToStage(dataMap);
			
		
			
		}catch(DataAccessException dataAccessException){
			isError = true;
			
			transactionMessage = "Failed while reading the columns names from the file, please contact helpline";
			
			log.error("Failed to read the file.   {}",dataAccessException.getMessage());
			
		}catch(Exception exception){
			isError = true;
			
			transactionMessage = "FFailed while reading the columns names from the file, please contact helpline";
			
			log.error("Failed to read the file.  {}",exception.getMessage());
			
		}finally{
			
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("IO EXCEPTION while closing the workbook   :  {}",e.getMessage());
				return false;
			}catch (Exception e) {
				log.error("EXCEPTION while closing the workbook   :  {}",e.getMessage());
				return false;
			}
		}
		
		if(isError){
			commonExceptionHandler.inspectDBTransacionResult(-1, transactionMessage, bindingResult);
			return false;
		}else {
			commonExceptionHandler.inspectDBTransacionResult(0, "Successfully inserted the data", bindingResult);
		}
		
		
		return true;
	}
	
	
	
	
	private void loadExcelDataToStage(List<Map<String, String>> dataMap) throws SQLException {
		
		Statement sqlStatement = null;
		ResultSet sqlResultSet = null;
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		Connection oracleConnection = null;
		
		try {
			
			oracleConnection = jpaConnections.appConnection();
			
			if(oracleConnection == null) {
				log.error("Oracle Datasource connection not available. ");
				throw new DataRetrievalFailureException("Oracle Datasource connection not available");
			}
			
			String DATAFLUX_MATCH_COLUMNS = EnvironmentProperties.envProperties.getProperty("DATAFLUX_MATCH_SQL_COLUMNS");
			
			
			StringBuilder sqlQuery = new StringBuilder("insert into NYEHMS_SASDB_OWNER.ER_DAILY_DOC_STAGE(");
			sqlQuery.append(DATAFLUX_MATCH_COLUMNS);
			sqlQuery.append(") values(");
			
			sqlQuery.append(EnvironmentProperties.envProperties.getProperty("DATAFLUX_MATCH_SQL_COLUMNS_VALUES"));
			
			sqlQuery.append(")");
			
			
			
			preparedStatement = oracleConnection.prepareStatement(sqlQuery.toString());
			
			
			int rowIndex = 0;
			for (Map<String, String> map : dataMap) {
				rowIndex++;
				
				
				
				String DATAFLUX_MATCH_SQL_MAPPING = EnvironmentProperties.envProperties.getProperty("DATAFLUX_MATCH_SQL_MAPPING");
				
				int columnIndex = 1;
				for (String columnName : DATAFLUX_MATCH_SQL_MAPPING.split(",")) {
					preparedStatement.setString(columnIndex++, map.get(columnName));
				}
				preparedStatement.setString(columnIndex++, "NYEHMS_DOC");
				
				preparedStatement.addBatch();
				
				if (rowIndex % 5000 == 0) {
					preparedStatement.executeBatch();
				}
				
			}
			
			preparedStatement.executeBatch();
			
			if(rowIndex == 0) {
				log.error("No Data to match");
				throw new DataRetrievalFailureException("No Data to match");
			}
			
			
			
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
	
	

}