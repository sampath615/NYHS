package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.ccd;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RawCCDProblemVO {

	private int uphnId;
	private int ccdDocID;
	private int problemSeqNo;
	private int problemDocId;
	private String problemDateText;
	private RawCCDNameVO providerName;
	private String facilityName;
	private String problemCode;
	private String problemCodeSystemName;
	private String problemDisplayName;
	private String encounterId;
	private String problemCategory;
	private boolean hasEntryRelationShipProblem = false;
	private String problemText;
	private String problemCodeSystem;
	
	/*Only for Document Viewer*/
	private String providerFullName;
	/**/

	public int getUphnId() {
		return uphnId;
	}

	public void setUphnId(int uphnId) {
		this.uphnId = uphnId;
	}

	public int getCcdDocID() {
		return ccdDocID;
	}

	public void setCcdDocID(int ccdDocID) {
		this.ccdDocID = ccdDocID;
	}

	public int getProblemSeqNo() {
		return problemSeqNo;
	}

	public void setProblemSeqNo(int problemSeqNo) {
		this.problemSeqNo = problemSeqNo;
	}

	public int getProblemDocId() {
		return problemDocId;
	}

	public void setProblemDocId(int problemDocId) {
		this.problemDocId = problemDocId;
	}

	public String getProblemDateText() {
		return problemDateText;
	}

	public void setProblemDateText(String problemDateText) {
		this.problemDateText = problemDateText;
	}

	public RawCCDNameVO getProviderName() {
		return providerName;
	}

	public void setProviderName(RawCCDNameVO providerName) {
		this.providerName = providerName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getProblemCode() {
		return problemCode;
	}

	public void setProblemCode(String problemCode) {
		this.problemCode = problemCode;
	}

	public String getProblemCodeSystemName() {
		return problemCodeSystemName;
	}

	public void setProblemCodeSystemName(String problemCodeSystemName) {
		this.problemCodeSystemName = problemCodeSystemName;
	}

	public String getProblemDisplayName() {
		return problemDisplayName;
	}

	public void setProblemDisplayName(String problemDisplayName) {
		this.problemDisplayName = problemDisplayName;
	}

	public String getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(String encounterId) {
		this.encounterId = encounterId;
	}

	public String getProblemCategory() {
		return problemCategory;
	}

	public void setProblemCategory(String problemCategory) {
		this.problemCategory = problemCategory;
	}

	public boolean isHasEntryRelationShipProblem() {
		return hasEntryRelationShipProblem;
	}

	public void setHasEntryRelationShipProblem(boolean hasEntryRelationShipProblem) {
		this.hasEntryRelationShipProblem = hasEntryRelationShipProblem;
	}

	public String getProblemText() {
		return problemText;
	}

	public void setProblemText(String problemText) {
		this.problemText = problemText;
	}

	public String getProblemCodeSystem() {
		return problemCodeSystem;
	}

	public void setProblemCodeSystem(String problemCodeSystem) {
		this.problemCodeSystem = problemCodeSystem;
	}

	public String getProviderFullName() {
		return providerFullName;
	}

	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
