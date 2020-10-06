package com.samcancode.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.samcancode.security.JpaUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JpaUserDetailsService jpaUserDetailsService;
	
	// The following are Grant Types:
	// 	password - this is being deprecated
	//  authorization code - user work directly with auth server which will provide token directly to client app
	//  client credentials
	//	refresh token
	//	implicit - deprecated
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("client1") //client refers to the client app 
				.secret("secret1")
				.scopes("read")
				.authorizedGrantTypes("password", "refresh_token")
			.and()
				.withClient("client2") //client refers to the client app 
				.secret("secret2")
				.scopes("read")
				.authorizedGrantTypes("authorization_code", "refresh_token")
				.redirectUris("http://localhost:8080/")
			.and()
				.withClient("client3") //client refers to the client app 
				.secret("secret3")
				.scopes("read")
				.authorizedGrantTypes("client_credentials")
			;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.userDetailsService(jpaUserDetailsService); //needed for refreshing a token
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(NoOpPasswordEncoder.getInstance()) //specify to use different encoder
				.checkTokenAccess("isAuthenticated()") //to allow client app to verify access token
				;
	}

	
}
