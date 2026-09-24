package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

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

public class GenerateLabErrorReportByErrorCode extends AbstractXlsView{

	
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
		
		
		
		Map<String, Map<String, Map<String, Integer>>> errorReportsByCategory =  (Map<String, Map<String, Map<String, Integer>>>)model.get("errorReportsByCategory");
		
		String fromDate = (String) model.get("fromDate");
		
		String toDate = (String) model.get("toDate");
		
		String fileName = "LAFT_ERROR_REPORTS.XLS";
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		Sheet sheet = workbook.createSheet("REPORT BY ERROR TYPE");
		
		sheet.setColumnWidth(0, 15290);
		sheet.setColumnWidth(1, 7290);
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
		
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
        //font.setBold(true);
        
        style.setFont(font);
        
        style.setWrapText(true);
        
        
        
        
        
        
        CellStyle errorCategoryStyle = workbook.createCellStyle();

		
          
        Font errorCategoryFont= workbook.createFont();
        errorCategoryFont.setFontHeightInPoints((short)14);
        errorCategoryFont.setFontName("Calibri");
        errorCategoryFont.setBold(true);
        
        errorCategoryStyle.setFont(errorCategoryFont);
        
        
        
        
        
        CellStyle errorDescriptionStyle = workbook.createCellStyle();

		
        
        
        errorDescriptionStyle.setWrapText(true);
        
        
        
        CellStyle boldStyle = workbook.createCellStyle();
        
        Font boldFont= workbook.createFont();
        
        boldFont.setBold(true);

        boldStyle.setFont(boldFont);
        
        boldStyle.setBorderTop(BorderStyle.MEDIUM);
        boldStyle.setBorderBottom(BorderStyle.MEDIUM);
        boldStyle.setBorderRight(BorderStyle.MEDIUM);
        boldStyle.setBorderLeft(BorderStyle.MEDIUM);
        
        //boldStyle.setFillForegroundColor(Color.LEMON_CHIFFON.index);
        boldStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        boldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        
        
        
        

        Row header0 = sheet.createRow(0);
        
        header0.setHeight((short)400);
        
        header0.createCell(0).setCellValue("LABORATORY REPORTS IN ERROR BY ERROR TYPE ");
        header0.getCell(0).setCellStyle(style);
        

        Row header1 = sheet.createRow(1);
        
        header1.setHeight((short)400);
        
        font.setFontHeightInPoints((short)11);
        
        style.setFont(font);
        
        Row header2 = sheet.createRow(2);
        
        header2.createCell(0).setCellValue("ERROR");
        header2.getCell(0).setCellStyle(style);
         
        header2.createCell(1).setCellValue("Number of Lab Reports in ERROR");
        header2.getCell(1).setCellStyle(style);
        
        int index = 3;
      

    	int totalErrors = 0;
    	
        for (String errorCheckType : errorReportsByCategory.keySet()) {
        	
        	
        	Map<String, Map<String, Integer>>  errorCheckMap = errorReportsByCategory.get(errorCheckType);
        	
        	
        	
        	
        	Row aRow = sheet.createRow(index++);
        	
       	 	aRow.createCell(0).setCellValue(errorCheckType);
       	 	aRow.getCell(0).setCellStyle(errorCategoryStyle);
       	 
            aRow.createCell(1).setCellValue("");
            

            for (String errorCategory : errorCheckMap.keySet()) {
			
            	
            	Map<String, Integer>  errorCategoryMap = errorCheckMap.get(errorCategory);
            	
            	
            	if(!"Soft Check".equals(errorCategory)){
            	
	            	Row aRowCategory = sheet.createRow(index++);
	            	
	            	aRowCategory.createCell(0).setCellValue(errorCategory);
	            	aRowCategory.getCell(0).setCellStyle(errorCategoryStyle);
	            	
	            	
	            	
	            	aRowCategory.createCell(1).setCellValue("");
            	}
                
            	
            	for (String error : errorCategoryMap.keySet()) {
					
            		Row aRowError = sheet.createRow(index++);
            		
            		aRowError.createCell(0).setCellValue("            "+error);
            		
            		//aRowError.createCell(0).setCellStyle(style);
            		aRowError.createCell(1).setCellValue(errorCategoryMap.get(error));
            		
            		totalErrors = totalErrors+ errorCategoryMap.get(error);
            		
				}
            	
            	
            	Row aRowError = sheet.createRow(index);
            	
            	aRowError.createCell(0).setCellValue("   TOTAL ERRORS");
            	aRowError.getCell(0).setCellStyle(boldStyle);
            	
            	aRowError.createCell(1).setCellValue(totalErrors);
            	aRowError.getCell(1).setCellStyle(boldStyle);
            	
            	
			}
        	
        	
		}
		
	}

}
