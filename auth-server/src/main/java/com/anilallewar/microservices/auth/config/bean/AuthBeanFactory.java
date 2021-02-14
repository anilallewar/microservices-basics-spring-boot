package com.anilallewar.microservices.auth.config.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anilallewar.microservices.auth.config.OAuthServerConfiguration;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class AuthBeanFactory {

	private static final String AUTHORIZATION_HEADER = "AUTHORIZATION";

	/**
	 * Swagger2 support with OAuth2 security for the API's
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {

		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.anilallewar.microservices.auth"))
				.paths(PathSelectors.any())
				.build()
				.enable(true)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey(), securitySchema()));
		// @formatter:on
	}

	private ApiKey apiKey() {
		return new ApiKey(AUTHORIZATION_HEADER, "api_key", "header");
	}

	@Bean
	SecurityConfiguration security() {

		return SecurityConfigurationBuilder.builder().clientId(OAuthServerConfiguration.OAUTH2_CLIENT_ID)
				.clientSecret(OAuthServerConfiguration.OAUTH2_CLIENT_PASSWORD).realm("test-app-realm")
				.appName("test-app").scopeSeparator(",").additionalQueryStringParams(null)
				.useBasicAuthenticationWithAccessCodeGrant(false).enableCsrfSupport(false).build();

//		return new SecurityConfiguration(null, null, null, // realm Needed for authenticate button to work
//				null, // appName Needed for authenticate button to work
//				"BEARER ", // apiKeyValue
//				ApiKeyVehicle.HEADER, AUTHORIZATION_HEADER, // apiKeyName
//				null);

	}

	private SecurityContext securityContext() {
		// @formatter:off
		return SecurityContext.builder()
					.securityReferences(defaultAuth())
					.forPaths(PathSelectors.regex("/anyPath.*"))
				.build();
		// @formatter:on
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(OAuthServerConfiguration.OAUTH2_CLIENT_SCOPE,
				"Access for open id  default");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
	}

	private OAuth securitySchema() {
		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(
				new AuthorizationScope(OAuthServerConfiguration.OAUTH2_CLIENT_SCOPE, "Access for open id  default"));

		List<GrantType> grantTypes = new ArrayList<>();
		ResourceOwnerPasswordCredentialsGrant passwordOwnerGrant = new ResourceOwnerPasswordCredentialsGrant(
				"/userauth/oauth/token");

		grantTypes.add(passwordOwnerGrant);

		OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);

		return oAuth;
	}
}
