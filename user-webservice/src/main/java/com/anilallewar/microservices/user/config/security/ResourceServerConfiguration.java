package com.anilallewar.microservices.user.config.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Override the security properties for the resources exposed by this
 * application. NOTE that the {@link EnableResourceServer} annotation creates a
 * filter with Order as {@link SecurityProperties#ACCESS_OVERRIDE_ORDER} - 1,
 * hence this security filter would be applied before the filter defined for web
 * security at {@link OAuth2SecurityConfiguration}.<br>
 * <br>
 * 
 * What this essentially means is that the rules for the endpoint applied here
 * would be applied before the rules defined in the
 * {@link WebSecurityConfigurerAdapter} class.
 * 
 * @author anilallewar
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * ResourceServerConfigurerAdapter#configure(org.springframework.security.config
	 * .annotation.web.builders.HttpSecurity)
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
					.anyRequest()
						.authenticated();
		// @formatter:on
	}

}
