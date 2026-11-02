package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.ny.state.doh.bhae.nyehms.web.spring.constants.CommonConstants;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.document.HITCommonDocumentService;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers.CommonSchedulerHelper;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.SendEmail;
import org.springframework.stereotype.Component;

@Component
public class HITMessageCountLoader {

	private static final Logger log = LoggerFactory.getLogger(HITMessageCountLoader.class);

	@Autowired
	private CommonSchedulerHelper commonSchedulerHelper;
	@Autowired
	private HITCommonDocumentService hitDocumentService;
	@Autowired
	SendEmail sendEmail;
	@Autowired
	private ManualQuartzJobService manualQuartzJobService;

	public HITMessageCountLoader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Loads HIT Message Counts from UPHN_TRANX_IN .
	 * 
	 * @param initiator
	 *            A string indicating either the user who initiated the
	 *            scheduler manually, or QUARTZ if done by the scheduler.
	 */
	public void loadHITMessageCountsFromUphn(String initiator) {
		try {

			synchronized (this) {
				String runningServerIpAddress = null;
				try {
					runningServerIpAddress = InetAddress.getLocalHost().getHostAddress();
					log.error("Running Server IP Address : {}", runningServerIpAddress);
				} catch (UnknownHostException e) {
					log.error("Unable to get the Server Ip Address");
				}
				StringBuilder messageContent = new StringBuilder();
				Map<String, String> schedulerMetaData = manualQuartzJobService.getSchedulerMetaData();
				if ("Y".equals(schedulerMetaData.get("REQUIRED_TO_LOAD_HIT_RECORDS_COUNTS"))) {
					String expectedServerIpAddress = schedulerMetaData.get("SERVER_IP_ADDRESS");
					if (expectedServerIpAddress.equals(runningServerIpAddress)) {
						log.error("********************** {} is in hitMessageCountLoader.loadHITMessageCountsFromUphn",initiator);
						String databaseName = schedulerMetaData.get("DATABASE_NAME");
						String statusMailSubject = commonSchedulerHelper.getStatusMailSubject(initiator,
								"HITMessageCountLoader", databaseName);
						log.error("HIT MESSAGE COUNT RUNTIME TOTAL MEMORY : {} .", Runtime.getRuntime().totalMemory());
						log.error(
								"HIT MESSAGE COUNT RUNTIME MEMORY FREE : {} .", Runtime.getRuntime().freeMemory());

						
						//To insert the HIT message counts to log table
						hitDocumentService.insertHitMessageCount();
						List<Map<String, Object>> rs = hitDocumentService.getCountsForTodayMail();

						messageContent.append("<font size:60px><b> HIT MESSAGE COUNTS REPORT  </b></font>");
						messageContent.append("</br>");
						messageContent.append(
								"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						messageContent.append("</br>");
						if (rs != null && rs.size() > 0) {
							for (Map<String, Object> row : rs) {
								String type = (String) row.get("TYPE");
								Timestamp lastReceived = (Timestamp) row.get("LAST_RECEIVED");
								BigDecimal total = (BigDecimal) row.get("TOTAL");
								BigDecimal distinct = (BigDecimal) row.get("DISTINCT");
								if (total == null) {
									total = BigDecimal.ZERO;
								}
								if (lastReceived != null && total != null) {
									Date date = new Date(lastReceived.getTime());
									SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
									String ourformat = formatter.format(date.getTime());
									String day = (new SimpleDateFormat("EEEE")).format(lastReceived.getTime());
									if (type.equals("HL7")) {
										messageContent.append("<font size:40px><b>Document Type : HL7 </b></font>");
										messageContent.append("</br></br>");
										String parameterYorN = (String) row.get("HL7_RECD_YN");
										// Code for live event only for HL7
										List<Map<String, Object>> rsForToday = hitDocumentService
												.getCountsForHITLiveEvent();
										if (rsForToday != null && rsForToday.size() > 0) {
											for (Map<String, Object> rowLive : rsForToday) {
												String typeLive = (String) rowLive.get("TYPE");
												Timestamp lastReceivedLive = (Timestamp) rowLive.get("RECEIVEDDAY");
												BigDecimal totalLive = (BigDecimal) rowLive.get("TOT");

												if (totalLive == null) {
													totalLive = BigDecimal.ZERO;
												}
												if (lastReceivedLive != null && totalLive != null) {
													Date dateLive = new Date(lastReceivedLive.getTime());
													SimpleDateFormat formatterLive = new SimpleDateFormat("MM/dd/yyyy");
													String ourformatLive = formatterLive.format(dateLive.getTime());
													if (typeLive.equals("HL7")) {
														messageContent.append("Incoming HL7 Counts for today are : ");
														messageContent
																.append("<table style='width:600px;margin-top: 20px' border><thead>	<tr style='background-color: #B3F0FF'>	<td style='width:200px'> Document Type:	<b>"
																		+ typeLive
																		+ "	</b></td>	<td style='width:200px'> Total: <b>"
																		+ totalLive
																		+ "	</b></td>  <td style='width:200px'> Date : <b>"
																		+ ourformatLive
																		+ "	</b></td></tr></thead></table>");
														messageContent.append("</br>");
													}
												}
											}
										} else {
											// messageContent.append("</br>");
											messageContent.append(
													"<table style='width:1050px;margin-top: 20px' border><thead>	<tr>	<td style='width:auto'> <font color='red'>ALERT :- NO HL7 MESSAGES RECEIVED TODAY</font></td></tr></thead></table>");
											messageContent.append("</br>");
										}
										if (parameterYorN.equals("Y")) {
											messageContent.append("HL7 Messages Received Yesterday :");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color: #B3F0FF'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>   <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td> <td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");
										} else {
											messageContent.append(
													"<table style='width:1050px;margin-top: 20px' border><thead>	<tr>	<td style='width:auto'> <font color='red'>ALERT :-  NO HL7 MESSAGES WERE RECEIVED YESTERDAY</font></td></tr></thead></table>");
											messageContent.append("</br>");
											messageContent.append(
													"Most Recent Entry in HIT_MESSAGE_COUNT table for HL7 is : ");
											messageContent.append("</br>");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color:#B3F0FF'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>   <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td> <td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");
										}
										messageContent.append("</br>");
										messageContent.append(
												"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										messageContent.append("</br></br>");
									}
									if (type.equals("CCD")) {
										messageContent.append("<font size:40px><b>Document Type : CCD </b></font>");
										messageContent.append("</br></br>");
										String parameterYorN = (String) row.get("CCD_RECD_YN");
										if (parameterYorN.equals("Y")) {
											messageContent.append("CCD Messages Received this week :");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color: #FFE680'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>  <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td><td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");
										} else {
											messageContent.append(
													"<table style='width:1050px;margin-top: 20px' border><thead>	<tr>	<td style='width:auto'> <font color='red'>ALERT :- NO CCD MESSAGES WERE RECEIVED THIS WEEK</font></td></tr></thead></table>");
											messageContent.append("</br>");
											messageContent.append(
													"Most Recent Entry in HIT_MESSAGE_COUNT table for CCD is : ");
											messageContent.append("</br>");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color: #FFE680'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>   <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td> <td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");

										}
										messageContent.append("</br>");
										messageContent.append(
												"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										messageContent.append("</br></br>");
									}
									if (type.equals("EXP")) {
										messageContent.append("<font size:40px><b>Document Type : EXP </b></font>");
										messageContent.append("</br></br>");
										String parameterYorN = (String) row.get("EXP_RECD_YN");
										if (parameterYorN.equals("Y")) {
											messageContent.append(
													"Most Recent Entry in HIT_MESSAGE_COUNT table for EXP is :");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color: #FF9999'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>  <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td><td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");
										} else {
											messageContent.append(
													"<table style='width:1050px;margin-top: 20px' border><thead>	<tr>	<td style='width:auto'> <font color='red'>ALERT :-  NO EXP MESSAGES WERE RECEIVED THIS MONTH</font></td></tr></thead></table>");
											messageContent.append("</br>");
											messageContent.append(
													"Most Recent Entry in HIT_MESSAGE_COUNT table for EXP is : ");
											messageContent.append("</br>");
											messageContent
													.append("<table style='width:1050px;margin-top: 20px' border><thead>	<tr style='background-color: #FF9999'>	<td style='width:200px'> Document Type:	<b>"
															+ type + "	</b></td>	<td style='width:200px'> Total: <b>"
															+ total
															+ "	</b></td>   <td style='width:200px'> Distinct: <b>"
															+ distinct
															+ "	</b></td>  <td style='width:200px'> Last Received: <b>"
															+ ourformat
															+ "	</b></td> <td style='width:250px'> Day of the week :  <b>"
															+ day + "	</b></td></tr></thead></table>");
										}
										messageContent.append("</br>");
										messageContent.append(
												"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										messageContent.append("</br></br>");
									}
								}
							}
						}
						/*
						 * else { messageContent.
						 * append("<font size:20px><b> No Messages were received Yesterday. </b></font>"
						 * ); }
						 * 
						 * if (rsForToday != null && rsForToday.size() > 0) {
						 * messageContent.append("</br> </br> </br>");
						 * messageContent.
						 * append("<font size:20px><b>HIT Message Counts for Today are :  </b></font>"
						 * ); for (Map<String, Object> row : rsForToday) {
						 * String type = (String) row.get("TYPE"); Timestamp
						 * lastReceived = (Timestamp) row.get("RECEIVEDDAY");
						 * BigDecimal total = (BigDecimal) row.get("TOT");
						 * 
						 * if (total == null) { total = BigDecimal.ZERO; } if
						 * (lastReceived != null && total != null) { Date date =
						 * new Date(lastReceived.getTime()); SimpleDateFormat
						 * formatter = new SimpleDateFormat("MM/DD/YYYY");
						 * String ourformat = formatter.format(date.getTime());
						 * String day = (new
						 * SimpleDateFormat("EEEE")).format(lastReceived.getTime
						 * ()); if (type.equals("HL7")) { messageContent
						 * .append("<table style='width:600px;margin-top: 20px' border><thead>	<tr style='background-color: #B3F0FF'>	<td style='width:200px'> File Type:	<b>"
						 * + type +
						 * "	</b></td>	<td style='width:200px'> Total: <b>"
						 * + total +
						 * "	</b></td>  <td style='width:200px'> Date : <b>"
						 * + ourformat + "	</b></td></tr></thead></table>"); }
						 * if (type.equals("CCD")) { messageContent
						 * .append("<table style='width:600px;margin-top: 20px' border><thead>	<tr style='background-color: #FFE680'>	<td style='width:200px'> File Type:	<b>"
						 * + type +
						 * "	</b></td>	<td style='width:200px'> Total: <b>"
						 * + total +
						 * "	</b></td>  <td style='width:200px'> Date : <b>"
						 * + ourformat + "	</b></td></tr></thead></table>"); }
						 * if (type.equals("EXP")) { messageContent
						 * .append("<table style='width:600px;margin-top: 20px' border><thead>	<tr style='background-color: #FF9999'>	<td style='width:200px'> File Type:	<b>"
						 * + type +
						 * "	</b></td>	<td style='width:200px'> Total: <b>"
						 * + total +
						 * "	</b></td>  <td style='width:200px'> Date : <b>"
						 * + ourformat + "	</b></td></tr></thead></table>"); }
						 * } } } else {
						 * messageContent.append("</br> </br> </br>");
						 * messageContent.
						 * append("<font size:20px><b> No Messages were received today. </b></font>"
						 * ); }
						 */
						//StringBuilder message = new StringBuilder();
						
						sendEmail.processEmail(
								null,
								statusMailSubject, messageContent.toString() ,
								"HIT_NOTIFICATION",
								CommonConstants.RESPONSE_CONTENT_TYPE_HTML);
						
						
						
//								sendEmail.sendSSLMessage(recipientEmailAddressesTo, statusMailSubject,
//										messageContent.toString(), schedulerMetaData.get("NOTIFICATIONS_FROM"), null,
						// DELETE FROM UPHN_TRANX_PROPS_IN table code goes here.
						log.error("Starting to delete data from UPHN_TRANX_PROS_IN table");
						hitDocumentService.deleteFromUphnTranxPropsIn();
						log.error("Successfully completed the deletion of data from UPHN_TRANX_PROS_IN table");
						//
					}
				}
			}
		} catch (Exception e) {
			log.error("Error Found in loadHITMessageCountsFromUphn Code    {}", e.getMessage());

			log.error(CommonUtil.getStackTrace(e).substring(0, 2000));

		}
	}
}
