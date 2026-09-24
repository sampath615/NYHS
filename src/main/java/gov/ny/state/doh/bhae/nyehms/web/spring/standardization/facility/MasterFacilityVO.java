package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility;

import java.util.Date;

public class MasterFacilityVO {
	private long masterFacilityId;
	private String facilityTypeDesc;
	private String masterFacilityName;
	private long newMasterFacilityId;
	private String createUid;
	private Date dateCreated;
	private Date dateended;
	private String endUid;
	private int totalSubFacility;
	private long inputSubFacilityId;

	public long getMasterFacilityId() {
		return masterFacilityId;
	}

	public void setMasterFacilityId(long masterFacilityId) {
		this.masterFacilityId = masterFacilityId;
	}

	public String getFacilityTypeDesc() {
		return facilityTypeDesc;
	}

	public void setFacilityTypeDesc(String facilityTypeDesc) {
		this.facilityTypeDesc = facilityTypeDesc;
	}

	public String getMasterFacilityName() {
		return masterFacilityName;
	}

	public void setMasterFacilityName(String masterFacilityName) {
		this.masterFacilityName = masterFacilityName;
	}

	public long getNewMasterFacilityId() {
		return newMasterFacilityId;
	}

	public void setNewMasterFacilityId(long newMasterFacilityId) {
		this.newMasterFacilityId = newMasterFacilityId;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateended() {
		return dateended;
	}

	public void setDateended(Date dateended) {
		this.dateended = dateended;
	}

	public String getEndUid() {
		return endUid;
	}

	public void setEndUid(String endUid) {
		this.endUid = endUid;
	}

	public int getTotalSubFacility() {
		return totalSubFacility;
	}

	public void setTotalSubFacility(int totalSubFacility) {
		this.totalSubFacility = totalSubFacility;
	}

	public long getInputSubFacilityId() {
		return inputSubFacilityId;
	}

	public void setInputSubFacilityId(long inputSubFacilityId) {
		this.inputSubFacilityId = inputSubFacilityId;
	}

	@Override
	public String toString() {
		return "MasterFacilityVO [masterFacilityId=" + masterFacilityId + ", masterFacilityName=" + masterFacilityName + ", newMasterFacilityId=" + newMasterFacilityId + ", createUid="
				+ createUid + ", dateCreated=" + dateCreated + ", dateended=" + dateended + ", endUid=" + endUid
				+ ", totalSubFacility=" + totalSubFacility + ", inputSubFacilityId=" + inputSubFacilityId + "]";
	}
	
}
