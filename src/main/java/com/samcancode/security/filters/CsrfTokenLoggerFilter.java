package com.samcancode.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

/*
 * This filter extract the CSRF token and print on console as a demonstration of filter creation in Spring.
 * The filter is inserted after the existing CSRF verification filter as configure in SecurityConfig class.
 */
public class CsrfTokenLoggerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		System.out.println("CSRF token from filter: "+ token.getToken());
		
		filterChain.doFilter(request, response);
	}

}
