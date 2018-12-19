package com.anilallewar.microservices.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Main Spring Boot Application class that starts the authorization
 * server.</br>
 * </br>
 * 
 * Note that the server is also a Eureka client so as to register with the
 * Eureka server and be auto-discovered by other Eureka clients.
 *
 * @author anilallewar
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
//@SessionAttributes("authorizationRequest")
@EnableSwagger2
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
