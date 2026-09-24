package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

public class RawCCDPersonDocVO {
	
	private int DOCUMENT_ID;
	private int UPHN_TRANX_IN_ID;
	private String DATE_CREATED;
	private String MPI;
	private RawCCDNameVO name;
	private String DOB_TEXT;
	private String GENDER;
	private String PERSON_PHONE_HOME;
	private String PERSON_PHONE_WORK;
	private String PERSON_PHONE_CELL;
	private String RACE_DESC;
	private String RACE_CODE;
	private String ETHNICITY;
	private String LANGUAGE;
	private int CURRENT_PROCESS_STATUS;
	private String cleartextPayload;
	private String hieName;
	private String payloadname;
	
	public int getDOCUMENT_ID() {
		return DOCUMENT_ID;
	}
	public void setDOCUMENT_ID(int dOCUMENT_ID) {
		DOCUMENT_ID = dOCUMENT_ID;
	}
	/*CURRENT PROCESS STATUS ADDED-Monish*/
	public int getCURRENT_PROCESS_STATUS() {
		return CURRENT_PROCESS_STATUS;
	}
	public void setCURRENT_PROCESS_STATUS(int cURRENT_PROCESS_STATUS) {
		CURRENT_PROCESS_STATUS = cURRENT_PROCESS_STATUS;
	}
	
	public int getUPHN_TRANX_IN_ID() {
		return UPHN_TRANX_IN_ID;
	}
	public void setUPHN_TRANX_IN_ID(int uPHN_TRANX_IN_ID) {
		UPHN_TRANX_IN_ID = uPHN_TRANX_IN_ID;
	}
	public String getDATE_CREATED() {
		return DATE_CREATED;
	}
	public void setDATE_CREATED(String dATE_CREATED) {
		DATE_CREATED = dATE_CREATED;
	}
	public String getMPI() {
		return MPI;
	}
	public void setMPI(String mPI) {
		MPI = mPI;
	}
	public RawCCDNameVO getName() {
		return name;
	}
	public void setName(RawCCDNameVO name){
		this.name = name;
	}
	public String getDOB_TEXT() {
		return DOB_TEXT;
	}
	public void setDOB_TEXT(String dOB_TEXT) {
		DOB_TEXT = dOB_TEXT;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getPERSON_PHONE_HOME() {
		return PERSON_PHONE_HOME;
	}
	public void setPERSON_PHONE_HOME(String pERSON_PHONE_HOME) {
		PERSON_PHONE_HOME = pERSON_PHONE_HOME;
	}
	public String getPERSON_PHONE_WORK() {
		return PERSON_PHONE_WORK;
	}
	public void setPERSON_PHONE_WORK(String pERSON_PHONE_WORK) {
		PERSON_PHONE_WORK = pERSON_PHONE_WORK;
	}
	public String getPERSON_PHONE_CELL() {
		return PERSON_PHONE_CELL;
	}
	public void setPERSON_PHONE_CELL(String pERSON_PHONE_CELL) {
		PERSON_PHONE_CELL = pERSON_PHONE_CELL;
	}
	public String getRACE_DESC() {
		return RACE_DESC;
	}
	public void setRACE_DESC(String rACE_DESC) {
		RACE_DESC = rACE_DESC;
	}
	public String getRACE_CODE() {
		return RACE_CODE;
	}
	public void setRACE_CODE(String rACE_CODE) {
		RACE_CODE = rACE_CODE;
	}
	public String getETHNICITY() {
		return ETHNICITY;
	}
	public void setETHNICITY(String eTHNICITY) {
		ETHNICITY = eTHNICITY;
	}
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	public void setLANGUAGE(String lANGUAGE) {
		LANGUAGE = lANGUAGE;
	}
	public String getCleartextPayload() {
		return cleartextPayload;
	}
	public void setCleartextPayload(String cleartextPayload) {
		this.cleartextPayload = cleartextPayload;
	}
	
	public String getHieName() {
		return hieName;
	}

	public void setHieName(String hieName) {
		this.hieName = hieName;
	}
	
	public String getPayloadname() {
		return payloadname;
	}
	public void setPayloadname(String payloadname) {
		this.payloadname = payloadname;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MPI: " + MPI + "\n");
		sb.append("DOB_TEXT: " + DOB_TEXT + "\n");
		sb.append("DATE_CREATED: " + DATE_CREATED + "\n");
		if (name != null) {
			sb.append("Patient name: " + name.getLastName() + ", "
					+ name.getFirstName() + " " + name.getMiddleName() + "\n");
		}
		sb.append("Ethnicity: " + ETHNICITY + "\n");
		sb.append("Gender code: " + GENDER + "\n");
		sb.append("Language code: " + LANGUAGE + "\n");
		sb.append("Race code and desc: " + RACE_CODE + "\n" + RACE_DESC + "\n");
		sb.append("Home Phone: " + PERSON_PHONE_HOME + "\n");
		sb.append("Cell phone: " + PERSON_PHONE_CELL + "\n");
		sb.append("Work phone: " + PERSON_PHONE_WORK + "\n");
		return sb.toString();
	}

}
