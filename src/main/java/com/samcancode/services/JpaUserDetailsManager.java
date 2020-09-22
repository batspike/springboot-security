package com.samcancode.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.samcancode.domain.User;
import com.samcancode.repository.UserRepository;
import com.samcancode.security.SecurityUser;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {
	
	private UserRepository userRepo;
	public JpaUserDetailsManager(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByUsername(username);
		User u = user.orElseThrow(() -> new UsernameNotFoundException(username));
		return new SecurityUser(u);
	}

	@Override
	public void createUser(UserDetails user) {
		User u = new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		userRepo.save(u);
	}

	@Override
	public void updateUser(UserDetails user) {
		this.createUser(user);
	}

	@Override
	public void deleteUser(String username) {
		Optional<User> user = userRepo.findUserByUsername(username);
		User u = user.orElseThrow(() -> new UsernameNotFoundException(username));
		userRepo.delete(u);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		//TODO
	}

	@Override
	public boolean userExists(String username) {
		Optional<User> user = userRepo.findUserByUsername(username);
		return user.isPresent();
	}

}
