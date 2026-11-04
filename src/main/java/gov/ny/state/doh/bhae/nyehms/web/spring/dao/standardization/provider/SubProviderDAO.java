package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.provider;


import java.util.List;
import java.util.Map;

public interface SubProviderDAO {
	
	// get the Sub Provider List
	
	// get the Sub Provider List using Sub Provider Address Id's 
	
	//Adding New Provider Address
    public void addNewProvider(Map<Object, Object> map);
    
    public List<Map<String, String>> getActiveProviders(String providerName);

	public int unableStandardizeRawProviderRecord(Map<Object, Object> map);

	public int unableStandardizeProviderAddressRecord(Map<Object, Object> map);
	

}
