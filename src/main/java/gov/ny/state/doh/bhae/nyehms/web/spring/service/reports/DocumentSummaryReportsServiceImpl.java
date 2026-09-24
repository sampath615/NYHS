package gov.ny.state.doh.bhae.nyehms.web.spring.service.reports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.reports.DocumentSummaryReportDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.RemoteServerUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.EnvironmentProperties;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;

@Service
public class DocumentSummaryReportsServiceImpl implements DocumentSummaryReportsService {

	private static Logger log = LoggerFactory.getLogger(DocumentSummaryReportsServiceImpl.class);
	

	@Autowired
	RemoteServerUtil remoteServer;
	@Autowired
	SendEmail sendEmail;

	@Autowired
	private DocumentSummaryReportDAO documentSummaryReportDAO ;
	
	private static Configuration prepareConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        return configuration;
    }
	
	
	@Override
	public void generateWeeklyDocumentStatusSummaryReport() throws TemplateNotFoundException, MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException, SftpException, MessagingException  {

		Configuration configuration=null;
		Map<String, Map<String, List<JSONObject>>> finalData = new LinkedHashMap<String, Map<String, List<JSONObject>>>();

		ConverterProperties converterProperties = new ConverterProperties();
		PdfWriter writer = null;
		PdfDocument pdfDocument = null;
		ChannelSftp obj_SFTPChannel = null;
		InputStream obj_InputStream = null;
		ByteArrayDataSource byteArrayDataSource = null;
		MimeBodyPart messageBodyPart2 = null;
        
	    	
		JSONArray sqlQueries = null;
		JSONParser parser = new JSONParser();
		
		
		obj_SFTPChannel = remoteServer.getRemoteServerSession();
        
        if(obj_SFTPChannel == null){
               throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
        }
        
		StringBuilder sqlString = new StringBuilder("");
        
        InputStream stream = obj_SFTPChannel.get(EnvironmentProperties.envProperties.getProperty("LAB_REPORT_PATH_NAME")+"DOCUMENT_REPORT/"+"sql.json");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = br.readLine()) != null) {
            	sqlString.append(line);
            }
            
            Object obj = parser.parse(sqlString.toString());
            
            sqlQueries = (JSONArray)obj;

        } catch (IOException io) {
            log.error("Exception occurred during reading file from SFTP server due to {}", io.getMessage());
            throw new DataRetrievalFailureException(io.getMessage());

        } catch (ParseException e) {
        	log.error("Exception occurred while parsing the sql string {}", e.getMessage());
        	throw new DataRetrievalFailureException(e.getMessage());
		}catch (Exception e) {
        	log.error("Exception occurred during reading file from SFTP server due to {}", e.getMessage());
        	throw new DataRetrievalFailureException(e.getMessage());
        }
        
        
         
        
        
		String referenceColumn = null;
		
		Iterator i = sqlQueries.iterator();
		
		while(i.hasNext()) {
		 
			JSONObject tableRefData = (JSONObject)i.next();
			
			String tableName = (String)tableRefData.get("TABLE_NAME");
			
			referenceColumn = (String)tableRefData.get("REFERENCE_COLUMN");
			
			
			String sqlQuery = (String)tableRefData.get("SQL_QUERY");
			
			Map<String, String> inputParameterMap = new HashMap<String, String>();
			
			inputParameterMap.put("SQL_QUERY", sqlQuery);
			
			List<Map<String, String>> documentSummaryList = documentSummaryReportDAO.getDocumentSummaryData(inputParameterMap); 
			
			if(documentSummaryList == null || documentSummaryList.size() == 0) {
				continue;
			}
			
			
			Map<String, List<JSONObject>> byDocument = new LinkedHashMap<String, List<JSONObject>>();
			
			for (Map<String, String> data : documentSummaryList) {
				
			
	
				String referenceColumnData = data.get(referenceColumn);
				List<JSONObject> byCreateDate = null;
				if(byDocument.get(referenceColumnData) != null) {
					byCreateDate =  byDocument.get(referenceColumnData);
				}else {
					byCreateDate = new ArrayList<JSONObject>();
				}
				
				JSONObject byStatus = new JSONObject();
				byStatus.put("CURRENT_PROCESS_STATUS", data.get("CURRENT_PROCESS_STATUS"));
				byStatus.put("PROCESSES_STATUS_DESC", data.get("PROCESSES_STATUS_DESC"));
				if(tableName.startsWith("STD")) {
					byStatus.put("FLUX_MATCH_CD", data.get("FLUX_MATCH_CD"));
				}
				byStatus.put("COUNT", data.get("COUNT"));
				
				byCreateDate.add(byStatus);
				
				byDocument.put(referenceColumnData, byCreateDate);
			}
			
			if(byDocument != null && byDocument.size() > 0) {
				finalData.put(tableName, byDocument);
			}
			
		}
		
		JSONObject inputData = new JSONObject();
		
		inputData.put("statusSummary", finalData);
		
		inputData.put("REFERENCE_COLUMN", referenceColumn);
		
		

        String formatedDate = CommonUtil.convertDateToStringFormat(new Date(), "YYYY-MM-dd");
        String slashedFormatedDate = CommonUtil.convertDateToStringFormat(new Date(), "MM/dd/YYYY");
        inputData.put("formattedDate", slashedFormatedDate);
		
		configuration = prepareConfiguration();
    	
		configuration.setClassForTemplateLoading(this.getClass(), "/templates/");

		Template template = configuration.getTemplate("documentStatusSummary.ftl");

		StringWriter responseContent = new StringWriter();
		
        template.process(inputData, responseContent);
		try {
        String filePath = EnvironmentProperties.envProperties.getProperty("LAB_REPORT_PATH_NAME");
        
        
        String fileName = (new StringBuilder(formatedDate).append("_").append("DOCUMENT_STATUS_REPORT.pdf").toString());
        
        writer= new PdfWriter(obj_SFTPChannel.put(filePath+"DOCUMENT_REPORT/"+fileName));
        pdfDocument = new PdfDocument(writer);
	        HtmlConverter.convertToPdf(responseContent.toString(), pdfDocument, converterProperties);
	        pdfDocument.close();
	        
	        
	        obj_InputStream = obj_SFTPChannel.get(filePath+"DOCUMENT_REPORT/"+fileName);
 	        byteArrayDataSource = new ByteArrayDataSource(obj_InputStream, "application/pdf"); 
 	        messageBodyPart2 = new MimeBodyPart();
 	        messageBodyPart2.setDataHandler(new DataHandler(byteArrayDataSource));
 	        messageBodyPart2.setFileName(fileName);  
	     	        
 	       String[] emailTo = EnvironmentProperties.envProperties.getProperty("DOCUMENT_STATUS_REPORT_TO").split(",");
     	   String[] emailCC = {};
           sendEmail.sendemailAttachments(emailTo, "Document Status Report "+slashedFormatedDate, "Please see attached document status report.", EnvironmentProperties.envProperties.getProperty("helpLineFrom"), emailCC, CommonConstants.RESPONSE_CONTENT_TYPE_HTML, messageBodyPart2);
     	        
     	      
     	        
     	       
		}finally {
			 if(obj_SFTPChannel != null) {
					obj_SFTPChannel.disconnect();
			 }
          if(writer != null) {
         	 writer.close();
          }
          if(pdfDocument != null) {
         	 pdfDocument.close();
          }

		}
	}
		
}


