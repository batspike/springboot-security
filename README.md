# springboot-security

## Project to demo Spring Boot Security
This project consists of a bare bone Spring Boot MVC Web application with some web endpoints to help demonstrate Spring Security. The primary focus of the project is to explore how Spring Security works and ways to configure it. The following endpoints have been created for testing:
- http://localhost:8080/user
- http://localhost:8080/admin
- http://localhost:8080/customer

Login with user:user , admin:admin , or cust:cust

#### Changes Applied - OAuth2-UsingOpaqueTokens-Lesson14
##### OAuth2 with separate Authentication Server and Resource Server

Authentication Server functionalities:
1. Generate access token based on userid/password
2. Return a access token which provide access to resources from resource server based on authorization provided by authentication server
3. Allowed resource server to verify access token validity

Resource Server functionalities:
1. Provide end points to resources for client consumption
2. Verify provided access token with Authentication Server
<p><img src="src/main/resources/password.jpg" width="80%"/>







