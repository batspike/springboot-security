package com.samcancode.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() //this config is needed to access /h2-console properly
			.headers().frameOptions().sameOrigin()
			.and()
			.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		
		http.authorizeRequests().anyRequest().permitAll();
		
		// the following setup CORS allowing all origins for Get and Post methods
		http.cors(c -> {
			CorsConfigurationSource cs = r -> {
				CorsConfiguration cc = new CorsConfiguration();
				cc.setAllowedOrigins(List.of("*"));
				cc.setAllowedMethods(List.of("GET","POST"));
				return cc;
			};
			c.configurationSource(cs);
		});
		
//		http.formLogin().and().httpBasic()
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
	public UserDetailsManager manager() {
		return new JpaUserDetailsManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
