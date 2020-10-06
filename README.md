# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied - based on SecurityWithJPA-CustomUserService branch
##### Intro to OAuth2.
1. Add Cloud OAuth2 dependancy from Spring Cloud Security
2. Create a config file, AuthServerConfig.java, to setup Authentication Server
- override configure(ClientDetailsServiceConfigurer clients)
- override configure(AuthorizationServerEndpointsConfigurer endpoints)

In the SecurityConfig.java,
- override the authenticationManagerBean() to link with the authentication server above

At this point, we are setup for the minimal authentication server setup. 
In the startup log, we should see the following in the filter chain list:
- OrRequestMatcher [requestMatchers=[Ant [pattern='/oauth/token'], Ant [pattern='/oauth/token_key'], Ant [pattern='/oauth/check_token']]]

The following diagram illustrate the flow for password grant-type:
<p><img src="src/main/resources/password.jpg" width="80%"/>

For Grant-Type = password, With Postman,
1. Send a POST method to http://localhost:8080/oauth/token?grant_type=password&username=user&password=user&scope=read with Basic Authorization Username=client1, and Password=secret1 as configured.
2. The auth server should return a Json message with the access_token value.

The following diagram illustrate the flow for authorization-code grant-type:
<p><img src="src/main/resources/auth_code.jpg" width="80%"/>

For Grant-Type=authorization_code, with Browser,
1. Send a GET request to http://localhost:8080/oauth/authorize?response_type=code&client_id=client2&scope=read
2. It will be redirected to OAuth Approval page. Select Approve and click Authorize.
3. System should return to the login page, and the address bar should provide a authorization code. Something look similar to "http://localhost:8080/?code=87lrZ7". The code is for one time used.
4. Now, in Postman we can use the code as part of the POST request, http://localhost:8080/oauth/token?grant_type=authorization_code&scope=read&code=87lrZ7 , the Basic Auth Username/Password should be set to client2/secret2 as configured.
5. The system should return a Json message with the access_token value.

An opaque token is just a token with no addition meta information, as per above generated tokens.
In contrast there is JWT which is a token with meta information. Using JWT there is no need to verify with Auth Server if the token is valid since this info is embedded within the token.

So how does the resource server knows if a access token is a valid one from the authorization server?
The resource server can call the authentication server to verify the token via path /oauth/check_token, e.g.
http://localhost:8080/oauth/check_token?token=0d27e1b4-f044-4249-9875-47ec7d067b68

Another way is to have a common token storage location, though this is not recommended. See figure below:
<p><img src="src/main/resources/token_store.jpg" width="80%"/>

Yet another setup is to have both the Authorization and Resource Servers to be same application. This is also not recommended. We do this by creating the ResourceServerConfig.java configuration. To get to any resource will be a two step process. 1. Get a token as per before; 2. Call the resource specifying header attribute Authorization=Bearer 8c9658c0-b26b-4486-8e18-e57f3efda979.

<p>
How to use a refresh token:
1. Send a POST method to http://localhost:8080/oauth/token?grant_type=password&username=user&password=user&scope=read
Note we also need to do Basic Auth with Username/Password, client1/secret1 as per usual.
2. The auth server should return a accss_token and refresh_token in the Json message. Copy the refresh token for use later.
3. The purpose of the refresh token is to allow us to request a new access token from auth server without having to go thru the whole request cycle.
4. To get a new access token, perform another POST http://localhost:8080/oauth/token?grant_type=refresh_token&scope=read&refresh_token=c276a162-f3c9-43ff-87e9-4f83cfd038cc
Note the use of refresh token in the parameter (copied in previous step), the grant type is also specified as refresh token.
5. The auth server should return with a new access token in the Json message.





