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
import org.springframework.web.servlet.view.document.AbstractXlsView;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;



public class GenerateCurrentStatenoData extends AbstractXlsView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
		Map<String, String> currentStatenoData =  (Map<String, String>)model.get("currentStatenoData");;
		
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=CurrentSTATENOData.XLS");
		
		
		Sheet statenoHistorysheet = workbook.createSheet();
		
		workbook.setSheetName(workbook.getSheetIndex(statenoHistorysheet), "STATENO HISTORY");
		
		
		
		statenoHistorysheet.setColumnWidth(0, 6000);
		statenoHistorysheet.setColumnWidth(1, 6000);
		statenoHistorysheet.setColumnWidth(2, 12000);
		statenoHistorysheet.setColumnWidth(3, 12000);
		
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
        
        
        String[] CURRENT_STATENO_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("CURRENT_STATENO_SPREAD_SHEET_COLUMNS").split(",");
        
        
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
        for (String _coulmnName : CURRENT_STATENO_SPREAD_SHEET_COLUMNS) {
		
        	header_sheet1.createCell(columnCount).setCellValue(_coulmnName);
        	header_sheet1.getCell(columnCount++).setCellStyle(boldStyle);
        	
        	
		}
        
        columnCount = 0;
        
        final String COMMA = ",";
        final String SPACE = " ";
        final String QUOTE = "'";
        
        StringBuilder currentStatenosWithComma = new StringBuilder("");
        StringBuilder currentStatenosWithSPACE = new StringBuilder("");
        
        for (String STATENO : currentStatenoData.keySet()) {
        	
        	Row aRow = statenoHistorysheet.createRow(rowIndex_sheet1++);
			
        	aRow.createCell(0).setCellValue(STATENO);
    		aRow.getCell(0).setCellStyle(commonStyles);
    		

    		String currentStateno =  currentStatenoData.get(STATENO);
    		
    		if(CommonUtil.isNullOrEmpty(currentStateno) || "-1".equals(currentStateno)){
    			aRow.createCell(1).setCellValue("There is no Active STATENO");
        		aRow.getCell(1).setCellStyle(commonStyles);
    		}else if("0".equals(currentStateno)){
    			aRow.createCell(1).setCellValue("Failed to get Current STATENO");
        		aRow.getCell(1).setCellStyle(commonStyles);
    		}else{
    			
    			aRow.createCell(1).setCellValue(currentStateno);
        		aRow.getCell(1).setCellStyle(commonStyles);
        		
        		currentStatenosWithComma.append(SPACE).append(QUOTE).append(currentStateno).append(QUOTE).append(COMMA);
        		currentStatenosWithSPACE.append(QUOTE).append(currentStateno).append(QUOTE).append(SPACE);
    			
    		}
    		
    		
    		
    		
    		
		}
        
    	Row aRow = statenoHistorysheet.getRow(1);

        if(currentStatenosWithComma.length() > 0){
        	aRow.createCell(2).setCellValue(currentStatenosWithComma.substring(0, currentStatenosWithComma.length()-1));
        	aRow.getCell(2).setCellStyle(commonStyles);
        }
    	aRow.createCell(3).setCellValue(currentStatenosWithSPACE.toString());
    	aRow.getCell(3).setCellStyle(commonStyles);

	 }
	
	
	
	
	
	
	
	
}
