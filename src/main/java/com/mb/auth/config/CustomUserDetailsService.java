package com.mb.auth.config;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;
import com.mb.user.dao.UserDao;
import com.mb.user.entity.User;

import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	private final Environment environment;

	/**
	 * Custom implementation of loadUserBy method of spring security details service
	 * which returns security user object, If user not found then throws exception
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param email
	 * @return {@link UserDetails}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userDao.userByUsername(username);

		if (user == null) {
			throw new CustomException(environment.getProperty(ExceptionMessage.USER_NOT_FOUND_BY_USERNAME) + username,
					HttpStatus.NOT_FOUND);
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				user.getGrantedAuthorities());
	}
}
