package com.samcancode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.samcancode.security.filters.UsernamePasswordAuthFilter;
import com.samcancode.security.providers.OtpAuthProvider;
import com.samcancode.security.providers.UsernamePasswordAuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsernamePasswordAuthFilter authFilter;
	
	@Autowired
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;
	
	@Autowired
	private OtpAuthProvider otpAuthProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthProvider)
			.authenticationProvider(otpAuthProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterAt(authFilter, BasicAuthenticationFilter.class); //replace the BasicAuthenticationFilter with our UsernamePasswordAuthFilter
		
		http.csrf().disable() //this config is needed to access /h2-console properly
			.headers().frameOptions().sameOrigin()
			.and()
			.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		
		// commented out the following to disable form login so can test our custom filter, UsernamePasswordAuthFilter
//		http.formLogin()//.and().httpBasic()
//			.and()
//			.authorizeRequests().mvcMatchers("/").permitAll()
//			.and()
//			.authorizeRequests().mvcMatchers("/user").hasAnyRole("USER","ADMIN")
//			.and()
//			.authorizeRequests().mvcMatchers("/admin").hasAnyRole("ADMIN")
//			.and()
//			.authorizeRequests().mvcMatchers("/customer").hasAnyRole("CUSTOMER","ADMIN")
//			.and()
//			.authorizeRequests().anyRequest().authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean //make the AuthenticationManager available in the App Context
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
}
