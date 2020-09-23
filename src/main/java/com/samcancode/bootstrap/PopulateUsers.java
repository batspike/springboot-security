package com.samcancode.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.samcancode.security.SecurityUser;
import com.samcancode.services.JpaUserDetailsManager;

@Component
public class PopulateUsers implements CommandLineRunner {
	
	private final JpaUserDetailsManager userManager;

	public PopulateUsers(JpaUserDetailsManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public void run(String... args) throws Exception {
		
		SecurityUser user = new SecurityUser();
		user.setUsername("user");
		user.setPassword("user");
		userManager.createUser(user);
		
		SecurityUser admin = new SecurityUser();
		admin.setUsername("admin");
		admin.setPassword("admin");
		userManager.createUser(admin);
		
		SecurityUser cust = new SecurityUser();
		cust.setUsername("cust");
		cust.setPassword("cust");
		userManager.createUser(cust);
		
	}
	
	

}
