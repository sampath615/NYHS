package gov.ny.state.doh.bhae.nyehms.web.spring.domain.reports;

public class ProvPortalSubmissionReportVO {
	
	private String file_type_cd;
	private String upload_filename;
	private String upload_dt;
	private String provider_id;
	private String provider_name;
	private String persons_in_file;
	private String matches_to_registry;
	private String non_matches;
	private String conf_cases;
	private String not_conf_cases;
	private String meets_case_def;
	private String meets_eligibility;
	
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
	public String getUpload_dt() {
		return upload_dt;
	}
	public void setUpload_dt(String upload_dt) {
		this.upload_dt = upload_dt;
	}
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}
	public String getPersons_in_file() {
		return persons_in_file;
	}
	public void setPersons_in_file(String persons_in_file) {
		this.persons_in_file = persons_in_file;
	}
	public String getMatches_to_registry() {
		return matches_to_registry;
	}
	public void setMatches_to_registry(String matches_to_registry) {
		this.matches_to_registry = matches_to_registry;
	}
	public String getNon_matches() {
		return non_matches;
	}
	public void setNon_matches(String non_matches) {
		this.non_matches = non_matches;
	}
	public String getConf_cases() {
		return conf_cases;
	}
	public void setConf_cases(String conf_cases) {
		this.conf_cases = conf_cases;
	}
	public String getNot_conf_cases() {
		return not_conf_cases;
	}
	public void setNot_conf_cases(String not_conf_cases) {
		this.not_conf_cases = not_conf_cases;
	}
	public String getMeets_case_def() {
		return meets_case_def;
	}
	public void setMeets_case_def(String meets_case_def) {
		this.meets_case_def = meets_case_def;
	}
	public String getMeets_eligibility() {
		return meets_eligibility;
	}
	public void setMeets_eligibility(String meets_eligibility) {
		this.meets_eligibility = meets_eligibility;
	}
	public String getProvider_id() {
		return provider_id;
	}
	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}

}
