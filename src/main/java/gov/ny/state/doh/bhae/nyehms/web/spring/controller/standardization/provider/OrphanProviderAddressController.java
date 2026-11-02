package gov.ny.state.doh.bhae.nyehms.web.spring.controller.standardization.provider;

import gov.ny.state.doh.bhae.nyehms.web.ajax.json.GridObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.standardization.provider.SubProviderVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.security.UserObject;
import gov.ny.state.doh.bhae.nyehms.web.spring.service.standardization.provider.OrphanProviderAddressService;
import gov.ny.state.doh.bhae.nyehms.web.spring.util.GetGridObject;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * The controller class to manage the orphan address operations. Orphan addresses are those
 * addresses which are standardized but not associated to any provider.
 * For convenience we refer to these type of provider addresses as 
 * Orphan Provider Addresses. A standardized address become an orphan address when it is
 * unlinked from its parent provider. An orphan address can again become a child of another provider
 * once it gets linked to that provider  
 */
@RequiredArgsConstructor
@Controller
public class OrphanProviderAddressController {
	/*
	 * The spring injected GetGridObject reference
	 */
	private final GetGridObject getGridObject;
	/*
	 * The spring injected reference of the service implementation 
	 */
	private final OrphanProviderAddressService orphanProviderAddressService; 
	/*
	 * Get a list of orphan provider addresses 
	 * @param request HTTPRequest reference
	 * @return GridObject the GridObject instance which holds the orphan addresses 
	 */
	@RequestMapping(value="/getOrphanProvideraddresses.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody GridObject getOrphanProviderAddressList(HttpServletRequest request, UserObject userObject){


		Map<String, String> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		GridObject jsonResponse = getGridObject.GetGrid(orphanProviderAddressService.getOrphanProviderAddresses(bindingResult, userObject));
		return jsonResponse;
	}
	/*
	 * This method links an orphan address to a provider. 
	 * After this operation the orphan address is associated to this provider and no longer an orphan
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @param providerId the parent provider to which this orphan address should be linked
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/linkOrphanAddressToProvider.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> linkOrphanAddressToProvider(@RequestParam long  providerAddressId, @RequestParam long  providerId,HttpServletRequest request, UserObject userObject){


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		orphanProviderAddressService.linkOrphanAddressToProvider(providerAddressId,providerId, bindingResult, userObject);
		
		return targetMap;
	}
	/*
	 * This method links a raw provider address to an orphan provider address. 
	 * Linking a raw address to an orphan address can not be completed 
	 * unless the orphan gets associated to a provider. Hence this operation first expect the user
	 * to select a provider and link the orphan address to this provider. After this sub-operation
	 * is successfully completed, the raw address is linked to the orphan address.
	 * Please see {@link #linkOrphanAddressToProvider(@RequestParam long  providerAddressId, @RequestParam long  providerId,HttpServletRequest request)}    
	 * After this operation the orphan address is associated to this provider and no longer an orphan
	 * and the raw provider address is associated to orphan provider address and no longer a raw provider address.
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @param providerId the parent provider to which this orphan address should be linked
	 * @rawProviderAddressId the raw provider address which needs to be linked to the orphan address
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/linkRawToOrphanProviderAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> linkRawToOrphanProviderAddress(@RequestParam long  providerAddressId, @RequestParam long  providerId,@RequestParam long  rawProviderAddressId, HttpServletRequest request, UserObject userObject){


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		orphanProviderAddressService.linkRawToOrphanProviderAddress(providerAddressId,providerId,rawProviderAddressId,bindingResult, userObject);
		return targetMap;
	}
	/*
	 * Adding New Provider and  linking it to Provider Address
	 * @param providerAddressId the provider Address id which needs to be linked
	 * @param subProviderVO the value object which holds the provider details
	 * @param request HTTPRequest reference 
	 * @return Map containing transaction result message
	 */
	@RequestMapping(value="/addNewProviderAndLinkingOrphanAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewProviderAndLinkingOrphanAddress(@RequestParam long  providerAddressId, @ModelAttribute("providerVO") SubProviderVO providerVO, HttpServletRequest request, UserObject userObject){


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		orphanProviderAddressService.addNewProviderAndLinkingOrphanAddrs(providerAddressId, providerVO, bindingResult, userObject);
		return targetMap;
	}
	/*
	 * This method links a raw provider address to an orphan provider address. 
	 * Linking a raw address to an orphan address can not be completed 
	 * unless the orphan gets associated to a provider. Hence this operation first expect the user
	 * to create a provider and link the orphan address to this provider. After this sub-operation
	 * is successfully completed, the raw address is linked to the orphan address.
	 * Please see {@link #linkRawToOrphanProviderAddress(@RequestParam long  providerAddressId, @RequestParam long  providerId,@RequestParam long  rawProviderAddressId, HttpServletRequest request)}    
	 * After this operation a new provider is created and the orphan address is associated to this provider and no longer an orphan.
	 * The raw provider address is associated to orphan provider address and no longer a raw provider address.
	 * @param providerAddressId the orphan address for which parent provider linking is needed
	 * @rawProviderAddressId the raw provider address which needs to be linked to the orphan address
 	 * @param providerVO the parent provider to be created and this orphan address should be linked
 	 * @param request HTTPRequest reference
 	 * @return String the transaction result
	 */
	@RequestMapping(value="/addNewProviderAndlinkRawToOrphanProviderAddress.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<Object, Object> addNewProviderAndLinkRawToOrphanProviderAddress(@RequestParam long  providerAddressId, long rawProviderAddressId, @ModelAttribute("providerVO") SubProviderVO providerVO,HttpServletRequest request, UserObject userObject){


		Map<Object, Object> targetMap= new HashMap<>();
		BindingResult bindingResult = new MapBindingResult(targetMap, "targetMap");

		orphanProviderAddressService.addNewProviderAndLinkRawToOrphanProviderAddress(providerAddressId,rawProviderAddressId,providerVO,bindingResult, userObject);
		return targetMap; 
	}
}
