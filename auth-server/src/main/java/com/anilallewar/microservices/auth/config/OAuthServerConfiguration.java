package com.anilallewar.microservices.auth.config;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.anilallewar.microservices.auth.service.JdbcUserDetailsService;
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
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

/**
 * The Class defines the authorization server that would authenticate the user
 * and define the client that seeks authorization on the resource owner's
 * behalf.
 * 
 * @author anilallewar
 */

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	private static final String AUTHORIZATION_HEADER = "AUTHORIZATION";
	private static final String OAUTH2_CLIENT_SCOPE = "openid";
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcUserDetailsService jdbcUserDetailsService;

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//Keypair is the alias name -> anilkeystore.jks / password / anila
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("anilkeystore.jks"), "password".toCharArray())
				.getKeyPair("anila");
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(this.dataSource).withClient("acme").secret("acmesecret").authorizedGrantTypes("authorization_code",
				"client_credentials", "password", "implicit", "refresh_token").scopes(OAUTH2_CLIENT_SCOPE);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter())
				.userDetailsService(this.jdbcUserDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	/**
	 * Configure the {@link AuthenticationManagerBuilder} with initial
	 * configuration to setup users.
	 * 
	 * Higher priority since lesser ordered value indicate higher priority.
	 * {@link Ordered#LOWEST_PRECEDENCE} has value as {@link Integer#MAX_VALUE}
	 * 
	 * @author anilallewar
	 *
	 */
	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 30)
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

		@Autowired
		private JdbcUserDetailsService jdbcUserDetailsService;

		/**
		 * Setup 2 users with different roles
		 */
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth.jdbcAuthentication().dataSource(dataSource).withUser("dave").password("secret").roles("USER").and()
					.withUser("anil").password("password").roles("ADMIN", "USER").and().getUserDetailsService();
			// @formatter:on

			// Add the default service
			jdbcUserDetailsService.addService(auth.getDefaultUserDetailsService());
		}
	}

	/**
	 * Swagger2 support with OAuth2 security for the API's
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.anilallewar.microservices.auth")).paths(PathSelectors.any())
				.build().enable(true).securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey(), securitySchema()));
	}

	private ApiKey apiKey() {
		return new ApiKey(AUTHORIZATION_HEADER, "api_key", "header");
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, // realm Needed for authenticate button to work
				null, // appName Needed for authenticate button to work
				"BEARER ", // apiKeyValue
				ApiKeyVehicle.HEADER, AUTHORIZATION_HEADER, // apiKeyName
				null);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/anyPath.*"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(OAUTH2_CLIENT_SCOPE,
				"Access for open id  default");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
	}

	private OAuth securitySchema() {
		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(new AuthorizationScope(OAUTH2_CLIENT_SCOPE, "Access for open id  default"));

		List<GrantType> grantTypes = new ArrayList<>();
		ResourceOwnerPasswordCredentialsGrant passwordOwnerGrant = new ResourceOwnerPasswordCredentialsGrant(
				"/userauth/oauth/token");

		grantTypes.add(passwordOwnerGrant);

		OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);

		return oAuth;
	}
}
