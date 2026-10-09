package gov.ny.state.doh.bhae.nyehms.web.spring.view.review;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import gov.ny.state.doh.bhae.nyehms.web.spring.view.AbstractExcelReportView;
import org.springframework.stereotype.Component;

@Component("generateClericalReportsByDailyStaff")
public class GenerateClericalReportByDailyStaff extends AbstractExcelReportView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date now = new Date();
		String timestamp = sdf.format(now);
		String reportType = (String) model.get("reportType");
		String fileName = null;
		if (reportType.equals("Clerical Death Review")) {
			fileName = "CLERICAL_DEATH_REPORT_REVIEWED_BY_STAFF_" + timestamp + ".XLS";
		} else {
			fileName = "CLERICAL_REPORT_REVIEWED_BY_STAFF_" + timestamp + ".XLS";
		}

		@SuppressWarnings("unchecked")
		Map<String, Map<String, Map<String, BigInteger>>> userDetails = (Map<String, Map<String, Map<String, BigInteger>>>) model
				.get("generateClericalReport");

		@SuppressWarnings("unchecked")
		Map<String, Map<String, BigInteger>> totalCountByUser = (Map<String, Map<String, BigInteger>>) model
				.get("userTotalCountByName");

		List<String> userNamesList = new ArrayList<>();
		userNamesList.addAll(totalCountByUser.keySet());

		String fromDate = (String) model.get("fromDate");
		String toDate = (String) model.get("toDate");

		setAttachmentHeaders(response, fileName);
		Sheet sheet = null;
		if (reportType.equals("Clerical Death Review")) {
			sheet = workbook.createSheet("CLERICAL DEATH REPORT REVIEWED BY STAFF");
		} else {
			sheet = workbook.createSheet("CLERICAL REPORT REVIEWED BY STAFF");
		}

		sheet.setDefaultColumnWidth(11);
		sheet.setColumnWidth(0, 7000);
		sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, totalCountByUser.size() * 2 + 2)); // Merged region is for
																								// title row

		CellStyle style = workbook.createCellStyle();
		setSolidFill(style, IndexedColors.LIGHT_GREEN);
		setBorders(style, BorderStyle.MEDIUM);
		style.setAlignment(HorizontalAlignment.CENTER);

		Font font = createCalibriFont(workbook, 14, false);
		style.setFont(font);
		style.setWrapText(true);

		CellStyle boldStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);
		setBorders(boldStyle, BorderStyle.MEDIUM);
		setSolidFill(boldStyle, IndexedColors.LIGHT_TURQUOISE);

		// Create title row in the merged region
		Row header0 = sheet.createRow(0);
		header0.setHeight((short) 400);
		if (reportType.equals("Clerical Death Review")) {
			header0.createCell(0).setCellValue(
					"Daily Count of Clerical Death Reviewed by Staff \n from " + fromDate + " through " + toDate);
		} else {
			header0.createCell(0).setCellValue(
					"Daily Count of Clerical Reviewed by Staff \n from " + fromDate + " through " + toDate);
		}
		header0.getCell(0).setCellStyle(style);

		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		Row header2 = sheet.createRow(3);
		int columnIndex = 0;
		header2.createCell(columnIndex).setCellValue("Staff ID");
		header2.getCell(columnIndex++).setCellStyle(style);

		// Add staff names
		for (String workerName : userNamesList) {
			sheet.addMergedRegion(new CellRangeAddress(3, 3, columnIndex, columnIndex + 1));
			header2.createCell(columnIndex).setCellValue(workerName);
			header2.getCell(columnIndex++).setCellStyle(style);
			header2.createCell(columnIndex++).setCellStyle(style);
		}

		// Add total columns
		sheet.addMergedRegion(new CellRangeAddress(3, 3, columnIndex, columnIndex + 1));
		header2.createCell(columnIndex).setCellValue("Total");
		header2.getCell(columnIndex++).setCellStyle(style);
		header2.createCell(columnIndex++).setCellStyle(style);

		// Add subtitle row
		Row header3 = sheet.createRow(4);
		columnIndex = 0;
		header3.createCell(columnIndex).setCellValue("Date");
		header3.getCell(columnIndex++).setCellStyle(style);
		for (String workerName : totalCountByUser.keySet()) {
			header3.createCell(columnIndex).setCellValue("Match");
			header3.getCell(columnIndex++).setCellStyle(style);
			header3.createCell(columnIndex).setCellValue("Non Match");
			header3.getCell(columnIndex++).setCellStyle(style);
		}
		header3.createCell(columnIndex).setCellValue("Match");
		header3.getCell(columnIndex++).setCellStyle(style);
		header3.createCell(columnIndex).setCellValue("Non Match");
		header3.getCell(columnIndex++).setCellStyle(style);

		int index = 5;

		for (String createDate : userDetails.keySet()) {
			Row aRow = sheet.createRow(index++);
			Map<String, Map<String, BigInteger>> dataMapByUsers = userDetails.get(createDate);
			columnIndex = 0;
			aRow.createCell(columnIndex++).setCellValue(createDate);
			BigInteger totalMatchCountByDay = BigInteger.ZERO;
			BigInteger totalNonMatchCountByDay = BigInteger.ZERO;

			if (dataMapByUsers != null) {
				for (String workerName : userNamesList) {
					Map<String, BigInteger> dataMapByStatus = dataMapByUsers.get(workerName);
					if (dataMapByStatus != null) {
						BigInteger matchCount = dataMapByStatus.get("Match");
						BigInteger nonmatchCount = dataMapByStatus.get("NonMatch");
						if (matchCount == null) {
							aRow.createCell(columnIndex++).setCellValue("");
						} else {
							aRow.createCell(columnIndex++).setCellValue(matchCount.intValue());
							totalMatchCountByDay = totalMatchCountByDay.add(matchCount);
						}
						if (nonmatchCount == null) {
							aRow.createCell(columnIndex++).setCellValue("");
						} else {
							
							aRow.createCell(columnIndex++).setCellValue(nonmatchCount.intValue());
							totalMatchCountByDay = totalMatchCountByDay.add(nonmatchCount);
						}
					} else {
						columnIndex = columnIndex + 2;
					}
				}
			} else {
				columnIndex = columnIndex + userNamesList.size() * 2;
			}

			if (totalMatchCountByDay.intValue() >= 0) {
				aRow.createCell(columnIndex++).setCellValue(totalMatchCountByDay.intValue());
			} else {
				aRow.createCell(columnIndex++).setCellValue(0);
			}
			if (totalNonMatchCountByDay.intValue() >= 0) {
				aRow.createCell(columnIndex++).setCellValue(totalNonMatchCountByDay.intValue());
			} else {
				aRow.createCell(columnIndex++).setCellValue(0);
			}
		}

		// Total Report
		columnIndex = 0;
		Row aRow = sheet.createRow(index++);
		aRow.createCell(columnIndex).setCellValue("Total For Date Range");
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);
		BigInteger totalMatchCount = BigInteger.ZERO;
		BigInteger totalNonMatchCount = BigInteger.ZERO;

		for (String workerName : userNamesList) {
			Map<String, BigInteger> totalCountByStatus = totalCountByUser.get(workerName);
			if (totalCountByStatus != null) {
				BigInteger matchCount = totalCountByStatus.get("Match");
				BigInteger nonmatchCount = totalCountByStatus.get("NonMatch");

				if (matchCount == null) {
					aRow.createCell(columnIndex).setCellValue(0);
				} else {
					aRow.createCell(columnIndex).setCellValue(matchCount.intValue());
					totalMatchCount = totalMatchCount.add(matchCount);
				}
				aRow.getCell(columnIndex++).setCellStyle(boldStyle);

				if (nonmatchCount == null) {
					aRow.createCell(columnIndex).setCellValue(0);
				} else {
					aRow.createCell(columnIndex).setCellValue(nonmatchCount.intValue());
					totalNonMatchCount = totalNonMatchCount.add(nonmatchCount);
				}
				aRow.getCell(columnIndex++).setCellStyle(boldStyle);
			} else {
				aRow.createCell(columnIndex).setCellValue(0);
				aRow.createCell(columnIndex++).setCellStyle(boldStyle);
				aRow.createCell(columnIndex).setCellValue(0);
				aRow.createCell(columnIndex++).setCellStyle(boldStyle);
			}
		}

		if (totalMatchCount.intValue() >= 0) {
			aRow.createCell(columnIndex).setCellValue(totalMatchCount.intValue());
		} else {
			aRow.createCell(columnIndex).setCellValue(0);
		}
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);

		if (totalNonMatchCount.intValue() >= 0) {
			aRow.createCell(columnIndex).setCellValue(totalNonMatchCount.intValue());
		} else {
			aRow.createCell(columnIndex).setCellValue(0);
		}
		aRow.getCell(columnIndex++).setCellStyle(boldStyle);

	}
}