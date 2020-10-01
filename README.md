# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied - MultipleAuthProviders branch
This demonstrate a introduction of usage of Multiple Authentication Providers, using username/password authentication follow by token authentication. This effectively shows the basic innerworking of dual authentication. After the username/password authentication is successful, a token is issued. User login with username/token for second authentication. Two authentication providers are created.
1. Use JPA with H2 Database
2. Created custom filter UsernamePasswordAuthFilter.java to be used as authentication filter (replacing default BasicAuthenticationFilter filter).
3. Created custom token classes UsernamePasswordAuthToken.java and OtpAuthToken.java as the authentication tokens.
4. Created custom provider classes UsernamePasswordAuthProvider.java and OtpAuthProvider.java to be assigned to authentication manager in the config setup.
5. Setup all the above components in SecurityConfig.java.
6. On first round of username/password successful authentication, a OTP token is created for the user.
7. On second round of username/otp-token successful authentication, a 'Authorization' header attribute is set in the response header.
8. Use Postman to send a header attributes "username/password" attributes in header to endpoint, http://localhost:8080/login.
9. Use Postman to send a header attributes "username/otp" attributes in header to endpoint, http://localhost:8080/login, for second OTP authentication.
10. Check in Postman response header should contain a attribute 'Authentication' containing generated UUID token from app.
11. With Postman, set in the request header attribute 'Authentication' with the UUID token received previously, and sent request to end-point, http://localhost:8080/user. App should return the user page successfully.

