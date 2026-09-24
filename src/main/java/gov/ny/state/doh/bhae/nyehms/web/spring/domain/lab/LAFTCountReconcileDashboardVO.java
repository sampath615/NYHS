package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

public class LAFTCountReconcileDashboardVO {
	
	private String reconcileId;
	private String laftUserId;
	private	String currentlyAssigned;
	private	String currentlyOpen;
	private	String reconciled;
	private	String surveyReceived;
	public String getReconcileId() {
		return reconcileId;
	}
	public void setReconcileId(String reconcileId) {
		this.reconcileId = reconcileId;
	}
	public String getLaftUserId() {
		return laftUserId;
	}
	public void setLaftUserId(String laftUserId) {
		this.laftUserId = laftUserId;
	}
	public String getCurrentlyAssigned() {
		return currentlyAssigned;
	}
	public void setCurrentlyAssigned(String currentlyAssigned) {
		this.currentlyAssigned = currentlyAssigned;
	}
	public String getCurrentlyOpen() {
		return currentlyOpen;
	}
	public void setCurrentlyOpen(String currentlyOpen) {
		this.currentlyOpen = currentlyOpen;
	}
	public String getReconciled() {
		return reconciled;
	}
	public void setReconciled(String reconciled) {
		this.reconciled = reconciled;
	}
	public String getSurveyReceived() {
		return surveyReceived;
	}
	public void setSurveyReceived(String surveyReceived) {
		this.surveyReceived = surveyReceived;
	}
	
	

}
