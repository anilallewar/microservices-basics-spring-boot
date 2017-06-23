package com.anilallewar.microservices.task.oauth2.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Annotation to help us setup our own security context
 * @author anilallewar
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
@WithSecurityContext(factory = WithOAuth2MockAccessTokenSecurityContextFactory.class)
public @interface WithMockOAuth2Token {

	// Default OAuth2 scope
	String[] scopes() default { "openid" };

	// Default roles
	String[] authorities() default { "ROLE_ADMIN", "ROLE_USER" };

	// Default user name
	String userName() default "anil";

	// Default password
	String password() default "password";
	
	// Default redirect Url
	String redirectUrl() default "http://localhost:8765";
	
	// Default client id
	String clientId() default "acme";
}
