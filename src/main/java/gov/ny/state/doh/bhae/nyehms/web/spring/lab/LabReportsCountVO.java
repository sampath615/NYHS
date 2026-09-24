package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LabReportsCountVO {
    
	private Integer dataSet;
	private String weekStartDate;
	private String weekEndDate;
	private String reportTestType;
	private Integer weekNumber;
	private Integer count;
	private Float percentage;
	public Integer getDataSet() {
		return dataSet;
	}
	public void setDataSet(Integer dataSet) {
		this.dataSet = dataSet;
	}
	public String getWeekStartDate() {
		return weekStartDate;
	}
	public void setWeekStartDate(String weekStartDate) {
		this.weekStartDate = weekStartDate;
	}
	public String getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	public String getReportTestType() {
		return reportTestType;
	}
	public void setReportTestType(String reportTestType) {
		this.reportTestType = reportTestType;
	}
	public Integer getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getPercentage() {
		return percentage;
	}
	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}
	
	
		

}
