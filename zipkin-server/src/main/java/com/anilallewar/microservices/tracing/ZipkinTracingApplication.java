package com.anilallewar.microservices.tracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

/**
 * The boot application class that provides the zipkin server UI.
 * 
 * @author anilallewar
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinTracingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZipkinTracingApplication.class,args);		
	}
}
