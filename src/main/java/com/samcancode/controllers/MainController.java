package com.samcancode.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
	// we can access authentication token object simply by injecting it
	// in the method parameter as shown below.
	public String userPage(Model model, Authentication auth) {
		System.out.println("Authentication object via injection:");
		System.out.println(auth.getClass().getName() + "; token name: "+ auth.getName());
		
		// We can also access the authentication token object at any layer (e.g. service layer).
		// To do this we use the SecurityContextHolder global object.
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		System.out.println("\nAuthentication object via SecurityContext:");
		System.out.println(securityCtx.getAuthentication());
		
		model.addAttribute("message", "User Page");
		return "result"; 
	}
	
	@GetMapping("/admin")
	// The above endpoint uses single thread request; meaning the SecurityContext will be available
	// through out the request cycle. If we set the request to be handled asynchronously using @Async
	// as per the following end point, the method will be executed in a different thread. By default 
	// the SecurityContext object will not be available in the child's thread. This means in the 
	// following method, the securityCtx variable will be null resulting in NEP! By default, the
	// SecurityContextHolder strategy is set to MODE_THREADLOCAL. This means the SecurityContext
	// is only available in the local thread, not its sub threads.
	//
	// We can fix this by setting the strategy to MODE_INHERITABLETHREADLOCAL or MODE_GLOBAL.
	// We can set this by:
	// 1. Using InitializingBean in config and set the strategy via SecurityContextHolder.
	// 2. Set the strategy with a system property called spring.security.strategy when
	//    starting the app using -D spring.security.strategy=MODE_INHERITABLETHREADLOCAL.
	//    This is useful if running the app in docker container.
	//
	// Note to use @Async our SecurityConfig must also annotated with @EnableAsync.
	@Async
	public String adminPage(Model model, Authentication auth) {
		
		System.out.println("Authentication object via injection:");
		System.out.println(auth.getClass().getName() + "; token name: "+ auth.getName());
		
		// We can also access the authentication token object at any layer (e.g. service layer).
		// To do this we use the SecurityContextHolder object.
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		System.out.println("\nAuthentication object via SecurityContext:");
		System.out.println(securityCtx.getAuthentication());

		model.addAttribute("message", "Admin Page");
		return "result"; 
	}
	
	@GetMapping("/customer")
	public String custPage(Model model) {
		model.addAttribute("message", "Customer Page");
		return "result"; 
	}
	
}
