package com.samcancode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.samcancode.security.filters.CustomAuthenticationFilter;
import com.samcancode.security.providers.CustomAuthenticationProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterAt(filter, BasicAuthenticationFilter.class); //replace the BasicAuthenticationFilter with our CustomAuthenticationFilter
		
		http.csrf().disable() //this config is needed to access /h2-console properly
			.headers().frameOptions().sameOrigin()
			.and()
			.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		
		http.formLogin().and().httpBasic()
			.and()
			.authorizeRequests().mvcMatchers("/").permitAll()
			.and()
			.authorizeRequests().mvcMatchers("/user").hasAnyRole("USER","ADMIN")
			.and()
			.authorizeRequests().mvcMatchers("/admin").hasAnyRole("ADMIN")
			.and()
			.authorizeRequests().mvcMatchers("/customer").hasAnyRole("CUSTOMER","ADMIN")
			.and()
			.authorizeRequests().anyRequest().authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	private CustomAuthenticationFilter filter;
	
	@Autowired
	private CustomAuthenticationProvider provider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
	
	
	
}
