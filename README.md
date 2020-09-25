# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied
1. Use JPA with H2 Database
2. Created custom filter CustomAuthenticationFilter.java to be used as authentication filter.
3. Created custom token class CustomAuthenticationToken.java as the authentication token.
4. Created custom provider class CustomAuthenticationProvider.java to be assigned to authentication manager.
5. Setup all the above components in SecurityConfig.java.
6. Use Postman to send a header attribute "Authentication" with value to match the key in application.properties.


This is a bare basic setup to demonstrate the flow of authentication from filter to authentication manager to authentication provider. Upon successful authentication, the token is sent back to the filter which will set the Security Context.
