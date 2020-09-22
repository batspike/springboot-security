package com.samcancode.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samcancode.domain.User;
import com.samcancode.repository.UserRepository;
import com.samcancode.security.SecurityUser;

//@Service
public class JpaUserDetailsService implements UserDetailsService {

	private UserRepository userRepo;
	public JpaUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByUsername(username);
		User u = user.orElseThrow(() -> new UsernameNotFoundException(username));
		return new SecurityUser(u);
	}



}
