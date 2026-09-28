package gov.ny.state.doh.bhae.nyehms.web.spring.view.assignment;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public abstract class AbstractITextPdfView extends AbstractView {

	public AbstractITextPdfView() {
		setContentType("application/pdf");
	}

	/**
	 * Marks the response as a PDF download with the given file name.
	 */
	protected static void setAttachmentHeaders(HttpServletResponse response, CharSequence fileName) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {}

	protected abstract void buildPdfDocument(Map<String, Object> model,
			Document document,
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ByteArrayOutputStream out = createTemporaryOutputStream();
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, out);
		writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
		buildPdfMetadata(model, document, request);
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		writeToResponse(response, out);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
}