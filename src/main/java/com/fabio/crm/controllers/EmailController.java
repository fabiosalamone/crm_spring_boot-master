package com.fabio.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fabio.crm.services.EmailService;



@Controller
public class EmailController {

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/sendMail/{email}", method = RequestMethod.POST)
	public String sendEmail(@RequestParam("object") String object, 
							@RequestParam("text") String text,
							@RequestParam("customerId") String id,
							@PathVariable(value = "email") String email,
							RedirectAttributes atts)
	{
		
	    if (emailService.sendEmail(email, object, text))
			atts.addFlashAttribute("message", "Email inviata corretramente");
		else
			atts.addFlashAttribute("message", "Email non inviata");

		return "redirect:/customer/edit/"+id;
	}
}