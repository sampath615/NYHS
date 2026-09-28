package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.math.BigDecimal;
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

import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;

public class GenerateLabErrorReportByFacility extends AbstractExcelReportView{


	
	/**
	 * `
	 */
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
        
		List<Map<String, Object>> errorReportsByFacilityName =  (List<Map<String, Object>>)model.get("errorReportsByFacilityName");
		
		String fromDate = (String) model.get("fromDate");
		
		String toDate = (String) model.get("toDate");
		
		String fileName = "LAFT_ERRORS_REPORTS_BY_LAB.XLS";
		
		setAttachmentHeaders(response, fileName);
		Sheet sheet = workbook.createSheet("REPORTS BY LAB");
		
		sheet.setColumnWidth(0, 5290);
		sheet.setColumnWidth(1, 5290);
		sheet.setColumnWidth(2, 3290);
		sheet.setColumnWidth(3, 13090);
		sheet.setColumnWidth(5, 7290);
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,4));
		
		CellStyle style = workbook.createCellStyle();

		
		setSolidFill(style, IndexedColors.LIGHT_TURQUOISE);
        
        setBorders(style, BorderStyle.MEDIUM);
        
        style.setAlignment(HorizontalAlignment.CENTER);
        
        Font font = createCalibriFont(workbook, 14, false);
        //font.setBold(true);
        
        style.setFont(font);
        
        style.setWrapText(true);
        

        Row header0 = sheet.createRow(0);
        
        header0.setHeight((short)400);
        
        header0.createCell(0).setCellValue("LABORATORY REPORTS IN ERROR BY LABORATORY ");
        header0.getCell(0).setCellStyle(style);
        

        Row header1 = sheet.createRow(1);
        
        header1.setHeight((short)400);
        
        font.setFontHeightInPoints((short)11);
        
        style.setFont(font);
        
        
        CellStyle boldStyle = workbook.createCellStyle();
        
        Font boldFont= workbook.createFont();
        
        boldFont.setBold(true);

        boldStyle.setFont(boldFont);
        
        setBorders(boldStyle, BorderStyle.MEDIUM);
        
        //boldStyle.setFillForegroundColor(Color.LEMON_CHIFFON.index);
        boldStyle.setFillForegroundColor(IndexedColors.AQUA.index);
       // boldStyle.setFillPattern(FillPatternType.);
        
        
        
        Row header2 = sheet.createRow(2);
        
        header2.createCell(0).setCellValue("LAFT staff");
        header2.getCell(0).setCellStyle(style);
        
        header2.createCell(1).setCellValue("CLIA");
        header2.getCell(1).setCellStyle(style);
         
        header2.createCell(2).setCellValue("PFI");
        header2.getCell(2).setCellStyle(style);
        
        header2.createCell(3).setCellValue("Laboratory Name");
        header2.getCell(3).setCellStyle(style);
        
         
        header2.createCell(4).setCellValue("Number of Lab Reports in ERROR");
        header2.getCell(4).setCellStyle(style);
        
        int index = 3;
        
        int totalErrors = 0;
        
        for (Map<String, Object> data : errorReportsByFacilityName) {
			
        	Row aRow = sheet.createRow(index++);
        	aRow.createCell(0).setCellValue((String)data.get("LAFT_STAFF"));
            aRow.createCell(1).setCellValue((String)data.get("CLIA"));
            aRow.createCell(2).setCellValue((String)data.get("PFI"));
            aRow.createCell(3).setCellValue((String)data.get("LAB_NAME"));
            
            int errorCount = ((BigDecimal)data.get("COUNT")).intValue();
            
            totalErrors = totalErrors+errorCount;
            
            aRow.createCell(4).setCellValue(errorCount);
        	
		}
        
        Row aRow = sheet.createRow(index);
        
        sheet.addMergedRegion(new CellRangeAddress(index,index,0,3));
        
        aRow.createCell(0).setCellValue("TOTAL NUMBER OF LABORATORY REPORTS in ERROR");
        aRow.getCell(0).setCellStyle(boldStyle);
        aRow.createCell(1).setCellStyle(boldStyle);
        aRow.createCell(2).setCellStyle(boldStyle);
        aRow.createCell(3).setCellStyle(boldStyle);
        
        
        aRow.createCell(4).setCellValue(totalErrors);
        aRow.getCell(4).setCellStyle(boldStyle);
        
        
	}

}
