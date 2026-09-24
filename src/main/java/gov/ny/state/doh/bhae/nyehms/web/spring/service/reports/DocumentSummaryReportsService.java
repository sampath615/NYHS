package gov.ny.state.doh.bhae.nyehms.web.spring.service.reports;

import java.io.IOException;

import javax.mail.MessagingException;

import com.jcraft.jsch.SftpException;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface DocumentSummaryReportsService {
	
	
	public void generateWeeklyDocumentStatusSummaryReport() throws TemplateNotFoundException, MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException, SftpException, MessagingException;
	
}