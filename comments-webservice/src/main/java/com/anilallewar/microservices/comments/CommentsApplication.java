package com.anilallewar.microservices.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * The boot application class that defines the spring boot application to have
 * the following properties<br>
 * <br>
 * 
 * <ol>
 * <li>Act as a Eureka client; this behavior is provided by the
 * {@link EnableEurekaClient} annotation. The Eureka server URL is provided by
 * the external configuration provided by the config server.</li>
 * <li>{@link EnableEurekaClient} makes the app into both a Eureka "instance" (i.e. it
 * registers itself) and a "client" (i.e. it can query the registry to locate
 * other services).</li>
 * <li>Note that all these annotations work in conjunction with properties
 * defined in the external configuration files specified by the config server.
 * </li>
 * </ol>
 * 
 * @author anilallewar
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class CommentsApplication {
	public static void main(String[] args) {

		SpringApplication.run(CommentsApplication.class,args);
		
	}
}
