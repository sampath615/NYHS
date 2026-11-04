package gov.ny.state.doh.bhae.nyehms.web.spring.domain.assignment;

public class AssignmentActivityVO {
	private String activityDate;
	private String doneBY;
	private String doneBYShortName;
	private String activityDesc;
	private String comments;
	
	public String getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
	public String getDoneBY() {
		return doneBY;
	}
	public void setDoneBY(String doneBY) {
		this.doneBY = doneBY;
	}
	public String getDoneBYShortName() {
		return doneBYShortName;
	}
	public void setDoneBYShortName(String doneBYShortName) {
		this.doneBYShortName = doneBYShortName;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
