package gov.ny.state.doh.bhae.nyehms.web.spring.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.test.Person;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.test.PersonInfo;

/*
 * This class is being used for testing purpose of any new functionality.
 */

@Controller
public class TestController {
	
	
	private static Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/SpringValidationSubmit", method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute("person") Person person, 
								@Valid Person per, BindingResult result) { 
		
		log.debug("********** Strp 1");		
		String name = person.getPersonInfo().getFIRST_NAME();
				
		log.debug("********** Strp 2");
		
		log.debug("******************** Get the first nae form post ", name);
		
		
		if (result.hasErrors()) {
			return "test/SpringValidation";
		} else {
			return "Done";
		} 
	}
 
	@RequestMapping(value = "/SpringValidation", method = RequestMethod.GET)
	public String displayCustomerForm(ModelMap model) { 
		
		Person person = new Person();
		PersonInfo personInfo =new PersonInfo();
		personInfo.setFIRST_NAME("Siva");
		
		 person.setPersonInfo(personInfo);
		 person.setLAST_NAME("Kumar");
		
		model.addAttribute("person", person);
		model.addAttribute("personInfo", personInfo);
		
		return "test/SpringValidation"; 
	}
	
}
