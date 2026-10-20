package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import org.springframework.beans.factory.annotation.Autowired;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.SpreadsheetProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.eprf.EPRFFollowUpDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;
import org.springframework.stereotype.Component;


@Component
public class GenerateEPRFSummarySpreadsheet extends AbstractExcelReportView{

	@Autowired
	private SpreadsheetProperties spreadsheetProperties;


    	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
	    
        List<EPRFFollowUpDocVO> ePRFFollowUpDocVOList =  (List<EPRFFollowUpDocVO>)model.get("ePRFFollowUpDocVOList");;
	    
		String documentFileName = (String) model.get("documentFileName");
	    
	    StringBuilder fileName = new StringBuilder("Review_"+documentFileName);
	    
	   	
	    
	    //if(fileName.indexOf(".xlsx") == -1 && fileName.indexOf(".XLSX") == -1){
	    	fileName.append(".xls");
	    //}
	    		
	    setAttachmentHeaders(response, fileName);
		
		
		Sheet sheet = workbook.createSheet();
		
		sheet.setDefaultColumnWidth(11);
		
		
		sheet.setColumnWidth(27, 12000);
		sheet.setColumnWidth(28, 5100);
		sheet.setColumnWidth(29, 4800);

		CellStyle commonStyles = workbook.createCellStyle();
		
		commonStyles.setAlignment(HorizontalAlignment.CENTER);
		setBorders(commonStyles, BorderStyle.THIN);
        commonStyles.setWrapText(true);
        
        Font commonFont = createCalibriFont(workbook, 11, false);
        
        commonStyles.setFont(commonFont);//set it to bold
        
        
        String[] EPRF_SUMMARY_SPREAD_SHEET_DOC_COLUMNS = spreadsheetProperties.eprfSummary().docColumns().split(",");
	    String[] EPRF_SUMMARY_SPREAD_SHEET_EHARS_COLUMNS = spreadsheetProperties.eprfSummary().eharsColumns().split(",");
	    String[] EPRF_SUMMARY_SPREAD_SHEET_EHARS_CONCLUSIONS = spreadsheetProperties.eprfSummary().eharsConclusions().split(",");
	    
	    int docColumnsLength = EPRF_SUMMARY_SPREAD_SHEET_DOC_COLUMNS.length;
		int eharsColumnsLength = EPRF_SUMMARY_SPREAD_SHEET_EHARS_COLUMNS.length;
		int conclusionsColumnsLength = EPRF_SUMMARY_SPREAD_SHEET_EHARS_CONCLUSIONS.length;
		
	   
	        int rowIndex = 0;
	      
		
		
		
		//HSSFPalette palette = workbook.getCustomPalette();
		
		Row header0 = sheet.createRow(rowIndex++);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,docColumnsLength-1));
		sheet.addMergedRegion(new CellRangeAddress(0,0,docColumnsLength, docColumnsLength+eharsColumnsLength-1));
		sheet.addMergedRegion(new CellRangeAddress(0,0,docColumnsLength+eharsColumnsLength, docColumnsLength+eharsColumnsLength+conclusionsColumnsLength-1));
		
		
		
		Row header = sheet.createRow(rowIndex++);
	    
	    
	    
	    
		
		CellStyle style1 = workbook.createCellStyle();
		
		style1.setAlignment(HorizontalAlignment.CENTER);
		setBorders(style1, BorderStyle.THIN);
		
        //style1.setFillForegroundColor(Color.DARK_TEAL.index);
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setWrapText(true);
    
        
        Font font = createCalibriFont(workbook, 14, true);
        
        style1.setFont(font);//set it to bold
        
        style1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        
        
        
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(style1);
        style2.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
        
        
        
        
        CellStyle style3 = workbook.createCellStyle();
        
        style3.cloneStyleFrom(style1);
          
        style3.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
        
        
        
        header0.createCell(0).setCellValue("From File Submitted by Provider");
        header0.getCell(0).setCellStyle(style1);
        
        header0.createCell(docColumnsLength).setCellValue("System Produced");
        header0.getCell(docColumnsLength).setCellStyle(style2);
                
        header0.createCell(docColumnsLength+eharsColumnsLength).setCellValue("Reviewer Entered");
        header0.getCell(docColumnsLength+eharsColumnsLength).setCellStyle(style3);
        
        
        
        Font font2 = createCalibriFont(workbook, 11, true);
        
        
        CellStyle style4 = workbook.createCellStyle();
        CellStyle style5 = workbook.createCellStyle();
        CellStyle style6 = workbook.createCellStyle();
        
        
        style4.cloneStyleFrom(style1);
        style4.setFont(font2);
        
        style5.cloneStyleFrom(style2);
        style5.setFont(font2);
        
        style6.cloneStyleFrom(style3);
        style6.setFont(font2);
        
        
        int columnCount = 0;
        for (String _coulmnName : EPRF_SUMMARY_SPREAD_SHEET_DOC_COLUMNS) {
		
        	header.createCell(columnCount).setCellValue(_coulmnName);
        	header.getCell(columnCount++).setCellStyle(style4);
        	
            
		}
        
        
        for (String _coulmnName : EPRF_SUMMARY_SPREAD_SHEET_EHARS_COLUMNS) {
    		
        	header.createCell(columnCount).setCellValue(_coulmnName);
            header.getCell(columnCount++).setCellStyle(style5);
            
		}
        

        for (String _coulmnName : EPRF_SUMMARY_SPREAD_SHEET_EHARS_CONCLUSIONS) {
    		
        	header.createCell(columnCount).setCellValue(_coulmnName);
            header.getCell(columnCount++).setCellStyle(style6);
            
		}
       
        
        Map<String, String> yesNoMap = new HashMap<>();
        
        
        yesNoMap.put("Y", "Yes");
        yesNoMap.put("N", "No");
        
        
        
        
        for (EPRFFollowUpDocVO ePRFFollowUpDocVO : ePRFFollowUpDocVOList) {
        	
        	columnCount = 0;
        	
        	Row aRow = sheet.createRow(rowIndex++);
    		
    		
    		
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getMED_RECORD_NUMBER());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getFNAME());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLNAME());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getDOB());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getPROV_LAST_VISIT_DATE());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_VIRAL_LOAD_DATE());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_VIRAL_LOAD_RESULT());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_CD4_DATE());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_CD4_RESULT_COUNT());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_CD4_RESULT_PERCENT());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getPROV_COUNTY());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            
            
            
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getQS_MATCH_CD());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getMATCH_CD());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYEHMS_STATENO());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYS_EHARS_STATENO());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYC_EHARS_STATENO());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYC_CITY_NO());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_HIV_LAB_TYPE());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_HIV_LAB_DATE());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getLAST_HIV_LAB_RESULT());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getMONTHS_SINCE_LAST_HIV_LAB());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYS_VITAL_STATUS());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getNYC_VITAL_STATUS());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getMEETS_CASE_DEFINITION_IN());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getMEETS_CDC_ELIG_ID());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            
            
             
            
            
            
            aRow.createCell(columnCount).setCellValue(yesNoMap.get(ePRFFollowUpDocVO.getCONFIRMATORY_RESULT_REPORTED()));
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(yesNoMap.get(ePRFFollowUpDocVO.getSUBMITTING_PROVIDER_PRESENT()));
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getREVIEW_COMMENT());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getINTERNAL_CONCLUSION_DESC());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            aRow.createCell(columnCount).setCellValue(ePRFFollowUpDocVO.getCONCLUSION_DESC());
            aRow.getCell(columnCount++).setCellStyle(commonStyles);
            
            
		}
        
		
        
        

       
        
        
        

	 }
	
	
}
