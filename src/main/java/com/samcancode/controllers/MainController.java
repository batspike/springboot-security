package com.samcancode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // @RestController = @Controller + @ResponseBody
public class MainController {
	@GetMapping("/")
	public String homePage() {
		return "main.html";
	}
	
	@PostMapping("/test")
	@ResponseBody //this tells webMvc the returned value is a text body instead of a view
//	@CrossOrigin("*") // all origins is allowed; it is better to configure this in a config file, see SecurityConfig.java
	public String test() {
		System.out.println("CORS does not prevent exectuion of this method!");
		System.out.println("but CORS prevent browser from accepting the response.");
		System.out.println("Unless we have a pre-flight request to decide if this method is to be invoked.");
		return "TEST!";
	}

	//===============================================================
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
