package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import gov.ny.state.doh.bhae.nyehms.web.spring.config.AppProperties;
import org.springframework.stereotype.Component;

import gov.ny.state.doh.bhae.nyehms.web.spring.util.CommonUtil;

@Component
public class CommonSchedulerHelper {

	@Autowired
	private AppProperties appProperties;
	

	
	/**
	 * Creates an email subject string to improve readability of the processing status
	 * emails received by developers and others.
	 * @param initiator If started manually, the ID of the person; if automatic,
	 * "QUARTZ".
	 * @param schedulerName One of the names describing the processing steps.
	 * @param databaseName One of DEV, EVAL, or PROD.
	 * @return
	 */
	public String getStatusMailSubject(
			String initiator,
			String schedulerName,
			String databaseName) {

		StringBuilder statusMailSubject = new StringBuilder(schedulerName + " ON ");
		statusMailSubject.append(appProperties.environmentName());
		statusMailSubject.append(" BY ");
		if(CommonUtil.isNullOrEmpty(initiator)){
			statusMailSubject.append("QUARTZ");
			initiator = "QUARTZ";
		}else{
			statusMailSubject.append("MANUAL <").append(initiator).append(">");
		}
		statusMailSubject.append(" DB ");
		statusMailSubject.append(databaseName);
		//Sending notification mail that the RAW records clean process started
		return statusMailSubject.toString();
	}

}
