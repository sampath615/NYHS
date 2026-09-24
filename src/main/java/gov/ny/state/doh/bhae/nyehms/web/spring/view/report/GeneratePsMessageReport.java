package gov.ny.state.doh.bhae.nyehms.web.spring.view.report;

import java.math.BigDecimal;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;




public class GeneratePsMessageReport extends AbstractXlsView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		String reportType =  (String)model.get("reportType");;
		String fromDate =  (String)model.get("fromDate");;
		String toDate =  (String)model.get("toDate");;
    	Map<String, Object> countyTotalMap =  (Map<String, Object>)model.get("countyTotalMap");;
    	Map<String, Object> metricTotalMap =  (Map<String, Object>)model.get("metricTotalMap");;
    	Map<String, Map<String, Object>> weekTotalByCountyMap =  (Map<String, Map<String, Object>>)model.get("weekTotalByCountyMap");;
    	List<String> countyList =  (List<String>)model.get("countyList");;
	    
    	
    	
	    
	    StringBuilder fileName = new StringBuilder("New 900 Message ").append(reportType).append(" Report ");
	    
	    if("Monthly".equals(reportType)) {
	    	fileName.append(DateUtil.convertStringToTimeStampInCustomFormat(fromDate, "MM/YYYY")).append(" to ").append(DateUtil.convertStringToTimeStampInCustomFormat(toDate, "MM/YYYY"));
	    }else {
	    	fileName.append(fromDate).append(" to ").append(toDate);
	    }

	    fileName.append(".xls");
	    		
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(18);
		
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
        
        
        CellStyle countyStyles = workbook.createCellStyle();
		
        countyStyles.setAlignment(HorizontalAlignment.LEFT);
        countyStyles.setBorderBottom(BorderStyle.THIN);
        countyStyles.setBorderTop(BorderStyle.THIN);
        countyStyles.setBorderLeft(BorderStyle.THIN);
        countyStyles.setBorderRight(BorderStyle.THIN);
        countyStyles.setWrapText(true);
        
        Font countyFont = workbook.createFont();
        countyFont.setFontHeight((short)(11*20));
        countyFont.setFontName("Calibri");
        countyFont.setBold(true);
        
        countyStyles.setFont(commonFont);
        
        
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
        header.createCell(columnCount).setCellValue("County Name");
    	header.getCell(columnCount++).setCellStyle(headerStyle);
    	
    	for (String metricName : metricTotalMap.keySet()) {
    		header.createCell(columnCount).setCellValue(metricName);
        	header.getCell(columnCount++).setCellStyle(headerStyle);
		}
        
    	header.createCell(columnCount).setCellValue("Total");
    	header.getCell(columnCount++).setCellStyle(headerStyle);
        
        
        
        
        for (String countyName : countyList) {
        	columnCount = 0;
        	Row row = sheet.createRow(rowIndex++);
        	
        	row.createCell(columnCount).setCellValue(countyName);
        	row.getCell(columnCount++).setCellStyle(countyStyles);
        	
        	Map<String, Object> countyMetricMap = weekTotalByCountyMap.get(countyName);
        	
        	if(countyMetricMap != null) {
        		for (String metricName : metricTotalMap.keySet()) {
        			
        			
        			int countyMetricCount = 0; 
        			
        			if(countyMetricMap.get(metricName) != null) {
        				countyMetricCount = ((BigDecimal)countyMetricMap.get(metricName)).intValue();
        			}
        				

            		row.createCell(columnCount).setCellValue(countyMetricCount);
            		row.getCell(columnCount++).setCellStyle(commonStyles);
        		}
            	
        		int countyTotalCount = ((BigDecimal)countyTotalMap.get(countyName)).intValue();
        		
            	row.createCell(columnCount).setCellValue(countyTotalCount);
            	row.getCell(columnCount++).setCellStyle(countyStyles);
        	}else {
        		
        		for (String metricName : metricTotalMap.keySet()) {
            		row.createCell(columnCount).setCellValue(0);
            		row.getCell(columnCount++).setCellStyle(commonStyles);
        		}
        		
        		row.createCell(columnCount).setCellValue(0);
            	row.getCell(columnCount++).setCellStyle(countyStyles);
        		
        	}
        	
        	
		}
        

	 }
	
	
}
