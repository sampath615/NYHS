package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility;

public class SubFacilityForRawFacilityVO {
	private long subFacilityId;
	private long inputRawFacilityId;
	
	public long getSubFacilityId() {
		return subFacilityId;
	}
	public void setSubFacilityId(long subFacilityId) {
		this.subFacilityId = subFacilityId;
	}
	public long getInputRawFacilityId() {
		return inputRawFacilityId;
	}
	public void setInputRawFacilityId(long inputRawFacilityId) {
		this.inputRawFacilityId = inputRawFacilityId;
	}
}
