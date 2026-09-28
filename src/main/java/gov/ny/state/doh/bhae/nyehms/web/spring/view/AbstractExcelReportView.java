package gov.ny.state.doh.bhae.nyehms.web.spring.view;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

/**
 * Base class for the Excel report downloads. Holds the style and response
 * set-up that every report used to repeat.
 */
public abstract class AbstractExcelReportView extends AbstractXlsView {

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
