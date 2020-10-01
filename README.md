# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied - CSRF
1. Added 2 endpoints in MainController: GET:/change and POST:/change
2. GET:/change will return the submit form page which will submit to POST:/change
3. POST:/change will display change message and return to index home page
4. Created change.html page for posting form to POST:/change with CSRF token
5. Created fake_change.html page for testing access with CSRF enabled or disabled
6. Simulate fake access by opening the fake_change.html separately
7. When CSRF is enabled in SecurityConfig, accessing via fake_change.html will be prompted for login
8. When CSRF is disabled, accessing via fake_change.html will go through

In addition, for demonstration, a CustomCsrfTokenRepository class is created to show how to custom generate csrf token.
We also demonstrate a filter to print the csrf on the console.
