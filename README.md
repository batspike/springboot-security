# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied
1. Use JPA with H2 Database
2. Created custom JpaAuthority to persist authority
3. Created custom JpaSecurityUser to store user data
4. Replace custom JpaUserDetailsManager with JpaUserDetailsService for minimal codes authentication/authorization
