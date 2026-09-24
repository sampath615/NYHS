package gov.ny.state.doh.bhae.nyehms.web.spring.view.uploads;

import java.io.StringReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.assignment.AbstractITextPdfView;

public class UpdatedMessagesPdfView extends AbstractITextPdfView{


	/**
	 * 
	 * @param mav
	 * @param document
	 * @param writer
	 * @param req
	 * @param resp
	 */
	@Override
	protected void buildPdfDocument(
			Map mav,
			Document document,
			PdfWriter writer,
			HttpServletRequest req,
			HttpServletResponse resp) throws Exception
	{
		Map<Object, Object> resultMap =  (Map<Object, Object>)mav.get("resultMap");;
		String resultMessage = (String) mav.get("resultMessage");
		//Calendar calendar = Calendar.getInstance();
		StringBuilder fileName = new StringBuilder("Updated Messages");
		//fileName.append(Calendar.MONTH).append("_").append(calendar.DATE).append("_").append(calendar.YEAR);
		fileName.append(".pdf");
		resp.setContentType("application/pdf");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String str = "<html><head></head><body>"+
				"<p align='middle' style='text-align:center;font-weight:bold'> <span>"+resultMessage+"</span></p>"+
				"</body></html>";
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(str));
		int columnCount = 2;
		PdfPTable  table = new PdfPTable(columnCount);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{50,135});
		PdfPCell cell = new PdfPCell(new Phrase("DOCUMENT ID"));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Message"));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		//table.addCell("Worker Name");
		table.setHeaderRows(columnCount);
		//htmlContent.append("<th>Worker Name</th></tr></thead><tbody>");
		for (Object DOCUMENT_ID : resultMap.keySet()) {
			table.addCell(DOCUMENT_ID.toString());
			table.addCell((resultMap.get(DOCUMENT_ID)).toString());
			table.completeRow();
		}
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		document.add(table);
	}

}
