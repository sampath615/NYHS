package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.Map;
import java.util.Set;

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

public class GenerateLabErrorReportByStatus extends AbstractExcelReportView{

	
	/**
	 * 
	 */
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception
	{
		 Map<String, Map<String, Map<String, Map<String, Integer>>>> errorReportsByStatus =  (Map<String, Map<String, Map<String, Map<String, Integer>>>>)model.get("errorReportsByStatus");
	        
	        Map<String, Map<String, Map<String, Integer>>> statusCountByUserByWeek =  (Map<String, Map<String, Map<String, Integer>>>)model.get("weeklyTotalByUser");
	        
	        Set<String> workerNames =  (Set<String>)model.get("workerNames");
	        
	        Map<String, Map<String, Integer>> totalStatusCountByUser =  (Map<String, Map<String, Integer>>)model.get("totalStatusCountByUser");
	        
	        
	        
			
			String fromDate = (String) model.get("fromDate");
			
			String toDate = (String) model.get("toDate");
			
			String fileName = "LAFT_Reprise_Delete_By_LAFT.XLS";
			
			setAttachmentHeaders(response, fileName);
			Sheet sheet = workbook.createSheet("REPORT BY SOLUTION");
			
			sheet.setColumnWidth(0, 13290);
			
			sheet.addMergedRegion(new CellRangeAddress(0,2,0,(workerNames.size()*3)+3));
			
			CellStyle style = workbook.createCellStyle();

			
	        setSolidFill(style, IndexedColors.LIGHT_GREEN);
	        
	        setBorders(style, BorderStyle.MEDIUM);
	        
	        style.setAlignment(HorizontalAlignment.CENTER);
	        
	        Font font = createCalibriFont(workbook, 14, false);
	        //font.setBold(true);
	        
	        style.setFont(font);
	        
	        style.setWrapText(true);
	        
	        
	        
	        CellStyle boldStyle = workbook.createCellStyle();
	        
	        Font boldFont= workbook.createFont();
	        
	        boldFont.setBold(true);

	        boldStyle.setFont(boldFont);
	        
	        setBorders(boldStyle, BorderStyle.MEDIUM);
	        
	        //boldStyle.setFillForegroundColor(Color.LEMON_CHIFFON.index);
	        setSolidFill(boldStyle, IndexedColors.LIGHT_TURQUOISE);
	        
	        
	        
	        
	        
	        Row header0 = sheet.createRow(0);
	        
	        header0.setHeight((short)400);
	        
	        header0.createCell(0).setCellValue("LAFT ERROR RESOLUTION \n Daily Count of Laboratory Reports in ERROR Deleted, Reprised or Edited \n FROM "+fromDate+" THROUGH "+toDate);
	        header0.getCell(0).setCellStyle(style);
	        

	        Row header1 = sheet.createRow(1);
	        
	        header1.setHeight((short)400);
	        
	        font.setFontHeightInPoints((short)11);
	        
	        style.setFont(font);
	        
	        Row header2 = sheet.createRow(3);
	        
	        int columnIndex = 0;
	        
	        header2.createCell(columnIndex).setCellValue("LAFT Staff");
	        header2.getCell(columnIndex++).setCellStyle(style);
	        
	        for (String workerName : workerNames) {
	        	
	        	sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+2));
	        	
