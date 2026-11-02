package gov.ny.state.doh.bhae.nyehms.web.spring.domain.cryptocardImage;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import gov.ny.state.doh.bhae.nyehms.web.spring.dao.CommonDAO;

/**
 * This is implementation of the ImageService interface
 *
 * @author vxp06
 */
@RequiredArgsConstructor
@Component
public class ImageServiceImpl implements ImageService {
    
   /**
     * This method will query nyehms_parameter table, which contains images
     * related to the given date range. and returns name of the image to the
     * front end, to be displayed on cryptocard page
     *
     * @return ArrayList
     */
	
	private final CommonDAO commonDAO;
	 String seasonNameAndDay = null;
    @Override
    public String getImage() {
    	
    	
    	
    	String imageName = commonDAO.getCryptoImageName();
    	
        return "/images/Crypto/" + imageName;
    }
    
}
