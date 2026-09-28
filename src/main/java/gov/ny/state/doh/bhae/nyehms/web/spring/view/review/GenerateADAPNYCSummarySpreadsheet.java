package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.HashMap;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;



public class GenerateADAPNYCSummarySpreadsheet extends AbstractExcelReportView{


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
		List<Map<String, String>> nycFileSummaryData =  (List<Map<String, String>>)model.get("nycFileSummaryData");;
		
		List<Map<String, String>> nycShippedMatchSummaryData =  (List<Map<String, String>>)model.get("nycShippedMatchSummaryData");;
		
		List<Map<String, String>> generateADAPNYCQualityStageMatchSummary =  (List<Map<String, String>>)model.get("generateADAPNYCQualityStageMatchSummary");;
		
		
		
		Map<String, String> reviewStatusMap = new HashMap<String, String>();
		
		reviewStatusMap.put("M", "Matches");
		reviewStatusMap.put("N", "Non Matches");
		reviewStatusMap.put("C_M", "Clerical Matches");
		reviewStatusMap.put("P_M", "Clerical Death Matches");
		reviewStatusMap.put("C_N", "Clerical Non Matches");
		reviewStatusMap.put("P_N", "Clerical Death Non Matches");
		reviewStatusMap.put("A", "Agree");
		reviewStatusMap.put("D", "Disagree");
		
	    
		String filename = (String) model.get("filename");
	    
	    StringBuilder fileName = new StringBuilder(filename);
	    
	    fileName.insert(0, "Summary_");
	   	
	    
	    if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
			
