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

import com.example.models.Ranking;
import com.example.services.RankingService;

@Controller
public class RankingController {
	@Autowired
	private RankingService service;
	
	public RankingController() {
		
	}
	@RequestMapping("/ranking_list")
	public String viewRankingList(Model model)
	{
		List<Ranking> lb=service.findAll();
		model.addAttribute("lb", lb);
		return "ranking_list";
	}
	
	@RequestMapping("/ranking_add")
	public String addRanking(Model model) {
		Ranking r = new Ranking();
		model.addAttribute("rank", r);;
		return "ranking_add";
	}
	
	
	@PostMapping(value="/saveRanking")
	public String saveRanking(@ModelAttribute("rank") Ranking rank) {
		if(rank.getNick().length()<3 || rank.getNick().length()>50) {
			return "redirect:/error";
		}
		else {
			service.save(rank);
			return "redirect:/ranking_list";
		}
	}
	
	@RequestMapping("/ranking_edit/{id}")
	public ModelAndView showEditRanking(@PathVariable(name = "id") Long id) {
	 ModelAndView mav = new ModelAndView("ranking_edit");
	 Optional<Ranking> r = service.findById(id);
	 mav.addObject("rank", r);
	 return mav;
	}
	
	@RequestMapping("/ranking_delete/{id}")
	public String deleteRanking(@PathVariable(name = "id") Long id) {
	 service.deleteById(id);
	 return "redirect:/ranking_list";
	}
}
