package gov.ny.state.doh.bhae.nyehms.web.spring.service.review;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.util.IOUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.MarginsWellKnown;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STPageOrientation;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;
import org.docx4j.wml.SectPr.PgSz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gov.ny.state.doh.bhae.nyehms.web.spring.dao.review.PerinatalReviewDAO;
import gov.ny.state.doh.bhae.nyehms.web.spring.enums.AppConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.scan.RemoteServerUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Service
public class PerinatalService {

	private static final ObjectFactory factory = Context.getWmlObjectFactory();

	private static Logger log = LoggerFactory.getLogger(PerinatalService.class);

	@Autowired
	PerinatalReviewDAO perinatalReviewDAO;

	@Autowired
	RemoteServerUtil remoteServer;

	
	

	public List<Map<String, String>> getFacilityNamesByBirthCriteria(String birthFirstYear, String birthFirstQuarter, String birthSecondYear, String birthSecondQuarter,
			BindingResult bindingResult, UserObject userObject){
		Map<String, String> inputParameterMap = new HashMap<>();

		inputParameterMap.put("birthFirstYear", birthFirstYear);
		inputParameterMap.put("birthFirstQuarter", birthFirstQuarter);
		inputParameterMap.put("birthSecondYear", birthSecondYear);
		inputParameterMap.put("birthSecondQuarter", birthSecondQuarter);
		inputParameterMap.put("FACILITY_NAME", "ALL");
		

		return perinatalReviewDAO.getFacilityNamesByBirthCriteria(inputParameterMap);
	}
	
	public Map<String, Object> getDocumentIdAndPotentialMatchesForStatenos(String documentType, String reviewType,
			BindingResult bindingResult, UserObject userObject){
		
		return null;
	}
	
