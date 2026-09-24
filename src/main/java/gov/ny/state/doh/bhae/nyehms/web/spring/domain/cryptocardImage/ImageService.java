package gov.ny.state.doh.bhae.nyehms.web.spring.domain.cryptocardImage;



/**
 * This is interface for the image service.
 * @author vxp06
 */
public interface ImageService {
    
    /**
     * This method will return image location on server, based on todays date
     * Caller of this will have to use it in their 
     * <Img src="<% call the service >" >
     * 
     * @return  path of the image
     */
    
    public String getImage();
    
}
