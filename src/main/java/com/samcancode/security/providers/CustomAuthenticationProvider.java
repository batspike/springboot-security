package com.samcancode.security.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.samcancode.security.authentication.CustomAuthenticationToken;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Value("${key}")
	private String key;
	
	@Override
	public Authentication authenticate(Authentication authentication)  {
		String requestKey = authentication.getName();
		if(requestKey.equals(key)) {
			CustomAuthenticationToken a = new CustomAuthenticationToken(null,null,null);
			return a;
		}
		else {
			throw new BadCredentialsException("Bad Credentials!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthenticationToken.class.equals(authentication);
	}

}