	public Map<String, Object> loadFacilityMonitoringReport(String birthFirstYear, String birthFirstQuarter, String birthSecondYear, String birthSecondQuarter, 
			String facilityName, String letterDate, BindingResult bindingResult, UserObject userObject) {

		ChannelSftp obj_SFTPChannel = null;
		Configuration configuration = null;
		
		InputStream obj_InputStream = null;
		
		WordprocessingMLPackage attachmentDocxOut = null;
		XHTMLImporterImpl attachmentXHTMLImporter = null;
		
		WordprocessingMLPackage facilityReportDocxOut = null;
		XHTMLImporterImpl facilityReportXHTMLImporter = null;
		
		Map<String, Object> responseObj = new HashMap<>();
		
		Map<String, String> failedFacilityNames = new HashMap<>();

		obj_SFTPChannel = remoteServer.getRemoteServerSession();

		if (obj_SFTPChannel == null) {
			throw new DataRetrievalFailureException("Remote server(Scchoit31) connection not established.");
		}

		try {

			Map<String, String> inputParameterMap = new HashMap<>();

			inputParameterMap.put("birthFirstYear", birthFirstYear);
			inputParameterMap.put("birthFirstQuarter", birthFirstQuarter);
			inputParameterMap.put("birthSecondYear", birthSecondYear);
			inputParameterMap.put("birthSecondQuarter", birthSecondQuarter);
			inputParameterMap.put("FACILITY_NAME", facilityName);

			List<Map<String, String>> pfiList = perinatalReviewDAO.getFacilityNamesByBirthCriteria(inputParameterMap);

			Map<String, String> prenatalData = perinatalReviewDAO.getPrenatalData(inputParameterMap);
			
			byte[] nysDohChartBarBytes = convertImageToByteArray(obj_SFTPChannel.get("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data/NYSDOH.png"));
			byte[] nysDohChartBarBytesAsBase64 = org.apache.commons.codec.binary.Base64.encodeBase64(nysDohChartBarBytes);
			
			String nysDohChartBarImgDataAsBase64 = new String(nysDohChartBarBytesAsBase64);
			String nysDohChartBarImgAsBase64 = "data:image/png;base64," + nysDohChartBarImgDataAsBase64;
		      
		  
			obj_SFTPChannel.cd("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data_5_5");

			String yearDirectory = "";
			boolean isYearsSame = true;
			String birthQuarterDirectory = "Q"+birthFirstQuarter+" Q"+birthSecondQuarter;
			if(birthFirstYear.equals(birthSecondYear)) {
				yearDirectory = birthFirstYear;
			}else {
				yearDirectory = birthFirstYear+" "+birthSecondYear;
				isYearsSame = false;
			}
			
			
			
			changeDirectory(yearDirectory, obj_SFTPChannel);
			changeDirectory(birthQuarterDirectory, obj_SFTPChannel);
			
			configuration = prepareConfiguration();

			configuration.setClassForTemplateLoading(this.getClass(), "/templates/");

			if(pfiList == null || pfiList.size() == 0) {
				
				responseObj.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
				responseObj.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "No Facilities available to generate the attachments.");

			}
			
			Map<String, String> datesRange = perinatalReviewDAO
					.getFacilityMonitoringReportDataRange(inputParameterMap);
			
			String fromDate = datesRange.get("FROM_DATE");
			String toDate = datesRange.get("TO_DATE");
			
			//xHTMLImporter.setHyperlinkStyle("Hyperlink");
			
			for (Map<String, String> pfiData : pfiList) {
				
				String currentPFI = pfiData.get("PFI");
				String currentFacilityName = null;

				try {
					
					

					inputParameterMap.put("PFI", currentPFI);

					List<Map<String, String>> facilityDataForTwoQuarters = perinatalReviewDAO
							.getFacilityMonitoringReportData(inputParameterMap);

					log.error("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data_5_5/" + yearDirectory + "/" + birthQuarterDirectory);
					
					obj_SFTPChannel
							.cd("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data_5_5/" + yearDirectory + "/" + birthQuarterDirectory);

					log.error("1");
					
					
					Map<String, String> facilityAddressDetails = perinatalReviewDAO.getFacilityAddressDetails(currentPFI);
					
			//		log.error("facilityAddressDetails:  "+facilityAddressDetails);

			//		log.error("facilityAddressDetails:  "+facilityAddressDetails.get("NAME"));
					if(facilityAddressDetails != null && CommonUtil.isNotNullAndEmpty(facilityAddressDetails.get("NAME"))) {
						
						currentFacilityName = facilityAddressDetails.get("NAME").replaceAll("/", " ");
						
						changeDirectory(currentFacilityName, obj_SFTPChannel);
						
					}else{
						throw new DataRetrievalFailureException("Facility name is blank for the PFI " + currentPFI);
					}
					
					
					log.error("2");
					for (Map<String, String> facilityData : facilityDataForTwoQuarters) {
						
						List<String> attachments = findPerinatalAttachments(facilityData);

						//generatePerinatalAttachment(configuration, attachments, facilityData, obj_SFTPChannel, attachmentDocxOut, attachmentXHTMLImporter);
						
						log.error("3");
						Template template = configuration.getTemplate("perinatalAttachment.ftl");
						
						for (String attachmentName : attachments) {
							
							if(attachmentDocxOut != null) {
								attachmentDocxOut.reset();
							}
							
							attachmentDocxOut = WordprocessingMLPackage.createPackage(PageSizePaper.LETTER, false);
							attachmentDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().setMargins(MarginsWellKnown.NARROW);
							attachmentXHTMLImporter = new XHTMLImporterImpl(attachmentDocxOut);
				
							log.error("4");
							
							
							String birthQtr = facilityData.get("BIRTHQTR");
				
							if (birthQtr.equals(birthFirstQuarter)) {
								birthQtr = "1";
							} else {
								birthQtr = "2";
							}
							
							log.error("5");
				
							String fileName = "Attachment " + birthQtr + "A.docx";
							
							facilityData.put("BIRTH_QTR", birthQtr);
							facilityData.put("ATTACHMENT_NAME", attachmentName);
						//	log.error("attachmentName  "+attachmentName);
							StringWriter responseContent = new StringWriter();
							
							facilityData.putAll(facilityAddressDetails);
				
							template.process(facilityData, responseContent);
							log.error("6");
							//log.error(responseContent);
							
							attachmentDocxOut.getMainDocumentPart().getContent().addAll( 
									attachmentXHTMLImporter.convert(responseContent.toString(), null) );
							
							attachmentDocxOut.save(obj_SFTPChannel.put(fileName));
							
							
							inputParameterMap.put("BIRTHQTR", facilityData.get("BIRTHQTR"));
							inputParameterMap.put("BIRTHYR", facilityData.get("BIRTHYR"));
							
							List<Map<String, String>> facilityDemographicsData = perinatalReviewDAO
									.getFacilityDemographicsData(inputParameterMap);
							
							if(facilityDemographicsData == null || facilityDemographicsData.size() == 0) {
								continue;
							}
							attachmentDocxOut = WordprocessingMLPackage.createPackage(PageSizePaper.LETTER, false);
							attachmentDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().setMargins(MarginsWellKnown.NARROW);
							attachmentXHTMLImporter = new XHTMLImporterImpl(attachmentDocxOut);
							
							Map<String, Object> facilityAttachmentData = new HashMap<>();
							
							
							
							facilityAttachmentData.put("FACILITY_DEMOGRAPHICS_DATA", facilityDemographicsData);
							facilityAttachmentData.put("BIRTH_QTR", facilityData.get("BIRTH_QTR"));
							facilityAttachmentData.put("NAME", facilityData.get("NAME"));
							facilityAttachmentData.put("REPORT_FROM_DATE", facilityData.get("REPORT_FROM_DATE"));
							facilityAttachmentData.put("REPORT_TO_DATE", facilityData.get("REPORT_TO_DATE"));
							facilityAttachmentData.put("nysDohChartBarImgAsBase64", nysDohChartBarImgAsBase64);
							
							
							responseContent = new StringWriter();
							
							Template facilityTemplate = configuration.getTemplate("perinatalFacilityAttachment.ftl");
							
							facilityTemplate.process(facilityAttachmentData, responseContent);
							
							log.error("responseContent ",responseContent);

							attachmentDocxOut.getMainDocumentPart().getContent().addAll( 
									attachmentXHTMLImporter.convert(responseContent.toString(), null) );
							
							attachmentDocxOut.save(obj_SFTPChannel.put("Attachment " + birthQtr + "B.docx"));
							
							
						}

					}
					
					
					log.error("7");
					
					Map<String, Object> facilityData = new HashMap<>();
					
					facilityData.put("FACILITY_DATA_TWO_QUARTERS", facilityDataForTwoQuarters);
					
					facilityData.put("nysDohChartBarImgAsBase64", nysDohChartBarImgAsBase64);
					
					Template graphTemplate = configuration.getTemplate("perinatalGraphAttachment.ftl");
					
					if(attachmentDocxOut != null) {
						attachmentDocxOut.reset();
					}
					
					attachmentDocxOut = WordprocessingMLPackage.createPackage(PageSizePaper.LETTER, false);
					attachmentDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().setMargins(MarginsWellKnown.NARROW);
					
					PgSz landScape = new PgSz();
					landScape.setH(BigInteger.valueOf(12240));
					landScape.setW(BigInteger.valueOf(15840));
					landScape.setOrient(STPageOrientation.LANDSCAPE); 
					
					SectPr sectPr=attachmentDocxOut.getDocumentModel().getSections().get(0).getSectPr();
					
					sectPr.setPgSz(landScape);
					
					attachmentDocxOut.getDocumentModel().getSections().get(0).setSectPr(sectPr);
					
					attachmentXHTMLImporter = new XHTMLImporterImpl(attachmentDocxOut);
		
					byte[] graphTemplteBytes = convertImageToByteArray(obj_SFTPChannel.get("expediteChartBar.png"));
					log.error("ChartBytes "+graphTemplteBytes);
					
					byte[] imgBytesAsBase64 = org.apache.commons.codec.binary.Base64.encodeBase64(graphTemplteBytes);
					
					log.error("ChartBytes64 "+imgBytesAsBase64);
					String imgDataAsBase64 = new String(imgBytesAsBase64);
					String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
					
					
					byte[] chartBarBytes = convertImageToByteArray(obj_SFTPChannel.get("chartBar.png"));
					log.error("chartBarBytes "+chartBarBytes);
					
					byte[] chartBarImgBytesAsBase64 = org.apache.commons.codec.binary.Base64.encodeBase64(chartBarBytes);
					
					log.error("chartBarBytes64 "+chartBarImgBytesAsBase64);
					String chartBarImgDataAsBase64 = new String(chartBarImgBytesAsBase64);
					String chartBarImgAsBase64 = "data:image/png;base64," + chartBarImgDataAsBase64;
					log.error("1");
					
		
					String fileName = "performance data attachment.docx";
					
					StringWriter graphResponseContent = new StringWriter();
					log.error("2");
					facilityData.putAll(facilityAddressDetails);
					
					facilityData.put("imgAsBase64", imgAsBase64);
					facilityData.put("chartBarImgAsBase64", chartBarImgAsBase64);
					facilityData.put("fromDate", fromDate);
					facilityData.put("toDate", toDate);
					log.error("3");
		
					graphTemplate.process(facilityData, graphResponseContent);
					log.error("4");
					//log.error(responseContent);
					
					System.out.println(graphResponseContent.toString());
					
					attachmentDocxOut.getMainDocumentPart().getContent().addAll( 
							attachmentXHTMLImporter.convert(graphResponseContent.toString(), null) );
					
					attachmentDocxOut.save(obj_SFTPChannel.put(fileName));
					
					Map<String, String> facilityAssigneeDetails = perinatalReviewDAO.getFacilityDesignees(currentPFI);

					
					log.error("5");
					
					Map<String, String> completeFacilityData = new HashMap<>();
					
					if (facilityAssigneeDetails == null || facilityAddressDetails == null) {
//						throw new DataRetrievalFailureException("No Facility information for the PFI " + currentPFI);
					}else {
						completeFacilityData.putAll(facilityAssigneeDetails);
					}
					log.error("6");
					completeFacilityData.putAll(prenatalData);
					
					completeFacilityData.putAll(facilityAddressDetails);
					
					Map<String, String> quarterNames = new HashMap<>();
					quarterNames.put("1", "1st");
					quarterNames.put("2", "2nd");
					quarterNames.put("3", "3rd");
					quarterNames.put("4", "4th");
					
					Map<String, String> quarterMonthRange = new HashMap<>();
					quarterMonthRange.put("1", "January-March");
					quarterMonthRange.put("2", "April-June");
					quarterMonthRange.put("3", "July-September");
					quarterMonthRange.put("4", "October-December");
					
					log.error("7");
					completeFacilityData.put("QUARTER_STRING", "first and second");
					
					
					completeFacilityData.put("staticPath", "/hpn/ctrldocs/nyehms");
					
					completeFacilityData.put("FIRST_QUARTER", quarterNames.get(birthFirstQuarter));
					completeFacilityData.put("SECOND_QUARTER", quarterNames.get(birthSecondQuarter));
					
					if(isYearsSame) {
						completeFacilityData.put("QUARTER_STRING", quarterNames.get(birthFirstQuarter)+" and "+quarterNames.get(birthSecondQuarter)+" quarters of "+birthFirstYear);
						completeFacilityData.put("DATE_RANGE", quarterNames.get(birthFirstQuarter)+" quarter ("+ quarterMonthRange.get(birthFirstQuarter)+") and "+quarterNames.get(birthSecondQuarter)+" quarter ("+quarterMonthRange.get(birthSecondQuarter)+") of "+birthFirstYear);
						completeFacilityData.put("ATTACHMENT_B_QUARTER", "Attachment 1B ("+quarterNames.get(birthFirstQuarter)+" quarter) and 2B ("+quarterNames.get(birthSecondQuarter)+" quarter)");
					}else {
						completeFacilityData.put("QUARTER_STRING", quarterNames.get(birthFirstQuarter)+" quarter of "+birthFirstYear+" and "+quarterNames.get(birthSecondQuarter)+" quarter of "+birthSecondYear);
						completeFacilityData.put("DATE_RANGE", quarterNames.get(birthFirstQuarter)+" quarter ("+ quarterMonthRange.get(birthFirstQuarter)+") of "+birthFirstYear+" and "+quarterNames.get(birthSecondQuarter)+" quarter ("+quarterMonthRange.get(birthSecondQuarter)+") of "+birthSecondYear);
						completeFacilityData.put("ATTACHMENT_B_QUARTER", "Attachment 1B ("+quarterNames.get(birthFirstQuarter)+" quarter "+birthFirstYear+") and 2B ("+quarterNames.get(birthSecondQuarter)+" quarter "+birthSecondYear+")");
						
					}
					log.error("8");
					
					
					//completeFacilityData.put("DATE_RANGE", quarterNames.get(birthFirstQuarter)+" ("+ quarterMonthRange.get(birthFirstQuarter)+") and "+quarterNames.get(birthSecondQuarter)+" ("+quarterMonthRange.get(birthSecondQuarter)+")");
					
//					if(isYearsSame) {
						
						
						
						if(isYearsSame) {
							completeFacilityData.put("YEAR", birthFirstYear);
						}else {

							completeFacilityData.put("YEAR", birthFirstYear+" and "+birthSecondYear);
						}
//					}
						
					Date letterDate1 = new SimpleDateFormat("MM/dd/yyyy").parse(letterDate); 
					completeFacilityData.put("LETTER_DATE", new SimpleDateFormat("MMMMM dd, yyyy").format(letterDate1));
					completeFacilityData.put("LETTER_MON_DATE", new SimpleDateFormat("MMMM yyyy").format(letterDate1));
					
				//	generateFacilityPerformanceLetter(configuration, obj_SFTPChannel, completeFacilityData, facilityReportDocxOut, facilityReportXHTMLImporter);
					log.error("9");
					
					if(facilityReportDocxOut != null) {
						facilityReportDocxOut.reset();
					}
					
					facilityReportDocxOut = WordprocessingMLPackage.createPackage(PageSizePaper.LETTER, false);
					facilityReportDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().setMargins(MarginsWellKnown.NARROW);
					
					 PgMar asfsd=	facilityReportDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().getPgMar();
					 
					 
					 asfsd.setLeft(BigInteger.valueOf(1360));
					 asfsd.setRight(BigInteger.valueOf(1360));
					 
					 facilityReportDocxOut.getDocumentModel().getSections().get(0).getPageDimensions().setPgMar(asfsd);
					facilityReportXHTMLImporter = new XHTMLImporterImpl(facilityReportDocxOut);
					log.error("10");
					
					//File file = new File(new URL("http://example.com/my-file-path.txt"));
				    byte[] bytes = convertImageToByteArray(obj_SFTPChannel.get("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data/Header_5_21_24.jpg"));
				      
				    HeaderPart headerPart = createHeader(facilityReportDocxOut);
				      
				    headerPart.getContent().add(newImage(facilityReportDocxOut, headerPart, bytes));
				      
				      
				     bytes = convertImageToByteArray(obj_SFTPChannel.get("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data/Footer12_30.png"));
				      
				     FooterPart footerPart = createFooter(facilityReportDocxOut);
				      
				     footerPart.getContent().add(newImage(facilityReportDocxOut, footerPart, bytes));
				     log.error("11");
				
					Template template = configuration.getTemplate("perinatalPerformanceLetter.ftl");
			
					StringWriter responseContent = new StringWriter();
					log.error("12");
					template.process(completeFacilityData, responseContent);
					
					String responseContentString = responseContent.toString();
					log.error("13");
					List<Object> wordContent = facilityReportXHTMLImporter.convert(responseContentString.toString(), null); 
					bytes = convertImageToByteArray(obj_SFTPChannel.get("/H:/BHAE/HAEGroup/Lab Reporting/Weekly Counts/Facility Data/drSgn12_30.png"));
				      
					addImageToPackage(facilityReportDocxOut, bytes, wordContent);
			        			        
			        Template signatureTemplate = configuration.getTemplate("perinatalPerformanceSignatures.ftl");
					
			        StringWriter signatureResponseContent = new StringWriter();
			
					signatureTemplate.process(completeFacilityData, signatureResponseContent);
					log.error("14");				
					wordContent.add(facilityReportXHTMLImporter.convert(signatureResponseContent.toString(), null));
					facilityReportDocxOut.getMainDocumentPart().getContent().addAll(wordContent );

					
					facilityReportDocxOut.save(obj_SFTPChannel.put("Bi-Annual Performance Letter.docx"));
					log.error("15");
				} catch (IOException | TemplateException e) {

					String transactionMessage = currentPFI+" : Exception occured while generating the Facility attachments :"
							+ e.getMessage();
					
					log.error(transactionMessage);

					failedFacilityNames.put(currentFacilityName, "Failed to generate the Attachments");

				} catch(DataAccessException dataAccessException){

					String transactionMessage = currentPFI+" : Exception occured while generating the Facility attachments :"
							+ dataAccessException.getMessage();
					
					log.error(transactionMessage);

					failedFacilityNames.put(currentFacilityName, dataAccessException.getMessage());

				}	catch (Exception e) {

					String transactionMessage = currentPFI+" : Exception occured while generating the Facility attachments :"
							+ e.getMessage();

					log.error(transactionMessage);

					failedFacilityNames.put(currentFacilityName, "Failed to generate the Attachments");


				}
			}
			
			
				if(failedFacilityNames.size() == pfiList.size()) {
					
					responseObj.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
					responseObj.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "Failed to generate the attachments, please contact helpline.");
				}else if(failedFacilityNames.size() > 0) {
					responseObj.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
					responseObj.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "Failed to generate the attachments for few Facilities");
				}else {
					responseObj.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_SUCCESS_CODE.getName());
					responseObj.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "Succefully generated the Facility attachments");
				}
				
				responseObj.put("FAILED_PFIS", failedFacilityNames);
				
				

		} catch (Exception e) {

			String transactionMessage = "Exception occured while generating the Facility attachments :" + e.getMessage();

			log.error(transactionMessage);
			
			log.error(CommonUtil.getStackTrace(e));

			responseObj.put(AppConstants.NYEHM_TRANSACTION_RESULT.getName(), AppConstants.NYEHM_TRANSACTION_FAILURE_CODE.getName());
			responseObj.put(AppConstants.NYEHM_TRANSACTION_MESSAGE.getName(), "Failed to generate the attachments, please contact helpline.");


		} finally {
			try {
				// workbook.close();

				if (obj_SFTPChannel != null) {
					obj_SFTPChannel.disconnect();
				}
				if (obj_InputStream != null) {
					obj_InputStream.close();
				}

			} catch (IOException e) {

				String transactionMessage = "Exception occured while closing the JSCH channel :" + e.getMessage();

				log.error(transactionMessage);
				log.error(CommonUtil.getStackTrace(e));

				throw new DataRetrievalFailureException(transactionMessage);

			}
		}
		
		return responseObj;
	}
	
	private static void addImageToPackage(WordprocessingMLPackage wordMLPackage,
            byte[] bytes, List<Object> hgh) throws Exception {
		BinaryPartAbstractImage imagePart =
		BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
		
		int docPrId = 1;
		int cNvPrId = 2;
		Inline inline = imagePart.createImageInline("image",
		"image", docPrId, cNvPrId, false);
		
//	      
//	      227, 65
		
		P paragraph = addInlineImageToParagraph(inline);
		
		//wordMLPackage.getMainDocumentPart().getContent().add(paragraph);
		
		hgh.add(paragraph);
		}
	
	
	private static P addInlineImageToParagraph(Inline inline) {
        // Now add the in-line image to a paragraph
        ObjectFactory factory = new ObjectFactory();
        P paragraph = factory.createP();
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
    }

