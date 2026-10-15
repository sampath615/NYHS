package gov.ny.state.doh.bhae.nyehms.web.spring.view.report;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;
import org.springframework.stereotype.Component;


@Component("ndvlReportSpreadsheet")
public class GenerateNdvlReportSpreadsheet extends AbstractExcelReportView{

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		
    	List<Map<String, String>> ndvlReportDataList =  (List<Map<String, String>>)model.get("ndvlReportData");;
    	
    	StringBuilder fileName = new StringBuilder(EnvironmentProperties.envProperties.getProperty("NDVL_REPORT_FILE_NAME"));
	    
	    fileName.append(".xls");
	    
	    String[] PS_OOC_EXPS_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("NDVL_REPORT_SHEET_COLUMNS").split(",");
	    		
	    setAttachmentHeaders(response, fileName);
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(18);
		
		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.LEFT);
		setBorders(commonStyles, BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = createCalibriFont(workbook, 11, false);
        
        commonStyles.setFont(commonFont);
        
        
        
        CellStyle headerStyle = workbook.createCellStyle();
		
		headerStyle.setAlignment(HorizontalAlignment.LEFT);
		setBorders(headerStyle, BorderStyle.THIN);
		
		setSolidFill(headerStyle, IndexedColors.BLUE_GREY);
        headerStyle.setWrapText(true);
    
        
        Font font = createCalibriFont(workbook, 12, true);
        
        headerStyle.setFont(font);//set it to bold
        
        
        
        int rowIndex = 0;
        
        
        Row header = sheet.createRow(rowIndex++);
        
        
        int columnCount = 0;
        
    	for (String columnName : PS_OOC_EXPS_SHEET_COLUMNS) {
    		header.createCell(columnCount).setCellValue(columnName);
        	header.getCell(columnCount++).setCellStyle(headerStyle);
		}
        
        for (Map<String, String> ndvlData : ndvlReportDataList) {
        	columnCount = 0;
        	Row row = sheet.createRow(rowIndex++);
        	
        	row.createCell(columnCount).setCellValue(ndvlData.get("stateno"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(ndvlData.get("documentId"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(ndvlData.get("COLLECTION_DATE"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);        	
        	
		}
        

	 }	
	
}