	        	header2.createCell(columnIndex).setCellValue(workerName);
	            header2.getCell(columnIndex++).setCellStyle(style);
	            header2.createCell(columnIndex++).setCellStyle(style);
	            header2.createCell(columnIndex++).setCellStyle(style);
	        	
				
			}
	        
	        
	        sheet.addMergedRegion(new CellRangeAddress(3,3,columnIndex,columnIndex+2));
	        
	        header2.createCell(columnIndex).setCellValue("Total");
	        header2.getCell(columnIndex++).setCellStyle(style);
	        header2.createCell(columnIndex++).setCellStyle(style);
	        header2.createCell(columnIndex++).setCellStyle(style);
	        
	        
	        Row header3 = sheet.createRow(4);
	        
	        columnIndex = 0;
	        
	        header3.createCell(columnIndex).setCellValue("Date");
	        header3.getCell(columnIndex++).setCellStyle(style);
	        
	        for (String workerName : workerNames) {
	        	
	        	header3.createCell(columnIndex).setCellValue("Deleted");
	        	header3.getCell(columnIndex++).setCellStyle(style);
	        	
	        	header3.createCell(columnIndex).setCellValue("Reprised");
	        	header3.getCell(columnIndex++).setCellStyle(style);
	        	
	        	header3.createCell(columnIndex).setCellValue("Edited");
	        	header3.getCell(columnIndex++).setCellStyle(style);
			}
	     	
	        header3.createCell(columnIndex).setCellValue("Deleted");
	    	header3.getCell(columnIndex++).setCellStyle(style);
	    	
	    	header3.createCell(columnIndex).setCellValue("Reprised");
	    	header3.getCell(columnIndex++).setCellStyle(style);
	    	
	    	header3.createCell(columnIndex).setCellValue("Edited");
	    	header3.getCell(columnIndex++).setCellStyle(style);
	        
	        
	        int index = 5;
	        for (String weekName : errorReportsByStatus.keySet()) {
				
	        	
	        	
	        	
	        	Map<String, Map<String, Map<String, Integer>>> errorReportsByWeek = errorReportsByStatus.get(weekName);
	        	
	        	
	        	//Daily Report
	        	
	        	for (String createDate : errorReportsByWeek.keySet()) {
	        		
	        		Row aRow = sheet.createRow(index++);
					
	        		Map<String, Map<String, Integer>> errorReportsByDate = errorReportsByWeek.get(createDate);
	        		
	        		columnIndex = 0;
	        		
	        		aRow.createCell(columnIndex++).setCellValue(createDate);
	        		

	        		Integer totalReprisedCountByDay = 0;
	        		Integer totalDeletedCountByDay = 0;
	        		Integer totalEditedCountByDay = 0;
			        
	        		  for (String workerName : workerNames) {
	        			  
	        			  	Map<String, Integer> errorReportStatus = errorReportsByDate.get(workerName);
	        			  	
	        			  	if(errorReportStatus != null){
	        	        	
	        			  		
	        			  		Integer deleteCount = errorReportStatus.get("DELETE");
	        			  		Integer repriseCount = errorReportStatus.get("REPRISE");
	        			  		Integer editCount = errorReportStatus.get("EDIT");
	        			  		
	        			  		
	        			  		if( deleteCount == null){
	    	  			  			aRow.createCell(columnIndex++).setCellValue("");
	    	  			  		}else{
	    	  			  			aRow.createCell(columnIndex++).setCellValue(deleteCount);
	    	  			  			
	    	  			  			totalDeletedCountByDay = totalDeletedCountByDay+deleteCount;
	    	  			  		}
	    		  	        	
	    		  	        	if(repriseCount == null){
	    		  	        		aRow.createCell(columnIndex++).setCellValue("");
	    	  			  		}else{
	    	  			  			aRow.createCell(columnIndex++).setCellValue(repriseCount);
	    	  			  		
	    	  			  			totalReprisedCountByDay = totalReprisedCountByDay+repriseCount;
	    	  			  		}
	    		  	        	
	    		  	        	if(editCount == null){
	    		  	        		aRow.createCell(columnIndex++).setCellValue("");
	    	  			  		}else{
	    	  			  			aRow.createCell(columnIndex++).setCellValue(editCount);
	    	  			  		
	    	  			  		totalEditedCountByDay = totalEditedCountByDay+editCount;
	    	  			  		}
	    		  	        	
	    		  	        	
	    		  	        	
	        			  		
	    		  	        	

	        			  	}else{
	        			  		columnIndex = columnIndex+3;
	        			  	}
	        		 }
	        		  
	        		  if(totalDeletedCountByDay > 0){
	        			  aRow.createCell(columnIndex++).setCellValue(totalDeletedCountByDay);
	        		  }else{
	        			  aRow.createCell(columnIndex++).setCellValue("");
	        		  }
	        		
	        		

	        		  if(totalReprisedCountByDay > 0){
	        			  aRow.createCell(columnIndex++).setCellValue(totalReprisedCountByDay);
	        		  }else{
	        			  aRow.createCell(columnIndex++).setCellValue("");
	        		  }
	        		  
	        		  if(totalEditedCountByDay > 0){
	        			  aRow.createCell(columnIndex++).setCellValue(totalEditedCountByDay);
	        		  }else{
	        			  aRow.createCell(columnIndex++).setCellValue("");
	        		  }
	    		    
	    		    
				}
	        	
	        	
	        	//Weekly Report
	        	columnIndex = 0;
	        	
	        	Row aRow = sheet.createRow(index++);
	    		
	    		aRow.createCell(columnIndex).setCellValue("Weekly Total");
	    		
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	
	        	Map<String, Map<String, Integer>> statusCountByUser = statusCountByUserByWeek.get(weekName);
	        	

		        Integer totalReprisedCountByWeek = 0;
		        Integer totalDeletedCountByWeek = 0;
		        Integer totalEditeddCountByWeek = 0;
		        
	        	for (String workerName : workerNames) {
	    			  
	  			  	Map<String, Integer> errorReportStatus = statusCountByUser.get(workerName);
	  			  	
	  			  	if(errorReportStatus != null){
	  	        	

    			  		Integer deleteCount = errorReportStatus.get("DELETE");
    			  		Integer repriseCount = errorReportStatus.get("REPRISE");
    			  		Integer editCount = errorReportStatus.get("EDIT");
    			  		
	  			  		if(deleteCount == null){
	  			  			aRow.createCell(columnIndex).setCellValue("");
	  			  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  		}else{
	  			  			aRow.createCell(columnIndex).setCellValue(deleteCount);
	  			  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  			
	  			  			totalDeletedCountByWeek = totalDeletedCountByWeek+deleteCount;
	  			  		}
		  	        	
		  	        	if(repriseCount == null){
		  	        		aRow.createCell(columnIndex).setCellValue("");
		  	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  		}else{
	  			  			aRow.createCell(columnIndex).setCellValue(repriseCount);
	  			  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  			
	  			  			totalReprisedCountByWeek = totalReprisedCountByWeek+repriseCount;
	  			  		}
		  	        	
		  	        	if(editCount == null){
		  	        		aRow.createCell(columnIndex).setCellValue("");
		  	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  		}else{
	  			  			aRow.createCell(columnIndex).setCellValue(editCount);
	  			  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	  			  			
	  			  		totalEditeddCountByWeek = totalEditeddCountByWeek+editCount;
	  			  		}
		  	        	
		  	        	
    			  		
    			  		
	  			  	}else{
	  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
	  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);			  			
	  			  		aRow.createCell(columnIndex++).setCellStyle(boldStyle);
	  			  	}
	        	}
	        	
	        	if(totalDeletedCountByWeek > 0){
	        		aRow.createCell(columnIndex).setCellValue(totalDeletedCountByWeek);
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        		
	        	}else{
	        		aRow.createCell(columnIndex).setCellValue("");
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	}
	        	
	        	
	        	if(totalReprisedCountByWeek > 0){
	        		aRow.createCell(columnIndex).setCellValue(totalReprisedCountByWeek);
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	}else{
	        		aRow.createCell(columnIndex).setCellValue("");
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	}
		    	
	        	if(totalEditeddCountByWeek > 0){
	        		aRow.createCell(columnIndex).setCellValue(totalEditeddCountByWeek);
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	}else{
	        		aRow.createCell(columnIndex).setCellValue("");
	        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	        	}
		    	
		    	
	        	
			}
	        
	        
	        
	        //Total Report
	        columnIndex = 0;
	        
	        Integer totalReprisedCount = 0;
	        Integer totalDeletedCount = 0;
	        Integer totalEditedCount = 0;
	    	
	        Row aRow = sheet.createRow(index++);
			
			aRow.createCell(columnIndex).setCellValue("Total For Date Range");
			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	
	    	for (String workerName : workerNames) {
				  
				  	Map<String, Integer> errorReportStatus = totalStatusCountByUser.get(workerName);
		        	
				  	if(errorReportStatus != null){
				  		

    			  		Integer deleteCount = errorReportStatus.get("DELETE");
    			  		Integer repriseCount = errorReportStatus.get("REPRISE");
    			  		Integer editCount = errorReportStatus.get("EDIT");
				  		
    			  	
    			  		
    			  		
    			  		if(deleteCount == null){
				  			aRow.createCell(columnIndex).setCellValue("");
				  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  		}else{
				  			aRow.createCell(columnIndex).setCellValue(deleteCount);
				  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  			
				  			totalDeletedCount = totalDeletedCount+deleteCount;
				  		}
			        	
			        	
			        	
			        	if(repriseCount == null){
			        		aRow.createCell(columnIndex).setCellValue("");
			        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  		}else{
				  			aRow.createCell(columnIndex).setCellValue(repriseCount);
				  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  			
				  			totalReprisedCount = totalReprisedCount+repriseCount;
				  		}
			        	
			        	if(editCount == null){
			        		aRow.createCell(columnIndex).setCellValue("");
			        		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  		}else{
				  			aRow.createCell(columnIndex).setCellValue(editCount);
				  			aRow.getCell(columnIndex++).setCellStyle(boldStyle);
				  			
				  			totalEditedCount = totalEditedCount+editCount;
				  		}
			        	
			        	
    			  		
				  	}else{
				  		columnIndex = columnIndex+3;
	  			  	}
	    	}
	    	
	    	if(totalDeletedCount > 0){
	    		aRow.createCell(columnIndex).setCellValue(totalDeletedCount);
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}else{
	    		aRow.createCell(columnIndex).setCellValue("");
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}
	    	
	    	
	    	if(totalReprisedCount > 0){
	    		aRow.createCell(columnIndex).setCellValue(totalReprisedCount);
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}else{
	    		aRow.createCell(columnIndex).setCellValue("");
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}
	    	
	    	if(totalEditedCount > 0){
	    		aRow.createCell(columnIndex).setCellValue(totalEditedCount);
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}else{
	    		aRow.createCell(columnIndex).setCellValue("");
	    		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
	    	}
	    	
	    	
	    	
		}
}
