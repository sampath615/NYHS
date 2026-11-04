package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;
import org.springframework.stereotype.Component;

@Component
public class DownloadFeedbackSNPDataView extends AbstractExcelReportView{


	
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
		List<Map<String, String>> docList =  (List<Map<String, String>>)model.get("docList");
		String documentFileName = (String) model.get("documentFileName");
		StringBuilder fileName = new StringBuilder(documentFileName+"_feedback");
		
		 Calendar calendar = Calendar.getInstance();
		 fileName.append("_").append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)+1).append(calendar.get(calendar.DATE));
		 
		  	
		  	
		fileName.append(".xls");
		setAttachmentHeaders(response, fileName);
		Sheet sheet = workbook.createSheet();
		sheet.setDefaultColumnWidth(25);
		CellStyle style = workbook.createCellStyle();

		
        setSolidFill(style, IndexedColors.GREY_25_PERCENT);

        
        Row header = sheet.createRow(0);
        
        header.createCell(0).setCellValue("CIN");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("CONFIRMED");
        header.getCell(1).setCellStyle(style);
        
        int index = 1;
        for (Map<String, String> data : docList) {
			
        	Row aRow = sheet.createRow(index++);
            aRow.createCell(0).setCellValue(data.get("MEDICAID_NUMBER"));
            aRow.createCell(1).setCellValue(data.get("ELIGIBILITY"));
        	
		}
	}

}
