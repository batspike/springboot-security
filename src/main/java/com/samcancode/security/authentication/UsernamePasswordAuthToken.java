package com.samcancode.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	public UsernamePasswordAuthToken() {super(null,null);}

	public UsernamePasswordAuthToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public UsernamePasswordAuthToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

}