//	private void generateFacilityPerformanceLetter(Configuration configuration, ChannelSftp obj_SFTPChannel, Map<String, String> completeFacilityData,
//			
//			facilityReportDocxOut.getMainDocumentPart().getContent().addAll( 
//				
//				
//				
//				
////				docxOut.getMainDocumentPart().getContent().addAll( 

	private List<String> findPerinatalAttachments(Map<String, String> facilityData) {

		List<String> attachments = new ArrayList<>();

		if ("1".equals(facilityData.get("ATTACHMENT_A"))) {
			attachments.add("Attachment A");
		}
		if ("1".equals(facilityData.get("ATTACHMENT_B"))) {
			attachments.add("Attachment B");
		}
		if ("1".equals(facilityData.get("ATTACHMENT_C"))) {
			attachments.add("Attachment C");
		}
		if ("1".equals(facilityData.get("ATTACHMENT_D"))) {
			attachments.add("Attachment D");
		}
		if ("1".equals(facilityData.get("ATTACHMENT_E"))) {
			attachments.add("Attachment E");
		}
		if ("1".equals(facilityData.get("ATTACHMENT_F"))) {
			attachments.add("Attachment F");
		}

		return attachments;

	}

	private Configuration prepareConfiguration() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		return configuration;
	}
	
	private void changeDirectory(String folderName, ChannelSftp obj_SFTPChannel) throws SftpException {
		try {
			obj_SFTPChannel.mkdir(folderName);
		} catch (SftpException SftpException) {

			if (log.isDebugEnabled()) {
				log.debug("{} - Folder name already exist",folderName);
			}
		}
		obj_SFTPChannel.cd(folderName);
	}
	
	
	
	
	
