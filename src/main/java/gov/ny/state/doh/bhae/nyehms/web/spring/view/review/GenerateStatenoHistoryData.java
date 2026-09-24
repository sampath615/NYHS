package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

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



public class GenerateStatenoHistoryData extends AbstractXlsView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
		Map<String, List<String>> statenoHistoryData =  (Map<String, List<String>>)model.get("statenoHistoryData");;
		
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=STATENOHistoryData.XLS");
		
		
		Sheet statenoHistorysheet = workbook.createSheet();
		
		workbook.setSheetName(workbook.getSheetIndex(statenoHistorysheet), "STATENO HISTORY");
		
		
		
		statenoHistorysheet.setColumnWidth(0, 6000);
		statenoHistorysheet.setColumnWidth(1, 20000);
		
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
        
        commonStyles.setFont(commonFont);//set it to bold
        
        
        String[] STATENO_HISTORY_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("STATENO_HISTORY_SPREAD_SHEET_COLUMNS").split(",");
        
        
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
        


	    int rowIndex_sheet1 = 0;

	    
		Row header_sheet1 = statenoHistorysheet.createRow(rowIndex_sheet1++);
		
		
		
        int columnCount = 0;
        for (String _coulmnName : STATENO_HISTORY_SPREAD_SHEET_COLUMNS) {
		
        	header_sheet1.createCell(columnCount).setCellValue(_coulmnName);
        	header_sheet1.getCell(columnCount++).setCellStyle(boldStyle);
        	
        	
		}
        
        columnCount = 0;
        
        
        for (String STATENO : statenoHistoryData.keySet()) {
        	
        	Row aRow = statenoHistorysheet.createRow(rowIndex_sheet1++);
			
        	aRow.createCell(0).setCellValue(STATENO);
    		aRow.getCell(0).setCellStyle(commonStyles);
    		

    		List<String> statenoHistory =  statenoHistoryData.get(STATENO);
    		
    		if(statenoHistory == null || statenoHistory.size() == 0){
    			aRow.createCell(1).setCellValue("There is history for this STATENO");
        		aRow.getCell(1).setCellStyle(commonStyles);
    		}else{
    			
    			StringBuilder statenoHistoryString = new StringBuilder("");
    			
    			for (String _oldStateno : statenoHistory) {
					
    				statenoHistoryString.append(_oldStateno).append(",");
    				
				}
    			
    			
    			
    			aRow.createCell(1).setCellValue(statenoHistoryString.substring(0, statenoHistoryString.length()-1));
        		aRow.getCell(1).setCellStyle(commonStyles);
    			
    		}
    		
    		
    		
		}
        

	 }
	
	
	
	
	
	
	
	
}
