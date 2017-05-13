package com.anilallewar.microservices.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


/**
 * The Main Spring Boot Application class.<br>
 * <br>
 * 
 * The {@link EnableConfigServer} annotation defines that this application will
 * serve as the REST based API for providing external configuration. <br>
 * <br>
 * 
 * The external repository from where the configuration will be picked up is
 * defined in the {@linkplain application.yml} file.
 * 
 * @author anilallewar
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}
}
