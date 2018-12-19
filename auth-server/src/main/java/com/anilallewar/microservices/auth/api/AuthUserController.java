package com.anilallewar.microservices.auth.api;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint to be used by other micro-services using SSO to validate the
 * authentication of the logged in user.
 * 
 * Since the "me" endpoint needs to be protected to be accessed only after the
 * OAuth2 authentication is successful; the OAuth2 server also becomes a
 * resource server.
 * 
 * @author anilallewar
 *
 */
@RestController
public class AuthUserController {

	/**
	 * Return the principal identifying the logged in user
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(path = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Principal getCurrentLoggedInUser(Principal user) {
		return user;
	}
}
