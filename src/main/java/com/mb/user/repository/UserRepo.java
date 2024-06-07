package com.mb.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.user.entity.User;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
public interface UserRepo extends JpaRepository<User, Long> {

	/**
	 * Retrieves an user entity by its uuid
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param uuid unique identifier of user entity. must not be null
	 * @return {@link Optional}
	 */
	Optional<User> findByUuid(String uuid);

	/**
	 * User by username, email or mobile
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param email
	 * @return {@link User}
	 */
	User findByEmail(String email);

}
