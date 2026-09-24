package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class FeedbackReportVO {
	
	private FeedbackReportOverallSummaryVO feedbackReportOverallSummaryVO;
	private FeedbackReportStateSummaryVO feedbackReportStateSummaryVO;
	
	public FeedbackReportOverallSummaryVO getFeedbackReportOverallSummaryVO() {
		return feedbackReportOverallSummaryVO;
	}
	public void setFeedbackReportOverallSummaryVO(
			FeedbackReportOverallSummaryVO feedbackReportOverallSummaryVO) {
		this.feedbackReportOverallSummaryVO = feedbackReportOverallSummaryVO;
	}
	public FeedbackReportStateSummaryVO getFeedbackReportStateSummaryVO() {
		return feedbackReportStateSummaryVO;
	}
	public void setFeedbackReportStateSummaryVO(
			FeedbackReportStateSummaryVO feedbackReportStateSummaryVO) {
		this.feedbackReportStateSummaryVO = feedbackReportStateSummaryVO;
	}
		
}
