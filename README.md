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
* Whenever a request comes to the API gateway, the AuthFilter intercepts and checks the authorization header for API key. 
* The API key is then checked in the Redis database if it exists 
* The API Key is then checked if it has access to the requested service.
* If the account associated with the API key is not activated, an Unauthorized Exception is thrown with a message: ``` User with API key {} has not been activated.```
* If the API key provided is not present in the database, an Unauthorized Exception is also thrown with a message: ```Please provide a valid API key.``` 
* If the API key is found but does not have access to the requested service, an Unauthorized Exception is thrown with a message: ```You are unauthorized to use this service.```

## Technical Details 
The authentication model for this project consists of the following fields:
``` 
private String key;
private String UserId;
private boolean isAccountActivated;
private Set<String> services;
private Date createdDate; 
```
```
key: API key
UserId: User Id associated with the API key
isAccountActivated: flag indicating if the user account is activated
services: set of services that the API key has access to
createdDate: date when the API key was created
```

## Dependencies
* Spring Web 
* Spring cloud gateway
* Spring Redis ORM
* Lombok


## Contributing
If you are interested in contributing to this project, feel free to submit a pull request. Your contributions are always welcome. 
## License
This project is licensed under the MIT License.

## Tags
#API-key-based-auth #Spring-Boot #Redis #ORM
