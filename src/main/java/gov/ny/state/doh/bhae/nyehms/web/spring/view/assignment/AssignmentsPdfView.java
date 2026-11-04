package gov.ny.state.doh.bhae.nyehms.web.spring.view.assignment;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import org.springframework.stereotype.Component;


@Component("assignmentReportsByDocumentPdfView")
public class AssignmentsPdfView extends AbstractITextPdfView{



	@Override
	protected void buildPdfDocument(
			Map mav,
			Document document,
			PdfWriter writer,
			HttpServletRequest req,
			HttpServletResponse resp) throws Exception
	{
		List<Map<String, String>> assignmentsList =
				(List<Map<String, String>>)mav.get("assignmentDocuments");;
		String documentType = (String) mav.get("documentType");
		String filterByCategory = (String) mav.get("filterByCategory");
		String filterByCategoryColumnName =
				(String) mav.get("filterByCategoryColumnName");
		String filerCondition = (String) mav.get("filerCondition");
		filerCondition = filerCondition.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
		documentType = documentType.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
		String assignmentStatusDisplayName = (String) mav.get("assignmentStatusDisplayName");
		StringBuilder fileName = new StringBuilder(documentType);
		if(CommonUtil.isNotNullAndEmpty(filterByCategory)){
			fileName.append("_").append(filterByCategory);
		}
		fileName.append("_").append(filerCondition);
		if(CommonUtil.isNotNullAndEmpty(assignmentStatusDisplayName)){
			assignmentStatusDisplayName = assignmentStatusDisplayName.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
			fileName.append("_").append(assignmentStatusDisplayName);
		}
		fileName.append("_").append("Assignments.pdf");
		setAttachmentHeaders(resp, fileName);
		String str = "<html><head></head><body>"+
				"<p align='middle' style='text-align:center;font-weight:bold'><span style='color:blue'>"+documentType+"</span><span style='color:orange'>"+filerCondition+"</span> <span style='color:red'>"+filterByCategory+"</span> <span style='color:green'>"+assignmentStatusDisplayName+"</span> <span>Assignments</span></p>"+
				"</body></html>";
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(str));
		int columnCount = 2;
		boolean isRegionNotAvailable = false;
		boolean isAssignmentStatusNotAvailable = false;
		if(CommonUtil.isNullOrEmpty(filterByCategory)){
			isRegionNotAvailable = true;
			columnCount++;
		}
		if(CommonUtil.isNullOrEmpty(assignmentStatusDisplayName)){
			isAssignmentStatusNotAvailable = true;
			columnCount++;
		}
		PdfPTable  table = new PdfPTable(columnCount);
		table.setWidthPercentage(100);
		if(isRegionNotAvailable && isAssignmentStatusNotAvailable){
			table.setWidths(new int[]{65,90, 160, 120});
		}else if(isRegionNotAvailable){
			table.setWidths(new int[]{65, 90, 120});
		}else if(isAssignmentStatusNotAvailable){
			table.setWidths(new int[]{65, 160, 120});
		}else{
			table.setWidths(new int[]{65,120});
		}
		PdfPCell cell = new PdfPCell(new Phrase("stateno"));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		//table.addCell("stateno");
		if(isRegionNotAvailable){
			cell = new PdfPCell(new Phrase(filterByCategoryColumnName));
			cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
			table.addCell(cell);
			//table.addCell("Region");
		}
		if(isAssignmentStatusNotAvailable){
			cell = new PdfPCell(new Phrase("Assignment Status"));
			cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
			table.addCell(cell);
			//table.addCell("Assignment Status");
		}
		cell = new PdfPCell(new Phrase("Worker Name"));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		//table.addCell("Worker Name");
		table.setHeaderRows(columnCount);
		//htmlContent.append("<th>Worker Name</th></tr></thead><tbody>");
		if(assignmentsList != null && assignmentsList.size() > 0){
			for (Map<String, String> assignment : assignmentsList) {
				table.addCell(assignment.get("STATENO"));
				if(isRegionNotAvailable){
					table.addCell(assignment.get("CATEGORY_DESCRIPTION"));
				}
				if(isAssignmentStatusNotAvailable){
					String ASSIGNMENT_STATUS = assignment.get("ASSIGNMENT_STATUS");
					if(CommonUtil.isNotNullAndEmpty(ASSIGNMENT_STATUS)){
						ASSIGNMENT_STATUS = ASSIGNMENT_STATUS.replaceAll("(?i)aids", "950").replaceAll("(?i)hiv", "900").replaceAll("(?i)adap", "HUCP");
					}
					table.addCell(ASSIGNMENT_STATUS);
				}
				table.addCell(assignment.get("WORKER_NAME"));
				table.completeRow();
			}
		}else{
			table.addCell("No Assignment data available.");
		}
		table.addCell("");
		if(isRegionNotAvailable){
			table.addCell("");
		}
		if(isAssignmentStatusNotAvailable){
			table.addCell("");
		}
		table.addCell("");
		table.addCell("");
		if(isRegionNotAvailable){
			table.addCell("");
		}
		if(isAssignmentStatusNotAvailable){
			table.addCell("");
		}
		table.addCell("");
		//
		document.add(table);
	}

}
