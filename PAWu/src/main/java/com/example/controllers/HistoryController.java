package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.models.History;
import com.example.services.HistoryServices;


@Controller
public class HistoryController {

	@Autowired
	private HistoryServices service;
	
	public HistoryController() {
		super();
	}
	@RequestMapping("/history_list")
	public String viewHistoryList(Model model)
	{
		List<History> lb=service.findAll();
		model.addAttribute("lb", lb);
		return "history_list";
	}
	
	@RequestMapping("/history_add")
	public String addHistory(Model model) {
		History r = new History();
		model.addAttribute("his", r);
		return "history_add";
	}
	
	
	@PostMapping(value="/saveHistory")
	public String saveHistory(@ModelAttribute("his") History his) {
		if(his.getWinner() == null || his.getLosser()==null) {
			return "redirect:/error";
		}
		else {
			service.save(his);
			return "redirect:/history_list";
		}
	}
	
	@RequestMapping("/history_edit/{id}")
	public ModelAndView showEditHistory(@PathVariable(name = "id") Long id) {
	 ModelAndView mav = new ModelAndView("history_edit");
	 Optional<History> r = service.findById(id);
	 mav.addObject("his", r);
	 return mav;
	}
	
	@RequestMapping("/history_delete/{id}")
	public String deleteHistory(@PathVariable(name = "id") Long id) {
	 service.deleteById(id);
	 return "redirect:/history_list";
	}
}
