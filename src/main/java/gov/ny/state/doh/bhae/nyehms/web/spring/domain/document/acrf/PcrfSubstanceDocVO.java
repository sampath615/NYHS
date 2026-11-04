package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.acrf;

public class PcrfSubstanceDocVO {
	
	private String subStanceName;
	private String refCd;
	private String subStancecode;
	private String screenDate;
	private String screenResult;
	private String subUseResult;
	private String screeningDone;
	private String otherDetails;
	
	private String pregOutcome;
	private String pregOutcomeYear;
	
	public String getSubStanceName() {
		return subStanceName;
	}
	public void setSubStanceName(String subStanceName) {
		this.subStanceName = subStanceName;
	}
	public String getSubStancecode() {
		return subStancecode;
	}
	public void setSubStancecode(String subStancecode) {
		this.subStancecode = subStancecode;
	}
	public String getScreenDate() {
		return screenDate;
	}
	public void setScreenDate(String screenDate) {
		if("../../....".equals(screenDate)) {
			this.screenDate = null;
		}else {
			this.screenDate = screenDate;
		}
		
		
	}
	public String getScreenResult() {
		return screenResult;
	}
	public void setScreenResult(String screenResult) {
		this.screenResult = screenResult;
	}
	public String getSubUseResult() {
		return subUseResult;
	}
	public void setSubUseResult(String subUseResult) {
		this.subUseResult = subUseResult;
	}
	public String getRefCd() {
		return refCd;
	}
	public void setRefCd(String refCd) {
		this.refCd = refCd;
	}
	public String getScreeningDone() {
		return screeningDone;
	}
	public void setScreeningDone(String screeningDone) {
		this.screeningDone = screeningDone;
	}
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	
	
	public String getPregOutcome() {
		return pregOutcome;
	}
	public void setPregOutcome(String pregOutcome) {
		this.pregOutcome = pregOutcome;
	}
	public String getPregOutcomeYear() {
		return pregOutcomeYear;
	}
	public void setPregOutcomeYear(String pregOutcomeYear) {
		this.pregOutcomeYear = pregOutcomeYear;
	}
	
}
