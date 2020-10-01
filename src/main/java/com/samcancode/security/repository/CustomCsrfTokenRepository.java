package com.samcancode.security.repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

/*
 * A custom CSRF Token Repository to demonstrate generation of custom CSRF token.
 * Usage setup is done in the SecurityConfig class.
 */
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		CsrfToken t = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "123456");
		return t;
	}

	@Override
	public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
		//TODO
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		return null;
	}

}
