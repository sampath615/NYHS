package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.RawProviderAddressVO;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface RawProviderAddressDAO {
	
	public List<RawProviderAddressVO> getRawProvAddrsList(Map<Object, Object> inOutParameterMap);
	
	/*
	 * linking Raw Provider Address To Sub Provider Address ID
	 * This method is annotated as transactional in nature and the transaction is ensured by the
	 * underlying stored procedure implementation 
	 * @param inOutParameterMap the map holding in out parameters for the stored procedure call 
	 */
	@Transactional
	public void linkRawProvToSubProvAddrs(Map<Object, Object> inOutParameterMap);
	
	@Transactional
	public void linkRawProvAddressesToSubProvAddrs(Map<Object, Object> inOutParameterMap);
	
	// get the  Raw Provider Address Id's 	
	public List<Long> getRawProvAdrsIdsForSearch(RawProviderAddressVO rawProviderAddressVO);
	
	//SubGrid of Sub Grid of Sub Provider i.e Raw Provider Address Id. getting the Raw Provider Addresses Using Sub Provider Address Id
	public List<RawProviderAddressVO> getRawProvAdrsListBySubprovAdrsId(Map<Object, Object> map);
	
	/*
	 * Flag the provider data as facility and copy it to facility data. This method is 
	 * annotated as transactional in nature and the transaction is ensured by the underlying
	 * stored procedure implementation 
	 * @param providerId Unique identifier of the provider data to be moved
	 * @return success or failure message based on the transaction   
	 */
	@Transactional
	public void flagProviderDataAsFacilty(Map<Object,Object> inOutParamaterMap);
	
	public List<RawProviderAddressVO> getRawProvAddrsNewList(Map<Object, Object> inOutParameterMap);
	
	@Transactional
	public void flagSelectedRawProviderAsFacilty(Map<Object,Object> inOutParamaterMap);

	public List<JSONObject> getMasterProvRecords();

	public JSONObject getMasterProvData(String providerId);

}
