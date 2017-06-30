package com.anilallewar.microservices.user.api;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anilallewar.microservices.user.dto.UserDTO;

/**
 * REST endpoint for the user functionality
 * 
 * @author anilallewar
 *
 */
@RestController
@RequestMapping("/")
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@Value("${mail.domain ?: google.com}")
	private String mailDomain;

	private List<UserDTO> users = Arrays.asList(new UserDTO("Anil", "Allewar", "1", "anil.allewar@" + mailDomain),
			new UserDTO("Stefan", "Weber", "2", "stefan.weber@" + mailDomain),
			new UserDTO("John", "Snow", "3", "john.snow@" + mailDomain));

	/**
	 * Return all users
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<UserDTO> getUsers() {
		return users;
	}

	/**
	 * Return user associated with specific user name
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "{userName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserDTO getUserByUserName(@PathVariable("userName") String userName) {
		UserDTO userDtoToReturn = null;
		for (UserDTO currentUser : users) {
			if (currentUser.getUserName().equalsIgnoreCase(userName)) {
				userDtoToReturn = currentUser;
				if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.info(String.format("Found matching user: %s", userDtoToReturn.toString()));
				}
				break;
			}
		}

		return userDtoToReturn;
	}
}
