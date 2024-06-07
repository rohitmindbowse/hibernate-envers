package com.mb.user.dao;

import com.mb.common.exception.CustomException;
import com.mb.user.entity.User;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public interface UserDao {

	/**
	 * Saves a user entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param user must not be null
	 * @return {@link User} the saved user entity will never be null
	 * @throws CustomException if user entity is null or any exception occured while
	 *                         saving entity
	 */
	User saveUser(User user);

	/**
	 * Retrieve user by uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param uuid unique identifier of user entity. must not be null
	 * @return {@link User}
	 * @throws CustomException if user not found by uuid
	 */
	User userByUuid(String uuid);
	

	/**
	 * delete  user 
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param uuid unique identifier of user entity. must not be null
	 * @throws CustomException if user not found by uuid
	 */
	void deleteUser(User user);

	/**
	 * User by username
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param username
	 * @return {@link User}
	 */
	User userByUsername(String username);
}
