package com.samcancode.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.samcancode.security.authentication.OtpAuthToken;
import com.samcancode.security.services.JpaOtpService;
import com.samcancode.security.services.JpaUserDetailsService;

@Component
public class OtpAuthProvider implements AuthenticationProvider {
	@Autowired
	private JpaOtpService otpService;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication)  {
		String username = authentication.getName();
		String rawOTP = (String) authentication.getCredentials();
		
		if(otpService.lookupOtp(username).equals(rawOTP)) {
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new OtpAuthToken(user.getUsername(), rawOTP, user.getAuthorities());
		}
		
		throw new BadCredentialsException("Bad OTP Credentials!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OtpAuthToken.class.equals(authentication);
	}

}
