package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

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
import org.apache.poi.ss.util.CellRangeAddress;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;

public class GenerateLabErrorStatusReportByFacility extends AbstractExcelReportView{


	
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
		
        
		List<Map<String, Object>> errorReportsByFacilityName =  (List<Map<String, Object>>)model.get("errorStatusReportByLab");
		
		String fromDate = (String) model.get("fromDate");
		
		String toDate = (String) model.get("toDate");
		
		String fileName = "LAFT_REPRISE_DELETE_BY_LAB.XLS";
		
		setAttachmentHeaders(response, fileName);
		Sheet sheet = workbook.createSheet("Lab Report Reprise or Deleted");
		
		sheet.setColumnWidth(0, 5290);
		sheet.setColumnWidth(1, 3290);
		sheet.setColumnWidth(2, 13090);
		sheet.setColumnWidth(3, 6290);
		sheet.setColumnWidth(4, 5290);
		sheet.setColumnWidth(5, 5290);
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,6));
		
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
        
        header0.createCell(0).setCellValue("LABORATORY REPORTS IN ERROR. REPRISED, DELETED and EDITED BY LABORATORY \n FROM "+fromDate+" THROUGH "+toDate);
        header0.getCell(0).setCellStyle(style);
        

        Row header1 = sheet.createRow(1);
        
        header1.setHeight((short)400);
        
        font.setFontHeightInPoints((short)11);
        

        Row header2 = sheet.createRow(2);        
        
        sheet.addMergedRegion(new CellRangeAddress(2,2,0,3));
        sheet.addMergedRegion(new CellRangeAddress(2,2,4,6));
        
        
        header2.createCell(0).setCellStyle(style);
        header2.createCell(1).setCellStyle(style);
        header2.createCell(2).setCellStyle(style);
        header2.createCell(3).setCellStyle(style);
        header2.createCell(4).setCellValue("Number Lab Reports");
        header2.getCell(4).setCellStyle(style);
        header2.createCell(5).setCellStyle(style);
        header2.createCell(6).setCellStyle(style);
        
        style.setFont(font);
        
        Row header3 = sheet.createRow(3);
        
        header3.createCell(0).setCellValue("CLIA");
        header3.getCell(0).setCellStyle(style);
         
        header3.createCell(1).setCellValue("PFI");
        header3.getCell(1).setCellStyle(style);
        
        header3.createCell(2).setCellValue("Laboratory Name");
        header3.getCell(2).setCellStyle(style);
        
         
        header3.createCell(3).setCellValue("LAFT Staff Assigned to Lab");
        header3.getCell(3).setCellStyle(style);
        
        header3.createCell(4).setCellValue("Reprised");
        header3.getCell(4).setCellStyle(style);
        
        header3.createCell(5).setCellValue("Deleted");
        header3.getCell(5).setCellStyle(style);
        
        header3.createCell(6).setCellValue("Edited");
        header3.getCell(6).setCellStyle(style);
        
        int index = 4;
        
        
        int totalReprisedCount = 0;
        int totalDeletedCount = 0;
        int totalEditCount = 0;
        
        for (Map<String, Object> data : errorReportsByFacilityName) {
			
        	Row aRow = sheet.createRow(index++);
            aRow.createCell(0).setCellValue((String)data.get("CLIA"));
            aRow.createCell(1).setCellValue((String)data.get("PFI"));
            aRow.createCell(2).setCellValue((String)data.get("LAB_NAME"));
            aRow.createCell(3).setCellValue((String)data.get("UPDATEID"));
            
            
            
            int REPRISE_CNT = ((BigDecimal)data.get("REPRISE_CNT")).intValue();
            
            int DELETE_CNT = ((BigDecimal)data.get("DELETE_CNT")).intValue();
            
            int EDIT_CNT = ((BigDecimal)data.get("EDIT_CNT")).intValue();
            
            totalReprisedCount = totalReprisedCount+REPRISE_CNT;
            
            totalDeletedCount = totalDeletedCount+DELETE_CNT;
            
            totalEditCount = totalEditCount+EDIT_CNT;
            
            
            aRow.createCell(4).setCellValue(REPRISE_CNT);
            aRow.createCell(5).setCellValue(DELETE_CNT);
            aRow.createCell(6).setCellValue(EDIT_CNT);
        	
		}
        
        
        sheet.addMergedRegion(new CellRangeAddress(index,index,0,3));
        
        Row aRow = sheet.createRow(index++);
        
        aRow.createCell(0).setCellValue("TOTAL LABORATORY REPORTS REPRISED, DELETED AND EDITED");
        aRow.getCell(0).setCellStyle(boldStyle);
        aRow.createCell(1).setCellStyle(boldStyle);
        aRow.createCell(2).setCellStyle(boldStyle);
        aRow.createCell(3).setCellStyle(boldStyle);
        
        aRow.createCell(4).setCellValue(totalReprisedCount);
        aRow.getCell(4).setCellStyle(boldStyle);
        
        aRow.createCell(5).setCellValue(totalDeletedCount);
        aRow.getCell(5).setCellStyle(boldStyle);
        aRow.createCell(6).setCellValue(totalEditCount);
        aRow.getCell(6).setCellStyle(boldStyle);
        
        
        
        
	}

}
	