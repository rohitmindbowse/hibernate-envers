package com.mb.user.dao;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;
import com.mb.user.entity.User;
import com.mb.user.repository.UserRepo;

import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

	private final Environment environment;

	private final UserRepo userRepo;

	/**
	 * Saves a user entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param user must not be null
	 * @return {@link User} the saved user entity will never be null
	 * @throws CustomException if any exception occured while saving entity
	 */
	@Override
	public User saveUser(User user) {
		try {

			return userRepo.save(user);

		} catch (Exception e) {
			throw new CustomException(environment.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieve user by uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param uuid unique identifier of user entity. must not be null
	 * @return {@link User}
	 * @throws CustomException if user not found by uuid
	 */
	@Override
	public User userByUuid(String uuid) {

		return userRepo.findByUuid(uuid).orElseThrow(() -> new CustomException(
				environment.getProperty(ExceptionMessage.USER_NOT_FOUND_BY_UUID) + uuid, HttpStatus.NOT_FOUND));
	}

	/**
	 * User by username
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param username
	 * @return {@link User}
	 */
	@Override
	public User userByUsername(String username) {

		return userRepo.findByEmail(username);
	}

	/**
	 * delete  user 
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param uuid
	 */
	@Override
	public void deleteUser(User  user) {
		userRepo.delete(user);
		
	}
}
