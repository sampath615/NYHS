package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAddressVO;

import java.util.List;
import java.util.Map;
/*
 * The DAO for orphan provider address operations
 */
public interface OrphanProviderAddressDAO {
	/*
	 * Get a list of orphan provider addresses 
	 * @return List list of orphan addresses in SubProviderAddressVO instances 
	 */
public List<SubProviderAddressVO> getOrphanProviderAddresses();
	/*
	 * This method links an orphan address to a provider. 
	 * After this operation the orphan address is associated to this provider and no longer an orphan
	 * @param inOutParameterMap the in/out parameter map for the mybatis SQL map to call the stored procedure
	 */
public void linkOrphanAddressToProvider(Map<Object,Object> inOutParameterMap);
}
