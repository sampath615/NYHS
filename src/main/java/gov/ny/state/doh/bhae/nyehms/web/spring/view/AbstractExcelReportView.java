package gov.ny.state.doh.bhae.nyehms.web.spring.view;

import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Base class for the Excel report downloads. Holds the style and response
 * set-up that every report used to repeat.
 * <p>
 * Renders an Excel 97-2003 (.xls) workbook the same way Spring's
 * {@code AbstractXlsView} did (that class is deprecated for removal in
 * Spring 7): the subclass fills a new {@link HSSFWorkbook}, then the content
 * type is set to {@code application/vnd.ms-excel} and the workbook is written
 * to the response.
 */
public abstract class AbstractExcelReportView extends AbstractView {

	protected AbstractExcelReportView() {
		setContentType("application/vnd.ms-excel");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Workbook workbook = new HSSFWorkbook();
		buildExcelDocument(model, workbook, request, response);
		response.setContentType(getContentType());
		ServletOutputStream out = response.getOutputStream();
		try (workbook) {
			workbook.write(out);
		}
	}

	/**
	 * Fills the workbook from the model.
	 */
	protected abstract void buildExcelDocument(Map<String, Object> model, Workbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * Marks the response as a spreadsheet download with the given file name.
	 */
	protected static void setAttachmentHeaders(HttpServletResponse response, CharSequence fileName) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	/**
	 * Sets the same border on all four sides of the cell style.
	 */
	protected static void setBorders(CellStyle style, BorderStyle border) {
		style.setBorderTop(border);
		style.setBorderBottom(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
	}

	/**
	 * Fills the cell style with a solid background color.
	 */
	protected static void setSolidFill(CellStyle style, IndexedColors color) {
		style.setFillForegroundColor(color.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}

	/**
	 * Creates a Calibri font of the given size.
	 */
	protected static Font createCalibriFont(Workbook workbook, int points, boolean bold) {
		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) points);
		if (bold) {
			font.setBold(true);
		}
		return font;
	}
}
