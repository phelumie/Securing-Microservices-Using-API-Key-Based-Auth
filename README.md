# Securing-Microservices-Using-API-Key-Based-Auth
A simple implementation of API Key based authentication for microservices using Spring Boot and Redis stack.


## Features
* API Key based authentication using Redis hash
* Redis bloom filter to check the uniqueness of API keys.
* API Gateway filter (AuthFilter) to intercept and check the authorization header for API key.
* Throws Unauthorized Exception in case: 
  * Provided API key is not present in database. 
  * API key does not have access to the requested service.
  * API Key is valid but user account is not activated. 

## How it Works 








The authentication model for this project consists of the following fields:
