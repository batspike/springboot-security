package com.samcancode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	@PostMapping("/change")
	public String changeSomething(Model model) {
		System.out.println("********* Something's changed!");
		
		model.addAttribute("message", "Something has changed!");
		return "index";
	}
	
	@GetMapping("/change")
	public String getToChangeSomething(Model model) {
		return "change";
	}
	
}
