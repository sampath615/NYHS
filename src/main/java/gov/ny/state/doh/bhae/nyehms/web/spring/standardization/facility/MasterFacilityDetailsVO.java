package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.facility;

import java.util.Comparator;
import java.util.List;

public class MasterFacilityDetailsVO  implements Comparator<MasterFacilityDetailsVO>{
	private long masterFacilityId;
	private String masterFacilityTypeCode;
	public String getMasterFacilityTypeCode() {
		return masterFacilityTypeCode;
	}

	public void setMasterFacilityTypeCode(String masterFacilityTypeCode) {
		this.masterFacilityTypeCode = masterFacilityTypeCode;
	}
	private String masterFacilityName;
	
	/*
	 * Default constructor
	 */
	public MasterFacilityDetailsVO(){
		
	}
	
	/*
	 * Constructor which initializes the instance with a facility id
	 * @param facility the Facility identifier 
	 */
	public MasterFacilityDetailsVO(long masterFacilityId){
		this.masterFacilityId=masterFacilityId;
	}
	
	public long getMasterFacilityId() {
		return masterFacilityId;
	}
	public void setMasterFacilityId(long masterFacilityId) {
		this.masterFacilityId = masterFacilityId;
	}

	public String getMasterFacilityName() {
		return masterFacilityName;
	}
	public void setMasterFacilityName(String masterFacilityName) {
		this.masterFacilityName = masterFacilityName;
	}
	private List<SubFacilityVO> subFacilities;
	public List<SubFacilityVO> getSubFacilities() {
		return subFacilities;
	}
	public void setSubFacilities(List<SubFacilityVO> subFacilities) {
		this.subFacilities = subFacilities;
	}

	/*
	 * Overridden equals method for comparing the instances. The instances are 
	 * compared for equality based on the facility identification value they
	 * hold inside the facilityId instance variable. 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterFacilityDetailsVO other = (MasterFacilityDetailsVO) obj;
		if (masterFacilityId != other.masterFacilityId)
			return false;
		return true;
	}

	/*
	 * Overridden hash code implementation for this object.
	 * The hash code generated is based on the facility identification 
	 * value each instance hold inside the facilityId instance variable. 
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (masterFacilityId ^ (masterFacilityId >>> 32));
		return result;
		
	}
	
	

	@Override
	public int compare(MasterFacilityDetailsVO masterFacilityDetailsVOFrom, MasterFacilityDetailsVO masterFacilityDetailsVOTo) {
		long fromMasterFacilityId = masterFacilityDetailsVOFrom.masterFacilityId;
		if(fromMasterFacilityId == -999){
			return -1;
			
		}else{
			return masterFacilityDetailsVOFrom.getMasterFacilityName().trim().compareToIgnoreCase(masterFacilityDetailsVOTo.getMasterFacilityName().trim());
		}
	}

}
