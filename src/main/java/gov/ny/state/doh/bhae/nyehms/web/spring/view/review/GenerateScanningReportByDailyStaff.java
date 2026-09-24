package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class GenerateScanningReportByDailyStaff extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date now = new Date();
		String timestamp = sdf.format(now);		
		String fileName = "SCANNING_REPORTS_REVIEWED_BY_STAFF_" + timestamp + ".XLS";
		
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, BigInteger>>> userDetails = (Map<String, Map<String, Map<String, BigInteger>>>) model.get("generateScanningReport");
		@SuppressWarnings("unchecked")
		Map<String, Map<String, BigInteger>> totalCountByUser = (Map<String, Map<String, BigInteger>>) model.get("totalCountByUser");
		
		String fromDate = (String)model.get("fromDate");
		String toDate = (String)model.get("toDate");
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		Sheet sheet = workbook.createSheet("DAILY SCANNING REVIEWED BY STAFF");
		sheet.setDefaultColumnWidth(11);
		sheet.setColumnWidth(0, 7000);
		sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, totalCountByUser.size()+1)); //Merged region is for title row
		
		CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM); 
        style.setAlignment(HorizontalAlignment.CENTER);
        
        Font font= workbook.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("Calibri");
        style.setFont(font);
        style.setWrapText(true);
        
        CellStyle boldStyle = workbook.createCellStyle();        
        Font boldFont= workbook.createFont();        
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);        
        boldStyle.setBorderTop(BorderStyle.MEDIUM);
        boldStyle.setBorderBottom(BorderStyle.MEDIUM);
        boldStyle.setBorderRight(BorderStyle.MEDIUM);
        boldStyle.setBorderLeft(BorderStyle.MEDIUM);
        boldStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        boldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        //Create title row in the merged region
        Row header0 = sheet.createRow(0);        
        header0.setHeight((short)400);        
        header0.createCell(0).setCellValue("Daily Count of Scanning Reviewed by Staff \n from "+fromDate+" through "+toDate);
        header0.getCell(0).setCellStyle(style);  
        
        font.setFontHeightInPoints((short)11);        
        style.setFont(font);        
        Row header2 = sheet.createRow(3);        
        int columnIndex = 0;       
        header2.createCell(columnIndex).setCellValue("Staff ID");
        header2.getCell(columnIndex++).setCellStyle(style);
        
        List<String> userListNames  = new ArrayList<String>();
       if(totalCountByUser != null) userListNames.addAll(totalCountByUser.keySet());
      //Add staff names
        for (String workerName : userListNames) {
        	
        	//sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+1));        	
        	header2.createCell(columnIndex).setCellValue(workerName);
            header2.getCell(columnIndex++).setCellStyle(style);
          //  header2.createCell(columnIndex++).setCellStyle(style);
		}
        
        //Add total columns
       // sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+1));       
        header2.createCell(columnIndex).setCellValue("Total");
        header2.getCell(columnIndex++).setCellStyle(style);
       // header2.createCell(columnIndex++).setCellStyle(style);
        
        Row header3 = sheet.createRow(4);
        columnIndex = 0;        
        header3.createCell(columnIndex).setCellValue("Date");
        header3.getCell(columnIndex++).setCellStyle(style); 
        
        //add daily count
        int index = 5;
        
        for(String createDate : userDetails.keySet()) {
        	Row aRow = sheet.createRow(index++);
        	Map<String, Map<String, BigInteger>> dataMapByUsers = userDetails.get(createDate);
        	columnIndex = 0;
        	aRow.createCell(columnIndex++).setCellValue(createDate);
        	BigInteger totalCountByDay = BigInteger.ZERO;
        	
        	if(dataMapByUsers != null) {
        		
        		for(String workerName : userListNames) {
        			Map<String,BigInteger> dataMapCount = dataMapByUsers.get(workerName);
        			if(dataMapCount != null) {
        				BigInteger count = dataMapCount.get("count");
        				
        				if(count == null) {
        					aRow.createCell(columnIndex++).setCellValue("");
        				}else {
        					aRow.createCell(columnIndex++).setCellValue(count.intValue());
        					totalCountByDay = totalCountByDay.add(count);
        				}
        			}else {
						columnIndex = columnIndex + 1;
					}
        			
        		}
        		
        	}else {
    			columnIndex = columnIndex + userListNames.size()+1;
    		}
        	
        	if (totalCountByDay.intValue() >= 0) {
				aRow.createCell(columnIndex++).setCellValue(totalCountByDay.intValue());
			} else {
				aRow.createCell(columnIndex++).setCellValue(0);
			}
        } 
        
     // Total Report
    	columnIndex = 0;
		Row aRow = sheet.createRow(index++);
		aRow.createCell(columnIndex).setCellValue("Total For Date Range");
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
		BigInteger totalCount = BigInteger.ZERO;
		
		for(String workerName : userListNames) {
			Map<String, BigInteger> totalCountMap = totalCountByUser.get(workerName);
			
			if(totalCountMap != null) {
				BigInteger totalCountByUsr =  totalCountMap.get("totalCount");
				
				if(totalCountByUsr == null) {
					aRow.createCell(columnIndex).setCellValue(0);
				} else {
					aRow.createCell(columnIndex).setCellValue(totalCountByUsr.intValue());
					totalCount = totalCount.add(totalCountByUsr);
				}
				aRow.getCell(columnIndex++).setCellStyle(boldStyle);
			}else {
				aRow.createCell(columnIndex).setCellValue(0);
				aRow.createCell(columnIndex++).setCellStyle(boldStyle);
			}
			
		}
		
		if(totalCount.intValue() >= 0) {
			aRow.createCell(columnIndex).setCellValue(totalCount.intValue());
		}else {
			aRow.createCell(columnIndex).setCellValue(0);
		}
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);

	}

}
