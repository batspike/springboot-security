package com.samcancode.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.samcancode.domain.User;
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
		User user = new User();

		user.setUsername("user");
		user.setPassword("user");
		UserDetails u = new SecurityUser(user);
		userManager.createUser(u);
		
		user.setUsername("admin");
		user.setPassword("admin");
		UserDetails a = new SecurityUser(user);
		userManager.createUser(a);
		
		user.setUsername("cust");
		user.setPassword("cust");
		UserDetails c = new SecurityUser(user);
		userManager.createUser(c);
		
	}
	
	

}
