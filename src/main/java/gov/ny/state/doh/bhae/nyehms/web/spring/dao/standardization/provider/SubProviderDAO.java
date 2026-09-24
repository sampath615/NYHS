package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;

import java.util.List;
import java.util.Map;

public interface SubProviderDAO {
	
	// get the Sub Provider List
	public List<SubProviderVO> getSubProviderList(SubProviderVO subProviderVO );
	
	// get the Sub Provider List using Sub Provider Address Id's 
	public List<SubProviderVO> getSubProvidersListForSubProvAdrsIds(Map<Object, Object> map);
	
	//Adding New Provider Address
    public void addNewProvider(Map<Object, Object> map);
    
    public List<Map<String, String>> getActiveProviders(String providerName);

	public int unableStandardizeRawProviderRecord(Map<Object, Object> map);

	public int unableStandardizeProviderAddressRecord(Map<Object, Object> map);
	

}
