package com.anilallewar.microservices.gateway.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
@Primary
public class SwaggerDocumentationConfiguration implements SwaggerResourcesProvider {

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(this.createSwaggerResource("auth-server", "/userauth/v2/api-docs", "2.0"));
		resources.add(this.createSwaggerResource("task-service", "/task-service/v2/api-docs", "2.0"));
		resources.add(this.createSwaggerResource("user-service", "/user-service/v2/api-docs", "2.0"));
		return resources;
	}

	/**
	 * Create swagger resource for all microservices
	 * 
	 * @param name
	 * @param location
	 * @param version
	 * @return
	 */
	private SwaggerResource createSwaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
}
