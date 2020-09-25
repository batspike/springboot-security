package com.samcancode.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.samcancode.security.authentication.CustomAuthenticationToken;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager manager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorization = request.getHeader("Authorization");
		
		CustomAuthenticationToken token = new CustomAuthenticationToken(authorization,null);
		
		try {
			Authentication result = manager.authenticate(token);
			
			if(result.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(result);
				filterChain.doFilter(request, response);
			}
			else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		catch (AuthenticationException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}

}