			fileName.append(".XLS");
			
		}
	    		
	    setAttachmentHeaders(response, fileName);
		
		
		Sheet Matchsheet = workbook.createSheet();
		
		Sheet NotMatchSheet = workbook.createSheet();
		
		Sheet nycShippedMatchSummarySheet = workbook.createSheet();
		
		workbook.setSheetName(workbook.getSheetIndex(Matchsheet), "Agree");
		
		workbook.setSheetName(workbook.getSheetIndex(NotMatchSheet), "Disagree");
		
		workbook.setSheetName(workbook.getSheetIndex(nycShippedMatchSummarySheet), "NYC Shipped Match Summary");
		

		
		Matchsheet.setDefaultColumnWidth(13);
		NotMatchSheet.setDefaultColumnWidth(13);
		
		
		nycShippedMatchSummarySheet.setColumnWidth(0, 9800);
		nycShippedMatchSummarySheet.setColumnWidth(1, 7100);
		

		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.CENTER);
		setBorders(commonStyles, BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = createCalibriFont(workbook, 11, false);
        
        commonStyles.setFont(commonFont);//set it to bold
        
        
        String[] ADAP_NYC_AGREE_SUMMARY_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_AGREE_SUMMARY_SPREAD_SHEET_COLUMNS").split(",");
        
        String[] ADAP_NYC_DISAGREE_SUMMARY_SPREAD_SHEET_COLUMNS = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_DISAGREE_SUMMARY_SPREAD_SHEET_COLUMNS").split(",");
        
        String[] ADAP_NYC_SHIPPED_MATCH_SUMMARY = EnvironmentProperties.envProperties.getProperty("ADAP_NYC_SHIPPED_MATCH_SUMMARY").split(",");

        
        
        CellStyle boldStyle = workbook.createCellStyle();
        
        Font boldFont= workbook.createFont();
        
        boldFont.setBold(true);

        boldStyle.setFont(boldFont);
        
        setBorders(boldStyle, BorderStyle.MEDIUM);
        
        //boldStyle.setFillForegroundColor(Color.LEMON_CHIFFON.index);
        setSolidFill(boldStyle, IndexedColors.LIGHT_TURQUOISE);
        

        
        CellStyle summaryStatusStyle = workbook.createCellStyle();
        
       

        summaryStatusStyle.setFont(boldFont);
        
        setBorders(summaryStatusStyle, BorderStyle.MEDIUM);
     
        
        

	    int rowIndex_sheet1 = 0;
	    int rowIndex_sheet2 = 0;
	    int rowIndex_sheet3 = 0;
	    
		Row header_sheet1 = Matchsheet.createRow(rowIndex_sheet1++);
		Row header_sheet2 = NotMatchSheet.createRow(rowIndex_sheet2++);
		
		
		Row header_sheet3 = nycShippedMatchSummarySheet.createRow(rowIndex_sheet3++);
		
		
		
        int columnCount = 0;
        for (String _coulmnName : ADAP_NYC_AGREE_SUMMARY_SPREAD_SHEET_COLUMNS) {
		
        	header_sheet1.createCell(columnCount).setCellValue(_coulmnName);
        	header_sheet1.getCell(columnCount++).setCellStyle(boldStyle);
        	
        	
		}
        
        columnCount = 0;
        for (String _coulmnName : ADAP_NYC_DISAGREE_SUMMARY_SPREAD_SHEET_COLUMNS) {
    		
        	header_sheet2.createCell(columnCount).setCellValue(_coulmnName);
        	header_sheet2.getCell(columnCount++).setCellStyle(boldStyle);
        	
		}
        

        columnCount = 0;
        for (String _coulmnName : ADAP_NYC_SHIPPED_MATCH_SUMMARY) {
    		
        	header_sheet3.createCell(columnCount).setCellValue(_coulmnName);
        	header_sheet3.getCell(columnCount++).setCellStyle(boldStyle);
        	
        	
        	//header.getCell(columnCount++).setCellStyle(style4);
        	
            
		}
        
        
        
        
        
        
        
        
        
        
        for (Map<String, String> rowData : nycFileSummaryData) {
        	
        	
        	
        	if("A".equals(rowData.get("REVIEW_STATUS"))){
        		
        		Row aRow = Matchsheet.createRow(rowIndex_sheet1++);
        		
        		aRow.createCell(0).setCellValue(rowData.get("STATENO"));
        		aRow.getCell(0).setCellStyle(commonStyles);
        		

        		aRow.createCell(1).setCellValue(rowData.get("CITYNO"));
        		aRow.getCell(1).setCellStyle(commonStyles);
        		
        		
        		
        	}else{
        		
        		Row aRow = NotMatchSheet.createRow(rowIndex_sheet2++);
        		
        		
        		aRow.createCell(0).setCellValue(rowData.get("STATENO"));
        		aRow.getCell(0).setCellStyle(commonStyles);
        		

        		aRow.createCell(1).setCellValue(rowData.get("CITYNO"));
        		aRow.getCell(1).setCellStyle(commonStyles);

        		aRow.createCell(2).setCellValue(rowData.get("REVIEWED_CITYNO"));
        		aRow.getCell(2).setCellStyle(commonStyles);

        		
        		aRow.createCell(3).setCellValue(rowData.get("REVIEW_COMMENTS"));
        		aRow.getCell(3).setCellStyle(commonStyles);
        	}
        	

            
		}
        
        for (Map<String, String> rowData : generateADAPNYCQualityStageMatchSummary) {
         	
         	
        	
     		Row aRow = nycShippedMatchSummarySheet.createRow(rowIndex_sheet3++);
     		
     		String status = rowData.get("STATUS");
     		
     		if(reviewStatusMap.get(status) != null){
     			aRow.createCell(0).setCellValue(reviewStatusMap.get(status));
     		}else{
     			aRow.createCell(0).setCellValue(status);
     		}
     		aRow.getCell(0).setCellStyle(summaryStatusStyle);
     		

     		aRow.createCell(1).setCellValue(rowData.get("COUNT"));
     		aRow.getCell(1).setCellStyle(commonStyles);
 
     	}
        
        
        rowIndex_sheet3 = rowIndex_sheet3+3;
        
        
        for (Map<String, String> rowData : nycShippedMatchSummaryData) {
         	
         	
     		Row aRow = nycShippedMatchSummarySheet.createRow(rowIndex_sheet3++);
     		
     		
     		
     		
     		
     		String status = rowData.get("STATUS");
     		
     		if(reviewStatusMap.get(status) != null){
     			aRow.createCell(0).setCellValue(reviewStatusMap.get(status));
     		}else{
     			aRow.createCell(0).setCellValue(status);
     		}
     		aRow.getCell(0).setCellStyle(summaryStatusStyle);
     		

     		aRow.createCell(1).setCellValue(rowData.get("COUNT"));
     		aRow.getCell(1).setCellStyle(commonStyles);
     		
     	}


        

	 }
	
	
	
	
	
	
	
	
}
