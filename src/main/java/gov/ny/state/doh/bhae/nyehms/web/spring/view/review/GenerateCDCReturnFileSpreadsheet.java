package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class GenerateCDCReturnFileSpreadsheet extends AbstractXlsView{


	
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
		List<Map<String, String>> cdcReturnData =  (List<Map<String, String>>)model.get("cdcReturnData");
		
		String cdcFileName = (String) model.get("filename");
		
		
		StringBuilder fileName = new StringBuilder();
		

		fileName.append(cdcFileName.toUpperCase());

		
		if(fileName.indexOf(".XLS") == -1 && fileName.indexOf(".XLSX") == -1){
			
			fileName.append(".XLS");
			
		}
		
		  	
		
		  	
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
		
		
		Sheet sheet = workbook.createSheet();
		

		sheet.setColumnWidth(1, 4290);
		sheet.setColumnWidth(2, 8290);
		sheet.setColumnWidth(3, 6290);
		sheet.setColumnWidth(4, 10590);
		sheet.setColumnWidth(5, 10390);
		

        int index = 0;
        for (Map<String, String> data : cdcReturnData) {
			
        	Row aRow = sheet.createRow(index++);
            aRow.createCell(0).setCellValue(data.get("LNAME_SOUNDEX"));
            aRow.createCell(1).setCellValue(data.get("DOB_TEXT"));
            aRow.createCell(2).setCellValue(data.get("BIRTH_SEX"));
            aRow.createCell(3).setCellValue(data.get("EHARS_STATENO"));
            aRow.createCell(4).setCellValue(data.get("CITYNO"));
        	
		}
	}

}
