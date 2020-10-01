package com.samcancode.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.samcancode.security.authentication.TokenAuthentication;
import com.samcancode.security.managers.TokenManager;

@Component
public class TokenAuthProvider implements AuthenticationProvider {
	@Autowired
	private TokenManager tokenManager;
	
	@Override
	public Authentication authenticate(Authentication authentication)  {
		String token = authentication.getName();
		
		if(tokenManager.contains(token)) {
			return new TokenAuthentication(token,null,null);
		}
		
		throw new BadCredentialsException("Bad Token Credentials!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.equals(authentication);
	}

}
