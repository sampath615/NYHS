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

/**
 * PDF download of an upload result: the model's "resultMessage" as a centred
 * title, followed by a two-column table with one row per entry of the model's
 * "resultMap" (key, message).
 * <p>
 * Subclasses only supply the file name and the two column headings.
 */
public abstract class AbstractKeyValuePdfView extends AbstractITextPdfView {

	private final String fileBaseName;
	private final String keyHeading;
	private final String valueHeading;

	/**
	 * @param fileBaseName name of the downloaded file, without the ".pdf" extension
	 * @param keyHeading   heading of the first column (the map keys)
	 * @param valueHeading heading of the second column (the map values)
	 */
	protected AbstractKeyValuePdfView(String fileBaseName, String keyHeading, String valueHeading) {
		this.fileBaseName = fileBaseName;
		this.keyHeading = keyHeading;
		this.valueHeading = valueHeading;
	}

	@Override
	protected void buildPdfDocument(
			Map<String, Object> mav,
			Document document,
			PdfWriter writer,
			HttpServletRequest req,
			HttpServletResponse resp) throws Exception
	{
		@SuppressWarnings("unchecked")
		Map<Object, Object> resultMap = (Map<Object, Object>) mav.get("resultMap");
		String resultMessage = (String) mav.get("resultMessage");
		setAttachmentHeaders(resp, fileBaseName + ".pdf");
		String str = "<html><head></head><body>"+
				"<p align='middle' style='text-align:center;font-weight:bold'> <span>"+resultMessage+"</span></p>"+
				"</body></html>";
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(str));
		int columnCount = 2;
		PdfPTable table = new PdfPTable(columnCount);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{50,135});
		PdfPCell cell = new PdfPCell(new Phrase(keyHeading));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(valueHeading));
		cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
		table.addCell(cell);
		table.setHeaderRows(columnCount);
		for (Object key : resultMap.keySet()) {
			table.addCell(key.toString());
			table.addCell((resultMap.get(key)).toString());
			table.completeRow();
		}
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell("");
		document.add(table);
	}
}
