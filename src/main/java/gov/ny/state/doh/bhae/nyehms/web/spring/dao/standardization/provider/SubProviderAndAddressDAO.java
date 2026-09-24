package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderAndAddressVO;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


/**
* This interface containing methods related to Provider search operations
* 
*  
* @author  
* @version 
*/
public interface SubProviderAndAddressDAO {
	
	
	//get the providerAddress for given ProviderID
    public List<SubProviderAndAddressVO> getProvAddressListByProvId(SubProviderAndAddressVO subProviderAndAddressVO);
	
    //Add New Sub Provider Address
    @Transactional
	public void addNewSubProvAddrs(Map<Object, Object> map);
	/* Adding New Provider Address to an existing Provider
	 * @param inOutParameterMap the map which holds the in out parameters for the stored procedure call
	 */
    @Transactional
	public void addNewAddrs(Map<Object, Object> inOutParameterMap);

	public int createRawProviderAddress(Map<Object, Object> inOutParameterMap);

	public int checkRawProviderAddressExistance(
			SubProviderAndAddressVO  subProviderAndAddressVO);

}
