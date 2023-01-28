package com.example.controllers;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.services.RegistrationServices;

import com.example.models.Registration;


@Controller
public class RegistrationController {
	@Autowired
	private RegistrationServices service;

	public RegistrationController() {
		
	}
	@RequestMapping("/registration_list")
	public String viewRegistrationList(Model model)
	{
		List<Registration> lb=service.findAll();
		model.addAttribute("lb", lb);
		return "registration_list";
	}
	
	@RequestMapping("/registration_add")
	public String addRegistration(Model model) {
		Registration r = new Registration();
		model.addAttribute("regis", r);
		return "registration_add";
	}
	

	@PostMapping(value="/saveRegistration")
	public String saveRegistration(@ModelAttribute("regis") Registration regis) {
		if(regis.getNick().length()<3 || 
		   regis.getNick().length()>50||
		   !Pattern.matches("^[s]{1}[0-9]{5,6}$", regis.getIndex())) {
			return "redirect:/error";
		}
		else {
			service.save(regis);
			return "redirect:/";
		}
	}
	
	@RequestMapping("/registration_edit/{id}")
	public ModelAndView showEditRegistration(@PathVariable(name = "id") Long id) {
	 ModelAndView mav = new ModelAndView("registration_edit");
	 Optional<Registration> r = service.findById(id);
	 mav.addObject("regis", r);
	 return mav;
	}
	
	@RequestMapping("/registration_delete/{id}")
	public String deleteRegistration(@PathVariable(name = "id") Long id) {
	 service.deleteById(id);
	 return "redirect:/registration_list";
	}
}
