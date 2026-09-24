package gov.ny.state.doh.bhae.nyehms.web.spring.view.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;


public class GenerateRiaActivityReportSpreadsheet extends AbstractXlsView{

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		
    	List<Map<String, String>> riaActivityRecords =  (List<Map<String, String>>)model.get("riaActivityRecords");;
    	
    	StringBuilder fileName = new StringBuilder(EnvironmentProperties.envProperties.getProperty("RIA_ACTIVITY_REPORT_FILE_NAME"));
    	String fromDate =  (String)model.get("fromDate");;
		String toDate =  (String)model.get("toDate");;
	    
	    fileName.append(".xls");
	    
	    String[] PS_OOC_EXPS_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("RIA_ACTIVITY_REPORT_SHEET_COLUMNS").split(",");
	    
    	fileName.append(fromDate).append(" to ").append(toDate);

	    fileName.append(".xls");
	    
	    		
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(21);
		
		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.LEFT);
		commonStyles.setBorderBottom(BorderStyle.THIN);
		commonStyles.setBorderTop(BorderStyle.THIN);
		commonStyles.setBorderLeft(BorderStyle.THIN);
        commonStyles.setBorderRight(BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = workbook.createFont();
        commonFont.setFontHeight((short)(11*20));
        commonFont.setFontName("Calibri");
        
        commonStyles.setFont(commonFont);
        
        
        
        CellStyle headerStyle = workbook.createCellStyle();
		
		headerStyle.setAlignment(HorizontalAlignment.LEFT);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);
    
        
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short)(12*20));
        font.setFontName("Calibri");
        
        headerStyle.setFont(font);//set it to bold
        
        
        
        int rowIndex = 0;
        
        
        Row header = sheet.createRow(rowIndex++);
        
        
        int columnCount = 0;
        
    	for (String columnName : PS_OOC_EXPS_SHEET_COLUMNS) {
    		header.createCell(columnCount).setCellValue(columnName);
        	header.getCell(columnCount++).setCellStyle(headerStyle);
		}
        
        for (Map<String, String> activityData : riaActivityRecords) {
        	columnCount = 0;
        	Row row = sheet.createRow(rowIndex++);
        	
        	row.createCell(columnCount).setCellValue(activityData.get("CURR_NYEHMS_STATENO"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(activityData.get("LEGACY_UID"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(activityData.get("USER_NAME"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(activityData.get("LAST_UPDATED_DATE"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	row.createCell(columnCount).setCellValue(activityData.get("USER_ACTIVITY_COUNT"));
        	row.getCell(columnCount++).setCellStyle(commonStyles);
        	
        	
		}
        

	 }	
	
}
