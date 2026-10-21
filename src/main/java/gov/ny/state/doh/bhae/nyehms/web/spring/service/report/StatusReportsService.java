package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

import gov.ny.state.doh.bhae.nyehms.web.spring.config.ReportProperties;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jakarta.activation.DataHandler;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.jcraft.jsch.ChannelSftp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.report.StatusLabReportsDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab.LabReportsCountVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.RemoteServerUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;


/**
* This class is service to use generate the weekly and monthly lab report 
*  
* @author  
* @version 
*/
@Service
public class StatusReportsService {

	@Autowired
	private ReportProperties reportProperties;

	@Autowired
	private StatusLabReportsDAO statusLabReportsDAO ;	

	@Autowired
	RemoteServerUtil remoteServer;
	
	@Autowired
	SendEmail sendEmail;
	
	
	private static Logger log = LoggerFactory.getLogger(StatusReportsService.class);	

	
	public void generateLabReportCount(String sqlQuery){
		
		
        ChannelSftp obj_SFTPChannel = null;
    	PdfWriter writer = null;
		PdfDocument pdfDocument = null;
		Configuration configuration = null;
		ConverterProperties converterProperties = new ConverterProperties();
		InputStream obj_InputStream = null;
		ByteArrayDataSource byteArrayDataSource = null;
		MimeBodyPart messageBodyPart2 = null;
        
        obj_SFTPChannel = remoteServer.getRemoteServerSession();
        
        if(obj_SFTPChannel == null){
               throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
        }
        
        Map<String, String> inputMap = new HashMap<>();
		
		inputMap.put("SQL_QUERY", sqlQuery);
		
		List<LabReportsCountVO> labReportsCountList= statusLabReportsDAO.getLabReportCount(inputMap);
        
		Map<String, Map<String, Map<String, Map<String, Number>>>> dataSetMap = new LinkedHashMap<String, Map<String, Map<String, Map<String, Number>>>>();
		
		Map<String, Map<String, Integer>> dataSetweeklyCount = new HashMap<>();
		
		//Map<String, Float> currentYearPercentageMap = new LinkedHashMap<String, Float>();
		
		parseReportsCount(labReportsCountList, dataSetMap, dataSetweeklyCount);
        
		Map<String, Object> labReportData = new HashMap<>();
		
		labReportData.put("dataSetMap", dataSetMap);
		labReportData.put("dataSetweeklyCount", dataSetweeklyCount);
		//labReportData.put("currentYearPercentageMap", currentYearPercentageMap);
		
		Map<String, String> headerNotes = new HashMap<>();
		headerNotes.put("1", reportProperties.labCount().dataSet1());
		headerNotes.put("2", reportProperties.labCount().dataSet2());
		headerNotes.put("3", reportProperties.labCount().dataSet3());
		headerNotes.put("4", reportProperties.labCount().dataSet4());
        
		labReportData.put("headerNotes", headerNotes);
		
		labReportData.put("footerNote", reportProperties.labCount().notes());
        try {
        	
        	
        	String formatedDate = CommonUtil.convertDateToStringFormat(new Date(), "YYYY-MM-dd");
			String slashedFormatedDate = CommonUtil.convertDateToStringFormat(new Date(), "MM/dd/YYYY");
			labReportData.put("formattedDate", slashedFormatedDate);
        	
        	configuration = prepareConfiguration();
        	
    		configuration.setClassForTemplateLoading(this.getClass(), "/templates/");

    		Template template = configuration.getTemplate("weeklyLabReport.ftl");

            StringWriter responseContent = new StringWriter();
            template.process(labReportData, responseContent);
            
			         
	        String filePath = reportProperties.outputPath();
	        String fileName = (new StringBuilder(formatedDate).append("_").append(reportProperties.labCount().fileName()).toString());
            writer= new PdfWriter(obj_SFTPChannel.put(filePath+fileName));
            pdfDocument = new PdfDocument(writer);
 	        HtmlConverter.convertToPdf(responseContent.toString(), pdfDocument, converterProperties);
 	        pdfDocument.close();
	
 	        obj_InputStream = obj_SFTPChannel.get(filePath+fileName);
 	        byteArrayDataSource = new ByteArrayDataSource(obj_InputStream, "application/pdf"); 
 	        messageBodyPart2 = new MimeBodyPart();
 	        messageBodyPart2.setDataHandler(new DataHandler(byteArrayDataSource));
 	        messageBodyPart2.setFileName(fileName);  
	     	        
     	    sendEmail.sendemailAttachmentsByIdentifier("BHAE Received Weekly Lab Results Report "+slashedFormatedDate, "Please see attached report of BHAE received lab results.", CommonConstants.RESPONSE_CONTENT_TYPE_HTML, "WEEKLY_LOINC", messageBodyPart2);
     	    		
        } catch (IOException | TemplateException e) {
        	
        	log.error(e.getMessage());
            throw new RuntimeException(e);
        	 
		
		}catch(Exception e) {
			
			String transactionMessage = "Exception occured while creating the lab report by weekly :"+e.getMessage();
			
			log.error(transactionMessage);
			log.error(CommonUtil.getStackTrace(e));
			
			throw new DataRetrievalFailureException(transactionMessage);
			
		}finally {
			try {
				//workbook.close();
				
	             if(obj_SFTPChannel != null) {
						obj_SFTPChannel.disconnect();
				 }
	             if(writer != null) {
	            	 writer.close();
	             }
	             if(pdfDocument != null) {
	            	 pdfDocument.close();
	             }
	             if(obj_InputStream != null) {
	            	 obj_InputStream.close();
	             }
	             
			} catch (IOException e) {

				String transactionMessage = "Exception occured while closing the JSCH channel :"+e.getMessage();
				
				log.error(transactionMessage);
				log.error(CommonUtil.getStackTrace(e));
				
				throw new DataRetrievalFailureException(transactionMessage);

			}
		}
        
	}

	
	private void parseReportsCount(List<LabReportsCountVO> labReportsCountList, Map<String, Map<String, Map<String, Map<String, Number>>>> dataSetMap,
			Map<String, Map<String, Integer>> dataSetweeklyCount) {
		
		for (LabReportsCountVO labReportsCountVO : labReportsCountList) {
			
			String dataset = Integer.toString(labReportsCountVO.getDataSet());
			String dateFrom = labReportsCountVO.getWeekStartDate();
			String dateEnd = labReportsCountVO.getWeekEndDate();
			String testType = labReportsCountVO.getReportTestType();
			Integer count = labReportsCountVO.getCount();
			Float percentage = labReportsCountVO.getPercentage();
			
			Map<String, Map<String, Map<String, Number>>>  testTypeMap = null;
			Map<String, Map<String, Number>> weeklyMap = null;
			
			if(dataSetMap.get(dataset) == null) {
				testTypeMap = new TreeMap<String, Map<String, Map<String, Number>>>();
			}else {
				testTypeMap = dataSetMap.get(dataset);
			}
			
			if(testTypeMap.get(testType) == null) {
				weeklyMap = new LinkedHashMap<String, Map<String, Number>>();
			}else {
				weeklyMap = testTypeMap.get(testType);
			}
			
			Map<String, Number> testTypeCountsByDate = new HashMap<>();
			
			testTypeCountsByDate.put("COUNT", count);
			testTypeCountsByDate.put("PERCENTAGE", percentage);
			
			String header = null;
			
				header = dateFrom + " - " + dateEnd;
			//}
			
			
			weeklyMap.put(header, testTypeCountsByDate);
			testTypeMap.put(testType, weeklyMap);
			dataSetMap.put(dataset, testTypeMap);
			
			//Weekly total by dataset
			Map<String, Integer>  weeklyCountMap = null;
			
			if(dataSetweeklyCount.get(dataset) == null) {
				weeklyCountMap = new LinkedHashMap<String, Integer>();
			}else {
				weeklyCountMap = dataSetweeklyCount.get(dataset);
			}
			
			if(weeklyCountMap.get(header) == null) {
				weeklyCountMap.put(header, count);
			}else {
				weeklyCountMap.put(header, count+weeklyCountMap.get(header));
			}
//			
			dataSetweeklyCount.put(dataset, weeklyCountMap);
//			
//			if("4".equals(dataset) || "5".equals(dataset) || "6".equals(dataset)) {
//				//3rd set Percentage Map
			
			
			
			
		}
		
		
		
		
	}
	
