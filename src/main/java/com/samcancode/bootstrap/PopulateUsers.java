package com.samcancode.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.samcancode.domain.User;
import com.samcancode.repository.UserRepository;

@Component
public class PopulateUsers implements CommandLineRunner {
	
	private final UserRepository userRepo;

	public PopulateUsers(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("user");
		user.setPassword("user");
		userRepo.save(user);
		
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");
		userRepo.save(admin);
		
		User cust = new User();
		cust.setUsername("cust");
		cust.setPassword("cust");
		userRepo.save(cust);
		
	}
	
	

}
