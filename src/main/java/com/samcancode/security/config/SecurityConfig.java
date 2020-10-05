package com.samcancode.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
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
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	
}
