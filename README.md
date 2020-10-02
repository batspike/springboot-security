# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied for CORS
1. Created main.html to demonstrate how CORS prevent source from other origins
2. Added a Get and Post methods in Controller to service main.html
3. Updated SecurityConfig to demo how CORS is configured
