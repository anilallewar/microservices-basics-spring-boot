package com.anilallewar.microservice.auth.config;

import java.security.KeyPair;
import java.util.LinkedList;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * The Class OAuth2Config defines the authorization server that would
 * authenticate the user and define the client that seeks authorization on the
 * resource owner's behalf.
 * 
 * @author anilallewar
 */
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JDBCUserDetailsService jdbcUserDetailsService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray())
				.getKeyPair("test");
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	/**
	 * We set our authorization storage feature specifying that we would use the
	 * JWT tokens.<br>
	 * <br>
	 * 
	 * We also attach the {@link AuthenticationManager} so that password grants
	 * can be processed.
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(auth).accessTokenConverter(jwtAccessTokenConverter())
				.userDetailsService(jdbcUserDetailsService);
	}

	/**
	 * Setup the client application which attempts to get access to user's
	 * account after user permission.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.jdbc(dataSource).passwordEncoder(passwordEncoder).withClient("client")
				.authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password",
						"implicit")
				.authorities("ROLE_CLIENT").resourceIds("apis").scopes("read").secret("secret")
				.accessTokenValiditySeconds(300);

	}

	/**
	 * Configure the {@link AuthenticationManagerBuilder} with initial
	 * configuration to setup users.
	 * 
	 * @author anilallewar
	 *
	 */
	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 20)
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

		@Autowired
		private JDBCUserDetailsService jdbcUserDetailsService;

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

	@Configuration
	protected static class JDBCUserDetailsService implements UserDetailsService {

		private List<UserDetailsService> uds = new LinkedList<>();

		public JDBCUserDetailsService() {
		}

		public void addService(UserDetailsService srv) {
			uds.add(srv);
		}

		@Override
		public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
			if (uds != null) {
				for (UserDetailsService srv : uds) {
					try {
						final UserDetails details = srv.loadUserByUsername(login);
						if (details != null) {
							return details;
						}
					} catch (UsernameNotFoundException ex) {
						assert ex != null;
					} catch (Exception ex) {
						ex.printStackTrace();
						throw ex;
					}
				}
			}

			throw new UsernameNotFoundException("Unknown user");
		}
	}

}