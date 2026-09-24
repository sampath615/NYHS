package gov.ny.state.doh.bhae.nyehms.web.spring.dao.standardization.facility;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface FacilityCodedDAO {
	public List<Map<String, String>> getFacilityTypeCodes()throws DataAccessException;
}
