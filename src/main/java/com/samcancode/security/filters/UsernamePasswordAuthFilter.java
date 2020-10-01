package com.samcancode.security.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.samcancode.security.authentication.OtpAuthToken;
import com.samcancode.security.authentication.UsernamePasswordAuthToken;
import com.samcancode.security.managers.TokenManager;
import com.samcancode.security.services.JpaOtpService;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JpaOtpService otpService;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String username = request.getHeader("username");
		String rawPassword = request.getHeader("password");
		String otp = request.getHeader("otp");
		
		try {
			if(otp == null) {
				// authenticate with username & password
				Authentication a = new UsernamePasswordAuthToken(username, rawPassword);
				a = authManager.authenticate(a);
				// need to generate and save a random otp in user database then 
				// redirect to do username/otp authentication
				otpService.generateOtp(username);
			}
			else {
				// authenticate with username & otp
				Authentication a = new OtpAuthToken(username, otp);
				a = authManager.authenticate(a);
				
				// generate a token for subsequent authentication to other endpoints
				String uuid = UUID.randomUUID().toString();
				tokenManager.add(uuid);
				response.setHeader("Authorization", uuid);
			}
		}
		catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		//this filter is only used for /login
		return !request.getServletPath().equals("/login");
	}
	
	

}
