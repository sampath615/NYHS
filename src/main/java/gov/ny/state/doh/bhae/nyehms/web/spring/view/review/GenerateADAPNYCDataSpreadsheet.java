package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;



public class GenerateADAPNYCDataSpreadsheet extends AbstractXlsView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
		List<Map<String, String>> adapNYCData =  (List<Map<String, String>>)model.get("adapNYCData");;
	    
		String filename = (String) model.get("filename");
	    
	    StringBuilder fileName = new StringBuilder(filename);
	    
	   	
	    
	    if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
			
			fileName.append(".XLS");
			
		}
	    		
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(11);
		
		
//		sheet.setColumnWidth(27, 12000);
//		sheet.setColumnWidth(28, 5100);
//		sheet.setColumnWidth(29, 4800);

		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.CENTER);
		commonStyles.setBorderBottom(BorderStyle.THIN);
		commonStyles.setBorderTop(BorderStyle.THIN);
		commonStyles.setBorderLeft(BorderStyle.THIN);
        commonStyles.setBorderRight(BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = workbook.createFont();
        commonFont.setFontHeight((short)(11*20));
        commonFont.setFontName("Calibri");
        
        commonStyles.setFont(commonFont);//set it to bold
        
        
        String[] ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS").split(",");
	    String[] ADAP_NYC_DATA_DB_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_DATA_DB_COLUMNS").split(",");
	    
	    
//		int dbColumnsLength = ADAP_NYC_DATA_DB_COLUMNS.length;
//		int spreadSheetColumnsLength = ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS.length;
		
	   
	        int rowIndex = 0;
	      
		
		
		
		//HSSFPalette palette = workbook.getCustomPalette();
		
		
		
	    
	    
		
//		CellStyle style1 = workbook.createCellStyle();
//		
//		style1.setAlignment(HorizontalAlignment.CENTER);
//		style1.setBorderBottom(BorderStyle.THIN);
//		style1.setBorderTop(BorderStyle.THIN);
//		style1.setBorderLeft(BorderStyle.THIN);
//		style1.setBorderRight(BorderStyle.THIN);
//		
//        //style1.setFillForegroundColor(Color.DARK_TEAL.index);
//        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style1.setWrapText(true);
    
        
//        Font font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight((short)(14*20));
//        font.setFontName("Calibri");
//        
//        style1.setFont(font);//set it to bold
//        
//        Color myColor1 = palette.findSimilarColor(146, 208, 80);
//        short palIndex = myColor1.getIndex();
//        style1.setFillForegroundColor(palIndex);
//        
//        
//        
//        CellStyle style2 = workbook.createCellStyle();
//        style2.cloneStyleFrom(style1);
//        Color myColor2 = palette.findSimilarColor(0, 176, 240);
//        palIndex = myColor2.getIndex();
//        style2.setFillForegroundColor(palIndex);
        
        
        
        
//        CellStyle style3 = workbook.createCellStyle();
//        
//        style3.cloneStyleFrom(style1);
//          
//        Color myColor3 = palette.findSimilarColor(255, 192, 0);
//        palIndex = myColor3.getIndex();
//        style3.setFillForegroundColor(palIndex);
        
        
//        
//        
//        Font font2 = workbook.createFont();
//        font2.setBold(true);
//        font2.setFontHeight((short)(11*20));
//        font2.setFontName("Calibri");
        
        
//        CellStyle style4 = workbook.createCellStyle();
//        CellStyle style5 = workbook.createCellStyle();
//        CellStyle style6 = workbook.createCellStyle();
//        
//        
//        style4.cloneStyleFrom(style1);
//        style4.setFont(font2);
//        
//        style5.cloneStyleFrom(style2);
//        style5.setFont(font2);
//        
//        style6.cloneStyleFrom(style3);
//        style6.setFont(font2);
        

		Row header = sheet.createRow(rowIndex++);
		
        int columnCount = 0;
        for (String _coulmnName : ADAP_NYC_DATA_SPREAD_SHEET_COLUMNS) {
		
        	header.createCell(columnCount++).setCellValue(_coulmnName);
        	//header.getCell(columnCount++).setCellStyle(style4);
        	
            
		}
        
        
//        for (String _coulmnName : EPRF_SUMMARY_SPREAD_SHEET_EHARS_COLUMNS) {
//    		
//        	header.createCell(columnCount).setCellValue(_coulmnName);
//            header.getCell(columnCount++).setCellStyle(style5);
//            
//		}
//        
//
//        for (String _coulmnName : EPRF_SUMMARY_SPREAD_SHEET_EHARS_CONCLUSIONS) {
//    		
//        	header.createCell(columnCount).setCellValue(_coulmnName);
//            header.getCell(columnCount++).setCellStyle(style6);
//            
//		}
//       
//        
//        Map<String, String> yesNoMap = new HashMap<String, String>();
//        
//        
//        yesNoMap.put("Y", "Yes");
//        yesNoMap.put("N", "No");
        
        
        
        
        for (Map<String, String> rowData : adapNYCData) {
        	
        	columnCount = 0;
        	
        	Row aRow = sheet.createRow(rowIndex++);
    		
        	for (String _coulmnName : ADAP_NYC_DATA_DB_COLUMNS) {
    		
        		aRow.createCell(columnCount).setCellValue(rowData.get(_coulmnName));
        		aRow.getCell(columnCount++).setCellStyle(commonStyles);
        	}
            
            
		}
        

	 }
	
	
}