//	private static P newImage( WordprocessingMLPackage wordMLPackage,
//			Part sourcePart,
//			byte[] bytes,
//			String filenameHint, String altText, 
//			int id1, int id2) throws Exception {
//		
//        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, 
//        		sourcePart, bytes);
//		
////        Inline inline = imagePart.createImageInline( filenameHint, altText, 
//
//        
//        // Now add the inline in w:p/w:r/w:drawing
	
	
	 private static byte[] convertImageToByteArray(InputStream inputStream)
	            throws FileNotFoundException, IOException {
		 
		   //InputStream is = new FileInputStream(inputStream );
	        
	        return IOUtils.toByteArray(inputStream);
	        
	        
//	        // You cannot create an array using a long, it needs to be an int.
//	        // Ensure all the bytes have been read
//	        if (offset < bytes.length) {
//	            System.out.println("Could not completely read file "
	    }
	 
	 
	 private static HeaderPart createHeader(
	            WordprocessingMLPackage word) throws Exception {
	        HeaderPart headerPart = new HeaderPart();
	        Relationship rel = word.getMainDocumentPart().addTargetPart(headerPart);
	        createHeaderReference(word, rel);
	        return headerPart;
	    }
//		
//		
	 private static FooterPart createFooter(
	            WordprocessingMLPackage word) throws Exception {
			FooterPart footerPart = new FooterPart();
	        Relationship rel = word.getMainDocumentPart().addTargetPart(footerPart);
	        createFooterReference(word, rel);
	        return footerPart;
	    }
		
		
		private static void createHeaderReference(
	            WordprocessingMLPackage word,
	            Relationship relationship )
	            throws InvalidFormatException {
	        List<SectionWrapper> sections = word.getDocumentModel().getSections();

	        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
	        // There is always a section wrapper, but it might not contain a sectPr
	        if (sectPr==null ) {
	            sectPr = factory.createSectPr();
	            word.getMainDocumentPart().addObject(sectPr);
	            sections.get(sections.size() - 1).setSectPr(sectPr);
	        }
	        HeaderReference headerReference = factory.createHeaderReference();
	        headerReference.setId(relationship.getId());
	        headerReference.setType(HdrFtrRef.DEFAULT);
	        sectPr.getEGHdrFtrReferences().add(headerReference);
	    }
	 
	 private static void createFooterReference(
	            WordprocessingMLPackage word,
	            Relationship relationship )
	            throws InvalidFormatException {
	        List<SectionWrapper> sections = word.getDocumentModel().getSections();

	        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
	        // There is always a section wrapper, but it might not contain a sectPr
	        if (sectPr==null ) {
	            sectPr = factory.createSectPr();
	            word.getMainDocumentPart().addObject(sectPr);
	            sections.get(sections.size() - 1).setSectPr(sectPr);
	        }
	        FooterReference footerReference = factory.createFooterReference();
	        footerReference.setId(relationship.getId());
	        footerReference.setType(HdrFtrRef.DEFAULT);
	        sectPr.getEGHdrFtrReferences().add(footerReference);
	    }
		
	 
	 private static P newImage(WordprocessingMLPackage word,
			 Part sourcePart,
			 byte[] bytes) throws Exception {
		 BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(word,  sourcePart, bytes);
	                //.createImagePart(word, sourcePart, bytes);
	        int id = (int) (Math.random() * 10000);
	        Inline inline = imagePart.createImageInline("image", "image", id, id * 2, false);

	        Drawing drawing = factory.createDrawing();
	        drawing.getAnchorOrInline().add(inline);

	        R r = factory.createR();
	        r.getContent().add(drawing);

	        P p = factory.createP();
	        p.getContent().add(r);

	        return p;
		}
}
