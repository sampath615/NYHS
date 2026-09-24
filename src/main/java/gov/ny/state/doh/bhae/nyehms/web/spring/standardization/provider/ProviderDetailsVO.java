package gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider;

import java.util.Comparator;
import java.util.List;

public class ProviderDetailsVO  implements Comparator<ProviderDetailsVO>{
	
	private long providerId;
	private String providerFname;
	private String providerMname;
	private String providerLname;
	private String providerFullName;
	private String providerSuffix;
	private String providerNPI;
	private List<SubProviderAddressVO> subProviderAddresses;
	private boolean fromNPIRegistry = false;
	private String providerSpeciality;
	/*
	 * Default constructor
	 */
	public ProviderDetailsVO(){
		
	}
	/*
	 * Constructor which initializes the instance with a provider id
	 * @param providerId the provider identifier 
	 */
	public ProviderDetailsVO(long providerId){
		this.providerId=providerId;
	}
	
	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getProviderFname() {
		return providerFname;
	}

	public void setProviderFname(String providerFname) {
		this.providerFname = providerFname;
	}

	public String getProviderMname() {
		return providerMname;
	}

	public void setProviderMname(String providerMname) {
		this.providerMname = providerMname;
	}

	public String getProviderLname() {
		return providerLname;
	}

	public void setProviderLname(String providerLname) {
		this.providerLname = providerLname;
	}

	public String getProviderSuffix() {
		return providerSuffix;
	}

	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}


	public List<SubProviderAddressVO> getSubProviderAddresses() {
		return subProviderAddresses;
	}

	public void setSubProviderAddresses(
			List<SubProviderAddressVO> subProviderAddresses) {
		this.subProviderAddresses = subProviderAddresses;
	}	
	
	public String getProviderFullName() {
		return providerFullName;
	}
	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}
	
	public String getProviderNPI() {
		return providerNPI;
	}
	public void setProviderNPI(String providerNPI) {
		this.providerNPI = providerNPI;
	}
	
	public boolean isFromNPIRegistry() {
		return fromNPIRegistry;
	}
	public void setFromNPIRegistry(boolean fromNPIRegistry) {
		this.fromNPIRegistry = fromNPIRegistry;
	}
	
	public String getProviderSpeciality() {
		return providerSpeciality;
	}
	public void setProviderSpeciality(String providerSpeciality) {
		this.providerSpeciality = providerSpeciality;
	}
	/*
	 * Overridden hash code implementation for this object.
	 * The hash code generated is based on the provider identification 
	 * value each instance hold inside the providerId instance variable. 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (providerId ^ (providerId >>> 32));
		return result;
	}
	/*
	 * Overridden equals method for comparing the instances. The instances are 
	 * compared for equality based on the provider identification value they
	 * hold inside the providerId instance variable. 
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
		ProviderDetailsVO other = (ProviderDetailsVO) obj;
		if (providerId != other.providerId)
			return false;
		return true;
	}
	
	@Override
	public int compare(ProviderDetailsVO providerDetailsVOFrom, ProviderDetailsVO providerDetailsVOTo) {
		long fromProviderId = providerDetailsVOFrom.providerId;
		if(fromProviderId == -999){
			return -1;
			
		}else{
			return providerDetailsVOFrom.getProviderFullName().trim().compareToIgnoreCase(providerDetailsVOTo.getProviderFullName().trim());
		}
	}

}
