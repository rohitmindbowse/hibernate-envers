package com.mb.auth.service;

import com.mb.auth.dto.SignupDto;
import com.mb.auth.model.AuthToken;

import jakarta.validation.Valid;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 */
public interface AuthService {

	/**
	 * User signup
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param signupDto
	 * @return {@link AuthToken}
	 */
	AuthToken userSignup(@Valid SignupDto signupDto);

	/**
	 * User login
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param username
	 * @param password
	 * @return {@link AuthToken}
	 */
	AuthToken userLogin(String username, String password);

	/**
	 * Refresh token using access token
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param token
	 * @return {@link AuthToken}
	 */
	AuthToken refreshToken(String token);

}
