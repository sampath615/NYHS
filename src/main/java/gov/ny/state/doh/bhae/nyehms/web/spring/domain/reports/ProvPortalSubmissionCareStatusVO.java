package gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports;

public class ProvPortalSubmissionCareStatusVO {
	
	private String file_type_cd;
	private String upload_filename;
	private String no_confirmatory_results_reported;
	private String not_a_provider_of_this_patient;
	private String deceased;
	private String current_residence_is_out_of_state;
	private String in_care;
	private String diagnosed_never_linked_to_care;
	private String out_of_care;
	private String none;
	private String totals;
	public String getFile_type_cd() {
		return file_type_cd;
	}
	public void setFile_type_cd(String file_type_cd) {
		this.file_type_cd = file_type_cd;
	}
	public String getUpload_filename() {
		return upload_filename;
	}
	public void setUpload_filename(String upload_filename) {
		this.upload_filename = upload_filename;
	}
	public String getNo_confirmatory_results_reported() {
		return no_confirmatory_results_reported;
	}
	public void setNo_confirmatory_results_reported(String no_confirmatory_results_reported) {
		this.no_confirmatory_results_reported = no_confirmatory_results_reported;
	}
	public String getNot_a_provider_of_this_patient() {
		return not_a_provider_of_this_patient;
	}
	public void setNot_a_provider_of_this_patient(String not_a_provider_of_this_patient) {
		this.not_a_provider_of_this_patient = not_a_provider_of_this_patient;
	}
	public String getDeceased() {
		return deceased;
	}
	public void setDeceased(String deceased) {
		this.deceased = deceased;
	}
	public String getCurrent_residence_is_out_of_state() {
		return current_residence_is_out_of_state;
	}
	public void setCurrent_residence_is_out_of_state(String current_residence_is_out_of_state) {
		this.current_residence_is_out_of_state = current_residence_is_out_of_state;
	}
	public String getIn_care() {
		return in_care;
	}
	public void setIn_care(String in_care) {
		this.in_care = in_care;
	}
	public String getDiagnosed_never_linked_to_care() {
		return diagnosed_never_linked_to_care;
	}
	public void setDiagnosed_never_linked_to_care(String diagnosed_never_linked_to_care) {
		this.diagnosed_never_linked_to_care = diagnosed_never_linked_to_care;
	}
	public String getOut_of_care() {
		return out_of_care;
	}
	public void setOut_of_care(String out_of_care) {
		this.out_of_care = out_of_care;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getTotals() {
		return totals;
	}
	public void setTotals(String totals) {
		this.totals = totals;
	}
	
}
