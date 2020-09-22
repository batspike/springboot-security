package com.samcancode.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

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
	public UserDetailsManager manager() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		
		UserDetails user;
		user = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();//use .roles for auto preappend ROLE_
		manager.createUser(user);
		user = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
		manager.createUser(user);
		user = User.withDefaultPasswordEncoder().username("cust").password("cust").roles("CUSTOMER").build();
		manager.createUser(user);
		
		return manager;
	}
	
	

}
