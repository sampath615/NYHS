package gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.hl7;

import java.util.Date;

public class RawHL7MshVO {

	private String mshSendingFacilityCode;
	private Date mshMessageDateRaw;
	private String mshMessageControlId;
	private String mshSendingFacilityName;
	private String mshCreateDate;
	private String mshSendingFacilityCodeSystem;

	public RawHL7MshVO() {
		// TODO Auto-generated constructor stub
	}

	public String getMshSendingFacilityCode() {
		return mshSendingFacilityCode;
	}

	public void setMshSendingFacilityCode(String mshSendingFacilityCode) {
		this.mshSendingFacilityCode = mshSendingFacilityCode;
	}

	public Date getMshMessageDateRaw() {
		return mshMessageDateRaw;
	}

	public void setMshMessageDateRaw(Date mshMessageDateRaw) {
		this.mshMessageDateRaw = mshMessageDateRaw;
	}

	public String getMshMessageControlId() {
		return mshMessageControlId;
	}

	public void setMshMessageControlId(String mshMessageControlId) {
		this.mshMessageControlId = mshMessageControlId;
	}

	public String getMshSendingFacilityName() {
		return mshSendingFacilityName;
	}

	public void setMshSendingFacilityName(String mshSendingFacilityName) {
		this.mshSendingFacilityName = mshSendingFacilityName;
	}

	public String getMshCreateDate() {
		return mshCreateDate;
	}

	public void setMshCreateDate(String mshCreateDate) {
		this.mshCreateDate = mshCreateDate;
	}

	public String getMshSendingFacilityCodeSystem() {
		return mshSendingFacilityCodeSystem;
	}

	public void setMshSendingFacilityCodeSystem(String mshSendingFacilityCodeSystem) {
		this.mshSendingFacilityCodeSystem = mshSendingFacilityCodeSystem;
	}

}
