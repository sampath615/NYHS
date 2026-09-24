package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.ProviderDetailsVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;
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
public interface ProviderDetailsDAO {
	
	/**
	 * Returns the complete Provider list containing the First name,
	 * Last name, middle name and the count of Address list associated to the Provider
	 * 
	 * This data will render depends on the search critera     
	 *
	 * @param  ProviderDetailsVO  Provider object related beans  
	 * @param  result  using to store error related information
	 * @param userObject, sessionID  used to track Exception related information 
	 * 
	 * @return      List of Providers
	 */
	public List<ProviderDetailsVO> getSrchProviderDetailsWithAddressList(Map<Object, Object> map);
	
	public List<ProviderDetailsVO> getProviderDetailsByProvider(Map<Object, Object> map);
	
	public List<ProviderDetailsVO> getProviderDetailsByProviderAddress(Map<Object, Object> map);
	
	public List<ProviderDetailsVO> getProviderDetailsByRawProviderAddress(Map<Object, Object> map);

	public List<RawProviderAddressVO> getRawProviderDetails(Map<Object, Object> map);
}
