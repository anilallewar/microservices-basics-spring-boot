package com.anilallewar.microservices.auth.config;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.anilallewar.microservices.auth.service.JdbcUserDetailsService;

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
	
	public static final String OAUTH2_CLIENT_SCOPE = "openid";
	public static final String OAUTH2_CLIENT_ID = "acme";
	public static final String OAUTH2_CLIENT_PASSWORD = "acmesecret";
	
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
		clients.jdbc(this.dataSource).withClient(OAUTH2_CLIENT_ID).secret(OAUTH2_CLIENT_PASSWORD).authorizedGrantTypes("authorization_code",
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

	
}
