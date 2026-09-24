package gov.ny.state.doh.bhae.nyehms.web.spring.service.report;

public interface StatusReportsService {
	

	public void generateLabReportCount(String sqlQuery);

	public void generateMontlyLabReportCount(String reportType);
	
}