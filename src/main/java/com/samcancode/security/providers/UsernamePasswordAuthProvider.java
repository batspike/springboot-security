package com.samcancode.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.samcancode.security.authentication.UsernamePasswordAuthToken;
import com.samcancode.security.services.JpaUserDetailsService;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication)  {
		String username = authentication.getName();
		String rawPassword = (String) authentication.getCredentials();
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if(passwordEncoder.matches(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		
		throw new BadCredentialsException("Bad username/password Credentials!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthToken.class.equals(authentication);
	}

}
