package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class GenerateMergeReportByBadMerges extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date now = new Date();
		String timestamp = sdf.format(now);		
		String fileName = "BAD_MERGE_REPORTS_" + timestamp + ".XLS";
		
		List<Map<String, Object>> mergeReportsByBadMerges =  (List<Map<String, Object>>)model.get("mergeReportsByBadMerges");
		String previousWeeksFriday1 = (String)model.get("previousWeeksFriday1");
		String previousWeeksFriday2 = (String)model.get("previousWeeksFriday2");
		String previousWeeksFriday3 = (String)model.get("previousWeeksFriday3");
		String previousWeeksFriday4 = (String)model.get("previousWeeksFriday4");
		String previousWeeksFriday5 = (String)model.get("previousWeeksFriday5");
		String previousWeeksFriday6 = (String)model.get("previousWeeksFriday6");		
		String previousWeeksFriday7 = (String)model.get("previousWeeksFriday7");
		String previousWeeksFriday8 = (String)model.get("previousWeeksFriday8");
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		Sheet sheet = workbook.createSheet("MERGE REPORT OF BAD MERGES");		
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 5000);
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,9));
		
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
        style.setFont(font);        
        style.setWrapText(true);

        Row header0 = sheet.createRow(0);        
        header0.setHeight((short)400);        
        header0.createCell(0).setCellValue("Number of Bad Merges Flagged for Review \n" + 
        		"*Weekly counts are pulled each Friday at 6 pm");
        header0.getCell(0).setCellStyle(style);        

        Row header1 = sheet.createRow(1);       
        header1.setHeight((short)400); 
        
        font.setFontHeightInPoints((short)11);        
        style.setFont(font);                
        CellStyle boldStyle = workbook.createCellStyle();        
        Font boldFont= workbook.createFont();        
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);        
        boldStyle.setBorderTop(BorderStyle.MEDIUM);
        boldStyle.setBorderBottom(BorderStyle.MEDIUM);
        boldStyle.setBorderRight(BorderStyle.MEDIUM);
        boldStyle.setBorderLeft(BorderStyle.MEDIUM);
        boldStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        boldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                      
        Row header2 = sheet.createRow(2);
        
        header2.createCell(0).setCellValue("Bad Merges");
        header2.getCell(0).setCellStyle(style);
         
        header2.createCell(1).setCellValue(previousWeeksFriday1);
        header2.getCell(1).setCellStyle(style);
        
        header2.createCell(2).setCellValue(previousWeeksFriday2);
        header2.getCell(2).setCellStyle(style);   
        
        header2.createCell(3).setCellValue(previousWeeksFriday3);
        header2.getCell(3).setCellStyle(style);
        
        header2.createCell(4).setCellValue(previousWeeksFriday4);
        header2.getCell(4).setCellStyle(style); 
        
        header2.createCell(5).setCellValue(previousWeeksFriday5);
        header2.getCell(5).setCellStyle(style);
        
        header2.createCell(6).setCellValue(previousWeeksFriday6);
        header2.getCell(6).setCellStyle(style); 
        
        header2.createCell(7).setCellValue(previousWeeksFriday7);
        header2.getCell(7).setCellStyle(style);
        
        header2.createCell(8).setCellValue(previousWeeksFriday8);
        header2.getCell(8).setCellStyle(style); 
         
        header2.createCell(9).setCellValue("Currently NEW");
        header2.getCell(9).setCellStyle(style);
        
        Map<String, Object> data = mergeReportsByBadMerges.get(0);			
    	Row aRow = sheet.createRow(3);
    	
        aRow.createCell(0).setCellValue("Total Bad Merges");
        aRow.createCell(1).setCellValue(((BigDecimal)data.get("BadMergesWeek1")).doubleValue());
        aRow.createCell(2).setCellValue(((BigDecimal)data.get("BadMergesWeek2")).doubleValue());
        aRow.createCell(3).setCellValue(((BigDecimal)data.get("BadMergesWeek3")).doubleValue());
        aRow.createCell(4).setCellValue(((BigDecimal)data.get("BadMergesWeek4")).doubleValue());
        aRow.createCell(5).setCellValue(((BigDecimal)data.get("BadMergesWeek5")).doubleValue());
        aRow.createCell(6).setCellValue(((BigDecimal)data.get("BadMergesWeek6")).doubleValue());
        aRow.createCell(7).setCellValue(((BigDecimal)data.get("BadMergesWeek7")).doubleValue());
        aRow.createCell(8).setCellValue(((BigDecimal)data.get("BadMergesWeek8")).doubleValue());            
        aRow.createCell(9).setCellValue(((BigDecimal)data.get("CURRENTLY_NEW")).doubleValue());
        
        for (int i=0; i<10; i++) {
        	aRow.getCell(i).setCellStyle(boldStyle);
        }
	}
}
