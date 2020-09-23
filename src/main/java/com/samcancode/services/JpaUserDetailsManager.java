package com.samcancode.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.samcancode.repository.SecurityUserRepository;
import com.samcancode.security.SecurityUser;

@Service
public class JpaUserDetailsManager implements UserDetailsManager {
	
	private SecurityUserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	public JpaUserDetailsManager(SecurityUserRepository userRepo, PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = encoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SecurityUser> user = userRepo.findUserByUsername(username);
		SecurityUser u = user.orElseThrow(() -> new UsernameNotFoundException(username));
		return u;
	}

	@Override
	public void createUser(UserDetails user) {
		SecurityUser u = (SecurityUser) user;
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save((SecurityUser)user);
	}

	@Override
	public void updateUser(UserDetails user) {
		this.createUser(user);
	}

	@Override
	public void deleteUser(String username) {
		Optional<SecurityUser> user = userRepo.findUserByUsername(username);
		SecurityUser u = user.orElseThrow(() -> new UsernameNotFoundException(username));
		userRepo.delete(u);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		//TODO
	}

	@Override
	public boolean userExists(String username) {
		Optional<SecurityUser> user = userRepo.findUserByUsername(username);
		return user.isPresent();
	}

}
