/**
 * 
 */
package com.anilallewar.microservice.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Since the "me" endpoint needs to be protected to be accessed only after the
 * OAuth2 authentication is successful; the server also becomes a resource
 * server.
 * 
 * @author anilallewar
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends WebMvcConfigurerAdapter {

	@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}
	
	@Configuration
	@Order(-20)
	protected static class LoginConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		/**
		 * Configure security to allow access to the /me endpoint only if the OAuth
		 * authorization returns "read" scope.<br>
		 * <br>
		 * 
		 * If you look at
		 * {@link OAuthConfiguration#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)}
		 * to check that by default the authorization server allows "read" scope
		 * only.
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.formLogin().loginPage("/login").permitAll()
			.and()
				.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
			.and()
				.requestMatchers().antMatchers("/me")
			.and()
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.antMatchers("/me").access("#oauth2.hasScope('read')");
			// @formatter:on
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.parentAuthenticationManager(authenticationManager);
		}
	}
}
