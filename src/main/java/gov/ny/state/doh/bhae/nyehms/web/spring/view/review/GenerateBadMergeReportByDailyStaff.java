package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;

public class GenerateBadMergeReportByDailyStaff extends AbstractExcelReportView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date now = new Date();
		String timestamp = sdf.format(now);		
		String fileName = "BAD_MERGE_REPORTS_REVIEWEWD_BY_STAFF_" + timestamp + ".XLS";
		
		Map<String, Map<String, Map<String, Map<String, Integer>>>> dataMapByWeeks = (Map<String, Map<String, Map<String, Map<String, Integer>>>>)model.get("badMergeReportsByDailyStaff");		
		Map<String, Map<String, Map<String, Integer>>> weeklyCountByWeeks = (Map<String, Map<String, Map<String, Integer>>>)model.get("weeklyCountByWeeks");
		Map<String, Map<String, Integer>> totalCountByUsers = (Map<String, Map<String, Integer>>)model.get("totalCountByUsers");
		List<String> userNamesList = (List<String>)model.get("userNamesList");
		
		String fromDate = (String)model.get("fromDate");
		String toDate = (String)model.get("toDate");
				
		setAttachmentHeaders(response, fileName);
		
		Sheet sheet = workbook.createSheet("DAILY BAD MERGES REVIEWED BY STAFF");
		sheet.setDefaultColumnWidth(11);
		sheet.setColumnWidth(0, 7000);
		sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, userNamesList.size()*2 + 2)); //Merged region is for title row
		
		CellStyle style = workbook.createCellStyle();
        setSolidFill(style, IndexedColors.LIGHT_GREEN);
        setBorders(style, BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        Font font = createCalibriFont(workbook, 14, false);
        style.setFont(font);
        style.setWrapText(true);
        
        CellStyle boldStyle = workbook.createCellStyle();        
        Font boldFont= workbook.createFont();        
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);        
        setBorders(boldStyle, BorderStyle.MEDIUM);
        setSolidFill(boldStyle, IndexedColors.LIGHT_TURQUOISE);
        
        //Create title row in the merged region
        Row header0 = sheet.createRow(0);        
        header0.setHeight((short)400);        
        header0.createCell(0).setCellValue("Daily Count of Bad Merges Reviewed by Staff \n from "+fromDate+" through "+toDate);
        header0.getCell(0).setCellStyle(style);  
        
        font.setFontHeightInPoints((short)11);        
        style.setFont(font);        
        Row header2 = sheet.createRow(3);        
        int columnIndex = 0;       
        header2.createCell(columnIndex).setCellValue("Staff ID");
        header2.getCell(columnIndex++).setCellStyle(style);
        
        //Add staff names
        for (String workerName : userNamesList) {       	
        	sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+1));        	
        	header2.createCell(columnIndex).setCellValue(workerName);
            header2.getCell(columnIndex++).setCellStyle(style);
            header2.createCell(columnIndex++).setCellStyle(style);
		}
        
        //Add total columns
        sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+1));       
        header2.createCell(columnIndex).setCellValue("Total");
        header2.getCell(columnIndex++).setCellStyle(style);
        header2.createCell(columnIndex++).setCellStyle(style);
        
        //Add subtitle row
        Row header3 = sheet.createRow(4);
        columnIndex = 0;        
        header3.createCell(columnIndex).setCellValue("Date");
        header3.getCell(columnIndex++).setCellStyle(style);        
        for (String workerName : userNamesList) {       	
        	header3.createCell(columnIndex).setCellValue("Unmerged");
        	header3.getCell(columnIndex++).setCellStyle(style);       	
        	header3.createCell(columnIndex).setCellValue("Rejected");
        	header3.getCell(columnIndex++).setCellStyle(style);
		}     	
        header3.createCell(columnIndex).setCellValue("Unmerged");
    	header3.getCell(columnIndex++).setCellStyle(style);    	
    	header3.createCell(columnIndex).setCellValue("Rejected");
    	header3.getCell(columnIndex++).setCellStyle(style);

    	//Add daily, weekly, and total counts
    	int index = 5;
        for (String weekName : dataMapByWeeks.keySet()) {
        	Map<String, Map<String, Map<String, Integer>>> dataMapByDates = dataMapByWeeks.get(weekName);

        	//Daily Report
        	for (String createDate : dataMapByDates.keySet()) {      		
				Row aRow = sheet.createRow(index++);				
				Map<String, Map<String, Integer>> dataMapByUsers = dataMapByDates.get(createDate);       		
				columnIndex = 0;       		
				aRow.createCell(columnIndex++).setCellValue(createDate);
				Integer totalMergedCountByDay = 0;
				Integer totalRejectedCountByDay = 0;
		        
				if (null != dataMapByUsers) {
					for (String workerName : userNamesList) {							
						Map<String, Integer> dataMapByStatus = dataMapByUsers.get(workerName);					
						if(dataMapByStatus != null){
							Integer mergedCount = dataMapByStatus.get("ACCEPT");
							Integer rejectedCount = dataMapByStatus.get("REJECT");
							if( mergedCount == null){
								aRow.createCell(columnIndex++).setCellValue("");
							} else {
								aRow.createCell(columnIndex++).setCellValue(mergedCount);	
								totalMergedCountByDay = totalMergedCountByDay + mergedCount;
							}					
							if(rejectedCount == null){
								aRow.createCell(columnIndex++).setCellValue("");
							} else {
								aRow.createCell(columnIndex++).setCellValue(rejectedCount);					
								totalRejectedCountByDay = totalRejectedCountByDay + rejectedCount;
							}
						} else {
							columnIndex = columnIndex+2;
						}						
					}
				} else {
					columnIndex = columnIndex + userNamesList.size()*2;
				}
    		  
				if(totalMergedCountByDay >= 0){
					aRow.createCell(columnIndex++).setCellValue(totalMergedCountByDay);
				}else{
					aRow.createCell(columnIndex++).setCellValue(0);
				}
				if(totalRejectedCountByDay >= 0){
					aRow.createCell(columnIndex++).setCellValue(totalRejectedCountByDay);
				}else{
					aRow.createCell(columnIndex++).setCellValue(0);
				}
			}
        	
        	//Weekly Report
        	columnIndex = 0;        	
        	Row aRow = sheet.createRow(index++);    		
    		aRow.createCell(columnIndex).setCellValue("Weekly Total");   		
    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);      	
        	Map<String, Map<String, Integer>> weeklyCountByUsers = weeklyCountByWeeks.get(weekName);
	        Integer totalMergedCountByWeek = 0;
	        Integer totalRejectedCountByWeek = 0;
	        
	        if (null != weeklyCountByUsers) {
	        	for (String workerName : userNamesList) {   			  
	  			  	Map<String, Integer> weeklyReportByStatus = weeklyCountByUsers.get(workerName);  			  	
	  			  	if(weeklyReportByStatus != null){
				  		Integer mergedCount = weeklyReportByStatus.get("ACCEPT");
				  		Integer rejectedCount = weeklyReportByStatus.get("REJECT");
				  		
	  			  		if(mergedCount == null){
	  			  			aRow.createCell(columnIndex).setCellValue("");			  			
	  			  		}else{
	  			  			aRow.createCell(columnIndex).setCellValue(mergedCount);			  			
	  			  			totalMergedCountByWeek = totalMergedCountByWeek + mergedCount;
	  			  		}
	  			  		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
		  	        	if(rejectedCount == null){
		  	        		aRow.createCell(columnIndex).setCellValue("");	  	        		
	  			  		}else{
	  			  			aRow.createCell(columnIndex).setCellValue(rejectedCount);  			  			
	  			  		totalRejectedCountByWeek = totalRejectedCountByWeek + rejectedCount;
	  			  		}
		  	        	aRow.getCell(columnIndex++).setCellStyle(boldStyle);	  		
	  			  	}else{
	  			  		//aRow.createCell(columnIndex).setCellValue("0");
	  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
	  			  		//aRow.createCell(columnIndex).setCellValue("0");			
	  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
	  			  	}
	        	}
	        } else {
	        	//columnIndex = columnIndex + userNamesList.size()*2;
	        	for (String workerName : userNamesList) {
	        		aRow.createCell(columnIndex++).setCellStyle(boldStyle);			
  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
	        	}
	        }
        	
        	if(totalMergedCountByWeek >= 0){
        		aRow.createCell(columnIndex).setCellValue(totalMergedCountByWeek);       		        		
        	}else{
        		aRow.createCell(columnIndex).setCellValue("");
        	}
        	aRow.getCell(columnIndex++).setCellStyle(boldStyle);
        	
        	if(totalRejectedCountByWeek >= 0){
        		aRow.createCell(columnIndex).setCellValue(totalRejectedCountByWeek);       		
        	}else{
        		aRow.createCell(columnIndex).setCellValue("");
        	}
        	aRow.getCell(columnIndex++).setCellStyle(boldStyle);
		}

        //Total Report
        columnIndex = 0;        
        Row aRow = sheet.createRow(index++);		
		aRow.createCell(columnIndex).setCellValue("Total For Date Range");
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
        Integer totalMergedCount = 0;
        Integer totalRejectedCount = 0;   	
    	
    	for (String workerName : userNamesList) {			  
		  	Map<String, Integer> totalCountByStatus = totalCountByUsers.get(workerName);	    	
		  	if(totalCountByStatus != null){
		  		Integer mergedCount = totalCountByStatus.get("ACCEPT");
		  		Integer rejectedCount = totalCountByStatus.get("REJECT");
		  		
		  		if(mergedCount == null){
		  			aRow.createCell(columnIndex).setCellValue(0);		  			
		  		}else{
		  			aRow.createCell(columnIndex).setCellValue(mergedCount);		  			
		  			totalMergedCount = totalMergedCount + mergedCount;
		  		}
		  		aRow.getCell(columnIndex++).setCellStyle(boldStyle);

	        	if(rejectedCount == null){
	        		aRow.createCell(columnIndex).setCellValue(0);	        		
		  		}else{
		  			aRow.createCell(columnIndex).setCellValue(rejectedCount);
		  			totalRejectedCount = totalRejectedCount + rejectedCount;
		  		}
	        	aRow.getCell(columnIndex++).setCellStyle(boldStyle);		  		
		  	}else{
		  		aRow.createCell(columnIndex).setCellValue(0);	
		  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
		  		aRow.createCell(columnIndex).setCellValue(0);	
		  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
		  	}
    	}
    	
    	if(totalMergedCount >= 0){
    		aRow.createCell(columnIndex).setCellValue(totalMergedCount);   		
    	}else{
    		aRow.createCell(columnIndex).setCellValue(0);
    	}
    	aRow.getCell(columnIndex++).setCellStyle(boldStyle);
    	
    	if(totalRejectedCount >= 0){
    		aRow.createCell(columnIndex).setCellValue(totalRejectedCount);    		
    	}else{
    		aRow.createCell(columnIndex).setCellValue(0);
    	}
    	aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	}
}