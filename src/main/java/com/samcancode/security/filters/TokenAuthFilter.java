package com.samcancode.security.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.samcancode.security.authentication.TokenAuthentication;

// this is not set as a component to prevent cyclic instantiation in context at boot up in SecurityConfig
public class TokenAuthFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager authManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Object token = request.getHeader("Authorization");
		Authentication authentication = new TokenAuthentication(token, null);
		Authentication a = authManager.authenticate(authentication);
		// at this point any error would have been thrown by AuthenticationManager
		
		SecurityContextHolder.getContext().setAuthentication(a);
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		//this filter is only used for other endpoints, i.e. not equal to '/login' or '/h2-console'
		//in other words, don't apply this filter to the following endpoint requests
		return request.getServletPath().equals("/login") 
			|| request.getServletPath().equals("/h2-console");
	}
	
	

}
