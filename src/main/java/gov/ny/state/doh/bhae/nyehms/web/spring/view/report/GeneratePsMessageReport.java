package gov.ny.state.doh.bhae.nyehms.web.spring.view.report;

import java.math.BigDecimal;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.util.DateUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;
import org.springframework.stereotype.Component;


@Component("psMessageReportSpreadsheet")
public class GeneratePsMessageReport extends AbstractExcelReportView{


    	
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
	    		
	    setAttachmentHeaders(response, fileName);
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(18);
		
		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.LEFT);
		setBorders(commonStyles, BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = createCalibriFont(workbook, 11, false);
        
        commonStyles.setFont(commonFont);
        
        
        CellStyle countyStyles = workbook.createCellStyle();
		
        countyStyles.setAlignment(HorizontalAlignment.LEFT);
        setBorders(countyStyles, BorderStyle.THIN);
        countyStyles.setWrapText(true);
        
        Font countyFont = createCalibriFont(workbook, 11, true);
        
        countyStyles.setFont(commonFont);
        
        
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
