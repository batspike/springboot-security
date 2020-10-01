package com.samcancode.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.csrf.CsrfFilter;

import com.samcancode.security.filters.CsrfTokenLoggerFilter;
import com.samcancode.security.repository.CustomCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//this config is needed to access /h2-console properly
		http.headers().frameOptions().sameOrigin()
			.and()
			.csrf().ignoringAntMatchers("/h2-console/**"); //disable csrf for h2-console
		
		http.formLogin().and().httpBasic()
			.and()
			.authorizeRequests().mvcMatchers("/","/h2-console/**","/change").permitAll()
			.and()
			.authorizeRequests().mvcMatchers("/user").hasAnyRole("USER","ADMIN")
			.and()
			.authorizeRequests().mvcMatchers("/admin").hasAnyRole("ADMIN")
			.and()
			.authorizeRequests().mvcMatchers("/customer").hasAnyRole("CUSTOMER","ADMIN")
			.and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.csrf().csrfTokenRepository(new CustomCsrfTokenRepository()); //using our custom csrf token supplier
		
		http.addFilterAfter( new CsrfTokenLoggerFilter(), CsrfFilter.class); //added new csrf filter
	}

	@Bean
	public UserDetailsManager manager() {
		return new JpaUserDetailsManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
