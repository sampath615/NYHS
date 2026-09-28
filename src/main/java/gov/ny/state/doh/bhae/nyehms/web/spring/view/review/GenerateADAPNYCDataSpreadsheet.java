package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;



public class GenerateADAPNYCDataSpreadsheet extends AbstractExcelReportView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
		List<Map<String, String>> adapNYCData =  (List<Map<String, String>>)model.get("adapNYCData");;
	    
		String filename = (String) model.get("filename");
	    
	    StringBuilder fileName = new StringBuilder(filename);
	    
	   	
	    
	    if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
			
			fileName.append(".XLS");
			
		}
	    		
	    setAttachmentHeaders(response, fileName);
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(11);
		
		

		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.CENTER);
		setBorders(commonStyles, BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = createCalibriFont(workbook, 11, false);
        
        commonStyles.setFont(commonFont);//set it to bold
        
        
        String[] ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS").split(",");
	    String[] ADAP_NYC_DATA_DB_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_DATA_DB_COLUMNS").split(",");
	    
	    
		
	   
	        int rowIndex = 0;
	      
		
		
		
		//HSSFPalette palette = workbook.getCustomPalette();
		
		
		
	    
	    
		
    
        
//        
//        style1.setFont(font);//set it to bold
        
        
        
        
        
        
        
        
        

		Row header = sheet.createRow(rowIndex++);
		
        int columnCount = 0;
        for (String _coulmnName : ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS) {
		
        	header.createCell(columnCount++).setCellValue(_coulmnName);
        	//header.getCell(columnCount++).setCellStyle(style4);
        	
            
		}
        
        
        
        
        
        
        for (Map<String, String> rowData : adapNYCData) {
        	
        	columnCount = 0;
        	
        	Row aRow = sheet.createRow(rowIndex++);
    		
        	for (String _coulmnName : ADAP_NYC_DATA_DB_COLUMNS) {
    		
        		aRow.createCell(columnCount).setCellValue(rowData.get(_coulmnName));
        		aRow.getCell(columnCount++).setCellStyle(commonStyles);
        	}
            
            
		}
        

	 }
	
	
}
