package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.models.Matches;
import com.example.services.MatchesServices;

@Controller
public class MatchesController {

	@Autowired
	private MatchesServices service;
	
	public MatchesController() {
		
	}
	@GetMapping("/matches_list")
	public String viewMatchesList(Model model)
	{
		List<Matches> lb=service.findAll();
		model.addAttribute("lb", lb);
		return "matches_list";
	}
	
	@GetMapping("/matches_add")
	public String addMatches(Model model) {
		Matches r = new Matches();
		model.addAttribute("match", r);
		return "matches_add";
	}
	
	
	@PostMapping(value="/saveMatches")
	public String saveMatches(@ModelAttribute("match") Matches match) {
		if(match.getFirstNick() == null || match.getSecoundNick()==null) {
			return "redirect:/error";
		}
		else {
			service.save(match);
			return "redirect:/matches_list";
		}
	}
	
	@GetMapping("/matches_edit/{id}")
	public ModelAndView showEditMatches(@PathVariable(name = "id") Long id) {
	 ModelAndView mav = new ModelAndView("matches_edit");
	 Optional<Matches> r = service.findById(id);
	 mav.addObject("match", r);
	 return mav;
	}
	
	@GetMapping("/matches_delete/{id}")
	public String deleteMatches(@PathVariable(name = "id") Long id) {
	 service.deleteById(id);
	 return "redirect:/matches_list";
	}
}
