package com.samcancode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samcancode.security.domain.JpaSecurityUser;
import com.samcancode.security.model.SecurityUserDetails;
import com.samcancode.security.repository.JpaSecurityUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	@Autowired
	private JpaSecurityUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JpaSecurityUser jpaUser = userRepo.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException(username));
		return new SecurityUserDetails(jpaUser);
	}
	
}
