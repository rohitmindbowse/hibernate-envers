package com.mb.user.service;

import com.mb.user.dto.UserDto;
import com.mb.user.model.UserModel;

import jakarta.validation.Valid;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public interface UserService {

	/**
	 * Create a user
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userDto
	 * @return {@link UserModel}
	 */
	UserModel createUser(@Valid UserDto userDto);

	/**
	 * User by uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @return {@link UserModel}
	 */
	UserModel userByUuid(String userUuid);

	/**
	 * Update user by user uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param request  body
	 * @return {@link UserModel}
	 */
	UserModel updateUser(String userUuid, @Valid UserDto userDto);

	/**
	 * Delete user by user uuid
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param userUuid unique identifier of user entity
	 * @param request
	 * @return {@link boolean}
	 */
	boolean deleteUser(String userUuid);

}
