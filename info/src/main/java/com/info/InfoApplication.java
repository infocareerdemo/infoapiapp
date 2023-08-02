package com.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoApplication.class, args);
	}

}




/*
http://localhost:8080/swagger-ui/index.html
Swagger provides a way to describe the APIs in a standard format known as OpenAPI Specification (formerly Swagger Specification). 
This specification provides a machine-readable description of the APIs, including details such as the endpoints, request and response formats, authentication mechanisms, and more. 
This helps in automated API documentation generation, and also helps in testing, debugging and validating the API.


http://localhost:8080/actuator
Spring Boot Actuator provides secured endpoints for monitoring and managing your Spring Boot application.
Monitor and manage your application in your production


slf4j - https://www.slf4j.org/

OptionalLiveReloadServer:59 - [] LiveReload server is running on port 35729 

Profiles are a core feature of the framework — allowing us to map our beans to different profiles — for example, dev, test, and prod.


https://nginx.org/
Nginx.org is the official website for the open-source Nginx software and community resources
https://nginx.org/en/docs/


https://www.nginx.com/
Nginx.com is the website for Nginx, Inc., providing commercial products and services built on top of the open-source Nginx web server.


Certbot is usually meant to be used to switch an existing HTTP site to work in HTTPS
https://certbot.eff.org/ 
*/