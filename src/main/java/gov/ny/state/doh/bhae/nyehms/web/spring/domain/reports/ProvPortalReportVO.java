package gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports;

import java.util.List;

public class ProvPortalReportVO {
	
	List<ProvPortalSubmissionReportVO> provPortalSubmissionDataList;
	List<ProvPortalSubmissionCareStatusVO> provPortalCareStatusDataList;
	
	public List<ProvPortalSubmissionReportVO> getProvPortalSubmissionDataList() {
		return provPortalSubmissionDataList;
	}
	public void setProvPortalSubmissionDataList(List<ProvPortalSubmissionReportVO> provPortalSubmissionDataList) {
		this.provPortalSubmissionDataList = provPortalSubmissionDataList;
	}
	public List<ProvPortalSubmissionCareStatusVO> getProvPortalCareStatusDataList() {
		return provPortalCareStatusDataList;
	}
	public void setProvPortalCareStatusDataList(List<ProvPortalSubmissionCareStatusVO> provPortalCareStatusDataList) {
		this.provPortalCareStatusDataList = provPortalCareStatusDataList;
	}
	
}
