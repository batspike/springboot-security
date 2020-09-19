package com.samcancode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	@GetMapping("/user")
	public String userPage(Model model) {
		model.addAttribute("message", "User Page");
		return "result"; 
	}
	
	@GetMapping("/admin")
	public String adminPage(Model model) {
		model.addAttribute("message", "Admin Page");
		return "result"; 
	}
	
	@GetMapping("/customer")
	public String custPage(Model model) {
		model.addAttribute("message", "Customer Page");
		return "result"; 
	}
	
}