	private Configuration prepareConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        return configuration;
    }
	
	public void generateMontlyLabReportCount(String reportType) {
		
		ChannelSftp obj_SFTPChannel = null;
    	PdfWriter writer = null;
		InputStream obj_InputStream = null;
		ByteArrayDataSource byteArrayDataSource = null;
		MimeBodyPart messageBodyPart2 = null;
        
        obj_SFTPChannel = remoteServer.getRemoteServerSession();
        
        if(obj_SFTPChannel == null){
               throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
        }
        
        

		Map<String, Object> inputParameterMap = new HashMap<>();
		
		String[] initialColumns = null;
		String sheetName = null;
		int rownum = 0;
		
		if("byRegion".equals(reportType)) {
			initialColumns = reportProperties.loincMonthly().byRegionColumns1().split(",");
			statusLabReportsDAO.monthlyLabReportByRegion(inputParameterMap);
			sheetName = "Lab Report by region";
			rownum = 4;
		}else {
			initialColumns = reportProperties.loincMonthly().columns1().split(",");
			statusLabReportsDAO.monthlyLoincTestReport(inputParameterMap);
			sheetName = "Lab Report";
		}
			
		List<Map<String, Object>> loincReportData = (List<Map<String, Object>>)inputParameterMap.get("loincReportData");
		
		Set<String> reportColumns = loincReportData.get(0).keySet();

		List<String> columns = new ArrayList<>(reportColumns);
		
		String[] endingColumns = reportProperties.loincMonthly().columns2().split(",");
		
		columns.removeAll(Arrays.asList(initialColumns));
		columns.removeAll(Arrays.asList(endingColumns));
		
		DateFormat columnDateFormat = new SimpleDateFormat(reportProperties.loincMonthly().dateFormat());
		columns.sort((o1, o2) -> {
			try {
				return columnDateFormat.parse(o1).compareTo(columnDateFormat.parse(o2));
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		});
				
		XSSFWorkbook workbook = new XSSFWorkbook(); 
    	
        try {
        	
            XSSFSheet sheet = workbook.createSheet(sheetName);
            
            
            CellStyle headerLabelStyle = workbook.createCellStyle();
     		
            headerLabelStyle.setBorderBottom(BorderStyle.THIN);
            headerLabelStyle.setBorderTop(BorderStyle.THIN);
            headerLabelStyle.setBorderLeft(BorderStyle.THIN);
            headerLabelStyle.setBorderRight(BorderStyle.THIN);
            headerLabelStyle.setWrapText(true);
             
            Font headerLabelfont = workbook.createFont();
            headerLabelfont.setBold(true);
//            headerLabelfont.setColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerLabelfont.setFontHeight((short)(15*20));

            headerLabelStyle.setBorderBottom(BorderStyle.NONE);
            headerLabelStyle.setBorderTop(BorderStyle.NONE);
            headerLabelStyle.setBorderLeft(BorderStyle.NONE);
            headerLabelStyle.setBorderRight(BorderStyle.NONE);
            headerLabelStyle.setFont(headerLabelfont);
            
            
            
            CellStyle headerStyle = workbook.createCellStyle();
     		
     		headerStyle.setBorderBottom(BorderStyle.THIN);
     		headerStyle.setBorderTop(BorderStyle.THIN);
     		headerStyle.setBorderLeft(BorderStyle.THIN);
     		headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setWrapText(true);
             
            Font font = workbook.createFont();
            font.setBold(true);
//            font.setColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            font.setFontHeight((short)(13*20));
            headerStyle.setFont(font);
             
             
             
             CellStyle bodyStyle = workbook.createCellStyle();
             bodyStyle.setBorderBottom(BorderStyle.THIN);
             bodyStyle.setBorderTop(BorderStyle.THIN);
             bodyStyle.setBorderLeft(BorderStyle.THIN);
             bodyStyle.setBorderRight(BorderStyle.THIN);
             
             
             CellStyle footerStyle = workbook.createCellStyle();
             footerStyle.setBorderBottom(BorderStyle.THIN);
             footerStyle.setBorderTop(BorderStyle.THIN);
             footerStyle.setBorderLeft(BorderStyle.THIN);
             footerStyle.setBorderRight(BorderStyle.THIN);
             footerStyle.setBorderBottom(BorderStyle.NONE);
             footerStyle.setBorderTop(BorderStyle.NONE);
             footerStyle.setBorderLeft(BorderStyle.NONE);
             footerStyle.setBorderRight(BorderStyle.NONE);
             footerStyle.setWrapText(true);
        	
             Font footerFont = workbook.createFont();
             footerFont.setFontHeight((short)(9*20));
             footerStyle.setFont(footerFont);
        	
        	
             if("byRegion".equals(reportType)) {
             	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 21)); //Merged region is for title row
             	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 21));
             	sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 21));
             	sheet.setColumnWidth(0, 400 );
                //Create title row in the merged region
                sheet.createRow(0);    
                Row header1 = sheet.createRow(1);  
                Row header2 = sheet.createRow(2);  

                header1.setHeight((short)400);  
                header2.setHeight((short)400);    
                String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
                Calendar c = Calendar.getInstance();
                header1.createCell(0).setCellValue("Laboratory Reporting by Region, Sending Facility CLIA, Test Type and  Month");
                header1.getCell(0).setCellStyle(headerLabelStyle); 
                

                
                int previousMonth = c.get(2);
                int currentMonth = c.get(2)-1;
                int currentYear = c.get(1);
                int previousYear = c.get(1)-1;
                if(currentMonth == -1) {
                	currentMonth = 11;
                	currentYear = currentYear - 1;
                }
                
                header2.createCell(0).setCellValue(monthNames[previousMonth]+(previousYear)+" through "+monthNames[currentMonth]+(currentYear));
                header2.getCell(0).setCellStyle(headerLabelStyle); 
                
             }
            
        	
        	Row row = sheet.createRow(rownum++);

        	int headerCellIndex = 0;
        	for (String key : initialColumns)
        	{
        		
        		Cell cell = row.createCell(headerCellIndex);
        		cell.setCellValue(key);
        		cell.setCellStyle(headerStyle);
        		headerCellIndex++;
        		
          	
        	}
            for (String key : columns)
            {
            	Cell cell = row.createCell(headerCellIndex);
            	cell.setCellValue(key);
        		cell.setCellStyle(headerStyle);
        		headerCellIndex++;
            	
            }
            for (String key : endingColumns)
        	{
            	Cell cell = row.createCell(headerCellIndex);
            	
        		cell.setCellValue(key);
        		cell.setCellStyle(headerStyle);
        		headerCellIndex++;
          	
        	}
            
            
            
            
        	for (Map<String, Object> testTypeData : loincReportData) {
        		row = sheet.createRow(rownum++);
        		
        		int cellnum = 0;
        		
        		for (String key : initialColumns) {
        			Object cellValue = testTypeData.get(key);
                    
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(bodyStyle);
                    
                    if (cellValue == null) {
                        cell.setCellValue("");
                      } else if (cellValue instanceof BigDecimal decimal) {
                    	  cell.setCellValue(decimal.doubleValue());
                      }else {
                    	  cell.setCellValue((String)cellValue);
                      }
        		}
        			
                for (String key : columns)
                {
                    Object cellValue = testTypeData.get(key);
                    
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(bodyStyle);
                    
                    if (cellValue == null) {
                        cell.setCellValue("");
                      } else if (cellValue instanceof BigDecimal decimal) {
                    	  cell.setCellValue(decimal.doubleValue());
                      }else {
                    	  cell.setCellValue((String)cellValue);
                      }
                    
                }
                for (String key : endingColumns) {
        			Object cellValue = testTypeData.get(key);
                    
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(bodyStyle);
                    
                    if (cellValue == null) {
                        cell.setCellValue("");
                      } else if (cellValue instanceof BigDecimal decimal) {
                    	  cell.setCellValue(decimal.doubleValue());
                      }else {
                    	  cell.setCellValue((String)cellValue);
                      }
        		}
			}
        	
        	if("byRegion".equals(reportType)) {
        	
	        	sheet.createRow(rownum++);    
	        	sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 21)); //Merged region is for title row
	        	
	        	//Create title row in the merged region
	            Row footer1 = sheet.createRow(rownum++);        
	            footer1.createCell(0).setCellValue("Specimen collection date in NYEHMS was used for date range.");
	            footer1.getCell(0).setCellStyle(footerStyle); 
	            
	            
	            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 21)); //Merged region is for title row
	        	
	        	//Create title row in the merged region
	            Row footer2 = sheet.createRow(rownum++);        
	            footer2.createCell(0).setCellValue("Percent change calculated using difference of 12 months total and first 11 months average");
	            footer2.getCell(0).setCellStyle(footerStyle); 
	            
	            

	            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 21)); //Merged region is for title row
	        	
	        	//Create title row in the merged region
	            Row footer3 = sheet.createRow(rownum++);        
	            footer3.createCell(0).setCellValue("Priority 1 is where percent change is less than -20");
	            footer3.getCell(0).setCellStyle(footerStyle); 
	            
	            

	            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 21)); //Merged region is for title row
	        	
	        	//Create title row in the merged region
	            Row footer4 = sheet.createRow(rownum++);        
	            footer4.createCell(0).setCellValue("Priority 2 is where percent change is less than 0 AND greater than or equal to -20");
	            footer4.getCell(0).setCellStyle(footerStyle); 
	            
	            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 21)); //Merged region is for title row
	        	
	        	//Create title row in the merged region
	            Row footer5 = sheet.createRow(rownum++);        
	            footer5.createCell(0).setCellValue("The ordering provider's county information as used for regional category");
	            footer5.getCell(0).setCellStyle(footerStyle); 
	            
	            
	            
	            
        	}
        	
        	for(int columnIndex = 0; columnIndex <= headerCellIndex; columnIndex ++) {
        		sheet.autoSizeColumn(columnIndex);
        	}
        	
        	String formatedDate = CommonUtil.convertDateToStringFormat(new Date(), "YYYY-MM-dd");
			String slashedFormatedDate = CommonUtil.convertDateToStringFormat(new Date(), "MM/dd/YYYY");
			
        	
        	String filePath = reportProperties.loincMonthly().pathName();
	        String fileName = null;
	        
	        if("byRegion".equals(reportType)) {
	        	fileName = (new StringBuilder(formatedDate).append("_").append(reportProperties.loincMonthly().fileNameByRegion()).toString());
	        }else {
	        	fileName = (new StringBuilder(formatedDate).append("_").append(reportProperties.loincMonthly().fileName()).toString());
	        }
        	
        	 OutputStream outStream = obj_SFTPChannel.put(filePath+fileName);
             
        	 workbook.write(outStream);
 	        obj_InputStream = obj_SFTPChannel.get(filePath+fileName);
 	        byteArrayDataSource = new ByteArrayDataSource(obj_InputStream, "application/pdf"); 
 	        messageBodyPart2 = new MimeBodyPart();
 	        messageBodyPart2.setDataHandler(new DataHandler(byteArrayDataSource));
 	        messageBodyPart2.setFileName(fileName); 
 	       if("byRegion".equals(reportType)) {
 	    	   sendEmail.sendemailAttachmentsByIdentifier("BHAE Monthly Lab Reporting By Region "+slashedFormatedDate, "Please see attached report of BHAE lab reporting by region.", CommonConstants.RESPONSE_CONTENT_TYPE_HTML, "MONTHLY_LOINC", messageBodyPart2);
 	       }else {
 	    	  sendEmail.sendemailAttachmentsByIdentifier("BHAE Received Monthly Lab Results Report "+slashedFormatedDate, "Please see attached report of BHAE received lab results.", CommonConstants.RESPONSE_CONTENT_TYPE_HTML, "MONTHLY_LOINC", messageBodyPart2);
 	 	       
 	       }
		}
        catch(Exception e) {
			
			String transactionMessage = "Exception occured while creating the lab report by weekly :"+e.getMessage();
			
			log.error(transactionMessage);
			log.error(CommonUtil.getStackTrace(e));
			
			throw new DataRetrievalFailureException(transactionMessage);
			
		}finally {
			try {
				//workbook.close();
				
	             if(obj_SFTPChannel != null) {
						obj_SFTPChannel.disconnect();
				 }
	             if(writer != null) {
	            	 writer.close();
	             }
	             if(workbook != null) {
	            	 workbook.close();
	             }
	             if(obj_InputStream != null) {
	            	 obj_InputStream.close();
	             }
	             
			} catch (IOException e) {

				String transactionMessage = "Exception occured while closing the JSCH channel :"+e.getMessage();
				
				log.error(transactionMessage);
				log.error(CommonUtil.getStackTrace(e));
				
				throw new DataRetrievalFailureException(transactionMessage);

			}
		}
		
	}
	
}


