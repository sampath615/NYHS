package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;


public class LAFTCountReconcileTestDataVO {
	
	private String testTypeCode;
	private String testTypeDesc;
	
	private int initialLabCount;
	private int currentLabCount;
	private int surveyLabCount;
	private int adjustedSurveyLabCount;
	private int countDifference;
	private String dateResolved;
	
	public String getTestTypeCode() {
		return testTypeCode;
	}
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}
	public String getTestTypeDesc() {
		return testTypeDesc;
	}
	public void setTestTypeDesc(String testTypeDesc) {
		this.testTypeDesc = testTypeDesc;
	}
	public int getInitialLabCount() {
		return initialLabCount;
	}
	public void setInitialLabCount(int initialLabCount) {
		this.initialLabCount = initialLabCount;
	}
	public int getCurrentLabCount() {
		return currentLabCount;
	}
	public void setCurrentLabCount(int currentLabCount) {
		this.currentLabCount = currentLabCount;
	}
	public int getSurveyLabCount() {
		return surveyLabCount;
	}
	public void setSurveyLabCount(int surveyLabCount) {
		this.surveyLabCount = surveyLabCount;
	}
	public int getAdjustedSurveyLabCount() {
		return adjustedSurveyLabCount;
	}
	public void setAdjustedSurveyLabCount(int adjustedSurveyLabCount) {
		this.adjustedSurveyLabCount = adjustedSurveyLabCount;
	}
	public int getCountDifference() {
		return countDifference;
	}
	public void setCountDifference(int countDifference) {
		this.countDifference = countDifference;
	}
	public String getDateResolved() {
		return dateResolved;
	}
	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}
	
	
	/*
	 * private String eclrsLabCount; private String nyehmsInitialLabCount; private
	 * String nyehmsCurrentLabCount; private String surveyRespLabCount; // Initial
	 * Lab Count private String AdustedLabRecount; private String countDifference;
	 * private String resubmittedRecGaps; private String dateResolved;
	 * 
	 * public String getTestTypeCode() { return testTypeCode; } public void
	 * setTestTypeCode(String testTypeCode) { this.testTypeCode = testTypeCode; }
	 * public String getTestTypeDesc() { return testTypeDesc; } public void
	 * setTestTypeDesc(String testTypeDesc) { this.testTypeDesc = testTypeDesc; }
	 * public String getEclrsLabCount() { return eclrsLabCount; } public void
	 * setEclrsLabCount(String eclrsLabCount) { this.eclrsLabCount = eclrsLabCount;
	 * } public String getNyehmsInitialLabCount() { return nyehmsInitialLabCount; }
	 * public void setNyehmsInitialLabCount(String nyehmsInitialLabCount) {
	 * this.nyehmsInitialLabCount = nyehmsInitialLabCount; } public String
	 * getNyehmsCurrentLabCount() { return nyehmsCurrentLabCount; } public void
	 * setNyehmsCurrentLabCount(String nyehmsCurrentLabCount) {
	 * this.nyehmsCurrentLabCount = nyehmsCurrentLabCount; } public String
	 * getSurveyRespLabCount() { return surveyRespLabCount; } public void
	 * setSurveyRespLabCount(String surveyRespLabCount) { this.surveyRespLabCount =
	 * surveyRespLabCount; } public String getAdustedLabRecount() { return
	 * AdustedLabRecount; } public void setAdustedLabRecount(String
	 * adustedLabRecount) { AdustedLabRecount = adustedLabRecount; } public String
	 * getCountDifference() { return countDifference; } public void
	 * setCountDifference(String countDifference) { this.countDifference =
	 * countDifference; } public String getResubmittedRecGaps() { return
	 * resubmittedRecGaps; } public void setResubmittedRecGaps(String
	 * resubmittedRecGaps) { this.resubmittedRecGaps = resubmittedRecGaps; } public
	 * String getDateResolved() { return dateResolved; } public void
	 * setDateResolved(String dateResolved) { this.dateResolved = dateResolved; }
	 */
}
