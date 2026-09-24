package gov.ny.state.doh.bhae.nyehms.web.spring.domain.review;

public class AhiAlertUserNotesHistoryVO {
	
	private int ahiAlertSeqId;
	private String userNotes;
	private int userNotesBy;
	private String userNotesdDate;
	
	public int getAhiAlertSeqId() {
		return ahiAlertSeqId;
	}
	public void setAhiAlertSeqId(int ahiAlertSeqId) {
		this.ahiAlertSeqId = ahiAlertSeqId;
	}
	public String getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}
	public int getUserNotesBy() {
		return userNotesBy;
	}
	public void setUserNotesBy(int userNotesBy) {
		this.userNotesBy = userNotesBy;
	}
	public String getUserNotesdDate() {
		return userNotesdDate;
	}
	public void setUserNotesdDate(String userNotesdDate) {
		this.userNotesdDate = userNotesdDate;
	}	
	
	
}
