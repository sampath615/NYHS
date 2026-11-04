package gov.ny.state.doh.bhae.nyehms.web.spring.controller.cryptocardImage;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.cryptocardImage.ImageService;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This is the controller for the image class.
 *
 * @author vxp06
 */
@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * This method will listen to request with path /getAllGraphData and will
     * return JSON Obj containing data for all the graphs to be displayed at
     * home page as dashboard
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getImageLocation", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String, String> getImageLocation(HttpServletRequest request) {
        // get the location of image from service
        String location = "";
        location = imageService.getImage();
        
        Map<String, String> winningImageMap = new HashMap<>();;
        winningImageMap.put("imageLocation", location);
        
        return winningImageMap;
    }
    
  

}
